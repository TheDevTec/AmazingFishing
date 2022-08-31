package me.devtec.amazingfishing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.construct.CatchFish;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.construct.FishType;
import me.devtec.amazingfishing.construct.Junk;
import me.devtec.amazingfishing.construct.Treasure;
import me.devtec.amazingfishing.utils.points.PointsManager;
import me.devtec.shared.Ref;
import me.devtec.shared.dataholder.Config;
import me.devtec.theapi.bukkit.nms.NBTEdit;

public class API {
	public static Map<String, Fish> fish = new ConcurrentHashMap<>();
	public static Map<String, Junk> junk = new ConcurrentHashMap<>();
	protected static Map<String, Treasure> treasure = new ConcurrentHashMap<>();
	protected static ItemStack cod = find("COD",0), salmon = find("SALMON",1)
			, pufferfish = find("PUFFERFISH",2), tropical_fish = find("TROPICAL_FISH",3)
			,head = find("PLAYER_HEAD", 0);
	protected static PointsManager points;
	protected static List<Runnable> onReload = new ArrayList<>();
	
	public static ItemStack getMaterialOf(FishType type) {
		if(type==FishType.JUNK) {
			return find(Loader.gui.getString("GUI.Index.Items.junk"), 0);
		}
		return type==FishType.COD?cod:(type==FishType.SALMON?salmon:(type==FishType.TROPICAL_FISH?tropical_fish:pufferfish));
	}

	public static void addRunnableOnReload(Runnable r) {
		onReload.add(r);
	}

	public static void removeRunnableOnReload(Runnable r) {
		onReload.remove(r);
	}

	public static List<Runnable> getRunnablesOnReload() {
		return onReload;
	}
	
	public static Fish register(Fish fish) {
		API.fish.put(fish.getName()+fish.getType().ordinal(), fish);
		return fish;
	}
	
	public static Map<String, Fish> getRegisteredFish() {
		return fish;
	}
	
	public static Fish unregister(Fish fish) {
		return API.fish.remove(fish.getName()+fish.getType().ordinal());
	}
	
	public static Fish getFish(FishType type, String name) {
		return API.fish.get(name+type.ordinal());
	}
	
	public static Map<String, Treasure> getRegisteredTreasures() {
		return treasure;
	}
	public static Map<String, Junk> getRegisteredJunk() {
		return junk;
	}
	
	public static PointsManager getPoints() {
		return points;
	}
	
	public static Treasure register(Treasure treas) {
		API.treasure.put(treas.getName(), treas);
		return treas;
	}
	
	public static Treasure unregister(Treasure treas) {
		return API.treasure.remove(treas.getName());
	}
	
	public static Junk register(Junk junk) {
		API.junk.put(junk.getName(), junk);
		return junk;
	}
	
	public static Junk unregister(Junk junk) {
		return API.junk.remove(junk.getName());
	}
	
	public static boolean isAFItem(ItemStack stack) {
		return new NBTEdit(stack).hasKey("af_data");
	}

	public static boolean isFishItem(ItemStack stack) {
		if(stack.getType()==head.getType())
			return true;
		return (stack.getType() == cod.getType() && Ref.isNewerThan(12)) || (stack.getData().getData() == cod.getData().getData()) ||
				(stack.getType() == salmon.getType() && Ref.isNewerThan(12)) || (stack.getData().getData() == salmon.getData().getData()) ||
				(stack.getType() == pufferfish.getType() && Ref.isNewerThan(12)) || (stack.getData().getData() == pufferfish.getData().getData()) ||
				(stack.getType() == tropical_fish.getType() && Ref.isNewerThan(12)) || (stack.getData().getData() == tropical_fish.getData().getData());
	}
	
	public static Fish getFish(ItemStack stack) {
		NBTEdit edit = new NBTEdit(stack);
		Config data = new Config();
		if(edit.hasKey("af_data"))
			data.reload(edit.getString("af_data"));
		
		if(data.getKeys().isEmpty())
			return null;
		for(Fish f : fish.values()) {
			if(f.isInstance(data))
				return f;
		}
		return null;
	}
	
	public static CatchFish getCatchFish(ItemStack stack) {
		NBTEdit edit = new NBTEdit(stack);
		Config data = new Config();
		if(edit.hasKey("af_data"))
			data.reload(edit.getString("af_data"));
		if(data.getKeys().isEmpty())return null;
		String id = data.getString("type");
		if(id!=null && id.equalsIgnoreCase("junk"))return null;
		for(Fish f : fish.values())
			if(f.isInstance(data))
				return f.createCatchFish(data);
		return null;
	}
	
	public static Junk getJunk(ItemStack stack) {
		NBTEdit edit = new NBTEdit(stack);
		Config data = new Config();
		if(edit.hasKey("af_data"))
			data.reload(edit.getString("af_data"));
		if(data.getKeys().isEmpty())return null;
		for(Junk junk : junk.values())
			if(junk.isInstance(data))return junk;
		return null;
	}
	
	//UTILITY
	private static ItemStack find(String name, int id) {
		if(Material.getMaterial(name)!=null)return new ItemStack(Material.getMaterial(name));
		return new ItemStack(Material.getMaterial("RAW_FISH"),1,(short)0,(byte)id);
	}

	public static boolean isConvertable(ItemStack convert) {
		return false;
	}

	public static ItemStack getConvertion(ItemStack convert) {
		return null;
	}
}

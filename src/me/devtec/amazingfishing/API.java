package me.devtec.amazingfishing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.construct.CatchFish;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.construct.FishType;
import me.devtec.amazingfishing.construct.Treasure;
import me.devtec.amazingfishing.utils.Utils;
import me.devtec.amazingfishing.utils.points.PointsManager;
import me.devtec.theapi.utils.datakeeper.Data;

public class API {
	public static Map<String, Fish> fish = new HashMap<>();
	protected static Map<String, Treasure> treasure = new HashMap<>();
	protected static Material cod = find("COD",0), salmon = find("SALMON",1)
			, pufferfish = find("PUFFERFISH",2), tropical_fish = find("TROPICAL_FISH",3);
	protected static PointsManager points;
	protected static List<Runnable> onReload = new ArrayList<>();
	
	public static Material getMaterialOf(FishType type) {
		return type==FishType.COD?cod:(type==FishType.SALMON?salmon:(type==FishType.TROPICAL_FISH?tropical_fish:pufferfish));
	}
	
	public static void addRunnableOnReload(Runnable r) {
		onReload.add(r);
	}
	
	public static Fish register(Fish fish) {
		API.fish.put(fish.getName()+fish.getType().ordinal(), fish);
		return fish;
	}
	
	public static Map<String, Fish> getRegisteredFish() {
		return new HashMap<>(fish);
	}
	
	public static Fish unregister(Fish fish) {
		return API.fish.remove(fish.getName()+fish.getType().ordinal());
	}
	
	public static Fish getFish(FishType type, String name) {
		return API.fish.get(name+type.ordinal());
	}
	
	public static Map<String, Treasure> getRegisteredTreasures() {
		return new HashMap<>(treasure);
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
	
	public static boolean isFish(ItemStack stack) {
		return isFishItem(stack)?Utils.hasString(Utils.getNBT(Utils.asNMS(stack))):false;
	}

	public static boolean isFishItem(ItemStack stack) {
		return stack.getType()==cod||stack.getType()==salmon||stack.getType()==pufferfish||stack.getType()==tropical_fish;
	}
	
	public static Fish getFish(ItemStack stack) {
		if(!isFishItem(stack))return null;
		Data data = Utils.getString(Utils.getNBT(Utils.asNMS(stack)));
		for(Fish f : fish.values())
			if(f.isInstance(data))return f;
		return null;
	}
	
	public static CatchFish getCatchFish(ItemStack stack) {
		if(!isFishItem(stack))return null;
		Data data = Utils.getString(Utils.getNBT(Utils.asNMS(stack)));
		for(Fish f : fish.values())
			if(f.isInstance(data))
				return f.createCatchFish(data);
		return null;
	}
	
	//UTILITY
	private static Material find(String name, int id) {
		if(Material.getMaterial(name)!=null)return Material.getMaterial(name);
		return new ItemStack(Material.getMaterial("RAW_FISH"),1,(short)id).getType();
	}
}

package me.devtec.amazingfishing;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.construct.Treasure;
import me.devtec.amazingfishing.utils.Utils;
import me.devtec.amazingfishing.utils.points.PointsManager;
import me.devtec.theapi.utils.datakeeper.Data;

public class API {
	protected static Map<String, Fish> fish = new HashMap<>();
	protected static Map<String, Treasure> treasure = new HashMap<>();
	protected static Material cod = find("COD",0), salmon = find("SALMON",1)
			, pufferfish = find("PUFFERFISH",2), tropical_fish = find("TROPICAL_FISH",3);
	protected static PointsManager points;
	
	public static void register(Fish fish) {
		API.fish.put(fish.getName()+fish.getType().ordinal(), fish);
	}
	
	public static Map<String, Fish> getRegisteredFish() {
		return new HashMap<>(fish);
	}
	
	public static void unregister(Fish fish) {
		API.fish.remove(fish.getName()+fish.getType().ordinal());
	}
	
	public static Map<String, Treasure> getRegisteredTreasures() {
		return new HashMap<>(treasure);
	}
	
	public static PointsManager getPoints() {
		return points;
	}
	
	public static void register(Treasure fish) {
		API.treasure.put(fish.getName(), fish);
	}
	
	public static void unregister(Treasure fish) {
		API.treasure.remove(fish.getName());
	}
	
	public static boolean isFish(ItemStack stack) {
		return isFishItem(stack)?Utils.hasString(Utils.getNBT(Utils.asNMS(stack))):false;
	}

	public static boolean isFishItem(ItemStack stack) {
		return stack.getType()==cod||stack.getType()==salmon||stack.getType()==pufferfish||stack.getType()==tropical_fish;
	}
	
	public static Fish getFish(ItemStack stack) {
		Data data = Utils.getString(Utils.getNBT(Utils.asNMS(stack)));
		for(Fish f : fish.values())
			if(f.isInstance(data))return f;
		return null;
	}
	
	//UTILITY
	@SuppressWarnings("deprecation")
	private static Material find(String name, int id) {
		if(Material.getMaterial(name)!=null)return Material.getMaterial(name);
		return new ItemStack(Material.getMaterial("RAW_FISH"),1,(short)id).getType();
	}
}

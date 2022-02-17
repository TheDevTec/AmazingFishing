package me.devtec.amazingfishing.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import me.devtec.amazingfishing.Loader;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.configapi.Config;
import me.devtec.theapi.utils.StringUtils;

public class Utils {
	public static ItemStack setModel(ItemStack s, int model) {
		if(model==0)return s;
		try {
			ItemMeta meta = s.getItemMeta();
			meta.setCustomModelData(model);
			s.setItemMeta(meta);
			return s;
		}catch(Exception | NoSuchMethodError | NoSuchFieldError e) {
			s.setDurability((short)model);
			return s;
		}
	}
	
	static Map<String, MaterialData> mat = new HashMap<>();
	static {
		mat.put("BLACK_STAINED_GLASS_PANE", find("BLACK_STAINED_GLASS_PANE", "STAINED_GLASS_PANE", 15));
		mat.put("BLUE_STAINED_GLASS_PANE", find("BLUE_STAINED_GLASS_PANE", "STAINED_GLASS_PANE", 11));
		mat.put("SUNFLOWER", find("SUNFLOWER", "DOUBLE_PLANT", 0));
	}
	
	static MaterialData find(String newName, String old, int data) {
		try {
			return new MaterialData(Material.getMaterial(newName.toUpperCase()));
		}catch(Exception | NoSuchFieldError e) {
			try {
			if(data==0)return new MaterialData(Material.getMaterial(old.toUpperCase()));
			return new MaterialData(Material.getMaterial(old.toUpperCase()),(byte)data);
			}catch(Exception | NoSuchFieldError er) {
				return null;
			}
		}
	}
	
	public static void fixDefaultConfig() {
		if(!TheAPI.isOlderThan(13))return;
		boolean save = false;
		for(String key : Loader.gui.getKeys(true)) {
			if(key.endsWith(".icon") && Loader.gui.getString(key)!=null) {
				switch(Loader.gui.getString(key).toUpperCase()) {
				case "ENCHANTING_TABLE":
					Loader.gui.set(key, "ENCHANTMENT_TABLE");
					save=true;
					break;
				case "SUNFLOWER":
					Loader.gui.set(key, "DOUBLE_PLANT");
					save=true;
					break;
				case "RED_STAINED_GLASS_PANE":
					Loader.gui.set(key, "STAINED_GLASS_PANE:14");
					save=true;
					break;
				case "COD":
					Loader.gui.set(key, "RAW_FISH");
					save=true;
					break;
				case "SALMON":
					Loader.gui.set(key, "RAW_FISH:1");
					save=true;
					break;
				case "PUFFERFISH":
					Loader.gui.set(key, "RAW_FISH:2");
					save=true;
					break;
				case "TROPICAL_FISH":
					Loader.gui.set(key, "RAW_FISH:3");
					save=true;
					break;
				case "CRAFTING_TABLE":
					Loader.gui.set(key, "WORKBENCH");
					save=true;
					break;
				case "GREEN_CONCRETE":
					Loader.gui.set(key, Material.getMaterial("CONCRETE")!=null?"CONCRETE:13":"STAINED_GLASS_PANE:13");
					save=true;
					break;
				case "RED_CONCRETE":
					Loader.gui.set(key, Material.getMaterial("CONCRETE")!=null?"CONCRETE:14":"STAINED_GLASS_PANE:14");
					save=true;
					break;
				}
			}
		}
		if(save)
			Loader.gui.save();
	}
	
	public static MaterialData getCachedMaterial(String name) {
		return mat.getOrDefault(name.toUpperCase(), new MaterialData(Material.STONE));
	}
	
	public static void convertFiles() {
		if(new File("plugins/AmazingFishing/Data.yml").exists()) {
			Config c = new Config("AmazingFishing/Data.yml");
			c.getData().getFile().renameTo(new File("plugins/AmazingFishing/Data.yml-Backup"));
			//FISH
			for(String s : c.getKeys("fish.cod",true)) {
				Loader.cod.set("cod."+s, c.get("fish.cod."+s));
			}
			Loader.cod.save();
			for(String s : c.getKeys("fish.salmon",true)) {
				Loader.salmon.set("salmon."+s, c.get("fish.salmon."+s));
			}
			Loader.salmon.save();
			for(String s : c.getKeys("fish.pufferfish",true)) {
				Loader.puffer.set("pufferfish."+s, c.get("fish.pufferfish."+s));
			}
			Loader.puffer.save();
			for(String s : c.getKeys("fish.tropical_fish",true)) {
				Loader.tropic.set("tropical_fish."+s, c.get("fish.tropical_fish."+s));
			}
			Loader.tropic.save();
			
			//OTHER
			for(String s : c.getKeys("enchantments",true)) {
				Loader.enchant.set("enchantments."+s, c.get("enchantments."+s));
			}
			Loader.enchant.save();
			for(String s : c.getKeys("quests",true)) {
				Loader.quest.set("quests."+s, c.get("quests."+s));
			}
			Loader.quest.save();
		}
	}

	public static MaterialData createType(String s) {
		try {
			return new MaterialData(Material.getMaterial(s.split(":")[0].toUpperCase()), s.contains(":")?(byte)StringUtils.getInt(s.split(":")[1]):(byte)0);
		}catch(Exception | NoSuchFieldError er) {
			return new MaterialData(Material.STONE);
		}
	}
}

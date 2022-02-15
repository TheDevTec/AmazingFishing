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
		mat.put("RED_STAINED_GLASS_PANE", find("RED_STAINED_GLASS_PANE", "STAINED_GLASS_PANE", 14));
		mat.put("LIME_STAINED_GLASS_PANE", find("LIME_STAINED_GLASS_PANE", "STAINED_GLASS_PANE", 5));
		mat.put("COD_BUCKET", find("COD_BUCKET", "WATER_BUCKET", 0));
		mat.put("KNOWLEDGE_BOOK", find("KNOWLEDGE_BOOK", "BOOK", 0));
		mat.put("CRAFTING_TABLE", find("CRAFTING_TABLE", "WORKBENCH", 0));
		mat.put("PURPLE_STAINED_GLASS_PANE", find("PURPLE_STAINED_GLASS_PANE", "STAINED_GLASS_PANE", 10));
		mat.put("LAPIS_LAZULI", find("LAPIS_LAZULI", "INK_SACK", 4));
		mat.put("SUNFLOWER", find("SUNFLOWER", "DOUBLE_PLANT", 0));
		mat.put("BLUE_CONCRETE_POWDER", find("BLUE_CONCRETE_POWDER", "CONCRETE_POWDER", 11)==null?find("BLUE_STAINED_GLASS_PANE", "STAINED_GLASS_PANE", 11):find("BLUE_CONCRETE_POWDER", "CONCRETE_POWDER", 11));
		mat.put("GREEN_CONCRETE", find("GREEN_CONCRETE", "CONCRETE", 13)==null?find("GREEN_STAINED_GLASS_PANE", "STAINED_GLASS_PANE", 13):find("GREEN_CONCRETE", "CONCRETE", 13));
		mat.put("RED_CONCRETE", find("RED_CONCRETE", "CONCRETE", 14)==null?find("RED_STAINED_GLASS_PANE", "STAINED_GLASS_PANE", 14):find("RED_CONCRETE", "CONCRETE", 14));
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
		Config c = Loader.gui;
		boolean save = false;
		if(c.getString("help.enchant.icon").equalsIgnoreCase("ENCHANTING_TABLE")) {
			c.set("help.enchant.icon", "ENCHANTMENT_TABLE");
			save=true;
		}
		if(c.getString("help.achievements.icon").equalsIgnoreCase("SUNFLOWER")) {
			c.set("help.achievements.icon", "DOUBLE_PLANT");
			save=true;
		}
		if(c.getString("help.close.icon").equalsIgnoreCase("RED_STAINED_GLASS_PANE")) {
			c.set("help.close.icon", "STAINED_GLASS_PANE:14");
			save=true;
		}
		if(c.getString("convertor.close.icon").equalsIgnoreCase("RED_STAINED_GLASS_PANE")) {
			c.set("convertor.close.icon", "STAINED_GLASS_PANE:14");
			save=true;
		}
		if(c.getString("index.close.icon").equalsIgnoreCase("RED_STAINED_GLASS_PANE")) {
			c.set("index.close.icon", "STAINED_GLASS_PANE:14");
			save=true;
		}
		if(c.getString("index.cod.icon").equalsIgnoreCase("COD")) {
			c.set("index.cod.icon", "RAW_FISH");
			save=true;
		}
		if(c.getString("index.salmon.icon").equalsIgnoreCase("SALMON")) {
			c.set("index.salmon.icon", "RAW_FISH:1");
			save=true;
		}
		if(c.getString("index.pufferfish.icon").equalsIgnoreCase("PUFFERFISH")) {
			c.set("index.pufferfish.icon", "RAW_FISH:2");
			save=true;
		}
		if(c.getString("index.tropical_fish.icon").equalsIgnoreCase("TROPICAL_FISH")) {
			c.set("index.tropical_fish.icon", "RAW_FISH:3");
			save=true;
		}
		if(c.getString("shops.points.icon").equalsIgnoreCase("LAPIS_LAZULI")) {
			c.set("shops.points.icon", "DYE:4");
			save=true;
		}
		if(c.getString("shops.close.icon").equalsIgnoreCase("RED_STAINED_GLASS_PANE")) {
			c.set("shops.close.icon", "STAINED_GLASS_PANE:14");
			save=true;
		}
		if(c.getString("shops.convertor.icon").equalsIgnoreCase("COD")) {
			c.set("shops.convertor.icon", "RAW_FISH");
			save=true;
		}
		if(c.getString("shops.sell.close.icon").equalsIgnoreCase("RED_STAINED_GLASS_PANE")) {
			c.set("shops.sell.close.icon", "STAINED_GLASS_PANE:14");
			save=true;
		}
		if(c.getString("shops.buy.close.icon").equalsIgnoreCase("RED_STAINED_GLASS_PANE")) {
			c.set("shops.buy.close.icon", "STAINED_GLASS_PANE:14");
			save=true;
		}
		if(c.getString("bag.close.icon").equalsIgnoreCase("RED_STAINED_GLASS_PANE")) {
			c.set("bag.close.icon", "STAINED_GLASS_PANE:14");
			save=true;
		}
		if(c.getString("enchant.close.icon").equalsIgnoreCase("RED_STAINED_GLASS_PANE")) {
			c.set("enchant.close.icon", "STAINED_GLASS_PANE:14");
			save=true;
		}
		if(c.getString("enchant.add.icon").equalsIgnoreCase("CRAFTING_TABLE")) {
			c.set("enchant.add.icon", "WORKBENCH");
			save=true;
		}
		if(c.getString("enchant.points.icon").equalsIgnoreCase("LAPIS_LAZULI")) {
			c.set("enchant.points.icon", "DYE:4");
			save=true;
		}
		if(c.getString("settings.records.on.icon").equalsIgnoreCase("GREEN_CONCRETE")) {
			c.set("settings.records.on.icon", Material.getMaterial("CONCRETE")!=null?"CONCRETE:13":"STAINED_GLASS_PANE:13");
			save=true;
		}
		if(c.getString("settings.records.off.icon").equalsIgnoreCase("RED_CONCRETE")) {
			c.set("settings.records.off.icon", Material.getMaterial("CONCRETE")!=null?"CONCRETE:14":"STAINED_GLASS_PANE:14");
			save=true;
		}
		if(c.getString("settings.close.icon").equalsIgnoreCase("RED_STAINED_GLASS_PANE")) {
			c.set("settings.close.icon", "STAINED_GLASS_PANE:14");
			save=true;
		}
		if(c.getString("quests.close.icon").equalsIgnoreCase("RED_STAINED_GLASS_PANE")) {
			c.set("quests.close.icon", "STAINED_GLASS_PANE:14");
			save=true;
		}
		if(c.getString("achievements.close.icon").equalsIgnoreCase("RED_STAINED_GLASS_PANE")) {
			c.set("achievements.close.icon", "STAINED_GLASS_PANE:14");
			save=true;
		}
		if(save)
			c.save();
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

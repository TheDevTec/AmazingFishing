package me.devtec.amazingfishing.utils;

import java.io.File;
import java.lang.reflect.Method;
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
import me.devtec.theapi.utils.datakeeper.Data;
import me.devtec.theapi.utils.datakeeper.DataType;
import me.devtec.theapi.utils.reflections.Ref;

public class Utils {
	//NMS
	private static Method asNMSCopy = Ref.method(Ref.craft("inventory.CraftItemStack"), "asNMSCopy", ItemStack.class), 
			asBukkitCopy = Ref.method(Ref.craft("inventory.CraftItemStack"), "asBukkitCopy", Ref.nms("ItemStack")), 
			getOrCreateTag = Ref.method(Ref.nms("ItemStack"), "getOrCreateTag"),
			setString =  Ref.method(Ref.nms("NBTTagCompound"), "setString", String.class, String.class),
			getString =  Ref.method(Ref.nms("NBTTagCompound"), "getString", String.class),
			hasKey =  Ref.method(Ref.nms("NBTTagCompound"), "hasKey", String.class);

	public static Object asNMS(ItemStack stack) {
		return Ref.invokeNulled(asNMSCopy, stack);
	}

	public static ItemStack asBukkit(Object stack) {
		return (ItemStack) Ref.invokeNulled(asBukkitCopy, stack);
	}
	
	public static Object getNBT(Object stack) {
		return Ref.invoke(stack,getOrCreateTag);
	}
	
	public static void setString(Object nbt, Data data) {
		Ref.invoke(nbt, setString, "af_data", data.toString(DataType.JSON));
	}
	
	public static Data getString(Object nbt) {
		Data data = new Data();
		data.reload((String)Ref.invoke(nbt, getString, "af_data"));
		return data;
	}

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
		if(c.getString("GUI.Settings.SendRecords.on.Item").equalsIgnoreCase("GREEN_CONCRETE")) {
			c.set("GUI.Settings.SendRecords.on.Item", "CONCRETE:13");
			save=true;
		}
		if(c.getString("GUI.Settings.SendRecords.off.Item").equalsIgnoreCase("RED_CONCRETE")) {
			c.set("GUI.Settings.SendRecords.off.Item", "CONCRETE:14");
			save=true;
		}
		if(save)
		c.save();
	}
	
	public static MaterialData getCachedMaterial(String name) {
		return mat.getOrDefault(name.toUpperCase(), new MaterialData(Material.STONE));
	}
	
	public static boolean hasString(Object nbt) {
		return (boolean)Ref.invoke(nbt, hasKey, "af_data");
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
		return new MaterialData(Material.getMaterial(s.split(":")[0].toUpperCase()), s.contains(":")?(byte)StringUtils.getInt(s.split(":")[1]):(byte)0);
	}
}

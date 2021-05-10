package me.devtec.amazingfishing.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

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
	
	static Map<String, Material> mat = new HashMap<>();
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
	
	static Material find(String newName, String old, int data) {
		try {
			return Material.getMaterial(newName);
		}catch(Exception | NoSuchFieldError e) {
			try {
			if(data==0)return Material.getMaterial(old);
			return new MaterialData(Material.getMaterial(old),(byte)data).getItemType();
			}catch(Exception | NoSuchFieldError er) {
				return null;
			}
		}
	}
	
	public static Material getCachedMaterial(String name) {
		return mat.getOrDefault(name.toUpperCase(), null);
	}
	
	public static boolean hasString(Object nbt) {
		return (boolean)Ref.invoke(nbt, hasKey, "af_data");
	}
}

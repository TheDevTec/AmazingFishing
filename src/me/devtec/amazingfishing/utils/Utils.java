package me.devtec.amazingfishing.utils;

import java.lang.reflect.Method;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
	
	public static boolean hasString(Object nbt) {
		return (boolean)Ref.invoke(nbt, hasKey, "af_data");
	}
}
package AmazingFishing;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.devtec.theapi.apis.ItemCreatorAPI;
import me.devtec.theapi.utils.datakeeper.Data;
import me.devtec.theapi.utils.datakeeper.DataType;
import me.devtec.theapi.utils.reflections.Ref;

public class Tag {
	
	/*
	 *   Special methods
	 */
	
	
	public static ItemStack add(double length, double weight, String type, String fish, ItemCreatorAPI i) {
		if(i==null) return null;
		 	ItemStack stack = i.create();
		 	Data d = new Data();
		 	d.set("af.length", length);
		 	d.set("af.weight", weight);
		 	d.set("af.type", type);
		 	d.set("af.fish", fish);
		 	setData(stack, d);
			return stack;
	}
	
	public static void setData(ItemStack item, Data data) {
		/*Data d = new Data();
		TheAPI.bcMsg(data.toString(DataType.YAML));
		d.reload(data.toString(DataType.BYTE));
		TheAPI.bcMsg(d.toString(DataType.YAML));*/
		Object itemstack = Ref.invokeNulled(Ref.method(Ref.craft("inventory.CraftItemStack"), "asNMSCopy", ItemStack.class), item);
		Ref.invoke(Ref.invoke(itemstack,"getOrCreateTag"),
		    Ref.method(Ref.nms("NBTTagCompound"), "setString", String.class, String.class), "thedata", data.toString(DataType.BYTE));
		item.setItemMeta((ItemMeta) Ref.invokeNulled(Ref.method(Ref.craft("inventory.CraftItemStack"), "getItemMeta", Ref.nms("ItemStack")), itemstack));
		}
	public static Data getData(ItemStack bukkitstack) {
		Object itemstack = Ref.invokeNulled(Ref.method(Ref.craft("inventory.CraftItemStack"), "asNMSCopy", ItemStack.class), bukkitstack);
		Data d = new Data();
		d.reload((String)Ref.invoke(Ref.invoke(itemstack,"getOrCreateTag"), Ref.method(Ref.nms("NBTTagCompound"), "getString", String.class), "thedata"));
		return d;
	}
}

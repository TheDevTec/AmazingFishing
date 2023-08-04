package me.devtec.amazingfishing.fishing;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.fishing.enums.FishType;
import me.devtec.amazingfishing.utils.MessageUtils;
import me.devtec.shared.dataholder.Config;
import me.devtec.shared.dataholder.loaders.DataLoader;
import me.devtec.theapi.bukkit.nms.NBTEdit;

public class CaughtItem{

	private ItemStack item;
	
	private FishType type;
	
	private Config data;
	
	private FishingItem fishItem;
	
	public CaughtItem(ItemStack item) {
		this.item = item;
		
		identify();
	}

	private void identify() {
		NBTEdit edit = new NBTEdit(item);
		data = new Config();
		if (edit.hasKey("af_data"))
			data.reload(edit.getString("af_data"));

		Bukkit.broadcastMessage(edit.getString("af_data"));
		Bukkit.broadcastMessage("Loader: "+DataLoader.findLoaderFor(edit.getString("af_data")).toString());
		
		if(data.getKeys().isEmpty())
			return;
		MessageUtils.sendAnnouncement("Type: "+data.getString("type"));
		type = FishType.value(data.getString("type"));
		MessageUtils.sendAnnouncement("The item type is: "+type.toString());
		
		fishItem = API.getItem(data.getString("file"));
	}
	
	public FishingItem getItem() {
		return fishItem;
	}
	
	public FishType getType() {
		return type;
	}
	
	
	public double getWeight() {
		if(data.exists("weigth"))
			return data.getDouble("weigth");
		return 0;
	}
	public double getLength() {
		if(data.exists("length"))
			return data.getDouble("length");
		return 0;
	}
	
	/*
	 * af_data:
	 *   type: fish|junk
	 *   file: <name of the file>
	 *   
	 *   weight: 
	 *   length: 
	 *   fisher: <plyer who caught this fish>
	 *   biome: <where this item was caught>
	 *   time:
	 *   weather:
	 *   addhunger: value # if this item should be edible and how much hunger it should add
	 *   
	 *   
	 */
}

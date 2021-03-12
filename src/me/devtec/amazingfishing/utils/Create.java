package me.devtec.amazingfishing.utils;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.devtec.theapi.apis.ItemCreatorAPI;
import me.devtec.theapi.guiapi.EmptyItemGUI;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.ItemGUI;

public class Create {
	public static ItemStack createItem(String name, Material material) {
    	ItemCreatorAPI a = new ItemCreatorAPI(material);
    	a.setDisplayName(name);
    	return a.create();
    }
    
	public static ItemStack createItem(String name, Material material, List<String> lore) {
    	ItemCreatorAPI a = new ItemCreatorAPI(material);
    	a.setDisplayName(name);
    	a.setLore(lore);
    	return a.create();
    }
	
	static ItemGUI item = new EmptyItemGUI(ItemCreatorAPI.create(Utils.getCachedMaterial("BLACK_STAINED_GLASS_PANE"), 1, "&c"));
	static ItemGUI blue = new EmptyItemGUI(ItemCreatorAPI.create(Utils.getCachedMaterial("BLUE_STAINED_GLASS_PANE"), 1, "&c"));
	static ItemGUI purple = new EmptyItemGUI(ItemCreatorAPI.create(Utils.getCachedMaterial("PURPLE_STAINED_GLASS_PANE"), 1, "&c"));
	
	public static GUI prepareInv(GUI inv) {
		for(int i = 0; i<10; ++i)
		inv.setItem(i, item);

		inv.setItem(17, item);
		inv.setItem(18, item);
		inv.setItem(26, item);
		inv.setItem(27, item);
		inv.setItem(35, item);
		inv.setItem(36, item);
		for(int i = 44; i<54; ++i)
		inv.setItem(i,  item);
		return inv;
	}
	public static GUI prepareInvBig(GUI inv) {
		for(int i = 45; i<54; ++i)
		inv.setItem(i, item);
		return inv;
	}
	public static GUI prepareInvSmall(GUI inv) {
		for(int i = 9; i<18; ++i)
		inv.setItem(i, item);
		return inv;
	}
	public static GUI prepareInvCount(GUI inv, int slots) {
		for(int i = 0; i<slots; ++i)
		inv.setItem(i, item);
		return inv;
	}
	
	public static GUI prepareNewBig(GUI inv, int type) {
		ItemGUI blue = type==0?Create.blue:Create.purple;
		inv.setItem(0, blue);
		inv.setItem(1, blue);
		inv.setItem(7, blue);
		inv.setItem(8, blue);
		inv.setItem(9, blue);
		inv.setItem(17, blue);
		
		inv.setItem(36, blue);
		inv.setItem(44, blue);
		inv.setItem(45, blue);
		inv.setItem(46, blue);
		inv.setItem(52, blue);
		inv.setItem(53, blue);
		while(inv.getFirstEmpty() != -1)
			inv.addItem(item);
		return inv;
	}
}

package me.devtec.amazingfishing.other;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.utils.Manager;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.utils.datakeeper.User;

public class Rod {

	/*public static ItemStack createItem(String name, int amount, Material material, List<String> lore, HashMap<Enchantment, Integer> enchs, boolean unb, boolean hide) {
		try {
			if(amount==0)amount=1;
			ItemStack i = new ItemStack(material, amount);
	        ItemMeta im = i.getItemMeta();
	        if(name != null)
	        im.setDisplayName(name);
	        if(lore != null)
	        im.setLore(lore);
	        im.setUnbreakable(unb);
	        if(!enchs.isEmpty())
	        	for(Enchantment e:enchs.keySet()) {
	        		int level = enchs.get(e);
	        		if(level==0)level=1;
	        im.addEnchant(e, level, true);
	        	}
	        if(hide)
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	        i.setItemMeta(im);
	        return i;
		}catch(Exception e) {
			return null;
		} 
	}*/ //Není třeba
	
	public static void saveRod(Player p, ItemStack i) {
		User u = TheAPI.getUser(p);
		u.set(Manager.getDataLocation()+".SavedRod",i);
		u.save();
	}
	public static ItemStack getRod(Player p) {
		User u = TheAPI.getUser(p);
		if(u.exists(Manager.getDataLocation()+".SavedRod"))
			return ((ItemStack)u.get(Manager.getDataLocation()+".SavedRod"));
		return null;
	}
	public static boolean saved(Player p) {
		User u = TheAPI.getUser(p);
		if(u.exist(Manager.getDataLocation()+".SavedRod")) 
			return true;
		else return false;
	}
	public static void deleteRod(Player p) {
		User u = TheAPI.getUser(p);
		u.remove(Manager.getDataLocation()+".SavedRod");
		u.save();
	}
	public static void retriveRod(Player p) {
	ItemStack rod = Rod.getRod(p);
	TheAPI.giveItem(p, rod);
	Rod.deleteRod(p);
	}
}


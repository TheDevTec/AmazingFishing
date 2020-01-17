package AmazingFishing;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Normal {
	public static ItemStack createItem(String name, int amount, Material material, List<String> lore, HashMap<Enchantment, Integer> enchs, boolean unb, boolean hide) {
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
	}
	public static void giveRod(Player p) {
		String path = "Players."+p.getName()+".SavedRod";
		if(Loader.me.getString(path)!=null) {
		ItemStack i = Loader.me.getItemStack(path);
		if (p.getInventory().firstEmpty() == -1) {
	    p.getWorld().dropItemNaturally(p.getLocation(),i);
	    } else {
		p.getInventory().addItem(i);
	    }
		Loader.me.set(path,null);
		Loader.saveChatMe();
		}}
	public static void takeRod(Player p, ItemStack i) {
		Loader.me.set("Players."+p.getName()+".SavedRod",i);
		Loader.saveChatMe();
	}
	public static ItemStack getRod(Player p) {
		String path = "Players."+p.getName()+".SavedRod";
		if(Loader.me.getString(path)!=null)return Loader.me.getItemStack(path);
		return null;
	}
}

package me.devtec.amazingfishing.other;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Rod {
	private static HashMap<Player, ItemStack> rod = new HashMap<Player, ItemStack>();
	
	public static void saveRod(Player p, ItemStack i) {
		rod.put(p, i);
	}
	public static ItemStack getRod(Player p) {
		return rod.getOrDefault(p, null);
	}
	public static boolean saved(Player p) {
		return rod.containsKey(p);
	}
	public static void deleteRod(Player p) {
		rod.remove(p);
	}
	public static void retriveRod(Player p) {
		p.getInventory().addItem(rod.remove(p));
	}
}


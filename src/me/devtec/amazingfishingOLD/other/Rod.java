package me.devtec.amazingfishingOLD.other;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.theapi.bukkit.BukkitLoader;

public class Rod {
	private static Map<Player, ItemStack> rod = new ConcurrentHashMap<>();

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
		BukkitLoader.getNmsProvider().postToMainThread(() -> p.getInventory().addItem(rod.remove(p)));

	}
}

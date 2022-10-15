package me.devtec.amazingfishing.gui;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Enchant;
import me.devtec.amazingfishing.other.Rod;
import me.devtec.amazingfishing.utils.Create;
import me.devtec.amazingfishing.utils.Create.Settings;
import me.devtec.shared.dataholder.Config;
import me.devtec.theapi.bukkit.BukkitLoader;
import me.devtec.theapi.bukkit.gui.GUI;
import me.devtec.theapi.bukkit.gui.GUI.ClickType;
import me.devtec.theapi.bukkit.gui.HolderGUI;
import me.devtec.theapi.bukkit.gui.ItemGUI;
import me.devtec.theapi.bukkit.nms.NBTEdit;

public class EnchantTable {
	public static boolean openMain(Player p) {
		if (Enchant.enchants.isEmpty())
			return false;
		GUI a = Create.setup(new GUI(Create.title("enchant.title-select"), 54), Create.make("enchant.close").build(), EnchantTable::openMain, me.devtec.amazingfishing.utils.Create.Settings.SIDES,
				Settings.CLOSE);
		int slot = -1;
		boolean add = false;
		for (ItemStack item : p.getInventory().getContents()) {
			++slot;
			if (item == null || item.getType() != Material.FISHING_ROD || containsAny(item))
				continue;
			int ss = slot;
			add = true;
			a.addItem(new ItemGUI(item) {
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType ctype) {
					Rod.saveRod(p, item);
					BukkitLoader.getNmsProvider().postToMainThread(() -> p.getInventory().setItem(ss, new ItemStack(Material.AIR)));
					openEnchantAdd(p);
				}
			});
		}
		if (add)
			a.open(p);
		else
			a.clear();
		return add;
	}

	private static boolean containsAny(ItemStack item) {
		for (Enchant enchant : Enchant.enchants.values())
			if (enchant.containsEnchant(item) && enchant.getMaxLevel() <= getLevel(item, enchant.getName()))
				return true;
		return false;
	}

	private static int getLevel(ItemStack rod, String enchant) {
		NBTEdit edit = new NBTEdit(rod);
		Config data = new Config();
		if (edit.hasKey("af_data"))
			data.reload(edit.getString("af_data"));
		return data.getInt("enchants." + enchant.toLowerCase());
	}

	public static void openEnchantAdd(Player p) {
		GUI a = Create.setup(new GUI(Create.title("enchant.title-add"), 54) {
			@Override
			public void onClose(Player arg0) {
				if (Rod.saved(p))
					Rod.retriveRod(p);
			}
		}, Create.make("enchant.close").build(), EnchantTable::openMain, Settings.CLOSE);
		a.setItem(4, Shop.replace(p, Create.make("enchant.points"), () -> {
		}));
		for (Enchant enchant : Enchant.enchants.values()) {
			ItemStack rod = Rod.getRod(p);
			if (!enchant.containsEnchant(rod)) {
				String name = enchant.getDisplayName();
				List<String> lore = enchant.getDescription();
				double cost = enchant.getCost();
				a.addItem(new ItemGUI(Create.createItem(name, Material.ENCHANTED_BOOK, lore)) {
					@Override
					public void onClick(Player p, HolderGUI arg, ClickType type) {
						if (API.getPoints().has(p.getName(), cost)) {
							API.getPoints().remove(p.getName(), cost);
							ItemStack erod = enchant.enchant(rod, 1);
							BukkitLoader.getNmsProvider().postToMainThread(() -> p.getInventory().addItem(erod));
							Rod.deleteRod(p);
							openMain(p);
						} else
							Loader.msg(Create.text("command.points.lack").replace("%amount%", "" + cost), p);
					}
				});
			}
		}
		a.open(p);
	}
}

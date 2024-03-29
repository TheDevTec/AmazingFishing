package me.devtec.amazingfishingOLD.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.devtec.amazingfishingOLD.API;
import me.devtec.amazingfishingOLD.Loader;
import me.devtec.amazingfishingOLD.construct.Enchant;
import me.devtec.amazingfishingOLD.other.Rod;
import me.devtec.amazingfishingOLD.utils.Create;
import me.devtec.amazingfishingOLD.utils.Create.Settings;
import me.devtec.shared.dataholder.Config;
import me.devtec.theapi.bukkit.BukkitLoader;
import me.devtec.theapi.bukkit.gui.GUI;
import me.devtec.theapi.bukkit.gui.GUI.ClickType;
import me.devtec.theapi.bukkit.gui.HolderGUI;
import me.devtec.theapi.bukkit.gui.ItemGUI;
import me.devtec.theapi.bukkit.nms.NBTEdit;

public class EnchantTable {

	static Settings[] withClose = { Settings.SIDES, Settings.CLOSE }, withoutClose = { Settings.SIDES };

	public static boolean openMain(Player p) {
		if (Enchant.enchants.isEmpty())
			return false;
		boolean withCloseState = Loader.config.getString("Options.Enchants.BackButton-Action").equalsIgnoreCase("CLOSE");
		GUI a = Create.setup(new GUI(Create.title("enchant.title-select"), 54), Create.make("enchant.close").build(), !withCloseState ? Help::open : null, withCloseState ? withClose : withoutClose);
		int slot = -1;
		boolean add = false;
		for (ItemStack item : p.getInventory().getContents()) {
			++slot;
			if (item == null || item.getType() != Material.FISHING_ROD)
				continue;
			List<Enchant> enchs = getApplicableEnchantsOn(item);
			if (enchs.isEmpty())
				continue;
			int finalSlot = slot;
			add = true;
			a.addItem(new ItemGUI(item) {
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType ctype) {
					Rod.saveRod(p, item);
					BukkitLoader.getNmsProvider().postToMainThread(() -> p.getInventory().setItem(finalSlot, new ItemStack(Material.AIR)));
					openEnchantAdd(p, enchs);
				}
			});
		}
		if (add)
			a.open(p);
		return add;
	}

	public static List<Enchant> getApplicableEnchantsOn(ItemStack item) {
		List<Enchant> enchants = new ArrayList<>(Enchant.enchants.size());
		NBTEdit edit = new NBTEdit(item);
		Config data = new Config();
		if (edit.hasKey("af_data"))
			data.reload(edit.getString("af_data"));
		for (Enchant enchant : Enchant.enchants.values())
			if (enchant.getMaxLevel() > data.getInt("enchants." + enchant.getName().toLowerCase()))
				enchants.add(enchant);
		return enchants;
	}

	public static void openEnchantAdd(Player p, List<Enchant> enchants) {
		GUI a = Create.setup(new GUI(Create.title("enchant.title-add"), 54) {
			@Override
			public void onClose(Player arg0) {
				if (Rod.saved(p))
					Rod.retriveRod(p);
			}
		}, Create.make("enchant.close").build(), target -> {
			if (!openMain(target))
				JavaPlugin.getPlugin(BukkitLoader.class).gui.get(target.getUniqueId()).close(target);
		});
		a.setItem(4, Shop.replace(p, Create.make("enchant.points"), () -> {
		}));
		ItemStack rod = Rod.getRod(p);
		for (Enchant enchant : enchants) {
			String name = enchant.getDisplayName();
			List<String> lore = enchant.getDescription();
			double cost = enchant.getCost();
			a.addItem(new ItemGUI(Create.createItem(name, Material.ENCHANTED_BOOK, lore)) {
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType type) {
					if (API.getPoints().has(p.getName(), cost)) {
						API.getPoints().remove(p.getName(), cost);
						Rod.deleteRod(p);
						ItemStack erod = enchant.enchant(rod, 1, true);
						BukkitLoader.getNmsProvider().postToMainThread(() -> {
							p.getInventory().addItem(erod);
							if (!openMain(p))
								arg.close(p);
						});
					} else
						Loader.msg(Create.text("command.points.lack").replace("%amount%", "" + cost), p);
				}
			});
		}
		a.open(p);
	}
}

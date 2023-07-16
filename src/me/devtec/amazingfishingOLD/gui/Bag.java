package me.devtec.amazingfishingOLD.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishingOLD.Loader;
import me.devtec.amazingfishingOLD.utils.Create;
import me.devtec.amazingfishingOLD.utils.Create.Settings;
import me.devtec.theapi.bukkit.gui.EmptyItemGUI;
import me.devtec.theapi.bukkit.gui.GUI;
import me.devtec.theapi.bukkit.gui.GUI.ClickType;
import me.devtec.theapi.bukkit.gui.HolderGUI;
import me.devtec.theapi.bukkit.gui.ItemGUI;

public class Bag {
	public static void openBag(Player p) {
		openBag(p, p);
	}

	static Settings[] withClose = { Settings.WITHOUT_TOP, Settings.CLOSE }, withoutClose = { Settings.WITHOUT_TOP };

	public static void openBag(Player p, Player target) {
		me.devtec.amazingfishingOLD.other.Bag bag = new me.devtec.amazingfishingOLD.other.Bag(p);
		GUI a = Create.setup(new GUI(Create.title("bag.title"), 54) {
			@Override
			public void onClose(Player arg0) {
				try {
					bag.saveBag(this);
				} catch (Exception err) {
					err.printStackTrace();
				}
			}
		}, Create.make("bag.close").build(), Loader.config.getString("Options.Bag.BackButton-Action").equalsIgnoreCase("BACK") ? Help::open : null,
				Loader.config.getString("Options.Bag.BackButton-Action").equalsIgnoreCase("CLOSE") ? withClose : withoutClose);

		if (Loader.config.getBoolean("Options.Shop.SellFish") && Loader.config.getBoolean("Options.Bag.SellFish"))
			a.setItem(49, new ItemGUI(Create.make("bag.sell").build()) {
				@Override
				public void onClick(Player p, HolderGUI gui, ClickType arg2) {
					Shop.sellAll(p, gui, true);
					bag.saveBag(a);
				}
			});

		for (ItemStack as : bag.getBag()) {
			if (as == null || as.getType() == Material.AIR)
				continue;
			a.addItem(new EmptyItemGUI(as).setUnstealable(false));
		}
		a.setInsertable(true);
		a.open(target);
	}
}

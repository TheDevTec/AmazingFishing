package me.devtec.amazingfishing.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.utils.Create;
import me.devtec.amazingfishing.utils.Create.Settings;
import me.devtec.theapi.guiapi.EmptyItemGUI;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.GUI.ClickType;
import me.devtec.theapi.guiapi.HolderGUI;
import me.devtec.theapi.guiapi.ItemGUI;

public class Bag {
	public static void openBag(Player p) {
		openBag(p,p);
	}
	public static void openBag(Player p, Player target) {
		me.devtec.amazingfishing.other.Bag bag = new me.devtec.amazingfishing.other.Bag(p);
		GUI a = Create.setup(new GUI(Create.text("bag.title"),54) {
			public void onClose(Player arg0) {
				try {
					bag.saveBag(this);
				}catch(Exception err) {
					err.printStackTrace();
				}
			}
		}, Create.make("bag.close").create(), f -> Help.open(f), Settings.WITHOUT_TOP);
		if(Loader.config.getBoolean("Options.Shop.SellFish") && Loader.config.getBoolean("Options.Bag.SellFish")) {
			a.setItem(49,new ItemGUI(Create.make("bag.sell").create()) {
				public void onClick(Player p, HolderGUI gui, ClickType arg2) {
					Shop.sellAll(p, gui, true);
					bag.saveBag(a);
				}
			});
		}
		for(ItemStack as : bag.getBag()) {
			if(as==null||as.getType()==Material.AIR)continue;
			a.addItem(new EmptyItemGUI(as).setUnstealable(false));
		}
		a.setInsertable(true);
		a.open(target);
	}
}

package me.devtec.amazingfishing.gui;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.gui.Shop.ShopType;
import me.devtec.amazingfishing.utils.Create;
import me.devtec.theapi.bukkit.gui.GUI;
import me.devtec.theapi.bukkit.gui.GUI.ClickType;
import me.devtec.theapi.bukkit.gui.HolderGUI;
import me.devtec.theapi.bukkit.gui.ItemGUI;

public class Help {
	public static void open(Player p) {
		GUI a = Create.setup(new GUI(Create.title("help.title"),54), Create.make("index.close").create(), null, me.devtec.amazingfishing.utils.Create.Settings.SIDES, me.devtec.amazingfishing.utils.Create.Settings.CLOSE);
		if(p.hasPermission("amazingfishing.command."+(Loader.gui.getString("help.shop.type").toUpperCase().equals("SELL")?"sellshop":"shop")) && Loader.config.getBoolean("Options.Shop.Enabled"))
			a.setItem(22,new ItemGUI(Create.make("help.shop").create()){
				public void onClick(Player p, HolderGUI arg, ClickType ctype) {
					try {
						Shop.openShop(p, ShopType.valueOf(Loader.gui.getString("help.shop.type").toUpperCase()));
					}catch(NoSuchFieldError e) {
						Shop.openShop(p, ShopType.BUY);
					}
				}});
		
		if(p.hasPermission("amazingfishing.command.index"))
			a.setItem(33,new ItemGUI(Create.make("help.index").create()){
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType ctype) {
					Index.open(p);
				}
			});
		
		if(p.hasPermission("amazingfishing.command.enchanter") && Loader.config.getBoolean("Options.Enchants.Enabled"))
			a.setItem(31,new ItemGUI(Create.make("help.enchant").create()){
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType ctype) {
					EnchantTable.openMain(p);
				}
			});
		
		if(p.hasPermission("amazingfishing.command.bag"))
			a.setItem(27,new ItemGUI(Create.make("help.bag").create()){
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType ctype) {
					Bag.openBag(p);
				}
			});
	
		if(p.hasPermission("amazingfishing.command.settings"))
			a.setItem(24,new ItemGUI(Create.make("help.settings").create()){
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType ctype) {
					Settings.open(p);
				}
			});

		if(p.hasPermission("amazingfishing.command.quests"))
			a.setItem(20,new ItemGUI(Create.make("help.quests").create()){
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType ctype) {
					if(p.hasPermission("amazingfishing.quests")) {
						QuestGUI.open(p);
					}
				}
			});

		if(p.hasPermission("amazingfishing.command.achievements"))
			a.setItem(29,new ItemGUI(Create.make("help.achievements").create()){
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType ctype) {
					AchievementGUI.open(p);
				}
			});
		a.open(p);
	
	}
}

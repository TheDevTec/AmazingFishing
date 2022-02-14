package me.devtec.amazingfishing.gui;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.gui.Shop.ShopType;
import me.devtec.amazingfishing.utils.Create;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.GUI.ClickType;
import me.devtec.theapi.guiapi.HolderGUI;
import me.devtec.theapi.guiapi.ItemGUI;

public class Help {
	public static void open(Player p) {
		GUI a = Create.setup(new GUI(Create.text("help.title"),54), Create.make("index.close").create(), null
				, me.devtec.amazingfishing.utils.Create.Settings.SIDES, me.devtec.amazingfishing.utils.Create.Settings.CLOSE);
		if(p.hasPermission("amazingfishing.shop") && Loader.config.getBoolean("Options.Shop.Enabled"))
			a.setItem(22,new ItemGUI(Create.make("help.shop").create()){
				public void onClick(Player p, HolderGUI arg, ClickType ctype) {
					if(p.hasPermission("amazingfishing.shop") && Loader.config.getBoolean("Options.Shop.Enabled"))
						try {
							Shop.openShop(p, ShopType.valueOf(Loader.gui.getString("help.shop.type").toUpperCase()));
						}catch(NoSuchFieldError e) {
							Shop.openShop(p, ShopType.BUY);
						}
				}});
		if(p.hasPermission("amazingfishing.index"))
		a.setItem(33,new ItemGUI(Create.make("help.index").create()){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.index"))
				Index.open(p);
			}
		});
		if(p.hasPermission("amazingfishing.enchant") && Loader.config.getBoolean("Options.Enchants.Enabled")) {
			a.setItem(31,new ItemGUI(Create.make("help.enchant").create()){
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType ctype) {
					if(p.hasPermission("amazingfishing.enchant") && Loader.config.getBoolean("Options.Enchants.Enabled"))
						EnchantTable.openMain(p);
				}}
			);
		}
		if(p.hasPermission("amazingfishing.bag"))
		a.setItem(27,new ItemGUI(Create.make("help.enchant").create()){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.bag"))
					Bag.openBag(p);
			}
		}
		);
	
		if(p.hasPermission("amazingfishing.settings"))
			a.setItem(24,new ItemGUI(Create.make("help.settings").create()){
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType ctype) {
					if(p.hasPermission("amazingfishing.settings")) {
						Settings.open(p);
					}
				}
			});

		if(p.hasPermission("amazingfishing.quests"))
		a.setItem(20,new ItemGUI(Create.make("help.quests").create()){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.quests")) {
					QuestGUI.open(p);
				}
			}
		});

		if(p.hasPermission("amazingfishing.achievements"))
		a.setItem(29,new ItemGUI(Create.make("help.achievements").create()){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.achievements")) {
					AchievementGUI.open(p);
				}
			}
		});
		a.open(p);
	
	}
}

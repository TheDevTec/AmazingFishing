package me.devtec.amazingfishing.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.gui.Help.BackButton;
import me.devtec.amazingfishing.gui.Shop.ShopType;
import me.devtec.amazingfishing.utils.Create;
import me.devtec.amazingfishing.utils.Trans;
import me.devtec.amazingfishing.utils.Utils;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.GUI.ClickType;
import me.devtec.theapi.guiapi.HolderGUI;
import me.devtec.theapi.guiapi.ItemGUI;

public class Bag {
	public static void openBag(Player p, BackButton BackButton) {
		me.devtec.amazingfishing.other.Bag bag = new me.devtec.amazingfishing.other.Bag(p);
		GUI a = new GUI(Trans.bag_title(),54) {
			public void onClose(Player arg0) {
				bag.saveBag(this);
			}
		};
		Create.prepareInvBig(a);
		a.setItem(49,new ItemGUI( Create.createItem(Trans.words_back(), Material.BARRIER)) {
			@Override
			public void onClick(Player p, HolderGUI menu, ClickType arg2) {
				switch(BackButton) {
					case Help:
						Help.open(p);
						break;
					case Shop:
						Shop.openShop(p, ShopType.Sell);
						break;
					default:
						menu.close(p);
						break;
				}
			}
		});
		if(Loader.config.getBoolean("Options.Shop.SellFish")) {
			if(Loader.config.getBoolean("Options.Bag.SellFish")) {
			a.setItem(51,new ItemGUI(Create.createItem(Trans.words_sell(), Utils.getCachedMaterial("COD_BUCKET"))) {
				@Override
				public void onClick(Player p, HolderGUI arg1, ClickType arg2) {
					Shop.sellAll(p, p.getOpenInventory().getTopInventory(), true);
					bag.saveBag(a);
				}
			});
			a.setItem(47,new ItemGUI(Create.createItem(Trans.words_sell(), Utils.getCachedMaterial("COD_BUCKET"))) {
				@Override
				public void onClick(Player p, HolderGUI arg1, ClickType arg2) {
					Shop.sellAll(p, p.getOpenInventory().getTopInventory(), true);
					bag.saveBag(a);
				}
			});
		}}
		if(Loader.config.getBoolean("Options.Shop.Enabled")) {
			if(Loader.config.getBoolean("Options.Bag.OpenShop")) {
				a.setItem(45, new ItemGUI(Create.createItem(Trans.bag_toShop(), Material.EMERALD)){
					@Override
					public void onClick(Player p, HolderGUI arg1, ClickType arg2) {
						Shop.openShop(p, ShopType.Buy);
					}
				});
				a.setItem(53, new ItemGUI(Create.createItem(Trans.bag_toShop(), Material.EMERALD)){
					@Override
					public void onClick(Player p, HolderGUI arg1, ClickType arg2) {
						Shop.openShop(p, ShopType.Buy);
					}
				});
				
		}}
		for(ItemStack as : new me.devtec.amazingfishing.other.Bag(p).getBag()) {
			if(as==null||as.getType()==Material.AIR)continue;
			ItemGUI item = new ItemGUI(as){
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType type) {
				}
			};
			item.setUnstealable(false);
			a.addItem(item);
		}
		a.setInsertable(true);
		a.open(p);
	}
}

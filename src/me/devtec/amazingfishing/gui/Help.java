package me.devtec.amazingfishing.gui;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.construct.FishType;
import me.devtec.amazingfishing.gui.EnchantTable.EnchantGUI;
import me.devtec.amazingfishing.gui.Shop.ShopType;
import me.devtec.amazingfishing.utils.Create;
import me.devtec.amazingfishing.utils.Manager;
import me.devtec.amazingfishing.utils.Trans;
import me.devtec.theapi.guiapi.EmptyItemGUI;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.GUI.ClickType;
import me.devtec.theapi.guiapi.HolderGUI;
import me.devtec.theapi.guiapi.ItemGUI;
import me.devtec.theapi.scheduler.Tasker;

public class Help {
	
	public static enum BackButton{
		Close,
		Help,
		Shop;
	}
	
	public static void open(Player p) {
		GUI a = Create.prepareNewBig(new GUI(Loader.gui.getString("GUI.Help.Player.Title"),54), Material.BLUE_STAINED_GLASS_PANE);
		a.setItem(4, new EmptyItemGUI(Create.createItem(Manager.getPluginName(), Material.KNOWLEDGE_BOOK, Arrays.asList(
				"&9Version &bV"+Loader.plugin.getDescription().getVersion(),
				"&9Created by &bStraikerinaCZ",
				"&9Developed by &bHouska02"))));
		if(p.hasPermission("amazingfishing.shop") && Loader.config.getBoolean("Options.Shop.Enabled"))
			a.setItem(22,new ItemGUI(Create.createItem(Trans.help_player_shop(), Material.EMERALD)){
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType ctype) {
					if(p.hasPermission("amazingfishing.shop") && Loader.config.getBoolean("Options.Shop.Enabled"))
					Shop.openShop(p, ShopType.Buy);
				}});
		if(p.hasPermission("amazingfishing.list"))
		a.setItem(33,new ItemGUI(Create.createItem(Trans.help_player_list(), Material.PAPER)){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				new Tasker() {
					
					@Override
					public void run() {
						Loader.msg(Trans.s("FishList.Topic"), p);
						
						Loader.msg(Trans.words_cod(), p);
						for(Fish fish: API.getRegisteredFish().values() ) {
							if(fish.getType()==FishType.COD)
							Loader.msg(Trans.s("FishList.Format")
									.replace("%fish%", fish.getDisplayName()), p);
						}
						Loader.msg(Trans.words_salmon(), p);
						for(Fish fish: API.getRegisteredFish().values() ) {
							if(fish.getType()==FishType.SALMON)
							Loader.msg(Trans.s("FishList.Format")
									.replace("%fish%", fish.getDisplayName()), p);
						}
						Loader.msg(Trans.words_pufferfish(), p);
						for(Fish fish: API.getRegisteredFish().values() ) {
							if(fish.getType()==FishType.PUFFERFISH)
							Loader.msg(Trans.s("FishList.Format")
									.replace("%fish%", fish.getDisplayName()), p);
						}
						Loader.msg(Trans.words_tropicalfish(), p);
						for(Fish fish: API.getRegisteredFish().values() ) {
							if(fish.getType()==FishType.TROPICAL)
							Loader.msg(Trans.s("FishList.Format")
									.replace("%fish%", fish.getDisplayName()), p);
						}
						
					}
				}.runTaskSync();
			}
		});
		if(p.hasPermission("amazingfishing.enchant") && Loader.config.getBoolean("Options.Enchants"))
		a.setItem(31,new ItemGUI(Create.createItem(Trans.help_player_enchants(), Material.matchMaterial("ENCHANTING_TABLE"))){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.enchant") && Loader.config.getBoolean("Options.Enchants"))
					EnchantTable.open(p, EnchantGUI.Main);
			}}
		);
		if(p.hasPermission("amazingfishing.bag"))
		a.setItem(27,new ItemGUI(Create.createItem(Trans.help_player_bag(), Material.CHEST)){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.bag"))
					Bag.openBag(p, BackButton.Help);
			}
		}
		);
	
		if(p.hasPermission("amazingfishing.settings"))
			a.setItem(24,new ItemGUI(Create.createItem(Trans.help_player_settings(), Material.LECTERN)){
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType ctype) {
					if(p.hasPermission("amazingfishing.settings")) {
						Settings.open(p);
					}
				}
			});
		/*a.setItem(--,new ItemGUI(Create.createItem(Trans.help_player_quests(), Material.BOOK)){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.quests")) {
					if(API.getQuest(p)==null) {
						Quests.selectQuest(p);
					}
					}else {
						Quests.openQuestMenu(p);
					}
				}
			}
		);*/ //TODO - později dodělat questy a udělat achievmenty!
		a.open(p);
	
	}
}

package me.devtec.amazingfishing.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.construct.FishType;
import me.devtec.amazingfishing.gui.EnchantTable.EnchantGUI;
import me.devtec.amazingfishing.gui.Shop.ShopType;
import me.devtec.amazingfishing.utils.Create;
import me.devtec.amazingfishing.utils.Manager;
import me.devtec.amazingfishing.utils.Trans;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.ItemGUI;

public class Help {
	
	public static enum PlayerType{
		Player,
		Admin
	}
	public static enum BackButton{
		Close,
		FishPlayer,
		FishAdmin,
		Shop;
	}
	public static void open(Player p, PlayerType type) {
		if(type==PlayerType.Admin) openAdmin(p);
		else openPlayer(p);
	}
	private static void openPlayer(Player p) {
		GUI a = new GUI(Loader.gui.getString("GUI.Help.Player.Title"),54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareNewBig(a, Material.BLUE_STAINED_GLASS_PANE);
	

		
	a.setItem(4, new ItemGUI(Create.createItem(Manager.getPluginName(), Material.KNOWLEDGE_BOOK, Manager.getPluginInfoIntoLore(PlayerType.Player))) {
		@Override
		public void onClick(Player p, GUI arg, ClickType ctype) {
		}
	});
		
	/*if(p.hasPermission("amazingfishing.top"))
		a.setItem(20, new ItemGUI(Create.createItem(Trans.help_top(), Material.DIAMOND)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.top"))
				gui.openGlobal(p);
			}
		});*/
	/*if(p.hasPermission("amazingfishing.record"))
		a.setItem(29,new ItemGUI(Create.createItem(Trans.help_rec(), Material.GOLD_INGOT)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.record"))
				gui.openMy(p);
			}}
		);*/

	if(p.hasPermission("amazingfishing.shop") && Loader.config.getBoolean("Options.Shop.Enabled"))
		a.setItem(22,new ItemGUI(Create.createItem(Trans.help_player_shop(), Material.EMERALD)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.shop") && Loader.config.getBoolean("Options.Shop.Enabled"))
				Shop.openShop(p, ShopType.Buy);
			}}
		);
	
	/*if(p.hasPermission("amazingfishing.stats")) { TODO
		ArrayList<String> l = new ArrayList<String>();
		for(String d:Loader.c.getStringList("GUI.Stats.1.Lore")) {
			l.add( TheAPI.colorize(rep(d,p.getDisplayName())) );
		}
		ItemStack head = ItemCreatorAPI.createHead(1, Trans.help_player_stats(), p.getName(), 
				l);		
		a.setItem(18,new ItemGUI(head){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.stats"))
					stats(p,p.getName(),false);
			}}
		);
		/*
		 a.setItem(18,new ItemGUI(Create.createItem(Trans.help_stats_my(), Material.WRITTEN_BOOK)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.stats"))
					stats(p,p.getName(),false);
			}}
		);*/
		 
	/*}*/
	
	/*if(p.hasPermission("amazingfishing.toggle"))
	a.setItem(26,new ItemGUI(Create.createItem(Trans.help_tog(), Material.REDSTONE_TORCH)){
		@Override
		public void onClick(Player p, GUI arg, ClickType ctype) {
			if(p.hasPermission("amazingfishing.toggle"))
				toggle(p);
		}}
	);*/
	
	if(p.hasPermission("amazingfishing.list"))
	a.setItem(33,new ItemGUI(Create.createItem(Trans.help_player_list(), Material.PAPER)){
		@Override
		public void onClick(Player p, GUI arg, ClickType ctype) {
			if(p.hasPermission("amazingfishing.list")) {
				Loader.msg(Trans.s("List.FishList"), p);

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
		}
	}
	);

	if(p.hasPermission("amazingfishing.enchant") && Loader.config.getBoolean("Options.Enchants"))
	a.setItem(31,new ItemGUI(Create.createItem(Trans.help_player_enchants(), Material.matchMaterial("ENCHANTING_TABLE"))){
		@Override
		public void onClick(Player p, GUI arg, ClickType ctype) {
			if(p.hasPermission("amazingfishing.enchant") && Loader.config.getBoolean("Options.Enchants"))
				EnchantTable.open(p, EnchantGUI.Main);
		}}
	);
	
	if(p.hasPermission("amazingfishing.bag"))
	a.setItem(27,new ItemGUI(Create.createItem(Trans.help_player_bag(), Material.CHEST)){
		@Override
		public void onClick(Player p, GUI arg, ClickType ctype) {
			if(p.hasPermission("amazingfishing.bag"))
				Bag.openBag(p, BackButton.FishPlayer);
		}
	}
	);

	if(p.hasPermission("amazingfishing.quests"))
	/*a.setItem(24,new ItemGUI(Create.createItem(Trans.help_player_quests(), Material.BOOK)){
		@Override
		public void onClick(Player p, GUI arg, ClickType ctype) {
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
	if(p.hasPermission("amazingfishing.reload")||
			p.hasPermission("amazingfishing.points.give")||
			p.hasPermission("amazingfishing.points.take")||
			p.hasPermission("amazingfishing.points.set")||
			p.hasPermission("amazingfishing.editor")||
		p.hasPermission("amazingfishing.stats.other")||
		p.hasPermission("amazingfishing.tournament")) {

	a.setItem(35, new ItemGUI(Create.createItem(Trans.help_admin_info(), Material.COMPASS)){
		@Override
		public void onClick(Player p, GUI arg, ClickType ctype) {
			open(p, PlayerType.Admin);
		}}
	);
	}
	
	}
	
	private static void openAdmin(Player p) {
		GUI a = new GUI(Trans.help_admin_title(),54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareNewBig(a, Material.ORANGE_STAINED_GLASS_PANE);
		/*
		 * Admin Section
		 *   20,29 | 22, 31 | 24, 33
		 */
		a.setItem(4, new ItemGUI(Create.createItem(Manager.getPluginName(), Material.KNOWLEDGE_BOOK, Manager.getPluginInfoIntoLore(PlayerType.Admin))) {
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				TheAPI.msg(Loader.trans.getString("Prefi")+" &fJoin our Discord: &b"+Manager.getDiscordLink(), p);
			}
		});
		
		if(p.hasPermission("amazingfishing.tournament"))
		a.setItem(20, new ItemGUI(Create.createItem(Trans.help_admin_tournament(), Material.CLOCK)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.tournament"))
				Tournament.open(p);
			}}
		);

		/*if(p.hasPermission("amazingfishing.editor"))
		a.setItem(22,new ItemGUI(Create.createItem(Trans.help_edit(), Material.WRITABLE_BOOK)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				TheAPI_GUIs g = new TheAPI_GUIs();
				if(p.hasPermission("amazingfishing.editor"))
				g.open(p);
			}}
		);*/
		
		/*if(p.hasPermission("amazingfishing.stats.other"))
		a.setItem(29,new ItemGUI(Create.createItem(Trans.help_stats_other(), Material.WRITTEN_BOOK)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.stats.other"))
				selectPlayer(p);
			}}
		);*/
		if(p.hasPermission("amazingfishing.reload"))
		a.setItem(31,new ItemGUI(Create.createItem(Trans.help_admin_reload(), Material.FIREWORK_STAR)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.reload")) {
					Loader.reload(p, true);
					TheAPI.msg(Trans.s("Prefix")+Trans.s("Admin.ConfigReloaded"), p);
					}
			}}
		);
		/*if(p.hasPermission("amazingfishing.points"))
		a.setItem(24,new ItemGUI(Create.createItem(Trans.help_points(), Material.LAPIS_LAZULI)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.points"))
				selectPlayerPointsManager(p);
			}}
		);*/
		a.setItem(35, new ItemGUI(Create.createItem(Trans.help_player_info(), Material.COMPASS)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				open(p,PlayerType.Player);
			}}
		);
	}
}

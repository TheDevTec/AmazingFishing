package me.devtec.amazingfishing.utils;

import java.util.Arrays;

import me.devtec.amazingfishing.Loader;
import me.devtec.theapi.configapi.Config;

public class Configs {

	public static void loadConfig(){
		Loader.config=new Config("AmazingFishing/Config.yml");

		Loader.config.addDefault("Options.PointsManager", "USER");
		Loader.config.setComments("Options.PointsManager", Arrays.asList("# PointsManager types: VAULT, USER"));

		Loader.config.addDefault("Options.Enchants", true);
		
		Loader.config.addDefault("Options.Shop.SellFish", true);
		Loader.config.addDefault("Options.Shop.Sounds.Shop-BuyItem", true);
		Loader.config.addDefault("Options.Shop.Enabled", true);
		Loader.config.addDefault("Options.Shop.Calculator.Money", "(%length% / 4 +%weight% / 4 + %money%) * %bonus%");
		Loader.config.addDefault("Options.Shop.Calculator.Xp", "(%length% / 4 +%weight% / 4 + %xp%) * %bonus%");
		Loader.config.addDefault("Options.Shop.Calculator.Points", "(%length% / 5 +%weight% / 5 + %points%) * %bonus%");

		Loader.config.addDefault("Options.Sell.EarnFromLength", true);
		Loader.config.addDefault("Options.Sell.EarnFromWeight", true);
		Loader.config.addDefault("Options.Sell.GiveFullPriceFish", true);
		Loader.config.addDefault("Options.Sell.DisableMoney", false);
		Loader.config.addDefault("Options.Sell.DisableXP", false);
		Loader.config.addDefault("Options.Sell.DisablePoints", false);

		Loader.config.addDefault("Options.Bag.Button.SellFish", true);
		Loader.config.addDefault("Options.Bag.Button.OpenShop", true);
		Loader.config.addDefault("Options.Bag.Enabled", true);
		
		Loader.config.addDefault("Options.Enchant.Enabled", true); //todo - uplatnit
		
		

		Loader.config.addDefault("Tournament.Type.Amount.Name", "&bAmount");
		Loader.config.addDefault("Tournament.Type.Length.Name", "&bLength");
		Loader.config.addDefault("Tournament.Type.Weight.Name", "&9Weight");
		Loader.config.addDefault("Tournament.Type.TotalLength.Name", "&3TotalLength");
		Loader.config.addDefault("Tournament.Type.TotalWeight.Name", "&3TotalWeight");
		Loader.config.addDefault("Tournament.Type.Random.Name", "&6Random");
		
		
		Loader.config.save();
	}
	public static void loadTranslation(){
		Loader.trans=new Config("AmazingFishing/Translations.yml");
		
		Loader.trans.addDefault("Prefix", "&bAmazingFishing &8&l» &7");
		Loader.trans.addDefault("Admin.ConfigReloaded", "&aReloaded configs");
		

		Loader.trans.addDefault("FishList.Topic", "&eList of fishes:");
		Loader.trans.addDefault("FishList.Format", "&7&l- %fish%");
		
		Loader.trans.addDefault("Points.Lack", "%prefix% &cYou have lack of points!");
		
		Loader.trans.addDefault("Tournaments.Stop.NoRunning", "%prefix% &cThere is no running tournament!");
		Loader.trans.addDefault("Tournaments.Stop.One", "%prefix% &aYou have stopped current tournament!");
		Loader.trans.addDefault("Tournaments.Stop.All", "%prefix% &aYou have stopped all current tournaments!");
		
		Loader.trans.addDefault("Words.Cod", "&7Cod");
		Loader.trans.addDefault("Words.Salmon", "&7Salmon");
		Loader.trans.addDefault("Words.PufferFish", "&7PufferFish");
		Loader.trans.addDefault("Words.TropicalFish", "&7TropicalFish");
		Loader.trans.addDefault("Words.Back", "&4Back");
		Loader.trans.addDefault("Words.Close", "&cClose");
		Loader.trans.addDefault("Words.Cancel", "&cCancel");
		Loader.trans.addDefault("Words.Sell", "&eSell fish");
		Loader.trans.addDefault("Words.Points", "&9%value% Points");
		Loader.trans.addDefault("Words.Bag", "&9%value% Points");
		
		Loader.trans.save();
	}
	public static void loadGuis(){
		Loader.gui=new Config("AmazingFishing/GUI.yml");
		
		Loader.gui.addDefault("GUI.Help.Player.Title", "&5Help &7- &4 Player");
		Loader.gui.addDefault("GUI.Help.Player.Shop", "&bShop");
		Loader.gui.addDefault("GUI.Help.Player.Stats", "&bStats");
		Loader.gui.addDefault("GUI.Help.Player.List", "&bFish list");
		Loader.gui.addDefault("GUI.Help.Player.Enchant", "&bEnchant table");
		Loader.gui.addDefault("GUI.Help.Player.Bag", "&bBackPack");
		Loader.gui.addDefault("GUI.Help.Player.Quests", "&bQuests");
		Loader.gui.addDefault("GUI.Help.Player.Info", "&ePlayer section");
		

		Loader.gui.addDefault("GUI.Help.Admin.Title", "&5Help &7- &4 Admin");
		Loader.gui.addDefault("GUI.Help.Admin.Info", "&cAdmin section");
		Loader.gui.addDefault("GUI.Help.Admin.Reload", "&6Reload");
		Loader.gui.addDefault("GUI.Help.Admin.Tournament", "&6Tournament");
		

		Loader.gui.addDefault("GUI.SellShop.Title", "&eSell Shop");
		Loader.gui.addDefault("GUI.BuyShop.Title", "&aBuy Shop");
		
		Loader.gui.addDefault("GUI.Bag.Title", "&eBag");
		Loader.gui.addDefault("GUI.Bag.Shop", "&aGo to shop");
		
		Loader.gui.addDefault("GUI.Enchant.Title", "&bEnchant Table");
		Loader.gui.addDefault("GUI.Enchant.Add", "&9Add Enchant");
		Loader.gui.addDefault("GUI.Enchant.Upgrade", "&9Retrive Rod");
		Loader.gui.addDefault("GUI.Enchant.Retriver_rod", "&2Retrive Rod");
		Loader.gui.addDefault("GUI.Enchant.SelectRod_Title", "&bEnchant Table &7&l- &9Select fishing rod");
		Loader.gui.addDefault("GUI.Enchant.Add_Enchant_Title", "&bEnchant Table &7&l- &9Add Enchant");
		Loader.gui.addDefault("GUI.Enchant.Upgrade_Enchant_Title", "&bEnchant Table &7&l- &9Add Enchant");
		
		Loader.gui.addDefault("GUI.Tournaments.Title", "&2Tournaments");
		Loader.gui.addDefault("GUI.Tournaments.Stop.Title", "&4Stop &2Tournaments");
		Loader.gui.addDefault("GUI.Tournaments.Stop.Item", "&4Stop tournaments");
		Loader.gui.addDefault("GUI.Tournaments.Stop.StopOne.Name", "&4Stop one tournament");
		Loader.gui.addDefault("GUI.Tournaments.Stop.StopOne.Description", Arrays.asList(
				"&7 - Left click to stop tournament with prizes (in your world)",
				"&7 - Right click to stop tournament without prizes (in your world)"));
		Loader.gui.addDefault("GUI.Tournaments.Stop.StopAll.Name", "&4Stop one tournament");
		Loader.gui.addDefault("GUI.Tournaments.Stop.StopAll.Description", Arrays.asList(
				"&7 - Left click to stop all tournaments with prizes",
				"&7 - Right click to stop all tournaments without prizes"));
		Loader.gui.addDefault("GUI.Tournaments.Start.Title", "&2Tournaments &7- &a %type%");
		Loader.gui.addDefault("GUI.Tournaments.Start.Start", "&6Click to start new tournament");
		Loader.gui.addDefault("GUI.Tournaments.Start.Description", Arrays.asList(
				"&3> &5Type: %type%","&3> &5Time: %time%"));
		
		
		Loader.gui.save();
	}
	public static void loadShop(){
		Loader.shop=new Config("AmazingFishing/Config.yml");
		
		Loader.shop.addDefault("GUI.Bag.Icon", "CHEST");
		Loader.shop.addDefault("GUI.Bag.ModelData", "1");
		Loader.shop.addDefault("GUI.Bag.Name", "&6Bag");
		Loader.shop.addDefault("GUI.Bag.Lore", Arrays.asList("&7Click to open bag"));

		Loader.shop.addDefault("GUI.Points.Icon", "LAPIS_LAZULI");
		Loader.shop.addDefault("GUI.Points.ModelData", "1");
		Loader.shop.addDefault("GUI.Points.Name", "&9Points");
		Loader.shop.addDefault("GUI.Points.Lore", Arrays.asList("&7Currently you have %points% points"));
		
		Loader.shop.addDefault("GUI.BuyShop.Icon", "EMERALD");
		Loader.shop.addDefault("GUI.BuyShop.ModelData", "1");
		Loader.shop.addDefault("GUI.BuyShop.Name", "&eBuy shop");
		Loader.shop.addDefault("GUI.BuyShop.Lore", Arrays.asList("&7Open shop in which you can buy items"));

		Loader.shop.addDefault("GUI.SellShop.Icon", "COD_BUCKET");
		Loader.shop.addDefault("GUI.SellShop.ModelData", "1");
		Loader.shop.addDefault("GUI.SellShop.Name", "&eSell Shop");
		Loader.shop.addDefault("GUI.SellShop.Lore", Arrays.asList("&7Open shop in which you can sell fish"));
		
		Loader.shop.addDefault("GUI.Sell.Icon", "GOLD_INGOT");
		Loader.shop.addDefault("GUI.Sell.ModelData", "1");
		Loader.shop.addDefault("GUI.Sell.Name", "&eSell fish");
		Loader.shop.addDefault("GUI.Sell.Lore", Arrays.asList("&7Sell fish in shop"));
	
		
		Loader.shop.addDefault("Items.1.Name", "&7TestingItem &e| %cost%");
		Loader.shop.addDefault("Items.1.Icon", "STONE");
		Loader.shop.addDefault("Items.1.Cost", 1);
		Loader.shop.addDefault("Items.1.Commands", Arrays.asList("say Super command"));
		Loader.shop.addDefault("Items.1.Messages", Arrays.asList("&6Some messages"));
		Loader.shop.addDefault("Items.1.Description", Arrays.asList("Fantastic lore!"));
		Loader.shop.addDefault("Items.1.Item.block.Material", "STONE");
		Loader.shop.addDefault("Items.1.Item.block.Amount", 5);
		Loader.shop.addDefault("Items.1.Item.block.Name", 5);
		Loader.shop.addDefault("Items.1.Item.block.Lore", 5);
		Loader.shop.addDefault("Items.1.Item.block.Unbreakable", true);
		Loader.shop.addDefault("Items.1.Item.block.HideEnchants", true);
		Loader.shop.addDefault("Items.1.Item.block.HideAttributes", true);
		Loader.shop.addDefault("Items.1.Item.block.Enchants", Arrays.asList("LURE:1"));
		
		Loader.shop.save();
	}
	public static void load(){
		loadConfig();
		loadTranslation();
		loadGuis();
		loadShop();
	}
}

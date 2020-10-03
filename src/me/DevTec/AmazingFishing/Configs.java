package me.DevTec.AmazingFishing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import me.DevTec.TheAPI.ConfigAPI.ConfigAPI;

public class Configs {
	
	public static ConfigAPI c;
	public static ConfigAPI TranslationsFile;
	public static ConfigAPI shop;
	public static ConfigAPI me;
	public static ConfigAPI ccc = Loader.getConfig("AmazingFishing", "AmazingFishing");
	
	static void setupConfig() {
		c = Loader.getConfig("AmazingFishing", "AmazingFishing");
		Map<String, Object> cc = new HashMap<String, Object>();
		cc.put("Options.BossBar.Use", true);
		cc.put("Options.BossBar.OnlyIfCatchFish", true);
		cc.put("Options.BossBar.Running", "&cRunning tournament type &e%type% &con &e%time_formated%");
		cc.put("Options.BossBar.Win", "&2Tournament was end. You are on %position%. position");
		cc.put("Options.Treasures", true);
		cc.put("Options.Shop", true);
		
		cc.put("Options.UseDoubles.Weight", true);
		cc.put("Options.UseDoubles.Weight", true);
		cc.put("Options.UseCustomFishDescription-ifItIsPossible", false);
		cc.put("Options.Fish.EnchantsOnCustomFish", false);
		cc.put("Options.Fish.HideEnchants", false);
		
		cc.put("Options.CustomFishOnlyWhileTournament", false);
		cc.put("Options.TreasuresOnlyWhileTournament", false);
		cc.put("Options.Enchants", true);
		cc.put("Options.DisableMoneyFromCaught", false);

		cc.put("Options.SellFish.DisableMoney", false);
		cc.put("Options.SellFish.DisableXP", false);
		cc.put("Options.SellFish.DisablePoints", false);
		cc.put("Options.SellFish.ShopGiveFullPriceFish", false);
		cc.put("Options.SellFish.EarnFromLength", false);
		
		cc.put("Options.ShopGiveFullPriceFish", false);
		cc.put("Options.EarnFromLength", true);
		cc.put("Options.UseGUI", true);
		cc.put("Options.ShopSellFish", true);
		cc.put("Options.LogCaughtFishToConsole", false);
		cc.put("Options.Tournament.AutoStart", true);
		cc.put("Options.Tournament.RequiredPlayers", 10);
		cc.put("Options.Tournament.Delay", "2h");
		cc.put("Options.Tournament.Time", "5min");
		cc.put("Options.Tournament.DeletePlayersOnLeave", true);
		cc.put("Options.FishRemove", false);
		cc.put("Options.Particles", true);
		cc.put("Options.Sounds.Shop-BuyItem", true);
		cc.put("Options.Sounds.Shop-SellFish", true);
		cc.put("Options.Sounds.Bag-SellFish", true);
		cc.put("Options.Sounds.CatchFish", false);
		cc.put("Options.Disabled-Worlds", Arrays.asList("creative_world","disabled_world"));
		cc.put("Options.AFK.Enabled", true);
		cc.put("Options.AFK.TimeToAFK", 300);
		cc.put("Options.Bag.StorageItems", Arrays.asList("Fish","Fishing_rod"));
		cc.put("Options.Bag.ButtonsToSellFish", true);
		cc.put("Options.Bag.StoreCaughtFish", true);
		
		cc.put("Options.Bag.ButtonsToOpenShop", true);
		cc.put("Options.Manual.MinimalFishLength", 0.15);
		cc.put("Options.Manual.ChanceForTreasure", 5);
		cc.put("Options.Perms-Treasures.Legendary.UseWorldGuard-Regions", false);
		cc.put("Options.Perms-Treasures.Legendary.WorldGuard-Regions",  Arrays.asList("Legendary_Treasures_Region"));
		cc.put("Options.Perms-Treasures.Epic.UseWorldGuard-Regions", false);
		cc.put("Options.Perms-Treasures.Epic.WorldGuard-Regions",  Arrays.asList("Epic_Treasures_Region"));
		cc.put("Options.Perms-Treasures.Rare.UseWorldGuard-Regions", false);
		cc.put("Options.Perms-Treasures.Rare.WorldGuard-Regions",  Arrays.asList("Rare_Treasures_Region"));
		cc.put("Options.Perms-Treasures.Common.UseWorldGuard-Regions", false);
		cc.put("Options.Perms-Treasures.Common.WorldGuard-Regions",  Arrays.asList("Common_Treasures_Region"));

		cc.put("Options.Perms-Treasures.Legendary.UseResidence", false);
		cc.put("Options.Perms-Treasures.Legendary.Residence", Arrays.asList("Legendary_Treasures_Residence"));
		cc.put("Options.Perms-Treasures.Epic.UseResidence", false);
		cc.put("Options.Perms-Treasures.Epic.Residence", Arrays.asList("Epic_Treasures_Residence"));
		cc.put("Options.Perms-Treasures.Rare.UseResidence", false);
		cc.put("Options.Perms-Treasures.Rare.Residence", Arrays.asList("Rare_Treasures_Residence"));
		cc.put("Options.Perms-Treasures.Common.UseResidence", false);
		cc.put("Options.Perms-Treasures.Common.Residence", Arrays.asList("Common_Treasures_Residence"));
		
		cc.put("Format.TopFisher", "&a%position%. &6%playername%&6: &a%record%cm");
		cc.put("Format.You", "&6You are &a%position%. &6with &a%record%cm");
		cc.put("Format.FishDescription", Arrays.asList("&8-------------------------"
				,"&7Weight: &a%weight%kg","&7Length: &a%length%cm","&7Can be found:"," &a%biomes%"
				,"&7Caught by: &a%fisher%","&8-------------------------"));
		
		cc.put("Format.FishOfDay",
				Arrays.asList("&8-------------------------"
				,"&7Name: &a%fish_name%"
				,"&7Type: &a%fish_type%"
				,"&7Bonus: &a%bonus%x"
				,"&8-------------------------"));
		
		if(!ccc.existPath("GUI.Stats")) {
		cc.put("GUI.Stats.1.Name", "&aPlayer");
		cc.put("GUI.Stats.1.Lore", Arrays.asList("&3> &a%playername%"));
		cc.put("GUI.Stats.1.Material", "PLAYER_HEAD");
		
		cc.put("GUI.Stats.2.Name", "&bAmount of catched fish");
		cc.put("GUI.Stats.2.Lore", Arrays.asList("&3> &a%fish_catched%"));
		cc.put("GUI.Stats.2.Material", "COD_BUCKET");
		
		cc.put("GUI.Stats.3.Name", "&4Best fish");
		cc.put("GUI.Stats.3.Lore", Arrays.asList("&3> &aType: &b%fish_type%","&3> &aName: &b%fish_name%","&3> &aRecord: &b%fish_record%"));
		cc.put("GUI.Stats.3.Material", "PAPER");
		
		cc.put("GUI.Stats.4.Name", "&eTournaments");
		cc.put("GUI.Stats.4.Lore", Arrays.asList("&3> &aPlayed: &b%tournament_played%",
				"&3> &aStats:","&3> &aStats:","&3>   &bTop 1: &b%tournament_top1%",
				"&3>   &6Top 2: &b%tournament_top2%","&3>   &eTop 3: &b%tournament_top3%"));
		cc.put("GUI.Stats.4.Material", "DIAMOND");
		}
		cc.put("Tournaments.DefaultLength", "10min");
		cc.put("Tournaments.Length.Name", "&aLength");
		cc.put("Tournaments.Length.Rewards.1", Arrays.asList("eco give %player% 500"));
		cc.put("Tournaments.Length.Rewards.2", Arrays.asList("eco give %player% 250"));
		cc.put("Tournaments.Length.Rewards.3", Arrays.asList("eco give %player% 100"));
		cc.put("Tournaments.Length.Positions", "&6%position%. %playername%&6 with %value%Cm");

		cc.put("Tournaments.Weight.Name", "&6Weight");
		cc.put("Tournaments.Weight.Rewards.1", Arrays.asList("eco give %player% 525"));
		cc.put("Tournaments.Weight.Rewards.2", Arrays.asList("eco give %player% 275"));
		cc.put("Tournaments.Weight.Rewards.3", Arrays.asList("eco give %player% 125"));
		cc.put("Tournaments.Weight.Positions", "&6%position%. %playername%&6 with %value%Kg");

		cc.put("Tournaments.MostCatch.Name", "&3Most Catch");
		cc.put("Tournaments.MostCatch.Rewards.1", Arrays.asList("eco give %player% 400"));
		cc.put("Tournaments.MostCatch.Rewards.2", Arrays.asList("eco give %player% 200"));
		cc.put("Tournaments.MostCatch.Rewards.3", Arrays.asList("eco give %player% 80"));
		cc.put("Tournaments.MostCatch.Positions", "&6%position%. %playername%&6 with %value% fish");
		
		if(!ccc.existPath("Types")) {
		cc.put("Types.Cod.0.Name", "&cBarracuda");
		cc.put("Types.Cod.0.Money", 0.5);
		cc.put("Types.Cod.0.Points", 0.1);
		cc.put("Types.Cod.0.Xp", 0.5);
		cc.put("Types.Cod.0.MaxCm", 130.0);
		cc.put("Types.Cod.0.ModelData", 0);
		cc.put("Types.Cod.0.Chance", 33.5);
		cc.put("Types.Cod.0.Lore", Arrays.asList("&8-------------------------"
				,"&7Weight: &a%weight%kg","&7Length: &a%length%cm","&7Can be found:"," &a%biomes%"
				,"&7Caught by: &a%fisher%","&8-------------------------"));
		cc.put("Types.Cod.0.Enchants", Arrays.asList("LURE:3"));
		
		
		cc.put("Types.Cod.1.Name", "&8Roach");
		cc.put("Types.Cod.1.Money", 0.2);
		cc.put("Types.Cod.1.Points", 0.1);
		cc.put("Types.Cod.1.Xp", 0.2);
		cc.put("Types.Cod.1.MaxCm", 60.0);
		cc.put("Types.Cod.1.ModelData", 1);
		cc.put("Types.Cod.1.Chance", 58.3);
		
		cc.put("Types.Cod.2.Name", "&2Common carp");
		cc.put("Types.Cod.2.Money", 0.6);
		cc.put("Types.Cod.2.Points", 0.2);
		cc.put("Types.Cod.2.Xp", 0.4);
		cc.put("Types.Cod.2.MaxCm", 80.0);
		cc.put("Types.Cod.2.ModelData", 2);
		cc.put("Types.Cod.2.Chance", 27.1);
		
		cc.put("Types.Salmon.0.Name", "&cSturgeon");
		cc.put("Types.Salmon.0.Money", 1.1);
		cc.put("Types.Salmon.0.Points", 0.3);
		cc.put("Types.Salmon.0.Xp", 0.9);
		cc.put("Types.Salmon.0.MaxCm", 120.0);
		cc.put("Types.Salmon.0.ModelData", 0);
		cc.put("Types.Salmon.0.Chance", 11.2);
		
		cc.put("Types.Salmon.1.Name", "&8Pike");
		cc.put("Types.Salmon.1.Money", 0.5);
		cc.put("Types.Salmon.1.Points", 0.2);
		cc.put("Types.Salmon.1.Xp", 0.3);
		cc.put("Types.Salmon.1.MaxCm", 70.0);
		cc.put("Types.Salmon.1.ModelData", 1);
		cc.put("Types.Salmon.1.Chance", 35.3);
		
		cc.put("Types.Salmon.2.Name", "&aCommon trout");
		cc.put("Types.Salmon.2.Money", 0.4);
		cc.put("Types.Salmon.2.Points", 0.1);
		cc.put("Types.Salmon.2.Xp", 0.2);
		cc.put("Types.Salmon.2.MaxCm", 60.0);
		cc.put("Types.Salmon.2.ModelData", 2);
		cc.put("Types.Salmon.2.Chance", 48.8);

		cc.put("Types.TropicalFish.0.Name", "&aAngel fish");
		cc.put("Types.TropicalFish.0.Money", 2.6);
		cc.put("Types.TropicalFish.0.Points", 0.8);
		cc.put("Types.TropicalFish.0.Xp", 3);
		cc.put("Types.TropicalFish.0.MaxCm", 25.0);
		cc.put("Types.TropicalFish.0.ModelData", 0);
		cc.put("Types.TropicalFish.0.Chance", 12.4);
		
		cc.put("Types.TropicalFish.1.Name", "&5Arowana fish");
		cc.put("Types.TropicalFish.1.Money", 1.5);
		cc.put("Types.TropicalFish.1.Points", 1.1);
		cc.put("Types.TropicalFish.1.Xp", 0.5);
		cc.put("Types.TropicalFish.1.MaxCm", 35.0);
		cc.put("Types.TropicalFish.1.ModelData", 1);
		cc.put("Types.TropicalFish.1.Chance", 25.9);

		cc.put("Types.TropicalFish.2.Name", "&6Brackish fish");
		cc.put("Types.TropicalFish.2.Money", 1.8);
		cc.put("Types.TropicalFish.2.Points", 0.2);
		cc.put("Types.TropicalFish.2.Xp", 6);
		cc.put("Types.TropicalFish.2.MaxCm", 40.0);
		cc.put("Types.TropicalFish.2.ModelData", 2);
		cc.put("Types.TropicalFish.2.Chance", 33.6);

		cc.put("Types.TropicalFish.3.Name", "&cNemo");
		cc.put("Types.TropicalFish.3.Money", 22.9);
		cc.put("Types.TropicalFish.3.Points", 0.6);
		cc.put("Types.TropicalFish.3.Xp", 1);
		cc.put("Types.TropicalFish.3.MaxCm", 20.0);
		cc.put("Types.TropicalFish.3.ModelData", 3);
		cc.put("Types.TropicalFish.3.Chance", 7.2);
		
		cc.put("Types.PufferFish.0.Name", "&ePufferfish");
		cc.put("Types.PufferFish.0.Money", 0.5);
		cc.put("Types.PufferFish.0.Points", 0.1);
		cc.put("Types.PufferFish.0.Xp", 1);
		cc.put("Types.PufferFish.0.MaxCm", 20.0);
		cc.put("Types.PufferFish.0.ModelData", 0);
		cc.put("Types.PufferFish.0.Chance", 45.9);

		cc.put("Types.PufferFish.1.Name", "&cArothron");
		cc.put("Types.PufferFish.1.Money", 0.6);
		cc.put("Types.PufferFish.1.Points", 0.1);
		cc.put("Types.PufferFish.1.Xp", 1);
		cc.put("Types.PufferFish.1.MaxCm", 45.0);
		cc.put("Types.PufferFish.1.ModelData", 1);
		cc.put("Types.PufferFish.1.Chance", 32.4);
		}
		if(!ccc.existPath("Treasures")) {
		cc.put("Treasures.COMMON.0.Name", "&8Old Chest");
		cc.put("Treasures.COMMON.0.Money", 17);
		cc.put("Treasures.COMMON.0.Points", 1.08);
		cc.put("Treasures.COMMON.0.Chance", 86);
		cc.put("Treasures.COMMON.0.Messages", Arrays.asList("&6You found %treasure% &7(Common)"));
		cc.put("Treasures.COMMON.0.Commands", Arrays.asList("give %player% stick 1", "give %player% dirt 4", "give %player% string 1"));
		cc.put("Treasures.COMMON.1.Name", "&6Crate");
		cc.put("Treasures.COMMON.1.Money", 27);
		cc.put("Treasures.COMMON.1.Points", 0.12);
		cc.put("Treasures.COMMON.1.Chance", 14);
		cc.put("Treasures.COMMON.1.Messages", Arrays.asList("&6You found %treasure% &7(Common)"));
		cc.put("Treasures.COMMON.1.Commands", Arrays.asList("give %player% stick 8", "give %player% apple 12", "give %player% string 11"));
		cc.put("Treasures.COMMON.2.Name", "&aTreasure");
		cc.put("Treasures.COMMON.2.Money", 53);
		cc.put("Treasures.COMMON.2.Points", 2.1);
		cc.put("Treasures.COMMON.2.Chance", 10);
		cc.put("Treasures.COMMON.2.Messages", Arrays.asList("&6You found %treasure% &7(Common)"));
		cc.put("Treasures.COMMON.2.Commands", Arrays.asList("give %player% gold_ingot 4", "give %player% fishing_rod 1", "give %player% iron_ingot 2"));

		cc.put("Treasures.RARE.0.Name", "&9Box");
		cc.put("Treasures.RARE.0.Money", 29);
		cc.put("Treasures.RARE.0.Points", 1.6);
		cc.put("Treasures.RARE.0.Chance", 78);
		cc.put("Treasures.RARE.0.Messages", Arrays.asList("&6You found %treasure% &7(Rare)"));
		cc.put("Treasures.RARE.0.Commands", Arrays.asList("give %player% iron_nugget 2"));
		cc.put("Treasures.RARE.1.Name", "&1Treasure");
		cc.put("Treasures.RARE.1.Money", 77);
		cc.put("Treasures.RARE.1.Points", 3.4);
		cc.put("Treasures.RARE.1.Chance", 22);
		cc.put("Treasures.RARE.1.Messages", Arrays.asList("&6You found %treasure% &7(Rare)"));
		cc.put("Treasures.RARE.1.Commands", Arrays.asList("give %player% diamond 1", "give %player% gold_nugget 4"));

		cc.put("Treasures.EPIC.0.Name", "&cJewelry");
		cc.put("Treasures.EPIC.0.Money", 175);
		cc.put("Treasures.EPIC.0.Points", 3.8);
		cc.put("Treasures.EPIC.0.Chance", 89);
		cc.put("Treasures.EPIC.0.Messages", Arrays.asList("&6You found %treasure% &7(Epic)"));
		cc.put("Treasures.EPIC.0.Commands", Arrays.asList("give %player% gold_nugget 15", "give %player% diamond 3"));
		cc.put("Treasures.EPIC.1.Name", "&cTreasure");
		cc.put("Treasures.EPIC.1.Money", 121);
		cc.put("Treasures.EPIC.1.Points", 9.74);
		cc.put("Treasures.EPIC.1.Chance", 11);
		cc.put("Treasures.EPIC.1.Messages", Arrays.asList("&6You found %treasure% &7(Epic)"));
		cc.put("Treasures.EPIC.1.Commands", Arrays.asList("give %player% diamond 7","give %player% iron_ingot 4","give %player% cobweb 1","give %player% string 3"));

		cc.put("Treasures.LEGENDARY.0.Name", "&cSafe");
		cc.put("Treasures.LEGENDARY.0.Money", 33);
		cc.put("Treasures.LEGENDARY.0.Points", 2.41);
		cc.put("Treasures.LEGENDARY.0.Chance", 92);
		cc.put("Treasures.LEGENDARY.0.Messages", Arrays.asList("&6You found %treasure% &7(Legendary)"));
		cc.put("Treasures.LEGENDARY.0.Commands", Arrays.asList("give %player% diamond 17","give %player% enchanted_golden_apple 5"));
		cc.put("Treasures.LEGENDARY.1.Name", "&4Lost Treasure");
		cc.put("Treasures.LEGENDARY.1.Money", 358);
		cc.put("Treasures.LEGENDARY.1.Points", 80.16);
		cc.put("Treasures.LEGENDARY.1.Chance", 8);
		cc.put("Treasures.LEGENDARY.1.Messages", Arrays.asList("&6You found %treasure% &7(Legendary)"));
		cc.put("Treasures.LEGENDARY.1.Commands", Arrays.asList("give %player% diamond_block 3","give %player% gold_ingot 27"
				,"give %player% golden_apple 11","give %player% cobweb 2"));
		}
		if(!ccc.existPath("Quests")) {
			cc.put("Quests.0.Name", "&aJohny is hungry (Easy)");
			cc.put("Quests.0.Description", "&6Catch 5x Roach, 2x Barracuda..");
			cc.put("Quests.0.Stage.0.Action", "CATCH_FISH");
			cc.put("Quests.0.Stage.0.Fish", 1);
			cc.put("Quests.0.Stage.0.Type", "Cod");
			cc.put("Quests.0.Stage.0.Amount", 5);

			cc.put("Quests.0.Stage.1.Action", "SELL_FISH");
			cc.put("Quests.0.Stage.1.Fish", 1);
			cc.put("Quests.0.Stage.1.Type", "Cod");
			cc.put("Quests.0.Stage.1.Amount", 2);

			cc.put("Quests.0.Stage.2.Action", "CATCH_FISH");
			cc.put("Quests.0.Stage.2.Fish", 0);
			cc.put("Quests.0.Stage.2.Type", "Cod");
			cc.put("Quests.0.Stage.2.Amount", 2);
			cc.put("Quests.0.Stage.3.Action", "SELL_FISH");
			cc.put("Quests.0.Stage.3.Fish", 0);
			cc.put("Quests.0.Stage.3.Type", "Cod");
			cc.put("Quests.0.Stage.3.Amount", 2);
			cc.put("Quests.0.Rewards.Commands", Arrays.asList("eco give %player% 500", "give %player% iron_ingot 10"));
			cc.put("Quests.0.Rewards.Messages", Arrays.asList("&aCongratulation %player% !", "&aYou completed quest &c%quest% &a!"));

			cc.put("Quests.1.Name", "&aStraiker123's challenge (Hard)");
			cc.put("Quests.1.Time", "7d");
			cc.put("Quests.1.Description", "&67 Days to complete, Catch 20x Brackish fish..");
			
			cc.put("Quests.1.Stage.0.Action", "CATCH_FISH");
			cc.put("Quests.1.Stage.0.Fish", 2);
			cc.put("Quests.1.Stage.0.Type", "TropicalFish");
			cc.put("Quests.1.Stage.0.Amount", 20);

			cc.put("Quests.1.Stage.1.Action", "CATCH_FISH");
			cc.put("Quests.1.Stage.1.Fish", 1);
			cc.put("Quests.1.Stage.1.Type", "PufferFish");
			cc.put("Quests.1.Stage.1.Amount", 15);

			cc.put("Quests.1.Stage.2.Action", "CATCH_FISH");
			cc.put("Quests.1.Stage.2.Fish", 1);
			cc.put("Quests.1.Stage.2.Type", "Cod");
			cc.put("Quests.1.Stage.2.Amount", 25);
			cc.put("Quests.1.Rewards.Commands", Arrays.asList("eco give %player% 2500", "give %player% diamond 5"));
			cc.put("Quests.1.Rewards.Messages", Arrays.asList("&aCongratulation %player% !", "&aYou completed quest &c%quest% &a!"));
			
			
		}
		if(!ccc.existPath("Enchants")) {
		cc.put("Enchants.0.Name", "&2Fortune");
		cc.put("Enchants.0.AmountBonus", 0.67);
		cc.put("Enchants.0.MoneyBonus", 0.39);
		cc.put("Enchants.0.PointsBonus", 0.11);
		cc.put("Enchants.0.ExpBonus", 0.23);
		cc.put("Enchants.0.Cost", 65);
		cc.put("Enchants.0.Description", Arrays.asList("&7Most increases amount and money bonus"," &7- Cost: %cost% Points"));
		cc.put("Enchants.1.Name", "&cDouble hook");
		cc.put("Enchants.1.AmountBonus", 0.21);
		cc.put("Enchants.1.MoneyBonus", 0.52);
		cc.put("Enchants.1.PointsBonus", 0.33);
		cc.put("Enchants.1.ExpBonus", 0.32);
		cc.put("Enchants.1.Cost", 45);
		cc.put("Enchants.1.Description", Arrays.asList("&7Increases money, points and exp bonus","&7From caught fish"," &7- Cost: %cost% Points"));
		cc.put("Enchants.2.Name", "&3Stronger line");
		cc.put("Enchants.2.AmountBonus", 0.12);
		cc.put("Enchants.2.MoneyBonus", 0.24);
		cc.put("Enchants.2.PointsBonus", 0.68);
		cc.put("Enchants.2.ExpBonus", 0.53);
		cc.put("Enchants.2.Cost", 35);
		cc.put("Enchants.2.Description", Arrays.asList("&7Increases points and exp bonus","&7From caught fish"," &7- Cost: %cost% Points"));
		cc.put("Enchants.3.Name", "&eLength of the fishing rod");
		cc.put("Enchants.3.AmountBonus", 0.15);
		cc.put("Enchants.3.MoneyBonus", 0.15);
		cc.put("Enchants.3.PointsBonus", 0.15);
		cc.put("Enchants.3.ExpBonus", 0.15);
		cc.put("Enchants.3.Cost", 50);
		cc.put("Enchants.3.Description", Arrays.asList("&7Increases all bonuses by 0.15%"," &7- Cost: %cost% Points"));
		cc.put("Enchants.4.Name", "&9Convenience");
		cc.put("Enchants.4.AmountBonus", 0.18);
		cc.put("Enchants.4.MoneyBonus", 0.22);
		cc.put("Enchants.4.PointsBonus", 0.25);
		cc.put("Enchants.4.ExpBonus", 0.34);
		cc.put("Enchants.4.Cost", 30);
		cc.put("Enchants.4.Description", Arrays.asList("&7Increases exp bonus","&7From caught fish"," &7- Cost: %cost% Points"));
		}
		c.addDefaults(cc);
		c.create();
		Loader.c=c.getConfig();
	}
	
	static void setupTranslations() {
		TranslationsFile = Loader.getConfig("AmazingFishing", "Translations");
		Map<String, Object> a = new HashMap<String, Object>();
		a.put("CommandIsDisabled", "&cCommand is disabled!");
		a.put("TopPlayers", "&6Top 3 players records on fish &a%fish%");
		a.put("Prefix", "&bFishing &4� &6");
		a.put("Stats", Arrays.asList("&6------< &b%playername% &6>------"
				,"&b> &6Caught fish: &a%fish%","&b> &6Longest fish & name: &a%fish%: %record%"
				,"&b> &6Participating tournaments: &a%tournaments%","&b> &6Top 1 in participating tournaments: &a%top1% times"));
		a.put("ConfigReloaded", "&2Config reloaded!");
		a.put("SoldFish", "&eSold %amount% fish for %money%$, %exp%exp and %points% points");
		a.put("MissingData", "&cMissing data, First you must caught fish");
		a.put("CommandIsDisabled", "&cCommand is disabled!");
		a.put("Biome-Added", "&aBiome added");
		a.put("ConsoleErrorMessage", "&cCommand can be used only in-game!");
		a.put("NoPermissions", "&cYou don't have permission '%permission%' to do that!");
		a.put("AFK-Title.1", "&6Please move");
		a.put("AFK-Title.2", "&6To another location");
		a.put("Help.Reload", "&5Reload config");
		a.put("Help.Stats.My", "&5Your fishing stats");
		a.put("Help.Stats.Other", "&5Fishing stats of specified player");
		a.put("Help.Tournament", "&5Start new tournament for time");
		a.put("Help.Tournament_Stop", "&cStop running tournament");
		a.put("Help.Editor", "&5Open virtual editor for plugin settings/fish and more");
		a.put("Help.List", "&5List of fish");
		a.put("Help.Points", "&5Manager of player points");
		a.put("Help.Points-Give", "&5Give player specified amount of points");
		a.put("Help.Points-Take", "&5Take from player specified amount of points");
		a.put("Help.Points-Set", "&5Set specified amount of player points");
		a.put("Help.Points-Balance", "&5Points balance of player");
		a.put("Help.Toggle", "&5Toggle new record message");
		a.put("Help.Record", "&5Your record of specified fish");
		a.put("Help.Top", "&5Top 3 players of specified fish");
		a.put("Help.Bag", "&5Open your fish bag");
		a.put("Help.Shop", "&5Open shop");
		
		a.put("Help.Enchants", "&5Open enchant table for your fishing rod");
		a.put("Help.Reload", "&5Reload config");
		a.put("Editor.MissingCrateName.1", "&6Missing");
		a.put("Editor.MissingCrateName.2", "&6Name of treasure!");
		a.put("Editor.SuccefullyCreatedCrate.1", "&6Treasure &a%treasure%");
		a.put("Editor.SuccefullyCreatedCrate.2", "&6Succefully created!");
		a.put("Editor.WriteCommand.1", "&6Write new command");
		a.put("Editor.WriteCommand.2", "&6To the chat.");
		a.put("Editor.WriteMoney.1", "&6Write new money amount");
		a.put("Editor.WriteMoney.2", "&6To the chat.");
		a.put("Editor.WriteExp.1", "&6Write new experiences amount");
		a.put("Editor.WriteExp.2", "&6To the chat.");
		a.put("Editor.WritePoint.1", "&6Write new points amount");
		a.put("Editor.WritePoint.2", "&6To the chat.");
		a.put("Editor.NewMessage.1", "&6Write new message");
		a.put("Editor.NewMessage.2", "&6To the chat.");
		a.put("Editor.WriteName.1", "&6Write new name");
		a.put("Editor.WriteName.2", "&6To the chat.");
		a.put("Editor.WriteCost.1", "&6Write cost of enchant");
		a.put("Editor.WriteCost.2", "&6To the chat.");
		
		a.put("Editor.WriteChance.1", "&6Write new chance in percentage");
		a.put("Editor.WriteChance.2", "&6To the chat.");
		a.put("Editor.WriteLength.1", "&6Write new max length of fish");
		a.put("Editor.WriteLength.2", "&6To the chat.");
		a.put("Editor.WriteExpBonus.1", "&6Write exp bonus in percentage");
		a.put("Editor.WriteExpBonus.2", "&6To the chat.");
		a.put("Editor.WriteAmount.1", "&6Write fish chance amount bonus");
		a.put("Editor.WriteAmount.2", "&6To the chat.");
		a.put("Editor.SuccefullyCreatedEnchant.1", "&6Enchant %enchant%");
		a.put("Editor.SuccefullyCreatedEnchant.2", "&6Succefully created!");
		a.put("Editor.MissingEnchantName.1", "&6Missing");
		a.put("Editor.MissingEnchantName.2", "&6Name of enchant!");
		
		a.put("Editor.WriteMoneyBonus.1", "&6Write money bonus in percentage");
		a.put("Editor.WriteMoneyBonus.2", "&6To the chat."); 
		a.put("Editor.WritePointsBonus.1", "&6Write points bonus in percentage");
		a.put("Editor.WritePointsBonus.2", "&6To the chat.");
		a.put("Editor.NewDescription.1", "&6Write new description message");
		a.put("Editor.NewDescription.2", "&6To the chat.");
		
		a.put("Editor.Saved.1", "&6Saved!");
		a.put("Editor.Saved.2", "");
		
		a.put("Editor.MissingFishName.1", "&6Missing");
		a.put("Editor.MissingFishName.2", "&6Name of fish!");
		a.put("Editor.SuccefullyCreated.1", "&6Fish &a%fish%");
		a.put("Editor.SuccefullyCreated.2", "&6Succefully created!");
		a.put("ReachNewRecord", "&6You reach new record on fish &a%fish%&6, Last record was &a%last%cm&6, New record &a%record%cm");
		a.put("RecordOnFish", "&6Your record on fish &a%fish%&6 is &a%record%cm");
		a.put("NeverCaught", "&6You never caught &c%fish%");
		a.put("Caught", "&6You caught &a%fish% &6with &a%cm%cm &7(%weight%kg)");
		a.put("TournamentTypes", "&6Tournament Types:");
		a.put("ListFish", "&6List of fish:");

		a.put("HelpGUI.Record.Want", "&aI want receive record reached message");
		a.put("HelpGUI.Record.DoNotWant", "&cI don't want receive record reached message");

		a.put("HelpGUI.Record.Receive", "&aYou will receive a new record message");
		a.put("HelpGUI.ClickToStart", "&aClick to start");

		a.put("HelpGUI.Record.NoLongerReceive", "&cYou will no longer receive a new record messagee");
		a.put("Winners", "&6Tournament winners of type &0'%type%&0'&6:");
		a.put("Stopped", "&cStopped tournament type &0'%type%&0' &6on %time%");
		a.put("Started", "&aStarted tournament type &0'%type%&0' &6on %time%");
		a.put("Running", "&6Running tournament type &0'%type%&0' &6on %time%");
		
		a.put("Words.Pufferfish", "&ePufferfish");
		a.put("Words.Salmon", "&4Salmon");
		a.put("Words.Cod", "&8Cod");
		a.put("Words.Tropical_Fish", "&cTropical Fish");
		a.put("Words.FishRemove", "&cRemove normal fish on catch custom fish");

		a.put("Words.Bag_Title", "&bFish Bag");
		a.put("Words.Cost", "&6Cost");
		a.put("Words.MoneyBonus", "&6Money Bonus");
		a.put("Words.PointsBonus", "&9Points Bonus");
		a.put("Words.Description", "&3Description");
		a.put("Words.Amount", "&aAmount Bonus");
		a.put("Words.Exp", "&9Experiences Bonus");
		a.put("Words.Help.Top", "&bTop 3 players records");
		a.put("Words.Help.Record", "&cYour records");
		a.put("Words.Help.Toggle", "&3Toggle new record reached message");
		a.put("Words.Help.Editor", "&6Editor of Fish and Settings");
		a.put("Words.Help.List", "&aList of fish");
		a.put("Words.Help.Tournament", "&bStart new tournament");
		a.put("Words.PerBiome", "&bPer Biome");
		
		a.put("Words.Help.Enchant", "&5Enchant table for your rod");
		a.put("Words.Help.Shop", "&dShop");
		a.put("Words.Help.Reload", "&2Reload config");
		a.put("Words.Help.Admin", "&7Switch to admin section");
		a.put("Words.Help.Player", "&7Switch to player section");
		a.put("Words.Help.Points", "&9Manager of player points");
		a.put("Words.Help.Stats.My", "&cStats about you");
		a.put("Words.Help.Bag", "&5Open fish bag");
		
		a.put("Words.Help.Stats.Other", "&cStats about specified player");
		
		a.put("Words.Create", "&2Create");
		a.put("Words.Delete", "&4Delete");
		a.put("Words.Edit", "&6Edit");
		a.put("Words.Deleted", "&4DELETED");
		a.put("Words.Money", "&6Money");
		a.put("Words.MaxCm", "&3Max length");
		a.put("Words.Cancel", "&cCancel");
		a.put("Words.Back", "&4Back");
		a.put("Words.Bag", "&eBag");
		

		a.put("Words.SelectEnchants", "&5Enchants editor");
		a.put("Words.SelectFish", "&6Fish editor");
		a.put("Words.SelectTreasures", "&bTreasures editor");
		a.put("Words.SelectSettings", "&7Settings editor");
		a.put("Words.Fish", "&7Fish");
		a.put("Words.FishOfDay", "&aToday's bonus fish");
		
		a.put("Words.Commands", "&dCommands");
		a.put("Words.Messages", "&rMessages");
		a.put("Words.Chance", "&eChance");
		a.put("Words.Sell_Fish", "&6Sell fish");
		
		a.put("Words.Common", "&7Common");
		a.put("Words.Rare", "&1Rare");
		a.put("Words.Epic", "&6Epic");
		a.put("Words.Legendary", "&4Legendary");
		a.put("Words.NowPoints-Give", "&2Give points");
		a.put("Words.NowPoints-Take", "&cTake points");
		a.put("Words.NowPoints-Set", "&6Set points");

		a.put("Words.Biomes.ALL.Name", "&6ALL");
		a.put("Words.Biomes.ALL.Icon", "CHEST");
		a.put("Words.Biomes.BADLANDS.Name", "&7BADLANDS");
		a.put("Words.Biomes.BADLANDS.Icon", "RED_SAND");
		a.put("Words.Biomes.BEACH.Name", "&eBEACH");
		a.put("Words.Biomes.BEACH.Icon", "SAND");
		a.put("Words.Biomes.MUSHROOM.Name", "&dMUSHROOMS FIELD");
		a.put("Words.Biomes.MUSHROOM.Icon", "MYCELIUM");
		
		

		a.put("Words.Biomes.NETHER.Name", "&4NETHER");
		a.put("Words.Biomes.NETHER.Icon", "LAVA_BUCKET");

		a.put("Words.Biomes.END.Name", "&5THE END");
		a.put("Words.Biomes.END.Icon", "ENDER_PEARL");

		a.put("Words.Biomes.TUNDRA.Name", "&9TUNDRA");
		a.put("Words.Biomes.TUNDRA.Icon", "SNOW");

		a.put("Words.Biomes.WOODED_HILLS.Name", "&2WOODED HILLS");
		a.put("Words.Biomes.WOODED_HILLS.Icon", "OAK_LOG");

		a.put("Words.Biomes.STONE_SHORE.Name", "&7STONE SHORE");
		a.put("Words.Biomes.STONE_SHORE.Icon", "STONE");

		a.put("Words.Biomes.SAVANNA.Name", "&6SAVANNA");
		a.put("Words.Biomes.SAVANNA.Icon", "SAND");
		
		a.put("Words.Biomes.COLD_OCEAN.Name", "&1COLD OCEAN");
		a.put("Words.Biomes.COLD_OCEAN.Icon", "WATER_BUCKET");
		a.put("Words.Biomes.DEEP_COLD_OCEAN.Name", "&9DEEP COLD OCEAN");
		a.put("Words.Biomes.DEEP_COLD_OCEAN.Icon", "WATER_BUCKET");
		a.put("Words.Biomes.DEEP_FROZEN_OCEAN.Name", "&1DEEP FROZEN OCEAN");
		a.put("Words.Biomes.DEEP_FROZEN_OCEAN.Icon", "ICE");
		a.put("Words.Biomes.DEEP_LUKEWARM_OCEAN.Name", "&cDEEP LUKEWARM OCEAN");
		a.put("Words.Biomes.DEEP_LUKEWARM_OCEAN.Icon", "FIRE_CORAL");
		a.put("Words.Biomes.DEEP_WARM_OCEAN.Name", "&cDEEP WARM OCEAN");
		a.put("Words.Biomes.DEEP_WARM_OCEAN.Icon", "FIRE_CORAL");
		a.put("Words.Biomes.DEEP_OCEAN.Name", "&9DEEP OCEAN");
		a.put("Words.Biomes.DEEP_OCEAN.Icon", "WATER_BUCKET");
		a.put("Words.Biomes.DESERT.Name", "&eDESERT");
		a.put("Words.Biomes.DESERT.Icon", "CACTUS");
		a.put("Words.Biomes.FOREST.Name", "&2FOREST");
		a.put("Words.Biomes.FOREST.Icon", "OAK_SAPLING");
		a.put("Words.Biomes.FROZEN_OCEAN.Name", "&1FROZEN OCEAN");
		a.put("Words.Biomes.FROZEN_OCEAN.Icon", "ICE");
		a.put("Words.Biomes.ICE_SPIKES.Name", "&1ICE SPIKES");
		a.put("Words.Biomes.ICE_SPIKES.Icon", "PACKED_ICE");
		a.put("Words.Biomes.JUNGLE.Name", "&aJUNGLE");
		a.put("Words.Biomes.JUNGLE.Icon", "JUNGLE_SAPLING");
		a.put("Words.Biomes.MOUNTAINS.Name", "&7MOUNTAINS");
		a.put("Words.Biomes.MOUNTAINS.Icon", "STONE");
		a.put("Words.Biomes.TAIGA.Name", "&2TAIGA");
		a.put("Words.Biomes.TAIGA.Icon", "SPRUCE_SAPLING");
		a.put("Words.Biomes.WARM_OCEAN.Name", "&cWARM OCEAN");
		a.put("Words.Biomes.WARM_OCEAN.Icon", "FIRE_CORAL");
		a.put("Words.Biomes.PLAINS.Name", "&aPLAINS");
		a.put("Words.Biomes.PLAINS.Icon", "GRASS_BLOCK");
		a.put("Words.Biomes.RIVER.Name", "&9RIVER");
		a.put("Words.Biomes.RIVER.Icon", "DIRT");
		a.put("Words.Biomes.SWAMP.Name", "&2SWAMP");
		a.put("Words.Biomes.SWAMP.Icon", "SLIME_BALL");
		
		
		a.put("Words.Close", "&cClose");
		a.put("Words.Save", "&2Save");
		a.put("Words.ShopLogo", "&6Fishing Shop");
		a.put("Words.Shop_Sell_Title", "&6Fishing Shop Sell Section");
		
		a.put("Words.EverythingByCm", "&6Earnings from fish length");
		a.put("Words.Enabled", "&aEnabled");
		a.put("Words.Disabled", "&cDisabled");
		a.put("Words.Shop", "&6Custom plugin shop");
		a.put("Words.HasPoints", "&9%points% points");
		a.put("Words.Enchants", "&6Custom fishing rod enchants");
		a.put("Words.Treasures", "&6Custom treasures");
		a.put("Words.TreasuresTitle", "&bTreaures");
		a.put("Words.Points", "&dPoints");
		a.put("Words.Name", "&bName");
		a.put("Words.Experiences", "&9Experiences");
		a.put("Toggled.true", "&6Notifications about new records will be no longer shows");
		a.put("Toggled.false", "&6Notifications about new records now will be showing");
		TranslationsFile.addDefaults(a);
		TranslationsFile.create();
		Loader.TranslationsFile=TranslationsFile.getConfig();
	}
	
	public static void loadShop() {

		shop = Loader.getConfig("AmazingFishing","AmazingFishing-Shop.yml");
		Map<String, Object> a = new HashMap<String, Object>();
			a.put("GUI.Bag.Icon", "CHEST");
			a.put("GUI.Bag.ModelData", "1");
			a.put("GUI.Bag.Name", "&6Bag");
			a.put("GUI.Bag.Lore", Arrays.asList("&7Click to open bag"));

			a.put("GUI.Points.Icon", "LAPIS_LAZULI");
			a.put("GUI.Points.ModelData", "1");
			a.put("GUI.Points.Name", "&9Points");
			a.put("GUI.Points.Lore", Arrays.asList("&7Currently you have %points% points"));
			
			a.put("GUI.BuyShop.Icon", "EMERALD");
			a.put("GUI.BuyShop.ModelData", "1");
			a.put("GUI.BuyShop.Name", "&eBuy shop");
			a.put("GUI.BuyShop.Lore", Arrays.asList("&7Open shop in which you can buy items"));

			a.put("GUI.SellShop.Icon", "COD_BUCKET");
			a.put("GUI.SellShop.ModelData", "1");
			a.put("GUI.SellShop.Name", "&eSell Shop");
			a.put("GUI.SellShop.Lore", Arrays.asList("&7Open shop in which you can sell fish"));
			
			a.put("GUI.Sell.Icon", "GOLD_INGOT");
			a.put("GUI.Sell.ModelData", "1");
			a.put("GUI.Sell.Name", "&eSell fish");
			a.put("GUI.Sell.Lore", Arrays.asList("&7Sell fish in shop"));
		if(!Loader.getConfig("AmazingFishing", "AmazingFishing-Shop.yml").existPath("Items")) {
			a.put("Items.0.Icon", "STONE_SWORD");
			a.put("Items.0.Name", "&7&lBeginner kit");
			a.put("Items.0.Description", Arrays.asList("&eCost %cost% points"));
			a.put("Items.0.Cost", 50);
			
			a.put("Items.0.Messages", Arrays.asList("&6You bought a &7&lBeginner kit&r &6for &a%cost% points"));
			
			a.put("Items.0.Item.0.Material", "Fishing_rod");
			a.put("Items.0.Item.0.Name", "&7Basic fishing rod");
			a.put("Items.0.Item.0.ModelData", "0");
			a.put("Items.0.Item.0.Amount", 1);
			a.put("Items.0.Item.0.HideEnchants", true);
			a.put("Items.0.Item.0.Enchants", Arrays.asList("Unbreaking 1"));
			
			a.put("Items.0.Item.1.Material", "Oak_Boat");
			a.put("Items.0.Item.1.ModelData", "1");
			a.put("Items.0.Item.1.HideAttributes", true);
			a.put("Items.0.Item.1.Unbreakable", true); //unused, but why not
			
			a.put("Items.0.Item.2.Material", "Apple");
			a.put("Items.0.Item.2.ModelData", "2");
			a.put("Items.0.Item.2.Name", "&cTasty apple");
			a.put("Items.0.Item.2.Amount", 3);
			a.put("Items.0.Item.2.Lore", Arrays.asList("&4&lEAT ME"));

			
			a.put("Items.1.Icon", "IRON_SWORD");
			a.put("Items.1.Name", "&e&lIron kit");
			a.put("Items.1.Description", Arrays.asList("&eCost %cost% points"));
			a.put("Items.1.Cost", 275);
			
			a.put("Items.1.Messages", Arrays.asList("&6You bought a &e&lIron kit&r &6for &a%cost% points"));
			a.put("Items.1.Commands", Arrays.asList("heal %player%"));
			
			a.put("Items.1.Item.0.Material", "Fishing_rod");
			a.put("Items.1.Item.0.ModelData", "3");
			a.put("Items.1.Item.0.Name", "&bPerfect fishing rod");
			a.put("Items.1.Item.0.Lore", Arrays.asList("&3Fishing 4 ever! <3"));
			a.put("Items.1.Item.0.Amount", 1);
			a.put("Items.1.Item.0.HideEnchants", true);
			a.put("Items.1.Item.0.Enchants", Arrays.asList("Unbreaking 4","Luck_of_sea 2"));
			
			a.put("Items.1.Item.1.Material", "Oak_Boat");
			a.put("Items.1.Item.1.Amount", 2);
			a.put("Items.1.Item.1.ModelData", "4");
			a.put("Items.1.Item.1.HideAttributes", true);
			a.put("Items.1.Item.1.Unbreakable", true); //unused, but why not
			
			a.put("Items.1.Item.2.Material", "Golden_Apple");
			a.put("Items.1.Item.2.Name", "&dSpecial apple");
			a.put("Items.1.Item.2.ModelData", "5");
			a.put("Items.1.Item.2.Lore", Arrays.asList("&5Really special!"));
			a.put("Items.1.Item.2.Amount", 7);
		}
		shop.setHeader(
				"%player%   select player\n"
				+"%coins%   amount of coins\n");
		shop.addDefaults(a);
		shop.create();
		Loader.shop=shop.getConfig();
	}
	
	public static void loadData() {
		me = Loader.getConfig("AmazingFishing", "Data");
		me.create();
		Loader.me=me.getConfig();
	    
	}
	public static void LoadConfigs() {
		setupConfig();
		setupTranslations();
		loadShop();
		loadData();
	}
	public static void reload() {
		c.reload();
		TranslationsFile.reload();
		shop.reload();
		me.reload();
		Loader.c=c.getConfig();
		Loader.TranslationsFile=TranslationsFile.getConfig();
		Loader.shop=shop.getConfig();
		Loader.me=me.getConfig();
	}

	
}

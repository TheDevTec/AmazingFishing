package me.DevTec.AmazingFishing;

import java.util.Arrays;

import me.DevTec.TheAPI.ConfigAPI.Config;

public class Configs {
	
	/*public static ConfigAPI c;
	public static ConfigAPI TranslationsFile;
	public static ConfigAPI shop;
	public static ConfigAPI me;*/
	public static Config ccc = Loader.getConfig("AmazingFishing", "AmazingFishing.yml");

	static void setupConfig() {
		//c = Loader.getConfig("AmazingFishing", "AmazingFishing");
		Loader.c = new Config("AmazingFishing/AmazingFishing.yml");
		
		Loader.c.addDefault("Options.BossBar.Use", true);
		Loader.c.addDefault("Options.BossBar.OnlyIfCatchFish", true);
		Loader.c.addDefault("Options.BossBar.Running", "&cRunning tournament type &e%type% &con &e%time_formated%");
		Loader.c.addDefault("Options.BossBar.Win", "&2Tournament was end. You are on %position%. position");
		Loader.c.addDefault("Options.Treasures", true);
		Loader.c.addDefault("Options.Shop", true);
		
		Loader.c.addDefault("Options.UseDoubles.Weight", true);
		Loader.c.addDefault("Options.UseDoubles.Length", true);
		Loader.c.addDefault("Options.UseCustomFishDescription-ifItIsPossible", false);
		Loader.c.addDefault("Options.Fish.EnchantsOnCustomFish", false);
		Loader.c.addDefault("Options.Fish.HideEnchants", false);
		
		Loader.c.addDefault("Options.CustomFishOnlyWhileTournament", false);
		Loader.c.addDefault("Options.TreasuresOnlyWhileTournament", false);
		Loader.c.addDefault("Options.Enchants", true);
		Loader.c.addDefault("Options.DisableMoneyFromCaught", false);

		Loader.c.addDefault("Options.SellFish.DisableMoney", false);
		Loader.c.addDefault("Options.SellFish.DisableXP", false);
		Loader.c.addDefault("Options.SellFish.DisablePoints", false);
		Loader.c.addDefault("Options.SellFish.ShopGiveFullPriceFish", false);
		Loader.c.addDefault("Options.SellFish.EarnFromLength", false);
		Loader.c.addDefault("Options.SellFish.EarnFromWeight", false);
		
		Loader.c.addDefault("Options.ShopGiveFullPriceFish", false);
		Loader.c.addDefault("Options.EarnFromLength", true);
		Loader.c.addDefault("Options.UseGUI", true);
		Loader.c.addDefault("Options.ShopSellFish", true);
		Loader.c.addDefault("Options.LogCaughtFishToConsole", false);
		Loader.c.addDefault("Options.Tournament.AutoStart", true);
		Loader.c.addDefault("Options.Tournament.RequiredPlayers", 10);
		Loader.c.addDefault("Options.Tournament.Delay", "2h");
		Loader.c.addDefault("Options.Tournament.Time", "5min");
		Loader.c.addDefault("Options.Tournament.DeletePlayersOnLeave", true);
		Loader.c.addDefault("Options.FishRemove", false);
		Loader.c.addDefault("Options.Particles", true);
		Loader.c.addDefault("Options.Sounds.Shop-BuyItem", true);
		Loader.c.addDefault("Options.Sounds.Shop-SellFish", true);
		Loader.c.addDefault("Options.Sounds.Bag-SellFish", true);
		Loader.c.addDefault("Options.Sounds.CatchFish", false);
		Loader.c.addDefault("Options.Disabled-Worlds", Arrays.asList("creative_world","disabled_world"));
		Loader.c.addDefault("Options.AFK.Enabled", true);
		Loader.c.addDefault("Options.AFK.TimeToAFK", 300);
		Loader.c.addDefault("Options.Bag.StorageItems", Arrays.asList("Fish","Fishing_rod"));
		Loader.c.addDefault("Options.Bag.ButtonsToSellFish", true);
		Loader.c.addDefault("Options.Bag.StoreCaughtFish", true);
		
		Loader.c.addDefault("Options.Bag.ButtonsToOpenShop", true);
		Loader.c.addDefault("Options.Manual.MinimalFishLength", 0.15);
		Loader.c.addDefault("Options.Manual.ChanceForTreasure", 5);
		Loader.c.addDefault("Options.Perms-Treasures.Legendary.UseWorldGuard-Regions", false);
		Loader.c.addDefault("Options.Perms-Treasures.Legendary.WorldGuard-Regions",  Arrays.asList("Legendary_Treasures_Region"));
		Loader.c.addDefault("Options.Perms-Treasures.Epic.UseWorldGuard-Regions", false);
		Loader.c.addDefault("Options.Perms-Treasures.Epic.WorldGuard-Regions",  Arrays.asList("Epic_Treasures_Region"));
		Loader.c.addDefault("Options.Perms-Treasures.Rare.UseWorldGuard-Regions", false);
		Loader.c.addDefault("Options.Perms-Treasures.Rare.WorldGuard-Regions",  Arrays.asList("Rare_Treasures_Region"));
		Loader.c.addDefault("Options.Perms-Treasures.Common.UseWorldGuard-Regions", false);
		Loader.c.addDefault("Options.Perms-Treasures.Common.WorldGuard-Regions",  Arrays.asList("Common_Treasures_Region"));

		Loader.c.addDefault("Options.Perms-Treasures.Legendary.UseResidence", false);
		Loader.c.addDefault("Options.Perms-Treasures.Legendary.Residence", Arrays.asList("Legendary_Treasures_Residence"));
		Loader.c.addDefault("Options.Perms-Treasures.Epic.UseResidence", false);
		Loader.c.addDefault("Options.Perms-Treasures.Epic.Residence", Arrays.asList("Epic_Treasures_Residence"));
		Loader.c.addDefault("Options.Perms-Treasures.Rare.UseResidence", false);
		Loader.c.addDefault("Options.Perms-Treasures.Rare.Residence", Arrays.asList("Rare_Treasures_Residence"));
		Loader.c.addDefault("Options.Perms-Treasures.Common.UseResidence", false);
		Loader.c.addDefault("Options.Perms-Treasures.Common.Residence", Arrays.asList("Common_Treasures_Residence"));
		
		Loader.c.addDefault("Format.TopFisher", "&a%position%. &6%playername%&6: &a%record%cm");
		Loader.c.addDefault("Format.You", "&6You are &a%position%. &6with &a%record%cm");
		Loader.c.addDefault("Format.FishDescription", Arrays.asList("&8-------------------------"
				,"&7Weight: &a%weight%kg","&7Length: &a%length%cm","&7Can be found:"," &a%biomes%"
				,"&7Caught by: &a%fisher%","&8-------------------------"));
		
		Loader.c.addDefault("Format.FishOfDay",
				Arrays.asList("&8-------------------------"
				,"&7Name: &a%fish_name%"
				,"&7Type: &a%fish_type%"
				,"&7Bonus: &a%bonus%x"
				,"&8-------------------------"));
		
		if(!ccc.exists("GUI.Stats")) {
		Loader.c.addDefault("GUI.Stats.1.Name", "&aPlayer");
		Loader.c.addDefault("GUI.Stats.1.Lore", Arrays.asList("&3> &a%playername%"));
		Loader.c.addDefault("GUI.Stats.1.Material", "PLAYER_HEAD");
		
		Loader.c.addDefault("GUI.Stats.2.Name", "&bAmount of catched fish");
		Loader.c.addDefault("GUI.Stats.2.Lore", Arrays.asList("&3> &a%fish_catched%"));
		Loader.c.addDefault("GUI.Stats.2.Material", "COD_BUCKET");
		
		Loader.c.addDefault("GUI.Stats.3.Name", "&4Best fish");
		Loader.c.addDefault("GUI.Stats.3.Lore", Arrays.asList("&3> &aType: &b%fish_type%","&3> &aName: &b%fish_name%","&3> &aRecord: &b%fish_record%"));
		Loader.c.addDefault("GUI.Stats.3.Material", "PAPER");
		
		Loader.c.addDefault("GUI.Stats.4.Name", "&eTournaments");
		Loader.c.addDefault("GUI.Stats.4.Lore", Arrays.asList("&3> &aPlayed: &b%tournament_played%",
				"&3> &aStats:","&3> &aStats:","&3>   &bTop 1: &b%tournament_top1%",
				"&3>   &6Top 2: &b%tournament_top2%","&3>   &eTop 3: &b%tournament_top3%"));
		Loader.c.addDefault("GUI.Stats.4.Material", "DIAMOND");
		}
		Loader.c.addDefault("Tournaments.DefaultLength", "10min");
		Loader.c.addDefault("Tournaments.Length.Name", "&aLength");
		Loader.c.addDefault("Tournaments.Length.Rewards.1", Arrays.asList("eco give %player% 500"));
		Loader.c.addDefault("Tournaments.Length.Rewards.2", Arrays.asList("eco give %player% 250"));
		Loader.c.addDefault("Tournaments.Length.Rewards.3", Arrays.asList("eco give %player% 100"));
		Loader.c.addDefault("Tournaments.Length.Positions", "&6%position%. %playername%&6 with %value%Cm");

		Loader.c.addDefault("Tournaments.Weight.Name", "&6Weight");
		Loader.c.addDefault("Tournaments.Weight.Rewards.1", Arrays.asList("eco give %player% 525"));
		Loader.c.addDefault("Tournaments.Weight.Rewards.2", Arrays.asList("eco give %player% 275"));
		Loader.c.addDefault("Tournaments.Weight.Rewards.3", Arrays.asList("eco give %player% 125"));
		Loader.c.addDefault("Tournaments.Weight.Positions", "&6%position%. %playername%&6 with %value%Kg");

		Loader.c.addDefault("Tournaments.MostCatch.Name", "&3Most Catch");
		Loader.c.addDefault("Tournaments.MostCatch.Rewards.1", Arrays.asList("eco give %player% 400"));
		Loader.c.addDefault("Tournaments.MostCatch.Rewards.2", Arrays.asList("eco give %player% 200"));
		Loader.c.addDefault("Tournaments.MostCatch.Rewards.3", Arrays.asList("eco give %player% 80"));
		Loader.c.addDefault("Tournaments.MostCatch.Positions", "&6%position%. %playername%&6 with %value% fish");
		
		if(!ccc.exists("Types")) {
		Loader.c.addDefault("Types.Cod.0.Name", "&cBarracuda");
		Loader.c.addDefault("Types.Cod.0.Money", 0.5);
		Loader.c.addDefault("Types.Cod.0.Points", 0.1);
		Loader.c.addDefault("Types.Cod.0.Xp", 0.5);
		Loader.c.addDefault("Types.Cod.0.MaxCm", 130.0);
		Loader.c.addDefault("Types.Cod.0.ModelData", 0);
		Loader.c.addDefault("Types.Cod.0.Chance", 33.5);
		Loader.c.addDefault("Types.Cod.0.Lore", Arrays.asList("&8-------------------------"
				,"&7Weight: &a%weight%kg","&7Length: &a%length%cm","&7Can be found:"," &a%biomes%"
				,"&7Caught by: &a%fisher%","&8-------------------------"));
		Loader.c.addDefault("Types.Cod.0.Enchants", Arrays.asList("LURE:3"));
		
		
		Loader.c.addDefault("Types.Cod.1.Name", "&8Roach");
		Loader.c.addDefault("Types.Cod.1.Money", 0.2);
		Loader.c.addDefault("Types.Cod.1.Points", 0.1);
		Loader.c.addDefault("Types.Cod.1.Xp", 0.2);
		Loader.c.addDefault("Types.Cod.1.MaxCm", 60.0);
		Loader.c.addDefault("Types.Cod.1.ModelData", 1);
		Loader.c.addDefault("Types.Cod.1.Chance", 58.3);
		
		Loader.c.addDefault("Types.Cod.2.Name", "&2Common carp");
		Loader.c.addDefault("Types.Cod.2.Money", 0.6);
		Loader.c.addDefault("Types.Cod.2.Points", 0.2);
		Loader.c.addDefault("Types.Cod.2.Xp", 0.4);
		Loader.c.addDefault("Types.Cod.2.MaxCm", 80.0);
		Loader.c.addDefault("Types.Cod.2.ModelData", 2);
		Loader.c.addDefault("Types.Cod.2.Chance", 27.1);
		
		Loader.c.addDefault("Types.Salmon.0.Name", "&cSturgeon");
		Loader.c.addDefault("Types.Salmon.0.Money", 1.1);
		Loader.c.addDefault("Types.Salmon.0.Points", 0.3);
		Loader.c.addDefault("Types.Salmon.0.Xp", 0.9);
		Loader.c.addDefault("Types.Salmon.0.MaxCm", 120.0);
		Loader.c.addDefault("Types.Salmon.0.ModelData", 0);
		Loader.c.addDefault("Types.Salmon.0.Chance", 11.2);
		
		Loader.c.addDefault("Types.Salmon.1.Name", "&8Pike");
		Loader.c.addDefault("Types.Salmon.1.Money", 0.5);
		Loader.c.addDefault("Types.Salmon.1.Points", 0.2);
		Loader.c.addDefault("Types.Salmon.1.Xp", 0.3);
		Loader.c.addDefault("Types.Salmon.1.MaxCm", 70.0);
		Loader.c.addDefault("Types.Salmon.1.ModelData", 1);
		Loader.c.addDefault("Types.Salmon.1.Chance", 35.3);
		
		Loader.c.addDefault("Types.Salmon.2.Name", "&aCommon trout");
		Loader.c.addDefault("Types.Salmon.2.Money", 0.4);
		Loader.c.addDefault("Types.Salmon.2.Points", 0.1);
		Loader.c.addDefault("Types.Salmon.2.Xp", 0.2);
		Loader.c.addDefault("Types.Salmon.2.MaxCm", 60.0);
		Loader.c.addDefault("Types.Salmon.2.ModelData", 2);
		Loader.c.addDefault("Types.Salmon.2.Chance", 48.8);

		Loader.c.addDefault("Types.TropicalFish.0.Name", "&aAngel fish");
		Loader.c.addDefault("Types.TropicalFish.0.Money", 2.6);
		Loader.c.addDefault("Types.TropicalFish.0.Points", 0.8);
		Loader.c.addDefault("Types.TropicalFish.0.Xp", 3);
		Loader.c.addDefault("Types.TropicalFish.0.MaxCm", 25.0);
		Loader.c.addDefault("Types.TropicalFish.0.ModelData", 0);
		Loader.c.addDefault("Types.TropicalFish.0.Chance", 12.4);
		
		Loader.c.addDefault("Types.TropicalFish.1.Name", "&5Arowana fish");
		Loader.c.addDefault("Types.TropicalFish.1.Money", 1.5);
		Loader.c.addDefault("Types.TropicalFish.1.Points", 1.1);
		Loader.c.addDefault("Types.TropicalFish.1.Xp", 0.5);
		Loader.c.addDefault("Types.TropicalFish.1.MaxCm", 35.0);
		Loader.c.addDefault("Types.TropicalFish.1.ModelData", 1);
		Loader.c.addDefault("Types.TropicalFish.1.Chance", 25.9);

		Loader.c.addDefault("Types.TropicalFish.2.Name", "&6Brackish fish");
		Loader.c.addDefault("Types.TropicalFish.2.Money", 1.8);
		Loader.c.addDefault("Types.TropicalFish.2.Points", 0.2);
		Loader.c.addDefault("Types.TropicalFish.2.Xp", 6);
		Loader.c.addDefault("Types.TropicalFish.2.MaxCm", 40.0);
		Loader.c.addDefault("Types.TropicalFish.2.ModelData", 2);
		Loader.c.addDefault("Types.TropicalFish.2.Chance", 33.6);

		Loader.c.addDefault("Types.TropicalFish.3.Name", "&cNemo");
		Loader.c.addDefault("Types.TropicalFish.3.Money", 22.9);
		Loader.c.addDefault("Types.TropicalFish.3.Points", 0.6);
		Loader.c.addDefault("Types.TropicalFish.3.Xp", 1);
		Loader.c.addDefault("Types.TropicalFish.3.MaxCm", 20.0);
		Loader.c.addDefault("Types.TropicalFish.3.ModelData", 3);
		Loader.c.addDefault("Types.TropicalFish.3.Chance", 7.2);
		
		Loader.c.addDefault("Types.PufferFish.0.Name", "&ePufferfish");
		Loader.c.addDefault("Types.PufferFish.0.Money", 0.5);
		Loader.c.addDefault("Types.PufferFish.0.Points", 0.1);
		Loader.c.addDefault("Types.PufferFish.0.Xp", 1);
		Loader.c.addDefault("Types.PufferFish.0.MaxCm", 20.0);
		Loader.c.addDefault("Types.PufferFish.0.ModelData", 0);
		Loader.c.addDefault("Types.PufferFish.0.Chance", 45.9);

		Loader.c.addDefault("Types.PufferFish.1.Name", "&cArothron");
		Loader.c.addDefault("Types.PufferFish.1.Money", 0.6);
		Loader.c.addDefault("Types.PufferFish.1.Points", 0.1);
		Loader.c.addDefault("Types.PufferFish.1.Xp", 1);
		Loader.c.addDefault("Types.PufferFish.1.MaxCm", 45.0);
		Loader.c.addDefault("Types.PufferFish.1.ModelData", 1);
		Loader.c.addDefault("Types.PufferFish.1.Chance", 32.4);
		}
		if(!ccc.exists("Treasures")) {
		Loader.c.addDefault("Treasures.COMMON.0.Name", "&8Old Chest");
		Loader.c.addDefault("Treasures.COMMON.0.Money", 17);
		Loader.c.addDefault("Treasures.COMMON.0.Points", 1.08);
		Loader.c.addDefault("Treasures.COMMON.0.Chance", 86);
		Loader.c.addDefault("Treasures.COMMON.0.Messages", Arrays.asList("&6You found %treasure% &7(Common)"));
		Loader.c.addDefault("Treasures.COMMON.0.Commands", Arrays.asList("give %player% stick 1", "give %player% dirt 4", "give %player% string 1"));
		Loader.c.addDefault("Treasures.COMMON.1.Name", "&6Crate");
		Loader.c.addDefault("Treasures.COMMON.1.Money", 27);
		Loader.c.addDefault("Treasures.COMMON.1.Points", 0.12);
		Loader.c.addDefault("Treasures.COMMON.1.Chance", 14);
		Loader.c.addDefault("Treasures.COMMON.1.Messages", Arrays.asList("&6You found %treasure% &7(Common)"));
		Loader.c.addDefault("Treasures.COMMON.1.Commands", Arrays.asList("give %player% stick 8", "give %player% apple 12", "give %player% string 11"));
		Loader.c.addDefault("Treasures.COMMON.2.Name", "&aTreasure");
		Loader.c.addDefault("Treasures.COMMON.2.Money", 53);
		Loader.c.addDefault("Treasures.COMMON.2.Points", 2.1);
		Loader.c.addDefault("Treasures.COMMON.2.Chance", 10);
		Loader.c.addDefault("Treasures.COMMON.2.Messages", Arrays.asList("&6You found %treasure% &7(Common)"));
		Loader.c.addDefault("Treasures.COMMON.2.Commands", Arrays.asList("give %player% gold_ingot 4", "give %player% fishing_rod 1", "give %player% iron_ingot 2"));

		Loader.c.addDefault("Treasures.RARE.0.Name", "&9Box");
		Loader.c.addDefault("Treasures.RARE.0.Money", 29);
		Loader.c.addDefault("Treasures.RARE.0.Points", 1.6);
		Loader.c.addDefault("Treasures.RARE.0.Chance", 78);
		Loader.c.addDefault("Treasures.RARE.0.Messages", Arrays.asList("&6You found %treasure% &7(Rare)"));
		Loader.c.addDefault("Treasures.RARE.0.Commands", Arrays.asList("give %player% iron_nugget 2"));
		Loader.c.addDefault("Treasures.RARE.1.Name", "&1Treasure");
		Loader.c.addDefault("Treasures.RARE.1.Money", 77);
		Loader.c.addDefault("Treasures.RARE.1.Points", 3.4);
		Loader.c.addDefault("Treasures.RARE.1.Chance", 22);
		Loader.c.addDefault("Treasures.RARE.1.Messages", Arrays.asList("&6You found %treasure% &7(Rare)"));
		Loader.c.addDefault("Treasures.RARE.1.Commands", Arrays.asList("give %player% diamond 1", "give %player% gold_nugget 4"));

		Loader.c.addDefault("Treasures.EPIC.0.Name", "&cJewelry");
		Loader.c.addDefault("Treasures.EPIC.0.Money", 175);
		Loader.c.addDefault("Treasures.EPIC.0.Points", 3.8);
		Loader.c.addDefault("Treasures.EPIC.0.Chance", 89);
		Loader.c.addDefault("Treasures.EPIC.0.Messages", Arrays.asList("&6You found %treasure% &7(Epic)"));
		Loader.c.addDefault("Treasures.EPIC.0.Commands", Arrays.asList("give %player% gold_nugget 15", "give %player% diamond 3"));
		Loader.c.addDefault("Treasures.EPIC.1.Name", "&cTreasure");
		Loader.c.addDefault("Treasures.EPIC.1.Money", 121);
		Loader.c.addDefault("Treasures.EPIC.1.Points", 9.74);
		Loader.c.addDefault("Treasures.EPIC.1.Chance", 11);
		Loader.c.addDefault("Treasures.EPIC.1.Messages", Arrays.asList("&6You found %treasure% &7(Epic)"));
		Loader.c.addDefault("Treasures.EPIC.1.Commands", Arrays.asList("give %player% diamond 7","give %player% iron_ingot 4","give %player% cobweb 1","give %player% string 3"));

		Loader.c.addDefault("Treasures.LEGENDARY.0.Name", "&cSafe");
		Loader.c.addDefault("Treasures.LEGENDARY.0.Money", 33);
		Loader.c.addDefault("Treasures.LEGENDARY.0.Points", 2.41);
		Loader.c.addDefault("Treasures.LEGENDARY.0.Chance", 92);
		Loader.c.addDefault("Treasures.LEGENDARY.0.Messages", Arrays.asList("&6You found %treasure% &7(Legendary)"));
		Loader.c.addDefault("Treasures.LEGENDARY.0.Commands", Arrays.asList("give %player% diamond 17","give %player% enchanted_golden_apple 5"));
		Loader.c.addDefault("Treasures.LEGENDARY.1.Name", "&4Lost Treasure");
		Loader.c.addDefault("Treasures.LEGENDARY.1.Money", 358);
		Loader.c.addDefault("Treasures.LEGENDARY.1.Points", 80.16);
		Loader.c.addDefault("Treasures.LEGENDARY.1.Chance", 8);
		Loader.c.addDefault("Treasures.LEGENDARY.1.Messages", Arrays.asList("&6You found %treasure% &7(Legendary)"));
		Loader.c.addDefault("Treasures.LEGENDARY.1.Commands", Arrays.asList("give %player% diamond_block 3","give %player% gold_ingot 27"
				,"give %player% golden_apple 11","give %player% cobweb 2"));
		}
		if(!ccc.exists("Quests")) {
			Loader.c.addDefault("Quests.0.Name", "&aJohny is hungry (Easy)");
			Loader.c.addDefault("Quests.0.Description", "&6Catch 5x Roach, 2x Barracuda..");
			Loader.c.addDefault("Quests.0.Stage.0.Action", "CATCH_FISH");
			Loader.c.addDefault("Quests.0.Stage.0.Fish", 1);
			Loader.c.addDefault("Quests.0.Stage.0.Type", "Cod");
			Loader.c.addDefault("Quests.0.Stage.0.Amount", 5);

			Loader.c.addDefault("Quests.0.Stage.1.Action", "SELL_FISH");
			Loader.c.addDefault("Quests.0.Stage.1.Fish", 1);
			Loader.c.addDefault("Quests.0.Stage.1.Type", "Cod");
			Loader.c.addDefault("Quests.0.Stage.1.Amount", 2);

			Loader.c.addDefault("Quests.0.Stage.2.Action", "CATCH_FISH");
			Loader.c.addDefault("Quests.0.Stage.2.Fish", 0);
			Loader.c.addDefault("Quests.0.Stage.2.Type", "Cod");
			Loader.c.addDefault("Quests.0.Stage.2.Amount", 2);
			Loader.c.addDefault("Quests.0.Stage.3.Action", "SELL_FISH");
			Loader.c.addDefault("Quests.0.Stage.3.Fish", 0);
			Loader.c.addDefault("Quests.0.Stage.3.Type", "Cod");
			Loader.c.addDefault("Quests.0.Stage.3.Amount", 2);
			Loader.c.addDefault("Quests.0.Rewards.Commands", Arrays.asList("eco give %player% 500", "give %player% iron_ingot 10"));
			Loader.c.addDefault("Quests.0.Rewards.Messages", Arrays.asList("&aCongratulation %player% !", "&aYou completed quest &c%quest% &a!"));

			Loader.c.addDefault("Quests.1.Name", "&aStraiker123's challenge (Hard)");
			Loader.c.addDefault("Quests.1.Time", "7d");
			Loader.c.addDefault("Quests.1.Description", "&67 Days to complete, Catch 20x Brackish fish..");
			
			Loader.c.addDefault("Quests.1.Stage.0.Action", "CATCH_FISH");
			Loader.c.addDefault("Quests.1.Stage.0.Fish", 2);
			Loader.c.addDefault("Quests.1.Stage.0.Type", "TropicalFish");
			Loader.c.addDefault("Quests.1.Stage.0.Amount", 20);

			Loader.c.addDefault("Quests.1.Stage.1.Action", "CATCH_FISH");
			Loader.c.addDefault("Quests.1.Stage.1.Fish", 1);
			Loader.c.addDefault("Quests.1.Stage.1.Type", "PufferFish");
			Loader.c.addDefault("Quests.1.Stage.1.Amount", 15);

			Loader.c.addDefault("Quests.1.Stage.2.Action", "CATCH_FISH");
			Loader.c.addDefault("Quests.1.Stage.2.Fish", 1);
			Loader.c.addDefault("Quests.1.Stage.2.Type", "Cod");
			Loader.c.addDefault("Quests.1.Stage.2.Amount", 25);
			Loader.c.addDefault("Quests.1.Rewards.Commands", Arrays.asList("eco give %player% 2500", "give %player% diamond 5"));
			Loader.c.addDefault("Quests.1.Rewards.Messages", Arrays.asList("&aCongratulation %player% !", "&aYou completed quest &c%quest% &a!"));
			
			
		}
		if(!ccc.exists("Enchants")) {
		Loader.c.addDefault("Enchants.0.Name", "&2Fortune");
		Loader.c.addDefault("Enchants.0.AmountBonus", 0.67);
		Loader.c.addDefault("Enchants.0.MoneyBonus", 0.39);
		Loader.c.addDefault("Enchants.0.PointsBonus", 0.11);
		Loader.c.addDefault("Enchants.0.ExpBonus", 0.23);
		Loader.c.addDefault("Enchants.0.Cost", 65);
		Loader.c.addDefault("Enchants.0.Description", Arrays.asList("&7Most increases amount and money bonus"," &7- Cost: %cost% Points"));
		Loader.c.addDefault("Enchants.1.Name", "&cDouble hook");
		Loader.c.addDefault("Enchants.1.AmountBonus", 0.21);
		Loader.c.addDefault("Enchants.1.MoneyBonus", 0.52);
		Loader.c.addDefault("Enchants.1.PointsBonus", 0.33);
		Loader.c.addDefault("Enchants.1.ExpBonus", 0.32);
		Loader.c.addDefault("Enchants.1.Cost", 45);
		Loader.c.addDefault("Enchants.1.Description", Arrays.asList("&7Increases money, points and exp bonus","&7From caught fish"," &7- Cost: %cost% Points"));
		Loader.c.addDefault("Enchants.2.Name", "&3Stronger line");
		Loader.c.addDefault("Enchants.2.AmountBonus", 0.12);
		Loader.c.addDefault("Enchants.2.MoneyBonus", 0.24);
		Loader.c.addDefault("Enchants.2.PointsBonus", 0.68);
		Loader.c.addDefault("Enchants.2.ExpBonus", 0.53);
		Loader.c.addDefault("Enchants.2.Cost", 35);
		Loader.c.addDefault("Enchants.2.Description", Arrays.asList("&7Increases points and exp bonus","&7From caught fish"," &7- Cost: %cost% Points"));
		Loader.c.addDefault("Enchants.3.Name", "&eLength of the fishing rod");
		Loader.c.addDefault("Enchants.3.AmountBonus", 0.15);
		Loader.c.addDefault("Enchants.3.MoneyBonus", 0.15);
		Loader.c.addDefault("Enchants.3.PointsBonus", 0.15);
		Loader.c.addDefault("Enchants.3.ExpBonus", 0.15);
		Loader.c.addDefault("Enchants.3.Cost", 50);
		Loader.c.addDefault("Enchants.3.Description", Arrays.asList("&7Increases all bonuses by 0.15%"," &7- Cost: %cost% Points"));
		Loader.c.addDefault("Enchants.4.Name", "&9Convenience");
		Loader.c.addDefault("Enchants.4.AmountBonus", 0.18);
		Loader.c.addDefault("Enchants.4.MoneyBonus", 0.22);
		Loader.c.addDefault("Enchants.4.PointsBonus", 0.25);
		Loader.c.addDefault("Enchants.4.ExpBonus", 0.34);
		Loader.c.addDefault("Enchants.4.Cost", 30);
		Loader.c.addDefault("Enchants.4.Description", Arrays.asList("&7Increases exp bonus","&7From caught fish"," &7- Cost: %cost% Points"));
		}
		Loader.c.save();
	}
	
	static void setupTranslations() {
		//TranslationsFile = Loader.getConfig("AmazingFishing", "Translations");
		Loader.TranslationsFile= new Config("AmazingFishing/Translations.yml");
		Loader.TranslationsFile.addDefault("CommandIsDisabled", "&cCommand is disabled!");
		Loader.TranslationsFile.addDefault("TopPlayers", "&6Top 3 players records on fish &a%fish%");
		Loader.TranslationsFile.addDefault("Prefix", "&bFishing &4» &6");
		Loader.TranslationsFile.addDefault("Stats", Arrays.asList("&6------< &b%playername% &6>------"
				,"&b> &6Caught fish: &a%fish%","&b> &6Longest fish & name: &a%fish%: %record%"
				,"&b> &6Participating tournaments: &a%tournaments%","&b> &6Top 1 in participating tournaments: &a%top1% times"));
		Loader.TranslationsFile.addDefault("ConfigReloaded", "&2Config reloaded!");
		Loader.TranslationsFile.addDefault("SoldFish", "&eSold %amount% fish for %money%$, %exp%exp and %points% points");
		Loader.TranslationsFile.addDefault("MissingData", "&cMissing data, First you must caught fish");
		Loader.TranslationsFile.addDefault("CommandIsDisabled", "&cCommand is disabled!");
		Loader.TranslationsFile.addDefault("Biome-Added", "&aBiome added");
		Loader.TranslationsFile.addDefault("ConsoleErrorMessage", "&cCommand can be used only in-game!");
		Loader.TranslationsFile.addDefault("NoPermissions", "&cYou don't have permission '%permission%' to do that!");
		Loader.TranslationsFile.addDefault("AFK-Title.1", "&6Please move");
		Loader.TranslationsFile.addDefault("AFK-Title.2", "&6To another location");
		Loader.TranslationsFile.addDefault("Help.Reload", "&5Reload config");
		Loader.TranslationsFile.addDefault("Help.Stats.My", "&5Your fishing stats");
		Loader.TranslationsFile.addDefault("Help.Stats.Other", "&5Fishing stats of specified player");
		Loader.TranslationsFile.addDefault("Help.Tournament", "&5Start new tournament for time");
		Loader.TranslationsFile.addDefault("Help.Tournament_Stop", "&cStop running tournament");
		Loader.TranslationsFile.addDefault("Help.Editor", "&5Open virtual editor for plugin settings/fish and more");
		Loader.TranslationsFile.addDefault("Help.List", "&5List of fish");
		Loader.TranslationsFile.addDefault("Help.Points", "&5Manager of player points");
		Loader.TranslationsFile.addDefault("Help.Points-Give", "&5Give player specified amount of points");
		Loader.TranslationsFile.addDefault("Help.Points-Take", "&5Take from player specified amount of points");
		Loader.TranslationsFile.addDefault("Help.Points-Set", "&5Set specified amount of player points");
		Loader.TranslationsFile.addDefault("Help.Points-Balance", "&5Points balance of player");
		Loader.TranslationsFile.addDefault("Help.Toggle", "&5Toggle new record message");
		Loader.TranslationsFile.addDefault("Help.Record", "&5Your record of specified fish");
		Loader.TranslationsFile.addDefault("Help.Top", "&5Top 3 players of specified fish");
		Loader.TranslationsFile.addDefault("Help.Bag", "&5Open your fish bag");
		Loader.TranslationsFile.addDefault("Help.Shop", "&5Open shop");
		
		Loader.TranslationsFile.addDefault("Help.Enchants", "&5Open enchant table for your fishing rod");
		Loader.TranslationsFile.addDefault("Help.Reload", "&5Reload config");
		Loader.TranslationsFile.addDefault("Editor.MissingCrateName.1", "&6Missing");
		Loader.TranslationsFile.addDefault("Editor.MissingCrateName.2", "&6Name of treasure!");
		Loader.TranslationsFile.addDefault("Editor.SuccefullyCreatedCrate.1", "&6Treasure &a%treasure%");
		Loader.TranslationsFile.addDefault("Editor.SuccefullyCreatedCrate.2", "&6Succefully created!");
		Loader.TranslationsFile.addDefault("Editor.WriteCommand.1", "&6Write new command");
		Loader.TranslationsFile.addDefault("Editor.WriteCommand.2", "&6To the chat.");
		Loader.TranslationsFile.addDefault("Editor.WriteMoney.1", "&6Write new money amount");
		Loader.TranslationsFile.addDefault("Editor.WriteMoney.2", "&6To the chat.");
		Loader.TranslationsFile.addDefault("Editor.WriteExp.1", "&6Write new experiences amount");
		Loader.TranslationsFile.addDefault("Editor.WriteExp.2", "&6To the chat.");
		Loader.TranslationsFile.addDefault("Editor.WritePoint.1", "&6Write new points amount");
		Loader.TranslationsFile.addDefault("Editor.WritePoint.2", "&6To the chat.");
		Loader.TranslationsFile.addDefault("Editor.NewMessage.1", "&6Write new message");
		Loader.TranslationsFile.addDefault("Editor.NewMessage.2", "&6To the chat.");
		Loader.TranslationsFile.addDefault("Editor.WriteName.1", "&6Write new name");
		Loader.TranslationsFile.addDefault("Editor.WriteName.2", "&6To the chat.");
		Loader.TranslationsFile.addDefault("Editor.WriteCost.1", "&6Write cost of enchant");
		Loader.TranslationsFile.addDefault("Editor.WriteCost.2", "&6To the chat.");
		
		Loader.TranslationsFile.addDefault("Editor.WriteChance.1", "&6Write new chance in percentage");
		Loader.TranslationsFile.addDefault("Editor.WriteChance.2", "&6To the chat.");
		Loader.TranslationsFile.addDefault("Editor.WriteLength.1", "&6Write new max length of fish");
		Loader.TranslationsFile.addDefault("Editor.WriteLength.2", "&6To the chat.");
		Loader.TranslationsFile.addDefault("Editor.WriteExpBonus.1", "&6Write exp bonus in percentage");
		Loader.TranslationsFile.addDefault("Editor.WriteExpBonus.2", "&6To the chat.");
		Loader.TranslationsFile.addDefault("Editor.WriteAmount.1", "&6Write fish chance amount bonus");
		Loader.TranslationsFile.addDefault("Editor.WriteAmount.2", "&6To the chat.");
		Loader.TranslationsFile.addDefault("Editor.SuccefullyCreatedEnchant.1", "&6Enchant %enchant%");
		Loader.TranslationsFile.addDefault("Editor.SuccefullyCreatedEnchant.2", "&6Succefully created!");
		Loader.TranslationsFile.addDefault("Editor.MissingEnchantName.1", "&6Missing");
		Loader.TranslationsFile.addDefault("Editor.MissingEnchantName.2", "&6Name of enchant!");
		
		Loader.TranslationsFile.addDefault("Editor.WriteMoneyBonus.1", "&6Write money bonus in percentage");
		Loader.TranslationsFile.addDefault("Editor.WriteMoneyBonus.2", "&6To the chat."); 
		Loader.TranslationsFile.addDefault("Editor.WritePointsBonus.1", "&6Write points bonus in percentage");
		Loader.TranslationsFile.addDefault("Editor.WritePointsBonus.2", "&6To the chat.");
		Loader.TranslationsFile.addDefault("Editor.NewDescription.1", "&6Write new description message");
		Loader.TranslationsFile.addDefault("Editor.NewDescription.2", "&6To the chat.");
		
		Loader.TranslationsFile.addDefault("Editor.Saved.1", "&6Saved!");
		Loader.TranslationsFile.addDefault("Editor.Saved.2", "");
		
		Loader.TranslationsFile.addDefault("Editor.MissingFishName.1", "&6Missing");
		Loader.TranslationsFile.addDefault("Editor.MissingFishName.2", "&6Name of fish!");
		Loader.TranslationsFile.addDefault("Editor.SuccefullyCreated.1", "&6Fish &a%fish%");
		Loader.TranslationsFile.addDefault("Editor.SuccefullyCreated.2", "&6Succefully created!");
		Loader.TranslationsFile.addDefault("ReachNewRecord", "&6You reach new record on fish &a%fish%&6, Last record was &a%last%cm&6, New record &a%record%cm");
		Loader.TranslationsFile.addDefault("RecordOnFish", "&6Your record on fish &a%fish%&6 is &a%record%cm");
		Loader.TranslationsFile.addDefault("NeverCaught", "&6You never caught &c%fish%");
		Loader.TranslationsFile.addDefault("Caught", "&6You caught &a%fish% &6with &a%cm%cm &7(%weight%kg)");
		Loader.TranslationsFile.addDefault("TournamentTypes", "&6Tournament Types:");
		Loader.TranslationsFile.addDefault("ListFish", "&6List of fish:");

		Loader.TranslationsFile.addDefault("HelpGUI.Record.Want", "&aI want receive record reached message");
		Loader.TranslationsFile.addDefault("HelpGUI.Record.DoNotWant", "&cI don't want receive record reached message");

		Loader.TranslationsFile.addDefault("HelpGUI.Record.Receive", "&aYou will receive a new record message");
		Loader.TranslationsFile.addDefault("HelpGUI.ClickToStart", "&aClick to start");

		Loader.TranslationsFile.addDefault("HelpGUI.Record.NoLongerReceive", "&cYou will no longer receive a new record messagee");
		Loader.TranslationsFile.addDefault("Winners", "&6Tournament winners of type &0'%type%&0'&6:");
		Loader.TranslationsFile.addDefault("Stopped", "&cStopped tournament type &0'%type%&0' &6on %time%");
		Loader.TranslationsFile.addDefault("Started", "&aStarted tournament type &0'%type%&0' &6on %time%");
		Loader.TranslationsFile.addDefault("Running", "&6Running tournament type &0'%type%&0' &6on %time%");
		
		Loader.TranslationsFile.addDefault("Words.Pufferfish", "&ePufferfish");
		Loader.TranslationsFile.addDefault("Words.Salmon", "&4Salmon");
		Loader.TranslationsFile.addDefault("Words.Cod", "&8Cod");
		Loader.TranslationsFile.addDefault("Words.Tropical_Fish", "&cTropical Fish");
		Loader.TranslationsFile.addDefault("Words.FishRemove", "&cRemove normal fish on catch custom fish");

		Loader.TranslationsFile.addDefault("Words.Bag_Title", "&bFish Bag");
		Loader.TranslationsFile.addDefault("Words.Cost", "&6Cost");
		Loader.TranslationsFile.addDefault("Words.MoneyBonus", "&6Money Bonus");
		Loader.TranslationsFile.addDefault("Words.PointsBonus", "&9Points Bonus");
		Loader.TranslationsFile.addDefault("Words.Description", "&3Description");
		Loader.TranslationsFile.addDefault("Words.Amount", "&aAmount Bonus");
		Loader.TranslationsFile.addDefault("Words.Exp", "&9Experiences Bonus");
		Loader.TranslationsFile.addDefault("Words.Help.Top", "&bTop 3 players records");
		Loader.TranslationsFile.addDefault("Words.Help.Record", "&cYour records");
		Loader.TranslationsFile.addDefault("Words.Help.Toggle", "&3Toggle new record reached message");
		Loader.TranslationsFile.addDefault("Words.Help.Editor", "&6Editor of Fish and Settings");
		Loader.TranslationsFile.addDefault("Words.Help.List", "&aList of fish");
		Loader.TranslationsFile.addDefault("Words.Help.Tournament", "&bStart new tournament");
		Loader.TranslationsFile.addDefault("Words.PerBiome", "&bPer Biome");
		
		Loader.TranslationsFile.addDefault("Words.Help.Enchant", "&5Enchant table for your rod");
		Loader.TranslationsFile.addDefault("Words.Help.Shop", "&dShop");
		Loader.TranslationsFile.addDefault("Words.Help.Reload", "&2Reload config");
		Loader.TranslationsFile.addDefault("Words.Help.Admin", "&7Switch to admin section");
		Loader.TranslationsFile.addDefault("Words.Help.Player", "&7Switch to player section");
		Loader.TranslationsFile.addDefault("Words.Help.Points", "&9Manager of player points");
		Loader.TranslationsFile.addDefault("Words.Help.Stats.My", "&cStats about you");
		Loader.TranslationsFile.addDefault("Words.Help.Bag", "&5Open fish bag");
		
		Loader.TranslationsFile.addDefault("Words.Help.Stats.Other", "&cStats about specified player");
		
		Loader.TranslationsFile.addDefault("Words.Create", "&2Create");
		Loader.TranslationsFile.addDefault("Words.Delete", "&4Delete");
		Loader.TranslationsFile.addDefault("Words.Edit", "&6Edit");
		Loader.TranslationsFile.addDefault("Words.Deleted", "&4DELETED");
		Loader.TranslationsFile.addDefault("Words.Money", "&6Money");
		Loader.TranslationsFile.addDefault("Words.MaxCm", "&3Max length");
		Loader.TranslationsFile.addDefault("Words.Cancel", "&cCancel");
		Loader.TranslationsFile.addDefault("Words.Back", "&4Back");
		Loader.TranslationsFile.addDefault("Words.Bag", "&eBag");
		

		Loader.TranslationsFile.addDefault("Words.SelectEnchants", "&5Enchants editor");
		Loader.TranslationsFile.addDefault("Words.SelectFish", "&6Fish editor");
		Loader.TranslationsFile.addDefault("Words.SelectTreasures", "&bTreasures editor");
		Loader.TranslationsFile.addDefault("Words.SelectSettings", "&7Settings editor");
		Loader.TranslationsFile.addDefault("Words.Fish", "&7Fish");
		Loader.TranslationsFile.addDefault("Words.FishOfDay", "&aToday's bonus fish");
		
		Loader.TranslationsFile.addDefault("Words.Commands", "&dCommands");
		Loader.TranslationsFile.addDefault("Words.Messages", "&rMessages");
		Loader.TranslationsFile.addDefault("Words.Chance", "&eChance");
		Loader.TranslationsFile.addDefault("Words.Sell_Fish", "&6Sell fish");
		
		Loader.TranslationsFile.addDefault("Words.Common", "&7Common");
		Loader.TranslationsFile.addDefault("Words.Rare", "&1Rare");
		Loader.TranslationsFile.addDefault("Words.Epic", "&6Epic");
		Loader.TranslationsFile.addDefault("Words.Legendary", "&4Legendary");
		Loader.TranslationsFile.addDefault("Words.NowPoints-Give", "&2Give points");
		Loader.TranslationsFile.addDefault("Words.NowPoints-Take", "&cTake points");
		Loader.TranslationsFile.addDefault("Words.NowPoints-Set", "&6Set points");

		Loader.TranslationsFile.addDefault("Words.Biomes.ALL.Name", "&6ALL");
		Loader.TranslationsFile.addDefault("Words.Biomes.ALL.Icon", "CHEST");
		Loader.TranslationsFile.addDefault("Words.Biomes.BADLANDS.Name", "&7BADLANDS");
		Loader.TranslationsFile.addDefault("Words.Biomes.BADLANDS.Icon", "RED_SAND");
		Loader.TranslationsFile.addDefault("Words.Biomes.BEACH.Name", "&eBEACH");
		Loader.TranslationsFile.addDefault("Words.Biomes.BEACH.Icon", "SAND");
		Loader.TranslationsFile.addDefault("Words.Biomes.MUSHROOM.Name", "&dMUSHROOMS FIELD");
		Loader.TranslationsFile.addDefault("Words.Biomes.MUSHROOM.Icon", "MYCELIUM");
		
		

		Loader.TranslationsFile.addDefault("Words.Biomes.NETHER.Name", "&4NETHER");
		Loader.TranslationsFile.addDefault("Words.Biomes.NETHER.Icon", "LAVA_BUCKET");

		Loader.TranslationsFile.addDefault("Words.Biomes.END.Name", "&5THE END");
		Loader.TranslationsFile.addDefault("Words.Biomes.END.Icon", "ENDER_PEARL");

		Loader.TranslationsFile.addDefault("Words.Biomes.TUNDRA.Name", "&9TUNDRA");
		Loader.TranslationsFile.addDefault("Words.Biomes.TUNDRA.Icon", "SNOW");

		Loader.TranslationsFile.addDefault("Words.Biomes.WOODED_HILLS.Name", "&2WOODED HILLS");
		Loader.TranslationsFile.addDefault("Words.Biomes.WOODED_HILLS.Icon", "OAK_LOG");

		Loader.TranslationsFile.addDefault("Words.Biomes.STONE_SHORE.Name", "&7STONE SHORE");
		Loader.TranslationsFile.addDefault("Words.Biomes.STONE_SHORE.Icon", "STONE");

		Loader.TranslationsFile.addDefault("Words.Biomes.SAVANNA.Name", "&6SAVANNA");
		Loader.TranslationsFile.addDefault("Words.Biomes.SAVANNA.Icon", "SAND");
		
		Loader.TranslationsFile.addDefault("Words.Biomes.COLD_OCEAN.Name", "&1COLD OCEAN");
		Loader.TranslationsFile.addDefault("Words.Biomes.COLD_OCEAN.Icon", "WATER_BUCKET");
		Loader.TranslationsFile.addDefault("Words.Biomes.DEEP_COLD_OCEAN.Name", "&9DEEP COLD OCEAN");
		Loader.TranslationsFile.addDefault("Words.Biomes.DEEP_COLD_OCEAN.Icon", "WATER_BUCKET");
		Loader.TranslationsFile.addDefault("Words.Biomes.DEEP_FROZEN_OCEAN.Name", "&1DEEP FROZEN OCEAN");
		Loader.TranslationsFile.addDefault("Words.Biomes.DEEP_FROZEN_OCEAN.Icon", "ICE");
		Loader.TranslationsFile.addDefault("Words.Biomes.DEEP_LUKEWARM_OCEAN.Name", "&cDEEP LUKEWARM OCEAN");
		Loader.TranslationsFile.addDefault("Words.Biomes.DEEP_LUKEWARM_OCEAN.Icon", "FIRE_CORAL");
		Loader.TranslationsFile.addDefault("Words.Biomes.DEEP_WARM_OCEAN.Name", "&cDEEP WARM OCEAN");
		Loader.TranslationsFile.addDefault("Words.Biomes.DEEP_WARM_OCEAN.Icon", "FIRE_CORAL");
		Loader.TranslationsFile.addDefault("Words.Biomes.DEEP_OCEAN.Name", "&9DEEP OCEAN");
		Loader.TranslationsFile.addDefault("Words.Biomes.DEEP_OCEAN.Icon", "WATER_BUCKET");
		Loader.TranslationsFile.addDefault("Words.Biomes.DESERT.Name", "&eDESERT");
		Loader.TranslationsFile.addDefault("Words.Biomes.DESERT.Icon", "CACTUS");
		Loader.TranslationsFile.addDefault("Words.Biomes.FOREST.Name", "&2FOREST");
		Loader.TranslationsFile.addDefault("Words.Biomes.FOREST.Icon", "OAK_SAPLING");
		Loader.TranslationsFile.addDefault("Words.Biomes.FROZEN_OCEAN.Name", "&1FROZEN OCEAN");
		Loader.TranslationsFile.addDefault("Words.Biomes.FROZEN_OCEAN.Icon", "ICE");
		Loader.TranslationsFile.addDefault("Words.Biomes.ICE_SPIKES.Name", "&1ICE SPIKES");
		Loader.TranslationsFile.addDefault("Words.Biomes.ICE_SPIKES.Icon", "PACKED_ICE");
		Loader.TranslationsFile.addDefault("Words.Biomes.JUNGLE.Name", "&aJUNGLE");
		Loader.TranslationsFile.addDefault("Words.Biomes.JUNGLE.Icon", "JUNGLE_SAPLING");
		Loader.TranslationsFile.addDefault("Words.Biomes.MOUNTAINS.Name", "&7MOUNTAINS");
		Loader.TranslationsFile.addDefault("Words.Biomes.MOUNTAINS.Icon", "STONE");
		Loader.TranslationsFile.addDefault("Words.Biomes.TAIGA.Name", "&2TAIGA");
		Loader.TranslationsFile.addDefault("Words.Biomes.TAIGA.Icon", "SPRUCE_SAPLING");
		Loader.TranslationsFile.addDefault("Words.Biomes.WARM_OCEAN.Name", "&cWARM OCEAN");
		Loader.TranslationsFile.addDefault("Words.Biomes.WARM_OCEAN.Icon", "FIRE_CORAL");
		Loader.TranslationsFile.addDefault("Words.Biomes.PLAINS.Name", "&aPLAINS");
		Loader.TranslationsFile.addDefault("Words.Biomes.PLAINS.Icon", "GRASS_BLOCK");
		Loader.TranslationsFile.addDefault("Words.Biomes.RIVER.Name", "&9RIVER");
		Loader.TranslationsFile.addDefault("Words.Biomes.RIVER.Icon", "DIRT");
		Loader.TranslationsFile.addDefault("Words.Biomes.SWAMP.Name", "&2SWAMP");
		Loader.TranslationsFile.addDefault("Words.Biomes.SWAMP.Icon", "SLIME_BALL");
		
		
		Loader.TranslationsFile.addDefault("Words.Close", "&cClose");
		Loader.TranslationsFile.addDefault("Words.Save", "&2Save");
		Loader.TranslationsFile.addDefault("Words.ShopLogo", "&6Fishing Shop");
		Loader.TranslationsFile.addDefault("Words.Shop_Sell_Title", "&6Fishing Shop Sell Section");
		
		Loader.TranslationsFile.addDefault("Words.EverythingByCm", "&6Earnings from fish length");
		Loader.TranslationsFile.addDefault("Words.Enabled", "&aEnabled");
		Loader.TranslationsFile.addDefault("Words.Disabled", "&cDisabled");
		Loader.TranslationsFile.addDefault("Words.Shop", "&6Custom plugin shop");
		Loader.TranslationsFile.addDefault("Words.HasPoints", "&9%points% points");
		Loader.TranslationsFile.addDefault("Words.Enchants", "&6Custom fishing rod enchants");
		Loader.TranslationsFile.addDefault("Words.Treasures", "&6Custom treasures");
		Loader.TranslationsFile.addDefault("Words.TreasuresTitle", "&bTreaures");
		Loader.TranslationsFile.addDefault("Words.Points", "&dPoints");
		Loader.TranslationsFile.addDefault("Words.Name", "&bName");
		Loader.TranslationsFile.addDefault("Words.Experiences", "&9Experiences");
		Loader.TranslationsFile.addDefault("Toggled.true", "&6Notifications about new records will be no longer shows");
		Loader.TranslationsFile.addDefault("Toggled.false", "&6Notifications about new records now will be showing");
		Loader.TranslationsFile.save();
	}
	
	public static void loadShop() {

		//shop = Loader.getConfig("AmazingFishing","AmazingFishing-Shop.yml");
		Loader.shop= new Config("AmazingFishing/AmazingFishing-Shop.yml");
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
		if(!Loader.getConfig("AmazingFishing", "AmazingFishing-Shop.yml").exists("Items")) {
			Loader.shop.addDefault("Items.0.Icon", "STONE_SWORD");
			Loader.shop.addDefault("Items.0.Name", "&7&lBeginner kit");
			Loader.shop.addDefault("Items.0.Description", Arrays.asList("&eCost %cost% points"));
			Loader.shop.addDefault("Items.0.Cost", 50);
			
			Loader.shop.addDefault("Items.0.Messages", Arrays.asList("&6You bought a &7&lBeginner kit&r &6for &a%cost% points"));
			
			Loader.shop.addDefault("Items.0.Item.0.Material", "Fishing_rod");
			Loader.shop.addDefault("Items.0.Item.0.Name", "&7Basic fishing rod");
			Loader.shop.addDefault("Items.0.Item.0.ModelData", "0");
			Loader.shop.addDefault("Items.0.Item.0.Amount", 1);
			Loader.shop.addDefault("Items.0.Item.0.HideEnchants", true);
			Loader.shop.addDefault("Items.0.Item.0.Enchants", Arrays.asList("Unbreaking 1"));
			
			Loader.shop.addDefault("Items.0.Item.1.Material", "Oak_Boat");
			Loader.shop.addDefault("Items.0.Item.1.ModelData", "1");
			Loader.shop.addDefault("Items.0.Item.1.HideAttributes", true);
			Loader.shop.addDefault("Items.0.Item.1.Unbreakable", true); //unused, but why not
			
			Loader.shop.addDefault("Items.0.Item.2.Material", "Apple");
			Loader.shop.addDefault("Items.0.Item.2.ModelData", "2");
			Loader.shop.addDefault("Items.0.Item.2.Name", "&cTasty apple");
			Loader.shop.addDefault("Items.0.Item.2.Amount", 3);
			Loader.shop.addDefault("Items.0.Item.2.Lore", Arrays.asList("&4&lEAT ME"));

			
			Loader.shop.addDefault("Items.1.Icon", "IRON_SWORD");
			Loader.shop.addDefault("Items.1.Name", "&e&lIron kit");
			Loader.shop.addDefault("Items.1.Description", Arrays.asList("&eCost %cost% points"));
			Loader.shop.addDefault("Items.1.Cost", 275);
			
			Loader.shop.addDefault("Items.1.Messages", Arrays.asList("&6You bought a &e&lIron kit&r &6for &a%cost% points"));
			Loader.shop.addDefault("Items.1.Commands", Arrays.asList("heal %player%"));
			
			Loader.shop.addDefault("Items.1.Item.0.Material", "Fishing_rod");
			Loader.shop.addDefault("Items.1.Item.0.ModelData", "3");
			Loader.shop.addDefault("Items.1.Item.0.Name", "&bPerfect fishing rod");
			Loader.shop.addDefault("Items.1.Item.0.Lore", Arrays.asList("&3Fishing 4 ever! <3"));
			Loader.shop.addDefault("Items.1.Item.0.Amount", 1);
			Loader.shop.addDefault("Items.1.Item.0.HideEnchants", true);
			Loader.shop.addDefault("Items.1.Item.0.Enchants", Arrays.asList("Unbreaking 4","Luck_of_sea 2"));
			
			Loader.shop.addDefault("Items.1.Item.1.Material", "Oak_Boat");
			Loader.shop.addDefault("Items.1.Item.1.Amount", 2);
			Loader.shop.addDefault("Items.1.Item.1.ModelData", "4");
			Loader.shop.addDefault("Items.1.Item.1.HideAttributes", true);
			Loader.shop.addDefault("Items.1.Item.1.Unbreakable", true); //unused, but why not
			
			Loader.shop.addDefault("Items.1.Item.2.Material", "Golden_Apple");
			Loader.shop.addDefault("Items.1.Item.2.Name", "&dSpecial apple");
			Loader.shop.addDefault("Items.1.Item.2.ModelData", "5");
			Loader.shop.addDefault("Items.1.Item.2.Lore", Arrays.asList("&5Really special!"));
			Loader.shop.addDefault("Items.1.Item.2.Amount", 7);
		}
		Loader.shop.setHeader(Arrays.asList("%player%   select player\n", "%coins%   amount of coins\n"));
		Loader.shop.save();
	}
	
	
	public static void LoadConfigs() {
		setupConfig();
		setupTranslations();
		loadShop();
	}
	public static void reload() {
		Loader.c.reload();
		Loader.TranslationsFile.reload();
		Loader.shop.reload();
		Loader.me.reload();
	}

	
}

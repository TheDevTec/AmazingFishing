package AmazingFishing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.DevTec.ConfigAPI;
import me.DevTec.EnchantmentAPI;
import me.DevTec.TheAPI;

public class Loader extends JavaPlugin {
    public static FileConfiguration c;
    public static FileConfiguration shop;
    public static FileConfiguration TranslationsFile;
    public static FileConfiguration me;
	public static Loader plugin;
	static FishOfDay f = new FishOfDay();
	private static HashMap<String, CEnch> map = new HashMap<String, CEnch>();
	
	public static List<CEnch> getEnchants(){
		List<CEnch> a = new ArrayList<CEnch>();
		for(String s : map.keySet())a.add(map.get(s));
		return a;
	}
	
	@Override
	public void onEnable() {
		if(!TheAPI.isNewVersion()) {
			Bukkit.getLogger().severe("************************************************");
			Bukkit.getLogger().severe("Supported server versions are only 1.13 and newer!");
			Bukkit.getLogger().severe("************************************************");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		plugin=this;
		LoadAll();
		for(String s : c.getConfigurationSection("Enchants").getKeys(false)) {
			CEnch d = new CEnch(new NamespacedKey(this, s));
			d.setName(c.getString("Enchants."+s+".Name"), s);
			EnchantmentAPI.registerEnchantment(d);
			map.put(s, d);
		}
		
		f.startRunnable();
		if(getDescription().getVersion().contains("TESTING"))isTest();
		getServer().getPluginManager().registerEvents(new onChat(), this);
		getServer().getPluginManager().registerEvents(new AFK(), this);
		getServer().getPluginManager().registerEvents(new onFish(), this);
		getServer().getPluginCommand("fish").setExecutor(new Fishing());
		getServer().getPluginCommand("amazingfishing").setExecutor(new Fishing());
		
	}
	private void isTest() {
			Bukkit.getLogger().warning("************************************************");
			Bukkit.getLogger().warning("This is testing version of AmazingFishing (Can contains bugs)");
			Bukkit.getLogger().warning("************************************************");
	}
	
	public static String s(String path) {
		String s = TranslationsFile.getString(path);
		if(s==null)s="&4Error, Missing path '"+path+"'";
		return s;
	}
	@Override
	public void onDisable() {
		reloadAll();
		f.stopRunnable();
	}
	public static void msgCmd(String msg, CommandSender s) {
		s.sendMessage(Color.c(msg));
	}
	
	public static void reloadAll() {
		a.reload();
		chat.reload();
		cc.reload();
		shopfile.reload();
	}
	
	public void LoadAll() {
		load();
		setupTranslations();
		setupChatMe();
		loadShop();
		}
	public static String getAFK(int i) {
		if(TranslationsFile.getString("AFK-Title."+i)!=null)
			return Color.c(TranslationsFile.getString("AFK-Title."+i));
		return "";
	}
	public static String get(String path, int i) {
		if(TranslationsFile.getString("Editor."+path+"."+i)!=null)
			return Color.c(TranslationsFile.getString("Editor."+path+"."+i));
		return "";
	}
	static ConfigAPI a=TheAPI.getConfig("AmazingFishing", "Translations");
	static void setupTranslations() {
		a.addDefault("CommandIsDisabled", "&cCommand is disabled!");
		a.addDefault("TopPlayers", "&6Top 3 players records on fish &a%fish%");
		a.addDefault("Prefix", "&bFishing &4• &6");
		a.addDefault("Stats", Arrays.asList("&6------< &b%playername% &6>------"
				,"&b> &6Caught fish: &a%fish%","&b> &6Longest fish & name: &a%fish%: %record%"
				,"&b> &6Participating tournaments: &a%tournaments%","&b> &6Top 1 in participating tournaments: &a%top1% times"));
		a.addDefault("ConfigReloaded", "&2Config reloaded!");
		a.addDefault("SoldFish", "&eSold %amount% fish for %money%$, %exp%exp and %points% points");
		a.addDefault("MissingData", "&cMissing data, First you must caught fish");
		a.addDefault("CommandIsDisabled", "&cCommand is disabled!");
		a.addDefault("Biome-Added", "&aBiome added");
		a.addDefault("ConsoleErrorMessage", "&cCommand can be used only in-game!");
		a.addDefault("NoPermissions", "&cYou don't have permission '%permission%' to do that!");
		a.addDefault("AFK-Title.1", "&6Please move");
		a.addDefault("AFK-Title.2", "&6To another location");
		a.addDefault("Help.Reload", "&5Reload config");
		a.addDefault("Help.Stats.My", "&5Your fishing stats");
		a.addDefault("Help.Stats.Other", "&5Fishing stats of specified player");
		a.addDefault("Help.Tournament", "&5Start new tournament for time");
		a.addDefault("Help.Tournament_Stop", "&cStop running tournament");
		a.addDefault("Help.Editor", "&5Open virtual editor for plugin settings/fish and more");
		a.addDefault("Help.List", "&5List of fish");
		a.addDefault("Help.Points", "&5Manager of player points");
		a.addDefault("Help.Points-Give", "&5Give player specified amount of points");
		a.addDefault("Help.Points-Take", "&5Take from player specified amount of points");
		a.addDefault("Help.Points-Set", "&5Set specified amount of player points");
		a.addDefault("Help.Points-Balance", "&5Points balance of player");
		a.addDefault("Help.Toggle", "&5Toggle new record message");
		a.addDefault("Help.Record", "&5Your record of specified fish");
		a.addDefault("Help.Top", "&5Top 3 players of specified fish");
		a.addDefault("Help.Bag", "&5Open your fish bag");
		a.addDefault("Help.Shop", "&5Open shop");
		
		a.addDefault("Help.Enchants", "&5Open enchant table for your fishing rod");
		a.addDefault("Help.Reload", "&5Reload config");
		a.addDefault("Editor.MissingCrateName.1", "&6Missing");
		a.addDefault("Editor.MissingCrateName.2", "&6Name of treasure!");
		a.addDefault("Editor.SuccefullyCreatedCrate.1", "&6Treasure &a%treasure%");
		a.addDefault("Editor.SuccefullyCreatedCrate.2", "&6Succefully created!");
		a.addDefault("Editor.WriteCommand.1", "&6Write new command");
		a.addDefault("Editor.WriteCommand.2", "&6To the chat.");
		a.addDefault("Editor.WriteMoney.1", "&6Write new money amount");
		a.addDefault("Editor.WriteMoney.2", "&6To the chat.");
		a.addDefault("Editor.WriteExp.1", "&6Write new experiences amount");
		a.addDefault("Editor.WriteExp.2", "&6To the chat.");
		a.addDefault("Editor.WritePoint.1", "&6Write new points amount");
		a.addDefault("Editor.WritePoint.2", "&6To the chat.");
		a.addDefault("Editor.NewMessage.1", "&6Write new message");
		a.addDefault("Editor.NewMessage.2", "&6To the chat.");
		a.addDefault("Editor.WriteName.1", "&6Write new name");
		a.addDefault("Editor.WriteName.2", "&6To the chat.");
		a.addDefault("Editor.WriteCost.1", "&6Write cost of enchant");
		a.addDefault("Editor.WriteCost.2", "&6To the chat.");
		
		a.addDefault("Editor.WriteChance.1", "&6Write new chance in percentage");
		a.addDefault("Editor.WriteChance.2", "&6To the chat.");
		a.addDefault("Editor.WriteLength.1", "&6Write new max length of fish");
		a.addDefault("Editor.WriteLength.2", "&6To the chat.");
		a.addDefault("Editor.WriteExpBonus.1", "&6Write exp bonus in percentage");
		a.addDefault("Editor.WriteExpBonus.2", "&6To the chat.");
		a.addDefault("Editor.WriteAmount.1", "&6Write fish chance amount bonus");
		a.addDefault("Editor.WriteAmount.2", "&6To the chat.");
		a.addDefault("Editor.SuccefullyCreatedEnchant.1", "&6Enchant %enchant%");
		a.addDefault("Editor.SuccefullyCreatedEnchant.2", "&6Succefully created!");
		a.addDefault("Editor.MissingEnchantName.1", "&6Missing");
		a.addDefault("Editor.MissingEnchantName.2", "&6Name of enchant!");
		
		a.addDefault("Editor.WriteMoneyBonus.1", "&6Write money bonus in percentage");
		a.addDefault("Editor.WriteMoneyBonus.2", "&6To the chat."); 
		a.addDefault("Editor.WritePointsBonus.1", "&6Write points bonus in percentage");
		a.addDefault("Editor.WritePointsBonus.2", "&6To the chat.");
		a.addDefault("Editor.NewDescription.1", "&6Write new description message");
		a.addDefault("Editor.NewDescription.2", "&6To the chat.");
		
		a.addDefault("Editor.Saved.1", "&6Saved!");
		a.addDefault("Editor.Saved.2", "");
		
		a.addDefault("Editor.MissingFishName.1", "&6Missing");
		a.addDefault("Editor.MissingFishName.2", "&6Name of fish!");
		a.addDefault("Editor.SuccefullyCreated.1", "&6Fish &a%fish%");
		a.addDefault("Editor.SuccefullyCreated.2", "&6Succefully created!");
		a.addDefault("ReachNewRecord", "&6You reach new record on fish &a%fish%&6, Last record was &a%last%cm&6, New record &a%record%cm");
		a.addDefault("RecordOnFish", "&6Your record on fish &a%fish%&6 is &a%record%cm");
		a.addDefault("NeverCaught", "&6You never caught &c%fish%");
		a.addDefault("Caught", "&6You caught &a%fish% &6with &a%cm%cm &7(%weight%kg)");
		a.addDefault("TournamentTypes", "&6Tournament Types:");
		a.addDefault("ListFish", "&6List of fish:");

		a.addDefault("HelpGUI.Record.Want", "&aI want receive record reached message");
		a.addDefault("HelpGUI.Record.DoNotWant", "&cI don't want receive record reached message");

		a.addDefault("HelpGUI.Record.Receive", "&aYou will receive a new record message");
		a.addDefault("HelpGUI.ClickToStart", "&aClick to start");

		a.addDefault("HelpGUI.Record.NoLongerReceive", "&cYou will no longer receive a new record messagee");
		a.addDefault("Winners", "&6Tournament winners of type &0'%type%&0'&6:");
		a.addDefault("Stopped", "&cStopped tournament type &0'%type%&0' &6on %time%");
		a.addDefault("Started", "&aStarted tournament type &0'%type%&0' &6on %time%");
		a.addDefault("Running", "&6Running tournament type &0'%type%&0' &6on %time%");
		
		a.addDefault("Words.Pufferfish", "&ePufferfish");
		a.addDefault("Words.Salmon", "&4Salmon");
		a.addDefault("Words.Cod", "&8Cod");
		a.addDefault("Words.Tropical_Fish", "&cTropical Fish");
		a.addDefault("Words.FishRemove", "&cRemove normal fish on catch custom fish");

		a.addDefault("Words.Bag_Title", "&bFish Bag");
		a.addDefault("Words.Cost", "&6Cost");
		a.addDefault("Words.MoneyBonus", "&6Money Bonus");
		a.addDefault("Words.PointsBonus", "&9Points Bonus");
		a.addDefault("Words.Description", "&3Description");
		a.addDefault("Words.Amount", "&aAmount Bonus");
		a.addDefault("Words.Exp", "&9Experiences Bonus");
		a.addDefault("Words.Help.Top", "&bTop 3 players records");
		a.addDefault("Words.Help.Record", "&cYour records");
		a.addDefault("Words.Help.Toggle", "&3Toggle new record reached message");
		a.addDefault("Words.Help.Editor", "&6Editor of Fish and Settings");
		a.addDefault("Words.Help.List", "&aList of fish");
		a.addDefault("Words.Help.Tournament", "&bStart new tournament");
		a.addDefault("Words.PerBiome", "&bPer Biome");
		
		a.addDefault("Words.Help.Enchant", "&5Enchant table for your rod");
		a.addDefault("Words.Help.Shop", "&dShop");
		a.addDefault("Words.Help.Reload", "&2Reload config");
		a.addDefault("Words.Help.Admin", "&7Switch to admin section");
		a.addDefault("Words.Help.Player", "&7Switch to player section");
		a.addDefault("Words.Help.Points", "&9Manager of player points");
		a.addDefault("Words.Help.Stats.My", "&cStats about you");
		a.addDefault("Words.Help.Bag", "&5Open fish bag");
		
		a.addDefault("Words.Help.Stats.Other", "&cStats about specified player");
		
		a.addDefault("Words.Create", "&2Create");
		a.addDefault("Words.Delete", "&4Delete");
		a.addDefault("Words.Edit", "&6Edit");
		a.addDefault("Words.Deleted", "&4DELETED");
		a.addDefault("Words.Money", "&6Money");
		a.addDefault("Words.MaxCm", "&3Max length");
		a.addDefault("Words.Cancel", "&cCancel");
		a.addDefault("Words.Back", "&4Back");
		a.addDefault("Words.Bag", "&eBag");
		

		a.addDefault("Words.SelectEnchants", "&5Enchants editor");
		a.addDefault("Words.SelectFish", "&6Fish editor");
		a.addDefault("Words.SelectTreasures", "&bTreasures editor");
		a.addDefault("Words.SelectSettings", "&7Settings editor");
		a.addDefault("Words.Fish", "&7Fish");
		
		a.addDefault("Words.Commands", "&dCommands");
		a.addDefault("Words.Messages", "&rMessages");
		a.addDefault("Words.Chance", "&eChance");
		a.addDefault("Words.Sell_Fish", "&6Sell fish");
		
		a.addDefault("Words.Common", "&7Common");
		a.addDefault("Words.Rare", "&1Rare");
		a.addDefault("Words.Epic", "&6Epic");
		a.addDefault("Words.Legendary", "&4Legendary");
		a.addDefault("Words.NowPoints-Give", "&2Give points");
		a.addDefault("Words.NowPoints-Take", "&cTake points");
		a.addDefault("Words.NowPoints-Set", "&6Set points");

		a.addDefault("Words.Biomes.ALL.Name", "&6ALL");
		a.addDefault("Words.Biomes.ALL.Icon", "CHEST");
		a.addDefault("Words.Biomes.BADLANDS.Name", "&7BADLANDS");
		a.addDefault("Words.Biomes.BADLANDS.Icon", "RED_SAND");
		a.addDefault("Words.Biomes.BEACH.Name", "&eBEACH");
		a.addDefault("Words.Biomes.BEACH.Icon", "SAND");
		a.addDefault("Words.Biomes.MUSHROOM.Name", "&dMUSHROOMS FIELD");
		a.addDefault("Words.Biomes.MUSHROOM.Icon", "MYCELIUM");
		
		

		a.addDefault("Words.Biomes.NETHER.Name", "&4NETHER");
		a.addDefault("Words.Biomes.NETHER.Icon", "LAVA_BUCKET");

		a.addDefault("Words.Biomes.END.Name", "&5THE END");
		a.addDefault("Words.Biomes.END.Icon", "ENDER_PEARL");

		a.addDefault("Words.Biomes.TUNDRA.Name", "&9TUNDRA");
		a.addDefault("Words.Biomes.TUNDRA.Icon", "SNOW");

		a.addDefault("Words.Biomes.WOODED_HILLS.Name", "&2WOODED HILLS");
		a.addDefault("Words.Biomes.WOODED_HILLS.Icon", "OAK_LOG");

		a.addDefault("Words.Biomes.STONE_SHORE.Name", "&7STONE SHORE");
		a.addDefault("Words.Biomes.STONE_SHORE.Icon", "STONE");

		a.addDefault("Words.Biomes.SAVANNA.Name", "&6SAVANNA");
		a.addDefault("Words.Biomes.SAVANNA.Icon", "SAND");
		
		a.addDefault("Words.Biomes.COLD_OCEAN.Name", "&1COLD OCEAN");
		a.addDefault("Words.Biomes.COLD_OCEAN.Icon", "WATER_BUCKET");
		a.addDefault("Words.Biomes.DEEP_COLD_OCEAN.Name", "&9DEEP COLD OCEAN");
		a.addDefault("Words.Biomes.DEEP_COLD_OCEAN.Icon", "WATER_BUCKET");
		a.addDefault("Words.Biomes.DEEP_FROZEN_OCEAN.Name", "&1DEEP FROZEN OCEAN");
		a.addDefault("Words.Biomes.DEEP_FROZEN_OCEAN.Icon", "ICE");
		a.addDefault("Words.Biomes.DEEP_LUKEWARM_OCEAN.Name", "&cDEEP LUKEWARM OCEAN");
		a.addDefault("Words.Biomes.DEEP_LUKEWARM_OCEAN.Icon", "FIRE_CORAL");
		a.addDefault("Words.Biomes.DEEP_WARM_OCEAN.Name", "&cDEEP WARM OCEAN");
		a.addDefault("Words.Biomes.DEEP_WARM_OCEAN.Icon", "FIRE_CORAL");
		a.addDefault("Words.Biomes.DEEP_OCEAN.Name", "&9DEEP OCEAN");
		a.addDefault("Words.Biomes.DEEP_OCEAN.Icon", "WATER_BUCKET");
		a.addDefault("Words.Biomes.DESERT.Name", "&eDESERT");
		a.addDefault("Words.Biomes.DESERT.Icon", "CACTUS");
		a.addDefault("Words.Biomes.FOREST.Name", "&2FOREST");
		a.addDefault("Words.Biomes.FOREST.Icon", "OAK_SAPLING");
		a.addDefault("Words.Biomes.FROZEN_OCEAN.Name", "&1FROZEN OCEAN");
		a.addDefault("Words.Biomes.FROZEN_OCEAN.Icon", "ICE");
		a.addDefault("Words.Biomes.ICE_SPIKES.Name", "&1ICE SPIKES");
		a.addDefault("Words.Biomes.ICE_SPIKES.Icon", "PACKED_ICE");
		a.addDefault("Words.Biomes.JUNGLE.Name", "&aJUNGLE");
		a.addDefault("Words.Biomes.JUNGLE.Icon", "JUNGLE_SAPLING");
		a.addDefault("Words.Biomes.MOUNTAINS.Name", "&7MOUNTAINS");
		a.addDefault("Words.Biomes.MOUNTAINS.Icon", "STONE");
		a.addDefault("Words.Biomes.TAIGA.Name", "&2TAIGA");
		a.addDefault("Words.Biomes.TAIGA.Icon", "SPRUCE_SAPLING");
		a.addDefault("Words.Biomes.WARM_OCEAN.Name", "&cWARM OCEAN");
		a.addDefault("Words.Biomes.WARM_OCEAN.Icon", "FIRE_CORAL");
		a.addDefault("Words.Biomes.PLAINS.Name", "&aPLAINS");
		a.addDefault("Words.Biomes.PLAINS.Icon", "GRASS_BLOCK");
		a.addDefault("Words.Biomes.RIVER.Name", "&9RIVER");
		a.addDefault("Words.Biomes.RIVER.Icon", "DIRT");
		a.addDefault("Words.Biomes.SWAMP.Name", "&2SWAMP");
		a.addDefault("Words.Biomes.SWAMP.Icon", "SLIME_BALL");
		
		
		a.addDefault("Words.Close", "&cClose");
		a.addDefault("Words.Save", "&2Save");
		a.addDefault("Words.ShopLogo", "&6Fishing Shop");
		a.addDefault("Words.Shop_Sell_Title", "&6Fishing Shop Sell Section");
		
		a.addDefault("Words.EverythingByCm", "&6Earnings from fish length");
		a.addDefault("Words.Enabled", "&aEnabled");
		a.addDefault("Words.Disabled", "&cDisabled");
		a.addDefault("Words.Shop", "&6Custom plugin shop");
		a.addDefault("Words.HasPoints", "&9%points% points");
		a.addDefault("Words.Enchants", "&6Custom fishing rod enchants");
		a.addDefault("Words.Treasures", "&6Custom treasures");
		a.addDefault("Words.TreasuresTitle", "&bTreaures");
		a.addDefault("Words.Points", "&dPoints");
		a.addDefault("Words.Name", "&bName");
		a.addDefault("Words.Experiences", "&9Experiences");
		a.addDefault("Toggled.true", "&6Notifications about new records will be no longer shows");
		a.addDefault("Toggled.false", "&6Notifications about new records now will be showing");
		a.create();
		TranslationsFile=a.getConfig();
	}
	static ConfigAPI chat= TheAPI.getConfig("AmazingFishing", "Data");
	static void setupChatMe() {
		chat.create();
		me=chat.getConfig();
	}
	static ConfigAPI cc= TheAPI.getConfig("AmazingFishing", "AmazingFishing");
	static void addDefault() {
		cc.addDefault("Options.BossBar.Use", true);
		cc.addDefault("Options.BossBar.OnlyIfCatchFish", true);
		cc.addDefault("Options.BossBar.Running", "&cRunning tournament type &e%type% &con &e%time_formated%");
		cc.addDefault("Options.BossBar.Win", "&2Tournament was end. You are on %position%. position");
		cc.addDefault("Options.Treasures", true);
		cc.addDefault("Options.Shop", true);
		cc.addDefault("Options.CustomFishOnlyWhileTournament", false);
		cc.addDefault("Options.TreasuresOnlyWhileTournament", false);
		cc.addDefault("Options.Enchants", true);
		cc.addDefault("Options.DisableMoneyFromCaught", false);
		cc.addDefault("Options.ShopGiveFullPriceFish", false);
		cc.addDefault("Options.EarnFromLength", true);
		cc.addDefault("Options.UseGUI", true);
		cc.addDefault("Options.ShopSellFish", true);
		cc.addDefault("Options.LogCaughtFishToConsole", false);
		cc.addDefault("Options.FishRemove", false);
		cc.addDefault("Options.Particles", true);
		cc.addDefault("Options.Sounds.Shop-BuyItem", true);
		cc.addDefault("Options.Sounds.Shop-SellFish", true);
		cc.addDefault("Options.Sounds.Bag-SellFish", true);
		cc.addDefault("Options.Sounds.CatchFish", false);
		cc.addDefault("Options.Disabled-Worlds", Arrays.asList("creative_world","disabled_world"));
		cc.addDefault("Options.AFK.Enabled", true);
		cc.addDefault("Options.AFK.TimeToAFK", 300);
		cc.addDefault("Options.Bag.StorageItems", Arrays.asList("Fish","Fishing_rod"));
		cc.addDefault("Options.Bag.ButtonsToSellFish", true);
		
		cc.addDefault("Options.Bag.ButtonsToOpenShop", true);
		cc.addDefault("Options.Manual.MinimalFishLength", 0.15);
		cc.addDefault("Options.Manual.ChanceForTreasure", 5);
		cc.addDefault("Options.Perms-Treasures.Legendary.UseWorldGuard-Regions", false);
		cc.addDefault("Options.Perms-Treasures.Legendary.WorldGuard-Regions",  Arrays.asList("Legendary_Treasures_Region"));
		cc.addDefault("Options.Perms-Treasures.Epic.UseWorldGuard-Regions", false);
		cc.addDefault("Options.Perms-Treasures.Epic.WorldGuard-Regions",  Arrays.asList("Epic_Treasures_Region"));
		cc.addDefault("Options.Perms-Treasures.Rare.UseWorldGuard-Regions", false);
		cc.addDefault("Options.Perms-Treasures.Rare.WorldGuard-Regions",  Arrays.asList("Rare_Treasures_Region"));
		cc.addDefault("Options.Perms-Treasures.Common.UseWorldGuard-Regions", false);
		cc.addDefault("Options.Perms-Treasures.Common.WorldGuard-Regions",  Arrays.asList("Common_Treasures_Region"));

		cc.addDefault("Options.Perms-Treasures.Legendary.UseResidence", false);
		cc.addDefault("Options.Perms-Treasures.Legendary.Residence", Arrays.asList("Legendary_Treasures_Residence"));
		cc.addDefault("Options.Perms-Treasures.Epic.UseResidence", false);
		cc.addDefault("Options.Perms-Treasures.Epic.Residence", Arrays.asList("Epic_Treasures_Residence"));
		cc.addDefault("Options.Perms-Treasures.Rare.UseResidence", false);
		cc.addDefault("Options.Perms-Treasures.Rare.Residence", Arrays.asList("Rare_Treasures_Residence"));
		cc.addDefault("Options.Perms-Treasures.Common.UseResidence", false);
		cc.addDefault("Options.Perms-Treasures.Common.Residence", Arrays.asList("Common_Treasures_Residence"));
		
		cc.addDefault("Format.TopFisher", "&a%position%. &6%playername%&6: &a%record%cm");
		cc.addDefault("Format.You", "&6You are &a%position%. &6with &a%record%cm");
		cc.addDefault("Format.FishDescription", Arrays.asList("&8-------------------------"
				,"&7Weight: &a%weight%kg","&7Length: &a%length%cm","&7Can be found:"," &a%biomes%"
				,"&7Caught by: &a%fisher%","&8-------------------------"));
		if(!cc.existPath("GUI.Stats")) {
		cc.addDefault("GUI.Stats.1.Name", "&aPlayer");
		cc.addDefault("GUI.Stats.1.Lore", Arrays.asList("&3> &a%playername%"));
		cc.addDefault("GUI.Stats.1.Material", "PLAYER_HEAD");
		
		cc.addDefault("GUI.Stats.2.Name", "&bAmount of catched fish");
		cc.addDefault("GUI.Stats.2.Lore", Arrays.asList("&3> &a%fish_catched%"));
		cc.addDefault("GUI.Stats.2.Material", "COD_BUCKET");
		
		cc.addDefault("GUI.Stats.3.Name", "&4Best fish");
		cc.addDefault("GUI.Stats.3.Lore", Arrays.asList("&3> &aType: &b%fish_type%","&3> &aName: &b%fish_name%","&3> &aRecord: &b%fish_record%"));
		cc.addDefault("GUI.Stats.3.Material", "PAPER");
		
		cc.addDefault("GUI.Stats.4.Name", "&eTournaments");
		cc.addDefault("GUI.Stats.4.Lore", Arrays.asList("&3> &aPlayed: &b%tournament_played%",
				"&3> &aStats:","&3> &aStats:","&3>   &bTop 1: &b%tournament_top1%",
				"&3>   &6Top 2: &b%tournament_top2%","&3>   &eTop 3: &b%tournament_top3%"));
		cc.addDefault("GUI.Stats.4.Material", "DIAMOND");
		}
		cc.addDefault("Tournaments.DefaultLength", "10min");
		cc.addDefault("Tournaments.Length.Name", "&aLength");
		cc.addDefault("Tournaments.Length.Rewards.1", Arrays.asList("eco give %player% 500"));
		cc.addDefault("Tournaments.Length.Rewards.2", Arrays.asList("eco give %player% 250"));
		cc.addDefault("Tournaments.Length.Rewards.3", Arrays.asList("eco give %player% 100"));
		cc.addDefault("Tournaments.Length.Positions", "&6%position%. %playername%&6 with %value%Cm");

		cc.addDefault("Tournaments.Weight.Name", "&6Weight");
		cc.addDefault("Tournaments.Weight.Rewards.1", Arrays.asList("eco give %player% 525"));
		cc.addDefault("Tournaments.Weight.Rewards.2", Arrays.asList("eco give %player% 275"));
		cc.addDefault("Tournaments.Weight.Rewards.3", Arrays.asList("eco give %player% 125"));
		cc.addDefault("Tournaments.Weight.Positions", "&6%position%. %playername%&6 with %value%Kg");

		cc.addDefault("Tournaments.MostCatch.Name", "&3Most Catch");
		cc.addDefault("Tournaments.MostCatch.Rewards.1", Arrays.asList("eco give %player% 400"));
		cc.addDefault("Tournaments.MostCatch.Rewards.2", Arrays.asList("eco give %player% 200"));
		cc.addDefault("Tournaments.MostCatch.Rewards.3", Arrays.asList("eco give %player% 80"));
		cc.addDefault("Tournaments.MostCatch.Positions", "&6%position%. %playername%&6 with %value% fish");
		
		if(!cc.existPath("Types")) {
		cc.addDefault("Types.Cod.0.Name", "&cBarracuda");
		cc.addDefault("Types.Cod.0.Money", 0.5);
		cc.addDefault("Types.Cod.0.Points", 0.1);
		cc.addDefault("Types.Cod.0.Xp", 0.5);
		cc.addDefault("Types.Cod.0.MaxCm", 130.0);
		cc.addDefault("Types.Cod.0.ModelData", 0);
		cc.addDefault("Types.Cod.0.Chance", 33.5);
		
		cc.addDefault("Types.Cod.1.Name", "&8Roach");
		cc.addDefault("Types.Cod.1.Money", 0.2);
		cc.addDefault("Types.Cod.1.Points", 0.1);
		cc.addDefault("Types.Cod.1.Xp", 0.2);
		cc.addDefault("Types.Cod.1.MaxCm", 60.0);
		cc.addDefault("Types.Cod.1.ModelData", 1);
		cc.addDefault("Types.Cod.1.Chance", 58.3);
		
		cc.addDefault("Types.Cod.2.Name", "&2Common carp");
		cc.addDefault("Types.Cod.2.Money", 0.6);
		cc.addDefault("Types.Cod.2.Points", 0.2);
		cc.addDefault("Types.Cod.2.Xp", 0.4);
		cc.addDefault("Types.Cod.2.MaxCm", 80.0);
		cc.addDefault("Types.Cod.2.ModelData", 2);
		cc.addDefault("Types.Cod.2.Chance", 27.1);
		
		cc.addDefault("Types.Salmon.0.Name", "&cSturgeon");
		cc.addDefault("Types.Salmon.0.Money", 1.1);
		cc.addDefault("Types.Salmon.0.Points", 0.3);
		cc.addDefault("Types.Salmon.0.Xp", 0.9);
		cc.addDefault("Types.Salmon.0.MaxCm", 120.0);
		cc.addDefault("Types.Salmon.0.ModelData", 0);
		cc.addDefault("Types.Salmon.0.Chance", 11.2);
		
		cc.addDefault("Types.Salmon.1.Name", "&8Pike");
		cc.addDefault("Types.Salmon.1.Money", 0.5);
		cc.addDefault("Types.Salmon.1.Points", 0.2);
		cc.addDefault("Types.Salmon.1.Xp", 0.3);
		cc.addDefault("Types.Salmon.1.MaxCm", 70.0);
		cc.addDefault("Types.Salmon.1.ModelData", 1);
		cc.addDefault("Types.Salmon.1.Chance", 35.3);
		
		cc.addDefault("Types.Salmon.2.Name", "&aCommon trout");
		cc.addDefault("Types.Salmon.2.Money", 0.4);
		cc.addDefault("Types.Salmon.2.Points", 0.1);
		cc.addDefault("Types.Salmon.2.Xp", 0.2);
		cc.addDefault("Types.Salmon.2.MaxCm", 60.0);
		cc.addDefault("Types.Salmon.2.ModelData", 2);
		cc.addDefault("Types.Salmon.2.Chance", 48.8);

		cc.addDefault("Types.TropicalFish.0.Name", "&aAngel fish");
		cc.addDefault("Types.TropicalFish.0.Money", 2.6);
		cc.addDefault("Types.TropicalFish.0.Points", 0.8);
		cc.addDefault("Types.TropicalFish.0.Xp", 3);
		cc.addDefault("Types.TropicalFish.0.MaxCm", 25.0);
		cc.addDefault("Types.TropicalFish.0.ModelData", 0);
		cc.addDefault("Types.TropicalFish.0.Chance", 12.4);
		
		cc.addDefault("Types.TropicalFish.1.Name", "&5Arowana fish");
		cc.addDefault("Types.TropicalFish.1.Money", 1.5);
		cc.addDefault("Types.TropicalFish.1.Points", 1.1);
		cc.addDefault("Types.TropicalFish.1.Xp", 0.5);
		cc.addDefault("Types.TropicalFish.1.MaxCm", 35.0);
		cc.addDefault("Types.TropicalFish.1.ModelData", 1);
		cc.addDefault("Types.TropicalFish.1.Chance", 25.9);

		cc.addDefault("Types.TropicalFish.2.Name", "&6Brackish fish");
		cc.addDefault("Types.TropicalFish.2.Money", 1.8);
		cc.addDefault("Types.TropicalFish.2.Points", 0.2);
		cc.addDefault("Types.TropicalFish.2.Xp", 6);
		cc.addDefault("Types.TropicalFish.2.MaxCm", 40.0);
		cc.addDefault("Types.TropicalFish.2.ModelData", 2);
		cc.addDefault("Types.TropicalFish.2.Chance", 33.6);

		cc.addDefault("Types.TropicalFish.3.Name", "&cNemo");
		cc.addDefault("Types.TropicalFish.3.Money", 22.9);
		cc.addDefault("Types.TropicalFish.3.Points", 0.6);
		cc.addDefault("Types.TropicalFish.3.Xp", 1);
		cc.addDefault("Types.TropicalFish.3.MaxCm", 20.0);
		cc.addDefault("Types.TropicalFish.3.ModelData", 3);
		cc.addDefault("Types.TropicalFish.3.Chance", 7.2);
		
		cc.addDefault("Types.PufferFish.0.Name", "&ePufferfish");
		cc.addDefault("Types.PufferFish.0.Money", 0.5);
		cc.addDefault("Types.PufferFish.0.Points", 0.1);
		cc.addDefault("Types.PufferFish.0.Xp", 1);
		cc.addDefault("Types.PufferFish.0.MaxCm", 20.0);
		cc.addDefault("Types.PufferFish.0.ModelData", 0);
		cc.addDefault("Types.PufferFish.0.Chance", 45.9);

		cc.addDefault("Types.PufferFish.1.Name", "&cArothron");
		cc.addDefault("Types.PufferFish.1.Money", 0.6);
		cc.addDefault("Types.PufferFish.1.Points", 0.1);
		cc.addDefault("Types.PufferFish.1.Xp", 1);
		cc.addDefault("Types.PufferFish.1.MaxCm", 45.0);
		cc.addDefault("Types.PufferFish.1.ModelData", 1);
		cc.addDefault("Types.PufferFish.1.Chance", 32.4);
		}
		if(!cc.existPath("Treasures")) {
		cc.addDefault("Treasures.COMMON.0.Name", "&8Old Chest");
		cc.addDefault("Treasures.COMMON.0.Money", 17);
		cc.addDefault("Treasures.COMMON.0.Points", 1.08);
		cc.addDefault("Treasures.COMMON.0.Chance", 86);
		cc.addDefault("Treasures.COMMON.0.Messages", Arrays.asList("&6You found %treasure% &7(Rare)"));
		cc.addDefault("Treasures.COMMON.0.Commands", Arrays.asList("give %player% stick 1", "give %player% dirt 4", "give %player% string 1"));
		cc.addDefault("Treasures.COMMON.1.Name", "&6Crate");
		cc.addDefault("Treasures.COMMON.1.Money", 27);
		cc.addDefault("Treasures.COMMON.1.Points", 0.12);
		cc.addDefault("Treasures.COMMON.1.Chance", 14);
		cc.addDefault("Treasures.COMMON.1.Messages", Arrays.asList("&6You found %treasure% &7(Rare)"));
		cc.addDefault("Treasures.COMMON.1.Commands", Arrays.asList("give %player% stick 8", "give %player% apple 12", "give %player% string 11"));
		cc.addDefault("Treasures.COMMON.2.Name", "&aTreasure");
		cc.addDefault("Treasures.COMMON.2.Money", 53);
		cc.addDefault("Treasures.COMMON.2.Points", 2.1);
		cc.addDefault("Treasures.COMMON.2.Chance", 10);
		cc.addDefault("Treasures.COMMON.2.Messages", Arrays.asList("&6You found %treasure% &7(Rare)"));
		cc.addDefault("Treasures.COMMON.2.Commands", Arrays.asList("give %player% gold_ingot 4", "give %player% fishing_rod 1", "give %player% iron_ingot 2"));

		cc.addDefault("Treasures.RARE.0.Name", "&9Box");
		cc.addDefault("Treasures.RARE.0.Money", 29);
		cc.addDefault("Treasures.RARE.0.Points", 1.6);
		cc.addDefault("Treasures.RARE.0.Chance", 78);
		cc.addDefault("Treasures.RARE.0.Messages", Arrays.asList("&6You found %treasure% &7(Rare)"));
		cc.addDefault("Treasures.RARE.0.Commands", Arrays.asList("give %player% iron_nugget 2"));
		cc.addDefault("Treasures.RARE.1.Name", "&1Treasure");
		cc.addDefault("Treasures.RARE.1.Money", 77);
		cc.addDefault("Treasures.RARE.1.Points", 3.4);
		cc.addDefault("Treasures.RARE.1.Chance", 22);
		cc.addDefault("Treasures.RARE.1.Messages", Arrays.asList("&6You found %treasure% &7(Rare)"));
		cc.addDefault("Treasures.RARE.1.Commands", Arrays.asList("give %player% diamond 1", "give %player% gold_nugget 4"));

		cc.addDefault("Treasures.EPIC.0.Name", "&cJewelry");
		cc.addDefault("Treasures.EPIC.0.Money", 175);
		cc.addDefault("Treasures.EPIC.0.Points", 3.8);
		cc.addDefault("Treasures.EPIC.0.Chance", 89);
		cc.addDefault("Treasures.EPIC.0.Messages", Arrays.asList("&6You found %treasure% &7(Epic)"));
		cc.addDefault("Treasures.EPIC.0.Commands", Arrays.asList("give %player% gold_nugget 15", "give %player% diamond 3"));
		cc.addDefault("Treasures.EPIC.1.Name", "&cTreasure");
		cc.addDefault("Treasures.EPIC.1.Money", 121);
		cc.addDefault("Treasures.EPIC.1.Points", 9.74);
		cc.addDefault("Treasures.EPIC.1.Chance", 11);
		cc.addDefault("Treasures.EPIC.1.Messages", Arrays.asList("&6You found %treasure% &7(Epic)"));
		cc.addDefault("Treasures.EPIC.1.Commands", Arrays.asList("give %player% diamond 7","give %player% iron_ingot 4","give %player% cobweb 1","give %player% string 3"));

		cc.addDefault("Treasures.LEGENDARY.0.Name", "&cSafe");
		cc.addDefault("Treasures.LEGENDARY.0.Money", 33);
		cc.addDefault("Treasures.LEGENDARY.0.Points", 2.41);
		cc.addDefault("Treasures.LEGENDARY.0.Chance", 92);
		cc.addDefault("Treasures.LEGENDARY.0.Messages", Arrays.asList("&6You found %treasure% &7(Legendary)"));
		cc.addDefault("Treasures.LEGENDARY.0.Commands", Arrays.asList("give %player% diamond 17","give %player% enchanted_golden_apple 5"));
		cc.addDefault("Treasures.LEGENDARY.1.Name", "&4Lost Treasure");
		cc.addDefault("Treasures.LEGENDARY.1.Money", 358);
		cc.addDefault("Treasures.LEGENDARY.1.Points", 80.16);
		cc.addDefault("Treasures.LEGENDARY.1.Chance", 8);
		cc.addDefault("Treasures.LEGENDARY.1.Messages", Arrays.asList("&6You found %treasure% &7(Legendary)"));
		cc.addDefault("Treasures.LEGENDARY.1.Commands", Arrays.asList("give %player% diamond_block 3","give %player% gold_ingot 27"
				,"give %player% golden_apple 11","give %player% cobweb 2"));
		}
		if(!cc.existPath("Quests")) {
			cc.addDefault("Quests.0.Name", "&aJohny is hungry (Easy)");
			cc.addDefault("Quests.0.Description", "&6Catch 5x Roach, 2x Barracuda..");
			cc.addDefault("Quests.0.Stage.0.Action", "CATCH_FISH");
			cc.addDefault("Quests.0.Stage.0.Fish", 1);
			cc.addDefault("Quests.0.Stage.0.Type", "Cod");
			cc.addDefault("Quests.0.Stage.0.Amount", 5);

			cc.addDefault("Quests.0.Stage.1.Action", "SELL_FISH");
			cc.addDefault("Quests.0.Stage.1.Fish", 1);
			cc.addDefault("Quests.0.Stage.1.Type", "Cod");
			cc.addDefault("Quests.0.Stage.1.Amount", 2);

			cc.addDefault("Quests.0.Stage.2.Action", "CATCH_FISH");
			cc.addDefault("Quests.0.Stage.2.Fish", 0);
			cc.addDefault("Quests.0.Stage.2.Type", "Cod");
			cc.addDefault("Quests.0.Stage.2.Amount", 2);
			cc.addDefault("Quests.0.Stage.3.Action", "SELL_FISH");
			cc.addDefault("Quests.0.Stage.3.Fish", 0);
			cc.addDefault("Quests.0.Stage.3.Type", "Cod");
			cc.addDefault("Quests.0.Stage.3.Amount", 2);
			cc.addDefault("Quests.0.Rewards.Commands", Arrays.asList("eco give %player% 500", "give %player% iron_ingot 10"));
			cc.addDefault("Quests.0.Rewards.Messages", Arrays.asList("&aCongratulation %player% !", "&aYou completed quest &c%quest% &a!"));

			cc.addDefault("Quests.1.Name", "&aStraiker123's challenge (Hard)");
			cc.addDefault("Quests.1.Time", "7d");
			cc.addDefault("Quests.1.Description", "&67 Days to complete, Catch 20x Brackish fish..");
			
			cc.addDefault("Quests.1.Stage.0.Action", "CATCH_FISH");
			cc.addDefault("Quests.1.Stage.0.Fish", 2);
			cc.addDefault("Quests.1.Stage.0.Type", "TropicalFish");
			cc.addDefault("Quests.1.Stage.0.Amount", 20);

			cc.addDefault("Quests.1.Stage.1.Action", "CATCH_FISH");
			cc.addDefault("Quests.1.Stage.1.Fish", 1);
			cc.addDefault("Quests.1.Stage.1.Type", "PufferFish");
			cc.addDefault("Quests.1.Stage.1.Amount", 15);

			cc.addDefault("Quests.1.Stage.2.Action", "CATCH_FISH");
			cc.addDefault("Quests.1.Stage.2.Fish", 1);
			cc.addDefault("Quests.1.Stage.2.Type", "Cod");
			cc.addDefault("Quests.1.Stage.2.Amount", 25);
			cc.addDefault("Quests.1.Rewards.Commands", Arrays.asList("eco give %player% 2500", "give %player% diamond 5"));
			cc.addDefault("Quests.1.Rewards.Messages", Arrays.asList("&aCongratulation %player% !", "&aYou completed quest &c%quest% &a!"));
			
			
		}
		if(!cc.existPath("Enchants")) {
		cc.addDefault("Enchants.0.Name", "&2Fortune");
		cc.addDefault("Enchants.0.AmountBonus", 0.67);
		cc.addDefault("Enchants.0.MoneyBonus", 0.39);
		cc.addDefault("Enchants.0.PointsBonus", 0.11);
		cc.addDefault("Enchants.0.ExpBonus", 0.23);
		cc.addDefault("Enchants.0.Cost", 65);
		cc.addDefault("Enchants.0.Description", Arrays.asList("&7Most increases amount and money bonus"," &7- Cost: %cost% Points"));
		cc.addDefault("Enchants.1.Name", "&cDouble hook");
		cc.addDefault("Enchants.1.AmountBonus", 0.21);
		cc.addDefault("Enchants.1.MoneyBonus", 0.52);
		cc.addDefault("Enchants.1.PointsBonus", 0.33);
		cc.addDefault("Enchants.1.ExpBonus", 0.32);
		cc.addDefault("Enchants.1.Cost", 45);
		cc.addDefault("Enchants.1.Description", Arrays.asList("&7Increases money, points and exp bonus","&7From caught fish"," &7- Cost: %cost% Points"));
		cc.addDefault("Enchants.2.Name", "&3Stronger line");
		cc.addDefault("Enchants.2.AmountBonus", 0.12);
		cc.addDefault("Enchants.2.MoneyBonus", 0.24);
		cc.addDefault("Enchants.2.PointsBonus", 0.68);
		cc.addDefault("Enchants.2.ExpBonus", 0.53);
		cc.addDefault("Enchants.2.Cost", 35);
		cc.addDefault("Enchants.2.Description", Arrays.asList("&7Increases points and exp bonus","&7From caught fish"," &7- Cost: %cost% Points"));
		cc.addDefault("Enchants.3.Name", "&eLength of the fishing rod");
		cc.addDefault("Enchants.3.AmountBonus", 0.15);
		cc.addDefault("Enchants.3.MoneyBonus", 0.15);
		cc.addDefault("Enchants.3.PointsBonus", 0.15);
		cc.addDefault("Enchants.3.ExpBonus", 0.15);
		cc.addDefault("Enchants.3.Cost", 50);
		cc.addDefault("Enchants.3.Description", Arrays.asList("&7Increases all bonuses by 0.15%"," &7- Cost: %cost% Points"));
		cc.addDefault("Enchants.4.Name", "&9Convenience");
		cc.addDefault("Enchants.4.AmountBonus", 0.18);
		cc.addDefault("Enchants.4.MoneyBonus", 0.22);
		cc.addDefault("Enchants.4.PointsBonus", 0.25);
		cc.addDefault("Enchants.4.ExpBonus", 0.34);
		cc.addDefault("Enchants.4.Cost", 30);
		cc.addDefault("Enchants.4.Description", Arrays.asList("&7Increases exp bonus","&7From caught fish"," &7- Cost: %cost% Points"));
		}
	}
	
	public static void saveTranslations() {
		a.save();
	}
	public static void load() {
		cc.setHeader("Showcase of permission: Options -> Perms-Treasures -> Legendary -> WorldGuard-Regions/Residence: amazingfishing.treasures.legendary.<region/residence>");
		addDefault();
		cc.create();
		c=cc.getConfig();
	}
	
	public static void save() {
		cc.save();
	}
	public static void saveChatMe() {
		chat.save();
	}
	static ConfigAPI shopfile;
	
	public static void loadShop() {
		shopfile = TheAPI.getConfig("AmazingFishing","AmazingFishing-Shop.yml");
		shopfile.setHeader(
		"%player%   select player\n"
		+"%coins%   amount of coins\n");
			shopfile.addDefault("GUI.Bag.Icon", "CHEST");
			shopfile.addDefault("GUI.Bag.ModelData", "1");
			shopfile.addDefault("GUI.Bag.Name", "&6Bag");
			shopfile.addDefault("GUI.Bag.Lore", Arrays.asList("&7Click to open bag"));

			shopfile.addDefault("GUI.Points.Icon", "LAPIS_LAZULI");
			shopfile.addDefault("GUI.Points.ModelData", "1");
			shopfile.addDefault("GUI.Points.Name", "&9Points");
			shopfile.addDefault("GUI.Points.Lore", Arrays.asList("&7Currently you have %points% points"));
			
			shopfile.addDefault("GUI.BuyShop.Icon", "EMERALD");
			shopfile.addDefault("GUI.BuyShop.ModelData", "1");
			shopfile.addDefault("GUI.BuyShop.Name", "&eBuy shop");
			shopfile.addDefault("GUI.BuyShop.Lore", Arrays.asList("&7Open shop in which you can buy items"));

			shopfile.addDefault("GUI.SellShop.Icon", "COD_BUCKET");
			shopfile.addDefault("GUI.SellShop.ModelData", "1");
			shopfile.addDefault("GUI.SellShop.Name", "&eSell Shop");
			shopfile.addDefault("GUI.SellShop.Lore", Arrays.asList("&7Open shop in which you can sell fish"));
			
			shopfile.addDefault("GUI.Sell.Icon", "GOLD_INGOT");
			shopfile.addDefault("GUI.Sell.ModelData", "1");
			shopfile.addDefault("GUI.Sell.Name", "&eSell fish");
			shopfile.addDefault("GUI.Sell.Lore", Arrays.asList("&7Sell fish in shop"));
		if(!shopfile.existPath("Items")) {
			shopfile.addDefault("Items.0.Icon", "STONE_SWORD");
			shopfile.addDefault("Items.0.Name", "&7&lBeginner kit");
			shopfile.addDefault("Items.0.Description", Arrays.asList("&eCost %cost% points"));
			shopfile.addDefault("Items.0.Cost", 50);
			
			shopfile.addDefault("Items.0.Messages", Arrays.asList("&6You bought a &7&lBeginner kit&r &6for &a%cost% points"));
			
			shopfile.addDefault("Items.0.Item.0.Material", "Fishing_rod");
			shopfile.addDefault("Items.0.Item.0.Name", "&7Basic fishing rod");
			shopfile.addDefault("Items.0.Item.0.ModelData", "0");
			shopfile.addDefault("Items.0.Item.0.Amount", 1);
			shopfile.addDefault("Items.0.Item.0.HideEnchants", true);
			shopfile.addDefault("Items.0.Item.0.Enchants", Arrays.asList("Unbreaking 1"));
			
			shopfile.addDefault("Items.0.Item.1.Material", "Oak_Boat");
			shopfile.addDefault("Items.0.Item.1.ModelData", "1");
			shopfile.addDefault("Items.0.Item.1.HideAttributes", true);
			shopfile.addDefault("Items.0.Item.1.Unbreakable", true); //unused, but why not
			
			shopfile.addDefault("Items.0.Item.2.Material", "Apple");
			shopfile.addDefault("Items.0.Item.2.ModelData", "2");
			shopfile.addDefault("Items.0.Item.2.Name", "&cTasty apple");
			shopfile.addDefault("Items.0.Item.2.Amount", 3);
			shopfile.addDefault("Items.0.Item.2.Lore", Arrays.asList("&4&lEAT ME"));

			
			shopfile.addDefault("Items.1.Icon", "IRON_SWORD");
			shopfile.addDefault("Items.1.Name", "&e&lIron kit");
			shopfile.addDefault("Items.1.Description", Arrays.asList("&eCost %cost% points"));
			shopfile.addDefault("Items.1.Cost", 275);
			
			shopfile.addDefault("Items.1.Messages", Arrays.asList("&6You bought a &e&lIron kit&r &6for &a%cost% points"));
			shopfile.addDefault("Items.1.Commands", Arrays.asList("heal %player%"));
			
			shopfile.addDefault("Items.1.Item.0.Material", "Fishing_rod");
			shopfile.addDefault("Items.1.Item.0.ModelData", "3");
			shopfile.addDefault("Items.1.Item.0.Name", "&bPerfect fishing rod");
			shopfile.addDefault("Items.1.Item.0.Lore", Arrays.asList("&3Fishing 4 ever! <3"));
			shopfile.addDefault("Items.1.Item.0.Amount", 1);
			shopfile.addDefault("Items.1.Item.0.HideEnchants", true);
			shopfile.addDefault("Items.1.Item.0.Enchants", Arrays.asList("Unbreaking 4","Luck_of_sea 2"));
			
			shopfile.addDefault("Items.1.Item.1.Material", "Oak_Boat");
			shopfile.addDefault("Items.1.Item.1.Amount", 2);
			shopfile.addDefault("Items.1.Item.1.ModelData", "4");
			shopfile.addDefault("Items.1.Item.1.HideAttributes", true);
			shopfile.addDefault("Items.1.Item.1.Unbreakable", true); //unused, but why not
			
			shopfile.addDefault("Items.1.Item.2.Material", "Golden_Apple");
			shopfile.addDefault("Items.1.Item.2.Name", "&dSpecial apple");
			shopfile.addDefault("Items.1.Item.2.ModelData", "5");
			shopfile.addDefault("Items.1.Item.2.Lore", Arrays.asList("&5Really special!"));
			shopfile.addDefault("Items.1.Item.2.Amount", 7);
		}
		shopfile.create();
		shop=shopfile.getConfig();
	    
	}
	
	public static void saveShop() {
		shopfile.save();
	}
	public static boolean hasPerm(CommandSender s, String perm) {
		if(s.hasPermission(perm)) {
		return true;
		}else {
			msgCmd(s("NoPermissions").replace("%permission%", perm),s);
			return false;
		}
	}
	public static void Help(CommandSender s, String cmd, String path) {
		msgCmd("&e"+cmd+" &7- &5"+s("Help."+path),s);
	}
	public static String PlayerNotEx(String p) {
		return s("PlayerNotExist").replace("%player%", p).replace("%playername%", p);
	}
	public static CEnch getEnchantment(String string) {
		return map.get(string);
	}
	
}

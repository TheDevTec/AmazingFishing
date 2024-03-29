package me.devtec.amazingfishingOLD;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.devtec.amazingfishingOLD.construct.Enchant;
import me.devtec.amazingfishingOLD.construct.Fish;
import me.devtec.amazingfishingOLD.construct.FishType;
import me.devtec.amazingfishingOLD.construct.Junk;
import me.devtec.amazingfishingOLD.construct.Treasure;
import me.devtec.amazingfishingOLD.creation.CustomEnchantment;
import me.devtec.amazingfishingOLD.creation.CustomFish;
import me.devtec.amazingfishingOLD.creation.CustomJunk;
import me.devtec.amazingfishingOLD.creation.CustomTreasure;
import me.devtec.amazingfishingOLD.utils.AFKSystem;
import me.devtec.amazingfishingOLD.utils.Achievements;
import me.devtec.amazingfishingOLD.utils.AmazingFishingCommand;
import me.devtec.amazingfishingOLD.utils.Configs;
import me.devtec.amazingfishingOLD.utils.Create;
import me.devtec.amazingfishingOLD.utils.Manager;
import me.devtec.amazingfishingOLD.utils.Quests;
import me.devtec.amazingfishingOLD.utils.Achievements.Achievement;
import me.devtec.amazingfishingOLD.utils.Categories.Category;
import me.devtec.amazingfishingOLD.utils.Quests.Quest;
import me.devtec.amazingfishingOLD.utils.listeners.CatchFish;
import me.devtec.amazingfishingOLD.utils.listeners.EatFish;
import me.devtec.amazingfishingOLD.utils.placeholders.PAPILoader;
import me.devtec.amazingfishingOLD.utils.placeholders.Placeholders;
import me.devtec.amazingfishingOLD.utils.points.EconomyAPI;
import me.devtec.amazingfishingOLD.utils.points.UserPoints;
import me.devtec.amazingfishingOLD.utils.points.VaultPoints;
import me.devtec.amazingfishingOLD.utils.tournament.TournamentManager;
import me.devtec.amazingfishingOLD.utils.tournament.TournamentType;
import me.devtec.shared.Ref;
import me.devtec.shared.dataholder.Config;
import me.devtec.shared.placeholders.PlaceholderAPI;
import me.devtec.shared.scheduler.Tasker;
import me.devtec.shared.utility.ColorUtils;
import me.devtec.shared.utility.StringUtils;
import me.devtec.shared.utility.TimeUtils;
import me.devtec.shared.versioning.VersionUtils;
import me.devtec.theapi.bukkit.BukkitLoader;
import me.devtec.theapi.bukkit.Metrics;
import me.devtec.theapi.bukkit.commands.hooker.BukkitCommandManager;
import me.devtec.theapi.bukkit.game.ItemMaker;
import net.milkbowl.vault.economy.Economy;

public class Loader extends JavaPlugin {

	public static Loader plugin;
	public static Config tran, config, gui, shop;
	public static Config cod, puffer, tropic, salmon, quest, treasur, enchant, achievements, junk;
	protected static String prefix = Manager.getPluginName();
	public static DecimalFormat ff = new DecimalFormat("###,###.#", DecimalFormatSymbols.getInstance(Locale.ENGLISH)),
			intt = new DecimalFormat("###,###", DecimalFormatSymbols.getInstance(Locale.ENGLISH));

	public static ItemStack next, prev;

	@Override
	public void onEnable() {
		if (VersionUtils.getVersion(Bukkit.getPluginManager().getPlugin("TheAPI").getDescription().getVersion(), "10.5") == VersionUtils.Version.OLDER_VERSION) {
			Loader.msg(prefix + " &8*********************************************", Bukkit.getConsoleSender());
			Loader.msg(prefix + " &4SECURITY: &cYou are running on outdated version of plugin TheAPI", Bukkit.getConsoleSender());
			Loader.msg(prefix + " &4SECURITY: &cPlease update plugin TheAPI to latest version.", Bukkit.getConsoleSender());
			Loader.msg(prefix + "        &6https://www.spigotmc.org/resources/72679/", Bukkit.getConsoleSender());
			Loader.msg(prefix + " &8*********************************************", Bukkit.getConsoleSender());
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		plugin = this;

		if (Bukkit.getPluginManager().getPlugin("Vault") != null)
			vaultEconomyHooking();

		Configs.load();
		API.points = EconomyAPI.economy != null && config.getString("Options.PointsManager").equalsIgnoreCase("vault") ? new VaultPoints() : new UserPoints();

		prefix = tran.getString("prefix");

		new Metrics(this, 10630);
		reload(Bukkit.getConsoleSender(), false);

		// COMMAND
		Bukkit.getPluginManager().registerEvents(new EatFish(), this);
		Bukkit.getPluginManager().registerEvents(new CatchFish(), this);
		PluginCommand cmd = BukkitCommandManager.createCommand(config.getString("Command.Name"), this);
		cmd.setPermission(config.getString("Command.Permission"));
		AmazingFishingCommand amf = new AmazingFishingCommand();
		cmd.setExecutor(amf);
		cmd.setAliases(config.getStringList("Command.Aliases"));
		BukkitCommandManager.registerCommand(cmd);

		// PlaceholderAPI
		PAPILoader.load();

		// Automatic Tournaments
		if (config.getBoolean("Tournament.Automatic.Use"))
			if (config.getBoolean("Tournament.Automatic.AllWorlds"))
				new Tasker() {
					@Override
					public void run() {
						if (TournamentManager.start(null, TournamentType.RANDOM, TimeUtils.timeFromString(config.getString("Tournament.Automatic.Length")))) {
							String format = TournamentManager.get(null).getType().formatted(), path = TournamentManager.get(null).getType().configPath();
							for (Player p : BukkitLoader.getOnlinePlayers()) {
								for (String f : config.getStringList("Tournament.Start." + path + ".Broadcast.Messages"))
									Loader.msg(PlaceholderAPI.apply(f.replace("%type%", format), p.getUniqueId()), p);
								for (String f : config.getStringList("Tournament.Start." + path + ".Broadcast.Commands"))
									Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.apply(f.replace("%type%", format), p.getUniqueId()));
							}
						}
					}
				}.runRepeating(TimeUtils.timeFromString(config.getString("Tournament.Automatic.Period")), TimeUtils.timeFromString(config.getString("Tournament.Automatic.Period")));
			else
				new Tasker() {
					@Override
					public void run() {
						World w = Bukkit.getWorld(StringUtils.randomFromList(config.getStringList("Tournament.Automatic.Worlds")));
						if (TournamentManager.start(w, TournamentType.RANDOM, TimeUtils.timeFromString(config.getString("Tournament.Automatic.Length")))) {
							String format = TournamentManager.get(w).getType().formatted(), path = TournamentManager.get(w).getType().configPath();
							for (Player p : w.getPlayers()) {
								for (String f : config.getStringList("Tournament.Start." + path + ".Broadcast.Messages"))
									Loader.msg(PlaceholderAPI.apply(f.replace("%type%", format), p.getUniqueId()), p);
								for (String f : config.getStringList("Tournament.Start." + path + ".Broadcast.Commands"))
									Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.apply(f.replace("%type%", format), p.getUniqueId()));
							}
						}
					}
				}.runRepeating(TimeUtils.timeFromString(config.getString("Tournament.Automatic.Period")), TimeUtils.timeFromString(config.getString("Tournament.Automatic.Period")));
	}

	@Override
	public void onDisable() {
		// Unloading placeholders
		if (PAPILoader.papi_theapi != null) // Theapi PlaceholderExpansion
			PAPILoader.papi_theapi.unregister();

		AFKSystem.unload();

		Bukkit.getScheduler().cancelTask(Placeholders.task);
	}

	// VAULT HOOKING
	private void vaultEconomyHooking() {
		getLogger().info("[Economy] Looking for Vault economy service..");
		new Tasker() {
			@Override
			public void run() {
				if (getVaultEconomy()) {
					getLogger().info("[Economy] Found Vault economy service. " + ((Economy) EconomyAPI.economy).getName());
					cancel();
				}
			}
		}.runTimer(0, 20, 15);
	}

	private boolean getVaultEconomy() {
		try {
			RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(Economy.class);
			if (economyProvider != null)
				EconomyAPI.economy = economyProvider.getProvider();
			return EconomyAPI.economy != null;
		} catch (Exception e) {
			return false;
		}
	}

	public static void reload(CommandSender ss, boolean reload) {
		// RELOAD-CONFIG
		if (reload) {
			AFKSystem.unload();
			gui.reload();
			cod.reload();
			salmon.reload();
			puffer.reload();
			tropic.reload();
			quest.reload();
			achievements.reload();
			treasur.reload();
			junk.reload();
			enchant.reload();
			config.reload();
			tran.reload();
			prefix = tran.getString("prefix");
			Loader.next = ItemMaker.loadFromConfig(Loader.gui, "buttons.next");
			Loader.prev = ItemMaker.loadFromConfig(Loader.gui, "buttons.previous");
			API.points = config.getString("Options.PointsManager").equalsIgnoreCase("vault") ? new VaultPoints() : new UserPoints();
			Loader.msg(Create.text("reload").replace("%prefix%", getPrefix()), ss);
		}
		AFKSystem.load();
		Placeholders.loadTops();

		// FISH

		// PRE-LOAD
		Map<String, FishType> toRegister = new ConcurrentHashMap<>();
		FishType type = FishType.COD;
		for (String fish : cod.getKeys("cod"))
			try {
				toRegister.put(fish + ":" + type.ordinal(), type);
			} catch (Exception | NoSuchFieldError err) {
			}
		type = FishType.SALMON;
		for (String fish : salmon.getKeys("salmon"))
			try {
				toRegister.put(fish + ":" + type.ordinal(), type);
			} catch (Exception | NoSuchFieldError err) {
			}
		type = FishType.PUFFERFISH;
		for (String fish : puffer.getKeys("pufferfish"))
			try {
				toRegister.put(fish + ":" + type.ordinal(), type);
			} catch (Exception | NoSuchFieldError err) {
			}
		type = FishType.TROPICAL_FISH;
		for (String fish : tropic.getKeys("tropical_fish"))
			try {
				toRegister.put(fish + ":" + type.ordinal(), type);
			} catch (Exception | NoSuchFieldError err) {
			}

		// REMOVE-NOT-LOADED
		List<Fish> remove = new ArrayList<>();
		for (Entry<String, Fish> fish : API.fish.entrySet())
			if (fish.getValue() instanceof CustomFish && Ref.get(fish.getValue(), "data").equals(getData(fish.getValue().getType())))
				if (toRegister.containsKey(fish.getValue().getName() + ":" + fish.getValue().getType().ordinal()))
					toRegister.remove(fish.getValue().getName() + ":" + fish.getValue().getType().ordinal());
				else
					remove.add(fish.getValue());
		for (Fish s : remove)
			API.unregister(s);

		// REGISTER-NOT-LOADED
		for (Entry<String, FishType> s : toRegister.entrySet())
			API.register(new CustomFish(s.getKey().substring(0, s.getKey().length() - 2), s.getValue().name().toLowerCase(), s.getValue(), getData(s.getValue())));

		// CLEAR-CACHE
		Loader.msg(prefix + " Fish registered (" + toRegister.size() + ") & removed unregistered (" + remove.size() + ").", ss);
		toRegister.clear();
		remove.clear();

		// TREASURE

		// PRE-LOAD
		Set<String> toReg = treasur.getKeys("treasures");

		// REMOVE-NOT-LOADED
		List<Treasure> removeT = new ArrayList<>();
		for (Entry<String, Treasure> fish : API.treasure.entrySet())
			if (fish.getValue() instanceof CustomTreasure && Ref.get(fish.getValue(), "data").equals(treasur))
				if (toReg.contains(fish.getValue().getName()))
					toReg.remove(fish.getValue().getName());
				else
					removeT.add(fish.getValue());
		for (Treasure s : removeT)
			API.unregister(s);

		// REGISTER-NOT-LOADED
		for (String s : toReg)
			API.register(new CustomTreasure(s, treasur));

		// CLEAR-CACHE
		Loader.msg(prefix + " Treasures registered (" + toReg.size() + ") & removed unregistered (" + removeT.size() + ").", ss);
		toReg.clear();
		removeT.clear();

		// ENCHANTMENT

		// PRE-LOAD
		toReg = enchant.getKeys("enchantments");
		// REMOVE-NOT-LOADED
		List<String> removeE = new ArrayList<>();
		for (Entry<String, Enchant> fish : Enchant.enchants.entrySet())
			if (fish.getValue() != null && fish.getValue() instanceof CustomEnchantment)
				if (toReg.contains(fish.getValue().getName()))
					toReg.remove(fish.getValue().getName());
				else
					removeE.add(fish.getKey());
		for (String s : removeE)
			Enchant.enchants.remove(s);

		// REGISTER-NOT-LOADED
		for (String s : toReg)
			new CustomEnchantment(s, enchant.getString("enchantments." + s + ".name"), enchant.getInt("enchantments." + s + ".maxlevel"), enchant.getDouble("enchantments." + s + ".bonus.chance"),
					enchant.getDouble("enchantments." + s + ".bonus.amount"), enchant.getDouble("enchantments." + s + ".bonus.money"), enchant.getDouble("enchantments." + s + ".bonus.points"),
					enchant.getDouble("enchantments." + s + ".bonus.exp"), enchant.getStringList("enchantments." + s + ".description"), enchant.getDouble("enchantments." + s + ".cost"),
					enchant.getDouble("enchantments." + s + ".bonus.bitespeed"));
		// CLEAR-CACHE
		Loader.msg(prefix + " Enchantments registered (" + toReg.size() + ") & removed unregistered (" + removeE.size() + ").", ss);
		toReg.clear();
		removeE.clear();

		// QUESTS

		// PRE-LOAD
		toReg = quest.getKeys("quests");

		// REMOVE-NOT-LOADED
		for (Entry<String, Quest> quest : Quests.quests.entrySet())
			if (quest.getValue().getClass() == Quest.class)
				if (!toReg.contains(quest.getKey()))
					removeE.add(quest.getKey());
		for (String s : removeE)
			Quests.quests.remove(s);

		// REGISTER-NOT-LOADED
		for (String s : toReg)
			Quests.register(new Quest(s, quest));

		// CLEAR-CACHE
		Loader.msg(prefix + " Quests registered (" + toReg.size() + ") & removed unregistered (" + removeE.size() + ").", ss);
		toReg.clear();
		removeE.clear();

		if (quest.exists("categories")) {
			int old = 0;
			if (!Quests.categories.isEmpty()) {
				old = Quests.categories.size();
				Quests.categories.clear();
			}

			for (String category : quest.getKeys("categories"))
				Quests.addToCategory(new Category(category, quest));

			Loader.msg(prefix + " Quests categories registered (" + Quests.categories.size() + ") & removed unregistered (" + old + ").", ss);
		}

		// ACHIEVEMENTS

		// PRE-LOAD
		toReg = achievements.getKeys("achievements");

		// REMOVE-NOT-LOADED
		for (Entry<String, Achievement> ach : Achievements.achievements.entrySet())
			if (ach.getValue().getClass() == Achievement.class)
				if (!toReg.contains(ach.getKey()))
					removeE.add(ach.getKey());
		for (String s : removeE)
			Achievements.achievements.remove(s);
		// REGISTER-NOT-LOADED
		for (String s : toReg)
			if (Loader.achievements.exists("achievements." + s + ".icon"))
				Achievements.register(new Achievement(s, achievements));

		// CLEAR-CACHE
		Loader.msg(prefix + " Achievements registered (" + toReg.size() + ") & removed unregistered (" + removeE.size() + ").", ss);
		toReg.clear();
		removeE.clear();

		if (achievements.exists("categories")) {
			int old = Achievements.categories.size();
			Achievements.categories.clear();
			for (String category : achievements.getKeys("categories"))
				Achievements.addToCategory(new Category(category, achievements));

			Loader.msg(prefix + " Achievements categories registered (" + Achievements.categories.size() + ") & removed unregistered (" + old + ").", ss);

		}

		// JUNK

		// PRE-LOAD
		toReg = junk.getKeys("items");

		// REMOVE-NOT-LOADED
		List<Junk> removeJ = new ArrayList<>();
		for (Entry<String, Junk> junk : API.junk.entrySet())
			if (junk.getValue() instanceof CustomJunk && Ref.get(junk.getValue(), "data").equals(junk))
				if (toReg.contains(junk.getValue().getName()))
					toReg.remove(junk.getValue().getName());
				else
					removeJ.add(junk.getValue());
		for (Junk s : removeJ)
			API.unregister(s);

		// REGISTER-NOT-LOADED
		for (String s : toReg)
			API.register(new CustomJunk(s, "items", junk));

		// CLEAR-CACHE
		Loader.msg(prefix + " Junk registered (" + toReg.size() + ") & removed unregistered (" + removeJ.size() + ").", ss);
		toReg.clear();
		removeJ.clear();

		API.onReload.forEach(Runnable::run);
	}

	public static Config getData(FishType type) {
		switch (type) {
		case COD:
			return cod;
		case PUFFERFISH:
			return puffer;
		case SALMON:
			return salmon;
		case TROPICAL_FISH:
			return tropic;
		case JUNK:
			return junk;
		}
		return null;
	}

	public static void msg(String msg, CommandSender s) {
		s.sendMessage(replace(msg, s));
	}

	public static String replace(String text, CommandSender s) {
		return ColorUtils.colorize(PlaceholderAPI.apply(text.replace("%prefix%", getPrefix()).replace("%player%", s.getName()), s instanceof Player ? ((Player) s).getUniqueId() : null));
	}

	public static boolean has(CommandSender s, String permission) {
		if (s.hasPermission(permission))
			return true;
		msg(tran.getString("NoPerms").replace("%permission%", permission), s);
		return false;
	}

	public static void onAfk(Player p) {
		for (String s : Loader.config.getStringList("Options.AFK.Action.Afking"))
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replace(s, p));
	}

	public static void onAfkStart(Player p) {
		for (String s : Loader.config.getStringList("Options.AFK.Action.Start"))
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replace(s, p));
	}

	public static void onAfkStop(Player p) {
		for (String s : Loader.config.getStringList("Options.AFK.Action.Stop"))
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replace(s, p));
	}

	/*
	 * AmazingFishing: Statistics: Tournament: Played: int Placements: int //Počet
	 * kolikrát jsi se umístil na TOP 4 (dohromady) <TOURNAMENT>: Played: int
	 * Placement: <pozice 1-4>: int //Počet kolikrát jsi se umístil na určité pozici
	 * Treasures: Caught: int <TREASURE>: Caught: int Shop: Gained: Exp: double
	 * Money: double Points: double Records: <TYP>: <RYBA>: WEIGHT: double LENGTH:
	 * double Fish: Caught: int Eaten: int Sold: int <TYP>: Caught: int Eaten: int
	 * Sold: int <RYBA>: Caught: int Eaten: int Sold: int
	 * 
	 */
	public static String getPrefix() {
		return prefix;
	}
}

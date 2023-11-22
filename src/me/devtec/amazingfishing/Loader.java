package me.devtec.amazingfishing;

import java.time.LocalDate;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.devtec.amazingfishing.guis.MenuLoader;
import me.devtec.amazingfishing.listeners.CatchFish;
import me.devtec.amazingfishing.listeners.PlayerQuit;
import me.devtec.amazingfishing.player.achievements.Achievements;
import me.devtec.amazingfishing.player.points_economy.EconomyAPI;
import me.devtec.amazingfishing.player.points_economy.PointsManager;
import me.devtec.amazingfishing.player.points_economy.UserPoints;
import me.devtec.amazingfishing.player.points_economy.VaultPoints;
import me.devtec.amazingfishing.utils.Configs;
import me.devtec.amazingfishing.utils.MessageUtils;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.amazingfishing.utils.MetricsFishing;
import me.devtec.amazingfishing.utils.command.CommandsManager;
import me.devtec.amazingfishing.utils.placeholders.PlaceholderLoader;
import me.devtec.shared.scheduler.Tasker;
import me.devtec.shared.versioning.SpigotUpdateChecker;
import me.devtec.shared.versioning.VersionUtils.Version;
import net.milkbowl.vault.economy.Economy;

public class Loader extends JavaPlugin {

	public static void main(String[] args) {
		/*
		 * Scanner scanner = new Scanner(System.in); String input = "";
		 * 
		 * boolean up = true; for (int pos = 0; ( pos<= 15 && pos >= 0);) {
		 * 
		 * if(pos == 15 && up) up = false; if(pos == 0 && !up) up = true;
		 * 
		 * for(int i = 0; i < pos; i++) System.out.print(" ");
		 * 
		 * System.out.print("V");
		 * 
		 * input = scanner.nextLine(); // clicking enter
		 * 
		 * if(input != null && !input.isEmpty() && input.equalsIgnoreCase("a")) {
		 * 
		 * }
		 * 
		 * clearCconsole(); if(up) pos++; else pos--;
		 * 
		 * }
		 * 
		 * scanner.close();
		 */

		LocalDate today = LocalDate.now();

		LocalDate yesterday = LocalDate.parse(today.minusDays(1).toString());

		System.out.println(yesterday.toString() + " ; " + today.toString());
	}

	public static void clearCconsole() {
		// Print multiple blank lines to simulate clearing the console
		for (int i = 0; i < 50; i++) {
			System.out.println();
		}

	}

	public static Loader plugin;

	@Override
	public void onLoad() {

		plugin = this;
	}

	@Override
	public void onEnable() {

		// Loading configs
		MessageUtils.msgConsole("[" + this.getDescription().getName() + "] &fLoading configs", null);
		Configs.load();

		// Loading VAULT economy
		if (Bukkit.getPluginManager().getPlugin("Vault") != null)
			vaultEconomyHooking();

		// Checking for updates
		MessageUtils.msgConsole("%name% Checking for updates....", Placeholders.c().add("name", "[AmazingFishing]"));
		Version ver = new SpigotUpdateChecker(this.getDescription().getVersion(), 71148).checkForUpdates();
		if (ver == Version.OLDER_VERSION)
			MessageUtils.msgConsole("%name% &cNew update available... &fCurrent version %version%", Placeholders.c()
					.add("version", this.getDescription().getVersion()).add("name", "[AmazingFishing]"));
		// MessageUtils.msgConsole("%prefix% &fThere is not a new update available.",
		// Placeholders.c());

		// Loading bStats
		new MetricsFishing(this, 10630);

		// Loading events

		Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
		Bukkit.getPluginManager().registerEvents(new CatchFish(), this);

		// Loading command
		CommandsManager.register();

		// Loading Placeholder expansion
		MessageUtils.msgConsole("%name% &fLoading placeholders", Placeholders.c().add("name", "[AmazingFishing]"));
		PlaceholderLoader.load();

		// Loading fishing items
		MessageUtils.msgConsole("%name% &fLoading fishing items (Fish & Junk files)...",
				Placeholders.c().add("name", "[AmazingFishing]"));
		API.loadFishingItems();

		// Loading Achievements
		Achievements.loadAchievements();
		
		// Loading fishing GUIs
		MessageUtils.msgConsole("%name% &fLoading fishing GUIs", Placeholders.c().add("name", "[AmazingFishing]"));
		MenuLoader.loadMenus();

		// Loading Fishing Points
		loadPointsManager(null);

		plugin = this;
	}

	@Override
	public void onDisable() {

		// Unloading Placeholder expansion if loaded
		if (PlaceholderLoader.holder != null)
			PlaceholderLoader.holder.unregister();

	}

	public static void reload(CommandSender sender) {
		// Clearing Fisher cache list
		API.getFisherList().clear();
		MessageUtils.sendPluginMessage(sender, "&3%name% &7Loading &3configs",
				Placeholders.c().add("name", "[AmazingFishing]"), sender);
		// Reloading configs
		Configs.load();

		// Reloading Fish & Junk items
		MessageUtils.sendPluginMessage(sender, "&3%name% &7Loading &3fishing items &7(Fish & Junk files)...",
				Placeholders.c().add("name", "[AmazingFishing]"), sender);
		API.loadFishingItems();

		// Loading fishing GUIs
		MessageUtils.sendPluginMessage(sender, "&3%name% &7Loading fishing &3GUIs",
				Placeholders.c().add("name", "[AmazingFishing]"), sender);
		MenuLoader.loadMenus();
	}

	/*
	 * VAULT & FISHING POINTS
	 */
	// VAULT HOOKING
	private void vaultEconomyHooking() {
		getLogger().info("[AmazingFishing] Looking for Vault economy service..");
		new Tasker() {
			@Override
			public void run() {
				if (getVaultEconomy()) {
					getLogger().info("[AmazingFishing] Found Vault economy service. "
							+ ((Economy) EconomyAPI.economy).getName());
					cancel();
				}
			}
		}.runTimer(0, 20, 15);
	}

	private boolean getVaultEconomy() {
		try {
			RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager()
					.getRegistration(Economy.class);
			if (economyProvider != null)
				EconomyAPI.economy = economyProvider.getProvider();
			return EconomyAPI.economy != null;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Loads new {@link PointsManager}
	 * 
	 * @param newPointsmanager New {@link PointsManager} (or null - plugin will
	 *                         decide)
	 */
	public static void loadPointsManager(PointsManager newPointsmanager) {
		if (newPointsmanager == null) {
			newPointsmanager = EconomyAPI.economy != null
					&& Configs.config.getString("options.fishingPoints").equalsIgnoreCase("vault") ? new VaultPoints()
							: new UserPoints();
		}
		API.points = newPointsmanager;
	}
}

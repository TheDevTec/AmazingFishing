package me.devtec.amazingfishing;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.devtec.amazingfishing.guis.MenuLoader;
import me.devtec.amazingfishing.listeners.CatchFish;
import me.devtec.amazingfishing.listeners.PlayerQuit;
import me.devtec.amazingfishing.utils.AmazingFishingCommand;
import me.devtec.amazingfishing.utils.Configs;
import me.devtec.amazingfishing.utils.MessageUtils;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.amazingfishing.utils.MetricksFishing;
import me.devtec.amazingfishing.utils.placeholders.PlaceholderLoader;
import me.devtec.amazingfishing.utils.points.EconomyAPI;
import me.devtec.shared.scheduler.Tasker;
import me.devtec.shared.versioning.SpigotUpdateChecker;
import me.devtec.shared.versioning.VersionUtils.Version;
import me.devtec.theapi.bukkit.commands.hooker.BukkitCommandManager;
import net.milkbowl.vault.economy.Economy;

public class Loader extends JavaPlugin {

	public static void main(String[] args) {
		/*Version ver = new SpigotUpdateChecker("5.6.5", 71148).checkForUpdates();
        
		//System.out.println(ver.toString());
		//System.out.println("");
		
		ver = getVersion("1.0", "1.0.0");
		System.out.println(ver.toString());
		ver = getVersion("1.0.0", "1.0");
		System.out.println(ver.toString());
		ver = getVersion("0.0.1", "0.0.0.1");
		System.out.println(ver.toString());
		ver = getVersion("1.00.1", "1.0");
		System.out.println(ver.toString());
		ver = getVersion("1.0", "1.9.8");
		System.out.println(ver.toString());
		ver = getVersion("5.9", "5.9");
		System.out.println(ver.toString());*/
		
        /*double minLength = 25.0; // Minimum fish length
        double maxLength = 75.0; // Maximum fish length
        double minWeight = 0.5;  // Minimum fish weight
        double maxWeight = 50.0; // Maximum fish weight

        double fishLength = 75.0; // Fish length to compute weight for

        double estimatedWeight = Calculator.calculateWeight(fishLength, minLength, maxLength, minWeight, maxWeight);
        System.out.println("Estimated weight of the fish: " + estimatedWeight + " kg");*/
		
		/*FishingWeather w = FishingWeather.RAIN;
		if(w.equals(FishingWeather.SNOW))
			System.out.println("Pravda");*/
		
		/*List<String> fishList = new ArrayList<String>();
		fishList.add("First");
		fishList.add("Second");
		fishList.add("Third");
		fishList.add("Last");
		fishList.add("Aaaaa");
		
        int i = 0;
        for(String item : fishList) {
        	if((fishList.size()-1) <= i)
        		System.out.println(item);
        	i++;
    		System.out.println(i);
        }*/
		
		/*String s = createData(50, 90).toString(DataType.YAML);
		System.out.println(s);
		Config data = new Config();
		data.reload(s);
		for(String string : data.getKeys())
			System.out.println(string + " " + data.getString(string));
		
		System.out.println("\n\n JSON:");
		s = new Config().set("file", "file_name").set("fish", "fish_name")
				.set("type", "fish_type").set("weigth", 50).set("length", 90).toString(DataType.JSON);
		System.out.println(s);
		data.clear();
		data.reload(s);
		for(String string : data.getKeys())
			System.out.println(string + " " + data.get(string));*/
		
	}

	/*private static Config createData(double weight, double length) {
		return new Config().set("file", "file_name").set("fish", "fish_name")
					.set("type", "fish_type").set("weigth", weight).set("length", length);
	}*/
	
	public static Loader plugin;
	
	@Override
	public void onLoad() {
		
		plugin = this;
	}
	
	@Override
	public void onEnable() {

		// Loading configs
		MessageUtils.msgConsole("["+this.getDescription().getName()+"] &fLoading configs", null);
        Configs.load();
		
        //Loading VAULT economy
		if (Bukkit.getPluginManager().getPlugin("Vault") != null)
			vaultEconomyHooking();
		
		// Checking for updates
		MessageUtils.msgConsole("%name% Checking for updates....", Placeholders.c().add("name", "[AmazingFishing]"));
        Version ver = new SpigotUpdateChecker(this.getDescription().getVersion(), 71148).checkForUpdates();
        if(ver == Version.OLDER_VERSION)
        	MessageUtils.msgConsole("%name% &cNew update available... &fCurrent version %version%", Placeholders.c().add("version", this.getDescription().getVersion()).add("name", "[AmazingFishing]"));
        //MessageUtils.msgConsole("%prefix% &fThere is not a new update available.", Placeholders.c());
        
        //Loading bStats
        new MetricksFishing(this, 10630);
        
        // Loading events

		Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
		Bukkit.getPluginManager().registerEvents(new CatchFish(), this);
		
		// Loading command
		PluginCommand cmd = BukkitCommandManager.createCommand(Configs.config.getString("command.name"), this);
		cmd.setPermission(Configs.config.getString("command.permission"));
		AmazingFishingCommand amf = new AmazingFishingCommand();
		cmd.setExecutor(amf);
		cmd.setAliases(Configs.config.getStringList("command.aliases"));
		BukkitCommandManager.registerCommand(cmd);
        
		//Loading Placeholder expansion
		MessageUtils.msgConsole("%name% &fLoading placeholders", Placeholders.c().add("name", "[AmazingFishing]"));
		PlaceholderLoader.load();
		
		// Loading fishing items
		MessageUtils.msgConsole("%name% &fLoading fishing items (Fish & Junk files)...", Placeholders.c().add("name", "[AmazingFishing]"));
		API.loadFishingItems();
		
		// Loading fishing GUIs
		MessageUtils.msgConsole("%name% &fLoading fishing GUIs", Placeholders.c().add("name", "[AmazingFishing]"));
		MenuLoader.loadMenus();

		plugin = this;
	}
	
	
	@Override
	public void onDisable() {
		
		// Unloading Placeholder expansion if loaded
		if(PlaceholderLoader.holder != null)
			PlaceholderLoader.holder.unregister();
	}
	
	public static void reload() {
		//Reloading configs
        Configs.load();
        //Reloading Fish & Junk items
		API.loadFishingItems();
		//Clearing Fisher cache list
		API.getFisherList().clear();
		//Loading fishing GUIs
		MenuLoader.loadMenus();
	}
	
	// VAULT HOOKING
	private void vaultEconomyHooking() {
		getLogger().info("[AmazingFishing] Looking for Vault economy service..");
		new Tasker() {
			@Override
			public void run() {
				if (getVaultEconomy()) {
					getLogger().info("[AmazingFishing] Found Vault economy service. " + ((Economy) EconomyAPI.economy).getName());
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
}

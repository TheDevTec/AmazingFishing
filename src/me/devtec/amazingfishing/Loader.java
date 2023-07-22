package me.devtec.amazingfishing;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.devtec.amazingfishing.listeners.CatchFish;
import me.devtec.amazingfishing.listeners.PlayerQuit;
import me.devtec.amazingfishing.utils.Calculator;
import me.devtec.amazingfishing.utils.Configs;
import me.devtec.amazingfishing.utils.MessageUtils;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.amazingfishing.utils.placeholders.PlaceholderLoader;
import me.devtec.shared.utility.ParseUtils;
import me.devtec.shared.versioning.SpigotUpdateChecker;
import me.devtec.shared.versioning.VersionUtils.Version;

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
	}

	
	public static Version getVersion(String version, String compareVersion) {
		if (version == null || compareVersion == null)
			return Version.UKNOWN;

		version = version.replaceAll("[^0-9.]+", "").trim();
		compareVersion = compareVersion.replaceAll("[^0-9.]+", "").trim();

		if (version.isEmpty() || compareVersion.isEmpty())
			return Version.UKNOWN;

		String[] primaryVersion = version.split("\\.");
		String[] compareToVersion = compareVersion.split("\\.");
		
		//System.out.println("MAX: "+Math.max(primaryVersion.length, compareToVersion.length));
		int max = Math.max(primaryVersion.length, compareToVersion.length);
		for (int i = 0; i <= max; ++i) {
			String number = i >= primaryVersion.length ? "0" : "1" + primaryVersion[i];
			//System.out.println("Compare length | i: "+compareToVersion.length+" | "+i);
			if (compareToVersion.length <= i) {
				//System.out.println("BREAK "+compareToVersion.length+" "+i);
				if(compareToVersion.length == i && compareToVersion.length == max)
					break;
				return Version.NEWER_VERSION;
			}
			//System.out.println(ParseUtils.getInt(number)+ ";"+ParseUtils.getInt("1" + compareToVersion[i]));
			if (ParseUtils.getInt(number) > ParseUtils.getInt("1" + compareToVersion[i]))
				return Version.NEWER_VERSION;
			if (ParseUtils.getInt(number) < ParseUtils.getInt("1" + compareToVersion[i]))
				return Version.OLDER_VERSION;
		}
		return Version.SAME_VERSION;
	}
	
	
	public static Loader plugin;
	
	@Override
	public void onLoad() {
		
		plugin = this;
	}
	
	@Override
	public void onEnable() {

		// Loading configs
		MessageUtils.msgConsole("&fLoading configs...", null);
        Configs.load();
        
		MessageUtils.msgConsole("%prefix% &fEnabling plugin...", Placeholders.c());
		
		// Checking for updates
		MessageUtils.msgConsole("Checking for updates....", Placeholders.c());
        Version ver = new SpigotUpdateChecker(this.getDescription().getVersion(), 71148).checkForUpdates();
        if(ver == Version.OLDER_VERSION)
        	MessageUtils.msgConsole("%prefix% &cNew update available... &fCurrent version %version%", Placeholders.c().add("version", this.getDescription().getVersion()));
        //MessageUtils.msgConsole("%prefix% &fThere is not a new update available.", Placeholders.c());
        
        
        // Loading events

		Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
		Bukkit.getPluginManager().registerEvents(new CatchFish(), this);
        
		//Loading Placeholder expansion
		PlaceholderLoader.load();
		
		// Loading fishing items
		MessageUtils.msgConsole("%prefix% &fLoading fishing items (Fish & Junk files)...", Placeholders.c());
		API.loadFishingItems();
		
		plugin = this;
	}
	
	
	@Override
	public void onDisable() {
		
		// Unloading Placeholder expansion if loaded
		if(PlaceholderLoader.holder != null)
			PlaceholderLoader.holder.unregister();
	}

}

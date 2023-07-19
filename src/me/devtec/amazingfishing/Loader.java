package me.devtec.amazingfishing;

import org.bukkit.plugin.java.JavaPlugin;

import me.devtec.amazingfishing.utils.Configs;
import me.devtec.amazingfishing.utils.MessageUtils;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.amazingfishing.utils.placeholders.PlaceholderLoader;
import me.devtec.shared.versioning.SpigotUpdateChecker;
import me.devtec.shared.versioning.VersionUtils.Version;

public class Loader extends JavaPlugin {

	public static void main(String[] args) {
		Version ver = new SpigotUpdateChecker("5.4", 71148).checkForUpdates();
        
		
		System.out.print(ver.toString());
	}
	
	
	public static Loader plugin;
	
	@Override
	public void onLoad() {
		
		plugin = this;
	}
	
	@Override
	public void onEnable() {
		MessageUtils.msgConsole("%prefix% &fEnabling plugin...", Placeholders.c());
		
		// Checking for updates
		MessageUtils.msgConsole("Checking for updates....", Placeholders.c());
        Version ver = new SpigotUpdateChecker(this.getDescription().getVersion(), 71148).checkForUpdates();
        if(ver == Version.OLDER_VERSION)
        	MessageUtils.msgConsole("%prefix% &cNew update available... &fCurrent version %version%", Placeholders.c().add("version", this.getDescription().getVersion()));
        //MessageUtils.msgConsole("%prefix% &fThere is not a new update available.", Placeholders.c());
        
        
		// Loading configs
		MessageUtils.msgConsole("%prefix% &fLoading configs...", Placeholders.c());
        Configs.load();
        
        
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

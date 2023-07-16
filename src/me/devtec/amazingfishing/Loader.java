package me.devtec.amazingfishing;

import org.bukkit.plugin.java.JavaPlugin;

import me.devtec.amazingfishing.utils.placeholders.PlaceholderLoader;

public class Loader extends JavaPlugin {

	public static Loader plugin;
	
	@Override
	public void onLoad() {
		
		plugin = this;
	}
	
	@Override
	public void onEnable() {
		
		//Loading Placeholder expansion
		PlaceholderLoader.load();
		
		
		plugin = this;
	}
	
	
	@Override
	public void onDisable() {
		
		// Unloading Placeholder expansion if loaded
		if(PlaceholderLoader.holder != null)
			PlaceholderLoader.holder.unregister();
	}

}

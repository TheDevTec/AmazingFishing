package me.devtec.amazingfishing;

import org.bukkit.plugin.java.JavaPlugin;

import me.devtec.amazingfishing.fishing.enums.FishingTime;
import me.devtec.amazingfishing.utils.UpdateChecker;
import me.devtec.amazingfishing.utils.placeholders.PlaceholderLoader;

public class Loader extends JavaPlugin {

	public static void main(String[] args) {
		FishingTime time = FishingTime.value("DAY");
		
		System.out.print(time.toString());
	}
	
	
	public static Loader plugin;
	
	@Override
	public void onLoad() {
		
		plugin = this;
	}
	
	@Override
	public void onEnable() {
		
        new UpdateChecker(this, 71148).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
            	//TODO - předělat zprávy
                getLogger().info("There is not a new update available.");
            } else {
                getLogger().info("There is a new update available.");
            }
        });
		
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

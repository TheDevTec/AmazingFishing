package me.devtec.amazingfishing;

import org.bukkit.plugin.java.JavaPlugin;

import me.devtec.amazingfishing.fishing.enums.FishingTime;
import me.devtec.amazingfishing.utils.MessageUtils;
import me.devtec.amazingfishing.utils.UpdateChecker;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
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
		MessageUtils.msgConsole("%prefix% &fEnabling plugin...", Placeholders.c());
		// Checking for updates
        new UpdateChecker(this, 71148).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
            	// not sending a new message when this is newest version?
        		//MessageUtils.msgConsole("%prefix% &fThere is not a new update available.", Placeholders.c());
            } else {
        		MessageUtils.msgConsole("%prefix% &cNew update available... &fCurrent version %version%", Placeholders.c().add("version", this.getDescription().getVersion()));
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

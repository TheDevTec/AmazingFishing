package me.devtec.amazingfishing.construct;

import java.util.List;

import org.bukkit.block.Biome;

public interface Treasure {
	public String getName();

	public String getDisplayName();

	public List<String> getMessages();

	public List<String> getCommands();
	
	public List<Biome> getBiomes();
	
	public double getChance();
	
	public String getPermission();
	
	public FishTime getCatchTime();
	
	public FishWeather getCatchWeather();
}

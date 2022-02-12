package me.devtec.amazingfishing.construct;

import java.util.List;

import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

public interface Treasure {
	public String getName();

	public String getDisplayName();

	public List<String> getMessages();

	public List<String> getCommands();
	
	public List<Biome> getBiomes();

	public List<Biome> getBlockedBiomes();
	
	public double getChance();
	
	public String getPermission();
	
	public FishTime getCatchTime();
	
	public FishWeather getCatchWeather();

	public default boolean isAllowedToCatch(Player player, Biome biome, boolean hasStorm, boolean thunder, long time) {
		return (getPermission()==null || getPermission()!=null && player.hasPermission(getPermission())) &&
				(getBiomes().isEmpty()||getBiomes().contains(biome)) &&
				(getBlockedBiomes().isEmpty()|| !getBlockedBiomes().contains(biome)) &&
				(getCatchTime()==FishTime.DAY && time <= 12000 || getCatchTime()==FishTime.NIGHT && time > 12000 || getCatchTime()==FishTime.EVERY)
				&& (getCatchWeather()==FishWeather.EVERY|| hasStorm&&getCatchWeather()==FishWeather.RAIN|| thunder&&getCatchWeather()==FishWeather.THUNDER|| !hasStorm&&getCatchWeather()==FishWeather.SUN);
	}
}

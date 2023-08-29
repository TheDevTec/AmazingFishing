package me.devtec.amazingfishing.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.fishing.enums.FishingTime;
import me.devtec.amazingfishing.fishing.enums.FishingWeather;

/**
 * This class is used to check if player is fishing at the same biome, time, weather, ... </br>
 * If you wan't to check if the situation is the same, use <code>isTheSame()</code> or <code>check()</code> boolean method.</br>
 * If the situation is not the same you can use <code>update()</code> method to update conditions.
 * Please don't use this if you do not know when to use it. It should be used when you are updating list of 
 * fishes that the player can catch at the moment. If condition are changing, the fish list is changing too (probably).
 */
public class FisherSituation {
	
	private Player player;
	
	private Biome biome;
	private FishingTime time;
	private FishingWeather weather;
	
	private boolean first_time;
	
	public FisherSituation(Player player) {
		this.player = player;
		update(player.getLocation());
		first_time = true;
	}
	/**
	 * Updates biome, time and weather status.
	 */
	public void update(Location hook_location) {
		biome = player.getWorld().getBiome(hook_location);
		time = FishingTime.getNow(hook_location.getWorld().getTime());
		weather = FishingWeather.getWeather(player);
	}
	
	/** Checking if player is in the same biome, time and weather
	 * @return If not, return false
	 */
	public boolean isTheSame(Location hook_location) {
		return check(hook_location);
	}
	/** Checking if player is in the same biome, time and weather
	 * @return If not, return false
	 */
	public boolean check(Location hook_location) {
		//Checking if this FisherSituation is freshly generated. If yes, the situation will probably be the same...
		// In that case we want to check if player can catch item or something... :D
		if(first_time) {
			first_time = false;
			return false;
		}
		Bukkit.broadcastMessage("Situation check: B "+(biome == player.getWorld().getBiome(hook_location))+
				" T "+(time == FishingTime.getNow(hook_location.getWorld().getTime()))+
				" W "+(weather == FishingWeather.getWeather(player)));
		return biome == player.getWorld().getBiome(hook_location) &&
				time == FishingTime.getNow(hook_location.getWorld().getTime()) &&
				weather == FishingWeather.getWeather(player);
	}
	
	public Biome getBiome() {
		return biome;
	}
	public FishingTime getTime() {
		return time;
	}
	public FishingWeather getWeather() {
		return weather;
	}
}


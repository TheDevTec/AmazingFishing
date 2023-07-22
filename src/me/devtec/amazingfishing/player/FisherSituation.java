package me.devtec.amazingfishing.player;

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
	
	public FisherSituation(Player player) {
		this.player = player;
		update();
	}
	/**
	 * Updates biome, time and weather status.
	 */
	public void update() {
		biome = player.getWorld().getBiome(player.getLocation());
		time = FishingTime.getNow(player.getWorld().getTime());
		weather = FishingWeather.getWeather(player);
	}
	
	/** Checking if player is in the same biome, time and weather
	 * @return If not, return false
	 */
	public boolean isTheSame() {
		return check();
	}
	/** Checking if player is in the same biome, time and weather
	 * @return If not, return false
	 */
	public boolean check() {
		return biome == player.getWorld().getBiome(player.getLocation()) &&
				time == FishingTime.getNow(player.getWorld().getTime()) &&
				weather == FishingWeather.getWeather(player);
	}
}


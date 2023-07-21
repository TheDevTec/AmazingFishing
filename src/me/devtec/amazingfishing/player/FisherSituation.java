package me.devtec.amazingfishing.player;

import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.fishing.enums.FishingTime;
import me.devtec.amazingfishing.fishing.enums.FishingWeather;

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


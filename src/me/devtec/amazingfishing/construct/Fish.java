package me.devtec.amazingfishing.construct;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.shared.dataholder.Config;

public interface Fish {
	public String getName();

	public FishType getType();

	public String getDisplayName();

	public String getCalculator(Calculator type);

	public List<String> getMessages(FishAction action);

	public List<String> getCommands(FishAction action);

	public List<Biome> getBiomes();

	public List<Biome> getBlockedBiomes();

	public List<String> getEnchantments();

	public double getChance();

	public String getPermission();

	public FishTime getCatchTime();

	public FishWeather getCatchWeather();

	public int getModel();

	public Config createData(double weight, double length);

	public Config createData(double weight, double length, double money, double points, double exp);

	public boolean isInstance(Config data);

	public ItemStack createItem(double height, double length);

	public ItemStack createItem(double weight, double length, Player p, Location hook);

	public ItemStack createItem(double height, double length, double money, double points, double exp);

	public ItemStack createItem(double weight, double length, double money, double points, double exp, Player p, Location hook);

	public ItemStack preview(Player p);

	public double getWeight();

	public double getLength();

	public double getMinWeight();

	public double getMinLength();

	public double getMoney();

	public double getPoints();

	public double getXp();

	public double getFood();

	public CatchFish createCatchFish(Config data);

	boolean isFood();

	public default boolean isAllowedToCatch(Player player, Biome biome, boolean hasStorm, boolean thunder, long time) {
		return (getPermission() == null || getPermission().isEmpty() || getPermission() != null && player.hasPermission(getPermission())) && (getBiomes().isEmpty() || getBiomes().contains(biome))
				&& (getBlockedBiomes().isEmpty() || !getBlockedBiomes().contains(biome))
				&& (getCatchTime() == FishTime.DAY && time <= 12000 || getCatchTime() == FishTime.NIGHT && time > 12000 || getCatchTime() == FishTime.EVERY) && (getCatchWeather() == FishWeather.EVERY
						|| hasStorm && getCatchWeather() == FishWeather.RAIN || thunder && getCatchWeather() == FishWeather.THUNDER || !thunder && !hasStorm && getCatchWeather() == FishWeather.SUN);
	}

}

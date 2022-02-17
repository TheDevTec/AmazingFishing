package me.devtec.amazingfishing.construct;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.theapi.utils.datakeeper.Data;

public interface Junk {

	public String getName();
	
	public String getDisplayName();
	
	public FishType getType();

	public int getAmount();
	
	public List<String> getLore();
	
	public List<String> getMessages(FishAction action);

	public List<String> getCommands(FishAction action);
	
	public List<Biome> getBiomes();

	public List<Biome> getBlockedBiomes();
	
	public String getCalculator(Calculator type);
	
	public double getChance();
	
	public String getPermission();
	
	public FishTime getCatchTime();
	
	public FishWeather getCatchWeather();
	
	public int getModel();
	
	public double getWeight();

	public double getLength();
	
	public double getMinWeight();

	public double getMinLength();
	
	public double getFood();
	
	boolean isFood();
	
	boolean hasWeight();
	
	boolean hasLength();
	
	boolean isInstance(Data data);
	
	public ItemStack create(double weight, double length, Player p, Location hook);

	public ItemStack createItem(double weight, double length, Player p, Location hook);

	public ItemStack createItem(Player p, Location hook);
	
	public ItemStack preview(Player p);
	
	boolean show();
	
	public List<String> getEnchantments();

	public default boolean isAllowedToCatch(Player player, Biome biome, boolean hasStorm, boolean thunder, long time) {
		return (getPermission()==null || getPermission()!=null && player.hasPermission(getPermission())) &&
				(getBiomes().isEmpty()||getBiomes().contains(biome)) &&
				(getBlockedBiomes().isEmpty()|| !getBlockedBiomes().contains(biome)) &&
				(getCatchTime()==FishTime.DAY && time <= 12000 || getCatchTime()==FishTime.NIGHT && time > 12000 || getCatchTime()==FishTime.EVERY)
				&& (getCatchWeather()==FishWeather.EVERY|| hasStorm&&getCatchWeather()==FishWeather.RAIN|| thunder&&getCatchWeather()==FishWeather.THUNDER|| !hasStorm&&getCatchWeather()==FishWeather.SUN);
	}
}

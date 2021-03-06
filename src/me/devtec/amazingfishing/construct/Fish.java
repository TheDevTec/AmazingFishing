package me.devtec.amazingfishing.construct;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.theapi.utils.datakeeper.Data;

public interface Fish {
	public String getName();
	
	public FishType getType();

	public String getDisplayName();

	public String getCalculator(Calculator type);

	public List<String> getMessages(FishAction action);

	public List<String> getCommands(FishAction action);
	
	public List<Biome> getBiomes();

	public List<Biome> getBlockedBiomes();
	
	public double getChance();
	
	public String getPermission();
	
	public FishTime getCatchTime();
	
	public FishWeather getCatchWeather();
	
	public int getModel();
	
	public Data createData(double weight, double length);
	
	public Data createData(double weight, double length, double money, double points, double exp);
	
	public boolean isInstance(Data data);
	
	public boolean isHead();

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
	
	public CatchFish createCatchFish(Data data);

	boolean hasFoodSet();

}

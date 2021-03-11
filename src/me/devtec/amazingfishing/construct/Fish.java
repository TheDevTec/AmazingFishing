package me.devtec.amazingfishing.construct;

import java.util.List;

import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import me.devtec.theapi.utils.datakeeper.Data;

public interface Fish {
	public String getName();
	
	public FishType getType();

	public String getDisplayName();

	public List<String> getMessages(FishAction action);

	public List<String> getCommands(FishAction action);
	
	public List<Biome> getBiomes();
	
	public double getChance();
	
	public String getPermission();
	
	public FishTime getCatchTime();
	
	public FishWeather getCatchWeather();
	
	public int getModel();
	
	public Data createData(double weight, double length);
	
	public boolean isInstance(Data data);

	public ItemStack createItem(double height, double length);

	public double getWeigth();

	public double getLength();

	public double getMinWeigth();

	public double getMinLength();
	
	public double getMoney();

	public double getPoints();
	
	public double getXp();
}

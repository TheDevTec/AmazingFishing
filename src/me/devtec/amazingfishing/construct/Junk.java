package me.devtec.amazingfishing.construct;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import me.devtec.theapi.utils.datakeeper.Data;

public interface Junk {

	public String getName();
	
	public String getDisplayName();
	
	public Material getType();
	
	public String getHead();
	
	public boolean isHead();

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
	
	public List<String> getEnchantments();
	
	public List<ItemFlag> getFlags();
}

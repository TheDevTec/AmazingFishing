package me.devtec.amazingfishing.fishing;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.fishing.enums.FishType;
import me.devtec.amazingfishing.fishing.enums.FishingTime;
import me.devtec.amazingfishing.fishing.enums.FishingWeather;
import me.devtec.amazingfishing.utils.Configs;
import me.devtec.shared.dataholder.Config;

/**
 * This is PARENT class for {@link Fish} and {@link Junk} classes. {@link Fish} and {@link Junk} classes have a small differences!
 */
abstract class FishingItem {

	public FishingItem(Config file, FishType type) {
		setConfig(file);
		setType(type);
	}
	
	/*
	 *  CONFIGURATION FILE
	 */
	// File with all the configuration
	private Config file;
	// Getting Configuration file
	public Config getConfig() {return file;}
	// Setting Configuration file
	public void setConfig(Config newFile) {file = newFile;}
	
	/*
	 *  FISH TYPE
	 */
	// Type of item
	private FishType type;
	public FishType getType()  { return type; }
	public void setType(FishType newType)  { type = newType; }
	
	/*
	 * PERMISSIONS
	 */
	protected String def_perm_path; //Default permission path for junk or fish items
	abstract void setDefaultPermissionPath(); //In each file you need to set special permission path
	/**
	 * @return Gets default permission from Config.yml
	 */
	public String getDefaultPermission() {
		return Configs.config.exists(def_perm_path) ? 
				Configs.config.getString(def_perm_path) :
					"";
	}
	/** Get permission from item configuration file
	 * @return 
	 */
	public String getPermission() {
		return file.exists("permission") ? 
				file.getString("permission") : "";
	}
	/** Set new permission for this item. This is going to rewrite <code>permission</code> in file.
	 * @param newPermission
	 * @return
	 */
	public boolean setPermission(String newPermission) {
		if(newPermission != null) {
			file.set("permission", newPermission);
			file.save();
			return true;
		}
		Bukkit.getLogger().warning("[AmazingFishing] There was an error when setting new item's PERMISSION. newPermission parammeter is NULL. Item's file is: "+file.getFile().getName());
		return false;
		
	}
	/** If {@link Player} has permission to catch this item
	 * @param player - player that is fishing
	 * @return true/false
	 */
	public abstract boolean hasPermission(Player player);

	
	/*
	 *   CHANCE
	 *   What chance a player has of catching this item
	 */
	/** Get chance from item configuration file
	 * @return Returns -1 if there is no 'chance' configuration in file.
	 */
	public double getChance() {
		return file.exists("chance") ? file.getDouble("chance") : -1;
	}
	/** Set new chance. This is going to rewrite <code>chance</code> in file.
	 * @param newChance New chance that will be saved.
	 */
	public void setChance(double newChance) {
		if(newChance < -1)
			newChance = -1; //-1 is not a chance, 0 is also not a chance but official :D
		file.set("chance", newChance);
		file.save();
		}
	
	/*
	 * NAME
	 */
	public String getName() {
		return file.getString("name");
	}
	/** Set new name. This is going to rewrite <code>name</code> in file.
	 * @param newName New name that will be saved
	 */
	public void setName(String newName) {
		if(newName != null) {
			file.set("name", newName);
			file.save();
		}
		else
			Bukkit.getLogger().warning("[AmazingFishing] There was an error when setting new item's NAME. newName parammeter is NULL. Item's file is: "+file.getFile().getName());
	}
	
	/*
	 *  ITEMS
	 */
	//Why abstract? You need to apply placeholders in lore, JUNK and FISH items won't have the same placeholders
	abstract ItemStack getItem();
	
	abstract ItemStack getPreviewItem();
	
	/*
	 *  BIOMES
	 */
	/** Get list of {@link Biome}s from file.
	 * @return Empty list if there are no biomes in file.
	 */
	public List<Biome> getBiomes() {
		List<Biome> biomes = new ArrayList<>();
		for (String biome : file.getStringList("biomes"))
			try {
				biomes.add(Biome.valueOf(biome.toUpperCase()));
			} catch (Exception | NoSuchFieldError e) {
			}
		return biomes;
	}
	/** Get list of blocked {@link Biome}s from file.
	 * @return Empty list if there are no blocked biomes in file.
	 */
	public List<Biome> getBlockedBiomes() {
		List<Biome> biomes = new ArrayList<>();
		for (String biome : file.getStringList("blockedBiomes"))
			try {
				biomes.add(Biome.valueOf(biome.toUpperCase()));
			} catch (Exception | NoSuchFieldError e) {
			}
		return biomes;
	}
	
	/*
	 *  CONDITIONS
	 */
	// TIME
	private FishingTime time;
	
	/** Gets {@link FishingTime} from configuration file.
	 * @return {@link FishingTime}
	 */
	public FishingTime getTime() {
		//If time is not null -> Check if there is any change
		if(time != null)
			if(!file.exists("conditions.time"))
				time = FishingTime.ANY;
			else
				if(!time.toString().equalsIgnoreCase(file.getString("conditions.time"))) //If there is change in coniguration file
					time = FishingTime.value(file.getString("conditions.time"));
		//If time is null -> Load time from configuration file
		if(time == null)
			if(file.exists("conditions.time"))
				time = FishingTime.value(file.getString("conditions.time"));
			else
				time = FishingTime.ANY;
		
		return time;
	}
	/** Set new {@link FishingTime}. This is going to rewrite <code>conditions.time</code> in file.
	 * @param newTime New time that will be saved.
	 */
	public void setTime(FishingTime newTime) {
		//if newTime is somehow null... how? who knows...
		if(newTime == null) {
			Bukkit.getLogger().warning("[AmazingFishing] There was an error when setting new item's TIME. newTime parammeter is NULL. Item's file is: "+file.getFile().getName());
			return;
		}
		//If time and newTime are the same...
		if(time == newTime)
			return;
		time = newTime;
		file.set("conditions.time", newTime.toString());
		file.save();
	}
	
	// WEATHER
	private FishingWeather weather;

	/** Gets {@link FishingWeather} from configuration file.
	 * @return {@link FishingWeather}
	 */
	public FishingWeather getWeather() {
		//If time is not null -> Check if there is any change
		if(weather != null)
			if(!file.exists("conditions.weather"))
				weather = FishingWeather.ANY;
			else
				if(!weather.toString().equalsIgnoreCase(file.getString("conditions.weather"))) //If there is change in coniguration file
					weather = FishingWeather.value(file.getString("conditions.weather"));
		//If time is null -> Load time from configuration file
		if(weather == null)
			if(file.exists("conditions.weather"))
				weather = FishingWeather.value(file.getString("conditions.weather"));
			else
				weather = FishingWeather.ANY;
		
		return weather;
	}

	/** Set new {@link FishingWeather}. This is going to rewrite <code>conditions.weather</code> in file.
	 * @param newTime New weather that will be saved.
	 */
	public void setWeather(FishingWeather newWeather) {
		//if newTime is somehow null... how? who knows...
		if(newWeather == null) {
			Bukkit.getLogger().warning("[AmazingFishing] There was an error when setting new item's TIME. newTime parammeter is NULL. Item's file is: "+file.getFile().getName());
			return;
		}
		//If time and newTime are the same...
		if(weather == newWeather)
			return;
		weather = newWeather;
		file.set("conditions.time", newWeather.toString());
		file.save();
	}
	
	
	/*
	 *  MESSAGES
	 */
	
	public List<String> getMessagesCatchRaw() {
		return file.getStringList("messages.catch");
	}
	public List<String> getMessagesEatRaw() {
		return file.getStringList("messages.eat");
	}
	public List<String> getMessagesSellhRaw() {
		return file.getStringList("messages.sell");
	}

	
	/*
	 *  COMMANDS
	 */
	
	public List<String> getCommandsCatchRaw() {
		return file.getStringList("commands.catch");
	}
	public List<String> getCommandsEatRaw() {
		return file.getStringList("commands.eat");
	}
	public List<String> getCommandsSellhRaw() {
		return file.getStringList("commands.sell");
	}
	
	
	/*
	 *  SHOP OPTIONS
	 */
	
	/** If item can be sold in shop.
	 * @return Value from configuration file
	 * @apiNote Enabled by default!
	 */
	public boolean isSaleable() {
		return file.exists("shop.sell") ? file.getBoolean("shop.sell") : true;
	}
	/** Gets base money that will player get for this item.
	 * @return Value from configuration file. If there is none, returns 0.
	 */
	public double getBaseMoney() {
		return file.exists("shop.money") ? file.getDouble("shop.money") : 0;
	}
	/** Gets base points that will player get for this item.
	 * @return Value from configuration file. If there is none, returns 0.
	 */
	public double getBasePoints() {
		return file.exists("shop.points") ? file.getDouble("shop.points") : 0;
	}
	/** Gets base XP that will player get for this item.
	 * @return Value from configuration file. If there is none, returns 0.
	 */
	public double getBaseXp() {
		return file.exists("shop.xp") ? file.getDouble("shop.xp") : 0;
	}
	
	//CALCULATOR? Hmm... probably different class
	
	
	/*
	 *  SPECIAL OPTIONS
	 */
	
	// EAT
	/** If player can eat this item.
	 * @return Value from configuration file
	 * @apiNote Disabled by default!
	 */
	public boolean isEdible() {
		return file.exists("eat.edible") ? file.getBoolean("eat.edible") : false;
	}
	public int getHunger() {
		return file.exists("eat.addhunger") ? file.getInt("eat.addhunger") : 1;
	}
	
	
	
}

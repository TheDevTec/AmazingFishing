package me.devtec.amazingfishing.fishing;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.fishing.enums.CalculatorType;
import me.devtec.amazingfishing.fishing.enums.FishType;
import me.devtec.amazingfishing.fishing.enums.FishingTime;
import me.devtec.amazingfishing.fishing.enums.FishingWeather;
import me.devtec.amazingfishing.fishing.enums.ItemAction;
import me.devtec.amazingfishing.player.Fisher;
import me.devtec.amazingfishing.utils.Calculator;
import me.devtec.amazingfishing.utils.Configs;
import me.devtec.amazingfishing.utils.MessageUtils;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.shared.dataholder.Config;
import me.devtec.theapi.bukkit.BukkitLoader;
import me.devtec.theapi.bukkit.game.ItemMaker;

/**
 * This is PARENT class for {@link Fish} and {@link Junk} classes. {@link Fish} and {@link Junk} classes have a small differences!
 */
public abstract class FishingItem {

	// File with all the configuration
	private Config file;
	// Type of item
	private FishType type;
	protected String def_perm_path; //Default permission path for junk or fish items
	
	private double chance = -1;
	// TIME
	private FishingTime time;
	// WEATHER
	private FishingWeather weather;

	
	public FishingItem(Config file, FishType type) {
		setConfig(file);
		setType(type);
	}
	
	
	
	
	/*
	 *  CONFIGURATION FILE
	 */
	// Getting Configuration file
	public Config getConfig() {return file;}
	// Setting Configuration file
	public void setConfig(Config newFile) {file = newFile;}
	
	/*
	 *  FISH TYPE
	 */
	public FishType getType()  { return type; }
	public void setType(FishType newType)  { type = newType; }
	
	/*
	 * PERMISSIONS
	 */
	abstract void setDefaultPermissionPath(); //In each file you need to set special permission path
	/**
	 * @return Gets default permission from Config.yml
	 */
	public String getDefaultPermission() {
		if(def_perm_path == null) {
			Loader.plugin.getLog4JLogger().warn("["+Loader.plugin.getDescription().getName()+"] There is no 'Default permission path'. This is plugin error, you can report this...");
			return "";
		}
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
		if(this.chance == -1)
			this.chance = file.exists("chance") ? file.getDouble("chance") : -1;
		return this.chance;
	}
	/** Set new chance. This is going to rewrite <code>chance</code> in file.
	 * @param newChance New chance that will be saved.
	 */
	public void setChance(double newChance) {
		if(newChance < -1)
			newChance = -1; //-1 is not a chance, 0 is also not a chance but official :D
		file.set("chance", newChance);
		file.save();
		this.chance = newChance;
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
	public abstract ItemMaker getItem(Placeholders placeholders);
	
	public abstract ItemMaker getPreviewItem();
	
	/** generating final item. If item is fish generating length and weight.
	 * @param player The fisher
	 * @param placeholders Placeholders that will be replaced in name, lore, etc...
	 * @return {@link ItemStack} - final form, you can give this item
	 */
	public abstract ItemStack generate(Player player, Placeholders placeholders);
	
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
	
	/** Gets {@link FishingTime} from configuration file.
	 * @return {@link FishingTime}
	 */
	public FishingTime getTime() {
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
	

	/** Gets {@link FishingWeather} from configuration file.
	 * @return {@link FishingWeather}
	 */
	public FishingWeather getWeather() {
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
	public List<String> getMessagesSellRaw() {
		return file.getStringList("messages.sell");
	}
	public List<String> getMessagesRaw(ItemAction action) {
		return file.getStringList("messages."+action.path());
	}
	/*
	 * Possible placeholders:
	 * %amount%, %weigth%, %length%
	 */
	public void runMessages(Player player, ItemAction action, Placeholders placeholders ) {
		List<String> msgs = getMessagesRaw(action);
		if(msgs == null || msgs.isEmpty())
			return;
		placeholders.addPlayer("player", player);  //TODO - more placeholders
		
		for(String message: msgs) {
			MessageUtils.sendPluginMessage(player, message, placeholders, player);
		}
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
	public List<String> getCommandsSellRaw() {
		return file.getStringList("commands.sell");
	}
	public List<String> getCommandsRaw(ItemAction action) {
		return file.getStringList("commands."+action.path());
	}
	public void runCommands(Player player, ItemAction action, Placeholders placeholders ) {
		List<String> cmds = getCommandsRaw(action);
		if(cmds==null || cmds.isEmpty())
			return;
		placeholders.addPlayer("player", player); //TODO - more placeholders
		
		for(String command: cmds) {
			BukkitLoader.getNmsProvider().postToMainThread(() -> {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), placeholders.apply(command));
			});
		}
		
	}
	
	/*
	 *  SHOP OPTIONS
	 */
	
	/** If item can be sold in shop.
	 * @return Value from configuration file
	 * @apiNote If item is {@link Junk} this is  disabled by default. If item is {@link Fish} it is enabled by default.
	 */
	public boolean isSaleable() {
		return file.exists("shop.sell") ? file.getBoolean("shop.sell") : false;
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
	
	/** Gets BONUS that will be applied when selling Tide's Treasure item. </br>
	 * 		<strong>This method is not checking if the item is today's bonus fish!!</strong>
	 * 		if you want to check that use {@link TidesTreasure} class...
	 * @param type Bonus that you wan't to get. {@link CalculatorType}
	 * @return {@link Double}
	 */
	public double getBonus(CalculatorType type) {
		if(file.exists(TidesTreasure.getConfigPath()+"."+type.getPath()))
			return file.getDouble(TidesTreasure.getConfigPath()+"."+type.getPath());
		else
			return Configs.config.getDouble(TidesTreasure.getConfigPath()+".bonus."+type.getPath());
	}
	
	/** Getting calculator equation from configuration file. If calculator is not in FishingItem file it will get calculator from Config.yml
	 * @param calculator {@link Calculator}
	 * @return
	 */
	public String getEquation(CalculatorType calculator) {
		if(getConfig().exists("calculator."+calculator.getPath()))
			return getConfig().getString("calculator."+calculator.getPath());
		if(Configs.config.exists("calculator."+calculator.getPath()))
			return Configs.config.getString("calculator."+calculator.getPath());
		return null;
	}
	
	
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
	public double getHunger() {
		return file.exists("eat.addhunger") ? file.getDouble("eat.addhunger") : 1.5;
	}
	
	
	/*
	 *  CAN CATCH THIS ITEM?
	 */
	
	abstract public boolean canCatch(Fisher player);
	
	
}

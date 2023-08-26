package me.devtec.amazingfishing.player;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.fishing.FishingItem;
import me.devtec.amazingfishing.fishing.enums.FishRecord;
import me.devtec.amazingfishing.fishing.enums.FishType;
import me.devtec.amazingfishing.fishing.enums.ItemAction;
import me.devtec.amazingfishing.utils.MessageUtils;
import me.devtec.shared.API;
import me.devtec.shared.dataholder.Config;
import me.devtec.shared.utility.StringUtils;
import me.devtec.shared.utility.StringUtils.FormatType;


/*
 * <dataLoc>:
 *   statistics:
 *     fish:
 *       caught: total fish count
 *       ate: kolik AF ryb snědl
 *       sold: kolik toho prodal
 *       <FISH / JUNK>:
 *         caught: kolik chytil určitého typu
 *         ate: kolik typu snědl
 *         sold: kolik určitého typu prodal
 *         <file name>:
 *           caught: kolik chytil určité ryby
 *           ate: kolik určité ryby snědl
 *           sold: kolik určité ryby prodal
 *           record:
 *             weight:
 *             length:
 *     shop:
 *       gained:
 *         exps:
 *         money:
 *         points:
 *     records:
 *       # ONLY FOR 'FISH' TYPE
 *       <file name>:
 *         length:
 *         weight:
 *   settings:
 *     sendRecords: true/false # pokud má dostávat oznámení o novém rekordu
 */


/**
 * The Statistics class manages various statistics, records, and earned values related to fishing activity on the Minecraft server. </br>
 * It offers methods to track catches, fish statistics, records, and earnings gained from selling fish items. </br>
 * This class empowers server administrators with tools to monitor and enhance the fishing experience on their server. </br>
 */
public class Statistics {

	/**  Represents the location for storing statistical data.  */
	private static String dataLoc = "amazingFishing";
	
	/**
     * Enumeration representing different ways to load fishing data for statistics. </br> </br>
     * 
     * GLOBAL - Gets global data. Total count of sold items or caught items, ..</br>
     * PER_TYPE - Gets data for exact {@link FishType}</br>
     * PER_FISH - Gets data for specific {@link FishingItem}</br>
     */
	public static enum LoadDataType {
		GLOBAL,
		PER_TYPE,
		PER_FISH;
	}
	
	/**
     * Records a new catch by a player, updates statistics, and checks for new records.
     *
     * @param player The player who caught the fishing item.
     * @param item The FishingItem that was caught.
     * @param length The length of the caught fishing item.
     * @param weight The weight of the caught fishing item.
     */
	public static void newCatch(Player player, FishingItem item, double length, double weight) {
		//Saving into config
		writeFishingItem(player, item, ItemAction.CATCH);
		//Checking if this item is a FISH
		if(item.getType() == FishType.FISH)
			recordCheck(player, item, length, weight); //checking new record
	}
	
	/*
	 * FishingItem STATS 
	 */
	
	/**
     * Retrieves fishing statistics based on {@link FishType} and {@link ItemAction}.
     *
     * @param player The player for whom to retrieve statistics.
     * @param fishType The type of fish. See {@link FishType}
     * @param ItemAction The type of fishing statistic (e.g., caught, sold).
     * @return The fishing statistic for the specified parameters.
     */
	public static int getFishingStat(Player player, FishType fishType, ItemAction ItemAction) {
		return getFishingStat(player, null, fishType, ItemAction, LoadDataType.PER_TYPE);
	}
	
	/**
     * Retrieves fishing statistics based on specific {@link FishingItem} and {@link ItemAction}.
     * 
	 * @param player The player for whom to retrieve statistics.
	 * @param fishingItem The {@link FishingItem} for which to get statistics.
	 * @param ItemAction The type of fishing statistic (e.g., caught, sold).
	 * @return
	 */
	public static int getFishingStat(Player player, FishingItem fishingItem, ItemAction ItemAction) {
		return getFishingStat(player, fishingItem, ItemAction, LoadDataType.PER_FISH);
	}
	
	/**
     * Retrieves fishing statistics based on specific {@link FishingItem}, {@link ItemAction}, and loading type.
     * 
	 * @param player The player for whom to retrieve statistics.
	 * @param fishingItem  The {@link FishingItem} for which to get statistics.
	 * @param ItemAction The type of fishing statistic (e.g., caught, sold).
	 * @param laodDataType {@link LoadDataType}
	 * @return
	 * @see {@link FishingItem}, {@link ItemAction}, and {@link LoadDataType}
	 */
	public static int getFishingStat(Player player, FishingItem fishingItem, ItemAction ItemAction, LoadDataType laodDataType) {
		return getFishingStat(player, fishingItem.getConfigName(), fishingItem.getType(), ItemAction, laodDataType);
	}
	
	/**
     * Retrieves fishing statistics based on fish file name, type, {@link ItemAction}, and loading type.
     * 
	 * @param player The player for whom to retrieve statistics.
	 * @param fishFileName The file name of this item. 
	 * @param fishType The type of item. See {@link FishType}
	 * @param ItemAction The type of fishing statistic (e.g., caught, sold).
	 * @param laodDataType {@link LoadDataType}
	 * @return
	 * @see {@link FishingItem}, {@link FishType}, {@link ItemAction}, and {@link LoadDataType}
	 */
	public static int getFishingStat(Player player, String fishFileName, FishType fishType, ItemAction ItemAction, LoadDataType laodDataType) {
		Config user = API.getUser(player.getUniqueId());
		
		if(laodDataType==null) return -1;
		
		switch(laodDataType) {
		case GLOBAL:
			return user.getInt(dataLoc+".statistics.fish."+ItemAction.getStatPath()); //All FishingItems
		case PER_TYPE: // Specific FishingItem type
			return user.getInt(dataLoc+".statistics.fish."+fishType.getName()+"."+ItemAction.getStatPath());
		case PER_FISH: //Specific FishingItem
			return user.getInt(dataLoc+".statistics.fish."+fishType.getName()+"."+fishFileName+"."+ItemAction.getStatPath());
		}
		return 0;
	}
	
	/**
	 * Updates fishing statistics after recording a fishing item action
	 * 
	 * @param player The player for whom to save statistics.
	 * @param fishingItem The item that player caught, sold or ate ({@link FishingItem})
	 * @param ItemAction {@link ItemAction}
	 */
	public static void writeFishingItem(Player player, FishingItem fishingItem, ItemAction ItemAction) {
		writeFishingItem(player, fishingItem, ItemAction, 1);
	}
	
	/**
	 * Updates fishing statistics with a specified amount after recording a fishing item action.
	 * 
	 * @param player The player for whom to save statistics.
	 * @param fishingItem The item that player caught, sold or ate ({@link FishingItem})
	 * @param ItemAction {@link ItemAction}
	 * @param amount How much player caught, sold or ate
	 */
	public static void writeFishingItem(Player player, FishingItem fishingItem, ItemAction ItemAction, int amount) {
		Config user = API.getUser(player.getUniqueId());
		// global
		user.set(dataLoc+".statistics.fish."+ItemAction.getStatPath(),
				getFishingStat(player, fishingItem, ItemAction, LoadDataType.GLOBAL)+amount);
		// per type
		user.set(dataLoc+".statistics.fish."+fishingItem.getType().getName()+"."+ItemAction.getStatPath(),
				getFishingStat(player, fishingItem, ItemAction, LoadDataType.PER_TYPE)+amount);
		// per item
		user.set(dataLoc+".statistics.fish."+fishingItem.getType().getName()+"."+fishingItem.getConfigName()+"."+ItemAction.getStatPath(),
				getFishingStat(player, fishingItem, ItemAction, LoadDataType.PER_FISH)+amount);
	
		user.save();
	}
	
	/*
	 * RECORDS
	 */
	
	/**
	 * Checks for new records based on length and weight of the caught fishing item.
	 *  
	 * @param player Player that caught this item
	 * @param FishingItem Caught {@link FishingItem}
	 * @param length The final item's length
	 * @param weight The final item's weight
	 */
	public static void recordCheck(Player player, FishingItem FishingItem, double length, double weight) {
		Config user = API.getUser(player.getUniqueId());
		//getting boolean that represents if player can get new record message
		boolean cansend=!user.exists(dataLoc+".settings.sendRecords")?true:user.getBoolean(dataLoc+".settings.sendRecords");
		//old record value
		double old;
		
		if(FishingItem!=null) {
			//checking length record
			if(length!=0) {
				old = getRecord(player, FishingItem, FishRecord.LENGTH);
				if(length > old) {
					setNewRecord(user, FishingItem, FishRecord.LENGTH, length);
					//if plugin should send message
					if(cansend && old!=0)
						MessageUtils.message(player, "records.length", FishingItem.getPlaceholders()
								.addPlayer("player", player)
								.add("old", StringUtils.formatDouble(FormatType.BASIC, old))
								.add("new",  StringUtils.formatDouble(FormatType.BASIC, length)));
				}
			}
			//checking weight record
			
			if(weight!=0) {
				old = getRecord(player, FishingItem, FishRecord.WEIGHT);
				if(weight > old) {
					setNewRecord(user, FishingItem, FishRecord.WEIGHT, weight);
					//if plugin should send message
					if(cansend && old!=0)
						MessageUtils.message(player, "records.weight", FishingItem.getPlaceholders()
								.addPlayer("player", player)
								.add("old", StringUtils.formatDouble(FormatType.BASIC, old))
								.add("new",  StringUtils.formatDouble(FormatType.BASIC, weight)));
				}
			}
		}
	}
	
	/**
	 * Sets a new record for a specific fishing item and record type.
	 * 
	 * @param user The player for whom to save new record.
	 * @param FishingItem The FishingItem for which to save new record.
	 * @param FishRecord The type of fish record (e.g., LENGTH, WEIGHT).
	 * @param newRecordValue
	 */
	private static void setNewRecord(Config user, FishingItem FishingItem, FishRecord FishRecord, double newRecordValue) {
		user.set(dataLoc+".statistics.records."+FishingItem.getConfigName()+"."+FishRecord.getPath(), newRecordValue);
		user.save();
	}
	
    /**
     * Retrieves the existing record value for a specific fishing item and record type.
     *
     * @param player The player for whom to retrieve the record.
     * @param FishingItem The FishingItem for which to retrieve the record.
     * @param FishRecord The type of fish record (e.g., LENGTH, WEIGHT).
     * @return The existing record value for the specified fishing item and record type.
     */
	public static double getRecord(Player player, FishingItem FishingItem, FishRecord FishRecord) {
		Config u = API.getUser(player.getUniqueId());
		if(FishRecord==null) return -1;
		return u.getDouble(dataLoc+".statistics.records."+FishingItem.getConfigName()+"."+FishRecord.getPath());
	}
	
	/*
	 * GAINED IN SHOP
	 */
	
	/** 
	 * Type of earnings (selling item earnings) you want to get from user's file.
	 * 
	 * MONEY </br>
	 * POINTS </br>
	 * EXP </br>
	 */
	public static enum GainedType {
		MONEY("money"),
		POINTS("points"),
		EXP("exps");
		
		private String config_path;
		
		GainedType(String config_path) {
			this.config_path = config_path;
		}
		
		public String getPath() {
			return config_path;
		}
	}
	
    /**
     * Retrieves the total earnings gained from selling fish items based on the earned type.
     *
     * @param player The player for whom to retrieve earnings.
     * @param GainedType The type of earnings to retrieve (MONEY, POINTS, EXP).
     * @return The total earnings gained of the specified type.
     */
	public static Double getGainedEarnings(Player player, GainedType GainedType) {
		Config user = API.getUser(player.getUniqueId());
		if(GainedType==null) return -1.0;
		return user.getDouble(dataLoc+".statistics.shop.gained."+GainedType.getPath());
	}
	
    /**
     * Updates the total earnings gained from selling fish items based on money, points, and experience.
     *
     * @param player The player for whom to update earnings.
     * @param money The amount of money earned.
     * @param points The amount of points earned.
     * @param exp The amount of experience earned.
     */
	public static void writeGainedEarnings(Player player, double money, double points, double exp) {
		Config u = API.getUser(player.getUniqueId());
		u.set(dataLoc+".statistics.shop.gained."+GainedType.MONEY.getPath(), getGainedEarnings(player, GainedType.MONEY)+money);
		u.set(dataLoc+".statistics.shop.gained."+GainedType.POINTS.getPath(), getGainedEarnings(player, GainedType.POINTS)+points);
		u.set(dataLoc+".statistics.shop.gained."+GainedType.EXP.getPath(), getGainedEarnings(player, GainedType.EXP)+exp);
		u.save();
	}
}

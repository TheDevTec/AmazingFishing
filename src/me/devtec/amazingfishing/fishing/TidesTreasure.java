package me.devtec.amazingfishing.fishing;

import java.time.LocalDate;
import java.util.HashMap;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.utils.Configs;
import me.devtec.shared.utility.StringUtils;

/**
 * The TidesTreasure class manages the mechanics of generating and applying bonus fish for a daily catch.
 * This class provides methods to generate a bonus fish, check if a caught item is a bonus fish, and handle related configurations.
 */
public class TidesTreasure {
	
	private final static String path = "tidesTreasure";
	
	private static Fish bonusFish = null;
	
	/**
     * Generates a bonus fish for the daily catch.
     *
     * @return A {@link FishingItem} representing the bonus fish, or null if no bonus fish is available.
     */
	public static FishingItem generateFish() {
		// If this mechanic is not enabled
		if(!Configs.config.getBoolean(path+".enabled")) {
			bonusFish = null;
			Configs.config.set(path+".data.fish", "none");
			Configs.config.save();
			return null;
		}
		
		HashMap<String, Fish> map = API.getFishList();
		
		if(map.isEmpty())
			return null;
		
		//Getting todays date
		LocalDate today = LocalDate.now();
		//Getting yesterday from config
		LocalDate yesterday = LocalDate.parse(Configs.config.getString(path+".data.date", today.minusDays(1).toString()));

		//Getting old fish
		String oldFish = Configs.config.getString(path+".data.fish", "CHOOSENEWFISHLOL");
		String newFish = null;
		
		// If is new day and plugin should choose new bonus fish because now is new day
		//   OR if the oldFish is not in the map
		if(!today.isEqual(yesterday) || !map.containsKey(oldFish)) {
			//Not choosing the same fish twice...
			do {
				newFish = StringUtils.randomFromCollection(map.keySet());
			} while (oldFish.equals(newFish) && !repetition());
		} else // applying and loading yesterday fish (the today yesterday fish) because today not ended
			newFish = oldFish;
		
		if(newFish == null)
			return null;
		
		Configs.config.set(path+".data.fish", newFish);
		Configs.config.set(path+".data.date", today.toString());
		Configs.config.save();
		
		bonusFish = map.get(newFish);
		
		return bonusFish;
	}
	
	/**
     * Retrieves the current bonus fish.
     * 
     * @return The current bonus fish, or null if no bonus fish is set.
     */
	public static Fish getBonusFish() {
		return bonusFish;
	}
	
	/** 
	 * Checks if a {@link CaughtItem} is the current bonus fish. </br>
	 * 
	 * This will also check if the fish is not too old, because admins can block old fishes to be sold as 
	 * 	bonus fish (Config.yml -> tidesTreasure.apply.oldFish setting). Old fish -> not caught that day
	 * 
	 * @param fish The {@link CaughtItem} to check.
	 * @return True if the CaughtItem is the current bonus fish, false otherwise.
	 */
	public static boolean isBonusFish(CaughtItem fish) {
		// If the Fish is even todays bonus fish
		if(isBonusFish(fish.getItem())) {
			//If bonus fish can be old fish
			if(Configs.config.getBoolean(path+".apply.oldFish"))
				return true;
			// If fish was caught today
			if(fish.getCaughtDate().isEqual(LocalDate.now()))
				return true;
		}
		return false;
	}
	
	/** 
	 * Checks if a {@link CaughtItem} is the current bonus fish. </br>
	 * 
	 * <strong>Only what fish is bonus today.</strong> This won't check if an in-game item can be sold 
	 * 	as bonus fish! For that you need to use isBonusFish({@link CaughtItem})
	 * @param fish The {@link FishingItem} to check
	 * @return True if the FishingItem is the current bonus fish, false otherwise.
	 */
	public static boolean isBonusFish(FishingItem fish) {
		if(bonusFish == null)
			return false;
		//If file names are the same -> the same FishingItem
		return fish.getConfig().getFile().getName().equals(bonusFish.getConfig().getFile().getName());
	}
	
	/** 
	 * Determines if the bonus fish can be the same two days in a row (or more).
	 * @return True if the bonus fish repetition is allowed, false otherwise.
	 */
	public static boolean repetition() {
		return Configs.config.getBoolean(path+".repetition", true);
	}
    /**
     * Gets the configuration path for the TidesTreasure module.
     *
     * @return The configuration path.
     */
	public static String getConfigPath() {
		return path;
	}
}

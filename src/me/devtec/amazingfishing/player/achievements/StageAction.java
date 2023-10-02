package me.devtec.amazingfishing.player.achievements;

import me.devtec.amazingfishing.fishing.enums.ItemAction;
import me.devtec.amazingfishing.player.Statistics;
import me.devtec.amazingfishing.player.Statistics.GainedType;

/**
 * Enum representing various actions that can be associated with player stages and achievements.
 */
public enum StageAction {
	
	// Item actions
    CATCH("item_catch"),        // Catching exact FishingItem				// ITEM & AMOUNT
    CATCH_ALL("catch_all"),		// Catching some number of FishingItems		// AMOUNT
    CATCH_TYPE("catch_type"),	// Catching some number of exact FishType	// TYPE & AMOUNT
    
    EAT("item_eat"),			// Eating exact FishingItem					// ITEM & AMOUNT
    EAT_ALL("item_eat_all"),	// Eating some number of FishingItems		// AMOUNT
    EAT_TYPE("item_eat_type"),	// Eating some number of exact FishType		// TYPE & AMOUNT
    
    SELL("item_sell"),			 // Selling exact FishingItem				// ITEM & AMOUNT
    SELL_ALL("item_sell_all"),	 // Selling some number of FishingItems		// AMOUNT
    SELL_TYPE("item_sell_type"), // Selling some number of exact FishType	// TYPE & AMOUNT
    
	// Completing achievements
	COMPLETE_ACHIEVEMENT("complete_achievement"),   // Completing a single achievement	// NAME AMOUNT
	COMPLETE_ACHIEVEMENTS("complete_achievements"), // Completing multiple achievements	// AMOUNT
	
	// Gained values from selling items
	GAINED_MONEY("gained_money"),       // Gained money						// AMOUNT
	GAINED_POINTS("gained_points"),     // Gained points					// AMOUNT
	GAINED_EXP("gained_exps");          // Gained experience points			// AMOUNT

	
	private String name;
	
    /**
     * Constructs a new StageAction enum value with the provided name.
     *
     * @param name The name associated with this action.
     */
	StageAction(String name) {
		this.name=name;
	}
	
    /**
     * Retrieves the name associated with this action.
     *
     * @return The name of the action.
     */
	public String getName() {
		return this.name;
	}
	
    /**
     * Retrieves the {@link StageAction} enum value corresponding to the given name.
     *
     * @param actionName The name of the action to identify.
     * @return The corresponding {@link StageAction} enum value, or null if not found.
     */
	public static StageAction value(String actions_name) {
		for(StageAction action : StageAction.values())
			if(actions_name.equalsIgnoreCase(action.getName()))
				return action;
		
		return null;
	}
	
    /**
     * Identifies the {@link StageAction} enum value corresponding to the given name.
     *
     * @param actionName The name of the action to identify.
     * @return The corresponding {@link StageAction} enum value, or null if not found.
     */
	public static StageAction identify(String actions_name) {
		return value(actions_name);
	}
	
    /**
     * Identifies the {@link StageAction} enum value corresponding to the given {@link ItemAction}.
     *
     * @param itemAction The {@link ItemAction} to identify.
     * @return The corresponding {@link StageAction} enum value, or null if not found.
     */
	public static StageAction identify(ItemAction ItemAction) {
		switch (ItemAction) {
			case CATCH:
				return CATCH;
			case EAT:
				return EAT;
			case SELL:
				return SELL;
		}
		
		return null;
	}
	
    /**
     * Identifies the {@link StageAction} enum value corresponding to the given {@link GainedType}.
     *
     * @param gainedType The {@link GainedType} to identify.
     * @return The corresponding {@link StageAction} enum value, or null if not found.
     */
	public static StageAction identify(Statistics.GainedType GainedType) {
		switch(GainedType) {
			case EXP:
				return GAINED_EXP;
			case MONEY:
				return GAINED_MONEY;
			case POINTS:
				return GAINED_POINTS;
		}
		
		return null;
	}
}

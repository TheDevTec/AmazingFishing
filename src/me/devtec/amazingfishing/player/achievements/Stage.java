package me.devtec.amazingfishing.player.achievements;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.fishing.FishingItem;
import me.devtec.amazingfishing.fishing.enums.FishType;
import me.devtec.amazingfishing.fishing.enums.ItemAction;
import me.devtec.amazingfishing.player.Statistics;
import me.devtec.amazingfishing.player.Statistics.GainedType;
import me.devtec.amazingfishing.player.Statistics.LoadDataType;
import me.devtec.shared.dataholder.Config;

public class Stage {

	private int stageID;
	private Config config;
	private String path;
	
	public Stage(Config config, int stageID) {
		this.config=config;
		this.stageID = stageID;
		
		this.path = "stages."+stageID;
	}
	
	public int getID() {
		return stageID;
	}

	public Config getConfig() {
		return this.config;
	}
	
	public String getConfigName() {
		return this.config.getFile().getName().replace(".yml", "");
	}
	
	/*
	 * COMMANDS & MESSAGES
	 */
	
	public List<String> getFinishMessages() {
		return config.getStringList(path+".messages");
	}
	public List<String> getFinishCommands() {
		return config.getStringList(path+".commands");
	}
	
	/*
	 * ACTION related stuff
	 */
	
	public StageAction getAction() {
		return StageAction.value( config.getString(path+".action") );
	}
	
	public String getItemName() {
		return config.getString(path+".name");
	}
	
	/**
	 * 
	 * @return null if the item does not exist (or is not loaded)
	 */
	public FishingItem getItem() {
		return API.getFishingItem(getItemName());
	}
	
	
	public double getAmount() {
		return config.getInt(path+".amount", 0);
	}
	
	
	
	/** Checking if the Stage is correctly configured
	 * @return True - if everything is fine
	 */
	public boolean prepCheck() {
		StageAction action = getAction();
		//specific FishingItem
		if(action == StageAction.CATCH || action == StageAction.EAT || action == StageAction.SELL)
			if(getItem() == null) {
				logWarn();
				Bukkit.getLogger().warning("The item '"+getItemName()+"' does not exist!!");
				return false;
			}
			if(getAmount() <= 0) {
				logWarn();
				Bukkit.getLogger().warning("The required number is not correct!! Must be greater than 0...");
				return false;
			}
		//specific FishType
		if(action == StageAction.CATCH_TYPE)
			if(FishType.value(getItemName())==null) {
				logWarn();
				Bukkit.getLogger().warning("The required FishingItem's type is not correct. Possible types: 'FISH' or 'JUNK'");
				return false;
			}
			if(getAmount() <= 0) {
				logWarn();
				Bukkit.getLogger().warning("The required number is not correct!! Must be greater than 0...");
				return false;
			}
		if(action == StageAction.COMPLETE_ACHIEVEMENT) { //TODO
			logWarn();
			Bukkit.getLogger().warning("The required Achievement name is not correct.");
			Bukkit.getLogger().info("Because this feature is not done yet!!");
			return false;
		}
		if(action == StageAction.CATCH_ALL || action == StageAction.EAT_ALL || action == StageAction.SELL_ALL || action == StageAction.COMPLETE_ACHIEVEMENTS || 
				action == StageAction.GAINED_EXP ||	action == StageAction.GAINED_MONEY || action == StageAction.GAINED_POINTS ) {
			if(getAmount() > 0)
					return true;
			logWarn();
			Bukkit.getLogger().warning("The required number is not correct!! Must be greater than 0...");
			return false;
		}
		
		return true;
	}
	
	private void logWarn() {
		Bukkit.getLogger().warning("[AmazingFishing] There was an error when setting up Achievement's stages!! See more details:"); 
		Bukkit.getLogger().warning("Stage '"+stageID+"' is not setup correctly!! Achievement: '"+config.getFile().getName()+"'");
	}
	
	
	/** Whether the player has already completed this stage in the past
	 * @param player Online {@link Player}
	 * @return <strong>True</strong> if player finished this stage in the past. Default value is <strong>false</strong>
	 */
	public boolean finished(Player player) {
		return API.getFisher(player).getUser().getBoolean(
				API.getDataLoc()+".achievements."+
				this.getConfigName()+"."+this.getID(), false);
	}
	
	/** Whether the player has met the conditions to finish this stage now (freshly). </br>
	 * 	After this check there should probably be something like finish message/commands or rewards? </br>
	 * @param player Online {@link Player}
	 * @return <strong>True</strong> if the player has met the conditions to complete this stage.
	 * 			 Default value is <strong>false</strong>
	 * @implNote
	 * <strong>If the player has met the conditions, this method will also save it to the user config. 
	 * 		In that case, the</strong> <i>finished({@link Player})</i> <strong>method will start working and it should not 
	 * 		be allowed to give out rewards for the initial completion of this stage.</strong>
	 */
	public boolean canFinish(Player player) {
		StageAction action = getAction();
		boolean returing = false;
		
		//CATCHING
		if(action == StageAction.CATCH || action == StageAction.CATCH_ALL || action == StageAction.CATCH_TYPE)
			returing = checkCatch(player);
		// EATING
		if(action == StageAction.EAT || action == StageAction.EAT_TYPE || action == StageAction.EAT_ALL)
			returing = checkEat(player);
		// SELLING
		if(action == StageAction.SELL || action == StageAction.SELL_TYPE || action == StageAction.SELL_ALL)
			returing = checkSell(player);
		// POINTS / MONEY / XP
		if(action == StageAction.GAINED_EXP || action == StageAction.GAINED_MONEY || action == StageAction.GAINED_POINTS)
			returing = checkGained(player);
		if(action == StageAction.COMPLETE_ACHIEVEMENTS) //TODO
			returing = false;
		if(action == StageAction.COMPLETE_ACHIEVEMENT) //TODO
			returing = false;
		
		//If player met all conditions ==> save it into the user file
		if(returing) {
			Config user = API.getUser(player);
			user.set(API.getDataLoc()+".achievements."+
				this.getConfigName()+"."+this.getID(), returing);
			user.save();
		}
		
		return returing;
	}
	
	private boolean checkCatch(Player player) {
		StageAction action = getAction();

		if(action == StageAction.CATCH)
			return (Statistics.getFishingStat(player, getItem(), ItemAction.CATCH) >= getAmount() );
		if(action == StageAction.CATCH_TYPE)
			return (Statistics.getFishingStat(player, FishType.value(getItemName()), ItemAction.CATCH, LoadDataType.PER_TYPE) >= getAmount() );
		if(action == StageAction.CATCH_ALL)
			return (Statistics.getFishingStat(player, null, null, ItemAction.CATCH, LoadDataType.GLOBAL) >= getAmount() );
		
		return false;
	}
	private boolean checkEat(Player player) {
		StageAction action = getAction();

		if(action == StageAction.EAT)
			return (Statistics.getFishingStat(player, getItem(), ItemAction.EAT) >= getAmount() );
		if(action == StageAction.EAT_TYPE)
			return (Statistics.getFishingStat(player, FishType.value(getItemName()), ItemAction.EAT, LoadDataType.PER_TYPE) >= getAmount() );
		if(action == StageAction.EAT_ALL)
			return (Statistics.getFishingStat(player, null, null, ItemAction.EAT, LoadDataType.GLOBAL) >= getAmount() );
		
		return false;
	}
	private boolean checkSell(Player player) {
		StageAction action = getAction();

		if(action == StageAction.SELL)
			return (Statistics.getFishingStat(player, getItem(), ItemAction.SELL) >= getAmount() );
		if(action == StageAction.SELL_TYPE)
			return (Statistics.getFishingStat(player, FishType.value(getItemName()), ItemAction.SELL, LoadDataType.PER_TYPE) >= getAmount() );
		if(action == StageAction.SELL_ALL)
			return (Statistics.getFishingStat(player, null, null, ItemAction.SELL, LoadDataType.GLOBAL) >= getAmount() );
		
		return false;
	}
	private boolean checkGained(Player player) {
		StageAction action = getAction();

		if(action == StageAction.GAINED_EXP)
			return (Statistics.getGainedEarnings(player, GainedType.EXP) >= getAmount() );
		if(action == StageAction.GAINED_MONEY)
			return (Statistics.getGainedEarnings(player, GainedType.MONEY) >= getAmount() );
		if(action == StageAction.GAINED_POINTS)
			return (Statistics.getGainedEarnings(player, GainedType.POINTS) >= getAmount() );
		
		return false;
	}
	
	
	
	
	
}
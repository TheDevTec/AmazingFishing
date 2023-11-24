package me.devtec.amazingfishing.player.achievements;

import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.amazingfishing.utils.Utils;
import me.devtec.shared.dataholder.Config;

/*
path?
 enabled:
 
 name: # Achievement's official name
 # Achievement's stages
 # Starts from 0 !!
 stages:
   0:
     messages:
     - ""
     commands:
     - ""
     
     action: "" # StageAction
     # Depends on action
     # • The FishingItem file name (without '.yml')
     # • The specific FishType (FISH or JUNK)
     # • OR Nothing
     catching: "" # btw case sensitive
     # • Number of items needed to be caught
     # • Number of achievements (when the action is 'COMPLETE_ACHIEVEMENTS')
     # • Number of gained money/points/xp
     amount:
     
     
 */

//TODO - reset command

//TODO - ukázkový achievement by mohl být, že když chytíš prví rybu tak ti to napíše že je na serveru custom rybaření a něco ti to ukáže?

public class Achievement {
	
	private Config config;
	private boolean enabled = true;
	
	public Achievement(Config config) {
		this.config=config;
		// loading stages
		loadStages();
	}
	
	
	
	public Config getConfig() {
		return this.config;
	}
	
	public String getConfigName() {
		return this.config.getFile().getName().replace(".yml", "");
	}
	public boolean isEnabled() {
		return this.enabled ? this.config.getBoolean("enabled") : enabled;
	}
	
	public String getName() {
		return config.getString("name");
	}
	
	
	
	/*
	 * STAGES
	 */
	
	public LinkedHashMap<Integer, Stage> stages = new LinkedHashMap<>();
	
	
	public void loadStages() {
		// First clearing already loaded stages
		if(!stages.isEmpty())
			stages.clear();
		
		// Adding Stage into stages HashMap
		//starting from 0
		for(int stageID = 0; stageID <= getConfig().getKeys("stages").size(); stageID++) {
			Stage stage = new Stage(config, stageID);
			if(stage.prepCheck()) {
				stages.put(stageID, stage);
				continue;
			} else { // because the stage is not configured correctly, plugin will not enable this achievement
				stages.clear();
				this.enabled = false;
				break;
			}
		}
	}
	
	// returning null if the map is not containing this stage
	// remember that stages starts from 0
	public Stage getStage(int stage) {
		return stages.get(stage);
	}
	
	// remember that stages starts from 0
	public int getMaxStage() {
		return stages.size();
	}
	
	
	/*
	 * PLAYER
	 */
	
	/** Gets player's current stage.
	 * @param player The {@link Player}
	 * @return If player finished the last stage -> returning <strong>"getMaxStage()+1"</strong>
	 */
	/*public int getStage(Player player) {
		int current = 0;
		for(int stage : stages.keySet())
			if(stages.get(stage).finished(player))
				current++;
		return current;
	}
	
	// if player finished this achievement
	public boolean isFinished(Player player) {
		if(getStage(player) > getMaxStage()) { // player finished the last stage -> getStage(player) is returning getMaxStage+1
			return true;
		}
		return false;
	}
	
	// if player finished certain stage
	public boolean isFinished(Player player, int stage) {
		if(stages.containsKey(stage) && stages.get(stage).finished(player))
			return true;
		return false;
	}
	
	// checking if player finished achievement and giving him rewards
	public void check(Player player) {
		if(isFinished(player)) { // if player finished this achievement
			giveRewards(player);
		}
	}*/
	
	/** Whether the player has already completed this {@link Achievement} in the past
	 * @param player Online {@link Player}
	 * @return <strong>True</strong> if player finished this {@link Achievement} in the past. Default value is <strong>false</strong>
	 */
	public boolean finished(Player player) {
		return API.getFisher(player).getUser().getBoolean(
				API.getDataLoc()+".achievements."+
				this.getConfigName()+".finished.", false);
	}
	
	/** Whether the player has met the conditions to finish this {@link Achievement} now (freshly). </br>
	 * 	After this check there should probably be something like finish message/commands or rewards? </br>
	 * @param player Online {@link Player}
	 * @return <strong>True</strong> if the player has met the conditions to complete this {@link Achievement}.
	 * 			 Default value is <strong>false</strong>
	 * @implNote
	 * <strong>If the player has met the conditions, this method will also save it to the user config. 
	 * 		In that case, the</strong> <i>finished({@link Player})</i> <strong>method will start working and it should not 
	 * 		be allowed to give out rewards for the initial completion of this {@link Achievement}.</strong>
	 */
	public boolean canFinish(Player player) {
		int count = 0; // current stage

		//Checking how much stages player finished
		for(Stage stage : stages.values()) {
			//If player finished this stage before
			if(stage.finished(player)) {
				count++;
				continue;
			}
			//If player finished this stage just now (freshly)
			if(stage.canFinish(player)) {
				Utils.runCommands(player, stage.getFinishCommands(), Placeholders.c());
				Utils.runMessages(player, stage.getFinishMessages(), Placeholders.c());
				count++;
				continue;
			}
		}
		//If player finished all stages, then REWARD!!! YAY
		if(count > getMaxStage()) {
			Config user = API.getUser(player);
			user.set(API.getDataLoc()+".achievements."+
				this.getConfigName()+".finished", true);
			user.save();
			return true;
		}
		
		return false;
	}
	
	public int getStage(Player player) {
		int current = 0;
		for(int stage : stages.keySet())
			if(stages.get(stage).finished(player))
				current++;
		return current;
	}
	
	
	/*
	 * REWARDS
	 */
	
	public double getRewardPoints() {
		return this.config.getDouble("finish.points");
	}
	public List<String> getRewardCommands(){
		return this.config.getStringList("finish.commands");
	}
	public List<String> getRewardMessages(){
		return this.config.getStringList("finish.messages");
	}
	
	public void giveRewards(Player player) {
		// Giving points as reward
		if(getRewardPoints() > 0)
			API.getPointsmanager().add(player.getName(), getRewardPoints());
		
		Placeholders placeholders = Placeholders.c().addPlayer("player", player)
				.add("points", getRewardPoints())
				.add("achievement_name", getName());
		// Running messages
		if(getRewardMessages()!= null && !getRewardMessages().isEmpty()) {
			Utils.runMessages(player, getRewardMessages(), placeholders);
		}
		// Running commands
		if(getRewardCommands()!= null && !getRewardCommands().isEmpty()) {
			Utils.runCommands(player, getRewardCommands(), placeholders);
		}
	}
}

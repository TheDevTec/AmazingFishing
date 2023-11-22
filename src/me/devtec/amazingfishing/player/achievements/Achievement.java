package me.devtec.amazingfishing.player.achievements;

import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.utils.MessageUtils;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.shared.dataholder.Config;
import me.devtec.theapi.bukkit.BukkitLoader;

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
	public int getStage(Player player) {
		int current = 0;
		for(int stage : stages.keySet())
			if(stages.get(stage).isFinished(player))
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
		if(stages.containsKey(stage) && stages.get(stage).isFinished(player))
			return true;
		return false;
	}
	
	
	// checking if player finished achievement and giving him rewards
	public void check(Player player) {
		
		if(isFinished(player)) { // if player finished this achievement
			giveRewards(player);
		}
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

		Placeholders placeholders = Placeholders.c().addPlayer("player", player);
		// Running messages
		if(getRewardMessages()!= null && !getRewardMessages().isEmpty()) {
			for(String message: getRewardMessages()) {
				MessageUtils.sendPluginMessage(player, message, placeholders, player);
			}
		}
		
		// Running commands
		if(getRewardCommands()!= null && !getRewardCommands().isEmpty()) {
			for(String command: getRewardCommands()) {
				BukkitLoader.getNmsProvider().postToMainThread(() -> {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), placeholders.apply(command));
				});
			}
		}
	}
}
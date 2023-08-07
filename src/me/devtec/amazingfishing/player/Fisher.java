package me.devtec.amazingfishing.player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.fishing.Fish;
import me.devtec.amazingfishing.fishing.FishingItem;
import me.devtec.amazingfishing.fishing.Junk;
import me.devtec.amazingfishing.player.points_economy.FisherPoints;
import me.devtec.shared.dataholder.Config;

public class Fisher {

	private Player player;
	private FisherSituation situation;
	
	private FisherPoints fisher_points;
	
	public Fisher(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	/** If player is still online.
	 * @return {@link Player}
	 */
	public boolean isOnline() {
		if(player == null)
			return false;
		return player.isOnline();
	}
	/*
	 * USER FILE
	 */
	/** Gets players user file. Using me.devtec.shared.API.getUser() method. </br>
	 * 	TheAPI user files are used to store your data about player in his file. 
	 * 		Each player have one .yml file. You can find these files in plugins/TheAPI/Users directory.
	 * @return {@link Config}
	 */
	public Config getUser() {
		return me.devtec.shared.API.getUser(player.getName());
	}
	
	/** Gets easier PointsManager only for this Fisher.
	 * @return
	 */
	public FisherPoints getPoints() {
		if(fisher_points == null)
			fisher_points = new FisherPoints(this);
		return fisher_points;
	}
	
	/*
	 * FisherSituation
	 */
	
	/**
	 * @return {@link FisherSituation}
	 */
	public FisherSituation getFisherSituation() {
		if(situation == null)
			situation = new FisherSituation(player);
		return situation;
	}
	
	/*
	 * GENERATING RANDOM FISH
	 */
	
	/*public FishingItem generateRandomItem() {
		HashMap<FishingItem, Double> generatedList = Calculator.normalizeFishChances(generateAvailableItems());
		return Calculator.getRandomFish(generatedList);
	}*/
	
	// Previously generated fish list
	private List<FishingItem> generated = new ArrayList<FishingItem>();
	
	/** Generates available fishing items for player. Items that player can catch.
	 * @return
	 */
	public List<FishingItem> generateAvailableItems(Location hookLocation) {
		// Checking if the situation is still the same. If not, updating...
		if(getFisherSituation().isTheSame(hookLocation)) {
			//Situation is the same
			return generated;
		}
		else {
			getFisherSituation().update(hookLocation);
		}
		// Clearing generated list
		if(generated != null)
			generated.clear();
		
		for(Fish fish : API.getFishList().values()) {
			if(fish.canCatch(this)) {
				generated.add(fish);
			}
		}
		for(Junk junk : API.getJunkList().values())
			if(junk.canCatch(this)) {				
				generated.add(junk);
			}
		
		return generated;
	}
}

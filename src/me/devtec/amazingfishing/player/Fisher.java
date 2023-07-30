package me.devtec.amazingfishing.player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.fishing.Fish;
import me.devtec.amazingfishing.fishing.FishingItem;
import me.devtec.amazingfishing.fishing.Junk;

public class Fisher {

	private Player player;
	private FisherSituation situation;
	
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
			Bukkit.broadcastMessage("SAME");
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

package me.devtec.amazingfishing.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;

import me.devtec.amazingfishing.fishing.Fish;
import me.devtec.amazingfishing.fishing.FishingItem;
import me.devtec.amazingfishing.fishing.enums.Limit;
import me.devtec.shared.utility.MathUtils;

public class Calculator {

	/*
	 * CALCULATING WEIGHT
	 */
	
    /** Calculates the weight according to a generated length
     * @param fish The {@link Fish} that the player catches
     * @param generated_length The generated length
     * @return Calculated weight
     */
    public static double calculateWeight(Fish fish, double generated_length) {
		return calculateWeight(generated_length, fish.getLength(Limit.MIN), fish.getLength(Limit.MAX), fish.getWeight(Limit.MIN), fish.getWeight(Limit.MIN));
    }
    
    /** Using Linear interpolation to calculate weight.
     * @param length Fish length to compute weight for
     * @param minLength Minimum fish length
     * @param maxLength Maximum fish length
     * @param minWeight Minimum fish weight
     * @param maxWeight Maximum fish weight
     * @return
     */
    public static double calculateWeight(double length, double minLength, double maxLength, double minWeight, double maxWeight) {
        double slope = (maxWeight - minWeight) / (maxLength - minLength);
        double yIntercept = minWeight;

        double estimatedWeight = slope * (length-minLength) + yIntercept;

        // Ensure the estimated weight is within the boundaries
        double finalWeight = Math.max(minWeight, Math.min(estimatedWeight, maxWeight));

        return finalWeight;
    }
    
    /*
     * GENERATING LENGTH
     */
    public static double generateLength(double minLength, double maxLength) {
    	return MathUtils.randomDouble(minLength, maxLength);
    }
    
    /*
     * CHANCE
     */
    
	/** Method used to normalize fish list chances </br> 
	 * This means that if total chance of all fishes player can now catch is more than 100% this method 
	 * will make sure to fix this.
	 * @param fishList List of fishes the player can catch
	 * @return Returns the same list but fixed chances. (in HashMap)
	 */
	public static HashMap<FishingItem, Double> normalizeFishingItemChances(List<FishingItem> fishList) {
		if(fishList == null || fishList.isEmpty())
			return new HashMap<FishingItem, Double>(); //returns empty list		
		
		HashMap<FishingItem, Double> fixed_list = new HashMap<FishingItem, Double>();
		
		double totalChances = 0.0;
		for (FishingItem fish : fishList) {
	    totalChances += fish.getChance();
		}
	
	    double scalingFactor = 100.0 / totalChances;
	
	    for (FishingItem fish : fishList) {
	        fixed_list.put(fish, fish.getChance()*scalingFactor);
	    }
	
	    return fixed_list;
    }
	
    public static FishingItem getRandomFishingItem(HashMap<FishingItem, Double> fishList) {
    	//if normalizeFishChances() algorithm used, total chance is always 100% --> if now, this will then work a bit shady :D
        double randomValue = Math.random() * 100.0; 

    	Bukkit.broadcastMessage("FishList:");
        for(Entry<FishingItem, Double> set : fishList.entrySet())
        	MessageUtils.sendAnnouncement(" • "+set.getKey().getName()+ " New chance: "+set.getValue());
        
        for (Entry<FishingItem, Double> set : fishList.entrySet()) {
            if (randomValue <= set.getValue()) {
            	//System.out.println("Returning...");
                return set.getKey();
            }
            randomValue -= set.getValue();
        }

        // In case of any rounding errors, return the last fish in the list as a fallback.
        int i = 0;
        for(FishingItem item : fishList.keySet()) {
        	if((fishList.size()-1) <= i)
        		return item;
        	i++;
        }
        //Well... everything went wrong ... enjoy NULL :P
        return null;
    }
	
}

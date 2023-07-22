package me.devtec.amazingfishing.utils;

import java.util.HashMap;
import java.util.List;

import me.devtec.amazingfishing.fishing.Fish;
import me.devtec.amazingfishing.fishing.FishingItem;
import me.devtec.amazingfishing.fishing.enums.Limit;

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
     * CHANCE
     */
    
	/** Method used to normalize fish list chances </br> 
	 * This means that if total chance of all fishes player can now catch is more than 100% this method 
	 * will make sure to fix this.
	 * @param fishList List of fishes the player can catch
	 * @return Returns the same list but fixed chances. (in HashMap)
	 */
	public static HashMap<FishingItem, Double> normalizeFishChances(List<FishingItem> fishList) {
		
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
	
	
}

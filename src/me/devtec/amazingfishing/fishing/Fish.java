package me.devtec.amazingfishing.fishing;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.fishing.enums.FishType;
import me.devtec.amazingfishing.fishing.enums.Limit;
import me.devtec.amazingfishing.player.Fisher;
import me.devtec.amazingfishing.player.FisherSituation;
import me.devtec.amazingfishing.utils.Calculator;
import me.devtec.amazingfishing.utils.ItemUtils;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.shared.dataholder.Config;
import me.devtec.shared.utility.MathUtils;
import me.devtec.theapi.bukkit.game.ItemMaker;

public class Fish extends FishingItem {

	
	public Fish(Config file) {
		super(file, FishType.FISH);
	}
	/*
	 * PERMISSIONS
	 */
	@Override
	public boolean hasPermission(Player player) {
		if(!getPermission().isEmpty())
			return player.hasPermission(getPermission());
		if(!getDefaultPermission().isEmpty())
			return player.hasPermission(getDefaultPermission());
		return true;
	}
	
	@Override
	void setDefaultPermissionPath() { def_perm_path = "fishing.permissions.fish"; }


	@Override
	public ItemStack generateItem() {
		ItemMaker item = ItemMaker.loadMakerFromConfig(getConfig(), "item");
		Placeholders.c().addPlayer("player", null)
			.add("fish_type", getType().toString())
			.add("fish_permission", getPermission())
			.add("fish_chance", getChance())
			.add("fish_name", getName())
			.add("fish_time", getTime().toString())
			.add("fish_weather", getWeather())
			.add("fish_cansell", isSaleable())
			.add("fish_money", getBaseMoney())
			.add("fish_points", getBasePoints())
			.add("fish_xp", getBaseXp())
			.add("fish_isedible", isEdible())
			.add("fish_hunger", getHunger());
		
		return item.build();
	}


	@Override
	public ItemStack getPreviewItem() {
		ItemMaker item = ItemMaker.loadMakerFromConfig(getConfig(), "preview");
		
		Placeholders placeholers = Placeholders.c().addPlayer("player", null)
			.add("fish_type", getType().toString())
			.add("fish_permission", getPermission())
			.add("fish_chance", getChance())
			.add("fish_name", getName())
			.add("fish_time", getTime().toString())
			.add("fish_weather", getWeather())
			.add("fish_cansell", isSaleable())
			.add("fish_money", getBaseMoney())
			.add("fish_points", getBasePoints())
			.add("fish_xp", getBaseXp())
			.add("fish_isedible", isEdible())
			.add("fish_hunger", getHunger())
			.add("fish_weight_min", getWeight(Limit.MIN))
			.add("fish_weight_max", getWeight(Limit.MAX))
			.add("fish_length_min", getLength(Limit.MIN))
			.add("fish_length_max", getLength(Limit.MAX));
		
		return ItemUtils.applyPlaceholders(item, placeholers).build();
	}

	@Override
	public void giveItem() {
		double length = Calculator.generateLength(getLength(Limit.MIN), getLength(Limit.MAX));
		double weight = getWeight(Limit.MIN);
		if(getConfig().exists("calculator.weight"))
			weight = MathUtils.calculate(getConfig().getString("calculator.weight")
					.replace("%fish_length%", length+"")
					.replace("%fish_length_min%", getLength(Limit.MIN)+"")
					.replace("%fish_length_max%", getLength(Limit.MAX)+"")
					.replace("%fish_weight_min%", getWeight(Limit.MIN)+"")
					.replace("%fish_weight_max%", getLength(Limit.MAX)+""));
		else
			weight = Calculator.calculateWeight(this, length); //default in-build calculator
		
		
		
	}
	
	/** Gets base weight of this fish from a file.
	 * @param limit represents limiting values. MIN or MAX value.
	 * @return Returns double from file. If there is none, returns 0.
	 * @see {@link Limit}
	 */
	public double getWeight(Limit limit) {
		if(getConfig().exists("weight."+limit.toString()))
			return getConfig().getDouble("weight."+limit.toString());
		return 0;
	}
	/** Gets base length of this fish from a file.
	 * @param limit {@link Limit} represents limiting values. MIN or MAX value.
	 * @return Returns double from file. If there is none, returns 0.
	 * @see {@link Limit}
	 */
	public double getLength(Limit limit) {
		if(getConfig().exists("length."+limit.toString()))
			return getConfig().getDouble("length."+limit.toString());
		return 0;
	}
	@Override
	public boolean canCatch(Fisher fisher) {
		if(!hasPermission(fisher.getPlayer()))
			return false;
		
		FisherSituation situation = fisher.getFisherSituation();
		
		if(!getBiomes().contains(situation.getBiome()))
			return false;
		if(getBlockedBiomes().contains(situation.getBiome()))
			return false;
		if(!getTime().equals(situation.getTime()))
			return false;
		if(!getWeather().equals(situation.getWeather()))
			return false;
		
		return true;
	}
	
}

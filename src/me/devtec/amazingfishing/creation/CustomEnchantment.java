package me.devtec.amazingfishing.creation;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.construct.Enchant;

public class CustomEnchantment extends Enchant {
	final int level;
	final double chance, amount;
	final String display;
	public CustomEnchantment(String name, String displayName, int level, double c, double a) {
		super(name);
		this.level=level;
		chance=c;
		amount=a;
		display=displayName;
	}

	@Override
	public String getDisplayName() {
		return display;
	}

	@Override
	public int getMaxLevel() {
		return level;
	}

	@Override
	public FishCatchList onCatch(Player player, int level, FishCatchList catchList) {
		double l = level+1;
		catchList.chance+=Math.sqrt(this.chance*l/1.75);
		catchList.max_amount+=Math.sqrt(this.amount*l/1.8);
		return catchList;
	}
}

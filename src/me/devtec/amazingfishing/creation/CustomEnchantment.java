package me.devtec.amazingfishing.creation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.construct.Enchant;

public class CustomEnchantment extends Enchant {
	final int level;
	final double chance, amount,cost;
	final String display;
	final List<String> description;
	public CustomEnchantment(String name, String displayName, int level, double c, double a, List<String> description, double cost) {
		super(name);
		this.level=level;
		chance=c;
		amount=a;
		display=displayName;
		this.description=description;
		this.cost=cost;
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

	@Override
	public List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		for(String line:description) 
			list.add(line
					.replace("%cost%", String.format("%2.02f",1).replace(",", "."))
					.replace("%maxlevel%", getMaxLevel()+"")
					.replace("%name%", getName())
					.replace("%displayname%", getDisplayName())
					.replace("%chance%", chance+"") );
		return list;
	}

	@Override
	public double getCost() {
		if(cost<=0)return 1;
		return cost;
	}
}

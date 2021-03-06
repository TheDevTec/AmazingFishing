package me.devtec.amazingfishing.creation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Enchant;
import me.devtec.theapi.utils.json.Writer;

public class CustomEnchantment extends Enchant {
	final int level;
	final double chance, amount,cost,money,points,exp;
	final String display;
	final List<String> description;
	public CustomEnchantment(String name, String displayName, int level, double c, double a, double mo, double po, double ex, List<String> description, double cost) {
		super(name);
		this.level=level;
		money=mo;
		points=po;
		exp=ex;
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
	
	public String toString() {
		Map<String, Object> map = new HashMap<>();
		map.put("type", "Enchantment");
		map.put("name", getName());
		map.put("cost", getCost());
		map.put("bonus_chance", getChance());
		map.put("bonus_amount", getAmount());
		map.put("bonus_money", getMoneyBoost());
		map.put("bonus_points", getPointsBoost());
		map.put("bonus_exp", getExpBoost());
		return Writer.write(map);
	}
	
	public boolean equals(Object o) {
		if(o instanceof Enchant) {
			if(o instanceof CustomEnchantment) {
				return ((CustomEnchantment) o).getName().equals(getName()) && getChance()==((CustomEnchantment) o).getChance()
						 && getAmount()==((CustomEnchantment) o).getAmount() && getCost()==((CustomEnchantment) o).getCost();
			}else {
				return ((Enchant) o).getName().equals(getName()) && getCost()==((Enchant) o).getCost()
						 && getMaxLevel()==((Enchant) o).getMaxLevel();
			}
		}
		return false;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public double getChance() {
		return chance;
	}

	@Override
	public FishCatchList onCatch(Player player, int level, FishCatchList catchList) {
		double l = level+1;
		catchList.chance+=Math.sqrt(this.chance*l/1.75);
		catchList.max_amount+=Math.sqrt(this.amount*l/1.8);
		catchList.money+=Math.sqrt(this.money*l/1.8);
		catchList.exp+=Math.sqrt(this.exp*l/1.8);
		catchList.points+=Math.sqrt(this.points*l/1.8);
		return catchList;
	}

	@Override
	public List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		for(String line:description) 
			list.add(line
					.replace("%cost%", Loader.ff.format(getCost()))
					.replace("%maxlevel%", getMaxLevel()+"")
					.replace("%name%", getName())
					.replace("%displayname%", getDisplayName())
					.replace("%chance%", Loader.ff.format(chance))
					.replace("%amount%", Loader.ff.format(amount)));
		return list;
	}

	@Override
	public double getCost() {
		if(cost<=0)return 1;
		return cost;
	}

	@Override
	public double getMoneyBoost() {
		return money;
	}

	@Override
	public double getExpBoost() {
		return exp;
	}

	@Override
	public double getPointsBoost() {
		return points;
	}
}

package me.devtec.amazingfishingOLD.creation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;

import me.devtec.amazingfishingOLD.Loader;
import me.devtec.amazingfishingOLD.construct.Enchant;
import me.devtec.shared.json.Json;

public class CustomEnchantment extends Enchant {
	final int level;
	final double chance, amount, cost, money, points, exp, bitespeed;
	final String display;
	final List<String> description;

	public CustomEnchantment(String name, String displayName, int level, double c, double a, double mo, double po, double ex, List<String> description, double cost, double bitespeed) {
		super(name);
		this.level = level;
		money = mo;
		points = po;
		exp = ex;
		chance = c;
		amount = a;
		this.bitespeed = bitespeed;
		display = displayName;
		this.description = description;
		this.cost = cost;
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
	public String toString() {
		Map<String, Object> map = new ConcurrentHashMap<>();
		map.put("type", "Enchantment");
		map.put("name", getName());
		map.put("cost", getCost());
		map.put("bonus_bite_speed", getBiteSpeed());
		map.put("bonus_chance", getChance());
		map.put("bonus_amount", getAmount());
		map.put("bonus_money", getMoneyBoost());
		map.put("bonus_points", getPointsBoost());
		map.put("bonus_exp", getExpBoost());
		return Json.writer().simpleWrite(map);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Enchant)
			return ((Enchant) o).getName().equals(getName()) && getCost() == ((Enchant) o).getCost() && getMaxLevel() == ((Enchant) o).getMaxLevel();
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
		double l = level + 1;
		catchList.chance += Math.sqrt(chance * l / 1.75);
		catchList.max_amount += Math.sqrt(amount * l / 1.8);
		catchList.money += Math.sqrt(money * l / 1.8);
		catchList.exp += Math.sqrt(exp * l / 1.8);
		catchList.points += Math.sqrt(points * l / 1.8);
		catchList.bitespeed += Math.sqrt(bitespeed * l / 1.8);
		return catchList;
	}

	@Override
	public List<String> getDescription() {
		List<String> list = new ArrayList<>();
		for (String line : description)
			list.add(line.replace("%cost%", Loader.ff.format(getCost())).replace("%maxlevel%", getMaxLevel() + "").replace("%name%", getName()).replace("%displayname%", getDisplayName())
					.replace("%chance%", Loader.ff.format(chance)).replace("%amount%", Loader.ff.format(amount)).replace("%bitespeed%", Loader.ff.format(bitespeed)));
		return list;
	}

	@Override
	public double getCost() {
		if (cost <= 0)
			return 1;
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

	@Override
	public double getBiteSpeed() {
		return bitespeed;
	}
}

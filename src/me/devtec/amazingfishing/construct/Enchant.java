package me.devtec.amazingfishing.construct;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.devtec.amazingfishing.utils.Utils;
import me.devtec.theapi.utils.StringUtils;
import me.devtec.theapi.utils.datakeeper.Data;

public abstract class Enchant {
	
	public static class FishCatchList {
		public double max_amount = 1, chance, points,money,exp;
	}
	
	public static Map<String, Enchant> enchants = new LinkedHashMap<>();
	final String name;
	public Enchant(String name) {
		this.name=name;
		enchants.put(name.toLowerCase(), this);
	}
	
	public String getName() {
		return name;
	}
	
	public abstract String getDisplayName();
	
	public abstract List<String> getDescription();

	public abstract int getMaxLevel();
	
	public abstract double getCost();
	
	public abstract double getMoneyBoost();
	
	public abstract double getExpBoost();
	
	public abstract double getPointsBoost();
	
	public abstract FishCatchList onCatch(Player player, int level, FishCatchList catchList);
	
	public int enchant(ItemStack rod, int amount) {
		Object r = Utils.asNMS(rod);
		Object n = Utils.getNBT(r);
		Data data = Utils.getString(n);
		String remove = data.getString("enchant."+name.toLowerCase());
		data.set("enchant."+name.toLowerCase(), StringUtils.colorize(getDisplayName()+style(data.getInt("enchants."+name.toLowerCase())+amount > getMaxLevel() ? 
						getMaxLevel() : data.getInt("enchants."+name.toLowerCase())+amount)));
		data.set("enchants."+name.toLowerCase(), 
				data.getInt("enchants."+name.toLowerCase())+amount > getMaxLevel() ? 
						getMaxLevel() : data.getInt("enchants."+name.toLowerCase())+amount);
		Utils.setString(n, data);
		ItemMeta m = Utils.asBukkit(r).getItemMeta();
		List<String> l = m.getLore() != null ? m.getLore() : new ArrayList<>();
		if(remove!=null)
			l.remove(remove);
		l.add(data.getString("enchant."+name.toLowerCase()));
		m.setLore(l);
		rod.setItemMeta(m);
		return data.getInt("enchants."+name.toLowerCase());
	}
	
	private String style(int i) {
		switch(i) {
		case 0:
			return "";
		case 1:
			return " I";
		case 2:
			return " II";
		case 3:
			return " III";
		case 4:
			return " IV";
		case 5:
			return " V";
		case 6:
			return " VI";
		case 7:
			return " VII";
		case 8:
			return " VIII";
		case 9:
			return " IX";
		case 10:
			return " X";
		}
		return " "+i;
	}
	public boolean containsEnchant(ItemStack rod) {
		Object r = Utils.asNMS(rod);
		Object n = Utils.getNBT(r);
		Data data = Utils.getString(n);
		if(data.exists("enchant."+name.toLowerCase()))
			return true;
		return false;
	}
}

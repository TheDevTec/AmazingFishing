package me.devtec.amazingfishing.construct;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.devtec.shared.dataholder.Config;
import me.devtec.shared.dataholder.DataType;
import me.devtec.shared.utility.StringUtils;
import me.devtec.theapi.bukkit.BukkitLoader;
import me.devtec.theapi.bukkit.nms.NBTEdit;

public abstract class Enchant {

	public static class FishCatchList {
		public double max_amount = 1, chance, points, money, exp, bitespeed;
	}

	public static Map<String, Enchant> enchants = new LinkedHashMap<>();
	final String name;

	public Enchant(String name) {
		this.name = name;
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

	public abstract double getBiteSpeed();

	public abstract FishCatchList onCatch(Player player, int level, FishCatchList catchList);

	public ItemStack enchant(ItemStack rod, int amount, boolean addLevels) {
		NBTEdit edit = new NBTEdit(rod);
		Config data = new Config();
		if (edit.hasKey("af_data"))
			data.reload(edit.getString("af_data"));
		ItemMeta m = rod.getItemMeta();
		List<String> l = m.hasLore() ? m.getLore() : new ArrayList<>();

		int pos = data.existsKey("enchant-pos." + name.toLowerCase()) ? data.getInt("enchant-pos." + name.toLowerCase()) : l.size();
		if (data.getString("enchant." + name.toLowerCase()) != null) {
			l.remove(data.getString("enchant." + name.toLowerCase()));
			data.remove("enchant." + name.toLowerCase());
		}
		data.set("enchant-pos." + name.toLowerCase(), pos);
		data.set("enchants." + name.toLowerCase(),
				addLevels ? data.getInt("enchants." + name.toLowerCase()) + amount > getMaxLevel() ? getMaxLevel() : data.getInt("enchants." + name.toLowerCase()) + amount : amount <= 0 ? 1 : amount);

		edit.setString("af_data", data.toString(DataType.JSON));
		rod = BukkitLoader.getNmsProvider().setNBT(rod, edit);

		m = rod.getItemMeta();
		if (pos == l.size())
			l.add(StringUtils.colorize(getDisplayName() + style(data.getInt("enchants." + name.toLowerCase()))));
		else
			l.set(pos, StringUtils.colorize(getDisplayName() + style(data.getInt("enchants." + name.toLowerCase()))));
		m.setLore(l);
		rod.setItemMeta(m);
		return rod;
	}

	private String style(int i) {
		switch (i) {
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
		return " " + i;
	}

	public boolean containsEnchant(ItemStack rod) {
		NBTEdit edit = new NBTEdit(rod);
		Config data = new Config();
		if (edit.hasKey("af_data"))
			data.reload(edit.getString("af_data"));
		return data.exists("enchant." + name.toLowerCase());
	}
}

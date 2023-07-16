package me.devtec.amazingfishingOLD.utils;

import java.util.List;

import me.devtec.shared.dataholder.Config;
import me.devtec.theapi.bukkit.game.ItemMaker;

public class Categories {

	public static class Category {
		private Config d;
		private String name; // Category Name

		public Category(String name, Config data) {
			this.name = name;
			d = data;
		}

		public String getName() {
			return name;
		}

		public String getDisplayName() {
			return d.getString("categories." + name + ".name");
		}

		public List<String> getDescription() {
			return d.getStringList("categories." + name + ".description");
		}

		public List<String> getContent() { // obsah - Achievementy, Questy atd...
			return d.getStringList("categories." + name + ".content");
		}

		public List<String> getFlags() {
			return d.getStringList("categories." + name + ".flags");
		}

		public boolean isUnbreakable() {
			return d.getBoolean("categories." + name + ".unbreakable");
		}

		public int getModel() {
			return d.getInt("categories." + name + ".model");
		}

		public ItemMaker getIcon() {
			return Create.find(d.exists("categories." + name + ".head") ? "head:" + d.getString("categories." + name + ".head") : d.getString("categories." + name + ".icon"), "STONE", 0);
		}
	}

	public static boolean hasIcon(Category category) {
		return category.d.exists("categories." + category.name + ".head") && category.d.exists("categories." + category.name + ".icon");
	}
}

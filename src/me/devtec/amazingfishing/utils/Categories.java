package me.devtec.amazingfishing.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemFlag;

import me.devtec.theapi.apis.ItemCreatorAPI;
import me.devtec.theapi.utils.datakeeper.Data;

public class Categories {

	public static class Category {
		private Data d;
		private String name; // Category Name
		public Category(String name, Data data) {
			this.name=name;
			d=data;
		}
		
		public String getName() {
			return name;
		}
		
		public String getDisplayName() {
			return d.getString("categories."+name+".name");
		}
		
		public List<String> getDescription() {
			return d.getStringList("categories."+name+".description");
		}
		
		public List<String> getContent() { //obsah - Achievementy, Questy atd...
			return d.getStringList("categories."+name+".content");
		}
		
		public List<ItemFlag> getFlags() {
			List<ItemFlag> flags = new ArrayList<>();
			for(String flag : d.getStringList("categories."+name+".flags")) {
				flags.add(ItemFlag.valueOf(flag.toUpperCase()));
			}
			return flags;
		}
		
		public boolean isUnbreakable() {
			return d.getBoolean("categories."+name+".unbreakable");
		}
		
		public int getModel() {
			return d.getInt("categories."+name+".model");
		}
		
		public ItemCreatorAPI getIcon() {
			return Create.find(d.exists("categories."+name+".head")?"head:"+d.getString("categories."+name+".head"):d.getString("categories."+name+".icon"), "STONE", 0);
		}
	}

	public static boolean hasIcon(Category category) {
		return category.d.exists("categories."+category.name+".head")&&category.d.exists("categories."+category.name+".icon");
	}
}

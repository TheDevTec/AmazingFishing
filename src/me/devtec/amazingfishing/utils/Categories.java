package me.devtec.amazingfishing.utils;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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
		public List<String> getDescription(){
			return d.getStringList("categories."+name+".description");
		}
		public List<String> getContent(){ //obsah - Achievementy, Questy atd...
			return d.getStringList("categories."+name+".content");
		}
		public ItemStack getIcon() {
			if(d.exists("categories."+name+".head"))
				return Create.createSkull(getHead());
			else
				return new ItemStack(Material.valueOf(d.getString("categories."+name+".icon") ));
		}

		public String getHead() {
			return d.getString("categories."+name+".head");
		}
	}
	
	public static boolean hasIcon(Category category) {
		if(category.getIcon()!=null)
			return true;
		return false;
	}
}

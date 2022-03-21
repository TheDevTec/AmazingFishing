package me.devtec.amazingfishing.gui;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.utils.Achievements;
import me.devtec.amazingfishing.utils.Achievements.Achievement;
import me.devtec.amazingfishing.utils.Categories;
import me.devtec.amazingfishing.utils.Categories.Category;
import me.devtec.amazingfishing.utils.Create;
import me.devtec.amazingfishing.utils.Create.Settings;
import me.devtec.amazingfishing.utils.Pagination;
import me.devtec.theapi.bukkit.gui.EmptyItemGUI;
import me.devtec.theapi.bukkit.gui.GUI;
import me.devtec.theapi.bukkit.gui.GUI.ClickType;
import me.devtec.theapi.bukkit.gui.HolderGUI;
import me.devtec.theapi.bukkit.gui.ItemGUI;

public class AchievementGUI {

	public static void open(Player p) {
		for(Category category :  Achievements.categories.values())
			if(Categories.hasIcon(category)) {
				openCategoryList(p, 0);
				return;
			}
		openAchievements(p, 0);
	}
	
	private static void openAchievements(Player player, int page) {
		GUI a = Create.setup(new GUI(Create.title("achievements.title"),54), Create.make("achievements.close").create(), f -> Help.open(f), Settings.SIDES);
		Pagination<Achievement> p;
		if(Achievements.categories.isEmpty()) {
			p = new Pagination<Achievement>(28, Achievements.achievements.values());
		} else {
			p = new Pagination<Achievement>(28);
			for(Category cat :  Achievements.categories.values()) {
				for(String  q: cat.getContent()) {
					Achievement ach = Achievements.achievements.get(q);
					if(ach!=null)p.add(ach);
				}
			}
		}
		if(p!=null && !p.isEmpty()) {
			for(Achievement category: p.getPage(page)) {
				a.add(new EmptyItemGUI(Create.createItem(category.getDisplayName(), category.getIcon(player), category.getDescription(player), category.getModel(player), category.getFlags(player), category.isUnbreakable(player))));
			}
			
			if(p.totalPages()>page+1) {
				a.setItem(51, new ItemGUI(Loader.next) {
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						openAchievements(player, page+1);
					}
				});
			}
			if(page>0) {
				a.setItem(47, new ItemGUI(Loader.prev) {
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						openAchievements(player, page-1);
					}
				});
			}
		}
		
		a.open(player);
	}
	
	private static void openCategoryList(Player player, int page) {
		GUI a = Create.setup(new GUI(Create.title("achievements.title"),54), Create.make("achievements.close").create(), f -> Help.open(f), Settings.SIDES);
		Pagination<Category> p = new Pagination<Category>(28, Achievements.categories.values());
		if(!p.isEmpty()) {
			for(Category category: p.getPage(page)) {
				a.add(new ItemGUI(Create.createItem(category.getDisplayName(), category.getIcon(), category.getDescription(), category.getModel(), category.getFlags(), category.isUnbreakable())) {
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						openCategory(player, 0, category);
					}
				});
			}
			if(p.totalPages()>page+1) {
				a.setItem(51, new ItemGUI(Loader.next) {
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						openCategoryList(player, page+1);
					}
				});
			}
			if(page>0) {
				a.setItem(47, new ItemGUI(Loader.prev) {
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						openCategoryList(player, page-1);
					}
				});
			}
		}
		a.open(player);
	}

	private static void openCategory(Player player, int page, Category category) {
		GUI a = Create.setup(new GUI(Create.title("achievements.title-category").replace("%category%", category.getDisplayName()),54), Create.make("achievements.close").create(), f -> openCategoryList(player, 0), Settings.SIDES);
		Pagination<Achievement> p = new Pagination<Achievement>(28);
		for(String q : category.getContent()) {
			Achievement ach = Achievements.achievements.get(q);
			if(ach!=null)p.add(ach);
		}
		if(!p.isEmpty()) {
			for(Achievement ach: p.getPage(page))
				a.add(new EmptyItemGUI(Create.createItem(ach.getDisplayName(), ach.getIcon(player), ach.getDescription(player), ach.getModel(player), ach.getFlags(player), ach.isUnbreakable(player))));
			if(p.totalPages()>page+1) {
				a.setItem(51, new ItemGUI(Loader.next) {
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						openCategory(player, page+1, category);
					}
				});
			}
			if(page>0) {
				a.setItem(47, new ItemGUI(Loader.prev) {
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						openCategory(player, page-1, category);
					}
				});
			}
		}
		a.open(player);
	}
}

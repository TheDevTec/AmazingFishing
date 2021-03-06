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
import me.devtec.amazingfishing.utils.Trans;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.GUI.ClickType;
import me.devtec.theapi.guiapi.HolderGUI;
import me.devtec.theapi.guiapi.ItemGUI;

public class AchievementGUI {

	public static void open(Player p) {
		boolean icon = false;
		if(!Achievements.categories.isEmpty()) {
			for(Category category :  Achievements.categories.values()) {
				if(Categories.hasIcon(category)) {
					icon=true;
					break;
				}
			}
		}
		if(icon==false)
			openAchievements(p, 0);
		else
			openCategoryList(p, 0);
	}
	private static void openAchievements(Player player, int page) {
		GUI a = Create.setup(new GUI(Trans.achievements_title(),54), f -> Help.open(f), Settings.SIDES);

		Pagination<Achievement> p = new Pagination<Achievement>(28);
		if(Achievements.categories.isEmpty()) {
			for(Achievement q :  Achievements.achievements.values()) {
				p.add(q);
			}
		} else {
			for(Category cat :  Achievements.categories.values()) {
				for(String  q: cat.getContent()) {
					if(Achievements.achievements.containsKey(q))
						p.add(Achievements.achievements.get(q));
				}
			}
		}

		if(p!=null && !p.isEmpty()) {
		for(Achievement ach: p.getPage(page)) {
			a.add(new ItemGUI( Create.createItem(ach.getDisplayName(), Achievements.getIcon(player, ach), ach.getDescription(player))) {
				
				@Override
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					//TODO - něco sem?
				}
			});
		}
		
		if(p.totalPages()>page+1) {
			a.setItem(51, new ItemGUI(Loader.next) {
				
				@Override
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					openAchievements(player, page+1);
					
				}
			});
		}
		if(page>0) {
			a.setItem(47, new ItemGUI(Loader.prev) {
				
				@Override
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					openAchievements(player, page-1);
					
				}
			});
		}
		}
		
		a.open(player);
	}
	
	private static void openCategoryList(Player player, int page) {
		GUI a = Create.setup(new GUI(Trans.achievements_title(),54), f -> Help.open(f), Settings.SIDES);

		Pagination<Category> p = new Pagination<Category>(28);
	
		for(Category category :  Achievements.categories.values()) {
				p.add(category);
		}
		
		if(p!=null && !p.isEmpty()) {
			for(Category category: p.getPage(page)) {
				a.add(new ItemGUI( Create.createItem(category.getDisplayName(), category.getIcon(), category.getDescription())) {
					@Override
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						openCategory(player, 0, category);
					}
				});
			}
			
			
			
			if(p.totalPages()>page+1) {
				a.setItem(51, new ItemGUI(Loader.next) {
					
					@Override
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						openCategoryList(player, page+1);
						
					}
				});
			}
			if(page>0) {
				a.setItem(47, new ItemGUI(Loader.prev) {
					
					@Override
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						openCategoryList(player, page-1);
						
					}
				});
			}
		}
		
		a.open(player);
	}

	private static void openCategory(Player player, int page, Category category) {
		GUI a = Create.setup(new GUI(Trans.achievements_title_category().replace("%category%", category.getDisplayName()),54), f -> openCategoryList(player, 0), Settings.SIDES);
		
		Pagination<Achievement> p = new Pagination<Achievement>(28);

		for(String q: category.getContent()) {
			if(Achievements.achievements.containsKey(q))
				p.add(Achievements.achievements.get(q));
		}
		
		if(p!=null && !p.isEmpty()) {
			for(Achievement ach: p.getPage(page)) {
				a.add(new ItemGUI( Create.createItem(ach.getDisplayName(), Achievements.getIcon(player, ach), ach.getDescription(player))) {
					@Override
					public void onClick(Player player, HolderGUI gui, ClickType click) {
					}
				});
			}
			
			if(p.totalPages()>page+1) {
				a.setItem(51, new ItemGUI(Loader.next) {
					
					@Override
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						openCategory(player, page+1, category);
						
					}
				});
			}
			if(page>0) {
				a.setItem(47, new ItemGUI(Loader.prev) {
					
					@Override
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						openCategory(player, page-1, category);
						
					}
				});
			}
			}
		
		a.open(player);
	}
}

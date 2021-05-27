package me.devtec.amazingfishing.gui;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.utils.Achievements;
import me.devtec.amazingfishing.utils.Achievements.Achievement;
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
		openAchievements(p, 0);
	}
	private static void openAchievements(Player player, int page) {
		GUI a = Create.setup(new GUI(Trans.achievements_title(),54), f -> Help.open(f), Settings.SIDES);

		Pagination<Achievement> p = new Pagination<Achievement>(28);
		for(Achievement q :  Achievements.achievements.values()) {
			p.add(q);
		}
		if(p!=null && !p.isEmpty()) {
		for(Achievement ach: p.getPage(page)) {
			a.add(new ItemGUI( Create.createItem(ach.getDisplayName(), Achievements.getIcon(player, ach), ach.getDescription())) {
				
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
}

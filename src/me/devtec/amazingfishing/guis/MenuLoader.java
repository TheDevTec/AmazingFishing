package me.devtec.amazingfishing.guis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import me.devtec.shared.dataholder.Config;

public class MenuLoader {

	public static Config mainMenuConfig;
	
	protected static List<String> fileList = Arrays.asList("main");
	private static HashMap<String, Menu> menuList = new HashMap<String, Menu>();
	
	public static List<String> getMenuFiles() {
		return fileList;
	}
	
	public static HashMap<String, Menu> getMenus() {
		return menuList;
	}
	
	public static void loadMenus() {
		
		for(String file : fileList) {
			Config config = new Config("plugins/AmazingFishing/Menus/"+file+".yml");
			if(!config.exists("enabled") || config.getBoolean("enabled")) //if menu is disabled
				menuList.put(file, new Menu(config));
		}
	}
	
	public static void openMenu(Player player, String menu) {
		if(getMenus().containsKey(menu))
			getMenus().get(menu);
	}
}

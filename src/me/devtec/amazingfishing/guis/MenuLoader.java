package me.devtec.amazingfishing.guis;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.fishing.enums.FishType;
import me.devtec.amazingfishing.guis.menus.Atlas;
import me.devtec.amazingfishing.guis.menus.Settings;
import me.devtec.amazingfishing.guis.menus.ShopSell;
import me.devtec.shared.dataholder.Config;

public class MenuLoader {

	public static Config mainMenuConfig;
	
	protected static List<String> fileList = Arrays.asList("main", "index", "index_fish", "index_junk", 
			"shop_sell", "player_settings");
	
	private static HashMap<String, Menu> menuList = new HashMap<String, Menu>();
	
	public static List<String> getMenuFiles() {
		return fileList;
	}
	
	public static HashMap<String, Menu> getMenus() {
		return menuList;
	}
	
	// Loading files and adding Menu into HashMap menuList
	public static void loadMenus() {
		// First clearing existing HashMap
		if(!menuList.isEmpty())
			menuList.clear();
		
		// Loading...
		File directory = new File("plugins/AmazingFishing/Menus");
		if(directory.exists() && directory.isDirectory()) {		
			for(File file : directory.listFiles()) { // loops all files in this directory
				Config config = new Config(file);
				if(config.getBoolean("enabled", true)) { //if menu is not disabled
					//Loading mainMenuConfig file
					String fileName = file.getName().replace(".yml", ""); //no sure if the getname() also returns type of the file (.yml)
					if(fileName.equalsIgnoreCase("main"))
						mainMenuConfig = config;
						
					// A few special menu loaders...
					if(fileName.equalsIgnoreCase("index_fish")) {
						if(config.getBoolean("perFishType")) //if there should be special GUI for each fish material (COD, SALMON, ...)
							for(FishType.Special special : FishType.Special.values())
							menuList.put(special.getIndexName(), new Atlas(config, special)); // creating new special GUIs
						menuList.put(fileName, new Atlas(config, FishType.FISH));
					}
					else if(fileName.equalsIgnoreCase("index_junk"))
						menuList.put(fileName, new Atlas(config, FishType.JUNK));
					else if(fileName.equalsIgnoreCase("shop_sell"))
						menuList.put(fileName, new ShopSell(config));
					else if(fileName.equalsIgnoreCase("player_settings"))
						menuList.put(fileName, new Settings(config));
					else
						menuList.put(fileName, new Menu(config));
				}//end of enabled check
			}
		}
	}
	
	/** Loads new {@link Menu}.
	 * @param file String representation of File's name. File needs to be in plugins/AmazingFishing/Menus folder and '.yml'.
	 * @param newMenu Class that extends or is {@link Menu} class. You can see examples in {@link Atlas} or {@link ShopSell} classes.
	 */
	public static void loadMenu(String file, Menu newMenu) {
		Config config = new Config("plugins/AmazingFishing/Menus/"+file+".yml");
		if(config.getBoolean("enabled", true)) { // if menu is not disabled
			menuList.put(file, newMenu);
		}
	}
	
	// If menu is loadded in HashMap menuList
	public static boolean isLoaded(String menu) {
		return getMenus().containsKey(menu);
	}
	
	// Returns Menu fron HashMap menuList
	public static Menu getMenu(String menu) {
		return getMenus().get(menu);
	}
	
	// Opens specific menu for player
	public static void openMenu(Player player, String menu) {
		if(isLoaded(menu))
			getMenu(menu).open(player);
		else
			throw new ArrayStoreException("[AmazingFishing] You are trying to open a non existing menu!! ("+menu+")");
		
	}
	
	// Opens specific menu for player and also adding backMenu as previous menu option
	public static void openMenu(Player player, String menu, Menu backMenu) {
		if(isLoaded(menu))
			getMenu(menu).open(player, backMenu);
		else
			throw new ArrayStoreException("[AmazingFishing] You are trying to open a non existing menu!! ("+menu+")");
	}
}

package me.devtec.amazingfishing.guis;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.devtec.shared.dataholder.Config;

public class Menu {

	private Config file;
	
	public Menu(Config file) {
		this.file = file;
	}
	
	public Config getConfig() {
		return this.file;
	}
	
	/*
	 * OPENING
	 */
	private HashMap<Player, Menu> backHolder = new HashMap<Player, Menu>();
	
	/** Opening new menu. Probably updating or opening menu as back menu or first open
	 * @param player {@link Player}
	 */
	public void open(Player player) {
		
	}
	/** Opening new menu for player. Also saving previous menu (adding menu into "linked list")
	 * @param player {@link Player}
	 * @param backMenu The previous {@link Menu}
	 */
	public void open(Player player, Menu backMenu) {
		if(backMenu != null)
			backHolder.put(player, backMenu);
	}

	/** Closing this menu and opening previous one.
	 * @param player {@link Player}
	 */
	public void back(Player player) {
		if(backHolder.containsKey(player))
			backHolder.get(player).open(player);
		backHolder.remove(player);
		
	}
	/** Removing player from all backHolder HashMaps. (deleting "linked list")
	 * @param player {@link Player}
	 */
	public void close(Player player) {
		if(backHolder.containsKey(player))
			backHolder.get(player).close(player);
		backHolder.remove(player);
	}
}

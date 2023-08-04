package me.devtec.amazingfishing.guis;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.utils.MessageUtils;
import me.devtec.theapi.bukkit.game.ItemMaker;
import me.devtec.theapi.bukkit.gui.GUI;
import me.devtec.theapi.bukkit.gui.GUI.ClickType;
import me.devtec.theapi.bukkit.gui.HolderGUI;
import me.devtec.theapi.bukkit.gui.ItemGUI;

public class MenuUtils {

	/** GUI preparation. Fills menu with filling glass panes. Header, footer and sides
	 * @param gui -> {@link GUI}
	 * @param fill If you want to fill the rest
	 * @return {@link GUI}
	 */
	public static GUI prepare(GUI gui, boolean fill) {
		ItemGUI glass_pane_black = new ItemGUI( ItemMaker.of(Material.BLACK_STAINED_GLASS_PANE).amount(1).displayName("&7").build()) {
			@Override
			public void onClick(Player player, HolderGUI gui, ClickType click) {
				//do nothing
			}}.setUnstealable(true);
		// if this should fill all the GUI
		if(fill) {
			ItemGUI glass_pane_gray = new ItemGUI( ItemMaker.of(Material.LIGHT_GRAY_STAINED_GLASS_PANE).amount(1).displayName("&7").build()) {
				@Override
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					//do nothing
					}}.setUnstealable(true);
			for (int i=0; i<=53; i++)
				gui.setItem(i, glass_pane_gray);
		}
		// header
		for (int i=0; i<=8; i++) {
			gui.setItem(i, glass_pane_black);
		}
		// left side
		for(int i = 9; i<=36; i=i+9)
			gui.setItem(i, glass_pane_black);
		// right side
		for(int i = 17; i<=44; i=i+9)
			gui.setItem(i, glass_pane_black);
		//footer
		for (int i=gui.size()-8; i<=gui.size(); i++) {
			gui.setItem(i, glass_pane_black);
		}
		return gui;
	}
	
	// Loaded universal buttons
	private static HashMap<ButtonType, MenuItem> loaded_buttons = new HashMap<ButtonType, MenuItem>();
	
	public static MenuItem getUniversalButton(ButtonType button) {
		if(!loaded_buttons.containsKey(button))
			loaded_buttons.put(button, new MenuItem(MenuLoader.mainMenuConfig, button));
		return loaded_buttons.get(button);
	}
	
}

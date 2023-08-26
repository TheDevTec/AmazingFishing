package me.devtec.amazingfishing.guis;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.utils.Configs;
import me.devtec.amazingfishing.utils.ItemUtils;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.shared.dataholder.Config;
import me.devtec.theapi.bukkit.game.ItemMaker;

public class MenuItem {

	private Config file;
	private String name;
	private String path;
	
	private int pos = -1;
	private ItemMaker maker = null;
	
	private Sound sound = Sound.BLOCK_CHEST_OPEN;
	private Sound error_sound = Sound.ENTITY_VILLAGER_NO;
	
	public MenuItem(Config file, String path_name) {
		this.file = file;
		this.name = path_name;
		this.path = "items."+path_name;
		
		load();
	}
	
	public MenuItem(Config file, ButtonType button) {
		this.file = file;
		this.name = button.toString();
		this.path = "buttons."+button.toString();
		
		load();
	}
	
	private void load() {
		// Loading GUI position
		loadPosition();
		// Loading ItemMaker
		loadItem();
	}
	
	/**
	 * @return {@link Config}
	 */
	public Config getConfig() {
		return this.file;
	}
	/** Gets item path in configuration file.
	 * @return
	 */
	public String getPath() {
		return this.path;
	}
	/** Gets item path name from file.
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	/*
	 * POSITION
	 */
	
	/** Sets new position of this item.
	 * @param newPosition From 0 to 53
	 */
	public void setposition(int newPosition) {
		if(newPosition<0)
			newPosition = -1;
		this.pos = newPosition;
	}
	/** Gets position of this item in GUI
	 * @return
	 */
	public int getPosition() {
		return this.pos;
	}
	
	// Loading position
	private void loadPosition() {
		if(getConfig().exists(getPath()+".position"))
			this.pos = getConfig().getInt(getPath()+".position");
		else
			this.pos = -1;
	}
	
	/*
	 * SOUNDS
	 */
	public Sound getSound() {
		return this.sound;
	}
	
	public Sound getErrorSound() {
		return this.error_sound;
	}
	
	public void loadSounds() {
		// Loading clicking sound
		try {
			this.sound = Sound.valueOf(getConfig().getString(getPath()+".sound"));
		} catch (Exception e) { this.sound = Sound.BLOCK_BARREL_OPEN; }
		// Loading error sound
		try {
			this.error_sound = Sound.valueOf(getConfig().getString(getPath()+".soundError"));
		} catch (Exception e) { this.error_sound = Sound.ENTITY_VILLAGER_NO; }
	
	}
	
	
	/*
	 * PERMISSION
	 */
	
	public String getPermission() {
		return getConfig().exists(getPath()+".permission") ? getConfig().getString(getPath()+".permission") : null;
	}
	
	/** Checking for permission
	 * @param player The player that is opening the menu
	 * @return
	 */
	public boolean hasPermission(Player player) {
		if(getPermission() != null)
			if(!player.hasPermission(getPermission())) {
				return false;
			}
		return true; // if there is none permission
	}
	
	/*
	 * OPENING ANOTHER MENU
	 */
	
	public boolean isOpening() {
		return getConfig().exists(getPath()+".opens");
	}
	
	public String getOpening() {
		return getConfig().getString(getPath()+".opens");
	}
	
	/*
	 * ITEM
	 */

	/** Sets and saves new item.
	 * @param newItem {@link ItemStack}
	 */
	public void setNewItem(ItemStack newItem) {
		ItemMaker.saveToConfig(getConfig(), getPath(), newItem);
		getConfig().save();
		this.maker = ItemMaker.of(newItem);
	}
	
	/** Sets and saves new item.
	 * @param newItem {@link ItemMaker}
	 */
	public void setNewItem(ItemMaker newItem) {
		ItemMaker.saveToConfig(getConfig(), getPath(), newItem.build());
		getConfig().save();
		this.maker = newItem;
	}
	
	private void loadItem() {
		
		// if there is item
		if(getConfig().exists(getPath()+"."+Configs.getTranslation()))
			this.maker = ItemMaker.loadMakerFromConfig(getConfig(), getPath()+"."+Configs.getTranslation());
		else //else loading default EN item
			this.maker = ItemMaker.loadMakerFromConfig(getConfig(), getPath()+".en");
		
		// last possibility is item without translation
		// items.<item>.HERE
		if(this.maker == null)
			this.maker = ItemMaker.loadMakerFromConfig(getConfig(), getPath());
		
	}

	/** Gets {@link ItemMaker}. This will not replace player specific placeholders.
	 * @return {@link ItemMaker}
	 */
	public ItemMaker getItem() {
		return getItem(null);
	}
	/** Gets {@link ItemMaker}
	 * @param player Player that is opening the menu. Will replace player specific placeholders..
	 * @return {@link ItemMaker}
	 */
	public ItemMaker getItem(Player player) {
		// Placeholders
		Placeholders placeholders = Placeholders.c();
		if(player!= null)
			placeholders.addPlayer("player", player);
		
		return getItem(player, placeholders);
	}
	
	public ItemMaker getItem(Player player, Placeholders placeholders) {
		ItemMaker item = this.maker.clone();
		// Adding player into placeholders
		if(player!= null)
			placeholders.addPlayer("player", player);
		item = ItemUtils.applyPlaceholders(maker, placeholders);
		
		return item;
	}
	
	
}

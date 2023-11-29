package me.devtec.amazingfishing.guis;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.guis.menus.ShopSell;
import me.devtec.amazingfishing.utils.Configs;
import me.devtec.amazingfishing.utils.MessageUtils;
import me.devtec.shared.dataholder.Config;
import me.devtec.theapi.bukkit.gui.GUI;
import me.devtec.theapi.bukkit.gui.GUI.ClickType;
import me.devtec.theapi.bukkit.gui.HolderGUI;
import me.devtec.theapi.bukkit.gui.ItemGUI;

public class Menu {
	
	private Config file;
	
	public Menu(Config file) {
		this.file = file;
		//loading items
		prepareItems();
	}
	
	/** Menu's configuration file.
	 * @return {@link Config}
	 */
	public Config getConfig() {
		return this.file;
	}
	
	/*
	 * SOME BASIC SHIT :D
	 */
	
	/** If menu is enabled in configuration file. Enabled in default.
	 * @return Enabled in default.
	 */
	public boolean isEnabled() {
		return getConfig().getBoolean("enabled", true);
	}
	
	/**
	 * @return Title from configuration file.
	 */
	public String getTitle() {
	    //First lookup for translation title, if not found, try to get default english title, 
		// if this fail too, return default global title.
	    return getConfig().getString("title."+Configs.getTranslation(), 
	    		getConfig().getString("title.en", getConfig().getString("title")));
	}
	
	/** Gets size from configuration file
	 * @return Size should be always {@link Integer}
	 */
	public int getSize() {
		return getConfig().getInt("size", 53);
	}
	
	/** Gets permission from configuration file
	 * @return String representation of this permission or <code>null</code> if there is not a permission needed
	 */
	public String getPermission() {
		return getConfig().getString("permission");
	}
	
	/** Checking for permission and also sending noPerm message
	 * @param player The player that is opening the menu
	 * @return True if player does have a permission to open this menu
	 */
	public boolean hasPermission(Player player) {
		if(getPermission() != null)
			if(!player.hasPermission(getPermission())) {
				MessageUtils.noPerm(player, getConfig().getString("permission"));
				return false;
			}
		return true; // if there is no permission or player does have a permission
	}
	
	/** If the menu is different for each player then enable this setting. </br>
	 * If there are placeholders that for each player looks different (different values) then enable this setting. </br>
	 * This is basically reloading items for each player again and again... </br>
	 * 	If items don't contain any placeholders then you can disable this. Better gui loading time. </br>
	 * @return Enabled in default.
	 */
	public boolean isPerPlayer() {
		return getConfig().getBoolean("perPlayer", false);
	}
	
	/** If the rest of GUI should be filled.
	 * @return Default value is <code>true</code>
	 */
	public boolean fill() {
		return getConfig().getBoolean("fill", true);
	}
	
	/** If players should be able to insert items into the menu. </br>
	 * 	Example can be in {@link ShopSell} where players are selling fishing items.
	 * @return Default value is <code>false</code>
	 */
	public boolean insertable() {
		return getConfig().getBoolean("insertable", false);
	}
	
	/*
	 * OPENING
	 */
	
	private HashMap<Player, Menu> backHolder = new HashMap<Player, Menu>();
	
	/** Opening new menu. Probably updating or opening menu as back menu or first open
	 * @param player {@link Player}
	 */
	public void open(Player player) {
		open(player, null);
	}
	/** Opening new menu for player. Also saving previous menu (adding menu into "linked list")
	 * @param player {@link Player}
	 * @param backMenu The previous {@link Menu}
	 */
	public void open(Player player, Menu backMenu) {
		// Checking for permission and also sending noPerm message
		if(!hasPermission(player))
			return;
		
		if(backMenu != null)
			backHolder.put(player, backMenu);
		
		openGUI(player);
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
	
	protected Menu getBackMenu(Player player) {
		if(backHolder.containsKey(player))
			return backHolder.get(player);
		return null;
	}
	
	protected Menu getThisBack() {
		return this;
	}
	
	/*
	 * ITEM PREPARATION
	 */
	
	private HashMap<String, MenuItem> loaded_items = new HashMap<String, MenuItem>();
	private HashMap<ButtonType, MenuItem> loaded_buttons = new HashMap<ButtonType, MenuItem>();
	
	private void prepareItems() {
		//Loading items from
		for(String item_path : getConfig().getKeys("items")) {
			loaded_items.put(item_path, new MenuItem(getConfig(), item_path));
		}
		
		//Loading buttons
		for(ButtonType button : ButtonType.values()) {
			if(getConfig().exists("buttons."+button.toString()))
				loaded_buttons.put(button, new MenuItem(file, button));
		}
	}
	
	public HashMap<String, MenuItem> getItems() {
		return loaded_items;
	}
	
	public MenuItem getItem(String item) {
		return getItems().get(item);
	}
	
	public HashMap<ButtonType, MenuItem> getButtons() {
		return loaded_buttons;
	}
	
	public MenuItem getButton(ButtonType button) {
		// If loaded in this menu
		if(getButtons().containsKey(button))
			return getButtons().get(button);
		
		// Else button from main.yml menu
		return MenuUtils.getUniversalButton(button);
	}
	/*
	 *  GUI OPENING
	 */
	
	protected void openGUI(Player player) {
		openGUI(player, 0);
	}
	
	protected void openGUI(Player player, int page) {
		GUI gui = MenuUtils.prepare(
				new GUI(getTitle(), getSize()) {
					@Override
					public void onClose(Player player) {
						onClose.run(player, this);
					}
				}, fill());
		
		//setCloseRunnable((p, g) -> g.close());
		
		gui.setInsertable(insertable());
		// Normal items
		putNormalItems(gui, player);
		
		// SPECIAL ITEMS
		putSpecialItems(gui, player, page);
		
		/*
		 * BACK & CLOSE buttons
		 */
		// Loading close item
		MenuItem item = getButton(ButtonType.CLOSE);
		ItemGUI guiItem = new ItemGUI(item.getItem().build()) {
			
			@Override
			public void onClick(Player player, HolderGUI gui, ClickType click) {
				player.playSound(player.getLocation(), getButton(ButtonType.CLOSE).getSound(), 5, 10);
				close(player);
				gui.close();
			}
		};
		// If there should be BACK item
		if(getBackMenu(player) != null) {
			// CLOSE -> BACK
			item = getButton(ButtonType.BACK);
			guiItem = new ItemGUI(item.getItem().build()) {
				@Override
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					player.playSound(player.getLocation(), getButton(ButtonType.BACK).getSound(), 5, 10);
					back(player);
				}
			};
		}
		// Setting CLOSE or BACK item
		gui.setItem(item.getPosition(), guiItem);
		
		
		gui.open(player);
	}
	
	private final void putNormalItems(GUI gui, Player player) {
		for(MenuItem item: getItems().values()) {
			if(item.hasPermission(player)) {
				gui.setItem(item.getPosition(), 
						new ItemGUI(isPerPlayer() ? item.getItem(player).build() : item.getItem().build() ) {
					
					@Override
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						//If this item is opening another menu
						if(item.isOpening()) {
							//trying to open menu
							try {
								player.playSound(player.getLocation(), item.getSound() , 5, 10);
								MenuLoader.openMenu(player, item.getOpening(), getThisBack());
								
							} catch (ArrayStoreException e) {
								player.playSound(player.getLocation(), item.getErrorSound() , 5, 10);
								e.printStackTrace();
							}
						}
						
					}
				});
			}	
		}
	}
	
	public void putSpecialItems(GUI gui, Player player, int page) {}
	
	private PRunnable onClose;
	
	protected void setCloseRunnable(PRunnable onClosing) {
		onClose = onClosing;
	}
	
	protected static interface PRunnable {
		public void run(Player p, GUI g);
	}
	
}
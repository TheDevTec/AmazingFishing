package me.devtec.amazingfishing.guis.menus;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.fishing.FishingItem;
import me.devtec.amazingfishing.fishing.enums.FishType;
import me.devtec.amazingfishing.guis.ButtonType;
import me.devtec.amazingfishing.guis.Menu;
import me.devtec.amazingfishing.guis.MenuItem;
import me.devtec.amazingfishing.utils.Pagination;
import me.devtec.shared.dataholder.Config;
import me.devtec.theapi.bukkit.gui.GUI;
import me.devtec.theapi.bukkit.gui.GUI.ClickType;
import me.devtec.theapi.bukkit.gui.HolderGUI;
import me.devtec.theapi.bukkit.gui.ItemGUI;

public class Atlas extends Menu {

	private FishType type;
	
	private Pagination<FishingItem> pagination = new Pagination<FishingItem>(28);
	
	public Atlas(Config file, FishType type) {
		super(file);
		this.type = type;
		loadPagination();
	}
	
	// NEVER FILL this menu, we need free position to add items
	public boolean fill() {
		return false;
	}
	
	private void loadPagination() {
		//Preparing pagination page size
		if(getSize() == 53)
			pagination = new Pagination<FishingItem>(28);
		else if(getSize() == 44)
			pagination = new Pagination<FishingItem>(21);
		else if(getSize() == 35)
			pagination = new Pagination<FishingItem>(14);
		else if(getSize() == 35)
			pagination = new Pagination<FishingItem>(7);
		
		//adding items into Pagination
		if(type == FishType.FISH)
			pagination.addAll( API.getFishList().values());
		else
			pagination.addAll( API.getJunkList().values());
		
	}
	
	protected void putSpecialitems(GUI gui, Player player, int page) {
		// If pagination is empty for some reason
		if(pagination==null || pagination.isEmpty())
			return;
		
		//Adding items from pagination
		for(FishingItem item : pagination.getPage(page)) {
			gui.addItem(new ItemGUI(item.getPreviewItem().build()) {
				
				@Override
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					
				}
			});
		}
		
		//NEXT AND PREVIOUS PAGE BUTTONS
		if(pagination.totalPages()>page+1) {
			MenuItem next = getButton(ButtonType.NEXT);
			gui.setItem(next.getPosition(), new ItemGUI(next.getItem().build()) {
				@Override
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					openGUI(player, page+1); //page+1 -> next page
				}
			});
		}
		if(page>0) { //If this is not first page, then add previous button
			MenuItem prev = getButton(ButtonType.PREVIOUS);
			gui.setItem(prev.getPosition(), new ItemGUI(prev.getItem().build()) {
				@Override
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					openGUI(player, page-1); //page-1 -> previous page
				}
			});
		}
		
	}

}

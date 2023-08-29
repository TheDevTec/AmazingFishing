package me.devtec.amazingfishing.guis.menus;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.fishing.Fish;
import me.devtec.amazingfishing.fishing.FishingItem;
import me.devtec.amazingfishing.fishing.TidesTreasure;
import me.devtec.amazingfishing.fishing.enums.CalculatorType;
import me.devtec.amazingfishing.fishing.enums.FishType;
import me.devtec.amazingfishing.guis.ButtonType;
import me.devtec.amazingfishing.guis.Menu;
import me.devtec.amazingfishing.guis.MenuItem;
import me.devtec.amazingfishing.guis.MenuLoader;
import me.devtec.amazingfishing.utils.ItemUtils;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.amazingfishing.utils.Pagination;
import me.devtec.shared.dataholder.Config;
import me.devtec.theapi.bukkit.game.ItemMaker;
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
	
	public void putSpecialItems(GUI gui, Player player, int page) {
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
		
		// Adding BONUS fish icon
		MenuItem item = getItem("tidesTreasure");
		
		if(item!=null && item.hasPermission(player)) {
			// THE BONUS FISH
			Fish bonus = TidesTreasure.getBonusFish();
			//Special placeholders
			Placeholders placeholders = Placeholders.c()
					.add("fish", bonus.getConfig().getFile().getName())
					.add("fish_name", bonus.getName())
					.add("fish_bonus_money", bonus.getBonus(CalculatorType.MONEY))
					.add("fish_bonus_points", bonus.getBonus(CalculatorType.POINTS))
					.add("fish_bonus_exps", bonus.getBonus(CalculatorType.EXPS))
					.add("fish_bonus_exps_catch", bonus.getBonus(CalculatorType.EXPS_CATCH));
			//Getting normal item
			ItemMaker maker = isPerPlayer() ? item.getItem(player, placeholders) : item.getItem(null, placeholders);
			//IF item should look like the fish item -> getting fishes preview item and maker's old icon
			if(getConfig().getBoolean(item.getPath()+".usePreviewFish"))
				maker = ItemUtils.fixIcon(maker, bonus.getPreviewItem());
			
			gui.setItem(item.getPosition(), new ItemGUI( maker.build() ) {
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

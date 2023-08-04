package me.devtec.amazingfishing.guis.menus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.fishing.CaughtItem;
import me.devtec.amazingfishing.fishing.enums.CalculatorType;
import me.devtec.amazingfishing.guis.Menu;
import me.devtec.amazingfishing.guis.MenuItem;
import me.devtec.amazingfishing.utils.Calculator;
import me.devtec.amazingfishing.utils.Configs;
import me.devtec.amazingfishing.utils.MessageUtils;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.amazingfishingOLD.utils.points.EconomyAPI;
import me.devtec.shared.dataholder.Config;
import me.devtec.theapi.bukkit.gui.EmptyItemGUI;
import me.devtec.theapi.bukkit.gui.GUI;
import me.devtec.theapi.bukkit.gui.GUI.ClickType;
import me.devtec.theapi.bukkit.gui.HolderGUI;
import me.devtec.theapi.bukkit.gui.ItemGUI;

public class ShopSell extends Menu {

	public ShopSell(Config file) {
		super(file);
	}

	// NEVER FILL this menu, we need free position to add items
	public boolean fill() {
		return false;
	}
	//THIS MENU NEEDS TO INSER FISHES YOU WANT TO SELL
	public boolean insertable() {
			return true;
	}
	
	protected void putSpecialitems(GUI gui, Player player, int page) {

		setCloseRunnable(p -> {
			for(ItemStack item : getInsertedItems(gui)) {
				returnItem(p, item);
			}
		});
		
		// Adding sell button
		MenuItem item = getItem("sell");
		
		if(item!=null && item.hasPermission(player)) {
			gui.setItem(item.getPosition(), 
					new ItemGUI(isPerPlayer() ? item.getItem(player).build() : item.getItem().build() ) {
				
				@Override
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					//trying to open menu
					try {
						player.playSound(player.getLocation(), item.getSound() , 5, 10);
						sellAll(player, gui);
					} catch (Exception e) {
						player.playSound(player.getLocation(), item.getErrorSound() , 5, 10);
						e.printStackTrace();
					}
					
				}
			});
		}
		
	}
	
	private void sellAll(Player player, HolderGUI gui) {
		List<ItemStack> items = getInsertedItems(gui);
		
		int soldItems = 0;
		double totalExp = 0, totalPoints = 0, totalMoney = 0;
		
		for(ItemStack itemStack : items) {
			if(itemStack == null || itemStack.getType() == Material.AIR)
				continue;
			
			CaughtItem item = API.identifyItem(itemStack);
			// If this item is not from this plugin
			if(item == null) {
				returnItem(player, itemStack);
				continue;
			}
			// If item can't be sold
			if(!item.getItem().isSaleable()) {
				returnItem(player, itemStack);
				continue;
			}
			
			// Calculating money, points and xp
			totalMoney += Calculator.calculate(CalculatorType.MONEY, item) * itemStack.getAmount();
			totalPoints += Calculator.calculate(CalculatorType.POINTS, item) * itemStack.getAmount();
			totalExp += Calculator.calculate(CalculatorType.EXPS, item) * itemStack.getAmount();
		
			soldItems += itemStack.getAmount();
		//TODO - statistics
		
		}
		// If player sold any items
		if(soldItems > 0) {
			if (Configs.config.getBoolean("shop.disableMoney"))
				totalMoney = 0;
			if (Configs.config.getBoolean("shop.disableXp"))
				totalExp = 0;
			if (Configs.config.getBoolean("shop.disablePoints"))
				totalPoints = 0;
			
			if (totalPoints > 0) {
				//TODO - POINTS
				//API.getPoints().add(p.getName(), totalPoints);
			}
			if (totalMoney > 0 && EconomyAPI.economy != null)
				EconomyAPI.depositPlayer(player.getName(), totalMoney);
			if ((int) totalExp > 0)
				player.giveExp((int) totalExp);
		
		
			MessageUtils.message(player, "shop.sold", Placeholders.c()
					.addPlayer("player", player)
					.add("amount", soldItems)
					.add("money", totalMoney)
					.add("exp", totalExp)
					.add("points", totalPoints));
		}
		
	}
	
	private List<ItemStack> getInsertedItems(HolderGUI gui){
		List<ItemStack> items = new ArrayList<ItemStack>();
		ItemGUI item = new EmptyItemGUI(new ItemStack(Material.AIR));
		item.setUnstealable(false);
		if(getSize() >= 26)
			for(int i = 10; i<=16; i++) {
				if (gui.getItem(i) == null)
					continue;
				items.add(gui.getItem(i));
				gui.setItem(i, item);
			}
		if(getSize() >= 35)
			for(int i = 19; i<=25; i++) {
				if (gui.getItem(i) == null)
					continue;
				items.add(gui.getItem(i));
				gui.setItem(i, item);
			}
		if(getSize() >= 44)
			for(int i = 28; i<=34; i++) {
				if (gui.getItem(i) == null)
					continue;
				items.add(gui.getItem(i));
				gui.setItem(i, item);
			}
		if(getSize() >= 53)
			for(int i = 37; i<=43; i++) {
				if (gui.getItem(i) == null)
					continue;
				items.add(gui.getItem(i));
				gui.setItem(i, item);
			}
		gui.setInsertable(true);
		return items;
	}
	
	private void returnItem(Player player, ItemStack itemStack) {
		player.getInventory().addItem(itemStack);
	}
}

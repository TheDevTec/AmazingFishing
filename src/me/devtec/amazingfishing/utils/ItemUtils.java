package me.devtec.amazingfishing.utils;

import java.util.ArrayList;
import java.util.List;

import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.theapi.bukkit.game.ItemMaker;

public class ItemUtils {

	/** This method will replace all possible placeholders from item's name and lore
	 * @param item the item, where placeholders should be replaced
	 * @param placeholders all placeholders you want to be replaced 
	 * @return {@link ItemMaker}
	 * @see {@link Placeholders}
	 */
	public static ItemMaker applyPlaceholders(ItemMaker item, Placeholders placeholders) {
		
		if(item==null || placeholders == null) return item;
		// replacing diplayName
		item.displayName(placeholders.apply(item.getDisplayName()));

		// replacing lore
		List<String> lore = item.getLore();
		if(lore!=null && !lore.isEmpty()) {
			List<String> newLore = new ArrayList<String>();
			
			for(String line : lore)
				newLore.add(placeholders.apply(line));
			
			item.lore(newLore);
		}
		
		return item;
	}
	
}

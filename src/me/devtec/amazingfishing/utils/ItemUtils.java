package me.devtec.amazingfishing.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.shared.dataholder.Config;
import me.devtec.theapi.bukkit.game.ItemMaker;
import me.devtec.theapi.bukkit.game.ItemMaker.HeadItemMaker;

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
		if(item.getDisplayName() != null && !item.getDisplayName().isEmpty())
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
	
	public static void giveItem(Entity item, ItemStack itemStack, Player p, Location itemloc) {
		if(itemStack == null)
			return;
		
		double d0 = p.getLocation().getX() - itemloc.getX();
		double d1 = p.getLocation().getY() - itemloc.getY() + 1;
		double d2 = p.getLocation().getZ() - itemloc.getZ();
		Vector vec = new Vector(d0 * 0.1, d1 * 0.1 + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08, d2 * 0.1);

		item = itemloc.getWorld().dropItem(itemloc, itemStack);
		item.setVelocity(vec);
	}
	
	/**
	 * Loads preview item from {@link Config}. </br>
	 * If preview item does not contain type, the type will be used from 'item' section.
	 * 
	 * @param file The file from which plugin is loading preview item
	 * @return Loaded {@link ItemMaker}
	 */
	public static ItemMaker loadPreviewItem(Config file) {
		ItemMaker item = null;
		
		if(file.exists("preview"))
			item = ItemMaker.loadMakerFromConfig(file, "preview");
		if(item == null){
			item = ItemMaker.loadMakerFromConfig(file, "item");
			if(file.exists("preview.lore"))
				item.lore(file.getStringList("preview.lore"));
			if(file.exists("preview.displayName"))
				item.displayName(file.getString("preview.displayName"));
		}
		return item;
	}
	
	public static ItemMaker fixIcon(ItemMaker to, ItemMaker from) {
		
		if(from instanceof HeadItemMaker) {
			to = ((HeadItemMaker) to);
			to.type(from.getMaterial());
			to.customModel(from.getCustomModel());
			int type = ((HeadItemMaker) to).getHeadOwnerType();
			if(type == 0)
				((HeadItemMaker) to).skinName( ((HeadItemMaker) from).getHeadOwner() );
			if(type == 1)
				((HeadItemMaker) to).skinValues( ((HeadItemMaker) from).getHeadOwner() );
			if(type == 2)
				((HeadItemMaker) to).skinUrl( ((HeadItemMaker) from).getHeadOwner() );
			
			return to;
		}
		to.type(from.getMaterial());
		to.customModel(from.getCustomModel());
		return to;
	}
	
}

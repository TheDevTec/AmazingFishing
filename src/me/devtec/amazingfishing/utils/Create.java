package me.devtec.amazingfishing.utils;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import me.devtec.amazingfishing.Loader;
import me.devtec.theapi.apis.ItemCreatorAPI;
import me.devtec.theapi.guiapi.EmptyItemGUI;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.GUI.ClickType;
import me.devtec.theapi.guiapi.HolderGUI;
import me.devtec.theapi.guiapi.ItemGUI;
import me.devtec.theapi.utils.StringUtils;

public class Create {
	public static ItemStack createItem(String name, Material material) {
    	ItemCreatorAPI a = new ItemCreatorAPI(material);
    	a.setDisplayName(name);
    	return a.create();
    }
    
	public static ItemStack createItem(String name, Material material, List<String> lore) {
    	ItemCreatorAPI a = new ItemCreatorAPI(material);
    	a.setDisplayName(name);
    	a.setLore(lore);
    	return a.create();
    }
	public static ItemStack createItem(String name, ItemStack item, List<String> lore) {
    	ItemCreatorAPI a = new ItemCreatorAPI(item);
    	a.setDisplayName(name);
    	a.setLore(lore);
    	return a.create();
    }
	public static ItemStack createItem(String name, MaterialData material) {
    	ItemCreatorAPI a = new ItemCreatorAPI(material.getItemType());
    	a.setDisplayName(name);
    	a.setDurability(material.getData());
    	return a.create();
    }
    
	public static ItemStack createItem(String name, MaterialData material, List<String> lore) {
    	ItemCreatorAPI a = new ItemCreatorAPI(material.getItemType());
    	a.setDisplayName(name);
    	a.setLore(lore);
    	a.setDurability(material.getData());
    	return a.create();
    }
	
	static Material mat;
	static {
		try {
			mat = Material.PLAYER_HEAD;
		} catch (NoSuchFieldError | Exception var1) {
			mat = Material.getMaterial("SKULL_ITEM");
		}
	}
	
	public static ItemCreatorAPI find(String item, String fallbackItem, int fallbackId) {
		item=item.toUpperCase();
		fallbackItem=fallbackItem.toUpperCase();
		ItemCreatorAPI creator;
		if(item.startsWith("head:")) {
			String head = item.substring(5);
			if(head.toLowerCase().startsWith("hdb:"))
				return new ItemCreatorAPI(HDBSupport.parse(head));
			else
			if(head.startsWith("https://")||head.startsWith("http://")) {
				creator = new ItemCreatorAPI(new ItemStack(mat, 1));
				creator.setSkullType(SkullType.PLAYER);
				creator.setOwnerFromWeb(head);
				return creator;
			}
			else
			if(head.length()>16) {
				creator = new ItemCreatorAPI(new ItemStack(mat, 1));
				creator.setSkullType(SkullType.PLAYER);
				creator.setOwnerFromValues(head);
				return creator;
			}else {
				creator = new ItemCreatorAPI(new ItemStack(mat, 1));
				creator.setSkullType(SkullType.PLAYER);
				creator.setOwner(head);
				return creator;
			}
		}
		String[] slit = item.split(":");
		if(item!=null && Material.getMaterial(slit[0])!=null) {
			creator = new ItemCreatorAPI(new ItemStack(Material.getMaterial(slit[0]),1,slit.length>=2?(short)StringUtils.getShort(slit[1]):0));
			return creator;
		}
		if(Material.getMaterial(fallbackItem)!=null) {
			creator = new ItemCreatorAPI(new ItemStack(Material.getMaterial(fallbackItem),1,(short)fallbackId));
			return creator;
		}
		creator = new ItemCreatorAPI(new ItemStack(Material.getMaterial("RAW_FISH"),1, (short)fallbackId));
		return creator;
	}
	
	public static ItemGUI item = new EmptyItemGUI(ItemCreatorAPI.create(Utils.getCachedMaterial("BLACK_STAINED_GLASS_PANE").getItemType(), 1, "&c",Utils.getCachedMaterial("BLACK_STAINED_GLASS_PANE").getData()));
	public static ItemGUI blue = new EmptyItemGUI(ItemCreatorAPI.create(Utils.getCachedMaterial("BLUE_STAINED_GLASS_PANE").getItemType(), 1, "&c",Utils.getCachedMaterial("BLUE_STAINED_GLASS_PANE").getData()));
	
	public static GUI prepareInvBig(GUI inv) {
		for(int i = 45; i<54; ++i)
		inv.setItem(i, item);
		return inv;
	}
	public static GUI prepareInvSmall(GUI inv) {
		for(int i = 9; i<18; ++i)
		inv.setItem(i, item);
		return inv;
	}
	public static GUI prepareInvCount(GUI inv, int slots) {
		for(int i = 0; i<slots; ++i)
		inv.setItem(i, item);
		return inv;
	}

	public static enum Settings {
		SIDES,
		WITHOUT_TOP,
		FILL,
		CLOSE,
		CLEAR
	}
	
	static ItemStack close = ItemCreatorAPI.create(Utils.getCachedMaterial("RED_STAINED_GLASS_PANE").getItemType(), 1, Loader.trans.getString("Words.Close"),Utils.getCachedMaterial("RED_STAINED_GLASS_PANE").getData());
	
	public static GUI setup(GUI inv, PRunnable backButton, Settings... settings) {
		boolean[] actions = {false, false,false};
		for(Settings s : settings) {
			switch (s) {
			case SIDES:
				inv.setItem(9, item);
				inv.setItem(17, item);
				inv.setItem(18, item);
				inv.setItem(26, item);
				inv.setItem(27, item);
				inv.setItem(35, item);
				inv.setItem(36, item);
				inv.setItem(44, item);
				break;
			case CLOSE:
				actions[0]=true;
				break;
			case WITHOUT_TOP:
				actions[2]=true;
				break;
			case CLEAR:
				actions[1]=true;
				break;
			case FILL:
				while(inv.getFirstEmpty() != -1)
					inv.addItem(item);
				break;
			}
		}
		if(!actions[2]) {
			for(int i = 1; i < 8; ++i)
				inv.setItem(i, item);
			inv.setItem(0, blue);
			inv.setItem(8, blue);
		}
		for(int i = 46; i < 53; ++i)
			inv.setItem(i, item);
		inv.setItem(53, blue);
		inv.setItem(45, new ItemGUI(close) {
			public void onClick(Player player, HolderGUI gui, ClickType click) {
				if(actions[0])
					gui.close();
				if(actions[1])
					gui.clear();
				if(backButton!=null)
					backButton.run(player);
		}});
		return inv;
	}
	
	public static interface PRunnable {
		public void run(Player p);
	}

	public static ItemStack createItem(String displayName, ItemCreatorAPI icon, List<String> description, int model, List<ItemFlag> flags, boolean unb) {
		icon.setDisplayName(displayName);
		icon.setLore(description);
		icon.setUnbreakable(unb);
		icon.addItemFlag(flags.toArray(new ItemFlag[0]));
		ItemStack item = icon.create();
		item=Utils.setModel(item, model);
		return item;
	}
}

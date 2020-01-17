package AmazingFishing;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.Straiker123.GUICreatorAPI;
import me.Straiker123.GUICreatorAPI.Options;
import me.Straiker123.ItemCreatorAPI;
import me.Straiker123.TheAPI;

public class Create {
    public static ItemStack createItem(String name, Material material) {
    	ItemCreatorAPI a = TheAPI.getItemCreatorAPI(material);
    	a.setDisplayName(name);
    	return a.create();
    }
    
	public static ItemStack createItem(String name, Material material, List<String> lore) {
    	ItemCreatorAPI a = TheAPI.getItemCreatorAPI(material);
    	a.setDisplayName(name);
    	a.setLore(lore);
    	return a.create();
    }
	public static void prepareInv(GUICreatorAPI inv) {
		ItemStack item = createItem(" ", Material.BLACK_STAINED_GLASS_PANE);
		HashMap<Options, Object> setting = new HashMap<Options, Object>();
		setting.put(Options.CANT_BE_TAKEN, true);
		for(int i = 0; i<10; ++i)
		inv.setItem(i, item,setting);
		inv.setItem(17, item,setting);
		inv.setItem(18, item,setting);
		inv.setItem(26, item,setting);
		inv.setItem(27, item,setting);
		inv.setItem(35, item,setting);
		inv.setItem(36, item,setting);
		for(int i = 44; i<54; ++i)
		inv.setItem(i, item,setting);
	}
	public static void prepareInvBig(GUICreatorAPI inv) {
		ItemStack item = createItem(" ", Material.BLACK_STAINED_GLASS_PANE);
		HashMap<Options, Object> setting = new HashMap<Options, Object>();
		setting.put(Options.CANT_BE_TAKEN, true);
		for(int i = 45; i<54; ++i)
		inv.setItem(i, item,setting);
	}
	public static void prepareInvSmall(GUICreatorAPI inv) {
		ItemStack item = createItem(" ", Material.BLACK_STAINED_GLASS_PANE);
		HashMap<Options, Object> setting = new HashMap<Options, Object>();
		setting.put(Options.CANT_BE_TAKEN, true);
		for(int i = 9; i<18; ++i)
		inv.setItem(i, item,setting);
	}
}

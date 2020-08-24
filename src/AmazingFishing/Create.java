package AmazingFishing;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import me.DevTec.TheAPI.APIs.ItemCreatorAPI;
import me.DevTec.TheAPI.GUIAPI.GUI;
import me.DevTec.TheAPI.GUIAPI.ItemGUI;

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
	public static void prepareInv(GUI inv) {
		ItemStack item = createItem(" ", Material.BLACK_STAINED_GLASS_PANE);
		for(int i = 0; i<10; ++i)
		inv.setItem(i, new ItemGUI(item){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {}
		});

		inv.setItem(17, new ItemGUI(item){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {}
		});
		inv.setItem(18, new ItemGUI(item){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {}
		});
		inv.setItem(26, new ItemGUI(item){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {}
		});
		inv.setItem(27, new ItemGUI(item){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {}
		});
		inv.setItem(35, new ItemGUI(item){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {}
		});
		inv.setItem(36, new ItemGUI(item){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {}
		});
		for(int i = 44; i<54; ++i)
		inv.setItem(i,  new ItemGUI(item){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {}
		});
	}
	public static void prepareInvBig(GUI inv) {
		ItemStack item = createItem(" ", Material.BLACK_STAINED_GLASS_PANE);
		for(int i = 45; i<54; ++i)
		inv.setItem(i, new ItemGUI(item){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {}
		});

	}
	public static void prepareInvSmall(GUI inv) {
		ItemStack item = createItem(" ", Material.BLACK_STAINED_GLASS_PANE);
		for(int i = 9; i<18; ++i)
		inv.setItem(i, new ItemGUI(item){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {}
		});
	}
}

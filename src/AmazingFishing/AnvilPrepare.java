package AmazingFishing;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import me.devtec.theapi.TheAPI;

public class AnvilPrepare implements Listener {

	@EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent e) {
        if(e.getInventory().getItem(0) != null && e.getInventory().getItem(1) != null 
        		&& e.getInventory().getItem(0).getType() == Material.FISHING_ROD) {
            ItemStack result = new ItemStack(e.getInventory().getItem(0));
            if(e.getInventory().getItem(1).getType() == Material.ENCHANTED_BOOK) {
                EnchantmentStorageMeta bookmeta = (EnchantmentStorageMeta) e.getInventory().getItem(1).getItemMeta();
                Map<Enchantment, Integer> enchantments = bookmeta.getStoredEnchants();
                result.addUnsafeEnchantments(enchantments);
            }
            if(e.getInventory().getItem(1).getType() == Material.FISHING_ROD&& e.getInventory().getItem(0).getType() == Material.FISHING_ROD) {
            	result.setDurability((short) 0);
            }
            if(!e.getInventory().getItem(0).getItemMeta().getDisplayName().equalsIgnoreCase(e.getInventory().getItem(2).getItemMeta().getDisplayName())){
                result.getItemMeta().setDisplayName(TheAPI.colorize(e.getResult().getItemMeta().getDisplayName()));
            }
            
            e.setResult(result);
            return;
        }
        if(e.getInventory().getItem(0) != null && e.getInventory().getItem(0).getType() == Material.FISHING_ROD) {
        	if(e.getInventory().getItem(2)==null)return;
        	ItemStack coloured = e.getResult();
            if(!e.getInventory().getItem(0).getItemMeta().getDisplayName().equalsIgnoreCase(e.getInventory().getItem(2).getItemMeta().getDisplayName())){
            	ItemMeta rod = coloured.getItemMeta();
            	ItemMeta newRod = e.getResult().getItemMeta();
            	rod.setDisplayName(TheAPI.colorize(newRod.getDisplayName()));
            	coloured.setItemMeta(rod);
            	coloured.addUnsafeEnchantments(e.getInventory().getItem(0).getEnchantments());
            }
            e.setResult(coloured);
            return;
        }
	}
}
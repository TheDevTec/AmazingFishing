package AmazingFishing;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class AnvilPrepare implements Listener {

	
	@SuppressWarnings("deprecation")
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
            result.addUnsafeEnchantments(e.getInventory().getItem(1).getEnchantments());
            e.setResult(result);
        }
    }
}

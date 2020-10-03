package AmazingFishing;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;

import me.DevTec.AmazingFishing.Loader;

public class onFish implements Listener {
	@EventHandler
	public void onCatch(PlayerFishEvent e) {
			Player s = e.getPlayer();
			if(AFK.isAFK(s))return;
			if(s.hasPermission("amazingfishing.use") && !Loader.c.getStringList("Options.Disabled-Worlds").contains(s.getWorld().getName())) {
			if(e.getState()==State.CAUGHT_FISH && e.getCaught() instanceof Item) {
				Item i = (Item)e.getCaught();
				ItemStack rod= e.getPlayer().getEquipment().getItemInMainHand();
				ItemStack f = i.getItemStack();
				if(rod==null||f==null)return;
				if(bag.isFish(f)) {
					if(!Loader.c.getBoolean("Options.CustomFishOnlyWhileTournament")||Loader.c.getBoolean("Options.CustomFishOnlyWhileTournament")&&Tournament.running()) {
						if(Loader.c.getBoolean("Options.FishRemove"))
							i.remove();
				double amount = 1;
				List<CEnch> enchs = new ArrayList<CEnch>();
				for(CEnch c : Loader.getEnchants()) {
					if(rod.getEnchantments().containsKey(c)) {
						enchs.add(c);
						amount+=c.getBonus("Amount",rod.getEnchantments().get(c));
					}}
				for (int ss =1; ss<=amount;ss++)
					ByBiome.generateFish(enchs,s, f.getType(),e.getHook().getLocation());
					}}else {
			if(Loader.c.getBoolean("Options.Treasures"))
			if(!Loader.c.getBoolean("Options.TreasuresOnlyWhileTournament")||Loader.c.getBoolean("Options.TreasuresOnlyWhileTournament")&&Tournament.running())
				Utils.giveTreasure(s);
			
			}}
			}
	}
}

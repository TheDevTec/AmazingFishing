package me.devtec.amazingfishing.listeners;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.fishing.Fish;
import me.devtec.amazingfishing.fishing.FishingItem;
import me.devtec.amazingfishing.fishing.Junk;
import me.devtec.amazingfishing.fishing.enums.ItemAction;
import me.devtec.amazingfishing.player.Fisher;
import me.devtec.amazingfishing.player.Statistics;
import me.devtec.amazingfishing.utils.Calculator;
import me.devtec.amazingfishing.utils.ItemUtils;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.shared.Ref;

public class CatchFish implements Listener {

	private static Method acc = Ref.method(PlayerFishEvent.class, "getHook");
	private Field biteTime;

	public CatchFish() {
		switch (Ref.serverVersionInt()) {
		case 7:
			biteTime = Ref.field(Ref.nms("", "EntityFishingHook"), "ay");
			break;
		case 8:
			biteTime = Ref.field(Ref.nms("", "EntityFishingHook"), "aw");
			break;
		case 12:
			biteTime = Ref.field(Ref.nms("", "EntityFishingHook"), "h");
			break;
		case 14:
			biteTime = Ref.field(Ref.nms("", "EntityFishingHook"), "as");
			break;
		case 16:
			biteTime = Ref.field(Ref.nms("", "EntityFishingHook"), "waitTime");
			break;
		case 17:
			biteTime = Ref.field(Ref.nms("world.entity.projectile", "EntityFishingHook"), "ar");
			break;
		case 20:
			biteTime = Ref.field(Ref.nms("world.entity.projectile", "EntityFishingHook"), "k");
			break;
		case 19:
			if (Ref.serverVersionRelease() == 3) {
				biteTime = Ref.field(Ref.nms("world.entity.projectile", "EntityFishingHook"), "k");
				break;
			}
		case 18:
			biteTime = Ref.field(Ref.nms("world.entity.projectile", "EntityFishingHook"), "as");
			break;
		default: // 1.18, 1.19
			biteTime = Ref.field(Ref.nms("world.entity.projectile", "EntityFishingHook"), "as");
			break;
		}
	}

	/**
	 * 1) If {@link State} is FISHING (player is fishing) we are editing fishing time. 
	 * 		This time is edited because of our Enchantments.  </br>
	 * 2) If {@link State} is CAUGHT_FISH we are creating our custom {@link FishingItem}. 
	 *  	This can be {@link Junk} or {@link Fish} item. </br>
	 *   	• First we are generating available items that player can catch. 
	 *   		Then we are fixing item chances (if total chance is more than 100%) </br>
	 *   	• Then we are selecting one {@link FishingItem} from this list. (using our {@link Calculator} class)  </br>
	 *   	• After generating one {@link FishingItem} we are generating {@link ItemStack} and 
	 *   		giving the item to player (like normal fishing - throwing it :D)
	 *   
	 *   
	 */
	@EventHandler
	public void onCatch(PlayerFishEvent event) {
		
		if (event.getState() == PlayerFishEvent.State.FISHING) {
			//TODO - enchanting editing bite time
			
			// Creating and calling custom event
			AmazingFishingPlayerFishEvent custom_event = event(event.getPlayer(), event.getState(), event);

			// Check if the event is not cancelled
			if (!custom_event.isCancelled()) {
			    //TODO
			}
			
		}
		if(event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
			Player player = event.getPlayer();
			Fisher fisher = API.getFisher(player);
			Entity caughtItem = event.getCaught();
			
			Object hook = Ref.invoke(event, CatchFish.acc);
			Location hookLocation = hook instanceof org.bukkit.entity.Fish ?
					((org.bukkit.entity.Fish) hook).getLocation() : ((FishHook) hook).getLocation();

			HashMap<FishingItem, Double> generatedList = Calculator.normalizeFishingItemChances(fisher.generateAvailableItems(hookLocation));
			
			// if there is generated fish list that the player is able to catch
			if(!generatedList.isEmpty()) {
				//removing default item
				caughtItem.remove();
				//Getting one fish from list
				FishingItem fishingItem =  Calculator.getRandomFishingItem(generatedList);

				// Creating and calling custom event
				AmazingFishingPlayerFishEvent custom_event = event(player, event.getState(), event, fishingItem);
				
				if (!custom_event.isCancelled()) {
					//Placeholders that will be used
					Placeholders placeholders = Placeholders.c()
							.add("fish_chance_final", generatedList.get(fishingItem))
							.add("loc_x", hookLocation.getX())
							.add("loc_y", hookLocation.getY())
							.add("loc_z", hookLocation.getZ())
							.add("loc_biome", hookLocation.getBlock().getBiome().name())
							.add("loc_world", hookLocation.getWorld().getName());
					
					// running message
					fishingItem.runMessages(player, ItemAction.CATCH, placeholders);
					// %fish_chance_final% is final chance to catch this fish... always different
					ItemStack item = fishingItem.generate(player, placeholders );
					//giving item to player (like normal fishing)
					ItemUtils.giveItem(event.getCaught(), item, player, hookLocation);
					// running commands
					fishingItem.runCommands(player, ItemAction.CATCH, placeholders);
				}
				else
					event.setCancelled(custom_event.isCancelled());
				
			}
			
		}
	} //event ending

	// Creating and calling custom event
	private AmazingFishingPlayerFishEvent event(Player player, State state, PlayerFishEvent event) {
		AmazingFishingPlayerFishEvent custom_event = new 
				AmazingFishingPlayerFishEvent(player, state, event);
		// Call the event
		Bukkit.getServer().getPluginManager().callEvent(custom_event);
		return custom_event;
	}

	// Creating and calling custom event
	private AmazingFishingPlayerFishEvent event(Player player, State state, PlayerFishEvent event, FishingItem caught) {
		AmazingFishingPlayerFishEvent custom_event = new 
				AmazingFishingPlayerFishEvent(player, state, event, caught);
		// Call the event
		Bukkit.getServer().getPluginManager().callEvent(custom_event);
		return custom_event;
	}
}

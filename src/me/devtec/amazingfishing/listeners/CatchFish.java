package me.devtec.amazingfishing.listeners;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.fishing.FishingItem;
import me.devtec.amazingfishing.player.Fisher;
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
	
	@EventHandler
	public void onCatch(PlayerFishEvent event) {

		if (event.getState() == PlayerFishEvent.State.FISHING) {
			//TODO - enchanting editing bite time
		}
		if(event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
			Player player = event.getPlayer();
			Fisher fisher = API.getFisher(player);
			Entity caughtItem = event.getCaught();
			
			Object hook = Ref.invoke(event, CatchFish.acc);
			Location hookLocation = hook instanceof org.bukkit.entity.Fish ?
					((org.bukkit.entity.Fish) hook).getLocation() : ((FishHook) hook).getLocation();

			HashMap<FishingItem, Double> generatedList = Calculator.normalizeFishChances(fisher.generateAvailableItems(hookLocation));
			if(!generatedList.isEmpty()) {
				//removing default item
				caughtItem.remove();
				
				FishingItem fishingItem =  Calculator.getRandomFish(generatedList);
				// %fish_chance_final% is final chance to catch this fish... always different
				ItemStack item = fishingItem.generate(player, Placeholders.c().add("fish_chance_final", generatedList.get(fishingItem)));
				//giving item to player
				ItemUtils.giveItem(event.getCaught(), item, player, hookLocation);
			}
			
			
			
		}
	} //event ending
}

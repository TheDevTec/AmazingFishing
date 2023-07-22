package me.devtec.amazingfishing.listeners;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.FishHook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.fishing.FishingItem;
import me.devtec.amazingfishing.player.Fisher;
import me.devtec.amazingfishing.utils.Calculator;
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
			
		}
		if(event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
			Fisher fisher = API.getFisher(event.getPlayer());
			
			Object hook = Ref.invoke(event, CatchFish.acc);
			Location hookLocation = hook instanceof org.bukkit.entity.Fish ?
					((org.bukkit.entity.Fish) hook).getLocation() : ((FishHook) hook).getLocation();

			HashMap<FishingItem, Double> generatedList = Calculator.normalizeFishChances(fisher.generateAvailableItems(hookLocation));
			FishingItem item =  Calculator.getRandomFish(generatedList);
			
			
		}
	}
}

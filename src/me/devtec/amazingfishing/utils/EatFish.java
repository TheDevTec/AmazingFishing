package me.devtec.amazingfishing.utils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.construct.FishAction;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.placeholderapi.PlaceholderAPI;

public class EatFish implements Listener {
	
	@EventHandler
	public void onEat(PlayerItemConsumeEvent e) {
		if(API.isFish(e.getItem())) {
			Fish f = API.getFish(e.getItem());
			if(f!=null) {
				for(String s : f.getMessages(FishAction.EAT))
					TheAPI.msg(PlaceholderAPI.setPlaceholders(e.getPlayer(), s), e.getPlayer());
				for(String s : f.getCommands(FishAction.EAT))
					TheAPI.sudoConsole(PlaceholderAPI.setPlaceholders(e.getPlayer(), s));
			}
		}
	}
}
package me.devtec.amazingfishing.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.devtec.amazingfishing.API;

public class PlayerQuit implements Listener {
	
	@EventHandler
	public void playerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		API.playerQuit(player);
	}

}

package me.devtec.amazingfishing.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.theapi.bukkit.BukkitLoader;

public class Utils {

	
	public static void runMessages(Player player, List<String> msgs, Placeholders placeholders) {
		if(msgs == null || msgs.isEmpty())
			return;
		
		if(placeholders == null)
			placeholders = Placeholders.c();
		
		placeholders.addPlayer("player", player);
		for(String message: msgs) {
			MessageUtils.sendPluginMessage(player, message, placeholders, player);
		}
	}
	
	public static void runCommands(Player player, List<String> cmds, Placeholders placeholders) {
		if(cmds==null || cmds.isEmpty())
			return;

		if(placeholders == null)
			placeholders = Placeholders.c();
		placeholders.addPlayer("player", player);
		
		//Final ...
		Placeholders plc = placeholders;
		for(String command: cmds) {
			BukkitLoader.getNmsProvider().postToMainThread(() -> {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), plc.apply(command));
			});
		}
	}
	
}

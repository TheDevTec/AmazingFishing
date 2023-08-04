package me.devtec.amazingfishing.utils;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.guis.MenuLoader;

public class AmazingFishingCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String arg2, String[] args) {
		if(args.length == 0) {
			((Player)s).playSound( ((Player)s).getLocation(), Sound.BLOCK_CHEST_OPEN, 5, 10);
			MenuLoader.openMenu((Player)s, "main");
		}
		
		
		
		return true;
	}

}

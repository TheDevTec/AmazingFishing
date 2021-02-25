package me.devtec.amazingfishing.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Enchant;
import me.devtec.amazingfishing.gui.Help;
import me.devtec.amazingfishing.gui.Help.PlayerType;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.utils.StringUtils;

public class AmazingFishingCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command var2, String var3, String[] args) {
		if(args.length==0) {
			Help.open( (Player)s,PlayerType.Player);
			//TheAPI.msg("/FishMenu Enchant <enchant> <level>", s);
			//TheAPI.msg("/FishMenu Reload", s);
			return true;
		}
		if(args[0].equalsIgnoreCase("enchant")) {
			if(args.length==1) {
				TheAPI.msg("/FishMenu Enchant <enchant>", s);
				return true;
			}
			if(args.length==2) {
				TheAPI.msg("Item in your hand enchanted with enchantment "+args[1].toLowerCase()+" to the level "+Enchant.enchants.get(args[1].toLowerCase()).enchant(((Player)s).getItemInHand(), 1), s);
				return true;
			}
			TheAPI.msg("Item in your hand enchanted with enchantment "+args[1].toLowerCase()+" to the level "+Enchant.enchants.get(args[1].toLowerCase()).enchant(((Player)s).getItemInHand(), StringUtils.getInt(args[2])), s);
			return true;
		}
		if(args[0].equalsIgnoreCase("reload")) {
			Loader.reload(s, true);
			return true;
		}
		return true;
	}

}

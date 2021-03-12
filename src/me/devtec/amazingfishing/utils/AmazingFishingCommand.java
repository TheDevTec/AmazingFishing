package me.devtec.amazingfishing.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Enchant;
import me.devtec.amazingfishing.gui.Help;
import me.devtec.amazingfishing.utils.tournament.TournamentType;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.utils.StringUtils;

public class AmazingFishingCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command var2, String var3, String[] args) {
		if(args.length==0) {
			Help.open((Player)s);
			//TheAPI.msg("/FishMenu Enchant <enchant> <level>", s);
			//TheAPI.msg("/FishMenu Reload", s);
			return true;
		}
		if(args[0].equalsIgnoreCase("test")) {
			Player p = ((Player)s);
			Statistics.addTournamentData(p, TournamentType.LENGTH, 1);
			Statistics.addTournamentData(p, TournamentType.LENGTH, 2);
			Statistics.addTournamentData(p, TournamentType.TOTAL_LENGTH, 3);
			Statistics.addTournamentData(p, TournamentType.TOTAL_WEIGHT, 4);
			Statistics.addTournamentData(p, TournamentType.WEIGHT, 5);
			Statistics.addTournamentData(p, TournamentType.AMOUNT, 0);
			Statistics.addTournamentData(p, null, 1);
			Bukkit.broadcastMessage("Statistiky přidány, mrkni se do dat hlupáku");
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

package me.devtec.amazingfishing.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Enchant;
import me.devtec.amazingfishing.gui.Help;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.utils.StringUtils;

public class AmazingFishingCommand implements CommandExecutor, TabCompleter {

	private static DecimalFormat ff = new DecimalFormat("###,###.#");
	
	@Override
	public boolean onCommand(CommandSender s, Command var2, String var3, String[] args) {
		if(args.length==0) {
			Help.open((Player)s);
			//TheAPI.msg("/FishMenu Enchant <enchant> <level>", s);
			//TheAPI.msg("/FishMenu Reload", s);
			return true;
		}
		/*if(args[0].equalsIgnoreCase("test")) {
			Player p = ((Player)s);
			Quests.start(p.getName(), Quests.quests.get("common"));;
			Bukkit.broadcastMessage("Loaded");
			return true;
		}*/
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

		if(args[0].equalsIgnoreCase("points") && Loader.has(s, "amazingfishing.points.manager")) {
			if(args.length==1) {
				TheAPI.msg("/Fish Points Add <player> <points>", s);
				TheAPI.msg("/Fish Points Get <player> ", s);
				TheAPI.msg("/Fish Points Set <player> <points>", s);
				return true;
			}
			if(args[1].equalsIgnoreCase("add")) {
				if(args.length==4) {
					double points = StringUtils.getDouble(args[3]);
					API.getPoints().add(args[2], points);
					Loader.msg(Trans.s("Points.Add").replace("%player%", args[2]).replace("%add%", ""+points)
							.replace("%points%", ff.format(StringUtils.getDouble(String.format("%.2f",API.getPoints().get(args[2]) ))).replace(",", ".").replaceAll("[^0-9.]+", ",") ), s);
					return true;
				}
				TheAPI.msg("/Fish Points Add <player> <points>", s);
				return true;
			}
			if(args[1].equalsIgnoreCase("get")) {
				if(args.length==3) {
					Loader.msg(Trans.s("Points.Get").replace("%player%", args[2])
							.replace("%points%", ff.format(StringUtils.getDouble(String.format("%.2f",API.getPoints().get(args[2]) ))).replace(",", ".").replaceAll("[^0-9.]+", ",") ), s);
					return true;
				}
				TheAPI.msg("/Fish Points Get <player> ", s);
				return true;
			}
			if(args[1].equalsIgnoreCase("set")) {
				if(args.length==4) {
					double points = StringUtils.getDouble(args[3]);
					API.getPoints().set(args[2], points);
					Loader.msg(Trans.s("Points.Set").replace("%player%", args[2]).replace("%add%", ff.format(StringUtils.getDouble(String.format("%.2f", points ))).replace(",", ".").replaceAll("[^0-9.]+", ",") )
							.replace("%points%", ""+points) , s);
					return true;
				}
				TheAPI.msg("/Fish Points Set <player> <points>", s);
				return true;
			}

			TheAPI.msg("/Fish Points Add <player> <points>", s);
			TheAPI.msg("/Fish Points Get <player> ", s);
			TheAPI.msg("/Fish Points Set <player> <points>", s);
			return true;
		}
		if(args[0].equalsIgnoreCase("reload")) {
			Loader.reload(s, true);
			return true;
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender s, Command arg1,
			String arg2, String[] args) {
		List<String> c = new ArrayList<>();
		if (s.hasPermission("amazingfishing.points.manager")) {
			if(args.length==1)
				c.addAll( StringUtil.copyPartialMatches(args[0], Arrays.asList("Points"), new ArrayList<>()) );
			if(args.length==2)
				c.addAll( StringUtil.copyPartialMatches(args[1], Arrays.asList("Add", "Get", "Set"), new ArrayList<>()) );
			if(args.length==3) return null;
		}
		return c;
	}
}

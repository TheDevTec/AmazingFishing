package me.devtec.amazingfishing.utils;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Enchant;
import me.devtec.amazingfishing.gui.Bag;
import me.devtec.amazingfishing.gui.EnchantTable;
import me.devtec.amazingfishing.gui.EnchantTable.EnchantGUI;
import me.devtec.amazingfishing.gui.Help;
import me.devtec.amazingfishing.gui.Index;
import me.devtec.amazingfishing.gui.QuestGUI;
import me.devtec.amazingfishing.gui.Settings;
import me.devtec.amazingfishing.gui.Shop;
import me.devtec.amazingfishing.gui.Shop.ShopType;
import me.devtec.amazingfishing.utils.tournament.TournamentManager;
import me.devtec.amazingfishing.utils.tournament.TournamentType;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.utils.StringUtils;

public class AmazingFishingCommand implements CommandExecutor/*, TabCompleter*/ {

	private static DecimalFormat ff = new DecimalFormat("###,###.#");
	
	@Override
	public boolean onCommand(CommandSender s, Command var2, String var3, String[] args) {
		if(args.length==0) {
			Help.open((Player)s);
			return true;
		}
		if(args[0].equalsIgnoreCase("help") && s.hasPermission("amazingfishing.command.help")) {
			TheAPI.msg("/Fish Open <player>", s);
			TheAPI.msg("/Fish Shop <player>", s);
			TheAPI.msg("/Fish SellShop <player>", s);
			TheAPI.msg("/Fish Convertor <player>", s);
			TheAPI.msg("/Fish Settings <player>", s);
			TheAPI.msg("/Fish Quests <player>", s);
			TheAPI.msg("/Fish Bag <player>", s);
			TheAPI.msg("/Fish Points Add <player> <amount>", s);
			TheAPI.msg("/Fish Points Remove <player> <amount>", s);
			TheAPI.msg("/Fish Points Get <player>", s);
			TheAPI.msg("/Fish Enchant <enchant> [level]", s);
			TheAPI.msg("/Fish EnchantTable <player>", s);
			TheAPI.msg("/Fish Reload", s);
			return true;
		}
		if(args[0].equalsIgnoreCase("open") && s.hasPermission("amazingfishing.command.open")) {
			if(args.length==1) {
				if(s instanceof Player) {
					Help.open((Player)s);
					TheAPI.msg("Opening menu..", s);
					return true;
				}
				TheAPI.msg("/Fish Open <player>", s);
				return true;
			}
			Player p = TheAPI.getPlayer(args[1]);
			if(p==null) {
				TheAPI.msg("Player "+args[1]+" isn't online", s);
				return true;
			}
			Help.open(p);
			TheAPI.msg("Opening menu for player "+p.getName(), s);
			return true;
		}
		if(args[0].equalsIgnoreCase("shop") && s.hasPermission("amazingfishing.command.shop")) {
			if(args.length==1) {
				if(s instanceof Player) {
					Shop.openShop((Player)s, ShopType.BUY);
					TheAPI.msg("Opening shop..", s);
					return true;
				}
				TheAPI.msg("/Fish Shop <player>", s);
				return true;
			}
			Player p = TheAPI.getPlayer(args[1]);
			if(p==null) {
				TheAPI.msg("Player "+args[1]+" isn't online", s);
				return true;
			}
			Shop.openShop(p, ShopType.BUY);
			TheAPI.msg("Opening shop for player "+p.getName(), s);
			return true;
		}
		if(args[0].equalsIgnoreCase("sellshop") && s.hasPermission("amazingfishing.command.sellshop")) {
			if(args.length==1) {
				if(s instanceof Player) {
					Shop.openShop((Player)s, ShopType.SELL);
					TheAPI.msg("Opening selling shop..", s);
					return true;
				}
				TheAPI.msg("/Fish SellShop <player>", s);
				return true;
			}
			Player p = TheAPI.getPlayer(args[1]);
			if(p==null) {
				TheAPI.msg("Player "+args[1]+" isn't online", s);
				return true;
			}
			Shop.openShop(p, ShopType.SELL);
			TheAPI.msg("Opening selling shop for player "+p.getName(), s);
			return true;
		}
		if(args[0].equalsIgnoreCase("convertor") && s.hasPermission("amazingfishing.command.convertor")) {
			if(args.length==1) {
				if(s instanceof Player) {
					Shop.openShop((Player)s, ShopType.CONVERTOR);
					TheAPI.msg("Opening convertor..", s);
					return true;
				}
				TheAPI.msg("/Fish Convertor <player>", s);
				return true;
			}
			Player p = TheAPI.getPlayer(args[1]);
			if(p==null) {
				TheAPI.msg("Player "+args[1]+" isn't online", s);
				return true;
			}
			Shop.openShop(p, ShopType.CONVERTOR);
			TheAPI.msg("Opening convertor for player "+p.getName(), s);
			return true;
		}
		if(args[0].equalsIgnoreCase("quests") && s.hasPermission("amazingfishing.command.quests")) {
			if(args.length==1) {
				if(s instanceof Player) {
					QuestGUI.open((Player)s);
					TheAPI.msg("Opening quests menu..", s);
					return true;
				}
				TheAPI.msg("/Fish Quests <player>", s);
				return true;
			}
			Player p = TheAPI.getPlayer(args[1]);
			if(p==null) {
				TheAPI.msg("Player "+args[1]+" isn't online", s);
				return true;
			}
			QuestGUI.open(p);
			TheAPI.msg("Opening quests menu for player "+p.getName(), s);
			return true;
		}
		if(args[0].equalsIgnoreCase("index") && s.hasPermission("amazingfishing.command.index")) {
			if(args.length==1) {
				if(s instanceof Player) {
					Index.open((Player)s);
					TheAPI.msg("Opening index menu..", s);
					return true;
				}
				TheAPI.msg("/Fish Index <player>", s);
				return true;
			}
			Player p = TheAPI.getPlayer(args[1]);
			if(p==null) {
				TheAPI.msg("Player "+args[1]+" isn't online", s);
				return true;
			}
			Index.open(p);
			TheAPI.msg("Opening index menu for player "+p.getName(), s);
			return true;
		}
		if(args[0].equalsIgnoreCase("settings") && s.hasPermission("amazingfishing.command.settings")) {
			if(args.length==1) {
				if(s instanceof Player) {
					Settings.open((Player)s);
					TheAPI.msg("Opening settings menu..", s);
					return true;
				}
				TheAPI.msg("/Fish Settings <player>", s);
				return true;
			}
			Player p = TheAPI.getPlayer(args[1]);
			if(p==null) {
				TheAPI.msg("Player "+args[1]+" isn't online", s);
				return true;
			}
			Settings.open(p);
			TheAPI.msg("Opening settings menu for player "+p.getName(), s);
			return true;
		}
		if((args[0].equalsIgnoreCase("enchanttable")||args[0].equalsIgnoreCase("enchanter")) && s.hasPermission("amazingfishing.command.enchanter")) {
			if(args.length==1) {
				if(s instanceof Player) {
					EnchantTable.open((Player)s, EnchantGUI.Main);
					TheAPI.msg("Opening enchanter menu..", s);
					return true;
				}
				TheAPI.msg("/Fish Enchanter <player>", s);
				return true;
			}
			Player p = TheAPI.getPlayer(args[1]);
			if(p==null) {
				TheAPI.msg("Player "+args[1]+" isn't online", s);
				return true;
			}
			EnchantTable.open(p, EnchantGUI.Main);
			TheAPI.msg("Opening enchanter menu for player "+p.getName(), s);
			return true;
		}
		if((args[0].equalsIgnoreCase("tournament")||args[0].equalsIgnoreCase("tournaments")) && s.hasPermission("amazingfishing.command.tournaments")) {
			if(args.length==1) {
				TheAPI.msg("/Fish Tournament Start <type> <time>", s);
				TheAPI.msg("/Fish Tournament Stop", s);

				TheAPI.msg("/Fish Tournament Start <world> <type> <time>", s);
				TheAPI.msg("/Fish Tournament Stop <world>", s);
				return true;
			}
			if(args[1].equalsIgnoreCase("start")) {
				if(args.length<=3) {
					TheAPI.msg("/Fish Tournament Start <type> <time>", s);
					TheAPI.msg("/Fish Tournament Start <world> <type> <time>", s);
					return true;
				}
				if(args.length==4) {
					try {
						TournamentType g= TournamentType.valueOf(args[3].toUpperCase());
						for(World w : Bukkit.getWorlds()) {
						TournamentManager.start(w, g, StringUtils.timeFromString(args[4]));
						TheAPI.msg("Started tournament in the world "+w.getName()+" of type "+args[3].toUpperCase(), s);
						}
					}catch(Exception | NoSuchFieldError e) {
						TheAPI.msg("Tournament type "+args[3]+" doesn't exist, valid types are: Amount, Length, Weight, Total_Length, Total_Weight", s);
					}
					return true;
				}
				World w= Bukkit.getWorld(args[2]);
				if(w==null) {
					TheAPI.msg("World "+args[2]+" doesn't exist", s);
					return true;
				}
				try {
					TournamentManager.start(w, TournamentType.valueOf(args[3].toUpperCase()), StringUtils.timeFromString(args[4]));
					TheAPI.msg("Started tournament in the world "+w.getName()+" of type "+args[3].toUpperCase(), s);
				}catch(Exception | NoSuchFieldError e) {
					TheAPI.msg("Tournament type "+args[3]+" doesn't exist, valid types are: Amount, Length, Weight, Total_Length, Total_Weight", s);
				}
				return true;
			}
			if(args[1].equalsIgnoreCase("stop")) {
				if(args.length==2) {
					for(World w : Bukkit.getWorlds())
						TournamentManager.get(w).stop(true);
					TheAPI.msg("All tournaments stopped", s);
					return true;
				}
				World w= Bukkit.getWorld(args[2]);
				if(w==null) {
					TheAPI.msg("World "+args[2]+" doesn't exist", s);
					return true;
				}
				TournamentManager.get(w).stop(true);
				TheAPI.msg("Tournament in the world "+w.getName()+" stopped", s);
				return true;
			}
			TheAPI.msg("/Fish Tournament Start <type> <time>", s);
			TheAPI.msg("/Fish Tournament Stop", s);

			TheAPI.msg("/Fish Tournament Start <world> <type> <time>", s);
			TheAPI.msg("/Fish Tournament Stop <world>", s);
			return true;
		}
		if(args[0].equalsIgnoreCase("bag") && s.hasPermission("amazingfishing.command.bag")) {
			if(args.length==1) {
				if(s instanceof Player) {
					Bag.openBag((Player)s);
					TheAPI.msg("Opening bag..", s);
					return true;
				}
				TheAPI.msg("/Fish Bag <player>", s);
				return true;
			}
			Player p = TheAPI.getPlayer(args[1]);
			if(p==null) {
				TheAPI.msg("Player "+args[1]+" isn't online", s);
				return true;
			}
			Bag.openBag(p);
			TheAPI.msg("Opening bag for player "+p.getName(), s);
			return true;
		}
		if(args[0].equalsIgnoreCase("enchant") && s.hasPermission("amazingfishing.command.enchant")) {
			if(args.length==1) {
				TheAPI.msg("/Fish Enchant <enchant>", s);
				return true;
			}
			if(args.length==2) {
				TheAPI.msg("Item in your hand enchanted with enchantment "+args[1].toLowerCase()+" to the level "+Enchant.enchants.get(args[1].toLowerCase()).enchant(((Player)s).getItemInHand(), 1), s);
				return true;
			}
			TheAPI.msg("Item in your hand enchanted with enchantment "+args[1].toLowerCase()+" to the level "+Enchant.enchants.get(args[1].toLowerCase()).enchant(((Player)s).getItemInHand(), StringUtils.getInt(args[2])), s);
			return true;
		}
		if(args[0].equalsIgnoreCase("points") && s.hasPermission("amazingfishing.command.points")) {
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
		if(args[0].equalsIgnoreCase("reload") && s.hasPermission("amazingfishing.command.reload")) {
			Loader.reload(s, true);
			return true;
		}
		return true;
	}

	/*@Override
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
	}*/
}

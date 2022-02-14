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
import me.devtec.amazingfishing.gui.AchievementGUI;
import me.devtec.amazingfishing.gui.Bag;
import me.devtec.amazingfishing.gui.EnchantTable;
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
			if(s instanceof Player)
				Help.open((Player)s);
			else
				TheAPI.msg("/Fish Help", s);
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
			TheAPI.msg("/Fish Tournament - For help", s);
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
					if(!Create.text("command.help.self").trim().isEmpty())
					TheAPI.msg(Create.text("command.help.self"), s);
					return true;
				}
				TheAPI.msg("/Fish Open <player>", s);
				return true;
			}
			Player p = TheAPI.getPlayer(args[1]);
			if(p==null) {
				TheAPI.msg(Create.text("command.help.offline").replace("%player%", args[1]), s);
				return true;
			}
			Help.open(p);
			TheAPI.msg(Create.text("command.help.other").replace("%player%", p.getName()), s);
			return true;
		}
		if(args[0].equalsIgnoreCase("shop") && s.hasPermission("amazingfishing.command.shop")) {
			if(args.length==1) {
				if(s instanceof Player) {
					Shop.openShop((Player)s, ShopType.BUY);
					if(!Create.text("command.buy-shop.self").trim().isEmpty())
					TheAPI.msg(Create.text("command.buy-shop.self"), s);
					return true;
				}
				TheAPI.msg("/Fish Shop <player>", s);
				return true;
			}
			Player p = TheAPI.getPlayer(args[1]);
			if(p==null) {
				TheAPI.msg(Create.text("command.buy-shop.offline").replace("%player%", args[1]), s);
				return true;
			}
			Shop.openShop(p, ShopType.BUY);
			TheAPI.msg(Create.text("command.buy-shop.other").replace("%player%", p.getName()), s);
			return true;
		}
		if(args[0].equalsIgnoreCase("sellshop") && s.hasPermission("amazingfishing.command.sellshop")) {
			if(args.length==1) {
				if(s instanceof Player) {
					Shop.openShop((Player)s, ShopType.SELL);
					if(!Create.text("command.sell-shop.self").trim().isEmpty())
					TheAPI.msg(Create.text("command.sell-shop.self"), s);
					return true;
				}
				TheAPI.msg("/Fish SellShop <player>", s);
				return true;
			}
			Player p = TheAPI.getPlayer(args[1]);
			if(p==null) {
				TheAPI.msg(Create.text("command.sell-shop.offline").replace("%player%", args[1]), s);
				return true;
			}
			Shop.openShop(p, ShopType.SELL);
			TheAPI.msg(Create.text("command.sell-shop.other").replace("%player%", p.getName()), s);
			return true;
		}
		if(args[0].equalsIgnoreCase("convertor") && s.hasPermission("amazingfishing.command.convertor")) {
			if(args.length==1) {
				if(s instanceof Player) {
					Shop.openShop((Player)s, ShopType.CONVERTOR);
					if(!Create.text("command.convertor.self").trim().isEmpty())
					TheAPI.msg(Create.text("command.convertor.self"), s);
					return true;
				}
				TheAPI.msg("/Fish Convertor <player>", s);
				return true;
			}
			Player p = TheAPI.getPlayer(args[1]);
			if(p==null) {
				TheAPI.msg(Create.text("command.convertor.offline").replace("%player%", args[1]), s);
				return true;
			}
			Shop.openShop(p, ShopType.CONVERTOR);
			TheAPI.msg(Create.text("command.convertor.other").replace("%player%", p.getName()), s);
			return true;
		}
		if(args[0].equalsIgnoreCase("quests") && s.hasPermission("amazingfishing.command.quests")) {
			if(args.length==1) {
				if(s instanceof Player) {
					QuestGUI.open((Player)s);
					if(!Create.text("command.quests.self").trim().isEmpty())
					TheAPI.msg(Create.text("command.quests.self"), s);
					return true;
				}
				TheAPI.msg("/Fish Quests <player>", s);
				return true;
			}
			Player p = TheAPI.getPlayer(args[1]);
			if(p==null) {
				TheAPI.msg(Create.text("command.quests.offline").replace("%player%", args[1]), s);
				return true;
			}
			QuestGUI.open(p);
			TheAPI.msg(Create.text("command.quests.other").replace("%player%", p.getName()), s);
			return true;
		}
		if(args[0].equalsIgnoreCase("achievements") && s.hasPermission("amazingfishing.command.achievements")) {
			if(args.length==1) {
				if(s instanceof Player) {
					AchievementGUI.open((Player)s);
					if(!Create.text("command.achievements.self").trim().isEmpty())
					TheAPI.msg(Create.text("command.achievements.self"), s);
					return true;
				}
				TheAPI.msg("/Fish Achievements <player>", s);
				return true;
			}
			Player p = TheAPI.getPlayer(args[1]);
			if(p==null) {
				TheAPI.msg(Create.text("command.achievements.offline").replace("%player%", args[1]), s);
				return true;
			}
			AchievementGUI.open(p);
			TheAPI.msg(Create.text("command.achievements.other").replace("%player%", p.getName()), s);
			return true;
		}
		if(args[0].equalsIgnoreCase("index") && s.hasPermission("amazingfishing.command.index")) {
			if(args.length==1) {
				if(s instanceof Player) {
					Index.open((Player)s);
					if(!Create.text("command.index.self").trim().isEmpty())
					TheAPI.msg(Create.text("command.index.self"), s);
					return true;
				}
				TheAPI.msg("/Fish Index <player>", s);
				return true;
			}
			Player p = TheAPI.getPlayer(args[1]);
			if(p==null) {
				TheAPI.msg(Create.text("command.index.offline").replace("%player%", args[1]), s);
				return true;
			}
			Index.open(p);
			TheAPI.msg(Create.text("command.index.other").replace("%player%", p.getName()), s);
			return true;
		}
		if(args[0].equalsIgnoreCase("settings") && s.hasPermission("amazingfishing.command.settings")) {
			if(args.length==1) {
				if(s instanceof Player) {
					Settings.open((Player)s);
					if(!Create.text("command.settings.self").trim().isEmpty())
					TheAPI.msg(Create.text("command.settings.self"), s);
					return true;
				}
				TheAPI.msg("/Fish Settings <player>", s);
				return true;
			}
			Player p = TheAPI.getPlayer(args[1]);
			if(p==null) {
				TheAPI.msg(Create.text("command.settings.offline").replace("%player%", args[1]), s);
				return true;
			}
			Settings.open(p);
			TheAPI.msg(Create.text("command.settings.other").replace("%player%", p.getName()), s);
			return true;
		}
		if((args[0].equalsIgnoreCase("enchanttable")||args[0].equalsIgnoreCase("enchanter")) && s.hasPermission("amazingfishing.command.enchanter")) {
			if(args.length==1) {
				if(s instanceof Player) {
					EnchantTable.openMain((Player)s);
					if(!Create.text("command.enchant-table.self").trim().isEmpty())
					TheAPI.msg(Create.text("command.enchant-table.self"), s);
					return true;
				}
				TheAPI.msg("/Fish Enchanter <player>", s);
				return true;
			}
			Player p = TheAPI.getPlayer(args[1]);
			if(p==null) {
				TheAPI.msg(Create.text("command.enchant-table.offline").replace("%player%", args[1]), s);
				return true;
			}
			EnchantTable.openMain(p);
			TheAPI.msg(Create.text("command.enchant-table.other").replace("%player%", p.getName()), s);
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
						TournamentType g= TournamentType.valueOf(args[2].toUpperCase());
						TournamentManager.start(null, g, StringUtils.timeFromString(args[3]));
						TheAPI.msg(Create.text("command.tournaments.start.global").replace("%time%", StringUtils.timeToString(StringUtils.timeFromString(args[4]))).replace("%type%", args[3].toLowerCase()), s);
						}catch(Exception | NoSuchFieldError e) {
						TheAPI.msg(Create.text("command.tournaments.start.invalid").replace("%type%", args[3].toLowerCase()), s);
						}
					return true;
				}
				World w= Bukkit.getWorld(args[2]);
				if(w==null) {
					TheAPI.msg(Create.text("command.tournaments.invalid").replace("%world%", args[2]), s);
					return true;
				}
				try {
					TournamentManager.start(w, TournamentType.valueOf(args[3].toUpperCase()), StringUtils.timeFromString(args[4]));
					TheAPI.msg(Create.text("command.tournaments.start.world").replace("%world%", w.getName()).replace("%time%", StringUtils.timeToString(StringUtils.timeFromString(args[4]))).replace("%type%", args[3].toLowerCase()), s);
				}catch(Exception | NoSuchFieldError e) {
					TheAPI.msg(Create.text("command.tournaments.start.invalid").replace("%type%", args[3].toLowerCase()), s);
				}
				return true;
			}
			if(args[1].equalsIgnoreCase("stop")) {
				if(args.length==2) {
					for(World w : Bukkit.getWorlds())
						if(TournamentManager.get(w)!=null)
							TournamentManager.get(w).stop(true);
					if(TournamentManager.getGlobal()!=null)
						TournamentManager.getGlobal().stop(true);
					TheAPI.msg(Create.text("command.tournaments.stop.all"), s);
					return true;
				}
				World w= Bukkit.getWorld(args[2]);
				if(w==null) {
					TheAPI.msg(Create.text("command.tournaments.invalid").replace("%world%", args[2]), s);
					return true;
				}
				TournamentManager.get(w).stop(true);
				TheAPI.msg(Create.text("command.tournaments.stop.world").replace("%world%", w.getName()), s);
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
					if(!Create.text("command.bag.self").trim().isEmpty())
						TheAPI.msg(Create.text("command.bag.self"), s);
					return true;
				}
				TheAPI.msg("/Fish Bag <player>", s);
				return true;
			}
			if(args.length==2) {
				Player p = TheAPI.getPlayer(args[1]);
				if(p==null) {
					TheAPI.msg(Create.text("command.bag.offline").replace("%player%", args[1]), s);
					return true;
				}
				Bag.openBag(p);
				TheAPI.msg(Create.text("command.bag.other").replace("%player%", p.getName()), s);
				return true;
			}
			Player p = TheAPI.getPlayer(args[1]);
			if(p==null) {
				TheAPI.msg(Create.text("command.bag.offline").replace("%player%", args[1]), s);
				return true;
			}
			Player t = TheAPI.getPlayer(args[2]);
			if(t==null) {
				TheAPI.msg(Create.text("command.bag.offline").replace("%player%", args[2]), s);
				return true;
			}
			Bag.openBag(p,t);
			TheAPI.msg(Create.text("command.bag.other-other").replace("%player%", p.getName()).replace("%target%", t.getName()), s);
			return true;
		}
		if(args[0].equalsIgnoreCase("enchant") && s.hasPermission("amazingfishing.command.enchant")) {
			if(args.length==1) {
				TheAPI.msg("/Fish Enchant <enchant>", s);
				return true;
			}
			if(Enchant.enchants.get(args[1].toLowerCase())==null) {
				TheAPI.msg(Create.text("command.enchant.invalid").replace("%enchant%", args[1].toLowerCase()), s);
				return true;
			}
			int level = 1;
			if(args.length>2)level=StringUtils.getInt(args[2]);
			if(level<=0)level=1;
			int to = Enchant.enchants.get(args[1].toLowerCase()).enchant(((Player)s).getItemInHand(), level);
			TheAPI.msg(Create.text("command.enchant.enchanted").replace("%enchant%", args[1].toLowerCase()).replace("%level%", ""+to), s);
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
					Loader.msg(Create.text("command.points.add").replace("%player%", args[2]).replace("%add%", ""+points)
							.replace("%points%", ff.format(StringUtils.getDouble(String.format("%.2f",API.getPoints().get(args[2]) ))).replace(",", ".").replaceAll("[^0-9.]+", ",") ), s);
					return true;
				}
				TheAPI.msg("/Fish Points Add <player> <points>", s);
				return true;
			}
			if(args[1].equalsIgnoreCase("get")||args[1].equalsIgnoreCase("balance")) {
				if(args.length==3) {
					Loader.msg(Create.text("command.points.balance").replace("%player%", args[2])
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
					Loader.msg(Create.text("command.points.set").replace("%player%", args[2]).replace("%add%", ff.format(StringUtils.getDouble(String.format("%.2f", points ))).replace(",", ".").replaceAll("[^0-9.]+", ",") )
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
		if( (args[0].equalsIgnoreCase("reload")||args[0].equalsIgnoreCase("rl")) && s.hasPermission("amazingfishing.command.reload")) {
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

package me.devtec.amazingfishingOLD.utils;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.devtec.amazingfishingOLD.API;
import me.devtec.amazingfishingOLD.Loader;
import me.devtec.amazingfishingOLD.construct.Enchant;
import me.devtec.amazingfishingOLD.construct.Fish;
import me.devtec.amazingfishingOLD.construct.FishType;
import me.devtec.amazingfishingOLD.construct.Treasure;
import me.devtec.amazingfishingOLD.gui.AchievementGUI;
import me.devtec.amazingfishingOLD.gui.Bag;
import me.devtec.amazingfishingOLD.gui.EnchantTable;
import me.devtec.amazingfishingOLD.gui.Help;
import me.devtec.amazingfishingOLD.gui.Index;
import me.devtec.amazingfishingOLD.gui.QuestGUI;
import me.devtec.amazingfishingOLD.gui.Settings;
import me.devtec.amazingfishingOLD.gui.Shop;
import me.devtec.amazingfishingOLD.gui.Shop.ShopType;
import me.devtec.amazingfishingOLD.utils.tournament.TournamentManager;
import me.devtec.amazingfishingOLD.utils.tournament.TournamentType;
import me.devtec.shared.dataholder.Config;
import me.devtec.shared.utility.ParseUtils;
import me.devtec.shared.utility.StringUtils;
import me.devtec.shared.utility.TimeUtils;
import me.devtec.theapi.bukkit.nms.NBTEdit;

public class AmazingFishingCommand implements CommandExecutor {

	private static DecimalFormat ff = new DecimalFormat("###,###.#");

	@Override
	public boolean onCommand(CommandSender s, Command var2, String var3, String[] args) {
		if (args.length == 0) {
			if (s instanceof Player)
				Help.open((Player) s);
			else
				Loader.msg("/Fish Help", s);
			return true;
		}
		if (args[0].equalsIgnoreCase("help") && s.hasPermission("amazingfishing.command.help")) {
			Loader.msg("/Fish Open <player>", s);
			Loader.msg("/Fish Shop <player>", s);
			Loader.msg("/Fish SellShop <player>", s);
			Loader.msg("/Fish Convertor <player>", s);
			Loader.msg("/Fish Settings <player>", s);
			Loader.msg("/Fish Quests <player>", s);
			Loader.msg("/Fish Bag <player>", s);
			Loader.msg("/Fish Tournament - For help", s);
			Loader.msg("/Fish Points Add <player> <amount>", s);
			Loader.msg("/Fish Points Remove <player> <amount>", s);
			Loader.msg("/Fish Points Get <player>", s);
			Loader.msg("/Fish Enchant <enchant> [level]", s);
			Loader.msg("/Fish EnchantTable <player>", s);
			Loader.msg("/Fish Reload", s);
			return true;
		}
		if (args[0].equalsIgnoreCase("open") && s.hasPermission("amazingfishing.command.open")) {
			if (args.length == 1 || !s.hasPermission("amazingfishing.command.open.other")) {
				if (s instanceof Player) {
					Help.open((Player) s);
					if (!Create.text("command.help.self").trim().isEmpty())
						Loader.msg(Create.text("command.help.self"), s);
					return true;
				}
				Loader.msg("/Fish Open <player>", s);
				return true;
			}
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				Loader.msg(Create.text("command.help.offline").replace("%player%", args[1]), s);
				return true;
			}
			Help.open(p);
			Loader.msg(Create.text("command.help.other").replace("%player%", p.getName()), s);
			return true;
		}
		if (args[0].equalsIgnoreCase("shop") && s.hasPermission("amazingfishing.command.shop")) {
			if (args.length == 1 || !s.hasPermission("amazingfishing.command.shop.other")) {
				if (s instanceof Player) {
					Shop.openShop((Player) s, ShopType.BUY);
					if (!Create.text("command.buy-shop.self").trim().isEmpty())
						Loader.msg(Create.text("command.buy-shop.self"), s);
					return true;
				}
				Loader.msg("/Fish Shop <player>", s);
				return true;
			}
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				Loader.msg(Create.text("command.buy-shop.offline").replace("%player%", args[1]), s);
				return true;
			}
			Shop.openShop(p, ShopType.BUY);
			Loader.msg(Create.text("command.buy-shop.other").replace("%player%", p.getName()), s);
			return true;
		}
		if (args[0].equalsIgnoreCase("sellshop") && s.hasPermission("amazingfishing.command.sellshop")) {
			if (args.length == 1 || !s.hasPermission("amazingfishing.command.sellshop.other")) {
				if (s instanceof Player) {
					Shop.openShop((Player) s, ShopType.SELL);
					if (!Create.text("command.sell-shop.self").trim().isEmpty())
						Loader.msg(Create.text("command.sell-shop.self"), s);
					return true;
				}
				Loader.msg("/Fish SellShop <player>", s);
				return true;
			}
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				Loader.msg(Create.text("command.sell-shop.offline").replace("%player%", args[1]), s);
				return true;
			}
			Shop.openShop(p, ShopType.SELL);
			Loader.msg(Create.text("command.sell-shop.other").replace("%player%", p.getName()), s);
			return true;
		}
		if (args[0].equalsIgnoreCase("convertor") && s.hasPermission("amazingfishing.command.convertor")) {
			if (args.length == 1 || !s.hasPermission("amazingfishing.command.convertor.other")) {
				if (s instanceof Player) {
					Shop.openShop((Player) s, ShopType.CONVERTOR);
					if (!Create.text("command.convertor.self").trim().isEmpty())
						Loader.msg(Create.text("command.convertor.self"), s);
					return true;
				}
				Loader.msg("/Fish Convertor <player>", s);
				return true;
			}
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				Loader.msg(Create.text("command.convertor.offline").replace("%player%", args[1]), s);
				return true;
			}
			Shop.openShop(p, ShopType.CONVERTOR);
			Loader.msg(Create.text("command.convertor.other").replace("%player%", p.getName()), s);
			return true;
		}
		if (args[0].equalsIgnoreCase("quests") && s.hasPermission("amazingfishing.command.quests")) {
			if (args.length == 1 || !s.hasPermission("amazingfishing.command.quests.other")) {
				if (s instanceof Player) {
					QuestGUI.open((Player) s);
					if (!Create.text("command.quests.self").trim().isEmpty())
						Loader.msg(Create.text("command.quests.self"), s);
					return true;
				}
				Loader.msg("/Fish Quests <player>", s);
				return true;
			}
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				Loader.msg(Create.text("command.quests.offline").replace("%player%", args[1]), s);
				return true;
			}
			QuestGUI.open(p);
			Loader.msg(Create.text("command.quests.other").replace("%player%", p.getName()), s);
			return true;
		}
		if (args[0].equalsIgnoreCase("achievements") && s.hasPermission("amazingfishing.command.achievements")) {
			if (args.length == 1 || !s.hasPermission("amazingfishing.command.achievements.other")) {
				if (s instanceof Player) {
					AchievementGUI.open((Player) s);
					if (!Create.text("command.achievements.self").trim().isEmpty())
						Loader.msg(Create.text("command.achievements.self"), s);
					return true;
				}
				Loader.msg("/Fish Achievements <player>", s);
				return true;
			}
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				Loader.msg(Create.text("command.achievements.offline").replace("%player%", args[1]), s);
				return true;
			}
			AchievementGUI.open(p);
			Loader.msg(Create.text("command.achievements.other").replace("%player%", p.getName()), s);
			return true;
		}
		if (args[0].equalsIgnoreCase("index") && s.hasPermission("amazingfishing.command.index")) {
			if (args.length == 1 || !s.hasPermission("amazingfishing.command.index.other")) {
				if (s instanceof Player) {
					Index.open((Player) s);
					if (!Create.text("command.index.self").trim().isEmpty())
						Loader.msg(Create.text("command.index.self"), s);
					return true;
				}
				Loader.msg("/Fish Index <player>", s);
				return true;
			}
			if (args.length == 2) {
				Player p = Bukkit.getPlayer(args[1]);
				if (p == null) {
					Loader.msg(Create.text("command.index.offline").replace("%player%", args[1]), s);
					return true;
				}
				Index.open(p);
				Loader.msg(Create.text("command.index.other").replace("%player%", p.getName()), s);
				return true;
			}
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				Loader.msg(Create.text("command.index.offline").replace("%player%", args[1]), s);
				return true;
			}
			FishType type;
			try {
				type = FishType.valueOf(args[2].toUpperCase());
				if (type == null) {
					Loader.msg(Create.text("command.index.wrong-category").replace("%category%", args[2]), s);
					return true;
				}
			} catch (Exception | NoSuchFieldError e) {
				Loader.msg(Create.text("command.index.wrong-category").replace("%category%", args[2]), s);
				return true;
			}
			Index.open(p, type, 0);
			Loader.msg(Create.text("command.index.other-category").replace("%player%", p.getName()).replace("%category%", type.name().toLowerCase()), s);
			return true;
		}
		if (args[0].equalsIgnoreCase("settings") && s.hasPermission("amazingfishing.command.settings")) {
			if (args.length == 1 || !s.hasPermission("amazingfishing.command.settings.other")) {
				if (s instanceof Player) {
					Settings.open((Player) s);
					if (!Create.text("command.settings.self").trim().isEmpty())
						Loader.msg(Create.text("command.settings.self"), s);
					return true;
				}
				Loader.msg("/Fish Settings <player>", s);
				return true;
			}
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				Loader.msg(Create.text("command.settings.offline").replace("%player%", args[1]), s);
				return true;
			}
			Settings.open(p);
			Loader.msg(Create.text("command.settings.other").replace("%player%", p.getName()), s);
			return true;
		}
		if ((args[0].equalsIgnoreCase("enchanttable") || args[0].equalsIgnoreCase("enchanter")) && s.hasPermission("amazingfishing.command.enchanter")) {
			if (args.length == 1 || !s.hasPermission("amazingfishing.command.enchanter.other")) {
				if (s instanceof Player) {
					EnchantTable.openMain((Player) s);
					if (!Create.text("command.enchant-table.self").trim().isEmpty())
						Loader.msg(Create.text("command.enchant-table.self"), s);
					return true;
				}
				Loader.msg("/Fish Enchanter <player>", s);
				return true;
			}
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				Loader.msg(Create.text("command.enchant-table.offline").replace("%player%", args[1]), s);
				return true;
			}
			EnchantTable.openMain(p);
			Loader.msg(Create.text("command.enchant-table.other").replace("%player%", p.getName()), s);
			return true;
		}
		if ((args[0].equalsIgnoreCase("tournament") || args[0].equalsIgnoreCase("tournaments")) && s.hasPermission("amazingfishing.command.tournaments")) {
			if (args.length == 1) {
				Loader.msg("/Fish Tournament Start <type> <time>", s);
				Loader.msg("/Fish Tournament Stop", s);

				Loader.msg("/Fish Tournament Start <world> <type> <time>", s);
				Loader.msg("/Fish Tournament Stop <world>", s);
				return true;
			}
			if (args[1].equalsIgnoreCase("start")) {
				if (args.length <= 3) {
					Loader.msg("/Fish Tournament Start <type> <time>", s);
					Loader.msg("/Fish Tournament Start <world> <type> <time>", s);
					return true;
				}
				if (args.length == 4) {
					try {
						TournamentType g = TournamentType.valueOf(args[2].toUpperCase());
						TournamentManager.start(null, g, TimeUtils.timeFromString(args[3]));
						Loader.msg(
								Create.text("command.tournaments.start.global").replace("%time%", TimeUtils.timeToString(TimeUtils.timeFromString(args[3]))).replace("%type%", args[2].toLowerCase()),
								s);
					} catch (Exception | NoSuchFieldError e) {
						// e.printStackTrace();
						Loader.msg(Create.text("command.tournaments.start.invalid").replace("%type%", args[2].toLowerCase()), s);
					}
					return true;
				}
				World w = Bukkit.getWorld(args[2]);
				if (w == null) {
					Loader.msg(Create.text("command.tournaments.invalid").replace("%world%", args[2]), s);
					return true;
				}
				try {
					TournamentManager.start(w, TournamentType.valueOf(args[3].toUpperCase()), TimeUtils.timeFromString(args[4]));
					Loader.msg(Create.text("command.tournaments.start.world").replace("%world%", w.getName()).replace("%time%", TimeUtils.timeToString(TimeUtils.timeFromString(args[4])))
							.replace("%type%", args[3].toLowerCase()), s);
				} catch (Exception | NoSuchFieldError e) {
					Loader.msg(Create.text("command.tournaments.start.invalid").replace("%type%", args[3].toLowerCase()), s);
				}
				return true;
			}
			if (args[1].equalsIgnoreCase("stop")) {
				if (args.length == 2) {
					for (World w : Bukkit.getWorlds())
						if (TournamentManager.get(w) != null)
							TournamentManager.get(w).stop(true);
					if (TournamentManager.getGlobal() != null)
						TournamentManager.getGlobal().stop(true);
					Loader.msg(Create.text("command.tournaments.stop.all"), s);
					return true;
				}
				World w = Bukkit.getWorld(args[2]);
				if (w == null) {
					Loader.msg(Create.text("command.tournaments.invalid").replace("%world%", args[2]), s);
					return true;
				}
				TournamentManager.get(w).stop(true);
				Loader.msg(Create.text("command.tournaments.stop.world").replace("%world%", w.getName()), s);
				return true;
			}
			Loader.msg("/Fish Tournament Start <type> <time>", s);
			Loader.msg("/Fish Tournament Stop", s);

			Loader.msg("/Fish Tournament Start <world> <type> <time>", s);
			Loader.msg("/Fish Tournament Stop <world>", s);
			return true;
		}
		if (args[0].equalsIgnoreCase("bag") && s.hasPermission("amazingfishing.command.bag")) {
			if (args.length == 1 || !s.hasPermission("amazingfishing.command.bag.other")) {
				if (s instanceof Player) {
					Bag.openBag((Player) s);
					if (!Create.text("command.bag.self").trim().isEmpty())
						Loader.msg(Create.text("command.bag.self"), s);
					return true;
				}
				Loader.msg("/Fish Bag <player>", s);
				return true;
			}
			if (args.length == 2) {
				Player p = Bukkit.getPlayer(args[1]);
				if (p == null) {
					Loader.msg(Create.text("command.bag.offline").replace("%player%", args[1]), s);
					return true;
				}
				Bag.openBag(p);
				Loader.msg(Create.text("command.bag.other").replace("%player%", p.getName()), s);
				return true;
			}
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				Loader.msg(Create.text("command.bag.offline").replace("%player%", args[1]), s);
				return true;
			}
			Player t = Bukkit.getPlayer(args[2]);
			if (t == null) {
				Loader.msg(Create.text("command.bag.offline").replace("%player%", args[2]), s);
				return true;
			}
			Bag.openBag(p, t);
			Loader.msg(Create.text("command.bag.other-other").replace("%player%", p.getName()).replace("%target%", t.getName()), s);
			return true;
		}
		if (args[0].equalsIgnoreCase("enchant") && s.hasPermission("amazingfishing.command.enchant")) {
			if (args.length == 1 || !s.hasPermission("amazingfishing.command.enchant.other")) {
				Loader.msg("/Fish Enchant <enchant>", s);
				return true;
			}
			if (Enchant.enchants.get(args[1].toLowerCase()) == null) {
				Loader.msg(Create.text("command.enchant.invalid").replace("%enchant%", args[1].toLowerCase()), s);
				return true;
			}
			int level = 1;
			if (args.length > 2)
				level = ParseUtils.getInt(args[2]);
			if (level <= 0)
				level = 1;
			((Player) s).setItemInHand(Enchant.enchants.get(args[1].toLowerCase()).enchant(((Player) s).getItemInHand(), level, false));
			NBTEdit edit = new NBTEdit(((Player) s).getItemInHand());
			Config data = new Config();
			if (edit.hasKey("af_data"))
				data.reload(edit.getString("af_data"));
			Loader.msg(Create.text("command.enchant.enchanted").replace("%enchant%", args[1].toLowerCase()).replace("%level%", "" + data.getInt("enchants." + args[1].toLowerCase())), s);
			return true;
		}
		if (args[0].equalsIgnoreCase("list") && s.hasPermission("amazingfishing.command.list")) {
			if (args.length == 1) {
				Loader.msg("/Fish List <cod/salmon/pufferfish/tropical_fish/junk/enchants/treasure>", s);
				return true;
			}
			Loader.msg(Create.text("words.list.header").replace("%type%", args[1].toLowerCase()), s);
			if (args[1].toUpperCase().equals("ENCHANT") || args[1].toUpperCase().equals("ENCHANTS")) {
				for (Enchant f : Enchant.enchants.values())
					Loader.msg(Create.text("words.list.format").replace("%name%", f.getName()).replace("%customname%", Create.text("words.enchants")).replace("%display%", f.getDisplayName()), s);
				return true;
			}
			if (args[1].toUpperCase().equals("TREASURE") || args[1].toUpperCase().equals("TREASURES")) {
				for (Treasure f : API.getRegisteredTreasures().values())
					Loader.msg(Create.text("words.list.format").replace("%name%", f.getName()).replace("%customname%", Create.text("words.treasure")).replace("%display%", f.getDisplayName()), s);
				return true;
			}
			String fish = args[1].toUpperCase();
			if (fish.equals("TROPICALFISH"))
				fish = "TROPICAL_FISH";
			try {
				if (FishType.valueOf(fish) == null) {
					Loader.msg(Create.text("words.list.uknown").replace("%type%", args[1]), s);
					return true;
				}
			} catch (Exception | NoSuchFieldError e) {
				Loader.msg(Create.text("words.list.uknown").replace("%type%", args[1]), s);
				return true;
			}
			for (Fish f : API.getRegisteredFish().values())
				if (f.getType().name().equals(fish))
					Loader.msg(Create.text("words.list.format").replace("%name%", f.getName()).replace("%customname%", Create.text("words." + fish.toLowerCase())).replace("%display%",
							f.getDisplayName()), s);
			return true;
		}
		if (args[0].equalsIgnoreCase("points") && s.hasPermission("amazingfishing.command.points")) {
			if (args.length == 1) {
				Loader.msg("/Fish Points Add <player> <points>", s);
				Loader.msg("/Fish Points Get <player> ", s);
				Loader.msg("/Fish Points Set <player> <points>", s);
				return true;
			}
			if (args[1].equalsIgnoreCase("add")) {
				if (args.length == 4) {
					double points = StringUtils.getDouble(args[3]);
					API.getPoints().add(args[2], points);
					Loader.msg(Create.text("command.points.add").replace("%player%", args[2]).replace("%add%", "" + points).replace("%points%",
							ff.format(StringUtils.getDouble(String.format("%.2f", API.getPoints().get(args[2])))).replace(",", ".").replaceAll("[^0-9.]+", ",")), s);
					return true;
				}
				Loader.msg("/Fish Points Add <player> <points>", s);
				return true;
			}
			if (args[1].equalsIgnoreCase("get") || args[1].equalsIgnoreCase("balance")) {
				if (args.length == 3) {
					Loader.msg(Create.text("command.points.balance").replace("%player%", args[2]).replace("%points%",
							ff.format(StringUtils.getDouble(String.format("%.2f", API.getPoints().get(args[2])))).replace(",", ".").replaceAll("[^0-9.]+", ",")), s);
					return true;
				}
				Loader.msg("/Fish Points Get <player> ", s);
				return true;
			}
			if (args[1].equalsIgnoreCase("set")) {
				if (args.length == 4) {
					double points = StringUtils.getDouble(args[3]);
					API.getPoints().set(args[2], points);
					Loader.msg(
							Create.text("command.points.set").replace("%player%", args[2])
									.replace("%add%", ff.format(StringUtils.getDouble(String.format("%.2f", points))).replace(",", ".").replaceAll("[^0-9.]+", ",")).replace("%points%", "" + points),
							s);
					return true;
				}
				Loader.msg("/Fish Points Set <player> <points>", s);
				return true;
			}

			Loader.msg("/Fish Points Add <player> <points>", s);
			Loader.msg("/Fish Points Get <player> ", s);
			Loader.msg("/Fish Points Set <player> <points>", s);
			return true;
		}
		if ((args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) && s.hasPermission("amazingfishing.command.reload"))
			Loader.reload(s, true);
		return true;
	}

	/*
	 * @Override public List<String> onTabComplete(CommandSender s, Command arg1,
	 * String arg2, String[] args) { List<String> c = new ArrayList<>(); if
	 * (s.hasPermission("amazingfishing.points.manager")) { if(args.length==1)
	 * c.addAll( StringUtil.copyPartialMatches(args[0], Arrays.asList("Points"), new
	 * ArrayList<>()) ); if(args.length==2) c.addAll(
	 * StringUtil.copyPartialMatches(args[1], Arrays.asList("Add", "Get", "Set"),
	 * new ArrayList<>()) ); if(args.length==3) return null; } return c; }
	 */
}

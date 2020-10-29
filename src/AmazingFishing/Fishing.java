package AmazingFishing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import AmazingFishing.Tournament.Type;
import me.DevTec.AmazingFishing.Configs;
import me.DevTec.AmazingFishing.Loader;
import me.DevTec.AmazingFishing.PAPIExpansion;
import me.DevTec.AmazingFishing.Placeholders;
import me.DevTec.TheAPI.TheAPI;
import me.DevTec.TheAPI.Utils.StringUtils;

public class Fishing implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {

		if(args.length==0) {
			if(s instanceof Player && Loader.c.getBoolean("Options.UseGUI")) {
				help.open((Player)s, help.Type.Player);
				return true;
			}
			if(s.hasPermission("amazingfishing.top"))
				if(s instanceof Player)
			Loader.Help(s, "/Fish Top", "Top");
			if(s.hasPermission("amazingfishing.record"))
				if(s instanceof Player)
			Loader.Help(s, "/Fish Record", "Record");
			if(s.hasPermission("amazingfishing.toggle"))
				if(s instanceof Player)
			Loader.Help(s, "/Fish Toggle", "Toggle");
			if(Loader.c.getBoolean("Options.Enchants"))
				if(s.hasPermission("amazingfishing.enchant"))
					if(s instanceof Player)
				Loader.Help(s, "/Fish Enchant", "Enchant");
			if(Loader.c.getBoolean("Options.Shop"))
				if(s instanceof Player)
				if(s.hasPermission("amazingfishing.shop"))
				Loader.Help(s, "/Fish Shop", "Shop");
			if(s.hasPermission("amazingfishing.stats.other"))
			Loader.Help(s, "/Fish Stats <player>", "Stats.Other");
			else {
				if(s.hasPermission("amazingfishing.stats"))
					Loader.Help(s, "/Fish Stats", "Stats.My");
			}
			if(s.hasPermission("amazingfishing.bag"))
				if(s instanceof Player)
			Loader.Help(s, "/Fish Bag", "Bag");
			if(s.hasPermission("amazingfishing.list"))
			Loader.Help(s, "/Fish List", "List");
			if(s.hasPermission("amazingfishing.quests"))
			Loader.Help(s, "/Fish Quests", "Quests");
			
			
			if(s.hasPermission("amazingfishing.editor")||s.hasPermission("amazingfishing.tournament")||s.hasPermission("amazingfishing.reload")||s.hasPermission("amazingfishing.points"))
			Loader.msgCmd("&8---- &cAdmin commands &8----", s);
			if(s.hasPermission("amazingfishing.editor"))
				if(s instanceof Player)
			Loader.Help(s, "/Fish Editor", "Editor");
			if(s.hasPermission("amazingfishing.tournament"))
			Loader.Help(s, "/Fish Tournament", "Tournament");
			if(s.hasPermission("amazingfishing.reload"))
			Loader.Help(s, "/Fish Reload", "Reload");
			if(s.hasPermission("amazingfishing.points"))
			Loader.Help(s, "/Fish Points", "Points");
			return true;
	}
		if(args[0].equalsIgnoreCase("Quests")) {
			if(Loader.hasPerm(s, "amazingfishing.quests")) {
				if(s instanceof Player) {
					if(API.getQuest((Player)s)==null)Quests.selectQuest((Player)s);
					else
						Quests.openQuestMenu((Player)s);
					return true;
				}
				Loader.msgCmd(Loader.s("ConsoleErrorMessage"), s);
				return true;
			}return true;
		}
		if(args[0].equalsIgnoreCase("Tournament")) {
			if(Loader.hasPerm(s, "amazingfishing.tournament")) {
				if(args.length==1) {
			Loader.msgCmd(Loader.s("Prefix")+Loader.s("TournamentTypes"), s);
			Loader.msgCmd("&e/Fish Tournament Start <type> <length>", s);
			Loader.msgCmd("&e/Fish Tournament Stop <give rewards>", s);
			Loader.msgCmd("&6Types:", s);
			Loader.msgCmd(" &a- Length", s);
			Loader.msgCmd(" &c- MostCatch", s);
			Loader.msgCmd(" &e- Weight", s);
			Loader.msgCmd(" &b- Random", s);
		return true;				
		}
				if(args[1].equalsIgnoreCase("start")) {
					if(args.length==2) {
					Loader.msgCmd("&e/Fish Tournament Start <type> <length>", s);
					Loader.msgCmd("&6Types:", s);
					Loader.msgCmd(" &a- Length", s);
					Loader.msgCmd(" &c- MostCatch", s);
					Loader.msgCmd(" &e- Weight", s);
					Loader.msgCmd(" &b- Random", s);
					return true;
					}
					if(args.length==3) {
						if(args[2].equalsIgnoreCase("length")) {
							if(Tournament.running()) {
								Loader.msgCmd("&cAlreary running tournament.", s);
								return true;
							}
							Tournament.startType(Type.Length, 0,false);
							return true;
						}else
							if(args[2].equalsIgnoreCase("MostCatch")) {
								if(Tournament.running()) {
									Loader.msgCmd("&cAlreary running tournament.", s);
									return true;
								}
								Tournament.startType(Type.MostCatch, 0,false);
								return true;
							}else
								if(args[2].equalsIgnoreCase("Weight")) {
									if(Tournament.running()) {
										Loader.msgCmd("&cAlreary running tournament.", s);
										return true;
									}
									Tournament.startType(Type.Weight, 0,false);
									return true;
								}else
						if(args[2].equalsIgnoreCase("Random")) {
							if(Tournament.running()) {
								Loader.msgCmd("&cAlreary running tournament.", s);
								return true;
							}
							Tournament.startType(Type.Random, 0,false);
							return true;
						}else {
							Loader.msgCmd("&e/Fish Tournament Start <type> <length>", s);
							Loader.msgCmd("&6Types:", s);
							Loader.msgCmd(" &a- Length", s);
							Loader.msgCmd(" &c- MostCatch", s);
							Loader.msgCmd(" &e- Weight", s);
							Loader.msgCmd(" &b- Random", s);
							return true;
						}
					}
					if(args.length==4) {
						if(args[2].equalsIgnoreCase("length")) {
							if(Tournament.running()) {
								Loader.msgCmd("&cAlreary running tournament.", s);
								return true;
							}
							Tournament.startType(Type.Length, (int)StringUtils.getTimeFromString(args[3]),false);
							return true;
						}else
							if(args[2].equalsIgnoreCase("MostCatch")) {
								if(Tournament.running()) {
									Loader.msgCmd("&cAlreary running tournament.", s);
									return true;
								}
								Tournament.startType(Type.MostCatch, (int)StringUtils.getTimeFromString(args[3]),false);
								return true;
							}else
								if(args[2].equalsIgnoreCase("Weight")) {
									if(Tournament.running()) {
										Loader.msgCmd("&cAlreary running tournament.", s);
										return true;
									}
									Tournament.startType(Type.Weight,(int)StringUtils.getTimeFromString(args[3]),false);
									return true;
								}else
						if(args[2].equalsIgnoreCase("Random")) {
							if(Tournament.running()) {
								Loader.msgCmd("&cAlreary running tournament.", s);
								return true;
							}
							Tournament.startType(Type.Random, (int)StringUtils.getTimeFromString(args[3]),false);
							return true;
						}else {
							Loader.msgCmd("&e/Fish Tournament Start <type> <length>", s);
							Loader.msgCmd("&6Types:", s);
							Loader.msgCmd(" &a- Length", s);
							Loader.msgCmd(" &c- MostCatch", s);
							Loader.msgCmd(" &e- Weight", s);
							Loader.msgCmd(" &b- Random", s);
							return true;
						}
					}
				}
				if(args[1].equalsIgnoreCase("stop")) {
					if(args.length==2) {
					if(!Tournament.running()) {
						Loader.msgCmd("&6Any tournament isn't running", s);
						return true;
					}
					Tournament.stop(false);
					return true;
				}
				if(args.length==3) {
					if(args[2].equalsIgnoreCase("yes")||args[2].equalsIgnoreCase("true")) {
						if(!Tournament.running()) {
							Loader.msgCmd("&6Any tournament isn't running", s);
							return true;
						}
						Tournament.stop(true);
						return true;
					}
					if(args[2].equalsIgnoreCase("no")||args[2].equalsIgnoreCase("false")) {
						if(!Tournament.running()) {
							Loader.msgCmd("&6Any tournament isn't running", s);
							return true;
						}
						Tournament.stop(false);
						return true;
					}
				}
				}
		}return true;}
		
		if(args[0].equalsIgnoreCase("Shop")) {
			if(Loader.hasPerm(s, "amazingfishing.Shop")) {
				if(Loader.c.getBoolean("Options.Shop"))
				new Shop(s);
				else
					Loader.msgCmd(Loader.s("Prefix")+Loader.s("CommandIsDisabled"), s);
				return true;
			}
			return true;
		}
		

		if(args[0].equalsIgnoreCase("Points")) {
			if(args.length==1) {
				
				if(s.hasPermission("amazingfishing.points.balance"))
				Loader.Help(s, "/Fish Points <player>", "Points-Balance");
				if(s.hasPermission("amazingfishing.points.give"))
				Loader.Help(s, "/Fish Points <player> Give <amount>", "Points-Give");
				if(s.hasPermission("amazingfishing.points.take"))
				Loader.Help(s, "/Fish Points <player> Take <amount>", "Points-Take");
				if(s.hasPermission("amazingfishing.points.set"))
				Loader.Help(s, "/Fish Points <player> Set <amount>", "Points-Set");
				return true;
			
			}
			if(args.length==2) {
				if(Loader.hasPerm(s, "amazingfishing.points.balance")) {
					Loader.msgCmd("&6Player &a"+args[1]+"&6 has &a"+Points.getBal(args[1])+" Points", s);
					return true;
				}
				return true;
			}
			if(args.length==3) {
					if(s.hasPermission("amazingfishing.points.give"))
					Loader.Help(s, "/Fish Points "+args[1]+" Give <amount>", "Points-Give");
					if(s.hasPermission("amazingfishing.points.take"))
					Loader.Help(s, "/Fish Points "+args[1]+" Take <amount>", "Points-Take");
				if(s.hasPermission("amazingfishing.points.set"))
					Loader.Help(s, "/Fish Points "+args[1]+" Set <amount>", "Points-Set");
					return true;
			}
			if(args.length==4) {
				String p = args[1];
				double b = StringUtils.getDouble(args[3]);
				if(args[2].equalsIgnoreCase("set")) {
				if(Loader.hasPerm(s, "amazingfishing.points.set")) {
					Points.take(p, Points.bal(p));
					Points.give(p, b);
					Loader.msgCmd("&6Points of player &a"+p+"&6 set to &a"+b+" Points", s);
					return true;
				}
				return true;
				}

				if(args[2].equalsIgnoreCase("give")) {
				if(Loader.hasPerm(s, "amazingfishing.points.give")) {
					Points.give(p, b);
					Loader.msgCmd("&6Given &a"+b+" Points &6to player "+p, s);
					return true;
				}
				return true;
				}

				if(args[2].equalsIgnoreCase("take")) {
				if(Loader.hasPerm(s, "amazingfishing.points.take")) {
					Points.take(p, b);
					Loader.msgCmd("&6Taken &a"+b+" Points &6from player "+p, s);
					return true;
				}
				return true;
				}
			}
		}
		

		if(args[0].equalsIgnoreCase("Enchant")) {
			if(Loader.hasPerm(s, "amazingfishing.Enchant")) {
				if(Loader.c.getBoolean("Options.Enchants")) {
					if(s instanceof Player) {
						TheAPI_GUIs g = new TheAPI_GUIs();
						g.openEnchantTable((Player)s);
						return true;
					}
					Loader.msgCmd(Loader.s("ConsoleErrorMessage"), s);
					return true;
				}else
					Loader.msgCmd(Loader.s("Prefix")+Loader.s("CommandIsDisabled"), s);
				return true;
			}
			return true;
		}
		if(args[0].equalsIgnoreCase("editor")) {
			if(Loader.hasPerm(s, "amazingfishing.editor")) {
				if(s instanceof Player) {
					TheAPI_GUIs g = new TheAPI_GUIs();
					g.open((Player)s);
					return true;
				}
				Loader.msgCmd(Loader.s("ConsoleErrorMessage"), s);
				return true;
			}
		}
		if(args[0].equalsIgnoreCase("Toggle")) {
			if(Loader.me.getBoolean("Players."+s.getName()+".Toggle")) {
				Loader.msgCmd(Loader.s("Prefix")+Loader.s("Toggled.false"), s);
				Loader.me.set("Players."+s.getName()+".Toggle", false);
				Loader.me.save();
			return true;
			}else {
				Loader.msgCmd(Loader.s("Prefix")+Loader.s("Toggled.true"), s);
				Loader.me.set("Players."+s.getName()+".Toggle", true);
				Loader.me.save();
			return true;
			}}
		if(args[0].equalsIgnoreCase("reload")) {
			if(Loader.hasPerm(s, "amazingfishing.reload")) {
				Loader.msgCmd(Loader.s("Prefix")+ ChatColor.YELLOW+"----------------- "+ChatColor.DARK_AQUA+"AmazingFishing Reload"+ChatColor.YELLOW+" -----------------",s);
			    Loader.msgCmd("",s);
				Configs.reload();
				
				if(Placeholders.isEnabledPlaceholderAPI()) {
					new PAPIExpansion().register();
				}
				Loader.msgCmd(Loader.s("Prefix")+Loader.s("ConfigReloaded"), s);
				return true;
			}
			return true;
		}
		if(args[0].equalsIgnoreCase("stats")||args[0].equalsIgnoreCase("info")) {
			if(Loader.hasPerm(s, "amazingfishing.stats")) {
				if(args.length==1) {
				if(s instanceof Player) {
					if(!Loader.me.exists("Players."+s.getName()+".Stats.Type")) {
						Loader.msgCmd(Loader.s("Prefix")+Loader.s("MissingData"),s);
						return true;
					}
				for(String f:Loader.TranslationsFile.getStringList("Stats")) {
					String type = Loader.me.getString("Players."+s.getName()+".Stats.Type");
					String fish = Loader.me.getString("Players."+s.getName()+".Stats.Fish");
					if(Loader.c.exists("Types."+type+"."+fish+".Name"))
						fish=Loader.c.getString("Types."+type+"."+fish+".Name");
					Loader.msgCmd(f
							.replace("%amount%", Loader.me.getInt("Players."+s.getName()+".Stats.Amount")+"")
							.replace("%record%", Loader.me.getDouble("Players."+s.getName()+".Stats.Length")+"")
							.replace("%tournament%", Loader.me.getInt("Players."+s.getName()+".Stats.Tournaments")+"")
							.replace("%tournaments%", Loader.me.getInt("Players."+s.getName()+".Stats.Tournaments")+"")
							.replace("%top1%", Loader.me.getInt("Players."+s.getName()+".Stats.Top.1.Tournaments")+"")
							.replace("%top2%", Loader.me.getInt("Players."+s.getName()+".Stats.Top.2.Tournaments")+"")
							.replace("%top3%", Loader.me.getInt("Players."+s.getName()+".Stats.Top.3.Tournaments")+"")
							.replace("%fish%", fish+"")
							.replace("%player%", s.getName())
							.replace("%playername%", getName(s.getName()))
							,s);
				}
				return true;
				}
				Loader.Help(s, "/Fish Stats <player>", "Stats.Other");
				return true;
				}
				if(args.length==2) {
					if(Loader.hasPerm(s, "amazingfishing.stats.Other")) {
					if(!Loader.me.exists("Players."+args[1]+".Stats.Type")) {
						Loader.msgCmd(Loader.PlayerNotEx(args[1]),s);
						return true;
					}
					for(String f:Loader.TranslationsFile.getStringList("Stats")) {
						String type = Loader.me.getString("Players."+args[1]+".Stats.Type");
						String fish = Loader.me.getString("Players."+args[1]+".Stats.Fish");
						if(Loader.c.exists("Types."+type+"."+fish+".Name"))
							fish=Loader.c.getString("Types."+type+"."+fish+".Name");
						Loader.msgCmd(f
								.replace("%fish%", Loader.me.getInt("Players."+args[1]+".Stats.Fish")+"")
								.replace("%record%", Loader.me.getDouble("Players."+args[1]+".Stats.Length")+"")
								.replace("%tournament%", Loader.me.getInt("Players."+args[1]+".Stats.Tournaments")+"")
								.replace("%tournaments%", Loader.me.getInt("Players."+args[1]+".Stats.Tournaments")+"")
								.replace("%top1%", Loader.me.getInt("Players."+args[1]+".Stats.Top.1.Tournaments")+"")
								.replace("%top2%", Loader.me.getInt("Players."+args[1]+".Stats.Top.2.Tournaments")+"")
								.replace("%top3%", Loader.me.getInt("Players."+args[1]+".Stats.Top.3.Tournaments")+"")
								.replace("%fish%", fish+"")
								.replace("%player%", args[1])
								.replace("%playername%", getName(args[1]))
								,s);
					}}return true;
				}
			}
			return true;
		}
		if(args[0].equalsIgnoreCase("top")||args[0].equalsIgnoreCase("global")) {
			if(Loader.hasPerm(s, "amazingfishing.top")) {
				if(s instanceof Player) {
				gui.openGlobal((Player)s);
				return true;
				}
				Loader.msgCmd(Loader.s("ConsoleErrorMessage"), s);
				return true;
			}
			return true;
		}

		if(args[0].equalsIgnoreCase("bag")) {
			if(Loader.hasPerm(s, "amazingfishing.bag")) {
				if(s instanceof Player) {
				bag.openBag((Player)s);
				return true;
				}
				Loader.msgCmd(Loader.s("ConsoleErrorMessage"), s);
				return true;
			}
			return true;
		}
		
		if(args[0].equalsIgnoreCase("record")||args[0].equalsIgnoreCase("records")) {
			if(Loader.hasPerm(s, "amazingfishing.record")) {
				if(s instanceof Player) {
				gui.openMy((Player)s);
				return true;
				}
				Loader.msgCmd(Loader.s("ConsoleErrorMessage"), s);
				return true;
			}
			return true;
		}
		if(args[0].equalsIgnoreCase("List")) {
			if(Loader.hasPerm(s, "amazingfishing.List")) {
				Loader.msgCmd(Loader.s("Prefix")+Loader.s("ListFish"),s);

				if(Loader.c.exists("Types.Cod")) {
				Loader.msgCmd(Trans.cod()+":",s);
				for(String cod:Loader.c.getKeys("Types.Cod")) {
					String name = cod;
					if(Loader.c.exists("Types.Cod."+cod+".Name"))
						name=Loader.c.getString("Types.Cod."+cod+".Name");
					Loader.msgCmd("&8 - "+name,s);
				}}
				
				if(Loader.c.exists("Types.Salmon")) { 
				Loader.msgCmd(Trans.sal()+":",s);
				for(String cod:Loader.c.getKeys("Types.Salmon")) {
					String name = cod;
					if(Loader.c.exists("Types.Salmon."+cod+".Name"))
						name=Loader.c.getString("Types.Salmon."+cod+".Name");
					Loader.msgCmd("&4 - "+name,s);
				}}
				if(Loader.c.exists("Types.PufferFish")) {
				Loader.msgCmd(Trans.puf()+":",s);
				for(String cod:Loader.c.getKeys("Types.PufferFish")) {
					String name = cod;
					if(Loader.c.exists("Types.PufferFish."+cod+".Name"))
						name=Loader.c.getString("Types.PufferFish."+cod+".Name");
					Loader.msgCmd("&e - "+name,s);
				}}
				if(Loader.c.exists("Types.TropicalFish")) {
				Loader.msgCmd(Trans.tro()+":",s);
				for(String cod:Loader.c.getKeys("Types.TropicalFish")) {
					String name = cod;
					if(Loader.c.exists("Types.TropicalFish."+cod+".Name"))
						name=Loader.c.getString("Types.TropicalFish."+cod+".Name");
					Loader.msgCmd("&c - "+name,s);
				}}
				return true;
			   }
			return true;
		}
		return true;
	}

	private CharSequence getName(String string) {
		if(TheAPI.getPlayer(string)!=null)string=TheAPI.getPlayer(string).getDisplayName();
		return string;
	}

	@Override
	public List<String> onTabComplete(CommandSender s, Command arg1, String arg2, String[] args) {
		List<String> c = new ArrayList<>();
		if(args.length==1) {
			if(s.hasPermission("amazingfishing.Enchant"))
			if(Loader.c.getBoolean("Options.Enchants"))
            	c.addAll(StringUtil.copyPartialMatches(args[0], Arrays.asList("Enchant"), new ArrayList<>()));
			if(s.hasPermission("amazingfishing.Shop"))
			if(Loader.c.getBoolean("Options.Shop"))
            	c.addAll(StringUtil.copyPartialMatches(args[0], Arrays.asList("Shop"), new ArrayList<>()));
			
			if(s.hasPermission("amazingfishing.editor"))
            	c.addAll(StringUtil.copyPartialMatches(args[0], Arrays.asList("Editor"), new ArrayList<>()));
				if(s.hasPermission("amazingfishing.top"))
	            	c.addAll(StringUtil.copyPartialMatches(args[0], Arrays.asList("Top"), new ArrayList<>()));
					if(s.hasPermission("amazingfishing.reload"))
		            	c.addAll(StringUtil.copyPartialMatches(args[0], Arrays.asList("Reload"), new ArrayList<>()));
					if(s.hasPermission("amazingfishing.list"))
		            	c.addAll(StringUtil.copyPartialMatches(args[0], Arrays.asList("List"), new ArrayList<>()));
					if(s.hasPermission("amazingfishing.record"))
		            	c.addAll(StringUtil.copyPartialMatches(args[0], Arrays.asList("Record"), new ArrayList<>()));
					if(s.hasPermission("amazingfishing.toggle"))
		            	c.addAll(StringUtil.copyPartialMatches(args[0], Arrays.asList("Toggle"), new ArrayList<>()));
					if(s.hasPermission("amazingfishing.tournament"))
		            	c.addAll(StringUtil.copyPartialMatches(args[0], Arrays.asList("Tournament"), new ArrayList<>()));
					if(s.hasPermission("amazingfishing.quests"))
		            	c.addAll(StringUtil.copyPartialMatches(args[0], Arrays.asList("Quests"), new ArrayList<>()));
					if(s.hasPermission("amazingfishing.stats"))
		            	c.addAll(StringUtil.copyPartialMatches(args[0], Arrays.asList("Stats"), new ArrayList<>()));
					if(s.hasPermission("amazingfishing.bag") && Loader.c.getBoolean("Options.Bag"))
		            	c.addAll(StringUtil.copyPartialMatches(args[0], Arrays.asList("Bag"), new ArrayList<>()));
					if(s.hasPermission("amazingfishing.points.give")
							||s.hasPermission("amazingfishing.points.balance")
							||s.hasPermission("amazingfishing.points.take")
							||s.hasPermission("amazingfishing.points.set"))
		            	c.addAll(StringUtil.copyPartialMatches(args[0], Arrays.asList("Points"), new ArrayList<>()));
		}
		if(args.length==2) {
			if(args[0].equalsIgnoreCase("points")) {
				if(s.hasPermission("amazingfishing.points.give")||s.hasPermission("amazingfishing.points.take")
						||s.hasPermission("amazingfishing.points.set"))
	            return null;
			}
			if(args[0].equalsIgnoreCase("stats"))
			if(s.hasPermission("amazingfishing.Stats.Other"))
				return null;
			if(args[0].equalsIgnoreCase("Tournament"))
			if(s.hasPermission("amazingfishing.tournament"))
            	c.addAll(StringUtil.copyPartialMatches(args[1], Arrays.asList("Start", "Stop"), new ArrayList<>()));
		}
		if(args.length==3) {
			if(args[0].equalsIgnoreCase("points")) {
				if(s.hasPermission("amazingfishing.points.give")) {
	            	c.addAll(StringUtil.copyPartialMatches(args[2], Arrays.asList("Give"), new ArrayList<>()));
				}
				if(s.hasPermission("amazingfishing.points.take")) {
	            	c.addAll(StringUtil.copyPartialMatches(args[2], Arrays.asList("Take"), new ArrayList<>()));
				}
				if(s.hasPermission("amazingfishing.points.set")) {
	            	c.addAll(StringUtil.copyPartialMatches(args[2], Arrays.asList("Set"), new ArrayList<>()));
				}
			}
			if(args[0].equalsIgnoreCase("Tournament")) {
			if(s.hasPermission("amazingfishing.tournament")) {
				if(args[1].equalsIgnoreCase("stop")) {
	            	c.addAll(StringUtil.copyPartialMatches(args[2], Arrays.asList("Yes", "No"), new ArrayList<>()));
				}
				if(args[1].equalsIgnoreCase("start")) {
	            	c.addAll(StringUtil.copyPartialMatches(args[2], Arrays.asList("Length", "Weight", "MostCatch","Random"), new ArrayList<>()));
				}
		}}}
		if(args.length==4) {

			if(args[0].equalsIgnoreCase("points")) {
				if(s.hasPermission("amazingfishing.points.give")||s.hasPermission("amazingfishing.points.take")
						||s.hasPermission("amazingfishing.points.set"))
	            	c.addAll(StringUtil.copyPartialMatches(args[3], Arrays.asList("?"), new ArrayList<>()));
			}
			if(args[0].equalsIgnoreCase("Tournament")) {
			if(s.hasPermission("amazingfishing.tournament")) {
				if(args[1].equalsIgnoreCase("start")) {
					if(args[2].equalsIgnoreCase("Length")||args[2].equalsIgnoreCase("Weight")||args[2].equalsIgnoreCase("MostCatch")||args[2].equalsIgnoreCase("Random"))
	            	c.addAll(StringUtil.copyPartialMatches(args[3], Arrays.asList("5min", "10min", "15min"), new ArrayList<>()));

				}}}}
		return c;
	}

}

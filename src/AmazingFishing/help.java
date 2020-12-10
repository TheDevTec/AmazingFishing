package AmazingFishing;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import AmazingFishing.Shop.ShopType;
import AmazingFishing.APIs.Enums.BackButton;
import AmazingFishing.APIs.Enums.PlayerType;
import AmazingFishing.APIs.Manager;
import me.DevTec.AmazingFishing.Configs;
import me.DevTec.AmazingFishing.Loader;
import me.DevTec.TheAPI.TheAPI;
import me.DevTec.TheAPI.APIs.ItemCreatorAPI;
import me.DevTec.TheAPI.GUIAPI.GUI;
import me.DevTec.TheAPI.GUIAPI.ItemGUI;
import me.DevTec.TheAPI.Utils.StringUtils;

public class help {
	public static void pointsManager(Player p,String name) {
		GUI a = new GUI("&9Points Manager",18,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};

		Create.prepareInvSmall(a);
		a.setItem(9, new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				help.open(p, PlayerType.Admin);
			}
		});
		a.setItem(0, new ItemGUI(Create.createItem("&9Amount of Points", Material.LAPIS_LAZULI,Arrays.asList("&3> &9"+Points.getBal(name)+"Points"))){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
			}
		});
		a.setItem(2, new ItemGUI(Create.createItem("&2Give points", Material.GREEN_DYE)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				help.givePoints(p, name);
			}
		});
		a.setItem(3,new ItemGUI( Create.createItem("&cTake points", Material.RED_DYE)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				help.takePoints(p, name);
			}
		});
		a.setItem(4, new ItemGUI(Create.createItem("&6Set amount of points", Material.ORANGE_DYE)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				help.setPoints(p, name);
			}
		});
	}
	public static void givePoints(Player p, String name) {
		GUI a = new GUI("&9Points Manager &7- &2Give",18,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInvSmall(a);

		a.setItem(9, new ItemGUI(Create.createItem(Trans.cancel(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				Loader.c.set("Points-"+p.getName(),null);
				Loader.save();
				pointsManager(p,name);
			}
		});

		a.setItem(0, new ItemGUI(Create.createItem("&9Amount of Points", Material.LAPIS_LAZULI,Arrays.asList("&3> &9"+Points.getBal(name)+"Points"))){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				givePoints(p,name);
			}
		});
		a.setItem(2, new ItemGUI(Create.createItem("&a+ 1", Material.GREEN_WOOL)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				Loader.c.set("Points-"+p.getName()+".Give",Loader.c.getDouble("Points-"+p.getName()+".Give")+1);
				Loader.save();
				givePoints(p, name);
			}
		});
		a.setItem(4, new ItemGUI(Create.createItem(Trans.points_give(), Material.CLOCK,
				Arrays.asList("&3> &9"+Loader.c.getDouble("Points-"+p.getName()+".Give")+"Points"))){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				Points.give(p.getName(), Loader.c.getDouble("Points-"+p.getName()+".Give"));
				Loader.c.set("Points-"+p.getName()+".Give",null);
				Loader.save();
				pointsManager(p, name);
			}
		});

		a.setItem(6, new ItemGUI(Create.createItem("&c- 1", Material.RED_WOOL)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				if(Loader.c.getDouble("Points-"+p.getName()+".Give")-1 > 0)
				Loader.c.set("Points-"+p.getName()+".Give",Loader.c.getDouble("Points-"+p.getName()+".Give")-1);
				Loader.save();
				givePoints(p, name);
			}
		});
	}

	public static void takePoints(Player p, String name) {
		GUI a = new GUI("&9Points Manager &7- &cTake",18,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInvSmall(a);
		a.setItem(9, new ItemGUI(Create.createItem(Trans.cancel(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				Loader.c.set("Points-"+p.getName(),null);
				Loader.save();
				pointsManager(p,name);
			}
		});
		
		a.setItem(0, new ItemGUI(Create.createItem("&9Amount of Points", Material.LAPIS_LAZULI,Arrays.asList("&3> &9"+Points.getBal(name)+"Points"))){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
			}
		});
		a.setItem(2, new ItemGUI(Create.createItem("&a+ 1", Material.GREEN_WOOL)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				Loader.c.set("Points-"+p.getName()+".Take",Loader.c.getDouble("Points-"+p.getName()+".Take")+1);
				Loader.save();
				takePoints(p, name);
			}
		});

		a.setItem(4, new ItemGUI(Create.createItem(Trans.points_give(), Material.CLOCK,
				Arrays.asList("&3> &9"+Loader.c.getDouble("Points-"+p.getName()+".Take")+"Points"))){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				Points.take(p.getName(), Loader.c.getDouble("Points-"+p.getName()+".Take"));
				Loader.c.set("Points-"+p.getName()+".Take",null);
				Loader.save();
				pointsManager(p, name);
			}
		});
		a.setItem(6, new ItemGUI(Create.createItem("&c- 1", Material.RED_WOOL)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				if(Loader.c.getDouble("Points-"+p.getName()+".Take")-1 > 0)
				Loader.c.set("Points-"+p.getName()+".Give",Loader.c.getDouble("Points-"+p.getName()+".Take")-1);
				Loader.save();
				takePoints(p, name);
			}
		});
	}

	public static void setPoints(Player p, String name) {
		GUI a = new GUI("&9Points Manager &7- &6Set",18,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInvSmall(a);
		a.setItem(9, new ItemGUI(Create.createItem(Trans.cancel(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				Loader.c.set("Points-"+p.getName(),null);
				Loader.save();
				pointsManager(p,name);
			}
		});
		a.setItem(0,  new ItemGUI(Create.createItem("&9Amount of Points", Material.LAPIS_LAZULI,Arrays.asList("&3> &9"+Points.getBal(name)+"Points"))){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
			}
		});
		a.setItem(2, new ItemGUI(Create.createItem("&a+ 1", Material.GREEN_WOOL)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				Loader.c.set("Points-"+p.getName()+".Set",Loader.c.getDouble("Points-"+p.getName()+".Set")+1);
				Loader.save();
				setPoints(p, name);
			}
		});
		a.setItem(4, new ItemGUI(Create.createItem(Trans.points_give(), Material.CLOCK,
				Arrays.asList("&3> &9"+Loader.c.getDouble("Points-"+p.getName()+".Set")+"Points"))){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				Points.set(p.getName(), Loader.c.getDouble("Points-"+p.getName()+".Set"));
				Loader.c.set("Points-"+p.getName()+".Set",null);
				Loader.save();
				pointsManager(p, name);
			}
		});
		a.setItem(6, new ItemGUI(Create.createItem("&c- 1", Material.RED_WOOL)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				if(Loader.c.getDouble("Points-"+p.getName()+".Set")-1 > 0)
				Loader.c.set("Points-"+p.getName()+".Set",Loader.c.getDouble("Points-"+p.getName()+".Set")-1);
				Loader.save();
				setPoints(p, name);
			}
		});
	}

	public static void selectPlayerPointsManager(Player p) {
		GUI a = new GUI("&9Points Manager",54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInvBig(a);
		for(Player s:TheAPI.getOnlinePlayers()) {
				ItemCreatorAPI ss = new ItemCreatorAPI(Material.LEGACY_SKULL_ITEM);
				ss.setOwner(s.getName());
				ss.setSkullType(SkullType.PLAYER);
				ss.setDisplayName(s.getDisplayName());
				a.addItem(new ItemGUI(ss.create()){
					@Override
					public void onClick(Player p, GUI arg, ClickType ctype) {
						pointsManager(p,s.getName());
					}
				});

		}

		a.setItem(53, new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				open(p,PlayerType.Admin);
			}
		});
	}
	
	public static void open(Player p, PlayerType type) {
		String typ = "Player";
		if(type==PlayerType.Admin)typ="Admin";

		GUI a = new GUI("&5Help &7- &4"+typ,54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		if(type==PlayerType.Player)
			Create.prepareNewBig(a, Material.BLUE_STAINED_GLASS_PANE);
		else
			Create.prepareNewBig(a, Material.ORANGE_STAINED_GLASS_PANE);
		
		if(type==PlayerType.Player) {
			
			a.setItem(4, new ItemGUI(Create.createItem(Manager.getPluginName(), Material.KNOWLEDGE_BOOK, Manager.getPluginInfoIntoLore(type))) {
				@Override
				public void onClick(Player p, GUI arg, ClickType ctype) {
				}
			});
			
			if(p.hasPermission("amazingfishing.top"))
		a.setItem(20, new ItemGUI(Create.createItem(Trans.help_top(), Material.DIAMOND)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.top"))
				gui.openGlobal(p);
			}
		});
		if(p.hasPermission("amazingfishing.record"))
		a.setItem(29,new ItemGUI(Create.createItem(Trans.help_rec(), Material.GOLD_INGOT)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.record"))
				gui.openMy(p);
			}}
		);

		if(p.hasPermission("amazingfishing.shop") && Loader.c.getBoolean("Options.Shop"))
		a.setItem(22,new ItemGUI(Create.createItem(Trans.help_shop(), Material.EMERALD)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.shop") && Loader.c.getBoolean("Options.Shop"))
				Shop.openShop(p, ShopType.Buy);
			}}
		);
		if(p.hasPermission("amazingfishing.stats")) {

			ArrayList<String> l = new ArrayList<String>();
			for(String d:Loader.c.getStringList("GUI.Stats.1.Lore")) {
				l.add(Color.c(rep(d,p.getDisplayName())));
			}
			ItemStack houska = ItemCreatorAPI.createHead(1, Trans.help_stats_my(), p.getName(), 
					l);		
			a.setItem(18,new ItemGUI(houska){
				@Override
				public void onClick(Player p, GUI arg, ClickType ctype) {
					if(p.hasPermission("amazingfishing.stats"))
						stats(p,p.getName(),false);
				}}
			);
			/*
			 a.setItem(18,new ItemGUI(Create.createItem(Trans.help_stats_my(), Material.WRITTEN_BOOK)){
				@Override
				public void onClick(Player p, GUI arg, ClickType ctype) {
					if(p.hasPermission("amazingfishing.stats"))
						stats(p,p.getName(),false);
				}}
			);
			 */
		}
		if(p.hasPermission("amazingfishing.toggle"))
		a.setItem(26,new ItemGUI(Create.createItem(Trans.help_tog(), Material.REDSTONE_TORCH)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.toggle"))
					toggle(p);
			}}
		);
		
		if(p.hasPermission("amazingfishing.list"))
		a.setItem(33,new ItemGUI(Create.createItem(Trans.help_list(), Material.PAPER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.list")) {
				Loader.msgCmd(Loader.s("Prefix")+Loader.s("ListFish"),p);
				if(Loader.c.exists("Types.Cod")) {
					Loader.msgCmd(Trans.cod()+":",p);
				for(String cod:Loader.c.getKeys("Types.Cod")) {
					String name = cod;
					if(Loader.c.exists("Types.Cod."+cod+".Name"))
						name=Loader.c.getString("Types.Cod."+cod+".Name");
					Loader.msgCmd("&8 - "+name,p);
				}}
				if(Loader.c.exists("Types.Salmon")) {
					Loader.msgCmd(Trans.sal()+":",p);
				for(String cod:Loader.c.getKeys("Types.Salmon")) {
					String name = cod;
					if(Loader.c.exists("Types.Salmon."+cod+".Name"))
						name=Loader.c.getString("Types.Salmon."+cod+".Name");
					Loader.msgCmd("&4 - "+name,p);
				}}
				if(Loader.c.exists("Types.PufferFish")) {
					Loader.msgCmd(Trans.puf()+":",p);
				for(String cod:Loader.c.getKeys("Types.PufferFish")) {
					String name = cod;
					if(Loader.c.exists("Types.PufferFish."+cod+".Name"))
						name=Loader.c.getString("Types.PufferFish."+cod+".Name");
					Loader.msgCmd("&e - "+name,p);
				}}
				if(Loader.c.exists("Types.TropicalFish")) {
					Loader.msgCmd(Trans.tro()+":",p);
				for(String cod:Loader.c.getKeys("Types.TropicalFish")) {
					String name = cod;
					if(Loader.c.exists("Types.TropicalFish."+cod+".Name"))
						name=Loader.c.getString("Types.TropicalFish."+cod+".Name");
					Loader.msgCmd("&c - "+name,p);
				}}}
			}
		}
		);

		if(p.hasPermission("amazingfishing.enchant") && Loader.c.getBoolean("Options.Enchants"))
		a.setItem(31,new ItemGUI(Create.createItem(Trans.help_ench(), Material.matchMaterial("ENCHANTING_TABLE"))){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				TheAPI_GUIs g = new TheAPI_GUIs();
				if(p.hasPermission("amazingfishing.enchant") && Loader.c.getBoolean("Options.Enchants"))
					g.openEnchantTable(p);
			}}
		);
		if(p.hasPermission("amazingfishing.bag"))
		a.setItem(27,new ItemGUI(Create.createItem(Trans.help_bag(), Material.CHEST)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.bag"))
					bag.openBag(p, BackButton.FishPlayer);
			}
		}
		);

		if(p.hasPermission("amazingfishing.quests"))
		a.setItem(24,new ItemGUI(Create.createItem("&6Quests", Material.BOOK)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				if(p.hasPermission("amazingfishing.quests")) {
					if(API.getQuest(p)==null) {
						Quests.selectQuest(p);
					}
					}else {
						Quests.openQuestMenu(p);
					}
				}
			}
		);
		if(p.hasPermission("amazingfishing.reload")||
				p.hasPermission("amazingfishing.points.give")||
				p.hasPermission("amazingfishing.points.take")||
				p.hasPermission("amazingfishing.points.set")||
				p.hasPermission("amazingfishing.editor")||
			p.hasPermission("amazingfishing.stats.other")||
			p.hasPermission("amazingfishing.tournament")) {

		a.setItem(35, new ItemGUI(Create.createItem(Trans.help_admin(), Material.COMPASS)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				open(p, PlayerType.Admin);
			}}
		);
		}
		}else {
			/*
			 * Admin Section
			 *   20,29 | 22, 31 | 24, 33
			 */
			a.setItem(4, new ItemGUI(Create.createItem(Manager.getPluginName(), Material.KNOWLEDGE_BOOK, Manager.getPluginInfoIntoLore(type))) {
				@Override
				public void onClick(Player p, GUI arg, ClickType ctype) {
					Loader.msgCmd(Loader.s("Prefix")+" &fJoin our Discord: &b"+Manager.getDiscordLink(), p);
				}
			});
			
			if(p.hasPermission("amazingfishing.tournament"))
			a.setItem(20, new ItemGUI(Create.createItem(Trans.help_tour(), Material.CLOCK)){
				@Override
				public void onClick(Player p, GUI arg, ClickType ctype) {
					if(p.hasPermission("amazingfishing.tournament"))
					tour(p);
				}}
			);

			if(p.hasPermission("amazingfishing.editor"))
			a.setItem(22,new ItemGUI(Create.createItem(Trans.help_edit(), Material.WRITABLE_BOOK)){
				@Override
				public void onClick(Player p, GUI arg, ClickType ctype) {
					TheAPI_GUIs g = new TheAPI_GUIs();
					if(p.hasPermission("amazingfishing.editor"))
					g.open(p);
				}}
			);

			if(p.hasPermission("amazingfishing.stats.other"))
			a.setItem(29,new ItemGUI(Create.createItem(Trans.help_stats_other(), Material.WRITTEN_BOOK)){
				@Override
				public void onClick(Player p, GUI arg, ClickType ctype) {
					if(p.hasPermission("amazingfishing.stats.other"))
					selectPlayer(p);
				}}
			);
			if(p.hasPermission("amazingfishing.reload"))
			a.setItem(31,new ItemGUI(Create.createItem(Trans.help_reload(), Material.FIREWORK_STAR)){
				@Override
				public void onClick(Player p, GUI arg, ClickType ctype) {
					if(p.hasPermission("amazingfishing.reload")) {
					Configs.reload();
					Loader.msgCmd(Loader.s("Prefix")+Loader.s("ConfigReloaded"), p);}
				}}
			);
			if(p.hasPermission("amazingfishing.points"))
			a.setItem(24,new ItemGUI(Create.createItem(Trans.help_points(), Material.LAPIS_LAZULI)){
				@Override
				public void onClick(Player p, GUI arg, ClickType ctype) {
					if(p.hasPermission("amazingfishing.points"))
					selectPlayerPointsManager(p);
				}}
			);
			a.setItem(35, new ItemGUI(Create.createItem(Trans.help_player(), Material.COMPASS)){
				@Override
				public void onClick(Player p, GUI arg, ClickType ctype) {
					open(p,PlayerType.Player);
				}}
			);
		}
	}

	public static void toggle(Player p) {
		GUI a = new GUI("&3Toggle record reached message",54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		a.setItem(49, new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				open(p,PlayerType.Player);
			}
		});
		a.setItem(20,new ItemGUI(Create.createItem(Loader.s("HelpGUI.Record.Want"), Material.GREEN_WOOL)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				Loader.me.set("Players."+p.getName()+".Toggle", false);
				Loader.save();
				help.toggle(p);
			}
		});
		String status = Loader.s("HelpGUI.Record.Receive");
		if(Loader.me.getBoolean("Players."+p.getName()+".Toggle"))status=Loader.s("HelpGUI.Record.NoLongerReceive");
		a.setItem(22,new ItemGUI(Create.createItem("&6Current status", Material.CLOCK,Arrays.asList("&3> "+status))){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
			}
		});
		a.setItem(24,new ItemGUI(Create.createItem(Loader.s("HelpGUI.Record.DoNotWant"), Material.RED_WOOL)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				Loader.me.set("Players."+p.getName()+".Toggle", true);
				Loader.save();
				help.toggle(p);
			}
		});
	}

	public static void tour(Player p) {
		GUI a = new GUI("&2Tournaments",54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);

		a.setItem(49, new ItemGUI(Create.createItem(Trans.cancel(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				open(p,PlayerType.Admin);
			}
		});
		a.setItem(40,new ItemGUI(Create.createItem(Loader.c.getString(Trans.tour_stop()), Material.RED_WOOL,Arrays.asList(
				"&7 - Left click to stop tournament with prizes","&7 - Right click to stop tournament without prizes"))){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				if(ctype.isLeftClick())
					Tournament.stop(true);
				if(ctype.isRightClick())
					Tournament.stop(false);
			}
		});

		a.setItem(20,new ItemGUI(Create.createItem(Loader.c.getString("Tournaments.Length.Name"), Material.PAPER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				Loader.c.set("Creating-Tournament."+p.getName()+".Type","Length");
				Loader.c.set("Creating-Tournament."+p.getName()+".Time",300);
				tourCreate(p);
			}
		});
		a.setItem(24,new ItemGUI(Create.createItem(Loader.c.getString("Tournaments.MostCatch.Name"), Material.WHEAT_SEEDS)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				Loader.c.set("Creating-Tournament."+p.getName()+".Type","MostCatch");
				Loader.c.set("Creating-Tournament."+p.getName()+".Time",300);
				tourCreate(p);
			}
		});
		a.setItem(31,new ItemGUI(Create.createItem(Loader.c.getString("Tournaments.Weight.Name"), Material.ANVIL)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				Loader.c.set("Creating-Tournament."+p.getName()+".Type","Weight");
				Loader.c.set("Creating-Tournament."+p.getName()+".Time",300);
				tourCreate(p);
			}
		});
		a.setItem(13,new ItemGUI(Create.createItem("&5Random", Material.EXPERIENCE_BOTTLE)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				Loader.c.set("Creating-Tournament."+p.getName()+".Type","Random");
				Loader.c.set("Creating-Tournament."+p.getName()+".Time",300);
				tourCreate(p);
			}
		});
	}

	public static enum TournamentType{
		Length,
		Weight,
		MostCatch,
		Random
	}
	public static void tourCreate(Player p) {
		GUI a = new GUI("&2Tournaments &7- &a"+Loader.c.getString("Creating-Tournament."+p.getName()+".Type"),54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		a.setItem(49,  new ItemGUI(Create.createItem(Trans.cancel(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				Loader.c.set("Creating-Tournament."+p.getName(),null);
			}
		});
		a.setItem(20,new ItemGUI(Create.createItem("&a+ 10s", Material.GREEN_WOOL)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				Loader.c.set("Creating-Tournament."+p.getName()+".Time",Loader.c.getInt("Creating-Tournament."+p.getName()+".Time")+10);
				Loader.save();
				tourCreate(p);
			}
		});

		a.setItem(29,new ItemGUI(Create.createItem("&a+ 30s", Material.GREEN_WOOL)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				Loader.c.set("Creating-Tournament."+p.getName()+".Time",Loader.c.getInt("Creating-Tournament."+p.getName()+".Time")+30);
				Loader.save();
				tourCreate(p);
			}
		});
		
		String time=StringUtils.timeToString(Loader.c.getInt("Creating-Tournament."+p.getName()+".Time"));
		a.setItem(22, new ItemGUI(Create.createItem(Loader.s("HelpGUI.ClickToStart"), Material.CLOCK,
				Arrays.asList("&3> &5Type: "+Loader.c.getString("Creating-Tournament."+p.getName()+".Type"),"&3> &5Time: "+time))){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				Tournament.startType(Tournament.Type.valueOf(Loader.c.getString("Creating-Tournament."+p.getName()+".Type"))
						, Loader.c.getInt("Creating-Tournament."+p.getName()+".Time"),false);
				Loader.c.set("Creating-Tournament."+p.getName()+".Time",Loader.c.getInt("Creating-Tournament."+p.getName()+".Time")+30);
				Loader.save();
				tour(p);
			}
		});
		a.setItem(24,new ItemGUI(Create.createItem("&c- 10s", Material.RED_WOOL)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				Loader.c.set("Creating-Tournament."+p.getName()+".Time",Loader.c.getInt("Creating-Tournament."+p.getName()+".Time")-10);
				Loader.save();
				tourCreate(p);
			}
		});
		a.setItem(33,new ItemGUI(Create.createItem("&c- 30s", Material.RED_WOOL)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				Loader.c.set("Creating-Tournament."+p.getName()+".Time",Loader.c.getInt("Creating-Tournament."+p.getName()+".Time")-30);
				Loader.save();
				tourCreate(p);
			}
		});
	}

	private static String rep(String s, String w) {
		String type = Loader.me.getString("Players."+w+".Stats.Type");
		String fish = Loader.me.getString("Players."+w+".Stats.Fish");
		if(Loader.c.exists("Types."+type+"."+fish+".Name"))
			fish=Loader.c.getString("Types."+type+"."+fish+".Name");
		if(type!=null) {
		if(type.equals("PufferFish"))type="Pufferfish";
		if(type.equals("TropicalFish"))type="Tropical_Fish";}
		if(fish == null) fish = "---";
		if(type == null) type = "---";
		return s.replace("%fish_catched%", Loader.me.getInt("Players."+w+".Stats.Amount")+"")
				.replace("%fish_record%", Loader.me.getDouble("Players."+w+".Stats.Length")+"")
				.replace("%fish_name%", fish+"")
				.replace("%fish_type%", type+"")
				.replace("%player%", w)
				.replace("%playername%",getName(w))
				.replace("%tournament_played%", Loader.me.getInt("Players."+w+".Stats.Tournaments")+"")
				.replace("%tournament_top1%", Loader.me.getInt("Players."+w+".Stats.Top.1.Tournaments")+"")
				.replace("%tournament_top2%", Loader.me.getInt("Players."+w+".Stats.Top.2.Tournaments")+"")
				.replace("%tournament_top3%", Loader.me.getInt("Players."+w+".Stats.Top.3.Tournaments")+"");
	}
	static String getName(String s) {
		if(TheAPI.getPlayer(s)!=null)return TheAPI.getPlayer(s).getDisplayName();
		return s;
	}
	public static void stats(Player p,String name, boolean admin) {
		GUI a = new GUI("&6Stats",18,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInvSmall(a);
		a.setItem(9, new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				if(admin)open(p,PlayerType.Admin);
				else
				open(p,PlayerType.Player);
			}
		});
		for(String s:Loader.c.getKeys("GUI.Stats")) {
			Material m = Material.STONE;
			if(Material.matchMaterial(Loader.c.getString("GUI.Stats."+s+".Material"))!=null)
				m=Material.matchMaterial(Loader.c.getString("GUI.Stats."+s+".Material"));
			String c = " ";
			if(Loader.c.exists("GUI.Stats."+s+".Name"))
			 c = Loader.c.getString("GUI.Stats."+s+".Name");
			ArrayList<String> l = new ArrayList<String>();
			if(Loader.c.exists("GUI.Stats."+s+".Lore"))
			for(String d:Loader.c.getStringList("GUI.Stats."+s+".Lore")) {
				l.add(Color.c(rep(d,name)));
			}
			if(l.isEmpty()==false) {
				if(m==Material.PLAYER_HEAD||m==Material.LEGACY_SKULL||m==Material.LEGACY_SKULL_ITEM) {
					ItemCreatorAPI ss = new ItemCreatorAPI(Material.LEGACY_SKULL_ITEM);
					ss.setOwner(name);
					ss.setSkullType(SkullType.PLAYER);
					ss.setDisplayName(rep(c,name));
					ss.setLore(l);
					a.addItem(new ItemGUI(ss.create()){
						@Override
						public void onClick(Player p, GUI arg, ClickType ctype) {
						}
					});			
				}else
			a.addItem(new ItemGUI(Create.createItem(Color.c(rep(c,name)), m,l)){
				@Override
				public void onClick(Player p, GUI arg, ClickType ctype) {
				}
			});
			}else {
				if(m==Material.PLAYER_HEAD||m==Material.LEGACY_SKULL||m==Material.LEGACY_SKULL_ITEM) {
					ItemCreatorAPI ss = new ItemCreatorAPI(Material.LEGACY_SKULL_ITEM);
					ss.setOwner(name);
					ss.setSkullType(SkullType.PLAYER);
					ss.setDisplayName(rep(c,name));
					a.addItem(new ItemGUI(ss.create()){
						@Override
						public void onClick(Player p, GUI arg, ClickType ctype) {
						}
					});		
				}else
				a.addItem(new ItemGUI(Create.createItem(Color.c(rep(c,name)), m)){
					@Override
					public void onClick(Player p, GUI arg, ClickType ctype) {
					}
				});
			}
		}
	}

	public static void selectPlayer(Player p) {
		GUI a = new GUI("&6Stats",54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInvBig(a);
		for(Player s:TheAPI.getOnlinePlayers()) {
			if(Loader.me.exists("Players."+p.getName()+".Stats")) {
				ItemCreatorAPI ss = new ItemCreatorAPI(Material.LEGACY_SKULL_ITEM);
				ss.setOwner(s.getName());
				ss.setSkullType(SkullType.PLAYER);
				ss.setDisplayName(s.getDisplayName());
				a.addItem( new ItemGUI(ss.create()){
					@Override
					public void onClick(Player p, GUI arg, ClickType ctype) {
						stats(p,s.getName(),true);
					}
				});		
		}}
		a.setItem(53, new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				open(p, PlayerType.Admin);
			}
		});
	}
	
}
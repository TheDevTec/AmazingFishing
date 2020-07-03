package AmazingFishing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;

import AmazingFishing.Shop.ShopType;
import me.DevTec.ItemCreatorAPI;
import me.DevTec.TheAPI;
import me.DevTec.GUI.GUICreatorAPI;
import me.DevTec.GUI.GUICreatorAPI.Options;

@SuppressWarnings("deprecation")
public class help {
	public static enum Type{
		Player,
		Admin
	}
	
	public static void pointsManager(Player p,String name) {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI("&9Points Manager",18,p);
		Create.prepareInvSmall(a);
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				help.open(p, Type.Admin);
			}});
		a.setItem(9, Create.createItem(Trans.back(), Material.BARRIER),w);
		w.remove(Options.RUNNABLE);
		a.setItem(0, Create.createItem("&9Amount of Points", Material.LAPIS_LAZULI,Arrays.asList("&3> &9"+Points.getBal(name)+"Points")),w);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				help.givePoints(p, name);
			}});
		a.setItem(2, Create.createItem("&2Give points", Material.GREEN_DYE),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				help.takePoints(p, name);
			}});
		a.setItem(3, Create.createItem("&cTake points", Material.RED_DYE),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				help.setPoints(p, name);
			}});
		a.setItem(4, Create.createItem("&6Set amount of points", Material.ORANGE_DYE),w);
	}
	
	public static void givePoints(Player p, String name) {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI("&9Points Manager &7- &2Give",18,p);
		Create.prepareInvSmall(a);
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Loader.c.set("Points-"+p.getName(),null);
				Loader.save();
				pointsManager(p,name);
			}});
		a.setItem(9, Create.createItem(Trans.cancel(), Material.BARRIER),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				givePoints(p,name);
			}});
		a.setItem(0, Create.createItem("&9Amount of Points", Material.LAPIS_LAZULI,Arrays.asList("&3> &9"+Points.getBal(name)+"Points")),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Loader.c.set("Points-"+p.getName()+".Give",Loader.c.getDouble("Points-"+p.getName()+".Give")+1);
				Loader.save();
				givePoints(p, name);
			}});
		a.setItem(2, Create.createItem("&a+ 1", Material.GREEN_WOOL),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Points.give(p.getName(), Loader.c.getDouble("Points-"+p.getName()+".Give"));
				Loader.c.set("Points-"+p.getName()+".Give",null);
				Loader.save();
				pointsManager(p, name);
			}});
		a.setItem(4, Create.createItem(Trans.points_give(), Material.CLOCK,
				Arrays.asList("&3> &9"+Loader.c.getDouble("Points-"+p.getName()+".Give")+"Points")),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				if(Loader.c.getDouble("Points-"+p.getName()+".Give")-1 > 0)
				Loader.c.set("Points-"+p.getName()+".Give",Loader.c.getDouble("Points-"+p.getName()+".Give")-1);
				Loader.save();
				givePoints(p, name);
			}});
		a.setItem(6, Create.createItem("&c- 1", Material.RED_WOOL),w);
	}

	public static void takePoints(Player p, String name) {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI("&9Points Manager &7- &cTake",18,p);
		Create.prepareInvSmall(a);
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Loader.c.set("Points-"+p.getName(),null);
				Loader.save();
				pointsManager(p,name);
			}});
		a.setItem(9, Create.createItem(Trans.cancel(), Material.BARRIER),w);
		w.remove(Options.RUNNABLE);
		a.setItem(0, Create.createItem("&9Amount of Points", Material.LAPIS_LAZULI,Arrays.asList("&3> &9"+Points.getBal(name)+"Points")),w);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Loader.c.set("Points-"+p.getName()+".Take",Loader.c.getDouble("Points-"+p.getName()+".Take")+1);
				Loader.save();
				takePoints(p, name);
			}});
		a.setItem(2, Create.createItem("&a+ 1", Material.GREEN_WOOL),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Points.take(p.getName(), Loader.c.getDouble("Points-"+p.getName()+".Take"));
				Loader.c.set("Points-"+p.getName()+".Take",null);
				Loader.save();
				pointsManager(p, name);
			}});
		a.setItem(4, Create.createItem(Trans.points_give(), Material.CLOCK,
				Arrays.asList("&3> &9"+Loader.c.getDouble("Points-"+p.getName()+".Take")+"Points")),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				if(Loader.c.getDouble("Points-"+p.getName()+".Take")-1 > 0)
				Loader.c.set("Points-"+p.getName()+".Give",Loader.c.getDouble("Points-"+p.getName()+".Take")-1);
				Loader.save();
				takePoints(p, name);
			}});
		a.setItem(6, Create.createItem("&c- 1", Material.RED_WOOL),w);
	}

	public static void setPoints(Player p, String name) {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI("&9Points Manager &7- &6Set",18,p);
		Create.prepareInvSmall(a);
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Loader.c.set("Points-"+p.getName(),null);
				Loader.save();
				pointsManager(p,name);
			}});
		a.setItem(9, Create.createItem(Trans.cancel(), Material.BARRIER),w);
		w.remove(Options.RUNNABLE);
		a.setItem(0, Create.createItem("&9Amount of Points", Material.LAPIS_LAZULI,Arrays.asList("&3> &9"+Points.getBal(name)+"Points")),w);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Loader.c.set("Points-"+p.getName()+".Set",Loader.c.getDouble("Points-"+p.getName()+".Set")+1);
				Loader.save();
				setPoints(p, name);
			}});
		a.setItem(2, Create.createItem("&a+ 1", Material.GREEN_WOOL),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Points.set(p.getName(), Loader.c.getDouble("Points-"+p.getName()+".Set"));
				Loader.c.set("Points-"+p.getName()+".Set",null);
				Loader.save();
				pointsManager(p, name);
			}});
		a.setItem(4, Create.createItem(Trans.points_give(), Material.CLOCK,
				Arrays.asList("&3> &9"+Loader.c.getDouble("Points-"+p.getName()+".Set")+"Points")),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				if(Loader.c.getDouble("Points-"+p.getName()+".Set")-1 > 0)
				Loader.c.set("Points-"+p.getName()+".Set",Loader.c.getDouble("Points-"+p.getName()+".Set")-1);
				Loader.save();
				setPoints(p, name);
			}});
		a.setItem(6, Create.createItem("&c- 1", Material.RED_WOOL),w);
	}

	public static void selectPlayerPointsManager(Player p) {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI("&9Points Manager",54,p);
		Create.prepareInvBig(a);
		for(Player s:Bukkit.getOnlinePlayers()) {
				ItemCreatorAPI ss = TheAPI.getItemCreatorAPI(Material.LEGACY_SKULL_ITEM);
				ss.setOwner(s.getName());
				ss.setSkullType(SkullType.PLAYER);
				ss.setDisplayName(s.getDisplayName());
				HashMap<Options, Object> w = new HashMap<Options, Object>();
				w.put(Options.CANT_PUT_ITEM, true);
				w.put(Options.CANT_BE_TAKEN, true);
				w.put(Options.RUNNABLE, new Runnable() {
					@Override
					public void run() {
						pointsManager(p,s.getName());
					}});
				a.addItem(ss.create(),w);	

		}
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				open(p,Type.Admin);
			}});
		a.setItem(53, Create.createItem(Trans.back(), Material.BARRIER),w);
	}
	
	public static void open(Player p, Type type) {
		String typ = "Player";
		if(type==Type.Admin)typ="Admin";

		GUICreatorAPI a = TheAPI.getGUICreatorAPI("&5Help &7- &4"+typ,18,p);
		Create.prepareInvSmall(a);
		if(type==Type.Player) {
			HashMap<Options, Object> w = new HashMap<Options, Object>();
			w.put(Options.CANT_PUT_ITEM, true);
			w.put(Options.CANT_BE_TAKEN, true);
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					if(p.hasPermission("amazingfishing.top"))
					gui.openGlobal(p);
				}});
			if(p.hasPermission("amazingfishing.top"))
		a.addItem(Create.createItem(Trans.help_top(), Material.DIAMOND),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				if(p.hasPermission("amazingfishing.record"))
				gui.openMy(p);
			}});
		if(p.hasPermission("amazingfishing.record"))
		a.addItem(Create.createItem(Trans.help_rec(), Material.GOLD_INGOT),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				if(p.hasPermission("amazingfishing.shop") && Loader.c.getBoolean("Options.Shop"))
				Shop.openShop(p, ShopType.Buy);
			}});
		if(p.hasPermission("amazingfishing.shop") && Loader.c.getBoolean("Options.Shop"))
		a.addItem(Create.createItem(Trans.help_shop(), Material.EMERALD),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				if(p.hasPermission("amazingfishing.stats"))
			stats(p,p.getName(),false);
			}});
		if(p.hasPermission("amazingfishing.stats"))
		a.addItem(Create.createItem(Trans.help_stats_my(), Material.WRITTEN_BOOK),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				if(p.hasPermission("amazingfishing.toggle"))
				toggle(p);
			}});
		if(p.hasPermission("amazingfishing.toggle"))
		a.addItem(Create.createItem(Trans.help_tog(), Material.REDSTONE_TORCH),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				if(p.hasPermission("amazingfishing.list")) {
				Loader.msgCmd(Loader.s("Prefix")+Loader.s("ListFish"),p);
				if(Loader.c.getString("Types.Cod")!=null) {
					Loader.msgCmd(Trans.cod()+":",p);
				for(String cod:Loader.c.getConfigurationSection("Types.Cod").getKeys(false)) {
					String name = cod;
					if(Loader.c.getString("Types.Cod."+cod+".Name")!=null)
						name=Loader.c.getString("Types.Cod."+cod+".Name");
					Loader.msgCmd("&8 - "+name,p);
				}}
				if(Loader.c.getString("Types.Salmon")!=null) {
					Loader.msgCmd(Trans.sal()+":",p);
				for(String cod:Loader.c.getConfigurationSection("Types.Salmon").getKeys(false)) {
					String name = cod;
					if(Loader.c.getString("Types.Salmon."+cod+".Name")!=null)
						name=Loader.c.getString("Types.Salmon."+cod+".Name");
					Loader.msgCmd("&4 - "+name,p);
				}}
				if(Loader.c.getString("Types.PufferFish")!=null) {
					Loader.msgCmd(Trans.puf()+":",p);
				for(String cod:Loader.c.getConfigurationSection("Types.PufferFish").getKeys(false)) {
					String name = cod;
					if(Loader.c.getString("Types.PufferFish."+cod+".Name")!=null)
						name=Loader.c.getString("Types.PufferFish."+cod+".Name");
					Loader.msgCmd("&e - "+name,p);
				}}
				if(Loader.c.getString("Types.TropicalFish")!=null) {
					Loader.msgCmd(Trans.tro()+":",p);
				for(String cod:Loader.c.getConfigurationSection("Types.TropicalFish").getKeys(false)) {
					String name = cod;
					if(Loader.c.getString("Types.TropicalFish."+cod+".Name")!=null)
						name=Loader.c.getString("Types.TropicalFish."+cod+".Name");
					Loader.msgCmd("&c - "+name,p);
				}}}
			}});
		if(p.hasPermission("amazingfishing.list"))
		a.addItem(Create.createItem(Trans.help_list(), Material.PAPER),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				TheAPI_GUIs g = new TheAPI_GUIs();
				if(p.hasPermission("amazingfishing.enchant") && Loader.c.getBoolean("Options.Enchants"))
				g.openEnchantTable(p);
			}});
		if(p.hasPermission("amazingfishing.enchant") && Loader.c.getBoolean("Options.Enchants"))
		a.addItem(Create.createItem(Trans.help_ench(), Material.matchMaterial("ENCHANTING_TABLE")),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				if(p.hasPermission("amazingfishing.bag"))
				bag.openBag(p);
			}});
		if(p.hasPermission("amazingfishing.bag"))
		a.addItem(Create.createItem(Trans.help_bag(), Material.CHEST),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				if(p.hasPermission("amazingfishing.quests")) {
				if(API.getQuest(p)==null)Quests.selectQuest(p);
				else
					Quests.openQuestMenu(p);}
			}});
		if(p.hasPermission("amazingfishing.quests"))
		a.addItem(Create.createItem("&6Quests", Material.BOOK),w);
		if(p.hasPermission("amazingfishing.reload")||
				p.hasPermission("amazingfishing.points.give")||
				p.hasPermission("amazingfishing.points.take")||
				p.hasPermission("amazingfishing.points.set")||
				p.hasPermission("amazingfishing.editor")||
			p.hasPermission("amazingfishing.stats.other")||
			p.hasPermission("amazingfishing.tournament")) {
			w.remove(Options.RUNNABLE);
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					open(p, Type.Admin);
				}});
		a.setItem(9, Create.createItem(Trans.help_admin(), Material.CHEST),w);
		}
		}else {
			HashMap<Options, Object> w = new HashMap<Options, Object>();
			w.put(Options.CANT_PUT_ITEM, true);
			w.put(Options.CANT_BE_TAKEN, true);
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					if(p.hasPermission("amazingfishing.tournament"))
					tour(p);
				}});
			if(p.hasPermission("amazingfishing.tournament"))
			a.addItem(Create.createItem(Trans.help_tour(), Material.CLOCK),w);
			w.remove(Options.RUNNABLE);
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					TheAPI_GUIs g = new TheAPI_GUIs();
					if(p.hasPermission("amazingfishing.editor"))
					g.open(p);
				}});
			if(p.hasPermission("amazingfishing.editor"))
			a.addItem(Create.createItem(Trans.help_edit(), Material.WRITABLE_BOOK),w);
			w.remove(Options.RUNNABLE);
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					if(p.hasPermission("amazingfishing.stats.other"))
					selectPlayer(p);
				}});
			if(p.hasPermission("amazingfishing.stats.other"))
			a.addItem(Create.createItem(Trans.help_stats_other(), Material.WRITTEN_BOOK),w);
			w.remove(Options.RUNNABLE);
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					if(p.hasPermission("amazingfishing.reload")) {
					Loader.reloadAll();
					Loader.msgCmd(Loader.s("Prefix")+Loader.s("ConfigReloaded"), p);}
				}});
			if(p.hasPermission("amazingfishing.reload"))
			a.addItem(Create.createItem(Trans.help_reload(), Material.FIREWORK_STAR),w);
			w.remove(Options.RUNNABLE);
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					if(p.hasPermission("amazingfishing.points"))
					selectPlayerPointsManager(p);
				}});
			if(p.hasPermission("amazingfishing.points"))
			a.addItem(Create.createItem(Trans.help_points(), Material.LAPIS_LAZULI),w);
			w.remove(Options.RUNNABLE);
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					open(p,Type.Player);
				}});
			a.setItem(9, Create.createItem(Trans.help_player(), Material.CHEST),w);
		}
	}
	
	public static void toggle(Player p) {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI("&3Toggle record reached message",54,p);
		Create.prepareInv(a);
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				open(p,Type.Player);
			}});
		a.setItem(49, Create.createItem(Trans.back(), Material.BARRIER),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Loader.me.set("Players."+p.getName()+".Toggle", false);
				Loader.save();
				help.toggle(p);
			}});
		a.setItem(20,Create.createItem(Loader.s("HelpGUI.Record.Want"), Material.GREEN_WOOL),w);
		String status = Loader.s("HelpGUI.Record.Receive");
		if(Loader.me.getBoolean("Players."+p.getName()+".Toggle"))status=Loader.s("HelpGUI.Record.NoLongerReceive");
		w.remove(Options.RUNNABLE);
		a.setItem(22,Create.createItem("&6Current status", Material.CLOCK,Arrays.asList("&3> "+status)),w);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Loader.me.set("Players."+p.getName()+".Toggle", true);
				Loader.save();
				help.toggle(p);
			}});
		a.setItem(24,Create.createItem(Loader.s("HelpGUI.Record.DoNotWant"), Material.RED_WOOL),w);
	}
	
	public static void tour(Player p) {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI("&2Tournaments",54,p);
		Create.prepareInv(a);
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				open(p,Type.Admin);
			}});
		a.setItem(49, Create.createItem(Trans.cancel(), Material.BARRIER),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE_LEFT_CLICK, new Runnable() {
			@Override
			public void run() {
				Tournament.stop(true);
			}});
		w.put(Options.RUNNABLE_RIGHT_CLICK, new Runnable() {
			@Override
			public void run() {
				Tournament.stop(false);
			}});
		a.setItem(40,Create.createItem(Loader.c.getString(Trans.tour_stop()), Material.RED_WOOL,Arrays.asList(
				"&7 - Left click to stop tournament with prizes","&7 - Right click to stop tournament without prizes")),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Loader.c.set("Creating-Tournament."+p.getName()+".Type","Length");
				Loader.c.set("Creating-Tournament."+p.getName()+".Time",300);
				tourCreate(p);
			}});
		a.setItem(20,Create.createItem(Loader.c.getString("Tournaments.Length.Name"), Material.PAPER),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Loader.c.set("Creating-Tournament."+p.getName()+".Type","MostCatch");
				Loader.c.set("Creating-Tournament."+p.getName()+".Time",300);
				tourCreate(p);
			}});
		a.setItem(24,Create.createItem(Loader.c.getString("Tournaments.MostCatch.Name"), Material.WHEAT_SEEDS),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Loader.c.set("Creating-Tournament."+p.getName()+".Type","Weight");
				Loader.c.set("Creating-Tournament."+p.getName()+".Time",300);
				tourCreate(p);
			}});
		a.setItem(31,Create.createItem(Loader.c.getString("Tournaments.Weight.Name"), Material.ANVIL),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Loader.c.set("Creating-Tournament."+p.getName()+".Type","Random");
				Loader.c.set("Creating-Tournament."+p.getName()+".Time",300);
				tourCreate(p);
			}});
		a.setItem(13,Create.createItem("&5Random", Material.EXPERIENCE_BOTTLE),w);
	}
	
	public static enum TournamentType{
		Length,
		Weight,
		MostCatch,
		Random
	}
	public static void tourCreate(Player p) {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI("&2Tournaments &7- &a"+Loader.c.getString("Creating-Tournament."+p.getName()+".Type"),54,p);
		Create.prepareInv(a);
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Loader.c.set("Creating-Tournament."+p.getName(),null);
				tour(p);
			}});
		a.setItem(49, Create.createItem(Trans.cancel(), Material.BARRIER),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Loader.c.set("Creating-Tournament."+p.getName()+".Time",Loader.c.getInt("Creating-Tournament."+p.getName()+".Time")+10);
				Loader.save();
				tourCreate(p);
			}});
		a.setItem(20,Create.createItem("&a+ 10s", Material.GREEN_WOOL),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Loader.c.set("Creating-Tournament."+p.getName()+".Time",Loader.c.getInt("Creating-Tournament."+p.getName()+".Time")+30);
				Loader.save();
				tourCreate(p);
			}});
		a.setItem(29,Create.createItem("&a+ 30s", Material.GREEN_WOOL),w);
		
		String time=TheAPI.getTimeConventorAPI().setTimeToString(Loader.c.getInt("Creating-Tournament."+p.getName()+".Time"));
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Tournament.startType(Tournament.Type.valueOf(Loader.c.getString("Creating-Tournament."+p.getName()+".Type"))
						, Loader.c.getInt("Creating-Tournament."+p.getName()+".Time"),false);
				Loader.c.set("Creating-Tournament."+p.getName()+".Time",Loader.c.getInt("Creating-Tournament."+p.getName()+".Time")+30);
				Loader.save();
				tour(p);
			}});
		a.setItem(22,Create.createItem(Loader.s("HelpGUI.ClickToStart"), Material.CLOCK,
				Arrays.asList("&3> &5Type: "+Loader.c.getString("Creating-Tournament."+p.getName()+".Type"),"&3> &5Time: "+time)),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Loader.c.set("Creating-Tournament."+p.getName()+".Time",Loader.c.getInt("Creating-Tournament."+p.getName()+".Time")-10);
				Loader.save();
				tourCreate(p);
			}});
		a.setItem(24,Create.createItem("&c- 10s", Material.RED_WOOL),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Loader.c.set("Creating-Tournament."+p.getName()+".Time",Loader.c.getInt("Creating-Tournament."+p.getName()+".Time")-30);
				Loader.save();
				tourCreate(p);
			}});
		a.setItem(33,Create.createItem("&c- 30s", Material.RED_WOOL),w);
	}
	
	private static String rep(String s, String w) {
		String type = Loader.me.getString("Players."+w+".Stats.Type");
		String fish = Loader.me.getString("Players."+w+".Stats.Fish");
		if(Loader.c.getString("Types."+type+"."+fish+".Name")!=null)
			fish=Loader.c.getString("Types."+type+"."+fish+".Name");
		if(type!=null) {
		if(type.equals("PufferFish"))type="Pufferfish";
		if(type.equals("TropicalFish"))type="Tropical_Fish";}
		if(fish == null) fish = "---";
		if(type == null) type = "---";
		return s.replace("%fish_catched%", Loader.me.getInt("Players."+w+".Stats.Fish")+"")
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
		GUICreatorAPI a = TheAPI.getGUICreatorAPI("&6Stats",18,p);
		Create.prepareInvSmall(a);
		HashMap<Options, Object> wb = new HashMap<Options, Object>();
		wb.put(Options.CANT_PUT_ITEM, true);
		wb.put(Options.CANT_BE_TAKEN, true);
		wb.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				if(admin)open(p,Type.Admin);
				else
				open(p,Type.Player);
			}});
		a.setItem(9, Create.createItem(Trans.back(), Material.BARRIER),wb);
		for(String s:Loader.c.getConfigurationSection("GUI.Stats").getKeys(false)) {
			Material m = Material.STONE;
			if(Material.matchMaterial(Loader.c.getString("GUI.Stats."+s+".Material"))!=null)
				m=Material.matchMaterial(Loader.c.getString("GUI.Stats."+s+".Material"));
			String c = " ";
			if(Loader.c.getString("GUI.Stats."+s+".Name")!=null)
			 c = Loader.c.getString("GUI.Stats."+s+".Name");
			ArrayList<String> l = new ArrayList<String>();
			if(Loader.c.getString("GUI.Stats."+s+".Lore")!=null)
			for(String d:Loader.c.getStringList("GUI.Stats."+s+".Lore")) {
				l.add(Color.c(rep(d,name)));
			}
			HashMap<Options, Object> w = new HashMap<Options, Object>();
			w.put(Options.CANT_PUT_ITEM, true);
			w.put(Options.CANT_BE_TAKEN, true);
			if(l.isEmpty()==false) {
				if(m==Material.PLAYER_HEAD||m==Material.LEGACY_SKULL||m==Material.LEGACY_SKULL_ITEM) {
					ItemCreatorAPI ss = TheAPI.getItemCreatorAPI(Material.LEGACY_SKULL_ITEM);
					ss.setOwner(name);
					ss.setSkullType(SkullType.PLAYER);
					ss.setDisplayName(rep(c,name));
					ss.setLore(l);
					a.addItem(ss.create(),w);		
				}else
			a.addItem(Create.createItem(Color.c(rep(c,name)), m,l),w);
			}else {
				if(m==Material.PLAYER_HEAD||m==Material.LEGACY_SKULL||m==Material.LEGACY_SKULL_ITEM) {
					ItemCreatorAPI ss = TheAPI.getItemCreatorAPI(Material.LEGACY_SKULL_ITEM);
					ss.setOwner(name);
					ss.setSkullType(SkullType.PLAYER);
					ss.setDisplayName(rep(c,name));
					a.addItem(ss.create(),w);		
				}else
				a.addItem(Create.createItem(Color.c(rep(c,name)), m),w);
			}
		}
	}
	
	public static void selectPlayer(Player p) {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI("&6Stats",54,p);
		Create.prepareInvBig(a);
		for(Player s:Bukkit.getOnlinePlayers()) {
			if(Loader.me.getString("Players."+p.getName()+".Stats")!=null) {
				ItemCreatorAPI ss = TheAPI.getItemCreatorAPI(Material.LEGACY_SKULL_ITEM);
				ss.setOwner(s.getName());
				ss.setSkullType(SkullType.PLAYER);
				ss.setDisplayName(s.getDisplayName());
				HashMap<Options, Object> w = new HashMap<Options, Object>();
				w.put(Options.CANT_PUT_ITEM, true);
				w.put(Options.CANT_BE_TAKEN, true);
				w.put(Options.RUNNABLE, new Runnable() {
					@Override
					public void run() {
						stats(p,s.getName(),true);
					}});
				a.addItem(ss.create(),w);		
		}}
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				open(p, Type.Admin);
			}});
		a.setItem(53, Create.createItem(Trans.back(), Material.BARRIER),w);
	}
	
}
package me.devtec.amazingfishing.gui;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.gui.Help.PlayerType;
import me.devtec.amazingfishing.utils.Create;
import me.devtec.amazingfishing.utils.Trans;
import me.devtec.amazingfishing.utils.tournament.TournamentManager;
import me.devtec.amazingfishing.utils.tournament.TournamentType;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.GUI.ClickType;
import me.devtec.theapi.guiapi.HolderGUI;
import me.devtec.theapi.guiapi.ItemGUI;

public class Tournament {

	/*
	 * MainGUI -> Stop Button -> Stom GUI -> StopALL, Stop určitý world
	 */
	public static void open(Player p) {
		GUI a = new GUI(Trans.tournaments_title(),54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);

		a.setItem(49, new ItemGUI(Create.createItem(Trans.words_cancel(), Material.BARRIER)){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				Help.open(p, PlayerType.Admin);
			}
		});
		a.setItem(40,new ItemGUI(Create.createItem(Trans.tournaments_stop_item(), Material.RED_WOOL,Arrays.asList(
				"&7Opens new GUI "))){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				stopTournamentGUI(p);
			}
		});
		
		//11,20, 13,22, 15,24
		// Length, Weight 
		// Amount, Random
		// Total_Length, Total_Weight
		time=300;
		a.setItem(11,new ItemGUI(Create.createItem(Trans.words_tournament(TournamentType.LENGTH), Material.PAPER)){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				startTournament(p, TournamentType.LENGTH);
			}
		});
		
		a.setItem(20,new ItemGUI(Create.createItem(Trans.words_tournament(TournamentType.WEIGHT), Material.PAPER)){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				startTournament(p, TournamentType.WEIGHT);
			}
		});
		
		a.setItem(13,new ItemGUI(Create.createItem(Trans.words_tournament(TournamentType.AMOUNT), Material.GOLD_INGOT)){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				startTournament(p, TournamentType.AMOUNT);
			}
		});
		a.setItem(22,new ItemGUI(Create.createItem(Trans.words_tournament(TournamentType.RANDOM), Material.PURPLE_WOOL)){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				startTournament(p, TournamentType.RANDOM);
			}
		});
		
		a.setItem(15,new ItemGUI(Create.createItem(Trans.words_tournament(TournamentType.TOTAL_LENGTH), Material.PURPLE_WOOL)){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				startTournament(p, TournamentType.TOTAL_LENGTH);
			}
		});
		a.setItem(24,new ItemGUI(Create.createItem(Trans.words_tournament(TournamentType.TOTAL_WEIGHT), Material.PURPLE_WOOL)){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				startTournament(p, TournamentType.TOTAL_WEIGHT);
			}
		});
		
	
	}
	static long time;
	public static void startTournament(Player p, TournamentType type) {
		GUI a = new GUI(Trans.tournaments_start_title().replace("%type%", type.formatted()),54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		a.setItem(49,  new ItemGUI(Create.createItem(Trans.words_cancel(), Material.BARRIER)){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				arg.close();
			}
		});
		a.setItem(20,new ItemGUI(Create.createItem("&a+ 10s", Material.GREEN_WOOL)){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				time=time+10;
				//Loader.c.set("Creating-Tournament."+p.getName()+".Time",Loader.c.getInt("Creating-Tournament."+p.getName()+".Time")+10);
				//Loader.save();
				startTournament(p, type);
			}
		});

		a.setItem(29,new ItemGUI(Create.createItem("&a+ 30s", Material.GREEN_WOOL)){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				//Loader.c.set("Creating-Tournament."+p.getName()+".Time",Loader.c.getInt("Creating-Tournament."+p.getName()+".Time")+30);
				//Loader.save();
				time=time+30;
				startTournament(p, type);
			}
		});
		
		//String time=StringUtils.timeToString(Loader.c.getInt("Creating-Tournament."+p.getName()+".Time"));
		a.setItem(22, new ItemGUI(Create.createItem(Trans.tournaments_start_start(), Material.CLOCK,
				Trans.tournaments_start_description(type, time) )){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				TournamentManager.start(p.getWorld(), type, time);
				/*Tournament.startType(Tournament.Type.valueOf(Loader.c.getString("Creating-Tournament."+p.getName()+".Type"))
						, Loader.c.getInt("Creating-Tournament."+p.getName()+".Time"),false);
				Loader.c.set("Creating-Tournament."+p.getName()+".Time",Loader.c.getInt("Creating-Tournament."+p.getName()+".Time")+30);
				Loader.save();*/
				arg.close();
			}
		});
		a.setItem(24,new ItemGUI(Create.createItem("&c- 10s", Material.RED_WOOL)){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				//Loader.c.set("Creating-Tournament."+p.getName()+".Time",Loader.c.getInt("Creating-Tournament."+p.getName()+".Time")-10);
				//Loader.save();
				time=time-10;
				startTournament(p, type);
			}
		});
		a.setItem(33,new ItemGUI(Create.createItem("&c- 30s", Material.RED_WOOL)){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType ctype) {
				/*Loader.c.set("Creating-Tournament."+p.getName()+".Time",Loader.c.getInt("Creating-Tournament."+p.getName()+".Time")-30);
				Loader.save();*/
				time=time-30;
				startTournament(p, type);
			}
		});
	}

	private static void stopTournamentGUI(Player p) {
		GUI a = new GUI(Trans.tournaments_stop_title(),54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInvCount(a, 26);
		
		a.setItem(11, new ItemGUI(Create.createItem(Trans.tournaments_stop_one_name(), Material.RED_WOOL, 
				Trans.tournaments_stop_one_description())) {
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType type) {
				World world = p.getWorld();
				if(TournamentManager.isRunning(world)) {
					if(type.name().contains("SHIFT_LEFT"))
						TournamentManager.get(world).stop(true);
					if(type.name().contains("SHIFT_RIGHT"))
						TournamentManager.get(world).stop(false);
					Loader.msg(Trans.s("Tournaments.Stop.One"), p);
					arg.close();
				}else {
					Loader.msg(Trans.s("Tournaments.Stop.NoRunning"), p);
					arg.close();
				}
			}
		});
		
		a.setItem(15, new ItemGUI(Create.createItem(Trans.tournaments_stop_all_name(), Material.RED_CONCRETE, 
				Trans.tournaments_stop_all_description())) {
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType type) {
				if(!TournamentManager.getAll().isEmpty()) {
					boolean rewards=type.name().contains("SHIFT_LEFT");
					for(me.devtec.amazingfishing.utils.tournament.Tournament t: TournamentManager.getAll().values()) {
						t.stop(rewards);
					}
					Loader.msg(Trans.s("Tournaments.Stop.All"), p);
					arg.close();
				}else {
					Loader.msg(Trans.s("Tournaments.Stop.NoRunning"), p);
					arg.close();
				}
			}
		});
	}
}

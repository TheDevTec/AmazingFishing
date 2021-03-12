package me.devtec.amazingfishing.utils;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.construct.FishType;
import me.devtec.amazingfishing.utils.tournament.TournamentType;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.utils.datakeeper.User;

public class Statistics {
	/*
	 AmazingFishing:
	 	Statistics:
	 		Tournament:
	 			<type>:
	 				Placement:
	 					1: počet kolikrát se hráč umístil na prvním místě v tomto tournamentu
	 					2:
	 					3:
	 					4:
	 				Played: počet odehraných tournamentů určitého typu
	 			Placement: globální počet umístění v top 4
	 			Played: počet celkových odehrání tournamentů (i bez umístění)
	 		Fish:
	 			Caught: počet celkových chycení ryb
	 			<TYPE: cod|salmon|...>:
	 				Caught: počet chycení jednotlivého druhu ryby
	 */
	
	public static int getCaught(Player p, FishType type) {
		User u = TheAPI.getUser(p);
		if(type==null) return u.getInt(Manager.getDataLocation()+".Statistics.Fish.Caught");
		else return u.getInt(Manager.getDataLocation()+".Statistics.Fish."+type.name()+".Caught");
	}

	public static void addFish(Player p, FishType type) {
		User u = TheAPI.getUser(p);
		if(type!=null) {
			u.set(Manager.getDataLocation()+".Statistics.Fish."+type.name()+".Caught", getCaught(p, type)+1);
		}
		u.set(Manager.getDataLocation()+".Statistics.Fish.Caught", getCaught(p, null)+1);
		u.save();
	}
	
	public static void addTournamentData(Player p, TournamentType type ,int position) {
		if(position>4) position=0;
		addTournamentPlacement(p, type, position);
		addTournamentPlayed(p, type);
	}
	public static int getTournamentPlacement(Player p, TournamentType type, int position) {
		User u = TheAPI.getUser(p);
		if(type==null || position==0) {
			if(!u.exist(Manager.getDataLocation()+".Statistics.Tournament.Placements")) return 0;
			return u.getInt(Manager.getDataLocation()+".Statistics.Tournament.Placements");
		}
		else {
			if(!u.exist(Manager.getDataLocation()+".Statistics.Tournament."+type.name()+".Placement."+position)) return 0;
			return u.getInt(Manager.getDataLocation()+".Statistics.Tournament."+type.name()+".Placement."+position);
		}
	}

	private static void addTournamentPlacement(Player p, TournamentType type, int position) {
		User u = TheAPI.getUser(p);
		if(type!=null && position!=0) {
			u.set(Manager.getDataLocation()+".Statistics.Tournament."+type.toString()+".Placement."+position, getTournamentPlacement(p, type, position)+1);
		}
		u.set(Manager.getDataLocation()+".Statistics.Tournament.Placements", getTournamentPlacement(p, null, 0)+1);
		u.save();
	}
	
	public static int getTournamentPlayed(Player p, TournamentType type) {
		User u = TheAPI.getUser(p);
		if(type==null) {
			if( !u.exist(Manager.getDataLocation()+".Statistics.Tournament.Played")) return 0;
			return u.getInt(Manager.getDataLocation()+".Statistics.Tournament.Played");
		}
		else {
			if( !u.exist(Manager.getDataLocation()+".Statistics.Tournament."+type.name()+".Played")) return 0;
			return u.getInt(Manager.getDataLocation()+".Statistics.Tournament."+type.name()+".Played");
		}
	}

	private static void addTournamentPlayed(Player p, TournamentType type) {
		User u = TheAPI.getUser(p);
		if(type!=null) {
			u.set(Manager.getDataLocation()+".Statistics.Tournament."+type.name()+".Played", getTournamentPlayed(p, type)+1);
		}
		u.set(Manager.getDataLocation()+".Statistics.Tournament.Played", getTournamentPlayed(p, null)+1);
		u.save();
	}
}

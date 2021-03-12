package me.devtec.amazingfishing.utils;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.construct.FishType;
import me.devtec.amazingfishing.utils.tournament.TournamentType;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.utils.datakeeper.User;

public class Statistics {
	
	public static int getCaught(Player p, FishType type) {
		User u = TheAPI.getUser(p);
		if(type==null) return u.getInt(Manager.getDataLocation()+".Statistics.Fish.Caught");
		else return u.getInt(Manager.getDataLocation()+".Statistics.Fish."+type.toString().toLowerCase()+".Caught");
	}

	public static void addFish(Player p, FishType type) {
		User u = TheAPI.getUser(p);
		if(type!=null) {
			u.set(Manager.getDataLocation()+".Statistics.Fish."+type.toString().toLowerCase()+".Caught", getCaught(p, type)+1);
		}
		u.set(Manager.getDataLocation()+".Statistics.Fish.Caught", getCaught(p, null)+1);
		u.save();
	}
	
	public static int getTournamentWins(Player p, TournamentType type) {
		User u = TheAPI.getUser(p);
		if(type==null) return u.getInt(Manager.getDataLocation()+".Statistics.Tournament.Win");
		else return u.getInt(Manager.getDataLocation()+".Statistics.Tournament."+type.toString().toLowerCase()+".Win");
	}

	public static void addTournamentWin(Player p, TournamentType type) {
		User u = TheAPI.getUser(p);
		if(type!=null) {
			u.set(Manager.getDataLocation()+".Statistics.Tournament."+type.toString().toLowerCase()+".Win", getTournamentWins(p, type)+1);
		}
		u.set(Manager.getDataLocation()+".Statistics.Tournament.Win", getTournamentWins(p, null));
		u.save();
	}
}

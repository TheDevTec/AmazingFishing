package me.devtec.amazingfishingOLD.utils.tournament;

import java.util.concurrent.ConcurrentHashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.World;

public class TournamentManager {
	private static Map<World, Tournament> t = new ConcurrentHashMap<>();
	private static Tournament global;
	
	public static boolean isRunning(World world) {
		return t.containsKey(world);
	}
	
	public static boolean start(World world, TournamentType type, long time) {
		if(world==null) {
			if(global!=null)return false;
			global=new Tournament(type, time, null);
			return true;
		}
		return isRunning(world) ? false : t.put(world, new Tournament(type, time, world))==null;
	}

	public static Tournament get(World w) {
		if(w==null)
			return global;
		if(t.containsKey(w))
			return t.get(w);
		return global;
	}
	public static Map<World, Tournament> getAll() {
		return t;
	}
	
	public static Tournament getGlobal() {
		return global;
	}
	
	public static void setGlobal(Tournament t) {
		global=t;
	}

	public static void remove(World w) {
		t.remove(w);
	}

	public static void remove(Tournament w) {
		if(w.equals(global)) {
			global=null;
			return;
		}
		for(Entry<World,Tournament> d : new HashSet<>(t.entrySet()))
			if(d.getValue().equals(w)) {
				t.remove(d.getKey());
			break;
		}
	}
}

package me.devtec.amazingfishing.utils.tournament;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.World;

public class TournamentManager {
	private static Map<World, Tournament> t = new HashMap<>();
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
		if(t.containsKey(w))
			return t.get(w);
		if(global!=null)
			return global;
		else
			return null;
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
		World g = null;
		for(Entry<World,Tournament> d : t.entrySet())
			if(d.getValue().equals(w)) {
			g=d.getKey();
			break;
		}
		if(g!=null)
		t.remove(g);
	}
}

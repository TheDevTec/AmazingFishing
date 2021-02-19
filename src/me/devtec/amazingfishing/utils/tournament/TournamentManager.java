package me.devtec.amazingfishing.utils.tournament;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.World;

public class TournamentManager {
	private static Map<World, Tournament> t = new HashMap<>();
	
	public static boolean isRunning(World world) {
		return t.containsKey(world);
	}
	
	public static boolean start(World world, TournamentType type, long time) {
		return isRunning(world) ? false : t.put(world, new Tournament(type, time))==null;
	}

	public static Tournament get(World w) {
		return t.getOrDefault(w, null);
	}
}

package me.devtec.amazingfishing.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.Loader;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.scheduler.Scheduler;
import me.devtec.theapi.scheduler.Tasker;
import me.devtec.theapi.utils.StringUtils;

public class AFKSystem {
	private static int i;
	private static long afkTime;
	protected static Map<UUID, Long> standing = new HashMap<>();
	protected static Map<UUID, Location> where = new HashMap<>();
	protected static List<UUID> noticed = new ArrayList<>();
	public static void load() {
		if(!Loader.config.getBoolean("Options.AFK.Enabled"))return;
		afkTime=StringUtils.timeFromString(Loader.config.getString("Options.AFK.Time"));
		i=new Tasker() {
			public void run() {
				for(Player p : TheAPI.getOnlinePlayers()) {
					Location stand = where.get(p.getUniqueId());
					if(stand==null) {
						where.put(p.getUniqueId(), p.getLocation());
					}else {
						Location l = p.getLocation();
						if(Math.abs(stand.getBlockX()-l.getBlockX())<=0
								||Math.abs(stand.getBlockY()-l.getBlockY())<=0
								||Math.abs(stand.getBlockZ()-l.getBlockZ())<=0) {
							where.put(p.getUniqueId(), l);
							standing.put(p.getUniqueId(), 0L);
							if(noticed.contains(p.getUniqueId())) {
								noticed.remove(p.getUniqueId());
								Loader.onAfkStop(p);
							}
						}else {
							standing.put(p.getUniqueId(), standing.get(p.getUniqueId())+1);
							if(isAFK(p.getUniqueId())) {
								if(!noticed.contains(p.getUniqueId())) {
									noticed.add(p.getUniqueId());
									Loader.onAfkStart(p);
								}else
									Loader.onAfk(p);
							}
						}
					}
				}
			}
		}.runRepeating(0, 20);
	}
	
	public static void unload() {
		if(i!=0)
		Scheduler.cancelTask(i);
		where.clear();
		standing.clear();
		i=0;
		for(UUID u : noticed)
			Loader.onAfkStop(Bukkit.getPlayer(u));
		noticed.clear();
	}
	
	public static boolean isAFK(UUID p) {
		return standing.getOrDefault(p, 0L)-afkTime<=0;
	}
	
	public static boolean isAFK(Player p) {
		return isAFK(p.getUniqueId());
	}
}

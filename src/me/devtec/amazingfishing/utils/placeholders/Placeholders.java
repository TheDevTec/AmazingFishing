package me.devtec.amazingfishing.utils.placeholders;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.utils.Manager;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.apis.PluginManagerAPI;
import me.devtec.theapi.scheduler.Tasker;
import me.devtec.theapi.sortedmap.RankingAPI;
import me.devtec.theapi.utils.StringUtils;
import me.devtec.theapi.utils.datakeeper.User;
import me.devtec.theapi.utils.nms.NMSAPI;

public class Placeholders {
	
    /*
	 * Placeholders:
	 * 
	 * amazingfishing_<CO>_<Poznávadlo | TYP NĚČEHO>_<POZNÁVADLO | NÁZEV>_<POZNÁVADLO>
	 * 
	 * %amazingfishing_<tournament | treasures | shop | records | fish>_
	 *
	 * %amazingfishing_tournament_<played | placements | TOURNAMENT TYPE>
	 * %amazingfishing_tournament_<TOURNAMENT>_<played | placement>%
	 *
	 * %amazingfishing_treasures_<caught | TREASURE>
	 * %amazingfishing_treasures_<TREASURE>_caught%
	 * 
	 * %amazingfishing_shop_gained_<exp | money | points>%
	 *
	 *%amazingfishing_records_<TYP RYBY: COD,...>_<jméno ryby>_<WEIGHT | LENGTH>%
	 *
	 *%amazingfishing_fish_<caught | eaten | sold>%
	 *%amazingfishing_fish_<TYP>_<caught | eaten | sold>%
	 *%amazingfishing_fish_<TYP>_<jméno ryby>_<caught | eaten | sold>%
	 */
	
	public static String getStatistics(Player player, String identifier) {
		String[] pp;
		pp=identifier.replace("amazingfishing", "").split("[_]");
		String path;
		path = Manager.getDataLocation()+".Statistics.";
		int i=-1;
		for(String s: pp) {
			++i;
			if(i==0)
				path=path+s.substring(0, 1).toUpperCase() + s.substring(1)+".";
			if(i==1) {
				if(s.equalsIgnoreCase("gained")) {
					path=path+s.substring(0, 1).toUpperCase() + s.substring(1)+".";
					continue;
				}
				if(identifier.startsWith("treasures")) {
					path=path+s+".";
					continue;
				}
				if(s.equalsIgnoreCase("played")||s.equalsIgnoreCase("placements")||s.equalsIgnoreCase("caught")
						||s.equalsIgnoreCase("eaten")||s.equalsIgnoreCase("sold"))
					path=path+s.substring(0, 1).toUpperCase() + s.substring(1);
				else
					path=path+s.toUpperCase()+".";
			}
			if(i==2) {
				if(s.equalsIgnoreCase("played")|| s.equalsIgnoreCase("placement")||s.equalsIgnoreCase("caught")||s.equalsIgnoreCase("exp")
						||s.equalsIgnoreCase("money")||s.equalsIgnoreCase("points")||s.equalsIgnoreCase("eaten")||s.equalsIgnoreCase("sold"))
					path=path+s.substring(0, 1).toUpperCase() + s.substring(1);
				else
					path=path+s+".";
					
			}
			if(i==3) {
				if(s.equalsIgnoreCase("Weight")|| s.equalsIgnoreCase("length"))
					path=path+s.toUpperCase();
				if(s.equalsIgnoreCase("caught") || s.equalsIgnoreCase("eaten")||s.equalsIgnoreCase("sold"))
					path=path+s.substring(0, 1).toUpperCase() + s.substring(1);
			}
		}
		if(!TheAPI.getUser(player).exist(path))
			return "0";
		return TheAPI.getUser(player).getString(path);
	}
	
	/*
	 * TOPS:
	 * 	tournaments wins
	 *  
	 *  fish caught
	 *  
	 */
	private static HashMap<Integer, Entry<UUID, Integer>> tournaments_wins = new HashMap<>(); // position - <UUID, Chyceno>
	private static HashMap<Integer, Entry<UUID, Integer>> fish_caught = new HashMap<>(); // position - < UUID, Chyceno>
	public static enum TopType {
		FISH_CAUGHT,
		TOURNAMENTS_WINS;
	}
	public static String getTop(TopType TopType, int position) {
		switch (TopType) {
		case FISH_CAUGHT:
			Entry<UUID, Integer> data = fish_caught.get(position);
			return Loader.config.getString("Options.Placeholders.Format.Fish.Caught").replace("%position%", position+"")
					.replace("%player%", Bukkit.getServer().getOfflinePlayer(data.getKey()).getName()+"" ).replace("%amount%", ""+data.getValue());
		case TOURNAMENTS_WINS:
			Entry<UUID, Integer> dat = fish_caught.get(position);
			return Loader.config.getString("Options.Placeholders.Format.Tournaments.Wins").replace("%position%", position+"")
					.replace("%player%", Bukkit.getServer().getOfflinePlayer(dat.getKey()).getName()+"" ).replace("%amount%", ""+dat.getValue());
		default:
			return "";
		}
		
	}
	public static void broadcast(String msg) {
			NMSAPI.postToMainThread(new Runnable() {
			public void run() {	
				Bukkit.broadcastMessage(msg);
			}
		});
	}
	static int task;
	public static void loadTops() {
		if(task!=0)
			Bukkit.getScheduler().cancelTask(task);
		task = new Tasker() {
			public void run() {	
				tournaments_wins.clear();
				fish_caught.clear();
				HashMap<UUID, Integer> t_wins = new HashMap<>();
				HashMap<UUID, Integer> f_caught = new HashMap<>();
				
				for(UUID uuid : TheAPI.getUsers()) {
					User u = TheAPI.getUser(uuid);
					//broadcast(uuid+" ; "+u.getName()+ " ;"+LoaderClass.cache.lookupNameById(uuid)+ " ; "+Bukkit.getServer().getOfflinePlayer(uuid).getName());
					if(u.exist(Manager.getDataLocation()+".Statistics.Tournament.Placements")) {
						int i = u.getInt(Manager.getDataLocation()+".Statistics.Tournament.Placements");
						t_wins.put(uuid, i);
					}
					if(u.exist(Manager.getDataLocation()+".Statistics.Fish.Caught")) {
						int i = u.getInt(Manager.getDataLocation()+".Statistics.Fish.Caught");
						f_caught.put(uuid, i);
					}
				}				

				RankingAPI<UUID, Integer> ranks = new RankingAPI<UUID, Integer>(t_wins);
				int pos=1;
				for(Entry<UUID, Integer> data: ranks.entrySet()) {
					if(pos==5)break;
					tournaments_wins.put(pos, data);
					++pos;
				};
				RankingAPI<UUID, Integer> ranks2 = new RankingAPI<UUID, Integer>(f_caught);
				pos=1;
				for(Entry<UUID, Integer> data: ranks2.entrySet()) {
					if(pos==5)break;
					fish_caught.put(pos, data);
					++pos;
				};
				if(Loader.config.getBoolean("Options.Placeholders.Settings.MessageOnReload")==true) {
					NMSAPI.postToMainThread(new Runnable() {
						public void run() {				
						TheAPI.getConsole().sendMessage(TheAPI.colorize("&8 *********************************************"));
						TheAPI.getConsole().sendMessage(TheAPI.colorize(Loader.getPrefix()+"&3 Reloaded TOP placeholders &3."));
						TheAPI.getConsole().sendMessage(TheAPI.colorize("&8 *********************************************"));
						}
					});
				}

			}
		}.runRepeating(1, StringUtils.getTimeFromString(Loader.config.getString("Options.Placeholders.Settings.Reload_of_data_time"))*20 );
		
	}
	
	public static boolean isEnabledPlaceholderAPI() {
		return PluginManagerAPI.getPlugin("PlaceholderAPI")!=null;
	}
	
}

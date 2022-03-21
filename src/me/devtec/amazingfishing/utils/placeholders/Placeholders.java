package me.devtec.amazingfishing.utils.placeholders;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.utils.Manager;
import me.devtec.shared.API;
import me.devtec.shared.dataholder.Config;
import me.devtec.shared.scheduler.Tasker;
import me.devtec.shared.sorting.RankingAPI;
import me.devtec.shared.sorting.SortingAPI.ComparableObject;
import me.devtec.shared.utility.StringUtils;

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
		if(!API.getUser(player.getUniqueId()).exists(path))
			return "0";
		return API.getUser(player.getUniqueId()).getString(path);
	}
	
	/*
	 * TOPS:
	 * 	tournaments wins
	 *  
	 *  fish caught
	 *  
	 */
	private static HashMap<Integer, ComparableObject<UUID, Integer>> tournaments_wins = new HashMap<>(); // position - <UUID, Chyceno>
	private static HashMap<Integer, ComparableObject<UUID, Integer>> fish_caught = new HashMap<>(); // position - < UUID, Chyceno>
	public static enum TopType {
		FISH_CAUGHT,
		TOURNAMENTS_WINS;
	}
	public static String getTop(TopType TopType, int position) {
		switch (TopType) {
		case FISH_CAUGHT:
			ComparableObject<UUID, Integer> data = fish_caught.get(position);			
			if(data==null)
				return Loader.config.getString("Options.Placeholders.Format.Fish.Caught").replace("%position%", position+"")
						.replace("%player%", "-" ).replace("%amount%", "-");
			return Loader.config.getString("Options.Placeholders.Format.Fish.Caught").replace("%position%", position+"")
					.replace("%player%", Bukkit.getServer().getOfflinePlayer(data.getKey()).getName()+"" ).replace("%amount%", ""+data.getValue());
		case TOURNAMENTS_WINS:
			ComparableObject<UUID, Integer> dat = tournaments_wins.get(position);
			if(dat==null)
				return Loader.config.getString("Options.Placeholders.Format.Tournaments.Wins").replace("%position%", position+"")
					.replace("%player%", "-" ).replace("%amount%", "-");
			return Loader.config.getString("Options.Placeholders.Format.Tournaments.Wins").replace("%position%", position+"")
					.replace("%player%", Bukkit.getServer().getOfflinePlayer(dat.getKey()).getName()+"" ).replace("%amount%", ""+dat.getValue());
		default:
			return "";
		}
		
	}
	
	public static int task;
	public static void loadTops() {
		if(task!=0)
			Bukkit.getScheduler().cancelTask(task);
		task = new Tasker() {
			public void run() {	
				tournaments_wins.clear();
				fish_caught.clear();
				HashMap<UUID, Integer> t_wins = new HashMap<>();
				HashMap<UUID, Integer> f_caught = new HashMap<>();
				
				for(File uuidfile : new File("plugins/TheAPI/Users").listFiles()) {
					UUID uuid = UUID.fromString(uuidfile.getName().replace(".yml", ""));
					Config u = API.getUser(uuid);
					if(u.exists(Manager.getDataLocation()+".Statistics.Tournament.Placements")) {
						int i = u.getInt(Manager.getDataLocation()+".Statistics.Tournament.Placements");
						t_wins.put(uuid, i);
					}
					if(u.exists(Manager.getDataLocation()+".Statistics.Fish.Caught")) {
						int i = u.getInt(Manager.getDataLocation()+".Statistics.Fish.Caught");
						f_caught.put(uuid, i);
					}
				}		
				int pos=1;		
				if(!t_wins.isEmpty()) {
				RankingAPI<UUID, Integer> ranks = new RankingAPI<UUID, Integer>(t_wins);
				for(ComparableObject<UUID, Integer> data: ranks.all()) {
					if(pos==5)break;
					tournaments_wins.put(pos, data);
					++pos;
				};
				}
				if(!f_caught.isEmpty()) {
				RankingAPI<UUID, Integer> ranks2 = new RankingAPI<UUID, Integer>(f_caught);
				pos=1;
				for(ComparableObject<UUID, Integer> data: ranks2.all()) {
					if(pos==5)break;
					fish_caught.put(pos, data);
					++pos;
				};
				}
			}
		}.runRepeating(1, StringUtils.getTimeFromString(Loader.config.getString("Options.Placeholders.Settings.Reload_of_data_time"))*20 );
		
	}
	
	public static boolean isEnabledPlaceholderAPI() {
		return Bukkit.getPluginManager().getPlugin("PlaceholderAPI")!=null;
	}
	
}

package me.DevTec.AmazingFishing.Placeholders;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.DevTec.AmazingFishing.Loader;
import me.DevTec.TheAPI.APIs.PluginManagerAPI;
import me.DevTec.TheAPI.Scheduler.Tasker;
import me.DevTec.TheAPI.SortedMap.RankingAPI;
import me.DevTec.TheAPI.Utils.StringUtils;

public class Placeholders {

	/*
	 * 
		Loader.me.set("Players."+player+".Stats.Tournaments", 1+Loader.me.getInt("Players."+player+".Stats.Tournaments"));
		Loader.me.set("Players."+player+".Stats.Top."+i+".Tournaments", 1+Loader.me.getInt("Players."+player+".Stats.Top."+i+".Tournaments"));
	 */
	
	/*
	 *   %amazingfishing_tournament_wins_top1%
	 */
	public static  HashMap<String, Integer> hash = new HashMap<String, Integer>();
	public static String getTopTournamentWins(String identifier) {
		if(!identifier.startsWith("tournament_wins_top"))
			return "&c&lError. Please use amazingfishing_tournament_wins_top<position>";
		String ide = identifier.replaceAll("[^0-9+-]", "");
		int top = StringUtils.getInt(ide);
		if(top==0)return "&c&lError. Please start from 1!";
		
		new Tasker() {
			@Override
			public void run() {

				 for(String player: Loader.me.getKeys("Players")) {
					 if(!Loader.me.exists("Players."+player+".Stats.Top.1.Tournaments"))continue;
					 if(!Loader.me.exists("Players."+player+".Stats.Tournaments"))continue;
					 hash.put(player, Loader.me.getInt("Players."+player+".Stats.Top.1.Tournaments"));
				 }
			}
		}.runTask();
		if(hash.size()<top)
			return "-";
		 RankingAPI<String, Integer> rank =new RankingAPI<String, Integer>(hash);
		 String player =rank.getObject(top).toString(); 
		 int wins = StringUtils.getInt(""+rank.getValue(player));
		 if(player==null) return "-";
		 String format = Loader.c.getString("Options.Placeholders.Format.amazingfishing_tournament_wins_top");	
		return format.replace("%position%", top+"").replace("%wins%", wins+"").replace("%player%", player)
				.replace("%total%", ""+Loader.me.getInt("Players."+player+".Stats.Tournaments"));
	}
	public static void prepareHash() {
		new Tasker() {
			@Override
			public void run() {
				for(String player: Loader.me.getKeys("Players")) {
					 if(Loader.me.exists("Players."+player+".Stats.Top.1.Tournaments"))
						 hash.put(player, Loader.me.getInt("Players."+player+".Stats.Top.1.Tournaments"));
					 if(Loader.me.exists("Players."+player+".Stats.Tournaments"))
						 hash2.put(player, Loader.me.getInt("Players."+player+".Stats.Tournaments"));
					 if(Loader.me.exists("Players."+player+".Stats.Amount"))
						 hash3.put(player, Loader.me.getInt("Players."+player+".Stats.Amount"));
				 }
			}
		}.runTask();
	}
	/*
	 *   %amazingfishing_tournament_played_top1%
	 */
	public static  HashMap<String, Integer> hash2 = new HashMap<String, Integer>();
		public static String getTopTournamentPlayed(String identifier) {
			if(!identifier.startsWith("tournament_played_top"))
				return "&c&lError. Please use amazingfishing_tournament_played_top<position>";
			String ide = identifier.replaceAll("[^0-9+-]", "");
			int top = StringUtils.getInt(ide);
			if(top==0)return "&c&lError. Please start from 1!";
			
			new Tasker() {
				@Override
				public void run() {

					 for(String player: Loader.me.getKeys("Players")) {
						 if(!Loader.me.exists("Players."+player+".Stats.Tournaments"))continue;
						 hash2.put(player, Loader.me.getInt("Players."+player+".Stats.Tournaments"));
					 }
				}
			}.runTask();
			if(hash2.size()<top)
				return "-";
			 RankingAPI<String, Integer> rank =new RankingAPI<String, Integer>(hash2);
			 String player =rank.getObject(top).toString(); 
			 int played = StringUtils.getInt(""+rank.getValue(player));
			 if(player==null) return "-";
			 String format = Loader.c.getString("Options.Placeholders.Format.amazingfishing_tournament_played_top");	
			return format.replace("%position%", top+"").replace("%played%", played+"").replace("%player%", player);
		}
		/*
		 *   %amazingfishing__caught_top1%
		 */
		public static  HashMap<String, Integer> hash3 = new HashMap<String, Integer>();
		public static String getTopByCaught(String identifier) {
			if(!identifier.startsWith("caught_top"))
				return "&c&lError. Please use amazingfishing_caught_top<position>";
			String ide = identifier.replaceAll("[^0-9+-]", "");
			int top = StringUtils.getInt(ide);
			if(top==0)return "&c&lError. Please start from 1!";
			
			new Tasker() {
				@Override
				public void run() {
					 for(String player: Loader.me.getKeys("Players")) {
						 if(!Loader.me.exists("Players."+player+".Stats.Amount"))continue;
						 hash3.put(player, Loader.me.getInt("Players."+player+".Stats.Amount"));
					 }
				}
			}.runTask();
			if(hash3.size()<top)
				return "-";
			 RankingAPI<String, Integer> rank =new RankingAPI<String, Integer>(hash3);
			 String player =rank.getObject(top).toString(); 
			 int amount = StringUtils.getInt(""+rank.getValue(player));
			 if(player==null) return "-";
			 String format = Loader.c.getString("Options.Placeholders.Format.amazingfishing_caught_top");
			return format.replace("%position%", top+"").replace("%amount%", amount+"").replace("%player%", player);
		}
		
	public static String getPlayerTournamentsTop(Player player, String identifier) {
		if(player==null) return "";
		if(!identifier.startsWith("player_tournaments_top"))
			return "&c&lError. Please use amazingfishing_player_tournaments_top<position>";
		String ide = identifier.replaceAll("[^0-9+-]", "");
		int top = StringUtils.getInt(ide);
		if(top==0||top>4)return "&c&lError. Please use numbers from 1-4!";
		if(!Loader.me.exists("Players."+player.getName()+".Stats.Top."+top+".Tournaments")) return "-";
		int wins = Loader.me.getInt("Players."+player.getName()+".Stats.Top."+top+".Tournaments");
		
		if(wins==0) return "-";
		return wins+"";
	}	
	
	public static boolean isEnabledPlaceholderAPI() {
		return PluginManagerAPI.getPlugin("PlaceholderAPI")!=null;
	}
	
}


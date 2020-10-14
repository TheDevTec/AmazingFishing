package AmazingFishing;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.DevTec.AmazingFishing.Loader;
import me.DevTec.TheAPI.TheAPI;
import me.DevTec.TheAPI.TheAPI.SudoType;
import me.DevTec.TheAPI.Scheduler.Scheduler;
import me.DevTec.TheAPI.Scheduler.Tasker;
import me.DevTec.TheAPI.SortedMap.RankingAPI;
import me.DevTec.TheAPI.Utils.StringUtils;

public class Tournament {

	static List<String> legend = Arrays.asList("Length","MostCatch","Weight");
	public static enum Type{
		Length,
		MostCatch,
		Weight,
		Random;
	}
	static int count;
	static int save;
	public static boolean running() {
		return now != null;
	}
	public static void add(Player p, double record, double weight) {
		if(now != null) {
			
		if(now==Type.MostCatch) {
			double i = stats.containsKey(p.getName()) ? stats.get(p.getName()).doubleValue(): 0.0;
			++i;
			if(stats.containsKey(p.getName()))
				stats.put(p.getName(), new BigDecimal(i));
			else
			stats.put(p.getName(), new BigDecimal(i));
		}else {
			double value;
			if(now==Type.Length) value=record;
			else value=weight;
			if(stats.containsKey(p.getName())) {
				if(stats.get(p.getName()).floatValue()<new BigDecimal(value).floatValue())
					stats.put(p.getName(), new BigDecimal(value));
			}else
			stats.put(p.getName(), new BigDecimal(value));
		}
	}}
	static Type now;
	public static boolean inParticle(Player p) {
		return stats.containsKey(p.getName());
	}
	
	static HashMap<String, BigDecimal> stats = new HashMap<String, BigDecimal>();
	public static void stop(boolean rewards) {
		if(now==null)return;
		if(!rewards) {
			TheAPI.broadcastMessage(Loader.s("Stopped")
					.replace("%type%", Loader.c.getString("Tournaments."+now.toString()+".Name")).replace("%time%",
							StringUtils.setTimeToString(Tournament.count)));
			if(r!=-1)
				Scheduler.cancelTask(r);
    	count=0;
    	save=0;
        stats.clear();
    	now=null;
	}else{
		TheAPI.broadcastMessage(Loader.s("Stopped")
				.replace("%type%", Loader.c.getString("Tournaments."+now.toString()+".Name"))
				.replace("%time%", StringUtils.setTimeToString(Tournament.count)));
		Scheduler.cancelTask(r);
		if(Loader.c.getBoolean("Options.Tournament.DeletePlayersOnLeave")==true)
			for(String s:stats.keySet()) {if(TheAPI.getPlayer(s)==null)stats.remove(s);}
    	TheAPI.broadcastMessage(Loader.s("Winners")
				.replace("%type%", Loader.c.getString("Tournaments."+now.toString()+".Name")));
		RankingAPI<String, BigDecimal> w = new RankingAPI<>(stats);
		int i = 0;
		for(Entry<String, BigDecimal> item : w.entrySet()) {
			if(i==3)break;
			++i;
 			String player = item.getKey();
 			TheAPI.broadcastMessage(Loader.c.getString("Tournaments."+now.toString()+".Positions")
     				.replace("%position%", i+"")
     				.replace("%player%", player)
     				.replace("%playername%", p(player))
     				.replace("%formated_value%", String.format("%2.02f",item.getValue().floatValue()).replace(",", "."))
     				.replace("%value%",item.getValue().floatValue()+""));
 			if(!player.equalsIgnoreCase("-"))
    			for(String s:Loader.c.getStringList("Tournaments."+now.toString()+".Rewards."+i))
    				TheAPI.sudoConsole(SudoType.COMMAND, Color.c(s.replace("%position%", i+"")
         				.replace("%player%", player)
         				.replace("%playername%", p(player))
         				.replace("%formated_value%", String.format("%2.02f",item.getValue().floatValue()).replace(",", "."))
         				.replace("%value%",item.getValue().floatValue()+"")));
 			Loader.me.set("Players."+player+".Stats.Tournaments", 1+Loader.me.getInt("Players."+player+".Stats.Tournaments"));
				Loader.me.set("Players."+player+".Stats.Top."+i+".Tournaments", 
						1+Loader.me.getInt("Players."+player+".Stats.Top."+i+".Tournaments"));
 		}
		stats.clear();
    	now=null;
        count=0;
        save=0;
        r=-1;
        return;
        }
	}
	public static int r=-1;
	public static void startType(Type type, int length, boolean e) {
		if(e)// TODO - asi nefunguje -- spouští se i když tam je málo hráèù
			if(Loader.c.getInt("Options.Tournament.RequiredPlayers") > TheAPI.getOnlinePlayers().size())return;
		if(type==Type.Random)type=Type.valueOf(TheAPI.getRandomFromList(legend).toString());
		now=type;
		count=length;
		if(count == 0)count = 600;
		save=count;
		 int as = (int) (save*0.80),bs = (int) (save*0.50), cs = (int) (save*0.30),ds = (int) (save*0.10);
         if(as<3)as=-20;
         if(bs<3)bs=-20;
         if(cs<3)cs=-20;
         if(ds<3)ds=-20;
         int a=as,b=bs,c=bs,d=ds;
		TheAPI.broadcastMessage(Loader.s("Started")
				.replace("%type%", Loader.c.getString("Tournaments."+type.toString()+".Name")).replace("%time%", StringUtils.setTimeToString(Tournament.count)));
		r=new Tasker() {
           public void run(){
				if(now==null) {
					cancel();
					return;
				}
                if(count > 0)
                --count;
                if(Loader.c.getBoolean("Options.Tournament.DeletePlayersOnLeave")==true)
                	for(String s:stats.keySet()) {if(TheAPI.getPlayer(s)==null)stats.remove(s);}
                if(Loader.c.getBoolean("Options.BossBar.Use"))
                for(Player p : TheAPI.getOnlinePlayers())
                	if(inParticle(p) && Loader.c.getBoolean("Options.BossBar.OnlyIfCatchFish")||!Loader.c.getBoolean("Options.BossBar.OnlyIfCatchFish"))
                		TheAPI.sendBossBar(p, Loader.c.getString("Options.BossBar.Running").replace("%time%", count+"").replace("%type%", now.toString()).replace("%time_formated%", StringUtils.setTimeToString(count)),1,20);

     			RankingAPI<String, BigDecimal> w = new RankingAPI<>(stats);
                if(count == a || count == b || count == c || count == d) {
                	TheAPI.broadcastMessage(Loader.s("Running")
            				.replace("%type%", Loader.c.getString("Tournaments."+now.toString()+".Name")).replace("%time%", 
            						StringUtils.setTimeToString(Tournament.count)));
                	for (int i = 1; i < 4; i++) {
                		if(w.getObject(i)==null)continue;
             			String player = w.getObject(i).toString();
        			if(player==null)continue;
        			String value = (now==Type.MostCatch ? ""+w.getValue(player).floatValue() : String.format("%2.02f",w.getValue(player).floatValue()).replace(",", "."));
        			if(!Loader.c.getBoolean("Options.UseDoubles.Length")||!Loader.c.getBoolean("Options.UseDoubles.Weight"))
        				value=value.replaceAll("\\.00", "");
        			TheAPI.broadcastMessage(Loader.c.getString("Tournaments."+now.toString()+".Positions")
            				.replace("%position%", i+"")
            				.replace("%player%", player)
            				.replace("%playername%", p(player))
            				.replace("%value%",value));
        		}}
                if(count == 0) {
                	TheAPI.broadcastMessage(Loader.s("Winners")
            				.replace("%type%", Loader.c.getString("Tournaments."+now.toString()+".Name")));
           		for (int i = 1; i < 4; i++) {
           			if(w.getObject(i)==null)continue;
         			String player = w.getObject(i).toString();
    			if(player==null)continue;
    			String value = (now==Type.MostCatch ? ""+w.getValue(player).floatValue() : String.format("%2.02f",w.getValue(player).floatValue()).replace(",", "."));
    			if(!Loader.c.getBoolean("Options.UseDoubles.Length")||!Loader.c.getBoolean("Options.UseDoubles.Weight"))
    				value=value.replaceAll("\\.00", "");
    			
    			TheAPI.broadcastMessage(Loader.c.getString("Tournaments."+now.toString()+".Positions")
        				.replace("%position%", i+"")
        				.replace("%player%", player)
        				.replace("%playername%", p(player))
        				.replace("%value%",value));
    			if(!player.equalsIgnoreCase("-")) {
         			for(String s:Loader.c.getStringList("Tournaments."+now.toString()+".Rewards."+i)) {
         				String valuE = value;
         				int in = i;
         				Bukkit.getScheduler().scheduleSyncDelayedTask(Loader.plugin, new Runnable() {
         					public void run() {
         						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s.replace("%player%", player)
                 						.replace("%playername%", p(player)).replace("%value%", valuE).replace("%position%", in+""));
         					}
         				}, 5);
         			}
    			}
      			Loader.me.set("Players."+player+".Stats.Tournaments", 1+Loader.me.getInt("Players."+player+".Stats.Tournaments"));
 					Loader.me.set("Players."+player+".Stats.Top."+i+".Tournaments", 
 							1+Loader.me.getInt("Players."+player+".Stats.Top."+i+".Tournaments"));
           		}
     			Loader.saveChatMe();
                stats.clear();
            	now=null;
                count=0;
                save=0;
                r=-1;
				cancel();
                }
           }
       }.runRepeating(20,20);
	}
	static String p(String s) {
		try {
		if(TheAPI.getPlayer(s)!=null)return TheAPI.getPlayer(s).getDisplayName();
		return s;
		}catch(Exception e) {
			return "-";
		}
	}
}

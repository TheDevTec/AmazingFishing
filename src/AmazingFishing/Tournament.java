package AmazingFishing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.Straiker123.TheAPI;

public class Tournament {
	public static enum Type{
		Length,
		MostCatch,
		Weight,
		Random;
	}
	static int count;
	static int save;
	public static boolean running() {
		if(now == null)return false;
		return true;
	}
	public static void add(Player p, double record, double weight) {
		if(running()) {
		if(now==Type.Length)addRecord(p,record);
		if(now==Type.MostCatch)addFish(p);
		if(now==Type.Weight)addWeight(p,weight);
	}}
	static Type now;
	public static void addFish(Player p) {
		double i = fishes.containsKey(p.getName()) ? fishes.get(p.getName()): 0.0;
		++i;
		if(fishes.containsKey(p.getName())) {
			fishes.replace(p.getName(),i);
		}else
		fishes.put(p.getName(), i);
	}
	public static void addRecord(Player p, double record) {
		if(records.containsKey(p.getName())) {
			if(records.get(p.getName())<record)
				records.replace(p.getName(), record);
		}else
		records.put(p.getName(), record);
	}
	public static void addWeight(Player p, double record) {
				if(weight.containsKey(p.getName())) {
					if(weight.get(p.getName())<record)
						weight.replace(p.getName(), record);
				}else
					weight.put(p.getName(), record);
	}
	
	public static boolean inParticle(Player p) {
		return weight.containsKey(p.getName()) || fishes.containsKey(p.getName()) || records.containsKey(p.getName());
	}
	
	static int run;
	static HashMap<String, Double> records = new HashMap<String, Double>();
	static HashMap<String, Double> fishes = new HashMap<String, Double>();
	static HashMap<String, Double> weight = new HashMap<String, Double>();
	public static void stop(boolean rewards) {
		if(now==null)return;
		if(!rewards) {
			TheAPI.broadcastMessage(Loader.s("Stopped")
					.replace("%type%", Loader.c.getString("Tournaments."+now.toString()+".Name")).replace("%time%",
							TheAPI.getStringUtils().setTimeToString(Tournament.count)));
    	Bukkit.getServer().getScheduler().cancelTask(run);
    	count=0;
    	save=0;
        records.clear();
        fishes.clear();
    	now=null;
	}else{
		TheAPI.broadcastMessage(Loader.s("Stopped")
				.replace("%type%", Loader.c.getString("Tournaments."+now.toString()+".Name"))
				.replace("%time%", TheAPI.getStringUtils().setTimeToString(Tournament.count)));
    	Bukkit.getServer().getScheduler().cancelTask(run);
    	TheAPI.broadcastMessage(Loader.s("Winners")
				.replace("%type%", Loader.c.getString("Tournaments."+now.toString()+".Name")));
		 switch(now) {
 		case Weight:
 			for(String s:weight.keySet()) {if(Bukkit.getPlayer(s)==null)weight.remove(s);}
 			Ranking wsss = new Ranking(weight);
     		for (int i = 2; i >= 0; i--) {
     			String player = wsss.getPlayer(3-i);
     			TheAPI.broadcastMessage(Loader.c.getString("Tournaments."+now.toString()+".Positions")
         				.replace("%position%", 3-i+"")
         				.replace("%player%", player)
         				.replace("%playername%", p(player))
         				.replace("%weight%", String.format("%2.02f",TheAPI.getStringUtils().getDouble(wsss.getDouble(3-i)))).replace(",", "."));
     			if(!player.equalsIgnoreCase("-"))
        			for(String s:Loader.c.getStringList("Tournaments."+now.toString()+".Rewards."+(3-i)))
         			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Color.c(s.replace("%position%", (3-i)+"")
             				.replace("%player%", player)
             				.replace("%playername%", p(player))
             				.replace("%weight%", String.format("%2.02f",TheAPI.getStringUtils().getDouble(wsss.getDouble(3-i))).replace(",", "."))));
     			Loader.me.set("Players."+player+".Stats.Tournaments", 1+Loader.me.getInt("Players."+player+".Stats.Tournaments"));
					Loader.me.set("Players."+player+".Stats.Top."+(3-i)+".Tournaments", 
							1+Loader.me.getInt("Players."+player+".Stats.Top."+(3-i)+".Tournaments"));
     		}
				Loader.saveChatMe();
 		break;
     		case MostCatch:
     			for(String s:fishes.keySet()) {if(Bukkit.getPlayer(s)==null)fishes.remove(s);}
     			Ranking wss = new Ranking(weight);
         		for (int i = 2; i >= 0; i--) {
         			String player = wss.getPlayer(3-i);
         			TheAPI.broadcastMessage(Loader.c.getString("Tournaments."+now.toString()+".Positions")
             				.replace("%position%", 3-i+"")
             				.replace("%player%", player)
             				.replace("%playername%", p(player))
             				.replace("%fishes%", TheAPI.getStringUtils().getInt(wss.getDouble(3-i))+""));
         			if(!player.equalsIgnoreCase("-"))
            			for(String s:Loader.c.getStringList("Tournaments."+now.toString()+".Rewards."+(3-i)))
             			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Color.c(s.replace("%position%", (3-i)+"")
                 				.replace("%player%", player)
                 				.replace("%playername%", p(player))
                 				.replace("%fishes%", TheAPI.getStringUtils().getInt(wss.getDouble(3-i))+"")));
         			Loader.me.set("Players."+player+".Stats.Tournaments", 1+Loader.me.getInt("Players."+player+".Stats.Tournaments"));
 					Loader.me.set("Players."+player+".Stats.Top."+(3-i)+".Tournaments", 
 							1+Loader.me.getInt("Players."+player+".Stats.Top."+(3-i)+".Tournaments"));
         		}
					Loader.saveChatMe();
     		break;
     		case Length:
     			for(String s:records.keySet()) {if(Bukkit.getPlayer(s)==null)records.remove(s);}
     			Ranking ws = new Ranking(records);
         		for (int i = 2; i >= 0; i--) {
         			String player = ws.getPlayer(3-i);
         			TheAPI.broadcastMessage(Loader.c.getString("Tournaments."+now.toString()+".Positions")
             				.replace("%position%", 3-i+"")
             				.replace("%player%", player)
             				.replace("%playername%", p(player))
             				.replace("%length%", String.format("%2.02f",TheAPI.getStringUtils().getDouble(ws.getDouble(3-i)))).replace(",", "."));
         			if(!player.equalsIgnoreCase("-"))
            			for(String s:Loader.c.getStringList("Tournaments."+now.toString()+".Rewards."+(3-i)))
             			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Color.c(s.replace("%position%", (3-i)+"")
                 				.replace("%player%", player)
                 				.replace("%playername%", p(player))
                 				.replace("%length%", String.format("%2.02f",TheAPI.getStringUtils().getDouble(ws.getDouble(3-i))).replace(",", "."))));
         			Loader.me.set("Players."+player+".Stats.Tournaments", 1+Loader.me.getInt("Players."+player+".Stats.Tournaments"));
 					Loader.me.set("Players."+player+".Stats.Top."+(3-i)+".Tournaments", 
 							1+Loader.me.getInt("Players."+player+".Stats.Top."+(3-i)+".Tournaments"));
         		}
					Loader.saveChatMe();
     		break;
     		default:
     			break;
          }
        records.clear();
        fishes.clear();
    	now=null;
        count=0;
        save=0;
        return;
        }
	}
	public static void startType(Type type, int length) {
		if(type==Type.Random) {
			ArrayList<String> legend = new ArrayList<String>();
			legend.add("Length");
			legend.add("MostCatch");
			legend.add("Weight");
			int target = 0;
			for (int counter =1; counter<=1;counter++) {
			    Random object = new Random();
			    target = 0;
			if(legend.size() > target) {
				target = object.nextInt(legend.size());
			}
		}
			type=Type.valueOf(legend.get(target));
		}
		now=type;
		count=length;
		if(count == 0) {
			count = 600;
		}
		save=count;
		TheAPI.broadcastMessage(Loader.s("Started")
				.replace("%type%", Loader.c.getString("Tournaments."+now.toString()+".Name")).replace("%time%", TheAPI.getStringUtils().setTimeToString(Tournament.count)));
		run=Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Loader.plugin, new Runnable(){
			@Override
           public void run(){
				if(now==null) {
					Bukkit.getScheduler().cancelTask(run);
					return;
				}
                if(count > 0) {
                --count;
                }
                for(Player p : Bukkit.getOnlinePlayers())
                	if(inParticle(p) && Loader.c.getBoolean("Options.BossBar.Use"))
                		TheAPI.sendBossBar(p, Loader.c.getString("Options.BossBar.Running").replace("%time%", count+"").replace("%type%", now.toString()).replace("%time_formated%", TheAPI.getStringUtils().setTimeToString(count)),1,20);
                int a = (int) (save*0.80);
                int b = (int) (save*0.50);
                int c = (int) (save*0.30);
                int d = (int) (save*0.10);
                if(a==0)a=-20;
                if(b==0)b=-20;
                if(c==0)c=-20;
                if(d==0)d=-20;
                
                if(a==1)a=-20;
                if(b==1)b=-20;
                if(c==1)c=-20;
                if(d==1)d=-20;

                if(a==2)a=-20;
                if(b==2)b=-20;
                if(c==2)c=-20;
                if(d==2)d=-20;
                
                if(a==3)a=-20;
                if(b==3)b=-20;
                if(c==3)c=-20;
                if(d==3)d=-20;
                if(count == a || count == b || count == c || count == d) {
                	TheAPI.broadcastMessage(Loader.s("Running")
            				.replace("%type%", Loader.c.getString("Tournaments."+now.toString()+".Name")).replace("%time%", 
            						TheAPI.getStringUtils().setTimeToString(Tournament.count)));
            		switch(now) {
            		case Weight:
            			for(String s:weight.keySet()) {if(Bukkit.getPlayer(s)==null)weight.remove(s);}
            			Ranking w = new Ranking(weight);
            		for (int i = 2; i >= 0; i--) {
            			String player = w.getPlayer(3-i);
            			if(player==null)continue;
            			TheAPI.broadcastMessage(Loader.c.getString("Tournaments."+now.toString()+".Positions")
                				.replace("%position%", 3-i+"")
                				.replace("%player%", player)
                				.replace("%playername%", p(player))
                				.replace("%weight%", String.format("%2.02f",TheAPI.getStringUtils().getDouble(w.getDouble(3-i)))).replace(",", "."));
            		}
            		break;
                		case MostCatch:
                			for(String s:fishes.keySet()) {if(Bukkit.getPlayer(s)==null)fishes.remove(s);}
                			Ranking ww = new Ranking(fishes);
                		for (int i = 2; i >= 0; i--) {
                			String player = ww.getPlayer(3-i);
                			if(player==null)continue;
                			TheAPI.broadcastMessage(Loader.c.getString("Tournaments."+now.toString()+".Positions")
                    				.replace("%position%", 3-i+"")
                    				.replace("%player%", player)
                    				.replace("%playername%", p(player))
                    				.replace("%fishes%",TheAPI.getStringUtils().getInt(ww.getDouble(3-i))+""));
                		}
                		break;
                		case Length:
                			for(String s:records.keySet()) {if(Bukkit.getPlayer(s)==null)records.remove(s);}
                			Ranking ws = new Ranking(records);
                    		for (int i = 2; i >= 0; i--) {

                    			String player = ws.getPlayer(3-i);
                    			if(player==null)continue;
                    			TheAPI.broadcastMessage(Loader.c.getString("Tournaments."+now.toString()+".Positions")
                        				.replace("%position%", 3-i+"")
                        				.replace("%player%", player)
                        				.replace("%playername%", p(player))
                        				.replace("%length%", String.format("%2.02f",TheAPI.getStringUtils().getDouble(ws.getDouble(3-i)))).replace(",", "."));
                    		}
                		break;
                		default:
                			break;
                     }}
                if(count == 0) {
                	Bukkit.getServer().getScheduler().cancelTask(run);
                	TheAPI.broadcastMessage(Loader.s("Winners")
            				.replace("%type%", Loader.c.getString("Tournaments."+now.toString()+".Name")));
                switch(now) {
        		case Weight:
        			for(String s:weight.keySet()) {if(Bukkit.getPlayer(s)==null)weight.remove(s);}
        			Ranking wsss = new Ranking(weight);
            		for (int i = 2; i >= 0; i--) {
            			String player = wsss.getPlayer(3-i);
            			if(Bukkit.getPlayer(player)!=null) {
                    	if(inParticle(Bukkit.getPlayer(player)) && Loader.c.getBoolean("Options.BossBar.Use"))
                    		TheAPI.sendBossBar(Bukkit.getPlayer(player), Loader.c.getString("Options.BossBar.Win").replace("%type%", now.toString()).replace("%position%", 3-1+""), 1, 20);
            			}
            			TheAPI.broadcastMessage(Loader.c.getString("Tournaments."+now.toString()+".Positions")
                				.replace("%position%", 3-i+"")
                				.replace("%player%", player)
                				.replace("%playername%", p(player))
                				.replace("%weight%", String.format("%2.02f",TheAPI.getStringUtils().getDouble(wsss.getDouble(3-i)))).replace(",", "."));
            			if(!player.equalsIgnoreCase("-"))
                			for(String s:Loader.c.getStringList("Tournaments."+now.toString()+".Rewards."+(3-i)))
                			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Color.c(s.replace("%position%", (3-i)+"")
                    				.replace("%player%", player)
                    				.replace("%playername%", p(player))
                    				.replace("%weight%", String.format("%2.02f",TheAPI.getStringUtils().getDouble(wsss.getDouble(3-i))).replace(",", "."))));
            			Loader.me.set("Players."+player+".Stats.Tournaments", 1+Loader.me.getInt("Players."+player+".Stats.Tournaments"));
    					Loader.me.set("Players."+player+".Stats.Top."+(3-i)+".Tournaments", 
    							1+Loader.me.getInt("Players."+player+".Stats.Top."+(3-i)+".Tournaments"));
            		}
					Loader.saveChatMe();
        		break;
            		case MostCatch:
            			for(String s:fishes.keySet()) {if(Bukkit.getPlayer(s)==null)fishes.remove(s);}
            			Ranking wss = new Ranking(weight);
                		for (int i = 2; i >= 0; i--) {
                			String player = wss.getPlayer(3-i);
                			if(Bukkit.getPlayer(player)!=null) {
                            	if(inParticle(Bukkit.getPlayer(player)) && Loader.c.getBoolean("Options.BossBar.Use"))
                            		TheAPI.sendBossBar(Bukkit.getPlayer(player), Loader.c.getString("Options.BossBar.Win").replace("%type%", now.toString()).replace("%position%", 3-1+""), 1, 20);
                    			}
                			TheAPI.broadcastMessage(Loader.c.getString("Tournaments."+now.toString()+".Positions")
                    				.replace("%position%", 3-i+"")
                    				.replace("%player%", player)
                    				.replace("%playername%", p(player))
                    				.replace("%fishes%", TheAPI.getStringUtils().getInt(wss.getDouble(3-i))+""));
                			if(!player.equalsIgnoreCase("-"))
                    			for(String s:Loader.c.getStringList("Tournaments."+now.toString()+".Rewards."+(3-i)))
                    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Color.c(s.replace("%position%", (3-i)+"")
                        				.replace("%player%", player)
                        				.replace("%playername%", p(player))
                        				.replace("%fishes%", TheAPI.getStringUtils().getInt(wss.getDouble(3-i))+"")));
                			Loader.me.set("Players."+player+".Stats.Tournaments", 1+Loader.me.getInt("Players."+player+".Stats.Tournaments"));
        					Loader.me.set("Players."+player+".Stats.Top."+(3-i)+".Tournaments", 
        							1+Loader.me.getInt("Players."+player+".Stats.Top."+(3-i)+".Tournaments"));
                		}
    					Loader.saveChatMe();
            		break;
            		case Length:
            			for(String s:records.keySet()) {if(Bukkit.getPlayer(s)==null)records.remove(s);}
            			Ranking ws = new Ranking(records);
                		for (int i = 2; i >= 0; i--) {
                			String player = ws.getPlayer(3-i);
                			if(Bukkit.getPlayer(player)!=null) {
                            	if(inParticle(Bukkit.getPlayer(player)) && Loader.c.getBoolean("Options.BossBar.Use"))
                            		TheAPI.sendBossBar(Bukkit.getPlayer(player), Loader.c.getString("Options.BossBar.Win").replace("%type%", now.toString()).replace("%position%", 3-1+""), 1, 20);
                    			}
                			TheAPI.broadcastMessage(Loader.c.getString("Tournaments."+now.toString()+".Positions")
                    				.replace("%position%", 3-i+"")
                    				.replace("%player%", player)
                    				.replace("%playername%", p(player))
                    				.replace("%length%", String.format("%2.02f",TheAPI.getStringUtils().getDouble(ws.getDouble(3-i)))).replace(",", "."));
                    		if(!player.equalsIgnoreCase("-"))
                			for(String s:Loader.c.getStringList("Tournaments."+now.toString()+".Rewards."+(3-i)))
                    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Color.c(s.replace("%position%", (3-i)+"")
                        				.replace("%player%", player)
                        				.replace("%playername%", p(player))
                        				.replace("%length%", String.format("%2.02f",TheAPI.getStringUtils().getDouble(ws.getDouble(3-i))).replace(",", "."))));
                			Loader.me.set("Players."+player+".Stats.Tournaments", 1+Loader.me.getInt("Players."+player+".Stats.Tournaments"));
        					Loader.me.set("Players."+player+".Stats.Top."+(3-i)+".Tournaments", 
        							1+Loader.me.getInt("Players."+player+".Stats.Top."+(3-i)+".Tournaments"));
                		}
    					Loader.saveChatMe();
            		break;
            		default:
            			break;
                 }
                records.clear();
                fishes.clear();
            	now=null;
                count=0;
                save=0;
                }
           }
       }, 20,20);
	}
	static String p(String s) {
		try {
		if(Bukkit.getPlayer(s)!=null)return Bukkit.getPlayer(s).getDisplayName();
		return s;
		}catch(Exception e) {
			return "-";
		}
	}
}

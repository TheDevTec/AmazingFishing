package me.devtec.amazingfishing.utils.tournament;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.placeholderapi.PlaceholderAPI;
import me.devtec.theapi.scheduler.Scheduler;
import me.devtec.theapi.scheduler.Tasker;
import me.devtec.theapi.sortedmap.RankingAPI;
import me.devtec.theapi.utils.StringUtils;

public class Tournament {
	private final int task;
	private long runOut,total;
	private final Map<Player, Double> values = new HashMap<>();
	private final TournamentType t;
	public Tournament(TournamentType type, long time) {
		t=type;
		total=time;
		runOut = time;
		task = new Tasker() {
			public void run() {
				if(runOut-- <= 0) {
					cancel();
					stop(true);
					return;
				}else {
					if(Loader.config.getBoolean("Tournament."+t.configPath()+".Bossbar.Use"))
						for(Player p : values.keySet())
						TheAPI.sendBossBar(p, replace(Loader.config.getString("Tournament."+t.configPath()+".Bossbar.Text"),p), StringUtils.calculate(replace(Loader.config.getString("Tournament."+t.configPath()+".Bossbar.Counter"),p)).doubleValue());
					if(Loader.config.getBoolean("Tournament."+t.configPath()+".Actionbar.Use"))
						for(Player p : values.keySet())
						TheAPI.sendActionBar(p, replace(Loader.config.getString("Tournament."+t.configPath()+".Actionbar.Text"),p));
				}
			}
		}.runRepeating(0, 20);
	}
	
	private String replace(String s, Player p) {
		return PlaceholderAPI.setPlaceholders(p, s.replace("%value%", values.get(p)+"").replace("%type%", t.formatted()).replace("%time%", StringUtils.timeToString(runOut)).replace("%formatted_time%", total+"").replace("%remaining%", runOut+""));
	}
	
	public long getTime() {
		return runOut;
	}
	
	public TournamentType getType() {
		return t;
	}
	
	public Set<Player> getPlayers() {
		return values.keySet();
	}
	
	public double getValue(Player player) {
		return values.get(player);
	}
	
	public Set<Entry<Player,Double>> getValues() {
		return values.entrySet();
	}
	
	public void stop(boolean giveRewards) {
		Scheduler.cancelTask(task);
		if(Loader.config.getBoolean("Tournament."+t.configPath()+".Bossbar.Use"))
			for(Player p : values.keySet())
			TheAPI.removeBossBar(p);
		if(Loader.config.getBoolean("Tournament."+t.configPath()+".Actionbar.Use"))
			for(Player p : values.keySet())
			TheAPI.removeActionBar(p);
		RankingAPI<Player, Double> top = new RankingAPI<>(values);
		if(giveRewards) {
			int pos = 1;
			for(Entry<Player, Double> d : top.entrySet()) {
				if(pos++==Loader.config.getInt("Tournament."+t.configPath()+".Positions")+1)break;
				for(String cmd : Loader.config.getStringList("Tournament."+t.configPath()+"."+pos+".Commands"))
					TheAPI.sudoConsole(replace(cmd,d.getKey()));
				for(String msg : Loader.config.getStringList("Tournament."+t.configPath()+"."+pos+".Messages"))
					TheAPI.msg(replace(msg,d.getKey()),d.getKey());
			}
		}else {
			for(Entry<Player, Double> d : top.entrySet()) {
				for(String cmd : Loader.config.getStringList("Tournament."+t.configPath()+".Stop.Messages"))
					TheAPI.msg(replace(cmd,d.getKey()),d.getKey());
				for(String cmd : Loader.config.getStringList("Tournament."+t.configPath()+".Stop.Commands"))
					TheAPI.sudoConsole(replace(cmd,d.getKey()));
			}
		}
	}

	public void catchFish(Player p, Fish f, double weight, double length) {
		if(!values.containsKey(p)) {
			for(String msg : Loader.config.getStringList("Tournament."+t.configPath()+".Participated.Commands"))
				TheAPI.sudoConsole(PlaceholderAPI.setPlaceholders(p, msg.replace("%type%", t.formatted())));
			for(String msg : Loader.config.getStringList("Tournament."+t.configPath()+".Participated.Messages"))
				TheAPI.msg(PlaceholderAPI.setPlaceholders(p, msg.replace("%type%", t.formatted())), p);
		}
		switch(t) {
		case AMOUNT:
			if(!values.containsKey(p))
			values.put(p, values.getOrDefault(p, 0.0)+1);
			break;
		case LENGTH:
			if(values.getOrDefault(p, 0.0)<length)
				values.put(p, length);
			break;
		case WEIGHT:
			if(values.getOrDefault(p, 0.0)<weight)
				values.put(p, weight);
			break;
		case TOTAL_LENGTH:
			values.put(p, values.getOrDefault(p, 0.0)+length);
			break;
		case TOTAL_WEIGHT:
			values.put(p, values.getOrDefault(p, 0.0)+weight);
			break;
		default:
			break;
		}
	}
}
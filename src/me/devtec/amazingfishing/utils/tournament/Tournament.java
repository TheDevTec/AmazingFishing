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

public class Tournament {
	private final int task;
	private long runOut;
	private final Map<Player, Double> values = new HashMap<>();
	private final TournamentType t;
	public Tournament(TournamentType type, long time) {
		t=type;
		runOut = time;
		task = new Tasker() {
			public void run() {
				if(runOut-- <= 0) {
					cancel();
					stop(true);
					return;
				}
			}
		}.runRepeating(0, 20);
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
		RankingAPI<Player, Double> top = new RankingAPI<>(values);
		if(giveRewards) {
			int pos = 1;
			for(Entry<Player, Double> d : top.entrySet()) {
				if(pos++==Loader.config.getInt("Tournament.Positions")+1)break;
				Player player = d.getKey();
				double value = d.getValue();
				for(String cmd : Loader.config.getStringList("Tournament."+t.configPath()+"."+pos+".Commands"))
					TheAPI.sudoConsole(PlaceholderAPI.setPlaceholders(player, cmd.replace("%value%", value+"").replace("%type%", t.formatted())));
				for(String msg : Loader.config.getStringList("Tournament."+t.configPath()+"."+pos+".Messages"))
					TheAPI.msg(PlaceholderAPI.setPlaceholders(player, msg.replace("%value%", value+"").replace("%type%", t.formatted())), player);
			}
		}else {
			for(String cmd : Loader.config.getStringList("Tournament."+t.configPath()+".Stop"))
				TheAPI.sudoConsole(PlaceholderAPI.setPlaceholders(null, cmd.replace("%type%", t.formatted())));
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
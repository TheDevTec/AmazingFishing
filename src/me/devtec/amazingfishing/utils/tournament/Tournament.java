package me.devtec.amazingfishing.utils.tournament;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
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
import me.devtec.theapi.utils.nms.NMSAPI;

public class Tournament {
	private static Random r = new Random();
	
	private final int task;
	private long runOut,total;
	private final Map<Player, Double> values = new HashMap<>();
	private final TournamentType t;
	public Tournament(TournamentType type, long time) {
		if(type==TournamentType.RANDOM) {
			type=TournamentType.values()[r.nextInt(TournamentType.values().length)];
		}
		t=type;
		total=time;
		runOut = time;
		task = new Tasker() {
			public void run() {
				if(runOut-- <= 0) {
					stop(true);
					return;
				}else {
					if(Loader.config.getBoolean("Tournament.Type."+t.configPath()+".Bossbar.Use"))
						for(Player p : values.keySet())
							TheAPI.sendBossBar(p, replace(Loader.config.getString("Tournament.Type."+t.configPath()+".Bossbar.Text"),p), StringUtils.calculate(replace(Loader.config.getString("Tournament.Type."+t.configPath()+".Bossbar.Counter"),p)).doubleValue());
						
						if(Loader.config.getBoolean("Tournament.Type."+t.configPath()+".Actionbar.Use"))
						for(Player p : values.keySet())
						TheAPI.sendActionBar(p, replace(Loader.config.getString("Tournament.Type."+t.configPath()+".Actionbar.Text"),p));
					}
			}
		}.runRepeating(0, 20);
	}
	
	private String replace(String s, Player p) {
		if(p!=null) {
			s=s.replace("%value%", values.getOrDefault(p,0.0)+"").replace("%player%", p.getName())
					.replace("%playername%", p.getDisplayName()+"")
					.replace("%displayname%", p.getDisplayName()+"")
					.replace("%customanem%", p.getCustomName()+"");
		}
		return PlaceholderAPI.setPlaceholders(p, s
				.replace("%type%", t.formatted()+"").
				replace("%time%", total+"")
				.replace("%participants%", values.size()+"")
				.replace("%formatted_time%", StringUtils.timeToString(runOut))
				.replace("%remaining%", runOut+""));
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
		if(Loader.config.getBoolean("Tournament.Type."+t.configPath()+".Bossbar.Use"))
			for(Player p : values.keySet())
				TheAPI.removeBossBar(p);
		if(Loader.config.getBoolean("Tournament.Type."+t.configPath()+".Actionbar.Use"))
			for(Player p : values.keySet())
				TheAPI.removeActionBar(p);
		RankingAPI<Player, Double> top = new RankingAPI<>(values);
		if(giveRewards) {
			int pos = 0;
			String f = PlaceholderAPI.setPlaceholders(null,Loader.config.getString("Tournament.Type."+t.configPath()+".Positions").replace("%participants%", values.size()+""));
			int wins = StringUtils.calculate(f).intValue();
			for(Entry<Player, Double> d : top.entrySet()) {
				++pos;
				if(pos>wins) {
					for(String cmd : Loader.config.getStringList("Tournament.Type."+t.configPath()+".Position.Other.Commands")) {
						String cfmd=replace(cmd.replace("%player%", d.getKey().getName())
								.replace("%playername%", d.getKey().getDisplayName()+"")
								.replace("%displayname%", d.getKey().getDisplayName()+"")
								.replace("%customname%", d.getKey().getCustomName()+""),d.getKey()).replace("%position%", pos+"");
						NMSAPI.postToMainThread(new Runnable() {
							public void run() {
								TheAPI.sudoConsole(cfmd);
							}
						});
					}
					for(String msg : Loader.config.getStringList("Tournament.Type."+t.configPath()+".Position.Other.Messages")) {
						msg=replace(msg.replace("%player%", d.getKey().getName())
								.replace("%playername%", d.getKey().getDisplayName()+"")
								.replace("%displayname%", d.getKey().getDisplayName()+"")
								.replace("%customname%", d.getKey().getCustomName()+""),d.getKey()).replace("%position%", pos+"");
						for(Player p : values.keySet())
						TheAPI.msg(msg,p);
					}
					continue;
				}
				for(String cmd : Loader.config.getStringList("Tournament.Type."+t.configPath()+".Position."+pos+".Commands")) {
					String cfmd=replace(cmd.replace("%player%", d.getKey().getName())
							.replace("%playername%", d.getKey().getDisplayName()+"")
							.replace("%displayname%", d.getKey().getDisplayName()+"")
							.replace("%customname%", d.getKey().getCustomName()+""),d.getKey()).replace("%position%", pos+"");
					NMSAPI.postToMainThread(new Runnable() {
						public void run() {
							TheAPI.sudoConsole(cfmd);
						}
					});
				}
				for(String msg : Loader.config.getStringList("Tournament.Type."+t.configPath()+".Position."+pos+".Messages")) {
					msg=replace(msg.replace("%player%", d.getKey().getName())
							.replace("%playername%", d.getKey().getDisplayName()+"")
							.replace("%displayname%", d.getKey().getDisplayName()+"")
							.replace("%customname%", d.getKey().getCustomName()+""),d.getKey()).replace("%position%", pos+"");
					for(Player p : values.keySet())
					TheAPI.msg(msg,p);
				}
			}
		}else {
			for(Entry<Player, Double> d : top.entrySet()) {
				for(String cmd : Loader.config.getStringList("Tournament.Type."+t.configPath()+".Stop.Messages"))
					TheAPI.msg(replace(cmd,d.getKey()),d.getKey());
				NMSAPI.postToMainThread(new Runnable() {
					public void run() {
						for(String cmd : Loader.config.getStringList("Tournament.Type."+t.configPath()+".Stop.Commands"))
							TheAPI.sudoConsole(replace(cmd,d.getKey()));
					}
				});
			}
		}
		TournamentManager.remove(this);
		Scheduler.cancelTask(task);
	}

	public void catchFish(Player p, Fish f, double weight, double length) {
		if(!values.containsKey(p)) {
			NMSAPI.postToMainThread(new Runnable() {
				public void run() {
					for(String msg : Loader.config.getStringList("Tournament.Type."+t.configPath()+".Participated.Commands"))
						TheAPI.sudoConsole(replace(msg,p));
				}
			});
			for(String msg : Loader.config.getStringList("Tournament.Type."+t.configPath()+".Participated.Messages"))
				TheAPI.msg(replace(msg,p), p);
		}
		switch(t) {
		case AMOUNT:
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
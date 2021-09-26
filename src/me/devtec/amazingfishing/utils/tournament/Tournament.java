package me.devtec.amazingfishing.utils.tournament;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.utils.Statistics;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.placeholderapi.PlaceholderAPI;
import me.devtec.theapi.scheduler.Scheduler;
import me.devtec.theapi.scheduler.Tasker;
import me.devtec.theapi.sortedmap.RankingAPI;
import me.devtec.theapi.sortedmap.SortedMap.ComparableObject;
import me.devtec.theapi.utils.StringUtils;
import me.devtec.theapi.utils.nms.NMSAPI;

public class Tournament {
	private static Random r = new Random();
	
	private final int task;
	private long runOut,total;
	private final Map<Player, Double> values = new HashMap<>();
	private final TournamentType t;
	public Tournament(TournamentType type, long time, World world) {
		if(type==TournamentType.RANDOM) {
			type=TournamentType.values()[r.nextInt(TournamentType.values().length)];
		}
		t=type;
		total=time;
		
		for(Player p : TheAPI.getOnlinePlayers()) {
			if(world!=null)
				if(world != p.getWorld())
					continue;
			NMSAPI.postToMainThread(new Runnable() {
				public void run() {
					for(String cmd : Loader.config.getStringList("Tournament.Type."+t.configPath()+".Start.Commands"))
						TheAPI.sudoConsole(replace(cmd,p));
				}
			});
			for(String msg : Loader.config.getStringList("Tournament.Type."+t.configPath()+".Start.Messages"))
				TheAPI.msg(replace(msg.replace("%time%", StringUtils.setTimeToString(time)) ,p), p);
		}
		
		runOut = time;
		task = new Tasker() {
			public void run() {
				if(runOut-- <= 0) {
					stop(true);
					return;
				}else {
					if(Loader.config.getBoolean("Tournament.Type."+t.configPath()+".Bossbar.Use"))
						for(Player p : values.keySet())
							TheAPI.sendBossBar(p, replace(Loader.config.getString("Tournament.Type."+t.configPath()+".Bossbar.Text"),p), StringUtils.calculate(replace(Loader.config.getString("Tournament.Type."+t.configPath()+".Bossbar.Counter"),p)));
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
			int wins = (int) StringUtils.calculate(f);
			for(ComparableObject<Player, Double> d : top.all()) {
				++pos;
				Statistics.addTournamentData(d.getKey(), getType(), pos);
				if(pos>wins) {
					for(String cmd : Loader.config.getStringList("Tournament.Type."+t.configPath()+".Position.Other.Commands")) {
						String cfmd=replace(cmd.replace("%player%", d.getKey().getName())
								.replace("%playername%", d.getKey().getDisplayName()+"")
								.replace("%displayname%", d.getKey().getDisplayName()+"")
								.replace("%customname%", d.getKey().getCustomName()+""),d.getKey()).replace("%position%", pos+"");
						NMSAPI.postToMainThread(() -> {
								TheAPI.sudoConsole(cfmd);
							});
					}
					for(String msg : Loader.config.getStringList("Tournament.Type."+t.configPath()+".Position.Other.Messages")) {
						msg=replace(msg.replace("%player%", d.getKey().getName())
								.replace("%playername%", d.getKey().getDisplayName()+"")
								.replace("%displayname%", d.getKey().getDisplayName()+"")
								.replace("%customname%", d.getKey().getCustomName()+""),d.getKey()).replace("%position%", pos+"")
								.replace("%top1_name%", top.get(1).getKey().getName())
								.replace("%top1_displayname%", top.get(1).getKey().getDisplayName())
								.replace("%top1_value%",""+top.get(1).getValue())
								.replace("%top2_name%", top.get(2)!=null ? top.get(2).getKey().getName() : "-")
								.replace("%top2_displayname%", top.get(2)!=null ? top.get(2).getKey().getDisplayName() : "-")
								.replace("%top2_value%", top.get(2)!=null ? ""+top.get(2).getValue() : "-")
								.replace("%top3_name%", top.get(3)!=null ? top.get(3).getKey().getName() : "-")
								.replace("%top3_displayname%", top.get(3)!=null ? top.get(3).getKey().getDisplayName() : "-")
								.replace("%top3_value%", top.get(3)!=null ? ""+top.get(3).getValue() : "-")
								.replace("%top4_name%", top.get(4)!=null ? top.get(4).getKey().getName() : "-")
								.replace("%top4_displayname%", top.get(4)!=null?top.get(4).getKey().getDisplayName():"-")
								.replace("%top4_value%", top.get(4)!=null?""+top.get(4).getValue():"-");
						TheAPI.msg(msg,d.getKey());
					}
					continue;
				}
				for(String cmd : Loader.config.getStringList("Tournament.Type."+t.configPath()+".Position."+pos+".Commands")) {
					String cfmd=replace(cmd.replace("%player%", d.getKey().getName())
							.replace("%playername%", d.getKey().getDisplayName()+"")
							.replace("%displayname%", d.getKey().getDisplayName()+"")
							.replace("%customname%", d.getKey().getCustomName()+""),d.getKey()).replace("%position%", pos+"");
					NMSAPI.postToMainThread(() -> {
							TheAPI.sudoConsole(cfmd);
						});
				}
				for(String msg : Loader.config.getStringList("Tournament.Type."+t.configPath()+".Position."+pos+".Messages")) {
					msg=replace(msg.replace("%player%", d.getKey().getName())
							.replace("%playername%", d.getKey().getDisplayName()+"")
							.replace("%displayname%", d.getKey().getDisplayName()+"")
							.replace("%customname%", d.getKey().getCustomName()+""),d.getKey()).replace("%position%", pos+"")
							.replace("%top1_name%", top.get(1).getKey().getName())
							.replace("%top1_displayname%", top.get(1).getKey().getDisplayName())
							.replace("%top1_value%", ""+top.get(1).getValue())
							.replace("%top2_name%", top.get(2)!=null ? top.get(2).getKey().getName() : "-")
							.replace("%top2_displayname%", top.get(2)!=null ? top.get(2).getKey().getDisplayName() : "-")
							.replace("%top2_value%", top.get(2)!=null ? ""+top.get(2).getValue() : "-")
							.replace("%top3_name%", top.get(3)!=null ? top.get(3).getKey().getName() : "-")
							.replace("%top3_displayname%", top.get(3)!=null ? top.get(3).getKey().getDisplayName() : "-")
							.replace("%top3_value%", top.get(3)!=null ? ""+top.get(3).getValue() : "-")
							.replace("%top4_name%", top.get(4)!=null ? top.get(4).getKey().getName() : "-")
							.replace("%top4_displayname%", top.get(4)!=null?top.get(4).getKey().getDisplayName():"-")
							.replace("%top4_value%", top.get(4)!=null?""+top.get(4).getValue():"-");
					TheAPI.msg(msg,d.getKey());
				}
			}
		}else {
			for(ComparableObject<Player, Double> d : top.all()) {
				for(String cmd : Loader.config.getStringList("Tournament.Type."+t.configPath()+".Stop.Messages"))
					TheAPI.msg(replace(cmd,d.getKey()),d.getKey());
				NMSAPI.postToMainThread(() -> {
						for(String cmd : Loader.config.getStringList("Tournament.Type."+t.configPath()+".Stop.Commands"))
							TheAPI.sudoConsole(replace(cmd,d.getKey()));
					});
			}
		}
		TournamentManager.remove(this);
		Scheduler.cancelTask(task);
	}

	public void catchFish(Player p, Fish f, double weight, double length) {
		if(!values.containsKey(p)) {
			NMSAPI.postToMainThread(() -> {
					for(String cmd : Loader.config.getStringList("Tournament.Type."+t.configPath()+".Participated.Commands"))
						TheAPI.sudoConsole(replace(cmd,p));
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
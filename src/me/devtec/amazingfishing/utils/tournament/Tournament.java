package me.devtec.amazingfishing.utils.tournament;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.utils.Statistics;
import me.devtec.amazingfishing.utils.tournament.bossbar.BossBarManager;
import me.devtec.amazingfishing.utils.tournament.bossbar.SBossBar;
import me.devtec.shared.placeholders.PlaceholderAPI;
import me.devtec.shared.scheduler.Scheduler;
import me.devtec.shared.scheduler.Tasker;
import me.devtec.shared.sorting.SortingAPI;
import me.devtec.shared.sorting.SortingAPI.ComparableObject;
import me.devtec.shared.utility.StringUtils;
import me.devtec.theapi.bukkit.BukkitLoader;
import me.devtec.theapi.bukkit.nms.NmsProvider.TitleAction;

public class Tournament {
	private static Random r = new Random();

	private final int task;
	private long runOut, total;
	private final Map<Player, Double> values = new ConcurrentHashMap<>();
	private final TournamentType t;

	public Tournament(TournamentType type, long time, World world) {
		while (type == TournamentType.RANDOM)
			type = TournamentType.values()[Tournament.r.nextInt(TournamentType.values().length)];
		t = type;
		total = time;

		for (Player p : BukkitLoader.getOnlinePlayers()) {
			if (world != null)
				if (world != p.getWorld())
					continue;
			BukkitLoader.getNmsProvider().postToMainThread(() -> {
				for (String cmd : Loader.config.getStringList("Tournament.Type." + t.configPath() + ".Start.Commands"))
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replace(cmd, p));
			});
			for (String msg : Loader.config.getStringList("Tournament.Type." + t.configPath() + ".Start.Messages"))
				Loader.msg(replace(msg.replace("%time%", StringUtils.timeToString(time)), p), p);
		}

		runOut = time;
		task = new Tasker() {
			@Override
			public void run() {
				if (runOut-- <= 0)
					BukkitLoader.getNmsProvider().postToMainThread(() -> {
						stop(true);
					});
				if (Loader.config.getBoolean("Tournament.Type." + t.configPath() + ".Bossbar.Use"))
					for (Player p : values.keySet()) {
						BukkitLoader.getNmsProvider().postToMainThread(() -> {
						SBossBar bar = BossBarManager.getOrCreate(p);
						bar.show();
						bar.setProgress(StringUtils.calculate(replace(Loader.config.getString("Tournament.Type." + t.configPath() + ".Bossbar.Counter"), p)));
						bar.setTitle(replace(Loader.config.getString("Tournament.Type." + t.configPath() + ".Bossbar.Text"), p));
						});
					}
				if (Loader.config.getBoolean("Tournament.Type." + t.configPath() + ".Actionbar.Use"))
					for (Player p : values.keySet()) {
						BukkitLoader.getNmsProvider().postToMainThread(() -> {
							BukkitLoader.getPacketHandler().send(p,
								BukkitLoader.getNmsProvider().packetTitle(TitleAction.ACTIONBAR, replace(Loader.config.getString("Tournament.Type." + t.configPath() + ".Actionbar.Text"), p)));
						});
					}
			}
		}.runRepeating(0, 20);
	}

	private String replace(String s, Player p) {
		if (p != null)
			s = s.replace("%value%", values.getOrDefault(p, 0.0) + "").replace("%player%", p.getName()).replace("%playername%", p.getDisplayName() + "")
					.replace("%displayname%", p.getDisplayName() + "").replace("%customanem%", p.getCustomName() + "");
		return PlaceholderAPI.apply(s.replace("%type%", t.formatted() + "").replace("%time%", total + "").replace("%participants%", values.size() + "")
				.replace("%formatted_time%", StringUtils.timeToString(runOut)).replace("%remaining%", runOut + ""), p.getUniqueId());
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

	public Set<Entry<Player, Double>> getValues() {
		return values.entrySet();
	}

	public void stop(boolean giveRewards) {
		if (Loader.config.getBoolean("Tournament.Type." + t.configPath() + ".Bossbar.Use"))
			for (Player p : values.keySet())
				BossBarManager.remove(p);
		// Map<Player, Double> att = new SortingAPI().sortByValue(values, true);
		Map<Integer, Entry<Player, Double>> top = new HashMap<>();
		int i = 0;
		for (Entry<Player, Double> data : SortingAPI.sortByValue(values, true).entrySet()) {
			top.put(i, data);
			i = i + 1;
			continue;
		}

		if (giveRewards) {
			int pos = 0;
			String f = PlaceholderAPI.apply(Loader.config.getString("Tournament.Type." + t.configPath() + ".Positions").replace("%participants%", values.size() + ""), null);
			int wins = (int) StringUtils.calculate(f);
			for (ComparableObject<Player, Double> d : SortingAPI.sortByValueArray(values, true)) {
				++pos;
				Statistics.addTournamentData(d.getKey(), getType(), pos);
				if (pos > wins) {
					for (String cmd : Loader.config.getStringList("Tournament.Type." + t.configPath() + ".Position.Other.Commands")) {
						String cfmd = replace(cmd.replace("%player%", d.getKey().getName()).replace("%playername%", d.getKey().getDisplayName() + "")
								.replace("%displayname%", d.getKey().getDisplayName() + "").replace("%customname%", d.getKey().getCustomName() + ""), d.getKey()).replace("%position%", pos + "");
						BukkitLoader.getNmsProvider().postToMainThread(() -> {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cfmd);
						});
					}
					for (String msg : Loader.config.getStringList("Tournament.Type." + t.configPath() + ".Position.Other.Messages")) {
						msg = replace(msg.replace("%player%", d.getKey().getName()).replace("%playername%", d.getKey().getDisplayName() + "").replace("%displayname%", d.getKey().getDisplayName() + "")
								.replace("%customname%", d.getKey().getCustomName() + ""), d.getKey()).replace("%position%", pos + "").replace("%top1_name%", top.get(0).getKey().getName())
								.replace("%top1_displayname%", top.get(0).getKey().getDisplayName()).replace("%top1_value%", "" + top.get(0).getValue())
								.replace("%top2_name%", top.size() >= 2 ? top.get(1).getKey().getName() : "-")
								.replace("%top2_displayname%", top.size() >= 2 ? top.get(1).getKey().getDisplayName() : "-").replace("%top2_value%", top.size() >= 2 ? "" + top.get(1).getValue() : "-")
								.replace("%top3_name%", top.size() >= 3 ? top.get(2).getKey().getName() : "-")
								.replace("%top3_displayname%", top.size() >= 3 ? top.get(2).getKey().getDisplayName() : "-").replace("%top3_value%", top.size() >= 3 ? "" + top.get(2).getValue() : "-")
								.replace("%top4_name%", top.size() >= 4 ? top.get(3).getKey().getName() : "-")
								.replace("%top4_displayname%", top.size() >= 4 ? top.get(3).getKey().getDisplayName() : "-")
								.replace("%top4_value%", top.size() >= 4 ? "" + top.get(3).getValue() : "-");
						Loader.msg(msg, d.getKey());
					}
					continue;
				}
				for (String cmd : Loader.config.getStringList("Tournament.Type." + t.configPath() + ".Position." + pos + ".Commands")) {
					String cfmd = replace(cmd.replace("%player%", d.getKey().getName()).replace("%playername%", d.getKey().getDisplayName() + "")
							.replace("%displayname%", d.getKey().getDisplayName() + "").replace("%customname%", d.getKey().getCustomName() + ""), d.getKey()).replace("%position%", pos + "");
					BukkitLoader.getNmsProvider().postToMainThread(() -> {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cfmd);
					});
				}
				for (String msg : Loader.config.getStringList("Tournament.Type." + t.configPath() + ".Position." + pos + ".Messages")) {
					msg = replace(msg.replace("%player%", d.getKey().getName()).replace("%playername%", d.getKey().getDisplayName() + "").replace("%displayname%", d.getKey().getDisplayName() + "")
							.replace("%customname%", d.getKey().getCustomName() + ""), d.getKey()).replace("%position%", pos + "").replace("%top1_name%", top.get(0).getKey().getName())
							.replace("%top1_displayname%", top.get(0).getKey().getDisplayName()).replace("%top1_value%", "" + top.get(0).getValue())
							.replace("%top2_name%", top.size() >= 2 ? top.get(1).getKey().getName() : "-").replace("%top2_displayname%", top.size() >= 2 ? top.get(1).getKey().getDisplayName() : "-")
							.replace("%top2_value%", top.size() >= 2 ? "" + top.get(1).getValue() : "-").replace("%top3_name%", top.size() >= 3 ? top.get(2).getKey().getName() : "-")
							.replace("%top3_displayname%", top.size() >= 3 ? top.get(2).getKey().getDisplayName() : "-").replace("%top3_value%", top.size() >= 3 ? "" + top.get(2).getValue() : "-")
							.replace("%top4_name%", top.size() >= 4 ? top.get(3).getKey().getName() : "-").replace("%top4_displayname%", top.size() >= 4 ? top.get(3).getKey().getDisplayName() : "-")
							.replace("%top4_value%", top.size() >= 4 ? "" + top.get(3).getValue() : "-");
					Loader.msg(msg, d.getKey());
				}
			}
		} else
			for (ComparableObject<Player, Double> d : SortingAPI.sortByValueArray(values, true)) {
				for (String cmd : Loader.config.getStringList("Tournament.Type." + t.configPath() + ".Stop.Messages"))
					Loader.msg(replace(cmd, d.getKey()), d.getKey());
				BukkitLoader.getNmsProvider().postToMainThread(() -> {
					for (String cmd : Loader.config.getStringList("Tournament.Type." + t.configPath() + ".Stop.Commands"))
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replace(cmd, d.getKey()));
				});
			}
		TournamentManager.remove(this);
		Scheduler.cancelTask(task);
	}

	public void catchFish(Player p, Fish f, double weight, double length) {
		if (!values.containsKey(p)) {
			BukkitLoader.getNmsProvider().postToMainThread(() -> {
				for (String cmd : Loader.config.getStringList("Tournament.Type." + t.configPath() + ".Participated.Commands"))
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replace(cmd, p));
			});
			for (String msg : Loader.config.getStringList("Tournament.Type." + t.configPath() + ".Participated.Messages"))
				Loader.msg(replace(msg, p), p);
		}
		switch (t) {
		case AMOUNT:
			values.put(p, values.getOrDefault(p, 0.0) + 1);
			break;
		case LENGTH:
			if (values.getOrDefault(p, 0.0) < length)
				values.put(p, length);
			break;
		case WEIGHT:
			if (values.getOrDefault(p, 0.0) < weight)
				values.put(p, weight);
			break;
		case TOTAL_LENGTH:
			values.put(p, values.getOrDefault(p, 0.0) + length);
			break;
		case TOTAL_WEIGHT:
			values.put(p, values.getOrDefault(p, 0.0) + weight);
			break;
		default:
			break;
		}
	}
}
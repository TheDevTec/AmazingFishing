package me.devtec.amazingfishing.utils.tournament.bossbar;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;

import me.devtec.shared.Ref;

public class BossBarManager {
	static Map<Player, SBossBar> bar = new ConcurrentHashMap<>();

	public static SBossBar get(Player player) {
		return bar.get(player);
	}

	public static SBossBar getOrCreate(Player player) {
		SBossBar bar = get(player);
		if (bar == null)
			BossBarManager.bar.put(player, bar = Ref.isOlderThan(9) ? new LegacyBossBar(player, "", 100) : new ModernBossBar(player, ""));
		return bar;
	}

	public static void remove(Player player) {
		SBossBar bar = BossBarManager.bar.remove(player);
		if (bar != null)
			bar.remove();
	}
}

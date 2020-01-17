package AmazingFishing;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Sounds {
	public static void play(Player p) {
			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 10);
}}

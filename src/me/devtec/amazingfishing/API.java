package me.devtec.amazingfishing;

import java.io.File;
import java.util.HashMap;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.fishing.Fish;
import me.devtec.amazingfishing.fishing.Junk;
import me.devtec.amazingfishing.player.Fisher;
import me.devtec.amazingfishing.utils.MessageUtils;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.shared.dataholder.Config;

public class API {
	
	private static HashMap<Player, Fisher> player_list = new HashMap<Player, Fisher>();
	
	private static HashMap<String, Fish> fish_list = new HashMap<String, Fish>(); //file_name | Fish
	private static HashMap<String, Junk> junk_list = new HashMap<String, Junk>(); //file_name | Junk
	
	public static void loadFishingItems() {
		File directory = new File("plugins/AmazingFishing/Fish");
		if(directory.exists() && directory.isDirectory()) {
			for(File file : directory.listFiles()) {
				Config config = new Config(file);
				if(config.getString("type").equalsIgnoreCase("FISH"))
					fish_list.put(file.getName(), new Fish(config));
				if(config.getString("type").equalsIgnoreCase("JUNK"))
					junk_list.put(file.getName(), new Junk(config));
			}
		}
		MessageUtils.msgConsole("%prefix% &fLoaded %fish% fish files and %junk% junk files.", 
				Placeholders.c().add("fish", fish_list.size()).add("junk", junk_list.size()));
	}
	
	/** Removes player from list of active fishers. Should be run when player leaves the server on when you want... your choice :D
	 * @param player The player that is leaving server.
	 */
	public static void playerQuit(Player player) {
		if(player_list.containsKey(player))
			player_list.remove(player);
	}
	
	public static Fisher getFisher(Player player) {
		Fisher fisher;
		if(player_list.containsKey(player))
			fisher = player_list.get(player);
		else
			fisher = new Fisher(player);
		return fisher;
	}
	
}

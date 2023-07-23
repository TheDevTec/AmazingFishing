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
	
	
	/*
	 *  FISH & JUNK LISTS
	 */
	
	public static void loadFishingItems() {
		File directory = new File("plugins/AmazingFishing/Fish");
		if(directory.exists() && directory.isDirectory()) {		
			for(File file : directory.listFiles()) { // loops all files in this directory
				Config config = new Config(file);
				if(config.getString("type").equalsIgnoreCase("FISH"))
					getFishList().put(file.getName(), new Fish(config)); // adds or replaces FISH in Map
				if(config.getString("type").equalsIgnoreCase("JUNK"))
					getJunkList().put(file.getName(), new Junk(config)); // adds or replaces JUNK in Map
			}
		}
		MessageUtils.msgConsole("%prefix% &fLoaded %fish% fish files and %junk% junk files.", 
				Placeholders.c().add("fish", getFishList().size()).add("junk", getJunkList().size()));
	}
	public static HashMap<String, Fish> getFishList() {
		return fish_list;
	}
	public static HashMap<String, Junk> getJunkList() {
		return junk_list;
	}
	
	
	/*
	 * PLAYER
	 */
	
	/** Removes player from list of active fishers. Should be run when player leaves the server on when you want... your choice :D
	 * @param player The player that is leaving server.
	 */
	public static void playerQuit(Player player) {
		if(getFisherList().containsKey(player))
			getFisherList().remove(player);
	}
	
	/** Gets {@link Fisher}.}
	 * @param player Online player you wan't to use.
	 * @return {@link Fisher}
	 */
	public static Fisher getFisher(Player player) {
		Fisher fisher;
		if(getFisherList().containsKey(player))
			fisher = getFisherList().get(player);
		else
			fisher = new Fisher(player);
		return fisher;
	}
	
	public static HashMap<Player, Fisher> getFisherList() {
		return player_list;
	}
	
}

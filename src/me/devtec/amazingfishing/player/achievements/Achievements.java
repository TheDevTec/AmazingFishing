package me.devtec.amazingfishing.player.achievements;

import java.io.File;
import java.util.HashMap;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.utils.MessageUtils;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.shared.dataholder.Config;

public class Achievements {

	private static HashMap<String, Achievement> achievements = new HashMap<String, Achievement>();
	
	public static void loadAchievements() {
		File directory = new File("plugins/AmazingFishing/Data/Achievements");
		if(directory.exists() && directory.isDirectory()) {		
			for(File file : directory.listFiles()) { // loops all files in this directory
				Config config = new Config(file);
				if(config.getBoolean("enabled"))
					achievements.put(file.getName().replace(".yml", ""), new Achievement(config));
			}
		}
		MessageUtils.msgConsole("%name% &fLoaded %achievements% achievements files.", 
				Placeholders.c().add("achievements", getAchievements().size()).add("name", "[AmazingFishing]"));
	}
	
	public static HashMap<String, Achievement> getAchievements(){
		return achievements;
	}
	
	public static void check(Player player) {
		for(Achievement achievement : getAchievements().values()) {
			if(!achievement.finished(player))
				if(achievement.canFinish(player))
					achievement.giveRewards(player);
					
		}
	}

	/* USER.yml
	 * 
	 * <path>:
	 *   achievements:
	 *     <achievement>:
	 *       finished: [true/false]
	 *       <stage>: [tru/false]
	 * 
	 */
	
}

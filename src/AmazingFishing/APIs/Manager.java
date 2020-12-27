package AmazingFishing.APIs;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import AmazingFishing.APIs.Enums.PlayerType;
import me.DevTec.AmazingFishing.Loader;

public class Manager {

	public static  EditorManager getInGameEditor = new EditorManager();
	public static  PlayerManager getPlayer(Player p) {
		return new PlayerManager(p);
	}
	
	public static String getPluginName() {
		return "&b&lAmazing &f&lFishing";
	}
	public static String getDiscordLink() {
		return "https://discord.gg/TtsMY5hhJM";
	}
	
	public static List<String> getPluginInfoIntoLore(PlayerType type) {
		if(type==PlayerType.Player)
			return Arrays.asList(
					"&9Version &bV"+Loader.plugin.getDescription().getVersion(),
					"&9Created by &bStraiker123",
					"&9Developed by &bHouska02");
		if(type==PlayerType.Admin)
			return Arrays.asList(
					"&9Version &bV"+Loader.plugin.getDescription().getVersion(),
					"&9Created by &bStraiker123",
					"&9Developed by &bHouska02",
					"",
					"&7For Discord link click!");
		return Arrays.asList(
				"&9Version &bV"+Loader.plugin.getDescription().getVersion(),
				"&9Created by &bStraiker123",
				"&9Developed by &bHouska02");
	}

}

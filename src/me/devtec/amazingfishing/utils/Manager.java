package me.devtec.amazingfishing.utils;

import java.util.Arrays;
import java.util.List;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.gui.Help.PlayerType;

public class Manager {

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
	public static String getDataLocation() {
		return "AmazingFishing";
	}
}

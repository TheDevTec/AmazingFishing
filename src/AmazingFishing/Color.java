package AmazingFishing;

import org.bukkit.ChatColor;

public class Color {
	public static String c(String s) {
		if(s==null)s="&4ERROR, Missing path";
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public static String r(String s) {
		if(s==null)s="";
		return ChatColor.stripColor(s);
	}
}

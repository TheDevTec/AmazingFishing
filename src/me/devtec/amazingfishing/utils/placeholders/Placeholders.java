package me.devtec.amazingfishing.utils.placeholders;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.utils.Manager;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.apis.PluginManagerAPI;

public class Placeholders {
	
    /*
	 * Placeholders:
	 * 
	 * amazingfishing_<CO>_<Poznávadlo | TYP NĚČEHO>_<POZNÁVADLO | NÁZEV>_<POZNÁVADLO>
	 * 
	 * %amazingfishing_<tournament | treasures | shop | records | fish>_
	 *
	 * %amazingfishing_tournament_<played | placements | TOURNAMENT TYPE>
	 * %amazingfishing_tournament_<TOURNAMENT>_<played | placement>%
	 *
	 * %amazingfishing_treasures_<caught | TREASURE>
	 * %amazingfishing_treasures_<TREASURE>_caught%
	 * 
	 * %amazingfishing_shop_gained_<exp | money | points>%
	 *
	 *%amazingfishing_records_<TYP RYBY: COD,...>_<jméno ryby>_<WEIGHT | LENGTH>%
	 *
	 *%amazingfishing_fish_<caught | eaten | sold>%
	 *%amazingfishing_fish_<TYP>_<caught | eaten | sold>%
	 *%amazingfishing_fish_<TYP>_<jméno ryby>_<caught | eaten | sold>%
	 */
	
	public static String getStatistics(Player player, String identifier) {
		String[] pp;
		pp=identifier.replace("amazingfishing", "").split("[_]");
		String path;
		path = Manager.getDataLocation()+".Statistics.";
		int i=-1;
		for(String s: pp) {
			++i;
			if(i==0)
				path=path+s.substring(0, 1).toUpperCase() + s.substring(1)+".";
			if(i==1) {
				if(s.equalsIgnoreCase("gained")) {
					path=path+s.substring(0, 1).toUpperCase() + s.substring(1)+".";
					continue;
				}
				if(identifier.startsWith("treasures")) {
					path=path+s+".";
					continue;
				}
				if(s.equalsIgnoreCase("played")||s.equalsIgnoreCase("placements")||s.equalsIgnoreCase("caught")
						||s.equalsIgnoreCase("eaten")||s.equalsIgnoreCase("sold"))
					path=path+s.substring(0, 1).toUpperCase() + s.substring(1);
				else
					path=path+s.toUpperCase()+".";
			}
			if(i==2) {
				if(s.equalsIgnoreCase("played")|| s.equalsIgnoreCase("placement")||s.equalsIgnoreCase("caught")||s.equalsIgnoreCase("exp")
						||s.equalsIgnoreCase("money")||s.equalsIgnoreCase("points")||s.equalsIgnoreCase("eaten")||s.equalsIgnoreCase("sold"))
					path=path+s.substring(0, 1).toUpperCase() + s.substring(1);
				else
					path=path+s+".";
					
			}
			if(i==3) {
				if(s.equalsIgnoreCase("Weight")|| s.equalsIgnoreCase("length"))
					path=path+s.toUpperCase();
				if(s.equalsIgnoreCase("caught") || s.equalsIgnoreCase("eaten")||s.equalsIgnoreCase("sold"))
					path=path+s.substring(0, 1).toUpperCase() + s.substring(1);
			}
		}
    	Bukkit.broadcastMessage(path);
		return TheAPI.getUser(player).getString(path);
	}
	
	
	
	public static boolean isEnabledPlaceholderAPI() {
		return PluginManagerAPI.getPlugin("PlaceholderAPI")!=null;
	}
	
}

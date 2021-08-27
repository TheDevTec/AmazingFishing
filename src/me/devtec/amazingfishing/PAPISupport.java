package me.devtec.amazingfishing;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.utils.placeholders.Placeholders;
import me.devtec.amazingfishing.utils.placeholders.Placeholders.TopType;
import me.devtec.theapi.placeholderapi.PlaceholderRegister;
import me.devtec.theapi.utils.StringUtils;

public class PAPISupport {
	public static void load() {
		Loader.reg=new PlaceholderRegister("amazingfishing", "DevTec", Loader.plugin.getDescription().getVersion()) {
			
		    
			public String onRequest(Player player, String identifier) {
		   	
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
		   	 *%amazingfishing_records_<TYP RYBY: COD,...>_<jméno ryby>_<Weight | lenght>%
		   	 *
		   	 *%amazingfishing_fish_<caught | eaten | sold>%
		   	 *%amazingfishing_fish_<TYP>_<caught | eaten | sold>%
		   	 *%amazingfishing_fish_<TYP>_<jméno ryby>_<caught | eaten | sold>%
		   	 */
		   	
				/*
				 * %amazingfishing_tournaments_wins_<1-4>%
				 * %amazingfishing_fish_caught_<1-4>%
				 */
		   	if(identifier.startsWith("tournaments_wins_")) {
		   		int pos = StringUtils.getInt(identifier.replace("tournaments_wins_", ""));
		   		return Placeholders.getTop(TopType.TOURNAMENTS_WINS, pos);
		   	}
		   	if(identifier.startsWith("fish_caught_")) {
		   		int pos = StringUtils.getInt(identifier.replace("fish_caught_", ""));
		   		return Placeholders.getTop(TopType.FISH_CAUGHT, pos);
		   	}
		       /*
		       Check if the player is online,
		       You should do this before doing anything regarding players
		        */
		       if(player == null){
		           return null;
		       }    	
		       if(identifier.startsWith("tournament")|| identifier.startsWith("treasures") || identifier.startsWith("shop") || identifier.startsWith("records")
		       		|| identifier.startsWith("fish")) {
		       	return Placeholders.getStatistics(player, identifier);
		       }


		       return null;
			}
		};
		Loader.reg.register();
	}
}

package me.devtec.amazingfishing.utils.placeholders;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.fishing.FishingItem;
import me.devtec.amazingfishing.fishing.enums.FishRecord;
import me.devtec.amazingfishing.fishing.enums.FishType;
import me.devtec.amazingfishing.fishing.enums.ItemAction;
import me.devtec.amazingfishing.player.Statistics;
import me.devtec.amazingfishing.player.Statistics.GainedType;
import me.devtec.amazingfishing.player.Statistics.LoadDataType;
import me.devtec.shared.placeholders.PlaceholderExpansion;
import me.devtec.shared.utility.StringUtils;
import me.devtec.shared.utility.StringUtils.FormatType;

public class PlaceholderLoader {

	/*
	 * LOADING PART
	 */
	
	public static PlaceholderExpansion holder;
	public static String placeholder_prefix = "amazingfishing";
	public static void load() {
		// TheAPI PlaceholderExpansion loading
		holder = loadApiPAPI();
		holder.register();
	}

	private static PlaceholderExpansion loadApiPAPI() {
		return new PlaceholderExpansion(placeholder_prefix) {
			@Override
			public String apply(String identifier, UUID playerId) {

				/*
				 * %tournaments_wins_<1-4>% %fish_caught_<1-4>%
				 */
				/*if (identifier.startsWith("tournaments_wins_")) {
					int pos = ParseUtils.getInt(identifier.replace("tournaments_wins_", ""));
					return Placeholders.getTop(TopType.TOURNAMENTS_WINS, pos);
				}
				if (identifier.startsWith("fish_caught_")) {
					int pos = ParseUtils.getInt(identifier.replace("fish_caught_", ""));
					return Placeholders.getTop(TopType.FISH_CAUGHT, pos);
				}*/
				/*
				 * Check if the player is online, You should do this before doing anything
				 * regarding players
				 */
				if (playerId == null)
					return null;
				
				if (/*identifier.startsWith("tournament") || identifier.startsWith("treasures") || */
						identifier.startsWith("gained") || 
						identifier.startsWith("records") || 
						identifier.startsWith("stats"))
					return PlaceholderLoader.getStatistics(Bukkit.getPlayer(playerId), identifier);

				return null;
			}
		};
	}
	
	/*
	 * %amazingfishing_....%
	 * _stats_<caught | ate | sold>
	 * _stats_<FISH TYPE>_<caught | ate | sold>
	 * _stats_<FISH TYPE>_<fish file>_<caught | ate | sold>
	 * 
	 * _gained_<exps | money | points>_[f | format]
	 * 
	 * _records_<fish file>_<WEIGHT | LENGTH>_[f | format]
	 * 
	 * 
	 * [] - not needed
	 */
	
	public static String getStatistics(Player player, String identifier) {
		
		//removing amazingfishing_ prefix
		if(identifier.startsWith(placeholder_prefix+"_"))
			identifier = identifier.substring(placeholder_prefix.length()+1);
		
		String[] pp;
		pp = identifier.split("[_]");
		
		if(pp == null)
			return "0";
		
		//remaining: stats_...
		if(pp[0].equalsIgnoreCase("stats")) {
			LoadDataType dataType = LoadDataType.GLOBAL;
			ItemAction action = null;
			FishType type = null;
			String fishFile = null;
			
			if(pp.length == 2)
				action = ItemAction.value(pp[1]);
			if(pp.length == 3) {
				type = FishType.value(pp[1]);
				action = ItemAction.value(pp[2]);
				dataType = LoadDataType.PER_TYPE;
			}
			if(pp.length == 4) {
				type = FishType.value(pp[1]);
				fishFile = pp[2];
				action = ItemAction.value(pp[3]);
				dataType = LoadDataType.PER_FISH;
			}
			if(action != null)
				return Statistics.getFishingStat(player, fishFile, type, action, dataType)+"";
		}
		//remaining: gained_...
		else if(pp[0].equalsIgnoreCase("gained") && pp.length>=2) {
			GainedType type = GainedType.value(pp[1]);
			if(type != null && pp.length==2)
				return Statistics.getGainedEarnings(player, type)+"";
			if(type != null && (pp[2].equalsIgnoreCase("format") || pp[2].equalsIgnoreCase("f")))
				return StringUtils.formatDouble(FormatType.BASIC, Statistics.getGainedEarnings(player, type))+"";
					
		}
		//remaining: records_<fish file>_<WEIGHT | LENGTH>_format
		else if(pp[0].equalsIgnoreCase("records") && pp.length >= 3) {
			FishingItem item = API.getFishingItem(pp[1]);
			FishRecord record = FishRecord.value(pp[2]);
			if(item != null && pp.length==3)
				return Statistics.getRecord(player, item, record)+"";
			if(item != null && (pp[3].equalsIgnoreCase("format") || pp[3].equalsIgnoreCase("f")))
				return StringUtils.formatDouble(FormatType.BASIC, Statistics.getRecord(player, item, record))+"";
		}
		
		
		return "0";
	}
	
	public static void main(String[] args) {
		String a = "amazingfishing_stats_ate";
		String b = "amazingfishing_stats_FISH_caught";
		String c = "amazingfishing_stats_JUNK_stick_sold";
		getStatistics(null, a);
		getStatistics(null, b);
		getStatistics(null, c);
		
	}
}

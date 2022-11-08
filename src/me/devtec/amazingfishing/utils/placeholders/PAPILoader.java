package me.devtec.amazingfishing.utils.placeholders;

import java.util.UUID;

import org.bukkit.Bukkit;

import me.devtec.amazingfishing.utils.placeholders.Placeholders.TopType;
import me.devtec.shared.placeholders.PlaceholderExpansion;
import me.devtec.shared.utility.StringUtils;

public class PAPILoader {

	/*
	 * Placeholders:
	 * 
	 * <CO>_<Poznávadlo | TYP NĚČEHO>_<POZNÁVADLO | NÁZEV>_<POZNÁVADLO>
	 * 
	 * %<tournament | treasures | shop | records | fish>_
	 *
	 * %tournament_<played | placements | TOURNAMENT TYPE>
	 * %tournament_<TOURNAMENT>_<played | placement>%
	 *
	 * %treasures_<caught | TREASURE> %treasures_<TREASURE>_caught%
	 * 
	 * %shop_gained_<exp | money | points>%
	 *
	 * %records_<TYP RYBY: COD,...>_<jméno ryby>_<Weight | lenght>%
	 *
	 * %fish_<caught | eaten | sold>% %fish_<TYP>_<caught | eaten | sold>%
	 * %fish_<TYP>_<jméno ryby>_<caught | eaten | sold>%
	 */

	public static PlaceholderExpansion papi_theapi;

	public static void load() {
		// TheAPI PlaceholderExpansion loading
		papi_theapi = loadApiPAPI();
		papi_theapi.register();

		// PlaceholderAPI PlaceholderExpansion loading
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
		}

	}

	private static PlaceholderExpansion loadApiPAPI() {
		return new PlaceholderExpansion("amazingfishing") {
			@Override
			public String apply(String identifier, UUID playerId) {

				/*
				 * %tournaments_wins_<1-4>% %fish_caught_<1-4>%
				 */
				if (identifier.startsWith("tournaments_wins_")) {
					int pos = StringUtils.getInt(identifier.replace("tournaments_wins_", ""));
					return Placeholders.getTop(TopType.TOURNAMENTS_WINS, pos);
				}
				if (identifier.startsWith("fish_caught_")) {
					int pos = StringUtils.getInt(identifier.replace("fish_caught_", ""));
					return Placeholders.getTop(TopType.FISH_CAUGHT, pos);
				}
				/*
				 * Check if the player is online, You should do this before doing anything
				 * regarding players
				 */
				if (playerId == null)
					return null;
				if (identifier.startsWith("tournament") || identifier.startsWith("treasures") || identifier.startsWith("shop") || identifier.startsWith("records") || identifier.startsWith("fish"))
					return Placeholders.getStatistics(Bukkit.getPlayer(playerId), identifier);

				return null;
			}
		};
	}
}

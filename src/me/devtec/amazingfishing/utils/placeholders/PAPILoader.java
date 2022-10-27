package me.devtec.amazingfishing.utils.placeholders;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.utils.placeholders.Placeholders.TopType;
import me.devtec.shared.placeholders.PlaceholderExpansion;
import me.devtec.shared.utility.StringUtils;

public class PAPILoader {


	/*
	 * Placeholders:
	 * 
	 * amazingfishing_<CO>_<Poznávadlo | TYP NĚČEHO>_<POZNÁVADLO |
	 * NÁZEV>_<POZNÁVADLO>
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
	 * %amazingfishing_records_<TYP RYBY: COD,...>_<jméno ryby>_<Weight | lenght>%
	 *
	 * %amazingfishing_fish_<caught | eaten | sold>%
	 * %amazingfishing_fish_<TYP>_<caught | eaten | sold>%
	 * %amazingfishing_fish_<TYP>_<jméno ryby>_<caught | eaten | sold>%
	 */
	

	public static PlaceholderExpansion papi_theapi;
	public static Object papi_papi;
	
	public static void load() {
		//TheAPI PlaceholderExpansion loading
		papi_theapi = loadApiPAPI();
		papi_theapi.register();
		

		//PlaceholderAPI PlaceholderExpansion loading
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			papi_papi = loadPapiPAIP();
			((me.clip.placeholderapi.expansion.PlaceholderExpansion) papi_papi).register();
		}
		
	}
	
	private static PlaceholderExpansion loadApiPAPI() {
		return new PlaceholderExpansion("amazingfishing") {
			@Override
			public String apply(String identifier, UUID playerId) {

				/*
				 * %amazingfishing_tournaments_wins_<1-4>% %amazingfishing_fish_caught_<1-4>%
				 */
				if (identifier.startsWith("amazingfishing_tournaments_wins_")) {
					int pos = StringUtils.getInt(identifier.replace("tournaments_wins_", ""));
					return Placeholders.getTop(TopType.TOURNAMENTS_WINS, pos);
				}
				if (identifier.startsWith("amazingfishing_fish_caught_")) {
					int pos = StringUtils.getInt(identifier.replace("fish_caught_", ""));
					return Placeholders.getTop(TopType.FISH_CAUGHT, pos);
				}
				/*
				 * Check if the player is online, You should do this before doing anything
				 * regarding players
				 */
				if (playerId == null)
					return null;
				if (identifier.startsWith("amazingfishing_tournament") || identifier.startsWith("amazingfishing_treasures") || identifier.startsWith("amazingfishing_shop")
						|| identifier.startsWith("amazingfishing_records") || identifier.startsWith("amazingfishing_fish"))
					return Placeholders.getStatistics(Bukkit.getPlayer(playerId), identifier);

				return null;
			}
		};
	}
	private static me.clip.placeholderapi.expansion.PlaceholderExpansion loadPapiPAIP() {
		return new me.clip.placeholderapi.expansion.PlaceholderExpansion() {

			@Override
			public String onRequest(OfflinePlayer player, String params) {

				/*
				 * %amazingfishing_tournaments_wins_<1-4>% %amazingfishing_fish_caught_<1-4>%
				 */
				
				if (params.startsWith("tournaments_wins_")) { //PAPI nepotřebuje amazingfishing_ nazačátku!!!!
					int pos = StringUtils.getInt(params.replace("tournaments_wins_", ""));
					return Placeholders.getTop(TopType.TOURNAMENTS_WINS, pos);
				}
				if (params.startsWith("fish_caught_")) {
					int pos = StringUtils.getInt(params.replace("fish_caught_", ""));
					return Placeholders.getTop(TopType.FISH_CAUGHT, pos);
				}
				/*
				 * Check if the player is online, You should do this before doing anything
				 * regarding players
				 */
				if (player == null)
					return null;
				if (params.startsWith("tournament") || params.startsWith("treasures") || params.startsWith("shop")
						|| params.startsWith("records") || params.startsWith("fish"))
					return Placeholders.getStatistics(Bukkit.getPlayer(player.getUniqueId()), params);

				return null;
			}

			@Override
			public String getVersion() {
				return Loader.plugin.getDescription().getVersion();
			}

			@Override
			public String getIdentifier() {
				return "amazingfishing";
			}

			@Override
			public String getAuthor() {
				return "DevTec";
			}
		};
	}

}

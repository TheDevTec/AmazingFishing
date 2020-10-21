package me.DevTec.AmazingFishing;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PAPIExpansion extends PlaceholderExpansion {

    /*
         identifier_<placeholder>
     */
    public String getIdentifier() {
        return "amazingfishing";
    }

    public String getPlugin() {
        return "AmazingFishing";
    }

    public String getAuthor() {
        return "DevTec";
    }

    public String getVersion() {
        return "1.0";
    }

    public String onPlaceholderRequest(Player player, String identifier) {
        /*
         %amazingfishing_<nìco>%
         Returns the number of online players
          */
    	
    	/*
    	 * Placeholders:
    	 *  amazingfishing_tournament_wins_top1 -DONE
    	 *  amazingfishing_tournament_played_top1 -DONE
    	 *  amazingfishing_caught_top1 
    	 *  
    	 *  Player:
    	 *  amazingfishing_player_caught -DONE
    	 *  amazingfishing_player_tournaments_top1 -DONE
    	 *  amazingfishing_player_tournaments_played -DONE
    	 */
        if(identifier.toLowerCase().startsWith("tournament_wins")){
            return Placeholders.getTopTournamentWins(identifier.toLowerCase());
        }
        if(identifier.toLowerCase().startsWith("tournament_played")){
            return Placeholders.getTopTournamentPlayed(identifier.toLowerCase());
        }
        if(identifier.toLowerCase().startsWith("caught_top")){
            return Placeholders.getTopByCaught(identifier.toLowerCase());
        }
        /*
        Check if the player is online,
        You should do this before doing anything regarding players
         */
        if(player == null){
            return "";
        }
        if(identifier.equalsIgnoreCase("player_caught")){
        	int amount = Loader.me.getInt("Players."+player.getName()+".Stats.Amount");
        	if(Loader.me.getString("Players."+player.getName()+".Stats.Amount")==null)
        		amount=0;
            return amount+"";
        }
        if(identifier.equalsIgnoreCase("player_tournaments_played")){
        	int amount = Loader.me.getInt("Players."+player.getName()+".Stats.Tournaments");
        	if(Loader.me.getString("Players."+player.getName()+".Stats.Tournaments")==null)
        		amount=0;
            return amount+"";
        }
        if(identifier.startsWith("player_tournaments")){
            return Placeholders.getPlayerTournamentsTop(player, identifier.toLowerCase());
        }
        if(identifier.equalsIgnoreCase("name")){
            return player.getName();
        }
 
 
        return null;
    }
}

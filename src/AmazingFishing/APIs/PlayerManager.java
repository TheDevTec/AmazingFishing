package AmazingFishing.APIs;

import org.bukkit.entity.Player;

import me.DevTec.AmazingFishing.Loader;

public class PlayerManager {
	public Player player; 
	public PlayerManager(Player p) {
		if(p==null) return;
		this.player=p;
	}
	
	public int getCaught() {
	if(!Loader.me.exists("Players."+player.getName()+".Stats.Amount")) return 0;
	int amount = Loader.me.getInt("Players."+player.getName()+".Stats.Amount");
	return amount;
	}

	public int getPlayedTournaments() {
		if(!Loader.me.exists("Players."+player.getName()+".Stats.Tournaments")) return 0;
		int amount = Loader.me.getInt("Players."+player.getName()+".Stats.Tournaments");
	return amount;
	}
	
	public int getPlayedTournamentPosition(int position) {
		if(!Loader.me.exists("Players."+player.getName()+".Stats.Top."+position+".Tournaments")
				||position>=5) return 0;
		int amount = Loader.me.getInt("Players."+player.getName()+".Stats.Top."+position+".Tournaments");
	return amount;
	}
	
}

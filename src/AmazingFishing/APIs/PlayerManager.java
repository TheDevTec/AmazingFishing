package AmazingFishing.APIs;

import org.bukkit.entity.Player;

import me.DevTec.AmazingFishing.Loader;

public class PlayerManager {
	public String player; 
	public PlayerManager(Player p) {
		if(p==null) return;
		this.player=p.getName();
	}
	public PlayerManager(String p) {
		if(p==null) return;
		this.player=p;
	}
	
	public int getCaught() {
	if(!Loader.me.exists("Players."+player+".Stats.Amount")) return 0;
	int amount = Loader.me.getInt("Players."+player+".Stats.Amount");
	return amount;
	}

	public int getPlayedTournaments() {
		if(!Loader.me.exists("Players."+player+".Stats.Tournaments")) return 0;
		int amount = Loader.me.getInt("Players."+player+".Stats.Tournaments");
	return amount;
	}
	
	public int getPlayedTournamentPosition(int position) {
		if(!Loader.me.exists("Players."+player+".Stats.Top."+position+".Tournaments")
				||position>=5) return 0;
		int amount = Loader.me.getInt("Players."+player+".Stats.Top."+position+".Tournaments");
	return amount;
	}
	public boolean meExists() {
		if(Loader.me.exists("Players."+player+".Stats.Type"))
			return true;
		else 
			return false;
	}
	//"Players."+s.getName()+".Stats.Type"
	
}

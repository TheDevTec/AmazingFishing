package AmazingFishing;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class AFK implements Listener {
	@SuppressWarnings("deprecation")
	public static boolean isAFK(Player p) {
		if(Loader.c.getBoolean("Options.AFK.Enabled")) {
		int afk = 300; //5min
		if(Loader.c.getInt("Options.AFK.TimeToAFK")!=0)afk=Loader.c.getInt("Options.AFK.TimeToAFK");
			long afks = Loader.me.getLong("Players."+p.getName()+".AFK")/1000-System.currentTimeMillis()/1000;
			afks=-1*afks;
		if(afks > afk && p.getItemInHand().getType()==Material.FISHING_ROD && !p.hasPermission("amazingfishing.afkbypass")) {
			p.sendTitle(Loader.getAFK(1), Loader.getAFK(2));
			return true;
		}
		return false;
		}
		return false;
	}
	
	private void save(String p) {
		Loader.me.set("Players."+p+".AFK",System.currentTimeMillis());
		Loader.saveChatMe();
	}
	private boolean waiting;
	private boolean w() {
		if(!waiting) {
			waiting=true;
			Bukkit.getScheduler().runTaskLater(Loader.plugin, new Runnable() {

				@Override
				public void run() {
					waiting=false;
					
				}
				
			}, 20);
			return false;
		}
		return true;
	}
	
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onMove(PlayerMoveEvent e) {
		if(Loader.c.getBoolean("Options.AFK.Enabled") && !w()) {
         if(Math.abs(e.getFrom().getBlockX() - e.getTo().getBlockX()) > 0 || Math.abs(e.getFrom().getBlockZ() - e.getTo().getBlockZ()) > 0)
 			save(e.getPlayer().getName());
      }}}
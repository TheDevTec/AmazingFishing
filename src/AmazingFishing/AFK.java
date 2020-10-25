package AmazingFishing;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.DevTec.AmazingFishing.Loader;
import me.DevTec.TheAPI.Utils.TheAPIUtils.LoaderClass;

public class AFK implements Listener {
	static HashMap<String, Long> a = new HashMap<>();
	@SuppressWarnings("deprecation")
	public static boolean isAFK(Player p) {
		if(LoaderClass.data.getBoolean("Options.AFK.Enabled")) {
		int afk = 300; //5min
		if(LoaderClass.data.getInt("Options.AFK.TimeToAFK")!=0)afk=LoaderClass.data.getInt("Options.AFK.TimeToAFK");
			long afks = (a.containsKey(p.getName()) ? a.get(p.getName()):0)-System.currentTimeMillis()/1000;
			afks=-1*afks;
		if(afks > afk && p.getItemInHand().getType()==Material.FISHING_ROD && !p.hasPermission("amazingfishing.afkbypass")) {
			p.sendTitle(Loader.getAFK(1), Loader.getAFK(2));
			return true;
		}
		return false;
		}
		return false;
	}
	
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onMove(PlayerMoveEvent e) {
		if(LoaderClass.data.getBoolean("Options.AFK.Enabled")) {
         if(Math.abs(e.getFrom().getBlockX() - e.getTo().getBlockX()) > 0 || Math.abs(e.getFrom().getBlockZ() - e.getTo().getBlockZ()) > 0)
 			a.put(e.getPlayer().getName(), System.currentTimeMillis()/1000);
      }}}
package AmazingFishing;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import me.DevTec.AmazingFishing.Loader;

public class WG {
	private static boolean existWG() {
		return Bukkit.getPluginManager().getPlugin("WorldGuard")!=null;
	}
    public static boolean isInRegion(Player p){
    	if(existWG()) {
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            if(BukkitAdapter.adapt(Bukkit.getWorld(p.getWorld().getName()))!=null) {
            RegionManager region = container.get(BukkitAdapter.adapt(Bukkit.getWorld(p.getWorld().getName())));
            boolean is = false;
            if(region != null) {
            for(String r:region.getRegions().keySet())
            if (region.getRegion(r).contains(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()))is= true;
            }
            return is;
            }
           return false;
    	}
    	return false;
	}
    public static String getRegion(Player p) {
    	String region = null;
    	if(existWG()) {
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            if(BukkitAdapter.adapt(Bukkit.getWorld(p.getWorld().getName()))!=null) {
            RegionManager reg = container.get(BukkitAdapter.adapt(Bukkit.getWorld(p.getWorld().getName())));
            if(reg != null)
            for(String r:reg.getRegions().keySet())
            if (reg.getRegion(r).contains(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()))region=r;
            }
    	}
    	return region;
    }
    
    public static enum Type{
    	Legendary,
    	Epic,
    	Rare,
    	Common;
    }
    public static boolean hasAccess(Player p, Type t) {
    	if(existWG()) {
    		if(Loader.c.exists("Options.Perms-Treasures."+t+".WorldGuard-Regions") && Loader.c.getBoolean("Options.Perms-Treasures."+t+".UseWorldGuard-Regions")) {
    			boolean bol = false;
    		for(String s:Loader.c.getStringList("Options.Perms-Treasures."+t+".WorldGuard-Regions")) {
    			if(p.hasPermission("amazingfishing.treasures."+t.toString().toLowerCase()+"."+s))bol= true;
    			//amazingfishing.treasures.legendary.<region name>
    		}
    		if(p.hasPermission("amazingfishing.treasures."+t.toString().toLowerCase()+".*"))bol=true;
    		return bol;
    		}
    		return true;
    	}
    	return true;
    }
}

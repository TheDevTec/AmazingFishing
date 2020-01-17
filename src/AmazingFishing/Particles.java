package AmazingFishing;

import org.bukkit.Location;
import org.bukkit.Particle;


public class Particles {
	public static void summon(Location p) {
		if(Loader.c.getBoolean("Options.Particles")) 
			p.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, p.getX(), p.getY()+1, p.getZ(),20);
	}
}

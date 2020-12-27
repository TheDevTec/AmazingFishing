package AmazingFishing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

import me.DevTec.AmazingFishing.Loader;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.scheduler.Scheduler;
import me.devtec.theapi.scheduler.Tasker;

public class FishOfDay {
	int run;
	public void startRunnable() {
		run=new Tasker() {
			public void run() {
				List<Object> r = new ArrayList<Object>();
				for(String s : Loader.c.getKeys("Types"))r.add(s);
				String type = TheAPI.getRandomFromList(r).toString();

				List<Object> rs = new ArrayList<Object>();
				for(String s : Loader.c.getKeys("Types."+type))rs.add(s);
				String fish = TheAPI.getRandomFromList(rs).toString();
				 Loader.me.set("FishOfDay.Type", type);
				 Loader.me.set("FishOfDay.Name", fish);
				 Loader.me.set("FishOfDay.Bonus", TheAPI.getRandomFromList(Arrays.asList(2,2,2,3,2,4,2,2,2,8,2,4,2,6,2,5,2,2,2,7,2,2,2,2,9,2,2)));
				 Loader.me.save();
			}
			
		}.runRepeatingSync(0,20*60*60*24);
	}
	
	public void stopRunnable() {
		Scheduler.cancelTask(run);
	}
	
	public String getType() {
		return Loader.me.getString("FishOfDay.Type");
	}
	
	public Material getMaterial() {
		if(getType().equalsIgnoreCase("cod")) {
			return Material.COD;
		}
		if(getType().equalsIgnoreCase("salmon")) {
			return Material.SALMON;
		}
		if(getType().equalsIgnoreCase("tropicalfish")) {
			return Material.TROPICAL_FISH;
		}
		if(getType().equalsIgnoreCase("pufferfish")) {
			return Material.PUFFERFISH;
		}
		return Material.COD;
	}
	
	public int getBonus() {
		return Loader.me.getInt("FishOfDay.Bonus");
	}
	
	public String getFish() {
		return Loader.me.getString("FishOfDay.Name");
	}
}

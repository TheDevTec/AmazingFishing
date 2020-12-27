package AmazingFishing;

import java.util.List;

import org.bukkit.entity.Player;

import AmazingFishing.APIs.Enums.FishType;
import AmazingFishing.Events.onChat2;
import AmazingFishing.Events.onChat2.create;
import me.DevTec.AmazingFishing.Loader;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.scheduler.Tasker;
import me.devtec.theapi.utils.StringUtils;

public class get {
	public static String fish(Player p, String path) {
		if(path.equals("PufferFish"))path="Pufferfish";
		if(path.equals("TropicalFish"))path="Tropical_Fish";
		String f = Loader.c.getString("Creating-"+path+"."+p.getName()+".Fish");
		if(f != null && f.equals("")||f != null && f.equals(" "))f=null;
		return f;
	}
	public static String getName(Player p,String path) {
		if(path.equals("PufferFish"))path="Pufferfish";
		if(path.equals("TropicalFish"))path="Tropical_Fish";
		return Loader.c.getString("Creating-"+path+"."+p.getName()+".Name");
	}
	public static void setfish(Player p, String path, String typ) {
		int ryba = 0;
		if(Loader.c.getString("Types."+typ)!=null)
		 ryba = Loader.c.getKeys("Types."+typ).size();
		Loader.c.set("Creating-"+path+"."+p.getName()+".Fish", ryba);
		
	}
	public static List<String> getBiomes(Player p, String path){
		return Loader.c.getStringList("Creating-"+path+"."+p.getName()+".BiomesList");
	}
	
	
	public static String getXp(Player p,String path) {
		if(path.equals("PufferFish"))path="Pufferfish";
		if(path.equals("TropicalFish"))path="Tropical_Fish";
		return Loader.c.getString("Creating-"+path+"."+p.getName()+".Xp");
	}
	public static String getPoints(Player p,String path) {
		if(path.equals("PufferFish"))path="Pufferfish";
		if(path.equals("TropicalFish"))path="Tropical_Fish";
		return Loader.c.getString("Creating-"+path+"."+p.getName()+".Points");
	}
	public static String getMoney(Player p,String path) {
		if(path.equals("PufferFish"))path="Pufferfish";
		if(path.equals("TropicalFish"))path="Tropical_Fish";
		return Loader.c.getString("Creating-"+path+"."+p.getName()+".Money");
	}
	public static String getCm(Player p,String path) {
		if(path.equals("PufferFish"))path="Pufferfish";
		if(path.equals("TropicalFish"))path="Tropical_Fish";
		return Loader.c.getString("Creating-"+path+"."+p.getName()+".MaxCm");
	}
	public static void addBiome(Player p, String path, String biome) {
		List<String> list = getBiomes(p,path);
		if(list.contains(biome)==false) {
			list.add(biome);
			Loader.c.set("Creating-"+path+"."+p.getName()+".BiomesList", list);
			Loader.c.save();
		}else {
			list.remove(biome);
			Loader.c.set("Creating-"+path+"."+p.getName()+".BiomesList", list);
			Loader.c.save();
		}
	}
	
	public static create typ(Player p, String path) {
		if(path.equals("PufferFish"))path="Pufferfish";
		if(path.equals("TropicalFish"))path="Tropical_Fish";
		if(Loader.c.exists("Creating-"+path+"."+p.getName()+".Type"))
		return create.valueOf(Loader.c.getString("Creating-"+path+"."+p.getName()+".Type"));
		return null;
	}
	public static boolean ready(Player p,String path) {
		if(path.equals("PufferFish"))path="Pufferfish";
		if(path.equals("TropicalFish"))path="Tropical_Fish";
		if(fish(p,path) != null && onChat2.ex("Creating-"+path+"."+p.getName()+".Xp") 
				&& onChat2.ex("Creating-"+path+"."+p.getName()+".Chance") 
				&& onChat2.ex("Creating-"+path+"."+p.getName()+".Money") 
				&& onChat2.ex("Creating-"+path+"."+p.getName()+".MaxCm") 
				&& onChat2.ex("Creating-"+path+"."+p.getName()+".Points"))return true;
		return false;
	}
	public static void warn(Player p, String path, FishType type) {
		p.sendTitle(Color.c(Loader.s("Editor.MissingFishName.1")), Color.c(Loader.s("Editor.MissingFishName.2")));
		if(!Loader.c.getBoolean("Creating-"+path+"."+p.getName()+".Warned")) {
			if(path.equals("PufferFish"))path="Pufferfish";
			if(path.equals("TropicalFish"))path="Tropical_Fish";
		Loader.c.set("Creating-"+path+"."+p.getName()+".Warned", true);
		Loader.c.save();
		String s = path;
		new Tasker() {
			public void run() {
			Loader.c.remove("Creating-"+s+"."+p.getName()+".Warned");
			Loader.c.save();
			TheAPI_GUIs g = new TheAPI_GUIs();
			g.openFishCreatorType(p, fish(p,s), type);
			}}.runLater(40);}
	}

	public static String getName(String path) {
		String name = null;
		for(int s = 0; s>-1;++s) {
			if(name!=null)break;
		if(!Loader.c.exists("Types."+path+"."+s)) {
			name=s+"";
		}
		}
		return name;
	}
	public static void finish(Player p,String path, boolean title) {
		p.getOpenInventory().close();
		String f = getName(path);
		if(f != null && getName(p,path) != null) {
		Loader.c.set("Types."+path+"."+f+".Name", getName(p,path));
		Loader.c.set("Types."+path+"."+f+".Xp", getXp(p,path));
		Loader.c.set("Types."+path+"."+f+".Points", getPoints(p,path));
		Loader.c.set("Types."+path+"."+f+".Money", getMoney(p,path));
		Loader.c.set("Types."+path+"."+f+".MaxCm", getCm(p,path));
		Loader.c.set("Types."+path+"."+f+".Biomes", getBiomes(p,path));
		Loader.c.set("Types."+path+"."+f+".Chance", getChance(p,path));
		Loader.c.set("Types."+path+"."+f+".ModelData", StringUtils.getInt(f));
		if(path.equals("PufferFish"))path="Pufferfish";
		if(path.equals("TropicalFish"))path="Tropical_Fish";
		Loader.c.remove("Creating-"+path+"."+p.getName());
		Loader.c.remove("Edit-"+path+"."+p.getName());
		Loader.c.save();
		if(path.equals("Pufferfish"))path="PufferFish";
		if(path.equals("Tropical_Fish"))path="TropicalFish";
			if(title)
				TheAPI.sendTitle(p,Loader.s("Editor.SuccefullyCreated.1")
						.replace("%fish%", Loader.c.getString("Types."+path+"."+f+".Name")), Loader.s("Editor.SuccefullyCreated.2")
						.replace("%fish%", Loader.c.getString("Types."+path+"."+f+".Name")));
				else
					TheAPI.sendTitle(p,Loader.get("Saved", 1), Loader.get("Saved", 2));
	}
		
	}
	public static String getChance(Player p, String path) {
		if(path.equals("PufferFish"))path="Pufferfish";
		if(path.equals("TropicalFish"))path="Tropical_Fish";
		return Loader.c.getString("Creating-"+path+"."+p.getName()+".Chance");
	}
}

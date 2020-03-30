package AmazingFishing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.sqlite.util.StringUtils;

import AmazingFishing.Quests.Actions;
import me.Straiker123.ItemCreatorAPI;
import me.Straiker123.TheAPI;

public class ByBiome {
	public static enum biomes{
		BADLANDS,
		BEACH,
		COLD_OCEAN,
		DEEP_COLD_OCEAN,
		DEEP_FROZEN_OCEAN,
		DEEP_LUKEWARM_OCEAN,
		DEEP_WARM_OCEAN,
		DEEP_OCEAN,
		DESERT,
		SAVANNA,
		END,
		NETHER,
		FOREST,
		WOODED_HILLS,
		STONE_SHORE,
		FROZEN_OCEAN,
		TUNDRA,
		ICE_SPIKES,
		JUNGLE,
		MOUNTAINS,
		TAIGA,
		WARM_OCEAN,
		PLAINS,
		RIVER,
		MUSHROOM,
		SWAMP
	}
	public static String getBiome(String name) {
		String a = null;
		for(biomes s:biomes.values()) {
			String f = Color.c(Loader.s("Words.Biomes."+s.name()+".Name"));
			if(f.equals(Color.c(name)))a=s.name();
		}
		return a;
	}
	
	public static void addBiome(String fish, String type, biomes biom) {
		if(Loader.c.getString("Types."+type+"."+fish+".Biomes")!=null) {
			List<String> list = Loader.c.getStringList("Types."+type+"."+fish+".Biomes");
			if(!list.contains(biom.name())) {
			list.add(biom.name());
			}else
				list.remove(biom.name());
			Loader.c.set("Types."+type+"."+fish+".Biomes",list);
			Loader.save();
		}else {
		List<String> list = new ArrayList<String>();
		list.add(biom.name());
		Loader.c.set("Types."+type+"."+fish+".Biomes",list);
		Loader.save();
		}
	}
	
	private static String fishes(String a, String type){
		List<String> fishes = new ArrayList<String>();
		if(Loader.c.getString("Types."+type)!=null)
		for(String d:Loader.c.getConfigurationSection("Types."+type).getKeys(false)) {
		if(Loader.c.getString("Types."+type+"."+d+".Biomes")!=null &&!Loader.c.getStringList("Types."+type+"."+d+".Biomes").isEmpty()) {
			for(String s:Loader.c.getStringList("Types."+type+"."+d+".Biomes"))
			if(s.toLowerCase().contains(a.toLowerCase())) {
	for(int i = 0; i < (Loader.c.getInt("Types."+type+"."+d+".Chance")>0 ? Loader.c.getInt("Types."+type+"."+d+".Chance") : 1); ++i)
				fishes.add(d);
			}}else {
	for(int i = 0; i < (Loader.c.getInt("Types."+type+"."+d+".Chance")>0 ? Loader.c.getInt("Types."+type+"."+d+".Chance") : 1); ++i)
				fishes.add(d);
			}
		}
		return TheAPI.getRandomFromList(fishes).toString();
	}
	
	public static String getTran(biomes b) {
		if(b!=null) {
		return Color.c(Loader.s("Words.Biomes."+b.name()+".Name"));
		}
		return Color.c(Loader.s("Words.Biomes.ALL.Name"));
	}
	
	public static void generateFish(Player p, Material t, Location hook) {
		String type = null;
		if(t==null)t=Material.COD;
		if(t==Material.COD)type="Cod";
		if(t==Material.PUFFERFISH)type="PufferFish";
		if(t==Material.TROPICAL_FISH)type="TropicalFish";
		if(t==Material.SALMON)type="Salmon";
		String fish = fishes(hook.getBlock().getBiome().name(),type);
		if(fish==null) {
			ItemCreatorAPI i = TheAPI.getItemCreatorAPI(t);
			double length = Generators.length(type, fish);
			double weight = Generators.weight(length);
			if(Loader.c.getString("Format.FishDescription")!=null) {
				List<String> lore=new ArrayList<String>();
				
				List<String> biomes = new ArrayList<String>();
				if(Loader.c.getString("Types."+type+"."+fish+".Biomes")!=null)
					
					for(String g:Loader.c.getStringList("Types."+type+"."+fish+".Biomes"))
						biomes.add(getTran(ByBiome.biomes.valueOf(g)));
				else
					biomes.add(getTran(null));
				String b = StringUtils.join(biomes, ", ");
			for(String s:Loader.c.getStringList("Format.FishDescription")) {
				lore.add(Color.c(s
						.replace("%fish_biomes%", b)
						.replace("%biomes%", b)
						.replace("%fish_weight%", weight+"")
						.replace("%weight%", weight+"")
						.replace("%fish_length%", length+"")
						.replace("%length%", length+"")
						.replace("%fish_name%", "Uknown")
						.replace("%fish%",  "Uknown")
						.replace("%time%", new SimpleDateFormat("HH:mm:ss").format(new Date()))
						.replace("%date%", new SimpleDateFormat("dd.MM.yyyy").format(new Date()))
						.replace("%fisherman%", p.getName())
						.replace("%fisher%", p.getName())));
			}
			i.setLore(lore);
			}
				bag.addFish(p,i.create());
			Loader.msgCmd(Loader.s("Prefix")+Loader.s("Caught").replace("%cm%", length+"").replace("%length%", length+"").replace("%weight%", weight+"").replace("%fish%",  "Uknown"), p);
			return;
		}
			ItemCreatorAPI i = TheAPI.getItemCreatorAPI(t);
			double length = Generators.length(type, fish);
			double weight = Generators.weight(length);
			String name = fish;
			if(Loader.c.getString("Types."+type+"."+fish+".Name")!=null)
				name=Color.c(Loader.c.getString("Types."+type+"."+fish+".Name"));
			i.setDisplayName(name);
			if(Loader.c.getString("Types."+type+"."+fish+".ModelData")!=null) {
			i.setCustomModelData(Loader.c.getInt("Types."+type+"."+fish+".ModelData"));
			}
			if(Loader.c.getString("Format.FishDescription")!=null) {
				List<String> lore=new ArrayList<String>();
				
				List<String> biomes = new ArrayList<String>();
				if(Loader.c.getString("Types."+type+"."+fish+".Biomes")!=null)
					
					for(String g:Loader.c.getStringList("Types."+type+"."+fish+".Biomes"))
						biomes.add(getTran(ByBiome.biomes.valueOf(g)));
				else
					biomes.add(getTran(null));
				String b = StringUtils.join(biomes, ", ");
			for(String s:Loader.c.getStringList("Format.FishDescription")) {
				lore.add(Color.c(s.replace("%chance%", ""+(Loader.c.getInt("Types."+type+"."+fish+".Chance")>0 ? Loader.c.getInt("Types."+type+"."+fish+".Chance") : 1))
						.replace("%fish_biomes%", b)
						.replace("%biomes%", b)
						.replace("%fish_weight%", weight+"")
						.replace("%weight%", weight+"")
						.replace("%fish_length%", length+"")
						.replace("%length%", length+"")
						.replace("%fish_name%", name)
						.replace("%fish%", name)
						.replace("%time%", new SimpleDateFormat("HH:mm:ss").format(new Date()))
						.replace("%date%", new SimpleDateFormat("dd.MM.yyyy").format(new Date()))
						.replace("%fisherman%", p.getName())
						.replace("%fisher%", p.getName())));
			}
			i.setLore(lore);
			}
				bag.addFish(p,i.create());
				Utils.addRecord(p, fish, type, length,weight);
				Tournament.add(p, length,weight);
				OnCatchFish.addEarn(p,type,fish,length);
			Loader.msgCmd(Loader.s("Prefix")+Loader.s("Caught").replace("%cm%", length+"").replace("%length%", length+"").replace("%weight%", weight+"").replace("%fish%", name), p);
			Logger.info(p.getDisplayName(), type, fish, length, weight);
			Quests.addProgress(p,type,fish,Actions.CATCH_FISH);
		return;
	}}
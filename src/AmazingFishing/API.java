package AmazingFishing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.sqlite.util.StringUtils;

import me.DevTec.AmazingFishing.Configs;
import me.DevTec.AmazingFishing.Loader;
import me.DevTec.TheAPI.TheAPI;
import me.DevTec.TheAPI.TheAPI.SudoType;
import me.DevTec.TheAPI.APIs.ItemCreatorAPI;
import me.DevTec.TheAPI.EconomyAPI.EconomyAPI;


public class API {
	public static enum treasureType{
		COMMON,
		RARE,
		EPIC,
		LEGENDARY
	}

	public static enum fishType{
		COD,
		SALMON,
		PUFFERFISH,
		TROPICAL_FISH
	}
	/**
	 * Planned, give specified Treasure
	 */
	public static void giveTreasure(Player p, treasureType type, String treasure) {
		String name = Loader.c.getString("Treasures."+type+"."+treasure+".Name");
		double money = Loader.c.getDouble("Treasures."+type+"."+treasure+".Money");
		List<String> messages = Loader.c.getStringList("Treasures."+type+"."+treasure+".Messages");
		List<String> commands = Loader.c.getStringList("Treasures."+type+"."+treasure+".Commands");
		double points = Loader.c.getDouble("Treasures."+type+"."+treasure+".Points");
		double per = Loader.c.getDouble("Treasures."+type+"."+treasure+".Chance");
		Points.give(p.getName(), points);
		EconomyAPI.depositPlayer(p.getName(), money);
		for(String d:messages) {
			TheAPI.msg(d.replace("%treasure%", name).replace("%percentage%", per+"").replace("%chance%", per+""),p);
		}
		for(String d:commands) {
			TheAPI.sudoConsole(SudoType.COMMAND, Color.c(d.replace("%treasure%", name).replace("%percentage%", per+"").replace("%chance%", per+"")));
		}
		if(Loader.c.getString("Treasures."+type+"."+treasure+".Contents")!=null)
		for(String i : Loader.c.getConfigurationSection("Treasures."+type+"."+treasure+".Contents").getKeys(false))
		TheAPI.giveItem(p, Loader.c.getItemStack("Treasures."+type+"."+treasure+".Contents."+i));
	}
	private static String createTreasure(treasureType type, boolean b) {
		List<String> a = new ArrayList<String>();
		for(String s:Loader.c.getConfigurationSection("Treasures."+type).getKeys(false)) {
			if(b) {
				for(int i = 0; i<Loader.c.getInt("Treasures."+type+"."+s+".Chance")+1; ++i)
					a.add(s);
				a=Utils.createShuffleList(a);
			}else
				a.add(s);
		}
		return TheAPI.getRandomFromList(a).toString();
	}

	/**
	 * give specifed Fish (Ignoring biome)
	 */
	public static void giveFish(Player p, fishType t, String fish) {
		ItemCreatorAPI a = new ItemCreatorAPI(Material.matchMaterial(""+t));
		String type=t.toString().replace("COD", "Cod")
				.replace("SALMON", "Salmon")
				.replace("TROPICAL_FISH", "TropicalFish")
				.replace("PUFFERFISH", "PufferFish");
		double length = Generators.length(type, fish);
		double weight = Generators.weight(length);
		String name = fish;
		if(Loader.c.getString("Types."+type+"."+fish+".Name")!=null)
			name=Color.c(Loader.c.getString("Types."+type+"."+fish+".Name"));
		a.setDisplayName(name);
		if(Loader.c.getString("Types."+type+"."+fish+".ModelData")!=null) {
		a.setCustomModelData(Loader.c.getInt("Types."+type+"."+fish+".ModelData"));
		}
		List<String> lore = null;
		if(Loader.c.getString("Format.FishDescription")!=null) {
			lore=new ArrayList<String>();
			List<String> biomes = new ArrayList<String>();
			if(Loader.c.getString("Types."+type+"."+fish+".Biomes")!=null)
				
				for(String g:Loader.c.getStringList("Types."+type+"."+fish+".Biomes"))
					biomes.add(ByBiome.getTran(ByBiome.biomes.valueOf(g)));
			else
				biomes.add(ByBiome.getTran(null));
			String b = StringUtils.join(biomes, ", ");
		for(String s:Loader.c.getStringList("Format.FishDescription")) {
			lore.add(Color.c(s
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
		}}
		a.setLore(lore);
		TheAPI.giveItem(p, a.create());
	}

	/**
	 * give random Fish (Ignoring biome)
	 */
	public static void giveFish(Player p, fishType type) {
		giveFish(p,type,createFish(type.toString()));
	}
	
	/**
	 * give specifed Fish by Biome
	 */
	public static void giveFish(Player p, fishType t, String fish, Biome biome) {
		ItemCreatorAPI a = new ItemCreatorAPI(Material.matchMaterial(""+t));
		String type=t.toString().replace("COD", "Cod")
				.replace("SALMON", "Salmon")
				.replace("TROPICAL_FISH", "TropicalFish")
				.replace("PUFFERFISH", "PufferFish");
		double length = Generators.length(type, fish);
		double weight = Generators.weight(length);
		String name = fish;
		if(Loader.c.getString("Types."+type+"."+fish+".Name")!=null)
			name=Color.c(Loader.c.getString("Types."+type+"."+fish+".Name"));
		a.setDisplayName(name);
		if(Loader.c.getString("Types."+type+"."+fish+".ModelData")!=null) {
		a.setCustomModelData(Loader.c.getInt("Types."+type+"."+fish+".ModelData"));
		}
		List<String> lore = null;
		if(Loader.c.getString("Format.FishDescription")!=null) {
			lore=new ArrayList<String>();
			List<String> biomes = new ArrayList<String>();
			if(Loader.c.getString("Types."+type+"."+fish+".Biomes")!=null)
				
				for(String g:Loader.c.getStringList("Types."+type+"."+fish+".Biomes"))
					biomes.add(ByBiome.getTran(ByBiome.biomes.valueOf(g)));
			else
				biomes.add(ByBiome.getTran(null));
			String b = StringUtils.join(biomes, ", ");
		for(String s:Loader.c.getStringList("Format.FishDescription")) {
			lore.add(Color.c(s
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
		}}
		a.setLore(lore);
		TheAPI.giveItem(p, a.create());
	}

	/**
	 * give random Fish by Biome
	 */
	public static void giveFish(Player p, fishType type, Biome biome) {
		giveFish(p,type,createFish(type.toString(),biome),biome);
	}
	
	/**
	 * give random Treasure
	 */
	public static void giveTreasure(Player p, treasureType type) {
		giveTreasure(p,type,createTreasure(type,false));
	}
	
	/**
	 * give random Treasure
	 */
	public static void giveTreasureByChance(Player p, treasureType type) {
		giveTreasure(p,type,createTreasure(type,true));
	}
	
	/**
	 * Start new quest
	 */
	public static void startQuest(Player p, String quest) {
		 Loader.me.set("Players."+p.getName()+".Quests.Current",quest);
		 Loader.me.set("Players."+p.getName()+".Quests.Amount",0);
		 Configs.me.save();
	}
	/**
	 * Cancel current quest
	 */
	public static void cancelQuest(Player p) {
		 Loader.me.set("Players."+p.getName()+".Quests",null);
		 Configs.me.save();
	}
	/**
	 * @return String Current quest
	 */
	public static String getQuest(Player p) {
		return Loader.me.getString("Players."+p.getName()+".Quests.Current");
	}
	
	private static String createFish(String type){
		type=type.replace("COD", "Cod")
				.replace("SALMON", "Salmon")
				.replace("TROPICAL_FISH", "TropicalFish")
				.replace("PUFFERFISH", "PufferFish");
		
		String f = null;
		List<String> fish = new ArrayList<String>();
		if(Loader.c.getString("Types."+type)!=null)
		for(String d:Loader.c.getConfigurationSection("Types."+type).getKeys(false)) {
			fish.add(d);
		}
		if(fish.isEmpty()==false) {
		int i=new Random().nextInt(fish.size()-1);
		f=fish.get(i);
		}
		return f;
	}
	
	private static String createFish(String type, Biome biome){
		type=type.replace("COD", "Cod")
				.replace("SALMON", "Salmon")
				.replace("TROPICAL_FISH", "TropicalFish")
				.replace("PUFFERFISH", "PufferFish");
			String f = null;
			List<String> fish = new ArrayList<String>();
			if(Loader.c.getString("Types."+type)!=null)
			for(String d:Loader.c.getConfigurationSection("Types."+type).getKeys(false)) {
			if(Loader.c.getString("Types."+type+"."+d+".Biomes")!=null) {
				for(String s:Loader.c.getStringList("Types."+type+"."+d+".Biomes")) {
				if(s.toLowerCase().contains(biome.name().toLowerCase())) {
					fish.add(d);
				}}}else
					fish.add(d);
			
			}
			
			if(fish.isEmpty()==false) {
			int i=new Random().nextInt(fish.size()-1);
			f=fish.get(i);
			}
			return f;
	}
}

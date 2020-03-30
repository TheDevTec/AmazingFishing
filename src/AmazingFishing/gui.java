package AmazingFishing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import AmazingFishing.ByBiome.biomes;
import AmazingFishing.help.Type;
import me.Straiker123.GUICreatorAPI;
import me.Straiker123.TheAPI;
import me.Straiker123.GUICreatorAPI.Options;
import me.Straiker123.ItemCreatorAPI;

@SuppressWarnings("deprecation")
public class gui {
	public static enum FishType {
		PUFFERFISH,TROPICAL_FISH,COD,SALMON
	}
	public static void openGlobal(Player p) {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI(p);
		a.setTitle("&6Select fish type");
		a.setSize(54);
		Create.prepareInv(a);
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
		if(Loader.c.getBoolean("Options.UseGUI")) {
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
				help.open(p, Type.Player);
				}});

			a.setItem(49,Create.createItem(Trans.back(), Material.BARRIER),w);
		}
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openGlobalFish(p,FishType.PUFFERFISH);
			}});
		a.setItem(20,Create.createItem(Trans.puf(), Material.PUFFERFISH),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openGlobalFish(p,FishType.TROPICAL_FISH);
			}});
		a.setItem(24,Create.createItem(Trans.tro(), Material.TROPICAL_FISH),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openGlobalFish(p,FishType.SALMON);
			}});
		a.setItem(30,Create.createItem(Trans.sal(), Material.SALMON),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openGlobalFish(p,FishType.COD);
			}});
		a.setItem(32,Create.createItem(Trans.cod(), Material.COD),w);
		a.open();
	}
	public static void openGlobalFish(Player p, FishType type) {
		String path = null;
		String title = null;
		Material i = null;
		switch(type) {
		case COD:
			i=Material.COD;
			path="Cod";
			title=Trans.cod();
			break;
		case PUFFERFISH:
			i=Material.PUFFERFISH;
			path="PufferFish";
			title=Trans.puf();
			break;
		case SALMON:
			i=Material.SALMON;
			path="Salmon";
			title=Trans.sal();
			break;
		case TROPICAL_FISH:
			i=Material.TROPICAL_FISH;
			path="TropicalFish";
			title=Trans.tro();
			break;
		}
		GUICreatorAPI a = TheAPI.getGUICreatorAPI(p);
		a.setTitle("&6Top 3 players &7- "+title);
		a.setSize(54);
		Create.prepareInv(a);
		String pat =path;
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					openGlobal(p);
				}});
			a.setItem(49,Create.createItem(Trans.back(), Material.BARRIER),w);
		for(String s:Loader.c.getConfigurationSection("Types."+path).getKeys(false)) {
			String name = s;
			if(Loader.c.getString("Types."+path+"."+s+".Name")!=null)name=Loader.c.getString("Types."+path+"."+s+".Name");
			w.remove(Options.RUNNABLE);
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					onOpenFish(p,type,s,pat);
				}});
			a.addItem(Create.createItem(name, i),w);
			}
		a.open();
	}
	private static String name(String s) {
		if(TheAPI.getPlayer(s)!=null)return TheAPI.getPlayer(s).getDisplayName();
		return s;
	}
	
	public static void onOpenFish(Player p, FishType t, String fish, String type) {
		String name = Loader.c.getString("Types."+type+"."+fish+".Name");
		if(name==null)name=fish;
		HashMap<String, Double> as = new HashMap<String, Double>();
		if(Loader.me.getString("Players")!=null)
		for(String s : Loader.me.getConfigurationSection("Players").getKeys(false)) {
			if(Loader.me.getString("Players."+s+"."+type+"."+fish+".Length")!=null)
			as.put(s, Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Length"));
		}
		Ranking r = new Ranking(as);
		
		HashMap<String, Double> aw = new HashMap<String, Double>();
		if(Loader.me.getString("Players")!=null)
		for(String s : Loader.me.getConfigurationSection("Players").getKeys(false)) {
			if(Loader.me.getString("Players."+s+"."+type+"."+fish+".Weight")!=null)
			aw.put(s, Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Weight"));
		}
		Ranking rw = new Ranking(aw);
		
		GUICreatorAPI a = TheAPI.getGUICreatorAPI(p);
		a.setTitle("&6Top 3 players on "+name);
		a.setSize(54);
		Create.prepareInv(a);
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);

			ItemCreatorAPI fs= TheAPI.getItemCreatorAPI(Material.getMaterial("LEGACY_SKULL_ITEM"));
			fs.setSkullType(SkullType.PLAYER);
			String s = "-";
			s="-";
			if(r.getPlayer(3)!=null)s=r.getPlayer(3);
		fs.setLore(Arrays.asList("&7 - Position: 1."
				,"&7Length:","&7 - "+Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Length")+"Cm"));
		if(s.equalsIgnoreCase("-"))
			fs.setOwner("none");
			else
				fs.setOwner(s);
		fs.setDisplayName(name(s));
		a.setItem(20, fs.create(),w);
		

		ItemCreatorAPI ss= TheAPI.getItemCreatorAPI(Material.getMaterial("LEGACY_SKULL_ITEM"));
		ss.setSkullType(SkullType.PLAYER);
		s="-";
		if(r.getPlayer(2)!=null)s=r.getPlayer(2);
		ss.setLore(Arrays.asList("&7 - Position: 2."
				,"&7Length:","&7 - "+Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Length")+"Cm"));
		if(s.equalsIgnoreCase("-"))
			ss.setOwner("none");
			else
				ss.setOwner(s);
		ss.setDisplayName(name(s));
		a.setItem(21, ss.create(),w);

		ItemCreatorAPI sss= TheAPI.getItemCreatorAPI(Material.getMaterial("LEGACY_SKULL_ITEM"));
		sss.setSkullType(SkullType.PLAYER);
		s="-";
		if(r.getPlayer(1)!=null)s=r.getPlayer(1);
		if(s.equalsIgnoreCase("-"))
			sss.setOwner("none");
			else
				sss.setOwner(s);
		sss.setDisplayName(name(s));
		sss.setLore(Arrays.asList("&7 - Position: 3."
				,"&7Length:","&7 - "+Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Length")+"Cm"));
		a.setItem(22, sss.create(),w);

		ItemCreatorAPI sa= TheAPI.getItemCreatorAPI(Material.getMaterial("LEGACY_SKULL_ITEM"));
			sa.setSkullType(SkullType.PLAYER);
		sa.setOwner(p.getName());
		sa.setDisplayName(p.getDisplayName());
		sa.setLore(Arrays.asList("&7 - Position: "+r.getPosition(p.getName())+"."
				,"&7Length:","&7 - "+Loader.me.getDouble("Players."+p.getName()+"."+type+"."+fish+".Length")+"Cm"));
		a.setItem(24, sa.create(),w);


			ItemCreatorAPI sg= TheAPI.getItemCreatorAPI(Material.getMaterial("LEGACY_SKULL_ITEM"));
			sg.setSkullType(SkullType.PLAYER);
			s="-";
			if(rw.getPlayer(3)!=null)s=rw.getPlayer(3);
			if(s.equalsIgnoreCase("-"))
				sg.setOwner("none");
				else
					sg.setOwner(s);
		sg.setLore(Arrays.asList("&7 - Position: 1."
				,"&7Weight:","&7 - "+Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Weight")+"Kg"));
		sg.setDisplayName(name(s));
		a.setItem(29, sg.create(),w);

		ItemCreatorAPI ssg= TheAPI.getItemCreatorAPI(Material.getMaterial("LEGACY_SKULL_ITEM"));
		ssg.setSkullType(SkullType.PLAYER);
		s="-";
		if(rw.getPlayer(2)!=null)s=rw.getPlayer(2);
		if(s.equalsIgnoreCase("-"))
			ssg.setOwner("none");
			else
				ss.setOwner(s);
		ssg.setLore(Arrays.asList("&7 - Position: 2."
				,"&7Weight:","&7 - "+Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Weight")+"Kg"));
		ssg.setDisplayName(name(s));
		a.setItem(30, ssg.create(),w);

		ItemCreatorAPI sssw= TheAPI.getItemCreatorAPI(Material.getMaterial("LEGACY_SKULL_ITEM"));
		sssw.setSkullType(SkullType.PLAYER);
		s="-";
		if(rw.getPlayer(1)!=null)s=rw.getPlayer(1);
		if(s.equalsIgnoreCase("-"))
		sssw.setOwner("none");
		else
			sssw.setOwner(s);
		sssw.setDisplayName(name(s));
		sssw.setLore(Arrays.asList("&7 - Position: 3."
				,"&7Weight:","&7 - "+Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Weight")+"Kg"));
		a.setItem(31, sssw.create(),w);

			ItemCreatorAPI sw= TheAPI.getItemCreatorAPI(Material.getMaterial("LEGACY_SKULL_ITEM"));
			sw.setSkullType(SkullType.PLAYER);
		sw.setOwner(p.getName());
		sw.setDisplayName(p.getDisplayName());
		sw.setLore(Arrays.asList("&7 - Position: "+rw.getPosition(p.getName())+"."
				,"&7Weight:","&7 - "+Loader.me.getDouble("Players."+p.getName()+"."+type+"."+fish+".Weight")+"Kg"));
		a.setItem(33, sw.create(),w);

		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openGlobalFish(p, t);
			}});
		a.setItem(49, Create.createItem(Trans.back(), Material.BARRIER),w);
		a.open();
	}

	public static void openMy(Player p) {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI(p);
		a.setTitle("&6Your records &7- &eSelect fish type");
		a.setSize(54);
		Create.prepareInv(a);
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
			
		if(Loader.c.getBoolean("Options.UseGUI")) {
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					help.open(p, Type.Player);
				}});
			a.setItem(49,Create.createItem(Trans.back(), Material.BARRIER),w);
		}
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openMyFish(p,FishType.PUFFERFISH);
			}});
		a.setItem(20,Create.createItem(Trans.puf(), Material.PUFFERFISH),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openMyFish(p,FishType.TROPICAL_FISH);
			}});
		a.setItem(24,Create.createItem(Trans.tro(), Material.TROPICAL_FISH),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openMyFish(p,FishType.SALMON);
			}});
		a.setItem(30,Create.createItem(Trans.sal(), Material.SALMON),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openMyFish(p,FishType.COD);
			}});
		a.setItem(32,Create.createItem(Trans.cod(), Material.COD),w);
		a.open();
	}
	public static void openMyFish(Player p, FishType type) {
		String path = null;
		String title = null;
		Material i = null;
		switch(type) {
		case COD:
			i=Material.COD;
			path="Cod";
			title=Trans.cod();
			break;
		case PUFFERFISH:
			i=Material.PUFFERFISH;
			path="PufferFish";
			title=Trans.puf();
			break;
		case SALMON:
			i=Material.SALMON;
			path="Salmon";
			title=Trans.sal();
			break;
		case TROPICAL_FISH:
			i=Material.TROPICAL_FISH;
			path="TropicalFish";
			title=Trans.tro();
			break;
		}
		GUICreatorAPI a = TheAPI.getGUICreatorAPI(p);
		a.setTitle("&6Your record &7- "+title);
		a.setSize(54);
		Create.prepareInv(a);
		String pat = path;
		
		for(String s:Loader.c.getConfigurationSection("Types."+path).getKeys(false)) {
			String name = s;
			if(Loader.c.getString("Types."+path+"."+s+".Name")!=null)name=Loader.c.getString("Types."+path+"."+s+".Name");
			HashMap<Options, Object> w = new HashMap<Options, Object>();
			w.put(Options.CANT_PUT_ITEM, true);
			w.put(Options.CANT_BE_TAKEN, true);
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					onOpenMy(p,type, s, pat);
				}});
			a.addItem(Create.createItem(name, i),w);
			}
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openMy(p);
			}});
		a.setItem(49,Create.createItem(Trans.back(), Material.BARRIER),w);
		a.open();
	}
	public static void onOpenMy(Player p, FishType t, String fish, String type) {
		String name = Loader.c.getString("Types."+type+"."+fish+".Name");
		if(name==null)name=fish;

		GUICreatorAPI a = TheAPI.getGUICreatorAPI(p);
		a.setTitle("&6Your record on "+name);
		a.setSize(54);
		Create.prepareInv(a);
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
		ItemCreatorAPI s= TheAPI.getItemCreatorAPI(Material.getMaterial("LEGACY_SKULL_ITEM"));
		s.setSkullType(SkullType.PLAYER);
		s.setOwner(p.getName());
		s.setDisplayName(p.getDisplayName());
		String i = Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Length")+"";
		if(Loader.me.getString("Players."+s+"."+type+"."+fish+".Length")==null)i="-";
		s.setLore(Arrays.asList("&7Length:","&7 - "+i+"Cm"));
		a.setItem(20, s.create(),w);
		
		s.setOwner(p.getName());

		i = Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Weight")+"";
		if(Loader.me.getString("Players."+s+"."+type+"."+fish+".Weight")==null)i="-";
		s.setLore(Arrays.asList("&7Weight:","&7 - "+i+"Kg"));
		s.setDisplayName(p.getDisplayName());
		a.setItem(24, s.create(),w);
		
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openMyFish(p, t);
			}});
		a.setItem(49, Create.createItem(Trans.back(), Material.BARRIER),w);
		a.open();
	}
	public static void openBiomeSettting(Player p, String fish, String type, boolean edit) {
		String title = null;
		FishType t = null;
		if(type.equalsIgnoreCase("pufferfish")) {
			type="PufferFish";
			t=FishType.PUFFERFISH;
			title=Trans.puf();
		}
		if(type.equalsIgnoreCase("tropical_fish")||type.equalsIgnoreCase("tropicalfish")) {
			type="TropicalFish";
			t=FishType.TROPICAL_FISH;
			title=Trans.tro();
		}
		if(type.equalsIgnoreCase("cod")) {
			type="Cod";
			t=FishType.COD;
			title=Trans.cod();
		}
		if(type.equalsIgnoreCase("salmon")) {
			type="Salmon";
			t=FishType.SALMON;
			title=Trans.sal();
		}
		String typ =type;
		GUICreatorAPI a = TheAPI.getGUICreatorAPI(p);
		a.setTitle("&6Fishing per biome fish settings &7- "+title+" "+fish);
		a.setSize(54);
		Create.prepareInv(a);
		for(biomes s :ByBiome.biomes.values()) {
			Material aS = Material.STONE;
			if(Material.matchMaterial(Loader.TranslationsFile.getString("Words.Biomes."+s+".Icon"))!=null)
				aS=Material.matchMaterial(Loader.TranslationsFile.getString("Words.Biomes."+s+".Icon"));
			
			String name = ByBiome.getTran(s);
			
			List<String> lore = new ArrayList<String>();
			if(Loader.c.getString("Types."+typ+"."+fish+".Biomes")!=null)
				if(Loader.c.getStringList("Types."+type+"."+fish+".Biomes").contains(s.name())) {
			lore.add(Loader.TranslationsFile.getString("Biome-Added"));
				}else {
					lore=null;
				}
			HashMap<Options, Object> w = new HashMap<Options, Object>();
			w.put(Options.CANT_PUT_ITEM, true);
			w.put(Options.CANT_BE_TAKEN, true);
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					List<String> list = new ArrayList<String>();
					if(Loader.c.getString("Types."+typ+"."+fish+".Biomes")!=null)
					for(String s:Loader.c.getStringList("Types."+typ+"."+fish+".Biomes"))list.add(s);
						if(list.isEmpty()==false && list.contains(s.name())) {
							list.remove(s.name());
						}else {
							list.add(s.name());
						}
						Loader.c.set("Types."+typ+"."+fish+".Biomes",list);
						Loader.save();
						openBiomeSettting(p, fish, typ,edit);
				}});
			a.addItem(Create.createItem(name, aS,lore),w);
		}
		FishType r = t;
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				TheAPI_GUIs a = new TheAPI_GUIs();
				if(edit)
				a.openFishEditType(p, fish, r);
				else
					a.openFishCreatorType(p, fish, r);
			}});
		a.setItem(49, Create.createItem(Trans.back(), Material.BARRIER),w);
		a.open();
	}
}
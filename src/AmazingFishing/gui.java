package AmazingFishing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import AmazingFishing.ByBiome.biomes;
import AmazingFishing.help.Type;
import Main.Configs;
import Main.Loader;
import me.DevTec.ItemCreatorAPI;
import me.DevTec.RankingAPI;
import me.DevTec.TheAPI;
import me.DevTec.GUI.GUICreatorAPI;
import me.DevTec.GUI.GUICreatorAPI.Options;

public class gui {
	public static enum FishType {
		PUFFERFISH,TROPICAL_FISH,COD,SALMON
	}
	public static void openGlobal(Player p) {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI("&6Select fish type",54,p);
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
		GUICreatorAPI a = TheAPI.getGUICreatorAPI("&6Top 3 players &7- "+title,54,p);
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
	}
	private static String name(String s) {
		if(TheAPI.getPlayer(s)!=null)return TheAPI.getPlayer(s).getDisplayName();
		return s;
	}
	
	private static ItemStack createHead(String name, String owner, List<String> lore) {
		ItemCreatorAPI a= TheAPI.getItemCreatorAPI(Material.getMaterial("LEGACY_SKULL_ITEM") == null ? Material.getMaterial("SKULL_ITEM") : Material.getMaterial("LEGACY_SKULL_ITEM"));
		a.setSkullType("PLAYER");
		a.setLore(lore);
		a.setOwner(owner.equals("-") ? "MHF_Question" : owner);
		a.setDisplayName(name.equals("-") ? "none" : name);
		return a.create();
	}
	
	public static void onOpenFish(Player p, FishType t, String fish, String type) {
		String name = Loader.c.getString("Types."+type+"."+fish+".Name");
		if(name==null)name=fish;
		HashMap<String, BigDecimal> as = new HashMap<String, BigDecimal>();
		if(Loader.me.getString("Players")!=null)
		for(String s : Loader.me.getConfigurationSection("Players").getKeys(false)) {
			if(Loader.me.getString("Players."+s+"."+type+"."+fish+".Length")!=null)
			as.put(s, new BigDecimal(Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Length")));
		}
		RankingAPI<String> r = new RankingAPI<String>(as);
		
		HashMap<String, BigDecimal> aw = new HashMap<String, BigDecimal>();
		if(Loader.me.getString("Players")!=null)
		for(String s : Loader.me.getConfigurationSection("Players").getKeys(false)) {
			if(Loader.me.getString("Players."+s+"."+type+"."+fish+".Weight")!=null)
			aw.put(s, new BigDecimal(Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Weight")));
		}
		RankingAPI<String> rw = new RankingAPI<String>(aw);
		
		GUICreatorAPI a = TheAPI.getGUICreatorAPI("&6Top 3 players on "+name,54,p);
		Create.prepareInv(a);
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
		for(int i = 1; i < 4; ++i) {
		String s = "-";
		if(r.getObject(i)!=null)s=r.getObject(i).toString();
		a.setItem(20+(i-1), createHead(name(s),s,Arrays.asList("&7 - Position: "+i+"."
				,"&7Length:","&7 - "+Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Length")+"Cm")),w);
		}
		a.setItem(24,createHead(p.getDisplayName(),p.getName(),Arrays.asList("&7 - Position: "+r.getPosition(p.getName())+"."
				,"&7Length:","&7 - "+Loader.me.getDouble("Players."+p.getName()+"."+type+"."+fish+".Length")+"Cm")),w);


		for(int i = 1; i < 4; ++i) {
		String s = "-";
		if(rw.getObject(i)!=null)s=rw.getObject(i).toString();
		a.setItem(29+(i-1), createHead(name(s),s,Arrays.asList("&7 - Position: "+i+"."
				,"&7Weight:","&7 - "+Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Weight")+"Kg")),w);
		}
		a.setItem(33,createHead(p.getDisplayName(),p.getName(),Arrays.asList("&7 - Position: "+rw.getPosition(p.getName())+"."
				,"&7Weight:","&7 - "+Loader.me.getDouble("Players."+p.getName()+"."+type+"."+fish+".Weight")+"Kg")),w);
		
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openGlobalFish(p, t);
			}});
		a.setItem(49, Create.createItem(Trans.back(), Material.BARRIER),w);
	}

	public static void openMy(Player p) {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI("&6Your records &7- &eSelect fish type",54,p);
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
		GUICreatorAPI a = TheAPI.getGUICreatorAPI("&6Your record &7- "+title,54,p);
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
	}
	public static void onOpenMy(Player p, FishType t, String fish, String type) {
		String name = Loader.c.getString("Types."+type+"."+fish+".Name");
		if(name==null)name=fish;

		GUICreatorAPI a = TheAPI.getGUICreatorAPI("&6Your record on "+name,54,p);
		Create.prepareInv(a);
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
		String s = p.getName();
		String i = Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Length")+"";
		if(Loader.me.getString("Players."+s+"."+type+"."+fish+".Length")==null)i="-";
		a.setItem(20, createHead(p.getDisplayName(),s, Arrays.asList("&7Length:","&7 - "+i+"Cm")),w);

		i = Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Weight")+"";
		if(Loader.me.getString("Players."+s+"."+type+"."+fish+".Weight")==null)i="-";
		a.setItem(24, createHead(p.getDisplayName(), s, Arrays.asList("&7Weight:","&7 - "+i+"Kg")),w);
		
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openMyFish(p, t);
			}});
		a.setItem(49, Create.createItem(Trans.back(), Material.BARRIER),w);
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
		GUICreatorAPI a = TheAPI.getGUICreatorAPI("&6Per biome fish settings &7- "+title+" "+fish,54,p);
		Create.prepareInv(a);
		for(biomes s :ByBiome.biomes.values()) {
			Material aS = Material.STONE;
			if(Material.matchMaterial(Loader.TranslationsFile.getString("Words.Biomes."+s+".Icon"))!=null)
				aS=Material.matchMaterial(Loader.TranslationsFile.getString("Words.Biomes."+s+".Icon"));
			
			String name = ByBiome.getTran(s);
			boolean ench = false;
			List<String> lore = new ArrayList<String>();
			if(Loader.c.getString("Types."+typ+"."+fish+".Biomes")!=null)
				if(Loader.c.getStringList("Types."+type+"."+fish+".Biomes").contains(s.name())) {
					ench = true;
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
						Configs.c.save();
						openBiomeSettting(p, fish, typ,edit);
				}});
			ItemStack as = Create.createItem(name, aS,lore);
			if(ench) {
				as.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				ItemMeta ad = as.getItemMeta();
				ad.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				ad.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				as.setItemMeta(ad);
			}
			a.addItem(as,w);
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
	}
}
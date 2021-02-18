package AmazingFishing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import AmazingFishing.ByBiome.biomes;
import AmazingFishing.APIs.Enums.FishType;
import AmazingFishing.APIs.Enums.PlayerType;
import me.DevTec.AmazingFishing.Loader;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.apis.ItemCreatorAPI;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.ItemGUI;
import me.devtec.theapi.sortedmap.RankingAPI;

public class gui {
	/*public static enum FishType {
		PUFFERFISH,TROPICAL_FISH,COD,SALMON
	}*/
	public static void openGlobal(Player p) {
		GUI a = new GUI("&6Select fish type",54,p) {
			
			
			@Override
			public void onClose(Player arg0) {
			}
		};

		Create.prepareInv(a);
		if(Loader.c.getBoolean("Options.UseGUI")) {
			a.setItem(49,new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
				@Override
				public void onClick(Player p, GUI arg, ClickType ctype) {
					help.open(p, PlayerType.Player);
				}
			});
		}
		a.setItem(20,new ItemGUI(Create.createItem(Trans.puf(), Material.PUFFERFISH)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				openGlobalFish(p,FishType.PUFFERFISH);
			}
		});
		a.setItem(24,new ItemGUI(Create.createItem(Trans.tro(), Material.TROPICAL_FISH)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				openGlobalFish(p,FishType.TROPICAL_FISH);
			}
		});
		a.setItem(30,new ItemGUI(Create.createItem(Trans.sal(), Material.SALMON)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				openGlobalFish(p,FishType.SALMON);
			}
		});
		a.setItem(32,new ItemGUI(Create.createItem(Trans.cod(), Material.COD)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				openGlobalFish(p,FishType.COD);
			}
		});;
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
		GUI a = new GUI("&6Top 3 players &7- "+title,54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		String pat =path;
			a.setItem(49,new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
				@Override
				public void onClick(Player p, GUI arg, ClickType ctype) {
					openGlobal(p);
				}
			});
		for(String s:Loader.c.getKeys("Types."+path)) {
			String name = s;
			if(Loader.c.exists("Types."+path+"."+s+".Name"))name=Loader.c.getString("Types."+path+"."+s+".Name");
			a.addItem(new ItemGUI(Create.createItem(name, i)){
				@Override
				public void onClick(Player p, GUI arg, ClickType ctype) {
					onOpenFish(p,type,s,pat);
				}
			});
			}
	}

	private static String name(String s) {
		if(TheAPI.getPlayer(s)!=null)return TheAPI.getPlayer(s).getDisplayName();
		return s;
	}
	
	private static ItemStack createHead(String name, String owner, List<String> lore) {
		ItemCreatorAPI a= new ItemCreatorAPI(Material.getMaterial("LEGACY_SKULL_ITEM") == null ? Material.getMaterial("SKULL_ITEM") : Material.getMaterial("LEGACY_SKULL_ITEM"));
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
		if(Loader.me.exists("Players"))
		for(String s : Loader.me.getKeys("Players")) {
			if(Loader.me.exists("Players."+s+"."+type+"."+fish+".Length"))
			as.put(s, new BigDecimal(Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Length")));
		}
		RankingAPI<String, BigDecimal> r = new RankingAPI<>(as);
		
		HashMap<String, BigDecimal> aw = new HashMap<String, BigDecimal>();
		if(Loader.me.exists("Players"))
		for(String s : Loader.me.getKeys("Players")) {
			if(Loader.me.exists("Players."+s+"."+type+"."+fish+".Weight"))
			aw.put(s, new BigDecimal(Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Weight")));
		}
		RankingAPI<String, BigDecimal> rw = new RankingAPI<>(aw);
		
		GUI a = new GUI("&6Top 3 players on "+name,54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		for(int i = 1; i < 4; ++i) {
		String s = "-";
		if(r.getObject(i)!=null)s=r.getObject(i).toString();
		a.setItem(20+(i-1), new ItemGUI(createHead(name(s),s,Arrays.asList("&7 - Position: "+i+"."
				,"&7Length:","&7 - "+Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Length")+"Cm"))){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
			}
		});
		}
		a.setItem(24,new ItemGUI(createHead(p.getDisplayName(),p.getName(),Arrays.asList("&7 - Position: "+r.getPosition(p.getName())+"."
				,"&7Length:","&7 - "+Loader.me.getDouble("Players."+p.getName()+"."+type+"."+fish+".Length")+"Cm"))){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
			}
		});

		for(int i = 1; i < 4; ++i) {
		String s = "-";
		if(rw.getObject(i)!=null)s=rw.getObject(i).toString();
		a.setItem(29+(i-1), new ItemGUI(createHead(name(s),s,Arrays.asList("&7 - Position: "+i+"."
				,"&7Weight:","&7 - "+Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Weight")+"Kg"))){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
			}
		});
		}
		a.setItem(33,new ItemGUI(createHead(p.getDisplayName(),p.getName(),Arrays.asList("&7 - Position: "+rw.getPosition(p.getName())+"."
				,"&7Weight:","&7 - "+Loader.me.getDouble("Players."+p.getName()+"."+type+"."+fish+".Weight")+"Kg"))){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
			}
		});
		
		a.setItem(49, new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				openGlobalFish(p, t);
			}
		});
	}

	public static void openMy(Player p) {
		GUI a = new GUI("&6Your records &7- &eSelect fish type",54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		if(Loader.c.getBoolean("Options.UseGUI")) {

			a.setItem(49,new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
				@Override
				public void onClick(Player p, GUI arg, ClickType ctype) {
					help.open(p, PlayerType.Player);
				}
			});
		}
		a.setItem(20,new ItemGUI(Create.createItem(Trans.puf(), Material.PUFFERFISH)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				openMyFish(p,FishType.PUFFERFISH);
			}
		});

		a.setItem(24,new ItemGUI(Create.createItem(Trans.tro(), Material.TROPICAL_FISH)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				openMyFish(p,FishType.TROPICAL_FISH);
			}
		});
		a.setItem(30,new ItemGUI(Create.createItem(Trans.sal(), Material.SALMON)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				openMyFish(p,FishType.SALMON);
			}
		});
		a.setItem(32,new ItemGUI(Create.createItem(Trans.cod(), Material.COD)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				openMyFish(p,FishType.COD);
			}
		});
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
		GUI a = new GUI("&6Your record &7- "+title,54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		String pat = path;
		
		for(String s:Loader.c.getKeys("Types."+path)) {
			String name = s;
			if(Loader.c.exists("Types."+path+"."+s+".Name"))name=Loader.c.getString("Types."+path+"."+s+".Name");
			a.addItem(new ItemGUI(Create.createItem(name, i)){
				@Override
				public void onClick(Player p, GUI arg, ClickType ctype) {
					onOpenMy(p,type, s, pat);
				}
			});
			}
		a.setItem(49,new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				openMy(p);
			}
		});
	}

	public static void onOpenMy(Player p, FishType t, String fish, String type) {
		String name = Loader.c.getString("Types."+type+"."+fish+".Name");
		if(name==null)name=fish;

		GUI a = new GUI("&6Your record on "+name,54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		String s = p.getName();
		String i = Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Length")+"";
		if(!Loader.me.exists("Players."+s+"."+type+"."+fish+".Length"))i="-";
		a.setItem(20, new ItemGUI(createHead(p.getDisplayName(),s, Arrays.asList("&7Length:","&7 - "+i+"Cm"))){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
			}
		});

		i = Loader.me.getDouble("Players."+s+"."+type+"."+fish+".Weight")+"";
		if(!Loader.me.exists("Players."+s+"."+type+"."+fish+".Weight"))i="-";
		a.setItem(24, new ItemGUI(createHead(p.getDisplayName(), s, Arrays.asList("&7Weight:","&7 - "+i+"Kg"))){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
			}
		});
		
		a.setItem(49, new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				openMyFish(p, t);
			}
		});
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
		GUI a = new GUI("&6Per biome fish settings &7- "+title+" "+fish,54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		for(biomes s :ByBiome.biomes.values()) {
			Material aS = Material.STONE;
			if(Material.matchMaterial(Loader.TranslationsFile.getString("Words.Biomes."+s+".Icon"))!=null)
				aS=Material.matchMaterial(Loader.TranslationsFile.getString("Words.Biomes."+s+".Icon"));
			
			String name = ByBiome.getTran(s);
			boolean ench = false;
			List<String> lore = new ArrayList<String>();
			if(Loader.c.exists("Types."+typ+"."+fish+".Biomes"))
				if(Loader.c.getStringList("Types."+type+"."+fish+".Biomes").contains(s.name())) {
					ench = true;
			lore.add(Loader.TranslationsFile.getString("Biome-Added"));
				}else {
					lore=null;
				}
			ItemStack as = Create.createItem(name, aS,lore);
			if(ench) {
				as.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				ItemMeta ad = as.getItemMeta();
				ad.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				ad.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				as.setItemMeta(ad);
			}
			a.addItem(new ItemGUI(as){
				@Override
				public void onClick(Player p, GUI arg, ClickType ctype) {
					List<String> list = new ArrayList<String>();
					if(Loader.c.exists("Types."+typ+"."+fish+".Biomes"))
					for(String s:Loader.c.getStringList("Types."+typ+"."+fish+".Biomes"))list.add(s);
						if(list.isEmpty()==false && list.contains(s.name())) {
							list.remove(s.name());
						}else {
							list.add(s.name());
						}
						Loader.c.set("Types."+typ+"."+fish+".Biomes",list);
						Loader.c.save();
						openBiomeSettting(p, fish, typ,edit);
				}
			});
		}
		FishType r = t;
		a.setItem(49, new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType ctype) {
				TheAPI_GUIs a = new TheAPI_GUIs();
				if(edit)
				a.openFishEditType(p, fish, r);
				else
					a.openFishCreatorType(p, fish, r);
			}
		});
	}

}
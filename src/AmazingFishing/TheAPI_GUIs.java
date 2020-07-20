package AmazingFishing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.sk89q.jchronic.Options;

import AmazingFishing.gui.FishType;
import AmazingFishing.help.Type;
import Main.Loader;
import me.DevTec.TheAPI;
import me.DevTec.GUI.GUICreatorAPI;
import me.DevTec.GUI.ItemGUI;

public class TheAPI_GUIs {
	public void editShop(Player p) {
		
	}
	public void open(Player p) {
		GUICreatorAPI a = new GUICreatorAPI("&6Manager &7- &6Selector",54,p) {
			@Override
			public void onClose(Player arg0) {
			}
		};

		Create.prepareInv(a);
		if(Loader.c.getBoolean("Options.UseGUI")) {
			a.setItem(49,new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
					help.open(p, Type.Admin);
				}
			});
		}else {
		a.setItem(49,new ItemGUI(Create.createItem("&bAmazing Fishing", Material.KNOWLEDGE_BOOK,
				Arrays.asList("&9Version &bV"+Loader.plugin.getDescription().getVersion(),"&9Created by &bStraiker123","&3Developed by Houska02"))){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
			}
		});
		}
		if(p.hasPermission("amazingfishing.editor.settings"))
		a.setItem(20,new ItemGUI(Create.createItem(Trans.settings(), Material.REDSTONE_BLOCK)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openSetting(p);
			}
		});
		if(p.hasPermission("amazingfishing.editor.enchants"))
		a.setItem(24,new ItemGUI(Create.createItem(Trans.enchants(), Material.ENCHANTING_TABLE)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				ench.openEnchanter(p);
			}
		});
		if(p.hasPermission("amazingfishing.editor.fish"))
		a.setItem(31,new ItemGUI(Create.createItem(Trans.fishs(), Material.COD)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openFish(p);
			}
		});

		if(p.hasPermission("amazingfishing.editor.treasures"))
		a.setItem(13,new ItemGUI(Create.createItem(Trans.treasures(), Material.CHEST)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openTreas(p);
			}
		});
	}

	public void openFish(Player p) {
		GUICreatorAPI a = new GUICreatorAPI("&6Manager &7- "+Trans.fishs(),54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
			a.setItem(49,new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
					open(p);
				}
			});
		a.setItem(20,new ItemGUI(Create.createItem(Trans.cre(), Material.GREEN_DYE)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openFishCreate(p);
			}
		});
		a.setItem(24,new ItemGUI(Create.createItem(Trans.del(), Material.RED_DYE)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openFishDelete(p);
			}
		});

		a.setItem(31,new ItemGUI(Create.createItem(Trans.edit(), Material.ORANGE_DYE)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openFishEdit(p);
			}
		});
	}	
	public void openFishEdit(Player p) {
		GUICreatorAPI a = new GUICreatorAPI("&6Editor",54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		a.setItem(20,new ItemGUI(Create.createItem(Trans.puf(), Material.PUFFERFISH)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openFishEditSelect(p, FishType.PUFFERFISH);
			}
		});
		a.setItem(24,new ItemGUI(Create.createItem(Trans.tro(), Material.TROPICAL_FISH)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openFishEditSelect(p, FishType.TROPICAL_FISH);
			}
		});
		a.setItem(30,new ItemGUI(Create.createItem(Trans.sal(), Material.SALMON)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openFishEditSelect(p, FishType.SALMON);
			}
		});
		a.setItem(32,new ItemGUI(Create.createItem(Trans.cod(), Material.COD)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openFishEditSelect(p, FishType.COD);
			}
		});

		a.setItem(49,new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openFish(p);
			}
		});;
	}
	public void openFishEditSelect(Player p, FishType type) {
		String title = "";
		Material m = null;
		String path = "";
		switch(type) {
		case COD:
			title = Trans.cod();
			m=Material.COD;
			path="Cod";
			break;
		case SALMON:
			title=Trans.sal();
			m=Material.SALMON;
			path="Salmon";
			break;
		case PUFFERFISH:
			title=Trans.puf();
			path="PufferFish";
			m=Material.PUFFERFISH;
			break;
		case TROPICAL_FISH:
			m=Material.TROPICAL_FISH;
			title=Trans.tro();
			path="TropicalFish";
			break;
		}
		GUICreatorAPI a = new GUICreatorAPI("&6Selector &7- "+title,54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		String pat=path;
		if(Loader.c.getString("Types."+path)!=null)
			for(String s:Loader.c.getConfigurationSection("Types."+path).getKeys(false)) {
				String name = s;
				if(Loader.c.getString("Types."+path+"."+s+".Name")!=null)name=Loader.c.getString("Types."+path+"."+s+".Name");
				a.addItem(new ItemGUI(Create.createItem(name, m)){
					@Override
					public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
						Loader.c.set("Edit-"+pat+"."+p.getName()+".Fish",s);
						Loader.save();
						openFishEditType(p,s, type);
					}
				});
				}
		a.setItem(49,new ItemGUI(Create.createItem(Trans.cancel(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openFishEdit(p);
			}
		});
	}
	
	public void openFishEditType(Player p,String fish, FishType type){
		String title = "";
		String path = "";
		switch(type) {
		case COD:
			path = "Cod";
			title = Trans.cod();
			break;
		case SALMON:
			path = "Salmon";
			title=Trans.sal();
			break;
		case PUFFERFISH:
			path = "PufferFish";
			title=Trans.puf();
			break;
		case TROPICAL_FISH:
			path = "TropicalFish";
			title=Trans.tro();
			break;
		}
		String cm = Loader.c.getString("Types."+path+"."+fish+".MaxCm");
		String money = Loader.c.getString("Types."+path+"."+fish+".Money");
		String xp = Loader.c.getString("Types."+path+"."+fish+".Xp");
		String points = Loader.c.getString("Types."+path+"."+fish+".Points");
		String name = Loader.c.getString("Types."+path+"."+fish+".Name");
		String chance = Loader.c.getString("Types."+path+"."+fish+".Chance");
		GUICreatorAPI a = new GUICreatorAPI("&6Editor &7- "+title+" &7"+fish,54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		String pat = path;
		String ww = path;
		if(path.equals("TropicalFish"))ww="Tropical_Fish";
		if(path.equals("PufferFish"))ww="Pufferfish";
		String pats = ww;
		if(name != null) {
		a.setItem(22, new ItemGUI(Create.createItem(Trans.name(), Material.NAME_TAG,Arrays.asList("&b>> "+name))){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				p.getOpenInventory().close();
				Loader.c.set("Edit-"+pats+"."+p.getName()+".Fish", fish);
				Loader.c.set("Edit-"+pats+"."+p.getName()+".Type", "Name");
				Loader.save();
				TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteName", 1), Loader.get("WriteName", 2));
			}
			});
		}else 
			a.setItem(22,new ItemGUI(Create.createItem(Trans.name(), Material.NAME_TAG)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
					p.getOpenInventory().close();
					Loader.c.set("Edit-"+pats+"."+p.getName()+".Fish", fish);
					Loader.c.set("Edit-"+pats+"."+p.getName()+".Type", "Name");
					Loader.save();
					TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteName", 1), Loader.get("WriteName", 2));
				}
			});
			
		if(xp != null) {
			a.setItem(30,new ItemGUI(Create.createItem(Trans.exp(), Material.EXPERIENCE_BOTTLE,Arrays.asList("&9"+xp+"Exps"))){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
					p.getOpenInventory().close();
					Loader.c.set("Edit-"+pats+"."+p.getName()+".Fish", fish);
					Loader.c.set("Edit-"+pats+"."+p.getName()+".Type", "Exp");
					Loader.save();
					TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteExp", 1), Loader.get("WriteExp", 2));
				}
			});

		}else 
			a.setItem(30,new ItemGUI(Create.createItem(Trans.exp(), Material.EXPERIENCE_BOTTLE)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
					p.getOpenInventory().close();
					Loader.c.set("Edit-"+pats+"."+p.getName()+".Fish", fish);
					Loader.c.set("Edit-"+pats+"."+p.getName()+".Type", "Exp");
					Loader.save();
					TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteExp", 1), Loader.get("WriteExp", 2));
				}
			});
		if(points != null) {
			a.setItem(29,new ItemGUI(Create.createItem(Trans.point(), Material.LAPIS_LAZULI,Arrays.asList("&d"+points+"Points"))){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
					p.getOpenInventory().close();
					Loader.c.set("Edit-"+pats+"."+p.getName()+".Fish", fish);
					Loader.c.set("Edit-"+pats+"."+p.getName()+".Type", "Points");
					Loader.save();
					TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WritePoint", 1), Loader.get("WritePoint", 2));
				}
			});
		}else 
			a.setItem(29,new ItemGUI(Create.createItem(Trans.point(), Material.LAPIS_LAZULI)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
					p.getOpenInventory().close();
					Loader.c.set("Edit-"+pats+"."+p.getName()+".Fish", fish);
					Loader.c.set("Edit-"+pats+"."+p.getName()+".Type", "Points");
					Loader.save();
					TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WritePoint", 1), Loader.get("WritePoint", 2));
				}
			});
		if(money != null) {
		a.setItem(32, new ItemGUI(Create.createItem(Trans.money(), Material.GOLD_INGOT,Arrays.asList("&6"+money+"$"))){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				p.getOpenInventory().close();
				Loader.c.set("Edit-"+pats+"."+p.getName()+".Fish", fish);
				Loader.c.set("Edit-"+pats+"."+p.getName()+".Type", "Money");
				Loader.save();
				TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteMoney", 1), Loader.get("WriteMoney", 2));
			}
		});
		}else 
			a.setItem(32,new ItemGUI(Create.createItem(Trans.money(), Material.GOLD_INGOT)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
					p.getOpenInventory().close();
					Loader.c.set("Edit-"+pats+"."+p.getName()+".Fish", fish);
					Loader.c.set("Edit-"+pats+"."+p.getName()+".Type", "Money");
					Loader.save();
					TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteMoney", 1), Loader.get("WriteMoney", 2));
				}
			});
		if(chance != null) {
		a.setItem(41,new ItemGUI(Create.createItem(Trans.chance(), Material.PAPER,Arrays.asList("&9"+chance+"%"))){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				p.getOpenInventory().close();
				Loader.c.set("Edit-"+pats+"."+p.getName()+".Fish", fish);
				Loader.c.set("Edit-"+pats+"."+p.getName()+".Type", "Chance");
				Loader.save();
				TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteChance", 1), Loader.get("WriteChance", 2));
			}
		});
		}else 
			a.setItem(41,new ItemGUI(Create.createItem(Trans.chance(), Material.PAPER)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
					p.getOpenInventory().close();
					Loader.c.set("Edit-"+pats+"."+p.getName()+".Fish", fish);
					Loader.c.set("Edit-"+pats+"."+p.getName()+".Type", "Chance");
					Loader.save();
					TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteChance", 1), Loader.get("WriteChance", 2));
				}
			});
		if(cm != null) {
			a.setItem(31,new ItemGUI(Create.createItem(Trans.cm(), Material.PAPER,Arrays.asList("&3Max "+cm+"Cm"))){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
					p.getOpenInventory().close();
					Loader.c.set("Edit-"+pats+"."+p.getName()+".Fish", fish);
					Loader.c.set("Edit-"+pats+"."+p.getName()+".Type", "Cm");
					Loader.save();
					TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteLength", 1), Loader.get("WriteLength", 2));
				}
			});
		}else
			a.setItem(31,new ItemGUI(Create.createItem(Trans.cm(), Material.PAPER)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
					p.getOpenInventory().close();
					Loader.c.set("Edit-"+pats+"."+p.getName()+".Fish", fish);
					Loader.c.set("Edit-"+pats+"."+p.getName()+".Type", "Cm");
					Loader.save();
					TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteLength", 1), Loader.get("WriteLength", 2));
				}
			});
		a.setItem(39,new ItemGUI(Create.createItem(Trans.perbiome(), Material.CHEST)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				gui.openBiomeSettting(p,fish,pat,true);
			}
		});
		a.setItem(40,new ItemGUI(Create.createItem(Trans.save(), Material.EMERALD_BLOCK)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				Loader.c.set("Edit-"+pats+"."+p.getName(), null);
				Loader.c.set("Creating-"+pats+"."+p.getName(), null);
				Loader.save();
				openFishEditSelect(p, type);
			}
		});
	}

	public void openFishDelete(Player p) {
		GUICreatorAPI a = new GUICreatorAPI("&cRemover",54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		a.setItem(20,new ItemGUI(Create.createItem(Trans.puf(), Material.PUFFERFISH)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openFishDeleteType(p, FishType.PUFFERFISH);
			}
		});
		a.setItem(24,new ItemGUI(Create.createItem(Trans.tro(), Material.TROPICAL_FISH)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openFishDeleteType(p, FishType.TROPICAL_FISH);
			}
		});
		a.setItem(30,new ItemGUI(Create.createItem(Trans.sal(), Material.SALMON)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openFishDeleteType(p, FishType.SALMON);
			}
		});
		a.setItem(32,new ItemGUI(Create.createItem(Trans.cod(), Material.COD)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openFishDeleteType(p, FishType.COD);
			}
		});
		a.setItem(49,new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openFish(p);
			}
		});
	}

	public void openFishDeleteType(Player p, FishType type) {
		String path = null;
		String title = null;
		Material i = null;
		switch(type) {
		case COD:
			path = "Cod";
			title = Trans.cod();
			i=Material.COD;
			break;
		case SALMON:
			i=Material.SALMON;
			path = "Salmon";
			title=Trans.sal();
			break;
		case PUFFERFISH:
			i=Material.PUFFERFISH;
			path = "PufferFish";
			title=Trans.puf();
			break;
		case TROPICAL_FISH:
			i=Material.TROPICAL_FISH;
			path = "TropicalFish";
			title=Trans.tro();
			break;
		}
		GUICreatorAPI a = new GUICreatorAPI("&cRemover &7- "+title,54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		String pat = path;
		if(Loader.c.getString("Types."+path)!=null)
		for(String s:Loader.c.getConfigurationSection("Types."+path).getKeys(false)) {
			String name = s;
			if(Loader.c.getString("Types."+path+"."+s+".Name")!=null)name=Loader.c.getString("Types."+path+"."+s+".Name");
			a.addItem(new ItemGUI(Create.createItem(name, i)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
					Loader.c.set("Types."+pat+"."+s, null);
					Loader.save();
					openFishDeleteType(p, type);
				}
			});
			}
		a.setItem(49,new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openFishDelete(p);
			}
		});
	}
	
	public void openFishCreate(Player p) {
		GUICreatorAPI a = new GUICreatorAPI("&aCreator",54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		a.setItem(20,new ItemGUI(Create.createItem(Trans.puf(), Material.PUFFERFISH)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openFishCreatorType(p,null, FishType.PUFFERFISH);
			}
		});
		a.setItem(24,new ItemGUI(Create.createItem(Trans.tro(), Material.TROPICAL_FISH)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openFishCreatorType(p,null, FishType.TROPICAL_FISH);
			}
		});

		a.setItem(30,new ItemGUI(Create.createItem(Trans.sal(), Material.SALMON)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openFishCreatorType(p,null, FishType.SALMON);
			}
		});
		a.setItem(32,new ItemGUI(Create.createItem(Trans.cod(), Material.COD)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openFishCreatorType(p,null, FishType.COD);
			}
		});
		a.setItem(49,new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
			openFish(p);
			}
		});
	}
	
	public void openFishCreatorType(Player p, String fish,FishType type) {
		String title = "";
		String path = "";
		String where = "";
		switch(type) {
		case COD:
			path = "Cod";
			where=path;
			title = Trans.cod();
			break;
		case SALMON:
			path = "Salmon";
			where=path;
			title=Trans.sal();
			break;
		case PUFFERFISH:
			path = "PufferFish";
			where="Pufferfish";
			title=Trans.puf();
			break;
		case TROPICAL_FISH:
			path = "TropicalFish";
			where="Tropical_Fish";
			title=Trans.tro();
			break;
		}
		String cm = null;
		String money = null;
		String xp = null;
		String points = null;
		String name = null;
		String chance = null;
		if(fish != null) {
			 title= title+" "+fish;
		 cm = get.getCm(p, where);
		 money = get.getMoney(p, where);
		 xp = get.getXp(p, where);
		 points = get.getPoints(p, where);
		 name = get.getName(p, where);
		 chance = get.getChance(p, where);
		}
		GUICreatorAPI a = new GUICreatorAPI("&aCreator &7- "+title,54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		String wd = where;
		a.setItem(22,new ItemGUI(name != null?Create.createItem(Trans.name(), Material.NAME_TAG,Arrays.asList("&b>> "+name)):Create.createItem(Trans.name(), Material.NAME_TAG)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				p.getOpenInventory().close();
				Loader.c.set("Creating-"+wd+"."+p.getName()+".Type", "Name");
				Loader.save();
				TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteName", 1), Loader.get("WriteName", 2));
			}
		});

			a.setItem(30,new ItemGUI(xp!=null?Create.createItem(Trans.exp(), Material.EXPERIENCE_BOTTLE,Arrays.asList("&9"+xp+"Exps")):
				Create.createItem(Trans.exp(), Material.EXPERIENCE_BOTTLE)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					p.getOpenInventory().close();
					Loader.c.set("Creating-"+wd+"."+p.getName()+".Type", "Exp");
					Loader.save();
					TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteExp", 1), Loader.get("WriteExp", 2));
				}
			});
			a.setItem(29,new ItemGUI(points != null?Create.createItem(Trans.point(), Material.LAPIS_LAZULI,Arrays.asList("&d"+points+"Points")):
				Create.createItem(Trans.point(), Material.LAPIS_LAZULI)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					p.getOpenInventory().close();
					Loader.c.set("Creating-"+wd+"."+p.getName()+".Type", "Points");
					Loader.save();
					TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WritePoint", 1), Loader.get("WritePoint", 2));
				}
			});
		a.setItem(32,new ItemGUI(money != null?Create.createItem(Trans.money(), Material.GOLD_INGOT,Arrays.asList("&6"+money+"$")):
			Create.createItem(Trans.money(), Material.GOLD_INGOT)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				p.getOpenInventory().close();
				Loader.c.set("Creating-"+wd+"."+p.getName()+".Type", "Money");
				Loader.save();
				TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteMoney", 1), Loader.get("WriteMoney", 2));
			}
		});
		a.setItem(41, new ItemGUI(chance != null?Create.createItem(Trans.chance(), Material.PAPER,Arrays.asList("&9"+chance+"%")):
			Create.createItem(Trans.chance(), Material.PAPER)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				p.getOpenInventory().close();
				Loader.c.set("Creating-"+wd+"."+p.getName()+".Type", "Chance");
				Loader.save();
				TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteChance", 1), Loader.get("WriteChance", 2));
			}
		});

			a.setItem(31,new ItemGUI(cm != null?Create.createItem(Trans.cm(), Material.PAPER,Arrays.asList("&3Max "+cm+"Cm")):
				Create.createItem(Trans.cm(), Material.PAPER)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					p.getOpenInventory().close();
					Loader.c.set("Creating-"+wd+"."+p.getName()+".Type", "Cm");
					Loader.save();
					TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteLength", 1), Loader.get("WriteLength", 2));
				}
			});
		a.setItem(40,new ItemGUI(Create.createItem(Trans.cancel(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				Loader.c.set("Edit-"+wd+"."+p.getName(), null);
				Loader.c.set("Creating-"+wd+"."+p.getName(), null);
				Loader.save();
				openFishCreate(p);
			}
		});
		String pat = path;
		a.setItem(39,new ItemGUI(Create.createItem(Trans.perbiome(), Material.CHEST)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				Loader.c.set("Creating-"+wd+"."+p.getName()+".Biome",true);
				Loader.save();
			gui.openBiomeSettting(p,fish,wd,false);
			}
		});
		a.setItem(33,new ItemGUI(Create.createItem(Trans.save(), Material.EMERALD_BLOCK)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				get.finish(p, pat, false);
			Loader.c.set("Edit-"+wd+"."+p.getName(), null);
			Loader.c.set("Creating-"+wd+"."+p.getName(), null);
			Loader.save();
			openFishCreate(p);
			}
		});
	}
	
 	public void openEnchantTable(Player p) {
		GUICreatorAPI a = new GUICreatorAPI("&5Enchant Table",54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
			a.setItem(49,new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					help.open(p, Type.Player);
				}
			});
			a.setItem(20,new ItemGUI(Create.createItem("&aAdd Enchant", Material.CRAFTING_TABLE)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					openEnchanterPlace(p,"add");
				}
			});

			a.setItem(22, new ItemGUI(Create.createItem("&2Retrive Rod", Material.FISHING_ROD)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					if(Loader.me.getString("Players."+p.getName()+".SavedRod")!=null) {
					ItemStack i = Loader.me.getItemStack("Players."+p.getName()+".SavedRod");
					TheAPI.giveItem(p, i);
					Loader.me.set("Players."+p.getName()+".SavedRod",null);
					Loader.saveChatMe();
					}
				}
			});
			a.setItem(24,new ItemGUI(Create.createItem("&6Upgrade Enchant", Material.ANVIL)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					openEnchanterPlace(p,"up");
				}
			});
		}
	
	public void openEnchanterPlace(Player p, String sel) {
		GUICreatorAPI a = new GUICreatorAPI("&5Enchant Table &7- &6Select fishing rod",54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		if(p.getInventory().getContents()!=null)
		for(ItemStack i : p.getInventory().getContents()) {
			if(i==null)continue;
			if(i.getType()!=Material.FISHING_ROD)continue;
			a.addItem(new ItemGUI(i){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					Normal.takeRod(p, i);
					p.getInventory().removeItem(i);
					openEnchantSel(p,sel);
				}
			});
		}
		a.setItem(49,new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				openEnchantTable(p);
			}
		});
	}

	public void openEnchantSel(Player p, String sel) {
		String select = null;
		Material mat = null;
		if(sel.equalsIgnoreCase("add")) {
			mat=Material.ENCHANTED_BOOK;
			select="&aAdd Enchant";
		}
		if(sel.equalsIgnoreCase("up")) {
			mat=Material.PAPER;
			select="&6Upgrade Enchant";
		}
		GUICreatorAPI a = new GUICreatorAPI("&5Enchant Table &7- &6"+select,54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		a.setItem(4,new ItemGUI(Create.createItem("&9"+Points.getBal(p.getName())+" Points", Material.LAPIS_LAZULI)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				TheAPI.giveItem(p, Normal.getRod(p));
				Loader.me.set("Players."+p.getName()+".SavedRod",null);
				Loader.saveChatMe();
			}
		});
		a.setItem(1,new ItemGUI(Normal.getRod(p)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				TheAPI.giveItem(p, Normal.getRod(p));
				Loader.me.set("Players."+p.getName()+".SavedRod",null);
				Loader.saveChatMe();
			}
		});
		a.setItem(6,new ItemGUI(Normal.getRod(p)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				TheAPI.giveItem(p, Normal.getRod(p));
				Loader.me.set("Players."+p.getName()+".SavedRod",null);
				Loader.saveChatMe();
			}
		});
		a.setItem(47,new ItemGUI(Normal.getRod(p)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				TheAPI.giveItem(p, Normal.getRod(p));
				Loader.me.set("Players."+p.getName()+".SavedRod",null);
				Loader.saveChatMe();
			}
		});
		a.setItem(51,new ItemGUI(Normal.getRod(p)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				TheAPI.giveItem(p, Normal.getRod(p));
				Loader.me.set("Players."+p.getName()+".SavedRod",null);
				Loader.saveChatMe();
			}
		});

		a.setItem(49,new ItemGUI(Create.createItem(Trans.cancel(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				openEnchantTable(p);
			}
		});
			if(Loader.c.getString("Enchants")!=null)
		for(String s:Loader.c.getConfigurationSection("Enchants").getKeys(false)) {
			CEnch c = Loader.getEnchantment(s);
			String name = s;
			if(Loader.c.getString("Enchants."+s+".Name")!=null)name=Loader.c.getString("Enchants."+s+".Name");
			 double cs= Loader.c.getDouble("Enchants."+s+".Cost");
			 boolean has = false;
			 int level = 0;
			 ItemStack f = Normal.getRod(p);
					if(c != null && f.getEnchantments().containsKey(c)) {
						has=true;
						level=f.getEnchantments().get(c);
							cs=cs+level+level/cs;
					}
				int l = level;
				boolean h = has;
				double cost = cs;
			List<String> lore = new ArrayList<String>();
			
			for(String g:Loader.c.getStringList("Enchants."+s+".Description"))lore.add(g
					.replace("%cost%", String.format("%2.02f",cost).replace(",", ".")));
			if(sel.equalsIgnoreCase("up")) {
				if(has) {
			a.addItem(new ItemGUI(Create.createItem(name, mat,lore)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {

					if(Points.has(p.getName(), cost)) {
					if(sel.equalsIgnoreCase("up")) {
					if(!h) {
						p.getOpenInventory().close();
						Loader.msgCmd(Loader.s("Prefix")+"&cEnchant isn't on your rod!", p);
						return;
					}
						Points.take(p.getName(), cost);
						f.addUnsafeEnchantment(c, l+1);
						ItemMeta m = f.getItemMeta();
						List<String> as = m.getLore() != null ? m.getLore() : new ArrayList<String>();
						as.remove(TheAPI.colorize(c.getName()+" "+Utils.trasfer(l)));
						as.add(TheAPI.colorize(c.getName()+" "+Utils.trasfer(l+1)));
						m.setLore(as);
						f.setItemMeta(m);
						TheAPI.giveItem(p, f);
						Loader.me.set("Players."+p.getName()+".SavedRod",null);
						Loader.saveChatMe();
						openEnchantTable(p);
					}else {
						if(h) {
							p.getOpenInventory().close();
							Loader.msgCmd(Loader.s("Prefix")+"&6Enchant is already on your rod!", p);
							return;
						}
						Points.take(p.getName(), cost);
						f.addUnsafeEnchantment(c, l+1);
						ItemMeta m = f.getItemMeta();
						List<String> as = m.getLore() != null ? m.getLore() : new ArrayList<String>();
						as.add(TheAPI.colorize(c.getName()+" "+Utils.trasfer(l+1)));
						m.setLore(as);
						f.setItemMeta(m);
						TheAPI.giveItem(p, f);
						Loader.me.set("Players."+p.getName()+".SavedRod",null);
						Loader.saveChatMe();
						openEnchantTable(p);
					}
					return;
				}
				Loader.msgCmd(Loader.s("Prefix")+"&cYou have lack of points!", p);
				return;
				}
			});
				}
			}
			if(sel.equalsIgnoreCase("add")) {
				if(!has) {
			a.addItem(new ItemGUI(Create.createItem(name, mat,lore)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {

					if(Points.has(p.getName(), cost)) {
					if(sel.equalsIgnoreCase("up")) {
					if(!h) {
						p.getOpenInventory().close();
						Loader.msgCmd(Loader.s("Prefix")+"&cEnchant isn't on your rod!", p);
						return;
					}
						Points.take(p.getName(), cost);
						f.addUnsafeEnchantment(c, l+1);
						ItemMeta m = f.getItemMeta();
						List<String> as = m.getLore() != null ? m.getLore() : new ArrayList<String>();
						as.remove(TheAPI.colorize(c.getName()+" "+Utils.trasfer(l)));
						as.add(TheAPI.colorize(c.getName()+" "+Utils.trasfer(l+1)));
						m.setLore(as);
						f.setItemMeta(m);
						TheAPI.giveItem(p, f);
						Loader.me.set("Players."+p.getName()+".SavedRod",null);
						Loader.saveChatMe();
						openEnchantTable(p);
					}else {
						if(h) {
							p.getOpenInventory().close();
							Loader.msgCmd(Loader.s("Prefix")+"&6Enchant is already on your rod!", p);
							return;
						}
						Points.take(p.getName(), cost);
						f.addUnsafeEnchantment(c, l+1);
						ItemMeta m = f.getItemMeta();
						List<String> as = m.getLore() != null ? m.getLore() : new ArrayList<String>();
						as.add(TheAPI.colorize(c.getName()+" "+Utils.trasfer(l+1)));
						m.setLore(as);
						f.setItemMeta(m);
						TheAPI.giveItem(p, f);
						Loader.me.set("Players."+p.getName()+".SavedRod",null);
						Loader.saveChatMe();
						openEnchantTable(p);
					}
					return;
				}
				Loader.msgCmd(Loader.s("Prefix")+"&cYou have lack of points!", p);
				return;
				}
			});
				}
			}
		}
	}

	public void openSetting(Player p) {
		GUICreatorAPI a = new GUICreatorAPI("&7Settings",54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		if(Loader.c.getBoolean("Options.UseGUI")) {
			a.setItem(49,new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					open(p);
				}
			});
		}else {
		a.setItem(49, new ItemGUI(Create.createItem("&bAmazing Fishing", Material.KNOWLEDGE_BOOK,
				Arrays.asList("&9Version &bV"+Loader.plugin.getDescription().getVersion(),"&9Created by &bStraiker123"))){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
			}
		});
		}

		a.setItem(24,new ItemGUI(Loader.c.getBoolean("Options.Shop")?Create.createItem(Trans.shop(), Material.EMERALD,Arrays.asList(Trans.enabled())):
				Create.createItem(Trans.shop(), Material.EMERALD,Arrays.asList(Trans.disabled()))){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					Loader.c.set("Options.Shop", !Loader.c.getBoolean("Options.Shop"));
					Loader.save();
					openSetting(p);
				}
		});
		if(Loader.c.getBoolean("Options.Treasures")) {
			a.setItem(31,new ItemGUI(Create.createItem(Trans.trea(), Material.CHEST,Arrays.asList(Trans.enabled()))){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					Loader.c.set("Options.Treasures", !Loader.c.getBoolean("Options.Treasures"));
					Loader.save();
					openSetting(p);
				}
			});
		}else {
			a.setItem(31,new ItemGUI(Create.createItem(Trans.trea(), Material.CHEST,Arrays.asList(Trans.disabled()))){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					Loader.c.set("Options.Treasures", !Loader.c.getBoolean("Options.Treasures"));
					Loader.save();
					openSetting(p);
				}
			});
		}
			a.setItem(22, new ItemGUI(Create.createItem(Trans.fishremove(), Material.COD,Arrays.asList(Loader.c.getBoolean("Options.FishRemove")?
					Trans.enabled():Trans.disabled()))){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					Loader.c.set("Options.FishRemove", !Loader.c.getBoolean("Options.FishRemove"));
					Loader.save();
					openSetting(p);
				}
			});

			a.setItem(13, new ItemGUI(Create.createItem(Trans.bycm(), Material.NETHER_STAR,Arrays.asList(Loader.c.getBoolean("Options.EarnFromLength")?
					Trans.enabled():Trans.disabled()))){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					Loader.c.set("Options.EarnFromLength", !Loader.c.getBoolean("Options.EarnFromLength"));
					Loader.save();
					openSetting(p);
				}
			});
			a.setItem(20,new ItemGUI(Create.createItem(Trans.enchs(), Material.ENCHANTED_BOOK,Arrays.asList(Loader.c.getBoolean("Options.Enchants")?
					Trans.enabled():Trans.disabled()))){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					Loader.c.set("Options.Enchants", !Loader.c.getBoolean("Options.Enchants"));
					Loader.save();
					openSetting(p);
				}
			});
	}
	//TODO
	/*
	 a.setItem(-, new ItemGUI(){
					@Override
					public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					}
				});
	 */
	//TODO
	public void openTreas(Player p) {
		GUICreatorAPI a = new GUICreatorAPI("&6Manager &7- "+Trans.treas(),54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
				open(p);
				}});
		a.setItem(49,Create.createItem(Trans.back(), Material.BARRIER),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openTreasSelect(p,select.CREATE);
			}});
		a.setItem(20,Create.createItem(Trans.cre(), Material.GREEN_DYE),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openTreasSelect(p,select.DELETE);
			}});
		a.setItem(24,Create.createItem(Trans.del(), Material.RED_DYE),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openTreasSelect(p,select.EDIT);
			}});
		a.setItem(31,Create.createItem(Trans.edit(), Material.ORANGE_DYE),w);
	}
	public enum select{
		CREATE,
		DELETE,
		EDIT;
	}
	public enum TreasureType{
		COMMON,
		RARE,
		EPIC,
		LEGEND;
	}

	public String getChance(double chance) {
		if(chance < 15 && chance > 0)return Color.c("&a&lLOW");
		if(chance < 70 && chance > 15)return Color.c("&6&lMEDIUM");
		if(chance < 101 && chance > 70)return Color.c("&4&lHIGH");
		return Color.c("&7&lUKNOWN");
	}
	public void openEditor(Player p, String f, select type, TreasureType t) {
		String s = null;
		String path = null;
		switch(t) {
		case COMMON:
			path="Common";
			break;
		case RARE:
			path="Rare";
			break;
		case EPIC:
			path="Epic";
			break;
		case LEGEND:
			path="Legendary";
			break;
		}
		String ac = null;
		switch(type) {
		case CREATE:
			ac="Creating";
			s="&aFishing Creator";
			break;
		case EDIT:
			ac="Edit";
			s="&6Fishing Editor";
			break;
		default:
			break;
		}
		GUICreatorAPI a = new GUICreatorAPI(s+" "+Trans.treas()+" &7- "+getTrans(t)+(f==null?"":" "+f),54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		String d = path;
		String c = ac;
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				p.getOpenInventory().close();
				Loader.c.set(c+"-"+d+"."+p.getName()+".Crate", f);
				Loader.c.set(c+"-"+d+"."+p.getName()+".Type", "Name");
				Loader.save();
				TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteName", 1), Loader.get("WriteName", 2));
			}});
		if(f != null) {
			String custom = f;
			if(Loader.c.getString("Treasures."+d+"."+f+".Name")!=null)
				custom=Loader.c.getString("Treasures."+d+"."+f+".Name");
		a.setItem(13,Create.createItem(Trans.name(), Material.NAME_TAG, Arrays.asList("&b>> "+Color.c(custom))),w);
		}else
			a.setItem(13,Create.createItem(Trans.name(), Material.NAME_TAG),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE_RIGHT_CLICK, new Runnable() {
				@Override
				public void run() {
					try {
					Loader.c.set("Treasures."+d+"."+f+".Commands", Loader.c.getStringList("Treasures."+d+"."+f+".Commands").remove( Loader.c.getStringList("Treasures."+d+"."+f+".Commands").size()-1));
					}catch(Exception e) {
						
					}
					Loader.save();
					openEditor(p, f, type, t);
				}});
		w.put(Options.RUNNABLE_LEFT_CLICK, new Runnable() {
			@Override
			public void run() {
				p.getOpenInventory().close();
				Loader.c.set(c+"-"+d+"."+p.getName()+".Crate", f);
				Loader.c.set(c+"-"+d+"."+p.getName()+".Type", "Command");
				Loader.save();
				TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteCommand", 1), Loader.get("WriteCommand", 2));
			}});
		if(f!=null && Loader.c.getString("Treasures."+d+"."+f+".Commands")!=null) {
			List<String> lore = new ArrayList<String>();
			for(String ff:Loader.c.getStringList("Treasures."+d+"."+f+".Commands"))
			lore.add("&6> &a"+ff);
			a.setItem(20,Create.createItem(Trans.cmd(), Material.COMMAND_BLOCK, lore),w);
			}else
				a.setItem(20,Create.createItem(Trans.cmd(), Material.COMMAND_BLOCK),w);
		w.remove(Options.RUNNABLE_LEFT_CLICK);
		w.remove(Options.RUNNABLE_RIGHT_CLICK);

		w.put(Options.RUNNABLE_RIGHT_CLICK, new Runnable() {
			@Override
			public void run() {
				try {
				Loader.c.set("Treasures."+d+"."+f+".Messages", Loader.c.getStringList("Treasures."+d+"."+f+".Messages").remove( Loader.c.getStringList("Treasures."+d+"."+f+".Messages").size()-1));
				}catch(Exception e) {
					
				}
				Loader.save();
				openEditor(p, f, type, t);
			}});
	w.put(Options.RUNNABLE_LEFT_CLICK, new Runnable() {
		@Override
		public void run() {
			p.getOpenInventory().close();
			Loader.c.set(c+"-"+d+"."+p.getName()+".Crate", f);
			Loader.c.set(c+"-"+d+"."+p.getName()+".Type", "Message");
			Loader.save();
			TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteMessage", 1), Loader.get("WriteMessage", 2));
		}});
		if(f!=null && Loader.c.getString("Treasures."+path+"."+f+".Messages")!=null) {
			List<String> lore = new ArrayList<String>();
			for(String ff:Loader.c.getStringList("Treasures."+path+"."+f+".Messages"))
			lore.add("&6> &a"+ff);
			a.setItem(24,Create.createItem(Trans.mes(), Material.PAPER, lore),w);
			}else
				a.setItem(24,Create.createItem(Trans.mes(), Material.PAPER),w);

		w.remove(Options.RUNNABLE_LEFT_CLICK);
		w.remove(Options.RUNNABLE_RIGHT_CLICK);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				p.getOpenInventory().close();
				Loader.c.set(c+"-"+d+"."+p.getName()+".Crate", f);
				Loader.c.set(c+"-"+d+"."+p.getName()+".Type", "Money");
				Loader.save();
				TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteMoney", 1), Loader.get("WriteMoney", 2));
			}});
		if(f!=null && Loader.c.getString("Treasures."+path+"."+f+".Money") != null) {
			a.setItem(22,Create.createItem(Trans.money(), Material.GOLD_INGOT,
					Arrays.asList("&6"+Loader.c.getDouble("Treasures."+path+"."+f+".Money")+"$")),w);
			}else
				a.setItem(22,Create.createItem(Trans.money(), Material.GOLD_INGOT),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				p.getOpenInventory().close();
				Loader.c.set(c+"-"+d+"."+p.getName()+".Crate", f);
				Loader.c.set(c+"-"+d+"."+p.getName()+".Type", "Points");
				Loader.save();
				TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WritePoint", 1), Loader.get("WritePoint", 2));
			}});
		if(f!=null && Loader.c.getString("Treasures."+path+"."+f+".Points") != null) {
			List<String> lore = new ArrayList<String>();
			lore.add(Color.c("&9"+Loader.c.getDouble("Treasures."+path+"."+f+".Points")+"Points"));
			a.setItem(30,Create.createItem(Trans.point(), Material.LAPIS_LAZULI,
					Arrays.asList("&9"+Loader.c.getDouble("Treasures."+path+"."+f+".Points")+"Points")),w);
			}else
				a.setItem(30,Create.createItem(Trans.point(), Material.LAPIS_LAZULI),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				p.getOpenInventory().close();
				Loader.c.set(c+"-"+d+"."+p.getName()+".Crate", f);
				Loader.c.set(c+"-"+d+"."+p.getName()+".Type", "Chance");
				Loader.save();
				TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteChance", 1), Loader.get("WriteChance", 2));
			}});
		if(f!=null && Loader.c.getString("Treasures."+path+"."+f+".Chance") != null) {
			a.setItem(32,Create.createItem(Trans.chance(), Material.EXPERIENCE_BOTTLE, 
					Arrays.asList("&b"+getChance(Loader.c.getDouble("Treasures."+path+"."+f+".Chance")))),w);
			}else
				a.setItem(32,Create.createItem(Trans.chance(), Material.EXPERIENCE_BOTTLE),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openSelected(p, type, t);
				Loader.c.set("Creating-"+d+"."+p.getName(), null);
				Loader.c.set("Edit-"+d+"."+p.getName(), null);
				Loader.save();
			}});
		a.setItem(49,Create.createItem(Trans.cancel(), Material.BARRIER),w);
	}
	public void openTreasSelect(Player p, select type) {
		String s = null;
		switch(type) {
		case CREATE:
			s="&aFishing Creator";
			break;
		case DELETE:
			s="&cFishing Destroyer";
			break;
		case EDIT:
			s="&6Fishing Editor";
			break;
		}

	GUICreatorAPI a = new GUICreatorAPI(s+" &7- "+Trans.treas(),54,p) {
		
		@Override
		public void onClose(Player arg0) {
		}
	};
		Create.prepareInv(a);
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					if(type==select.CREATE)
						openEditor(p, null, type, TreasureType.COMMON);
					else
					openSelected(p,type,TreasureType.COMMON);
				}});
		a.setItem(13,Create.createItem(Trans.common(), Material.GRAY_DYE),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				if(type==select.CREATE)
					openEditor(p, null, type, TreasureType.RARE);
				else
				openSelected(p,type,TreasureType.RARE);
			}});
		a.setItem(20,Create.createItem(Trans.rare(), Material.BLUE_DYE),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				if(type==select.CREATE)
					openEditor(p, null, type, TreasureType.EPIC);
				else
				openSelected(p,type,TreasureType.EPIC);
			}});
		a.setItem(24,Create.createItem(Trans.epic(), Material.ORANGE_DYE),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				if(type==select.CREATE)
					openEditor(p, null, type, TreasureType.LEGEND);
				else
				openSelected(p,type,TreasureType.LEGEND);
			}});
		a.setItem(31,Create.createItem(Trans.legend(), Material.RED_DYE),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openTreas(p);
			}});
		a.setItem(49,Create.createItem(Trans.back(), Material.BARRIER),w);
	}
	private String getTrans(TreasureType t) {
		if(t==TreasureType.COMMON)
		return Trans.common();
		if(t==TreasureType.RARE)
		return Trans.rare();
		if(t==TreasureType.EPIC)
		return Trans.epic();
		if(t==TreasureType.LEGEND)
		return Trans.legend();
		return "";
	}
	public void openSelected(Player p, select type, TreasureType f) {
		String path = null;
		String s = null;
		Material material = null;
		switch(type) {
		case CREATE:
			s="&aFishing Selector";
			break;
		case DELETE:
			s="&cFishing Destroyer";
			break;
		case EDIT:
			s="&6Fishing Selector";
			break;
		}
		switch(f) {
		case COMMON:
			material=Material.GRAY_DYE;
			path="Common";
			break;
		case RARE:
			material=Material.BLUE_DYE;
			path="Rare";
			break;
		case EPIC:
			material=Material.ORANGE_DYE;
			path="Epic";
			break;
		case LEGEND:
			material=Material.RED_DYE;
			path="Legendary";
			break;
		}
		GUICreatorAPI a = new GUICreatorAPI(s+" "+Trans.treas()+" &7- "+getTrans(f),54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		
		if(Loader.c.getString("Treasures."+path)!=null)
		for(String g:Loader.c.getConfigurationSection("Treasures."+path).getKeys(false)) {
			String name = g;
			if(Loader.c.getString("Treasures."+path+"."+g+".Name")!=null)
				name=Loader.c.getString("Treasures."+path+"."+g+".Name");
			String pat = path;
			HashMap<Options, Object> w = new HashMap<Options, Object>();
			w.put(Options.CANT_PUT_ITEM, true);
			w.put(Options.CANT_BE_TAKEN, true);
				w.put(Options.RUNNABLE, new Runnable() {
					@Override
					public void run() {
						if(select.DELETE!=type)
						openEditor(p, g, type, f);
						else {
							Loader.c.set("Treasures."+pat+"."+g, null);
							Loader.save();
							openSelected(p, type, f);
						}
					}});
			a.addItem(Create.createItem(name,material),w);
		}
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					openTreasSelect(p, type);
				}});
		a.setItem(49,Create.createItem(Trans.cancel(), Material.BARRIER),w);
	}
}

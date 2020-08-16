package AmazingFishing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import AmazingFishing.onChat.enchs;
import me.DevTec.TheAPI;
import me.DevTec.AmazingFishing.Configs;
import me.DevTec.AmazingFishing.Loader;
import me.DevTec.GUI.GUICreatorAPI;
import me.DevTec.GUI.ItemGUI;

public class ench {

	public static void openEnchanter(Player p) {
		GUICreatorAPI a = new GUICreatorAPI("&6Fishing Manager &7- &dEnchants",54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
			a.setItem(49,new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					TheAPI_GUIs s = new TheAPI_GUIs();
					s.open(p);
				}
			});
		
		a.setItem(20,new ItemGUI(Create.createItem(Trans.cre(), Material.GREEN_DYE)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				openEditor(p, select.CREATE, null);
			}
		});

		a.setItem(24,new ItemGUI(Create.createItem(Trans.del(), Material.RED_DYE)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				openSelected(p,select.DELETE);
			}
		});
		a.setItem(31,new ItemGUI(Create.createItem(Trans.edit(), Material.ORANGE_DYE)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				openSelected(p,select.EDIT);
			}
		});
		
	}
	public static enum select{
		CREATE,
		DELETE,
		EDIT;
	}
	public static void openEditor(Player p, select type, String name) {
		String s = null;
		String what = "";
		switch(type) {
		case CREATE:
			what="Creating";
			s="&aFishing Creator &7- &dEnchants";
			break;
		case EDIT:
			what="Edit";
			s="&6Fishing Editor &7- &dEnchants";
			break;
		default:
			break;
		}
		String wa =what;
		GUICreatorAPI a = new GUICreatorAPI(name==null?s:s+" &7- "+name,54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};

		Create.prepareInv(a);
		if(name != null) {
			String custom = name;
			if(Loader.c.getString("Enchants."+name+".Name")!=null)
				custom=Loader.c.getString("Enchants."+name+".Name");
		a.setItem(13,new ItemGUI(Create.createItem(Trans.name(), Material.NAME_TAG, Arrays.asList("&b>> "+Color.c(custom)))){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				p.getOpenInventory().close();
				Loader.c.set(wa+"-Enchants."+p.getName()+".Type", enchs.Name);
				Configs.c.save();
				TheAPI.sendTitle(p,Loader.get("WriteName", 1), Loader.get("WriteName", 2));
			}});
		}else
			a.setItem(13,new ItemGUI(Create.createItem(Trans.name(), Material.NAME_TAG)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					p.getOpenInventory().close();
					Loader.c.set(wa+"-Enchants."+p.getName()+".Type", enchs.Name);
					Configs.c.save();
					TheAPI.sendTitle(p,Loader.get("WriteName", 1), Loader.get("WriteName", 2));
				}});
		if(name!=null && Loader.c.getString("Enchants."+name+".Cost") != null) {
			a.setItem(22,new ItemGUI(Create.createItem(Trans.cost(), Material.SUNFLOWER, 
					Arrays.asList("&6"+Loader.c.getDouble("Enchants."+name+".Cost")+"Points"))){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					p.getOpenInventory().close();
					Loader.c.set(wa+"-Enchants."+p.getName()+".Type", enchs.Cost);
					Configs.c.save();
					TheAPI.sendTitle(p,Loader.get("WriteCost", 1), Loader.get("WriteCost", 2));
				}
			});
			}else
				a.setItem(22,new ItemGUI(Create.createItem(Trans.cost(), Material.SUNFLOWER)){
					@Override
					public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
						p.getOpenInventory().close();
						Loader.c.set(wa+"-Enchants."+p.getName()+".Type", enchs.Cost);
						Configs.c.save();
						TheAPI.sendTitle(p,Loader.get("WriteCost", 1), Loader.get("WriteCost", 2));
					}
				});

		if(name!=null && Loader.c.getString("Enchants."+name+".ExpBonus") != null) {
			a.setItem(20,new ItemGUI(Create.createItem(Trans.expbonus(), Material.EXPERIENCE_BOTTLE, 
					Arrays.asList("&9"+Loader.c.getDouble("Enchants."+name+".ExpBonus")+"% Bonus"))){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					p.getOpenInventory().close();
					Loader.c.set(wa+"-Enchants."+p.getName()+".Type", enchs.ExpBonus);
					Configs.c.save();
					TheAPI.sendTitle(p,Loader.get("WriteExpBonus", 1), Loader.get("WriteExpBonus", 2));
				}
			});
			}else
		a.setItem(20,new ItemGUI(Create.createItem(Trans.expbonus(), Material.EXPERIENCE_BOTTLE)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				p.getOpenInventory().close();
				Loader.c.set(wa+"-Enchants."+p.getName()+".Type", enchs.ExpBonus);
				Configs.c.save();
				TheAPI.sendTitle(p,Loader.get("WriteExpBonus", 1), Loader.get("WriteExpBonus", 2));
			}
		});
		
		if(name!=null && Loader.c.getString("Enchants."+name+".AmountBonus") != null) {
			a.setItem(21,new ItemGUI(Create.createItem(Trans.amountbonus(), Material.WHEAT_SEEDS, 
					Arrays.asList("&9"+Loader.c.getDouble("Enchants."+name+".AmountBonus")+" Bonus"))){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					p.getOpenInventory().close();
					Loader.c.set(wa+"-Enchants."+p.getName()+".Type", enchs.AmountBonus);
					Configs.c.save();
					TheAPI.sendTitle(p,Loader.get("WriteAmount", 1), Loader.get("WriteAmount", 2));
				}
			});
			}else
				a.setItem(21,new ItemGUI(Create.createItem(Trans.amountbonus(), Material.WHEAT_SEEDS)){
					@Override
					public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
						p.getOpenInventory().close();
						Loader.c.set(wa+"-Enchants."+p.getName()+".Type", enchs.AmountBonus);
						Configs.c.save();
						TheAPI.sendTitle(p,Loader.get("WriteAmount", 1), Loader.get("WriteAmount", 2));
					}
				});
		
		
		
		if(name!=null && Loader.c.getString("Enchants."+name+".Description")!=null) {
			List<String> lore = new ArrayList<String>();
			for(String f:Loader.c.getStringList("Enchants."+name+".Description"))
			lore.add(Color.c("&6> &a"+f));
			a.setItem(24,new ItemGUI(Create.createItem(Trans.dec(), Material.PAPER, lore)){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType typee) {
					if(typee.isRightClick()) {
						try {
							Loader.c.set("Enchants."+name+".Description", 
						Loader.c.getStringList("Enchants."+name+".Description")
						.remove(Loader.c.getStringList("Enchants."+name+".Description").size()-1));
						}catch(Exception e) {
							
						}
				openEditor(p, type, name);
				Configs.c.save();
					}
					if(typee.isLeftClick()) {
						p.getOpenInventory().close();
						Loader.c.set(wa+"-Enchants."+p.getName()+".Type",enchs.Description); 
						Configs.c.save();
						TheAPI.sendTitle(p,Loader.get("NewDescription", 1), Loader.get("NewDescription", 2));
					}
				}
			});
			}else
				a.setItem(24,new ItemGUI(Create.createItem(Trans.dec(), Material.PAPER)){
					@Override
					public void onClick(Player p, GUICreatorAPI arg, ClickType typee) {
						if(typee.isRightClick()) {
							try {
								Loader.c.set("Enchants."+name+".Description", 
							Loader.c.getStringList("Enchants."+name+".Description")
							.remove(Loader.c.getStringList("Enchants."+name+".Description").size()-1));
							}catch(Exception e) {
								
							}
					openEditor(p, type, name);
					Configs.c.save();
						}
						if(typee.isLeftClick()) {
							p.getOpenInventory().close();
							Loader.c.set(wa+"-Enchants."+p.getName()+".Type",enchs.Description); 
							Configs.c.save();
							TheAPI.sendTitle(p,Loader.get("NewDescription", 1), Loader.get("NewDescription", 2));
						}
					}
				});

		if(name!=null && Loader.c.getString("Enchants."+name+".PointsBonus") != null) {
			a.setItem(30,new ItemGUI(Create.createItem(Trans.pointbonus(), Material.LAPIS_LAZULI, 
					Arrays.asList("&9"+Loader.c.getDouble("Enchants."+name+".PointsBonus")+"% Bonus"))){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					p.getOpenInventory().close();
					Loader.c.set(wa+"-Enchants."+p.getName()+".Type", enchs.PointsBonus);
					Configs.c.save();
					TheAPI.sendTitle(p,Loader.get("WritePointsBonus", 1), Loader.get("WritePointsBonus", 2));
				}
			});
			}else
				a.setItem(30,new ItemGUI(Create.createItem(Trans.pointbonus(), Material.LAPIS_LAZULI)){
					@Override
					public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
						p.getOpenInventory().close();
						Loader.c.set(wa+"-Enchants."+p.getName()+".Type", enchs.PointsBonus);
						Configs.c.save();
						TheAPI.sendTitle(p,Loader.get("WritePointsBonus", 1), Loader.get("WritePointsBonus", 2));
					}
				});
		if(name!=null && Loader.c.getString("Enchants."+name+".MoneyBonus") != null) {
			a.setItem(32, new ItemGUI(Create.createItem(Trans.moneybonus(), Material.GOLD_INGOT, 
					Arrays.asList("&6"+Loader.c.getDouble("Enchants."+name+".MoneyBonus")+"% Bonus"))){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					p.getOpenInventory().close();
					Loader.c.set(wa+"-Enchants."+p.getName()+".Type", enchs.MoneyBonus);
					Configs.c.save();
					TheAPI.sendTitle(p,Loader.get("WriteMoneyBonus", 1), Loader.get("WriteMoneyBonus", 2));
				}
			});
			}else
				a.setItem(32,new ItemGUI(Create.createItem(Trans.moneybonus(), Material.GOLD_INGOT)){
					@Override
					public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
						p.getOpenInventory().close();
						Loader.c.set(wa+"-Enchants."+p.getName()+".Type", enchs.MoneyBonus);
						Configs.c.save();
						TheAPI.sendTitle(p,Loader.get("WriteMoneyBonus", 1), Loader.get("WriteMoneyBonus", 2));
					}
				});

		a.setItem(49,new ItemGUI(Create.createItem(Trans.save(), Material.EMERALD_BLOCK)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				if(type==select.EDIT) {
				ench.openSelected(p, type);
				}else
					openEnchanter(p);
				Loader.c.set("Creating-Enchants."+p.getName(), null);
				Loader.c.set("Edit-Enchants."+p.getName(), null);
				Configs.c.save();
			}
		});
	}

	public static void openSelected(Player p, select type) {
		String s = null;
		switch(type) {
		case CREATE:
			s="&aFishing Selector &7- &dEnchants";
			break;
		case DELETE:
			s="&cFishing Destroyer &7- &dEnchants";
			break;
		case EDIT:
			s="&6Fishing Selector &7- &dEnchants";
			break;
		}
		GUICreatorAPI a = new GUICreatorAPI(s,54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		if(Loader.c.getString("Enchants")!=null)
		for(String g:Loader.c.getConfigurationSection("Enchants").getKeys(false)) {
			String name = g;
			if(Loader.c.getString("Enchants."+g+".Name")!=null)
				name=Loader.c.getString("Enchants."+g+".Name");

			a.addItem(new ItemGUI(Create.createItem(name,Material.ENCHANTED_BOOK,Loader.c.getStringList("Enchants."+g+".Description"))){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
					if(type==select.DELETE) {;
					Loader.c.set("Enchants."+g, null);
					Configs.c.save();
					openSelected(p, type);
						return;
					}
					openEditor(p, type, g);
				}
			});
		}
		a.setItem(49,new ItemGUI(Create.createItem(Trans.cancel(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType ctype) {
				openEnchanter(p);
			}
		});
	}

}
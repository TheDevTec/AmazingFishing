package AmazingFishing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import AmazingFishing.onChat.enchs;
import me.DevTec.TheAPI;
import me.DevTec.GUI.GUICreatorAPI;
import me.DevTec.GUI.GUICreatorAPI.Options;

public class ench {

	public static void openEnchanter(Player p) {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI("&6Fishing Manager &7- &dEnchants",54,p);
		Create.prepareInv(a);
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					TheAPI_GUIs s = new TheAPI_GUIs();
				s.open(p);
				}});
			a.setItem(49,Create.createItem(Trans.back(), Material.BARRIER),w);
		
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openEditor(p, select.CREATE, null);
			}});
		a.setItem(20,Create.createItem(Trans.cre(), Material.GREEN_DYE),w);

		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openSelected(p,select.DELETE);
			}});
		a.setItem(24,Create.createItem(Trans.del(), Material.RED_DYE),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openSelected(p,select.EDIT);
			}});
		a.setItem(31,Create.createItem(Trans.edit(), Material.ORANGE_DYE),w);
		
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
		GUICreatorAPI a = TheAPI.getGUICreatorAPI(name==null?s:s+" &7- "+name,54,p);
		Create.prepareInv(a);
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					p.getOpenInventory().close();
					Loader.c.set(wa+"-Enchants."+p.getName()+".Type", enchs.Name);
					Loader.save();
					TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteName", 1), Loader.get("WriteName", 2));
				}});
		if(name != null) {
			String custom = name;
			if(Loader.c.getString("Enchants."+name+".Name")!=null)
				custom=Loader.c.getString("Enchants."+name+".Name");
		a.setItem(13,Create.createItem(Trans.name(), Material.NAME_TAG, Arrays.asList("&b>> "+Color.c(custom))),w);
		}else
			a.setItem(13,Create.createItem(Trans.name(), Material.NAME_TAG),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				p.getOpenInventory().close();
				Loader.c.set(wa+"-Enchants."+p.getName()+".Type", enchs.Cost);
				Loader.save();
				TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteCost", 1), Loader.get("WriteCost", 2));
			}
		});
		if(name!=null && Loader.c.getString("Enchants."+name+".Cost") != null) {
			a.setItem(22,Create.createItem(Trans.cost(), Material.SUNFLOWER, 
					Arrays.asList("&6"+Loader.c.getDouble("Enchants."+name+".Cost")+"Points")),w);
			}else
				a.setItem(22,Create.createItem(Trans.cost(), Material.SUNFLOWER),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				p.getOpenInventory().close();
				Loader.c.set(wa+"-Enchants."+p.getName()+".Type", enchs.ExpBonus);
				Loader.save();
				TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteExpBonus", 1), Loader.get("WriteExpBonus", 2));
			}
		});
		if(name!=null && Loader.c.getString("Enchants."+name+".ExpBonus") != null) {
			a.setItem(20,Create.createItem(Trans.expbonus(), Material.EXPERIENCE_BOTTLE, 
					Arrays.asList("&9"+Loader.c.getDouble("Enchants."+name+".ExpBonus")+"% Bonus")),w);
			}else
		a.setItem(20,Create.createItem(Trans.expbonus(), Material.EXPERIENCE_BOTTLE),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				p.getOpenInventory().close();
				Loader.c.set(wa+"-Enchants."+p.getName()+".Type", enchs.AmountBonus);
				Loader.save();
				TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteAmount", 1), Loader.get("WriteAmount", 2));
			}
		});
		if(name!=null && Loader.c.getString("Enchants."+name+".AmountBonus") != null) {
			a.setItem(21,Create.createItem(Trans.amountbonus(), Material.WHEAT_SEEDS, 
					Arrays.asList("&9"+Loader.c.getDouble("Enchants."+name+".AmountBonus")+" Bonus")),w);
			}else
				a.setItem(21,Create.createItem(Trans.amountbonus(), Material.WHEAT_SEEDS),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE_RIGHT_CLICK, new Runnable() {
			@Override
			public void run() {
						try {
							Loader.c.set("Enchants."+name+".Description", 
						Loader.c.getStringList("Enchants."+name+".Description")
						.remove(Loader.c.getStringList("Enchants."+name+".Description").size()-1));
						}catch(Exception e) {
							
						}
				openEditor(p, type, name);
				Loader.save();
			}
		});
		w.put(Options.RUNNABLE_LEFT_CLICK, new Runnable() {
			@Override
			public void run() {
				p.getOpenInventory().close();
				Loader.c.set(wa+"-Enchants."+p.getName()+".Type",enchs.Description); 
				Loader.save();
				TheAPI.getPlayerAPI(p).sendTitle(Loader.get("NewDescription", 1), Loader.get("NewDescription", 2));
			}
		});
		if(name!=null && Loader.c.getString("Enchants."+name+".Description")!=null) {
			List<String> lore = new ArrayList<String>();
			for(String f:Loader.c.getStringList("Enchants."+name+".Description"))
			lore.add(Color.c("&6> &a"+f));
			a.setItem(24,Create.createItem(Trans.dec(), Material.PAPER, lore),w);
			}else
				a.setItem(24,Create.createItem(Trans.dec(), Material.PAPER),w);
		w.remove(Options.RUNNABLE_LEFT_CLICK);
		w.remove(Options.RUNNABLE_RIGHT_CLICK);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				p.getOpenInventory().close();
				Loader.c.set(wa+"-Enchants."+p.getName()+".Type", enchs.PointsBonus);
				Loader.save();
				TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WritePointsBonus", 1), Loader.get("WritePointsBonus", 2));
			}
		});
		if(name!=null && Loader.c.getString("Enchants."+name+".PointsBonus") != null) {
			a.setItem(30,Create.createItem(Trans.pointbonus(), Material.LAPIS_LAZULI, 
					Arrays.asList("&9"+Loader.c.getDouble("Enchants."+name+".PointsBonus")+"% Bonus")),w);
			}else
				a.setItem(30,Create.createItem(Trans.pointbonus(), Material.LAPIS_LAZULI),w);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				p.getOpenInventory().close();
				Loader.c.set(wa+"-Enchants."+p.getName()+".Type", enchs.MoneyBonus);
				Loader.save();
				TheAPI.getPlayerAPI(p).sendTitle(Loader.get("WriteMoneyBonus", 1), Loader.get("WriteMoneyBonus", 2));
			}
		});
		if(name!=null && Loader.c.getString("Enchants."+name+".MoneyBonus") != null) {
			a.setItem(32,Create.createItem(Trans.moneybonus(), Material.GOLD_INGOT, 
					Arrays.asList("&6"+Loader.c.getDouble("Enchants."+name+".MoneyBonus")+"% Bonus")),w);
			}else
				a.setItem(32,Create.createItem(Trans.moneybonus(), Material.GOLD_INGOT),w);

		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				if(type==select.EDIT)
				ench.openSelected(p, type);
				else
					openEnchanter(p);
				Loader.c.set("Creating-Enchants."+p.getName(), null);
				Loader.c.set("Edit-Enchants."+p.getName(), null);
				Loader.save();
			}
		});
		a.setItem(49,Create.createItem(Trans.save(), Material.EMERALD_BLOCK),w);
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
		GUICreatorAPI a = TheAPI.getGUICreatorAPI(s,54,p);
		Create.prepareInv(a);
		if(Loader.c.getString("Enchants")!=null)
		for(String g:Loader.c.getConfigurationSection("Enchants").getKeys(false)) {
			String name = g;
			if(Loader.c.getString("Enchants."+g+".Name")!=null)
				name=Loader.c.getString("Enchants."+g+".Name");
			HashMap<Options, Object> w = new HashMap<Options, Object>();
			w.put(Options.CANT_PUT_ITEM, true);
			w.put(Options.CANT_BE_TAKEN, true);
				w.put(Options.RUNNABLE, new Runnable() {
					@Override
					public void run() {
						if(type==select.DELETE) {;
						Loader.c.set("Enchants."+g, null);
						Loader.save();
						openSelected(p, type);
							return;
						}
						openEditor(p, type, g);
					}});
			a.addItem(Create.createItem(name,Material.ENCHANTED_BOOK,Loader.c.getStringList("Enchants."+g+".Description")),w);
		}
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_PUT_ITEM, true);
		w.put(Options.CANT_BE_TAKEN, true);
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					openEnchanter(p);
				}});
		a.setItem(49,Create.createItem(Trans.cancel(), Material.BARRIER),w);
	}
}
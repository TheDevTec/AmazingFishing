package AmazingFishing.NewGUIs;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import AmazingFishing.Create;
import AmazingFishing.TheAPI_GUIs;
import AmazingFishing.Trans;
import AmazingFishing.get;
import AmazingFishing.gui;
import AmazingFishing.APIs.Enums.FishType;
import me.DevTec.AmazingFishing.Loader;
import me.DevTec.TheAPI.TheAPI;
import me.DevTec.TheAPI.GUIAPI.GUI;
import me.DevTec.TheAPI.GUIAPI.ItemGUI;

public class Editors {

	public static void openFishCreatorType(Player p, String fish,FishType type) {
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
		GUI a = new GUI("&aCreator &7- "+title,54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		String wd = where;
		a.setItem(22,new ItemGUI(name != null?Create.createItem(Trans.name(), Material.NAME_TAG,Arrays.asList("&b>> "+name)):Create.createItem(Trans.name(), Material.NAME_TAG)){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {
				p.getOpenInventory().close();
				Loader.c.set("Creating-"+wd+"."+p.getName()+".Type", "Name");
				Loader.save();
				TheAPI.sendTitle(p,Loader.get("WriteName", 1), Loader.get("WriteName", 2));
			}
		});

			a.setItem(30,new ItemGUI(xp!=null?Create.createItem(Trans.exp(), Material.EXPERIENCE_BOTTLE,Arrays.asList("&9"+xp+"Exps")):
				Create.createItem(Trans.exp(), Material.EXPERIENCE_BOTTLE)){
				@Override
				public void onClick(Player p, GUI arg, ClickType type) {
					p.getOpenInventory().close();
					Loader.c.set("Creating-"+wd+"."+p.getName()+".Type", "Exp");
					Loader.save();
					TheAPI.sendTitle(p,Loader.get("WriteExp", 1), Loader.get("WriteExp", 2));
				}
			});
			a.setItem(29,new ItemGUI(points != null?Create.createItem(Trans.point(), Material.LAPIS_LAZULI,Arrays.asList("&d"+points+"Points")):
				Create.createItem(Trans.point(), Material.LAPIS_LAZULI)){
				@Override
				public void onClick(Player p, GUI arg, ClickType type) {
					p.getOpenInventory().close();
					Loader.c.set("Creating-"+wd+"."+p.getName()+".Type", "Points");
					Loader.save();
					TheAPI.sendTitle(p,Loader.get("WritePoint", 1), Loader.get("WritePoint", 2));
				}
			});
		a.setItem(32,new ItemGUI(money != null?Create.createItem(Trans.money(), Material.GOLD_INGOT,Arrays.asList("&6"+money+"$")):
			Create.createItem(Trans.money(), Material.GOLD_INGOT)){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {
				p.getOpenInventory().close();
				Loader.c.set("Creating-"+wd+"."+p.getName()+".Type", "Money");
				Loader.save();
				TheAPI.sendTitle(p,Loader.get("WriteMoney", 1), Loader.get("WriteMoney", 2));
			}
		});
		a.setItem(41, new ItemGUI(chance != null?Create.createItem(Trans.chance(), Material.PAPER,Arrays.asList("&9"+chance+"%")):
			Create.createItem(Trans.chance(), Material.PAPER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {
				p.getOpenInventory().close();
				Loader.c.set("Creating-"+wd+"."+p.getName()+".Type", "Chance");
				Loader.save();
				TheAPI.sendTitle(p,Loader.get("WriteChance", 1), Loader.get("WriteChance", 2));
			}
		});

			a.setItem(31,new ItemGUI(cm != null?Create.createItem(Trans.cm(), Material.PAPER,Arrays.asList("&3Max "+cm+"Cm")):
				Create.createItem(Trans.cm(), Material.PAPER)){
				@Override
				public void onClick(Player p, GUI arg, ClickType type) {
					p.getOpenInventory().close();
					Loader.c.set("Creating-"+wd+"."+p.getName()+".Type", "Cm");
					Loader.save();
					TheAPI.sendTitle(p,Loader.get("WriteLength", 1), Loader.get("WriteLength", 2));
				}
			});
		a.setItem(40,new ItemGUI(Create.createItem(Trans.cancel(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {
				Loader.c.remove("Edit-"+wd+"."+p.getName());
				Loader.c.remove("Creating-"+wd+"."+p.getName());
				Loader.save();
				new TheAPI_GUIs().openFishCreate(p);
			}
		});
		String pat = path;
		a.setItem(39,new ItemGUI(Create.createItem(Trans.perbiome(), Material.CHEST)){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {
				Loader.c.set("Creating-"+wd+"."+p.getName()+".Biome",true);
				Loader.save();
			gui.openBiomeSettting(p,fish,wd,false);
			}
		});
		a.setItem(33,new ItemGUI(Create.createItem(Trans.save(), Material.EMERALD_BLOCK)){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {
				get.finish(p, pat, false);
			Loader.c.set("Edit-"+wd+"."+p.getName(), null);
			Loader.c.set("Creating-"+wd+"."+p.getName(), null);
			Loader.save();
			new TheAPI_GUIs().openFishCreate(p);
			}
		});
	}
}

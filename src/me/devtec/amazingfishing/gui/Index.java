package me.devtec.amazingfishing.gui;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.construct.FishType;
import me.devtec.amazingfishing.construct.Junk;
import me.devtec.amazingfishing.utils.CatchFish;
import me.devtec.amazingfishing.utils.CatchFish.Fishing;
import me.devtec.amazingfishing.utils.Create;
import me.devtec.amazingfishing.utils.Pagination;
import me.devtec.amazingfishing.utils.Statistics;
import me.devtec.amazingfishing.utils.Statistics.RecordType;
import me.devtec.amazingfishing.utils.Statistics.SavingType;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.GUI.ClickType;
import me.devtec.theapi.guiapi.HolderGUI;
import me.devtec.theapi.guiapi.ItemGUI;
import me.devtec.theapi.placeholderapi.PlaceholderAPI;

public class Index {
	static GUI g=Create.setup(new GUI(Create.text("index.title"),54), Create.make("index.close").create(), p -> Help.open(p), me.devtec.amazingfishing.utils.Create.Settings.SIDES);
	static {

		if(CatchFish.isEnabled(Fishing.FISH)) {
			g.setItem(20, new ItemGUI(Create.make("index.cod").create()) {
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					open(player, FishType.COD, 0);
				}
			});
			g.setItem(24, new ItemGUI(Create.make("index.salmon").create()) {
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					open(player, FishType.SALMON, 0);
				}
			});
			g.setItem(30, new ItemGUI(Create.make("index.tropical_fish").create()) {
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					open(player, FishType.TROPICAL_FISH, 0);
				}
			});
			g.setItem(32, new ItemGUI(Create.make("index.pufferfish").create()) {
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					open(player, FishType.PUFFERFISH, 0);
				}
			});
		}
		if(CatchFish.isEnabled(Fishing.JUNK)) {
			g.setItem(22, new ItemGUI(Create.make("index.junk").create()) {
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					open(player, FishType.JUNK, 0);
				}
			});
		}
	}
	
	public static void open(Player s) {
		g.open(s);
	}
	
	public static void open(Player s, FishType type, int page) {
		GUI c=Create.setup(new GUI(Loader.gui.getString("GUI.Index."+type.name().toLowerCase()),54),Create.make("index.close").create(), p -> open(p));
		if(type!=FishType.JUNK) {
			Pagination<Fish> g = new Pagination<>(36, API.getRegisteredFish().values().stream().filter(a -> a.getType()==type).collect(Collectors.toList()));
			if(g.isEmpty()||g.getPage(page).isEmpty())return;
			for(Fish f : g.getPage(page)) {
				c.addItem(new ItemGUI(f.preview(s)) {
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						List<String> prew = Loader.config.getStringList("Preview");
						prew.replaceAll(a -> PlaceholderAPI.setPlaceholders(player, a.replace("%weight%", Loader.ff.format(Statistics.getRecord(player, f, RecordType.WEIGHT)))
								.replace("%length%", Loader.ff.format(Statistics.getRecord(player, f, RecordType.LENGTH)))
								.replace("%caught%", Loader.intt.format(Statistics.getCaught(player, f, SavingType.PER_FISH)))));
						for(String sd : prew)
						TheAPI.sendMessage(sd, player);
					}
				});
			}
			if(g.totalPages()-1!=page && g.totalPages()-1>page) {
				c.setItem(51, new ItemGUI(Loader.next) {
					public void onClick(Player var1, HolderGUI var2, ClickType var3) {
						open(var1, type, page+1);
					}
				});
			}
			if(page>0) {
				c.setItem(47, new ItemGUI(Loader.prev) {
					public void onClick(Player var1, HolderGUI var2, ClickType var3) {
						open(var1, type, page-1);
					}
				});
			}
		}
		else {
			Pagination<Junk> g = new Pagination<>(36, API.getRegisteredJunk().values().stream().collect(Collectors.toList()));
			if(g.isEmpty()||g.getPage(page).isEmpty())return;
			for(Junk f : g.getPage(page)) {
				if(f.show()) {
					c.addItem(new ItemGUI(f.preview(s)) {
						public void onClick(Player player, HolderGUI gui, ClickType click) {
							/*List<String> prew = Loader.config.getStringList("Preview");
							prew.replaceAll(a -> PlaceholderAPI.setPlaceholders(player, a));
							for(String sd : prew)
							TheAPI.sendMessage(sd, player);*/
						}
					});
				}
			}
			if(g.totalPages()-1!=page && g.totalPages()-1>page) {
				c.setItem(51, new ItemGUI(Loader.next) {
					public void onClick(Player var1, HolderGUI var2, ClickType var3) {
						open(var1, type, page+1);
					}
				});
			}
			if(page>0) {
				c.setItem(47, new ItemGUI(Loader.prev) {
					public void onClick(Player var1, HolderGUI var2, ClickType var3) {
						open(var1, type, page-1);
					}
				});
			}
			
		}
		c.open(s);
	}
}

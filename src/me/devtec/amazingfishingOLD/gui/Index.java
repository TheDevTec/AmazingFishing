package me.devtec.amazingfishingOLD.gui;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import me.devtec.amazingfishingOLD.API;
import me.devtec.amazingfishingOLD.Loader;
import me.devtec.amazingfishingOLD.construct.Fish;
import me.devtec.amazingfishingOLD.construct.FishType;
import me.devtec.amazingfishingOLD.construct.Junk;
import me.devtec.amazingfishingOLD.utils.Create;
import me.devtec.amazingfishingOLD.utils.Pagination;
import me.devtec.amazingfishingOLD.utils.Statistics;
import me.devtec.amazingfishingOLD.utils.Statistics.RecordType;
import me.devtec.amazingfishingOLD.utils.Statistics.SavingType;
import me.devtec.amazingfishingOLD.utils.listeners.CatchFish;
import me.devtec.amazingfishingOLD.utils.listeners.CatchFish.Fishing;
import me.devtec.shared.placeholders.PlaceholderAPI;
import me.devtec.theapi.bukkit.gui.EmptyItemGUI;
import me.devtec.theapi.bukkit.gui.GUI;
import me.devtec.theapi.bukkit.gui.GUI.ClickType;
import me.devtec.theapi.bukkit.gui.HolderGUI;
import me.devtec.theapi.bukkit.gui.ItemGUI;

public class Index {
	static GUI g=Create.setup(new GUI(Create.title("index.title"),54), Create.make("index.close").build(), p -> Help.open(p), me.devtec.amazingfishingOLD.utils.Create.Settings.SIDES);
	static {

		if(CatchFish.isEnabled(Fishing.FISH)) {
			g.setItem(20, new ItemGUI(Create.make("index.cod").build()) {
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					open(player, FishType.COD, 0);
				}
			});
			g.setItem(24, new ItemGUI(Create.make("index.salmon").build()) {
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					open(player, FishType.SALMON, 0);
				}
			});
			g.setItem(30, new ItemGUI(Create.make("index.tropical_fish").build()) {
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					open(player, FishType.TROPICAL_FISH, 0);
				}
			});
			g.setItem(32, new ItemGUI(Create.make("index.pufferfish").build()) {
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					open(player, FishType.PUFFERFISH, 0);
				}
			});
		}
		if(CatchFish.isEnabled(Fishing.JUNK)) {
			g.setItem(22, new ItemGUI(Create.make("index.junk").build()) {
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
		GUI c = Create.setup(new GUI(Create.title("index.title-"+type.name().toLowerCase()),54),Create.make("index.close").build(), p -> open(p));
		if(type!=FishType.JUNK) {
			Pagination<Fish> g = new Pagination<>(36, API.getRegisteredFish().values().stream().filter(a -> a.getType()==type).collect(Collectors.toList()));
			if(g.isEmpty()||!g.exists(page))return;
			for(Fish f : g.getPage(page)) {
				c.addItem(new ItemGUI(f.preview(s)) {
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						List<String> prew = Loader.config.getStringList("Preview");
						prew.replaceAll(a -> PlaceholderAPI.apply(a.replace("%weight%", Loader.ff.format(Statistics.getRecord(player, f, RecordType.WEIGHT)))
								.replace("%length%", Loader.ff.format(Statistics.getRecord(player, f, RecordType.LENGTH)))
								.replace("%caught%", Loader.intt.format(Statistics.getCaught(player, f, SavingType.PER_FISH))), player.getUniqueId()));
						for(String sd : prew)
							Loader.msg(sd, player);
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
		}else {
			Pagination<Junk> g = new Pagination<>(36, API.getRegisteredJunk().values());
			if(g.isEmpty()||!g.exists(page))return;
			for(Junk f : g.getPage(page))
				if(f.show())c.addItem(new EmptyItemGUI(f.preview(s)));
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

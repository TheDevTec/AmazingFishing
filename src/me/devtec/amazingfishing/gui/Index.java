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
import me.devtec.amazingfishing.utils.Create;
import me.devtec.amazingfishing.utils.Pagination;
import me.devtec.amazingfishing.utils.Statistics;
import me.devtec.amazingfishing.utils.Statistics.RecordType;
import me.devtec.amazingfishing.utils.Statistics.SavingType;
import me.devtec.amazingfishing.utils.Trans;
import me.devtec.amazingfishing.utils.CatchFish.Fishing;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.apis.ItemCreatorAPI;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.GUI.ClickType;
import me.devtec.theapi.guiapi.HolderGUI;
import me.devtec.theapi.guiapi.ItemGUI;
import me.devtec.theapi.placeholderapi.PlaceholderAPI;

public class Index {
	static GUI g=Create.setup(new GUI(Loader.gui.getString("GUI.Index.Title"),54), p -> Help.open(p), me.devtec.amazingfishing.utils.Create.Settings.SIDES);
	static {

		if(CatchFish.isEnabled(Fishing.Fish)) {
			g.setItem(20, new ItemGUI(ItemCreatorAPI.create(API.getMaterialOf(FishType.COD).getType(), 1, Trans.words_cod(), API.getMaterialOf(FishType.COD).getData().getData())) {
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					open(player, FishType.COD, 0);
				}
			});
			g.setItem(24, new ItemGUI(ItemCreatorAPI.create(API.getMaterialOf(FishType.SALMON).getType(), 1, Trans.words_salmon(), API.getMaterialOf(FishType.SALMON).getData().getData())) {
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					open(player, FishType.SALMON, 0);
				}
			});
			g.setItem(30, new ItemGUI(ItemCreatorAPI.create(API.getMaterialOf(FishType.TROPICAL_FISH).getType(), 1, Trans.words_tropicalfish(), API.getMaterialOf(FishType.TROPICAL_FISH).getData().getData())) {
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					open(player, FishType.TROPICAL_FISH, 0);
				}
			});
			g.setItem(32, new ItemGUI(ItemCreatorAPI.create(API.getMaterialOf(FishType.PUFFERFISH).getType(), 1, Trans.words_pufferfish(), API.getMaterialOf(FishType.PUFFERFISH).getData().getData())) {
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					open(player, FishType.PUFFERFISH, 0);
				}
			});
		}
		if(CatchFish.isEnabled(Fishing.Junk)) {
			g.setItem(22, new ItemGUI(ItemCreatorAPI.create(API.getMaterialOf(FishType.JUNK).getType(), 1, Trans.words_junk(), API.getMaterialOf(FishType.JUNK).getData().getData())) {
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
		GUI c=Create.setup(new GUI(Loader.gui.getString("GUI.Index."+type.name().toLowerCase()),54), p -> open(p));
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

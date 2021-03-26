package me.devtec.amazingfishing.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.utils.Create;
import me.devtec.amazingfishing.utils.Pagination;
import me.devtec.amazingfishing.utils.Quests;
import me.devtec.amazingfishing.utils.Quests.Quest;
import me.devtec.amazingfishing.utils.Trans;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.GUI.ClickType;
import me.devtec.theapi.guiapi.HolderGUI;
import me.devtec.theapi.guiapi.ItemGUI;
import me.devtec.theapi.utils.datakeeper.User;

public class QuestGUI {
	
	public static void open(Player p) {
		openQuests(p, 0);
	}

	private static void openQuests(Player player, int page) {
		GUI a = new GUI(Trans.quests_title_all() ,54);
		Create.prepareInv(a);

		Pagination<Quest> p = new Pagination<Quest>(28);
		for(Quest q :  Quests.getQuests(player.getName()).values()) {
			p.add(q);
		}
		if(p!=null && !p.isEmpty()) {
		for(Quest q: p.getPage(page)) {
			a.add(new ItemGUI( Create.createItem(q.getDisplayName(), q.getDisplayIcon(), q.getDescription())) {
				
				@Override
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					if(click == ClickType.LEFT_PICKUP) {
						if(Quests.canStartNew(player.getName()) && !Quests.isInPorgress(player.getName(), q.getName()) ) {
							Quests.start(player, q);
						}else {
							Loader.msg(Trans.s("Quests.CannotStart").replace("%name%", q.getName())
									.replace("%questname%", q.getDisplayName())
									.replace("%icon%", q.getDisplayIcon().name())
									.replace("%stages%", ""+q.getStages()), player);
						}
					}
					if(click == ClickType.RIGHT_PICKUP) {
						if(Quests.canCancel(player.getName(), q)) {
							Quests.cancel(player.getName(), q.getName());
							Loader.msg(Trans.s("Quests.Cancel").replace("%name%", q.getName())
									.replace("%questname%", q.getDisplayName())
									.replace("%icon%", q.getDisplayIcon().name())
									.replace("%stages%", ""+q.getStages()), player);
							return;
						}else {
							Loader.msg(Trans.s("Quests.CannotCancel").replace("%name%", q.getName())
									.replace("%questname%", q.getDisplayName())
									.replace("%icon%", q.getDisplayIcon().name())
									.replace("%stages%", ""+q.getStages()), player);
						}
					}
					
				}
			});
			
		}

		Bukkit.broadcastMessage(p.totalPages()+" ; "+page);
		if(p.totalPages()>page+1) {
			a.setItem(52, new ItemGUI( Create.createItem("&bNext page", Material.ARROW)) {
				
				@Override
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					openQuests(player, page+1);
					
				}
			});
		}
		}
		a.setItem(26, new ItemGUI(Create.createItem(Loader.gui.getString("GUI.Quests.MyQuests"), Material.BLUE_CONCRETE_POWDER)) {
			@Override
			public void onClick(Player player, HolderGUI gui, ClickType click) {
				openMyQuests(player, 0);
				
			}
		});
		a.setItem(49, new ItemGUI( Create.createItem(Trans.words_back(), Material.BARRIER)) {
			
			@Override
			public void onClick(Player player, HolderGUI gui, ClickType click) {
				Help.open(player);
				
			}
		});
		a.open(player);
	}
	private static void openMyQuests(Player p, int page) {
		GUI a = new GUI(Trans.quests_title_my() ,54);
		Create.prepareInv(a);
		User u = TheAPI.getUser(p);
		
		Pagination<String> pagi = new Pagination<String>(28);
		List<String> finishpagi = new ArrayList<String>();
		for(String quest: u.getKeys("af-quests")) { //First unfinished
			if( !Quests.isFinished(p.getName(), quest) ) pagi.add(quest);
			else finishpagi.add(quest);
		}
		if(!finishpagi.isEmpty())
			for(String quest: finishpagi)
				pagi.add(quest);
		for(String quest: pagi.getPage(page)) {
			Quest q = Quests.quests.get(quest);
			if(q==null)continue;
			
			int stage = u.getInt("af-quests."+quest+".stage");
			String[] ss = q.getValue(stage).split("[.]");
			String name = Loader.data.getString("fish."+ss[0]+"."+ss[1]+".name");
			List<String> lore = new ArrayList<String>();
			if(Quests.isFinished(p.getName(), quest))
				for(String s: Loader.gui.getStringList("GUI.Quests.Quest_finished"))
					lore.add(s.replace("%stage%", ""+stage)
							.replace("%action%", ""+q.getAction(stage))
							.replace("%now%", ""+u.getInt("af-quests."+quest+".count")) 
							.replace("%total%", ""+q.getCount(stage)) 
							.replace("%fish%", name)
							.replace("%type%", ss[0])
							.replace("%stages_total%", ""+q.getStages()) 
							);
			else
				for(String s: Loader.gui.getStringList("GUI.Quests.Quest_progress")) //Quest_finished
					lore.add(s.replace("%stage%", ""+stage)
							.replace("%action%", ""+q.getAction(stage))
							.replace("%now%", ""+u.getInt("af-quests."+quest+".count")) 
							.replace("%total%", ""+q.getCount(stage)) 
							.replace("%fish%", name)
							.replace("%type%", ss[0])
							.replace("%stages_total%", ""+q.getStages()) 
							);
			if(Quests.isFinished(p.getName(), quest))
				a.add(new ItemGUI(Create.createItem(q.getDisplayName(),  Material.GREEN_CONCRETE, lore)) {
					@Override
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						
					}
				});
			else
				a.add(new ItemGUI(Create.createItem(q.getDisplayName(),  Material.RED_CONCRETE, lore)) {
					@Override
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						
					}
				});
		}
		if(pagi.totalPages()>page+1) {
			a.setItem(52, new ItemGUI( Create.createItem("&bNext page", Material.ARROW)) {
				
				@Override
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					openMyQuests(player, page+1);
					
				}
			});
		}
		a.setItem(49, new ItemGUI( Create.createItem(Trans.words_back(), Material.BARRIER)) {
			
			@Override
			public void onClick(Player player, HolderGUI gui, ClickType click) {
				open(p);
				
			}
		});
		a.open(p);
	}
}
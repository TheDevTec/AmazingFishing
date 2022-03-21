package me.devtec.amazingfishing.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.FishType;
import me.devtec.amazingfishing.utils.Categories;
import me.devtec.amazingfishing.utils.Categories.Category;
import me.devtec.amazingfishing.utils.Create;
import me.devtec.amazingfishing.utils.Create.Settings;
import me.devtec.amazingfishing.utils.Pagination;
import me.devtec.amazingfishing.utils.Quests;
import me.devtec.amazingfishing.utils.Quests.Quest;
import me.devtec.shared.dataholder.Config;
import me.devtec.theapi.bukkit.gui.EmptyItemGUI;
import me.devtec.theapi.bukkit.gui.GUI;
import me.devtec.theapi.bukkit.gui.GUI.ClickType;
import me.devtec.theapi.bukkit.gui.HolderGUI;
import me.devtec.theapi.bukkit.gui.ItemGUI;

public class QuestGUI {
	
	public static void open(Player p) {
		for(Category category : Quests.categories.values())
			if(Categories.hasIcon(category)) {
				openCategoryList(p, 0);
				return;
			}
		openQuests(p, 0);
	}

	private static void openQuests(Player player, int page) {
		GUI a = Create.setup(new GUI(Create.title("quests.title"),54), Create.make("quests.close").create(), f -> Help.open(f), Settings.SIDES);
		Pagination<Quest> p = new Pagination<Quest>(28, Quests.getQuests(player.getName()).values());
		if(!p.isEmpty()) {
			for(Quest q: p.getPage(page)) {
				a.add(new ItemGUI(Create.createItem(q.getDisplayName(), q.getIcon(), q.getDescription(), q.getModel(), q.getFlags(), q.isUnbreakable())) {
					
					@Override
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						if(click == ClickType.LEFT_PICKUP) {
							if(Quests.canStartNew(player.getName()) && !Quests.isInPorgress(player.getName(), q.getName())) {
								Quests.start(player, q);
							}else {
								Loader.msg(Create.text("quests.cannot-start").replace("%name%", q.getName())
										.replace("%questname%", q.getDisplayName())
										.replace("%stages%", ""+q.getStages()), player);
							}
						}
						if(click == ClickType.RIGHT_PICKUP) {
							if(Quests.canCancel(player.getName(), q)) {
								Quests.cancel(player.getName(), q.getName());
								Loader.msg(Create.text("quests.cancel").replace("%name%", q.getName())
										.replace("%questname%", q.getDisplayName())
										.replace("%stages%", ""+q.getStages()), player);
								return;
							}else {
								Loader.msg(Create.text("quests.cannot-cancel").replace("%name%", q.getName())
										.replace("%questname%", q.getDisplayName())
										.replace("%stages%", ""+q.getStages()), player);
							}
						}
						
					}
				});
			}
		if(p.totalPages()>page+1) {
			a.setItem(51, new ItemGUI(Loader.next) {
				
				@Override
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					openQuests(player, page+1);
				}
			});
		}
		if(page>0) {
			a.setItem(47, new ItemGUI(Loader.prev) {
				
				@Override
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					openQuests(player, page-1);
				}
			});
		}
		}
		a.setItem(26, new ItemGUI(Create.make("quests.own").create()) {
			@Override
			public void onClick(Player player, HolderGUI gui, ClickType click) {
				openMyQuests(player, 0);
			}
		});
		a.open(player);
	}
	
	private static void openCategoryList(Player player, int page) {
		GUI a = Create.setup(new GUI(Create.title("quests.title"),54), Create.make("quests.close").create(), f -> Help.open(f), Settings.SIDES);
		Pagination<Category> p = new Pagination<Category>(28, Quests.categories.values());
		if(!p.isEmpty()) {
			for(Category category: p.getPage(page)) {
				a.add(new ItemGUI(Create.createItem(category.getDisplayName(), category.getIcon(), category.getDescription(), category.getModel(), category.getFlags(), category.isUnbreakable())) {
					@Override
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						openCategory(player, 0, category);
					}
				});
			}
			if(p.totalPages()>page+1) {
				a.setItem(51, new ItemGUI(Loader.next) {
					
					@Override
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						openCategoryList(player, page+1);
					}
				});
			}
			if(page>0) {
				a.setItem(47, new ItemGUI(Loader.prev) {
					
					@Override
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						openCategoryList(player, page-1);
					}
				});
			}
		}
		a.setItem(26, new ItemGUI(Create.make("quests.own").create()) {
			@Override
			public void onClick(Player player, HolderGUI gui, ClickType click) {
				openMyQuests(player, 0);
				
			}
		});
		a.open(player);
	}
	
	private static void openCategory(Player player, int page, Category category) {
		GUI a = Create.setup(new GUI(Create.title("quests.title-category").replace("%category%", category.getDisplayName()),54),Create.make("quests.close").create(), f -> Help.open(f), Settings.SIDES);
		Pagination<Quest> p = new Pagination<Quest>(28);
		for(String q :  category.getContent()) {
			Quest quest = Quests.quests.get(q);
			if(quest!=null)p.add(quest);
		}
		if(!p.isEmpty()) {
			for(Quest q: p.getPage(page)) {
				a.add(new ItemGUI(Create.createItem(q.getDisplayName(), q.getIcon(), q.getDescription(), q.getModel(), q.getFlags(), q.isUnbreakable())) {
					
					@Override
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						if(click == ClickType.LEFT_PICKUP) {
							if(Quests.canStartNew(player.getName()) && !Quests.isInPorgress(player.getName(), q.getName())) {
								Quests.start(player, q);
							}else {
								Loader.msg(Create.text("quests.cannot-start").replace("%name%", q.getName())
										.replace("%questname%", q.getDisplayName())
										.replace("%stages%", ""+q.getStages()), player);
							}
						}
						if(click == ClickType.RIGHT_PICKUP) {
							if(Quests.canCancel(player.getName(), q)) {
								Quests.cancel(player.getName(), q.getName());
								Loader.msg(Create.text("quests.cancel").replace("%name%", q.getName())
										.replace("%questname%", q.getDisplayName())
										.replace("%stages%", ""+q.getStages()), player);
								return;
							}else {
								Loader.msg(Create.text("quests.cannot-cancel").replace("%name%", q.getName())
										.replace("%questname%", q.getDisplayName())
										.replace("%stages%", ""+q.getStages()), player);
							}
						}
						
					}
				});
			}
			if(p.totalPages()>page+1) {
				a.setItem(51, new ItemGUI(Loader.next) {
					
					@Override
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						openQuests(player, page+1);
					}
				});
			}
			if(page>0) {
				a.setItem(47, new ItemGUI(Loader.prev) {
					
					@Override
					public void onClick(Player player, HolderGUI gui, ClickType click) {
						openQuests(player, page-1);
					}
				});
			}
		}
		a.setItem(26, new ItemGUI(Create.make("quests.own").create()) {
			@Override
			public void onClick(Player player, HolderGUI gui, ClickType click) {
				openMyQuests(player, 0);
			}
		});
		a.open(player);
	}
	
	private static void openMyQuests(Player p, int page) {
		GUI a = Create.setup(new GUI(Create.title("quests.title-own"),54),Create.make("quests.close").create(), f -> Help.open(f));
		Config u = me.devtec.shared.API.getUser(p.getUniqueId());
		Pagination<String> pagi = new Pagination<String>(28);
		List<String> finishpagi = new ArrayList<String>();
		for(String quest: u.getKeys("af-quests")) {
			if(!Quests.isFinished(p.getName(), quest)) pagi.add(quest);
			else finishpagi.add(quest);
		}
		pagi.addAll(finishpagi);
		if(!pagi.isEmpty())
			for(String quest: pagi.getPage(page)) {
				Quest q = Quests.quests.get(quest);
				if(q==null)continue;
				
				int stage = u.getInt("af-quests."+quest+".stage");
				String[] ss = q.getValue(stage).split("\\.");
				String name = API.getFish(FishType.valueOf(ss[0].toUpperCase()),ss[1]).getDisplayName();
				List<String> lore = new ArrayList<String>();
				if(!Quests.isFinished(p.getName(), quest))
					for(String s: Loader.gui.getStringList("quests.quest-progress"))
						lore.add(s.replace("%stage%", ""+stage)
								.replace("%action%", ""+q.getAction(stage))
								.replace("%now%", ""+u.getInt("af-quests."+quest+".count")) 
								.replace("%total%", ""+q.getCount(stage)) 
								.replace("%fish%", name)
								.replace("%type%", ss[0])
								.replace("%stages_total%", ""+q.getStages()) 
								);
				a.add(new EmptyItemGUI(Quests.isFinished(p.getName(), quest) ? q.getFinishedIcon() : Create.createItem(q.getDisplayName(), q.getIcon(), lore, q.getModel(), q.getFlags(), q.isUnbreakable())));
			}
		if(pagi.totalPages()>page+1) {
			a.setItem(51, new ItemGUI(Loader.next) {
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					openMyQuests(player, page+1);
				}
			});
		}
		if(page>0) {
			a.setItem(47, new ItemGUI(Loader.prev) {
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					openMyQuests(player, page-1);
				}
			});
		}
		a.open(p);
	}
}

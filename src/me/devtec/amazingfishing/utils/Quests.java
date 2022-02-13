package me.devtec.amazingfishing.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import me.devtec.amazingfishing.utils.Categories.Category;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.apis.ItemCreatorAPI;
import me.devtec.theapi.placeholderapi.PlaceholderAPI;
import me.devtec.theapi.utils.datakeeper.Data;
import me.devtec.theapi.utils.datakeeper.User;

public class Quests {
	public static class Quest {
		private Data d;
		private String name;
		public Quest(String name, Data data) {
			this.name=name;
			d=data;
		}
		
		public String getName() {
			return name;
		}
		
		public String getDisplayName() {
			return d.getString("quests."+name+".name");
		}
		
		public List<String> getDescription() {
			List<String> list = new ArrayList<String>();
			for(String s: d.getStringList("quests."+name+".description"))
				list.add(s.replace("%name%", getName())
						.replace("%questname%", getDisplayName())
						.replace("%stages%", ""+getStages()));
			return list;
		}
		
		public List<ItemFlag> getFlags() {
			List<ItemFlag> flags = new ArrayList<>();
			for(String flag : d.getStringList("quests."+name+".flags")) {
				flags.add(ItemFlag.valueOf(flag.toUpperCase()));
			}
			return flags;
		}
		
		public boolean isUnbreakable() {
			return d.getBoolean("quests."+name+".unbreakable");
		}
		
		public int getModel() {
			return d.getInt("quests."+name+".model");
		}
		
		public ItemCreatorAPI getIcon() {
			if(d.exists("quests."+name+".icon")||d.exists("quests."+name+".head"))
				return Create.find(d.exists("quests."+name+".head")?"head:"+d.getString("quests."+name+".head"):d.getString("quests."+name+".icon"), "STONE", 0);
			return new ItemCreatorAPI(Utils.getCachedMaterial("SUNFLOWER").toItemStack());
		}
		
		public int getStages() {
			return d.getKeys("quests."+name+".stages").size();
		}
		
		public List<String> getCommands(int stage) {
			return d.getStringList("quests."+name+".stages."+stage+".commands");
		}
		
		public List<String> getMessages(int stage) {
			return d.getStringList("quests."+name+".stages."+stage+".messages");
		}
		
		public List<String> getStartCommands() {
			return d.getStringList("quests."+name+".start.commands");
		}
		public List<String> getStartMessages() {
			return d.getStringList("quests."+name+".start.messages");
		}

		//action = catch_fish, eat_fish, sell_fish
		public String getAction(int stage) {
			return d.getString("quests."+name+".stages."+stage+".action");
		}

		//value = type_of_fish.name_of_fish -> cod.nazev
		public String getValue(int stage) {
			return d.getString("quests."+name+".stages."+stage+".value");
		}
		
		public int getCount(int stage) {
			return d.getInt("quests."+name+".stages."+stage+".count");
		}
	}
	
	public static Map<String, Quest> quests = new HashMap<>();
	public static Map<String, Category> categories = new HashMap<>(); // name, Category
	
	public static void register(Quest quest) {
		quests.put(quest.getName(), quest);
	}
	
	public static void unregister(Quest quest) {
		quests.remove(quest.getName());
	}
	
	public static void unregister(String quest) {
		quests.remove(quest);
	}
	public static void addToCategory(Category category) {
		categories.put(category.getName(), category);
	}
	
	//PlayerName, Set<Object[QuestName, Stage]>
	private static Map<String, Set<Object[]>> progress = new HashMap<>();
	
	//action = catch_fish, eat_fish, sell_fish
	//value = type_of_fish.name_of_fish -> cod.nazev
	public static void addProgress(Player player, String action, String value, int amount) {
		if(!progress.containsKey(player.getName())) {
			load(player.getName());
		}
		check(player.getName());
		Iterator<Object[]> it = progress.get(player.getName()).iterator();
		if(it==null)
			progress.remove(player.getName());
		else
		while(it.hasNext()) {
			Object[] a = it.next();
			// a[0] - Quest name
			// a[1] - Quest stage
			// a[2] - Progress - chyceno ryb třeba
			Quest q = quests.get(a[0]);
			String ac = q.getAction((int)a[1]);
			if(!action.equalsIgnoreCase(ac))continue;
			String v = q.getValue((int)a[1]);
			if(!value.equalsIgnoreCase(v))continue;
			int c = q.getCount((int)a[1]);
			a[2]=(int)a[2]+amount;
			if(c<=(int)a[2]) {
				TheAPI.getNmsProvider().postToMainThread(new Runnable() {
					public void run() {
						for(String cmd : q.getCommands((int)a[1]))
							TheAPI.sudoConsole(PlaceholderAPI.setPlaceholders(player, cmd.replace("%player%", player.getName()).replace("%quest%", q.getDisplayName()).replace("%questname%", q.getName()).replace("%prefix%", Trans.prefix()) ) );
						for(String cmd : q.getMessages((int)a[1]))
							TheAPI.msg(PlaceholderAPI.setPlaceholders(player, cmd.replace("%player%", player.getName()).replace("%quest%", q.getDisplayName()).replace("%questname%", q.getName()).replace("%prefix%", Trans.prefix()) ),player);
					}
				});
				if(q.getStages()<((int)a[1]+1)) { //END OF QUEST
					finish(player.getName(), q.getName());
					it.remove();
				}else { // PREPARE FOR NEXT STAGE
					a[1]=(int)a[1]+1;
					a[2]=0;
				}
			}
		}
		save(player.getName());
	}

	public static void check(String name) {
		Iterator<Object[]> it = progress.get(name).iterator();
		while(it.hasNext()) {
			Object[] o = it.next();
			if(!quests.containsKey((String)o[0]))it.remove();
		}
	}

	public static void save(String name) {
		User a = TheAPI.getUser(name);
		Iterator<Object[]> it = progress.get(name).iterator();
		while(it.hasNext()) {
			Object[] o = it.next();
			a.set("af-quests."+o[0]+".stage", o[1]);
			a.set("af-quests."+o[0]+".count", o[2]);
		}
		a.save();
	}

	public static void loadNew(String name) {
		if( !progress.containsKey(name) ) {
			load(name);
		}
	}
	
	public static void load(String name) {
		User a = TheAPI.getUser(name);
		Set<Object[]> set = new HashSet<>();
		for(String s : a.getKeys("af-quests"))
			if(quests.containsKey(s) && !isFinished(name, s))
				set.add(new Object[] {s, a.getInt("af-quests."+s+".stage"), a.getInt("af-quests."+s+".count") });
		progress.put(name, set);
	}
	
	public static void unload(String name) {
		User a = TheAPI.getUser(name);
		Iterator<Object[]> it = progress.remove(name).iterator();
		a.remove("af-quests");
		while(it.hasNext()) {
			Object[] o = it.next();
			a.set("af-quests."+o[0]+".stage", o[1]);
			a.set("af-quests."+o[0]+".count", o[2]);
		}
		a.save();
	}

	public static void remove(String name) {
		progress.remove(name);
	}
	
	public static void start(Player player, Quest quest) {
		User a = TheAPI.getUser(player);
		a.set("af-quests."+quest.getName()+".finished", false);
		int i = 0;
		if(quest.getAction(0)==null || quest.getAction(0).equalsIgnoreCase("")) i =1;
		a.set("af-quests."+quest.getName()+".stage", i);
		a.set("af-quests."+quest.getName()+".count", 0);
		a.save();
		load(player.getName());
		
		TheAPI.getNmsProvider().postToMainThread(new Runnable() {
			public void run() {
				for(String cmd : quest.getStartCommands() )
					TheAPI.sudoConsole(PlaceholderAPI.setPlaceholders(player, cmd.replace("%player%", player.getName())
							.replace("%quest%", quest.getDisplayName()).replace("%questname%", quest.getName()).replace("%prefix%", Trans.prefix()) ) );
				for(String cmd : quest.getStartMessages())
					TheAPI.msg(PlaceholderAPI.setPlaceholders(player, cmd.replace("%player%", player.getName())
							.replace("%quest%", quest.getDisplayName()).replace("%questname%", quest.getName()).replace("%prefix%", Trans.prefix()) ),player);
			}
		});
	}
	public static void finish(String name, String quest) {
		User a = TheAPI.getUser(name);
		a.set("af-quests."+quest+".finished", true);
		a.save();
	}
	public static void cancel(String name, String quest) {
		User a = TheAPI.getUser(name);
		a.remove("af-quests."+quest );
		a.save();
	}
	public static boolean haveQuest(String name) {
		if (progress.containsKey(name))return true;
		return false;
	}
	public static boolean isFinished(String player, String quest) {
		return TheAPI.getUser(player).getBoolean("af-quests."+quest+".finished");
	}	
	public static boolean isInPorgress(String player, String quest) {
		User u = TheAPI.getUser(player);
		if(u.exist("af-quests."+quest) ) return true;
		return false;
	}	
	public static boolean canStartNew(String player) {
		if(getActiveQuests(player).size()>=3) return false;
		return true;
	}	
	public static boolean canCancel(String player, Quest quest) {
		if(!TheAPI.getUser(player).exist("af-quests."+quest.getName()) ) return false;
		if(isFinished(player, quest.getName())) return false;
		else return true;
	}
	public static Map<String, Quest> getQuests(String player) {
		Map<String, Quest> q = new HashMap<>();
		for(Quest quest: quests.values()) {
			if(!isFinished(player, quest.getName()))
				q.put(quest.getName(), quest);
		}
		return q;
	}
	public static Map<String, Quest> getActiveQuests(String player) {
		Map<String, Quest> q = new HashMap<>();
		User u = TheAPI.getUser(player);
		for(String quest: u.getKeys("af-quests")) {
			if(!isFinished(player, quest))
				q.put(quest, quests.get(quest));
		}
		return q;
	}
}

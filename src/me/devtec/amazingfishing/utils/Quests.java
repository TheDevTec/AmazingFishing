package me.devtec.amazingfishing.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.devtec.theapi.TheAPI;
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
		
		public int getStages() {
			return d.getKeys("quests."+name+".stages").size();
		}
		
		public List<String> getCommands(int stage) {
			return d.getStringList("quests."+name+".stages."+stage+".commands");
		}
		
		public List<String> getMessages(int stage) {
			return d.getStringList("quests."+name+".stages."+stage+".messages");
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
	
	public static void register(Quest quest) {
		quests.put(quest.getName(), quest);
	}
	
	public static void unregister(Quest quest) {
		quests.remove(quest.getName());
	}
	
	public static void unregister(String quest) {
		quests.remove(quest);
	}
	
	//PlayerName, Set<Object[QuestName, Stage]>
	private static Map<String, Set<Object[]>> progress = new HashMap<>();
	
	//action = catch_fish, eat_fish, sell_fish
	//value = type_of_fish.name_of_fish -> cod.nazev
	public static void addProgress(Player player, String action, String value) {
		Bukkit.broadcastMessage(progress.toString());
		if(!progress.containsKey(player.getName()))return;
		check(player.getName());
		Iterator<Object[]> it = progress.get(player.getName()).iterator();
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
			a[2]=(int)a[2]+1;
			Bukkit.broadcastMessage(c+" ; "+a[2]);
			if(c<=(int)a[2]) {
				Bukkit.broadcastMessage("něco ; "+a[1]);
				for(String cmd : q.getCommands((int)a[1]))
					TheAPI.sudoConsole(PlaceholderAPI.setPlaceholders(player, cmd.replace("%player%", player.getName()).replace("%quest%", q.getDisplayName()).replace("%questname%", q.getName()).replace("%prefix%", Trans.prefix()) ) );
				for(String cmd : q.getMessages((int)a[1]))
					TheAPI.msg(PlaceholderAPI.setPlaceholders(player, cmd.replace("%player%", player.getName()).replace("%quest%", q.getDisplayName()).replace("%questname%", q.getName()).replace("%prefix%", Trans.prefix()) ),player);
				if(q.getStages()<((int)a[1]+1)) { //END OF QUEST
					finish(player.getName(), q.getName());
					it.remove();
					progress.remove(player.getName());
				}else { // PREPARE FOR NEXT STAGE
					a[1]=(int)a[1]+1;
					a[2]=0;
				}
			}else
				Bukkit.broadcastMessage("nic");
		}
	}

	public static void check(String name) {
		Iterator<Object[]> it = progress.get(name).iterator();
		Object[] o;
		for(o = it.next(); it.hasNext();)
			if(!quests.containsKey((String)o[0]))it.remove();
	}

	public static void save(String name) {
		User a = TheAPI.getUser(name);
		Iterator<Object[]> it = progress.get(name).iterator();
		a.remove("af-quests");
		while(it.hasNext()) {
			Object[] o = it.next();
			a.set("af-quests."+o[0], o[1]);
		}
		a.save();
	}
	
	public static void load(String name) {
		User a = TheAPI.getUser(name);
		Set<Object[]> set = new HashSet<>();
		for(String s : a.getKeys("af-quests"))
			if(quests.containsKey(s))
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
	
	public static void start(String name, Quest quest) {
		User a = TheAPI.getUser(name);
		a.set("af-quests."+quest.getName()+".finished", false);
		a.save();
		load(name);
	}
	public static void finish(String name, String quest) {
		User a = TheAPI.getUser(name);
		a.set("af-quests."+quest+".finished", true);
		a.save();
	}
	public static boolean haveQuest(String name) {
		if (progress.containsKey(name))return true;
		return false;
	}
}

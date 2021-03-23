package me.devtec.amazingfishing.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
			return d.getString("Quests."+name+".Name");
		}
		
		public int getStages() {
			return d.getKeys("Quests."+name+".Stages").size();
		}
		
		public List<String> getCommands(int stage) {
			return d.getStringList("Quests."+name+".Stages."+stage+".Commands");
		}
		
		public List<String> getMessages(int stage) {
			return d.getStringList("Quests."+name+".Stages."+stage+".Messages");
		}
		
		public String getAction(int stage) {
			return d.getString("Quests."+name+".Stages."+stage+".Action");
		}
		
		public String getValue(int stage) {
			return d.getString("Quests."+name+".Stages."+stage+".Value");
		}
		
		public int getCount(int stage) {
			return d.getInt("Quests."+name+".Stages."+stage+".Count");
		}
	}
	
	private static Map<String, Quest> quests = new HashMap<>();
	
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
		if(!progress.containsKey(player.getName()))return;
		check(player.getName());
		Iterator<Object[]> it = progress.get(player.getName()).iterator();
		while(it.hasNext()) {
			Object[] a = it.next();
			Quest q = quests.get(a[0]);
			String ac = q.getAction((int)a[1]);
			if(!action.equalsIgnoreCase(ac))continue;
			String v = q.getValue((int)a[1]);
			if(!value.equalsIgnoreCase(v))continue;
			int c = q.getCount((int)a[1]);
			a[2]=(int)a[2]+1;
			if(c<=(int)a[2]) {
				for(String cmd : q.getCommands((int)a[1]))
					TheAPI.sudoConsole(PlaceholderAPI.setPlaceholders(player, cmd.replace("%player%", player.getName()).replace("%quest%", q.getDisplayName()).replace("%questname%", q.getName())));
				for(String cmd : q.getMessages((int)a[1]))
					TheAPI.msg(PlaceholderAPI.setPlaceholders(player, cmd.replace("%player%", player.getName()).replace("%quest%", q.getDisplayName()).replace("%questname%", q.getName())),player);
				if(q.getStages()<=((int)a[1]+1)) {
					it.remove();
				}else {
					a[1]=(int)a[1]+1;
				}
			}
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
			if(!quests.containsKey(s))
				set.add(new Object[] {s,a.getInt("af-quests."+s+".stage"),a.getInt("af-quests."+s+".count")});
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
}

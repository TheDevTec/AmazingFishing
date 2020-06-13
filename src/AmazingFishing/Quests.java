package AmazingFishing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.DevTec.TheAPI;
import me.DevTec.TheAPI.SudoType;
import me.DevTec.GUI.GUICreatorAPI;
import me.DevTec.GUI.GUICreatorAPI.Options;

public class Quests {
	public static void openQuestMenu(Player p) {
		
		GUICreatorAPI a = TheAPI.getGUICreatorAPI(p);
		a.setTitle("&6Quests");
		a.setSize(54);
		Create.prepareInv(a);
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_BE_TAKEN, true);
		w.put(Options.CANT_PUT_ITEM, true);
		String quest = API.getQuest(p);
		if(quest==null) {
			API.cancelQuest(p);
			selectQuest(p);
			return;
		}
		if(Loader.c.getString("Quests."+quest+".Time")!=null) { //is timed quest
			if(TheAPI.getCooldownAPI("amazingfishing.quests."+quest).expired(p.getName())) {
				API.cancelQuest(p);
				selectQuest(p);
				return;
			}
		}
		int stage = Loader.me.getInt("Players."+p.getName()+".Quests.Stage");
		String questInfo = null;
		int amount =Loader.c.getInt("Quests."+quest+".Stage."+stage+".Amount");
		if(Actions.valueOf(Loader.c.getString("Quests."+quest+".Stage."+stage+".Action").toUpperCase()).equals(Actions.CATCH_FISH)) {
			String typ = Loader.c.getString("Quests."+quest+".Stage."+stage+".Type");
			String fis = Loader.c.getString("Quests."+quest+".Stage."+stage+".Fish");
			String fishname=Loader.c.getString("Types."+typ+"."+fis+".Name");
			questInfo="Catch "+amount+"x "+fishname;
		}
		if(Actions.valueOf(Loader.c.getString("Quests."+quest+".Stage."+stage+".Action").toUpperCase()).equals(Actions.SELL_FISH)) {
			String typ = Loader.c.getString("Quests."+quest+".Stage."+stage+".Type");
			String fis = Loader.c.getString("Quests."+quest+".Stage."+stage+".Fish");
			String fishname=Loader.c.getString("Types."+typ+"."+fis+".Name");
			questInfo="Sell "+amount+"x "+fishname;
		}
		int questStages =Loader.c.getConfigurationSection("Quests."+quest+".Stage").getKeys(false).size()-1;
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				if(stage>=questStages) {
				Loader.me.set("Players."+p.getName()+".Quests", null);
				Loader.saveChatMe();
				if(Loader.c.getString("Quests."+quest+".Rewards.Commands")!=null)
				for(String s :Loader.c.getStringList("Quests."+quest+".Rewards.Commands")) {
					TheAPI.sudoConsole(SudoType.COMMAND, TheAPI.colorize(s.replace("%player%", p.getName()).replace("%quest%", quest)));
				}
				if(Loader.c.getString("Quests."+quest+".Rewards.Messages")!=null)
				for(String s :Loader.c.getStringList("Quests."+quest+".Rewards.Messages")) {
					TheAPI.getPlayerAPI(p).msg(s.replace("%player%", p.getName()).replace("%quest%", quest));
				}
				if(Loader.c.getString("Quests."+quest+".Rewards.Items")!=null)
				for(String s :Loader.c.getStringList("Quests."+quest+".Rewards.Items")) {
					TheAPI.giveItem(p, Loader.c.getItemStack("Quests."+quest+".Rewards.Items."+s));
				}
				selectQuest(p);
				}
			}});
		a.setItem(20, Create.createItem("&2Finish quest", Material.GREEN_WOOL),w); //Finish quest (If available)
		w.remove(Options.RUNNABLE);
		a.setItem(22, Create.createItem("&6Info about quest", Material.PAPER, Arrays.asList("&7 - Quest "+Loader.c.getString("Quests."+quest+".Name"),"&7 - "+questInfo
				,"&7 - Progress: "+Loader.c.getInt("Players."+p.getName()+".Stage."+stage+".Amount")+"/"+Loader.c.getInt("Quests."+quest+".Stage."+stage+".Amount"),"&7 - Stage: "+stage+"/"+questStages)),w); //Info about quest
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				Loader.me.set("Players."+p.getName()+".Quests."+quest, null);
				selectQuest(p);
			}});
		a.setItem(24, Create.createItem("&cCancel quest", Material.RED_WOOL),w); //Cancel quest
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				p.getOpenInventory().close();
			}});
		a.setItem(49, Create.createItem(Trans.close(), Material.BARRIER),w); //Cancel quest
		a.open();
	}
	
	public static enum Actions {
		CATCH_FISH,
		SELL_FISH
		
	}
	
	public static void selectQuest(Player p) {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI(p);
		a.setTitle("&6Quests &7- &eSelect Quest");
		a.setSize(54);
		Create.prepareInv(a);
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_BE_TAKEN, true);
		w.put(Options.CANT_PUT_ITEM, true);
		List<Object> list = new ArrayList<Object>();
		if(Loader.c.getString("Quests")!=null)
		for(String s: Loader.c.getConfigurationSection("Quests").getKeys(false))list.add(s);
		String q = TheAPI.getRandomFromList(list).toString();
		if(q!=null) {
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					Actions s = Actions.valueOf(Loader.c.getString("Quests."+q+".Stage.0.Action").toUpperCase());
					if(s==null)return;
					if(s==Actions.CATCH_FISH||s==Actions.SELL_FISH){
						Loader.me.set("Players."+p.getName()+".Quests.Current", q);
						Loader.me.set("Players."+p.getName()+".Quests.Stage", 0);
						if(Loader.c.getString("Quests."+q+".Time")!=null)
						TheAPI.getCooldownAPI("amazingfishing.quests."+q).createCooldown(p.getName(), TheAPI.getStringUtils().getTimeFromString(Loader.c.getString("Quests."+q+".Time")));
						}
					Loader.saveChatMe();
					openQuestMenu(p);
				}});
		a.setItem(20, Create.createItem("&aAccept quest", Material.GREEN_WOOL),w);
		String questInfo = Loader.c.getString("Quests."+q+".Description");
		String questStages =Loader.c.getConfigurationSection("Quests."+q+".Stage").getKeys(false).size()-1+"";

		w.remove(Options.RUNNABLE);
		a.setItem(22, Create.createItem("&6Info about quest", Material.PAPER, Arrays.asList("&7 - "+Loader.c.getString("Quests."+q+".Name"),"&7 - "+questInfo
				,"&7 - Stages: "+questStages)),w);
		}

		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				selectQuest(p);
			}});
		a.setItem(24, Create.createItem("&6Another quest", Material.ORANGE_WOOL),w);
		w.remove(Options.RUNNABLE);
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				p.getOpenInventory().close();
			}});
		a.setItem(49, Create.createItem(Trans.close(), Material.BARRIER),w);
		a.open();
	}
	public static void addProgress(Player p, String type, String fish, Actions c) {
		String q = API.getQuest(p);
		if(q!=null) {
			int next=Loader.me.getInt("Players."+p.getName()+".Quests.Stage")+1;
			Actions s = Actions.valueOf(Loader.c.getString("Quests."+q+".Stage."+(next-1)+".Action").toUpperCase());
				if(type.equals(Loader.c.getString("Quests."+q+".Stage."+(next-1)+".Type")) && fish.equals(Loader.c.getString("Quests."+q+".Stage."+(next-1)+".Fish"))) {
					
					if(Loader.c.getString("Quests."+q+".Stage."+next)==null)return;
					int amount = Loader.c.getInt("Quests."+q+".Stage."+(next-1)+".Amount")+1;
					Loader.me.set("Players."+p.getName()+".Quests.Amount", amount);
					if(amount>=Loader.c.getInt("Quests."+q+".Stage."+next+".Amount")) {
						Loader.me.set("Players."+p.getName()+".Quests.Stage", next);
						Loader.me.set("Players."+p.getName()+".Quests.Amount", 0);
					TheAPI.getPlayerAPI(p).msg(Loader.s("Prefix")+"&6-= Quest &a"+q+"&r&6 =-");
					TheAPI.getPlayerAPI(p).msg(Loader.s("Prefix")+"&6+ Stage "+next+"/"+(Loader.c.getConfigurationSection("Quests."+q+".Stage").getKeys(false).size()-1));
					String action = "&aCatch fish";
					if(s==Actions.SELL_FISH)
						action="&eSell fish";
					String typ = Loader.c.getString("Quests."+q+".Stage."+next+".Type");
					String fis = Loader.c.getString("Quests."+q+".Stage."+next+".Fish");
					TheAPI.getPlayerAPI(p).msg(Loader.s("Prefix")+"&6+ Action: &c"+action);
					TheAPI.getPlayerAPI(p).msg(Loader.s("Prefix")+"&6+ Fish: &c"+Loader.c.getString("Types."+typ+"."+fis+".Name"));
					TheAPI.getPlayerAPI(p).msg(Loader.s("Prefix")+"&6+ Amount: &c"+Loader.c.getInt("Quests."+q+".Stage."+next+".Amount"));
				}
					Loader.saveChatMe();
			}
		}
	}
	
	public static void open(Player p) { 
		//editor
		//Planned
	}

}

package AmazingFishing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import me.DevTec.TheAPI;
import me.DevTec.TheAPI.SudoType;
import me.DevTec.AmazingFishing.Loader;
import me.DevTec.GUI.GUICreatorAPI;
import me.DevTec.GUI.ItemGUI;
import me.DevTec.Other.StringUtils;

public class Quests {
	public static void openQuestMenu(Player p) {
		GUICreatorAPI a = new GUICreatorAPI("&6Quests",54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
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

		a.setItem(20, new ItemGUI(Create.createItem("&2Finish quest", Material.GREEN_WOOL)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				if(stage>=questStages) {
					Loader.me.set("Players."+p.getName()+".Quests", null);
					Loader.saveChatMe();
					if(Loader.c.getString("Quests."+quest+".Rewards.Commands")!=null)
					for(String s :Loader.c.getStringList("Quests."+quest+".Rewards.Commands")) {
						TheAPI.sudoConsole(SudoType.COMMAND, TheAPI.colorize(s.replace("%player%", p.getName()).replace("%quest%", quest)));
					}
					if(Loader.c.getString("Quests."+quest+".Rewards.Messages")!=null)
					for(String s :Loader.c.getStringList("Quests."+quest+".Rewards.Messages")) {
						TheAPI.msg(s.replace("%player%", p.getName()).replace("%quest%", quest),p);
					}
					if(Loader.c.getString("Quests."+quest+".Rewards.Items")!=null)
					for(String s :Loader.c.getStringList("Quests."+quest+".Rewards.Items")) {
						TheAPI.giveItem(p, Loader.c.getItemStack("Quests."+quest+".Rewards.Items."+s));
					}
					selectQuest(p);
					}
			}}); //Finish quest (If available)
		a.setItem(22,new ItemGUI(
				Create.createItem("&6Info about quest", Material.PAPER, Arrays.asList("&7 - Quest "+Loader.c.getString("Quests."+quest+".Name")
				,"&7 - "+questInfo
				,"&7 - Progress: "+Loader.c.getInt("Players."+p.getName()+".Stage."+stage+".Amount")
				+"/"+Loader.c.getInt("Quests."+quest+".Stage."+stage+".Amount"),"&7 - Stage: "+stage+"/"+questStages))){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
			}
		}); //Info about quest
		a.setItem(24, new ItemGUI(Create.createItem("&cCancel quest", Material.RED_WOOL)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				Loader.me.set("Players."+p.getName()+".Quests."+quest, null);
				selectQuest(p);
			}
		}); //Cancel quest
		a.setItem(49, new ItemGUI(Create.createItem(Trans.close(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				p.getOpenInventory().close();
			}
		}); //Cancel quest
	}

	public static enum Actions {
		CATCH_FISH,
		SELL_FISH
		
	}

	public static void selectQuest(Player p) {
		GUICreatorAPI a = new GUICreatorAPI("&6Quests &7- &eSelect Quest",54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		List<Object> list = new ArrayList<Object>();
		if(Loader.c.getString("Quests")!=null)
		for(String s: Loader.c.getConfigurationSection("Quests").getKeys(false))list.add(s);
		String q = TheAPI.getRandomFromList(list).toString();

		if(q!=null) {
		a.setItem(20, new ItemGUI(Create.createItem("&aAccept quest", Material.GREEN_WOOL)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				Actions s = Actions.valueOf(Loader.c.getString("Quests."+q+".Stage.0.Action").toUpperCase());
				if(s==null)return;
				if(s==Actions.CATCH_FISH||s==Actions.SELL_FISH){
					Loader.me.set("Players."+p.getName()+".Quests.Current", q);
					Loader.me.set("Players."+p.getName()+".Quests.Stage", 0);
					if(Loader.c.getString("Quests."+q+".Time")!=null)
					TheAPI.getCooldownAPI("amazingfishing.quests."+q).createCooldown(p.getName(), StringUtils.getTimeFromString(Loader.c.getString("Quests."+q+".Time")));
					}
				Loader.saveChatMe();
				openQuestMenu(p);
			}
		});
		String questInfo = Loader.c.getString("Quests."+q+".Description");
		String questStages =Loader.c.getConfigurationSection("Quests."+q+".Stage").getKeys(false).size()-1+"";

		a.setItem(22,new ItemGUI(Create.createItem("&6Info about quest", Material.PAPER,
				Arrays.asList("&7 - "+Loader.c.getString("Quests."+q+".Name"),"&7 - "+questInfo
				,"&7 - Stages: "+questStages))){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
			}
		});
		}

		a.setItem(24, new ItemGUI(Create.createItem("&6Another quest", Material.ORANGE_WOOL)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				selectQuest(p);
			}
		});
		
		a.setItem(49, new ItemGUI(Create.createItem(Trans.close(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				p.getOpenInventory().close();
			}
		});
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
					TheAPI.msg(Loader.s("Prefix")+"&6-= Quest &a"+q+"&r&6 =-",p);
					TheAPI.msg(Loader.s("Prefix")+"&6+ Stage "+next+"/"+(Loader.c.getConfigurationSection("Quests."+q+".Stage").getKeys(false).size()-1),p);
					String action = "&aCatch fish";
					if(s==Actions.SELL_FISH)
						action="&eSell fish";
					String typ = Loader.c.getString("Quests."+q+".Stage."+next+".Type");
					String fis = Loader.c.getString("Quests."+q+".Stage."+next+".Fish");
					TheAPI.msg(Loader.s("Prefix")+"&6+ Action: &c"+action,p);
					TheAPI.msg(Loader.s("Prefix")+"&6+ Fish: &c"+Loader.c.getString("Types."+typ+"."+fis+".Name"),p);
					TheAPI.msg(Loader.s("Prefix")+"&6+ Amount: &c"+Loader.c.getInt("Quests."+q+".Stage."+next+".Amount"),p);
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

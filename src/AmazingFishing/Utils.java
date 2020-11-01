package AmazingFishing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;

import AmazingFishing.API.treasureType;
import AmazingFishing.RD.Type;
import me.DevTec.AmazingFishing.Loader;
import me.DevTec.TheAPI.TheAPI;
import me.DevTec.TheAPI.EconomyAPI.EconomyAPI;

public class Utils {
	
	private static void g(Player p) {
		List<String> legend = new ArrayList<String>();
		boolean c,r,e,l;
		c=RD.hasAccess(p, Type.Common)||WG.hasAccess(p, AmazingFishing.WG.Type.Common);
		r=RD.hasAccess(p, Type.Rare)||WG.hasAccess(p, AmazingFishing.WG.Type.Rare);
		e=RD.hasAccess(p, Type.Epic)||WG.hasAccess(p, AmazingFishing.WG.Type.Epic);
		l=RD.hasAccess(p, Type.Legendary)||WG.hasAccess(p, AmazingFishing.WG.Type.Legendary);
		if(c) {
		legend.add("Common");
		legend.add("Common");
		legend.add("Common");
		legend.add("Common");
		legend.add("Common");
		legend.add("Common");
		legend.add("Common");
		legend.add("Common");
		legend.add("Common");
		legend.add("Common");
		legend.add("Common");
		legend.add("Common");
		legend.add("Common");
		legend.add("Common");
		legend.add("Common");
		legend.add("Common");
		legend.add("Common");
		}
		if(r) {
		legend.add("Rare");
		legend.add("Rare");
		legend.add("Rare");
		legend.add("Rare");
		legend.add("Rare");
		}
		if(e) {
		legend.add("Epic");
		legend.add("Epic");
		}
		if(l)
		legend.add("Legendary");
		Collections.shuffle(legend);
		String select = TheAPI.getRandomFromList(legend).toString();
			if(Loader.c.exists("Treasures."+select.toUpperCase())) {
				if(Type.valueOf(select)!=null && WG.Type.valueOf(select)!=null) {
					if(RD.hasAccess(p, Type.valueOf(select))|| WG.hasAccess(p, AmazingFishing.WG.Type.valueOf(select))) {
						API.giveTreasureByChance(p, treasureType.valueOf(select.toUpperCase()));
					}
				}
	}}
	
	
	public static void giveTreasure(Player p) {
		List<Integer> list = new ArrayList<Integer>(); // numbers from 0 to 99
		int chance = Loader.c.getInt("Options.Manual.ChanceForTreasure");
		if(chance==0)chance=1;
		for(int i = 0; i < 100; ++i)
			list.add(i);
		int number = list.get(new Random().nextInt(99)); //random number from list
		boolean run = true;
		for (int counter =0; counter<chance; counter++) { // chance 5 ---> 0-4 == 5 chances
			int random = new Random().nextInt(99);
			if(run) {
			if(number == random) {
				run=false;
				g(p);
			}}
		}
		list.clear();
		}
	
	public static void addRecord(Player p, String fish, String type, double record, double weight) {
		int amount = Loader.me.getInt("Players."+p.getName()+".Stats.Amount")+1;
		if(!Loader.me.exists("Players."+p.getName()+"."+type+"."+fish)) {
			Loader.me.set("Players."+p.getName()+"."+type+"."+fish+".Length", record);
			Loader.me.set("Players."+p.getName()+"."+type+"."+fish+".Weight", weight);
			Loader.me.set("Players."+p.getName()+".Stats.Amount", amount);
			Loader.me.set("Players."+p.getName()+".Stats.Fish", fish);
			Loader.me.set("Players."+p.getName()+".Stats.Type", type);
			Loader.me.set("Players."+p.getName()+".Stats.Length", record);
			Loader.me.set("Players."+p.getName()+".Stats.Weight", weight);
			Loader.saveChatMe();
			giveReward(p,fish,type);
		}else {
			Loader.me.set("Players."+p.getName()+".Stats.Amount", amount);
			Loader.saveChatMe();
			
			if(Loader.me.getDouble("Players."+p.getName()+".Stats.Length")<record ||!Loader.me.exists("Players."+p.getName()+".Stats.Type")) {
				Loader.me.set("Players."+p.getName()+".Stats.Fish", fish);
				Loader.me.set("Players."+p.getName()+".Stats.Type", type);
				Loader.me.set("Players."+p.getName()+".Stats.Length", record);
				Loader.saveChatMe();
			}
			if(Loader.me.getDouble("Players."+p.getName()+".Stats.Weight")<weight) {
				Loader.me.set("Players."+p.getName()+".Stats.Fish", fish);
				Loader.me.set("Players."+p.getName()+".Stats.Type", type);
				Loader.me.set("Players."+p.getName()+".Stats.Weight", weight);
				Loader.saveChatMe();
			}
			if(Loader.me.getDouble("Players."+p.getName()+"."+type+"."+fish+".Weight")<weight) {
				Loader.me.set("Players."+p.getName()+"."+type+"."+fish+".Length", weight);
				Loader.saveChatMe();
			}
			if(Loader.me.getDouble("Players."+p.getName()+"."+type+"."+fish+".Length")<record) {
				double last = Loader.me.getDouble("Players."+p.getName()+"."+type+"."+fish+".Length");
				Loader.me.set("Players."+p.getName()+"."+type+"."+fish+".Length", record);
				String name = Loader.c.getString("Types."+type+"."+fish+".Name");
				if(name==null)name=fish;
				if(!Loader.me.getBoolean("Players."+p.getName()+".Toggle")) {
					if(!Loader.c.getBoolean("Options.UseDoubles.Length")) {
						Loader.msgCmd(Loader.s("Prefix")+Loader.s("ReachNewRecord").replace("%record%", (record+"").replace(".0", "")).replace("%fish%", name).replace("%last%", (last+"").replace(".0", "")), p);
					}else {
				Loader.msgCmd(Loader.s("Prefix")+Loader.s("ReachNewRecord").replace("%record%", record+"").replace("%fish%", name).replace("%last%", last+""), p);
					}
				}
				Loader.saveChatMe();
				giveReward(p,fish,type);
			}
		}}
	public static void giveReward(Player p, String fish, String type) {
		if(Loader.me.getDouble("Players."+p.getName()+"."+type+"."+fish+".Length")>=Loader.c.getDouble("Types."+type+"."+fish+".MaxCm")-5) {
			EconomyAPI.depositPlayer(p.getName(), Loader.c.getDouble("Types."+type+"."+fish+".Money")+(Loader.c.getDouble("Types."+type+"."+fish+".Money")%85));
		}}

	public static String trasfer(int i) {
		String s= "I";
		switch(i) {
		case 1:
			s="I";
			break;
		case 2:
			s="II";
			break;
		case 3:
			s="III";
			break;
		case 4:
			s="IV";
			break;
		case 5:
			s="V";
			break;
		case 6:
			s="VI";
			break;
		case 7:
			s="VII";
			break;
		case 8:
			s="VIII";
			break;
		case 9:
			s="IX";
			break;
		case 10:
			s="X";
			break;
		default:
			s=""+i;
			break;
		}
		return s;
	}}
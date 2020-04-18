package AmazingFishing;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.Straiker123.TheAPI;

public class Utils {
	
	public static List<String> createShuffleList(List<String> input){
		List<String> s = new ArrayList<String>();
		int size = input.size();
		for(int i = size; i > 0; --i) {
			String random = TheAPI.getRandomFromList(input).toString();
		s.add(random);
		input.remove(random);
		}
		return s;
	}
	
	public static void addRecord(Player p, String fish, String type, double record, double weight) {
		int amount = Loader.me.getInt("Players."+p.getName()+".Stats.Fish")+1;
		if(Loader.me.getString("Players."+p.getName()+"."+type+"."+fish)==null) {
			Loader.me.set("Players."+p.getName()+"."+type+"."+fish+".Length", record);
			Loader.me.set("Players."+p.getName()+"."+type+"."+fish+".Weight", weight);
			Loader.me.set("Players."+p.getName()+".Stats.Fish", amount);
			Loader.me.set("Players."+p.getName()+".Stats.Fish", fish);
			Loader.me.set("Players."+p.getName()+".Stats.Type", type);
			Loader.me.set("Players."+p.getName()+".Stats.Length", record);
			Loader.me.set("Players."+p.getName()+".Stats.Weight", weight);
			Loader.saveChatMe();
			giveReward(p,fish,type);
		}else {
			Loader.me.set("Players."+p.getName()+".Stats.Fish", amount);
			Loader.saveChatMe();
			
			if(Loader.me.getDouble("Players."+p.getName()+".Stats.Length")<record ||Loader.me.getString("Players."+p.getName()+".Stats.Type")==null) {
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
				if(!Loader.me.getBoolean("Players."+p.getName()+".Toggle"))
				Loader.msgCmd(Loader.s("Prefix")+Loader.s("ReachNewRecord").replace("%record%", record+"").replace("%fish%", name).replace("%last%", last+""), p);
				Loader.saveChatMe();
				giveReward(p,fish,type);
			}
		}}
	public static void giveReward(Player p, String fish, String type) {
		if(Loader.me.getDouble("Players."+p.getName()+"."+type+"."+fish+".Length")>=Loader.c.getDouble("Types."+type+"."+fish+".MaxCm")-5) {
			TheAPI.getEconomyAPI().depositPlayer(p.getName(), Loader.c.getDouble("Types."+type+"."+fish+".Money")+(Loader.c.getDouble("Types."+type+"."+fish+".Money")%85));
		}}}
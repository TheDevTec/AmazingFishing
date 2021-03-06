package me.devtec.amazingfishing.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import me.devtec.amazingfishing.construct.CatchFish;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.construct.Treasure;
import me.devtec.amazingfishing.utils.Categories.Category;
import me.devtec.amazingfishing.utils.Statistics.CaughtTreasuresType;
import me.devtec.amazingfishing.utils.Statistics.SavingType;
import me.devtec.amazingfishing.utils.Statistics.gainedType;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.placeholderapi.PlaceholderAPI;
import me.devtec.theapi.utils.datakeeper.Data;
import me.devtec.theapi.utils.datakeeper.User;

public class Achievements {
	//TODO udělat více potřebných věcí na dokončení ÚSPECHU
	// TODO - progress bar?
	
	public static class Achievement {
		private Data d;
		private String name;
		public Achievement(String name, Data data) {
			this.name=name;
			d=data;
		}
		
		public String getName() {
			return name;
		}
		
		public String getDisplayName() {
			return d.getString("achievements."+name+".name");
		}
		public MaterialData getUnfinishedIcon() {
			if(d.exists("achievements."+name+".icon.unfinished"))
				return Utils.createType(d.getString("achievements."+name+".icon.unfinished"));
			else return Utils.getCachedMaterial("SUNFLOWER");
		}
		public MaterialData getFinishedIcon() {
			if(d.exists("achievements."+name+".icon.finished"))
				return Utils.createType(d.getString("achievements."+name+".icon.finished"));
			else return Utils.getCachedMaterial("GREEN_WOOL");
		}
		
		public List<String> getDescription(Player p) {
			List<String> list = new ArrayList<String>();
			if(isFinished(p, this) && d.exists("achievements."+name+".description.finished")) {
				for(String s: d.getStringList("achievements."+name+".description.finished"))
					list.add(s.replace("%name%", getName())
							.replace("%questname%", getDisplayName())
							.replace("%unfinished_icon%", getUnfinishedIcon().getItemType().name())
							.replace("%finished_icon%", getFinishedIcon().getItemType().name())
							.replace("%requirement%", ""+getRequirement())
							);
			}
			else {
				if(d.exists("achievements."+name+".description.unfinished"))
				for(String s: d.getStringList("achievements."+name+".description.unfinished"))
					list.add(s.replace("%name%", getName())
							.replace("%questname%", getDisplayName())
							.replace("%unfinished_icon%", getUnfinishedIcon().getItemType().name())
							.replace("%finished_icon%", getFinishedIcon().getItemType().name())
							.replace("%requirement%", ""+getRequirement())
							);
				else
					for(String s: d.getStringList("achievements."+name+".description"))
						list.add(s.replace("%name%", getName())
								.replace("%questname%", getDisplayName())
								.replace("%unfinished_icon%", getUnfinishedIcon().getItemType().name())
								.replace("%finished_icon%", getFinishedIcon().getItemType().name())
								.replace("%requirement%", ""+getRequirement())
								);
			}
			return list;
		}
		
		public int getRequirement() {
			return d.getKeys("achievements."+name+".requirement").size();
		}
		
		public List<String> getFinishCommands() {
			return d.getStringList("achievements."+name+".commands");
		}
		public List<String> getFinishMessages() {
			return d.getStringList("achievements."+name+".messages");
		}
		
		/*
		Action:
		#   fishing: catch_fish, eat_fish, sell_fish
        #   treasures: catch_treasure
        #   shop: gained_money, gained_points, gained_xp
        #   other: catch_all (fish or treasure)
		 */
		public String getAction(int stage) {
			return d.getString("achievements."+name+".requirement."+stage+".action");
		}

		//value = type_of_fish.name_of_fish -> cod.nazev
		/*
		 Value:
		# Value: type_of_fish.name_of_fish -> cod.<name> -> cod.1
        # Value: treasure.name_of_treasure -> treasure.<name> -> treasure.common
        # Other: all; type_of_fish -> cod (just type)
		 */
		public String getValue(int stage) {
			return d.getString("achievements."+name+".requirement."+stage+".value");
		}
		
		public int getAmount(int stage) {
			return d.getInt("achievements."+name+".requirement."+stage+".amount");
		}
	}
	
	
	
	public static Map<String, Achievement> achievements = new HashMap<>();
	public static Map<String, Category> categories = new HashMap<>();  // name, Category
	
	public static void register(Achievement achievement) {
		achievements.put(achievement.getName(), achievement);
	}
	public static void unregister(Achievement achievement) {
		achievements.remove(achievement.getName());
	}
	
	public static void unregister(String achievement) {
		achievements.remove(achievement);
	}
	public static void addToCategory(Category category) {
		categories.put(category.getName(), category);
	}
	/*public static void addToCategory(String category, List<Achievement> list) {
		categories.put(category, list);
	}*/
	public static void removeFromCategory(String category, Category cat) {
		categories.remove(category, cat);
	}
	public static void removeFromCategory(String category) {
		categories.remove(category);
	}
	
	public static enum Action {
		/*Fishing:*/ catch_fish, eat_fish, sell_fish,
        /*Treasures:*/ catch_treasure,
        /*shop:*/ gained_money, gained_points, gained_xp,
        /* other:*/ catch_all
	}
	
	public static void check(Player p, CatchFish f) {
		add(p, f.getType().name().toLowerCase()+"."+f.getName());
	}
	public static void check(Player p, Fish f) {
		add(p, f.getType().name().toLowerCase()+"."+f.getName());
	}
	public static void check(Player p, Treasure treasure) {
		add(p, "treasure."+treasure.getName());
	}
	/*
	Action:
	#   fishing: catch_fish, eat_fish, sell_fish
    #   treasures: catch_treasure
    #   shop: gained_money, gained_points, gained_xp
    #   other: catch_all (fish or treasure)
	 */
	/*
	 Value:
	# Value: type_of_fish.name_of_fish -> cod.<name> -> cod.1
    # Value: treasure.name_of_treasure -> treasure.<name> -> treasure.common
    # Other: all; type_of_fish -> cod (just type)
	 */
	public static void add(Player p, String value) {
		for(Achievement achievement: achievements.values()) { // Achievements
			if(isFinished(p, achievement)) continue;
			
			for (int i = 0; i < achievement.getRequirement(); ++i) { // Stages / Requirement thinks
				if(stageIsFinished(p, achievement, i))
					continue;
				String action = achievement.getAction(i);
				String val = achievement.getValue(i);
				
				//  FISH
				if( (action.equalsIgnoreCase("catch_fish")||action.equalsIgnoreCase("eat_fish")||action.equalsIgnoreCase("sell_fish")) && 
						(value.startsWith("cod")||value.startsWith("pufferfish")||value.startsWith("salmon")||
								value.startsWith("tropicalfish")||value.startsWith("tropical_fish")) ) {
					String[] s = value.split("[.]"); // type.name
					SavingType type = null;
					if(val.equalsIgnoreCase(value)) // Určitá ryba
						type= SavingType.PER_FISH;
					if(val.equalsIgnoreCase("all")) // Všechny ryby
						type= SavingType.GLOBAL;
					if(val.equalsIgnoreCase( s[0] )) // Všechny ryby určitého typu
						type= SavingType.PER_TYPE;
					
					if(action.equalsIgnoreCase("catch_fish")&&
							( Statistics.getCaught(p, s[1], s[0], type)>=achievement.getAmount(i) ) ) {
						setStageFinished(p, achievement, i);
						continue;
					}
					if(action.equalsIgnoreCase("eat_fish")&&
							( Statistics.getEaten(p, s[1], s[0].toUpperCase(), type)>=achievement.getAmount(i) ) ) {
						setStageFinished(p, achievement, i);
						continue;
					}
					if(action.equalsIgnoreCase("sell_fish")&&
							( Statistics.getSold(p, s[1], s[0].toUpperCase(), type)>=achievement.getAmount(i) ) ) {
						setStageFinished(p, achievement, i);
						continue;
					}
				}
				// TREASURES
				if(action.equalsIgnoreCase("catch_treasure") && value.startsWith("treasure") ) {
					String[] s = value.split("[.]"); // treasure.name
					CaughtTreasuresType type = null;
					if(val.equalsIgnoreCase(value)) // Určitá ryba
						type= CaughtTreasuresType.PER_TREASURE;
					if(val.equalsIgnoreCase("all")) // Všechny ryby
						type= CaughtTreasuresType.GLOBAL;
					if( Statistics.getCaughtTreasures(p, s[1], type)>=achievement.getAmount(i)) {
						setStageFinished(p, achievement, i);
						continue;
					}
				}
				// Gaining values from selling fish (money, exp, points)
				if( action.equalsIgnoreCase("gained_money")||action.equalsIgnoreCase("gained_points")||action.equalsIgnoreCase("gained_xp")) {
					gainedType type = null;
					if(action.equalsIgnoreCase("gained_money"))
						type= gainedType.MONEY;
					if(action.equalsIgnoreCase("gained_points"))
						type= gainedType.POINTS;
					if(action.equalsIgnoreCase("gained_xp"))
						type= gainedType.EXP;
					if(Statistics.getGainedValues(p, type)>= achievement.getAmount(i)) {
						setStageFinished(p, achievement, i);
						continue;
					}
				}
				// catch_all
				if( action.equalsIgnoreCase("catch_all") ) {
					int amount = Statistics.getCaught(p, "", "", SavingType.GLOBAL) + Statistics.getCaughtTreasures(p, "", CaughtTreasuresType.GLOBAL);
					if(amount >=achievement.getAmount(i)) {
						setStageFinished(p, achievement, i);
						continue;
					}
				}
			}
			if (allStagesFinished(p, achievement)) {
				setFinished(p, achievement);
				for(String cmd : achievement.getFinishCommands())
					TheAPI.sudoConsole(PlaceholderAPI.setPlaceholders(p, cmd.replace("%player%", p.getName()).replace("%achievement%", achievement.getName()).replace("%achievement_name%", achievement.getDisplayName()).replace("%prefix%", Trans.prefix()) ) );
				for(String msg : achievement.getFinishMessages())
					TheAPI.msg(PlaceholderAPI.setPlaceholders(p, msg.replace("%player%", p.getName()).replace("%achievement%", achievement.getName()).replace("%achievement_name%", achievement.getDisplayName()).replace("%prefix%", Trans.prefix()) ), p);
			continue;
			}
			
		}
		
	}
	
	public static boolean isFinished(Player player, Achievement achievement) {
		User u = TheAPI.getUser(player);
		if( !u.exist(Manager.getDataLocation()+".achievements."+achievement.getName()+".finished") )
			return false;
		else return u.getBoolean(Manager.getDataLocation()+".achievements."+achievement.getName()+".finished");
	}
	private static void setFinished(Player player, Achievement achievement) {
		User u = TheAPI.getUser(player);
		u.set(Manager.getDataLocation()+".achievements."+achievement.getName()+".finished", true);
		u.save();
	}
	private static boolean stageIsFinished(Player player, Achievement achievement, int stage) {
		User u = TheAPI.getUser(player);
		if( !u.exist(Manager.getDataLocation()+".achievements."+achievement.getName()+"."+stage) )
			return false;
		else return u.getBoolean(Manager.getDataLocation()+".achievements."+achievement.getName()+"."+stage);
	}
	
	private static void setStageFinished(Player player, Achievement achievement, int stage) {
		User u = TheAPI.getUser(player);
		u.set(Manager.getDataLocation()+".achievements."+achievement.getName()+"."+stage, true);
		u.save();
	}
	private static boolean allStagesFinished(Player player, Achievement achievement) {
		boolean fin = true;
		for (int i = 0; i < achievement.getRequirement(); ++i) { // Stages / Requirement thinks
			if(!stageIsFinished(player, achievement, i))
				fin=false;
		}
		return fin;
	}
	public static MaterialData getIcon(Player player, Achievement achievement) {
		if(isFinished(player, achievement))
			return achievement.getFinishedIcon();
		else 
			return achievement.getUnfinishedIcon();
	}
	/*
	 dataLocation:
	 	Achievements:
	 		<name>:
	 			finished: true/false
	 			<stage>: true/false // Finished?
	 */

}

package me.devtec.amazingfishing.utils;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.construct.Treasure;
import me.devtec.amazingfishing.utils.tournament.TournamentType;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.utils.datakeeper.User;

public class Statistics {
	
	public static enum SavingType { //For Fishing, Eating, Selling (Shop)
		GLOBAL, PER_TYPE, PER_FISH
	}
	
	/*
	 * 		Fishing:
	 */
	
	public static int getCaught(Player p, Fish f, SavingType t) {
		User u = TheAPI.getUser(p);
		switch(t) {
		case GLOBAL:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish.Caught");
		case PER_FISH:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+"."+f.getName()+".Caught");
		case PER_TYPE:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+".Caught");
		}
		return 0;
	}

	public static void addFish(Player p, Fish f) {
		User u = TheAPI.getUser(p);
		u.set(Manager.getDataLocation()+".Statistics.Fish.Caught", getCaught(p, f, SavingType.GLOBAL)+1);
		u.set(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+".Caught", getCaught(p, f, SavingType.PER_TYPE)+1);
		u.set(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+"."+f.getName()+".Caught", getCaught(p, f, SavingType.PER_FISH)+1);
		u.save();
	}
	
	//Records
	public static void addRecord(Player p, Fish f, double length, double weight) {
		User user = TheAPI.getUser(p);
		boolean cansend=!user.exist(Manager.getDataLocation()+".Settings.SendRecords")?true:user.getBoolean(Manager.getDataLocation()+".Settings.SendRecords");
		double old;
		if(f!=null) {
			if(length!=0) {
				old = getRecord(p, f, RecordType.LENGTH);
				if(length > old) {
					setNewRecord(user, f, RecordType.LENGTH, length);
					if(cansend && old!=0)
						for(String msg : Loader.trans.getStringList("NewRecord"))
							Loader.msg(msg
									.replace("%type%", "Length")
									.replace("%new%", Loader.ff.format(length))
									.replace("%old%", Loader.ff.format(old))
									, p);
				}
			}
			if(weight!=0) {
				old = getRecord(p, f, RecordType.WEIGHT);
				if(weight > old) {
					setNewRecord(user, f, RecordType.WEIGHT, weight);
					if(cansend && old!=0)
						for(String msg : Loader.trans.getStringList("NewRecord"))
							Loader.msg(msg
								.replace("%type%", "Weight")
								.replace("%new%", Loader.ff.format(weight))
								.replace("%old%", Loader.ff.format(old))
								, p);
				}
			}
		}
	}
	
	private static void setNewRecord(User u, Fish f, RecordType type, double record) {
		u.setAndSave( Manager.getDataLocation()+".Statistics.Records."+f.getType()+"."+f.getName()+"."+type.name() , record);
	}
	public static double getRecord(Player p, Fish f, RecordType type) {
		User u = TheAPI.getUser(p);
		if(u.exist(Manager.getDataLocation()+".Statistics.Records."+f.getType()+"."+f.getName()+"."+type.name() ))
			return u.getDouble( Manager.getDataLocation()+".Statistics.Records."+f.getType()+"."+f.getName()+"."+type.name() );
		return 0;
	}
	public enum RecordType{
		LENGTH,
		WEIGHT;
	}
	
	/*
	 * Eating
	 */
	

	public static int getEaten(Player p, Fish f, SavingType t) {
		User u = TheAPI.getUser(p);
		switch(t) {
		case GLOBAL:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish.Eaten");
		case PER_FISH:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+"."+f.getName()+".Eaten");
		case PER_TYPE:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+".Eaten");
		}
		return 0;
	}

	public static void addEating(Player p, Fish f) {
		User u = TheAPI.getUser(p);
		u.set(Manager.getDataLocation()+".Statistics.Fish.Eaten", getEaten(p, f, SavingType.GLOBAL)+1);
		u.set(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+".Eaten", getEaten(p, f, SavingType.PER_TYPE)+1);
		u.set(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+"."+f.getName()+".Eaten", getEaten(p, f, SavingType.PER_FISH)+1);
		u.save();
	}
	
	/*
	 * Shop (Selling)
	 */
	
	// Selling fish
	public static int getSold(Player p, Fish f, SavingType t) {
		User u = TheAPI.getUser(p);
		switch(t) {
		case GLOBAL:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish.Sold");
		case PER_FISH:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+"."+f.getName()+".Sold");
		case PER_TYPE:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+".Sold");
		}
		return 0;
	}

	public static void addSelling(Player p, Fish f) {
		User u = TheAPI.getUser(p);
		u.set(Manager.getDataLocation()+".Statistics.Fish.Sold", getSold(p, f, SavingType.GLOBAL)+1);
		u.set(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+".Sold", getSold(p, f, SavingType.PER_TYPE)+1);
		u.set(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+"."+f.getName()+".Sold", getSold(p, f, SavingType.PER_FISH)+1);
		u.save();
	}
	
	//Gaining moneys, xps, points
	//Value of total earnings from selling fish
	
	public static enum gainedType {
		MONEY, POINTS, EXP;
	}
	public static Double getGainedValues(Player p, gainedType gainedType) { //Value of total earnings from selling fish 
		User u = TheAPI.getUser(p);
		switch(gainedType) {
		case EXP:
			return u.getDouble(Manager.getDataLocation()+".Statistics.Shop.Gained.Exp");
		case MONEY:
			return u.getDouble(Manager.getDataLocation()+".Statistics.Shop.Gained.Money");
		case POINTS:
			return u.getDouble(Manager.getDataLocation()+".Statistics.Shop.Gained.Points");
		}
		return 0.0;
	}
	
	public static void addSellingValues(Player player, double money, double points, double exp) { //Value of total earnings from selling fish 
		User u = TheAPI.getUser(player);
		u.set(Manager.getDataLocation()+".Statistics.Shop.Gained.Money", getGainedValues(player, gainedType.MONEY)+money);
		u.set(Manager.getDataLocation()+".Statistics.Shop.Gained.Points", getGainedValues(player, gainedType.POINTS)+points);
		u.set(Manager.getDataLocation()+".Statistics.Shop.Gained.Exp", getGainedValues(player, gainedType.EXP)+exp);
		u.save();
	}
	
	/*
	 * 		Treasures:
	 */
	public static enum CaughtTreasuresType {
		GLOBAL, PER_TREASURE
	}
	public static int getCaughtTreasures(Player p, Treasure treasure, CaughtTreasuresType t) {
		User u = TheAPI.getUser(p);
		switch(t) {
		case GLOBAL:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish.Caught");
		case PER_TREASURE:
			return u.getInt(Manager.getDataLocation()+".Statistics.Treasures."+treasure.getName()+".Caught");
		}
		return 0;
	}

	public static void addTreasure(Player p, Treasure treasure) {
		User u = TheAPI.getUser(p);
		u.set(Manager.getDataLocation()+".Statistics.Treasures.Caught", getCaughtTreasures(p, treasure, CaughtTreasuresType.GLOBAL)+1);
		u.set(Manager.getDataLocation()+".Statistics.Treasures."+treasure.getName()+".Caught", getCaughtTreasures(p, treasure, CaughtTreasuresType.PER_TREASURE)+1);
		u.save();
	}
	
	/*
	 * 		Tournaments:
	 */
	
	public static void addTournamentData(Player p, TournamentType type ,int position) {
		if(position>4) position=0;
		addTournamentPlacement(p, type, position);
		addTournamentPlayed(p, type);
	}
	
	public static int getTournamentPlacement(Player p, TournamentType type, int position) {
		User u = TheAPI.getUser(p);
		if(type==null || position==0) {
			if(!u.exist(Manager.getDataLocation()+".Statistics.Tournament.Placements")) return 0;
			return u.getInt(Manager.getDataLocation()+".Statistics.Tournament.Placements");
		}
		else {
			if(!u.exist(Manager.getDataLocation()+".Statistics.Tournament."+type.name()+".Placement."+position)) return 0;
			return u.getInt(Manager.getDataLocation()+".Statistics.Tournament."+type.name()+".Placement."+position);
		}
	}

	private static void addTournamentPlacement(Player p, TournamentType type, int position) {
		User u = TheAPI.getUser(p);
		if(type!=null && position!=0) {
			u.set(Manager.getDataLocation()+".Statistics.Tournament."+type.name()+".Placement."+position, getTournamentPlacement(p, type, position)+1);
		}
		u.set(Manager.getDataLocation()+".Statistics.Tournament.Placements", getTournamentPlacement(p, null, 0)+1);
		u.save();
	}
	
	public static int getTournamentPlayed(Player p, TournamentType type) {
		User u = TheAPI.getUser(p);
		if(type==null)
			return u.getInt(Manager.getDataLocation()+".Statistics.Tournament.Played");
		return u.getInt(Manager.getDataLocation()+".Statistics.Tournament."+type.name()+".Played");
		}

	private static void addTournamentPlayed(Player p, TournamentType type) {
		User u = TheAPI.getUser(p);
		if(type!=null)
			u.set(Manager.getDataLocation()+".Statistics.Tournament."+type.name()+".Played", getTournamentPlayed(p, type)+1);
		u.set(Manager.getDataLocation()+".Statistics.Tournament.Played", getTournamentPlayed(p, null)+1);
		u.save();
	}
}

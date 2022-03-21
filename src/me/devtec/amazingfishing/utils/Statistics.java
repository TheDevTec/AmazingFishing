package me.devtec.amazingfishing.utils;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.construct.Treasure;
import me.devtec.amazingfishing.utils.tournament.TournamentType;
import me.devtec.shared.API;
import me.devtec.shared.dataholder.Config;

public class Statistics {
	
	public static enum SavingType {
		GLOBAL, PER_TYPE, PER_FISH
	}
	
	public static int getCaught(Player p, Fish f, SavingType SavingType) {
		Config u = API.getUser(p.getUniqueId());
		if(SavingType==null) return -1;
		switch(SavingType) {
		case GLOBAL:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish.Caught"); //All fishes
		case PER_FISH:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+"."+f.getName()+".Caught");
		case PER_TYPE:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+".Caught");
		}
		return 0;
	}
	public static int getCaught(Player p, String fish, String type, SavingType SavingType) {
		Config u = API.getUser(p.getUniqueId());
		if(SavingType==null) return -1;
		switch(SavingType) {
		case GLOBAL:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish.Caught"); //All fishes
		case PER_FISH:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish."+type.toUpperCase()+"."+fish+".Caught");
		case PER_TYPE:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish."+type.toUpperCase()+".Caught");
		}
		return 0;
	}
	public static void addFish(Player p, Fish f) {
		Config u = API.getUser(p.getUniqueId());
		u.set(Manager.getDataLocation()+".Statistics.Fish.Caught", getCaught(p, f, SavingType.GLOBAL)+1);
		u.set(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+".Caught", getCaught(p, f, SavingType.PER_TYPE)+1);
		u.set(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+"."+f.getName()+".Caught", getCaught(p, f, SavingType.PER_FISH)+1);
		u.save();
	}
	
	//Records
	public static void addRecord(Player p, Fish f, double length, double weight) {
		Config user = API.getUser(p.getUniqueId());
		boolean cansend=!user.exists(Manager.getDataLocation()+".Settings.SendRecords")?true:user.getBoolean(Manager.getDataLocation()+".Settings.SendRecords");
		double old;
		if(f!=null) {
			if(length!=0) {
				old = getRecord(p, f, RecordType.LENGTH);
				if(length > old) {
					setNewRecord(user, f, RecordType.LENGTH, length);
					if(cansend && old!=0)
						for(String msg : Create.list("records.length"))
							Loader.msg(msg.replace("%new%", Loader.ff.format(length)).replace("%old%", Loader.ff.format(old)), p);
				}
			}
			if(weight!=0) {
				old = getRecord(p, f, RecordType.WEIGHT);
				if(weight > old) {
					setNewRecord(user, f, RecordType.WEIGHT, weight);
					if(cansend && old!=0)
						for(String msg : Create.list("records.weight"))
							Loader.msg(msg.replace("%new%", Loader.ff.format(weight)).replace("%old%", Loader.ff.format(old)), p);
				}
			}
		}
	}
	
	private static void setNewRecord(Config u, Fish f, RecordType RecordType, double record) {
		u.set( Manager.getDataLocation()+".Statistics.Records."+f.getType()+"."+f.getName()+"."+RecordType.name() , record);
	}
	public static double getRecord(Player p, Fish f, RecordType RecordType) {
		Config u = API.getUser(p.getUniqueId());
		if(RecordType==null) return -1;
		return u.getDouble( Manager.getDataLocation()+".Statistics.Records."+f.getType()+"."+f.getName()+"."+RecordType.name() );
	}
	public enum RecordType{
		LENGTH,
		WEIGHT;
	}
	
	/*
	 * Eating
	 */
	
	public static int getEaten(Player p, Fish f, SavingType SavingType) {
		Config u = API.getUser(p.getUniqueId());
		if(SavingType==null) return -1;
		switch(SavingType) {
		case GLOBAL:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish.Eaten");
		case PER_FISH:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+"."+f.getName()+".Eaten");
		case PER_TYPE:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+".Eaten");
		}
		return 0;
	}
	public static int getEaten(Player p, String fish, String type, SavingType SavingType) {
		Config u = API.getUser(p.getUniqueId());
		if(SavingType==null) return -1;
		switch(SavingType) {
		case GLOBAL:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish.Eaten");
		case PER_FISH:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish."+type+"."+fish+".Eaten");
		case PER_TYPE:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish."+type+".Eaten");
		}
		return 0;
	}

	public static void addEating(Player p, Fish f) {
		Config u = API.getUser(p.getUniqueId());
		u.set(Manager.getDataLocation()+".Statistics.Fish.Eaten", getEaten(p, f, SavingType.GLOBAL)+1);
		u.set(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+".Eaten", getEaten(p, f, SavingType.PER_TYPE)+1);
		u.set(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+"."+f.getName()+".Eaten", getEaten(p, f, SavingType.PER_FISH)+1);
		u.save();
	}
	
	/*
	 * Shop (Selling)
	 */
	
	// Selling fish
	public static int getSold(Player p, Fish f, SavingType SavingType) {
		Config u = API.getUser(p.getUniqueId());
		if(SavingType==null) return -1;
		switch(SavingType) {
		case GLOBAL:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish.Sold");
		case PER_FISH:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+"."+f.getName()+".Sold");
		case PER_TYPE:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish."+f.getType().name()+".Sold");
		}
		return 0;
	}
	public static int getSold(Player p, String fish, String type, SavingType SavingType) {
		Config u = API.getUser(p.getUniqueId());
		if(SavingType==null) return -1;
		switch(SavingType) {
		case GLOBAL:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish.Sold");
		case PER_FISH:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish."+type+"."+fish+".Sold");
		case PER_TYPE:
			return u.getInt(Manager.getDataLocation()+".Statistics.Fish."+type+".Sold");
		}
		return 0;
	}

	public static void addSelling(Player p, Fish fish) {
		Config u = API.getUser(p.getUniqueId());
		u.set(Manager.getDataLocation()+".Statistics.Fish.Sold", getSold(p, fish, SavingType.GLOBAL)+1);
		u.set(Manager.getDataLocation()+".Statistics.Fish."+fish.getType().name()+".Sold", getSold(p, fish, SavingType.PER_TYPE)+1);
		u.set(Manager.getDataLocation()+".Statistics.Fish."+fish.getType().name()+"."+fish.getName()+".Sold", getSold(p, fish, SavingType.PER_FISH)+1);
		u.save();
	}
	public static void addSelling(Player p, Fish fish, int amount) {
		Config u = API.getUser(p.getUniqueId());
		u.set(Manager.getDataLocation()+".Statistics.Fish.Sold", getSold(p, fish, SavingType.GLOBAL)+amount);
		u.set(Manager.getDataLocation()+".Statistics.Fish."+fish.getType().name()+".Sold", getSold(p, fish, SavingType.PER_TYPE)+amount);
		u.set(Manager.getDataLocation()+".Statistics.Fish."+fish.getType().name()+"."+fish.getName()+".Sold", getSold(p, fish, SavingType.PER_FISH)+amount);
		u.save();
		
	}
	//Gaining moneys, xps, points
	//Value of total earnings from selling fish
	
	public static enum gainedType {
		MONEY, POINTS, EXP;
	}
	public static Double getGainedValues(Player p, gainedType gainedType) { //Values of total earnings from selling fish 
		Config u = API.getUser(p.getUniqueId());
		if(gainedType==null) return -1.0;
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
		Config u = API.getUser(player.getUniqueId());
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
	public static int getCaughtTreasures(Player p, Treasure treasure, CaughtTreasuresType CaughtTreasuresType) {
		Config u = API.getUser(p.getUniqueId());
		if(CaughtTreasuresType==null) return -1;
		switch(CaughtTreasuresType) {
		case GLOBAL:
			return u.getInt(Manager.getDataLocation()+".Statistics.Treasures.Caught");
		case PER_TREASURE:
			return u.getInt(Manager.getDataLocation()+".Statistics.Treasures."+treasure.getName()+".Caught");
		}
		return 0;
	}
	public static int getCaughtTreasures(Player p, String treasure, CaughtTreasuresType CaughtTreasuresType) {
		Config u = API.getUser(p.getUniqueId());
		if(CaughtTreasuresType==null) return -1;
		switch(CaughtTreasuresType) {
		case GLOBAL:
			return u.getInt(Manager.getDataLocation()+".Statistics.Treasures.Caught");
		case PER_TREASURE:
			return u.getInt(Manager.getDataLocation()+".Statistics.Treasures."+treasure+".Caught");
		}
		return 0;
	}

	public static void addTreasure(Player p, Treasure treasure) {
		Config u = API.getUser(p.getUniqueId());
		u.set(Manager.getDataLocation()+".Statistics.Treasures.Caught", getCaughtTreasures(p, treasure, CaughtTreasuresType.GLOBAL)+1);
		u.set(Manager.getDataLocation()+".Statistics.Treasures."+treasure.getName()+".Caught", getCaughtTreasures(p, treasure, CaughtTreasuresType.PER_TREASURE)+1);
		u.save();
	}
	
	/*
	 * 		Tournaments:
	 */
	
	public static void addTournamentData(Player p, TournamentType TournamentType ,int position) {
		if(position>4) position=0;
		addTournamentPlacement(p, TournamentType, position);
		addTournamentPlayed(p, TournamentType);
	}
	
	public static int getTournamentPlacement(Player p, TournamentType TournamentType, int position) {
		Config u = API.getUser(p.getUniqueId());
		if(TournamentType==null || position==0) {
			return u.getInt(Manager.getDataLocation()+".Statistics.Tournament.Placements");
		}
		else {
			return u.getInt(Manager.getDataLocation()+".Statistics.Tournament."+TournamentType.name()+".Placement."+position);
		}
	}

	private static void addTournamentPlacement(Player p, TournamentType TournamentType, int position) {
		Config u = API.getUser(p.getUniqueId());
		if(TournamentType!=null && position!=0) {
			u.set(Manager.getDataLocation()+".Statistics.Tournament."+TournamentType.name()+".Placement."+position, getTournamentPlacement(p, TournamentType, position)+1);
		}
		u.set(Manager.getDataLocation()+".Statistics.Tournament.Placements", getTournamentPlacement(p, null, 0)+1);
		u.save();
	}
	
	public static int getTournamentPlayed(Player p, TournamentType TournamentType) {
		Config u = API.getUser(p.getUniqueId());
		if(TournamentType==null)
			return u.getInt(Manager.getDataLocation()+".Statistics.Tournament.Played");
		return u.getInt(Manager.getDataLocation()+".Statistics.Tournament."+TournamentType.name()+".Played");
		}

	private static void addTournamentPlayed(Player p, TournamentType TournamentType) {
		Config u = API.getUser(p.getUniqueId());
		if(TournamentType!=null)
			u.set(Manager.getDataLocation()+".Statistics.Tournament."+TournamentType.name()+".Played", getTournamentPlayed(p, TournamentType)+1);
		u.set(Manager.getDataLocation()+".Statistics.Tournament.Played", getTournamentPlayed(p, null)+1);
		u.save();
	}
}

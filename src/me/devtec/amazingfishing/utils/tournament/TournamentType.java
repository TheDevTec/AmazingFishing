package me.devtec.amazingfishing.utils.tournament;

import me.devtec.amazingfishing.Loader;

public enum TournamentType {
	AMOUNT("Amount",Loader.config.getString("Tournament.Type.Amount")),
	LENGTH("Length",Loader.config.getString("Tournament.Type.Length")),
	WEIGHT("Weight",Loader.config.getString("Tournament.Type.Weight")),
	TOTAL_LENGTH("TotalLength",Loader.config.getString("Tournament.Type.TotalLength")),
	TOTAL_WEIGHT("TotalWeight",Loader.config.getString("Tournament.Type.TotalWeight")),
	RANDOM("Random",Loader.config.getString("Tournament.Type.Random"));
	
	String d,e;
	TournamentType(String s, String f){
		d=s;
		e=f;
	}

	public String configPath() {
		return d;
	}

	public String formatted() {
		return e;
	}
}

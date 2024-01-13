package me.devtec.amazingfishing.fishing.enums;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.Player;

public enum FishingWeather {
	
	ANY,
	SUN,
	SNOW,
	RAIN,
	THUNDERSTORM;
	
	public boolean isWeather(Player player) {
		World world = player.getWorld();
		switch (this) {
		case ANY:
			return true;
		case SNOW:
		case RAIN:
			if(world.hasStorm())
				return true;
		case THUNDERSTORM:
			return world.isThundering();
		case SUN:
			return (!world.isThundering() && !world.hasStorm());
		}
		return false;
	}
	
	public boolean equals(FishingWeather weather) {
		if(this == ANY || weather == ANY)
			return true;
		
		if(this == weather)
			return true;
		
		if(this == RAIN && weather == SNOW  || this == SNOW && weather == RAIN)
			return true;
		return true;
	}
	
	/** If your instance of {@link FishingWeather} is in a List of {@link FishingWeather}s
	 * @param weatherList List of {@link FishingWeather}s that you want to check if your {@link FishingWeather} is part of...
	 * @return true if it is, otherwise false
	 */
	public boolean equals(List<FishingWeather> weatherList) {
		for(FishingWeather weather : weatherList) {
			if(weather.equals(this))
				return true;
			continue;
		}
		return false;
	}
	
	public static FishingWeather getWeather(Player player) {
		World world = player.getWorld();
		if(world.isThundering())
			return THUNDERSTORM;
		if(world.hasStorm())
			return RAIN;
		if(!world.isThundering() && !world.hasStorm())
			return SUN;
		return ANY;
	}
	
	/** Determines and returns the {@link FishingTime} value from the {@link String} 
	 * @param value String representation of FishingTime values 
	 * @return FishingWeather.ANY if there is no match or values are null
	 */
	public static FishingWeather value(String value) {
		if(value.equalsIgnoreCase("ANY")) return FishingWeather.ANY;
		if(value.equalsIgnoreCase("SUN")) return FishingWeather.SUN;
		if(value.equalsIgnoreCase("SNOW")) return FishingWeather.SNOW;
		if(value.equalsIgnoreCase("RAIN")) return FishingWeather.RAIN;
		if(value.equalsIgnoreCase("THUNDERSTORM")) return FishingWeather.THUNDERSTORM;
		return FishingWeather.ANY;
	}

	
	/** Determines and returns the list of {@link FishingWeather}s values from list of {@link String}s
	 * @param values List of {@link String}s representation of FishingWeather values
	 * @return FishingWeather.ANY if there is no match or values are null
	 */
	public static List<FishingWeather> values(List<String> values) {
		List<FishingWeather> weatherList = new ArrayList<FishingWeather>();
		if(values != null)
			values.forEach(value -> {
				weatherList.add(value(value));
			});
		if(weatherList.isEmpty())
			weatherList.add(ANY);
		return weatherList;
	}
	
	public String toString() {
		if(this == FishingWeather.ANY) return "ANY";
		if(this == FishingWeather.SUN) return "SUN";
		if(this == FishingWeather.SNOW) return "SNOW";
		if(this == FishingWeather.RAIN) return "RAIN";
		if(this == FishingWeather.THUNDERSTORM) return "THUNDERSTORM";
		return null;
	}
}

package me.devtec.amazingfishing.fishing.enums;

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
	 * @return null if there is no match
	 */
	public static FishingWeather value(String value) {
		if(value.equalsIgnoreCase("ANY")) return FishingWeather.ANY;
		if(value.equalsIgnoreCase("SUN")) return FishingWeather.SUN;
		if(value.equalsIgnoreCase("SNOW")) return FishingWeather.SNOW;
		if(value.equalsIgnoreCase("RAIN")) return FishingWeather.RAIN;
		if(value.equalsIgnoreCase("THUNDERSTORM")) return FishingWeather.THUNDERSTORM;
		return FishingWeather.ANY;
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

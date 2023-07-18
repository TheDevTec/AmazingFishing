package me.devtec.amazingfishing.fishing.enums;

import org.bukkit.entity.Player;

public enum FishingTime {
		// Using https://minecraft.fandom.com/wiki/Daylight_cycle as start & end ticks source
		ANY(0, 24000),
		
		DAY(0, 12000), //Just day
		SUNSET(12000, 13000), // Beginning of the Minecraft sunset in 12000 ticks
		NIGHT(13000, 23000), //Just night
		SUNRISE(23000, 24000), //
		
		NOON(5723, 6277), // Sun is at its peak in 6000 (boundaries 6000 +- 277)
		MIDNIGHT(17843, 18157); // Moon is at its peak in 18000 ticks (boundaries 18000 +- 157)
		
		private final double startTime;
		private final double endTime;
		
		private FishingTime(long startTime, long endTime) {
			this.startTime = startTime;
			this.endTime = endTime;
		}
		
		/** If the current time corresponds to the enum value 
		 * @param player Player that is currently online and fishing.
		 * @return
		 */
		public boolean isNow(Player player) {
			long now = player.getWorld().getTime();
			return startTime <= now && endTime >= now;
		}
		/** If the current time corresponds to the enum value 
		 * @param now Current time.
		 * @return
		 */
		public boolean isNow(long now) {
			return startTime <= now && endTime >= now;
		}
		
		/** Determines and returns the {@link FishingTime} value from the {@link String} 
		 * @param value String representation of FishingTime values 
		 * @return null if there is no match
		 */
		public static FishingTime value(String value) {
			if(value.equalsIgnoreCase("ANY")) return FishingTime.ANY;
			if(value.equalsIgnoreCase("DAY")) return FishingTime.DAY;
			if(value.equalsIgnoreCase("NIGHT")) return FishingTime.NIGHT;
			if(value.equalsIgnoreCase("NOON")) return FishingTime.NOON;
			if(value.equalsIgnoreCase("SUNSET")) return FishingTime.SUNSET;
			if(value.equalsIgnoreCase("MIDNIGHT")) return FishingTime.MIDNIGHT;
			if(value.equalsIgnoreCase("SUNRISE")) return FishingTime.SUNRISE;
			return FishingTime.ANY;
		}
		
		public String toString() {
			if(this == FishingTime.ANY) return "ANY";
			if(this == FishingTime.DAY) return "DAY";
			if(this == FishingTime.NIGHT) return "NIGHT";
			if(this == FishingTime.NOON) return "NOON";
			if(this == FishingTime.SUNSET) return "SUNSET";
			if(this == FishingTime.MIDNIGHT) return "MIDNIGHT";
			if(this == FishingTime.SUNRISE) return "SUNRISE";
			return null;
		}
	}
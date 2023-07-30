package me.devtec.amazingfishing.fishing.enums;

import org.bukkit.entity.Player;

public enum FishingTime {
		// Using https://minecraft.fandom.com/wiki/Daylight_cycle as start & end ticks source
		ANY(0, 24000),
		
		DAY(0, 12000), //Just day
		SUNSET(12000, 13000), // Beginning of the Minecraft sunset in 12000 ticks
		NIGHT(13000, 23000), //Just night
		SUNRISE(23000, 24000), // Beginning of the Minecraft sunrise in 23000 ticks
		
		NOON(5723, 6277), // Sun is at its peak in 6000 (boundaries 6000 +- 277)
		MIDNIGHT(17843, 18157); // Moon is at its peak in 18000 ticks (boundaries 18000 +- 157)
		
		private final long startTime;
		private final long endTime;
		
		private FishingTime(long startTime, long endTime) {
			this.startTime = startTime;
			this.endTime = endTime;
		}
		
		public long getStartTime() { return startTime;}
		public long getEndTime() { return endTime;}
		
		/** If the current time corresponds to the enum value 
		 * @param player Player that is currently online and fishing.
		 * @return
		 */
		public boolean isNow(Player player) {
			long now = player.getWorld().getTime();
			return isNow(now);
		}
		/** If the current time corresponds to the enum value 
		 * @param now Current time.
		 * @return
		 */
		public boolean isNow(long now) {
			return startTime <= now && endTime >= now;
		}
		
		public boolean equals(FishingTime now) {
			if(this == ANY || now == ANY)
				return true;
			if(this == now)
				return true;
			if(this == DAY && now == NOON)
				return true;
			if(this == NIGHT && now == MIDNIGHT)
				return true;
			
			return false;
		}
		
		/** Gets what {@link FishingTime} type is now. <br>
		 * If now is NOON or MIDNIGHT, the DAY or NIGHT will be ignored. This is bit shame,
		 * but I have no idea if I should include that if now is NOON should DAY be also now...
		 * @param now
		 * @return
		 */
		public static FishingTime getNow(long now) {
			for(FishingTime time : FishingTime.values()) {
				if(time == ANY) continue; //skipping any time, because all the time is ANY time :D
				if(time == DAY || time == NIGHT) continue; //skipping these time to also check NOON and MIDNIGHT
				if(time.isNow(now))
					return time;
			}
			//Checking DAY and NIGHT
			FishingTime time = DAY;
			if(time.isNow(now))
				return time;
			time = NIGHT;
			if(time.isNow(now))
				return time;
			
			return ANY;
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
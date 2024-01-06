package me.devtec.amazingfishing.fishing.enums;

import me.devtec.theapi.bukkit.xseries.XMaterial;

public enum FishType {
	FISH("FISH"),
	JUNK("JUNK");
	
	private String name;
	private FishType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static FishType value(String value) {
		if(value.equalsIgnoreCase("JUNK"))
			return JUNK;
		if(value.equalsIgnoreCase("FISH"))
			return FISH;
		return null;
	}
	
	public enum Special {
		COD("COD"),
		SALMON("SALMON"),
		PUFFERFISH("PUFFERFISH"),
		TROPICAL_FISH("TROPICAL_FISH");

		private String name;
		
		Special(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
		
		/**
		 * @param configPath <code>fish_cod</code>, <code>fish_salmon</code>, <code>fish_pufferfish</code>, <code>fish_tropical</code>
		 * @return {@link Special}, or default COD
		 */
		public static Special identifyFromPath(String configPath) {
			switch (configPath) {
			case "fish_cod":
				return COD;
			case "fish_salmon":
				return SALMON;
			case "fish_pufferfish":
				return PUFFERFISH;
			case "fish_tropical":
				return TROPICAL_FISH;
			case "fish_tropicalFish":
				return TROPICAL_FISH;
			case "fish_tropical_fish":
				return TROPICAL_FISH;
			case "fish_tropicalfish":
				return TROPICAL_FISH;
			default:
				return COD;
			}
		}
		
		public String getIndexName() {
			switch (this) {
			case COD:
				return "index_cod";
			case PUFFERFISH:
				return "index_pufferfish";
			case SALMON:
				return "index_salmon";
			case TROPICAL_FISH:
				return "index_tropical";
			default:
				return "index_cod";

			}
		}
		
		public boolean isType(XMaterial material) {
			if(material.isAir())
				return false;
			return material == XMaterial.valueOf(getName());
		}
		
	}
}

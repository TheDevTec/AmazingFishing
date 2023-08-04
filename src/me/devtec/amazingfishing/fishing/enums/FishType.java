package me.devtec.amazingfishing.fishing.enums;

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
}

package me.devtec.amazingfishing.fishing.enums;

/**
 * This enum represents limiting values. MIN or MAX values.
 */
public enum Limit {
	MIN("min"),
	MAX("max");
	
	private final String name;
	private Limit(String name) {
		this.name = name;
	}
	
	/**
	 * Returns name but in lower case
	 */
	public String toString() {
		return name;
	}
}

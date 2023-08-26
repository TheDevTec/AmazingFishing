package me.devtec.amazingfishing.fishing.enums;

public enum FishRecord {

	LENGTH("length"),
	WEIGHT("weight");

	private String config_path;
	
	FishRecord(String config_path) {
		this.config_path = config_path;
	}
	
	public String getPath() {
		return config_path;
	}
	
	public String path() {
		return getPath();
	}
	
	
}

package me.devtec.amazingfishing.fishing.enums;

public enum ItemAction {

	EAT("eat"),
	SELL("sell"),
	CATCH("catch");
	
	private String config_path;
	
	private ItemAction(String config_path) {
		this.config_path = config_path;
	}
	public String path() {
		return config_path;
	}
}

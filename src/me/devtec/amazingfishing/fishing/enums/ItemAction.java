package me.devtec.amazingfishing.fishing.enums;

public enum ItemAction {

	EAT("eat", "ate"),
	SELL("sell", "sold"),
	CATCH("catch", "caught");
	
	private String config_path;
	private String stat_config_path;
	
	private ItemAction(String config_path, String stat_config_path) {
		this.config_path = config_path;
		this.stat_config_path = stat_config_path;
	}
	public String path() {
		return config_path;
	}
	
	public String getStatPath() {
		return stat_config_path;
	}
}

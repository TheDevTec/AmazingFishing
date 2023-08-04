package me.devtec.amazingfishing.guis;

public enum ButtonType {
	BACK("back"),
	CLOSE("close"),
	NEXT("next"),
	PREVIOUS("previous");
	
	private String config_path;

	private ButtonType(String config_path) {
		this.config_path=config_path;
	}
	
	public String path() {
		return config_path;
	}
	
	public String getConfigPath() {
		return path();
	}
	
	public String toString() {
		return path();
	}
}

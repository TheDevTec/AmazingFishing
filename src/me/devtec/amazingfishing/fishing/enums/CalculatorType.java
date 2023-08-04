package me.devtec.amazingfishing.fishing.enums;

public enum CalculatorType {
		MONEY("money"),
		POINTS("points"),
		EXPS("exps"),
		WEIGHT("weight");
	
	private String path;
	
	private CalculatorType(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
}
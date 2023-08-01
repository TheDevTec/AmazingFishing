package me.devtec.amazingfishing.guis;

import me.devtec.shared.dataholder.Config;

public class Menu {

	private Config file;
	
	public Menu(Config file) {
		this.file = file;
	}
	
	public Config getConfig() {
		return this.file;
	}
	
	
}

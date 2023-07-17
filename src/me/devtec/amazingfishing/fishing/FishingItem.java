package me.devtec.amazingfishing.fishing;

import me.devtec.shared.dataholder.Config;

abstract class FishingItem {

	//File with all the configuration
	private Config file;
	
	// Getting Configuration file
	public Config getConfig() {return file;}
	// Setting Configuration file
	public void setConfig(Config newFile) {file = newFile;}
	
	
}

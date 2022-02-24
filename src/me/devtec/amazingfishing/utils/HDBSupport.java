package me.devtec.amazingfishing.utils;

import me.arcaniax.hdb.api.HeadDatabaseAPI;

public class HDBSupport {
	static Object api;
	static {
		try {
			api = new HeadDatabaseAPI();
		}catch(Exception | NoClassDefFoundError e) {}
	}
	
	public static String parse(String text) {
		if(api!=null && text.toLowerCase().startsWith("hdb:"))
		return ((HeadDatabaseAPI)api).getBase64(text.substring(4));
		return null;
	}
}

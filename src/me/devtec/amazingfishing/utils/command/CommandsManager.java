package me.devtec.amazingfishing.utils.command;

import java.util.HashMap;

public class CommandsManager {
	
	@SuppressWarnings("rawtypes")
	private static HashMap<String, PluginCommand> registered;
	
	@SuppressWarnings("rawtypes")
	public static void register() {
		
		PluginCommand cmd = new Command();
		cmd.register();
		registered.put(cmd.getSection(), cmd);
	}
	
	@SuppressWarnings("rawtypes")
	public static void unregister() {
		for(PluginCommand register : registered.values()) {
			register.unregister();
		}
		registered.clear();
	}
	
	@SuppressWarnings("rawtypes")
	public static HashMap<String, PluginCommand> getRegistered() {
		return registered;
	}
}

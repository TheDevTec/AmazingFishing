package me.devtec.amazingfishing;

import java.io.File;
import java.util.HashMap;

import me.devtec.amazingfishing.fishing.Fish;
import me.devtec.amazingfishing.fishing.Junk;
import me.devtec.amazingfishing.utils.MessageUtils;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.shared.dataholder.Config;

public class API {
	
	
	private static HashMap<String, Fish> fish_list = new HashMap<String, Fish>(); //file_name | Fish
	private static HashMap<String, Junk> junk_list = new HashMap<String, Junk>(); //file_name | Junk
	
	public static void loadFishingItems() {
		
		File directory = new File("plugins/AmazingFishing/Fish");
		if(directory.exists() && directory.isDirectory()) {
			for(File file : directory.listFiles()) {
				Config config = new Config(file);
				if(config.getString("type").equalsIgnoreCase("FISH"))
					fish_list.put(file.getName(), new Fish(config));
				if(config.getString("type").equalsIgnoreCase("JUNK"))
					junk_list.put(file.getName(), new Junk(config));
			}
		}
		MessageUtils.msgConsole("%prefix% &fLoaded %fish% fish files and %junk% junk files.", 
				Placeholders.c().add("fish", fish_list.size()).add("junk", junk_list.size()));
	}
	
	
}

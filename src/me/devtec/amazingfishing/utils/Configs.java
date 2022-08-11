package me.devtec.amazingfishing.utils;

import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

import me.devtec.amazingfishing.Loader;
import me.devtec.shared.dataholder.Config;
import me.devtec.shared.dataholder.DataType;
import me.devtec.shared.utility.StreamUtils;

public class Configs {
	static List<String> datas = Arrays.asList("Config.yml","GUI.yml","Shop.yml","Translations.yml");
	
	public static void load() {
		Config data = new Config();
    	boolean change = false;
		for(String s : datas) {
			data.reset();
			Config c = null;
	    	switch(s) {
	    	case "Config.yml":
	    		c=Loader.config;
	    		break;
	    	case "Shop.yml":
	    		c=Loader.shop;
	    		break;
	    	case "GUI.yml":
	    		c=Loader.gui;
	    		break;
	    	case "Translations.yml":
	    		c=Loader.tran;
	    		break;
	    	}
	    	if(c!=null) {
	    		c.reload(c.getFile());
	    	}else c=new Config("plugins/AmazingFishing/"+s);
    		try {
    		URLConnection u = Loader.plugin.getClass().getClassLoader().getResource("Configs/"+s).openConnection();
    		u.setUseCaches(false);
    		data.reload(StreamUtils.fromStream(u.getInputStream()));
    		}catch(Exception e) {e.printStackTrace();}
	    	change = c.merge(data);
	    	if(change)
	    	c.save();
	    	switch(s) {
	    	case "Config.yml":
	    		Loader.config=c;
	    		break;
	    	case "Shop.yml":
	    		Loader.shop=c;
	    		break;
	    	case "GUI.yml":
	    		Loader.gui=c;
	    		break;
	    	case "Translations.yml":
	    		Loader.tran=c;
	    		break;
	    	}
		}
		data.reset();
		Loader.cod=loadOrReload(data,Loader.cod, "Fish/Cod.yml");
		Loader.salmon=loadOrReload(data,Loader.salmon, "Fish/Salmon.yml");
		Loader.puffer=loadOrReload(data,Loader.puffer, "Fish/Pufferfish.yml");
		Loader.tropic=loadOrReload(data,Loader.tropic, "Fish/TropicalFish.yml");
		Loader.junk=loadOrReload(data,Loader.junk, "Fish/Junk.yml");
		Loader.quest=loadOrReload(data,Loader.quest, "Data/Quests.yml");
		Loader.achievements=loadOrReload(data,Loader.achievements, "Data/Achievements.yml");
		Loader.treasur=loadOrReload(data,Loader.treasur, "Data/Treasures.yml");
		Loader.enchant=loadOrReload(data,Loader.enchant, "Data/Enchantments.yml");
		Utils.convertFiles();
		Utils.fixDefaultConfig();
		Loader.next = Create.make("buttons.next").create();
		Loader.prev = Create.make("buttons.previous").create();
	}

	private static Config loadOrReload(Config data, Config d, String path) {
		if(d!=null) {
			d.reload(d.getFile());
		}else {
			d = new Config("plugins/AmazingFishing/"+path);
			try{
				d.getFile().getParentFile().mkdirs();
				}catch(Exception er) {}
				try{
					d.getFile().createNewFile();
				}catch(Exception er) {}
			
    		if(StreamUtils.fromStream(d.getFile()).trim().isEmpty())
        		try {
        			URLConnection u = Loader.plugin.getClass().getClassLoader().getResource("Configs/"+path).openConnection();
        			u.setUseCaches(false);
        			data.reload(StreamUtils.fromStream(u.getInputStream()));
        		}catch(Exception es) {es.printStackTrace();}
        	    boolean change = d.merge(data);
        	    if(change)
        	    	d.save(DataType.YAML);
		}
		data.reset();
		return d;
	}
}

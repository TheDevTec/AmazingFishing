package me.devtec.amazingfishing.utils;

import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

import me.devtec.amazingfishing.Loader;
import me.devtec.theapi.configapi.Config;
import me.devtec.theapi.utils.StreamUtils;
import me.devtec.theapi.utils.datakeeper.Data;
import me.devtec.theapi.utils.datakeeper.DataType;

public class Configs {
	static List<String> datas = Arrays.asList("Config.yml","GUI.yml","Shop.yml","Translations.yml");
	
	public static void load() {
		Data data = new Data();
		Data d = Loader.data;
    	if(d!=null) {
    		d.reload(d.getFile());
    	}else d=new Data("plugins/AmazingFishing/Data.yml");
		try {
		URLConnection u = Loader.plugin.getClass().getClassLoader().getResource("Configs/Data.yml").openConnection();
		u.setUseCaches(false);
		data.reload(StreamUtils.fromStream(u.getInputStream()));
    	Loader.data=d;
		}catch(Exception es) {es.printStackTrace();}
    	boolean change = d.merge(data, true, true);
    	if(change)
    	d.save(DataType.YAML);
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
	    		c=Loader.trans;
	    		break;
	    	}
	    	if(c!=null) {
	    		c.reload();
	    	}else c=new Config("AmazingFishing/"+s);
    		try {
    		URLConnection u = Loader.plugin.getClass().getClassLoader().getResource("Configs/"+s).openConnection();
    		u.setUseCaches(false);
    		data.reload(StreamUtils.fromStream(u.getInputStream()));
    		}catch(Exception e) {e.printStackTrace();}
	    	change = c.getData().merge(data, true, true);
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
	    		Loader.trans=c;
	    		break;
	    	}
		}
	}
}

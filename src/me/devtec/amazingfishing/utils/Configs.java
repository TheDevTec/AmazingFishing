package me.devtec.amazingfishing.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import me.devtec.amazingfishing.Loader;
import me.devtec.shared.dataholder.Config;
import me.devtec.shared.dataholder.DataType;
import me.devtec.shared.utility.StreamUtils;

public class Configs {

	//Main config
	public static Config config;
	
	//Translations
	public static Config engtrans, //English default translation
						translation; //normal translation
	
	// TODO - GUIS
	public static Config gui, shop;
	
	public static Config quest, treasur, enchant, achievements;
	
	private static Config temp_data = new Config();
	static List<String> datas = Arrays.asList("Config.yml","GUI.yml","Tags.yml");
	
	/**
	 * Creates and loads all config files. </br>
	 * Also loading next and previous button.
	 */
	public static void load() {
		config = loadAndMerge("Config.yml", "Config.yml");
		loadTranslations();
		loadDefaultExamples();
		
		
		//shop = loadAndMerge("Shop.yml", "Shop.yml");
		//gui = loadAndMerge("GUI.yml", "GUI.yml");
		
		// Loadin translations
		
	}
	// Loading translations files
	private static void loadTranslations() {
		//Loading included translations
		engtrans = loadAndMerge("Translations/en.yml", "Translations/en.yml"); //default English translation
		loadAndMerge("Translations/cz.yml", "Translations/cz.yml"); //Czech translation included in default
		
		//loading new translations
		String type = "en";
		if (config.exists("translation_file"))
			type = config.getString("translation_file");
		if (!new File("plugins/AmazingFishing/Translations/" + type + ".yml").exists())
			type = "en";
		translation = loadAndMerge("Translations/" + type + ".yml", "Translations/" + type + ".yml");

	}
	
	private static void loadDefaultExamples() {
		//Loading codExample.yml & junkExample if there is not plugins/AmazingFishing/Fish directory
		if(!new File("plugins/AmazingFishing/Fish").exists()) {
			loadAndMerge("Fish/codExample.yml", "Fish/codExample.yml");
			loadAndMerge("Fish/junkExample.yml", "Fish/junkExample.yml");
		}
	}
	
	/**
	 * This method will copy and load all content in file
	 * 
	 * @param sourcePath - path to .yml (source file)
	 * @param filePath - path to .yml in plugins/AmazingTags directory (server file)
	 * @return {@link Config}
	 */
	private static Config loadAndMerge(String sourcePath, String filePath) {
		temp_data.reload(StreamUtils.fromStream(Loader.plugin.getResource("resources/" + sourcePath)));
		Config result = new Config("plugins/AmazingFishing/" + filePath);
		if (result.merge(temp_data))
			result.save(DataType.YAML);
		temp_data.clear();
		return result;
	}
}

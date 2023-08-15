package me.devtec.amazingfishing.utils;

import java.io.File;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.guis.MenuLoader;
import me.devtec.shared.dataholder.Config;
import me.devtec.shared.dataholder.DataType;
import me.devtec.shared.utility.StreamUtils;

public class Configs {

	//Main config
	public static Config config;
	
	//Translations
	public static Config engtrans, //English default translation
						translation; //normal translation
	
	//TODO
	public static Config quest, treasur, enchant, achievements;
	
	private static Config temp_data = new Config();
	
	/**
	 * Creates and loads all config files. </br>
	 * Also loading next and previous button.
	 */
	public static void load() {
		config = loadAndMerge("Config.yml", "Config.yml");
		
		// Loading translations
		loadTranslations();
		// Loading default examples
		loadDefaultExamples();
		
		// Loading GUIs
		for(String file: MenuLoader.getMenuFiles())
			if(!new File("plugins/AmazingFishing/Menus/"+file+".yml").exists())
				loadAndMerge("Menus/" + file + ".yml", "Menus/" + file + ".yml");
		
		
	}
	// Loading translations files
	private static void loadTranslations() {
		//Loading included translations
		engtrans = loadAndMerge("Translations/en.yml", "Translations/en.yml"); //default English translation
		loadAndMerge("Translations/cs.yml", "Translations/cs.yml"); //Czech translation included in default
		
		//loading new translations
		String type = getTranslation();
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
	
	/** Get what translation is currently used.
	 * @return Returning 'translation_file' String from Config.yml. (en, cs, ...)
	 */
	public static String getTranslation() {
		String type = "en";
		if (config.exists("translation_file"))
			type = config.getString("translation_file");
		return type;
	}
}

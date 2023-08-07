package me.devtec.amazingfishing;

import java.io.File;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.fishing.CaughtItem;
import me.devtec.amazingfishing.fishing.Fish;
import me.devtec.amazingfishing.fishing.FishingItem;
import me.devtec.amazingfishing.fishing.Junk;
import me.devtec.amazingfishing.player.Fisher;
import me.devtec.amazingfishing.utils.MessageUtils;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.amazingfishing.utils.points_economy.PointsManager;
import me.devtec.shared.dataholder.Config;
import me.devtec.theapi.bukkit.nms.NBTEdit;

public class API {
	
	private static HashMap<Player, Fisher> player_list = new HashMap<Player, Fisher>();
	
	private static HashMap<String, Fish> fish_list = new HashMap<String, Fish>(); //file_name | Fish
	private static HashMap<String, Junk> junk_list = new HashMap<String, Junk>(); //file_name | Junk
	
	protected static PointsManager points;
	
	/*
	 *  FISH & JUNK LISTS
	 */
	
	/**
	 * Loads all enabled items. </br>
	 * Sorted into two hashMaps. One with {@link Fish} and other with {@link Junk} items. 
	 * You can get these HashMaps with getFishList() and getJunkList methods. </br>
	 * If HashMap is empty that means that there is none Fish or Junk item enabled/created. 
	 * All files are in plugins/AmazingFIshing/Fish directory (or resources/Fish directory if you are on github) </br>
	 * 
	 * @apiNote Its only used on plugin's enable or when reloading all files.
	 */
	public static void loadFishingItems() {
		File directory = new File("plugins/AmazingFishing/Fish");
		if(directory.exists() && directory.isDirectory()) {		
			for(File file : directory.listFiles()) { // loops all files in this directory
				Config config = new Config(file);
				if(config.getString("type").equalsIgnoreCase("FISH"))
					getFishList().put(file.getName(), new Fish(config)); // adds or replaces FISH in Map
				if(config.getString("type").equalsIgnoreCase("JUNK"))
					getJunkList().put(file.getName(), new Junk(config)); // adds or replaces JUNK in Map
			}
		}
		MessageUtils.msgConsole("%name% &fLoaded %fish% fish files and %junk% junk files.", 
				Placeholders.c().add("fish", getFishList().size()).add("junk", getJunkList().size()).add("name", "[AmazingFishing]"));
	}
	public static HashMap<String, Fish> getFishList() {
		return fish_list;
	}
	public static HashMap<String, Junk> getJunkList() {
		return junk_list;
	}
	
	/** Gets {@link Fish} from loaded Fishes
	 * @param fileName
	 * @return null if there is no match
	 */
	public static Fish getFish(String fileName) {
		if(getFishList().containsKey(fileName))
			return getFishList().get(fileName);
		return null;
	}
	/** Gets {@link Junk} from loaded Junk items
	 * @param fileName
	 * @return null if there is no match
	 */
	public static Junk getJunk(String fileName) {
		if(getJunkList().containsKey(fileName))
			return getJunkList().get(fileName);
		return null;
	}
	/** Gets {@link FishingItem} from loaded Fish & Junk items.
	 * @param fileName
	 * @return {@link FishingItem} - It is parent class for {@link Fish} and {@link Junk} classes
	 * 
	 * @implNote {@link FishingItem} is parent class for {@link Fish} and {@link Junk} classes. 
	 * So this will return you {@link Junk} and also {@link Fish} item depending on fileName...
	 */
	public static FishingItem getFishingItem(String fileName){
		if(getFish(fileName) != null)
			return getFish(fileName);
		if(getJunk(fileName) != null)
			return getJunk(fileName);
		return null;
	}
	
	/** Checks if item is from this plugin
	 * @param item {@link CaughtItem}
	 * @return null if item is not from this plugin
	 */
	public static CaughtItem identifyItem(ItemStack item) {
		NBTEdit edit = new NBTEdit(item);
		Config data = new Config();
		
		if (edit.hasKey("af_data"))
			data.reload(edit.getString("af_data"));

		if (data.getKeys().isEmpty())
			return null;
		
		return new CaughtItem(item);
	}
	
	/*
	 * PLAYER
	 */

	
	/** Gets {@link Fisher}.}
	 * @param player Online player you wan't to use.
	 * @return {@link Fisher}
	 */
	public static Fisher getFisher(Player player) {
		Fisher fisher;
		if(getFisherList().containsKey(player))
			fisher = getFisherList().get(player);
		else
			fisher = new Fisher(player);
		return fisher;
	}
	
	public static HashMap<Player, Fisher> getFisherList() {
		return player_list;
	}
	
	/** Removes player from list of active fishers. Should be run when player leaves the server on when you want... your choice :D
	 * @param player The player that is leaving server.
	 */
	public static void playerQuit(Player player) {
		if(getFisherList().containsKey(player))
			getFisherList().remove(player);
	}
	
	/** Gets {@link PointsManager} class where you can manage Players points. </r>
	 * Depending on Config.yml settings plugin is not always using Fishing points. Plugin can use Vault's economy money instead of Fishing points.
	 * @return {@link PointsManager}
	 */
	public static PointsManager getPoints() {
		return points;
	}
	public static PointsManager getPointsmanager() {
		return getPoints();
	}
	
}

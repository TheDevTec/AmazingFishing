package me.devtec.amazingfishing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.devtec.amazingfishing.construct.Enchant;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.construct.FishType;
import me.devtec.amazingfishing.construct.Treasure;
import me.devtec.amazingfishing.creation.CustomEnchantment;
import me.devtec.amazingfishing.creation.CustomFish;
import me.devtec.amazingfishing.creation.CustomTreasure;
import me.devtec.amazingfishing.utils.AmazingFishingCommand;
import me.devtec.amazingfishing.utils.CatchFish;
import me.devtec.amazingfishing.utils.Configs;
import me.devtec.amazingfishing.utils.EatFish;
import me.devtec.amazingfishing.utils.Trans;
import me.devtec.amazingfishing.utils.points.UserPoints;
import me.devtec.amazingfishing.utils.points.VaultPoints;
import me.devtec.amazingfishing.utils.tournament.TournamentManager;
import me.devtec.amazingfishing.utils.tournament.TournamentType;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.apis.PluginManagerAPI;
import me.devtec.theapi.configapi.Config;
import me.devtec.theapi.placeholderapi.PlaceholderAPI;
import me.devtec.theapi.scheduler.Tasker;
import me.devtec.theapi.utils.StringUtils;
import me.devtec.theapi.utils.VersionChecker;
import me.devtec.theapi.utils.datakeeper.Data;
import me.devtec.theapi.utils.reflections.Ref;

public class Loader extends JavaPlugin {

	public static Loader plugin;
	public static Data data = new Data("plugins/AmazingFishing/Data.yml");
	public static Config trans /*= new Config("AmazingFishing/Translations.yml")*/,
			config /*= new Config("AmazingFishing/Config.yml")*/,
			gui, shop;
	static {
		Configs.load();
		API.points=config.getString("Options.PointsManager").equalsIgnoreCase("vault")?new VaultPoints():new UserPoints();
		prefix = trans.getString("Prefix");
	}
	static String prefix = trans.getString("Prefix");

	public void onEnable() {
		if(VersionChecker.getVersion(PluginManagerAPI.getVersion("TheAPI"), "4.9.4")==VersionChecker.Version.NEW) {
			TheAPI.msg(prefix+" &8*********************************************", TheAPI.getConsole());
			TheAPI.msg(prefix+" &4SECURITY: &cYou are running on outdated version of plugin TheAPI", TheAPI.getConsole());
			TheAPI.msg(prefix+" &4SECURITY: &cPlease update plugin TheAPI to latest version.", TheAPI.getConsole());
			TheAPI.msg(prefix+"        &6https://www.spigotmc.org/resources/72679/", TheAPI.getConsole());
			TheAPI.msg(prefix+" &8*********************************************", TheAPI.getConsole());
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		new me.devtec.theapi.utils.theapiutils.metrics.Metrics(this, 10630);
		reload(TheAPI.getConsole(),false);
		Bukkit.getPluginManager().registerEvents(new EatFish(), this);
		Bukkit.getPluginManager().registerEvents(new CatchFish(), this);
		TheAPI.createAndRegisterCommand("amazingfishing", "amazingfishing.command", new AmazingFishingCommand(), "fishmenu","fish","afish");
		if(config.getBoolean("Tournament.Automatic.Use")) {
			if(config.getBoolean("Tournament.Automatic.AllWorlds")) {
				new Tasker() {
					public void run() {
						for(World w : Bukkit.getWorlds()) {
							if(!config.getStringList("Tournament.Automatic.Worlds").contains(w.getName()))continue;
							if(TournamentManager.start(w, TournamentType.RANDOM, StringUtils.timeFromString(config.getString("Tournament.Automatic.Length")))) {
								String format = TournamentManager.get(w).getType().formatted(), path = TournamentManager.get(w).getType().configPath();
								for(Player p : w.getPlayers()) {
									for(String f : config.getStringList("Tournament.Start."+path+".Broadcast.Messages"))
										TheAPI.msg(PlaceholderAPI.setPlaceholders(p, f.replace("%type%", format)), p);
									for(String f : config.getStringList("Tournament.Start."+path+".Broadcast.Commands"))
										TheAPI.sudoConsole(PlaceholderAPI.setPlaceholders(p, f.replace("%type%", format)));
								}
							}
						}
					}
				}.runRepeating(StringUtils.timeFromString(config.getString("Tournament.Automatic.Period")), StringUtils.timeFromString(config.getString("Tournament.Automatic.Period")));
			}else {
				new Tasker() {
					public void run() {
						World w = Bukkit.getWorld(TheAPI.getRandomFromList(config.getStringList("Tournament.Automatic.Worlds")));
						if(TournamentManager.start(w, TournamentType.RANDOM, StringUtils.timeFromString(config.getString("Tournament.Automatic.Length")))) {
							String format = TournamentManager.get(w).getType().formatted(), path = TournamentManager.get(w).getType().configPath();
							for(Player p : w.getPlayers()) {
								for(String f : config.getStringList("Tournament.Start."+path+".Broadcast.Messages"))
									TheAPI.msg(PlaceholderAPI.setPlaceholders(p, f.replace("%type%", format)), p);
								for(String f : config.getStringList("Tournament.Start."+path+".Broadcast.Commands"))
									TheAPI.sudoConsole(PlaceholderAPI.setPlaceholders(p, f.replace("%type%", format)));
							}
						}
					}
				}.runRepeating(StringUtils.timeFromString(config.getString("Tournament.Automatic.Period")), StringUtils.timeFromString(config.getString("Tournament.Automatic.Period")));
			}
		}
		plugin=this;
	}
	
	public static void reload(CommandSender ss, boolean reload) {
		//RELOAD-CONFIG
		if(reload) {
		data.reload(data.getFile());
		config.reload();
		trans.reload();
		prefix = trans.getString("Prefix");
		API.points=config.getString("Options.PointsManager").equalsIgnoreCase("vault")?new VaultPoints():new UserPoints();
		TheAPI.msg(prefix+" Configurations reloaded.", ss);
		}
		
		//FISH
		
		//PRE-LOAD
		Map<String, FishType> toRegister = new HashMap<>();
		for(String typeS : data.getKeys("fish"))
			try {
				FishType type = FishType.valueOf(typeS.toUpperCase());
				for(String fish : data.getKeys("fish."+typeS)) {
					toRegister.put(fish+":"+type.ordinal(), type);
				}
			}catch(Exception | NoSuchFieldError err) {}
		
		//REMOVE-NOT-LOADED
		List<Fish> remove = new ArrayList<>();
		for(Entry<String, Fish> fish : API.fish.entrySet())
			if(fish.getValue() instanceof CustomFish && Ref.get(fish.getValue(), "data").equals(data))
				if(toRegister.containsKey(fish.getValue().getName()+":"+fish.getValue().getType().ordinal()))
					toRegister.remove(fish.getValue().getName()+":"+fish.getValue().getType().ordinal());
				else
					remove.add(fish.getValue());
		for(Fish s : remove)
			API.unregister(s);
		
		//REGISTER-NOT-LOADED
		for(Entry<String, FishType> s : toRegister.entrySet())
			API.register(new CustomFish(s.getKey().substring(0, s.getKey().length()-2), s.getValue().name().toLowerCase(), s.getValue(), data));
		//CLEAR-CACHE
		TheAPI.msg(prefix+" Fish registered ("+toRegister.size()+") & removed unregistered ("+remove.size()+").", ss);
		toRegister.clear();
		remove.clear();
		
		//TREASURE
		
		//PRE-LOAD
		List<String> toReg = new ArrayList<>(data.getKeys("treasures"));
		
		//REMOVE-NOT-LOADED
		List<Treasure> removeT = new ArrayList<>();
		for(Entry<String, Treasure> fish : API.treasure.entrySet())
			if(fish.getValue() instanceof CustomTreasure && Ref.get(fish.getValue(), "data").equals(data))
				if(toReg.contains(fish.getValue().getName()))
					toReg.remove(fish.getValue().getName());
				else
					removeT.add(fish.getValue());
		for(Treasure s : removeT)
			API.unregister(s);
		
		//REGISTER-NOT-LOADED
		for(String s : toReg)
			API.register(new CustomTreasure(s, data));
		
		//CLEAR-CACHE
		TheAPI.msg(prefix+" Treasures registered ("+toReg.size()+") & removed unregistered ("+removeT.size()+").", ss);
		toReg.clear();
		removeT.clear();
		
		//ENCHANTMENT
		
		//PRE-LOAD
		toReg = new ArrayList<>(data.getKeys("enchantments"));
		
		//REMOVE-NOT-LOADED
		List<String> removeE = new ArrayList<>();
		for(Entry<String, Enchant> fish : Enchant.enchants.entrySet())
			if(fish.getValue() !=null && fish.getValue() instanceof CustomEnchantment)
				if(toReg.contains(fish.getValue().getName()))
					toReg.remove(fish.getValue().getName());
				else
					removeE.add(fish.getKey());
		for(String s : removeE)
			Enchant.enchants.remove(s);
		
		//REGISTER-NOT-LOADED
		for(String s : toReg)
			new CustomEnchantment(s, 
					data.getString("enchantments."+s+".name"),
					data.getInt("enchantments."+s+".maxlevel"), 
					data.getDouble("enchantments."+s+".bonus.chance"),
					data.getDouble("enchantments."+s+".bonus.amount"),
					data.getStringList("enchantments."+s+".description"),
					data.getDouble("enchantments."+s+".cost"));
		
		//CLEAR-CACHE
		TheAPI.msg(prefix+" Enchantments registered ("+toReg.size()+") & removed unregistered ("+removeE.size()+").", ss);
		toReg.clear();
		removeE.clear();
		API.onReload.forEach(a->a.run());
	}
	public static void msg(String msg, CommandSender s) {
		TheAPI.msg(msg.replace("%prefix%", Trans.s("Prefix")), s);
	}
}

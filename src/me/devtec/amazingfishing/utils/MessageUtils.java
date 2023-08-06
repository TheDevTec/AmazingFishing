package me.devtec.amazingfishing.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.Loader;
import me.devtec.shared.components.ComponentAPI;
import me.devtec.shared.dataholder.Config;
import me.devtec.shared.json.Json;
import me.devtec.shared.placeholders.PlaceholderAPI;
import me.devtec.shared.utility.ColorUtils;
import me.devtec.shared.utility.StringUtils;
import me.devtec.shared.utility.StringUtils.FormatType;
import me.devtec.theapi.bukkit.BukkitLoader;
import me.devtec.theapi.bukkit.nms.NmsProvider.ChatType;


public class MessageUtils {
	
	/**
	 * Utility class used in our MessageUtils class methods. </br>Add placeholders like %player%, %money% and this class will handle the replacing!</br>
	 * In String text placeholders looks like %your_placeholder% but "%" is added automatically when you are adding placeholder!!
	 */
	public static class Placeholders {
		private final Map<String, String> set = new HashMap<>();
		private final Map<String, Player> player_set = new HashMap<>();

		public static Placeholders c() {
			return new Placeholders();
		}
		
		/** Method used to add new replaceable placeholder
		 * @param placeholder Placeholder in the message (example: %player% as player)
		 * @param replace What the placeholder should be replaced with
		 * @return Method returns this instance
		 */
		public Placeholders add(String placeholder, Object replace) {
			if(replace instanceof Double)
				set.put(placeholder, StringUtils.formatDouble(FormatType.NORMAL, ((Double)replace)) );
			else
				set.put(placeholder, replace + "");
			return this;
		}

		/** Method used to add new replaceable player placeholder.
		 * Placeholder %player_...% is replaced automatically!
		 * @param placeholder Placeholder in the message (example: %target% as some other player)
		 * @param replace Which player the placeholder should be replaced with
		 * @return Method returns this instance
		 */
		public Placeholders addPlayer(String placeholder, Player player) {
			if (player != null && placeholder != null)
				player_set.put(placeholder, player);
			return this;
		}

		/** Method used to add new replaceable player placeholder.
		 * Placeholder %player_...% is replaced automatically!
		 * @param placeholder Placeholder in the message (example: %target% as some other player)
		 * @param replace Which player the placeholder should be replaced with
		 * @return Method returns this instance
		 */
		public Placeholders addPlayer(String placeholder, CommandSender player) {
			if (player == null || placeholder == null)
				return this;
			if (player instanceof Player)
				player_set.put(placeholder, (Player) player);
			else
				replace(placeholder, "CONSOLE");
			return this;
		}

		/** Method used to add new replaceable offline player placeholder.
		 * Placeholder %player_...% is replaced automatically!
		 * @param placeholder Placeholder in the message (example: %target% as some other player)
		 * @param replace Which player the placeholder should be replaced with
		 * @return Method returns this instance
		 */
		public Placeholders addOffline(String palceholder, String player) {
			add(palceholder, player);
			add(palceholder + "_name", player);
			add(palceholder + "_displayname", player);
			add(palceholder + "_customname", player);
			return this;
		}

		/** Method used to add new replaceable placeholder
		 * @param placeholder Placeholder in the message (example: %player% as player)
		 * @param replace What the placeholder should be replaced with
		 * @return Method returns this instance
		 */
		public Placeholders replace(String placeholder, Object replace) {
			return add(placeholder, replace);
		}
		
		/** Will copy all placeholders into new {@link Placeholders}.
		 * @param placeholders Your already existing placeholders.
		 * @return {@link Placeholders}
		 */
		public Placeholders add(Placeholders placeholders) {
			if(!placeholders.set.isEmpty())
				set.putAll(placeholders.set);
			if(!placeholders.player_set.isEmpty())
				player_set.putAll(placeholders.player_set);
			return this;
		}
		
		/** Method used to apply placeholders in message
		 * @param sender The player to whom you are sending the message
		 * @param text Message where you want to replace placeholders
		 * @param placeholders 
		 * @return Returning edited text
		 */
		public String apply(String text) {
			if (!player_set.isEmpty())
				for (Entry<String, Player> players : player_set.entrySet()) {
					/*
					 * %player%, %target%, etc... 
					 * 		%player% - Real or Nickname (used in our different plugin, sorry :D)
					 * 		%player_name% - Just Real name 
					 * 		%player_displayname% %player_customname%
					 */
					if (text.contains("%" + players.getKey() + "_name%")) {
						text = text.replace("%" + players.getKey() + "_name%", players.getValue().getName());
						continue;
					}
					if (text.contains("%" + players.getKey() + "_displayname%")) {
						text = text.replace("%" + players.getKey() + "_displayname%", players.getValue().getDisplayName());
						continue;
					}
					if (text.contains("%" + players.getKey() + "_customname%")) {
						text = text.replace("%" + players.getKey() + "_customname%", players.getValue().getCustomName());
						continue;
					}
					text = text.replace("%" + players.getKey() + "%", players.getValue().getName());
				}
			
			for (Entry<String, String> placeholder : set.entrySet()) {
					text = text.replace("%" + placeholder.getKey() + "%", placeholder.getValue());
			}
			
			// PlaceholderAPI expansion
			if(!player_set.isEmpty() && player_set.containsKey("player"))
				return PlaceholderAPI.apply(text, player_set.get("player").getUniqueId());
			
			return PlaceholderAPI.apply(text, null);
		//	return text;
		}
	} //END OF CLASS PLACEHOLDERS

	/**
	 * Gets prefix from translation file
	 */
	public static String getPrefix() {
		if(Configs.translation == null || Configs.engtrans == null)
			return null;
		return Configs.translation.exists("prefix") ? Configs.translation.getString("prefix") : 
			Configs.engtrans.getString("prefix");
	}
	
	/** Method used to replace placeholders in messages
	 * @param sender The player to whom you are sending the message
	 * @param message Message where you want to replace placeholders
	 * @param placeholders {@link Placeholders}
	 * @return Returning edited message
	 */
	public static List<String> placeholder(CommandSender sender, List<String> string, Placeholders placeholders) {
		List<String> clone = new ArrayList<>(string);
		clone.replaceAll(s -> placeholder(sender, s, placeholders));
		return clone;
	}

	/** Method used to replace placeholders in message.
	 * @param sender The player to whom you are sending the message
	 * @param message Message where you want to replace placeholders
	 * @param placeholders {@link Placeholders}
	 * @return Returning edited message
	 * 
	 * @apiNote This is modified method, default method is in {@link Placeholders} class!!
	 */
	public static String placeholder(CommandSender sender, String message, Placeholders placeholders) {
		//Replacing %prefix% in message
		if (getPrefix() != null)
			message = message.replace("%prefix%", getPrefix());
		
		//If there even are something to replace
		if (placeholders != null) {
			//adding default player placeholder if there is none in existing Maps
			if (sender instanceof Player && !placeholders.set.containsKey("player") && 
					!placeholders.player_set.containsKey("player"))
				placeholders.addPlayer("player", sender);
			
			//Replacing player placeholder
			if (!placeholders.player_set.isEmpty())
				for (Entry<String, Player> players : placeholders.player_set.entrySet()) {
					/*
					 * %player%, %target%, etc... 
					 * 		%player% - Real or Nickname (used in our different plugin, sorry :D)
					 * 		%player_name% - Just Real name 
					 * 		%player_displayname% %player_customname%
					 */
					if (message.contains("%" + players.getKey() + "_name%")) {
						message = message.replace("%" + players.getKey() + "_name%", players.getValue().getName());
						continue;
					}
					if (message.contains("%" + players.getKey() + "_displayname%")) {
						message = message.replace("%" + players.getKey() + "_displayname%", players.getValue().getDisplayName());
						continue;
					}
					if (message.contains("%" + players.getKey() + "_customname%")) {
						message = message.replace("%" + players.getKey() + "_customname%", players.getValue().getCustomName());
						continue;
					}
					message = message.replace("%" + players.getKey() + "%", players.getValue().getName());
				}
			
			//Replacing rest of placeholders
			for (Entry<String, String> placeholder : placeholders.set.entrySet())
				message = message.replace("%" + placeholder.getKey() + "%", placeholder.getValue() + "");
			
		}
	
		//Returning edited String
		return message;
	}

	/*
	 * SENDING MESSAGES FROM TRANSLATION FILE
	 * 
	 * Note: If you want to use this class in your project, replace 'Loader.config' as your translation Config.
	 * 			You can also add default translation file.
	 */
	
	/** Sending message from translation {@link Config}.
	 * @param player Original message receiver
	 * @param path Path to that message in the file
	 * @param placeholders {@link Placeholders}
	 */
	public static void message(CommandSender player, String path, Placeholders placeholders) {
		message(player, path, placeholders, true, player);
	}
	/** Sending message from translation {@link Config}.
	 * @param player Original message receiver
	 * @param path Path to that message in the file
	 * @param placeholders {@link Placeholders}
	 * @param split If plugin should search for /n characters to split message into multiple lines
	 */
	public static void message(CommandSender player, String path, Placeholders placeholders, boolean split) {
		message(player, path, placeholders, split, player);
	}
	/** Sending message from translation {@link Config}.
	 * @param player Original message receiver
	 * @param path Path to that message in the file
	 * @param placeholders {@link Placeholders}
	 * @param targets More targets <strong>(THIS SHOULD INCLUDE ORIGINAL MESSAGE RECEIVER IF YOU ALSO WANT TO SEND HIM THIS MESSAGE)</strong>
	 */
	public static void message(CommandSender player, String path, Placeholders placeholders, CommandSender... targets) {
		message(player, path, placeholders, true, targets);
	}
	/** Sending message from translation {@link Config}.
	 * @param player Original message receiver
	 * @param path Path to that message in the file
	 * @param placeholders {@link Placeholders}
	 * @param split If plugin should search for /n characters to split message into multiple lines
	 * @param targets More targets <strong>(THIS SHOULD INCLUDE ORIGINAL MESSAGE RECEIVER IF YOU ALSO WANT TO SEND HIM THIS MESSAGE)</strong>
	 */
	public static void message(CommandSender player, String path, Placeholders placeholders, boolean split, CommandSender... targets) {
		if (Configs.translation.exists(path)) // If path exists in file -> Seding message
			msgConfig(player, Configs.translation, path, placeholders, split, targets);
		else {
			Loader.plugin.getLogger().warning("Path " + path + " not found in config " + Configs.translation.getFile().getName() + ", please complete your translation.");
			// Sending message from default translation file
			msgConfig(player, Configs.engtrans, path, placeholders, split, targets);
		}
	}
	
	/*
	 * SENDING MESSAGES FROM SPECIFIC FILE
	 * 
	 */

	/** Sending message from specific {@link Config} file
	 * @param player Original message receiver
	 * @param path Path to that message in the file
	 * @param config File where the message is located ({@link Config})
	 * @param placeholders {@link Placeholders}
	 */
	public static void msgConfig(CommandSender player, String path, Config config, Placeholders placeholders) {
		msgConfig(player, config, path, placeholders, true, player);
	}
	/** Sending message from specific {@link Config} file
	 * @param player Original message receiver
	 * @param path Path to that message in the file
	 * @param config File where the message is located ({@link Config})
	 * @param placeholders {@link Placeholders}
	 * @param split If plugin should search for /n characters to split message into multiple lines
	 */
	public static void msgConfig(CommandSender player, String path, Config config, Placeholders placeholders, boolean split) {
		msgConfig(player, config, path, placeholders, split, player);
	}
	/** Sending message from specific {@link Config} file
	 * @param player Original message receiver
	 * @param path Path to that message in the file
	 * @param config File where the message is located ({@link Config})
	 * @param placeholders {@link Placeholders}
	 * @param targets More targets <strong>(THIS SHOULD INCLUDE ORIGINAL MESSAGE RECEIVER IF YOU ALSO WANT TO SEND HIM THIS MESSAGE)</strong>
	 */
	public static void msgConfig(CommandSender player, String path, Config config, Placeholders placeholders, CommandSender... targets) {
		msgConfig(player, config, path, placeholders, true, targets);
	}
	/** Sending message from specific {@link Config} file
	 * @param player Original message receiver
	 * @param path Path to that message in the file
	 * @param config File where the message is located ({@link Config})
	 * @param placeholders {@link Placeholders}
	 * @param split If plugin should search for /n characters to split message into multiple lines
	 * @param targets More targets <strong>(THIS SHOULD INCLUDE ORIGINAL MESSAGE RECEIVER IF YOU ALSO WANT TO SEND HIM THIS MESSAGE)</strong>
	 */
	public static void msgConfig(CommandSender player, String path, Config config, Placeholders placeholders, boolean split, CommandSender... targets) {
		msgConfig(player, config, path, placeholders, split, targets);
	}

	/*
	 * SPECIAL METHODS
	 */
	
	/** Sending the message directly. Usually from plugin, message is usually not in file.
	 * 		If you want to send messages from file, maybe use 
	 * 		<code>message(...)</code> or <code>msgConfig(...)</code>.
	 * 
	 * @param player Original message receiver
	 * @param message Original message you are sending
	 * @param placehholders {@link Placeholders}
	 * @param targets More targets <strong>(THIS SHOULD INCLUDE ORIGINAL MESSAGE RECEIVER IF YOU ALSO WANT TO SEND HIM THIS MESSAGE)</strong>
	 */
	public static void sendPluginMessage(CommandSender player, String message, Placeholders placehholders, CommandSender... targets) {
		//If there is no one to send this to or message is null
		if (targets == null || message == null)
			return;
		//JSON message check
		if (message.startsWith("[") && message.endsWith("]") || message.startsWith("{") && message.endsWith("}")) {
			String trimmed = message.trim();
			if (trimmed.equals("[]") || trimmed.equals("{}"))
				return; // Do not send empty json
			msgJson(Bukkit.getConsoleSender(), message, null, targets);
			return;
		}

		if (message.isEmpty())
			return; // Do not send empty strings
		msg(player, message, placehholders, true, targets);
	}
	
	
	
	/** No permission message...
	 * @param player message recipient
	 * @param permission what permission is player missing
	 */
	public static void noPerm(CommandSender player, String permission) {
		message(player, "noPerms", Placeholders.c().replace("permission", permission));
	}
	
	/** Sending message to CONSOLE
	 * @param message
	 * @param placehholders {@link Placeholders}
	 */
	public static void msgConsole(String message, Placeholders placehholders) {
		Bukkit.getConsoleSender().sendMessage(ColorUtils.colorize(placeholder(null, message, placehholders)));
	}

	/** Sending an announcement message to players
	 * @param message message that you want to send
	 * @param targets receivers
	 */
	public static void sendAnnouncement(String message, CommandSender... targets) {
		boolean split = true;
		if (targets == null)
			return;
		if (message.startsWith("[") && message.endsWith("]") || message.startsWith("{") && message.endsWith("}")) {
			String trimmed = message.trim();
			if (trimmed.equals("[]") || trimmed.equals("{}"))
				return; // Do not send empty json
			msgJson(Bukkit.getConsoleSender(), message, null, targets);
			return;
		}

		if (message.isEmpty())
			return; // Do not send empty strings
		msg(Bukkit.getConsoleSender(), message, null, split, targets);
	}
	public static void sendAnnouncement(String message) {
		CommandSender[] plist = new CommandSender[Bukkit.getOnlinePlayers().size()+1];
		int i = 0;
		for(CommandSender p : Bukkit.getOnlinePlayers())
			plist[i++] = p;
		plist[i] = Bukkit.getConsoleSender();
		
		if(plist != null)
			sendAnnouncement(message, plist);
	}

	/*
	 * SENDING MESSAGE and related methods
	 */
	
	/** Main method for sending message.
	 * @param player Original message receiver
	 * @param config File where is your message
	 * @param path Path to that message in the file
	 * @param placeholders {@link Placeholders}
	 * @param split If plugin should search for /n characters to split message into multiple lines
	 * @param targets More targets <strong>(THIS SHOULD INCLUDE ORIGINAL MESSAGE RECEIVER IF YOU ALSO WANT TO SEND HIM THIS MESSAGE)</strong>
	 */
	public static void msgConfig(CommandSender player, Config config, String path, Placeholders placeholders, boolean split, CommandSender... targets) {
		// If the main player is null
		if (player == null)
			return;
		// Getting Object from config file
		Object text = config.get(path);
		// If there is something wrong -> Probably missing message in config
		if (text == null) {
			Loader.plugin.getLogger().warning("Path " + path + " not found in config " + config.getFile().getName() + ", report this bug to the DevTec discord.");
			return;
		}

		// If text is JSON or list of messages
		if (text instanceof Collection || text instanceof Map) {
			if (config.isJson(path)) {
				String line = config.getString(path);
				String trimmed = line.trim();
				if (trimmed.equals("[]") || trimmed.equals("{}"))
					return; // Do not send empty json
				msgJson(player, line, placeholders, targets);
				return;
			}
			// It is a list!! Sending each line...
			for (String line : config.getStringList(path))
				msg(player, line, placeholders, split, targets);
			return;
		}
		// Text is not JSON -> Getting normal String and sending message
		String line = config.getString(path);
		if (line.isEmpty())
			return; // Do not send empty strings
		msg(player, line, placeholders, split, targets);
	}

	//Sending JSON message
	@SuppressWarnings("unchecked")
	private static void msgJson(CommandSender s, String original, Placeholders placeholders, CommandSender... targets) {
		// Weird magic here... :D
		Object json = Json.reader().simpleRead(original);
		List<Map<String, Object>> jsonList = new ArrayList<>();
		if (json instanceof Collection) {
			for (Object val : (Collection<?>) json)
				if (val instanceof Map)
					jsonList.add((Map<String, Object>) val);
				else {
					if (val.toString().isEmpty())
						continue; // You are trying to fix json by yourself?
					jsonList.addAll(ComponentAPI.toJsonList(ComponentAPI.fromString(val.toString())));
				}
		} else if (json instanceof Map)
			jsonList.add((Map<String, Object>) json);
		else
			return; // Bug?

		// PROCESS PLACEHOLDER & COLORS
		for (Map<String, Object> map : jsonList)
			replaceJson(s, map, placeholders);
		// Replacing to minecraft json
		jsonList = ComponentAPI.fixJsonList(jsonList);
		// Sending packet
		String written = Json.writer().simpleWrite(jsonList);
		written = "[\"\", " + written.substring(1);
		Object chat = BukkitLoader.getNmsProvider().chatBase(written);

		Object packet = BukkitLoader.getNmsProvider().packetChat(ChatType.SYSTEM, chat);
		for (CommandSender target : targets)
			if (target instanceof Player)
				BukkitLoader.getPacketHandler().send((Player) target, packet);
			else
				target.sendMessage(ComponentAPI.listToString(jsonList));
	}

	//Replacing placeholders from JSON message
	@SuppressWarnings("unchecked")
	private static void replaceJson(CommandSender s, Map<String, Object> map, Placeholders placeholders) {
		for (Entry<String, Object> entry : map.entrySet()) {
			if (entry.getKey().equals("text")) {
				String text = entry.getValue() + "";

				text = placeholder(s, text, placeholders);
				text = ColorUtils.colorize(PlaceholderAPI.apply(text, s instanceof Player ? ((Player) s).getUniqueId() : null));
				entry.setValue(text);
				continue;
			}
			if (entry.getKey().equals("hoverEvent") || entry.getKey().equals("clickEvent")) {
				replaceJson(s, (Map<String, Object>) entry.getValue(), placeholders);
				continue;
			}
			if (entry.getKey().equals("contents") || entry.getKey().equals("value")) {
				if (entry.getValue() instanceof Map) {
					replaceJson(s, (Map<String, Object>) entry.getValue(), placeholders);
					continue;
				}
				if (entry.getValue() instanceof List) {
					List<Object> col = (List<Object>) entry.getValue();
					ListIterator<Object> itr = col.listIterator();
					while (itr.hasNext()) {
						Object val = itr.next();
						if (val instanceof Map)
							replaceJson(s, (Map<String, Object>) val, placeholders);
						else {
							String text = val + "";

							text = placeholder(s, text, placeholders);
							text = ColorUtils.colorize(text);
							// text = ColorUtils.colorize(PlaceholderAPI.apply(text, s instanceof Player ?
							// ((Player) s).getUniqueId() : null));
							itr.set(text);
						}
					}
					continue;
				}
				String text = entry.getValue() + "";

				text = placeholder(s, text, placeholders);
				text = ColorUtils.colorize(text);
				// text = ColorUtils.colorize(PlaceholderAPI.apply(text, s instanceof Player ?
				// ((Player) s).getUniqueId() : null));
				entry.setValue(text);
			}
		}
	}

	/** Final method in message sending chain. Sending message, replacing colors and placeholders and some special splitting (/n)
	 * @param s Original message receiver
	 * @param original The message that you want to send 
	 * @param placeholders {@link Placeholders}
	 * @param split If plugin should search for /n characters to split message into multiple lines.
	 * @param targets More targets <strong>(YES THIS SHOULD INCLUDE ORIGINAL MESSAGE RECEIVER IF YOU ALSO WANT TO SEND HIM THIS MESSAGE)</strong>
	 */
	private static void msg(CommandSender s, String original, Placeholders placeholders, boolean split, CommandSender... targets) {
		String text = original;
		text = ColorUtils.colorize(placeholder(s, text, placeholders));
		//If if the plugin should search for special characters /n or //n to split message into multiple lines
		if (split) {
			String lastcolor = null;
			for (String line : text.replace("\\n", "\n").split("\n")) {
				if (lastcolor != null && lastcolor.length() == 1) // minecraft colors
					lastcolor = "&" + lastcolor;
				if (lastcolor != null && lastcolor.length() == 7) { // HEX colors
					lastcolor = "&" + lastcolor;
					lastcolor = lastcolor.replace("&x", "#");
				}
				if (lastcolor != null && lastcolor.length() > 7) { //what? oh yeah... more colors
					StringBuilder build = new StringBuilder();
					for (String c : lastcolor.split(""))
						build.append("&").append(c);
					lastcolor = build.toString();
				}
				// Replacing placeholders and color symbols
				String coloredText = ColorUtils.colorize(lastcolor == null ? line : lastcolor + "" + line);
				// Preparing packet
				Object packet = BukkitLoader.getNmsProvider().packetChat(ChatType.SYSTEM, ComponentAPI.fromString(coloredText));
				for (CommandSender target : targets)
					if (target instanceof Player) // if PLAYER -> sending packet -> sending message
						BukkitLoader.getPacketHandler().send((Player) target, packet);
					else
						target.sendMessage(coloredText);//or sending message as in the old days (it's console)
				//fixing new last color
				lastcolor = ColorUtils.getLastColors(ColorUtils.colorize(line));
			}
		} else {
			// Replacing placeholders and color symbols
			String coloredText = ColorUtils.colorize(PlaceholderAPI.apply(text, s instanceof Player ? ((Player) s).getUniqueId() : null));
			// Preparing packet
			Object packet = BukkitLoader.getNmsProvider().packetChat(ChatType.SYSTEM, ComponentAPI.fromString(coloredText));
			for (CommandSender target : targets)
				if (target instanceof Player) // if PLAYER -> sending packet -> sending message
					BukkitLoader.getPacketHandler().send((Player) target, packet); 
				else
					target.sendMessage(coloredText); //or sending message as in the old days (it's console)
		}
	}
}
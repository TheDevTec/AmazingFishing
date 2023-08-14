package me.devtec.amazingfishing.utils.command;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.utils.Configs;
import me.devtec.amazingfishing.utils.MessageUtils;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.shared.commands.holder.CommandHolder;
import me.devtec.shared.commands.manager.PermissionChecker;
import me.devtec.shared.commands.structures.CommandStructure;
import me.devtec.shared.dataholder.Config;
import me.devtec.shared.utility.StringUtils;
import me.devtec.theapi.bukkit.BukkitLoader;

public interface PluginCommand<T> {
	final Config file = Configs.config;

	/*
	 * PERMISSIONS
	 */
	public static final PermissionChecker<CommandSender> PERMS_CHECKER = (sender, perm, tablist) -> {
		if(!(sender instanceof Player))
			return true; //player is CONSOLE
		return API.getFisher((Player)sender).hasPermission(perm, !tablist);
	};
	
	public static final PermissionChecker<Player> PLAYER_PERMS_CHECKER = (sender, perm, tablist) -> 
		API.getFisher(sender).hasPermission(perm, !tablist);
	
	String getSection();
	
	void register();
	
	void unregister();
	
	boolean isRegistered();
	
	CommandHolder<T> getCommand();
	
	void updateCommand(CommandStructure<T> newCommand);
	
	/*
	 * QUICK MESSAGE SENDING
	 */
	default void msg(CommandSender sender, String path) {
		MessageUtils.message(sender, path, Placeholders.c());
	}

	default void msg(CommandSender sender, String path, Placeholders placeholders) {
		MessageUtils.message(sender, path, placeholders);
	}
	
	default void offlinePlayer(CommandSender sender, String player) {
		MessageUtils.message(sender, "offlinePlayer", Placeholders.c().add("target", player));
	}
	
	/*
	 * GETTING PERMISSION
	 */
	
	/** Gets specific command permission from configuration file.
	 * @param path Default permission is 'cmd'
	 * @return
	 */
	default String getPermission(String path) {
		return file.getString(getSection()+".permissions."+path);
	}
	
	default List<String> getCommands() {
		return file.getStringList(getSection() + ".cmds");
	}
	
	/*
	 * PLAYER SELECTORS
	 * 
	 *  /cmd <selector> ---> You can select Online player, or use @.... as selector
	 */
	public default Collection<? extends Player> playerSelectors(CommandSender sender, String selector) {
		char lowerCase = selector.charAt(0) == '*' ? '*' : selector.charAt(0) == '@' && selector.length() == 2 ? Character.toLowerCase(selector.charAt(1)) : 0;
		if (lowerCase != 0)
			switch (lowerCase) {
			case 'a':
			case 'e':
			case '*':
				return BukkitLoader.getOnlinePlayers();
			case 'r':
				return Arrays.asList(StringUtils.randomFromCollection(BukkitLoader.getOnlinePlayers()));
			case 's':
			case 'p':
				Location pos = null;
				if (sender instanceof Player)
					pos = ((Player) sender).getLocation();
				else if (sender instanceof BlockCommandSender)
					pos = ((BlockCommandSender) sender).getBlock().getLocation();
				else
					pos = new Location(Bukkit.getWorlds().get(0), 0, 0, 0);
				double distance = -1;
				Player nearestPlayer = null;
				for (Player sameWorld : pos.getWorld().getPlayers())
					if (distance == -1 || distance < sameWorld.getLocation().distance(pos)) {
						distance = sameWorld.getLocation().distance(pos);
						nearestPlayer = sameWorld;
					}
				return BukkitLoader.getOnlinePlayers().isEmpty() ? Collections.emptyList() : Arrays.asList(nearestPlayer == null ? BukkitLoader.getOnlinePlayers().iterator().next() : nearestPlayer);
			}
		Player target = Bukkit.getPlayer(selector);
		return target == null ? Collections.emptyList() : Arrays.asList(target);
	}
	
}

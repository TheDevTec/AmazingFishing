package me.devtec.amazingfishing.utils.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.guis.MenuLoader;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.shared.commands.holder.CommandHolder;
import me.devtec.shared.commands.selectors.Selector;
import me.devtec.shared.commands.structures.CommandStructure;

public class Command implements PluginCommand<CommandSender>{

	private Sound opening = Sound.BLOCK_CHEST_OPEN;
	private CommandHolder<CommandSender> cmd;
	
	@Override
	public CommandHolder<CommandSender> getCommand() {
		return this.cmd;
	}

	@Override
	public void updateCommand(CommandStructure<CommandSender> newCommand) {
		//Registering command
		List<String> aliases = getCommands();
		if(!aliases.isEmpty())
			this.cmd = newCommand.build().register(aliases.remove(0), aliases.toArray(new String[0]));
	}
	
	@Override
	public String getSection() {
		return "command";
	}

	/*
	 * cmd - opening main menu
	 * cmd open <menu> (player) - opening specific menu (for specific player)
	 * cmd sell - opening selling menu
	 * 
	 */
	@Override
	public void register() {
		CommandStructure<CommandSender> cmd = CommandStructure.create(CommandSender.class, PERMS_CHECKER, (s, structure, args) -> {
			//Opening menu to player
			if(s instanceof Player)  //if player opening the menu
				tryOpen(s, "main");
		}).permission(getPermission("cmd"));
		
		// cmd sell
		cmd.argument("sell", (s, structure, args) -> {
			if(s instanceof Player) //if player opening the menu
				tryOpen(s, "shop_sell");
			
		});
		
		// cmd open
		cmd.parent().argument("open", (s, structure, args) -> {
			if(s instanceof Player)  //if player opening the menu
				tryOpen(s, "main");
		}).permission(getPermission("open"))
			// cmd open <menu>
			.callableArgument((s, structure, args) -> new ArrayList<>(MenuLoader.getMenus().keySet()), 
					(s, structure, args) -> {
						if(s instanceof Player)  //if player opening the menu
							tryOpen(s, args[1]); //opening specific menu
					}).permission(getPermission("open"))
				//message if player is not online
				.fallback((s, structure, args) -> {
					offlinePlayer(s, args[1]);
				})
				// cmd open <menu> <player>
				.selector(Selector.ENTITY_SELECTOR, (s, structure, args) -> {
					for(Player player : playerSelectors(s, args[2])) {
							tryOpen(player, args[1]);
					}
				}).permission(getPermission("openOthers"));
		//TODO - POINTS
		
		
		//Registering command
		updateCommand(cmd);
	}

	@Override
	public void unregister() {
		if(!isRegistered())
			return;
		cmd.unregister();
		cmd = null;
	}

	@Override
	public boolean isRegistered() {
		return cmd != null;
	}

	
	private void playOpeningSong(CommandSender player) {
		((Player)player).playSound( ((Player)player).getLocation(), opening, 5, 10);
	}
	
	private void tryOpen(CommandSender s, String menu) {
		if(!(s instanceof Player)) //if not player
			return;
		//try to open menu - or error message
		try {
			MenuLoader.openMenu((Player)s, "main");
			playOpeningSong(s);
		} catch (Exception e) {
			msg(s, "guis.notLoaded", Placeholders.c().add("menu", "main"));
		}
	}

}

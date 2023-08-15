package me.devtec.amazingfishing.utils.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.guis.MenuLoader;
import me.devtec.amazingfishing.player.Fisher;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.shared.commands.holder.CommandHolder;
import me.devtec.shared.commands.selectors.Selector;
import me.devtec.shared.commands.structures.CommandStructure;
import me.devtec.shared.utility.StringUtils;

public class Command implements PluginCommand<CommandSender> {

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
	 * cmd points <Add | Remove | Get > <player> (points)
	 * 
	 */
	@Override
	public void register() {
		CommandStructure<CommandSender> cmd = CommandStructure.create(CommandSender.class, PERMS_CHECKER, (s, structure, args) -> {
			//Opening menu to player
			if(s instanceof Player)  //if player opening the menu
				tryOpen(s, "main");
			else
				helpAll(s);
		}).permission(getPermission("cmd"));
		
		// cmd HELP
		cmd.argument("help", (s, structure, args) -> {
				helpAll(s);
		}).permission(getPermission("cmd"));
		
		// cmd SELL
		cmd.argument("sell", (s, structure, args) -> {
			if(s instanceof Player) //if player opening the menu
				tryOpen(s, "shop_sell");
			else
				helpAll(s);
		}).permission(getPermission("open"));
		
		// cmd RELOAD
		cmd.argument("reload", (s, structure, args) -> {
			
		});
		
		// cmd OPEN
		cmd.argument("open", (s, structure, args) -> {
			if(s instanceof Player)  //if player opening the menu
				tryOpen(s, "main");
			else //help message for console
				help(s, "openOthers");
		}).permission(getPermission("open"))
			// cmd open <MENU>
			.callableArgument((s, structure, args) -> new ArrayList<>(MenuLoader.getMenus().keySet()), 
					(s, structure, args) -> {
						if(s instanceof Player)  //if player opening the menu
							tryOpen(s, args[1]); //opening specific menu
						else
							help(s, "openOthers");
					}).permission(getPermission("open"))
			
				// cmd open <menu> <PLAYER>
				//message if player is not online
				.fallback((s, structure, args) -> {
					offlinePlayer(s, args[1]);
				})
				.selector(Selector.ENTITY_SELECTOR, (s, structure, args) -> {
					for(Player player : playerSelectors(s, args[2])) {
							tryOpen(player, args[1]);
					}
				}).permission(getPermission("openOthers"));
		
		// cmd points <Add | Remove | Get > <player> (points)
		// cmd POINTS
		cmd.argument("points", (s, structure, args) -> {
			// if sender is CONSOLE -> sending help messages
			if( !(s instanceof Player)) {
				help(s, "points_get");
				help(s, "points_set");
				help(s, "points_add");
				return;
			}
			Fisher f = API.getFisher((Player)s);
			// if sender can do all three commands -> sending help messages
			if(f.hasPermission(getPermission("points_set"), false) && 
					f.hasPermission(getPermission("points_add"), false)) {
				help(s, "points_get");
				help(s, "points_set");
				help(s, "points_add");
				return;
			}
			msg(s, "points.get", Placeholders.c().add("points", API.getPointsmanager().get(s.getName()))
					.addPlayer("target", s));
		}).permission(getPermission("points_get"))
		
			// cmd points GET
			.argument("get", (s, structure, args) -> {
					help(s, "points_get");
			}).permission(getPermission("points_get"))
				// cmd points get <PLAYER>
				//message if player is not online
				.fallback((s, structure, args) -> {
					offlinePlayer(s, args[1]);
				})
				.selector(Selector.ENTITY_SELECTOR, (s, structure, args) -> {
					for(Player player : playerSelectors(s, args[2])) {
						msg(s, "points.get", Placeholders.c().add("points", API.getPointsmanager().get(player.getName()))
							.addPlayer("target", player));
					}
				})
				.parent(1) // cmd points get <--
				// cmd points get <OFFLINE PLAYER>
				.argument(null, (s, structure, args) -> {
					String target = args[2];
					if(API.getUser(target)!= null) {
						msg(s, "points.get", Placeholders.c().add("points", API.getPointsmanager().get(target))
								.add("target", target));
					}
				})
				
		.parent(2) // cmd points <--
			
			// cmd points REMOVE
			.argument("remove", (s, structure, args) -> {
				help(s, "points_remove");
			}).permission(getPermission("points_remove"))
				// cmd points remove <PLAYER>
				//message if player is not online
				.fallback((s, structure, args) -> {
					offlinePlayer(s, args[1]);
				})
				.selector(Selector.ENTITY_SELECTOR, (s, structure, args) -> {
					help(s, "points_remove");
				})
					// cmd points remove <player> <AMOUNT>
					.selector(Selector.NUMBER, (s, structure, args) -> {
						for(Player player : playerSelectors(s, args[2])) {
							
							double amount = StringUtils.getDouble(args[3]);
							
							if(API.getPointsmanager().has(player.getName(), amount)) //if player even have these points
								API.getPointsmanager().remove(player.getName(), amount);
							else
								API.getPointsmanager().set(player.getName(), 0);
							//messages
							msg(s, "points.remove.sender", Placeholders.c()
									.add("amount", amount)
									.add("points", API.getPointsmanager().get(player.getName()))
									.addPlayer("target", player)
									.addPlayer("player", s));
							msg(player, "points.remove.target", Placeholders.c()
									.add("amount", amount)
									.add("points", API.getPointsmanager().get(player.getName()))
									.addPlayer("target", player)
									.addPlayer("player", s));
						}
					})
						// cmd points remove <player> <amount> -s
						.argument("-s", (s, structure, args) -> {
							for(Player player : playerSelectors(s, args[2])) {
								
								double amount = StringUtils.getDouble(args[3]);
								if(API.getPointsmanager().has(player.getName(), amount)) //if player even have these points
									API.getPointsmanager().remove(player.getName(), amount);
								else
									API.getPointsmanager().set(player.getName(), 0);

							}
						})
				// cmd points remove <--
				.parent(3)
				// cmd points remove <OFFLINE PLAYER>
				.argument(null, (s, structure, args) -> {
					help(s, "points_remove");
				})
					// cmd points remove <offline player> <AMOUNT>
					.selector(Selector.NUMBER, (s, structure, args) -> {
						String target = args[2];
						if(API.getUser(target)!= null) {
							double amount = StringUtils.getDouble(args[3]);
							
							if(API.getPointsmanager().has(target, amount)) //if player even have these points
								API.getPointsmanager().remove(target, amount);
							else
								API.getPointsmanager().set(target, 0);
							//messages
							msg(s, "points.remove.sender", Placeholders.c()
									.add("amount", amount)
									.add("points", API.getPointsmanager().get(target))
									.add("target", target)
									.addPlayer("player", s));
						}
						
					})
						// cmd points remove <offline player> <amount> -s
						.argument("-s", (s, structure, args) -> {
							String target = args[2];
							if(API.getUser(target)!= null) {
								double amount = StringUtils.getDouble(args[3]);
								
								if(API.getPointsmanager().has(target, amount)) //if player even have these points
									API.getPointsmanager().remove(target, amount);
								else
									API.getPointsmanager().set(target, 0);
							}
							
						})
						
		.parent(4) // cmd points <--
						
			// cmd points ADD
			.argument("add", (s, structure, args) -> {
				help(s, "points_add");
			}).permission(getPermission("points_add"))
				// cmd points add <PLAYER>
				//message if player is not online
				.fallback((s, structure, args) -> {
					offlinePlayer(s, args[1]);
				})
				.selector(Selector.ENTITY_SELECTOR, (s, structure, args) -> {
					help(s, "points_add");
				})
					// cmd points add <player> <AMOUNT>
					.selector(Selector.NUMBER, (s, structure, args) -> {
						for(Player player : playerSelectors(s, args[2])) {
							//getting amount
							double amount = StringUtils.getDouble(args[3]);
							// adding points
							API.getPointsmanager().add(player.getName(), amount);
							
							//messages
							msg(s, "points.add.sender", Placeholders.c()
									.add("amount", amount)
									.add("points", API.getPointsmanager().get(player.getName()))
									.addPlayer("target", player)
									.addPlayer("player", s));
							msg(player, "points.add.target", Placeholders.c()
									.add("amount", amount)
									.add("points", API.getPointsmanager().get(player.getName()))
									.addPlayer("target", player)
									.addPlayer("player", s));
						}
					})
						// cmd points add <player> <amount> -s
						.argument("-s", (s, structure, args) -> {
							for(Player player : playerSelectors(s, args[2])) {
								//getting amount
								double amount = StringUtils.getDouble(args[3]);
								// adding points
								API.getPointsmanager().add(player.getName(), amount);
							}
						})
			// cmd points add <--
			.parent(3)
				// cmd points add <OFFLINE PLAYER>
				.argument(null, (s, structure, args) -> {
					help(s, "points_add");
				})
					// cmd points add <offline player> <AMOUNT>
					.selector(Selector.NUMBER, (s, structure, args) -> {
						// getting target
						String target = args[2];
						if(API.getUser(target)!= null) {
							//getting amount
							double amount = StringUtils.getDouble(args[3]);
							// adding points
							API.getPointsmanager().add(target, amount);
							//message
							msg(s, "points.add.sender", Placeholders.c()
									.add("amount", amount)
									.add("points", API.getPointsmanager().get(target))
									.add("target", target)
									.addPlayer("player", s));
						}
					})
						// cmd points add <offline player> <amount> -s
						.argument("-s", (s, structure, args) -> {
							String target = args[2];
							if(API.getUser(target)!= null) {
								//getting amount
								double amount = StringUtils.getDouble(args[3]);
								// adding points
								API.getPointsmanager().add(target, amount);
							}
						});
		
		
		
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
			MenuLoader.openMenu((Player)s, menu);
			playOpeningSong(s);
		} catch (Exception e) {
			msg(s, "guis.notLoaded", Placeholders.c().add("menu", menu));
		}
	}

}

package me.devtec.amazingfishing.utils;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.construct.FishAction;
import me.devtec.amazingfishing.construct.Junk;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.placeholderapi.PlaceholderAPI;

public class EatFish implements Listener {
	
	@EventHandler
	public void onEat(PlayerItemConsumeEvent e) {
		if(API.isAFItem(e.getItem())) {
			Fish f = API.getFish(e.getItem());
			if(f!=null) {
				if(e.getPlayer().getFoodLevel()<20 && f.isFood()) {
					e.getPlayer().setFoodLevel( (int) Math.max( (e.getPlayer().getFoodLevel()+f.getFood()) , 20));
				}
		        Quests.addProgress(e.getPlayer(), "eat_fish", f.getType().name().toLowerCase()+"."+f.getName(), 1);
		        Statistics.addEating(e.getPlayer(), f);
		        Achievements.check(e.getPlayer(), f);
				for(String s : f.getMessages(FishAction.EAT))
					TheAPI.msg(PlaceholderAPI.setPlaceholders(e.getPlayer(), s
							.replace("%name%", f.getDisplayName())
							.replace("%player%", e.getPlayer().getName())
							.replace("%displayname%", e.getPlayer().getDisplayName())
							), e.getPlayer());
				for(String s : f.getCommands(FishAction.EAT))
					TheAPI.sudoConsole(PlaceholderAPI.setPlaceholders(e.getPlayer(), s
							.replace("%name%", f.getDisplayName())
							.replace("%player%", e.getPlayer().getName())
							.replace("%displayname%", e.getPlayer().getDisplayName())));
			}
			Junk j = API.getJunk(e.getItem());
			if(j!=null) {
				for(String s : j.getMessages(FishAction.EAT))
					TheAPI.msg(PlaceholderAPI.setPlaceholders(e.getPlayer(), s
							.replace("%name%", j.getDisplayName())
							.replace("%player%", e.getPlayer().getName())
							.replace("%displayname%", e.getPlayer().getDisplayName())
							), e.getPlayer());
				for(String s : j.getCommands(FishAction.EAT))
					TheAPI.sudoConsole(PlaceholderAPI.setPlaceholders(e.getPlayer(), s
							.replace("%name%", j.getDisplayName())
							.replace("%player%", e.getPlayer().getName())
							.replace("%displayname%", e.getPlayer().getDisplayName())));
				return;
			}
		}
	}
	
	@EventHandler
	public void onUse(PlayerInteractEvent e) {
		if(e.getItem()==null)return;
		if(e.getPlayer().isSneaking() || e.getAction().name().contains("LEFT"))return;
		if(API.isAFItem(e.getItem())) {
			Fish f = API.getFish(e.getItem());
			if(f!=null) {
				if(!f.isFood()) return;
				if(e.getPlayer().getFoodLevel()>=20) {
					e.setCancelled(true);
					return;
				}
				e.getPlayer().setFoodLevel((int) Math.max((e.getPlayer().getFoodLevel()+f.getFood()), 20));
				
				if(e.getItem().getAmount()==1) {
					e.getPlayer().setItemInHand(null);
					e.getItem().setAmount(0);
				}else {
					e.getItem().setAmount(e.getItem().getAmount()-1);
					e.getPlayer().setItemInHand(e.getItem());
				}
				
				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_GENERIC_EAT , 5, 10);
		        Quests.addProgress(e.getPlayer(), "eat_fish", f.getType().name().toLowerCase()+"."+f.getName(), 1);
		        Statistics.addEating(e.getPlayer(), f);
		        Achievements.check(e.getPlayer(), f);
				for(String s : f.getMessages(FishAction.EAT))
					TheAPI.msg(PlaceholderAPI.setPlaceholders(e.getPlayer(), s
							.replace("%name%", f.getDisplayName())
							.replace("%player%", e.getPlayer().getName())
							.replace("%displayname%", e.getPlayer().getDisplayName())
							), e.getPlayer());
				for(String s : f.getCommands(FishAction.EAT))
					TheAPI.sudoConsole(PlaceholderAPI.setPlaceholders(e.getPlayer(), s
							.replace("%name%", f.getDisplayName())
							.replace("%player%", e.getPlayer().getName())
							.replace("%displayname%", e.getPlayer().getDisplayName())
							));
				return;
			}
			Junk j = API.getJunk(e.getItem());
			if(j!=null) {
				if(!j.isFood())return;
				if(e.getPlayer().getFoodLevel()>=20) {
					e.setCancelled(true);
					return;
				}
				e.getPlayer().setFoodLevel((int) Math.max((e.getPlayer().getFoodLevel()+j.getFood()) , 20));

				if(e.getItem().getAmount()==1) {
					e.getPlayer().setItemInHand(null);
					e.getItem().setAmount(0);
				}else {
					e.getItem().setAmount(e.getItem().getAmount()-1);
					e.getPlayer().setItemInHand(e.getItem());
				}
				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_GENERIC_EAT , 5, 10);
				for(String s : j.getMessages(FishAction.EAT))
					TheAPI.msg(PlaceholderAPI.setPlaceholders(e.getPlayer(), s
							.replace("%name%", j.getDisplayName())
							.replace("%player%", e.getPlayer().getName())
							.replace("%displayname%", e.getPlayer().getDisplayName())
							), e.getPlayer());
				for(String s : j.getCommands(FishAction.EAT))
					TheAPI.sudoConsole(PlaceholderAPI.setPlaceholders(e.getPlayer(), s
							.replace("%name%", j.getDisplayName())
							.replace("%player%", e.getPlayer().getName())
							.replace("%displayname%", e.getPlayer().getDisplayName())));
				return;
			}
		}
	
		
	}
	@EventHandler
	public void onUse(BlockPlaceEvent e) {
		if(API.isAFItem(e.getItemInHand()) && e.getItemInHand()!=null) {
			Fish fish = API.getFish(e.getItemInHand());
			Junk junk = API.getJunk(e.getItemInHand());
			if(fish!=null && fish.isFood() || junk!=null && junk.isFood())
			e.setCancelled(true);
		}
	}
}

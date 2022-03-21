package me.devtec.amazingfishing.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.construct.FishAction;
import me.devtec.amazingfishing.construct.Junk;
import me.devtec.shared.placeholders.PlaceholderAPI;

public class EatFish implements Listener {
	
	@EventHandler
	public void onEat(PlayerItemConsumeEvent e) {
		if(API.isAFItem(e.getItem())) {
			Fish f = API.getFish(e.getItem());
			if(f!=null) {
				eat(f, e.getPlayer());
				return;
			}
			Junk j = API.getJunk(e.getItem());
			if(j!=null) {
				eat(j, e.getPlayer());
				return;
			}
		}
	}
	
	@EventHandler
	public void onUse(PlayerInteractEvent e) {
		if(e.getItem()==null)return;
		if(e.getAction()==Action.LEFT_CLICK_AIR || e.getAction()==Action.LEFT_CLICK_BLOCK)return;
		if(API.isAFItem(e.getItem()) && (e.getItem().getType().isEdible() || e.getItem().getType() == Material.POTION || e.getItem().getType() == Material.MILK_BUCKET)) {
			Fish f = API.getFish(e.getItem());
			if(f!=null) {
				if(!f.isFood()) return;
				e.getPlayer().setFoodLevel((int) Math.max((e.getPlayer().getFoodLevel()+f.getFood()), 20));
				
				if(e.getItem().getAmount()==1) {
					e.getPlayer().setItemInHand(null);
				}else {
					e.getItem().setAmount(e.getItem().getAmount()-1);
					e.getPlayer().setItemInHand(e.getItem());
				}
				
				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_GENERIC_EAT , 5, 10);
				eat(f, e.getPlayer());
				return;
			}
			Junk j = API.getJunk(e.getItem());
			if(j!=null) {
				if(!j.isFood())return;
				e.getPlayer().setFoodLevel((int) Math.max((e.getPlayer().getFoodLevel()+j.getFood()) , 20));

				if(e.getItem().getAmount()==1) {
					e.getPlayer().setItemInHand(null);
				}else {
					e.getItem().setAmount(e.getItem().getAmount()-1);
					e.getPlayer().setItemInHand(e.getItem());
				}
				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_GENERIC_EAT , 5, 10);
				eat(j, e.getPlayer());
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
	
	public void eat(Fish f, Player who) {
		if(who.getFoodLevel()<20 && f.isFood()) {
			who.setFoodLevel( (int) Math.max( (who.getFoodLevel()+f.getFood()) , 20));
		}
        Quests.addProgress(who, "eat_fish", f.getType().name().toLowerCase()+"."+f.getName(), 1);
        Statistics.addEating(who, f);
        Achievements.check(who, f);
		for(String s : f.getMessages(FishAction.EAT))
			Loader.msg(PlaceholderAPI.apply(s
					.replace("%name%", f.getDisplayName())
					.replace("%player%", who.getName())
					.replace("%displayname%", who.getDisplayName()), who.getUniqueId()
					), who);
		for(String s : f.getCommands(FishAction.EAT))
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.apply(s
					.replace("%name%", f.getDisplayName())
					.replace("%player%", who.getName())
					.replace("%displayname%", who.getDisplayName()), who.getUniqueId()));
	}
	
	public void eat(Junk f, Player who) {
		if(who.getFoodLevel()<20 && f.isFood()) {
			who.setFoodLevel( (int) Math.max( (who.getFoodLevel()+f.getFood()) , 20));
		}
		for(String s : f.getMessages(FishAction.EAT))
			Loader.msg(PlaceholderAPI.apply(s
					.replace("%name%", f.getDisplayName())
					.replace("%player%", who.getName())
					.replace("%displayname%", who.getDisplayName()), who.getUniqueId()
					), who);
		for(String s : f.getCommands(FishAction.EAT))
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.apply(s
					.replace("%name%", f.getDisplayName())
					.replace("%player%", who.getName())
					.replace("%displayname%", who.getDisplayName()), who.getUniqueId()));
	}
}

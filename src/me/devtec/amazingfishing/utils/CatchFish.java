package me.devtec.amazingfishing.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.util.Vector;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.construct.Calculator;
import me.devtec.amazingfishing.construct.Enchant;
import me.devtec.amazingfishing.construct.Enchant.FishCatchList;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.construct.FishAction;
import me.devtec.amazingfishing.construct.FishTime;
import me.devtec.amazingfishing.construct.FishWeather;
import me.devtec.amazingfishing.construct.Treasure;
import me.devtec.amazingfishing.utils.tournament.Tournament;
import me.devtec.amazingfishing.utils.tournament.TournamentManager;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.placeholderapi.PlaceholderAPI;
import me.devtec.theapi.utils.PercentageList;
import me.devtec.theapi.utils.StringUtils;
import me.devtec.theapi.utils.datakeeper.Data;

public class CatchFish implements Listener {
	
	@EventHandler
	public void onCatch(PlayerFishEvent e) {
		if(e.getState()==State.CAUGHT_FISH) {
			Item item = (Item)e.getCaught();
			if(API.isFishItem(item.getItemStack())) {
				PercentageList<Fish> ff = generateRandom(e.getPlayer(), e.getHook().getLocation().getBlock().getBiome(),
						e.getHook().getWorld().hasStorm(), e.getHook().getWorld().isThundering(), e.getHook().getWorld().getTime());
				if(ff.isEmpty())return;
				FishCatchList list = new FishCatchList();
				Data data = Utils.getString(Utils.getNBT(Utils.asNMS(e.getPlayer().getItemInHand())));
				for(String s : data.getKeys("enchants"))
					if(Enchant.enchants.containsKey(s))
					Enchant.enchants.get(s).onCatch(e.getPlayer(), data.getInt("enchants."+s), list);
				int am = list.chance*10 > 1 ? (int)list.chance*10 : 1;
				if(am>list.max_amount)am=(int) list.max_amount;
				double money=list.money, points=list.points, exp=list.exp;
				item.remove();
		        double d0 = e.getPlayer().getLocation().getX() - item.getLocation().getX();
		        double d1 = e.getPlayer().getLocation().getY() - item.getLocation().getY()+1;
		        double d2 = e.getPlayer().getLocation().getZ() - item.getLocation().getZ();
				Vector vec = new Vector(d0 * 0.1, d1 * 0.1 + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08, d2 * 0.1);
				try {
				am=random.nextInt(am);
				}catch(Exception er) {}
				if(am<=0)am=1;
				while(am!=0) {
					--am;
					try {
					Fish f = ff.getRandom();
					double weight = 0, length = 0;
					try {
						length=random.nextInt((int)f.getLength())+random.nextDouble();
					}catch(Exception er) {}
					try {
						weight=StringUtils.calculate(f.getCalculator(Calculator.WEIGHT).replace("%weigth%", f.getWeigth()+"")
								.replace("%maxweight%", f.getWeigth()+"")
								.replace("%length%", length+"")
								.replace("%maxlength%", f.getLength()+"").replace("%minlength%", f.getMinLength()+"")
								.replace("%minweight%", f.getMinWeigth()+"")).doubleValue();
					}catch(Exception er) {}
					if(weight>f.getWeigth())weight=f.getWeigth();
					if(length>f.getLength())length=f.getLength();
					if(weight<f.getMinWeigth())weight=f.getMinWeigth();
					if(length<f.getMinLength())length=f.getMinLength();
					item = (Item) e.getCaught().getWorld().dropItem(e.getCaught().getLocation(), f.createItem(weight, length, money, points, exp, e.getPlayer(), e.getHook().getLocation()));
			        item.setVelocity(vec);
			        Statistics.addFish(e.getPlayer(), f);
			        Statistics.addRecord(e.getPlayer(), f, length, weight);
			        Tournament t= TournamentManager.get(e.getPlayer().getWorld());
			        if(t!=null)t.catchFish(e.getPlayer(), f, weight, length);
			        Quests.addProgress(e.getPlayer(), "catch_fish", f.getType().name().toLowerCase()+"."+f.getName());
					for(String s : f.getMessages(FishAction.CATCH))
						TheAPI.msg(s(s,e.getPlayer(), e.getHook().getLocation())
								.replace("%chance%", fs.format(f.getChance()))
								.replace("%weight%", fs.format(weight))
								.replace("%length%", fs.format(length))
								.replace("%name%", s(f.getDisplayName(),e.getPlayer(), e.getHook().getLocation()))
								.replace("%biomes%", sub(f.getBiomes().toString())),e.getPlayer());
					for(String s : f.getCommands(FishAction.CATCH))
						TheAPI.sudoConsole(s(s,e.getPlayer(), e.getHook().getLocation())
								.replace("%chance%", fs.format(f.getChance()))
								.replace("%weight%", fs.format(weight))
								.replace("%length%", fs.format(length))
								.replace("%name%", s(f.getDisplayName(),e.getPlayer(), e.getHook().getLocation()))
								.replace("%biomes%", sub(f.getBiomes().toString())));
					}catch(Exception er) {
						break;
					}
				}
			}else {
				if(generateChance()) {
					Treasure treas = generateTreasure(e.getPlayer(), e.getHook().getLocation().getBlock().getBiome(),
					e.getHook().getWorld().hasStorm(), e.getHook().getWorld().isThundering(), e.getHook().getWorld().getTime());
					if(treas != null) {
						item.remove();
						for(String s : treas.getMessages())
							TheAPI.msg(s(s,e.getPlayer(), e.getHook().getLocation())
									.replace("%chance%", fs.format(treas.getChance()))
									.replace("%name%", s(treas.getDisplayName(),e.getPlayer(), e.getHook().getLocation()))
									.replace("%biomes%", sub(treas.getBiomes().toString())),e.getPlayer());
						for(String s : treas.getCommands())
							TheAPI.sudoConsole(s(s,e.getPlayer(), e.getHook().getLocation())
									.replace("%chance%", fs.format(treas.getChance()))
									.replace("%name%", s(treas.getDisplayName(),e.getPlayer(), e.getHook().getLocation()))
									.replace("%biomes%", sub(treas.getBiomes().toString())));
					}
				}
			}
		}
	}

	private String sub(String s) {
		return s.substring(1,s.length()-1);
	}
	
	private String s(String s, Player p, Location l) {
		return PlaceholderAPI.setPlaceholders(p, s
				.replace("%player%", p.getName())
				.replace("%playername%", p.getDisplayName())
				.replace("%displayname%", p.getDisplayName())
				.replace("%biome%",l.getBlock().getBiome().name())
				.replace("%x%", ""+l.getBlockX())
				.replace("%y%", ""+l.getBlockY())
				.replace("%z%", ""+l.getBlockZ())
				.replace("%world%", l.getWorld().getName()));
	}
	
	private Random random = new Random();
	
	public boolean generateChance() {
		return random.nextInt(5)==4;
	}
	
	public PercentageList<Fish> generateRandom(Player player, Biome biome, boolean hasStorm, boolean thunder, long time) {
		PercentageList<Fish> fish = new PercentageList<>();
		if(time <= 12000) { //day
			for(Fish f : API.getRegisteredFish().values())
				if((f.getPermission()==null || f.getPermission()!=null && player.hasPermission(f.getPermission())) &&
						(f.getBiomes().isEmpty()||f.getBiomes().contains(biome)) &&
						(f.getBlockedBiomes().isEmpty()|| !f.getBlockedBiomes().contains(biome)) &&
						(f.getCatchTime()==FishTime.DAY || f.getCatchTime()==FishTime.EVERY)
						&& (f.getCatchWeather()==FishWeather.EVERY|| hasStorm&&f.getCatchWeather()==FishWeather.RAIN|| thunder&&f.getCatchWeather()==FishWeather.THUNDER|| !hasStorm&&f.getCatchWeather()==FishWeather.SUN))
					fish.add(f, f.getChance());
		}else { //night
			for(Fish f : API.getRegisteredFish().values())
				if((f.getPermission()==null || player.hasPermission(f.getPermission())) && 
						(f.getBiomes().isEmpty()||f.getBiomes().contains(biome)) &&
						(f.getBlockedBiomes().isEmpty()|| !f.getBlockedBiomes().contains(biome)) &&
						(f.getCatchTime()==FishTime.NIGHT || f.getCatchTime()==FishTime.EVERY)
						&& (f.getCatchWeather()==FishWeather.EVERY|| hasStorm&&f.getCatchWeather()==FishWeather.RAIN|| thunder&&f.getCatchWeather()==FishWeather.THUNDER|| !hasStorm&&f.getCatchWeather()==FishWeather.SUN))
					fish.add(f, f.getChance());
		}
		return fish;
	}
	private static DecimalFormat fs = new DecimalFormat("###,###.#", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
	public Treasure generateTreasure(Player player, Biome biome, boolean hasStorm, boolean thunder, long time) {
		PercentageList<Treasure> treas = new PercentageList<>();
		if(time <= 12000) { //day
			for(Treasure f : API.getRegisteredTreasures().values())
				if((f.getPermission()==null || player.hasPermission(f.getPermission())) &&(f.getBiomes().isEmpty()||f.getBiomes().contains(biome)) &&
						(f.getCatchTime()==FishTime.DAY || f.getCatchTime()==FishTime.EVERY)
						&& (f.getCatchWeather()==FishWeather.EVERY|| hasStorm&&f.getCatchWeather()==FishWeather.RAIN|| thunder&&f.getCatchWeather()==FishWeather.THUNDER|| !hasStorm&&f.getCatchWeather()==FishWeather.SUN))
					treas.add(f, f.getChance());
		}else { //night
			for(Treasure f : API.getRegisteredTreasures().values())
				if((f.getPermission()==null || player.hasPermission(f.getPermission())) && (f.getBiomes().isEmpty()||f.getBiomes().contains(biome)) &&
						(f.getCatchTime()==FishTime.NIGHT || f.getCatchTime()==FishTime.EVERY)
						&& (f.getCatchWeather()==FishWeather.EVERY|| hasStorm&&f.getCatchWeather()==FishWeather.RAIN|| thunder&&f.getCatchWeather()==FishWeather.THUNDER|| !hasStorm&&f.getCatchWeather()==FishWeather.SUN))
					treas.add(f, f.getChance());
		}
		return treas.getRandom();
	}
}

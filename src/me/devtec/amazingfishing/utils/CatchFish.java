package me.devtec.amazingfishing.utils;

import java.util.Random;

import org.bukkit.block.Biome;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.util.Vector;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.construct.Enchant;
import me.devtec.amazingfishing.construct.Enchant.FishCatchList;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.construct.FishAction;
import me.devtec.amazingfishing.construct.FishTime;
import me.devtec.amazingfishing.construct.FishWeather;
import me.devtec.amazingfishing.construct.Treasure;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.placeholderapi.PlaceholderAPI;
import me.devtec.theapi.utils.PercentageList;
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
				item.remove();
		          double d0 = e.getPlayer().getLocation().getX() - item.getLocation().getX();
		          double d1 = e.getPlayer().getLocation().getY() - item.getLocation().getY()+1;
		          double d2 = e.getPlayer().getLocation().getZ() - item.getLocation().getZ();
				Vector vec = new Vector(d0 * 0.1D, d1 * 0.1D + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08D, d2 * 0.1D);
				try {
				am=random.nextInt(am);
				}catch(Exception er) {}
				if(am<=0)am=1;
				for(int a = 0; a < am; ++a) {
					Fish f = ff.getRandom();
					double weight = 0, length = 0;
					try {
						weight=random.nextInt((int)f.getWeigth())+random.nextDouble();
					}catch(Exception er) {}
					try {
						length=random.nextInt((int)f.getLength())+random.nextDouble();
					}catch(Exception er) {}
					if(weight>f.getWeigth())weight=f.getWeigth();
					if(length>f.getLength())length=f.getLength();
					if(weight<f.getMinWeigth())weight=f.getMinWeigth();
					if(length<f.getMinLength())length=f.getMinLength();
					item = (Item) e.getCaught().getWorld().dropItem(e.getCaught().getLocation(), f.createItem(weight, length, e.getPlayer(), e.getHook().getLocation()) );
			        item.setVelocity(vec);
			        Statistics.addFish(e.getPlayer(), f.getType());
					for(String s : f.getMessages(FishAction.CATCH))
						TheAPI.msg(PlaceholderAPI.setPlaceholders(e.getPlayer(), s
								.replace("%weight%", (""+weight).replace(",", ".").replaceAll("[^0-9.]+", ","))
								.replace("%length%", (""+length).replace(",", ".").replaceAll("[^0-9.]+", ","))
								.replace("%chance%", (""+f.getChance()).replace(",", ".").replaceAll("[^0-9.]+", ","))
								.replace("%name%", f.getDisplayName())
								.replace("%player%", e.getPlayer().getName())
								.replace("%displayname%", e.getPlayer().getDisplayName())
								.replace("%biomes%", f.getBiomes().toString().replace("[", "").replace("]", "") )
								.replace("%biome%", e.getHook().getLocation().getBlock().getBiome().name())
								.replace("%x%", ""+e.getHook().getLocation().getX())
								.replace("%y%", ""+e.getHook().getLocation().getY())
								.replace("%z%", ""+e.getHook().getLocation().getZ())
								.replace("%world%", e.getHook().getLocation().getWorld().getName())
								), e.getPlayer());
					for(String s : f.getCommands(FishAction.CATCH))
						TheAPI.sudoConsole(PlaceholderAPI.setPlaceholders(e.getPlayer(), s
								.replace("%weight%", (""+weight).replace(",", ".").replaceAll("[^0-9.]+", ","))
								.replace("%length%", (""+length).replace(",", ".").replaceAll("[^0-9.]+", ","))
								.replace("%chance%", (""+f.getChance()).replace(",", ".").replaceAll("[^0-9.]+", ","))
								.replace("%name%", f.getDisplayName())
								.replace("%player%", e.getPlayer().getName())
								.replace("%displayname%", e.getPlayer().getDisplayName())
								.replace("%biomes%", f.getBiomes().toString().replace("[", "").replace("]", "") )
								.replace("%biome%", e.getHook().getLocation().getBlock().getBiome().name())
								.replace("%x%", ""+e.getHook().getLocation().getX())
								.replace("%y%", ""+e.getHook().getLocation().getY())
								.replace("%z%", ""+e.getHook().getLocation().getZ())
								.replace("%world%", e.getHook().getLocation().getWorld().getName())
								));
				}
			}else {
				if(generateChance()) {
					Treasure treas = generateTreasure(e.getPlayer(), e.getHook().getLocation().getBlock().getBiome(),
					e.getHook().getWorld().hasStorm(), e.getHook().getWorld().isThundering(), e.getHook().getWorld().getTime());
					if(treas != null) {
						item.remove();
						for(String s : treas.getMessages())
							TheAPI.msg(PlaceholderAPI.setPlaceholders(e.getPlayer(), s
									.replace("%player%", e.getPlayer().getName())
									.replace("%chance%", (""+treas.getChance()).replace(",", ".").replaceAll("[^0-9.]+", ","))
									.replace("%name%", treas.getName())
									.replace("%displayname%", treas.getDisplayName())
									.replace("%biomes%", treas.getBiomes().toString().replace("[", "").replace("]", "") )
									.replace("%biome%", e.getHook().getLocation().getBlock().getBiome().name())
									.replace("%x%", ""+e.getHook().getLocation().getX())
									.replace("%y%", ""+e.getHook().getLocation().getY())
									.replace("%z%", ""+e.getHook().getLocation().getZ())
									.replace("%world%", e.getHook().getLocation().getWorld().getName())
									), e.getPlayer());
						for(String s : treas.getCommands())
							TheAPI.sudoConsole(PlaceholderAPI.setPlaceholders(e.getPlayer(), s
									.replace("%player%", e.getPlayer().getName())
									.replace("%chance%", (""+treas.getChance()).replace(",", ".").replaceAll("[^0-9.]+", ","))
									.replace("%name%", treas.getName())
									.replace("%displayname%", treas.getDisplayName())
									.replace("%biomes%", treas.getBiomes().toString().replace("[", "").replace("]", "") )
									.replace("%biome%", e.getHook().getLocation().getBlock().getBiome().name())
									.replace("%x%", ""+e.getHook().getLocation().getX())
									.replace("%y%", ""+e.getHook().getLocation().getY())
									.replace("%z%", ""+e.getHook().getLocation().getZ())
									.replace("%world%", e.getHook().getLocation().getWorld().getName())));
					}
				}
			}
		}
	}
	
	private static Random random = new Random();
	
	public static boolean generateChance() {
		return random.nextInt(5)==4;
	}
	
	public static PercentageList<Fish> generateRandom(Player player, Biome biome, boolean hasStorm, boolean thunder, long time) {
		PercentageList<Fish> fish = new PercentageList<>();
		if(time <= 12000) { //day
			for(Fish f : API.getRegisteredFish().values())
				if((f.getPermission()==null || f.getPermission()!=null && player.hasPermission(f.getPermission())) &&(f.getBiomes().isEmpty()||f.getBiomes().contains(biome)) &&
						(f.getCatchTime()==FishTime.DAY || f.getCatchTime()==FishTime.EVERY)
						&& (f.getCatchWeather()==FishWeather.EVERY|| hasStorm&&f.getCatchWeather()==FishWeather.RAIN|| thunder&&f.getCatchWeather()==FishWeather.THUNDER|| !hasStorm&&f.getCatchWeather()==FishWeather.SUN))
					fish.add(f, f.getChance());
		}else { //night
			for(Fish f : API.getRegisteredFish().values())
				if((f.getPermission()==null || player.hasPermission(f.getPermission())) && (f.getBiomes().isEmpty()||f.getBiomes().contains(biome)) &&
						(f.getCatchTime()==FishTime.NIGHT || f.getCatchTime()==FishTime.EVERY)
						&& (f.getCatchWeather()==FishWeather.EVERY|| hasStorm&&f.getCatchWeather()==FishWeather.RAIN|| thunder&&f.getCatchWeather()==FishWeather.THUNDER|| !hasStorm&&f.getCatchWeather()==FishWeather.SUN))
					fish.add(f, f.getChance());
		}
		return fish;
	}

	public static Treasure generateTreasure(Player player, Biome biome, boolean hasStorm, boolean thunder, long time) {
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

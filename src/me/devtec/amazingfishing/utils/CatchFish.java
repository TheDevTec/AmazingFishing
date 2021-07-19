package me.devtec.amazingfishing.utils;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Calculator;
import me.devtec.amazingfishing.construct.Enchant;
import me.devtec.amazingfishing.construct.Enchant.FishCatchList;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.construct.FishAction;
import me.devtec.amazingfishing.construct.FishTime;
import me.devtec.amazingfishing.construct.FishWeather;
import me.devtec.amazingfishing.construct.Junk;
import me.devtec.amazingfishing.construct.Treasure;
import me.devtec.amazingfishing.utils.tournament.Tournament;
import me.devtec.amazingfishing.utils.tournament.TournamentManager;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.placeholderapi.PlaceholderAPI;
import me.devtec.theapi.utils.PercentageList;
import me.devtec.theapi.utils.StringUtils;
import me.devtec.theapi.utils.datakeeper.Data;
import me.devtec.theapi.utils.nms.nbt.NBTEdit;
import me.devtec.theapi.utils.reflections.Ref;

public class CatchFish implements Listener {

	private static Method acc = Ref.method(PlayerFishEvent.class,"getHook");
	@EventHandler
	public void onCatch(PlayerFishEvent e) {
		if(e.getState()==State.CAUGHT_FISH) {
			if(!Loader.config.getBoolean("Options.AFK.Enabled")||!AFKSystem.isAFK(e.getPlayer())||!Loader.config.getBoolean("Options.AFK.DisallowFishing")) {
			Item item = (Item)e.getCaught();
			Object hook = Ref.invoke(e, acc);
			Location loc = hook instanceof org.bukkit.entity.Fish? ((org.bukkit.entity.Fish)hook).getLocation() : ((FishHook)hook).getLocation();
			if(API.isFishItem(item.getItemStack())) {
				item.remove();
				PercentageList<Fish> ff = generateRandom(e.getPlayer(), loc.getBlock().getBiome(),
						loc.getWorld().hasStorm(), loc.getWorld().isThundering(), loc.getWorld().getTime());
				if(ff.isEmpty()) {
					Bukkit.broadcastMessage("no fish");
					return;
				}
				FishCatchList list = new FishCatchList();
				NBTEdit edit = new NBTEdit(e.getPlayer().getItemInHand());
				Data data = new Data();
				if(edit.getString("af_data")!=null)
				data.reload(edit.getString("af_data"));
				for(String s : data.getKeys("enchants"))
					if(Enchant.enchants.containsKey(s))
					Enchant.enchants.get(s).onCatch(e.getPlayer(), data.getInt("enchants."+s), list);
				int am = list.chance*10 > 1 ? (int)list.chance*10 : 1;
				if(am>list.max_amount)am=(int) list.max_amount;
				double money=list.money, points=list.points, exp=list.exp;
				
		       /* double d0 = e.getPlayer().getLocation().getX() - item.getLocation().getX();
		        double d1 = e.getPlayer().getLocation().getY() - item.getLocation().getY()+1;
		        double d2 = e.getPlayer().getLocation().getZ() - item.getLocation().getZ();
				Vector vec = new Vector(d0 * 0.1, d1 * 0.1 + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08, d2 * 0.1);*/
				try {
				am=random.nextInt(am);
				}catch(Exception er) {}
				if(am<=0)am=1;
				while(am!=0) {
					--am;
					try {
					Fish f = ff.getRandom();
					double weight = 0;
					double length = 0;
					try {
						length=random.nextInt((int)f.getLength())+random.nextDouble();
					}catch(Exception er) {}
					try {
						weight = (double) StringUtils.calculate(f.getCalculator(Calculator.WEIGHT).replace("%weight%", f.getWeight()+"")
								.replace("%maxweight%", f.getWeight()+"")
								.replace("%length%", length+"")
								.replace("%maxlength%", f.getLength()+"").replace("%minlength%", f.getMinLength()+"")
								.replace("%minweight%", f.getMinWeight()+""));
					}catch(Exception er) {}
					if(weight>f.getWeight())weight=f.getWeight();
					if(length>f.getLength())length=f.getLength();
					if(weight<f.getMinWeight())weight=f.getMinWeight();
					if(length<f.getMinLength())length=f.getMinLength();

					giveItem(item, f.createItem(weight, length, money, points, exp, e.getPlayer(), loc), e.getPlayer(), loc);
					//item = (Item) e.getCaught().getWorld().dropItem(e.getCaught().getLocation(), f.createItem(weight, length, money, points, exp, e.getPlayer(), loc));
			        //item.setVelocity(vec);
					
			        Statistics.addFish(e.getPlayer(), f);
			        Statistics.addRecord(e.getPlayer(), f, length, weight);
			        Tournament t= TournamentManager.get(e.getPlayer().getWorld());
			        if(t!=null)t.catchFish(e.getPlayer(), f, weight, length);
			        Quests.addProgress(e.getPlayer(), "catch_fish", f.getType().name().toLowerCase()+"."+f.getName(), 1);
			        Achievements.check(e.getPlayer(), f);
					for(String s : f.getMessages(FishAction.CATCH))
						TheAPI.msg(s(s,e.getPlayer(), loc)
								.replace("%chance%", fs.format(f.getChance()))
								.replace("%weight%", fs.format(weight))
								.replace("%length%", fs.format(length))
								.replace("%name%", s(f.getDisplayName(),e.getPlayer(), loc))
								.replace("%biomes%", sub(f.getBiomes().toString())),e.getPlayer());
					for(String s : f.getCommands(FishAction.CATCH))
						TheAPI.sudoConsole(s(s,e.getPlayer(), loc)
								.replace("%chance%", fs.format(f.getChance()))
								.replace("%weight%", fs.format(weight))
								.replace("%length%", fs.format(length))
								.replace("%name%", s(f.getDisplayName(),e.getPlayer(), loc))
								.replace("%biomes%", sub(f.getBiomes().toString())));
					}catch(Exception er) {
						break;
					}
				}
			}else {
				if(generateChance()) {
					Treasure treas = generateTreasure(e.getPlayer(), loc.getBlock().getBiome(),
					loc.getWorld().hasStorm(), loc.getWorld().isThundering(), loc.getWorld().getTime());
					if(treas != null) {
						item.remove();
						Statistics.addTreasure(e.getPlayer(), treas);
						 Achievements.check(e.getPlayer(), treas);
						for(String s : treas.getMessages())
							TheAPI.msg(s(s,e.getPlayer(), loc)
									.replace("%chance%", fs.format(treas.getChance()))
									.replace("%name%", s(treas.getDisplayName(),e.getPlayer(), loc))
									.replace("%biomes%", sub(treas.getBiomes().toString())),e.getPlayer());
						for(String s : treas.getCommands())
							TheAPI.sudoConsole(s(s,e.getPlayer(), loc)
									.replace("%chance%", fs.format(treas.getChance()))
									.replace("%name%", s(treas.getDisplayName(),e.getPlayer(), loc))
									.replace("%biomes%", sub(treas.getBiomes().toString())));
					}
				} else {
					Junk junk = generateJunk(e.getPlayer(), loc.getBlock().getBiome(),
					loc.getWorld().hasStorm(), loc.getWorld().isThundering(), loc.getWorld().getTime());
					if(junk!=null) {
						item.remove();
						
				        /*double d0 = e.getPlayer().getLocation().getX() - item.getLocation().getX();
				        double d1 = e.getPlayer().getLocation().getY() - item.getLocation().getY()+1;
				        double d2 = e.getPlayer().getLocation().getZ() - item.getLocation().getZ();
						Vector vec = new Vector(d0 * 0.1, d1 * 0.1 + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08, d2 * 0.1);*/
						
						double weight = -1;
						double length = -1;
						if(junk.hasLength()) {
							try {
								length=random.nextInt((int)junk.getLength())+random.nextDouble();
							}catch(Exception er) {}
						}
						if(junk.hasWeight() && length!=-1) {
							try {
								weight = (double) StringUtils.calculate(junk.getCalculator(Calculator.WEIGHT).replace("%weight%", junk.getWeight()+"")
										.replace("%maxweight%", junk.getWeight()+"")
										.replace("%length%", length+"")
										.replace("%maxlength%", junk.getLength()+"").replace("%minlength%", junk.getMinLength()+"")
										.replace("%minweight%", junk.getMinWeight()+""));
							}catch(Exception er) {}
						}
						
						if(junk.hasWeight() && weight>junk.getWeight())weight=junk.getWeight();
						if(junk.hasLength() &&length>junk.getLength())length=junk.getLength();
						if(junk.hasWeight() &&weight<junk.getMinWeight())weight=junk.getMinWeight();
						if(junk.hasLength() &&length<junk.getMinLength())length=junk.getMinLength();
						
						ItemStack i = junk.create(weight, length, e.getPlayer(), loc);
						if(i!=null) {
							giveItem(item, i, e.getPlayer(), loc);
							/*item = (Item) e.getCaught().getWorld().dropItem(e.getCaught().getLocation(), i, loc));
					        item.setVelocity(vec);*/
						}
						for(String s : junk.getMessages(FishAction.CATCH))
							TheAPI.msg(s(s,e.getPlayer(), loc)
									.replace("%chance%", fs.format(junk.getChance()))
									.replace("%weight%", fs.format(weight))
									.replace("%length%", fs.format(length))
									.replace("%name%", s(junk.getDisplayName(),e.getPlayer(), loc))
									.replace("%biomes%", sub(junk.getBiomes().toString())),e.getPlayer());
						for(String s : junk.getCommands(FishAction.CATCH))
							TheAPI.sudoConsole(s(s,e.getPlayer(), loc)
									.replace("%chance%", fs.format(junk.getChance()))
									.replace("%weight%", fs.format(weight))
									.replace("%length%", fs.format(length))
									.replace("%name%", s(junk.getDisplayName(),e.getPlayer(), loc))
									.replace("%biomes%", sub(junk.getBiomes().toString())) );

					}
				}
			}
		}else e.setCancelled(true);
		}
	}
	
	public static void giveItem(Item item, ItemStack itemStack, Player p, Location itemloc) {
        double d0 = p.getLocation().getX() - itemloc.getX();
        double d1 = p.getLocation().getY() - itemloc.getY()+1;
        double d2 = p.getLocation().getZ() - itemloc.getZ();
		Vector vec = new Vector(d0 * 0.1, d1 * 0.1 + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08, d2 * 0.1);
		
		item = (Item) itemloc.getWorld().dropItem(itemloc, itemStack);
		item.setVelocity(vec);
        
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
		if(fish==null || fish.isEmpty())
			return null;
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
		if(treas==null || treas.isEmpty())
			return null;
		return treas.getRandom();
	}
	public Junk generateJunk(Player player, Biome biome, boolean hasStorm, boolean thunder, long time) {
		PercentageList<Junk> junk = new PercentageList<>();
		if(time <= 12000) { //day
			for(Junk f : API.getRegisteredJunk().values())
				if((f.getPermission()==null || f.getPermission()!=null && player.hasPermission(f.getPermission())) &&
						(f.getBiomes().isEmpty()||f.getBiomes().contains(biome)) &&
						(f.getBlockedBiomes().isEmpty()|| !f.getBlockedBiomes().contains(biome)) &&
						(f.getCatchTime()==FishTime.DAY || f.getCatchTime()==FishTime.EVERY)
						&& (f.getCatchWeather()==FishWeather.EVERY|| hasStorm&&f.getCatchWeather()==FishWeather.RAIN|| thunder&&f.getCatchWeather()==FishWeather.THUNDER|| !hasStorm&&f.getCatchWeather()==FishWeather.SUN))
					junk.add(f, f.getChance());
		}else { //night
			for(Junk f : API.getRegisteredJunk().values())
				if((f.getPermission()==null || player.hasPermission(f.getPermission())) && 
						(f.getBiomes().isEmpty()||f.getBiomes().contains(biome)) &&
						(f.getBlockedBiomes().isEmpty()|| !f.getBlockedBiomes().contains(biome)) &&
						(f.getCatchTime()==FishTime.NIGHT || f.getCatchTime()==FishTime.EVERY)
						&& (f.getCatchWeather()==FishWeather.EVERY|| hasStorm&&f.getCatchWeather()==FishWeather.RAIN|| thunder&&f.getCatchWeather()==FishWeather.THUNDER|| !hasStorm&&f.getCatchWeather()==FishWeather.SUN))
					junk.add(f, f.getChance());
		}
		if(junk==null || junk.isEmpty())
			return null;
		return junk.getRandom();
	}
}

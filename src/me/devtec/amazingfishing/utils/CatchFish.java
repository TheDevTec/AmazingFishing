package me.devtec.amazingfishing.utils;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
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
import me.devtec.amazingfishing.construct.Junk;
import me.devtec.amazingfishing.construct.Treasure;
import me.devtec.amazingfishing.utils.tournament.Tournament;
import me.devtec.amazingfishing.utils.tournament.TournamentManager;
import me.devtec.shared.Ref;
import me.devtec.shared.dataholder.Config;
import me.devtec.shared.placeholders.PlaceholderAPI;
import me.devtec.shared.utility.StringUtils;
import me.devtec.shared.utils.PercentageList;
import me.devtec.theapi.bukkit.nms.NBTEdit;

public class CatchFish implements Listener {

	private static Method acc = Ref.method(PlayerFishEvent.class,"getHook");
	private Random random = new Random();
	private DecimalFormat fs = new DecimalFormat("###,###.#", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
	
	@EventHandler
	public void onCatchRemake(PlayerFishEvent e) {
		if(e.getState()==State.CAUGHT_FISH) {
			if(!Loader.config.getBoolean("Options.AFK.Enabled")||!AFKSystem.isAFK(e.getPlayer())||!Loader.config.getBoolean("Options.AFK.DisallowFishing")) {
				Item item = (Item)e.getCaught();
				Object hook = Ref.invoke(e, acc);
				Location loc = hook instanceof org.bukkit.entity.Fish? ((org.bukkit.entity.Fish)hook).getLocation() : ((FishHook)hook).getLocation();
				
				Fishing type = generate();
				switch(type) {
				case FISH:{
					PercentageList<Fish> ff = generateRandom(e.getPlayer(), loc.getBlock().getBiome(), loc.getWorld().hasStorm(), loc.getWorld().isThundering(), loc.getWorld().getTime());
					
					if(!ff.isEmpty()) {
						item.remove();
						
						FishCatchList list = new FishCatchList();
						NBTEdit edit = new NBTEdit(e.getPlayer().getItemInHand());
						Config data = new Config();
						if(edit.hasKey("af_data"))
							data.reload(edit.getString("af_data"));
						for(String s : data.getKeys("enchants")) {
							Enchant ench = Enchant.enchants.get(s);
							if(ench!=null)
								ench.onCatch(e.getPlayer(), data.getInt("enchants."+s), list);
						}
						
						int am = list.chance*10 > 1 ? (int)list.chance*10 : 1;
						if(am>list.max_amount)am=(int) list.max_amount;
						double money=list.money, points=list.points, exp=list.exp;
						
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
							
							if(length>f.getLength())length=f.getLength();
							if(length<f.getMinLength())length=f.getMinLength();
							
							try {
								weight = (double) StringUtils.calculate(f.getCalculator(Calculator.WEIGHT).replace("%weight%", f.getWeight()+"")
										.replace("%maxweight%", f.getWeight()+"")
										.replace("%length%", length+"")
										.replace("%maxlength%", f.getLength()+"").replace("%minlength%", f.getMinLength()+"")
										.replace("%minweight%", f.getMinWeight()+""));
							}catch(Exception er) {}
							if(weight>f.getWeight())weight=f.getWeight();
							if(weight<f.getMinWeight())weight=f.getMinWeight();
		
							giveItem(item, f.createItem(weight, length, money, points, exp, e.getPlayer(), loc), e.getPlayer(), loc);
							
					        Statistics.addFish(e.getPlayer(), f);
					        Statistics.addRecord(e.getPlayer(), f, length, weight);
					        Tournament t = TournamentManager.get(e.getPlayer().getWorld());
					        if(t!=null)t.catchFish(e.getPlayer(), f, weight, length);
					        Quests.addProgress(e.getPlayer(), "catch_fish", f.getType().name().toLowerCase()+"."+f.getName(), 1);
					        Achievements.check(e.getPlayer(), f);
							for(String s : f.getMessages(FishAction.CATCH))
								Loader.msg(s(s,e.getPlayer(), loc)
										.replace("%chance%", fs.format(f.getChance()))
										.replace("%weight%", fs.format(weight))
										.replace("%length%", fs.format(length))
										.replace("%name%", s(f.getDisplayName(),e.getPlayer(), loc))
										.replace("%biomes%", sub(f.getBiomes().toString())),e.getPlayer());
							for(String s : f.getCommands(FishAction.CATCH))
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s(s,e.getPlayer(), loc)
										.replace("%chance%", fs.format(f.getChance()))
										.replace("%weight%", fs.format(weight))
										.replace("%length%", fs.format(length))
										.replace("%name%", s(f.getDisplayName(),e.getPlayer(), loc))
										.replace("%biomes%", sub(f.getBiomes().toString())));
							}catch(Exception er) {
								break;
							}
						}
					}else type=Fishing.JUNK;
					break;
					}
					case TREASURE:{
						Treasure treas = generateTreasure(e.getPlayer(), loc.getBlock().getBiome(),
						loc.getWorld().hasStorm(), loc.getWorld().isThundering(), loc.getWorld().getTime());
						if(treas != null) {
							item.remove();
							Statistics.addTreasure(e.getPlayer(), treas);
							 Achievements.check(e.getPlayer(), treas);
							for(String s : treas.getMessages())
								Loader.msg(s(s,e.getPlayer(), loc)
										.replace("%chance%", fs.format(treas.getChance()))
										.replace("%name%", s(treas.getDisplayName(),e.getPlayer(), loc))
										.replace("%biomes%", sub(treas.getBiomes().toString())),e.getPlayer());
							for(String s : treas.getCommands())
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s(s,e.getPlayer(), loc)
										.replace("%chance%", fs.format(treas.getChance()))
										.replace("%name%", s(treas.getDisplayName(),e.getPlayer(), loc))
										.replace("%biomes%", sub(treas.getBiomes().toString())));
						}else type= Fishing.JUNK;
					}
					default:
						break;
				}
				if(type==Fishing.JUNK){
					Junk junk = generateJunk(e.getPlayer(), loc.getBlock().getBiome(),
					loc.getWorld().hasStorm(), loc.getWorld().isThundering(), loc.getWorld().getTime());
					if(junk!=null) {
						item.remove();
						
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
						if(i!=null)
							giveItem(item, i, e.getPlayer(), loc);
						for(String s : junk.getMessages(FishAction.CATCH))
							Loader.msg(s(s,e.getPlayer(), loc)
									.replace("%chance%", fs.format(junk.getChance()))
									.replace("%weight%", fs.format(weight))
									.replace("%length%", fs.format(length))
									.replace("%name%", s(junk.getDisplayName(),e.getPlayer(), loc))
									.replace("%biomes%", sub(junk.getBiomes().toString())),e.getPlayer());
						for(String s : junk.getCommands(FishAction.CATCH))
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s(s,e.getPlayer(), loc)
									.replace("%chance%", fs.format(junk.getChance()))
									.replace("%weight%", fs.format(weight))
									.replace("%length%", fs.format(length))
									.replace("%name%", s(junk.getDisplayName(),e.getPlayer(), loc))
									.replace("%biomes%", sub(junk.getBiomes().toString())) );
					}
				}
			}else e.setCancelled(true);
		}
	}
	
	public static void giveItem(Entity item, ItemStack itemStack, Player p, Location itemloc) {
        double d0 = p.getLocation().getX() - itemloc.getX();
        double d1 = p.getLocation().getY() - itemloc.getY()+1;
        double d2 = p.getLocation().getZ() - itemloc.getZ();
		Vector vec = new Vector(d0 * 0.1, d1 * 0.1 + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08, d2 * 0.1);
		
		item = itemloc.getWorld().dropItem(itemloc, itemStack);
		item.setVelocity(vec);
	}
	
	private String sub(String s) {
		return s.substring(1,s.length()-1);
	}
	
	private String s(String s, Player p, Location l) {
		return PlaceholderAPI.apply(s
				.replace("%player%", p.getName())
				.replace("%playername%", p.getDisplayName())
				.replace("%displayname%", p.getDisplayName())
				.replace("%biome%",l.getBlock().getBiome().name())
				.replace("%x%", ""+l.getBlockX())
				.replace("%y%", ""+l.getBlockY())
				.replace("%z%", ""+l.getBlockZ())
				.replace("%world%", l.getWorld().getName()), p.getUniqueId());
	}
	
	public PercentageList<Fish> generateRandom(Player player, Biome biome, boolean hasStorm, boolean thunder, long time) {
		PercentageList<Fish> fish = new PercentageList<>();
		for(Fish f : API.getRegisteredFish().values())
			if(f.isAllowedToCatch(player,biome,hasStorm,thunder,time))
				fish.add(f, f.getChance() <= 0 ? 0.1 : f.getChance());
		return fish;
	}
	
	public Treasure generateTreasure(Player player, Biome biome, boolean hasStorm, boolean thunder, long time) {
		PercentageList<Treasure> treas = new PercentageList<>();
		for(Treasure f : API.getRegisteredTreasures().values())
			if(f.isAllowedToCatch(player,biome,hasStorm,thunder,time))
				treas.add(f, f.getChance() <= 0 ? 0.1 : f.getChance());
		return treas.getRandom();
	}
	
	public Junk generateJunk(Player player, Biome biome, boolean hasStorm, boolean thunder, long time) {
		PercentageList<Junk> junk = new PercentageList<>();
		for(Junk f : API.getRegisteredJunk().values())
			if(f.isAllowedToCatch(player,biome,hasStorm,thunder,time))
				junk.add(f, f.getChance() <= 0 ? 0.1 : f.getChance());
		return junk.getRandom();
	}
	
	public static boolean isEnabled(Fishing type) {
		return Loader.config.getBoolean("Options.Fishing."+type.configName()+".Enabled");
	}
	
	public static Fishing generate() {
		PercentageList<Fishing> gen = new PercentageList<>();
		for(Fishing type : Fishing.values()) {
			if(isEnabled(type))
				gen.add(type, Loader.config.getDouble("Options.Fishing."+type.configName()+".Chance"));
		}
		return gen.getRandom();
	}
	
	public enum Fishing {
		FISH("Fish"), TREASURE("Treasure"), JUNK("Junk");
		
		String d;
		Fishing(String s){
			d=s;
		}
		
		public String configName() {
			return d;
		}
	}
}

package AmazingFishing;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import AmazingFishing.Quests.Actions;
import me.DevTec.AmazingFishing.Loader;
import me.devtec.theapi.apis.ItemCreatorAPI;
import me.devtec.theapi.economyapi.EconomyAPI;
import me.devtec.theapi.utils.PercentageList;
import me.devtec.theapi.utils.StringUtils;

public class ByBiome {
	public static enum biomes{
		BADLANDS,
		BEACH,
		COLD_OCEAN,
		DEEP_COLD_OCEAN,
		DEEP_FROZEN_OCEAN,
		DEEP_LUKEWARM_OCEAN,
		DEEP_WARM_OCEAN,
		DEEP_OCEAN,
		DESERT,
		SAVANNA,
		END,
		NETHER,
		FOREST,
		WOODED_HILLS,
		STONE_SHORE,
		FROZEN_OCEAN,
		TUNDRA,
		ICE_SPIKES,
		JUNGLE,
		MOUNTAINS,
		TAIGA,
		WARM_OCEAN,
		PLAINS,
		RIVER,
		MUSHROOM,
		SWAMP
	}
	public static String getBiome(String name) {
		String a = null;
		for(biomes s:biomes.values()) {
			String f = Color.c(Loader.s("Words.Biomes."+s.name()+".Name"));
			if(f.equals(Color.c(name)))a=s.name();
		}
		return a;
	}
	
	public static void addBiome(String fish, String type, biomes biom) {
		if(Loader.c.exists("Types."+type+"."+fish+".Biomes")) {
			List<String> list = Loader.c.getStringList("Types."+type+"."+fish+".Biomes");
			if(!list.contains(biom.name())) {
			list.add(biom.name());
			}else
				list.remove(biom.name());
			Loader.c.set("Types."+type+"."+fish+".Biomes",list);
			Loader.c.save();
		}else {
		List<String> list = new ArrayList<String>();
		list.add(biom.name());
		Loader.c.set("Types."+type+"."+fish+".Biomes",list);
		Loader.c.save();
		}
	}
	
	private static String fish(String a, String type){
		PercentageList<String> fish = new PercentageList<>();
		for(String d:Loader.c.getKeys("Types."+type)) {
			if(Loader.c.getStringList("Types."+type+"."+d+".Biomes").isEmpty()) {
				fish.add(d, Loader.c.getDouble("Types."+type+"."+d+".Chance"));
			}else
			for(String s:Loader.c.getStringList("Types."+type+"."+d+".Biomes"))
			if(s.toLowerCase().contains(a.toLowerCase())) {
				fish.add(d, Loader.c.getDouble("Types."+type+"."+d+".Chance"));
			}else {
				fish.add(d, Loader.c.getDouble("Types."+type+"."+d+".Chance"));
			}
		}
		return fish.getRandom();
	}
	
	public static String getTran(biomes b) {
		if(b!=null) {
		return Color.c(Loader.s("Words.Biomes."+b.name()+".Name"));
		}
		return Color.c(Loader.s("Words.Biomes.ALL.Name"));
	}
	
	public static void addEarn(CEnch c,Player p, String type, String fish, double length) {
		double money = Loader.c.getDouble("Types."+type+"."+fish+".Money");
		double points = Loader.c.getDouble("Types."+type+"."+fish+".Points");
		//Bukkit.broadcastMessage(points+"");
		double exp = Loader.c.getInt("Types."+type+"."+fish+".Exp");
		if(Loader.c.getBoolean("Options.EarnFromLength")) {
			money=money%length;
			points = points%length;
			exp = exp%length;
		}
		double moneybonus = c.getBonus("Money",p);
		double pointbonus = c.getBonus("Points",p);
		double expbonus = c.getBonus("Exp",p);
		
		if(moneybonus!=0.0)
		money = money+(money%moneybonus);
		if(pointbonus!=0.0)
		points = points+(points%pointbonus);
		
		if(expbonus!=0)
		exp = exp+(exp%expbonus);
		//if(money==0.0||points==0.0||exp==0.0) addEarn(c, p, type, fish, length);
		if(!Loader.c.getBoolean("Options.DisableMoneyFromCaught"))
		EconomyAPI.depositPlayer(p.getName(), money);
		p.giveExp((int)exp);
		Points.give(p.getName(), points);
	}
	
	public static void generateFish(List<CEnch> enchs, Player p, Material t, Location hook) {
		String type = null;
		if(t==null)t=Material.COD;
		if(t==Material.COD)type="Cod";
		if(t==Material.PUFFERFISH)type="PufferFish";
		if(t==Material.TROPICAL_FISH)type="TropicalFish";
		if(t==Material.SALMON)type="Salmon";
		String fish = fish(hook.getBlock().getBiome().name(),type);
		if(fish==null) {
			ItemCreatorAPI i = new ItemCreatorAPI(t);
			double length = Generators.length(type, fish);
			double weight = Generators.weight(length);
			if(!Loader.c.getBoolean("Options.UseDoubles.Length"))
				length=(int)length;
			if(!Loader.c.getBoolean("Options.UseDoubles.Weight"))
				weight=(int)weight;
			 
			if(Loader.c.getBoolean("Options.Fish.EnchantsOnCustomFish")==true&&Loader.c.exists("Types."+type+"."+fish+".Enchants")) {
				for(String s:Loader.c.getStringList("Types."+type+"."+fish+".Enchants")) {
					String[] ss = s.split(":");
					if(ss[0]==null || ss[1]==null || ss[0].isEmpty() || ss[1].isEmpty()) continue;
					i.addEnchantment(ss[0], StringUtils.getInt(ss[1]));
				}
				/*if(Loader.c.getBoolean("Options.Fish.HideEnchants")==true){
					Bukkit.broadcastMessage("1");
					i.getItemFlags().add(ItemFlag.HIDE_ENCHANTS);
					
				i.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
				}*/
			}
			
			if(Loader.c.exists("Format.FishDescription")) {
				List<String> lore=new ArrayList<String>();
				
				List<String> biomes = new ArrayList<String>();
				if(Loader.c.exists("Types."+type+"."+fish+".Biomes"))
					
					for(String g:Loader.c.getStringList("Types."+type+"."+fish+".Biomes"))
						biomes.add(getTran(ByBiome.biomes.valueOf(g)));
				else
					biomes.add(getTran(null));
				String b = StringUtils.join(biomes, ", ");
				
				if(Loader.c.getBoolean("Options.UseCustomFishDescription-ifItIsPossible")==true&&Loader.c.exists("Types."+type+"."+fish+".Lore")) {
					for(String s:Loader.c.getStringList("Types."+type+"."+fish+".Lore")) {
						lore.add(Color.c(s
								.replace("%chance%", ""+(Loader.c.getInt("Types."+type+"."+fish+".Chance")>0 ? Loader.c.getInt("Types."+type+"."+fish+".Chance") : 1))
								.replace("%fish_biomes%", b)
								.replace("%biomes%", b)
								.replace("%fish_weight%", (new DecimalFormat("#,##0.00").format(weight)).replaceAll("\\.00", ""))
								.replace("%weight%", (new DecimalFormat("#,##0.00").format(weight)).replaceAll("\\.00", ""))
								.replace("%fish_length%", (new DecimalFormat("#,##0.00").format(length)).replaceAll("\\.00", ""))
								.replace("%length%", (new DecimalFormat("#,##0.00").format(length)).replaceAll("\\.00", ""))
								.replace("%fish_name%", "Uknown")
								.replace("%fish%",  "Uknown")
								.replace("%time%", new SimpleDateFormat("HH:mm:ss").format(new Date()))
								.replace("%date%", new SimpleDateFormat("dd.MM.yyyy").format(new Date()))
								.replace("%fisherman%", p.getName())
								.replace("%fisher%", p.getName())));
					}
				}else {
					for(String s:Loader.c.getStringList("Format.FishDescription")) {
						lore.add(Color.c(s
								.replace("%chance%", ""+(Loader.c.getInt("Types."+type+"."+fish+".Chance")>0 ? Loader.c.getInt("Types."+type+"."+fish+".Chance") : 1))
								.replace("%fish_biomes%", b)
								.replace("%biomes%", b)
								.replace("%fish_weight%", (new DecimalFormat("#,##0.00").format(weight)).replaceAll("\\.00", ""))
								.replace("%weight%", (new DecimalFormat("#,##0.00").format(weight)).replaceAll("\\.00", ""))
								.replace("%fish_length%", (new DecimalFormat("#,##0.00").format(length)).replaceAll("\\.00", ""))
								.replace("%length%", (new DecimalFormat("#,##0.00").format(length)).replaceAll("\\.00", ""))
								.replace("%fish_name%", "Uknown")
								.replace("%fish%",  "Uknown")
								.replace("%time%", new SimpleDateFormat("HH:mm:ss").format(new Date()))
								.replace("%date%", new SimpleDateFormat("dd.MM.yyyy").format(new Date()))
								.replace("%fisherman%", p.getName())
								.replace("%fisher%", p.getName())));
					}
				}
			i.setLore(lore);
			}
			ItemStack bukkitstack = Tag.add(length, weight, type, fish, i);
			
				bag.addFishToBagOrInv(p,bukkitstack);
			Loader.msgCmd(Loader.s("Prefix")+Loader.s("Caught").replace("%cm%", (new DecimalFormat("#,##0.00").format(length))
					.replaceAll("\\.00", "")).replace("%length%", (new DecimalFormat("#,##0.00").format(length)).replaceAll("\\.00", ""))
					.replace("%weight%",(new DecimalFormat("#,##0.00").format(weight)).replaceAll("\\.00", "")).replace("%fish%",  "Uknown"), p);
			return;
		}
			ItemCreatorAPI i = new ItemCreatorAPI(t);
			double length = Generators.length(type, fish);
			double weight = Generators.weight(length);
			if(!Loader.c.getBoolean("Options.UseDoubles.Length"))
				length=(int)length;
			if(!Loader.c.getBoolean("Options.UseDoubles.Weight"))
				weight=(int)weight;
			
			if(Loader.c.getBoolean("Options.Fish.EnchantsOnCustomFish")==true&&Loader.c.exists("Types."+type+"."+fish+".Enchants")) {
				for(String s:Loader.c.getStringList("Types."+type+"."+fish+".Enchants")) {
					String[] ss = s.split(":");
					if(ss[0]==null || ss[1]==null || ss[0].isEmpty() || ss[1].isEmpty()) continue;
					i.addEnchantment(ss[0], StringUtils.getInt(ss[1]));
				}
				/*if(Loader.c.getBoolean("Options.Fish.HideEnchants")==true) {
					Bukkit.broadcastMessage("1");

					i.getItemFlags().add(ItemFlag.HIDE_ENCHANTS);
					i.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
				}*/
			}
			String name = fish;
			if(Loader.c.exists("Types."+type+"."+fish+".Name"))
				name=Color.c(Loader.c.getString("Types."+type+"."+fish+".Name"));
			i.setDisplayName(name);
			if(Loader.c.exists("Types."+type+"."+fish+".ModelData")) {
			i.setCustomModelData(Loader.c.getInt("Types."+type+"."+fish+".ModelData"));
			}
			if(Loader.c.exists("Format.FishDescription")) {
				List<String> lore=new ArrayList<String>();
				
				List<String> biomes = new ArrayList<String>();
				if(Loader.c.exists("Types."+type+"."+fish+".Biomes"))
					
					for(String g:Loader.c.getStringList("Types."+type+"."+fish+".Biomes"))
						biomes.add(getTran(ByBiome.biomes.valueOf(g)));
				else
					biomes.add(getTran(null));
				String b = StringUtils.join(biomes, ", ");
				
				if(Loader.c.getBoolean("Options.UseCustomFishDescription-ifItIsPossible")==true&&Loader.c.exists("Types."+type+"."+fish+".Lore")) {
					for(String s:Loader.c.getStringList("Types."+type+"."+fish+".Lore")) {
						lore.add(Color.c(s
								.replace("%chance%", ""+(Loader.c.getInt("Types."+type+"."+fish+".Chance")>0 ? Loader.c.getInt("Types."+type+"."+fish+".Chance") : 1))
								.replace("%fish_biomes%", b)
								.replace("%biomes%", b)
								.replace("%fish_weight%", (new DecimalFormat("#,##0.00").format(weight)).replaceAll("\\.00", ""))
								.replace("%weight%", (new DecimalFormat("#,##0.00").format(weight)).replaceAll("\\.00", ""))
								.replace("%fish_length%", (new DecimalFormat("#,##0.00").format(length)).replaceAll("\\.00", ""))
								.replace("%length%", (new DecimalFormat("#,##0.00").format(length)).replaceAll("\\.00", ""))
								.replace("%fish_name%", "Uknown")
								.replace("%fish%",  "Uknown")
								.replace("%time%", new SimpleDateFormat("HH:mm:ss").format(new Date()))
								.replace("%date%", new SimpleDateFormat("dd.MM.yyyy").format(new Date()))
								.replace("%fisherman%", p.getName())
								.replace("%fisher%", p.getName())));
					}
				}else {
					for(String s:Loader.c.getStringList("Format.FishDescription")) {
						lore.add(Color.c(s
								.replace("%chance%", ""+(Loader.c.getInt("Types."+type+"."+fish+".Chance")>0 ? Loader.c.getInt("Types."+type+"."+fish+".Chance") : 1))
								.replace("%fish_biomes%", b)
								.replace("%biomes%", b)
								.replace("%fish_weight%", (new DecimalFormat("#,##0.00").format(weight)).replaceAll("\\.00", ""))
								.replace("%weight%", (new DecimalFormat("#,##0.00").format(weight)).replaceAll("\\.00", ""))
								.replace("%fish_length%", (new DecimalFormat("#,##0.00").format(length)).replaceAll("\\.00", ""))
								.replace("%length%", (new DecimalFormat("#,##0.00").format(length)).replaceAll("\\.00", ""))
								.replace("%fish_name%", "Uknown")
								.replace("%fish%",  "Uknown")
								.replace("%time%", new SimpleDateFormat("HH:mm:ss").format(new Date()))
								.replace("%date%", new SimpleDateFormat("dd.MM.yyyy").format(new Date()))
								.replace("%fisherman%", p.getName())
								.replace("%fisher%", p.getName())));
					}
				}
			i.setLore(lore);
			}
			/*ItemStack bukkitstack = i.create();
			net.minecraft.server.v1_16_R2.ItemStack nms = CraftItemStack.asNMSCopy(bukkitstack);
			NBTTagCompound tag = nms.getOrCreateTag();
			tag.setDouble("af.length", length);
			nms.setTag(tag);
			bukkitstack = CraftItemStack.asBukkitCopy(nms);*/
			ItemStack bukkitstack = Tag.add(length, weight, type, fish, i);
				bag.addFishToBagOrInv(p,bukkitstack);
				Utils.addRecord(p, fish, type, length,weight);
				Tournament.add(p, length,weight);
				for(CEnch ec: enchs)
				addEarn(ec,p,type,fish,length);
			Loader.msgCmd(Loader.s("Prefix")+Loader.s("Caught").replace("%cm%", (new DecimalFormat("#,##0.00").format(length))
					.replaceAll("\\.00", "")).replace("%length%", (new DecimalFormat("#,##0.00").format(length)).replaceAll("\\.00", ""))
					.replace("%weight%",(new DecimalFormat("#,##0.00").format(weight)).replaceAll("\\.00", "")).replace("%fish%", name), p);
			Logger.info(p.getDisplayName(), type, fish, length, weight);
			Quests.addProgress(p,type,fish,Actions.CATCH_FISH);
		return;
	}}
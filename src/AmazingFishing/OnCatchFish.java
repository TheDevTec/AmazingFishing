package AmazingFishing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import AmazingFishing.API.treasureType;
import AmazingFishing.RD.Type;
import me.Straiker123.TheAPI;

public class OnCatchFish implements Listener {

	public static void addEarn(Player p, String type, String fish, double length) {
		double money = Loader.c.getDouble("Types."+type+"."+fish+".Money");
		double points = Loader.c.getDouble("Types."+type+"."+fish+".Points");
		int exp = Loader.c.getInt("Types."+type+"."+fish+".Exp");
		if(Loader.c.getBoolean("Options.EarnFromLength")) {
			money=money%length;
			points = points%length;
			exp = (int) (exp%length);
		}
		double moneybonus = Generators.getBonus(p, "Money");
		double pointbonus = Generators.getBonus(p, "Points");
		int expbonus =(int) Generators.getBonus(p, "Exp");
		
		if(moneybonus!=0.0)
		money = money+(money%moneybonus);
		if(pointbonus!=0.0)
		points = points+(points%pointbonus);
		if(expbonus!=0)
		exp = exp+(exp%expbonus);
		if(!Loader.cc.getConfig().getBoolean("Options.DisableMoneyFromCaught"))
		TheAPI.getEconomyAPI().depositPlayer(p.getName(), money);
		p.giveExp(exp);
		Points.give(p.getName(), points);
	}
	
	private void getFish(ItemStack i, Player p, Location hook) {
		Bukkit.getScheduler().runTaskLater(Loader.plugin, new Runnable() {

			@Override
			public void run() {
				task(i,p,hook);
				
			}
			
		},0);
	}
	
	private boolean isDisable(org.bukkit.World world) {
		boolean is = false;
		for(String s:Loader.c.getStringList("Options.Disabled-Worlds"))
		if(world.getName().equals(s))is=true;
		return is;
	}
	
	private void task(ItemStack i, Player p, Location hook) {
		Particles.summon(hook);
		if(Loader.c.getBoolean("Options.Sounds.CatchFish"))
		Sounds.play(p);
		int amount = Generators.amount(p);
		for (int ss =1; ss<=amount;ss++) {
			ByBiome.generateFish(p, i.getType(),hook);
			}}
	
	private static void giveTreasure(Player p) {
		List<String> legend = new ArrayList<String>();
		boolean c,r,e,l;
		c=RD.hasAccess(p, Type.Common)||WG.hasAccess(p, AmazingFishing.WG.Type.Common);
		r=RD.hasAccess(p, Type.Rare)||WG.hasAccess(p, AmazingFishing.WG.Type.Rare);
		e=RD.hasAccess(p, Type.Epic)||WG.hasAccess(p, AmazingFishing.WG.Type.Epic);
		l=RD.hasAccess(p, Type.Legendary)||WG.hasAccess(p, AmazingFishing.WG.Type.Legendary);
		if(c) {
		legend.add("Common");
		legend.add("Common");
		legend.add("Common");
		legend.add("Common");
		}
		if(r)
		legend.add("Rare");
		if(c) {
			legend.add("Common");
			legend.add("Common");
			legend.add("Common");
			legend.add("Common");
			}
		if(r)
		legend.add("Rare");
		if(e)
		legend.add("Epic");
		if(c) {
			legend.add("Common");
			legend.add("Common");
			legend.add("Common");
			legend.add("Common");
			}
		if(l)
		legend.add("Legendary");
		if(c) {
			legend.add("Common");
			legend.add("Common");
			legend.add("Common");
			legend.add("Common");
			legend.add("Common");
			legend.add("Common");
			legend.add("Common");
			legend.add("Common");
			}
		if(r)
		legend.add("Rare");
		if(c) {
			legend.add("Common");
			legend.add("Common");
			legend.add("Common");
			legend.add("Common");
			}
		if(l)
		legend.add("Legendary");
		if(e)
		legend.add("Epic");
		if(c) {
			legend.add("Common");
			legend.add("Common");
			legend.add("Common");
			legend.add("Common");
			}
		if(r)
		legend.add("Rare");
		String select = legend.get(new Random().nextInt(legend.size()-1));
			if(Loader.c.getString("Treasures."+select)!=null) {
				if(Type.valueOf(select)!=null && WG.Type.valueOf(select)!=null)
			if(RD.hasAccess(p, Type.valueOf(select))|| WG.hasAccess(p, AmazingFishing.WG.Type.valueOf(select))) {
			API.giveTreasureByChance(p, treasureType.valueOf(select.toUpperCase()));
			}
	}}
	
	
	private void task2(Player p) {
		List<Integer> list = new ArrayList<Integer>();
		int chance = Loader.c.getInt("Options.Manual.ChanceForTreasure");
		if(chance==0)chance=1;
		for(int i = 0; i < 100; ++i)
			list.add(i);
		boolean run = true;
		for (int counter =0; counter<chance; counter++) {
		if(run)
		if(list.get(new Random().nextInt(99)) == new Random().nextInt(99)) {
			run=false;
			giveTreasure(p);
		}}
		list.clear();
		}
	
	@EventHandler
	public void onFish(PlayerFishEvent e) {
		Player p = e.getPlayer();
		if(p.hasPermission("amazingfishing.use") && !isDisable(p.getWorld())) {
		if(e.getCaught() instanceof Item) {
		Item i = (Item)e.getCaught();
		if(i==null)return;
			ItemStack d = i.getItemStack();
			if(bag.isFish(d)) {
				if(!d.getItemMeta().hasDisplayName() && !d.getItemMeta().hasLore() && !e.getCaught().isOnGround() && e.getHook().getNearbyEntities(0, 1, 0).isEmpty()) {
					if(!Loader.c.getBoolean("Options.CustomFishOnlyWhileTournament")||Loader.c.getBoolean("Options.CustomFishOnlyWhileTournament")&&Tournament.running()) {
					try {
				if(Loader.c.getBoolean("Options.FishRemove"))
					i.remove();
				if(!AFK.isAFK(p))
					getFish(d,e.getPlayer(),e.getHook().getLocation());
			}catch(Exception ea) {

				if(Loader.c.getBoolean("Options.FishRemove"))
				i.remove();
			}}
		}}else {
			if(!Loader.c.getBoolean("Options.TreasuresOnlyWhileTournament")||Loader.c.getBoolean("Options.TreasuresOnlyWhileTournament")&&Tournament.running())
			if(!AFK.isAFK(p))
    		if(Loader.c.getBoolean("Options.Treasures"))
    					task2(p);
			}}}}}

package AmazingFishing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Straiker123.TheAPI;

public class Generators {
	public static double length(String path, String fish) {
		String awd = "0.0";
		if(Loader.c.getString("Types."+path+"."+fish+".MaxCm")!=null)
			awd=Loader.c.getString("Types."+path+"."+fish+".MaxCm");
		double limit = TheAPI.getStringUtils().getDouble(awd);
		if(limit==0.0)limit=1000;
		double random = new Random().nextInt((int)limit)+new Random().nextDouble();
		if(random<Loader.c.getDouble("Options.Manual.MinimalFishLength"))random= Loader.c.getDouble("Options.Manual.MinimalFishLength");
		String c =String.format("%2.02f",random);
		return TheAPI.getStringUtils().getDouble(c);
	}

	public static double weight(double length) {
		ArrayList<Integer> random = new ArrayList<Integer>();
		random.add(7);
		random.add(6);
		random.add(5);
		random.add(4);
		random.add(3);
		random.add(2);
		random.add(1);
		int selected = new Random().nextInt(7);
		length=(length/100)*random.get(selected);
		String c =String.format("%2.02f",length);
		length=TheAPI.getStringUtils().getDouble(c);
		if(length<0.1)length=0.1;
		return length;
	}
	public static double getBonus(Player p, String bonus) {
		double d = 0;
		if(Loader.c.getBoolean("Options.Enchants")) {
		@SuppressWarnings("deprecation")
		ItemStack i = p.getItemInHand();
		ItemMeta m = i.getItemMeta();
		if(m.hasLore())
		for(String s:m.getLore()) {
			String ev = s;
			if(Enchants.getEnchantLevel(s)!=0) {
				s=s.replaceFirst(" "+Enchants.convertLevel(Enchants.getEnchantLevel(s)), "");
				}
				if(Enchants.getEnchant(s)!=null){
				int lvl = Enchants.getEnchantLevel(ev);
				double a = Loader.c.getDouble("Enchants."+Enchants.getEnchant(s)+"."+bonus+"Bonus");
				
				
				double c = a;
				if(lvl != 0 )c=c+(a/random(4)*lvl);
				d=d+c;
			}
		}
		}
		return d;
	}
	
	public static int amount(Player p) {
		int d = 1;
		if(Loader.c.getBoolean("Options.Enchants")) {
		@SuppressWarnings("deprecation")
		ItemStack i = p.getItemInHand();
		if(i!=null) {
		ItemMeta m = i.getItemMeta();
		if(m.hasLore())
		for(String s:m.getLore()) {
			String ev = s;
			if(Enchants.getEnchantLevel(s)!=0) {
				s=s.replaceFirst(" "+Enchants.convertLevel(Enchants.getEnchantLevel(s)), "");
				}
				if(Enchants.getEnchant(s)!=null){
				int lvl = Enchants.getEnchantLevel(ev);
				if(lvl==0)lvl=1;
				double a = Loader.c.getDouble("Enchants."+Enchants.getEnchant(s)+".AmountBonus");
				int del = 8;
				del=Arrays.asList(8,6,4,2).get(new Random().nextInt(4));
				
				
				double c = a+((a/del)*lvl);
				if(c>=2)
	  			d=d+(int) new Random().nextInt((int)c);
				else
				if((int)c == 1)++d;
			}
		}
		}
		if(d==0)d=1;
		}
		return d;
	}
	
	public static double random(double i) {
		double r = 0;
			    Random object = new Random();
			if(i > r) {
				if((int)i != 0)
				r = object.nextInt((int)i)+object.nextDouble();
				else
					r = 0+object.nextDouble();
			}
			if(r==0)r=1;
		return r;
	}
}

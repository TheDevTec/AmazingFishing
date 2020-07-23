package AmazingFishing;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.DevTec.TheAPI;
import me.DevTec.AmazingFishing.Loader;

public class CEnch extends Enchantment {
	private String n,r;
	public CEnch(NamespacedKey key) {
		super(key);
	}

	public void setName(String name, String real) {
		n=name;
		r=real;
	}

	public String realName() {
		return r;
	}
	
	public static double generateRandomDouble(double maxDouble) {
		boolean inMinus = false;
		double i = 0.0;
		if (maxDouble < 0) {
			maxDouble = -1 * maxDouble;
			inMinus = true;
		}
		if (maxDouble == 0.0) {
			return 0.0;
		}
		if (maxDouble < 1.0) {
			i = 0.0 + new Random().nextDouble();
		}else {
			i = new Random().nextInt((int) maxDouble) + new Random().nextDouble();
		}
		//double i = new Random().nextInt((int) maxDouble) + new Random().nextDouble();
		if (i <= 0)
			i = 1;
		if (i > maxDouble)
			i = maxDouble;
		if (inMinus)
			i = -1 * i;
		return i;
	}
	
	public double getBonus(String b,int lvl) {
		double s = 0;
		switch(b) {
		case "Amount":{
			double a = Loader.c.getDouble("Enchants."+r+".AmountBonus");
			int del=Arrays.asList(8,6,4,2).get(TheAPI.generateRandomInt(3));
			s= generateRandomDouble(a+(a > 0 ? (a/del)*lvl : 0.1));
		}break;
		default:
			double a = Loader.c.getDouble("Enchants."+r+"."+b+"Bonus");
			s=a+((a/TheAPI.generateRandomInt(4))*lvl);
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage("a "+a);
			Bukkit.broadcastMessage("s "+s);
			Bukkit.broadcastMessage("lvl "+lvl);
			Bukkit.broadcastMessage("b "+b);
			Bukkit.broadcastMessage("Enchants."+r+"."+b+"Bonus");
			Bukkit.broadcastMessage("");
			break;
		}
		return s;
	}
	public double getBonus(String b,Player s) {
		return getBonus(b,s.getEquipment().getItemInMainHand().getEnchantmentLevel(this));
	}
	
	@Override
	public boolean canEnchantItem(ItemStack e) {
		return e.getType()==Material.FISHING_ROD;
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.FISHING_ROD;
	}

	@Override
	public int getMaxLevel() {
		return 50;
	}

	@Override
	public String getName() {
		return n;
	}

	@Override
	public int getStartLevel() {
		return 1;
	}

	@Override
	public boolean isCursed() {
		return false;
	}

	@Override
	public boolean isTreasure() {
		return false;
	}

}

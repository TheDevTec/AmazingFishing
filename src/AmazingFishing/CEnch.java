package AmazingFishing;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.DevTec.TheAPI;

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
	
	public double getBonus(String b,int lvl) {
		double s = 0;
		switch(b) {
		case "Amount":{
			double a = Loader.c.getDouble("Enchants."+r+".AmountBonus");
			int del=Arrays.asList(8,6,4,2).get(TheAPI.generateRandomInt(3));
			s= TheAPI.generateRandomDouble(a+(a > 0 ? (a/del)*lvl : 0));
		}break;
		default:
			double a = Loader.c.getDouble("Enchants."+r+"."+b+"Bonus");
			s=a+((a/TheAPI.generateRandomInt(4))*lvl);
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

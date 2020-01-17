package AmazingFishing;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Enchants {
	public static int getEnchantLevel(String enchant) {
		if(Loader.c.getString("Enchants")!=null)
		for(String s:Loader.c.getConfigurationSection("Enchants").getKeys(false)) {
			String name = s;
			if(Loader.c.getString("Enchants."+s+".Name")!=null)name=Loader.c.getString("Enchants."+s+".Name");
		enchant=enchant.replaceFirst(Color.c(name+" "), "");
		}
		if(enchant.equals("50"))return 50;
		if(enchant.equals("49"))return 49;
		if(enchant.equals("48"))return 48;
		if(enchant.equals("47"))return 47;
		if(enchant.equals("46"))return 46;
		if(enchant.equals("45"))return 45;
		if(enchant.equals("44"))return 44;
		if(enchant.equals("43"))return 43;
		if(enchant.equals("42"))return 42;
		if(enchant.equals("41"))return 41;
		if(enchant.equals("40"))return 40;
		if(enchant.equals("39"))return 39;
		if(enchant.equals("38"))return 38;
		if(enchant.equals("37"))return 37;
		if(enchant.equals("36"))return 36;
		if(enchant.equals("35"))return 35;
		if(enchant.equals("34"))return 34;
		if(enchant.equals("33"))return 33;
		if(enchant.equals("32"))return 32;
		if(enchant.equals("31"))return 31;
		if(enchant.equals("30"))return 30;
		if(enchant.equals("29"))return 29;
		if(enchant.equals("28"))return 28;
		if(enchant.equals("27"))return 27;
		if(enchant.equals("26"))return 26;
		if(enchant.equals("25"))return 25;
		if(enchant.equals("24"))return 24;
		if(enchant.equals("23"))return 23;
		if(enchant.equals("22"))return 22;
		if(enchant.equals("21"))return 21;
		if(enchant.equals("20"))return 20;
		if(enchant.equals("19"))return 19;
		if(enchant.equals("18"))return 18;
		if(enchant.equals("17"))return 17;
		if(enchant.equals("16"))return 16;
		if(enchant.equals("15"))return 15;
		if(enchant.equals("14"))return 14;
		if(enchant.equals("13"))return 13;
		if(enchant.equals("12"))return 12;
		if(enchant.equals("11"))return 11;
		if(enchant.equals("X"))return 10;
		if(enchant.equals("IX"))return 9;
		if(enchant.equals("VIII"))return 8;
		if(enchant.equals("VII"))return 7;
		if(enchant.equals("VI"))return 6;
		if(enchant.equals("V"))return 5;
		if(enchant.equals("IV"))return 4;
		if(enchant.equals("III"))return 3;
		if(enchant.equals("II"))return 2;
		if(enchant.equals("I"))return 1;
		return 0;
	}
	public static String convertLevel(int level) {
		if(level==50)return "50";
		if(level==49)return "49";
		if(level==48)return "48";
		if(level==47)return "47";
		if(level==46)return "46";
		if(level==45)return "45";
		if(level==44)return "44";
		if(level==43)return "43";
		if(level==42)return "42";
		if(level==41)return "41";
		if(level==40)return "40";
		if(level==39)return "39";
		if(level==38)return "38";
		if(level==37)return "37";
		if(level==36)return "36";
		if(level==35)return "35";
		if(level==34)return "34";
		if(level==33)return "33";
		if(level==32)return "32";
		if(level==31)return "31";
		if(level==30)return "30";
		if(level==29)return "29";
		if(level==28)return "28";
		if(level==27)return "27";
		if(level==26)return "26";
		if(level==25)return "25";
		if(level==24)return "24";
		if(level==23)return "23";
		if(level==22)return "22";
		if(level==21)return "21";
		if(level==20)return "20";
		if(level==19)return "19";
		if(level==18)return "18";
		if(level==17)return "17";
		if(level==16)return "16";
		if(level==15)return "15";
		if(level==14)return "14";
		if(level==13)return "13";
		if(level==12)return "12";
		if(level==11)return "11";
		if(level==10)return "X";
		if(level==9)return "IX";
		if(level==8)return "VIII";
		if(level==7)return "VII";
		if(level==6)return "VI";
		if(level==5)return "V";
		if(level==4)return "IV";
		if(level==3)return "III";
		if(level==2)return "II";
		if(level==1)return "I";
		return "";
	}
	
	public static String getEnchant(String s) {
		String enchant = null;
		if(Loader.c.getString("Enchants")!=null)
		for(String f:Loader.c.getConfigurationSection("Enchants").getKeys(false)) {
			String name = f;
			if(Loader.c.getString("Enchants."+f+".Name")!=null)name=Color.c(Loader.c.getString("Enchants."+f+".Name"));
			if(s.equals(name))enchant=f;
		}
		return enchant;
	}
	public static ArrayList<String> getEnchants(ItemStack item){
		 ArrayList<String> ench = new  ArrayList<String>();
		ItemMeta m = item.getItemMeta();
		if(m.hasLore())
		for(String s:m.getLore()) {
			if(Enchants.getEnchantLevel(s)!=0) {
				s=s.replaceFirst(" "+Enchants.convertLevel(Enchants.getEnchantLevel(s)), "");
				}

			String e = getEnchant(s);
			if(e!=null)ench.add(e);
		}
		return ench;
	}
	public static void addEnchant(String enchant, Player p) {
		ItemStack i = Normal.getRod(p);
		ItemMeta m =i.getItemMeta();
		boolean is = false;
		int where =-1;
		boolean find = true;
		String w = null;
		if(m.hasLore())
		for(String s:m.getLore()) {
			if(find) {
				where=where+1; 
				w=s;
			if(Enchants.getEnchantLevel(s)!=0) {
				s=s.replaceFirst(" "+Enchants.convertLevel(Enchants.getEnchantLevel(s)), "");
				}
			if(Enchants.getEnchant(s)!=null && Enchants.getEnchant(s).equalsIgnoreCase(enchant)) {
				is=true;
			find=false; 
			}
			}}
		find=true;
		if(is==true) {
			List<String> lore = m.getLore(); 
			int level =getEnchantLevel(w)+1;
			lore.remove(where); 
			String name = enchant;
			if(Loader.c.getString("Enchants."+enchant+".Name")!=null)name=Loader.c.getString("Enchants."+enchant+".Name");
			lore.add(Color.c(name+" "+convertLevel(level))); 
			m.setLore(lore); 
			i.setItemMeta(m);
			Loader.me.set("Players."+p.getName()+".SavedRod", i);
			Loader.saveChatMe();
		}else {
			String name = enchant;
			if(Loader.c.getString("Enchants."+enchant+".Name")!=null)name=Loader.c.getString("Enchants."+enchant+".Name");
			List<String> lore =	new ArrayList<String>();
			if(m.hasLore())lore=m.getLore();
			lore.add(Color.c(name));
			m.setLore(lore);
			i.setItemMeta(m);
			Loader.me.set("Players."+p.getName()+".SavedRod", i);
			Loader.saveChatMe();
		}}
		
}

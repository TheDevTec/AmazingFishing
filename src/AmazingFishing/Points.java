package AmazingFishing;

import me.DevTec.AmazingFishing.Loader;

public class Points {

	public static double bal(String p) {
		return Loader.me.getDouble("Players."+p+".Points");
	}
	
	public static boolean has(String p, double d) {
		return bal(p) >= d;
	}
	
	public static String getBal(String string) {
		return String.format("%2.02f",bal(string)).replace(",", ".");
	}
	public static void take(String p, double m) {
		double money = bal(p)-m;
		Loader.me.set("Players."+p+".Points", money);
		Loader.saveChatMe();
	}
	public static void give(String p, double points) {
		double money = bal(p)+points;
		Loader.me.set("Players."+p+".Points", money);
		Loader.saveChatMe();
	}

	public static void set(String name, double double1) {
		take(name,bal(name));
		give(name,double1);
	}
}

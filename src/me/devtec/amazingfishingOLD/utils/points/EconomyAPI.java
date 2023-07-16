package me.devtec.amazingfishingOLD.utils.points;

import net.milkbowl.vault.economy.Economy;

public class EconomyAPI {

	public static Object economy;

	public static double getBalance(String player) {
		if (economy == null)
			return 0;
		return ((Economy) economy).getBalance(player);
	}

	public static void depositPlayer(String player, double val) {
		if (economy == null)
			return;
		((Economy) economy).depositPlayer(player, val);
	}

	public static void withdrawPlayer(String player, double val) {
		if (economy == null)
			return;
		((Economy) economy).withdrawPlayer(player, val);
	}

	public static boolean has(String player, double val) {
		return getBalance(player) >= val;
	}

}

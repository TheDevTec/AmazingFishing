package me.devtec.amazingfishing.player.points_economy;

import net.milkbowl.vault.economy.Economy;

public class EconomyAPI {

	public static Object economy;

	/** Gets player account status. How much money they have.
	 * @param player Online or offline player's name
	 * @return
	 */
	public static double getBalance(String player) {
		if (economy == null)
			return 0;
		return ((Economy) economy).getBalance(player);
	}

	/** Deposits money to player's vault account
	 * @param player Online or offline player's name
	 * @param val Amount of money
	 */
	public static void depositPlayer(String player, double val) {
		if (economy == null)
			return;
		((Economy) economy).depositPlayer(player, val);
	}

	/** Withdraws money from player's vault account
	 * @param player Online or offline player's name
	 * @param val Amount of money
	 */
	public static void withdrawPlayer(String player, double val) {
		if (economy == null)
			return;
		((Economy) economy).withdrawPlayer(player, val);
	}

	/** Check if player have some amount of money
	 * @param player Online or offline player's name
	 * @param val Amount of money
	 * @return true/false
	 */
	public static boolean has(String player, double val) {
		return getBalance(player) >= val;
	}

}

package me.devtec.amazingfishingOLD.utils.points;

public class VaultPoints implements PointsManager {

	public double get(String player) {
		return EconomyAPI.getBalance(player);
	}

	public void set(String player, double points) {
		remove(player, get(player));
		add(player, points);
	}

	public void add(String player, double points) {
		EconomyAPI.depositPlayer(player, points);
	}

	public void remove(String player, double points) {
		EconomyAPI.withdrawPlayer(player, points);
	}
}
package me.devtec.amazingfishing.utils.points;

import me.devtec.theapi.TheAPI;

public class UserPoints implements PointsManager {

	public double get(String player) {
		return TheAPI.getUser(player).getDouble("af.points");
	}

	public void set(String player, double points) {
		TheAPI.getUser(player).set("af.points", points);
	}

	public void add(String player, double points) {
		set(player, get(player)+points);
	}

	public void remove(String player, double points) {
		set(player, get(player)-points);
	}
}

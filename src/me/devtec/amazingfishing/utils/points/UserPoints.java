package me.devtec.amazingfishing.utils.points;

import me.devtec.shared.API;
import me.devtec.shared.dataholder.DataType;

public class UserPoints implements PointsManager {

	@Override
	public double get(String player) {
		return API.getUser(player).getDouble("af.points");
	}

	@Override
	public void set(String player, double points) {
		API.getUser(player).set("af.points", points).save(DataType.YAML);
	}

	@Override
	public void add(String player, double points) {
		set(player, get(player) + points);
	}

	@Override
	public void remove(String player, double points) {
		set(player, get(player) - points);
	}
}

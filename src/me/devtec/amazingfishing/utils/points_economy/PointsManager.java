package me.devtec.amazingfishing.utils.points_economy;

public interface PointsManager {
	
	public double get(String player);

	public void set(String player, double points);

	public void add(String player, double points);

	public void remove(String player, double points);

	public default boolean has(String player, double points) {
		return get(player) >= points;
	}
}

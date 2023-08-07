package me.devtec.amazingfishing.player.points_economy;

import me.devtec.amazingfishing.player.Fisher;

public class FisherPoints {
	
	private Fisher fisher;
	
	public FisherPoints(Fisher fisher) {
		this.fisher = fisher;
	}
	
	public double get() {
		return me.devtec.amazingfishing.API.getPoints().get(fisher.getPlayer().getName());
	}
	public void set(double points) {
		me.devtec.amazingfishing.API.getPoints().set(fisher.getPlayer().getName(), points);
	}
	public void add(double points) {
		me.devtec.amazingfishing.API.getPoints().add(fisher.getPlayer().getName(), points);
	}
	public void remove(double points) {
		me.devtec.amazingfishing.API.getPoints().remove(fisher.getPlayer().getName(), points);
	}
	
}

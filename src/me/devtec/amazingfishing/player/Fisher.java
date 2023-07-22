package me.devtec.amazingfishing.player;

import org.bukkit.entity.Player;

public class Fisher {

	private Player player;
	private FisherSituation situation;
	
	public Fisher(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	/** If player is still online.
	 * @return {@link Player}
	 */
	public boolean isOnline() {
		if(player == null)
			return false;
		return player.isOnline();
	}
	
	/*
	 * FisherSituation
	 */
	
	/**
	 * @return {@link FisherSituation}
	 */
	public FisherSituation getFisherSituation() {
		if(situation == null)
			situation = new FisherSituation(player);
		return situation;
	}
	
}

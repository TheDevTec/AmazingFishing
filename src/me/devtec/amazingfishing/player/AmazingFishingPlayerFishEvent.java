package me.devtec.amazingfishing.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;

import me.devtec.amazingfishing.fishing.FishingItem;

public class AmazingFishingPlayerFishEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
    private String message;
    
    private boolean cancelled;
    private State state;
    private Player player;
    private PlayerFishEvent event;
    private FishingItem caught;
    
    public AmazingFishingPlayerFishEvent(String example) {
        message = example;
    }

    public AmazingFishingPlayerFishEvent(Player player, State state, PlayerFishEvent event) {
        this.state = state;
        this.player = player;
        this.event = event;
        this.caught = null;
    }
    public AmazingFishingPlayerFishEvent(Player player, State state, PlayerFishEvent event, FishingItem caught) {
        this.state = state;
        this.player = player;
        this.event = event;
        this.caught = caught;
    }
    
    public String getMessage() {
        return message;
    }
    
    public State getState() {
    	return state;
    }
    
    public Player getPlayer() {
    	return player;
    }
    
    public PlayerFishEvent getOriginalEvent() {
    	return event;
    }
    
    public FishingItem getCaught() {
    	return caught;
    }
    
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /*
     * CANCELLING
     */
	@Override
	public boolean isCancelled() { //If Event is cancelled
        return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) { //Cancelling event (true)
        cancelled = cancel;
		
	}
}

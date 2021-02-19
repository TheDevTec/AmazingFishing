package me.devtec.amazingfishing.other;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.utils.Manager;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.utils.datakeeper.User;

public class Bag {
	private Player player;
	private User u;
	public Bag(Player p) {
		this.player=p;
		this.u=TheAPI.getUser(p);
	}
	
	public String getName() { 
		return player.getName(); 
	}

	public List<ItemStack> getBag(){
		return u.getData().getListAs(Manager.getDataLocation()+".Bag", ItemStack.class);
	}
	public void saveBag(GUI i) {
		u.remove(Manager.getDataLocation()+".Bag");
		List<ItemStack> list = new ArrayList<ItemStack>();
		
		for(int slot = 0; slot < 45; ++slot) {
			if(i.getItem(slot)==null)continue;
			Fish fish = API.getFish(i.getItem(slot));
			if(fish==null)  returnItem(i.getItem(slot));
			list.add(i.getItem(slot));
		}
		setItems(list);
	}
	private void returnItem(ItemStack item) {
		TheAPI.giveItem(player, item);
	}
	private void setItems(List<ItemStack> list) {
		u.set(Manager.getDataLocation()+".Bag", list);
		u.save();
	}
}

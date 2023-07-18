package me.devtec.amazingfishing.fishing;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Fish extends FishingItem {

	public void testing() {
		
	}

	
	/*
	 * PERMISSIONS
	 */
	@Override
	public boolean hasPermission(Player player) {
		if(!getPermission().isEmpty())
			return player.hasPermission(getPermission());
		if(!getDefaultPermission().isEmpty())
			return player.hasPermission(getDefaultPermission());
		return false;
	}
	@Override
	void setDefaultPermissionPath() { def_perm_path = "fishing.permissions.fish"; }


	@Override
	ItemStack getItem() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	ItemStack getPreviewItem() {
		// TODO Auto-generated method stub
		return null;
	}
}

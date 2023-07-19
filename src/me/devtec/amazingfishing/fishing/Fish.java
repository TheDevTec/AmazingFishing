package me.devtec.amazingfishing.fishing;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.fishing.enums.FishType;
import me.devtec.shared.dataholder.Config;

public class Fish extends FishingItem {

	
	public Fish(Config file) {
		super(file, FishType.FISH);
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

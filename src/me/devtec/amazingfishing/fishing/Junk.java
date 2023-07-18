package me.devtec.amazingfishing.fishing;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.fishing.enums.FishType;
import me.devtec.shared.dataholder.Config;

public class Junk extends FishingItem {

	public Junk(Config file) {
		
		setConfig(file);
		setType(FishType.JUNK);
		
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
	void setDefaultPermissionPath() { def_perm_path = "fishing.permissions.junk"; }


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

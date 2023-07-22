package me.devtec.amazingfishing.fishing;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.fishing.enums.FishType;
import me.devtec.amazingfishing.player.Fisher;
import me.devtec.amazingfishing.player.FisherSituation;
import me.devtec.amazingfishing.utils.ItemUtils;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.shared.dataholder.Config;
import me.devtec.theapi.bukkit.game.ItemMaker;

public class Junk extends FishingItem {

	public Junk(Config file) {
		super(file, FishType.JUNK);
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
		return true;
	}
	@Override
	void setDefaultPermissionPath() { def_perm_path = "fishing.permissions.junk"; }


	@Override
	public ItemStack generateItem() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ItemStack getPreviewItem() {
		ItemMaker item = ItemMaker.loadMakerFromConfig(getConfig(), "preview");
			
		Placeholders placeholers = Placeholders.c().addPlayer("player", null)
			.add("fish_type", getType().toString())
			.add("fish_permission", getPermission())
			.add("fish_chance", getChance())
			.add("fish_name", getName())
			.add("fish_time", getTime().toString())
			.add("fish_weather", getWeather())
			.add("fish_cansell", isSaleable())
			.add("fish_money", getBaseMoney())
			.add("fish_points", getBasePoints())
			.add("fish_xp", getBaseXp())
			.add("fish_isedible", isEdible())
			.add("fish_hunger", getHunger());
		
		return ItemUtils.applyPlaceholders(item, placeholers).build();
	}

	@Override
	public void giveItem() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean canCatch(Fisher fisher) {
		if(!hasPermission(fisher.getPlayer()))
			return false;
		
		FisherSituation situation = fisher.getFisherSituation();
		
		if(!getBiomes().contains(situation.getBiome()))
			return false;
		if(getBlockedBiomes().contains(situation.getBiome()))
			return false;
		if(!getTime().equals(situation.getTime()))
			return false;
		if(!getWeather().equals(situation.getWeather()))
			return false;
		
		return true;
	}
}

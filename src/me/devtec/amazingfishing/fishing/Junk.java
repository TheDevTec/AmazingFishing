package me.devtec.amazingfishing.fishing;

import java.time.LocalDate;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.fishing.enums.FishType;
import me.devtec.amazingfishing.player.Fisher;
import me.devtec.amazingfishing.player.FisherSituation;
import me.devtec.amazingfishing.player.Statistics;
import me.devtec.amazingfishing.utils.ItemUtils;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.shared.dataholder.Config;
import me.devtec.shared.dataholder.DataType;
import me.devtec.theapi.bukkit.BukkitLoader;
import me.devtec.theapi.bukkit.game.ItemMaker;
import me.devtec.theapi.bukkit.nms.NBTEdit;

public class Junk extends FishingItem {

	public Junk(Config file) {
		super(file, FishType.JUNK);
		this.setDefaultPermissionPath();
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
	public ItemMaker getItem(Placeholders placeholders) {
		ItemMaker item = ItemMaker.loadMakerFromConfig(getConfig(), "item");
		placeholders.add("fish_type", getType().toString())
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
		
		return ItemUtils.applyPlaceholders(item, placeholders);
	}


	@Override
	public ItemMaker getPreviewItem() {
		ItemMaker item = ItemUtils.loadPreviewItem(getConfig());
			
		Placeholders placeholders = Placeholders.c()
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
		
		return ItemUtils.applyPlaceholders(item, placeholders);
	}

	@Override
	public ItemStack generate(Player player, Placeholders placeholders) {
		ItemMaker item = getItem(placeholders.addPlayer("player", player));

		if(item == null)
			return null;

		Statistics.newCatch(player, this, 0, 0);
		
		//If item is not Edible or Saleable or empty lore -> Admin probably want this to be more default minecraft item
		if(isSaleable() || isEdible() || (item.getLore()!= null && !item.getLore().isEmpty()) ) {
			NBTEdit edit = new NBTEdit(item.build());
			edit.setString("af_data", createData().toString(DataType.JSON));
			return BukkitLoader.getNmsProvider().setNBT(item.build(), edit);
		}
		
		return item.build();
	}

	private Config createData() {
		Config data = new Config().set("file", getConfig().getFile().getName()).set("name", getName())
				.set("type", getType().getName())
				.set("date", LocalDate.now().toString());
		if(isEdible())
			data.set("addhunger", getHunger());
		
		return data;
	
	}


	@Override
	public boolean canCatch(Fisher fisher) {
		if(!hasPermission(fisher.getPlayer()))
			return false;
		
		FisherSituation situation = fisher.getFisherSituation();
		
		if(!getBiomes().isEmpty() && !getBiomes().contains(situation.getBiome()))
			return false;
		if(!getBlockedBiomes().isEmpty() && getBlockedBiomes().contains(situation.getBiome()))
			return false;
		if(!getTime().equals(situation.getTime()))
			return false;
		if(!getWeather().equals(situation.getWeather()))
			return false;
		
		return true;
	}
}

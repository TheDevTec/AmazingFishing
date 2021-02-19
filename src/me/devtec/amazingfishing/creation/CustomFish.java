package me.devtec.amazingfishing.creation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.construct.FishAction;
import me.devtec.amazingfishing.construct.FishTime;
import me.devtec.amazingfishing.construct.FishType;
import me.devtec.amazingfishing.construct.FishWeather;
import me.devtec.amazingfishing.utils.Utils;
import me.devtec.theapi.apis.ItemCreatorAPI;
import me.devtec.theapi.utils.datakeeper.Data;

public class CustomFish implements Fish {
	final String name, path;
	final Data data;
	final FishType type;
	
	public CustomFish(String name, String path, FishType type, Data data) {
		this.name=name;
		this.type=type;
		this.path=path;
		this.data=data;
	}
	
	@Override
	public String getName() {
		return name;
	}

	
	@Override
	public FishType getType() {
		return type;
	}

	@Override
	public String getDisplayName() {
		return data.getString("fish."+path+"."+name+".name");
	}

	@Override
	public List<String> getMessages(FishAction action) {
		return data.getStringList("fish."+path+"."+name+".messages."+action.name().toLowerCase());
	}

	@Override
	public List<String> getCommands(FishAction action) {
		return data.getStringList("fish."+path+"."+name+".commands."+action.name().toLowerCase());
	}

	@Override
	public List<Biome> getBiomes() {
		List<Biome> biomes = new ArrayList<>();
		for(String biome : data.getStringList("fish."+path+"."+name+".biomes"))
			try {
				biomes.add(Biome.valueOf(biome.toUpperCase()));
			}catch(Exception | NoSuchFieldError e) {}
		return biomes;
	}

	@Override
	public double getChance() {
		return data.getDouble("fish."+path+"."+name+".chance");
	}

	@Override
	public String getPermission() {
		String perm = data.getString("fish."+path+"."+name+".permission");
		if(perm==null||perm.trim().equals(""))return null;
		return perm;
	}

	@Override
	public FishTime getCatchTime() {
		try {
			return FishTime.valueOf(data.getString("fish."+path+"."+name+".catch.time").toUpperCase());
		}catch(Exception | NoSuchFieldError e) {}
		return FishTime.EVERY;
	}

	@Override
	public FishWeather getCatchWeather() {
		try {
			return FishWeather.valueOf(data.getString("fish."+path+"."+name+".catch.weather").toUpperCase());
		}catch(Exception | NoSuchFieldError e) {}
		return FishWeather.EVERY;
	}

	
	public Data createData(double weight, double length) {
		return new Data().set("fish", name).set("type", type.name()).set("weigth", length).set("length", length);
	}

	
	public boolean isInstance(Data data) {
		return data.exists("fish") && data.exists("type") && data.getString("fish").equals(name) && data.getString("type").equals(type.name());
	}
	
	public int getModel() {
		return data.getInt("fish."+path+"."+name+".model");
	}

	private static DecimalFormat ff = new DecimalFormat("###,###.#");
	
	@Override
	public ItemStack createItem(double weight, double length) {
		ItemCreatorAPI c = new ItemCreatorAPI(find(type.name(), type.ordinal()));
		c.setDisplayName(getDisplayName());
		List<String> l = data.getStringList("fish."+path+"."+name+".lore");
		l.replaceAll(a -> a.replace("%weight%", ff.format(weight).replace(",", ".").replaceAll("[^0-9.]+", ",")).replace("%length%", ff.format(length).replace(",", ".").replaceAll("[^0-9.]+", ",")));
		c.setLore(l);
		ItemStack stack = Utils.setModel(c.create(), getModel());
		Object r = Utils.asNMS(stack);
		Utils.setString(Utils.getNBT(r), createData(weight, length));
		return Utils.asBukkit(r);
	}
	
	@SuppressWarnings("deprecation")
	private static ItemStack find(String name, int id) {
		if(Material.getMaterial(name)!=null)return new ItemStack(Material.getMaterial(name));
		return new ItemStack(Material.getMaterial("RAW_FISH"),1,(short)id);
	}

	@Override
	public double getWeigth() {
		return data.getDouble("fish."+path+"."+name+".weight");
	}

	@Override
	public double getLength() {
		return data.getDouble("fish."+path+"."+name+".length");
	}

	@Override
	public double getMinWeigth() {
		return data.getDouble("fish."+path+"."+name+".minweigth");
	}

	@Override
	public double getMinLength() {
		return data.getDouble("fish."+path+"."+name+".minlength");
	}
}

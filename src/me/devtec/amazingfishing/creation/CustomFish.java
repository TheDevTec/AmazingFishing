package me.devtec.amazingfishing.creation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.construct.Calculator;
import me.devtec.amazingfishing.construct.CatchFish;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.construct.FishAction;
import me.devtec.amazingfishing.construct.FishTime;
import me.devtec.amazingfishing.construct.FishType;
import me.devtec.amazingfishing.construct.FishWeather;
import me.devtec.amazingfishing.utils.Utils;
import me.devtec.theapi.apis.ItemCreatorAPI;
import me.devtec.theapi.utils.datakeeper.Data;
import me.devtec.theapi.utils.json.Writer;

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
	
	public String toString() {
		Map<String, Object> map = new HashMap<>();
		map.put("type", "Fish");
		map.put("name", getName());
		map.put("chance", getChance());
		map.put("fish", getType().name().toLowerCase());
		if(getPermission()!=null)
		map.put("permission", getPermission());
		map.put("catch_time", getCatchTime().name().toLowerCase());
		map.put("catch_weather", getCatchWeather().name().toLowerCase());
		map.put("weigth", getWeigth());
		map.put("min_weigth", getMinWeigth());
		map.put("length", getLength());
		map.put("min_length", getMinLength());
		map.put("money", getMoney());
		map.put("exp", getXp());
		map.put("points", getPoints());
		map.put("model", getModel());
		return Writer.write(map);
	}
	
	public boolean equals(Object o) {
		if(o instanceof Fish) {
			if(o instanceof CustomFish) {
				return ((CustomFish) o).data.equals(data) && type.equals(((CustomFish) o).type) && name.equals(((CustomFish) o).name);
			}else {
				return ((Fish) o).getName().equals(name) && getType().equals(((Fish) o).getType());
			}
		}
		return false;
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
		l.replaceAll(a -> a.replace("%weight%", ff.format(weight).replace(",", ".").replaceAll("[^0-9.]+", ","))
				.replace("%length%", ff.format(length).replace(",", ".").replaceAll("[^0-9.]+", ","))
				.replace("%chance%", (""+getChance()).replace(",", ".").replaceAll("[^0-9.]+", ","))
				.replace("%name%", getDisplayName())
				.replace("%biomes%", getBiomes().toString().replace("[", "").replace("]", "") )
				);
		c.setLore(l);
		ItemStack stack = Utils.setModel(c.create(), getModel());
		Object r = Utils.asNMS(stack);
		Utils.setString(Utils.getNBT(r), createData(weight, length));
		return Utils.asBukkit(r);
	}

	@Override
	public ItemStack createItem(double weight, double length, Player p, Location hook) {
		ItemCreatorAPI c = new ItemCreatorAPI(find(type.name(), type.ordinal()));
		c.setDisplayName(getDisplayName());
		List<String> l = data.getStringList("fish."+path+"."+name+".lore");
		l.replaceAll(a -> a.replace("%weight%", ff.format(weight).replace(",", ".").replaceAll("[^0-9.]+", ","))
				.replace("%length%", ff.format(length).replace(",", ".").replaceAll("[^0-9.]+", ","))
				.replace("%chance%", (""+getChance()).replace(",", ".").replaceAll("[^0-9.]+", ","))
				.replace("%name%", getDisplayName())
				.replace("%player%", p.getName())
				.replace("%displayname%", p.getDisplayName())
				.replace("%biomes%", getBiomes().toString().replace("[", "").replace("]", "") )
				.replace("%biome%", hook.getBlock().getBiome().name())
				.replace("%x%", ""+hook.getX())
				.replace("%y%", ""+hook.getY())
				.replace("%z%", ""+hook.getZ())
				.replace("%world%", hook.getWorld().getName())
				);
		c.setLore(l);
		ItemStack stack = Utils.setModel(c.create(), getModel());
		Object r = Utils.asNMS(stack);
		Utils.setString(Utils.getNBT(r), createData(weight, length));
		return Utils.asBukkit(r);
	}
	
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
	
	@Override
	public double getMoney() {
		return data.getDouble("fish."+path+"."+name+".money");
	}

	@Override
	public double getPoints() {
		return data.getDouble("fish."+path+"."+name+".points");
	}

	@Override
	public double getXp() {
		return data.getDouble("fish."+path+"."+name+".xp");
	}

	@Override
	public CatchFish createCatchFish(Data data) {
		return new CatchFish() {
			
			@Override
			public double getWeigth() {
				return data.getDouble("weigth");
			}
			
			@Override
			public FishType getType() {
				return CustomFish.this.getType();
			}
			
			@Override
			public String getName() {
				return CustomFish.this.getName();
			}
			
			@Override
			public double getLength() {
				return data.getDouble("length");
			}
			
			@Override
			public Fish getFish() {
				return CustomFish.this;
			}
		};
	}

	@Override
	public String getCalculator(Calculator type) {
		return data.getString("fish."+path+"."+name+".calculator."+type.name().toLowerCase());
	}
}

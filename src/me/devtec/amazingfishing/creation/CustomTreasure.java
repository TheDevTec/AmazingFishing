package me.devtec.amazingfishing.creation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.block.Biome;

import me.devtec.amazingfishing.construct.FishTime;
import me.devtec.amazingfishing.construct.FishWeather;
import me.devtec.amazingfishing.construct.Treasure;
import me.devtec.theapi.utils.datakeeper.Data;
import me.devtec.theapi.utils.json.Json;

public class CustomTreasure implements Treasure {
	final String name;
	final Data data;
	
	public CustomTreasure(String name, Data data) {
		this.name=name;
		this.data=data;
	}
	
	public String toString() {
		Map<String, Object> map = new HashMap<>();
		map.put("type", "Treasure");
		map.put("name", getName());
		map.put("chance", getChance());
		if(getPermission()!=null)
		map.put("permission", getPermission());
		map.put("catch_time", getCatchTime().name().toLowerCase());
		map.put("catch_weather", getCatchWeather().name().toLowerCase());
		return Json.writer().write(map);
	}
	
	public boolean equals(Object o) {
		if(o instanceof Treasure) {
			if(o instanceof CustomTreasure) {
				return ((CustomTreasure) o).data.equals(data) && name.equals(((CustomTreasure) o).name);
			}else {
				return ((Treasure) o).getName().equals(name);
			}
		}
		return false;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDisplayName() {
		return data.getString("treasures."+name+".name");
	}

	@Override
	public List<String> getMessages() {
		return data.getStringList("treasures."+name+".messages");
	}

	@Override
	public List<String> getCommands() {
		return data.getStringList("treasures."+name+".commands");
	}

	@Override
	public List<Biome> getBiomes() {
		List<Biome> biomes = new ArrayList<>();
		for(String biome : data.getStringList("treasures."+name+".biomes"))
			try {
				biomes.add(Biome.valueOf(biome.toUpperCase()));
			}catch(Exception | NoSuchFieldError e) {}
		return biomes;
	}

	@Override
	public List<Biome> getBlockedBiomes() {
		List<Biome> biomes = new ArrayList<>();
		for(String biome : data.getStringList("treasures."+name+".blockedbiomes"))
			try {
				biomes.add(Biome.valueOf(biome.toUpperCase()));
			}catch(Exception | NoSuchFieldError e) {}
		return biomes;
	}

	@Override
	public double getChance() {
		return data.getDouble("treasures."+name+".chance");
	}

	@Override
	public String getPermission() {
		String perm = data.getString("treasures."+name+".permission");
		if(perm==null||perm.trim().isEmpty())return null;
		return perm;
	}

	@Override
	public FishTime getCatchTime() {
		try {
			return FishTime.valueOf(data.getString("treasures."+name+".catch.time").toUpperCase());
		}catch(Exception | NoSuchFieldError e) {}
		return FishTime.EVERY;
	}

	@Override
	public FishWeather getCatchWeather() {
		try {
			return FishWeather.valueOf(data.getString("treasures."+name+".catch.weather").toUpperCase());
		}catch(Exception | NoSuchFieldError e) {}
		return FishWeather.EVERY;
	}
}
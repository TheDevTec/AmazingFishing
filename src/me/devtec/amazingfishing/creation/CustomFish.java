package me.devtec.amazingfishing.creation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Calculator;
import me.devtec.amazingfishing.construct.CatchFish;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.construct.FishAction;
import me.devtec.amazingfishing.construct.FishTime;
import me.devtec.amazingfishing.construct.FishType;
import me.devtec.amazingfishing.construct.FishWeather;
import me.devtec.amazingfishing.utils.Utils;
import me.devtec.theapi.apis.ItemCreatorAPI;
import me.devtec.theapi.placeholderapi.PlaceholderAPI;
import me.devtec.theapi.utils.datakeeper.Data;
import me.devtec.theapi.utils.datakeeper.DataType;
import me.devtec.theapi.utils.json.Writer;
import me.devtec.theapi.utils.nms.NMSAPI;
import me.devtec.theapi.utils.nms.nbt.NBTEdit;

public class CustomFish implements Fish {
	final String name, path;
	final Data data;
	final FishType type;
	
	public CustomFish(String name, String path, FishType type, Data data) {
		this.name=name;
		this.type=type;
		this.path=path.toLowerCase();
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
		map.put("weight", getWeight());
		map.put("min_weight", getMinWeight());
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
		return data.getString(path+"."+name+".name");
	}

	@Override
	public List<String> getMessages(FishAction action) {
		return data.getStringList(path+"."+name+".messages."+action.name().toLowerCase());
	}

	@Override
	public List<String> getCommands(FishAction action) {
		return data.getStringList(path+"."+name+".commands."+action.name().toLowerCase());
	}

	@Override
	public List<Biome> getBiomes() {
		List<Biome> biomes = new ArrayList<>();
		for(String biome : data.getStringList(path+"."+name+".biomes"))
			try {
				biomes.add(Biome.valueOf(biome.toUpperCase()));
			}catch(Exception | NoSuchFieldError e) {}
		return biomes;
	}
	
	@Override
	public List<Biome> getBlockedBiomes() {
		List<Biome> biomes = new ArrayList<>();
		for(String biome : data.getStringList(path+"."+name+".blockedbiomes"))
			try {
				biomes.add(Biome.valueOf(biome.toUpperCase()));
			}catch(Exception | NoSuchFieldError e) {}
		return biomes;
	}

	@Override
	public double getChance() {
		return data.getDouble(path+"."+name+".chance");
	}

	@Override
	public String getPermission() {
		String perm = data.getString(path+"."+name+".permission");
		if(perm==null||perm.trim().equals(""))return null;
		return perm;
	}

	@Override
	public FishTime getCatchTime() {
		try {
			return FishTime.valueOf(data.getString(path+"."+name+".catch.time").toUpperCase());
		}catch(Exception | NoSuchFieldError e) {}
		return FishTime.EVERY;
	}

	@Override
	public FishWeather getCatchWeather() {
		try {
			return FishWeather.valueOf(data.getString(path+"."+name+".catch.weather").toUpperCase());
		}catch(Exception | NoSuchFieldError e) {}
		return FishWeather.EVERY;
	}
	
	public Data createData(double weight, double length) {
		return new Data().set("fish", name).set("type", type.name()).set("weigth", weight).set("length", length);
	}
	
	public Data createData(double weight, double length, double money, double points, double exp) {
		return new Data().set("fish", name).set("type", type.name()).set("weigth", weight)
				.set("length", length).set("money", money).set("points", points).set("exp", exp);
	}
	
	public boolean isInstance(Data data) {
		return data.exists("fish") && data.exists("type") && data.getString("fish").equals(name) && data.getString("type").equals(type.name());
	}
	
	public int getModel() {
		return data.getInt(path+"."+name+".model");
	}
	
	@Override
	public ItemStack createItem(double weight, double length) {
		ItemCreatorAPI c = new ItemCreatorAPI(find(type.name(), type.ordinal()));
		String bc = sub(getBiomes().toString()), bbc = sub(getBlockedBiomes().toString()),
				cf = getDisplayName().replace("%weight%", Loader.ff.format(weight))
				.replace("%length%", Loader.ff.format(length))
				.replace("%chance%", Loader.ff.format(getChance()))
				.replace("%biomes%", bc)
				.replace("%blockedbiomes%", bbc)
				.replace("%name%", getName());
		c.setDisplayName(cf);
		List<String> l = data.getStringList(path+"."+name+".lore");
		l.replaceAll(a -> PlaceholderAPI.setPlaceholders(null, a.replace("%weight%", Loader.ff.format(weight))
				.replace("%length%", Loader.ff.format(length))
				.replace("%chance%", Loader.ff.format(getChance()))
				.replace("%name%", cf)
				.replace("%biomes%", bc)
				.replace("%blockedbiomes%", bbc)
				));
		c.setLore(l);
		ItemStack stack = Utils.setModel(c.create(), getModel());
		NBTEdit edit = new NBTEdit(stack);
		edit.setString("af_data", createData(weight, length).toString(DataType.JSON));
		return NMSAPI.setNBT(stack, edit);
	}
	
	@Override
	public ItemStack createItem(double weight, double length, double money, double points, double exp) {
		ItemCreatorAPI c = new ItemCreatorAPI(find(type.name(), type.ordinal()));
		String bc = sub(getBiomes().toString()), bbc = sub(getBlockedBiomes().toString()),
				cf = getDisplayName().replace("%weight%", Loader.ff.format(weight))
				.replace("%length%", Loader.ff.format(length))
				.replace("%chance%", Loader.ff.format(getChance()))
				.replace("%biomes%", bc)
				.replace("%blockedbiomes%", bbc)
				.replace("%money_boost%", Loader.ff.format(money))
				.replace("%points_boost%", Loader.ff.format(points))
				.replace("%exp_boost%", Loader.ff.format(exp))
				.replace("%money_bonus%", Loader.ff.format(money))
				.replace("%points_bonus%", Loader.ff.format(points))
				.replace("%exp_bonus%", Loader.ff.format(exp))
				.replace("%name%", getName());
		c.setDisplayName(cf);
		List<String> l = data.getStringList(path+"."+name+".lore");
		l.replaceAll(a -> PlaceholderAPI.setPlaceholders(null, a.replace("%weight%", Loader.ff.format(weight))
				.replace("%length%", Loader.ff.format(length))
				.replace("%chance%", Loader.ff.format(getChance()))
				.replace("%name%", cf)
				.replace("%biomes%", bc)
				.replace("%blockedbiomes%", bbc)
				.replace("%money_boost%", Loader.ff.format(money))
				.replace("%points_boost%", Loader.ff.format(points))
				.replace("%money_bonus%", Loader.ff.format(money))
				.replace("%points_bonus%", Loader.ff.format(points))
				.replace("%exp_bonus%", Loader.ff.format(exp))
				.replace("%exp_boost%", Loader.ff.format(exp))
				));
		c.setLore(l);
		ItemStack stack = Utils.setModel(c.create(), getModel());
		NBTEdit edit = new NBTEdit(stack);
		edit.setString("af_data", createData(weight, length,money,points,exp).toString(DataType.JSON));
		return NMSAPI.setNBT(stack, edit);
	}

	@Override
	public ItemStack createItem(double weight, double length, Player p, Location hook) {
		ItemCreatorAPI c = new ItemCreatorAPI(find(type.name(), type.ordinal()));
		String bc = sub(getBiomes().toString()), bbc = sub(getBlockedBiomes().toString()),
				cf=s(getDisplayName(),p,hook).replace("%weight%", Loader.ff.format(weight))
				.replace("%length%", Loader.ff.format(length))
				.replace("%chance%", Loader.ff.format(getChance()))
				.replace("%biomes%", bc)
				.replace("%blockedbiomes%", bbc)
				.replace("%name%", getName());
		c.setDisplayName(cf);
		List<String> l = data.getStringList(path+"."+name+".lore");
		l.replaceAll(a -> s(a
				.replace("%weight%", Loader.ff.format(weight))
				.replace("%length%", Loader.ff.format(length))
				.replace("%chance%", Loader.ff.format(getChance()))
				.replace("%name%", cf)
				.replace("%biomes%", bc)
				.replace("%blockedbiomes%", bbc),p,hook));
		c.setLore(l);
		ItemStack stack = Utils.setModel(c.create(), getModel());
		NBTEdit edit = new NBTEdit(stack);
		edit.setString("af_data", createData(weight, length).toString(DataType.JSON));
		return NMSAPI.setNBT(stack, edit);
	}

	@Override
	public ItemStack createItem(double weight, double length, double money, double points, double exp, Player p, Location hook) {
		ItemCreatorAPI c = new ItemCreatorAPI(find(type.name(), type.ordinal()));
		String bc = sub(getBiomes().toString()), bbc = sub(getBlockedBiomes().toString()),
				cf=s(getDisplayName(),p,hook).replace("%weight%", Loader.ff.format(weight))
				.replace("%length%", Loader.ff.format(length))
				.replace("%chance%", Loader.ff.format(getChance()))
				.replace("%biomes%", bc)
				.replace("%blockedbiomes%", bbc)
				.replace("%money_boost%", Loader.ff.format(money))
				.replace("%points_boost%", Loader.ff.format(points))
				.replace("%exp_boost%", Loader.ff.format(exp))
				.replace("%money_bonus%", Loader.ff.format(money))
				.replace("%points_bonus%", Loader.ff.format(points))
				.replace("%exp_bonus%", Loader.ff.format(exp))
				.replace("%name%", getName());
		c.setDisplayName(cf);
		List<String> l = data.getStringList(path+"."+name+".lore");
		l.replaceAll(a -> s(a
				.replace("%weight%", Loader.ff.format(weight))
				.replace("%length%", Loader.ff.format(length))
				.replace("%chance%", Loader.ff.format(getChance()))
				.replace("%name%", cf)
				.replace("%biomes%", bc)
				.replace("%money_boost%", Loader.ff.format(money))
				.replace("%points_boost%", Loader.ff.format(points))
				.replace("%exp_boost%", Loader.ff.format(exp))
				.replace("%money_bonus%", Loader.ff.format(money))
				.replace("%points_bonus%", Loader.ff.format(points))
				.replace("%exp_bonus%", Loader.ff.format(exp))
				.replace("%blockedbiomes%", bbc),p,hook));
		c.setLore(l);
		ItemStack stack = Utils.setModel(c.create(), getModel());
		NBTEdit edit = new NBTEdit(stack);
		edit.setString("af_data", createData(weight, length,money,points,exp).toString(DataType.JSON));
		return NMSAPI.setNBT(stack, edit);
	}

	@Override
	public ItemStack preview(Player p) {
		ItemCreatorAPI c = new ItemCreatorAPI(find(type.name(), type.ordinal()));
		String bc = sub(getBiomes().toString()), bbc = sub(getBlockedBiomes().toString());
		String nn = PlaceholderAPI.setPlaceholders(p, (data.getString(path+"."+name+".preview.name")!=null?data.getString(path+"."+name+".preview.name"):getDisplayName())
				.replace("%weight%", Loader.ff.format(getWeight()))
				.replace("%length%", Loader.ff.format(getLength()))
				.replace("%chance%", Loader.ff.format(getChance()))
				.replace("%biomes%", bc)
				.replace("%blockedbiomes%", bbc)
				.replace("%player%", p.getName())
				.replace("%playername%", p.getDisplayName())
				.replace("%displayname%", p.getDisplayName())
				.replace("%name%", getName()));
		c.setDisplayName(nn);
		List<String> l = data.exists(path+"."+name+".preview.lore")?data.getStringList(path+"."+name+".preview.lore"):data.getStringList(path+"."+name+".lore");
		l.replaceAll(a -> PlaceholderAPI.setPlaceholders(p, a
				.replace("%weight%", Loader.ff.format(getWeight()))
				.replace("%length%", Loader.ff.format(getLength()))
				.replace("%chance%", Loader.ff.format(getChance()))
				.replace("%name%", nn)
				.replace("%biomes%", bc)
				.replace("%blockedbiomes%", bbc)
				.replace("%player%", p.getName())
				.replace("%playername%", p.getDisplayName())
				.replace("%displayname%", p.getDisplayName())));
		c.setLore(l);
		return Utils.setModel(c.create(), getModel());
	}

	private String sub(String s) {
		return s.substring(1,s.length()-1);
	}
	
	private String s(String s, Player p, Location l) {
		return PlaceholderAPI.setPlaceholders(p, s
				.replace("%player%", p.getName())
				.replace("%playername%", p.getDisplayName())
				.replace("%displayname%", p.getDisplayName())
				.replace("%biome%",l.getBlock().getBiome().name())
				.replace("%x%", ""+l.getBlockX())
				.replace("%y%", ""+l.getBlockY())
				.replace("%z%", ""+l.getBlockZ())
				.replace("%world%", l.getWorld().getName()));
	}
	
	private ItemStack find(String name, int id) {
		if(Material.getMaterial(name)!=null)return new ItemStack(Material.getMaterial(name));
		return new ItemStack(Material.getMaterial("RAW_FISH"),1,(short)id);
	}

	@Override
	public double getWeight() {
		return data.getDouble(path+"."+name+".weight");
	}

	@Override
	public double getLength() {
		return data.getDouble(path+"."+name+".length");
	}

	@Override
	public double getMinWeight() {
		return data.getDouble(path+"."+name+".minweight");
	}

	@Override
	public double getMinLength() {
		return data.getDouble(path+"."+name+".minlength");
	}
	
	@Override
	public double getMoney() {
		return data.getDouble(path+"."+name+".money");
	}

	@Override
	public double getPoints() {
		return data.getDouble(path+"."+name+".points");
	}

	@Override
	public double getXp() {
		return data.getDouble(path+"."+name+".xp");
	}

	@Override
	public CatchFish createCatchFish(Data data) {
		return new CatchFish() {
			
			@Override
			public double getWeight() {
				return data.getDouble("weight");
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

			@Override
			public double getMoneyBoost() {
				return data.getDouble("money");
			}

			@Override
			public double getPointsBoost() {
				return data.getDouble("points");
			}

			@Override
			public double getExpBoost() {
				return data.getDouble("exp");
			}
		};
	}

	@Override
	public String getCalculator(Calculator type) {
		String ff = data.getString(path+"."+name+".calculator."+type.name().toLowerCase());
		if(ff!=null)return ff;
		return Loader.config.getString("Options.Calculator."+type.name().toLowerCase());
	}
}

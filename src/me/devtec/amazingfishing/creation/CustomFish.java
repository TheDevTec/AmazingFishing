package me.devtec.amazingfishing.creation;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Calculator;
import me.devtec.amazingfishing.construct.CatchFish;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.construct.FishAction;
import me.devtec.amazingfishing.construct.FishTime;
import me.devtec.amazingfishing.construct.FishType;
import me.devtec.amazingfishing.construct.FishWeather;
import me.devtec.amazingfishing.utils.Create;
import me.devtec.amazingfishing.utils.EnchantmentAPI;
import me.devtec.amazingfishing.utils.ItemCreatorAPI;
import me.devtec.amazingfishing.utils.Utils;
import me.devtec.shared.dataholder.Config;
import me.devtec.shared.dataholder.DataType;
import me.devtec.shared.json.Json;
import me.devtec.shared.placeholders.PlaceholderAPI;
import me.devtec.theapi.bukkit.BukkitLoader;
import me.devtec.theapi.bukkit.nms.NBTEdit;

public class CustomFish implements Fish {
	final String name, path;
	final Config data;
	final FishType type;
	String item, showItem;
	
	public CustomFish(String name, String path, FishType type, Config data) {
		this.name=name;
		this.type=type;
		this.path=path.toLowerCase();
		this.data=data;
		if(data.exists(path+"."+name+".head"))
			this.item="head:"+data.getString(path+"."+name+".head");
		else
			if(data.exists(path+"."+name+".icon"))
				this.item=data.getString(path+"."+name+".icon");
			else
				this.item=data.getString(path+"."+name+".type");
		this.showItem=data.getString(path+"."+name+".preview.type")!=null?
				data.getString(path+"."+name+".preview.type"):data.getString(path+"."+name+".preview.icon");
		if(item==null)item=type.name();
		if(showItem==null)showItem=item;
	}
	
	public String toString() {
		Map<String, Object> map = new ConcurrentHashMap<>();
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
		return Json.writer().simpleWrite(map);
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
	public List<String> getEnchantments() {
		return data.getStringList(path+"."+name+".enchants");
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
	
	public Config createData(double weight, double length) {
		return new Config().set("fish", name).set("type", type.name()).set("weigth", weight).set("length", length);
	}
	
	public Config createData(double weight, double length, double money, double points, double exp) {
		return new Config().set("fish", name).set("type", type.name()).set("weigth", weight)
				.set("length", length).set("money", money).set("points", points).set("exp", exp);
	}
	
	public boolean isInstance(Config data) {
		return data.exists("fish") && data.exists("type") && data.getString("fish").equals(name) && data.getString("type").equals(type.name());
	}
	
	public int getModel() {
		return data.getInt(path+"."+name+".model");
	}

	@Override
	public boolean isFood() {
		return data.getBoolean(path+"."+name+".options.eatable");
	}
	
	@Override
	public ItemStack createItem(double weight, double length) {
		ItemCreatorAPI c = Create.find(item, type.name(), type.ordinal());
		String bc = sub(getBiomes().toString()), bbc = sub(getBlockedBiomes().toString()),
				cf = getDisplayName().replace("%weight%", Loader.ff.format(weight))
				.replace("%length%", Loader.ff.format(length))
				.replace("%chance%", Loader.ff.format(getChance()))
				.replace("%biomes%", bc)
				.replace("%blockedbiomes%", bbc)
				.replace("%name%", getName());
		c.setDisplayName(cf);
		List<String> l = data.getStringList(path+"."+name+".lore");
		l.replaceAll(a -> PlaceholderAPI.apply(a.replace("%weight%", Loader.ff.format(weight))
				.replace("%length%", Loader.ff.format(length))
				.replace("%chance%", Loader.ff.format(getChance()))
				.replace("%name%", cf)
				.replace("%biomes%", bc)
				.replace("%blockedbiomes%", bbc)
				,null));
		c.setLore(l);
		c.setUnbreakable(data.getBoolean(path+"."+name+".unbreakable"));
		for(String itemFlag : data.getStringList(path+"."+name+".flags"))
			try {
				c.addItemFlag(ItemFlag.valueOf(itemFlag.toUpperCase()));
			}catch(Exception | NoSuchFieldError | NoClassDefFoundError | NoSuchMethodError err) {
				
			}
		for(String enchant : getEnchantments()) {
			if(EnchantmentAPI.byName(enchant)!=null)
				c.addEnchantment(EnchantmentAPI.byName(enchant).getEnchantment(), 1);
		}
		ItemStack stack = Utils.setModel(c.create(), getModel());
		NBTEdit edit = new NBTEdit(stack);
		edit.setString("af_data", createData(weight, length).toString(DataType.JSON));
		return BukkitLoader.getNmsProvider().setNBT(stack, edit);
	}
	
	@Override
	public ItemStack createItem(double weight, double length, double money, double points, double exp) {
		ItemCreatorAPI c = Create.find(item, type.name(), type.ordinal());
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
		l.replaceAll(a -> PlaceholderAPI.apply(a.replace("%weight%", Loader.ff.format(weight))
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
				, null));
		c.setLore(l);
		for(String enchant : getEnchantments()) {
			if(EnchantmentAPI.byName(enchant)!=null)
				c.addEnchantment(EnchantmentAPI.byName(enchant).getEnchantment(), 1);
		}
		c.setUnbreakable(data.getBoolean(path+"."+name+".unbreakable"));
		for(String itemFlag : data.getStringList(path+"."+name+".flags"))
			try {
				c.addItemFlag(ItemFlag.valueOf(itemFlag.toUpperCase()));
			}catch(Exception | NoSuchFieldError | NoClassDefFoundError | NoSuchMethodError err) {
				
			}
		ItemStack stack = Utils.setModel(c.create(), getModel());
		NBTEdit edit = new NBTEdit(stack);
		edit.setString("af_data", createData(weight, length,money,points,exp).toString(DataType.JSON));
		return BukkitLoader.getNmsProvider().setNBT(stack, edit);
	}

	@Override
	public ItemStack createItem(double weight, double length, Player p, Location hook) {
		ItemCreatorAPI c = Create.find(item, type.name(), type.ordinal());
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
		for(String enchant : getEnchantments()) {
			if(EnchantmentAPI.byName(enchant)!=null)
				c.addEnchantment(EnchantmentAPI.byName(enchant).getEnchantment(), 1);
		}
		c.setUnbreakable(data.getBoolean(path+"."+name+".unbreakable"));
		for(String itemFlag : data.getStringList(path+"."+name+".flags"))
			try {
				c.addItemFlag(ItemFlag.valueOf(itemFlag.toUpperCase()));
			}catch(Exception | NoSuchFieldError | NoClassDefFoundError | NoSuchMethodError err) {
				
			}
		ItemStack stack = Utils.setModel(c.create(), getModel());
		NBTEdit edit = new NBTEdit(stack);
		edit.setString("af_data", createData(weight, length).toString(DataType.JSON));
		return BukkitLoader.getNmsProvider().setNBT(stack, edit);
	}

	@Override
	public ItemStack createItem(double weight, double length, double money, double points, double exp, Player p, Location hook) {
		ItemCreatorAPI c = Create.find(item, type.name(), type.ordinal());
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
		for(String enchant : getEnchantments()) {
			if(EnchantmentAPI.byName(enchant)!=null)
				c.addEnchantment(EnchantmentAPI.byName(enchant).getEnchantment(), 1);
		}
		c.setUnbreakable(data.getBoolean(path+"."+name+".unbreakable"));
		for(String itemFlag : data.getStringList(path+"."+name+".flags"))
			try {
				c.addItemFlag(ItemFlag.valueOf(itemFlag.toUpperCase()));
			}catch(Exception | NoSuchFieldError | NoClassDefFoundError | NoSuchMethodError err) {
				
			}
		ItemStack stack = Utils.setModel(c.create(), getModel());
		NBTEdit edit = new NBTEdit(stack);
		edit.setString("af_data", createData(weight, length,money,points,exp).toString(DataType.JSON));
		return BukkitLoader.getNmsProvider().setNBT(stack, edit);
	}

	@Override
	public ItemStack preview(Player p) {
		ItemCreatorAPI c = Create.find(showItem, type.name(), type.ordinal());
		String bc = sub(getBiomes().toString()), bbc = sub(getBlockedBiomes().toString());
		String nn = PlaceholderAPI.apply((data.getString(path+"."+name+".preview.name")!=null?data.getString(path+"."+name+".preview.name"):getDisplayName())
				.replace("%weight%", Loader.ff.format(getWeight()))
				.replace("%length%", Loader.ff.format(getLength()))
				.replace("%chance%", Loader.ff.format(getChance()))
				.replace("%biomes%", bc)
				.replace("%blockedbiomes%", bbc)
				.replace("%player%", p.getName())
				.replace("%playername%", p.getDisplayName())
				.replace("%displayname%", p.getDisplayName())
				.replace("%name%", getName())
				.replace("%fishname%", getDisplayName()), p.getUniqueId());
		c.setDisplayName(nn);
		c.setUnbreakable(data.exists(path+"."+name+".preview.unbreakable")?data.getBoolean(path+"."+name+".preview.unbreakable"):data.getBoolean(path+"."+name+".unbreakable"));
		for(String itemFlag : data.exists(path+"."+name+".preview.flags")?data.getStringList(path+"."+name+".preview.flags"):data.getStringList(path+"."+name+".flags"))
			try {
				c.addItemFlag(ItemFlag.valueOf(itemFlag.toUpperCase()));
			}catch(Exception | NoSuchFieldError | NoClassDefFoundError | NoSuchMethodError err) {
				
			}
		List<String> l = data.exists(path+"."+name+".preview.lore")?data.getStringList(path+"."+name+".preview.lore"):data.getStringList(path+"."+name+".lore");
		l.replaceAll(a -> PlaceholderAPI.apply(a
				.replace("%weight%", Loader.ff.format(getWeight()))
				.replace("%length%", Loader.ff.format(getLength()))
				.replace("%chance%", Loader.ff.format(getChance()))
				.replace("%name%", nn)
				.replace("%biomes%", bc)
				.replace("%blockedbiomes%", bbc)
				.replace("%player%", p.getName())
				.replace("%playername%", p.getDisplayName())
				.replace("%displayname%", p.getDisplayName()), p.getUniqueId()));
		c.setLore(l);
		for(String enchant : data.exists(path+"."+name+".preview.enchants")?data.getStringList(path+"."+name+".preview.enchants"):data.getStringList(path+"."+name+".enchants")) {
			if(EnchantmentAPI.byName(enchant)!=null)
				c.addEnchantment(EnchantmentAPI.byName(enchant).getEnchantment(), 1);
		}
		return Utils.setModel(c.create(), data.exists(path+"."+name+".preview.model")?data.getInt(path+"."+name+".preview.model"):data.getInt(path+"."+name+".model"));
		}

	private String sub(String s) {
		return s.substring(1,s.length()-1);
	}
	
	private String s(String s, Player p, Location l) {
		return PlaceholderAPI.apply(s
				.replace("%player%", p.getName())
				.replace("%playername%", p.getDisplayName())
				.replace("%displayname%", p.getDisplayName())
				.replace("%biome%",l.getBlock().getBiome().name())
				.replace("%x%", ""+l.getBlockX())
				.replace("%y%", ""+l.getBlockY())
				.replace("%z%", ""+l.getBlockZ())
				.replace("%world%", l.getWorld().getName()), p.getUniqueId());
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
	public double getFood() {
		if(data.exists(path+"."+name+".options.addhunger") )
			return data.getDouble(path+"."+name+".options.addhunger");
		else
			return 1;
	}
	
	@Override
	public CatchFish createCatchFish(Config data) {
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

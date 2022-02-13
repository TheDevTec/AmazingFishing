package me.devtec.amazingfishing.creation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Calculator;
import me.devtec.amazingfishing.construct.FishAction;
import me.devtec.amazingfishing.construct.FishTime;
import me.devtec.amazingfishing.construct.FishType;
import me.devtec.amazingfishing.construct.FishWeather;
import me.devtec.amazingfishing.construct.Junk;
import me.devtec.amazingfishing.utils.Utils;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.apis.ItemCreatorAPI;
import me.devtec.theapi.placeholderapi.PlaceholderAPI;
import me.devtec.theapi.utils.datakeeper.Data;
import me.devtec.theapi.utils.datakeeper.DataType;
import me.devtec.theapi.utils.json.Json;
import me.devtec.theapi.utils.nms.nbt.NBTEdit;

public class CustomJunk implements Junk {
	
	final String name, path;
	final Data data;
	String item, showItem;
	
	public CustomJunk(String name, String path, Data data) {
		this.name=name;
		this.path=path.toLowerCase();
		this.data=data;
		if(data.exists(path+"."+name+".head"))
			this.item="head:"+data.getString(path+"."+name+".head");
		else
			this.item=data.getString(path+"."+name+".type");
		this.showItem=data.getString(path+"."+name+".preview.type");
		if(showItem==null)showItem=item;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDisplayName() {
		if(data.exists(path+"."+name+".name"))
			return data.getString(path+"."+name+".name");
		else
			return null;
	}
	
	@Override
	public FishType getType() {
		return FishType.JUNK;
	}

	@Override
	public List<String> getLore(){
		return data.getStringList(path+"."+name+".lore");
	}
	
	@Override
	public int getModel() {
		return data.getInt(path+"."+name+".model");
	}

	@Override
	public int getAmount() {
		if(data.exists(path+"."+name+".amount"))
			return data.getInt(path+"."+name+".amount");
		else
			return 1;
	}
	
	@Override
	public List<String> getEnchantments() {
		return data.getStringList(path+"."+name+".enchants");
	}
	
	@Override
	public List<ItemFlag> getFlags() {
		List<ItemFlag> list = new ArrayList<>();
		if(data.exists(path+"."+name+".flags")) {
			for(String flag: data.getStringList(path+"."+name+".flags")) {
				try {
					list.add(ItemFlag.valueOf(flag));
				}catch (Exception | NoSuchFieldError | NoSuchMethodError | NoClassDefFoundError er) {
					Bukkit.getLogger().warning("Error when getting junk:" + name + "! Itemflag " + flag + ", itemflag is invalid");
				}
			}
			return list;
		}
		return list;
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
	public String getCalculator(Calculator type) {
		String ff = data.getString(path+"."+name+".calculator."+type.name().toLowerCase());
		if(ff!=null)return ff;
		return Loader.config.getString("Options.Calculator."+type.name().toLowerCase());
	}

	@Override
	public double getChance() {
		return data.getDouble(path+"."+name+".chance");
	}

	@Override
	public String getPermission() {
		return data.getString(path+"."+name+".permission");
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

	@Override
	public double getWeight() {
		if(data.exists(path+"."+name+".weight"))
			return data.getDouble(path+"."+name+".weight");
		else
			return -1;
	}

	@Override
	public double getLength() {
		if(data.exists(path+"."+name+".length"))
			return data.getDouble(path+"."+name+".length");
		else
			return -1;
	}

	@Override
	public double getMinWeight() {
		return data.getDouble(path+"."+name+".minweigth");
	}

	@Override
	public double getMinLength() {
		return data.getDouble(path+"."+name+".minlength");
	}

	@Override
	public double getFood() {
		if(data.exists(path+"."+name+".options.addhunger") )
			return data.getDouble(path+"."+name+".options.addhunger");
		else
			return 1;
	}

	@Override
	public boolean isFood() {
		return data.getBoolean(path+"."+name+".options.eatable");
	}
	
	@Override
	public boolean hasWeight() {
		if(data.exists(path+"."+name+".options.weight") && getWeight()!=-1)
			return data.getBoolean(path+"."+name+".options.weight");
		else
			return false;
	}
	
	@Override
	public boolean hasLength() {
		if(data.exists(path+"."+name+".options.length") && getLength()!=-1)
			return data.getBoolean(path+"."+name+".options.length");
		else
			return false;
	}
	
	@Override
	public boolean isInstance(Data data) {
		return data.exists("junk") && data.exists("type") && data.getString("junk").equals(name) && data.getString("type").equals("JUNK");
	}
	
	@Override
	public boolean show() {
		return data.getBoolean(path+"."+name+".preview.show");
	}
	
	@Override
	public ItemStack createItem(Player p, Location hook) {
		return createItem(-1, -1, p, hook);
	}
	
	@Override
	public ItemStack create(double weight, double length, Player p, Location hook) {
		if(weight<=0 || length<=0 || !hasLength() || !hasWeight())
			return createItem(-1, -1, p, hook);
		return createItem(weight, length, p, hook);
	}

	@Override
	public ItemStack createItem(double weight, double length, Player p, Location hook) {
		ItemCreatorAPI c = CustomFish.find("STICK", 0, item);
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
		c.setUnbreakable(data.getBoolean(path+"."+name+".unbreakable"));
		for(String itemFlag : data.getStringList(path+"."+name+".flags"))
			try {
				c.addItemFlag(ItemFlag.valueOf(itemFlag));
			}catch(Exception | NoSuchFieldError err) {
				
			}
		ItemStack stack = Utils.setModel(c.create(), getModel());
		NBTEdit edit = new NBTEdit(stack);
		edit.setString("af_data", createData(weight, length).toString(DataType.JSON));
		return TheAPI.getNmsProvider().setNBT(stack, edit);
	}

	@Override
	public ItemStack preview(Player p) {
		ItemCreatorAPI c = CustomFish.find("STICK", 0, showItem);
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
				.replace("%name%", getName())
				.replace("%fishname%", getDisplayName()));
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
		c.setUnbreakable(data.exists(path+"."+name+".preview.unbreakable")?data.getBoolean(path+"."+name+".preview.unbreakable"):data.getBoolean(path+"."+name+".unbreakable"));
		for(String itemFlag : data.exists(path+"."+name+".preview.flags")?data.getStringList(path+"."+name+".preview.flags"):data.getStringList(path+"."+name+".flags"))
			try {
				c.addItemFlag(ItemFlag.valueOf(itemFlag));
			}catch(Exception | NoSuchFieldError err) {
				
			}
		return Utils.setModel(c.create(), data.exists(path+"."+name+".preview.model")?data.getInt(path+"."+name+".preview.model"):data.getInt(path+"."+name+".model"));
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
	
	public Data createData(double weight, double length) {
		return new Data().set("junk", name).set("type", getType().name()).set("item", item).set("weigth", weight).set("length", length);
	}

	public Data createData() {
		return new Data().set("junk", name).set("type", getType().name()).set("item", item);
	}
	
	public String toString() {
		Map<String, Object> map = new HashMap<>();
		map.put("type", "Junk");
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
		map.put("item", item);
		map.put("model", getModel());
		return Json.writer().simpleWrite(map);
	}
	
	public boolean equals(Object o) {
		if(o instanceof Junk) {
			if(o instanceof CustomJunk) {
				return ((CustomJunk) o).data.equals(data) && name.equals(((CustomJunk) o).name);
			}else {
				return ((Junk) o).getName().equals(name);
			}
		}
		return false;
	}
}

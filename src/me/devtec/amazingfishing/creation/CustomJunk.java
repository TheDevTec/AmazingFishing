package me.devtec.amazingfishing.creation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Calculator;
import me.devtec.amazingfishing.construct.FishAction;
import me.devtec.amazingfishing.construct.FishTime;
import me.devtec.amazingfishing.construct.FishType;
import me.devtec.amazingfishing.construct.FishWeather;
import me.devtec.amazingfishing.construct.Junk;
import me.devtec.amazingfishing.utils.Create;
import me.devtec.amazingfishing.utils.EnchantmentAPI;
import me.devtec.amazingfishing.utils.Utils;
import me.devtec.shared.dataholder.Config;
import me.devtec.shared.dataholder.DataType;
import me.devtec.shared.json.Json;
import me.devtec.shared.placeholders.PlaceholderAPI;
import me.devtec.theapi.bukkit.BukkitLoader;
import me.devtec.theapi.bukkit.game.ItemMaker;
import me.devtec.theapi.bukkit.nms.NBTEdit;

public class CustomJunk implements Junk {

	final String name, path;
	final Config data;
	String item, showItem;

	public CustomJunk(String name, String path, Config data) {
		this.name = name;
		this.path = path.toLowerCase();
		this.data = data;
		if (data.exists(path + "." + name + ".head"))
			item = "head:" + data.getString(path + "." + name + ".head");
		else if (data.exists(path + "." + name + ".icon"))
			item = data.getString(path + "." + name + ".icon");
		else
			item = data.getString(path + "." + name + ".type");
		showItem = data.getString(path + "." + name + ".preview.type") != null ? data.getString(path + "." + name + ".preview.type") : data.getString(path + "." + name + ".preview.icon");
		if (item == null)
			item = "STONE";
		if (showItem == null)
			showItem = item;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDisplayName() {
		if (data.exists(path + "." + name + ".name"))
			return data.getString(path + "." + name + ".name");
		return null;
	}

	@Override
	public FishType getType() {
		return FishType.JUNK;
	}

	@Override
	public List<String> getLore() {
		return data.getStringList(path + "." + name + ".lore");
	}

	@Override
	public int getModel() {
		return data.getInt(path + "." + name + ".model");
	}

	@Override
	public int getAmount() {
		if (data.exists(path + "." + name + ".amount"))
			return data.getInt(path + "." + name + ".amount");
		return 1;
	}

	@Override
	public List<String> getEnchantments() {
		return data.getStringList(path + "." + name + ".enchants");
	}

	@Override
	public List<String> getMessages(FishAction action) {
		return data.getStringList(path + "." + name + ".messages." + action.name().toLowerCase());
	}

	@Override
	public List<String> getCommands(FishAction action) {
		return data.getStringList(path + "." + name + ".commands." + action.name().toLowerCase());
	}

	@Override
	public List<Biome> getBiomes() {
		List<Biome> biomes = new ArrayList<>();
		for (String biome : data.getStringList(path + "." + name + ".biomes"))
			try {
				biomes.add(Biome.valueOf(biome.toUpperCase()));
			} catch (Exception | NoSuchFieldError e) {
			}
		return biomes;
	}

	@Override
	public List<Biome> getBlockedBiomes() {
		List<Biome> biomes = new ArrayList<>();
		for (String biome : data.getStringList(path + "." + name + ".blockedbiomes"))
			try {
				biomes.add(Biome.valueOf(biome.toUpperCase()));
			} catch (Exception | NoSuchFieldError e) {
			}
		return biomes;
	}

	@Override
	public String getCalculator(Calculator type) {
		String ff = data.getString(path + "." + name + ".calculator." + type.name().toLowerCase());
		if (ff != null)
			return ff;
		return Loader.config.getString("Options.Calculator." + type.name().toLowerCase());
	}

	@Override
	public double getChance() {
		return data.getDouble(path + "." + name + ".chance");
	}

	@Override
	public String getPermission() {
		return data.getString(path + "." + name + ".permission");
	}

	@Override
	public FishTime getCatchTime() {
		try {
			return FishTime.valueOf(data.getString(path + "." + name + ".catch.time").toUpperCase());
		} catch (Exception | NoSuchFieldError e) {
		}
		return FishTime.EVERY;
	}

	@Override
	public FishWeather getCatchWeather() {
		try {
			return FishWeather.valueOf(data.getString(path + "." + name + ".catch.weather").toUpperCase());
		} catch (Exception | NoSuchFieldError e) {
		}
		return FishWeather.EVERY;
	}

	@Override
	public double getWeight() {
		if (data.exists(path + "." + name + ".weight"))
			return data.getDouble(path + "." + name + ".weight");
		return -1;
	}

	@Override
	public double getLength() {
		if (data.exists(path + "." + name + ".length"))
			return data.getDouble(path + "." + name + ".length");
		return -1;
	}

	@Override
	public double getMinWeight() {
		return data.getDouble(path + "." + name + ".minweigth");
	}

	@Override
	public double getMinLength() {
		return data.getDouble(path + "." + name + ".minlength");
	}

	@Override
	public double getFood() {
		if (data.exists(path + "." + name + ".options.addhunger"))
			return data.getDouble(path + "." + name + ".options.addhunger");
		return 1;
	}

	@Override
	public boolean isFood() {
		return data.getBoolean(path + "." + name + ".options.eatable");
	}

	@Override
	public boolean hasWeight() {
		if (data.exists(path + "." + name + ".options.weight") && getWeight() != -1)
			return data.getBoolean(path + "." + name + ".options.weight");
		return false;
	}

	@Override
	public boolean hasLength() {
		if (data.exists(path + "." + name + ".options.length") && getLength() != -1)
			return data.getBoolean(path + "." + name + ".options.length");
		return false;
	}

	@Override
	public boolean isInstance(Config data) {
		return data.exists("junk") && data.exists("type") && data.getString("junk").equals(name) && data.getString("type").equals("JUNK");
	}

	@Override
	public boolean show() {
		return data.getBoolean(path + "." + name + ".preview.show");
	}

	@Override
	public ItemStack createItem(Player p, Location hook) {
		return createItem(-1, -1, p, hook);
	}

	@Override
	public ItemStack create(double weight, double length, Player p, Location hook) {
		if (weight <= 0 || length <= 0 || !hasLength() || !hasWeight())
			return createItem(-1, -1, p, hook);
		return createItem(weight, length, p, hook);
	}

	@Override
	public ItemStack createItem(double weight, double length, Player p, Location hook) {
		ItemMaker c = Create.find(item, "STONE", 0);
		String bc = sub(getBiomes().toString()), bbc = sub(getBlockedBiomes().toString()), cf = s(getDisplayName(), p, hook).replace("%weight%", Loader.ff.format(weight))
				.replace("%length%", Loader.ff.format(length)).replace("%chance%", Loader.ff.format(getChance())).replace("%biomes%", bc).replace("%blockedbiomes%", bbc).replace("%name%", getName());
		c.displayName(cf);
		List<String> l = data.getStringList(path + "." + name + ".lore");
		l.replaceAll(a -> s(a.replace("%weight%", Loader.ff.format(weight)).replace("%length%", Loader.ff.format(length)).replace("%chance%", Loader.ff.format(getChance())).replace("%name%", cf)
				.replace("%biomes%", bc).replace("%blockedbiomes%", bbc), p, hook));
		c.lore(l);
		c.unbreakable(data.getBoolean(path + "." + name + ".unbreakable"));
		for (String enchant : getEnchantments())
			if (EnchantmentAPI.byName(enchant) != null)
				c.enchant(EnchantmentAPI.byName(enchant).getEnchantment(), 1);
		c.itemFlags(data.getStringList(path + "." + name + ".flags"));
		ItemStack stack = Utils.setModel(c.build(), getModel());
		NBTEdit edit = new NBTEdit(stack);
		edit.setString("af_data", createData(weight, length).toString(DataType.JSON));
		return BukkitLoader.getNmsProvider().setNBT(stack, edit);
	}

	@Override
	public ItemStack preview(Player p) {
		ItemMaker c = Create.find(showItem, "STONE", 0);
		String bc = sub(getBiomes().toString()), bbc = sub(getBlockedBiomes().toString());
		String nn = PlaceholderAPI.apply((data.getString(path + "." + name + ".preview.name") != null ? data.getString(path + "." + name + ".preview.name") : getDisplayName())
				.replace("%weight%", Loader.ff.format(getWeight())).replace("%length%", Loader.ff.format(getLength())).replace("%chance%", Loader.ff.format(getChance())).replace("%biomes%", bc)
				.replace("%blockedbiomes%", bbc).replace("%player%", p.getName()).replace("%playername%", p.getDisplayName()).replace("%displayname%", p.getDisplayName()).replace("%name%", getName())
				.replace("%fishname%", getDisplayName()), p.getUniqueId());
		c.displayName(nn);
		List<String> l = data.exists(path + "." + name + ".preview.lore") ? data.getStringList(path + "." + name + ".preview.lore") : data.getStringList(path + "." + name + ".lore");
		l.replaceAll(a -> PlaceholderAPI.apply(a.replace("%weight%", Loader.ff.format(getWeight())).replace("%length%", Loader.ff.format(getLength()))
				.replace("%chance%", Loader.ff.format(getChance())).replace("%name%", nn).replace("%biomes%", bc).replace("%blockedbiomes%", bbc).replace("%player%", p.getName())
				.replace("%playername%", p.getDisplayName()).replace("%displayname%", p.getDisplayName()), p.getUniqueId()));
		c.lore(l);
		for (String enchant : data.exists(path + "." + name + ".preview.enchants") ? data.getStringList(path + "." + name + ".preview.enchants") : data.getStringList(path + "." + name + ".enchants"))
			if (EnchantmentAPI.byName(enchant) != null)
				c.enchant(EnchantmentAPI.byName(enchant).getEnchantment(), 1);
		c.unbreakable(data.exists(path + "." + name + ".preview.unbreakable") ? data.getBoolean(path + "." + name + ".preview.unbreakable") : data.getBoolean(path + "." + name + ".unbreakable"));
		c.itemFlags(data.exists(path + "." + name + ".preview.flags") ? data.getStringList(path + "." + name + ".preview.flags") : data.getStringList(path + "." + name + ".flags"));
		return Utils.setModel(c.build(), data.exists(path + "." + name + ".preview.model") ? data.getInt(path + "." + name + ".preview.model") : data.getInt(path + "." + name + ".model"));
	}

	private String sub(String s) {
		return s.substring(1, s.length() - 1);

	}

	private String s(String s, Player p, Location l) {
		return PlaceholderAPI
				.apply(s.replace("%player%", p.getName()).replace("%playername%", p.getDisplayName()).replace("%displayname%", p.getDisplayName()).replace("%biome%", l.getBlock().getBiome().name())
						.replace("%x%", "" + l.getBlockX()).replace("%y%", "" + l.getBlockY()).replace("%z%", "" + l.getBlockZ()).replace("%world%", l.getWorld().getName()), p.getUniqueId());
	}

	public Config createData(double weight, double length) {
		return new Config().set("junk", name).set("type", getType().name()).set("item", item).set("weigth", weight).set("length", length);
	}

	public Config createData() {
		return new Config().set("junk", name).set("type", getType().name()).set("item", item);
	}

	@Override
	public String toString() {
		Map<String, Object> map = new ConcurrentHashMap<>();
		map.put("type", "Junk");
		map.put("name", getName());
		map.put("chance", getChance());
		map.put("fish", getType().name().toLowerCase());
		if (getPermission() != null)
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

	@Override
	public boolean equals(Object o) {
		if (o instanceof Junk)
			if (o instanceof CustomJunk)
				return ((CustomJunk) o).data.equals(data) && name.equals(((CustomJunk) o).name);
			else
				return ((Junk) o).getName().equals(name);
		return false;
	}
}

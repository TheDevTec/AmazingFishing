package me.devtec.amazingfishing.creation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Calculator;
import me.devtec.amazingfishing.construct.FishAction;
import me.devtec.amazingfishing.construct.FishTime;
import me.devtec.amazingfishing.construct.FishWeather;
import me.devtec.amazingfishing.construct.Junk;
import me.devtec.amazingfishing.utils.HDBSupport;
import me.devtec.amazingfishing.utils.Utils;
import me.devtec.theapi.apis.EnchantmentAPI;
import me.devtec.theapi.apis.ItemCreatorAPI;
import me.devtec.theapi.placeholderapi.PlaceholderAPI;
import me.devtec.theapi.utils.StringUtils;
import me.devtec.theapi.utils.datakeeper.Data;
import me.devtec.theapi.utils.datakeeper.DataType;
import me.devtec.theapi.utils.nms.NMSAPI;
import me.devtec.theapi.utils.nms.nbt.NBTEdit;

public class CustomJunk implements Junk{
	
	final String name, path;
	final Data data;
	boolean head, item;
	
	public CustomJunk(String name, String path, Data data) {
		this.name=name;
		this.path=path.toLowerCase();
		this.data=data;

		if(data.exists(path+"."+name+".name")&& (data.exists(path+"."+name+".type") ||data.exists(path+"."+name+".head")))
			this.item=true;
		else
			this.item=false;
		
		if(data.exists(path+"."+name+".head")&&this.item==true)
			this.head=true;
		else
			this.head=false;
	}

	/*
	 *  ITEM
	 */
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDisplayName() {
		return data.getString(path+"."+name+".name");
	}

	@Override
	public Material getType() {
		if(data.exists(path+"."+name+".type") && !isHead())
			return Material.valueOf(data.getString(path+"."+name+".type"));
		if(isHead())
			return Material.PLAYER_HEAD;
		else
			return Material.AIR;
	}

	@Override
	public List<String> getLore(){
		return data.getStringList(path+"."+name+".lore");
	}
	
	@Override
	public String getHead() {
		return data.getString(path+"."+name+".head");
	}

	@Override
	public boolean isHead() {
		return this.head;
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
	
	/*
	 * OTHER
	 */
	
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
		if(data.exists(path+"."+name+".options.eatable"))
			return data.getBoolean(path+"."+name+".options.eatable");
		else
			return false;
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
		return data.exists("junk") && data.exists("type") && data.getString("junk").equals(name) && data.getString("type").equals(getType().name());
	}
	
	@Override
	public boolean show() {
		return data.getBoolean(path+"."+name+".preview.show");
	}
	
	@Override
	public ItemStack create(double weight, double length, Player p, Location hook) {
		if(this.item==false)
			return null;
		
		if(weight==0 || length==0)
			return createItem(p, hook);
		
		if( !hasLength() || !hasWeight())
			return createItem(p, hook);
		
		return createItem(weight, length, p, hook);
	}

	@Override
	public ItemStack createItem(double weight, double length, Player p, Location hook) {
		ItemCreatorAPI c = new ItemCreatorAPI(find(getType(), getType().ordinal()));
		fixHead(c);
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
		c.setAmount(getAmount());
		
		for(ItemFlag flag : getFlags())
			c.addItemFlag(flag);
		
		for (String enchs : getEnchantments()) {
			String nonum = enchs.replaceAll("[^A-Za-z_]+", "").toUpperCase();
			if (EnchantmentAPI.byName(nonum)==null)
				Bukkit.getLogger().warning("Error when getting junk: " + name + "!! Enchantment " + enchs + " (Converted to "+nonum+"), enchantment is invalid");
			else
				c.addEnchantment(nonum, StringUtils.getInt(enchs.replaceAll("[^+0-9]+", ""))<=0?1:StringUtils.getInt(enchs.replaceAll("[^+0-9]+", "")));
		}
		
		ItemStack stack = Utils.setModel(c.create(), getModel());
		NBTEdit edit = new NBTEdit(stack);
		edit.setString("af_data", createData(weight, length).toString(DataType.JSON));
		return NMSAPI.setNBT(stack, edit);
	}
	@Override
	public ItemStack createItem(Player p, Location hook) {
		ItemCreatorAPI c = new ItemCreatorAPI(find(getType(), getType().ordinal()));
		fixHead(c);
		String bc = sub(getBiomes().toString()), bbc = sub(getBlockedBiomes().toString()),
				cf=s(getDisplayName(),p,hook).replace("%weight%", Loader.ff.format(-1))
				.replace("%length%", Loader.ff.format(-1))
				.replace("%chance%", Loader.ff.format(getChance()))
				.replace("%biomes%", bc)
				.replace("%blockedbiomes%", bbc)
				.replace("%name%", getName());
		c.setDisplayName(cf);
		List<String> l = data.getStringList(path+"."+name+".lore");
		l.replaceAll(a -> s(a
				.replace("%weight%", Loader.ff.format(-1))
				.replace("%length%", Loader.ff.format(-1))
				.replace("%chance%", Loader.ff.format(getChance()))
				.replace("%name%", cf)
				.replace("%biomes%", bc)
				.replace("%blockedbiomes%", bbc),p,hook));
		c.setLore(l);
		c.setAmount(getAmount());
		
		for(ItemFlag flag : getFlags())
			c.addItemFlag(flag);
		
		for (String enchs : getEnchantments()) {
			String nonum = enchs.replaceAll("[^A-Za-z_]+", "").toUpperCase();
			if (EnchantmentAPI.byName(nonum)==null)
				Bukkit.getLogger().warning("Error when getting junk: " + name + "!! Enchantment " + enchs + " (Converted to "+nonum+"), enchantment is invalid");
			else
				c.addEnchantment(nonum, StringUtils.getInt(enchs.replaceAll("[^+0-9]+", ""))<=0?1:StringUtils.getInt(enchs.replaceAll("[^+0-9]+", "")));
		}
		
		ItemStack stack = Utils.setModel(c.create(), getModel());
		NBTEdit edit = new NBTEdit(stack);
		edit.setString("af_data", createData().toString(DataType.JSON));
		return NMSAPI.setNBT(stack, edit);
	}
	
	@Override
	public ItemStack preview(Player p) {
		ItemCreatorAPI c = new ItemCreatorAPI(find(getType(), getType().ordinal()));
		fixHead(c);
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
		List<String> l = data.exists(path+"."+name+".preview.lore")?data.getStringList(path+"."+name+".preview.lore"):getLore();
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
		c.setAmount(getAmount());
		
		for(ItemFlag flag : getFlags())
			c.addItemFlag(flag);
		
		for (String enchs : getEnchantments()) {
			String nonum = enchs.replaceAll("[^A-Za-z_]+", "").toUpperCase();
			if (EnchantmentAPI.byName(nonum)==null)
				Bukkit.getLogger().warning("Error when getting junk: " + name + "!! Enchantment " + enchs + " (Converted to "+nonum+"), enchantment is invalid");
			else
				c.addEnchantment(nonum, StringUtils.getInt(enchs.replaceAll("[^+0-9]+", ""))<=0?1:StringUtils.getInt(enchs.replaceAll("[^+0-9]+", "")));
		}
		return Utils.setModel(c.create(), getModel());
	}
	
	private ItemStack find(Material material, int id) {
		if(this.head) {
			String head = getHead();
			if(head.toLowerCase().startsWith("hdb:"))
				return new ItemCreatorAPI( HDBSupport.parse(head)).create();
			else
			if(head.startsWith("https://")||head.startsWith("http://"))
				return ItemCreatorAPI.createHeadByWeb(1, "&7Head from website", head);
			else
			if(head.length()>16) {
				return ItemCreatorAPI.createHeadByValues(1, "&7Head from values", head);
			}else
				return ItemCreatorAPI.createHead(1, "&7" + head + "'s Head", head);
		}
		if(material!=null)return new ItemStack(material);
		return new ItemStack(Material.getMaterial("RAW_FISH"),1,(short)id);
	}
	private ItemCreatorAPI fixHead(ItemCreatorAPI item) {
		if(this.head) {
			if(data.getString(path+"."+this.name+".head").length()>16)
				item.setOwnerFromValues(data.getString(path+"."+this.name+".head"));
		}
		return item;
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
		return new Data().set("junk", name).set("type", getType().name()).set("weigth", weight).set("length", length).set("ID", "JUNK");
	}
	public Data createData() {
		return new Data().set("junk", name).set("type", getType().name()).set("ID", "JUNK");
	}
}

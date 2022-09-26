package me.devtec.amazingfishing.utils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.BookMeta.Generation;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import me.devtec.amazingfishing.Loader;
import me.devtec.shared.Ref;
import me.devtec.shared.dataholder.Config;
import me.devtec.shared.utility.StringUtils;
import me.devtec.theapi.bukkit.BukkitLoader;
import me.devtec.theapi.bukkit.game.ItemMaker;
import me.devtec.theapi.bukkit.game.ItemMaker.HeadItemMaker;
import me.devtec.theapi.bukkit.gui.EmptyItemGUI;
import me.devtec.theapi.bukkit.gui.GUI;
import me.devtec.theapi.bukkit.gui.GUI.ClickType;
import me.devtec.theapi.bukkit.gui.HolderGUI;
import me.devtec.theapi.bukkit.gui.ItemGUI;
import me.devtec.theapi.bukkit.nms.GameProfileHandler;
import me.devtec.theapi.bukkit.nms.GameProfileHandler.PropertyHandler;
import me.devtec.theapi.bukkit.xseries.XMaterial;

public class Create {
	public static ItemMaker make(String path) {
		ItemStack item = ItemMaker.loadFromConfig(Loader.gui, path);
		return ItemMaker.of(item);
	}
	/*public static ItemMaker make(String path) {		
		ItemMaker create = find(Loader.gui.getString(path + ".icon"), "STONE", 0);
		create.displayName(Loader.gui.getString(path + ".name"));
		create.lore(Loader.gui.getStringList(path + ".lore"));
		for (String ench : Loader.gui.getStringList(path + ".enchants")) {
			String[] split = ench.split(":");
			if (EnchantmentAPI.byName(split[0]) != null)
				create.enchant(EnchantmentAPI.byName(split[0]).getEnchantment(), split.length == 1 ? 1 : StringUtils.getInt(split[1]));
		}
		create.itemFlags(Loader.gui.getStringList(path + ".flags"));
		create.unbreakable(Loader.gui.getBoolean(path + ".unbreakable"));
		return create;
	}*/

	public static ItemMaker makeShop(String path) {
		ItemStack item = ItemMaker.loadFromConfig(Loader.shop, path);
		return ItemMaker.of(item);
	}
	/*public static ItemMaker makeShop(String path) {
		ItemMaker create = find(Loader.shop.getString(path + ".icon"), "STONE", 0);
		create.displayName(Loader.shop.getString(path + ".name"));
		create.lore(Loader.shop.getStringList(path + ".lore"));
		for (String ench : Loader.shop.getStringList(path + ".enchants")) {
			String[] split = ench.split(":");
			if (EnchantmentAPI.byName(split[0]) != null)
				create.enchant(EnchantmentAPI.byName(split[0]).getEnchantment(), split.length == 1 ? 1 : StringUtils.getInt(split[1]));
		}
		create.itemFlags(Loader.shop.getStringList(path + ".flags"));
		create.unbreakable(Loader.shop.getBoolean(path + ".unbreakable"));
		return create;
	}*/

	public static String title(String path) {
		return Loader.gui.getString(path);
	}

	public static String text(String path) {
		return Loader.tran.getString(path).replace("%prefix%", Loader.getPrefix());
	}

	public static List<String> list(String path) {
		List<String> list = Loader.tran.getStringList(path);
		list.replaceAll(a -> a.replace("%prefix%", Loader.getPrefix()));
		return list;
	}

	public static ItemMaker find(String item, String fallbackItem, int fallbackId) {
		// head
		if (item.startsWith("head:")) {
			String head = item.substring(5);
			if (head.startsWith("hdb:"))
				return ItemMaker.ofHead().skinValues(HDBSupport.parse(head.substring(4)));
			if (head.startsWith("https://") || head.startsWith("http://"))
				return ItemMaker.ofHead().skinUrl(head);
			if (head.length() > 16) {
				return ItemMaker.ofHead().skinValues(head);
			}
			return ItemMaker.ofHead().skinName(head);
		}
		// legacy
		if (item.startsWith("hdb:"))
			return ItemMaker.ofHead().skinValues(HDBSupport.parse(item.substring(4)));
		if (item.startsWith("https://") || item.startsWith("http://"))
			return ItemMaker.ofHead().skinUrl(item);
		// normal item
		item = item.toUpperCase();
		Optional<XMaterial> material = XMaterial.matchXMaterial(item);
		if (material.isPresent())
			return ItemMaker.of(material.get().parseMaterial()).data(material.get().getData());
		fallbackItem = fallbackItem.toUpperCase();
		if (Material.getMaterial(fallbackItem) != null)
			return ItemMaker.of(Material.getMaterial(fallbackItem)).damage(fallbackId);
		return ItemMaker.of(Material.getMaterial("RAW_FISH")).damage(fallbackId);
	}

	public static ItemGUI item = new EmptyItemGUI(Utils.getCachedMaterial("BLACK_STAINED_GLASS_PANE").displayName("§c").build());
	public static ItemGUI blue = new EmptyItemGUI(Utils.getCachedMaterial("BLUE_STAINED_GLASS_PANE").displayName("§c").build());

	public static GUI prepareInvBig(GUI inv) {
		for (int i = 45; i < 54; ++i)
			inv.setItem(i, item);
		return inv;
	}

	public static GUI prepareInvSmall(GUI inv) {
		for (int i = 9; i < 18; ++i)
			inv.setItem(i, item);
		return inv;
	}

	public static GUI prepareInvCount(GUI inv, int slots) {
		for (int i = 0; i < slots; ++i)
			inv.setItem(i, item);
		return inv;
	}

	public static enum Settings {
		SIDES, WITHOUT_TOP, FILL, CLOSE, CLEAR
	}

	public static GUI setup(GUI inv, ItemStack close, PRunnable backButton, Settings... settings) {
		boolean[] actions = { false, false, false };
		for (Settings s : settings)
			switch (s) {
			case SIDES:
				inv.setItem(9, item);
				inv.setItem(17, item);
				inv.setItem(18, item);
				inv.setItem(26, item);
				inv.setItem(27, item);
				inv.setItem(35, item);
				inv.setItem(36, item);
				inv.setItem(44, item);
				break;
			case CLOSE:
				actions[0] = true;
				break;
			case WITHOUT_TOP:
				actions[2] = true;
				break;
			case CLEAR:
				actions[1] = true;
				break;
			case FILL:
				while (inv.getFirstEmpty() != -1)
					inv.addItem(item);
				break;
			}
		if (!actions[2]) {
			for (int i = 1; i < 8; ++i)
				inv.setItem(i, item);
			inv.setItem(0, blue);
			inv.setItem(8, blue);
		}
		for (int i = 46; i < 53; ++i)
			inv.setItem(i, item);
		inv.setItem(53, blue);
		inv.setItem(45, new ItemGUI(close) {
			@Override
			public void onClick(Player player, HolderGUI gui, ClickType click) {
				if (actions[0])
					gui.close();
				if (actions[1])
					gui.clear();
				if (backButton != null)
					backButton.run(player);
			}
		});
		return inv;
	}

	public static interface PRunnable {
		public void run(Player p);
	}

	public static ItemStack createItem(String displayName, ItemMaker icon, List<String> description, int model, List<String> flags, boolean unb) {
		icon.displayName(displayName);
		icon.lore(description);
		icon.unbreakable(unb);
		icon.itemFlags(flags);
		ItemStack item = icon.build();
		return Utils.setModel(item, model);
	}

	public static ItemStack createItem(String name, Material paper, List<String> lore) {
		return ItemMaker.of(paper).displayName(name).lore(lore).build();
	}
	
}

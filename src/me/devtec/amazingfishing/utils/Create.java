package me.devtec.amazingfishing.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.Loader;
import me.devtec.shared.utility.StringUtils;
import me.devtec.theapi.bukkit.gui.EmptyItemGUI;
import me.devtec.theapi.bukkit.gui.GUI;
import me.devtec.theapi.bukkit.gui.GUI.ClickType;
import me.devtec.theapi.bukkit.gui.HolderGUI;
import me.devtec.theapi.bukkit.gui.ItemGUI;

public class Create {
	public static ItemCreatorAPI make(String path) {
		ItemCreatorAPI create = find(Loader.gui.getString(path + ".icon"), "STONE", 0);
		create.setDisplayName(Loader.gui.getString(path + ".name"));
		create.setLore(Loader.gui.getStringList(path + ".lore"));
		for (String ench : Loader.gui.getStringList(path + ".enchants")) {
			String[] split = ench.split(":");
			create.addEnchantment(split[0], split.length == 1 ? 1 : StringUtils.getInt(split[1]));
		}
		for (String flag : Loader.gui.getStringList(path + ".flags"))
			try {
				create.addItemFlag(ItemFlag.valueOf(flag.toUpperCase()));
			} catch (Exception | NoSuchFieldError er) {
				Loader.msg("[GUI] Invalid ItemFlag (" + flag + ") of item in the section " + path, Bukkit.getConsoleSender());
			}
		create.setUnbreakable(Loader.gui.getBoolean(path + ".unbreakable"));
		return create;
	}

	public static ItemCreatorAPI makeShop(String path) {
		ItemCreatorAPI create = find(Loader.shop.getString(path + ".icon"), "STONE", 0);
		create.setDisplayName(Loader.shop.getString(path + ".name"));
		create.setLore(Loader.shop.getStringList(path + ".lore"));
		for (String ench : Loader.shop.getStringList(path + ".enchants")) {
			String[] split = ench.split(":");
			create.addEnchantment(split[0], split.length == 1 ? 1 : StringUtils.getInt(split[1]));
		}
		for (String flag : Loader.shop.getStringList(path + ".flags"))
			try {
				create.addItemFlag(ItemFlag.valueOf(flag.toUpperCase()));
			} catch (Exception | NoSuchFieldError er) {
				Loader.msg("[Shop] Invalid ItemFlag (" + flag + ") of item in the section " + path, Bukkit.getConsoleSender());
			}
		create.setUnbreakable(Loader.shop.getBoolean(path + ".unbreakable"));
		return create;
	}

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

	static Material mat;
	static {
		try {
			mat = Material.PLAYER_HEAD;
		} catch (NoSuchFieldError | Exception var1) {
			mat = Material.getMaterial("SKULL_ITEM");
		}
	}

	public static ItemCreatorAPI find(String item, String fallbackItem, int fallbackId) {
		item = item.toUpperCase();
		ItemCreatorAPI creator;
		if (item.startsWith("head:")) {
			String head = item.substring(5);
			if (head.startsWith("hdb:")) {
				creator = new ItemCreatorAPI(new ItemStack(mat, 1));
				creator.setSkullType(SkullType.PLAYER);
				creator.setOwnerFromValues(HDBSupport.parse(head.substring(4)));
				return creator;
			}
			if (head.startsWith("https://") || head.startsWith("http://")) {
				creator = new ItemCreatorAPI(new ItemStack(mat, 1));
				creator.setSkullType(SkullType.PLAYER);
				creator.setOwnerFromWeb(head);
			} else if (head.length() > 16) {
				creator = new ItemCreatorAPI(new ItemStack(mat, 1));
				creator.setSkullType(SkullType.PLAYER);
				creator.setOwnerFromValues(head);
			} else {
				creator = new ItemCreatorAPI(new ItemStack(mat, 1));
				creator.setSkullType(SkullType.PLAYER);
				creator.setOwner(head);
			}
			return creator;
		}
		// legacy
		if (item.startsWith("hdb:")) {
			creator = new ItemCreatorAPI(new ItemStack(mat, 1));
			creator.setSkullType(SkullType.PLAYER);
			creator.setOwnerFromValues(HDBSupport.parse(item.substring(4)));
			return creator;
		}
		if (item.startsWith("https://") || item.startsWith("http://")) {
			creator = new ItemCreatorAPI(new ItemStack(mat, 1));
			creator.setSkullType(SkullType.PLAYER);
			creator.setOwnerFromWeb(item);
			return creator;
		}
		String[] slit = item.split(":");
		if (Material.getMaterial(slit[0]) != null) {
			return new ItemCreatorAPI(new ItemStack(Material.getMaterial(slit[0]), 1, slit.length >= 2 ? StringUtils.getShort(slit[1]) : 0));
		}
		fallbackItem = fallbackItem.toUpperCase();
		if (Material.getMaterial(fallbackItem) != null) {
			return new ItemCreatorAPI(new ItemStack(Material.getMaterial(fallbackItem), 1, (short) fallbackId));
		}
		return new ItemCreatorAPI(new ItemStack(Material.getMaterial("RAW_FISH"), 1, (short) fallbackId));
	}

	public static ItemGUI item = new EmptyItemGUI(
			ItemCreatorAPI.create(Utils.getCachedMaterial("BLACK_STAINED_GLASS_PANE").getItemType(), 1, "&c", Utils.getCachedMaterial("BLACK_STAINED_GLASS_PANE").getData()));
	public static ItemGUI blue = new EmptyItemGUI(
			ItemCreatorAPI.create(Utils.getCachedMaterial("BLUE_STAINED_GLASS_PANE").getItemType(), 1, "&c", Utils.getCachedMaterial("BLUE_STAINED_GLASS_PANE").getData()));

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

	public static ItemStack createItem(String displayName, ItemCreatorAPI icon, List<String> description, int model, List<ItemFlag> flags, boolean unb) {
		icon.setDisplayName(displayName);
		icon.setLore(description);
		icon.setUnbreakable(unb);
		icon.addItemFlag(flags.toArray(new ItemFlag[0]));
		ItemStack item = icon.create();
		return Utils.setModel(item, model);
	}

	public static ItemStack createItem(String name, Material paper, List<String> lore) {
		return ItemCreatorAPI.create(paper, 1, name, lore);
	}
}

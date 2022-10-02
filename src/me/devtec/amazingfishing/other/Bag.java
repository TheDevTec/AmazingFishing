package me.devtec.amazingfishing.other;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.utils.Manager;
import me.devtec.shared.database.DatabaseAPI;
import me.devtec.shared.database.DatabaseAPI.DatabaseType;
import me.devtec.shared.database.DatabaseAPI.SqlDatabaseSettings;
import me.devtec.shared.database.DatabaseHandler;
import me.devtec.shared.database.DatabaseHandler.InsertQuery;
import me.devtec.shared.database.DatabaseHandler.RemoveQuery;
import me.devtec.shared.database.DatabaseHandler.Result;
import me.devtec.shared.database.DatabaseHandler.Row;
import me.devtec.shared.database.DatabaseHandler.SelectQuery;
import me.devtec.shared.dataholder.Config;
import me.devtec.shared.json.Json;
import me.devtec.shared.scheduler.Scheduler;
import me.devtec.theapi.bukkit.BukkitLoader;
import me.devtec.theapi.bukkit.gui.GUI;

public class Bag {
	private static DatabaseHandler sql;
	private static int task;

	public static void initialize() {
		if (!Loader.config.getBoolean("Options.Bag.Enabled") || !(Loader.config.getString("Options.Bag.SaveLocation").equalsIgnoreCase("sql") || Loader.config.getString("Options.Bag.SaveLocation").equalsIgnoreCase("mysql")
				|| Loader.config.getString("Options.Bag.SaveLocation").equalsIgnoreCase("database") || Loader.config.getString("Options.Bag.SaveLocation").equalsIgnoreCase("db")))
			return;
		try {
			sql = DatabaseAPI.openConnection(DatabaseType.MYSQL, new SqlDatabaseSettings(DatabaseType.MYSQL, Loader.config.getString("Options.Bag.MySQL.Host"), 3306,
					Loader.config.getString("Options.Bag.MySQL.Database"), Loader.config.getString("Options.Bag.MySQL.Username"), Loader.config.getString("Options.Bag.MySQL.Password")));
			sql.createTable("amazingfishing", new Row[] { new Row("name", "varchar(64)", false, "", "", ""), new Row("bag", "text", false, "", "", "") });
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	public static void cancelTask() {
		if (task != 0)
			Scheduler.cancelTask(task);
		task = 0;
	}

	private Player player;
	private Config u;

	public Bag(Player p) {
		player = p;
		u = me.devtec.shared.API.getUser(p.getUniqueId());
	}

	public String getName() {
		return player.getName();
	}

	public List<ItemStack> getBag() {
		if (sql != null) {
			List<ItemStack> list = new ArrayList<>();
			Result set;
			try {
				set = sql.get(SelectQuery.table("amazingfishing", "bag").where("name", player.getName().toLowerCase()));
			} catch (SQLException e1) {
				e1.printStackTrace();
				return list;
			}
			try {
				while (set.hasNext())
					try {
						list.add((ItemStack) Json.reader().read(set.getValue()[0]));
						set = set.next();
					} catch (Exception e) {
					}
			} catch (Exception e) {
			}
			return list;
		}
		return u.getListAs(Manager.getDataLocation() + ".Bag", ItemStack.class);
	}

	public void saveBag(GUI i) {
		if (sql != null) {
			List<ItemStack> list = new ArrayList<>();
			for (int st = 0; st < 45; ++st) {
				int slot = st;
				if (i.getItem(slot) == null)
					continue;
				if (!API.isFishItem(i.getItem(slot))) {
					BukkitLoader.getNmsProvider().postToMainThread(() -> player.getInventory().addItem(i.getItem(slot)));
					continue;
				}
				Fish fish = API.getFish(i.getItem(slot));
				if (fish == null) {
					BukkitLoader.getNmsProvider().postToMainThread(() -> player.getInventory().addItem(i.getItem(slot)));
					continue;
				}
				list.add(i.getItem(slot));
			}
			try {
				sql.remove(RemoveQuery.table("amazingfishing").where("name", player.getName().toLowerCase()).limit(0));
				for (ItemStack stack : list)
					sql.insert(InsertQuery.table("amazingfishing", player.getName().toLowerCase(), Json.writer().write(stack)));
			} catch (Exception err) {
				err.printStackTrace();
			}
		} else {
			List<ItemStack> list = new ArrayList<>();
			for (int st = 0; st < 45; ++st) {
				int slot = st;
				if (i.getItem(slot) == null)
					continue;
				if (!API.isFishItem(i.getItem(slot))) {
					BukkitLoader.getNmsProvider().postToMainThread(() -> player.getInventory().addItem(i.getItem(slot)));
					continue;
				}
				Fish fish = API.getFish(i.getItem(slot));
				if (fish == null) {
					BukkitLoader.getNmsProvider().postToMainThread(() -> player.getInventory().addItem(i.getItem(slot)));
					continue;
				}
				list.add(i.getItem(slot));
			}
			u.set(Manager.getDataLocation() + ".Bag", list);
			u.save();
		}
	}
}

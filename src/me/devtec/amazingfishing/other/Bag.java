package me.devtec.amazingfishing.other;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Fish;
import me.devtec.amazingfishing.utils.Manager;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.scheduler.Tasker;
import me.devtec.theapi.sqlapi.SQLAPI;
import me.devtec.theapi.utils.datakeeper.User;
import me.devtec.theapi.utils.json.Json;
import me.devtec.theapi.utils.nms.NMSAPI;

public class Bag {
	private static SQLAPI sql;
	private static int task;
	public static void initialize() {
		if(!Loader.config.getBoolean("Options.Bag.Enabled"))return;
		if(!(Loader.config.getString("Options.Bag.SaveLocation").equalsIgnoreCase("sql")||Loader.config.getString("Options.Bag.SaveLocation").equalsIgnoreCase("mysql")||Loader.config.getString("Options.Bag.SaveLocation").equalsIgnoreCase("database")||Loader.config.getString("Options.Bag.SaveLocation").equalsIgnoreCase("db")))return;
		sql=new SQLAPI(Loader.config.getString("Options.Bag.MySQL.Host"), Loader.config.getString("Options.Bag.MySQL.Database")
				, Loader.config.getString("Options.Bag.MySQL.Username"), Loader.config.getString("Options.Bag.MySQL.Password"));
		sql.execute("create table if not exists amazingfishing (name varchar(64), bag text)");
		task=new Tasker() {
			public void run() {
				sql.reconnect();
			}
		}.runRepeating(20*60*15, 20*60*15);
	}
	
	public static void cancelTask() {
		if(task!=0)
			me.devtec.theapi.scheduler.Scheduler.cancelTask(task);
		task=0;
	}
	
	
	private Player player;
	private User u;
	public Bag(Player p) {
		this.player=p;
		this.u=TheAPI.getUser(p);
	}
	
	public String getName() { 
		return player.getName(); 
	}

	public List<ItemStack> getBag(){
		if(sql!=null) {
			List<ItemStack> list = new ArrayList<>();
			ResultSet set = sql.query("select * from amazingfishing where name='"+u.getName().toLowerCase()+"'");
			int id = 0;
			try {
			while(set.next()) {
				try {
					list.add((ItemStack)Json.reader().read(set.getString(++id)));
					//list.add((ItemStack)Reader.read(set.getString(++id)));
				} catch (Exception e) {
				}
			}
			} catch (Exception e) {
			}
			return list;
		}
		return u.getData().getListAs(Manager.getDataLocation()+".Bag", ItemStack.class);
	}
	
	public void saveBag(GUI i) {
		if(sql!=null) {
			List<ItemStack> list = new ArrayList<>();
			for(int st = 0; st < 45; ++st) {
				int slot = st;
				if(i.getItem(slot)==null)continue;
				if(!API.isFishItem(i.getItem(slot))) {
					NMSAPI.postToMainThread(() -> TheAPI.giveItem(player, i.getItem(slot)));
					continue;
				}
				Fish fish = API.getFish(i.getItem(slot));
				if(fish==null) {
					NMSAPI.postToMainThread(() -> TheAPI.giveItem(player, i.getItem(slot)));
					continue;
				}
				list.add(i.getItem(slot));
			}
			sql.execute("delete from amazingfishing where name='"+u.getName().toLowerCase()+"'");
			for(ItemStack stack : list)
				sql.set("amazingfishing", "bag", Json.writer().write(stack), u.getName().toLowerCase(), "name");
			//sql.set("amazingfishing", "bag", Writer.write(stack), u.getName().toLowerCase(), "name");
		}else {
			List<ItemStack> list = new ArrayList<>();
			for(int st = 0; st < 45; ++st) {
				int slot = st;
				if(i.getItem(slot)==null)continue;
				if(!API.isFishItem(i.getItem(slot))) {
					NMSAPI.postToMainThread(() -> TheAPI.giveItem(player, i.getItem(slot)));
					continue;
				}
				Fish fish = API.getFish(i.getItem(slot));
				if(fish==null) {
					NMSAPI.postToMainThread(() -> TheAPI.giveItem(player, i.getItem(slot)));
					continue;
				}
				list.add(i.getItem(slot));
			}
			u.set(Manager.getDataLocation()+".Bag", list);
			u.save();
		}
	}
}

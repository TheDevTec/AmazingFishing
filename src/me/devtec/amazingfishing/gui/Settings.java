package me.devtec.amazingfishing.gui;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.utils.Create;
import me.devtec.amazingfishing.utils.Manager;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.GUI.ClickType;
import me.devtec.theapi.guiapi.HolderGUI;
import me.devtec.theapi.guiapi.ItemGUI;
import me.devtec.theapi.utils.datakeeper.User;

public class Settings {

	public static void open(Player p) {
		GUI a = Create.setup(new GUI(Create.text("settings.title"),54) {
			public void onClose(Player player) {
				clear();
			}}, Create.make("settings.close").create(),  s -> Help.open(s), me.devtec.amazingfishing.utils.Create.Settings.SIDES);
		
		User u = TheAPI.getUser(p);
		String records = "on";
		if(u.exist(Manager.getDataLocation()+".Settings.SendRecords") && 
				!u.getBoolean(Manager.getDataLocation()+".Settings.SendRecords"))
			records = "off";
		a.setItem(21, new ItemGUI(Create.make("settings.records."+records).create()) {
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType click) {
				boolean val = !u.getBoolean(Manager.getDataLocation()+".Settings.SendRecords");
				u.setAndSave(Manager.getDataLocation()+".Settings.SendRecords", val);
				String records = val?"on":"off";
				setItem(Create.make("settings.records."+records).create());
				arg.setItem(21, this);
			}
		});
		
		a.open(p);
	}
}

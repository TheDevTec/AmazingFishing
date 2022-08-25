package me.devtec.amazingfishing.gui;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.utils.Create;
import me.devtec.amazingfishing.utils.Manager;
import me.devtec.shared.API;
import me.devtec.shared.dataholder.Config;
import me.devtec.theapi.bukkit.gui.GUI;
import me.devtec.theapi.bukkit.gui.GUI.ClickType;
import me.devtec.theapi.bukkit.gui.HolderGUI;
import me.devtec.theapi.bukkit.gui.ItemGUI;

public class Settings {

	public static void open(Player p) {
		GUI a = Create.setup(new GUI(Create.title("settings.title"),54), Create.make("settings.close").build(),  s -> Help.open(s), me.devtec.amazingfishing.utils.Create.Settings.SIDES);
		Config u = API.getUser(p.getUniqueId());
		String records = "on";
		if(u.exists(Manager.getDataLocation()+".Settings.SendRecords") && 
				!u.getBoolean(Manager.getDataLocation()+".Settings.SendRecords"))records = "off";
		a.setItem(21, new ItemGUI(Create.make("settings.records."+records).build()) {
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType click) {
				boolean val = !u.getBoolean(Manager.getDataLocation()+".Settings.SendRecords");
				u.set(Manager.getDataLocation()+".Settings.SendRecords", val);
				String records = val?"on":"off";
				setItem(Create.make("settings.records."+records).build());
				arg.setItem(21, this);
			}
		});
		a.open(p);
	}
}

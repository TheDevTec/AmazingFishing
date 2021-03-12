package me.devtec.amazingfishing.gui;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.devtec.amazingfishing.Loader;
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
		GUI a = Create.prepareNewBig(new GUI(Loader.gui.getString("GUI.Help.Player.Title"),54), Material.PURPLE_STAINED_GLASS_PANE);
		
		User u = TheAPI.getUser(p);
		String records = "on";
		if(u.exist(Manager.getDataLocation()+".Settings.SendRecords") && 
				!u.getBoolean(Manager.getDataLocation()+".Settings.SendRecords"))
			records = "off";
		
		Material item = Material.valueOf( Loader.gui.getString("GUI.Settings.SendRecords."+records+".Item") );
		String name =  Loader.gui.getString("GUI.Settings.SendRecords."+records+".Name");
		List<String> lore = Loader.gui.getStringList("GUI.Settings.SendRecords."+records+".Lore");
		a.setItem(21, new ItemGUI(Create.createItem(name, item, lore)) {
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType click) {
				if(!u.exist(Manager.getDataLocation()+".Settings.SendRecords")) {
					u.setAndSave(Manager.getDataLocation()+".Settings.SendRecords", false);
					open(p);
					return;
				}
				else if(!u.getBoolean(Manager.getDataLocation()+".Settings.SendRecords"))
					u.setAndSave(Manager.getDataLocation()+".Settings.SendRecords", true);
				else u.setAndSave(Manager.getDataLocation()+".Settings.SendRecords", false);
				open(p);
			}
		});
		
	}
}

package me.devtec.amazingfishing.guis.menus;

import org.bukkit.entity.Player;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.guis.Menu;
import me.devtec.amazingfishing.guis.MenuItem;
import me.devtec.amazingfishing.player.Fisher;
import me.devtec.amazingfishing.utils.MessageUtils;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.shared.dataholder.Config;
import me.devtec.theapi.bukkit.gui.GUI;
import me.devtec.theapi.bukkit.gui.GUI.ClickType;
import me.devtec.theapi.bukkit.gui.HolderGUI;
import me.devtec.theapi.bukkit.gui.ItemGUI;

public class Settings extends Menu {

	public Settings(Config file) {
		super(file);
	}
	
	public void putSpecialItems(GUI gui, Player player, int page) {
		Fisher fisher = API.getFisher(player);
		
		MenuItem item = getRecordStatusItem(fisher);
		if(item != null)
			gui.setItem(item.getPosition(), new ItemGUI(item.getItem().build()) {
				
				@Override
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					boolean newStatus = !fisher.canSendRecordMessages();
					fisher.setSendRecordMessages(newStatus);
					item.playSound(player);
					if(newStatus)
						MessageUtils.message(player, "records.enabled", Placeholders.c().addPlayer("player", player));
					else
						MessageUtils.message(player, "records.disabled", Placeholders.c().addPlayer("player", player));
					openGUI(player); //re-opening this menu
				}
			});
	}
	
	private MenuItem getRecordStatusItem(Fisher fisher) {
		if(fisher.canSendRecordMessages())
			return getItem("records_on");
		return getItem("records_off");
	}

}

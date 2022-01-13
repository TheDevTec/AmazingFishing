package me.devtec.amazingfishing.utils;

import java.util.ArrayList;
import java.util.List;

import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.gui.Shop.ShopType;
import me.devtec.amazingfishing.utils.tournament.TournamentType;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.utils.StringUtils;

public class Trans {
	public static String s(String s) {
		if(Loader.trans.exists(s))
			return Loader.trans.getString(s);
		else {
			TheAPI.getConsole().sendMessage("&4ERROR: This path doesn't exist in Translation file! &f'"+s+"'");
			return null;
		}
	}
	public static String prefix() {
		return Loader.trans.getString("Prefix");
	}
	public static String noPerms() {
		return Loader.trans.getString("NoPerms");
	}
	
	public static String help_player_title() {
		return Loader.gui.getString("GUI.Help.Player.Title");
	}
	public static String help_player_info() {
		return Loader.gui.getString("GUI.Help.Player.Info");
	}
	public static String help_player_shop() {
		return Loader.gui.getString("GUI.Help.Player.Shop");
	}	
	public static String help_player_stats() {
		return Loader.gui.getString("GUI.Help.Player.Stats");
	}
	public static String help_player_list() {
		return Loader.gui.getString("GUI.Help.Player.List");
	}
	public static String help_player_enchants() {
		return Loader.gui.getString("GUI.Help.Player.Enchant");
	}
	public static String help_player_bag() {
		return Loader.gui.getString("GUI.Help.Player.Bag");
	}
	public static String help_player_quests() {
		return Loader.gui.getString("GUI.Help.Player.Quests");
	}
	public static String help_player_achievements() {
		return Loader.gui.getString("GUI.Help.Player.Achievements");
	}
	public static String help_player_settings() {
		return Loader.gui.getString("GUI.Help.Player.Settings");
	}
	
	
	public static String help_admin_info() {
		return Loader.gui.getString("GUI.Help.Admin.Infp");
	}
	public static String help_admin_title() {
		return Loader.gui.getString("GUI.Help.Admin.Title");
	}
	public static String help_admin_reload() {
		return Loader.gui.getString("GUI.Help.Admin.Reload");
	}
	public static String help_admin_tournament() {
		return Loader.gui.getString("GUI.Help.Admin.Tournament");
	}
	
	public static String shop_title(ShopType type) {
		if(type==ShopType.SELL)
			return Loader.gui.getString("GUI.SellShop.Title");
		else
			return Loader.gui.getString("GUI.BuyShop.Title");
	}
	
	public static String bag_title() {
			return Loader.gui.getString("GUI.Bag.Title");
	}

	public static String bag_toShop() {
			return Loader.gui.getString("GUI.Bag.Shop");
	}
	
	public static String enchant_title() {
		return Loader.gui.getString("GUI.Enchant.Title");
	}
	public static String enchant_add() {
		return Loader.gui.getString("GUI.Enchant.Add");
	}
	public static String enchant_upgrade() {
		return Loader.gui.getString("GUI.Enchant.Upgrade");
	}
	public static String enchant_selectRod_title() {
		return Loader.gui.getString("GUI.Enchant.SelectRod_Title");
	}
	public static String enchant_add_title() {
		return Loader.gui.getString("GUI.Enchant.Add_Enchant_Title");
	}
	public static String enchant_upgrade_title() {
		return Loader.gui.getString("GUI.Enchant.Upgrade_Enchant_Title");
	}
	
	public static String tournaments_title() {
		return Loader.gui.getString("GUI.Tournaments.Title");
	}
	public static String tournaments_stop_title() {
		return Loader.gui.getString("GUI.Tournaments.Stop.Title");
	}
	public static String tournaments_stop_one_name() {
		return Loader.gui.getString("GUI.Tournaments.Stop.StopOne.Name");
	}
	public static List<String> tournaments_stop_one_description() {
		return Loader.gui.getStringList("GUI.Tournaments.Stop.StopOne.Description");
	}
	public static String tournaments_stop_all_name() {
		return Loader.gui.getString("GUI.Tournaments.Stop.StopAll.Name");
	}
	public static List<String> tournaments_stop_all_description() {
		return Loader.gui.getStringList("GUI.Tournaments.Stop.StopAll.Description");
	}
	public static String tournaments_stop_item() {
		return Loader.gui.getString("GUI.Tournaments.Stop.Item");
	}
	public static String tournaments_start_title() {
		return Loader.gui.getString("GUI.Tournaments.Start.Title");
	}
	public static String tournaments_start_start() {
		return Loader.gui.getString("GUI.Tournaments.Start.Start");
	}
	public static List<String> tournaments_start_description(TournamentType type, long time) {
		List<String> list = new ArrayList<String>();
		for(String s:  Loader.gui.getStringList("GUI.Tournaments.Start.Description"))
		list.add(s.replace("%time%", StringUtils.setTimeToString(time))
				.replace("%type%", type.formatted()) );
			return list;
	}
	
	public static String quests_title_all() {
		return Loader.gui.getString("GUI.Quests.Title.All");
	}	
	public static String quests_title_categories() {
		return Loader.gui.getString("GUI.Quests.Title.Category");
	}
	public static String quests_title_my() {
		return Loader.gui.getString("GUI.Quests.Title.MyQuests");
	}
	
	public static String achievements_title() {
		return Loader.gui.getString("GUI.Achievements.Title");
	}
	public static String achievements_title_category() {
		return Loader.gui.getString("GUI.Achievements.Category");
	}
	
	public static String words_length() {
		return Loader.trans.getString("Words.Length");
	}
	public static String words_weight() {
		return Loader.trans.getString("Words.Weight");
	}
	public static String words_cod() {
		return Loader.trans.getString("Words.Cod");
	}
	public static String words_salmon() {
		return Loader.trans.getString("Words.Salmon");
	}
	public static String words_pufferfish() {
		return Loader.trans.getString("Words.PufferFish");
	}
	public static String words_tropicalfish() {
		return Loader.trans.getString("Words.TropicalFish");
	}
	public static String words_junk() {
		return Loader.trans.getString("Words.Junk");
	}
	public static String words_back() {
		return Loader.trans.getString("Words.Back");
	}
	public static String words_close() {
		return Loader.trans.getString("Words.Close");
	}
	public static String words_cancel() {
		return Loader.trans.getString("Words.Cancel");
	}
	public static String words_sell() {
		return Loader.trans.getString("Words.Sell");
	}
	public static String words_points() {
		return Loader.trans.getString("Words.Points");
	}
	public static String words_tournament(TournamentType TournamentType) {
		return Loader.config.getString("Tournament.Type."+TournamentType.formatted()+".Name");
	}
	public static String words_convert() {
		return Loader.trans.getString("Words.Convert");
	}
	public static String convertor_title() {
		return Loader.gui.getString("GUI.Convertor.Title");
	}
	public static String next() {
		return Loader.gui.getString("GUI.Button.Next");
	}
	public static String previous() {
		return Loader.gui.getString("GUI.Button.Previous");
	}
	public static String head_next() {
		return Loader.gui.getString("GUI.Button.Next_Head");
	}
	public static String head_previous() {
		return Loader.gui.getString("GUI.Button.Previous_Head");
	}
}

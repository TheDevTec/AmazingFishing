package me.devtec.amazingfishing.utils.placeholders;

import me.devtec.amazingfishing.utils.MessageUtils;
import me.devtec.amazingfishing.utils.MessageUtils.Placeholders;
import me.devtec.shared.placeholders.PlaceholderExpansion;

public class PlaceholderLoader {


	public static PlaceholderExpansion holder;

	public static void load() { //TODO
		MessageUtils.msgConsole("%prefix% &fLoading placeholders", Placeholders.c());

	}

}

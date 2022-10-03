package me.devtec.amazingfishing.utils.tournament.bossbar;

import org.bukkit.entity.Player;

import me.devtec.shared.utility.StringUtils;
import me.devtec.theapi.bukkit.bossbar.BossBar;

public class LegacyBossBar implements SBossBar {

	public BossBar bar;

	protected LegacyBossBar(Player player, String title, double progress) {
		bar = new BossBar(player, title, progress);
	}

	@Override
	public void setTitle(String title) {
		bar.setTitle(StringUtils.colorize(title));
	}

	@Override
	public void setProgress(double progress) {
		if (progress <= 0)
			progress = 0;
		bar.setProgress(progress);
	}

	@Override
	public void setStyle(String styleName) {

	}

	@Override
	public void setColor(String colorName) {

	}

	@Override
	public void setRandomStyle() {

	}

	@Override
	public void setRandomColor() {

	}

	@Override
	public boolean isVisible() {
		return !bar.isHidden();
	}

	@Override
	public void hide() {
		bar.hide();
	}

	@Override
	public void show() {
		bar.show();
	}

	@Override
	public void remove() {
		bar.remove();
	}
}

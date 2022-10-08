package me.devtec.amazingfishing.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Calculator;
import me.devtec.amazingfishing.construct.CatchFish;
import me.devtec.amazingfishing.utils.Achievements;
import me.devtec.amazingfishing.utils.Create;
import me.devtec.amazingfishing.utils.Create.Settings;
import me.devtec.amazingfishing.utils.Quests;
import me.devtec.amazingfishing.utils.Statistics;
import me.devtec.amazingfishing.utils.Utils;
import me.devtec.amazingfishing.utils.points.EconomyAPI;
import me.devtec.shared.placeholders.PlaceholderAPI;
import me.devtec.shared.utility.StringUtils;
import me.devtec.shared.utility.StringUtils.FormatType;
import me.devtec.theapi.bukkit.BukkitLoader;
import me.devtec.theapi.bukkit.game.ItemMaker;
import me.devtec.theapi.bukkit.gui.EmptyItemGUI;
import me.devtec.theapi.bukkit.gui.GUI;
import me.devtec.theapi.bukkit.gui.GUI.ClickType;
import me.devtec.theapi.bukkit.gui.HolderGUI;
import me.devtec.theapi.bukkit.gui.ItemGUI;

public class Shop {
	public static enum ShopType {
		BUY, SELL, CONVERTOR
	}

	public static void openShop(Player p, ShopType t) {
		if (!Loader.config.getBoolean("Options.Shop.Enabled"))
			return;
		GUI a = Create.setup(new GUI(Create.title("shops." + (t == ShopType.BUY ? "buy" : "sell") + ".title"), 54) {
			@Override
			public void onClose(Player player) {
				if (t == ShopType.SELL) {
					for (int count = 10; count < 17; ++count)
						if (getItem(count) != null)
							p.getInventory().addItem(getItem(count));
					for (int count = 19; count < 26; ++count)
						if (getItem(count) != null)
							p.getInventory().addItem(getItem(count));
					for (int count = 28; count < 34; ++count)
						if (getItem(count) != null)
							p.getInventory().addItem(getItem(count));
					for (int count = 37; count < 44; ++count)
						if (getItem(count) != null)
							p.getInventory().addItem(getItem(count));
				}
			}
		}, Create.make("shops." + (t == ShopType.BUY ? "buy" : "sell") + ".close").build(), Help::open, Settings.SIDES);

		if (t == ShopType.SELL)
			a.setInsertable(true);
		if (!Loader.config.getBoolean("Options.Shop.HidePoints"))
			a.setItem(4, replace(p, Create.make("shops.points"), () -> {
			}));
		if (p.hasPermission("amazingfishing.command.bag") && Loader.config.getBoolean("Options.Bag.Enabled"))
			a.setItem(26, replace(p, Create.make("shops." + (t == ShopType.BUY ? "buy" : "sell") + ".bag"), () -> {
				Bag.openBag(p);
			}));
		if (p.hasPermission("amazingfishing.command.convertor") && !Loader.config.getBoolean("Options.Shop.DisableConventor"))
			a.setItem(18, replace(p, Create.make("shops.convertor"), () -> {
				Convertor.open(p);
			}));
		if (t == ShopType.BUY) {
			if (Loader.config.getBoolean("Options.Shop.SellFish"))
				a.setItem(35, replace(p, Create.make("shops.buy.sell-shop"), () -> {
					openShop(p, ShopType.SELL);
				}));
			addItems(a);
		} else {
			// TODO - Fish OF Day
			if (Loader.config.getBoolean("Options.Shop.BuyShop"))
				a.setItem(35, replace(p, Create.make("shops.sell.buy-shop"), () -> {
					openShop(p, ShopType.BUY);
				}));
			a.setItem(49, new ItemGUI(Create.make("shops.sell.sell").build()) {
				@Override
				public void onClick(Player player, HolderGUI gui, ClickType click) {
					sellAll(p, gui, false);
				}
			});
		}
		a.open(p);
	}

	private static void addItems(GUI inv) {
		for (String item : Loader.shop.getKeys()) {
			double costVault = Loader.shop.getDouble(item + ".cost.vault"), costPoints = Loader.shop.getDouble(item + ".cost.points");
			int costExp = Loader.shop.getInt(item + ".cost.exp");
			inv.addItem(new ItemGUI(Utils.setModel(Create.makeShop(item).build(), Loader.shop.getInt(item + ".model"))) {
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType type) {
					if (API.getPoints().get(p.getName()) >= costPoints && EconomyAPI.has(p.getName(), costVault) && p.getTotalExperience() >= costExp) {
						API.getPoints().remove(p.getName(), costPoints);
						EconomyAPI.withdrawPlayer(p.getName(), costVault);
						p.giveExp(-costExp);
						for (String msg : Loader.shop.getStringList(item + ".actions.messages"))
							Loader.msg(PlaceholderAPI.apply(msg.replace("%player%", p.getName()).replace("%playername%", p.getDisplayName()).replace("%points%",
									StringUtils.formatDouble(FormatType.NORMAL, API.getPoints().get(p.getName()))), p.getUniqueId()), p);
						List<String> cmds = Loader.shop.getStringList(item + ".actions.commands");
						BukkitLoader.getNmsProvider().postToMainThread(() -> {
							for (String cmd : cmds)
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.apply(cmd.replace("%player%", p.getName()).replace("%playername%", p.getDisplayName())
										.replace("%points%", StringUtils.formatDouble(FormatType.NORMAL, API.getPoints().get(p.getName()))), p.getUniqueId()));
						});
						arg.setItem(4, replace(p, Create.make("shops.points"), () -> {
						}));
					}
				}
			});
		}
	}

	protected static ItemGUI replace(Player p, ItemMaker make, Runnable run) {
		make.displayName(PlaceholderAPI.apply(make.getDisplayName().replace("%player%", p.getName()).replace("%playername%", p.getDisplayName()).replace("%points%",
				StringUtils.formatDouble(FormatType.NORMAL, API.getPoints().get(p.getName()))), p.getUniqueId()));
		if (make.getLore() != null)
			make.getLore().replaceAll(a -> PlaceholderAPI.apply(
					a.replace("%player%", p.getName()).replace("%playername%", p.getDisplayName()).replace("%points%", StringUtils.formatDouble(FormatType.NORMAL, API.getPoints().get(p.getName()))),
					p.getUniqueId()));
		return new ItemGUI(make.build()) {
			@Override
			public void onClick(Player var1, HolderGUI var2, ClickType var3) {
				run.run();
			}
		};
	}

	public static void sellAll(Player p, HolderGUI gui, boolean expand) {
		List<ItemStack> a = new ArrayList<>();
		ItemGUI item = new EmptyItemGUI(new ItemStack(Material.AIR));
		item.setUnstealable(false);
		if (!expand) {
			for (int count = 10; count < 17; ++count) {
				if (gui.getItem(count) == null)
					continue;
				a.add(gui.getItem(count));
				gui.setItem(count, item);
			}
			for (int count = 19; count < 26; ++count) {
				if (gui.getItem(count) == null)
					continue;
				a.add(gui.getItem(count));
				gui.setItem(count, item);
			}
			for (int count = 28; count < 35; ++count) {
				if (gui.getItem(count) == null)
					continue;
				a.add(gui.getItem(count));
				gui.setItem(count, item);
			}
			for (int count = 37; count < 44; ++count) {
				if (gui.getItem(count) == null)
					continue;
				a.add(gui.getItem(count));
				gui.setItem(count, item);
			}
		} else
			for (int count = 0; count < 45; ++count) {
				if (gui.getItem(count) == null)
					continue;
				a.add(gui.getItem(count));
				gui.setItem(count, item);
			}
		gui.setInsertable(true);

		int sel = 0;
		double totalExp = 0, totalPoints = 0, totalMoney = 0;
		for (ItemStack d : a) {
			if (d == null || d.getType() == Material.AIR)
				continue;
			CatchFish f = API.getCatchFish(d);
			if (f == null) {
				p.getInventory().addItem(d);
				continue;
			}
			double length = 0, weight = 0;
			int bonus = 1; // TODO - Fish Of Day

			// MONEY & POINTS
			length = f.getLength();
			weight = f.getWeight();

			sel = sel + d.getAmount();

			// CALCULATE
			totalMoney += StringUtils.calculate(f.getFish().getCalculator(Calculator.MONEY).replace("%length%", "" + length).replace("%weight%", "" + weight)
					.replace("%money%", "" + f.getFish().getMoney()).replace("%experiences%", "" + f.getFish().getXp()).replace("%money_boost%", "" + f.getMoneyBoost())
					.replace("%points_boost%", "" + f.getPointsBoost()).replace("%exp_boost%", "" + f.getExpBoost()).replace("%money_bonus%", "" + f.getMoneyBoost())
					.replace("%points_bonus%", "" + f.getPointsBoost()).replace("%exp_bonus%", "" + f.getExpBoost()).replace("%points%", "" + f.getFish().getPoints()).replace("%bonus%", "" + bonus))
					* d.getAmount();
			totalExp += StringUtils.calculate(f.getFish().getCalculator(Calculator.EXPS).replace("%length%", "" + length).replace("%weight%", "" + weight)
					.replace("%money%", "" + f.getFish().getMoney()).replace("%experiences%", "" + f.getFish().getXp()).replace("%money_boost%", "" + f.getMoneyBoost())
					.replace("%points_boost%", "" + f.getPointsBoost()).replace("%exp_boost%", "" + f.getExpBoost()).replace("%money_bonus%", "" + f.getMoneyBoost())
					.replace("%points_bonus%", "" + f.getPointsBoost()).replace("%exp_bonus%", "" + f.getExpBoost()).replace("%points%", "" + f.getFish().getPoints()).replace("%bonus%", "" + bonus))
					* d.getAmount();
			totalPoints += StringUtils.calculate(f.getFish().getCalculator(Calculator.POINTS).replace("%length%", "" + length).replace("%weight%", "" + weight)
					.replace("%money%", "" + f.getFish().getMoney()).replace("%experiences%", "" + f.getFish().getXp()).replace("%points%", "" + f.getFish().getPoints()).replace("%bonus%", "" + bonus)
					.replace("%money_boost%", "" + f.getMoneyBoost()).replace("%points_boost%", "" + f.getPointsBoost()).replace("%exp_boost%", "" + f.getExpBoost())
					.replace("%money_bonus%", "" + f.getMoneyBoost()).replace("%points_bonus%", "" + f.getPointsBoost()).replace("%exp_bonus%", "" + f.getExpBoost())) * d.getAmount();
			// a.remove(d);
			Statistics.addSelling(p, f.getFish(), d.getAmount()); // Adding fish to Selling statistics
			BukkitLoader.getNmsProvider().postToMainThread(() -> {
				Achievements.check(p, f);
				Quests.addProgress(p, "sell_fish", f.getType().name().toLowerCase() + "." + f.getName(), d.getAmount());
			});
		}
		if (sel != 0) {
			// TODO sounds - EDIT - commands & messages (list)
			if (Loader.config.getBoolean("Options.Sell.DisableMoney"))
				totalMoney = 0;
			if (Loader.config.getBoolean("Options.Sell.DisableXP"))
				totalExp = 0;
			if (Loader.config.getBoolean("Options.Sell.DisablePoints"))
				totalPoints = 0;
			if (totalPoints > 0)
				API.getPoints().add(p.getName(), totalPoints);
			if (totalMoney > 0 && EconomyAPI.economy != null)
				EconomyAPI.depositPlayer(p.getName(), totalMoney);
			if ((int) totalExp > 0)
				p.giveExp((int) totalExp);

			Statistics.addSellingValues(p, totalMoney, totalPoints, totalExp);

			for (String msg : Create.list("sold-fish"))
				Loader.msg(msg.replace("%amount%", sel + "").replace("%exp%", Loader.ff.format(totalExp)).replace("%money%", Loader.ff.format(totalMoney))
						.replace("%points%", Loader.ff.format(totalPoints)).replace("%prefix%", Loader.getPrefix()), p);
		}
	}
}

package me.devtec.amazingfishing.gui;

import java.util.ArrayList;
import java.util.List;

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
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.apis.ItemCreatorAPI;
import me.devtec.theapi.economyapi.EconomyAPI;
import me.devtec.theapi.guiapi.EmptyItemGUI;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.GUI.ClickType;
import me.devtec.theapi.guiapi.HolderGUI;
import me.devtec.theapi.guiapi.ItemGUI;
import me.devtec.theapi.placeholderapi.PlaceholderAPI;
import me.devtec.theapi.scheduler.Tasker;
import me.devtec.theapi.utils.StringUtils;
import me.devtec.theapi.utils.reflections.Ref;

public class Shop {
	public static enum ShopType {
		BUY,
		SELL,
		CONVERTOR
	}
	
	public static void openShop(Player p, ShopType t) {
		GUI a = Create.setup(new GUI(Create.title("shops."+(t==ShopType.BUY?"buy":"sell")+".title"),54) {
			public void onClose(Player player) {
				if(t==ShopType.SELL) {
					for(int count =10; count < 17; ++count)
						TheAPI.giveItem(p, getItem(count));
					for(int count =19; count < 26; ++count)
						TheAPI.giveItem(p, getItem(count));
					for(int count =28; count < 34; ++count)
						TheAPI.giveItem(p, getItem(count));
					for(int count =37; count < 44; ++count)
						TheAPI.giveItem(p, getItem(count));
				}
			}}, Create.make("shops."+(t==ShopType.BUY?"buy":"sell")+".close").create(),  s -> Help.open(s), Settings.SIDES);
		if(t==ShopType.SELL)
			a.setInsertable(true);
		new Tasker() {
			public void run() {
				a.setItem(4,replace(p,Create.make("shops.points"), ()->{}));
				if(p.hasPermission("amazingfishing.command.bag"))
					a.setItem(26,replace(p,Create.make("shops."+(t==ShopType.BUY?"buy":"sell")+".bag"),() -> {
							Bag.openBag(p);
						}));
				if(p.hasPermission("amazingfishing.command.convertor"))
					a.setItem(18, replace(p,Create.make("shops.convertor"),() -> {
							Convertor.open(p);
						}));
				if(t==ShopType.BUY) {
					if(Loader.config.getBoolean("Options.Shop.SellFish"))
						a.setItem(35, replace(p,Create.make("shops.buy.sell-shop"),() -> {
								openShop(p, ShopType.SELL);
							}));
					addItems(a);
				}else {
					//TODO - Fish OF Day
					 a.setItem(35, replace(p,Create.make("shops.sell.buy-shop"),() -> {
								openShop(p, ShopType.BUY);
							}));
					a.setItem(49, new ItemGUI(Create.make("shops.sell.sell").create()) {
						public void onClick(Player player, HolderGUI gui, ClickType click) {
							sellAll(p, gui, false);
						}
					});
				}
				a.open(p);
			}
		}.runTask();
	}
	
	private  static void addItems(GUI inv) {
		for(String item : Loader.shop.getKeys()) {
			double costVault = Loader.shop.getDouble(item+".cost.vault"), costPoints = Loader.shop.getDouble(item+".cost.points");
			int costExp = Loader.shop.getInt(item+".cost.exp");
			inv.addItem(new ItemGUI(Utils.setModel(Create.makeShop(item).create(), Loader.shop.getInt(item+".model"))){
				public void onClick(Player p, HolderGUI arg, ClickType type) {
					if(API.getPoints().get(p.getName()) >= costPoints && EconomyAPI.has(p, costVault) && p.getTotalExperience() >= costExp) {
						API.getPoints().remove(p.getName(), costPoints);
						EconomyAPI.withdrawPlayer(p, costVault);
						p.giveExp(-costExp);
						for(String msg : Loader.shop.getStringList(item+".actions.messages"))
							TheAPI.msg(PlaceholderAPI.setPlaceholders(p, msg.replace("%player%", p.getName()).replace("%playername%", p.getDisplayName()).replace("%points%", StringUtils.fixedFormatDouble(API.getPoints().get(p.getName())))), p);
						List<String> cmds = Loader.shop.getStringList(item+".actions.commands");
						TheAPI.getNmsProvider().postToMainThread(() -> {
							for(String cmd : cmds)
								TheAPI.sudoConsole(PlaceholderAPI.setPlaceholders(p, cmd.replace("%player%", p.getName()).replace("%playername%", p.getDisplayName()).replace("%points%", StringUtils.fixedFormatDouble(API.getPoints().get(p.getName())))));
						});
						arg.setItem(4,replace(p, Create.make("shops.points"), ()->{}));
					}
				}
			});
		}
	}
	
	protected static ItemGUI replace(Player p, ItemCreatorAPI make, Runnable run) {
		make.setDisplayName(PlaceholderAPI.setPlaceholders(p, Ref.get(make,"name").toString().replace("%player%", p.getName()).replace("%playername%", p.getDisplayName()).replace("%points%", StringUtils.fixedFormatDouble(API.getPoints().get(p.getName())))));
		make.getLore().replaceAll(a -> PlaceholderAPI.setPlaceholders(p, a.replace("%player%", p.getName()).replace("%playername%", p.getDisplayName()).replace("%points%", StringUtils.fixedFormatDouble(API.getPoints().get(p.getName())))));
		return new ItemGUI(make.create()) {
		public void onClick(Player var1, HolderGUI var2, ClickType var3) {
			run.run();
		}};
	}
	
	public static void sellAll(Player p, HolderGUI gui, boolean expand) {
		List<ItemStack> a = new ArrayList<>();
		ItemGUI item = new EmptyItemGUI(new ItemStack(Material.AIR) );
		item.setUnstealable(false);
		if(!expand) {
			for(int count =10; count < 17; ++count) {
				a.add(gui.getItem(count));
				gui.setItem(count, item );
			}
			for(int count =19; count < 26; ++count) {
				a.add(gui.getItem(count));
				gui.setItem(count, item );
			}
			for(int count =28; count < 35; ++count) {
				a.add(gui.getItem(count));
				gui.setItem(count, item );
			}
			for(int count =37; count < 44; ++count) {
				a.add(gui.getItem(count));
				gui.setItem(count, item );
			}
		}else {
			for(int count = 0; count < 45; ++count) {
				a.add(gui.getItem(count));
				gui.setItem(count, item );
			}
		}
		gui.setInsertable(true);
		
		int sel = 0;
		double totalExp=0, totalPoints=0, totalMoney=0;
		for(ItemStack d:a) {
			if(d==null||d.getType()==Material.AIR)continue;
			CatchFish f = API.getCatchFish(d);
			if(f==null) {
				if(API.isFishItem(d) && Loader.config.getBoolean("Options.Shop.SellDefaultFish")==true) {
					if(Loader.config.exists("Options.Sell.DefaultFish."+d.getType().name()+".Money"))
						totalMoney+= Loader.config.getDouble("Options.Sell.DefaultFish."+d.getType()+".Money");
					else
						totalMoney+= Loader.config.getDouble("Options.Sell.DefaultFish.Money");
					
					if(Loader.config.exists("Options.Sell.DefaultFish."+d.getType().name()+".Exps"))
						totalExp+= Loader.config.getDouble("Options.Sell.DefaultFish."+d.getType()+".Exps");
					else
						totalExp+= Loader.config.getDouble("Options.Sell.DefaultFish.Exps");
					
					if(Loader.config.exists("Options.Sell.DefaultFish."+d.getType().name()+".Points"))
						totalPoints+= Loader.config.getDouble("Options.Sell.DefaultFish."+d.getType()+".Points");
					else
						totalPoints+= Loader.config.getDouble("Options.Sell.DefaultFish.Points");
					sel=sel+d.getAmount();
				}else
					TheAPI.giveItem(p, d);
				continue;
			}
			double length = 0, weight = 0;
			int bonus=1; //TODO - Fish Of Day
			
			//MONEY & POINTS
			length=f.getLength();
			weight=f.getWeight();
			
			sel=sel+d.getAmount();
			
			//CALCULATE
			totalMoney += StringUtils.calculate(f.getFish().getCalculator(Calculator.MONEY).replace("%length%", ""+length).replace("%weight%", ""+weight)
					.replace("%money%", ""+f.getFish().getMoney()).replace("%experiences%", ""+f.getFish().getXp())
					.replace("%money_boost%", ""+f.getMoneyBoost()).replace("%points_boost%", ""+f.getPointsBoost()).replace("%exp_boost%", ""+f.getExpBoost())
					.replace("%money_bonus%", ""+f.getMoneyBoost()).replace("%points_bonus%", ""+f.getPointsBoost()).replace("%exp_bonus%", ""+f.getExpBoost())
					.replace("%points%", ""+f.getFish().getPoints()).replace("%bonus%", ""+bonus))*d.getAmount();
			totalExp += StringUtils.calculate(f.getFish().getCalculator(Calculator.EXPS).replace("%length%", ""+length).replace("%weight%", ""+weight)
						.replace("%money%", ""+f.getFish().getMoney()).replace("%experiences%", ""+f.getFish().getXp())
						.replace("%money_boost%", ""+f.getMoneyBoost()).replace("%points_boost%", ""+f.getPointsBoost()).replace("%exp_boost%", ""+f.getExpBoost())
						.replace("%money_bonus%", ""+f.getMoneyBoost()).replace("%points_bonus%", ""+f.getPointsBoost()).replace("%exp_bonus%", ""+f.getExpBoost())
						.replace("%points%", ""+f.getFish().getPoints()).replace("%bonus%", ""+bonus))*d.getAmount();
			totalPoints += StringUtils.calculate(f.getFish().getCalculator(Calculator.POINTS).replace("%length%", ""+length).replace("%weight%", ""+weight)
					.replace("%money%", ""+f.getFish().getMoney()).replace("%experiences%", ""+f.getFish().getXp())
					.replace("%points%", ""+f.getFish().getPoints()).replace("%bonus%", ""+bonus)
					.replace("%money_boost%", ""+f.getMoneyBoost()).replace("%points_boost%", ""+f.getPointsBoost()).replace("%exp_boost%", ""+f.getExpBoost())
					.replace("%money_bonus%", ""+f.getMoneyBoost()).replace("%points_bonus%", ""+f.getPointsBoost()).replace("%exp_bonus%", ""+f.getExpBoost()))*d.getAmount();
			//a.remove(d);
			Statistics.addSelling(p, f.getFish(), d.getAmount()); //Adding fish to Selling statistics
			TheAPI.getNmsProvider().postToMainThread(() -> {
			        Achievements.check(p, f);
			        Quests.addProgress(p, "sell_fish", f.getType().name().toLowerCase()+"."+f.getName(), d.getAmount());
				});
		}
		if(sel != 0) {
			//TODO sounds - EDIT - commands & messages (list)
			if(Loader.config.getBoolean("Options.SellFish.DisableMoney")) totalMoney=0;
			if(Loader.config.getBoolean("Options.SellFish.DisableXP")) totalExp=0;
			if(Loader.config.getBoolean("Options.SellFish.DisablePoints")) totalPoints=0;
			API.getPoints().add(p.getName(), totalPoints);
			EconomyAPI.depositPlayer(p, totalMoney);
			p.giveExp((int)totalExp);

			Statistics.addSellingValues(p, totalMoney, totalPoints, totalExp);
			
			for(String msg: Create.list("sold-fish")) {
				Loader.msg(msg.replace("%amount%", sel+"").replace("%exp%", Loader.ff.format(totalExp))
				.replace("%money%", Loader.ff.format(totalMoney))
				.replace("%points%", Loader.ff.format(totalPoints))
				.replace("%prefix%", Loader.getPrefix()),p);
			}
		}
	}
}

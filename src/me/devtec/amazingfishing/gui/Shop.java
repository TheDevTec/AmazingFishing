package me.devtec.amazingfishing.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Calculator;
import me.devtec.amazingfishing.construct.CatchFish;
import me.devtec.amazingfishing.utils.Achievements;
import me.devtec.amazingfishing.utils.Create;
import me.devtec.amazingfishing.utils.Create.Settings;
import me.devtec.amazingfishing.utils.Quests;
import me.devtec.amazingfishing.utils.Statistics;
import me.devtec.amazingfishing.utils.Trans;
import me.devtec.amazingfishing.utils.Utils;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.TheAPI.SudoType;
import me.devtec.theapi.apis.ItemCreatorAPI;
import me.devtec.theapi.economyapi.EconomyAPI;
import me.devtec.theapi.guiapi.EmptyItemGUI;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.GUI.ClickType;
import me.devtec.theapi.guiapi.HolderGUI;
import me.devtec.theapi.guiapi.ItemGUI;
import me.devtec.theapi.scheduler.Tasker;
import me.devtec.theapi.utils.StringUtils;

public class Shop {
	public static enum ShopType {
		BUY,
		SELL,
		CONVERTOR
	}
	
	public static void openShop(Player p, ShopType t) {
		GUI a = Create.setup(new GUI(Trans.shop_title(t),54) {
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
			}},  s -> Help.open(s), Settings.SIDES);
		if(t==ShopType.SELL)
			a.setInsertable(true);
		new Tasker() {
			public void run() {
				a.setItem(4,c(p,"Points",null));
				a.setItem(26,c(p,"Bag",() -> {
						if(p.hasPermission("amazingfishing.bag"))
						Bag.openBag(p);
					}));
				a.setItem(18,c(p,"Convertor",() -> {
						if(p.hasPermission("amazingfishing.convertor"))
						Convertor.open(p);
					}));
				if(t==ShopType.BUY) {
				if(Loader.config.getBoolean("Options.Shop.SellFish"))
					a.setItem(35,c(p,"SellShop",() -> {
							openShop(p, ShopType.SELL);
						}));
					addItems(a);
				}else {
					//TODO - Fish OF Day
					 a.setItem(35, c(p,"BuyShop",() -> {
								openShop(p, ShopType.BUY);
							}));
					a.setItem(49, new ItemGUI(cc(p,"Sell")) {
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
		for(String item:Loader.shop.getKeys("Items")) {
			double cost = Loader.shop.getDouble("Items."+item+".Cost");
			String ItemName=Loader.shop.exists("Items."+item+".Name")?Loader.shop.getString("Items."+item+".Name")
					.replace("%item%", item).replace("%cost%", cost+""):item;
			Material icon = null;
			try{
				String s = Loader.shop.getString("Items."+item+".Icon").toUpperCase();
				icon=s.contains(":")? new MaterialData(Material.matchMaterial(s.split(":")[0]), StringUtils.getByte(s.split(":")[1])).getItemType() : Material.matchMaterial(s);
			}catch(Exception | NoSuchFieldError err) {}
			if(icon==null)icon=Material.STONE;
			List<String> lore= Loader.shop.getStringList("Items."+item+".Description");
			if(lore!=null)lore.replaceAll(ss -> ss.replace("%item%", item).replace("%cost%", cost+""));
			ItemCreatorAPI a = new ItemCreatorAPI(icon);
			a.setDisplayName(ItemName);
			a.setLore(lore);
			inv.addItem(new ItemGUI(Loader.shop.exists("Items."+item+".ModelData")?Utils.setModel(a.create(), Loader.shop.getInt("Items."+item+".ModelData")):a.create()){
				public void onClick(Player p, HolderGUI arg, ClickType type) {
					giveItem(p, item);
					arg.setItem(4,c(p,"Points",null));
				}
			});
		}
	}
	
	public static void giveItem(Player p,String kit) {
		double cost = Loader.shop.getDouble("Items."+kit+".Cost");
		if(API.getPoints().has(p.getName(), cost)) {
			//TODO sounds
			API.getPoints().remove(p.getName(), cost);
			TheAPI.getNmsProvider().postToMainThread(() -> {for(String f:Loader.shop.getStringList("Items."+kit+".Commands"))
					TheAPI.sudoConsole(SudoType.COMMAND, TheAPI.colorize(f.replace("%player%", p.getName()).replace("%item%", kit).replace("%cost%", cost+"") ));
				});
			for(String f:Loader.shop.getStringList("Items."+kit+".Messages"))
				TheAPI.msg(f.replace("%player%", p.getName()).replace("%item%", kit).replace("%cost%", cost+""),p);
			for(String f:Loader.shop.getKeys("Items."+kit+".Item")) {
				try {
					Material icon = null;
					try{
						String s = Loader.shop.getString("Items."+kit+".Item."+f+".Material").toUpperCase();
						icon=s.contains(":")? new MaterialData(Material.matchMaterial(s.split(":")[0]), StringUtils.getByte(s.split(":")[1])).getItemType() : Material.matchMaterial(s);
					}catch(Exception | NoSuchFieldError err) {}
					if(icon==null)icon=Material.STONE;
					ItemCreatorAPI a = new ItemCreatorAPI(icon);
					a.setAmount(Loader.shop.getInt("Items."+kit+".Item."+f+".Amount")>0?Loader.shop.getInt("Items."+kit+".Item."+f+".Amount"):1);
					a.setDisplayName(Loader.shop.getString("Items."+kit+".Item."+f+".Name").replace("%player%", p.getName()).replace("%item%", kit).replace("%cost%", cost+""));
					List<String> lore = Loader.shop.getStringList("Items."+kit+".Item."+f+".Lore");
					lore.replaceAll(w-> w.replace("%item%", kit).replace("%player%", p.getName()).replace("%cost%", cost+""));
					a.setLore(lore);
					a.setUnbreakable(Loader.shop.getBoolean("Items."+kit+".Item."+f+".Unbreakable"));
					if(Loader.shop.getBoolean("Items."+kit+".Item."+f+".HideEnchants"))
						a.addItemFlag(ItemFlag.HIDE_ENCHANTS);
					if(Loader.shop.getBoolean("Items."+kit+".Item."+f+".HideAttributes"))
						a.addItemFlag(ItemFlag.HIDE_ATTRIBUTES);
					for(String s:Loader.shop.getStringList("Items."+kit+".Item."+f+".Enchants")) {
		            	String ench = s.replace(":", "").replace(" ", "").replaceAll("[0-9]+", "");
		            	int num = StringUtils.getInt(s.replace(":", "").replace(" ", "").replace("_", ""));
		            	if(num==0)num=1;
		            	try {
		            		a.addEnchantment(ench, num);
		            	}catch(Exception e) {
		            		
		            	}
					}
					TheAPI.giveItem(p,Loader.shop.exists("Items."+kit+".Item."+f+".Model")?Utils.setModel(a.create(), Loader.shop.getInt("Items."+kit+".Item."+f+".Model")):a.create());
					}catch(Exception | NoSuchFieldError e) {}
			}
		}
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
			
			for(String msg: Loader.trans.getStringList("SoldFish")) {
				Loader.msg(msg.replace("%amount%", sel+"").replace("%exp%", Loader.ff.format(totalExp))
				.replace("%money%", Loader.ff.format(totalMoney))
				.replace("%points%", Loader.ff.format(totalPoints))
				.replace("%prefix%", Trans.s("Prefix")),p);
			}
		}
	}
	
	private static ItemGUI c(Player p, String item, Runnable r) {
		String name = Loader.shop.getString("GUI."+item+".Name")
				.replace("%player%", p.getName())
				.replace("%playername%", p.getDisplayName())
				.replace("%points%", Loader.ff.format(API.getPoints().get(p.getName())));
		List<String> lore = Loader.shop.getStringList("GUI."+item+".Lore");
		lore.replaceAll(s->s.replace("%player%", p.getName())
					.replace("%playername%", p.getDisplayName())
					.replace("%points%", Loader.ff.format(API.getPoints().get(p.getName()))));
		ItemCreatorAPI a = new ItemCreatorAPI(Create.createItem(name, Utils.createType(Loader.shop.getString("GUI."+item+".Icon")), lore));
		ItemGUI d = new ItemGUI(Loader.shop.exists("GUI."+item+".ModelData")?Utils.setModel(a.create(), Loader.shop.getInt("GUI."+item+".ModelData")):a.create()){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType type) {
				if(r!=null)
					r.run();
		}};
		return d;
	}
	private static ItemStack cc(Player p, String item) {
		String name = Loader.shop.getString("GUI."+item+".Name")
				.replace("%player%", p.getName())
				.replace("%playername%", p.getDisplayName())
				.replace("%points%", Loader.ff.format(API.getPoints().get(p.getName())));
		List<String> lore = Loader.shop.getStringList("GUI."+item+".Lore");
		lore.replaceAll(s->s.replace("%player%", p.getName())
					.replace("%playername%", p.getDisplayName())
					.replace("%points%", Loader.ff.format(API.getPoints().get(p.getName()))));
		ItemCreatorAPI a = new ItemCreatorAPI(Create.createItem(name, Utils.createType(Loader.shop.getString("GUI."+item+".Icon")), lore));
		
		return Loader.shop.exists("GUI."+item+".ModelData")?Utils.setModel(a.create(), Loader.shop.getInt("GUI."+item+".ModelData")):a.create();
	}
}

package me.devtec.amazingfishing.gui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Calculator;
import me.devtec.amazingfishing.construct.CatchFish;
import me.devtec.amazingfishing.gui.Help.BackButton;
import me.devtec.amazingfishing.utils.Create;
import me.devtec.amazingfishing.utils.Quests;
import me.devtec.amazingfishing.utils.Trans;
import me.devtec.amazingfishing.utils.Utils;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.TheAPI.SudoType;
import me.devtec.theapi.apis.ItemCreatorAPI;
import me.devtec.theapi.economyapi.EconomyAPI;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.GUI.ClickType;
import me.devtec.theapi.guiapi.HolderGUI;
import me.devtec.theapi.guiapi.ItemGUI;
import me.devtec.theapi.scheduler.Tasker;
import me.devtec.theapi.utils.StringUtils;
import me.devtec.theapi.utils.datakeeper.Data;
import me.devtec.theapi.utils.nms.NMSAPI;

public class Shop {
	public static enum ShopType {
		Buy,
		Sell
	}
	
	public static void openShop(Player p, ShopType t) {
		GUI a = new GUI(Trans.shop_title(t) ,54) {
			@Override
			public void onClose(Player player) {
				if(t==ShopType.Sell) {
					for(int count =10; count < 17; ++count)
						TheAPI.giveItem(p, getItem(count));
					for(int count =19; count < 26; ++count)
						TheAPI.giveItem(p, getItem(count));
					for(int count =28; count < 34; ++count)
						TheAPI.giveItem(p, getItem(count));
					for(int count =37; count < 44; ++count)
						TheAPI.giveItem(p, getItem(count));
				}
			}
		};
		if(t==ShopType.Sell)
			a.setInsertable(true);
		new Tasker() {
			public void run() {
				Create.prepareInv(a);
				a.setItem(4,c(p,"Points",null));
				if(t==ShopType.Buy) {
				if(Loader.config.getBoolean("Options.Shop.SellFish"))
					a.setItem(45,c(p,"SellShop",new Runnable() {
						@Override
						public void run() {
							openShop(p, ShopType.Sell);
						}}));
				addItems(a);
				}else {
					//TODO - Fish OF Day
					 a.setItem(45, c(p,"BuyShop",new Runnable() {
							@Override
							public void run() {
								openShop(p, ShopType.Buy);
							}}));
					a.setItem(26,c(p,"Bag",new Runnable() {
						@Override
						public void run() {
							Bag.openBag(p, BackButton.Shop);
						}}));
					a.setItem(49,c(p,"Sell",new Runnable() {
						@Override
						public void run() {
							sellAll(p, p.getOpenInventory().getTopInventory(), false);
						}}));
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
				Material.matchMaterial(Loader.shop.getString("Items."+item+".Icon").toUpperCase());
			}catch(Exception | NoSuchFieldError err) {}
			if(icon==null)icon=Material.STONE;
			List<String> lore= Loader.shop.getStringList("Items."+item+".Description");
			if(lore!=null)lore.replaceAll(ss -> ss.replace("%item%", item).replace("%cost%", cost+""));
			ItemCreatorAPI a = new ItemCreatorAPI(new ItemStack(icon));
			a.setDisplayName(ItemName);
			a.setLore(lore);
			inv.addItem(new ItemGUI(Loader.shop.exists("Items."+item+".ModelData")?Utils.setModel(a.create(), Loader.shop.getInt("Items."+item+".ModelData")):a.create()){
				public void onClick(Player p, HolderGUI arg, ClickType type) {
					giveItem(p, item);
				}
			});
		}
	}
	
	public static void giveItem(Player p,String kit) {
		double cost = Loader.shop.getDouble("Items."+kit+".Cost");
		if(API.getPoints().has(p.getName(), cost)) {
			//TODO sounds
			API.getPoints().remove(p.getName(), cost);
			for(String f:Loader.shop.getStringList("Items."+kit+".Commands"))
				TheAPI.sudoConsole(SudoType.COMMAND, TheAPI.colorize(f.replace("%player%", p.getName()).replace("%item%", kit).replace("%cost%", cost+"") ));
			for(String f:Loader.shop.getStringList("Items."+kit+".Messages"))
				TheAPI.msg(f.replace("%player%", p.getName()).replace("%item%", kit).replace("%cost%", cost+""),p);
			for(String f:Loader.shop.getKeys("Items."+kit+".Item")) {
				try {
				ItemCreatorAPI a = new ItemCreatorAPI(Material.matchMaterial(Loader.shop.getString("Items."+kit+".Item."+f+".Material")));
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

	static Data data = new Data("plugins/AmazingFishing/Data.yml");
	public static void sellAll(Player p, Inventory i, boolean expand) {
		List<ItemStack> a = new ArrayList<>();
		if(!expand) {
			for(int count =10; count < 17; ++count)
				a.add(i.getItem(count));
			for(int count =19; count < 26; ++count)
				a.add(i.getItem(count));
			for(int count =28; count < 34; ++count)
				a.add(i.getItem(count));
			for(int count =37; count < 44; ++count)
				a.add(i.getItem(count));
		}else {
			for(int count = 0; count < 45; ++count)
				a.add(i.getItem(count));
		}
		int sel = 0;
		double totalExp=0, totalPoints=0, totalMoney=0;
		for(ItemStack d:a) {
			if(d==null)continue;
			CatchFish f = API.getCatchFish(d);
			if(f==null)continue;
			double length = 0, weight = 0;
			int bonus=1; //TODO - Fish Of Day
			
			//MONEY & POINTS
			length=f.getLength();
			weight=f.getWeigth();
			
			sel=sel+d.getAmount();

			String moneyPath=Loader.config.getString("Options.Shop.Calculator.Money");
			String pointsPath=Loader.config.getString("Options.Shop.Calculator.Points");
			String expsPath=Loader.config.getString("Options.Shop.Calculator.Exps");
			if(f.getFish().getCalculator(Calculator.MONEY)!=null)
				moneyPath=f.getFish().getCalculator(Calculator.MONEY);
			if(f.getFish().getCalculator(Calculator.EXPS)!=null)
				expsPath=f.getFish().getCalculator(Calculator.EXPS);
			if(f.getFish().getCalculator(Calculator.POINTS)!=null)
				pointsPath=f.getFish().getCalculator(Calculator.POINTS);
			//CALCULATE
			totalMoney += StringUtils.calculate(moneyPath.replace("%length%", ""+length).replace("%weight%", ""+weight)
					.replace("%money%", ""+f.getFish().getMoney()).replace("%experiences%", ""+f.getFish().getXp())
					.replace("%points%", ""+f.getFish().getPoints()).replace("%bonus%", ""+bonus)).doubleValue();

			totalExp += StringUtils.calculate(expsPath.replace("%length%", ""+length).replace("%weight%", ""+weight)
						.replace("%money%", ""+f.getFish().getMoney()).replace("%experiences%", ""+f.getFish().getXp())
						.replace("%points%", ""+f.getFish().getPoints()).replace("%bonus%", ""+bonus)).doubleValue();

			totalPoints += StringUtils.calculate(pointsPath.replace("%length%", ""+length).replace("%weight%", ""+weight)
					.replace("%money%", ""+f.getFish().getMoney()).replace("%experiences%", ""+f.getFish().getXp())
					.replace("%points%", ""+f.getFish().getPoints()).replace("%bonus%", ""+bonus)).doubleValue();
			i.remove(d);
			NMSAPI.postToMainThread(new Runnable() {
				public void run() {
			        Quests.addProgress(p, "sell_fish", f.getType().name().toLowerCase()+"."+f.getName());
				}});
		}
		if(sel != 0) {
			//TODO sounds
			if(Loader.config.getBoolean("Options.SellFish.DisableMoney")) totalMoney=0;
			if(Loader.config.getBoolean("Options.SellFish.DisableXP")) totalExp=0;
			if(Loader.config.getBoolean("Options.SellFish.DisablePoints")) totalPoints=0;
			API.getPoints().add(p.getName(), totalPoints);
			EconomyAPI.depositPlayer(p, totalMoney);
			p.giveExp((int)totalExp);

			
			for(String msg: Loader.trans.getStringList("SoldFish")) {
				Loader.msg(msg.replace("%amount%", sel+"").replace("%exp%", String.format("%2.02f",totalExp).replace(",", ".")+"")
				.replace("%money%", String.format("%2.02f",totalMoney).replace(",", ".")+"")
				.replace("%points%", String.format("%2.02f",totalPoints).replace(",", ".")+"")
				.replace("%prefix%", Trans.s("Prefix")),p);
			}
		/*Loader.msg(Trans.s("Prefix")+Trans.s("SoldFish")
			.replace("%amount%", sel+"").replace("%exp%", String.format("%2.02f",totalExp).replace(",", ".")+"")
				.replace("%money%", String.format("%2.02f",totalMoney).replace(",", ".")+"")
				.replace("%points%", String.format("%2.02f",totalPoints).replace(",", ".")+""), p);*/
		}
	}

	private static ItemGUI c(Player p, String item, Runnable r) {
		String name = Loader.shop.getString("GUI."+item+".Name")
				.replace("%player%", p.getName())
				.replace("%playername%", p.getDisplayName())
				.replace("%points%", new DecimalFormat("###,###.#").format(StringUtils.getDouble(String.format("%.2f",API.getPoints().get(p.getName()) ))).replace(",", ".").replaceAll("[^0-9.]+", ",") );
		List<String> lore = Loader.shop.getStringList("GUI."+item+".Lore");
		lore.replaceAll(s->s.replace("%player%", p.getName())
					.replace("%playername%", p.getDisplayName())
					.replace("%points%", new DecimalFormat("###,###.#").format(StringUtils.getDouble(String.format("%.2f",API.getPoints().get(p.getName()) ))).replace(",", ".").replaceAll("[^0-9.]+", ",")  ));
		ItemCreatorAPI a = new ItemCreatorAPI(Create.createItem(name, Material.valueOf(Loader.shop.getString("GUI."+item+".Icon").toUpperCase()), lore));
		ItemGUI d = new ItemGUI(Loader.shop.exists("GUI."+item+".ModelData")?Utils.setModel(a.create(), Loader.shop.getInt("GUI."+item+".ModelData")):a.create()){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType type) {
				if(r!=null)
					r.run();
		}};
		return d;
	}
}

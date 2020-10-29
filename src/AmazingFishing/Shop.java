package AmazingFishing;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import AmazingFishing.Quests.Actions;
import AmazingFishing.help.Type;
import me.DevTec.AmazingFishing.Loader;
import me.DevTec.TheAPI.TheAPI;
import me.DevTec.TheAPI.TheAPI.SudoType;
import me.DevTec.TheAPI.APIs.ItemCreatorAPI;
import me.DevTec.TheAPI.EconomyAPI.EconomyAPI;
import me.DevTec.TheAPI.GUIAPI.GUI;
import me.DevTec.TheAPI.GUIAPI.ItemGUI;
import me.DevTec.TheAPI.Scheduler.Tasker;
import me.DevTec.TheAPI.Utils.StringUtils;
import me.DevTec.TheAPI.Utils.DataKeeper.Data;

public class Shop {
	public Shop(CommandSender s) {
		if(s instanceof Player) {
			openShop((Player)s, ShopType.Buy);
			return;
		}
		Loader.msgCmd(Loader.s("ConsoleErrorMessage"), s);
		return;
	}
	public static enum ShopType {
		Buy,
		Sell
	}
	
	private static ItemGUI c(Player p, String item, Runnable r) {
		ItemCreatorAPI a= new ItemCreatorAPI(new ItemStack(Material.matchMaterial(Loader.shop.getString("GUI."+item+".Icon").toUpperCase())));
		a.setDisplayName(Loader.shop.getString("GUI."+item+".Name").replace("%player%", p.getName()).replace("%playername%", p.getDisplayName()).replace("%points%", Points.getBal(p.getName())));
		List<String> lore =new ArrayList<String>();
		for(String s : Loader.shop.getStringList("GUI."+item+".Lore")) {
			lore.add(s.replace("%player%", p.getName()).replace("%playername%", p.getDisplayName()).replace("%points%", Points.getBal(p.getName())));
		}
		if(Loader.shop.exists("GUI."+item+".ModelData"))
		a.setCustomModelData(Loader.shop.getInt("GUI."+item+".ModelData"));
		a.setLore(lore);
		ItemGUI d = new ItemGUI(a.create()){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {
				if(r!=null)
					r.run();
			}
		};
		return d;
	}
	
	public static void openShop(Player p, ShopType t) {
		GUI a = new GUI(Trans.shoplog()+(t==ShopType.Sell?"&6"+Trans.shoplogsell():""),54,p) {
			@Override
			public void onClose(Player arg0) {
			}
		};
		if(t==ShopType.Sell)
			a.setInsertable(true);
		new Tasker() {
			
			@Override
			public void run() {
		Create.prepareInv(a);
		a.setItem(4,c(p,"Points",null));
		if(t==ShopType.Buy) {
		if(Loader.c.getBoolean("Options.ShopSellFish"))
			a.setItem(45,c(p,"SellShop",new Runnable() {
				@Override
				public void run() {
					openShop(p, ShopType.Sell);
				}}));
		addItems(a);
		}else {
			List<String> s = new ArrayList<String>();
			for(String d : Loader.c.getStringList("Format.FishOfDay"))
				s.add(d
						.replace("%fish_name%", Loader.c.getString("Types."+Loader.f.getType()+"."+Loader.f.getFish()+".Name"))
						.replace("%fish%", Loader.f.getFish())
						.replace("%fish_type%", Loader.f.getType())
						.replace("%bonus%", ""+Loader.f.getBonus()));
			ItemCreatorAPI item = new ItemCreatorAPI(Loader.f.getMaterial());
			int mod = 0;
			if(Loader.c.exists("Types."+Loader.f.getType()+"."+Loader.f.getFish()+".ModelData"))
				mod = Loader.c.getInt("Types."+Loader.f.getType()+"."+Loader.f.getFish()+".ModelData");
			item.setCustomModelData(mod);
			item.setDisplayName(Trans.fishday());
			item.setLore(s);
			a.setItem(35,new ItemGUI(item.create()){
				@Override
				public void onClick(Player p, GUI arg, ClickType type) {
				}
			});
			
			 a.setItem(45, c(p,"BuyShop",new Runnable() {
					@Override
					public void run() {
						openShop(p, ShopType.Buy);
					}}));
			a.setItem(26,c(p,"Bag",new Runnable() {
				@Override
				public void run() {
					bag.openBag(p);
				}}));
			a.setItem(49,c(p,"Sell",new Runnable() {
				@Override
				public void run() {
					sellAll(p, p.getOpenInventory().getTopInventory(), true, false);
				}}));
		}
		if(Loader.c.getBoolean("Options.UseGUI")) {
		a.setItem(53,new ItemGUI(Create.createItem(Trans.back(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {
				help.open(p, Type.Player);
			}
		});
		}
			}
		}.runTask();
	}
	
	private static boolean ex(String ss) {
		return Loader.shop.exists(ss);
	}
	private  static void addItems(GUI inv) {
		try {
		if(Loader.shop.exists("Items"))
		for(String item:Loader.shop.getKeys("Items")) {
		int cost = Loader.shop.getInt("Items."+item+".Cost");
				String ItemName=item;
				if(ex("Items."+item+".Name"))
					ItemName=Loader.shop.getString("Items."+item+".Name").replace("%item%", item).replace("%cost%", cost+"");
				Material icon = Material.matchMaterial(Loader.shop.getString("Items."+item+".Icon").toUpperCase());
				if(icon==null)icon=Material.STONE;
				List<String> lore= new ArrayList<String>();
					if(ex("Items."+item+".Description") && Loader.shop.getStringList("Items."+item+".Description").isEmpty()==false)
					for(String ss:Loader.shop.getStringList("Items."+item+".Description"))lore.add(ss.replace("%item%", item).replace("%cost%", cost+""));
						ItemCreatorAPI a = new ItemCreatorAPI(new ItemStack(icon));
						a.setDisplayName(ItemName);
						a.setLore(lore);
						if(Loader.shop.exists("Items."+item+".ModelData"))
						a.setCustomModelData(Loader.shop.getInt("Items."+item+".ModelData"));
					inv.addItem(new ItemGUI(a.create()){
							@Override
							public void onClick(Player p, GUI arg, ClickType type) {
								giveItem(inv.getPlayers().get(0), item);
							}
						});
			 
		}
		}catch(Exception e) {
			Bukkit.getLogger().severe("Error when adding items to Amazing Fishing Shop");	
			}
		}

	public static void giveItem(Player p,String kit) {
			int cost = Loader.shop.getInt("Items."+kit+".Cost");
			if(Points.has(p.getName(), cost)) {

				if(Loader.c.getBoolean("Options.Sounds.Shop-BuyItem"))
					Sounds.play(p);
				Points.take(p.getName(), cost);
				List<String> cmds=null;
				if(ex("Items."+kit+".Commands"))
					cmds=Loader.shop.getStringList("Items."+kit+".Commands");
				if(cmds != null)
					for(String f:cmds) {
						TheAPI.sudoConsole(SudoType.COMMAND, Color.c(f.replace("%player%", p.getName()).replace("%item%", kit).replace("%cost%", cost+"")));
					}
				
				List<String> msgs=null;
				if(ex("Items."+kit+".Messages"))
					msgs=Loader.shop.getStringList("Items."+kit+".Messages");
				
				if(msgs != null)
					for(String f:msgs) {
						TheAPI.msg(f.replace("%player%", p.getName()).replace("%item%", kit).replace("%cost%", cost+""),p);
					}
				if(ex("Items."+kit+".Item")) {
					for(String f:Loader.shop.getKeys("Items."+kit+".Item")) {
						try {
							ItemCreatorAPI a = new ItemCreatorAPI(Material.matchMaterial(Loader.shop.getString("Items."+kit+".Item."+f+".Material")));
							int amount = 1;
							if(Loader.shop.getInt("Items."+kit+".Item."+f+".Amount")>0)
								amount=Loader.shop.getInt("Items."+kit+".Item."+f+".Amount");
						a.setAmount(amount);
						a.setDisplayName(Loader.shop.getString("Items."+kit+".Item."+f+".Name").replace("%player%", p.getName()).replace("%item%", kit).replace("%cost%", cost+""));
						List<String> lore = new ArrayList<String>();
						for(String w:Loader.shop.getStringList("Items."+kit+".Item."+f+".Lore"))lore.add(w.replace("%item%", kit).replace("%player%", p.getName()).replace("%cost%", cost+""));
						a.setLore(lore);
						if(Loader.shop.exists("Items."+kit+".Item."+f+".ModelData"))
						a.setCustomModelData(Loader.shop.getInt("Items."+kit+".Item."+f+".ModelData"));
						a.setUnbreakable(Loader.shop.getBoolean("Items."+kit+".Item."+f+".Unbreakable"));
						if(Loader.shop.getBoolean("Items."+kit+".Item."+f+".HideEnchants"))
							a.addItemFlag(ItemFlag.HIDE_ENCHANTS);
						if(Loader.shop.getBoolean("Items."+kit+".Item."+f+".HideAttributes"))
							a.addItemFlag(ItemFlag.HIDE_ATTRIBUTES);
						if(Loader.shop.exists("Items."+kit+".Item."+f+".Enchants"))
						for(String s:Loader.shop.getStringList("Items."+kit+".Item."+f+".Enchants")) {
			            	String ench = s.replace(":", "").replace(" ", "").replaceAll("[0-9]+", "");
			            	int num = StringUtils.getInt(s.replace(":", "").replace(" ", "").replace("_", ""));
			            	if(num==0)num=1;
			            	try {
			            		a.addEnchantment(ench, num);
			            	}catch(Exception e) {
			            		
			            	}
						}
						TheAPI.giveItem(p,a.create());
					}catch(Exception e) {
					Bukkit.getLogger().warning("Error when giving item from AmazingFishing Shop to player "+p.getName()+", ShopItem: "+kit+", Item: "+f);	
					}}}}}
	
	public static void sellAll(Player p, Inventory i, boolean sell, boolean expand) {
		ArrayList<ItemStack> a = new ArrayList<ItemStack>();
		if(!expand) {

			for(int count =10; count < 17; ++count) {
				a.add(i.getItem(count));
				}
			for(int count =19; count < 26; ++count) {
				a.add(i.getItem(count));
				}
			for(int count =28; count < 34; ++count) {
				a.add(i.getItem(count));
				}
			for(int count =37; count < 44; ++count) {
				a.add(i.getItem(count));
				}
		}else {
			for(int count = 0; count < 45; ++count) {
				a.add(i.getItem(count));
				}
		}
		double sold = 0.0;
		int amount = 0;
		int exp =0;
		double points = 0.0;
		int sel = 0;
		for(ItemStack d:a) {
			if(d==null)continue;
			if(sell) {
			Material m = d.getType();
			//String w = d.getItemMeta().getDisplayName();
			
			String path = null;
			String type = null;
			if(m==Material.SALMON)type="Salmon";
			if(m==Material.PUFFERFISH)type="PufferFish";
			if(m==Material.TROPICAL_FISH)type="TropicalFish";
			if(m==Material.COD)type="Cod";
			

			ItemStack bukkitstack = d;
			double length = 0.0;
			double weight = 0.0;
			Data data = Tag.getData(bukkitstack);
			if(Loader.c.getBoolean("Options.SellFish.EarnFromLength")==true) 
				length=data.getDouble("af.length");
			if(Loader.c.getBoolean("Options.SellFish.EarnFromWeight")==true)
				weight=data.getDouble("af.weight");
			
			if(d.getItemMeta().hasDisplayName()) {
			path="Types."+type; 
			String fish = data.getString("af.fish");
			/*if(Loader.c.exists(path))
			for(String s:Loader.c.getKeys(path)) {
				if(Loader.c.exists(path+"."+s+".Name")) {
					if(w.equalsIgnoreCase(Color.c(Loader.c.getString(path+"."+s+".Name"))))fish=s;
				}
			}*/
			if(fish!=null) {
			path=path+"."+fish;
			
			int bonus=1;
			if(Loader.f.getFish().equals(fish) && Loader.f.getType().equals(type))
				bonus=Loader.me.getInt("FishOfDay.Bonus");
			
			sel=sel+d.getAmount();
			amount=amount+d.getAmount();
			
			//sold=sold+(( (Loader.c.getBoolean("Options.ShopGiveFullPriceFish")? Loader.c.getDouble(path+".Money") : Loader.c.getDouble(path+".Money")/4)*d.getAmount())*bonus);
			double money = 0;
			if(Loader.c.getBoolean("Options.SellFish.EarnFromLength"))
				money = ((length*(Loader.c.getBoolean("Options.SellFish.ShopGiveFullPriceFish")?Loader.c.getDouble(path+".Money") :Loader.c.getDouble(path+".Money")/4))*bonus);
			if(Loader.c.getBoolean("Options.SellFish.EarnFromWeight"))
				money = money+((weight*(Loader.c.getBoolean("Options.SellFish.ShopGiveFullPriceFish")?Loader.c.getDouble(path+".Money") :Loader.c.getDouble(path+".Money")/4))*bonus);
			if(Loader.c.getBoolean("Options.SellFish.EarnFromLength")==false&&Loader.c.getBoolean("Options.SellFish.EarnFromWeight")==false)
				money = (((Loader.c.getBoolean("Options.SellFish.ShopGiveFullPriceFish")? Loader.c.getDouble(path+".Money") : Loader.c.getDouble(path+".Money")/4)*d.getAmount())*bonus);
			sold = sold+money;
			/*sold=sold+( 
					(Loader.c.getBoolean("Options.SellFish.EarnFromLength")? 
					(length*(Loader.c.getBoolean("Options.SellFish.ShopGiveFullPriceFish")?Loader.c.getDouble(path+".Money") :Loader.c.getDouble(path+".Money")/4))*bonus :
					((Loader.c.getBoolean("Options.SellFish.ShopGiveFullPriceFish")? Loader.c.getDouble(path+".Money") : Loader.c.getDouble(path+".Money")/4)*d.getAmount())*bonus
					)+(
					Loader.c.getBoolean("Options.SellFish.EarnFromWeight")? 
					(weight*(Loader.c.getBoolean("Options.SellFish.ShopGiveFullPriceFish")?Loader.c.getDouble(path+".Money") :Loader.c.getDouble(path+".Money")/4))*bonus :
					((Loader.c.getBoolean("Options.SellFish.ShopGiveFullPriceFish")? Loader.c.getDouble(path+".Money") : Loader.c.getDouble(path+".Money")/4)*d.getAmount())*bonus
					)
					);*/
			// if("Options.EarnFromLength") +length*money :
			//if(length!=0.0) sold=sold+(length*fishMoney);
			
			points=points+(( (Loader.c.getBoolean("Options.SellFish.ShopGiveFullPriceFish")? Loader.c.getDouble(path+".Points") : Loader.c.getDouble(path+".Points")/2)*d.getAmount())*bonus);
			exp=exp+(int)(( (Loader.c.getBoolean("Options.SellFish.ShopGiveFullPriceFish")? Loader.c.getDouble(path+".Xp") : Loader.c.getDouble(path+".Xp")/2)*d.getAmount())*bonus);
			
			Quests.addProgress(p,path,fish,Actions.SELL_FISH);
			i.remove(d);
		}else {
			TheAPI.giveItem(p, d);
			i.remove(d);
		}}else {
				Material fish = null;
				if(m==Material.SALMON || m==Material.COD || m==Material.TROPICAL_FISH||m==Material.PUFFERFISH)fish=Material.STONE;
				
				if(fish!=null) {
					sel=sel+d.getAmount();
					amount=amount+d.getAmount();
					sold=sold+(0.1*d.getAmount());
					points=points+(0.1*d.getAmount());
					exp=exp+(1*d.getAmount());
				i.remove(d);
			}else{
				TheAPI.giveItem(p, d);
				i.remove(d);
				}
			}
			}else {
				TheAPI.giveItem(p, d);
				i.remove(d);
			}
		}
		if(sell && sel != 0) {
			if(!expand) {
			if(Loader.c.getBoolean("Options.Sounds.Shop-SellFish"))
				Sounds.play(p);
			}else {
				if(Loader.c.getBoolean("Options.Sounds.Bag-SellFish"))
					Sounds.play(p);
			}
			
			if(Loader.c.getBoolean("Options.SellFish.DisableMoney")==true) sold=0.0;
			if(Loader.c.getBoolean("Options.SellFish.DisableXP")==true) exp=0;
			if(Loader.c.getBoolean("Options.SellFish.DisablePoints")==true) points=0.0;
			EconomyAPI.depositPlayer(p.getName(), sold);
			Points.give(p.getName(), points);
			p.giveExp(exp);
			
		Loader.msgCmd(Loader.s("Prefix")+Loader.s("SoldFish")
		.replace("%amount%", sel+"")
		.replace("%exp%", exp+"")
				.replace("%money%", String.format("%2.02f",sold).replace(",", ".")+"")
				.replace("%points%", String.format("%2.02f",points).replace(",", ".")+""), p);
	}}}

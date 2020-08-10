package AmazingFishing;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import AmazingFishing.Shop.ShopType;
import me.DevTec.TheAPI;
import me.DevTec.AmazingFishing.Configs;
import me.DevTec.AmazingFishing.Loader;
import me.DevTec.GUI.GUICreatorAPI;
import me.DevTec.GUI.ItemGUI;

public class bag {
	public static void openBag(Player p) {
		GUICreatorAPI a = new GUICreatorAPI("&b"+Trans.bag_title(),54,p) {
			
			@Override
			public void onClose(Player arg0) {
				saveBag(p,p.getOpenInventory().getTopInventory());
			}
		};
		Create.prepareInvBig(a);

		a.setItem(49,new ItemGUI( Create.createItem(Trans.close(), Material.BARRIER)) {
			@Override
			public void onClick(Player p, GUICreatorAPI arg1, ClickType arg2) {
				p.getOpenInventory().close();
			}
		});
		
		if(Loader.c.getBoolean("Options.ShopSellFish")) {
			if(Loader.c.getBoolean("Options.Bag.ButtonsToSellFish")) {
			a.setItem(51,new ItemGUI(Create.createItem(Trans.sellFish(), Material.COD_BUCKET)) {
				@Override
				public void onClick(Player p, GUICreatorAPI arg1, ClickType arg2) {
					Shop.sellAll(p, p.getOpenInventory().getTopInventory(), true, true);
					saveBag(p,p.getOpenInventory().getTopInventory());
				}
			});
			a.setItem(47,new ItemGUI(Create.createItem(Trans.sellFish(), Material.COD_BUCKET)) {
				@Override
				public void onClick(Player p, GUICreatorAPI arg1, ClickType arg2) {
					Shop.sellAll(p, p.getOpenInventory().getTopInventory(), true, true);
					saveBag(p,p.getOpenInventory().getTopInventory());
				}
			});
		}}
		if(Loader.c.getBoolean("Options.Shop")) {
			if(Loader.c.getBoolean("Options.Bag.ButtonsToOpenShop")) {
				a.setItem(45, new ItemGUI(Create.createItem(Trans.help_shop(), Material.EMERALD)){
					@Override
					public void onClick(Player p, GUICreatorAPI arg1, ClickType arg2) {
						Shop.openShop(p, ShopType.Buy);
					}
				});
				a.setItem(53, new ItemGUI(Create.createItem(Trans.help_shop(), Material.EMERALD)){
					@Override
					public void onClick(Player p, GUICreatorAPI arg1, ClickType arg2) {
						Shop.openShop(p, ShopType.Buy);
					}
				});
				
		}}
		if(getFish(p).isEmpty()==false)
		for(ItemStack as : getFish(p)) {
			ItemGUI item = new ItemGUI(as){
				@Override
				public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
				}
			};
			item.setUnstealable(false);
			a.addItem(item);
		}
		a.setInsertable(true);
	}
	/*
	 a.setItem(-, new ItemGUI(){
					@Override
					public void onClick(Player p, GUICreatorAPI arg, ClickType type) {
					}
				});
	 */
	public static void saveBag(Player p, Inventory i) {
		Loader.me.set("Players."+p.getName()+".Bag",null);
		Configs.me.save();
		for(int count = 0; count < 45; ++count) {
			if(i.getItem(count)==null)continue;
			addFish(p,i.getItem(count));
		}
		
	}

	private static List<ItemStack> getFish(Player p){
		List<ItemStack> i = new ArrayList<ItemStack>();
		if(Loader.me.getString("Players."+p.getName()+".Bag")!=null) {
			for(int count = 0; count < 45; ++count) {
				if(Loader.me.getString("Players."+p.getName()+".Bag."+count)!=null)
				i.add(Loader.me.getItemStack("Players."+p.getName()+".Bag."+count));
			}
		}
		return i;
	}
	private static boolean getFirstEmpty(Player p, ItemStack i) {
		boolean find = false;
		if(!isFishBag(i) || !p.hasPermission("amazingfishing.bag"))return false;
		for(int count = 0; count < 45; ++count) {
			if(find)break;
			if(Loader.me.getString("Players."+p.getName()+".Bag."+count)==null) {
				Loader.me.set("Players."+p.getName()+".Bag."+count, i);
				Configs.me.save();
				find=true;
			}else {
				ItemStack a = Loader.me.getItemStack("Players."+p.getName()+".Bag."+count).clone();
				a.setAmount(1);
				ItemStack b = i.clone();
				b.setAmount(1);
				if(a.equals(b)) {
					find=true;
					ItemStack c = i.clone();
					if(i.getAmount()+Loader.me.getItemStack("Players."+p.getName()+".Bag."+count).getAmount()<64) {
						c.setAmount(i.getAmount()+Loader.me.getItemStack("Players."+p.getName()+".Bag."+count).getAmount());
						Loader.me.set("Players."+p.getName()+".Bag."+count, c);
						Configs.me.save();
						find=true;
					}
				}
			}
		}
		return find;
	}
	public static boolean isFish(ItemStack i) {
		Material a = i.getType();
		if(a==Material.COD||a==Material.SALMON||a==Material.PUFFERFISH||a==Material.TROPICAL_FISH)return true;
		return false;
	}
	private static boolean isFishBag(ItemStack i) {
		Material a = i.getType();
		for(String s:Loader.c.getStringList("Options.Bag.StorageItems")) {
			if(s.equalsIgnoreCase("fishes")||s.equalsIgnoreCase("fish")) {
				if(a==Material.COD||a==Material.SALMON||a==Material.PUFFERFISH||a==Material.TROPICAL_FISH)return true;
			}else
			if(s.equalsIgnoreCase(a.name()))return true;
		}
		return false;
	}
	public static void addFish(Player p, ItemStack fish) {
		if(Loader.c.getBoolean("Options.Fish.HideEnchants")==true&& !fish.getEnchantments().isEmpty()) {
			Bukkit.broadcastMessage("2");
			ItemMeta m = fish.getItemMeta();
			m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			fish.setItemMeta(m);
		}
		
		if(Loader.c.getBoolean("Options.Bag.StoreCaughtFish")==false)TheAPI.giveItem(p,fish);
		else { if(!getFirstEmpty(p,fish))TheAPI.giveItem(p,fish); }
	}
}

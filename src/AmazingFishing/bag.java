package AmazingFishing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import AmazingFishing.Shop.ShopType;
import me.DevTec.TheAPI;
import me.DevTec.GUI.GUICreatorAPI;
import me.DevTec.GUI.GUICreatorAPI.Options;

public class bag {
	public static void openBag(Player p) {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI(p);
		a.setTitle("&b"+Trans.bag_title());
		a.setSize(54);
		Create.prepareInvBig(a);
		HashMap<Options, Object> w = new HashMap<Options, Object>();
		w.put(Options.CANT_BE_TAKEN, true);
		w.put(Options.RUNNABLE_ON_INV_CLOSE, new Runnable() {
			@Override
			public void run() {
				saveBag(p,p.getOpenInventory().getTopInventory());
			}
		});
		w.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				p.getOpenInventory().close();
			}
		});
		a.setItem(49, Create.createItem(Trans.close(), Material.BARRIER),w);
		if(Loader.c.getBoolean("Options.ShopSellFish")) {
			if(Loader.c.getBoolean("Options.Bag.ButtonsToSellFish")) {
			w.remove(Options.RUNNABLE);
			w.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					Shop.sellAll(p, p.getOpenInventory().getTopInventory(), true, true);
					saveBag(p,p.getOpenInventory().getTopInventory());
				}
			});
		a.setItem(51, Create.createItem(Trans.sellFish(), Material.COD_BUCKET),w);
		a.setItem(47, Create.createItem(Trans.sellFish(), Material.COD_BUCKET),w);
		}}
		if(Loader.c.getBoolean("Options.Shop")) {
			if(Loader.c.getBoolean("Options.Bag.ButtonsToOpenShop")) {
				w.remove(Options.RUNNABLE);
				w.put(Options.RUNNABLE, new Runnable() {
					@Override
					public void run() {
						Shop.openShop(p, ShopType.Buy);
					}
				});
				a.setItem(45, Create.createItem(Trans.help_shop(), Material.EMERALD),w);
				a.setItem(53, Create.createItem(Trans.help_shop(), Material.EMERALD),w);
		}}
		if(getFish(p).isEmpty()==false)
		for(ItemStack as : getFish(p)) {
			
			a.addItem(as);
		}
		a.open();
	}
	public static void saveBag(Player p, Inventory i) {
		Loader.me.set("Players."+p.getName()+".Bag",null);
		Loader.saveChatMe();
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
				Loader.saveChatMe();
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
						Loader.saveChatMe();
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
		if(!getFirstEmpty(p,fish))TheAPI.giveItem(p,fish);
	}
}

package AmazingFishing;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import AmazingFishing.Shop.ShopType;
import AmazingFishing.APIs.Enums.BackButton;
import AmazingFishing.APIs.Enums.PlayerType;
import me.DevTec.AmazingFishing.Loader;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.ItemGUI;

public class bag {
	public static void openBag(Player p, BackButton BackButton) {
		GUI a = new GUI("&b"+Trans.bag_title(),54,p) {
			
			@Override
			public void onClose(Player arg0) {
				saveBag(p,this);
			}
		};
		Create.prepareInvBig(a);
		
		if(BackButton.equals(AmazingFishing.APIs.Enums.BackButton.Close)) {
			a.setItem(49,new ItemGUI( Create.createItem(Trans.close(), Material.BARRIER)) {
				@Override
				public void onClick(Player p, GUI arg1, ClickType arg2) {
					arg1.close(p);
					
				}
			});
		} else {
			a.setItem(49,new ItemGUI( Create.createItem(Trans.back(), Material.BARRIER)) {
				@Override
				public void onClick(Player p, GUI menu, ClickType arg2) {
					switch(BackButton) {
						case FishAdmin:
							help.open(p, PlayerType.Admin);
							break;
						case FishPlayer:
							help.open(p, PlayerType.Player);
							break;
						case Shop:
							Shop.openShop(p, ShopType.Sell);
							break;
						default:
							menu.close(p);
							break;
					}
						
					
				}
			});
		}
		
		if(Loader.c.getBoolean("Options.ShopSellFish")) {
			if(Loader.c.getBoolean("Options.Bag.ButtonsToSellFish")) {
			a.setItem(51,new ItemGUI(Create.createItem(Trans.sellFish(), Material.COD_BUCKET)) {
				@Override
				public void onClick(Player p, GUI arg1, ClickType arg2) {
					Shop.sellAll(p, p.getOpenInventory().getTopInventory(), true, true);
					//saveBag(p,p.getOpenInventory().getTopInventory());
				}
			});
			a.setItem(47,new ItemGUI(Create.createItem(Trans.sellFish(), Material.COD_BUCKET)) {
				@Override
				public void onClick(Player p, GUI arg1, ClickType arg2) {
					Shop.sellAll(p, p.getOpenInventory().getTopInventory(), true, true);
				}
			});
		}}
		if(Loader.c.getBoolean("Options.Shop")) {
			if(Loader.c.getBoolean("Options.Bag.ButtonsToOpenShop")) {
				a.setItem(45, new ItemGUI(Create.createItem(Trans.help_shop(), Material.EMERALD)){
					@Override
					public void onClick(Player p, GUI arg1, ClickType arg2) {
						Shop.openShop(p, ShopType.Buy);
					}
				});
				a.setItem(53, new ItemGUI(Create.createItem(Trans.help_shop(), Material.EMERALD)){
					@Override
					public void onClick(Player p, GUI arg1, ClickType arg2) {
						Shop.openShop(p, ShopType.Buy);
					}
				});
				
		}}
		for(ItemStack as : getFish(p)) {
			ItemGUI item = new ItemGUI(as){
				@Override
				public void onClick(Player p, GUI arg, ClickType type) {
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
					public void onClick(Player p, GUI arg, ClickType type) {
					}
				});
	 */
	public static void saveBag(Player p, GUI i) {
		Loader.me.set("Players."+p.getName()+".Bag", null);
		for(int count = 0; count < 45; ++count) {
			if(i.getItem(count)==null)continue;
			addFish(p,i.getItem(count));
		}
		Loader.me.save();
	}

	private static List<ItemStack> getFish(Player p){
		return Loader.me.getData().getListAs("Players."+p.getName()+".Bag", ItemStack.class);
	}
	private static boolean getFirstEmpty(Player p, ItemStack i) {
		if(!isFishBag(i))return false;
		List<ItemStack> fish = Loader.me.getData().getListAs("Players."+p.getName()+".Bag", ItemStack.class);
		if(fish.size()<45) {
			fish.add(i);
			Loader.me.set("Players."+p.getName()+".Bag", fish);
			return true;
		}
		return false;
		/*for(int count = 0; count < 45; ++count) {
			if(!Loader.me.exists("Players."+p.getName()+".Bag."+count)) { //chtìlo by to pøedìlat celý saving tohoto
				//to asi ano 
				//až na to bude èas :D
				Loader.me.set("Players."+p.getName()+".Bag."+count, i);
				Loader.me.save();
				find=true;
				break;
			}/*else {
				ItemStack a = ((ItemStack)Loader.me.get("Players."+p.getName()+".Bag."+count)).clone();
				a.setAmount(1);
				ItemStack b = i.clone();
				b.setAmount(1);
				if(a.equals(b)) {
					find=true;
					ItemStack c = i.clone();
					if(i.getAmount()+((ItemStack)Loader.me.get("Players."+p.getName()+".Bag."+count)).getAmount()<64) {
						c.setAmount(i.getAmount()+((ItemStack)Loader.me.get("Players."+p.getName()+".Bag."+count)).getAmount());
						Loader.me.set("Players."+p.getName()+".Bag."+count, c);
						Loader.me.save();
						find=true;
					}
				}
			}
		}
		return find;*/
	}
	public static boolean isFish(ItemStack i) {
		Material a = i.getType();
		if(a==Material.COD||a==Material.SALMON||a==Material.PUFFERFISH||a==Material.TROPICAL_FISH)return true;
		return false;
	}
	public static boolean isFishBag(ItemStack i) {
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
	public static void addFishToBagOrInv(Player p, ItemStack fish) {
		if(Loader.c.getBoolean("Options.Fish.HideEnchants")==true&& !fish.getEnchantments().isEmpty()) {
			ItemMeta m = fish.getItemMeta();
			m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			fish.setItemMeta(m);
		}
		if(Loader.c.getBoolean("Options.Bag.StoreCaughtFish")==false)TheAPI.giveItem(p,fish);
		else { if(!getFirstEmpty(p,fish))TheAPI.giveItem(p,fish); }

	}
}

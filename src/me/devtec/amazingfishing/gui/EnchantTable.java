package me.devtec.amazingfishing.gui;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Enchant;
import me.devtec.amazingfishing.other.Rod;
import me.devtec.amazingfishing.utils.Create;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.GUI.ClickType;
import me.devtec.theapi.guiapi.HolderGUI;
import me.devtec.theapi.guiapi.ItemGUI;
import me.devtec.theapi.utils.datakeeper.Data;
import me.devtec.theapi.utils.nms.nbt.NBTEdit;

public class EnchantTable {
	public static void openMain(Player p) {
		GUI a = Create.setup(new GUI(Create.title("enchant.title"),54), Create.make("enchant.close").create(), f -> Help.open(f), me.devtec.amazingfishing.utils.Create.Settings.SIDES);
			a.setItem(20,new ItemGUI(Create.make("enchant.add").create()){
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType type) {
					if(!Enchant.enchants.isEmpty())
					openEnchanterPlace(p, 0);
				}
			});
			a.setItem(24,new ItemGUI(Create.make("enchant.upgrade").create()){
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType type) {
					if(!Enchant.enchants.isEmpty())
					openEnchanterPlace(p, 1);
				}
			});
			a.open(p);
		}
	
	private static boolean openEnchanterPlace(Player p, int type) {
		GUI a = Create.setup(new GUI(Create.title("enchant.title-select"),54), Create.make("enchant.close").create(), f -> openMain(f), me.devtec.amazingfishing.utils.Create.Settings.SIDES);
		int slot = -1;
		boolean add = false;
		if(p.getInventory().getContents()!=null)
		for(ItemStack item : p.getInventory().getContents()) {
			++slot;
			if(item==null)continue;
			if(item.getType()!=Material.FISHING_ROD)continue;
			if(type==0?!canAdd(item):!containsAny(item))continue;
			int ss = slot;
			add=true;
			a.addItem(new ItemGUI(item){
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType ctype) {
					Rod.saveRod(p,item);
					p.getInventory().setItem(ss, new ItemStack(Material.AIR));
					if(type== 1)
						openEnchantUpgrade(p);
					else
						openEnchantAdd(p);
				}
			});
		}
		if(add)
		a.open(p);
		else a.clear();
		return add;
	}

	private static boolean canAdd(ItemStack item) {
		for(Enchant enchant: Enchant.enchants.values())
			 if(!enchant.containsEnchant(item))return true;
		return false;
	}
	private static boolean containsAny(ItemStack item) {
		for(Enchant enchant: Enchant.enchants.values())
			 if(enchant.containsEnchant(item) && enchant.getMaxLevel()>getLevel(item, enchant.getName()))return true;
		return false;
	}
	
	private static int getLevel(ItemStack rod, String enchant) {
		NBTEdit edit = new NBTEdit(rod);
		Data data = new Data();
		if(edit.hasKey("af_data"))
			data.reload(edit.getString("af_data"));
		return data.getInt("enchants."+enchant.toLowerCase());
	}
	
	public static void openEnchantAdd(Player p) {
		GUI a = Create.setup(new GUI(Create.title("enchant.title-add"),54) {
			public void onClose(Player arg0) {
				if(Rod.saved(p))
					Rod.retriveRod(p);
			}
		}, Create.make("enchant.close").create(), f -> openMain(f));
		a.setItem(4,Shop.replace(p,Create.make("enchant.points"), () -> {}));
		for(Enchant enchant: Enchant.enchants.values()) {
			 ItemStack rod = Rod.getRod(p);
			 if(!enchant.containsEnchant(rod)) {
				String name = enchant.getDisplayName();
				List<String> lore = enchant.getDescription();
				double cost = enchant.getCost();
				a.addItem(new ItemGUI(Create.createItem(name, Material.ENCHANTED_BOOK,lore)){
					public void onClick(Player p, HolderGUI arg, ClickType type) {
						if(API.getPoints().has(p.getName(), cost)) {
							API.getPoints().remove(p.getName(), cost);
							enchant.enchant(rod, 1);
							TheAPI.giveItem(p, rod);
							Rod.deleteRod(p);
							if(!openEnchanterPlace(p, 0))
								openMain(p);
							return;
						}else
							Loader.msg(Create.text("points.lack").replace("%amount%", ""+cost), p);
						return;
					}
				});
			 }
		}
		a.open(p);
	}
	
	public static void openEnchantUpgrade(Player p) {
		GUI a = Create.setup(new GUI(Create.title("enchant.title-upgrade"),54) {
			public void onClose(Player arg0) {
				if(Rod.saved(p))
					Rod.retriveRod(p);
			}
		}, Create.make("enchant.close").create(), f -> openMain(f));
		a.setItem(4,Shop.replace(p,Create.make("enchant.points"), () -> {}));
		for(Enchant enchant: Enchant.enchants.values()) {
			 ItemStack rod = Rod.getRod(p);
			 if(enchant.containsEnchant(rod)) {
				String name = enchant.getDisplayName();
				List<String> lore = enchant.getDescription();
				double cost = enchant.getCost();
				a.addItem(new ItemGUI(Create.createItem(name, Material.PAPER,lore)){
					public void onClick(Player p, HolderGUI arg, ClickType type) {
						if(API.getPoints().has(p.getName(), cost)) {
							API.getPoints().remove(p.getName(), cost);
							enchant.enchant(rod, 1);
							TheAPI.giveItem(p, rod);
							Rod.deleteRod(p);
							if(!openEnchanterPlace(p, 1))
								openMain(p);
							return;
					}else
						Loader.msg(Create.text("points.lack").replace("%amount%", ""+cost), p);
					return;
					}
				});
			 }
		}
		a.open(p);
	}
}

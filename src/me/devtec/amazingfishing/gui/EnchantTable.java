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
import me.devtec.amazingfishing.utils.Trans;
import me.devtec.amazingfishing.utils.Utils;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.guiapi.EmptyItemGUI;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.GUI.ClickType;
import me.devtec.theapi.guiapi.HolderGUI;
import me.devtec.theapi.guiapi.ItemGUI;
import me.devtec.theapi.utils.datakeeper.Data;

public class EnchantTable {

	public static enum EnchantGUI{
		Main,
		Rod_Upgrade,
		Rod_Add,
		Enchant,
		Upgrade;
	}
	public static void open(Player p, EnchantGUI type) {
		if(type==EnchantGUI.Main) openMain(p);
		if(!Enchant.enchants.isEmpty()) {
		if(type==EnchantGUI.Rod_Upgrade) openEnchanterPlace(p, type);
		if(type==EnchantGUI.Rod_Add) openEnchanterPlace(p, type);
		if(type==EnchantGUI.Enchant) openEnchantAdd(p);
		if(type==EnchantGUI.Upgrade) openEnchantUpgrade(p);
		}
	}
	private static void openMain(Player p) {
		GUI a = new GUI(Trans.enchant_title(),54);
		Create.prepareInv(a);
			a.setItem(49,new ItemGUI(Create.createItem(Trans.words_back(), Material.BARRIER)){
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType type) {
					Help.open(p);
				}
			});
			a.setItem(20,new ItemGUI(Create.createItem(Trans.enchant_add(), Utils.getCachedMaterial("CRAFTING_TABLE"))){
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType type) {
					open(p, EnchantGUI.Rod_Add);
				}
			});

			a.setItem(22, new ItemGUI(Create.createItem(Trans.enchant_retrive(), Material.FISHING_ROD)){
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType type) {
					if(Rod.saved(p)) {
						ItemStack rod = Rod.getRod(p);
						TheAPI.giveItem(p, rod);
						Rod.deleteRod(p);
					}
				}
			});
			a.setItem(24,new ItemGUI(Create.createItem(Trans.enchant_upgrade(), Material.ANVIL)){
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType type) {
					open(p, EnchantGUI.Rod_Upgrade);
				}
			});
			a.open(p);
		}
	
	private static boolean openEnchanterPlace(Player p, EnchantGUI type) {
		GUI a = new GUI(Trans.enchant_selectRod_title(),54);
		Create.prepareInv(a);
		int slot = -1;
		boolean add = false;
		if(p.getInventory().getContents()!=null)
		for(ItemStack item : p.getInventory().getContents()) {
			++slot;
			if(item==null)continue;
			if(item.getType()!=Material.FISHING_ROD)continue;
			if(type==EnchantGUI.Rod_Add?!canAdd(item):!containsAny(item))continue;
			int ss = slot;
			add=true;
			a.addItem(new ItemGUI(item){
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType ctype) {
					Rod.saveRod(p,item);
					p.getInventory().setItem(ss, new ItemStack(Material.AIR));
					if(type== EnchantGUI.Rod_Upgrade)
						open(p, EnchantGUI.Upgrade);
					else
						open(p, EnchantGUI.Enchant);
				}
			});
		}
		a.setItem(49,new ItemGUI(Create.createItem(Trans.words_back(), Material.BARRIER)){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType type) {
				open(p, EnchantGUI.Main);
			}
		});
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
		Object r = Utils.asNMS(rod);
		Object n = Utils.getNBT(r);
		Data data = Utils.getString(n);
		return data.getInt("enchants."+enchant.toLowerCase());
	}
	
	public static void openEnchantAdd(Player p) {
		Material mat = Material.ENCHANTED_BOOK;
		GUI a = new GUI(Trans.enchant_add_title(),54) {
			
			@Override
			public void onClose(Player arg0) {
				if(Rod.saved(p))
					Rod.retriveRod(p);
			}
		};
		Create.prepareInv(a);
		a.setItem(4,new EmptyItemGUI(Create.createItem(Trans.words_points()
						.replace("%value%", ""+API.getPoints().get(p.getName())), Utils.getCachedMaterial("LAPIS_LAZULI"))));
		ItemGUI getRod = new ItemGUI(Rod.getRod(p)){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType type) {
				Rod.retriveRod(p);
				a.close();
			}
		};
		a.setItem(1,getRod);
		a.setItem(6,getRod);
		a.setItem(47,getRod);
		a.setItem(51,getRod);
		a.setItem(49,new ItemGUI(Create.createItem(Trans.words_cancel(), Material.BARRIER)){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType type) {
				open(p, EnchantGUI.Main);
			}
		});
		for(Enchant enchant: Enchant.enchants.values()) {
			 ItemStack rod = Rod.getRod(p);
			 if(!enchant.containsEnchant(rod)) {
				String name = enchant.getDisplayName();
				List<String> lore = enchant.getDescription();
				double cost = enchant.getCost();
				a.addItem(new ItemGUI(Create.createItem(name, mat,lore)){
					@Override
					public void onClick(Player p, HolderGUI arg, ClickType type) {
						if(API.getPoints().has(p.getName(), cost)) {
							API.getPoints().remove(p.getName(), cost);
							enchant.enchant(rod, 1);
							TheAPI.giveItem(p, rod);
							Rod.deleteRod(p);
							if(!openEnchanterPlace(p, EnchantGUI.Rod_Add))
								openMain(p);
							return;
						}
						else
						Loader.msg(Trans.s("Points.Lack").replace("%amount%", ""+cost), p);
						return;
					}
				});
			 }
		}
		a.open(p);
	}
	
	public static void openEnchantUpgrade(Player p) {
		Material mat = Material.PAPER;
		GUI a = new GUI(Trans.enchant_upgrade_title(),54) {
			public void onClose(Player arg0) {
				if(Rod.saved(p)) {
					Rod.retriveRod(p);
				}
			}
		};
		Create.prepareInv(a);
		a.setItem(4, new EmptyItemGUI(Create.createItem(Trans.words_points()
						.replace("%value%", ""+API.getPoints().get(p.getName())), Utils.getCachedMaterial("LAPIS_LAZULI"))));
		ItemGUI getRod = new ItemGUI(Rod.getRod(p)){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType type) {
				Rod.retriveRod(p);
				a.close();
			}
		};
		a.setItem(1,getRod);
		a.setItem(6,getRod);
		a.setItem(47,getRod);
		a.setItem(51,getRod);
		a.setItem(49,new ItemGUI(Create.createItem(Trans.words_cancel(), Material.BARRIER)){
			@Override
			public void onClick(Player p, HolderGUI arg, ClickType type) {
				open(p, EnchantGUI.Main);
			}
		});
		for(Enchant enchant: Enchant.enchants.values()) {
			 ItemStack rod = Rod.getRod(p);
			 if(enchant.containsEnchant(rod)) {
				String name = enchant.getDisplayName();
				List<String> lore = enchant.getDescription();
				double cost = enchant.getCost();
				a.addItem(new ItemGUI(Create.createItem(name, mat,lore)){
					@Override
					public void onClick(Player p, HolderGUI arg, ClickType type) {
						if(API.getPoints().has(p.getName(), cost)) {
							API.getPoints().remove(p.getName(), cost);
							enchant.enchant(rod, 1);
							TheAPI.giveItem(p, rod);
							Rod.deleteRod(p);
							if(!openEnchanterPlace(p, EnchantGUI.Rod_Upgrade))
								openMain(p);
							return;
					}
					else Loader.msg(Trans.s("Points.Lack").replace("%amount%", ""+cost), p);
					return;
					}
				});
			 }
		}
		a.open(p);
	}
}

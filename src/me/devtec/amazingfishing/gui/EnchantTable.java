package me.devtec.amazingfishing.gui;

import java.text.DecimalFormat;
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
import me.devtec.theapi.utils.StringUtils;
import me.devtec.theapi.utils.datakeeper.Data;
import me.devtec.theapi.utils.nms.nbt.NBTEdit;

public class EnchantTable {
	public static void openMain(Player p) {
		GUI a = Create.setup(new GUI(Trans.enchant_title(),54), f -> Help.open(f), me.devtec.amazingfishing.utils.Create.Settings.SIDES);
			a.setItem(20,new ItemGUI(Create.createItem(Trans.enchant_add(), Utils.getCachedMaterial("CRAFTING_TABLE"))){
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType type) {
					if(!Enchant.enchants.isEmpty())
					openEnchanterPlace(p, 0);
				}
			});
			a.setItem(24,new ItemGUI(Create.createItem(Trans.enchant_upgrade(), Material.ANVIL)){
				@Override
				public void onClick(Player p, HolderGUI arg, ClickType type) {
					if(!Enchant.enchants.isEmpty())
					openEnchanterPlace(p, 1);
				}
			});
			a.open(p);
		}
	
	private static boolean openEnchanterPlace(Player p, int type) {
		GUI a = Create.setup(new GUI(Trans.enchant_selectRod_title(),54), f -> openMain(f), me.devtec.amazingfishing.utils.Create.Settings.SIDES);
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
		if(edit.getString("af_data")!=null)
		data.reload(edit.getString("af_data"));
		return data.getInt("enchants."+enchant.toLowerCase());
	}
	
	public static void openEnchantAdd(Player p) {
		GUI a = Create.setup(new GUI(Trans.enchant_add_title(),54) {
			public void onClose(Player arg0) {
				if(Rod.saved(p))
					Rod.retriveRod(p);
			}
		}, f -> openMain(f));
		a.setItem(4,new ItemGUI( Create.createItem(Trans.words_points()
				.replace("%value%", new DecimalFormat("###,###.#").format(StringUtils.getDouble(String.format("%.2f",API.getPoints().get(p.getName()) ))).replace(",", ".").replaceAll("[^0-9.]+", ",") )
				.replace("%points%", new DecimalFormat("###,###.#").format(StringUtils.getDouble(String.format("%.2f",API.getPoints().get(p.getName()) ))).replace(",", ".").replaceAll("[^0-9.]+", ",") )
				, Utils.getCachedMaterial("LAPIS_LAZULI")) ) {
			
			@Override
			public void onClick(Player player, HolderGUI gui, ClickType click) {
				
			}
		});
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
		GUI a = Create.setup(new GUI(Trans.enchant_upgrade_title(),54) {
			public void onClose(Player arg0) {
				if(Rod.saved(p))
					Rod.retriveRod(p);
			}
		}, f -> openMain(f));
		a.setItem(4, new EmptyItemGUI(Create.createItem(Trans.words_points()
				.replace("%value%", new DecimalFormat("###,###.#").format(StringUtils.getDouble(String.format("%.2f",API.getPoints().get(p.getName()) ))).replace(",", ".").replaceAll("[^0-9.]+", ",") )
				.replace("%points%", new DecimalFormat("###,###.#").format(StringUtils.getDouble(String.format("%.2f",API.getPoints().get(p.getName()) ))).replace(",", ".").replaceAll("[^0-9.]+", ",") )
						, Utils.getCachedMaterial("LAPIS_LAZULI"))));
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

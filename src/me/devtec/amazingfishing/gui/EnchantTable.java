package me.devtec.amazingfishing.gui;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.Loader;
import me.devtec.amazingfishing.construct.Enchant;
import me.devtec.amazingfishing.gui.Help.PlayerType;
import me.devtec.amazingfishing.other.Rod;
import me.devtec.amazingfishing.utils.Create;
import me.devtec.amazingfishing.utils.Trans;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.ItemGUI;

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
		if(type==EnchantGUI.Rod_Upgrade) openEnchanterPlace(p, type);
		if(type==EnchantGUI.Rod_Add) openEnchanterPlace(p, type);
		if(type==EnchantGUI.Enchant) openEnchantAdd(p);
		if(type==EnchantGUI.Upgrade) openEnchantUpgrade(p);;
	}
	private static void openMain(Player p) {
		GUI a = new GUI(Trans.enchant_title(),54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
			a.setItem(49,new ItemGUI(Create.createItem(Trans.words_back(), Material.BARRIER)){
				@Override
				public void onClick(Player p, GUI arg, ClickType type) {
					Help.open(p, PlayerType.Player);
				}
			});
			a.setItem(20,new ItemGUI(Create.createItem(Trans.enchant_add(), Material.CRAFTING_TABLE)){
				@Override
				public void onClick(Player p, GUI arg, ClickType type) {
					open(p, EnchantGUI.Rod_Add);
					//openEnchanterPlace(p,"add");
				}
			});

			a.setItem(22, new ItemGUI(Create.createItem(Trans.enchant_retrive(), Material.FISHING_ROD)){
				@Override
				public void onClick(Player p, GUI arg, ClickType type) {
					if(Rod.saved(p)) {
						ItemStack rod = Rod.getRod(p);
						TheAPI.giveItem(p, rod);
						Rod.deleteRod(p);
					}
				}
			});
			a.setItem(24,new ItemGUI(Create.createItem(Trans.enchant_upgrade(), Material.ANVIL)){
				@Override
				public void onClick(Player p, GUI arg, ClickType type) {
					open(p, EnchantGUI.Rod_Upgrade);
					//openEnchanterPlace(p,"up");
				}
			});
		}
	
	private static void openEnchanterPlace(Player p, EnchantGUI type) {
		GUI a = new GUI(Trans.enchant_selectRod_title(),54,p) {
			
			@Override
			public void onClose(Player arg0) {
			}
		};
		Create.prepareInv(a);
		if(p.getInventory().getContents()!=null)
		for(ItemStack i : p.getInventory().getContents()) {
			if(i==null)continue;
			if(i.getType()!=Material.FISHING_ROD)continue;
			a.addItem(new ItemGUI(i){
				@Override
				public void onClick(Player p, GUI arg, ClickType ctype) {
					Rod.saveRod(p, i);
					p.getInventory().removeItem(i);
					if(type== EnchantGUI.Rod_Upgrade)
						open(p, EnchantGUI.Upgrade);
					else
						open(p, EnchantGUI.Enchant);
				}
			});
		}
		a.setItem(49,new ItemGUI(Create.createItem(Trans.words_back(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {
				open(p, EnchantGUI.Main);
			}
		});
	}
	
	public static void openEnchantAdd(Player p) {
		Material mat = Material.ENCHANTED_BOOK;
		GUI a = new GUI(Trans.enchant_add_title(),54,p) {
			
			@Override
			public void onClose(Player arg0) {
				if(Rod.saved(p)) {
					Rod.retriveRod(p);
				}
			}
		};
		Create.prepareInv(a);
		a.setItem(4,
				new ItemGUI(Create.createItem(Trans.words_points()
						.replace("%value%", ""+API.getPoints().get(p.getName())), Material.LAPIS_LAZULI)){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {
			}
		});
		if(Rod.saved(p)) {
			List<Integer> slots = Arrays.asList(1, 6, 47, 51);
			for(int slot: slots) {
				a.setItem(slot,new ItemGUI(Rod.getRod(p)){
					@Override
					public void onClick(Player p, GUI arg, ClickType type) {
						Rod.retriveRod(p);
						a.close();
					}
				});
			}
		}

		a.setItem(49,new ItemGUI(Create.createItem(Trans.words_cancel(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {
				open(p, EnchantGUI.Main);
			}
		});
		if(!Enchant.enchants.isEmpty() ) {
			for(Enchant enchant: Enchant.enchants.values()) {
				
				 ItemStack rod = Rod.getRod(p);
				 if(!enchant.containsEnchant(rod)) {
						String name = enchant.getDisplayName();
						List<String> lore = enchant.getDescription();
						double cost = enchant.getCost();
						
						a.addItem(new ItemGUI(Create.createItem(name, mat,lore)){
							@Override
							public void onClick(Player p, GUI arg, ClickType type) {

								if(API.getPoints().has(p.getName(), cost)) {
									API.getPoints().remove(p.getName(), cost);
									enchant.enchant(rod, 1);
									/*f.addUnsafeEnchantment(c, l+1);
									ItemMeta m = f.getItemMeta();
									List<String> as = m.getLore() != null ? m.getLore() : new ArrayList<String>();
									as.add(TheAPI.colorize(c.getName()+" "+Utils.trasfer(l+1)));
									m.setLore(as);
									f.setItemMeta(m);*/
									TheAPI.giveItem(p, rod);
									Rod.deleteRod(p);
									openEnchanterPlace(p, EnchantGUI.Main);
							}
							Loader.msg(Trans.s("Points.Lack").replace("%amount%", ""+cost), p);
							return;
							}
						});
				 }
			}
		}
	}
	
	public static void openEnchantUpgrade(Player p) {
		Material mat = Material.PAPER;
		GUI a = new GUI(Trans.enchant_upgrade_title(),54,p) {
			
			@Override
			public void onClose(Player arg0) {
				if(Rod.saved(p)) {
					Rod.retriveRod(p);
				}
			}
		};
		Create.prepareInv(a);
		a.setItem(4,
				new ItemGUI(Create.createItem(Trans.words_points()
						.replace("%value%", ""+API.getPoints().get(p.getName())), Material.LAPIS_LAZULI)){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {
			}
		});
		if(Rod.saved(p)) {
			List<Integer> slots = Arrays.asList(1, 6, 47, 51);
			for(int slot: slots) {
				a.setItem(slot,new ItemGUI(Rod.getRod(p)){
					@Override
					public void onClick(Player p, GUI arg, ClickType type) {
						Rod.retriveRod(p);
						a.close();
					}
				});
			}
		}

		a.setItem(49,new ItemGUI(Create.createItem(Trans.words_cancel(), Material.BARRIER)){
			@Override
			public void onClick(Player p, GUI arg, ClickType type) {
				open(p, EnchantGUI.Main);
			}
		});
		if(!Enchant.enchants.isEmpty() ) {
			for(Enchant enchant: Enchant.enchants.values()) {
				
				 ItemStack rod = Rod.getRod(p);
				 if(enchant.containsEnchant(rod)) {
						String name = enchant.getDisplayName();
						List<String> lore = enchant.getDescription();
						double cost = enchant.getCost();
						
						a.addItem(new ItemGUI(Create.createItem(name, mat,lore)){
							@Override
							public void onClick(Player p, GUI arg, ClickType type) {

								if(API.getPoints().has(p.getName(), cost)) {
									API.getPoints().remove(p.getName(), cost);
									enchant.enchant(rod, 1);
									TheAPI.giveItem(p, rod);
									Rod.deleteRod(p);
									open(p, EnchantGUI.Main );
							}
							Loader.msg(Trans.s("Points.Lack").replace("%amount%", ""+cost), p);
							return;
							}
						});
				 }
			}
		}
	}
}

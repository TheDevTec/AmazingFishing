package me.devtec.amazingfishing.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.devtec.amazingfishing.API;
import me.devtec.amazingfishing.utils.Create;
import me.devtec.amazingfishing.utils.Create.Settings;
import me.devtec.amazingfishing.utils.Trans;
import me.devtec.amazingfishing.utils.Utils;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.guiapi.EmptyItemGUI;
import me.devtec.theapi.guiapi.GUI;
import me.devtec.theapi.guiapi.GUI.ClickType;
import me.devtec.theapi.guiapi.HolderGUI;
import me.devtec.theapi.guiapi.ItemGUI;

public class Convertor {
	public static void open(Player p) {
		GUI a = Create.setup(new GUI(Trans.convertor_title(),54) {
			public void onClose(Player arg0) {
				for(int count =0; count < 44; ++count)
					if(count!=4 && count!=13 && count!=22 && count!=31 && count!=40)
						TheAPI.giveItem(p, getItem(count));
			}
		}, f -> Help.open(f), Settings.WITHOUT_TOP);
		a.setItem(49,new ItemGUI(Create.createItem(Trans.words_convert(), Utils.getCachedMaterial("LIME_STAINED_GLASS_PANE"))) {
			public void onClick(Player p, HolderGUI arg1, ClickType arg2) {
				convert(arg1);
			}
			
			public void convert(HolderGUI arg1, int count, int d) {
				if(d==-1)return;
				ItemStack convert = arg1.getItem(count);
				if(convert==null)return;
				if(API.isFishItem(convert)) {
					int amount = convert.getAmount()+(arg1.getItem(d)==null?0:arg1.getItem(d).getAmount());
					if(amount>=65) {
						arg1.setItem(d, new EmptyItemGUI(new ItemStack(convert.getType(), 64, convert.getDurability())).setUnstealable(false));
						amount-=64;
						convert.setAmount(amount);
						arg1.setItem(count, new EmptyItemGUI(convert).setUnstealable(false));
						int find = find(arg1, count);
						if(find!=-1) {
							convert(arg1, count, find);
						}else {
							return; //full inv
						}
					}else {
						arg1.remove(count);
						arg1.setItem(d, new EmptyItemGUI(new ItemStack(convert.getType(), amount, convert.getDurability())).setUnstealable(false));
					}
				}
			}

			private void convert(HolderGUI arg1) {
				for(int count =0; count < 4; ++count)
					if(arg1.getItem(count)==null || arg1.getItem(count).getType()==Material.AIR)continue;
					else
						convert(arg1, count, find(arg1, count));
				for(int count =9; count < 13; ++count)
					if(arg1.getItem(count)==null || arg1.getItem(count).getType()==Material.AIR)continue;
					else
						convert(arg1, count, find(arg1, count));
				for(int count =18; count < 22; ++count)
					if(arg1.getItem(count)==null || arg1.getItem(count).getType()==Material.AIR)continue;
					else
						convert(arg1, count, find(arg1, count));
				for(int count =27; count < 31; ++count)
					if(arg1.getItem(count)==null || arg1.getItem(count).getType()==Material.AIR)continue;
					else
						convert(arg1, count, find(arg1, count));
				for(int count =36; count < 40; ++count)
					if(arg1.getItem(count)==null || arg1.getItem(count).getType()==Material.AIR)continue;
					else
						convert(arg1, count, find(arg1, count));
			}

			private int find(HolderGUI arg1, int c) {
				ItemStack stack = arg1.getItem(c);
				for(int count =5; count < 9; ++count) {
					int i = find(arg1, stack, count);
					if(i!=-1)return i;
				}
				for(int count =14; count < 18; ++count) {
					int i = find(arg1, stack, count);
					if(i!=-1)return i;
				}
				for(int count =23; count < 27; ++count) {
					int i = find(arg1, stack, count);
					if(i!=-1)return i;
				}
				for(int count =32; count < 36; ++count) {
					int i = find(arg1, stack, count);
					if(i!=-1)return i;
				}
				for(int count =41; count < 45; ++count) {
					int i = find(arg1, stack, count);
					if(i!=-1)return i;
				}
				return -1;
			}

			private int find(HolderGUI arg1, ItemStack stack, int count) {
				if(stack!=null)
				if(arg1.getItem(count)==null||arg1.getItem(count).getType()==Material.AIR||arg1.getItem(count).getType()==stack.getType()
				&& arg1.getItem(count).getAmount()!=64)
					return count;
				return -1;
			}
		});
		a.setItem(4, Create.item);
		a.setItem(13, Create.item);
		a.setItem(22, Create.item);
		a.setItem(31, Create.item);
		a.setItem(40, Create.item);
		a.setInsertable(true);
		a.open(p);
	}

}

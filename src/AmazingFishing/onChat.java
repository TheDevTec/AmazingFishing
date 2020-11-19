package AmazingFishing;

import org.bukkit.event.Listener;

public class onChat implements Listener {
/*	private void edit(Player p ,String message,PlayerChatEvent e, FishType type) {
		String path = "";
		String typ = "";
		switch(type) {
		case COD:
			path="Cod";
			typ ="Cod";
			break;
		case SALMON:
			path="Salmon";
			typ ="Salmon";
			break;
		case PUFFERFISH:
			path="Pufferfish";
			typ ="PufferFish";
			break;
		case TROPICAL_FISH:
			path="Tropical_Fish";
			typ ="TropicalFish";
			break;
		}
		if(Loader.c.exists("Edit-"+path+"."+p.getName())) {
			create c = create.valueOf(Loader.c.getString("Edit-"+path+"."+p.getName()+".Type"));
			String id =Loader.c.getString("Edit-"+path+"."+p.getName()+".Fish");
			
			switch(c) {
			case Money:
				e.setCancelled(true);
				if(id == null) {
					p.sendTitle(Loader.s("Editor.MissingFishName.1"), Loader.s("Editor.MissingFishName.2"));
					if(!Loader.c.getBoolean("Edit-"+path+"."+p.getName()+".Warned")) {
					Loader.c.set("Edit-"+path+"."+p.getName()+".Warned", true);
					Loader.save();
					String d = path ;
					new Tasker() {
						public void run() {
							TheAPI_GUIs gui = new TheAPI_GUIs();
							gui.openFishEditType(p, id, type);
						Loader.c.set("Edit-"+d+"."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
				Loader.c.set("Types."+typ+"."+id+".Money", StringUtils.getDouble(message.replace(" ", "")));
				Loader.c.set("Edit-"+path, null);
				Loader.save();
				TheAPI_GUIs gui = new TheAPI_GUIs();
				gui.openFishEditType(p, id, type);}
				break;

			case Cm:
				e.setCancelled(true);
				if(id == null) {
					p.sendTitle(Loader.s("Editor.MissingFishName.1"), Loader.s("Editor.MissingFishName.2"));
					if(!Loader.c.getBoolean("Edit-"+path+"."+p.getName()+".Warned")) {
					Loader.c.set("Edit-"+path+"."+p.getName()+".Warned", true);
					Loader.save();
					String da = path;
					new Tasker() {
						public void run() {
							TheAPI_GUIs gui = new TheAPI_GUIs();
							gui.openFishEditType(p, id, type);
						Loader.c.set("Edit-"+da+"."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
				Loader.c.set("Types."+typ+"."+id+".MaxCm", StringUtils.getDouble(message.replace(" ", "")));
				Loader.save();
				TheAPI_GUIs gui = new TheAPI_GUIs();
				gui.openFishEditType(p, id, type);}
				break;

			case Chance:
				e.setCancelled(true);
				if(id == null) {
					p.sendTitle(Loader.s("Editor.MissingFishName.1"), Loader.s("Editor.MissingFishName.2"));
					if(!Loader.c.getBoolean("Edit-"+path+"."+p.getName()+".Warned")) {
					Loader.c.set("Edit-"+path+"."+p.getName()+".Warned", true);
					Loader.save();
					String da = path;
					new Tasker() {
						public void run() {
							TheAPI_GUIs gui = new TheAPI_GUIs();
							gui.openFishEditType(p, id, type);
						Loader.c.set("Edit-"+da+"."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
				Loader.c.set("Types."+typ+"."+id+".Chance", StringUtils.getDouble(message.replace(" ", "")));
				Loader.save();
				TheAPI_GUIs gui = new TheAPI_GUIs();
				gui.openFishEditType(p, id, type);}
				break;
			case Exp:
				e.setCancelled(true);
				if(id == null) {
					p.sendTitle(Loader.s("Editor.MissingFishName.1"), Loader.s("Editor.MissingFishName.2"));
					if(!Loader.c.getBoolean("Edit-"+path+"."+p.getName()+".Warned")) {
					Loader.c.set("Edit-"+path+"."+p.getName()+".Warned", true);
					Loader.save();
					String da = path;
					new Tasker() {
						public void run() {
							TheAPI_GUIs gui = new TheAPI_GUIs();
							gui.openFishEditType(p, id, type);
						Loader.c.set("Edit-"+da+"."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
				Loader.c.set("Types."+typ+"."+id+".Xp", StringUtils.getInt(message.replace(" ", "")));
				Loader.save();
				TheAPI_GUIs gui = new TheAPI_GUIs();
				gui.openFishEditType(p, id, type);}
				break;
			case Points:
				e.setCancelled(true);
				if(id == null) {
					p.sendTitle(Loader.s("Editor.MissingFishName.1"), Loader.s("Editor.MissingFishName.2"));
					if(!Loader.c.getBoolean("Edit-"+path+"."+p.getName()+".Warned")) {
					Loader.c.set("Edit-"+path+"."+p.getName()+".Warned", true);
					Loader.save();
					String da = path;
					new Tasker() {
						public void run() {
							TheAPI_GUIs gui = new TheAPI_GUIs();
							gui.openFishEditType(p, id, type);
						Loader.c.set("Edit-"+da+"."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
				Loader.c.set("Types."+typ+"."+id+".Points", StringUtils.getDouble(message.replace(" ", "")));
				Loader.save();
				TheAPI_GUIs gui = new TheAPI_GUIs();
				gui.openFishEditType(p, id, type);}
				break;
			case Name:
				Loader.c.set("Types."+typ+"."+id+".Name",message);
				Loader.save();
				e.setCancelled(true);
				TheAPI_GUIs gui = new TheAPI_GUIs();
				gui.openFishEditType(p, id, type);
				break;
			}}}
	private void setEverything(Player p, String msg, FishType type) {
		String path = null;
		String typ = null;
		switch(type) {
		case COD:
			path="Cod";
			typ ="Cod";
			break;
		case SALMON:
			path="Salmon";
			typ ="Salmon";
			break;
		case PUFFERFISH:
			path="Pufferfish";
			typ ="PufferFish";
			break;
		case TROPICAL_FISH:
			path="Tropical_Fish";
			typ ="TropicalFish";
			break;
		}
		if(Loader.c.exists("Creating-"+path+"."+p.getName())) {
			String fish =get.fish(p, path);
			if(get.typ(p, path)==null) {
				TheAPI_GUIs gui = new TheAPI_GUIs();
				gui.openFishCreatorType(p, fish, type);
				return;
			}
			switch(get.typ(p, path)) {
			case Points:
				if(fish == null)
					get.warn(p, path, type);
					else {
				Loader.c.set("Creating-"+path+"."+p.getName()+".Points", StringUtils.getDouble(msg.replace(" ", "")));
				Loader.c.set("Creating-"+path+"."+p.getName()+".Type", null);
				Loader.save();
				if(get.ready(p, path)) {
					get.finish(p, typ,true);
			}else {
				TheAPI_GUIs gui = new TheAPI_GUIs();
				gui.openFishCreatorType(p, fish, type);}
				}
			break;
			case Cm:
				if(fish == null)
					get.warn(p, path, type);
					else {
				Loader.c.set("Creating-"+path+"."+p.getName()+".MaxCm", StringUtils.getDouble(msg.replace(" ", "")));
				Loader.c.set("Creating-"+path+"."+p.getName()+".Type", null);
				Loader.save();
				if(get.ready(p, path)) {
					get.finish(p, typ,true);
			}else{
				TheAPI_GUIs gui = new TheAPI_GUIs();
				gui.openFishCreatorType(p, fish, type);}
				}
			break;
			case Money:
				if(fish == null)
					get.warn(p, path, type);
					else {
				Loader.c.set("Creating-"+path+"."+p.getName()+".Money", StringUtils.getDouble(msg.replace(" ", "")));
				Loader.c.set("Creating-"+path+"."+p.getName()+".Type", null);
				Loader.save();
				if(get.ready(p, path)) {
					get.finish(p, typ,true);
			}else {
				TheAPI_GUIs gui = new TheAPI_GUIs();
				gui.openFishCreatorType(p, fish, type);}}
			break;
			case Chance:
				if(fish == null)
					get.warn(p, path, type);
					else {
				Loader.c.set("Creating-"+path+"."+p.getName()+".Chance", StringUtils.getDouble(msg.replace(" ", "")));
				Loader.c.set("Creating-"+path+"."+p.getName()+".Type", null);
				Loader.save();
				if(get.ready(p, path)) {
					get.finish(p, typ,true);
			}else {
				TheAPI_GUIs gui = new TheAPI_GUIs();
				gui.openFishCreatorType(p, fish, type);}}
			break;
			case Exp:
				if(fish == null)
					get.warn(p, path, type);
					else {
						Loader.c.set("Creating-"+path+"."+p.getName()+".Xp", StringUtils.getInt(msg.replace(" ", "")));
						Loader.c.set("Creating-"+path+"."+p.getName()+".Type", null);
				Loader.save();
				if(get.ready(p, path)) 
				get.finish(p, typ,true);
			else {
				TheAPI_GUIs gui = new TheAPI_GUIs();
				gui.openFishCreatorType(p, fish, type);}}
			break;
			case Name:
				if(fish==null) {
				get.setfish(p, path, typ);
				}
				fish=get.fish(p, path);
				
				Loader.c.set("Creating-"+path+"."+p.getName()+".Name", msg);
				Loader.c.set("Creating-"+path+"."+p.getName()+".Type", null);
				Loader.save();
				TheAPI_GUIs gui = new TheAPI_GUIs();
				gui.openFishCreatorType(p, fish, type);
			break;
			}}}
	@EventHandler
	public void onSendMessage(PlayerChatEvent e) {
		Player p = e.getPlayer();
		if(ex("Creating-Pufferfish")||ex("Creating-Cod")||ex("Creating-Salmon")||ex("Creating-Tropical_Fish")) {
		FishType type = null;
		if(Loader.c.exists("Creating-Pufferfish."+p.getName()))type=FishType.PUFFERFISH;
		if(Loader.c.exists("Creating-Cod."+p.getName()))type=FishType.COD;
		if(Loader.c.exists("Creating-Salmon."+p.getName()))type=FishType.SALMON;
		if(Loader.c.exists("Creating-Tropical_Fish."+p.getName()))type=FishType.TROPICAL_FISH;
		if(type!=null) {
			e.setCancelled(true);
			setEverything(p,e.getMessage(),type);
		}}
		if(ex("Edit-Pufferfish")||ex("Edit-Cod")||ex("Edit-Salmon")||ex("Edit-Tropical_Fish")) {
		FishType w = null;
		if(Loader.c.exists("Edit-Pufferfish."+p.getName()))w=FishType.PUFFERFISH;
		if(Loader.c.exists("Edit-Cod."+p.getName()))w=FishType.COD;
		if(Loader.c.exists("Edit-Salmon."+p.getName()))w=FishType.SALMON;
		if(Loader.c.exists("Edit-Tropical_Fish."+p.getName()))w=FishType.TROPICAL_FISH;
		if(w!=null)
		edit(p,e.getMessage(),e,w);
		}
		if(ex("Edit-Enchants")) {
			editEnch(p,e.getMessage(),e);
		}
		if(ex("Creating-Enchants")) {
			createEnch(p,e.getMessage(),e);
		}
		if(ex("Creating-Legendary")||ex("Creating-Epic")||ex("Creating-Rare")||ex("Creating-Common")) {
			TreasureType w = null;
		if(Loader.c.exists("Creating-Legendary."+p.getName()))w=TreasureType.LEGEND;
		if(Loader.c.exists("Creating-Epic."+p.getName()))w=TreasureType.EPIC;
		if(Loader.c.exists("Creating-Rare."+p.getName()))w=TreasureType.RARE;
		if(Loader.c.exists("Creating-Common."+p.getName()))w=TreasureType.COMMON;
		if(w!=null)
		setEverythingTreasure(p,e.getMessage(),e,w);
		}
		if(ex("Edit-Legendary")||ex("Edit-Epic")||ex("Edit-Rare")||ex("Edit-Common")) {
			TreasureType w = null;
		if(Loader.c.exists("Edit-Legendary."+p.getName()))w=TreasureType.LEGEND;
		if(Loader.c.exists("Edit-Epic."+p.getName()))w=TreasureType.EPIC;
		if(Loader.c.exists("Edit-Rare."+p.getName()))w=TreasureType.RARE;
		if(Loader.c.exists("Edit-Common."+p.getName()))w=TreasureType.COMMON;
		if(w!=null)
			editTreasure(p,e.getMessage(),e,w);
		}}
	private void editEnch(Player p, String message, PlayerChatEvent e) {
		if(Loader.c.exists("Edit-Enchants."+p.getName())) {
			String n =Loader.c.getString("Edit-Enchants."+p.getName()+".Fish");
			enchs c = enchs.valueOf(Loader.c.getString("Edit-Enchants."+p.getName()+".Type"));
		    String fish = n;
			switch(c) {
			case PointsBonus:
				e.setCancelled(true);
				if(fish == null) {
					p.sendTitle(Loader.get("MissingEnchantName",1), Loader.get("MissingEnchantName",2));
					if(!Loader.c.getBoolean("Edit-Enchants."+p.getName()+".Warned")) {
					Loader.c.set("Edit-Enchants."+p.getName()+".Warned", true);
					Loader.save();
					new Tasker() {
						public void run() {
							ench.openEditor(p, ench.select.CREATE, fish);
						Loader.c.set("Edit-Enchants."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
				Loader.c.set("Enchants."+fish+".PointsBonus", StringUtils.getDouble(message.replace(" ", "")));
				Loader.save();
						
				if(ex("Enchants."+fish+".Cost") && ex("Enchants."+fish+".MoneyBonus") && ex("Enchants."+fish+".PointsBonus")) {
					p.getOpenInventory().close();
				Loader.c.set("Edit-Enchants."+p.getName(), null);
				Loader.save();
				p.sendTitle(Loader.get("SuccefullyCreatedEnchant",1)
						.replace("%enchant%", Loader.c.getString("Enchants."+fish+".Name")),Loader.get("SuccefullyCreatedEnchant",2)
						.replace("%enchant%", Loader.c.getString("Enchants."+fish+".Name")));
			}else
				ench.openEditor(p, ench.select.EDIT, fish);
				}
				break;
			case MoneyBonus:
				e.setCancelled(true);
				if(fish == null) {
					p.sendTitle(Loader.get("MissingEnchantName",1), Loader.get("MissingEnchantName",2));
					if(!Loader.c.getBoolean("Edit-Enchants."+p.getName()+".Warned")) {
					Loader.c.set("Edit-Enchants."+p.getName()+".Warned", true);
					Loader.save();
					new Tasker() {
						public void run() {
							ench.openEditor(p, ench.select.CREATE, fish);
						Loader.c.set("Edit-Enchants."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
				Loader.c.set("Enchants."+fish+".MoneyBonus", StringUtils.getDouble(message.replace(" ", "")));
				Loader.save();
						
				if(ex("Enchants."+fish+".Cost") && ex("Enchants."+fish+".MoneyBonus") && ex("Enchants."+fish+".PointsBonus")) {
					p.getOpenInventory().close();
				Loader.c.set("Edit-Enchants."+p.getName(), null);
				Loader.save();
				p.sendTitle(Loader.get("SuccefullyCreatedEnchant",1)
						.replace("%enchant%", Loader.c.getString("Enchants."+fish+".Name")),Loader.get("SuccefullyCreatedEnchant",2)
						.replace("%enchant%", Loader.c.getString("Enchants."+fish+".Name")));
			}else
				ench.openEditor(p, ench.select.EDIT, fish);
				}
				break;
			case Cost:
				e.setCancelled(true);
				if(fish == null) {
					p.sendTitle(Loader.get("MissingEnchantName",1), Loader.get("MissingEnchantName",2));
					if(!Loader.c.getBoolean("Edit-Enchants."+p.getName()+".Warned")) {
					Loader.c.set("Edit-Enchants."+p.getName()+".Warned", true);
					Loader.save();
					new Tasker() {
						public void run() {
							ench.openEditor(p, ench.select.CREATE, fish);
						Loader.c.set("Edit-Enchants."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
				Loader.c.set("Enchants."+fish+".Cost", StringUtils.getDouble(message.replace(" ", "")));
				Loader.save();
						
				if(ex("Enchants."+fish+".Cost") && ex("Enchants."+fish+".MoneyBonus") && ex("Enchants."+fish+".PointsBonus")) {
					p.getOpenInventory().close();
				Loader.c.set("Edit-Enchants."+p.getName(), null);
				Loader.save();
				p.sendTitle(Loader.get("SuccefullyCreatedEnchant",1)
						.replace("%enchant%", Loader.c.getString("Enchants."+fish+".Name")),Loader.get("SuccefullyCreatedEnchant",2)
						.replace("%enchant%", Loader.c.getString("Enchants."+fish+".Name")));
			}else
				ench.openEditor(p, ench.select.EDIT, fish);
				}
				break;
			case Description:
				e.setCancelled(true);
				if(fish == null) {
					p.sendTitle(Loader.get("MissingEnchantName",1), Loader.get("MissingEnchantName",2));
					if(!Loader.c.getBoolean("Edit-Enchants."+p.getName()+".Warned")) {
					Loader.c.set("Edit-Enchants."+p.getName()+".Warned", true);
					Loader.save();
					new Tasker() {
						public void run() {
							ench.openEditor(p, ench.select.CREATE, fish);
						Loader.c.set("Edit-Enchants."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
							List<String> list = Loader.c.getStringList("Enchants."+fish+".Description");
							list.add(message);
				Loader.c.set("Enchants."+fish+".Description", list);
				Loader.save();
				ench.openEditor(p, ench.select.EDIT, fish);
				}
				break;
			case Name:
				Loader.c.set("Enchants."+fish+".Name",message);
				Loader.save();
				e.setCancelled(true);
				ench.openEditor(p, ench.select.EDIT, fish);
				break;
			case ExpBonus:
				e.setCancelled(true);
				if(fish == null) {
					p.sendTitle(Loader.get("MissingEnchantName",1), Loader.get("MissingEnchantName",2));
					if(!Loader.c.getBoolean("Edit-Enchants."+p.getName()+".Warned")) {
					Loader.c.set("Edit-Enchants."+p.getName()+".Warned", true);
					Loader.save();
					new Tasker() {
						public void run() {
							ench.openEditor(p, ench.select.CREATE, fish);
						Loader.c.set("Edit-Enchants."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
				Loader.c.set("Enchants."+fish+".ExpBonus", StringUtils.getDouble(message.replace(" ", "")));
				Loader.save();
				ench.openEditor(p, ench.select.EDIT, fish);
				}
				break;
			case AmountBonus:
				e.setCancelled(true);
				if(fish == null) {
					p.sendTitle(Loader.get("MissingEnchantName",1), Loader.get("MissingEnchantName",2));
					if(!Loader.c.getBoolean("Edit-Enchants."+p.getName()+".Warned")) {
					Loader.c.set("Edit-Enchants."+p.getName()+".Warned", true);
					Loader.save();
					new Tasker() {
						public void run() {
							ench.openEditor(p, ench.select.CREATE, fish);
						Loader.c.set("Edit-Enchants."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
				Loader.c.set("Enchants."+fish+".AmountBonus", StringUtils.getDouble(message.replace(" ", "")));
				Loader.save();
				ench.openEditor(p, ench.select.EDIT, fish);
				}
				break;
			}}}
	private void createEnch(Player p, String message, PlayerChatEvent e) {
		if(Loader.c.exists("Creating-Enchants."+p.getName())) {
			String n =Loader.c.getString("Creating-Enchants."+p.getName()+".Fish");
			enchs c = enchs.valueOf(Loader.c.getString("Creating-Enchants."+p.getName()+".Type"));
			switch(c) {
			case PointsBonus:
				e.setCancelled(true);
				if(n == null) {
					p.sendTitle(Loader.get("MissingEnchantName",1), Loader.get("MissingEnchantName",2));
					if(!Loader.c.getBoolean("Creating-Enchants."+p.getName()+".Warned")) {
					Loader.c.set("Creating-Enchants."+p.getName()+".Warned", true);
					Loader.save();
					String ed = n;
					new Tasker() {
						public void run() {
							ench.openEditor(p, ench.select.CREATE, ed);
						Loader.c.set("Creating-Enchants."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
				Loader.c.set("Enchants."+n+".PointsBonus", StringUtils.getDouble(message.replace(" ", "")));
				Loader.save();

				if(ex("Enchants."+n+".Cost") && ex("Enchants."+n+".AmountBonus") &&ex("Enchants."+n+".MoneyBonus") && ex("Enchants."+n+".PointsBonus") && ex("Enchants."+n+".ExpBonus")) {
					p.getOpenInventory().close();
				Loader.c.set("Creating-Enchants."+p.getName(), null);
				Loader.save();
				p.sendTitle(Loader.get("SuccefullyCreatedEnchant",1)
						.replace("%enchant%", Loader.c.getString("Enchants."+n+".Name")),Loader.get("SuccefullyCreatedEnchant",2)
						.replace("%enchant%", Loader.c.getString("Enchants."+n+".Name")));
			}else
				ench.openEditor(p, ench.select.CREATE, n);
				}
				break;
			case MoneyBonus:
				e.setCancelled(true);
				if(n == null) {
					p.sendTitle(Loader.get("MissingEnchantName",1), Loader.get("MissingEnchantName",2));
					if(!Loader.c.getBoolean("Creating-Enchants."+p.getName()+".Warned")) {
					Loader.c.set("Creating-Enchants."+p.getName()+".Warned", true);
					Loader.save();
					String ed = n;
					new Tasker() {
						public void run() {
							ench.openEditor(p, ench.select.CREATE, ed);
						Loader.c.set("Creating-Enchants."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
				Loader.c.set("Enchants."+n+".MoneyBonus",StringUtils.getDouble(message.replace(" ", "")));
				Loader.save();

				if(ex("Enchants."+n+".Cost") && ex("Enchants."+n+".AmountBonus") &&ex("Enchants."+n+".MoneyBonus") && ex("Enchants."+n+".PointsBonus") && ex("Enchants."+n+".ExpBonus")) {
					p.getOpenInventory().close();
				Loader.c.set("Creating-Enchants."+p.getName(), null);
				Loader.save();
				p.sendTitle(Loader.get("SuccefullyCreatedEnchant",1)
						.replace("%enchant%", Loader.c.getString("Enchants."+n+".Name")),Loader.get("SuccefullyCreatedEnchant",2)
						.replace("%enchant%", Loader.c.getString("Enchants."+n+".Name")));
			}else
				ench.openEditor(p, ench.select.CREATE, n);
				}
				break;
			case Cost:
				e.setCancelled(true);
				if(n == null) {
					p.sendTitle(Loader.get("MissingEnchantName",1), Loader.get("MissingEnchantName",2));
					if(!Loader.c.getBoolean("Creating-Enchants."+p.getName()+".Warned")) {
					Loader.c.set("Creating-Enchants."+p.getName()+".Warned", true);
					Loader.save();
					String ed = n;
					new Tasker() {
						public void run() {
							ench.openEditor(p, ench.select.CREATE, ed);
						Loader.c.set("Creating-Enchants."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
				Loader.c.set("Enchants."+n+".Cost", StringUtils.getDouble(message.replace(" ", "")));
				Loader.save();

				if(ex("Enchants."+n+".Cost") && ex("Enchants."+n+".AmountBonus") &&ex("Enchants."+n+".MoneyBonus") && ex("Enchants."+n+".PointsBonus") && ex("Enchants."+n+".ExpBonus")) {
					p.getOpenInventory().close();
				Loader.c.set("Creating-Enchants."+p.getName(), null);
				Loader.save();
				p.sendTitle(Loader.get("SuccefullyCreatedEnchant",1)
						.replace("%enchant%", Loader.c.getString("Enchants."+n+".Name")),Loader.get("SuccefullyCreatedEnchant",2)
						.replace("%enchant%", Loader.c.getString("Enchants."+n+".Name")));
			}else
				ench.openEditor(p, ench.select.CREATE, n);
				}
				break;
			case Description:
				e.setCancelled(true);
				if(n == null) {
					p.sendTitle(Loader.get("MissingEnchantName",1), Loader.get("MissingEnchantName",2));
					if(!Loader.c.getBoolean("Creating-Enchants."+p.getName()+".Warned")) {
					Loader.c.set("Creating-Enchants."+p.getName()+".Warned", true);
					Loader.save();
					String ed = n;
					new Tasker() {
						public void run() {
							ench.openEditor(p, ench.select.CREATE, ed);
						Loader.c.set("Creating-Enchants."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
							List<String> list = Loader.c.getStringList("Enchants."+n+".Description");
							list.add(e.getMessage());
				Loader.c.set("Enchants."+n+".Description", list);
				Loader.save();
				ench.openEditor(p, ench.select.CREATE, n);
				}
				break;
			case Name:
				if(n==null) {
				n=getName("Enchants");
				Loader.c.set("Creating-Enchants."+p.getName()+".Fish", n);
				Loader.c.set("Creating-Enchants."+p.getName()+".Name",message);
				Loader.save();
				}
				Loader.c.set("Enchants."+n+".Name",message);
				Loader.save();
				e.setCancelled(true);
				ench.openEditor(p, ench.select.CREATE, n);
				break;
			case ExpBonus:
				e.setCancelled(true);
				if(n == null) {
					p.sendTitle(Loader.get("MissingEnchantName",1), Loader.get("MissingEnchantName",2));
					if(!Loader.c.getBoolean("Creating-Enchants."+p.getName()+".Warned")) {
					Loader.c.set("Creating-Enchants."+p.getName()+".Warned", true);
					Loader.save();
					String ed = n;
					new Tasker() {
						public void run() {
							ench.openEditor(p, ench.select.CREATE, ed);
						Loader.c.set("Creating-Enchants."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
				Loader.c.set("Enchants."+n+".ExpBonus", StringUtils.getDouble(message.replace(" ", "")));
				Loader.save();
				if(ex("Enchants."+n+".Cost") && ex("Enchants."+n+".AmountBonus") &&ex("Enchants."+n+".MoneyBonus") && ex("Enchants."+n+".PointsBonus") && ex("Enchants."+n+".ExpBonus")) {
					p.getOpenInventory().close();
				Loader.c.set("Creating-Enchants."+p.getName(), null);
				Loader.save();
				p.sendTitle(Loader.get("SuccefullyCreatedEnchant",1)
						.replace("%enchant%", Loader.c.getString("Enchants."+n+".Name")),Loader.get("SuccefullyCreatedEnchant",2)
						.replace("%enchant%", Loader.c.getString("Enchants."+n+".Name")));
			}else
				ench.openEditor(p, ench.select.CREATE, n);
				}
				break;
			case AmountBonus:
				e.setCancelled(true);
				if(n == null) {
					p.sendTitle(Loader.get("MissingEnchantName",1), Loader.get("MissingEnchantName",2));
					if(!Loader.c.getBoolean("Creating-Enchants."+p.getName()+".Warned")) {
					Loader.c.set("Creating-Enchants."+p.getName()+".Warned", true);
					Loader.save();
					String ed = n;
					new Tasker() {
						public void run() {
							ench.openEditor(p, ench.select.CREATE, ed);
						Loader.c.set("Creating-Enchants."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
				Loader.c.set("Enchants."+n+".AmountBonus", StringUtils.getDouble(message.replace(" ", "")));
				Loader.save();
				if(ex("Enchants."+n+".Cost") && ex("Enchants."+n+".AmountBonus") &&ex("Enchants."+n+".MoneyBonus") && ex("Enchants."+n+".PointsBonus") && ex("Enchants."+n+".ExpBonus")) {
					p.getOpenInventory().close();
				Loader.c.set("Creating-Enchants."+p.getName(), null);
				Loader.save();
				p.sendTitle(Loader.get("SuccefullyCreatedEnchant",1)
						.replace("%enchant%", Loader.c.getString("Enchants."+n+".Name")),Loader.get("SuccefullyCreatedEnchant",2)
						.replace("%enchant%", Loader.c.getString("Enchants."+n+".Name")));
			}else
				ench.openEditor(p, ench.select.CREATE, n);
				}
				break;
			}}}
	private void editTreasure(Player p, String message, PlayerChatEvent e, TreasureType w) {
		String path = "";
		switch(w) {
		case LEGEND:
			path="Legendary";
			break;
		case EPIC:
			path="Epic";
			break;
		case RARE:
			path="Rare";
			break;
		case COMMON:
			path="Common";
			break;
		}
		if(Loader.c.exists("Edit-"+path+"."+p.getName())) {
			tre c = tre.valueOf(Loader.c.getString("Edit-"+path+"."+p.getName()+".Type"));
			String fish =Loader.c.getString("Edit-"+path+"."+p.getName()+".Crate");
			
			switch(c) {
			case Points:
				e.setCancelled(true);
				if(fish == null) {
					p.sendTitle(Color.c(Loader.s("Editor.MissingCrateName.1")), Color.c(Loader.s("Editor.MissingCrateName.2")));
					if(!Loader.c.getBoolean("Edit-"+path+"."+p.getName()+".Warned")) {
					Loader.c.set("Edit-"+path+"."+p.getName()+".Warned", true);
					Loader.save();
					String d = path ;
					new Tasker() {
						public void run() {
							TheAPI_GUIs gui = new TheAPI_GUIs();
							gui.openEditor(p, fish, select.EDIT,w);
						Loader.c.set("Edit-"+d+"."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
				Loader.c.set("Treasures."+path+"."+fish+".Points", StringUtils.getDouble(message.replace(" ", "")));
				Loader.save();
				TheAPI_GUIs gui = new TheAPI_GUIs();
				gui.openEditor(p, fish, select.EDIT,w);
				}
				break;
			case Money:
				e.setCancelled(true);
				if(fish == null) {
					p.sendTitle(Color.c(Loader.s("Editor.MissingCrateName.1")), Color.c(Loader.s("Editor.MissingCrateName.2")));
					if(!Loader.c.getBoolean("Edit-"+path+"."+p.getName()+".Warned")) {
					Loader.c.set("Edit-"+path+"."+p.getName()+".Warned", true);
					Loader.save();
					String d = path ;
					new Tasker() {
						public void run() {
							TheAPI_GUIs gui = new TheAPI_GUIs();
							gui.openEditor(p, fish, select.EDIT,w);
						Loader.c.set("Edit-"+d+"."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
				Loader.c.set("Treasures."+path+"."+fish+".Money", StringUtils.getDouble(message.replace(" ", "")));
				Loader.save();
				TheAPI_GUIs gui = new TheAPI_GUIs();
				gui.openEditor(p, fish, select.EDIT,w);
				}
				break;
			case Name:
				e.setCancelled(true);
				Loader.c.set("Treasures."+path+"."+fish+".Name", e.getMessage());
				Loader.save();
				TheAPI_GUIs gui = new TheAPI_GUIs();
				gui.openEditor(p, fish, select.EDIT,w);
				break;
			case Chance:
				e.setCancelled(true);
				if(fish == null) {
					p.sendTitle(Color.c(Loader.s("Editor.MissingCrateName.1")), Color.c(Loader.s("Editor.MissingCrateName.2")));
					if(!Loader.c.getBoolean("Edit-"+path+"."+p.getName()+".Warned")) {
					Loader.c.set("Edit-"+path+"."+p.getName()+".Warned", true);
					Loader.save();
					String d = path ;
					new Tasker() {
						public void run() {
							TheAPI_GUIs gui = new TheAPI_GUIs();
							gui.openEditor(p, fish, select.EDIT,w);
						Loader.c.set("Edit-"+d+"."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
				Loader.c.set("Treasures."+path+"."+fish+".Chance",StringUtils.getDouble(message.replace(" ", "")));
				Loader.save();
				TheAPI_GUIs guis= new TheAPI_GUIs();
				guis.openEditor(p, fish, select.EDIT,w);
				}
				break;
			case Message:
				e.setCancelled(true);
				if(fish == null) {
					p.sendTitle(Color.c(Loader.s("Editor.MissingCrateName.1")), Color.c(Loader.s("Editor.MissingCrateName.2")));
					if(!Loader.c.getBoolean("Edit-"+path+"."+p.getName()+".Warned")) {
					Loader.c.set("Edit-"+path+"."+p.getName()+".Warned", true);
					Loader.save();
					String d = path ;
					new Tasker() {
						public void run() {
							TheAPI_GUIs gui = new TheAPI_GUIs();
							gui.openEditor(p, fish, select.EDIT,w);
						Loader.c.set("Edit-"+d+"."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
							List<String> list = Loader.c.getStringList("Treasures."+path+"."+fish+".Messages");
							list.add(e.getMessage());
				Loader.c.set("Treasures."+path+"."+fish+".Messages", list);
				Loader.save();
				TheAPI_GUIs guis= new TheAPI_GUIs();
				guis.openEditor(p, fish, select.EDIT,w);
				}
				break;
			case Command:
				e.setCancelled(true);
				if(fish == null) {
					p.sendTitle(Color.c(Loader.s("Editor.MissingCrateName.1")), Color.c(Loader.s("Editor.MissingCrateName.2")));
					if(!Loader.c.getBoolean("Edit-"+path+"."+p.getName()+".Warned")) {
					Loader.c.set("Edit-"+path+"."+p.getName()+".Warned", true);
					Loader.save();
					String d = path ;
					new Tasker() {
						public void run() {
							TheAPI_GUIs gui = new TheAPI_GUIs();
							gui.openEditor(p, fish, select.EDIT,w);
						Loader.c.set("Edit-"+d+"."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
							List<String> list = Loader.c.getStringList("Treasures."+path+"."+fish+".Commands");
							list.add(e.getMessage());
				Loader.c.set("Treasures."+path+"."+fish+".Commands", list);
				Loader.save();
				TheAPI_GUIs guis= new TheAPI_GUIs();
				guis.openEditor(p, fish, select.EDIT,w);
				}
				break;
	}}}

	public static String getName(String path) {
		String name = null;
		for(int s = 0; s>-1;++s) {
			if(name!=null)break;
		if(!Loader.c.exists(path+"."+s)) {
			name=s+"";
		}
		}
		return name;
	}
	private void setEverythingTreasure(Player p, String message, PlayerChatEvent e, TreasureType w) {
		String path = "";
		switch(w) {
		case LEGEND:
			path="Legendary";
			break;
		case EPIC:
			path="Epic";
			break;
		case RARE:
			path="Rare";
			break;
		case COMMON:
			path="Common";
			break;
		}
		if(Loader.c.exists("Creating-"+path+"."+p.getName())) {
			tre c = tre.valueOf(Loader.c.getString("Creating-"+path+"."+p.getName()+".Type"));
			String fish =Loader.c.getString("Creating-"+path+"."+p.getName()+".Crate");

			TheAPI_GUIs gui = new TheAPI_GUIs();
			switch(c) {
			case Points:
				e.setCancelled(true);
				if(fish == null) {
					p.sendTitle(Color.c(Loader.s("Editor.MissingCrateName.1")), Color.c(Loader.s("Editor.MissingCrateName.2")));
					if(!Loader.c.getBoolean("Creating-"+path+"."+p.getName()+".Warned")) {
					Loader.c.set("Creating-"+path+"."+p.getName()+".Warned", true);
					Loader.save();
					String d = path ;
					new Tasker() {
						public void run() {
							TheAPI_GUIs gui = new TheAPI_GUIs();
							gui.openEditor(p, null, select.CREATE,w);
						Loader.c.set("Creating-"+d+"."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
				Loader.c.set("Treasures."+path+"."+fish+".Points", StringUtils.getDouble(message.replace(" ", "")));
				Loader.save();
						
				if(ex("Treasures."+path+"."+fish+".Points") && ex("Treasures."+path+"."+fish+".Money") && ex("Treasures."+path+"."+fish+".Chance")) {
					p.getOpenInventory().close();
				Loader.c.set("Creating-"+path+"."+p.getName(), null);
				Loader.save();
				p.sendTitle(Color.c(Loader.s("Editor.SuccefullyCreatedCrate.1")
						.replace("%treasure%", Loader.c.getString("Treasures."+path+"."+fish+".Name"))), 
						Color.c(Loader.s("Editor.SuccefullyCreatedCrate.2")
						.replace("%treasure%", Loader.c.getString("Treasures."+path+"."+fish+".Name"))));
			}else
				gui.openEditor(p, fish, select.CREATE,w);
				}
				break;
			case Money:
				e.setCancelled(true);
				if(fish == null) {
					p.sendTitle(Color.c(Loader.s("Editor.MissingCrateName.1")), Color.c(Loader.s("Editor.MissingCrateName.2")));
					if(!Loader.c.getBoolean("Creating-"+path+"."+p.getName()+".Warned")) {
					Loader.c.set("Creating-"+path+"."+p.getName()+".Warned", true);
					Loader.save();
					String d = path ;
					new Tasker() {
						public void run() {
							gui.openEditor(p, null, select.CREATE,w);
						Loader.c.set("Creating-"+d+"."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
				Loader.c.set("Treasures."+path+"."+fish+".Money", StringUtils.getDouble(message.replace(" ", "")));
				Loader.save();

				if(ex("Treasures."+path+"."+fish+".Points") && ex("Treasures."+path+"."+fish+".Money") && ex("Treasures."+path+"."+fish+".Chance")) {
					p.getOpenInventory().close();
				Loader.c.set("Creating-"+path+"."+p.getName(), null);
				Loader.save();
				p.sendTitle(Color.c(Loader.s("Editor.SuccefullyCreatedCrate.1")
						.replace("%treasure%", Loader.c.getString("Treasures."+path+"."+fish+".Name"))), 
						Color.c(Loader.s("Editor.SuccefullyCreatedCrate.2")
						.replace("%treasure%", Loader.c.getString("Treasures."+path+"."+fish+".Name"))));
			}else
				gui.openEditor(p, fish, select.CREATE,w);
				}
				break;
			case Name:
				e.setCancelled(true);
				if(fish==null) {
				fish=getName("Treasures."+path);
				Loader.c.set("Creating-"+path+"."+p.getName()+".Crate", fish);
				}
				Loader.c.set("Treasures."+path+"."+fish+".Name", message);
				Loader.save();
				gui.openEditor(p, fish, select.CREATE,w);
				break;
			case Chance:
				e.setCancelled(true);
				if(fish == null) {
					p.sendTitle(Color.c(Loader.s("Editor.MissingCrateName.1")), Color.c(Loader.s("Editor.MissingCrateName.2")));
					if(!Loader.c.getBoolean("Creating-"+path+"."+p.getName()+".Warned")) {
					Loader.c.set("Creating-"+path+"."+p.getName()+".Warned", true);
					Loader.save();
					String d = path ;
					new Tasker() {
						public void run() {
							gui.openEditor(p, null, select.CREATE,w);
						Loader.c.set("Creating-"+d+"."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
				Loader.c.set("Treasures."+path+"."+fish+".Chance", StringUtils.getDouble(message.replace(" ", "")));
				Loader.save();

				if(ex("Treasures."+path+"."+fish+".Points") && ex("Treasures."+path+"."+fish+".Money") && ex("Treasures."+path+"."+fish+".Chance")) {
					p.getOpenInventory().close();
				Loader.c.set("Creating-"+path+"."+p.getName(), null);
				Loader.save();
				p.sendTitle(Color.c(Loader.s("Editor.SuccefullyCreatedCrate.1")
						.replace("%treasure%", Loader.c.getString("Treasures."+path+"."+fish+".Name"))), 
						Color.c(Loader.s("Editor.SuccefullyCreatedCrate.2")
						.replace("%treasure%", Loader.c.getString("Treasures."+path+"."+fish+".Name"))));
			}else
				gui.openEditor(p, fish, select.CREATE,w);
				}
				break;
			case Message:
				e.setCancelled(true);
				if(fish == null) {
					p.sendTitle(Color.c(Loader.s("Editor.MissingCrateName.1")), Color.c(Loader.s("Editor.MissingCrateName.2")));
					if(!Loader.c.getBoolean("Creating-"+path+"."+p.getName()+".Warned")) {
					Loader.c.set("Creating-"+path+"."+p.getName()+".Warned", true);
					Loader.save();
					String d = path ;
					new Tasker() {
						public void run() {
							gui.openEditor(p, null, select.CREATE,w);
						Loader.c.set("Creating-"+d+"."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
							List<String> list = Loader.c.getStringList("Treasures."+path+"."+fish+".Messages");
							list.add(message);
				Loader.c.set("Treasures."+path+"."+fish+".Messages", list);
				Loader.save();

				if(ex("Treasures."+path+"."+fish+".Points") && ex("Treasures."+path+"."+fish+".Money") && ex("Treasures."+path+"."+fish+".Chance")) {
					p.getOpenInventory().close();
				Loader.c.set("Creating-"+path+"."+p.getName(), null);
				Loader.save();
				p.sendTitle(Color.c(Loader.s("Editor.SuccefullyCreatedCrate.1")
						.replace("%treasure%", Loader.c.getString("Treasures."+path+"."+fish+".Name"))), 
						Color.c(Loader.s("Editor.SuccefullyCreatedCrate.2")
						.replace("%treasure%", Loader.c.getString("Treasures."+path+"."+fish+".Name"))));
			}else
				gui.openEditor(p, fish, select.CREATE,w);
				}
				break;
			case Command:
				e.setCancelled(true);
				if(fish == null) {
					p.sendTitle(Color.c(Loader.s("Editor.MissingCrateName.1")), Color.c(Loader.s("Editor.MissingCrateName.2")));
					if(!Loader.c.getBoolean("Creating-"+path+"."+p.getName()+".Warned")) {
					Loader.c.set("Creating-"+path+"."+p.getName()+".Warned", true);
					Loader.save();
					String d = path ;
					new Tasker() {
						public void run() {
							gui.openEditor(p, null, select.CREATE,w);
						Loader.c.set("Creating-"+d+"."+p.getName()+".Warned", false);
						Loader.save();
						}}.runLater(40);}}else {
							List<String> list = Loader.c.getStringList("Treasures."+path+"."+fish+".Commands");
							list.add(message);
				Loader.c.set("Treasures."+path+"."+fish+".Commands", list);
				Loader.save();

				if(ex("Treasures."+path+"."+fish+".Points") && ex("Treasures."+path+"."+fish+".Money") && ex("Treasures."+path+"."+fish+".Chance")) {
					p.getOpenInventory().close();
				Loader.c.set("Creating-"+path+"."+p.getName(), null);
				Loader.save();
				p.sendTitle(Color.c(Loader.s("Editor.SuccefullyCreatedCrate.1")
						.replace("%treasure%", Loader.c.getString("Treasures."+path+"."+fish+".Name"))), 
						Color.c(Loader.s("Editor.SuccefullyCreatedCrate.2")
						.replace("%treasure%", Loader.c.getString("Treasures."+path+"."+fish+".Name"))));
			}else
				gui.openEditor(p, fish, select.CREATE,w);
				}
				break;
	}}}
	static boolean ex(String path) {
		return Loader.c.exists(path);
	}
	public enum create {
		Money,
		Exp,
		Cm,
		Points,
		Name,
		Chance
	}
	public static enum enchs {
		MoneyBonus,
		PointsBonus,
		ExpBonus,
		AmountBonus,
		Cost,
		Description,
		Name;
	}
	private enum tre {
		Money,
		Points,
		Name,
		Chance,
		Message,
		Command;
	}*/
}

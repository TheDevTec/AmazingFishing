package AmazingFishing.APIs;

import org.bukkit.entity.Player;

import AmazingFishing.APIs.Enums.EditType;
import AmazingFishing.APIs.Enums.FishType;
import AmazingFishing.APIs.Enums.TreasureType;
import me.DevTec.AmazingFishing.Loader;

public class EditorManager {

	public static  EditorManager e = new EditorManager();
	
	/*
	 *   Fish Edit/Create
	 */
	public boolean isCreatingFish(Player p) {
		if(Loader.c.exists("Creating-Pufferfish."+p.getName())) return true;
		if(Loader.c.exists("Creating-Cod."+p.getName())) return true;
		if(Loader.c.exists("Creating-Salmon."+p.getName())) return true;
		if(Loader.c.exists("Creating-Tropical_Fish."+p.getName())) return true;
		return false;
		
	}
	public boolean isEditingFish(Player p) {
		if(Loader.c.exists("Edit-Pufferfish."+p.getName())) return true;
		if(Loader.c.exists("Edit-Cod."+p.getName())) return true;
		if(Loader.c.exists("Edit-Salmon."+p.getName())) return true;
		if(Loader.c.exists("Edit-Tropical_Fish."+p.getName())) return true;
		return false;
	}
	public FishType getFishTypeByEdit(Player p, EditType EditType) {
		switch(EditType) {
		case Fish_Create:
			if(Loader.c.exists("Creating-Pufferfish."+p.getName()))return FishType.PUFFERFISH;
			if(Loader.c.exists("Creating-Cod."+p.getName()))return FishType.COD;
			if(Loader.c.exists("Creating-Salmon."+p.getName()))return FishType.SALMON;
			if(Loader.c.exists("Creating-Tropical_Fish."+p.getName()))return FishType.TROPICAL_FISH;
		break;
		case Fish_Edit:
			if(Loader.c.exists("Edit-Pufferfish."+p.getName()))return FishType.PUFFERFISH;
			if(Loader.c.exists("Edit-Cod."+p.getName()))return FishType.COD;
			if(Loader.c.exists("Edit-Salmon."+p.getName()))return FishType.SALMON;
			if(Loader.c.exists("Edit-Tropical_Fish."+p.getName()))return FishType.TROPICAL_FISH;
			break;
		default:
			break;
		}
		return null;
	}
	/*
	 *   Treasure Edit/Create
	 */
	public boolean isCreatingTreasure(Player p) {
		if(Loader.c.exists("Creating-Legendary."+p.getName()))return true;
		if(Loader.c.exists("Creating-Epic."+p.getName()))return true;
		if(Loader.c.exists("Creating-Rare."+p.getName()))return true;
		if(Loader.c.exists("Creating-Common."+p.getName()))return true;
		return false;
	}
	public boolean isEditingTreasure(Player p) {
		if(Loader.c.exists("Edit-Legendary."+p.getName()))return true;
		if(Loader.c.exists("Edit-Epic."+p.getName()))return true;
		if(Loader.c.exists("Edit-Rare."+p.getName()))return true;
		if(Loader.c.exists("Edit-Common."+p.getName()))return true;
		return false;
	}
	public TreasureType getTreasureTypeByEdit(Player p, EditType EditType) {
		switch(EditType) {
		case Treasure_Create:
			if(Loader.c.exists("Creating-Legendary."+p.getName()))return TreasureType.LEGEND;
			if(Loader.c.exists("Creating-Epic."+p.getName()))return TreasureType.EPIC;
			if(Loader.c.exists("Creating-Rare."+p.getName()))return TreasureType.RARE;
			if(Loader.c.exists("Creating-Common."+p.getName()))return TreasureType.COMMON;
			break;
		case Treasure_Edit:
			if(Loader.c.exists("Edit-Legendary."+p.getName()))return TreasureType.LEGEND;
			if(Loader.c.exists("Edit-Epic."+p.getName()))return TreasureType.EPIC;
			if(Loader.c.exists("Edit-Rare."+p.getName()))return TreasureType.RARE;
			if(Loader.c.exists("Edit-Common."+p.getName()))return TreasureType.COMMON;
			break;
		default:
			break;
		}
		return null;
	}
	
	public String getStringFromFishType(FishType type, boolean path) {
		switch(type) {
		case COD:
			if(path) return "Cod";
			else return "Cod";
		case SALMON:
			if(path) return "Salmon";
			else return "Salmon";
		case PUFFERFISH:
			if(path) return "Pufferfish";
			else return "PufferFish";
		case TROPICAL_FISH:
			if(path)return "Tropical_Fish";
			else return "TropicalFish";
		}
		return null;
	}
}

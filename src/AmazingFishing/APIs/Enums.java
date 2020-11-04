package AmazingFishing.APIs;

public class Enums {

	public static enum EditType {
		Fish_Create,
		Fish_Edit,
		Enchants_Create,
		Enchants_Edit,
		Treasure_Edit,
		Treasure_Create;
	}
	public static enum FishType {
		PUFFERFISH,
		TROPICAL_FISH,
		COD,
		SALMON
	}
	public enum TreasureType{
		COMMON,
		RARE,
		EPIC,
		LEGEND;
	}
	public static enum PlayerType{
		Player,
		Admin
	}
}

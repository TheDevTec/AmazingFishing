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
	public static enum TreasureType{
		COMMON,
		RARE,
		EPIC,
		LEGEND;
	}
	public static enum PlayerType{
		Player,
		Admin
	}
	public static enum EnchantEditorSelect{
		CREATE,
		DELETE,
		EDIT;
	}
	public static enum select{ // Select Edit in-game editor (Fish and Treasures)
		CREATE,
		DELETE,
		EDIT;
	}
	public enum FishCreate {
		Money,
		Exp,
		Cm,
		Points,
		Name,
		Chance
	}
	public static enum EnchsCreate {
		MoneyBonus,
		PointsBonus,
		ExpBonus,
		AmountBonus,
		Cost,
		Description,
		Name;
	}

	public static enum BackButton{
		Close,
		FishPlayer,
		FishAdmin,
		Shop;
	}
}

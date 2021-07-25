package captainsly.paper.mechanics.items;

public class Equipment extends Item {

	public enum EquipmentType {
		HEAD, NECK, CHEST, HANDS, WEAPON, SHEILD, LEGS, FEET
	}

	private EquipmentType equipmentType;
	private Rarity equipmentRarity;

	private int[] equipmentStats;

	public Equipment(String itemId, String itemName, String itemDesc, EquipmentType equipmentType) {
		super(itemId, itemName, itemDesc, ItemType.EQUIPMENT);
		equipmentStats = new int[EquipmentStat.values().length];
		equipmentRarity = Rarity.COMMON;
	}

	public EquipmentType getEquipmentType() {
		return equipmentType;
	}

	public Rarity getEquipmentRarity() {
		return equipmentRarity;
	}

	public int[] getEquipmentStats() {
		return equipmentStats;
	}

	public void setStat(EquipmentStat stat, int amount) {
		equipmentStats[stat.ordinal()] = amount;
	}

	public int getStat(EquipmentStat stat) {
		return equipmentStats[stat.ordinal()];
	}

}

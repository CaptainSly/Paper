package captainsly.paper.mechanics.items.equipment.weapons;

import captainsly.paper.mechanics.items.equipment.Equipment;

public class Weapon extends Equipment {

	public enum WeaponType {
		SWORD, AXE, LANCE
	}
	
	private WeaponType weaponType;
	
	public Weapon(String itemId, String itemName, String itemDesc, WeaponType weaponType) {
		super(itemId, itemName, itemDesc, EquipmentType.WEAPON);
	}

	public WeaponType getWeaponType() {
		return weaponType;
	}
	
}

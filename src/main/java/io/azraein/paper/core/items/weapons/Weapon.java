package io.azraein.paper.core.items.weapons;

import io.azraein.paper.core.items.Item;

public class Weapon extends Item {

	private DamageType weaponDamageType;
	private WeaponType weaponType;

	private String weaponDamageRoll;

	public Weapon(String itemId, String itemName, String itemDescription, DamageType weaponDamageType,
			WeaponType weaponType, String weaponDamageRoll) {
		super(itemId, itemName, itemDescription);
		this.weaponDamageType = weaponDamageType;
		this.weaponType = weaponType;
		this.weaponDamageRoll = weaponDamageRoll;
	}

	public String getWeaponDamageRoll() {
		return weaponDamageRoll;
	}

	public WeaponType getWeaponType() {
		return weaponType;
	}

	public DamageType getWeaponDamageType() {
		return weaponDamageType;
	}

}

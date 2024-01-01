package io.azraein.paper.core.items.equipment;

import io.azraein.paper.core.entities.stats.Skills;
import io.azraein.paper.core.items.Item;

public class Equipment extends Item {

	private EquipType equipmentEquipType;

	private Skills equipmentSkillType;
	private int equipmentSkillBonus;

	private int equipmentDefenceBonus;

	public Equipment(String itemId, String itemName, String itemDescription, EquipType equipmentEquipType,
			int equipmentDefenceBonus) {
		super(itemId, itemName, itemDescription);
		this.equipmentEquipType = equipmentEquipType;
		this.equipmentDefenceBonus = equipmentDefenceBonus;
		equipmentSkillBonus = 0;

	}

	public EquipType getEquipmentEquipType() {
		return equipmentEquipType;
	}

	public Skills getEquipmentSkillType() {
		return equipmentSkillType;
	}

	public int getEquipmentDefenceBonus() {
		return equipmentDefenceBonus;
	}

	public int getEquipmentSkillBonus() {
		return equipmentSkillBonus;
	}

	public void setEquipmentSkillType(Skills equipmentSkillType) {
		this.equipmentSkillType = equipmentSkillType;
	}

	public void setEquipmentSkillBonus(int equipmentSkillBonus) {
		this.equipmentSkillBonus = equipmentSkillBonus;
	}

}

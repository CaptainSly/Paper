package io.azraein.paper.core.items;

import io.azraein.paper.core.entities.Entity;

public class PotionItem extends ConsumableItem {

	public enum PotionType {
		HEALTH, MANA
	}

	private PotionType potionType;
	private int potionHealAmount;

	public PotionItem(String itemId, String itemName, String itemDescription, PotionType potionType,
			int potionHealAmount) {
		super(itemId, itemName, itemDescription);
		this.potionType = potionType;
		this.potionHealAmount = potionHealAmount;
	}

	@Override
	public void onConsume(Entity entity) {
		switch (potionType) {
		case HEALTH:
			entity.healEntity(potionHealAmount);
		case MANA:
			entity.addEntityMana(potionHealAmount);
		}
	}

	public PotionType getPotionType() {
		return potionType;
	}

	public int getPotionHealAmount() {
		return potionHealAmount;
	}

}

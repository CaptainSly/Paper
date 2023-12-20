package io.azraein.paper.core.items;

import io.azraein.paper.core.entities.Entity;

public abstract class ConsumableItem extends Item {

	public ConsumableItem(String itemId, String itemName, String itemDescription) {
		super(itemId, itemName, itemDescription);
	}

	public abstract void onConsume(Entity entity);

}

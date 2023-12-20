package io.azraein.paper.core.items.container;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ItemSlot {

	private final StringProperty slotItemId = new SimpleStringProperty("");
	private final IntegerProperty slotCount = new SimpleIntegerProperty(0);

	public ItemSlot(String slotItemId, int slotCount) {
		this.slotItemId.set(slotItemId);
		this.slotCount.set(slotCount);
	}

	public void setSlotItemId(String slotItemId) {
		this.slotItemId.set(slotItemId);
	}

	public void setSlotItemCount(int slotCount) {
		this.slotCount.set(slotCount);
	}

	public String getSlotItemId() {
		return slotItemId.get();
	}

	public int getSlotCount() {
		return slotCount.get();
	}

	public boolean isEmpty() {
		return getSlotItemId().isBlank();
	}

	public StringProperty slotItemIdProperty() {
		return slotItemId;
	}

	public IntegerProperty slotItemCountProperty() {
		return slotCount;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemSlot) {
			ItemSlot slot = (ItemSlot) obj;

			if (this.getSlotItemId().equals(slot.getSlotItemId()))
				return true;

		}

		return false;
	}

}

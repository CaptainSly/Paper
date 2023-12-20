package io.azraein.paper.core.items.container;

import org.tinylog.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

	private final ObservableList<ItemSlot> itemSlots;

	public Inventory() {
		itemSlots = FXCollections.observableArrayList();
	}

	public synchronized ItemSlot getFreeSlot() {
		ItemSlot freeSlot = null;

		for (ItemSlot slot : itemSlots)
			if (slot.isEmpty())
				freeSlot = slot;

		// If we couldn't find a free slot in the inventory, create one, add it and
		// rerun.
		if (freeSlot == null) {
			itemSlots.add(new ItemSlot("", 0));
			return getFreeSlot();
		}

		return freeSlot;
	}

	public synchronized ItemSlot getSlotByItem(String itemId) {
		ItemSlot itemSlot = null;

		Logger.debug("Looking for Item: " + itemId);

		for (int i = 0; i < itemSlots.size(); i++) {
			ItemSlot s = itemSlots.get(i);
			if (!s.isEmpty()) {
				Logger.debug("Found slot with name: " + s.getSlotItemId());
				if (s.getSlotItemId().equals(itemId)) {
					Logger.debug("returning slot: " + itemId);
					return s;
				}
			}
		}

		Logger.debug("Returning null, couldn't find slot");
		return itemSlot == null ? null : itemSlot;
	}

	public synchronized void addItem(String itemId, int amount) {
		ItemSlot itemSlot = getSlotByItem(itemId);

		if (itemSlot == null) {
			itemSlot = getFreeSlot();
			itemSlots.remove(itemSlot);
		}

		var itemCount = itemSlot.getSlotCount() + amount;
		Logger.debug("adding : " + amount + " to itemCount: " + itemSlot.getSlotCount());
		itemSlot.setSlotItemId(itemId);
		itemSlot.setSlotItemCount(itemCount);
		itemSlots.add(itemSlot);
	}

	public synchronized void removeItem(String itemId, int amount) {
		ItemSlot itemSlot = getSlotByItem(itemId);

		// Nothing to do here if the itemSlot is null.
		if (itemSlot == null)
			return;

		Logger.debug("Removing: " + amount + " from total: " + itemSlot.getSlotCount());
		if (amount > itemSlot.getSlotCount())
			amount = itemSlot.getSlotCount();

		int removalAmount = itemSlot.getSlotCount() - amount;
		Logger.debug("Slot New Count: " + removalAmount);
		itemSlot.setSlotItemCount(removalAmount);

		if (itemSlot.getSlotCount() == 0)
			itemSlots.remove(itemSlot);
	}

	public ObservableList<ItemSlot> getItemSlots() {
		return itemSlots;
	}

}

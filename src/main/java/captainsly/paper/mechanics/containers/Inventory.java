package captainsly.paper.mechanics.containers;

import java.util.Collections;

import captainsly.paper.mechanics.items.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

	private ObservableList<ItemSlot> slots;

	public Inventory() {
		slots = FXCollections.observableArrayList();
	}

	public void add(Item item) {
		add(item, 1);
	}

	public void add(Item item, int amount) {
		if (!inventoryContainsItem(item))
			getFreeItemSlot().add(item, amount);
		else
			getSlotFromItem(item).add(amount);
	}

	public void remove(Item item, int amount) {
		if (!inventoryContainsItem(item))
			return;

		getSlotFromItem(item).remove(amount);
	}

	public boolean inventoryContainsItem(Item item) {
		Collections.sort(slots);
		for (ItemSlot slot : slots)
			if (slot.getItem() != null) {
				if (slot.getItem().getItemId().contentEquals(item.getItemId()))
					return true;
			} else
				return false;

		return false;
	}

	public ItemSlot getSlotFromItem(Item item) {
		Collections.sort(slots);
		for (ItemSlot slot : slots)
			if (slot.getItem().getItemId().contentEquals(item.getItemId()))
				return slot;

		return null;
	}

	public ItemSlot getFreeItemSlot() {
		Collections.sort(slots);
		if (slots.size() <= 0) {
			slots.add(new ItemSlot());
			return getFreeItemSlot();
		}

		for (ItemSlot slot : slots) {
			if (slot.isEmpty())
				return slot;
		}

		slots.add(new ItemSlot());
		return getFreeItemSlot();
	}

	public ObservableList<ItemSlot> getItemSlots() {
		return slots;
	}

}

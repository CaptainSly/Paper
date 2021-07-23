package captainsly.paper.mechanics.containers;

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
		if (!slotContainsItem(item))
			getFreeItemSlot().add(item, amount);
		else
			getSlotFromItem(item).add(amount);
	}

	public void remove(Item item, int amount) {
		if (!slotContainsItem(item))
			return;

		getSlotFromItem(item).add(-amount);
	}

	public boolean slotContainsItem(Item item) {
		for (ItemSlot slot : slots)
			if (slot.getItem() != null) {
				if (slot.getItem().getItemId().equals(item.getItemId()))
					return true;
			} else
				return false;

		return false;
	}

	public ItemSlot getSlotFromItem(Item item) {
		for (ItemSlot slot : slots)
			if (slot.getItem().getItemId().equals(item.getItemId()))
				return slot;

		return null;
	}

	public ItemSlot getFreeItemSlot() {
		if (slots.size() <= 0) {
			slots.add(new ItemSlot());
			return slots.get(0);
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

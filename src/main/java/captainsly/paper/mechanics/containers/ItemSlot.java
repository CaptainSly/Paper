package captainsly.paper.mechanics.containers;

import captainsly.paper.mechanics.items.Item;

public class ItemSlot {

	private Item item;
	private int itemCount;
	private boolean empty;

	public ItemSlot() {
		this.item = null;
		this.itemCount = 0;
		this.empty = true;
	}

	public void add(Item item) {
		this.item = item;
		this.itemCount = 1;
		this.empty = false;
	}

	public void add(Item item, int amount) {
		this.item = item;
		add(amount);
	}

	public void add(int amount) {
		this.itemCount += amount;
		if (empty) empty = false;
	}
	
	public void remove(int amount) {
		this.itemCount -= amount;
		if (itemCount <= 0) {
			itemCount = 0;
			empty = true;
			item = null;
		}
	}
	
	public Item getItem() {
		return item;
	}

	public int getItemCount() {
		return itemCount;
	}

	public boolean isEmpty() {
		return empty;
	}

}

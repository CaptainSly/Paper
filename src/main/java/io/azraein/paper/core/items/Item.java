package io.azraein.paper.core.items;

public class Item {

	private final String itemId;
	private String itemName;
	private String itemDescription;

	private int sellPrice;

	public Item(String itemId, String itemName, String itemDescription) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemDescription = itemDescription;

		sellPrice = 10;
	}

	public String getItemId() {
		return itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public int getSellPrice() {
		return sellPrice;
	}

}

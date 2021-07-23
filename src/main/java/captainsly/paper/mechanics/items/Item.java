package captainsly.paper.mechanics.items;

public class Item {

	public enum ItemType {
		CONSUMABLE, ACCESSORY, KEY, COMMON, WEAPON, ARMOR
	}

	private String itemId, itemName, itemDesc;
	private int itemCost;
	private ItemType itemType;

	public Item(String itemId, String itemName, String itemDesc, ItemType itemType) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemDesc = itemDesc;
		this.itemType = itemType;
	}

	public void onUse() {
		System.out.println("I am " + itemId);
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public int getItemCost() {
		return itemCost;
	}

	public void setItemCost(int itemCost) {
		this.itemCost = itemCost;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public ItemType getItemType() {
		return itemType;
	}
	
	public String getItemId() {
		return itemId;
	}

	public String getItemName() {
		return itemName;
	}

}

package captainsly.paper.mechanics;

public class Item {

	private String itemId, itemName, itemDesc;
	private int itemCost;

	public Item(String itemId, String itemName, String itemDesc) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemDesc = itemDesc;
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

	public String getItemId() {
		return itemId;
	}

	public String getItemName() {
		return itemName;
	}

}

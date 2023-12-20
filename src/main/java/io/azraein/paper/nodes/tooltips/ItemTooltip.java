package io.azraein.paper.nodes.tooltips;

import io.azraein.paper.core.items.Item;
import io.azraein.paper.core.system.Registry;
import javafx.scene.control.Tooltip;

public class ItemTooltip extends Tooltip {

	private Item tooltipItem;
	
	public ItemTooltip(String itemId) {
		this.tooltipItem = Registry.getItem(itemId);
		
		// TODO: Update when Items get defined more
		
		String itemTooltip = "~=~=~ " + tooltipItem.getItemName() + " ~=~=~\n"
				+ tooltipItem.getItemDescription();
		
		this.setText(itemTooltip);
	}
	
	public Item getTooltipItem() {
		return tooltipItem;
	}
	
}

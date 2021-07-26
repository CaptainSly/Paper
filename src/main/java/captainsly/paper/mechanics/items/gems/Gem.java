package captainsly.paper.mechanics.items.gems;

import captainsly.paper.mechanics.items.Item;
import captainsly.paper.mechanics.magic.Magic;

public class Gem extends Item {

	private Magic gemMagicType;
	
	public Gem(String itemId, String itemName, String itemDesc, Magic gemMagicType) {
		super(itemId, itemName, itemDesc, ItemType.GEM);
		this.gemMagicType = gemMagicType;
	}
	
	public Magic getGemMagicType() {
		return gemMagicType;
	}

}

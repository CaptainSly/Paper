package captainsly.paper.mechanics.items.equipment;

import captainsly.paper.mechanics.items.gems.Gem;

public interface ISlottable {

	Gem getSlottedGem();
	void onSlotInsert(Gem slottedGem);
	
}

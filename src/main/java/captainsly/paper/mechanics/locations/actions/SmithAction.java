package captainsly.paper.mechanics.locations.actions;

import captainsly.paper.mechanics.crafting.CraftingStation;
import captainsly.paper.mechanics.nodes.regions.WorldRegion;

public class SmithAction extends Action {

	private CraftingStation smithCraftingStation;
	
	public SmithAction() {
		super("actionSmith", "Smith");
	}

	@Override
	public void onAction(WorldRegion worldNode) {
	}

}

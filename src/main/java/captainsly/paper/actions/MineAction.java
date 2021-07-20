package captainsly.paper.actions;

import captainsly.paper.mechanics.Item;
import captainsly.paper.mechanics.Registry;
import captainsly.paper.nodes.WorldNode;

public class MineAction extends Action {

	public MineAction() {
		super("Mine");
	}

	@Override
	public void onAction(WorldNode worldNode) {
		//TODO: Check to see if the player has a pick equipped
		Item item = Registry.itemRegistry.get("oreIron");
		worldNode.getPlayer().getActorInventory().add(item);
	}

}

package captainsly.paper.location;

import captainsly.paper.location.actions.Action;
import captainsly.paper.mechanics.Registry;
import captainsly.paper.mechanics.items.Item;
import captainsly.paper.nodes.WorldNode;

public class MineLocationAction extends LocationAction {
	
	private Item oreType;

	public MineLocationAction(Location parentLocation) {
		super(parentLocation, "Mine", "Default Test Mine");
		oreType = Registry.itemRegistry.get("oreCopper");
		
		Action mineAction = new Action("mineAction") {

			@Override
			public void onAction(WorldNode worldNode) {
				worldNode.getPlayer().getActorInventory().add(oreType);
				worldNode.getPlayerStatNode().getPlayerInventoryList().refresh();
			}
			
		};
		
		this.setAction(mineAction);		
	}
	
	

}

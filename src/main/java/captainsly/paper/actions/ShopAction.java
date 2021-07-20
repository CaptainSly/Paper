package captainsly.paper.actions;

import captainsly.paper.entities.Merchant;
import captainsly.paper.nodes.MerchantDialog;
import captainsly.paper.nodes.WorldNode;

public class ShopAction extends Action {

	private Merchant merchant = new Merchant("testNpc", "TEST NPC");
	
	public ShopAction() { 
		super("Shop");
	}
	
	@Override
	public void onAction(WorldNode worldNode) {
		MerchantDialog mv = new MerchantDialog(worldNode.getPlayer(), merchant, worldNode.getCurrentLocation().getLocationName());
		mv.showAndWait();
	}

}

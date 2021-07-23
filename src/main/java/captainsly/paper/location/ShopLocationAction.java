package captainsly.paper.location;

import captainsly.paper.actions.Action;
import captainsly.paper.entities.Merchant;
import captainsly.paper.nodes.MerchantDialog;
import captainsly.paper.nodes.WorldNode;

public class ShopLocationAction extends LocationAction {

	private Merchant merchant = new Merchant("merchantTest", "Merchant McTest");

	public ShopLocationAction(Location parentLocation) {
		super(parentLocation, "Shop", "DEfault SHop");

		Action shopAction = new Action("shopAction") {

			@Override
			public void onAction(WorldNode worldNode) {
				MerchantDialog dialog = new MerchantDialog(worldNode.getPlayer(), merchant, parentLocation.getLocationName());
				dialog.showAndWait();
				worldNode.getPlayerStatNode().getPlayerInventoryList().refresh();
			}
		};
		
		this.setAction(shopAction);
	}

}

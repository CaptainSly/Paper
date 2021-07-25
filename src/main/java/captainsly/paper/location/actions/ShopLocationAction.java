package captainsly.paper.location.actions;

import captainsly.paper.entities.Merchant;
import captainsly.paper.location.Location;
import captainsly.paper.nodes.dialogs.MerchantDialog;
import captainsly.paper.nodes.regions.WorldRegion;

public class ShopLocationAction extends LocationAction {

	private Merchant merchant = new Merchant("merchantTest", "Merchant McTest");

	public ShopLocationAction(Location parentLocation) {
		super(parentLocation, "Shop", "DEfault SHop");

		Action shopAction = new Action("shopAction") {	

			@Override
			public void onAction(WorldRegion worldNode) {
				MerchantDialog dialog = new MerchantDialog(worldNode.getPlayer(), merchant, parentLocation.getLocationName());
				dialog.showAndWait();
				worldNode.refresh();
			}
		};
		
		this.setAction(shopAction);
	}

}

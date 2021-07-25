package captainsly.paper.mechanics.locations.actions;

import captainsly.paper.entities.Merchant;
import captainsly.paper.nodes.dialogs.MerchantDialog;
import captainsly.paper.nodes.regions.WorldRegion;

public class ShopAction extends Action {

	private Merchant merchant;

	public ShopAction() {
		super("actionShop", "Shop");
		merchant = new Merchant("ERROR_NULL", "ERROR_NULL");
	}

	@Override
	public void onAction(WorldRegion worldNode) {
		MerchantDialog dialog = new MerchantDialog(worldNode.getPlayer(), merchant,
				worldNode.getCurrentLocation().getLocationName());
		dialog.showAndWait();
		worldNode.refresh();
	}

}

package captainsly.paper.mechanics.locations.actions;

import captainsly.paper.entities.Merchant;
import captainsly.paper.mechanics.nodes.alerts.MerchantAlert;
import captainsly.paper.mechanics.nodes.regions.WorldRegion;

public class ShopAction extends Action {

	private Merchant merchant;

	public ShopAction() {
		super("actionShop", "Shop");
		merchant = new Merchant("ERROR_NULL", "ERROR_NULL");
	}

	@Override
	public void onAction(WorldRegion worldRegion) {
		MerchantAlert dialog = new MerchantAlert(worldRegion.getPlayer(), merchant,
				worldRegion.getCurrentLocation().getLocationName());
		dialog.showAndWait();
		worldRegion.refresh();
	}

}

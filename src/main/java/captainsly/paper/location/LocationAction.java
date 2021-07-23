package captainsly.paper.location;

import captainsly.paper.actions.Action;

public abstract class LocationAction {
	private String locationActionName, locationActionDescription;
	private Location parentLocation;
	private Action locationAction;

	public LocationAction(Location parentLocation, String locationActionName, String locationActionDescription) {
		this.parentLocation = parentLocation;
		this.locationActionName = locationActionName;
		this.locationActionDescription = locationActionDescription;
	}

	public void setAction(Action locationAction) {
		this.locationAction = locationAction;
	}

	public Action getAction() {
		return locationAction;
	}

	public String getLocationActionName() {
		return locationActionName;
	}

	public String getLocationActionDescription() {
		return locationActionDescription;
	}

	public Location getParentLocation() {
		return parentLocation;
	}

}

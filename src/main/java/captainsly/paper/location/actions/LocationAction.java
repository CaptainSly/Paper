package captainsly.paper.location.actions;

import captainsly.paper.location.Location;

public abstract class LocationAction {
	
	public enum LocationStatus {
		MINING, NONE
	}
	
	protected String locationActionName, locationActionDescription;
	public int locationTimer;
	protected LocationStatus locationActionStatus;
	protected Location parentLocation;
	protected Action locationAction;
	
	protected boolean inAction;

	public LocationAction(Location parentLocation, String locationActionName, String locationActionDescription) {
		this.parentLocation = parentLocation;
		this.locationActionName = locationActionName;
		this.locationActionDescription = locationActionDescription;
		this.locationActionStatus = LocationStatus.NONE;
		inAction = false;
	}

	public void setAction(Action locationAction) {
		this.locationAction = locationAction;
	}

	public void setLocationActionStatus(LocationStatus locationActionStatus) {
		this.locationActionStatus = locationActionStatus;
	}
	
	public LocationStatus getLocationActionStatus() {
		return locationActionStatus;
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
	
	public boolean isInAction() {
		return inAction;
	}

}

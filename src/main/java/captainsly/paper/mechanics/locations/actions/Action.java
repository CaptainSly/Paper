package captainsly.paper.mechanics.locations.actions;

import captainsly.paper.nodes.regions.WorldRegion;

public abstract class Action {

	protected String actionId, actionName, actionDescription;
	protected boolean inAction = false;
	protected int actionCounter;

	public Action(String actionId, String actionName) {
		this.actionId = actionId;
		this.actionName = actionName;
	}

	public abstract void onAction(WorldRegion worldNode);

	public String getActionName() {
		return actionName;
	}

	public String getActionId() {
		return actionId;
	}
	
	public String getActionDescription() {
		return actionDescription;
	}

	public int getActionCounter() {
		return actionCounter;
	}

	public boolean isInAction() {
		return inAction;
	}

}

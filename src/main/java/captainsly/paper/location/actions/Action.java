package captainsly.paper.location.actions;

import captainsly.paper.nodes.regions.WorldRegion;

public abstract class Action {

	private String actionName;
	private String actionDesc;

	public Action(String actionName) {
		this.actionName = actionName;
	}

	public abstract void onAction(WorldRegion worldNode);

	public String getActionName() {
		return actionName;
	}

	public String getActionDesc() {
		return actionDesc;
	}

}

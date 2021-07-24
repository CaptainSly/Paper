package captainsly.paper.location.actions;

import captainsly.paper.nodes.WorldNode;

public abstract class Action {

	private String actionName;
	private String actionDesc;

	public Action(String actionName) {
		this.actionName = actionName;
	}

	public abstract void onAction(WorldNode worldNode);

	public String getActionName() {
		return actionName;
	}

	public String getActionDesc() {
		return actionDesc;
	}

}

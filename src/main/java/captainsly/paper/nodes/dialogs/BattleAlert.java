package captainsly.paper.nodes.dialogs;

import javafx.scene.control.Alert;

public class BattleAlert extends Alert {

	public enum BattleState {
		PLAYER_TURN, ENEMY_TURN, WIN_STATE, LOSE_STATE
	}

	public BattleAlert() {
		super(AlertType.INFORMATION);

		// TODO: Prototype the battle alert, decide whether or not an alert is the best
		// way to show it. Will need to be able to autoclose the alert when the battle
		// is over

	}

}

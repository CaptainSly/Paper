package captainsly.paper.nodes.dialogs;

import captainsly.paper.entities.Player;
import captainsly.paper.entities.stats.Stat;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;

public class LevelUpDialog extends Alert {

	private Player player;
	private GridPane statGrid;

	// The Player will be able to put points into a skill to level them up.
	// They have so many stat points that they can put into each skill. The amount
	// of stat points is influenced by the Intelligence skill

	// TODO: Figure out/Make the right control to raise or lower
	private int skillPoints;

	public LevelUpDialog(Player player) {
		super(AlertType.INFORMATION);
		this.player = player;
		this.setTitle("Congratulations " + player.getActorName() + ", you've leveled up to Level "
				+ player.getActorStat(Stat.LEVEL));
		this.setHeaderText("ASCENDED TO LEVEL " + player.getActorStat(Stat.LEVEL));
		this.setGraphic(null);

		// TODO: Change this
		skillPoints = 10;

		statGrid = new GridPane();
		setupStatGrid();

		this.getButtonTypes().clear();
		this.getButtonTypes().add(ButtonType.CLOSE);

		this.getDialogPane().setContent(statGrid);

	}

	private void setupStatGrid() {

	}

}

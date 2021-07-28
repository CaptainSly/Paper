package captainsly.paper.nodes.dialogs;

import captainsly.paper.entities.Player;
import captainsly.paper.entities.stats.Stat;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;

public class LevelUpDialog extends Alert {

	private Player player;
	private GridPane statGrid;

	public LevelUpDialog(Player player) {
		super(AlertType.INFORMATION);
		this.player = player;
		this.setTitle("Congratulations " + player.getActorName() + ", you've leveled up to Level "
				+ player.getActorStat(Stat.LEVEL));
		this.setHeaderText("LEVEL " + player.getActorStat(Stat.LEVEL));
		this.setGraphic(null);
		
		statGrid = new GridPane();
		

		this.getButtonTypes().clear();
		this.getButtonTypes().add(ButtonType.CLOSE);
		this.getDialogPane().getChildren().add(statGrid);
		
	}

}

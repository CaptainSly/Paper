package captainsly.paper.nodes.dialogs;

import captainsly.paper.nodes.regions.WorldRegion;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;

public class PlayerJournalAlert extends Alert {

	private TabPane journalTabPane;
	private WorldRegion worldRegion;
	
	//TODO: Implement The Player Journal

	public PlayerJournalAlert(WorldRegion worldRegion) {
		super(AlertType.INFORMATION);
		this.worldRegion = worldRegion;
		this.setGraphic(null);
		this.setTitle(worldRegion.getPlayer().getActorName() + "'s Journal");
		this.setHeaderText(null);
		setupJournal();

		this.getDialogPane().setContent(journalTabPane);
	}
	
	private void setupJournal() {
		getButtonTypes().set(0, ButtonType.CLOSE);
	}

}

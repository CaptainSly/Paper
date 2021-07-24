package captainsly.paper.nodes.playerui;

import captainsly.paper.nodes.WorldNode;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;

public class PlayerJournal extends Alert {

	private TabPane journalTabPane;
	private WorldNode worldNode;
	
	//TODO: Implement The Player Journal

	public PlayerJournal(WorldNode worldNode) {
		super(AlertType.INFORMATION);
		this.worldNode = worldNode;
		this.setGraphic(null);
		this.setTitle(worldNode.getPlayer().getActorName() + "'s Journal");
		setupJournal();

		this.getDialogPane().setContent(journalTabPane);
	}
	
	private void setupJournal() {
	}

}

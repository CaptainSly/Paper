package captainsly.paper.mechanics.nodes.alerts;

import captainsly.paper.mechanics.nodes.regions.WorldRegion;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class PlayerJournalAlert extends Alert {

	private TabPane journalTabPane;
	private WorldRegion worldRegion;
	
	//TODO: Implement The Player Journal
	private Tab journalTab, questTab, factionsTab, skillsTab, beastiaryTab;
	
	public PlayerJournalAlert(WorldRegion worldRegion) {
		super(AlertType.INFORMATION);
		this.worldRegion = worldRegion;
		this.setGraphic(null);
		this.setTitle(worldRegion.getPlayer().getActorName() + "'s Journal");
		this.setHeaderText(null);

		journalTabPane = new TabPane();
		
		getButtonTypes().set(0, ButtonType.CLOSE);
		
		setupJournal();

		journalTabPane.getTabs().addAll(journalTab, skillsTab, questTab, factionsTab, beastiaryTab);
		
		this.getDialogPane().setContent(journalTabPane);
	}
	
	private void setupJournal() {
		journalTab = new Tab("Journal");
		questTab = new Tab("Quests");
		factionsTab = new Tab("Factions");
		skillsTab = new Tab("Skills");
		beastiaryTab = new Tab("Beastiary");
		
		journalTab.setClosable(false);
		questTab.setClosable(false);
		factionsTab.setClosable(false);
		skillsTab.setClosable(false);
		beastiaryTab.setClosable(false);
		
	}

}

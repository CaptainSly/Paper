package captainsly.paper.nodes.regions;

import java.util.Random;

import captainsly.paper.entities.Player;
import captainsly.paper.mechanics.locations.Location;
import captainsly.paper.mechanics.locations.Location.Direction;
import captainsly.paper.mechanics.locations.actions.Action;
import captainsly.paper.nodes.dialogs.PlayerJournalAlert;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class WorldRegion extends Region {

	private BorderPane worldRootPane, worldLocationPane;
	private GridPane worldJournalGrid;
	private TextArea worldOutputArea;
	private Location worldCurrentLocation;
	private MovementRegion movementRegion;
	private PlayerStatRegion playerStatRegion;
	private LocationInteractionRegion locationInteractionRegion;

	private PlayerJournalAlert playerJournal;
	private Player player;
	private Random rnJesus;

	public WorldRegion(Player player, Location worldCurrentLocation) {
		this.player = player;

		player.setWorldRegion(this);

		rnJesus = new Random();
		playerStatRegion = new PlayerStatRegion(this);
		movementRegion = new MovementRegion(this);
		locationInteractionRegion = new LocationInteractionRegion(this);
		playerJournal = new PlayerJournalAlert(this);

		worldRootPane = new BorderPane();
		worldJournalGrid = new GridPane();
		worldLocationPane = new BorderPane();

		worldOutputArea = new TextArea();

		worldOutputArea.setEditable(false);
		worldOutputArea.setWrapText(true);

		setLocation(worldCurrentLocation);

		setupPlayerJournalPane();
		GridPane locationControlGrid = new GridPane();
		locationControlGrid.setVgap(10);

		locationControlGrid.add(movementRegion, 0, 0);
		locationControlGrid.add(worldJournalGrid, 0, 1);

		worldLocationPane.setCenter(worldOutputArea);
		worldLocationPane.setLeft(locationControlGrid);

		worldRootPane.setPadding(new Insets(10, 10, 10, 10));
		player.modifyActorGold(20000);

		worldRootPane.setRight(locationInteractionRegion);
		worldRootPane.setCenter(worldLocationPane);
		worldRootPane.setLeft(playerStatRegion);

		this.getChildren().add(worldRootPane);
	}

	private String getLocationNeighborText(Location location) {
		String text = "";

		if (location.getNeighbor(Direction.NORTH) != null)
			text = text + "To the North lies " + location.getNeighbor(Direction.NORTH).getLocationName() + "\n";
		if (location.getNeighbor(Direction.SOUTH) != null)
			text = text + "To the South lies " + location.getNeighbor(Direction.SOUTH).getLocationName() + "\n";
		if (location.getNeighbor(Direction.EAST) != null)
			text = text + "To the East lies " + location.getNeighbor(Direction.EAST).getLocationName() + "\n";
		if (location.getNeighbor(Direction.WEST) != null)
			text = text + "To the West lies " + location.getNeighbor(Direction.WEST).getLocationName() + "\n";
		return text;
	}

	private void setupPlayerJournalPane() {
		worldJournalGrid.setHgap(10);
		worldJournalGrid.setVgap(10);

		Button journalBtn = new Button("JOURNAL");
		Button saveBtn = new Button("SAVE");
		Button loadBtn = new Button("LOAD");
		Button optionsBtn = new Button("OPTIONS");

		// Setup Button Stuff
		journalBtn.setOnAction(e -> playerJournal.show());

		worldJournalGrid.setHgap(5);
		worldJournalGrid.setVgap(5);
		worldJournalGrid.setPadding(new Insets(5, 5, 5, 5));

		worldJournalGrid.add(journalBtn, 0, 0);
		worldJournalGrid.add(saveBtn, 0, 1);
		worldJournalGrid.add(loadBtn, 0, 2);
		worldJournalGrid.add(optionsBtn, 0, 3);
	}

	public void resetOutput() {
		playerStatRegion.getCharacterInventoryList().refresh();
		worldOutputArea.clear();
		write("-=- " + worldCurrentLocation.getLocationName() + " -=-\n" + worldCurrentLocation.getLocationDesc()
				+ "\n\n");
		write(getLocationNeighborText(worldCurrentLocation));

		if (worldCurrentLocation.getActions().size() > 0) {
			write("");
			for (Action action : worldCurrentLocation.getActions())
				write("There is a " + action.getActionName());
		}

		write("\n");
	}

	public void write(String text) {
		worldOutputArea.appendText(text + "\n");
	}

	/**
	 * Clears the worldOutputArea TextArea
	 */
	public void clearWorldView() {
		worldOutputArea.clear();
		locationInteractionRegion.clear();
	}

	public void refresh() {
		getPlayerStatNode().getCharacterInventoryList().refresh();
	}

	public void setLocation(Location location) {
		this.worldCurrentLocation = location;
		locationInteractionRegion.setLocation(location);
		movementRegion.checkLocationPositions(location);
		refresh();
		resetOutput();
	}

	public PlayerStatRegion getPlayerStatNode() {
		return playerStatRegion;
	}

	public MovementRegion getMovementRegion() {
		return movementRegion;
	}

	public Location getCurrentLocation() {
		return worldCurrentLocation;
	}

	public Random getRNJesus() {
		return rnJesus;
	}

	public Player getPlayer() {
		return player;
	}

}

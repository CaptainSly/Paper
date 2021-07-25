package captainsly.paper.nodes.regions;

import java.util.Random;

import captainsly.paper.entities.Player;
import captainsly.paper.location.Location;
import captainsly.paper.location.Location.Direction;
import captainsly.paper.location.actions.LocationAction;
import captainsly.paper.nodes.dialogs.PlayerJournalAlert;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.Callback;

public class WorldRegion extends Region {

	private BorderPane worldRootPane, worldLocationPane, worldInteractionPane, worldCharacterPane, worldControlPane;
	private GridPane worldMovementPane;
	private Button worldMovementButtonUp, worldMovementButtonDown, worldMovementButtonLeft, worldMovementButtonRight;
	private TextArea worldOutputArea;
	private ListView<LocationAction> locationActionListView;

	private Location worldCurrentLocation;

	private WorldRegion worldNode;
	private PlayerStatRegion playerStatNode;
	private PlayerJournalAlert playerJournal;
	private Player player;
	private Random rnJesus;

	public WorldRegion(Player player, Location worldCurrentLocation) {
		this.player = player;

		worldNode = this;
		rnJesus = new Random();
		playerStatNode = new PlayerStatRegion(this);
		playerJournal = new PlayerJournalAlert(this);

		worldRootPane = new BorderPane();
		worldInteractionPane = new BorderPane();
		worldLocationPane = new BorderPane();
		worldCharacterPane = new BorderPane();
		worldControlPane = new BorderPane();
		worldMovementPane = new GridPane();

		worldMovementButtonUp = new Button();
		worldMovementButtonDown = new Button();
		worldMovementButtonLeft = new Button();
		worldMovementButtonRight = new Button();
		worldOutputArea = new TextArea();

		worldOutputArea.setEditable(false);
		worldOutputArea.setWrapText(true);

		setupMovementPane();
		setupActionPane();
		setLocation(worldCurrentLocation);

		worldCharacterPane.setPadding(new Insets(5, 5, 5, 5));
		worldInteractionPane.setPadding(new Insets(5, 5, 5, 5));

		worldLocationPane.setCenter(worldOutputArea);
		worldCharacterPane.setCenter(playerStatNode);

		worldControlPane.setLeft(worldMovementPane);
		worldControlPane.setRight(setupPlayerJournalPane());

		worldRootPane.setPadding(new Insets(10, 10, 10, 10));

		worldRootPane.setRight(worldInteractionPane);
		worldRootPane.setCenter(worldLocationPane);
		worldRootPane.setLeft(worldCharacterPane);
		worldRootPane.setBottom(worldControlPane);

		this.getChildren().add(worldRootPane);
	}

	private void setupActionPane() {
		locationActionListView = new ListView<LocationAction>();
		locationActionListView.setCellFactory(new Callback<ListView<LocationAction>, ListCell<LocationAction>>() {

			@Override
			public ListCell<LocationAction> call(ListView<LocationAction> param) {
				ListCell<LocationAction> cell = new ListCell<LocationAction>() {
					@Override
					protected void updateItem(LocationAction item, boolean empty) {
						super.updateItem(item, empty);

						if (empty) {
							this.setText("");
							this.setTooltip(null);
						} else {
							String locationTimer = "";
							if (item.locationTimer != 0) {
								locationTimer = "" + item.locationTimer;
							}
							this.setText(item.getLocationActionName() + "  |  " + item.getLocationActionStatus() + "  " + locationTimer);
							this.setTooltip(new Tooltip(item.getLocationActionDescription()));
							this.setOnMouseClicked(e -> {
								if (!item.isInAction())
									item.getAction().onAction(worldNode);
							});
						}
					}
				};
				return cell;
			}
		});

		worldInteractionPane.setCenter(locationActionListView);
	}

	private void setupMovementPane() {
		worldMovementButtonUp.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.ARROW_UP));
		worldMovementButtonUp.setOnAction(e -> {
			setLocation(worldCurrentLocation.getNeighbor(Direction.NORTH));
		});

		worldMovementButtonDown.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.ARROW_DOWN));
		worldMovementButtonDown.setOnAction(e -> {
			setLocation(worldCurrentLocation.getNeighbor(Direction.SOUTH));
		});

		worldMovementButtonLeft.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.ARROW_LEFT));
		worldMovementButtonLeft.setOnAction(e -> {
			setLocation(worldCurrentLocation.getNeighbor(Direction.EAST));
		});

		worldMovementButtonRight.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.ARROW_RIGHT));
		worldMovementButtonRight.setOnAction(e -> {
			setLocation(worldCurrentLocation.getNeighbor(Direction.WEST));
		});

		worldMovementPane.setHgap(5);
		worldMovementPane.setVgap(5);
		worldMovementPane.setPadding(new Insets(10, 10, 10, 10));

		worldMovementPane.add(worldMovementButtonUp, 1, 0);
		worldMovementPane.add(worldMovementButtonLeft, 0, 1);
		worldMovementPane.add(worldMovementButtonRight, 2, 1);
		worldMovementPane.add(worldMovementButtonDown, 1, 2);

	}

	private void checkLocationPositions(Location location) {
		worldMovementButtonUp.setDisable(!(location.getNeighbor(Direction.NORTH) != null));
		worldMovementButtonDown.setDisable(!(location.getNeighbor(Direction.SOUTH) != null));
		worldMovementButtonLeft.setDisable(!(location.getNeighbor(Direction.EAST) != null));
		worldMovementButtonRight.setDisable(!(location.getNeighbor(Direction.WEST) != null));

		worldMovementButtonUp.setTooltip(new Tooltip(worldCurrentLocation.doesNeighborExist(Direction.NORTH)
				? "To " + worldCurrentLocation.getNeighbor(Direction.NORTH).getLocationName()
				: ""));
		worldMovementButtonDown.setTooltip(new Tooltip(worldCurrentLocation.doesNeighborExist(Direction.SOUTH)
				? "To " + worldCurrentLocation.getNeighbor(Direction.SOUTH).getLocationName()
				: ""));
		worldMovementButtonLeft.setTooltip(new Tooltip(worldCurrentLocation.doesNeighborExist(Direction.EAST)
				? "To " + worldCurrentLocation.getNeighbor(Direction.EAST).getLocationName()
				: ""));
		worldMovementButtonRight.setTooltip(new Tooltip(worldCurrentLocation.doesNeighborExist(Direction.WEST)
				? "To " + worldCurrentLocation.getNeighbor(Direction.WEST).getLocationName()
				: ""));
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

	private GridPane setupPlayerJournalPane() {
		GridPane journalPane = new GridPane();

		journalPane.setHgap(10);
		journalPane.setVgap(10);

		Button journalBtn = new Button("JOURNAL");
		Button saveBtn = new Button("SAVE");
		Button loadBtn = new Button("LOAD");
		Button optionsBtn = new Button("OPTIONS");

		// Setup Button Stuff
		journalBtn.setOnAction(e -> {
			playerJournal.show();
		});

		journalPane.setHgap(5);
		journalPane.setVgap(5);
		journalPane.setPadding(new Insets(5, 5, 5, 5));

		journalPane.add(journalBtn, 0, 0);
		journalPane.add(saveBtn, 1, 0);
		journalPane.add(loadBtn, 2, 0);
		journalPane.add(optionsBtn, 3, 0);

		return journalPane;
	}

	public void resetOutput() {
		playerStatNode.getCharacterInventoryList().refresh();
		locationActionListView.getItems().clear();
		worldOutputArea.clear();
		write("-=- " + worldCurrentLocation.getLocationName() + " -=-\n" + worldCurrentLocation.getLocationDesc()
				+ "\n\n");
		write(getLocationNeighborText(worldCurrentLocation));

		if (worldCurrentLocation.getLocationActions().size() > 0) {
			locationActionListView
					.setItems(FXCollections.observableArrayList(worldCurrentLocation.getLocationActions()));
			write("");
			for (LocationAction action : worldCurrentLocation.getLocationActions())
				write("There is a " + action.getLocationActionName());
		}

		write("\n");
	}

	public void write(String text) {
		worldOutputArea.appendText(text + "\n");
	}

	public void clear() {
		worldOutputArea.clear();
	}

	public void refresh() {
		getPlayerStatNode().getCharacterInventoryList().refresh();
		locationActionListView.refresh();
	}

	public void setLocation(Location location) {
		this.worldCurrentLocation = location;
		checkLocationPositions(location);
		refresh();
		resetOutput();
	}

	public PlayerStatRegion getPlayerStatNode() {
		return playerStatNode;
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

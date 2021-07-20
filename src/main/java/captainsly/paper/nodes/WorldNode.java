package captainsly.paper.nodes;

import captainsly.paper.actions.Action;
import captainsly.paper.entities.Player;
import captainsly.paper.location.Location;
import captainsly.paper.location.Location.Direction;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.Callback;

public class WorldNode extends Region {

	private BorderPane worldLocationPane;
	private BorderPane worldRootPane, worldActionsPane, worldCharacterPane, worldControlPane;
	private GridPane worldMovementPane;
	private Button worldMovementButtonUp, worldMovementButtonDown, worldMovementButtonLeft, worldMovementButtonRight;
	private TextArea worldOutputArea;
	private ListView<Action> actionListView;

	private Location worldCurrentLocation;

	private WorldNode worldNode;
	private PlayerStatNode playerStatNode;
	private Player player;

	public WorldNode(Player player, Location worldCurrentLocation) {
		this.player = player;

		worldNode = this;
		playerStatNode = new PlayerStatNode(player);

		worldRootPane = new BorderPane();
		worldActionsPane = new BorderPane();
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

		worldLocationPane.setCenter(worldOutputArea);
		worldCharacterPane.setCenter(playerStatNode);

		worldControlPane.setLeft(worldMovementPane);
		worldControlPane.setRight(setupPlayerJournalPane());

		worldRootPane.setRight(worldActionsPane);
		worldRootPane.setCenter(worldLocationPane);
		worldRootPane.setLeft(worldCharacterPane);
		worldRootPane.setBottom(worldControlPane);

		this.getChildren().add(worldRootPane);
	}

	private void setupActionPane() {
		actionListView = new ListView<Action>();
		actionListView.setCellFactory(new Callback<ListView<Action>, ListCell<Action>>() {

			@Override
			public ListCell<Action> call(ListView<Action> param) {
				ListCell<Action> cell = new ListCell<Action>() {
					@Override
					protected void updateItem(Action item, boolean empty) {
						super.updateItem(item, empty);

						if (empty) {
							this.setText("");
							this.setTooltip(null);
						} else {
							this.setText(item.getActionName());
							this.setTooltip(new Tooltip(item.getActionDesc()));
							this.setOnMouseClicked(e -> item.onAction(worldNode));
						}
					}
				};
				return cell;
			}
		});

		worldActionsPane.setCenter(actionListView);
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
		Button questBtn = new Button("QUESTS");
		Button mapBtn = new Button("MAP");

		Button saveBtn = new Button("SAVE");
		Button loadBtn = new Button("LOAD");
		Button optionsBtn = new Button("OPTIONS");

		journalPane.add(journalBtn, 0, 0);
		journalPane.add(questBtn, 1, 0);
		journalPane.add(mapBtn, 2, 0);
		journalPane.add(saveBtn, 0, 1);
		journalPane.add(loadBtn, 1, 1);
		journalPane.add(optionsBtn, 2, 1);

		return journalPane;
	}

	public void resetOutput() {
		playerStatNode.getPlayerInventoryList().refresh();
		actionListView.getItems().clear();
		worldOutputArea.clear();
		write("-=-" + worldCurrentLocation.getLocationName() + "-=-\n" + worldCurrentLocation.getLocationDesc()
				+ "\n\n");
		write(getLocationNeighborText(worldCurrentLocation));

		if (worldCurrentLocation.getActions().size() > 0) {
			actionListView.setItems(FXCollections.observableArrayList(worldCurrentLocation.getActions()));
			write("");
			for (Action action : worldCurrentLocation.getActions())
				write("There is a " + action.getActionName());
		}

		write("\n");
	}

	public void write(String text) {
		worldOutputArea.appendText(text + "\n");
	}

	public void setLocation(Location location) {
		this.worldCurrentLocation = location;
		checkLocationPositions(location);
		resetOutput();
	}

	public Location getCurrentLocation() {
		return worldCurrentLocation;
	}

	public Player getPlayer() {
		return player;
	}

}

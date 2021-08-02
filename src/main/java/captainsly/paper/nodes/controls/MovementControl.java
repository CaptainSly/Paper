package captainsly.paper.nodes.controls;

import captainsly.paper.mechanics.locations.Location;
import captainsly.paper.mechanics.locations.Location.Direction;
import captainsly.paper.nodes.regions.WorldRegion;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class MovementControl extends Region {

	private GridPane movementGrid;

	private Button worldMovementButtonUp, worldMovementButtonDown, worldMovementButtonLeft, worldMovementButtonRight;

	private WorldRegion worldRegion;

	public MovementControl(WorldRegion worldRegion) {
		this.worldRegion = worldRegion;
		movementGrid = new GridPane();

		worldMovementButtonUp = new Button();
		worldMovementButtonUp.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.ARROW_UP));
		worldMovementButtonUp.setOnAction(e -> {
			worldRegion.setLocation(worldRegion.getCurrentLocation().getNeighbor(Direction.NORTH));
		});

		worldMovementButtonDown = new Button();
		worldMovementButtonDown.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.ARROW_DOWN));
		worldMovementButtonDown.setOnAction(e -> {
			worldRegion.setLocation(worldRegion.getCurrentLocation().getNeighbor(Direction.SOUTH));
		});

		worldMovementButtonLeft = new Button();
		worldMovementButtonLeft.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.ARROW_LEFT));
		worldMovementButtonLeft.setOnAction(e -> {
			worldRegion.setLocation(worldRegion.getCurrentLocation().getNeighbor(Direction.EAST));
		});

		worldMovementButtonRight = new Button();
		worldMovementButtonRight.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.ARROW_RIGHT));
		worldMovementButtonRight.setOnAction(e -> {
			worldRegion.setLocation(worldRegion.getCurrentLocation().getNeighbor(Direction.WEST));
		});

		movementGrid.setHgap(5);
		movementGrid.setVgap(5);
		movementGrid.setPadding(new Insets(10, 10, 10, 10));

		movementGrid.add(worldMovementButtonUp, 1, 0);
		movementGrid.add(worldMovementButtonLeft, 0, 1);
		movementGrid.add(worldMovementButtonRight, 2, 1);
		movementGrid.add(worldMovementButtonDown, 1, 2);
		
		this.getChildren().add(movementGrid);
	}

	public void checkLocationPositions(Location location) {
		worldMovementButtonUp.setDisable(!(location.getNeighbor(Direction.NORTH) != null));
		worldMovementButtonDown.setDisable(!(location.getNeighbor(Direction.SOUTH) != null));
		worldMovementButtonLeft.setDisable(!(location.getNeighbor(Direction.EAST) != null));
		worldMovementButtonRight.setDisable(!(location.getNeighbor(Direction.WEST) != null));

		worldMovementButtonUp.setTooltip(new Tooltip(worldRegion.getCurrentLocation().doesNeighborExist(Direction.NORTH)
				? "To " + worldRegion.getCurrentLocation().getNeighbor(Direction.NORTH).getLocationName()
				: ""));
		worldMovementButtonDown
				.setTooltip(new Tooltip(worldRegion.getCurrentLocation().doesNeighborExist(Direction.SOUTH)
						? "To " + worldRegion.getCurrentLocation().getNeighbor(Direction.SOUTH).getLocationName()
						: ""));
		worldMovementButtonLeft
				.setTooltip(new Tooltip(worldRegion.getCurrentLocation().doesNeighborExist(Direction.EAST)
						? "To " + worldRegion.getCurrentLocation().getNeighbor(Direction.EAST).getLocationName()
						: ""));
		worldMovementButtonRight
				.setTooltip(new Tooltip(worldRegion.getCurrentLocation().doesNeighborExist(Direction.WEST)
						? "To " + worldRegion.getCurrentLocation().getNeighbor(Direction.WEST).getLocationName()
						: ""));
	}

	public void disableMovement() {

		worldMovementButtonUp.setDisable(true);
		worldMovementButtonDown.setDisable(true);
		worldMovementButtonLeft.setDisable(true);
		worldMovementButtonRight.setDisable(true);
	}

}

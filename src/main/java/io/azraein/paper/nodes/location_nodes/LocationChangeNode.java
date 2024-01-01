package io.azraein.paper.nodes.location_nodes;

import io.azraein.paper.PaperApp;
import io.azraein.paper.core.locations.Direction;
import io.azraein.paper.core.system.Registry;
import io.azraein.paper.nodes.PaperNode;
import io.azraein.paper.scenes.PaperGameScene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class LocationChangeNode extends PaperNode {

	private Button[] locationChangeButton;

	public LocationChangeNode(PaperApp paperApp, PaperGameScene paperGameScene) {
		super(paperApp);
		locationChangeButton = new Button[4];

		for (Direction dir : Direction.values()) {
			locationChangeButton[dir.ordinal()] = new Button();
			locationChangeButton[dir.ordinal()]
					.setGraphic(new ImageView(new Image(dir.name().toLowerCase() + "_arrow.png")));
			locationChangeButton[dir.ordinal()].setDisable(true);

		}

		GridPane locationChangeGrid = new GridPane();
		locationChangeGrid.setPadding(new Insets(10));
		locationChangeGrid.setHgap(10);
		locationChangeGrid.setVgap(10);

		locationChangeGrid.add(locationChangeButton[Direction.NORTH.ordinal()], 1, 0);
		locationChangeGrid.add(locationChangeButton[Direction.WEST.ordinal()], 0, 1);
		locationChangeGrid.add(locationChangeButton[Direction.SOUTH.ordinal()], 1, 2);
		locationChangeGrid.add(locationChangeButton[Direction.EAST.ordinal()], 2, 1);

		paperApp.locationProperty().addListener((observableValue, oldLocation, newLocation) -> {

			if (newLocation != null) {

				for (Direction dir : Direction.values()) {
					boolean hasNeighbor = newLocation.getLocationNeighbor(dir) != null;

					int idx = dir.ordinal();
					locationChangeButton[idx].setDisable(!hasNeighbor);
					locationChangeButton[idx].setOnAction(e -> paperApp
							.changePaperLocation(Registry.getLocation(newLocation.getLocationNeighbor(dir))));
				}
			}

		});

		getChildren().add(locationChangeGrid);
	}

}

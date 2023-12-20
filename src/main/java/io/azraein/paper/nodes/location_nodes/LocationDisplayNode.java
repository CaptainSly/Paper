package io.azraein.paper.nodes.location_nodes;

import io.azraein.paper.PaperApp;
import io.azraein.paper.nodes.PaperNode;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;

public class LocationDisplayNode extends PaperNode {

	public LocationDisplayNode(PaperApp paperApp) {
		super(paperApp);
		this.setPadding(new Insets(10, 5, 10, 5));

		TextArea locationDispTextArea = new TextArea();
		locationDispTextArea.setEditable(false);
		locationDispTextArea.setWrapText(true);

		paperApp.locationProperty().addListener((obs, old, nu) -> {
			if (nu != null) {
				locationDispTextArea
						.setText("-=-=-" + nu.getLocationName() + "-=-=-" + "\n\n\n" + nu.getLocationDescription());
			}
		});

		getChildren().add(locationDispTextArea);
	}

}

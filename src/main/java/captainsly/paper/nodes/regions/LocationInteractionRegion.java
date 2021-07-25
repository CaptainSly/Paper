package captainsly.paper.nodes.regions;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

public class LocationInteractionRegion extends Region {
	// TODO: Fill out this class, bring over the LocationAction controls from the
	// worldnode to here

	private BorderPane interactionNodeRoot;

	public LocationInteractionRegion() {
		interactionNodeRoot = new BorderPane();

		setupLocationActions();
		setupLocationNpcs();

		this.getChildren().add(interactionNodeRoot);
	}

	private void setupLocationActions() {
	}

	private void setupLocationNpcs() {
	}

}

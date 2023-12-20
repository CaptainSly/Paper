package io.azraein.paper.nodes;

import io.azraein.paper.PaperApp;
import javafx.scene.Node;
import javafx.scene.layout.Region;

public abstract class PaperNode extends Region {

	protected final PaperApp paperApp;

	public PaperNode(PaperApp paperApp) {
		this.paperApp = paperApp;
	}

	protected void setContent(Node node) {
		getChildren().add(node);
	}

}

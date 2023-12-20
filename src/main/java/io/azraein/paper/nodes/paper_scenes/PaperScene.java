package io.azraein.paper.nodes.paper_scenes;

import io.azraein.paper.PaperApp;
import javafx.scene.Parent;
import javafx.scene.layout.Region;

public abstract class PaperScene extends Region {

	// PaperScene Members
	protected Parent rootContent;
	protected PaperApp paperApp;

	public PaperScene(PaperApp paperApp, Parent rootContent) {
		this.paperApp = paperApp;
		this.rootContent = rootContent;

		getChildren().add(rootContent);
	}

	public PaperScene(PaperApp paperApp) {
		this.paperApp = paperApp;
	}

	// Getters and Setters

	public Parent getRootContent() {
		return rootContent;
	}

	public PaperApp getPaperApp() {
		return paperApp;
	}

	public void setRootContent(Parent rootContent) {
		this.rootContent = rootContent;
		getChildren().clear();
		getChildren().add(rootContent);
	}

	public void setPaperApp(PaperApp paperApp) {
		this.paperApp = paperApp;
	}

}

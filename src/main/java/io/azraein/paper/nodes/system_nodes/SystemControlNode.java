package io.azraein.paper.nodes.system_nodes;

import io.azraein.paper.PaperApp;
import io.azraein.paper.nodes.PaperNode;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class SystemControlNode extends PaperNode {

	public SystemControlNode(PaperApp paperApp) {
		super(paperApp);
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10));
		grid.setHgap(10);
		grid.setVgap(10);

		Button saveGameBtn = new Button("Save Game");
		Button loadGameBtn = new Button("Load Game");
		Button optionsBtn = new Button("Options");
		Button questManagerBtn = new Button("Quests");

		grid.add(saveGameBtn, 0, 0);
		grid.add(loadGameBtn, 1, 0);
		grid.add(optionsBtn, 2, 0);
		grid.add(questManagerBtn, 3, 0);

		getChildren().add(grid);
	}

}

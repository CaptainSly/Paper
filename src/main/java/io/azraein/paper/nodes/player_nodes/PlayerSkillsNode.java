package io.azraein.paper.nodes.player_nodes;

import io.azraein.paper.PaperApp;
import io.azraein.paper.core.entities.stats.Skills;
import io.azraein.paper.nodes.PaperNode;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class PlayerSkillsNode extends PaperNode {

	private ListView<Skills> playerSkillView;
	
	public PlayerSkillsNode(PaperApp paperApp) {
		super(paperApp);

		playerSkillView = new ListView<>();
		playerSkillView.getItems().addAll(Skills.values());
		playerSkillView.setCellFactory(new Callback<>() {

			@Override
			public ListCell<Skills> call(ListView<Skills> param) {
				return new ListCell<Skills>() {

					@Override
					protected void updateItem(Skills item, boolean empty) {
						super.updateItem(item, empty);
						var player = paperApp.playerProperty().get();

						if (item != null) {
							this.setText(item.name() + ": " + player.getEntitySkill(item));
						} else
							this.setText("");

					}

				};
			}

		});

		getChildren().add(playerSkillView);

	}
	
	public void reset() {
		playerSkillView.getItems().clear();
		playerSkillView.getItems().addAll(Skills.values());
	}

}

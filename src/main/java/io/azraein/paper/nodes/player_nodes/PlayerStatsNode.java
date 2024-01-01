package io.azraein.paper.nodes.player_nodes;

import io.azraein.paper.PaperApp;
import io.azraein.paper.core.Paper;
import io.azraein.paper.core.entities.stats.Characteristics;
import io.azraein.paper.nodes.PaperNode;
import io.azraein.paper.scenes.PaperGameScene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class PlayerStatsNode extends PaperNode {

	public PlayerStatsNode(PaperApp paperApp, PaperGameScene gameScene) {
		super(paperApp);

		PlayerSkillsNode playerSkillsNode = new PlayerSkillsNode(paperApp);

		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setPadding(new Insets(10, 5, 10, 5));
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		Button playerName = new Button("");
		Label playerHp = new Label("Hp");
		Label playerMp = new Label("Mp");
		Label playerGold = new Label("G");

		Label timeLbl = new Label();
		Tooltip timeLblTooltip = new Tooltip("");
		timeLbl.setTooltip(timeLblTooltip);
		timeLbl.textProperty().bind(Paper.paperCalendar.timeProperty());
		timeLblTooltip.textProperty().bind(Paper.paperCalendar.dateProperty());

		Label[] charLabels = new Label[Characteristics.values().length];
		for (Characteristics chara : Characteristics.values())
			charLabels[chara.ordinal()] = new Label(chara.name() + ": ");

		playerName.setOnAction(e -> {
			playerSkillsNode.reset();
			
			Alert alert = new Alert(AlertType.NONE);
			alert.setTitle(paperApp.playerProperty().get().getEntityName() + "'s Skills");
			alert.getButtonTypes().add(ButtonType.OK);
			alert.getDialogPane().setContent(playerSkillsNode);
			alert.show();
		});

		paperApp.playerProperty().addListener((obs, oldValue, newValue) -> {

			if (newValue != null) {
				var player = paperApp.playerProperty().get();

				playerName.setText(player.getEntityName());
				playerHp.setText(
						player.getEntityCurrentHealthPoints() + "/" + player.getEntityMaxHealthPoints() + "HP");
				playerMp.setText(player.getEntityCurrentManaPoints() + "/" + player.getEntityMaxManaPoints() + "MP");
				playerGold.setText(player.entityGoldProperty().get() + "G");

				for (Characteristics chara : Characteristics.values())
					charLabels[chara.ordinal()].setText(chara.name() + ": " + player.getEntityCharacteristic(chara));

				// Set Player's Mana/HP Listeners
				player.entityCurrentHealthPointsProperty().addListener((observable, oldHp, newHp) -> {

					if (newHp != null)
						playerHp.setText(
								player.getEntityCurrentHealthPoints() + "/" + player.getEntityMaxHealthPoints() + "HP");

				});

				player.entityCurrentManaPointsProperty().addListener((observable, oldMp, newMp) -> {

					if (newMp != null)
						playerMp.setText(
								player.getEntityCurrentManaPoints() + "/" + player.getEntityMaxManaPoints() + "MP");

				});

				player.entityGoldProperty().addListener((observable, oldGp, newGp) -> {

					if (newGp != null)
						playerGold.setText(newGp + "G");

				});

			}

		});

		gridPane.add(playerName, 0, 0);
		gridPane.add(playerHp, 1, 0);
		gridPane.add(playerMp, 2, 0);
		gridPane.add(timeLbl, 3, 0);
		gridPane.add(playerGold, 4, 0);

		gridPane.add(charLabels[Characteristics.STR.ordinal()], 0, 1);
		gridPane.add(charLabels[Characteristics.CON.ordinal()], 1, 1);
		gridPane.add(charLabels[Characteristics.DEX.ordinal()], 2, 1);
		gridPane.add(charLabels[Characteristics.INT.ordinal()], 3, 1);

		gridPane.add(charLabels[Characteristics.SIZ.ordinal()], 0, 2);
		gridPane.add(charLabels[Characteristics.POW.ordinal()], 1, 2);
		gridPane.add(charLabels[Characteristics.APP.ordinal()], 2, 2);
		gridPane.add(charLabels[Characteristics.EDU.ordinal()], 3, 2);

		getChildren().add(gridPane);
	}

}

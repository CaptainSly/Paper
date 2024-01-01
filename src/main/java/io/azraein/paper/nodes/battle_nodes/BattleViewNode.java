package io.azraein.paper.nodes.battle_nodes;

import io.azraein.paper.PaperApp;
import io.azraein.paper.core.entities.Entity;
import io.azraein.paper.nodes.PaperNode;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class BattleViewNode extends PaperNode {

	private final ObjectProperty<Entity> enemyEntityProperty = new SimpleObjectProperty<>();

	private Image enemySprite;

	public BattleViewNode(PaperApp paperApp) {
		super(paperApp);

		HBox rootContainer = new HBox();
		rootContainer.setAlignment(Pos.CENTER);
		
		GridPane enemyGrid = new GridPane();
		GridPane playerGrid = new GridPane();

		Label enemyNameLbl = new Label();
		Label enemyHpLbl = new Label();

		Label playerNameLbl = new Label();
		Label playerHpLbl = new Label();

		ImageView enemyImageView = new ImageView();
		ImageView playerImageView = new ImageView();

		enemyGrid.setPadding(new Insets(15));
		enemyGrid.setHgap(5);
		enemyGrid.add(enemyNameLbl, 0, 0);
		enemyGrid.add(enemyHpLbl, 1, 0);
		enemyGrid.add(enemyImageView, 0, 1);

		playerGrid.setPadding(new Insets(15));
		playerGrid.setHgap(5);
		playerGrid.add(playerNameLbl, 0, 0);
		playerGrid.add(playerHpLbl, 1, 0);
		playerGrid.add(playerImageView, 0, 1);

		rootContainer.getChildren().addAll(playerGrid, enemyGrid);

		enemyEntityProperty.addListener((obs, oldValue, newValue) -> {

			if (newValue != null) {
				enemySprite = new Image(
						newValue.getEntityCharacterBackground().getBackgroundName().toLowerCase() + ".png");
				enemyImageView.setImage(enemySprite);

				enemyNameLbl.setText("Name: " + newValue.getEntityName());

				enemyHpLbl.setText(newValue.getEntityCurrentHealthPoints() + "/" + newValue.getEntityMaxHealthPoints());
				newValue.entityCurrentHealthPointsProperty().addListener((ocs, olddValue, newwValue) -> {

					if (newwValue != null) {
						enemyHpLbl.setText(newwValue.intValue() + "/" + newValue.getEntityMaxHealthPoints());
					}

				});

			}
		});

		paperApp.playerProperty().addListener((obbs, ooldValue, player) -> {

			if (player != null) {

				playerNameLbl.setText("Name: " + player.getEntityName());
				playerHpLbl.setText(player.getEntityCurrentHealthPoints() + "/" + player.getEntityMaxHealthPoints());

				playerImageView.setImage(
						new Image(player.getEntityCharacterBackground().getBackgroundName().toLowerCase() + ".png"));

				player.entityCurrentHealthPointsProperty().addListener((obs, oldValue, newValue) -> {

					if (newValue != null) {

						playerHpLbl.setText(newValue.intValue() + "/" + player.getEntityMaxHealthPoints());

					}

				});
			}

		});

		this.getChildren().add(rootContainer);
	}

	public void startBattle(Entity enemy) {
		enemyEntityProperty.set(enemy);
	}

}

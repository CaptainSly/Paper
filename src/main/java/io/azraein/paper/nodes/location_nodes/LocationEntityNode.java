package io.azraein.paper.nodes.location_nodes;

import io.azraein.paper.PaperApp;
import io.azraein.paper.core.entities.Entity;
import io.azraein.paper.core.system.Registry;
import io.azraein.paper.nodes.PaperNode;
import io.azraein.paper.nodes.paper_scenes.PaperBattleScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class LocationEntityNode extends PaperNode {

	private ListView<Entity> entityList;

	public LocationEntityNode(PaperApp paperApp) {
		super(paperApp);
		entityList = new ListView<>();
		entityList.setCellFactory(new Callback<ListView<Entity>, ListCell<Entity>>() {

			@Override
			public ListCell<Entity> call(ListView<Entity> param) {
				return new ListCell<Entity>() {

					// TODO: V
					/*
					 * The LocationEntityNode can hold both Npcs, and Monsters/Enemies the node
					 * itself is a ListView that represents the ListCell as a button. Depending on
					 * the Entity superclass, something different will happen when the entities is
					 * interacted with
					 */

					@Override
					protected void updateItem(Entity item, boolean empty) {
						super.updateItem(item, empty);
						this.setPadding(new Insets(5));
						this.setAlignment(Pos.CENTER);

						if (item != null) {
							Button npcButton = new Button(item.getEntityName());
							npcButton.setOnAction(event -> {
								var battleScene = (PaperBattleScene) paperApp.getPaperScenes().get("battleScene");
								battleScene.startBattle(item);
								paperApp.changePaperScene("battleScene");
							});

							this.setGraphic(npcButton);
						} else {
							this.setGraphic(null);
						}

					}

				};
			}

		});

		paperApp.locationProperty().addListener((obs, oldLocation, newLocation) -> {

			if (newLocation != null) {
				entityList.getItems().clear();
				ObservableList<Entity> dummyList = FXCollections.observableArrayList();
				for (String id : newLocation.getLocationEntities())
					dummyList.add(Registry.getEntity(id));

				entityList.setItems(dummyList);
			}

		});

		getChildren().add(entityList);
	}

}

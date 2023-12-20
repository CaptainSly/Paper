package io.azraein.paper.nodes.location_nodes;

import org.tinylog.Logger;

import io.azraein.paper.PaperApp;
import io.azraein.paper.core.impl.IGameEvent;
import io.azraein.paper.core.system.Registry;
import io.azraein.paper.nodes.PaperNode;
import io.azraein.paper.nodes.paper_scenes.PaperGameScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;

public class LocationMenuNode extends PaperNode {

	private ListView<IGameEvent> eventList;

	public LocationMenuNode(PaperApp paperApp, PaperGameScene gameScene) {
		super(paperApp);
		eventList = new ListView<IGameEvent>();

		eventList.setCellFactory(new Callback<ListView<IGameEvent>, ListCell<IGameEvent>>() {

			@Override
			public ListCell<IGameEvent> call(ListView<IGameEvent> param) {
				return new ListCell<IGameEvent>() {

					@Override
					protected void updateItem(IGameEvent item, boolean empty) {
						super.updateItem(item, empty);
						this.setPadding(new Insets(5));
						this.setAlignment(Pos.CENTER);

						if (item != null) {
							Button btn = new Button(item.eventName());
							btn.setTooltip(new Tooltip("Not running"));
							btn.setOnAction(e -> {

								if (gameScene.getRepeatableEvents().contains(item)) {
									int i = gameScene.getRepeatableEvents().indexOf(item);
									Logger.debug("Stopping Repeatable Event");
									var gameEvent = gameScene.getRepeatableEvents().get(i);
									gameEvent.stop();
									btn.setText(item.eventName());
									btn.setTooltip(new Tooltip("Not running"));
								} else {
									btn.setText(item.eventName() + "*");
									btn.setTooltip(new Tooltip("Currently Running"));
									item.reset();
									gameScene.addGameEventToQueue(item);
								}

							});

							this.setGraphic(btn);
						} else {
							this.setGraphic(null);
						}

					}

				};
			}

		});

		paperApp.locationProperty().addListener((obs, oldLocation, newLocation) -> {

			if (newLocation != null) {
				eventList.getItems().clear();
				ObservableList<IGameEvent> dummyList = FXCollections.observableArrayList();
				for (String id : newLocation.getLocationEvents())
					dummyList.add(Registry.getGameEvent(id));

				eventList.setItems(dummyList);
			}

		});

		getChildren().add(eventList);
	}

}

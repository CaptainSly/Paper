package captainsly.paper.nodes.regions;

import captainsly.paper.entities.Npc;
import captainsly.paper.mechanics.locations.Location;
import captainsly.paper.mechanics.locations.actions.Action;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class LocationInteractionRegion extends Region {
	// TODO: Fill out this class, bring over the LocationAction controls from the
	// worldnode to here

	private VBox interactionNodeRoot;

	private ListView<Action> actionListView;
	private ListView<Npc> npcListView;

	private WorldRegion worldRegion;

	public LocationInteractionRegion(WorldRegion worldRegion) {
		this.worldRegion = worldRegion;
		interactionNodeRoot = new VBox();
		actionListView = new ListView<Action>();
		npcListView = new ListView<Npc>();

		interactionNodeRoot.setPadding(new Insets(5, 5, 5, 5));
		
		setupLocationActions();
		setupLocationNpcs();

		this.getChildren().add(interactionNodeRoot);
	}

	private void setupLocationActions() {
		actionListView.setMaxHeight(350);
		actionListView.setCellFactory(new Callback<ListView<Action>, ListCell<Action>>() {

			@Override
			public ListCell<Action> call(ListView<Action> param) {
				ListCell<Action> cell = new ListCell<Action>() {
					@Override
					protected void updateItem(Action item, boolean empty) {
						super.updateItem(item, empty);

						if (empty) {
							this.setText("");
							this.setTooltip(null);
						} else {
							this.setText(item.getActionName());
							this.setTooltip(new Tooltip(item.getActionDescription()));
							this.setOnMouseClicked(e -> {
								if (!item.isInAction())
									item.onAction(worldRegion);
							});
						}
					}
				};
				return cell;
			}
		});
		
		interactionNodeRoot.getChildren().add(actionListView);
	}

	private void setupLocationNpcs() {
		npcListView.setMaxHeight(350);
		npcListView.setCellFactory(new Callback<ListView<Npc>, ListCell<Npc>>() {

			@Override
			public ListCell<Npc> call(ListView<Npc> param) {
				ListCell<Npc> cell = new ListCell<Npc>() {
					@Override
					protected void updateItem(Npc item, boolean empty) {
						super.updateItem(item, empty);

						if (empty) {
							this.setText("");
							this.setTooltip(null);
						} else {
							this.setText(item.getActorName());
							this.setOnMouseClicked(e -> {
								//TODO: Create a Dialogue(Talking) dialog lol
							});
						}
					}
				};
				return cell;
			}
		});
		

		interactionNodeRoot.getChildren().add(npcListView);
	}
	
	public void setLocation(Location location) {
		actionListView.setItems(FXCollections.observableArrayList(location.getActions()));
		npcListView.setItems(FXCollections.observableArrayList(location.getNpcs()));
	}

	public void clear() {
		actionListView.getItems().clear();
		npcListView.getItems().clear();
	}

}

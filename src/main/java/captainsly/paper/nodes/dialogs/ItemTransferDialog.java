package captainsly.paper.nodes.dialogs;

import java.util.Optional;

import captainsly.paper.entities.Actor;
import captainsly.paper.mechanics.containers.ItemSlot;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class ItemTransferDialog extends Alert {

	private BorderPane containerPane;
	private GridPane containerGrid, playerGrid;
	private ListView<ItemSlot> playerInventoryList, containerInventoryList;
	private Label playerLabel, containerLabel;
	private Actor containerActor, playerActor;

	public ItemTransferDialog(Actor containerActor, Actor playerActor) {
		super(AlertType.CONFIRMATION);
		this.setTitle("Searching " + containerActor.getActorName());
		this.setHeaderText("Transfer");
		this.containerActor = containerActor;
		this.playerActor = playerActor;

		this.getButtonTypes().clear();

		ButtonType leaveButton = new ButtonType("Leave");
		this.getButtonTypes().add(leaveButton);

		containerPane = new BorderPane();
		containerGrid = new GridPane();
		playerGrid = new GridPane();

		setupListViews();
		
		containerPane.setLeft(playerGrid);
		containerPane.setRight(containerGrid);
		this.getDialogPane().setContent(containerPane);
	}

	private void setupListViews() {
		containerInventoryList = new ListView<ItemSlot>(containerActor.getActorInventory().getItemSlots());
		playerInventoryList = new ListView<ItemSlot>(playerActor.getActorInventory().getItemSlots());

		playerLabel = new Label(playerActor.getActorName());
		containerLabel = new Label(containerActor.getActorName());

		containerInventoryList.setCellFactory(new Callback<ListView<ItemSlot>, ListCell<ItemSlot>>() {

			@Override
			public ListCell<ItemSlot> call(ListView<ItemSlot> param) {

				ListCell<ItemSlot> cell = new ListCell<ItemSlot>() {

					@Override
					protected void updateItem(ItemSlot item, boolean empty) {
						super.updateItem(item, empty);
						if (empty || item.isEmpty()) {
							this.setText("");
							this.setTooltip(null);
							this.setOnMouseClicked(null);
						} else {
							this.setText(item.getItem().getItemName() + " | " + item.getItemCount());
							this.setTooltip(new Tooltip(item.getItem().getItemDesc()));
							this.setOnMouseClicked(e -> {
								if (item.getItemCount() > 1) {
									NumberDialog takeDialog = new NumberDialog(
											"How many " + item.getItem().getItemName() + " do you want to take?", null);
									Optional<Integer> result = takeDialog.showAndWait();
									result.ifPresent(takenAmount -> {
										if (takenAmount > item.getItemCount())
											takenAmount = item.getItemCount();

										Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
										confirmationAlert.setTitle("Are you sure?");
										confirmationAlert.setContentText(
												"About to take " + takenAmount + " " + item.getItem().getItemName());

										Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();
										if (confirmationResult.get() == ButtonType.OK) {
											playerActor.getActorInventory().add(item.getItem(), takenAmount);
											item.remove(takenAmount);
											refresh();
										}

									});

								} else {
									Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
									confirmationAlert.setTitle("Are you sure?");
									confirmationAlert
											.setContentText("You're about to sell " + item.getItem().getItemName());

									Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();
									if (confirmationResult.get() == ButtonType.OK) {
										playerActor.getActorInventory().add(item.getItem(), 1);
										item.remove(1);
										refresh();

									}
								}
							});
						}
					}

				};

				return cell;
			}
		});

		playerInventoryList.setCellFactory(new Callback<ListView<ItemSlot>, ListCell<ItemSlot>>() {

			@Override
			public ListCell<ItemSlot> call(ListView<ItemSlot> param) {

				ListCell<ItemSlot> cell = new ListCell<ItemSlot>() {

					@Override
					protected void updateItem(ItemSlot item, boolean empty) {
						super.updateItem(item, empty);
						if (empty || item.isEmpty()) {
							this.setText("");
							this.setTooltip(null);
							this.setOnMouseClicked(null);
						} else {
							this.setText(item.getItem().getItemName() + " | " + item.getItemCount());
							this.setTooltip(new Tooltip(item.getItem().getItemDesc()));
							this.setOnMouseClicked(e -> {
								if (item.getItemCount() > 1) {
									NumberDialog putDialog = new NumberDialog(
											"How many " + item.getItem().getItemName() + " do you want to put?", null);
									Optional<Integer> result = putDialog.showAndWait();
									result.ifPresent(takenAmount -> {
										if (takenAmount > item.getItemCount())
											takenAmount = item.getItemCount();

										Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
										confirmationAlert.setTitle("Are you sure?");
										confirmationAlert.setContentText(
												"About to put " + takenAmount + " " + item.getItem().getItemName());

										Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();
										if (confirmationResult.get() == ButtonType.OK) {
											containerActor.getActorInventory().add(item.getItem(), takenAmount);
											item.remove(takenAmount);
											refresh();
										}

									});

								} else {
									Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
									confirmationAlert.setTitle("Are you sure?");
									confirmationAlert
											.setContentText("You're about to sell " + item.getItem().getItemName());

									Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();
									if (confirmationResult.get() == ButtonType.OK) {
										containerActor.getActorInventory().add(item.getItem(), 1);
										item.remove(1);
										refresh();

									}
								}
							});
						}
					}

				};

				return cell;
			}
		});
		
		containerGrid.add(containerLabel, 0, 0);
		containerGrid.add(containerInventoryList, 0, 1);
		
		playerGrid.add(playerLabel, 0, 0);
		playerGrid.add(playerInventoryList, 0, 1);

	}

	private void refresh() {
		playerInventoryList.refresh();
		containerInventoryList.refresh();
	}

}

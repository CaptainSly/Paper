package captainsly.paper.nodes.dialogs;

import java.util.Optional;

import captainsly.paper.entities.Actor;
import captainsly.paper.mechanics.containers.Inventory;
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

public class MerchantAlert extends Alert {

	private Actor player, merchant;

	private BorderPane merchantPane;
	private GridPane playerControls, merchantControls;
	private Inventory playerInventory, merchantInventory;
	private ListView<ItemSlot> playerInventoryList, merchantInventoryList;
	private Label playerLabel, merchantLabel;

	public MerchantAlert(Actor player, Actor merchant, String locationName) {
		super(AlertType.CONFIRMATION);
		this.setTitle(locationName + " Market");
		this.setHeaderText("BUY/SELL");
		this.player = player;
		this.merchant = merchant;
		this.getButtonTypes().clear();

		ButtonType leaveButton = new ButtonType("Leave");
		this.getButtonTypes().add(leaveButton);

		merchantPane = new BorderPane();
		playerControls = new GridPane();
		merchantControls = new GridPane();

		playerInventory = player.getActorInventory();
		merchantInventory = merchant.getActorInventory();

		merchantPane.setLeft(playerControls);
		merchantPane.setRight(merchantControls);

		setupMerchantPane();
		this.getDialogPane().setContent(merchantPane);
	}

	private void setupMerchantPane() {
		playerInventoryList = new ListView<ItemSlot>(playerInventory.getItemSlots());
		merchantInventoryList = new ListView<ItemSlot>(merchantInventory.getItemSlots());

		// Buttons and Labels
		playerLabel = new Label(player.getActorName() + " GP: " + player.getActorGold());
		merchantLabel = new Label(merchant.getActorName() + " GP: " + merchant.getActorGold());

		// Both lists use the same callback so just create it here and set it later
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
									NumberDialog sellDialog = new NumberDialog(
											"How many " + item.getItem().getItemName() + " do you want to sell?",
											"Each " + item.getItem().getItemName() + " costs "
													+ item.getItem().getItemCost() + "gp");

									Optional<Integer> result = sellDialog.showAndWait();
									result.ifPresent(soldAmount -> {
										if (soldAmount > item.getItemCount())
											soldAmount = item.getItemCount();

										// Throw up another dialog making sure it's okay
										Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
										confirmationAlert.setTitle("Are you sure?");
										confirmationAlert.setContentText("You're about to sell " + soldAmount + " "
												+ item.getItem().getItemName() + "s");

										Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();
										if (confirmationResult.get() == ButtonType.OK) {

											final int cost = item.getItem().getItemCost() * soldAmount;
											if (merchant.getActorGold() >= cost) {
												merchant.modifyActorGold(-cost);
												player.modifyActorGold(cost);
												merchantInventory.add(item.getItem(), soldAmount);
												item.remove(soldAmount);
												refresh();
											}
										}
									});
								} else {
									// Throw up another dialog making sure it's okay
									Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
									confirmationAlert.setTitle("Are you sure?");
									confirmationAlert
											.setContentText("You're about to sell " + item.getItem().getItemName());

									Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();
									if (confirmationResult.get() == ButtonType.OK) {

										final int cost = item.getItem().getItemCost();
										if (merchant.getActorGold() >= cost) {
											merchant.modifyActorGold(-cost);
											player.modifyActorGold(cost);
											merchantInventory.add(item.getItem());
											item.remove(1);
											refresh();
										}
									}
								}
							});
						}
					}
				};

				return cell;
			}

		});

		merchantInventoryList.setCellFactory(new Callback<ListView<ItemSlot>, ListCell<ItemSlot>>() {

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
									NumberDialog buyDialog = new NumberDialog(
											"How many " + item.getItem().getItemName() + " do you want to buy?",
											"Each " + item.getItem().getItemName() + " costs "
													+ item.getItem().getItemCost() + "gp");

									Optional<Integer> result = buyDialog.showAndWait();
									result.ifPresent(boughtAmount -> {
										if (boughtAmount > item.getItemCount())
											boughtAmount = item.getItemCount();

										// Throw up another dialog making sure it's okay
										Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
										confirmationAlert.setTitle("Are you sure?");
										confirmationAlert.setContentText("You're about to buy " + boughtAmount + " "
												+ item.getItem().getItemName() + "s");

										Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();
										if (confirmationResult.get() == ButtonType.OK) {

											final int cost = item.getItem().getItemCost() * boughtAmount;
											if (player.getActorGold() >= cost) {
												merchant.modifyActorGold(cost);
												player.modifyActorGold(-cost);
												playerInventory.add(item.getItem(), boughtAmount);
												item.remove(boughtAmount);
												refresh();
											}
										}
									});
								} else {

									// Throw up another dialog making sure it's okay
									Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
									confirmationAlert.setTitle("Are you sure?");
									confirmationAlert
											.setContentText("You're about to buy " + item.getItem().getItemName());

									Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();
									if (confirmationResult.get() == ButtonType.OK) {

										final int cost = item.getItem().getItemCost();
										if (player.getActorGold() >= cost) {
											merchant.modifyActorGold(cost);
											player.modifyActorGold(-cost);
											playerInventory.add(item.getItem());
											item.remove(1);
											refresh();
										}
									}
								}
							});
						}
					}
				};

				return cell;
			}

		});

		playerControls.add(playerLabel, 0, 0);
		playerControls.add(playerInventoryList, 0, 1);

		merchantControls.add(merchantLabel, 0, 0);
		merchantControls.add(merchantInventoryList, 0, 1);
	}

	private void refresh() {
		playerInventoryList.refresh();
		merchantInventoryList.refresh();

		playerLabel.setText(player.getActorName() + " GP: " + player.getActorGold());
		merchantLabel.setText(merchant.getActorName() + " GP: " + merchant.getActorGold());

	}

}

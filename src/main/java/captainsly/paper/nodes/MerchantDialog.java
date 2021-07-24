package captainsly.paper.nodes;

import captainsly.paper.entities.Actor;
import captainsly.paper.mechanics.containers.Inventory;
import captainsly.paper.mechanics.containers.ItemSlot;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class MerchantDialog extends Alert {

	private Actor player, merchant;

	private BorderPane merchantPane;
	private GridPane playerControls, merchantControls;

	private ObservableList<ItemSlot> currentSelection;
	private Inventory playerInventory, merchantInventory;

	private int itemCost = 0;

	public MerchantDialog(Actor player, Actor merchant, String locationName) {
		super(AlertType.CONFIRMATION);
		this.setTitle(locationName + " Market");
		this.setHeaderText("BUY/SELL");
		this.player = player;
		this.merchant = merchant;
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
		ListView<ItemSlot> playerInventoryList = new ListView<ItemSlot>(playerInventory.getItemSlots());
		ListView<ItemSlot> merchantInventoryList = new ListView<ItemSlot>(merchantInventory.getItemSlots());

		playerInventoryList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		merchantInventoryList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// Buttons and Labels
		Label playerLabel = new Label(player.getActorName() + " GP: " + player.getActorGold());
		Label merchantLabel = new Label(merchant.getActorName() + " GP: " + merchant.getActorGold());
		Label totalCostLabel = new Label();

		Button transferToPlayer = new Button();
		Button transferToMerchant = new Button();

		transferToPlayer.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.CHEVRON_LEFT));
		transferToMerchant.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.CHEVRON_RIGHT));

		// Both lists use the same callback so just create it here and set it later
		Callback<ListView<ItemSlot>, ListCell<ItemSlot>> merchantCellFactory = new Callback<ListView<ItemSlot>, ListCell<ItemSlot>>() {

			@Override
			public ListCell<ItemSlot> call(ListView<ItemSlot> param) {
				ListCell<ItemSlot> cell = new ListCell<ItemSlot>() {
					@Override
					protected void updateItem(ItemSlot item, boolean empty) {
						super.updateItem(item, empty);
						if (empty || item.isEmpty()) {
							this.setText("");
							this.setTooltip(null);
						} else {
							this.setText(item.getItem().getItemName() + " | " + item.getItemCount());
							this.setTooltip(new Tooltip(item.getItem().getItemDesc()));
						}
					}
				};

				cell.itemProperty().addListener(new ChangeListener<ItemSlot>() {

					@Override
					public void changed(ObservableValue<? extends ItemSlot> observable, ItemSlot oldValue,
							ItemSlot newValue) {
					}

				});

				return cell;
			}

		};

		// TODO: Setup Buttons
		transferToPlayer.setOnAction(e ->

		{
			merchantInventoryList.refresh();
			playerInventoryList.refresh();
			currentSelection = merchantInventoryList.getSelectionModel().getSelectedItems();

			// // Calculate Price of the Items in the selection

			// for (ItemSlot slot : currentSelection) {
			// itemCost += slot.getItem().getItemCost();
			// System.out.println("price: " + itemCost);
			// }

			// totalCostLabel.setText("" + itemCost);

			// if (player.getNpcGold() >= itemCost) {
			// for (ItemSlot slot : currentSelection) {
			// // TODO: Take in account if the player can afford the item
			// if (slot.getItemCount() == 1) {
			// merchantInventoryList.getItems().remove(slot);
			// } else
			// merchantInventory.remove(slot.getItem(), 1);
			// playerInventory.add(slot.getItem());
			// player.increaseNpcGold(-itemCost);
			// }
			// }
		});

		transferToMerchant.setOnAction(e -> {
			currentSelection = playerInventoryList.getSelectionModel().getSelectedItems();

			// // Calculate Price of the Items in the selection
			// for (ItemSlot slot : currentSelection) {
			// itemCost += slot.getItem().getItemCost();
			// }

			// totalCostLabel.setText("" + itemCost);
			// if (merchant.getNpcGold() >= itemCost) {
			// for (ItemSlot slot : currentSelection) {
			// // TODO: Take in account if the merchant can afford the item
			// if (slot.getItemCount() == 1) {
			// playerInventoryList.getItems().remove(slot);
			// } else
			// playerInventory.remove(slot.getItem(), 1);
			// merchantInventory.add(slot.getItem());
			// merchant.increaseNpcGold(-itemCost);
			// }
			// }
		});

		playerInventoryList.setCellFactory(merchantCellFactory);
		merchantInventoryList.setCellFactory(merchantCellFactory);

		playerControls.add(playerLabel, 0, 0);
		playerControls.add(playerInventoryList, 0, 1);
		playerControls.add(transferToMerchant, 0, 2);
		playerControls.add(totalCostLabel, 0, 4);

		merchantControls.add(merchantLabel, 0, 0);
		merchantControls.add(merchantInventoryList, 0, 1);
		merchantControls.add(transferToPlayer, 0, 2);
	}

}

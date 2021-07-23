package captainsly.paper.nodes;

import java.util.Optional;

import captainsly.paper.entities.Player;
import captainsly.paper.entities.Stat;
import captainsly.paper.mechanics.containers.ItemSlot;
import captainsly.paper.mechanics.items.Item;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.Callback;

public class PlayerStatNode extends Region {

	private BorderPane characterPane;
	private GridPane characterStatGrid, characterEquipmentGrid;
	private ListView<ItemSlot> characterInventoryList;

	private Player player;

	public PlayerStatNode(Player player) {
		this.player = player;
		characterPane = new BorderPane();
		characterStatGrid = new GridPane();
		characterEquipmentGrid = new GridPane();

		characterInventoryList = new ListView<ItemSlot>();
		characterInventoryList.setItems(player.getActorInventory().getItemSlots());

		characterStatGrid.setHgap(10);
		characterStatGrid.setVgap(10);

		setupStats();
		setupInventory();
		setupEquipment();

		characterPane.setTop(characterStatGrid);
		characterPane.setCenter(characterInventoryList);
		characterPane.setBottom(characterEquipmentGrid);

		this.getChildren().add(characterPane);
	}

	private void setupStats() {
		Label playerName = new Label("NAME: " + player.getActorName());
		Label playerLevel = new Label("LEVEL: " + player.getActorStat(Stat.LEVEL));
		Label playerHp = new Label("HP: " + player.getActorStat(Stat.MAX_HP) + " / " + player.getActorStat(Stat.HP));
		Label playerMp = new Label("MP: " + player.getActorStat(Stat.MAX_MP) + " / " + player.getActorStat(Stat.MP));
		Label playerXp = new Label("XP: " + player.getActorStat(Stat.XP));
		Label playerAtk = new Label("ATK: " + player.getActorStat(Stat.ATK));
		Label playerDef = new Label("DEF: " + player.getActorStat(Stat.DEF));
		Label playerSpd = new Label("SPD: " + player.getActorStat(Stat.SPD));
		Label playerWis = new Label("WIS: " + player.getActorStat(Stat.WIS));

		playerXp.setTooltip(new Tooltip("To next level: " + player.toNextLevel()));

		// Setup tooltips
		characterStatGrid.add(playerName, 0, 0);
		characterStatGrid.add(playerLevel, 1, 0);
		characterStatGrid.add(playerXp, 2, 0);

		characterStatGrid.add(playerHp, 0, 1);
		characterStatGrid.add(playerMp, 1, 1);
		characterStatGrid.add(playerAtk, 0, 2);
		characterStatGrid.add(playerDef, 1, 2);
		characterStatGrid.add(playerSpd, 0, 3);
		characterStatGrid.add(playerWis, 1, 3);
	}

	private void setupInventory() {
		characterInventoryList.setCellFactory(new Callback<ListView<ItemSlot>, ListCell<ItemSlot>>() {

			@Override
			public ListCell<ItemSlot> call(ListView<ItemSlot> param) {
				ListCell<ItemSlot> cell = new ListCell<ItemSlot>() {
					@Override
					protected void updateItem(ItemSlot item, boolean empty) {
						super.updateItem(item, empty);
						if (empty || item.isEmpty()) {
							this.setText("");
							this.setTooltip(null);
							this.setContextMenu(null);
						} else {
							this.setText(item.getItem().getItemName() + "  |  " + item.getItemCount());
							this.setTooltip(new Tooltip("Item Type: " + item.getItem().getItemType().name() + "\n\n"
									+ item.getItem().getItemDesc() + "\n\nYou currently have: " + item.getItemCount()));

							ContextMenu slotContextMenu = new ContextMenu();
							Item slotItem = item.getItem();

							MenuItem slotMenuUse = new MenuItem("Use " + slotItem.getItemName());
							MenuItem slotMenuInfo = new MenuItem("Inspect " + slotItem.getItemName());
							MenuItem slotMenuToss = new MenuItem("Toss " + slotItem.getItemName());

							slotMenuUse.setOnAction(e -> {
								slotItem.onUse();
								item.remove(1);
								characterInventoryList.refresh();
							});

							slotMenuInfo.setOnAction(e -> {
								// TODO: Implement item inspection
							});

							slotMenuToss.setOnAction(e -> {
								Dialog<Integer> slotTossDialog = new Dialog<Integer>();
								slotTossDialog.setTitle("Toss how much " + slotItem.getItemName());
								slotTossDialog
										.setHeaderText("How many " + slotItem.getItemName() + " do you want to toss");
								slotTossDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
								
								Node okayButton = slotTossDialog.getDialogPane().lookupButton(ButtonType.OK);
								okayButton.setDisable(true);
								
								TextField contextText = new TextField();
								contextText.setPromptText("Item Amount");
								contextText.textProperty().addListener(new ChangeListener<String>() {

									@Override
									public void changed(ObservableValue<? extends String> observable, String oldValue,
											String newValue) {
										
										okayButton.setDisable(newValue.trim().isEmpty());
										if (!newValue.matches("\\d*")) {
											contextText.setText(newValue.replaceAll("[^\\d]", ""));
										}
										
										
									}
								});
								
								GridPane slotGrid = new GridPane();
								slotGrid.setHgap(10);
								slotGrid.setVgap(10);
								slotGrid.setPadding(new Insets(20, 150, 10, 10));
								
								slotGrid.add(new Label("Please input how many to toss"), 0,	0);
								slotGrid.add(contextText, 1, 0);
								
								slotTossDialog.getDialogPane().setContent(slotGrid);
								Platform.runLater(() -> contextText.requestFocus());
								
								slotTossDialog.setResultConverter(dialogButton -> {
									if (dialogButton == ButtonType.OK)
										return Integer.parseInt(contextText.getText());
									
									return null;
								});	
								
								Optional<Integer> result = slotTossDialog.showAndWait();
								
								result.ifPresent(throwAmount -> {
									System.out.println("Throwing away " + throwAmount + " " + slotItem.getItemName());
									item.remove(throwAmount);
								});
							});

							slotContextMenu.getItems().addAll(slotMenuUse, slotMenuInfo, slotMenuToss);
							this.setContextMenu(slotContextMenu);
							characterInventoryList.refresh();
						}

					}
				};

				return cell;
			}
		});

	}

	private void setupEquipment() {
		// TODO: Setup Equipment

	}

	public ListView<ItemSlot> getPlayerInventoryList() {
		return characterInventoryList;
	}

}

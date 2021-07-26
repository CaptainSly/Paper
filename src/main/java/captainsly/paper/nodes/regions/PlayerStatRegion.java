package captainsly.paper.nodes.regions;

import java.util.Optional;

import captainsly.paper.entities.Player;
import captainsly.paper.entities.stats.Stat;
import captainsly.paper.mechanics.containers.ItemSlot;
import captainsly.paper.mechanics.items.Item;
import captainsly.paper.mechanics.items.Item.ItemType;
import captainsly.paper.mechanics.items.equipment.Equipment.EquipmentType;
import captainsly.paper.nodes.EquipmentButton;
import captainsly.paper.nodes.dialogs.NumberDialog;
import captainsly.paper.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.Callback;

public class PlayerStatRegion extends Region {

	private BorderPane characterPane;
	private GridPane characterStatGrid, characterEquipmentGrid;
	private ListView<ItemSlot> characterInventoryList;

	private WorldRegion worldRegion;
	private Player player;

	private EquipmentButton[] equipmentSlots;

	private SimpleStringProperty playerLevelValue, playerXpValue, playerHpValue, playerMpValue, playerAtkValue,
			playerDefValue, playerSpdValue, playerWisValue, playerGoldValue;

	private Tooltip playerXpTooltip;

	public PlayerStatRegion(WorldRegion worldRegion) {
		this.worldRegion = worldRegion;
		this.player = worldRegion.getPlayer();
		player.setPlayerStatRegion(this);

		characterPane = new BorderPane();
		characterStatGrid = new GridPane();
		characterEquipmentGrid = new GridPane();

		equipmentSlots = new EquipmentButton[EquipmentType.values().length];
		for (int i = 0; i < EquipmentType.values().length; i++) {
			equipmentSlots[i] = new EquipmentButton(worldRegion, EquipmentType.values()[i]);
		}

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
		Label playerName = new Label("-=- " + player.getActorName() + "'s Stats -=-");
		Label playerLevel = new Label("LEVEL: ");
		Label playerHp = new Label("HP: ");
		Label playerMp = new Label("MP: ");
		Label playerXp = new Label("XP: ");
		Label playerAtk = new Label("ATK: ");
		Label playerDef = new Label("DEF: ");
		Label playerSpd = new Label("SPD: ");
		Label playerWis = new Label("WIS: ");
		Label playerGold = new Label("GP: ");

		playerLevelValue = new SimpleStringProperty();
		playerHpValue = new SimpleStringProperty();
		playerMpValue = new SimpleStringProperty();
		playerXpValue = new SimpleStringProperty();
		playerAtkValue = new SimpleStringProperty();
		playerDefValue = new SimpleStringProperty();
		playerSpdValue = new SimpleStringProperty();
		playerWisValue = new SimpleStringProperty();
		playerGoldValue = new SimpleStringProperty();

		playerHpValue.set("HP: " + player.getActorStat(Stat.HP) + "/" + player.getActorStat(Stat.MAX_HP));
		playerMpValue.set("MP: " + player.getActorStat(Stat.MP) + "/" + player.getActorStat(Stat.MAX_MP));

		playerLevelValue.set(Utils.writeStatString(Stat.LEVEL, player.getActorStat(Stat.LEVEL)));
		playerXpValue.set(Utils.writeStatString(Stat.XP, player.getActorStat(Stat.XP)));
		playerAtkValue.set(Utils.writeStatString(Stat.ATK, player.getActorStat(Stat.ATK)));
		playerDefValue.set(Utils.writeStatString(Stat.DEF, player.getActorStat(Stat.DEF)));
		playerSpdValue.set(Utils.writeStatString(Stat.SPD, player.getActorStat(Stat.SPD)));
		playerWisValue.set(Utils.writeStatString(Stat.WIS, player.getActorStat(Stat.WIS)));
		playerGoldValue.set("GP: " + player.getActorGold());

		playerXpTooltip = new Tooltip("Amount need for next level: " + player.toNextLevel());

		playerLevel.textProperty().bind(playerLevelValue);
		playerHp.textProperty().bind(playerHpValue);
		playerMp.textProperty().bind(playerMpValue);
		playerXp.textProperty().bind(playerXpValue);
		playerAtk.textProperty().bind(playerAtkValue);
		playerDef.textProperty().bind(playerDefValue);
		playerSpd.textProperty().bind(playerSpdValue);
		playerWis.textProperty().bind(playerWisValue);
		playerGold.textProperty().bind(playerGoldValue);

		playerXp.setTooltip(playerXpTooltip);

		// Setup tooltips
		characterStatGrid.setHgap(5);
		characterStatGrid.setVgap(5);
		characterStatGrid.setPadding(new Insets(5, 5, 5, 5));

		characterStatGrid.add(playerName, 0, 0);

		characterStatGrid.add(playerLevel, 0, 1);
		characterStatGrid.add(playerGold, 1, 1);
		characterStatGrid.add(playerXp, 2, 1);

		characterStatGrid.add(playerHp, 0, 2);
		characterStatGrid.add(playerMp, 1, 2);
		characterStatGrid.add(playerAtk, 0, 3);
		characterStatGrid.add(playerDef, 1, 3);
		characterStatGrid.add(playerSpd, 0, 4);
		characterStatGrid.add(playerWis, 1, 4);
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
								NumberDialog slotTossDialog = new NumberDialog(
										"Toss how much " + slotItem.getItemName(),
										"How many " + slotItem.getItemName() + " do you want to toss");

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

				cell.setOnDragDetected(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
						ClipboardContent content = new ClipboardContent();

						if (!cell.isEmpty()) {
							if (!cell.getItem().isEmpty()) {
								if (cell.getItem().getItem().getItemType() == ItemType.EQUIPMENT) {
									// Since the drag operation was detected on the inventory, we must grab the
									// entire Equipment item
									content.putString(cell.getItem().getItem().getItemId());
									db.setContent(content);
									event.consume();
								}
							}
						}
					}

				});

				cell.setOnDragDone(new EventHandler<DragEvent>() {

					@Override
					public void handle(DragEvent event) {
						if (event.getTransferMode() == TransferMode.MOVE) {
							cell.getItem().remove(1);
							characterInventoryList.refresh();
						}

						event.consume();
					}
				});

				return cell;
			}
		});

	}

	private void setupEquipment() {
		// TODO: Setup Equipment

		characterEquipmentGrid.setHgap(5);
		characterEquipmentGrid.setVgap(5);
		characterEquipmentGrid.setPadding(new Insets(5, 5, 5, 5));

		characterEquipmentGrid.add(equipmentSlots[EquipmentType.HEAD.ordinal()], 1, 0);
		characterEquipmentGrid.add(equipmentSlots[EquipmentType.NECK.ordinal()], 2, 0);

		characterEquipmentGrid.add(equipmentSlots[EquipmentType.HANDS.ordinal()], 0, 1);
		characterEquipmentGrid.add(equipmentSlots[EquipmentType.CHEST.ordinal()], 1, 1);

		characterEquipmentGrid.add(equipmentSlots[EquipmentType.LEGS.ordinal()], 1, 2);
		characterEquipmentGrid.add(equipmentSlots[EquipmentType.FEET.ordinal()], 2, 2);

		characterEquipmentGrid.add(equipmentSlots[EquipmentType.WEAPON.ordinal()], 0, 3);
		characterEquipmentGrid.add(equipmentSlots[EquipmentType.SHEILD.ordinal()], 1, 3);
	}

	public WorldRegion getWorldNode() {
		return worldRegion;
	}

	public SimpleStringProperty getPlayerLevelValue() {
		return playerLevelValue;
	}

	public SimpleStringProperty getPlayerXpValue() {
		return playerXpValue;
	}

	public SimpleStringProperty getPlayerHpValue() {
		return playerHpValue;
	}

	public SimpleStringProperty getPlayerMpValue() {
		return playerMpValue;
	}

	public SimpleStringProperty getPlayerAtkValue() {
		return playerAtkValue;
	}

	public SimpleStringProperty getPlayerDefValue() {
		return playerDefValue;
	}

	public SimpleStringProperty getPlayerSpdValue() {
		return playerSpdValue;
	}

	public SimpleStringProperty getPlayerWisValue() {
		return playerWisValue;
	}

	public SimpleStringProperty getPlayerGoldValue() {
		return playerGoldValue;
	}

	public SimpleStringProperty getPropertyByStat(Stat stat) {
		switch (stat) {
			case ATK:
				return playerAtkValue;
			case DEF:
				return playerDefValue;
			case HP:
				return playerHpValue;
			case LEVEL:
				return playerLevelValue;
			case MAX_HP:
				return playerHpValue;
			case MAX_MP:
				return playerMpValue;
			case MP:
				return playerMpValue;
			case SPD:
				return playerSpdValue;
			case WIS:
				return playerWisValue;
			case XP:
				return playerXpValue;
			default:
				return null;

		}
	}

	public Tooltip getPlayerXpTooltip() {
		return playerXpTooltip;
	}

	public ListView<ItemSlot> getCharacterInventoryList() {
		return characterInventoryList;
	}

}

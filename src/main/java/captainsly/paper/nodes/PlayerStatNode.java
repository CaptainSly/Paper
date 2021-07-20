package captainsly.paper.nodes;

import captainsly.paper.entities.Player;
import captainsly.paper.entities.Stat;
import captainsly.paper.mechanics.ItemSlot;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
						} else {
							this.setText(item.getItem().getItemName() + "  |  " + item.getItemCount());
							this.setTooltip(new Tooltip("Item Type: " + item.getItem().getItemType().name() + "\n\n"
									+ item.getItem().getItemDesc() + "\n\nYou currently have: " + item.getItemCount()));
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

package io.azraein.paper.nodes.item_slots_nodes;

import io.azraein.paper.PaperApp;
import io.azraein.paper.core.items.PotionItem;
import io.azraein.paper.core.items.container.ItemSlot;
import io.azraein.paper.core.items.equipment.Equipment;
import io.azraein.paper.core.system.Registry;
import io.azraein.paper.nodes.PaperNode;
import io.azraein.paper.nodes.tooltips.ItemTooltip;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class PlayerItemSlotNode extends PaperNode {

	private String itemId;

	public PlayerItemSlotNode(PaperApp paperApp, ItemSlot itemSlot) {
		super(paperApp);
		itemId = itemSlot.getSlotItemId();

		StackPane itemSlotStack = new StackPane();
		Button itemSlotBtn = new Button("");
		Image itemSlotImage = itemId.contains("test") || itemId.contains("") ? new Image("defaultTexture.png")
				: new Image(itemId + ".png");
		Label itemSlotCountLbl = new Label("");

		var item = Registry.getItem(itemId);
		var player = paperApp.playerProperty().get();

		// Setup Nodes
		StackPane.setAlignment(itemSlotBtn, Pos.CENTER);
		StackPane.setAlignment(itemSlotCountLbl, Pos.BOTTOM_RIGHT);

		itemSlotBtn.setGraphic(new ImageView(itemSlotImage));
		itemSlotBtn.setTooltip(new ItemTooltip(itemId));
		itemSlotBtn.setOnAction(e -> {
			// Using an Item
			if (item instanceof Equipment) {
				player.equipEntityEquipment((Equipment) item);
			} else if (item instanceof PotionItem) {
				((PotionItem) item).onConsume(player);
			}
		});

		itemSlotCountLbl.setFont(Font.font(18));
		itemSlotCountLbl.setStyle("-fx-stroke: white; -fx-stroke-width: 5px;");
		itemSlotCountLbl.setText("" + itemSlot.getSlotCount());

		itemSlot.slotItemCountProperty().addListener((obs, oldValue, newValue) -> {

			if (newValue != null)
				itemSlotCountLbl.setText("" + newValue);

		});

		// Context Menus
		ContextMenu itemSlotContext = new ContextMenu();
		MenuItem itemSlotUseItem = new MenuItem("Use " + item.getItemName());
		MenuItem itemSlotInspectItem = new MenuItem("Inspect " + item.getItemName());
		MenuItem itemSlotDropItem = new MenuItem("Drop " + item.getItemName());

		itemSlotUseItem.setOnAction(e -> itemSlotBtn.fire());
		itemSlotDropItem.setOnAction(e -> {
			player.getEntityInventory().removeItem(itemId, 1);
		});

		itemSlotInspectItem.setOnAction(e -> {
		});

		itemSlotContext.getItems().addAll(itemSlotUseItem, itemSlotInspectItem, itemSlotDropItem);

		// Set final settings, and add children to stack
		itemSlotBtn.setContextMenu(itemSlotContext);
		itemSlotStack.getChildren().addAll(itemSlotBtn, itemSlotCountLbl);

		setContent(itemSlotStack);
	}

	public String getItemSlotItemId() {
		return itemId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PlayerItemSlotNode) {

			PlayerItemSlotNode node = (PlayerItemSlotNode) obj;
			return this.getItemSlotItemId().equals(node.getItemSlotItemId());

		} else
			return false;
	}

}

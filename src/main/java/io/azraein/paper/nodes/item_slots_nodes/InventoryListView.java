package io.azraein.paper.nodes.item_slots_nodes;

import io.azraein.paper.PaperApp;
import io.azraein.paper.core.entities.Entity;
import io.azraein.paper.core.items.container.ItemSlot;
import io.azraein.paper.core.system.Registry;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class InventoryListView extends ListView<ItemSlot> {

	public InventoryListView(PaperApp paperApp, Entity entity) {
		this.setCellFactory(new Callback<ListView<ItemSlot>, ListCell<ItemSlot>>() {

			@Override
			public ListCell<ItemSlot> call(ListView<ItemSlot> param) {
				return new ListCell<ItemSlot>() {

					@Override
					protected void updateItem(ItemSlot slot, boolean empty) {
						super.updateItem(slot, empty);

						if (slot != null) {
							var item = Registry.getItem(slot.getSlotItemId());
							if (item != null) {
								this.setGraphic(
										new ImageView(item.getItemId().contains("test") || item.getItemId().contains("")
												? new Image("defaultTexture.png")
												: new Image(item.getItemId() + ".png")));

								if (slot.getSlotCount() > 1)
									this.setText(item.getItemName() + " (" + slot.getSlotCount() + ") | "
											+ item.getSellPrice() + "G");
								else
									this.setText(item.getItemName() + " | " + item.getSellPrice() + "G");

							} else {
								this.setGraphic(null);
								this.setText("");
							}

						}

					}
				};
			}

		});
		
		this.getItems().clear();
		this.setItems(entity.getEntityInventory().getItemSlots());
	}

}

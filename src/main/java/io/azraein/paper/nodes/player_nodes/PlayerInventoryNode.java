package io.azraein.paper.nodes.player_nodes;

import java.util.List;

import org.tinylog.Logger;

import io.azraein.paper.PaperApp;
import io.azraein.paper.core.items.container.Inventory;
import io.azraein.paper.core.items.container.ItemSlot;
import io.azraein.paper.nodes.PaperNode;
import io.azraein.paper.nodes.item_slots_nodes.PlayerItemSlotNode;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;

public class PlayerInventoryNode extends PaperNode {

	public PlayerInventoryNode(PaperApp paperApp) {
		super(paperApp);

		FlowPane inventoryFlow = new FlowPane();
		inventoryFlow.setPadding(new Insets(10, 5, 10, 5));
		inventoryFlow.setHgap(10);
		inventoryFlow.setVgap(10);

		paperApp.playerProperty().addListener((obs, oldValue, newValue) -> {

			if (newValue != null) {
				// A new player was created, lets assign a new listener to the players inventory
				Inventory i = paperApp.playerProperty().get().getEntityInventory();

				List<ItemSlot> dummyList = i.getItemSlots();
				i.getItemSlots().clear();

				i.getItemSlots().addListener(new ListChangeListener<ItemSlot>() {

					@Override
					public void onChanged(Change<? extends ItemSlot> c) {

						while (c.next()) {
							Logger.debug("There was an inventory change");

							if (c.wasAdded()) {
								Logger.debug("The change was a slot was added");
								var addedSlots = c.getAddedSubList();
								for (int i = 0; i < c.getAddedSize(); i++) {
									var slot = addedSlots.get(i);

									if (!slot.getSlotItemId().isBlank()) {

										PlayerItemSlotNode iNode = new PlayerItemSlotNode(paperApp, slot);
										Logger.debug("Checking to see if the inventoryFlow contains the child");

										if (!inventoryFlow.getChildren().contains(iNode)) {
											inventoryFlow.getChildren().add(iNode);
										}
									}
								}
							} else if (c.wasRemoved()) {
								Logger.debug("The change was a slot was removed");
								var itemSlots = c.getRemoved();

								for (ItemSlot slot : itemSlots) {
									if (!slot.getSlotItemId().isBlank() && slot != null) {
										PlayerItemSlotNode iNode = new PlayerItemSlotNode(paperApp, slot);
										if (inventoryFlow.getChildren().contains(iNode))
										inventoryFlow.getChildren().remove(iNode);
									}
								}
							}
						}

					}
				});

				i.getItemSlots().addAll(dummyList);
			}

		});

		getChildren().add(inventoryFlow);
	}

}

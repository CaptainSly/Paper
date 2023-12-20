package io.azraein.paper.nodes.player_nodes;

import io.azraein.paper.PaperApp;
import io.azraein.paper.core.items.equipment.EquipType;
import io.azraein.paper.nodes.PaperNode;
import io.azraein.paper.nodes.tooltips.EquipmentTooltip;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class PlayerEquipmentNode extends PaperNode {

	private Button[] equipBtns;

	public PlayerEquipmentNode(PaperApp paperApp) {
		super(paperApp);
		GridPane rootGrid = new GridPane();
		rootGrid.setPadding(new Insets(10));
		rootGrid.setHgap(10);
		rootGrid.setVgap(10);

		equipBtns = new Button[EquipType.values().length];
		for (EquipType equipType : EquipType.values()) {
			equipBtns[equipType.ordinal()] = new Button();
			equipBtns[equipType.ordinal()]
					.setGraphic(new ImageView(new Image(equipType.name().toLowerCase() + "_slot.png")));
			equipBtns[equipType.ordinal()].setOnAction(e -> {
				// Make sure there is Equipment in the slot first.
				var player = paperApp.playerProperty().get();

				if (player.getEntityEquipment(equipType) != null)
					player.unequipEntityEquipment(equipType);
			});
			
		}

		paperApp.playerProperty().addListener((obs, oldValue, newValue) -> {

			if (newValue != null) {
				// New Player was assigned. Add a listener to the Equipment
				for (EquipType equipType : EquipType.values()) {
					newValue.entityEquipmentProperty(equipType)
							.addListener((observable, oldEquipment, newEquipment) -> {
								if (newEquipment != null) {
									ImageView im = new ImageView();
									if (newEquipment.getItemId().contains("test"))
										im.setImage(new Image("defaultTexture.png"));
									else
										im.setImage(new Image(newEquipment.getItemId() + ".png"));

									equipBtns[equipType.ordinal()].setGraphic(im);
									equipBtns[equipType.ordinal()]
											.setTooltip(new EquipmentTooltip(newEquipment.getItemId()));
								} else {
									equipBtns[equipType.ordinal()]
											.setTooltip(new Tooltip(equipType.name() + " | EMPTY"));
									equipBtns[equipType.ordinal()].setGraphic(
											new ImageView(new Image(equipType.name().toLowerCase() + "_slot.png")));
								}

							});
				}

			}

		});

		Button weaponBtn = new Button();
		weaponBtn.setGraphic(new ImageView(new Image("weapon_slot.png")));
		weaponBtn.setOnAction(e -> {
			// TODO: Weapon Equipping
		});

		Button shieldBtn = new Button();
		shieldBtn.setGraphic(new ImageView(new Image("shield_slot.png")));
		shieldBtn.setOnAction(e -> {
			// TODO: Shield Equipping
		});

		rootGrid.add(equipBtns[EquipType.HEAD.ordinal()], 1, 0);
		rootGrid.add(equipBtns[EquipType.SHOULDERS.ordinal()], 0, 1);
		rootGrid.add(equipBtns[EquipType.CHEST.ordinal()], 1, 1);
		rootGrid.add(shieldBtn, 2, 1);
		rootGrid.add(equipBtns[EquipType.HANDS.ordinal()], 0, 2);
		rootGrid.add(equipBtns[EquipType.LEGS.ordinal()], 1, 2);
		rootGrid.add(weaponBtn, 2, 2);
		rootGrid.add(equipBtns[EquipType.FEET.ordinal()], 1, 3);

		getChildren().add(rootGrid);
	}

	public Button[] getEquipmentBtns() {
		return equipBtns;
	}

}

package captainsly.paper.nodes;

import captainsly.paper.entities.Player;
import captainsly.paper.mechanics.Registry;
import captainsly.paper.mechanics.items.equipment.Equipment;
import captainsly.paper.mechanics.items.equipment.Equipment.EquipmentType;
import captainsly.paper.mechanics.items.equipment.EquipmentStat;
import captainsly.paper.nodes.regions.WorldRegion;
import captainsly.paper.utils.Utils;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class EquipmentButton extends Button {

	private Equipment currentEquipment;
	private EquipmentType equipmentType;
	private WorldRegion worldRegion;
	private Player player;
	public static final DataFormat equipmentFormat = new DataFormat("equipment");

	public EquipmentButton(WorldRegion worldNode, EquipmentType equipmentType) {
		super(equipmentType.name().substring(0, 1));
		this.worldRegion = worldNode;
		this.player = worldNode.getPlayer();
		this.equipmentType = equipmentType;
		setToolTip();

		EquipmentButton thiss = this;

		this.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if (event.getGestureSource() != thiss && event.getDragboard().hasString()) {
					event.acceptTransferModes(TransferMode.MOVE);
				}
			}

		});

		this.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;

				if (db.hasString()) {
					Equipment equipment = Registry.equipmentRegistry.get(db.getString());
					if (equipment.getEquipmentType() == equipmentType) {
						setEquipment(equipment);
						success = true;
					}
				}

				event.setDropCompleted(success);
				event.consume();
			}

		});

		this.setOnAction(e -> {
			if (currentEquipment != null) {
				player.getActorInventory().add(currentEquipment);
				removeEquipment();
			}
		});

	}

	private void setToolTip() {
		setToolTip("This slot holds: " + Utils.toNormalCase(equipmentType.name()));
	}

	public void setEquipment(Equipment equipment) {
		this.currentEquipment = equipment;
		player.setEquipmentInSlot(equipmentType, equipment);
		if (equipment != null) {
			setToolTip("It's a " + equipment.getItemName() + "\nATK: "
					+ equipment.getEquipmentStats()[EquipmentStat.ATK.ordinal()] + "\nDEF: "
					+ equipment.getEquipmentStats()[EquipmentStat.DEF.ordinal()] + "\nSPD: "
					+ equipment.getEquipmentStats()[EquipmentStat.SPD.ordinal()]);
		}
	}

	public void removeEquipment() {
		this.currentEquipment = null;
		player.removeEquipmentFromSlot(equipmentType);
		setToolTip();
		worldRegion.getPlayerStatNode().getCharacterInventoryList().refresh();
	}

	private void setToolTip(String tooltip) {

		this.setTooltip(new Tooltip(tooltip));
	}

	public Equipment getCurrentEquipment() {
		return currentEquipment;
	}

	public EquipmentType getEquipmentType() {
		return equipmentType;
	}

	public WorldRegion getWorldNode() {
		return worldRegion;
	}

}

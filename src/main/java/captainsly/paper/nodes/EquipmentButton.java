package captainsly.paper.nodes;

import captainsly.paper.entities.Player;
import captainsly.paper.mechanics.items.Equipment;
import captainsly.paper.mechanics.items.Equipment.EquipmentType;
import captainsly.paper.utils.Utils;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class EquipmentButton extends Button {

	private Equipment currentEquipment;
	private EquipmentType equipmentType;
	private WorldNode worldNode;
	private Player player;
	public static final DataFormat equipmentFormat = new DataFormat("equipment");

	public EquipmentButton(WorldNode worldNode, EquipmentType equipmentType) {
		super(equipmentType.name().substring(0, 1));
		this.worldNode = worldNode;
		this.player = worldNode.getPlayer();
		this.equipmentType = equipmentType;
		setToolTip();

		EquipmentButton thiss = this;
		this.setOnDragDetected(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (currentEquipment == null)
					return;

				Dragboard db = thiss.startDragAndDrop(TransferMode.MOVE);
				ClipboardContent content = new ClipboardContent();
				content.put(equipmentFormat, currentEquipment);
				db.setContent(content);

				event.consume();
			}

		});

		this.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {

				if (event.getGestureSource() != this && event.getDragboard().hasContent(equipmentFormat)) {
					Equipment equip = (Equipment) event.getDragboard().getContent(equipmentFormat);
					if (thiss.getEquipmentType().equals(equip.getEquipmentType()))
						event.acceptTransferModes(TransferMode.MOVE);
				}

			}

		});

		this.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (db.hasContent(equipmentFormat)) {
					Equipment equipment = (Equipment) db.getContent(equipmentFormat);
					thiss.setEquipment(equipment);
					success = true;
				}

				event.setDropCompleted(success);
				event.consume();
			}
		});

	}

	private void setToolTip() {

		this.setTooltip(new Tooltip("This slot holds: " + Utils.toNormalCase(equipmentType.name())));
	}

	public void setEquipment(Equipment equipment) {
		this.currentEquipment = equipment;
		// TODO: Change player stats on equip
		player.setEquipmentInSlot(equipmentType, equipment);

	}

	public Equipment getCurrentEquipment() {
		return currentEquipment;
	}

	public EquipmentType getEquipmentType() {
		return equipmentType;
	}

	public WorldNode getWorldNode() {
		return worldNode;
	}

}

package io.azraein.paper.nodes.tooltips;

import io.azraein.paper.core.items.equipment.Equipment;
import io.azraein.paper.core.system.Registry;
import io.azraein.paper.core.system.Utils;
import javafx.scene.control.Tooltip;

public class EquipmentTooltip extends Tooltip {

	private Equipment tooltipEquipment;

	public EquipmentTooltip(String equipmentId) {
		tooltipEquipment = (Equipment) Registry.getItem(equipmentId);

		// TODO: Equipment Statistics
		
		String equipTypeName = Utils.toNormalCase(tooltipEquipment.getEquipmentEquipType().name());

		String skillType = "";
		if (tooltipEquipment.getEquipmentSkillType() != null)
			skillType = "[ " + Utils.toNormalCase(tooltipEquipment.getEquipmentSkillType().name()) + " ( +"
					+ tooltipEquipment.getEquipmentSkillBonus() + " )" + " ]";

		String equipmentTooltip = "~=~=~ " + tooltipEquipment.getItemName() + " | " + equipTypeName + " ~=~=~\n"
				+ skillType + "\n\n" + tooltipEquipment.getItemDescription();

		this.setText(equipmentTooltip);
	}

	public Equipment getTooltipEquipment() {
		return tooltipEquipment;
	}

}

package captainsly.paper.entities;

import captainsly.paper.mechanics.items.Equipment;
import captainsly.paper.mechanics.items.Equipment.EquipmentType;

public class Player extends Actor {

	private Equipment[] equipmentSlots;

	public Player() {
		super("actorPlayer", "Azraein");
		
		setActorStat(Stat.LEVEL, 1);
		setActorStat(Stat.MAX_HP, 100);
		setActorStat(Stat.MAX_MP, 50);
		setActorStat(Stat.HP, 100);
		setActorStat(Stat.MP, 50);
		setActorStat(Stat.ATK, 2);
		setActorStat(Stat.DEF, 2);
		setActorStat(Stat.SPD, 2);
		setActorStat(Stat.WIS, 2);

		equipmentSlots = new Equipment[EquipmentType.values().length];
	}

	public Equipment getEquipmentFromSlot(EquipmentType type) {
		return equipmentSlots[type.ordinal()];
	}

	public void setEquipmentInSlot(EquipmentType type, Equipment equipment) {
		equipmentSlots[type.ordinal()] = equipment;
	}

}

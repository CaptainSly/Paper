package captainsly.paper.entities;

import captainsly.paper.entities.stats.Stat;
import captainsly.paper.mechanics.items.Equipment;
import captainsly.paper.mechanics.items.Equipment.EquipmentType;
import captainsly.paper.mechanics.items.EquipmentStat;
import captainsly.paper.nodes.regions.PlayerStatRegion;
import captainsly.paper.utils.Utils;

public class Player extends Actor {

	private PlayerStatRegion playerStats;
	private Equipment[] equipmentSlots;

	public Player() {
		super("actorPlayer", "Azraein");

		setActorStat(Stat.LEVEL, 1);
		setActorStat(Stat.MAX_HP, 100);
		setActorStat(Stat.MAX_MP, 50);
		setActorStat(Stat.XP, 0);
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

	public void modifyXp(int xpValue) {
		modifyActorStat(Stat.XP, xpValue);
		playerStats.getPlayerXpValue().setValue(Utils.writeStatString(Stat.XP, getActorStat(Stat.XP)));
		if (getActorStat(Stat.XP) == toNextLevel()) {
			onLevelUp();
		}
	}

	public void setEquipmentInSlot(EquipmentType type, Equipment equipment) {
		equipmentSlots[type.ordinal()] = equipment;
		modifyActorStat(Stat.ATK, equipment.getStat(EquipmentStat.ATK));
		modifyActorStat(Stat.DEF, equipment.getStat(EquipmentStat.DEF));
		modifyActorStat(Stat.SPD, equipment.getStat(EquipmentStat.SPD));
	}

	public void removeEquipmentFromSlot(EquipmentType equipmentType) {
		modifyActorStat(Stat.ATK, -equipmentSlots[equipmentType.ordinal()].getStat(EquipmentStat.ATK));
		modifyActorStat(Stat.DEF, -equipmentSlots[equipmentType.ordinal()].getStat(EquipmentStat.DEF));
		modifyActorStat(Stat.SPD, -equipmentSlots[equipmentType.ordinal()].getStat(EquipmentStat.SPD));
		equipmentSlots[equipmentType.ordinal()] = null;
	}

	public void setPlayerStatRegion(PlayerStatRegion playerStats) {
		this.playerStats = playerStats;
	}

	@Override
	public void modifyActorGold(int goldValue) {
		super.modifyActorGold(goldValue);
		playerStats.getPlayerGoldValue().set("GP: " + getActorGold());
	}

	private void onLevelUp() {
		modifyActorStat(Stat.LEVEL, 1);
		playerStats.getPlayerLevelValue().set(Utils.writeStatString(Stat.LEVEL, getActorStat(Stat.LEVEL)));
		playerStats.getPlayerXpTooltip().setText("To next level: " + toNextLevel());
		// TODO: Show Up Level up Dialog

	}

}

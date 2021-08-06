package captainsly.paper.entities;

import captainsly.paper.entities.stats.Stat;
import captainsly.paper.mechanics.items.equipment.Equipment;
import captainsly.paper.mechanics.items.equipment.Equipment.EquipmentType;
import captainsly.paper.mechanics.nodes.alerts.LevelUpAlert;
import captainsly.paper.mechanics.nodes.regions.PlayerStatRegion;
import captainsly.paper.mechanics.nodes.regions.WorldRegion;
import captainsly.paper.mechanics.items.equipment.EquipmentStat;
import captainsly.paper.utils.Utils;

public class Player extends Actor {

	private PlayerStatRegion playerStats;
	private WorldRegion worldRegion;
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
		super.modifyActorStat(Stat.XP, xpValue);
		playerStats.getPlayerXpValue().setValue(Utils.writeStatString(Stat.XP, getActorStat(Stat.XP)));
		playerStats.getPlayerXpTooltip()
				.setText("Amount needed for next level: " + (toNextLevel() - getActorStat(Stat.XP)));
		worldRegion.write("You were awarded with " + xpValue + " experience!\n");
		if (getActorStat(Stat.XP) >= toNextLevel()) {
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

	public void setWorldRegion(WorldRegion worldRegion) {
		this.worldRegion = worldRegion;
	}

	@Override
	public void modifyActorGold(int goldValue) {
		super.modifyActorGold(goldValue);
		playerStats.getPlayerGoldValue().set("GP: " + getActorGold());
	}

	@Override
	public void modifyActorStat(Stat stat, int statIncrease) {
		super.modifyActorStat(stat, statIncrease);
		if (stat.equals(Stat.MAX_HP) || stat.equals(Stat.HP)) {
			super.modifyActorStat(Stat.HP, statIncrease);
			playerStats.getPropertyByStat(stat).set("HP: " + getActorStat(Stat.MAX_HP) + "/" + getActorStat(Stat.HP));
		} else if (stat.equals(Stat.MAX_MP) || stat.equals(Stat.MP)) {
			super.modifyActorStat(Stat.MP, statIncrease);
			playerStats.getPropertyByStat(stat).set("MP: " + getActorStat(Stat.MAX_MP) + "/" + getActorStat(Stat.MP));
		} else
			playerStats.getPropertyByStat(stat).set(Utils.writeStatString(stat, getActorStat(stat)));
	}

	private void onLevelUp() {
		modifyActorStat(Stat.LEVEL, 1);
		playerStats.getPlayerLevelValue().set(Utils.writeStatString(Stat.LEVEL, getActorStat(Stat.LEVEL)));
		playerStats.getPlayerXpTooltip().setText("Amount needed for next level: " + toNextLevel());
		worldRegion.write("LEVEL UP!");

		LevelUpAlert levelup = new LevelUpAlert(this);
		levelup.showDialog();

		if (getActorStat(Stat.XP) >= toNextLevel())
			onLevelUp();
	}

}

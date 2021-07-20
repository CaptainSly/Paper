package captainsly.paper.entities;

import captainsly.paper.mechanics.Equipment;

public class Player extends Actor {

	public enum EquipmentSlots {
		HEAD, NECK, CHEST, ARMS, LEGS, HAND_L, HAND_R; 
	}
	
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
	}
	
}

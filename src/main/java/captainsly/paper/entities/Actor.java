package captainsly.paper.entities;

import captainsly.paper.entities.stats.CharacterClass;
import captainsly.paper.entities.stats.CharacterRace;
import captainsly.paper.entities.stats.Faction;
import captainsly.paper.entities.stats.Skill;
import captainsly.paper.entities.stats.Stat;
import captainsly.paper.mechanics.containers.Inventory;

public abstract class Actor {

	protected String actorId, actorName;
	protected CharacterRace actorRace;
	protected CharacterClass actorClass;
	protected Faction actorFaction;
	protected Inventory actorInventory;
	protected int actorGold;

	// NPC Stats
	protected int[] actorStats;
	protected int[] actorSkills;

	public Actor(String actorId, String actorName) {
		this.actorId = actorId;
		this.actorName = actorName;

		actorInventory = new Inventory();
		actorStats = new int[Stat.values().length];
		actorSkills = new int[Skill.values().length];
	}

	public void setActorStat(Stat stat, int statIncrease) {
		actorStats[stat.ordinal()] = statIncrease;
	}

	public void modifyActorStat(Stat stat, int statIncrease) {
		actorStats[stat.ordinal()] += statIncrease;
	}

	public void setActorSkill(Skill skill, int statIncrease) {
		actorSkills[skill.ordinal()] = statIncrease;
	}

	public void modifySkill(Skill skill, int statIncrease) {
		actorSkills[skill.ordinal()] += statIncrease;
	}

	public void modifyActorGold(int gold) {
		actorGold += gold;
	}

	public String getActorId() {
		return actorId;
	}

	public String getActorName() {
		return actorName;
	}

	public Inventory getActorInventory() {
		return actorInventory;
	}

	public CharacterRace getActorRace() {
		return actorRace;
	}

	public CharacterClass getActorCharacterClass() {
		return actorClass;
	}

	public Faction getActorFaction() {
		return actorFaction;
	}

	public int getActorGold() {
		return actorGold;
	}

	public int getActorStat(Stat stat) {
		return actorStats[stat.ordinal()];
	}

	public int getActorSkill(Skill skill) {
		return actorSkills[skill.ordinal()];
	}

	public int toNextLevel() {
		return (getActorStat(Stat.LEVEL) + (getActorStat(Stat.LEVEL) + 1)) * 75;
	}

	public int[] getActorSkills() {
		return actorSkills;
	}

	public int[] getActorStats() {
		return actorStats;
	}

}

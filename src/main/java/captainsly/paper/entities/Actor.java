package captainsly.paper.entities;

import captainsly.paper.mechanics.containers.Inventory;

public abstract class Actor {

	protected String actorId, actorName;
	protected Inventory actorInventory;
	protected int actorGold;

	// NPC Stats
	protected int[] actorStats;

	public Actor(String actorId, String actorName) {
		this.actorId = actorId;
		this.actorName = actorName;

		actorInventory = new Inventory();
		actorStats = new int[Stat.values().length];
	}

	public void setActorStat(Stat stat, int statIncrease) {
		actorStats[stat.ordinal()] = statIncrease;
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

	public int getActorGold() {
		return actorGold;
	}

	public int getActorStat(Stat stat) {
		return actorStats[stat.ordinal()];
	}

	public int[] getActorStats() {
		return actorStats;
	}

	public int toNextLevel() {
		int level = getActorStat(Stat.LEVEL);
		return (level + (level + 1)) * 30;
	}

}

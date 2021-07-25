package captainsly.paper.entities;

public class Npc extends Actor {

	public enum Occupation {
		SHOP_KEEP, SMITHY, GUARD, NPC,
	}

	private Occupation npcOccupation;

	public Npc(String actorId, String actorName, Occupation npcOccupation) {
		super(actorId, actorName);
		this.npcOccupation = npcOccupation;
	}

	public Occupation getNpcOccupation() {
		return npcOccupation;
	}

}

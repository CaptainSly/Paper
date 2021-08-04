package captainsly.paper.entities.stats;

import java.util.List;

public class Faction {

	private String factionId, factionName, factionDescription;

	private List<Faction> aggroFaction, neutralFaction, positiveFaction;

	public String getFactionId() {
		return factionId;
	}

	public String getFactionName() {
		return factionName;
	}

	public String getFactionDescription() {
		return factionDescription;
	}

	public List<Faction> getAggroFaction() {
		return aggroFaction;
	}

	public List<Faction> getNeutralFaction() {
		return neutralFaction;
	}

	public List<Faction> getPositiveFaction() {
		return positiveFaction;
	}

}

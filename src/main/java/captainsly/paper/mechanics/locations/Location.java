package captainsly.paper.mechanics.locations;

import java.util.ArrayList;
import java.util.List;

import captainsly.paper.entities.Npc;
import captainsly.paper.mechanics.locations.actions.Action;

public class Location {

	private String locationId, locationName, locationDesc;
	private Location[] neighborLocations;

	private List<Action> actionList;
	private List<Npc> npcList;

	public enum Direction {
		NORTH, SOUTH, EAST, WEST;

		public Direction opposite() {
			switch (this) {
				case EAST:
					return WEST;
				case NORTH:
					return SOUTH;
				case SOUTH:
					return NORTH;
				case WEST:
				default:
					return EAST;

			}
		}
	}

	public Location(String locationId, String locationName, String locationDesc) {
		this.locationId = locationId;
		this.locationDesc = locationDesc;
		this.locationName = locationName;
		neighborLocations = new Location[4];
		actionList = new ArrayList<Action>();
		npcList = new ArrayList<Npc>();
	}

	public void addLocationAction(Action action) {
		actionList.add(action);
	}

	public void addNpc(Npc npc) {
		npcList.add(npc);
	}

	public void addLocationAction(Action... actions) {
		for (Action action : actions)
			actionList.add(action);
	}

	public void addNpc(Npc... npcs) {
		for (Npc npc : npcs)
			npcList.add(npc);
	}

	public void addNeighbor(Location location, Direction direction) {
		neighborLocations[direction.ordinal()] = location;
		location.getNeighbors()[direction.opposite().ordinal()] = this;
	}

	public void removeNpc(Npc toRemove) {
		for (Npc npc : npcList) {
			if (npc.equals(toRemove))
				npcList.remove(npc);
		}
	}

	public boolean doesNeighborExist(Direction dir) {
		return neighborLocations[dir.ordinal()] != null;
	}

	public List<Action> getActions() {
		return actionList;
	}
	
	public List<Npc> getNpcs() {
		return npcList;
	}

	public Location[] getNeighbors() {
		return neighborLocations;
	}

	public Location getNeighbor(Direction direction) {
		return neighborLocations[direction.ordinal()];
	}

	public String getLocationId() {
		return locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public String getLocationDesc() {
		return locationDesc;
	}

}

package captainsly.paper.location;

import java.util.ArrayList;
import java.util.List;

import captainsly.paper.entities.Npc;

public class Location {

	private String locationId, locationName, locationDesc;
	private Location[] neighborLocations;

	private List<LocationAction> actionList;
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
		actionList = new ArrayList<LocationAction>();
		npcList = new ArrayList<Npc>();
	}

	public void addLocationAction(LocationAction action) {
		actionList.add(action);
	}

	public void addNpc(Npc npc) {
		npcList.add(npc);
	}

	public void addLocationAction(LocationAction... actions) {
		for (LocationAction action : actions)
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

	public List<LocationAction> getLocationActions() {
		return actionList;
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

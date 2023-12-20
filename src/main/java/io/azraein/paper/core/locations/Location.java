package io.azraein.paper.core.locations;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class Location {

	// Location Identifiers
	private final String locationId;

	private String locationName;
	private String locationDescription;

	private final EnumMap<Direction, String> locationNeighbors;
	private final List<String> locationEvents;
	private final List<String> locationEntities;
	private final List<String> locationSubLocations;

	public Location(String locationId, String locationName, String locationDescription) {
		this.locationId = locationId;
		this.locationName = locationName;
		this.locationDescription = locationDescription;

		locationNeighbors = new EnumMap<>(Direction.class);
		locationEvents = new ArrayList<>();
		locationEntities = new ArrayList<>();
		locationSubLocations = new ArrayList<>();
	}

	// Public Location Methods

	/**
	 * Links this location to a neighboring location in a specific direction.
	 * 
	 * @param dir      - The direction where the neighboring location is located.
	 * @param location - The adjacent location to establish a connection with.
	 */
	public void addLocationNeighbor(Direction dir, Location location) {

		// Check to make sure the location doesn't already have a neighbor in this
		// direction or this location assigned to a different direction
		if (!(locationNeighbors.containsKey(dir)) || !(locationNeighbors.containsValue(location.getLocationId()))) {

			// Connects the neighboring location to this one in the specified direction.
			locationNeighbors.put(dir, location.getLocationId());

			// Additionally, ensures a two-way connection by linking this object to the
			// neighboring location
			// in the opposite direction.
			location.getLocationNeighbors().put(dir.getOpposite(), this.getLocationId());
		}
	}

	public void addSubLocation(String subLocationId) {
		locationSubLocations.add(subLocationId);
	}

	public void addLocationEvent(String gameEventId) {
		locationEvents.add(gameEventId);
	}

	public void addLocationEntity(String entityId) {
		locationEntities.add(entityId);
	}

	// Getters and Setters

	public String getLocationId() {
		return locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public String getLocationDescription() {
		return locationDescription;
	}

	public String getLocationNeighbor(Direction dir) {
		return locationNeighbors.get(dir);
	}

	public EnumMap<Direction, String> getLocationNeighbors() {
		return locationNeighbors;
	}

	public List<String> getLocationSubLocations() {
		return locationSubLocations;
	}

	public List<String> getLocationEvents() {
		return locationEvents;
	}

	public List<String> getLocationEntities() {
		return locationEntities;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}

}

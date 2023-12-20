package io.azraein.paper.core.system;

import java.util.HashMap;
import java.util.Map;

import io.azraein.paper.core.entities.Entity;
import io.azraein.paper.core.impl.IGameEvent;
import io.azraein.paper.core.items.Item;
import io.azraein.paper.core.locations.Location;

public final class Registry {

	private static final Map<String, IGameEvent> REGISTRY_GAME_EVENTS = new HashMap<>();
	private static final Map<String, Entity> REGISTRY_ENTITIES = new HashMap<>();
	private static final Map<String, Location> REGISTRY_LOCATIONS = new HashMap<>();
	private static final Map<String, Item> REGISTRY_ITEMS = new HashMap<>();

	public static void addGameEvent(String gameEventId, IGameEvent gameEvent) {
		Registry.REGISTRY_GAME_EVENTS.put(gameEventId, gameEvent);
	}

	public static IGameEvent getGameEvent(String gameEventId) {
		return Registry.REGISTRY_GAME_EVENTS.get(gameEventId);
	}

	public static void addEntity(Entity entity) {
		Registry.REGISTRY_ENTITIES.put(entity.getEntityId(), entity);
	}

	public static Entity getEntity(String entityId) {
		return Registry.REGISTRY_ENTITIES.get(entityId);
	}

	public static void addLocation(Location location) {
		Registry.REGISTRY_LOCATIONS.put(location.getLocationId(), location);
	}

	public static Location getLocation(String locationId) {
		return Registry.REGISTRY_LOCATIONS.get(locationId);
	}

	public static void addItem(Item item) {
		Registry.REGISTRY_ITEMS.put(item.getItemId(), item);
	}

	public static Item getItem(String itemId) {
		return Registry.REGISTRY_ITEMS.get(itemId);
	}

	public static Map<String, IGameEvent> getRegistryGameEvents() {
		return Registry.REGISTRY_GAME_EVENTS;
	}

	public static Map<String, Entity> getRegistryEntities() {
		return Registry.REGISTRY_ENTITIES;
	}

	public static Map<String, Location> getRegistryLocations() {
		return Registry.REGISTRY_LOCATIONS;
	}

	public static Map<String, Item> getRegistryItems() {
		return Registry.REGISTRY_ITEMS;
	}

}

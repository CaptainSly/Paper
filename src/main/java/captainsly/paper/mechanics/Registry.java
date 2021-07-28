package captainsly.paper.mechanics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.stream.JsonReader;

import captainsly.paper.entities.Npc;
import captainsly.paper.entities.Npc.Occupation;
import captainsly.paper.mechanics.items.Item;
import captainsly.paper.mechanics.items.Item.ItemType;
import captainsly.paper.mechanics.items.equipment.Equipment;
import captainsly.paper.mechanics.items.equipment.Equipment.EquipmentType;
import captainsly.paper.mechanics.items.equipment.EquipmentStat;
import captainsly.paper.mechanics.locations.Location;
import captainsly.paper.mechanics.locations.Location.Direction;
import captainsly.paper.mechanics.locations.actions.MineAction;
import captainsly.paper.mechanics.locations.actions.ShopAction;

public class Registry {

	public static final HashMap<String, Item> itemRegistry = new HashMap<String, Item>();
	public static final HashMap<String, Equipment> equipmentRegistry = new HashMap<String, Equipment>();
	public static final HashMap<String, Location> locationRegistry = new HashMap<String, Location>();
	public static final HashMap<String, Lootlist> lootListRegistry = new HashMap<String, Lootlist>();
	public static final HashMap<String, Npc> npcRegistry = new HashMap<String, Npc>();

	private static HashMap<String, HashMap<String, Direction>> locationNeighborMap = new HashMap<String, HashMap<String, Direction>>();

	public static void register() {
		JsonReader jsonReader = new JsonReader(new StringReader(readFile("src/main/resources/paper.cdb")));
		jsonReader.setLenient(true);
		try {

			// String flag that determines what data is being done
			String data = "";

			jsonReader.beginObject();
			while (jsonReader.hasNext()) {
				String name = jsonReader.nextName();
				if (name.contentEquals("sheets")) { // Castle DB files are structured like spreadsheets, there is a
													// sheet object, that holds all your data. The Columns object can be
													// ignored since most of the data will need to be imported specially
					jsonReader.beginArray();
					while (jsonReader.hasNext()) {
						jsonReader.beginObject();
						while (jsonReader.hasNext()) {
							name = jsonReader.nextName();
							if (name.contentEquals("name")) { // Token was name, based on the sheets name we determine
																// the data inside
								data = jsonReader.nextString(); // item_data, location_data etc.
							} else if (name.contentEquals("lines")) { // The actual data for the sheet lies inside
																		// lines, so we can ignore everything else
								jsonReader.beginArray();

								while (jsonReader.hasNext()) {
									if (data.contentEquals("npc_data")) {
										Npc npc = null;
										String npcName = "";
										String npcId = "";
										Occupation npcOccupation = Occupation.GUARD;

										jsonReader.beginObject();
										while (jsonReader.hasNext()) {
											name = jsonReader.nextName();
											switch (name) {
												case "npcId":
													npcId = jsonReader.nextString();
													break;
												case "npcName":
													npcName = jsonReader.nextString();
													break;
												case "npcOccupation":
													npcOccupation = Occupation.values()[jsonReader.nextInt()];
													break;
											}
										}

										npc = new Npc(npcId, npcName, npcOccupation);
										jsonReader.endObject();
										npcRegistry.put(npcId, npc);
									} else if (data.contentEquals("item_data")) { // All the item data lies in here so
																					// if the
																					// data flag was set to this, start
																					// preparing
																					// for item creation

										String[] itemIds = new String[3];
										int itemCost = 0;
										int itemType = 0;

										// Begin Working inside the Item Object
										jsonReader.beginObject();
										while (jsonReader.hasNext()) {
											name = jsonReader.nextName();
											switch (name) { // Switch on the item's properties, add them to the array
												case "itemId":
													itemIds[0] = jsonReader.nextString();
													break;
												case "itemName":
													itemIds[1] = jsonReader.nextString();
													break;
												case "itemDescription":
													itemIds[2] = jsonReader.nextString();
													break;
												case "itemCost":
													itemCost = jsonReader.nextInt();
													break;
												case "itemType":
													itemType = jsonReader.nextInt();
													break;
											}
										}
										jsonReader.endObject();

										// Create a fake genItem and toss it in the list, keeping a copy always
										Item genItem = new Item(itemIds[0], itemIds[1], itemIds[2],
												ItemType.values()[itemType]);
										genItem.setItemCost(itemCost);
										itemRegistry.put(itemIds[0], genItem);

									} else if (data.contentEquals("lootlist_data")) {
										Lootlist lootlist = null;

										jsonReader.beginObject();
										while (jsonReader.hasNext()) {
											name = jsonReader.nextName();
											switch (name) {
												case "lootlistId":
													lootlist = new Lootlist(jsonReader.nextString());
													break;
												case "itemIds":
													jsonReader.beginArray();
													while (jsonReader.hasNext()) {
														jsonReader.beginObject();
														if (jsonReader.nextName().contentEquals("itemId"))
															lootlist.add(jsonReader.nextString());

														jsonReader.endObject();
													}
													jsonReader.endArray();
													break;
											}
										}
										jsonReader.endObject();
										lootListRegistry.put(lootlist.getLootListId(), lootlist);

									} else if (data.contentEquals("location_data")) { // All the location data is inside
																						// here
										Location genLocal = null;
										String[] localIds = new String[3];
										String action = "";
										List<String> npcIds = new ArrayList<String>();
										HashMap<String, Direction> locationNeighbors = new HashMap<String, Direction>();

										// Just like the items, a fake location is made as well as a String array to
										// hold the id, name and description.
										// A hashmap holding a string and Direction is also created so we can get the
										// neighbors of this location and put them together after location creation

										jsonReader.beginObject();
										while (jsonReader.hasNext()) {
											name = jsonReader.nextName();
											switch (name) { // Switch case on all the location data
												case "locationId":
													localIds[0] = jsonReader.nextString();
													break;
												case "locationName":
													localIds[1] = jsonReader.nextString();
													break;
												case "locationDescription":
													localIds[2] = jsonReader.nextString();
													break;
												case "locationNeighbors":
													// Location Neighbors is HashMap holding two
													// objects, location and direction. Location
													// is the id of it's neighboring location,
													// and direction is the ordinal of the enum
													jsonReader.beginArray();
													while (jsonReader.hasNext()) {
														String location = "";
														int direction = 0;
														jsonReader.beginObject();
														while (jsonReader.hasNext()) {
															name = jsonReader.nextName();
															switch (name) {
																case "location":
																	location = jsonReader.nextString();
																	break;
																case "direction":
																	direction = jsonReader.nextInt();
																	break;
															}
														}
														jsonReader.endObject();
														locationNeighbors.put(location, Direction.values()[direction]);
													}
													jsonReader.endArray();
													break;
												case "locationActions":
													jsonReader.beginArray();
													while (jsonReader.hasNext()) {
														jsonReader.beginObject();
														while (jsonReader.hasNext()) {
															// Right now actions aren't really created expertly, there
															// might be another system, like the neighbor map that adds
															// a bunch of actions to a location, but for now there is
															// only two actions anyway.
															name = jsonReader.nextName();
															action = jsonReader.nextString();
														}
														jsonReader.endObject();
													}
													jsonReader.endArray();
													break;
												case "locationNpcs":
													jsonReader.beginArray();
													while (jsonReader.hasNext()) {
														jsonReader.beginObject();
														while (jsonReader.hasNext()) {
															if (jsonReader.nextName().contentEquals("npcId")) {
																npcIds.add(jsonReader.nextString());
															}
														}
														jsonReader.endObject();
													}
													jsonReader.endArray();

											}

										}
										jsonReader.endObject();

										// We finally create the fake location and add it to the list along with adding
										// the created neighbors to the locationNeighborMap
										genLocal = new Location(localIds[0], localIds[1], localIds[2]);
										for (String id : npcIds)
											genLocal.addNpc(npcRegistry.get(id));

										locationRegistry.put(genLocal.getLocationId(), genLocal);
										locationNeighborMap.put(genLocal.getLocationId(), locationNeighbors);

										// Add the actions to the location
										if (action.contentEquals("actionMine"))
											locationRegistry.get(genLocal.getLocationId())
													.addLocationAction(new MineAction());
										else if (action.contentEquals("actionShop"))
											locationRegistry.get(genLocal.getLocationId())
													.addLocationAction(new ShopAction());

									} else if (data.contentEquals("equipment_data")) {
										Equipment equipment = null;
										String[] equipmentData = new String[3];
										int[] equipmentStat = new int[3];
										EquipmentType equipmentType = null;

										jsonReader.beginObject();
										while (jsonReader.hasNext()) {
											name = jsonReader.nextName();
											switch (name) {
												case "equipmentId":
													equipmentData[0] = jsonReader.nextString();
													break;
												case "equipmentName":
													equipmentData[1] = jsonReader.nextString();
													break;
												case "equipmentDescription":
													equipmentData[2] = jsonReader.nextString();
													break;
												case "equipmentType":
													equipmentType = EquipmentType.values()[jsonReader.nextInt()];
													break;
												case "equipmentStats":
													jsonReader.beginArray();
													jsonReader.beginObject();
													while (jsonReader.hasNext()) {
														name = jsonReader.nextName();
														switch (name) {
															case "atk":
																equipmentStat[0] = jsonReader.nextInt();
																break;
															case "def":
																equipmentStat[1] = jsonReader.nextInt();
																break;
															case "spd":
																equipmentStat[2] = jsonReader.nextInt();
																break;
														}
													}
													jsonReader.endObject();
													jsonReader.endArray();
													break;
											}
										}
										equipment = new Equipment(equipmentData[0], equipmentData[1], equipmentData[2],
												equipmentType);
										equipment.setStat(EquipmentStat.ATK, equipmentStat[0]);
										equipment.setStat(EquipmentStat.DEF, equipmentStat[1]);
										equipment.setStat(EquipmentStat.SPD, equipmentStat[2]);
										equipmentRegistry.put(equipmentData[0], equipment);
										jsonReader.endObject();
									} else {
										// As the CastleDB grows, more data will be added either here or above depending
										// on how the data needs to be loaded in
										jsonReader.skipValue();
									}
								}

								jsonReader.endArray();
							} else
								jsonReader.skipValue();

						}
						jsonReader.endObject();
					}
					jsonReader.endArray();
				} else
					jsonReader.skipValue();
			}
			jsonReader.endObject();
			jsonReader.close();

			addLocationNeigbors();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String readFile(String filePath) {
		StringBuilder builder = new StringBuilder();
		try {
			FileReader fileReader = new FileReader(filePath);
			BufferedReader reader = new BufferedReader(fileReader);
			{
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line).append("\n");
				}
			}

			fileReader.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return builder.toString();
	}

	private static void addLocationNeigbors() {
		// We iterate through the locationRegistry adding all the neighbors of each
		// location, some locations won't have a neighbor (since adding a location as a
		// neighbor links back to the parent location), so we must check to see if the
		// HashMap's size is not equal to zero before we begin

		Iterator<Location> locationIterator = locationRegistry.values().iterator();
		// Get an iterator from the location registry and make sure the extracted
		// location is not null
		while (locationIterator.hasNext()) {
			Location location = locationIterator.next();
			if (location != null) {
				// Check the size of the location's neighborMap
				HashMap<String, Direction> neighborMap = locationNeighborMap.get(location.getLocationId());
				if (neighborMap.size() != 0) {

					// It's populated get an iterator
					Iterator<Entry<String, Direction>> neighborIterator = neighborMap.entrySet().iterator();
					while (neighborIterator.hasNext()) {

						// Get the Entry for the neighbor
						Entry<String, Direction> neighborEntry = (Entry<String, Direction>) neighborIterator.next();
						if (neighborEntry != null) {

							// Add the neighbors to the current Location
							location.addNeighbor(locationRegistry.get(neighborEntry.getKey()),
									neighborEntry.getValue());
						}
					}
				}
			}

		}
	}

}

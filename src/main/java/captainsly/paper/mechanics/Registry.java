package captainsly.paper.mechanics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.gson.stream.JsonReader;

import captainsly.paper.location.Location;
import captainsly.paper.location.Location.Direction;

public class Registry {

	public static final HashMap<String, Item> itemRegistry = new HashMap<String, Item>();
	public static final HashMap<String, Location> locationRegistry = new HashMap<String, Location>();

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
							System.out.println("Token is: " + name);
							if (name.contentEquals("name")) { // Token was name, based on the sheets name we determine
																// the data inside
								data = jsonReader.nextString(); // item_data, location_data etc.
								System.out.println("Working with " + data);
							} else if (name.contentEquals("lines")) { // The actual data for the sheet lies inside
																		// lines, so we can ignore everything else
								System.out.println("Inside the lines section");
								jsonReader.beginArray();

								while (jsonReader.hasNext()) {
									if (data.contentEquals("item_data")) { // All the item data lies in here so if the
																			// data flag was set to this, start prepring
																			// for item creation
										System.out.println("Working on Item Data");
										String[] itemIds = new String[3];
										int itemCost = 0;

										// Begin Working inside the Item Object
										jsonReader.beginObject();
										while (jsonReader.hasNext()) {
											name = jsonReader.nextName();
											switch (name) { // Switch on the item's properties, add them to the array
												case "id":
													itemIds[0] = jsonReader.nextString();
													break;
												case "name":
													itemIds[1] = jsonReader.nextString();
													break;
												case "desc":
													itemIds[2] = jsonReader.nextString();
													break;
												case "cost":
													itemCost = jsonReader.nextInt();
													break;
											}
										}
										jsonReader.endObject();

										// Create a fake genItem and toss it in the list, keeping a copy always
										Item genItem = new Item(itemIds[0], itemIds[1], itemIds[2]);
										genItem.setItemCost(itemCost);
										itemRegistry.put(itemIds[0], genItem);
										System.out.println("Item Registry Currently has: " + itemRegistry.size());

									} else if (data.contentEquals("location_data")) { // All the location data is inside
																						// here
										System.out.println("Working on Location Data");
										Location genLocal = null;
										String[] localIds = new String[3];
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
													// Location Neighbors is an array holding two
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
													jsonReader.skipValue();
													break;

											}

										}
										jsonReader.endObject();

										// We finally create the fake location and add it to the list along with adding
										// the created neighbors to the locationNeighborMap
										genLocal = new Location(localIds[0], localIds[1], localIds[2]);
										locationRegistry.put(genLocal.getLocationId(), genLocal);
										locationNeighborMap.put(genLocal.getLocationId(), locationNeighbors);
										System.out.println("Added Location: "
												+ locationRegistry.get(genLocal.getLocationId()).getLocationName());

										System.out.println("Added Neighbor Locations: "
												+ locationNeighborMap.get(genLocal.getLocationId()).size());

									} else {
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
		System.out.println("Adding Neighbors");
		// We iterate through the locationRegistry adding all the neighbors of each
		// location, some locations won't have a neighbor (since adding a location as a
		// neighbor links back to the parent location), so we must check to see if the
		// HashMap's size is not equal to zero before we begin
		Iterator<Location> locationIterator = locationRegistry.values().iterator();
		while (locationIterator.hasNext()) {
			Location location = locationIterator.next();
			if (location != null) {
				System.out.println("Working on location: " + location.getLocationId());

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
							System.out.println("Adding Location: " + neighborEntry.getKey() + " to the "
									+ neighborEntry.getValue().name());
							location.addNeighbor(locationRegistry.get(neighborEntry.getKey()),
									neighborEntry.getValue());
						}
					}
				}
			}

		}
	}

}

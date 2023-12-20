package io.azraein.paper;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import io.azraein.paper.core.Paper;
import io.azraein.paper.core.entities.Npc;
import io.azraein.paper.core.entities.Player;
import io.azraein.paper.core.entities.stats.Skills;
import io.azraein.paper.core.events.TestEvent;
import io.azraein.paper.core.items.Item;
import io.azraein.paper.core.items.PotionItem;
import io.azraein.paper.core.items.PotionItem.PotionType;
import io.azraein.paper.core.items.equipment.EquipType;
import io.azraein.paper.core.items.equipment.Equipment;
import io.azraein.paper.core.locations.Direction;
import io.azraein.paper.core.locations.Location;
import io.azraein.paper.core.system.Calendar;
import io.azraein.paper.core.system.Registry;
import io.azraein.paper.nodes.paper_scenes.PaperBattleScene;
import io.azraein.paper.nodes.paper_scenes.PaperCharCreatorScene;
import io.azraein.paper.nodes.paper_scenes.PaperGameScene;
import io.azraein.paper.nodes.paper_scenes.PaperScene;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class PaperApp extends Application {

	// PaperApp Properties
	private final ObjectProperty<Player> playerProperty = new SimpleObjectProperty<>();
	private final ObjectProperty<Location> locationProperty = new SimpleObjectProperty<>();
	private final ObjectProperty<PaperScene> papersceneProperty = new SimpleObjectProperty<>();

	// PaperApp Members
	private Stage primaryStage;
	private Map<String, PaperScene> paperScenes;

	@Override
	public void start(Stage stage) throws Exception {
		this.primaryStage = stage;
		paperScenes = new HashMap<>();

		// Setup the default property listeners
		setupSceneListeners();

		// TODO: REMOVE
		createDummyContent();

		Paper.paperCalendar = new Calendar();
		Paper.rnJesus = new Random(System.nanoTime());

		paperScenes.put("charCreator", new PaperCharCreatorScene(this));
		paperScenes.put("gameScene", new PaperGameScene(this));
		paperScenes.put("battleScene", new PaperBattleScene(this));

		primaryStage.setScene(new Scene(new Region(), 825, 780));
		primaryStage.setResizable(false);
		primaryStage.setMinWidth(840);
		primaryStage.setMinHeight(800);
		primaryStage.setTitle("Paper Engine: Version " + Paper.PAPER_VERSION);
		primaryStage.show();

		changePaperScene("charCreator");
		changePaperLocation(Registry.getLocation("testLocation1"));
	}

	private void setupSceneListeners() {
		papersceneProperty.addListener((observableValue, oldSceneValue, newSceneValue) -> {

			// If the old scene was the GameScene, stop the animation timer, and also start
			// if it is the newscene.
			if (oldSceneValue instanceof PaperGameScene) {
				PaperGameScene scene = (PaperGameScene) oldSceneValue;
				scene.stopLoop();
			} else if (oldSceneValue instanceof PaperBattleScene) {
				PaperBattleScene scene = (PaperBattleScene) oldSceneValue;
				scene.stopLoop();
			}

			// If the scene is set, set the root content to the scene
			if (newSceneValue != null) {

				if (newSceneValue instanceof PaperGameScene) {
					PaperGameScene scene = (PaperGameScene) newSceneValue;
					scene.startLoop();
				}

				primaryStage.getScene().setRoot(newSceneValue);
			}

		});

		playerProperty.addListener((obs, oldValue, newValue) -> {

			if (newValue != null) {
				Registry.addEntity(newValue);
			}

		});
	}

	private void createDummyContent() {
		TestEvent testEvent = new TestEvent();
		Npc testNpc = new Npc("testNpc1", "Lord OneTesty Face");
		Npc testNpc1 = new Npc("testNpc2", "Lord TwoTesty Face");
		Npc testNpc2 = new Npc("testNpc3", "Lord ThreeTesty McFace");
		Npc testNpc3 = new Npc("testNpc4", "Kyle");
		Npc testNpc4 = new Npc("testNpc5", "Jannet");
		Npc testNpc5 = new Npc("testNpc6", "Face");

		Location testLocation = new Location("testLocation1", "Denmark", "Denmarkles");
		Location testLocation2 = new Location("testLocation2", "Iceland", "Where the FUCK is the ice?");
		Location testLocation3 = new Location("testLocation3", "Canada", "Mooses eh");
		Location testLocation4 = new Location("testLocation4", "Las Vegas", "A ring-a-ding ding");

		testLocation2.addLocationEntity("testNpc2");
		testLocation2.addLocationEntity("testNpc3");
		testLocation3.addLocationEntity("testNpc4");
		testLocation4.addLocationEntity("testNpc5");

		testLocation.addLocationEntity("testNpc1");
		testLocation.addLocationEvent("testEvent1");
		testLocation.addSubLocation("lordTestShop");
		testLocation.addLocationNeighbor(Direction.NORTH, testLocation4);
		testLocation.addLocationNeighbor(Direction.EAST, testLocation2);
		testLocation.addLocationNeighbor(Direction.SOUTH, testLocation3);

		Equipment testEquipment = new Equipment("testEquipment", "The Test Helmet", "It does test shit",
				EquipType.HEAD);

		Equipment testChestplate = new Equipment("testChestplate", "The Test Chestplate",
				"it Does chestplate test shit", EquipType.CHEST);

		Equipment testLegs = new Equipment("testLegs", "The Test Leggings", "it Does legs test shit", EquipType.LEGS);

		Equipment testFeet = new Equipment("testFeet", "The Test Feet", "it Does feet test shit", EquipType.FEET);
		testFeet.setEquipmentSkillBonus(10);
		testFeet.setEquipmentSkillType(Skills.STEALTH);

		Equipment testHands = new Equipment("testHands", "The Test Gloves", "it Does glove test shit", EquipType.HANDS);

		Item slimeItem = new Item("redSlime", "Red Slime", "It's a potion component");

		PotionItem smallHealthPotion = new PotionItem("potion_small_health", "Small Health Potion",
				"A Small Health Potion that heals 10HP", PotionType.HEALTH, 10);

		Registry.addItem(smallHealthPotion);
		Registry.addItem(slimeItem);
		Registry.addItem(testLegs);
		Registry.addItem(testFeet);
		Registry.addItem(testHands);
		Registry.addItem(testEquipment);
		Registry.addItem(testChestplate);
		Registry.addEntity(testNpc);
		Registry.addEntity(testNpc1);
		Registry.addEntity(testNpc2);
		Registry.addEntity(testNpc3);
		Registry.addEntity(testNpc4);
		Registry.addEntity(testNpc5);
		Registry.addGameEvent("testEvent1", testEvent);
		Registry.addLocation(testLocation);
		Registry.addLocation(testLocation2);
		Registry.addLocation(testLocation3);
		Registry.addLocation(testLocation4);

	}

	public void changePaperScene(String paperSceneId) {
		papersceneProperty.setValue(getPaperScenes().get(paperSceneId));

	}

	public void changePaperLocation(Location location) {
		locationProperty.setValue(location);
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public ObjectProperty<Player> playerProperty() {
		return playerProperty;
	}

	public ObjectProperty<Location> locationProperty() {
		return locationProperty;
	}

	public ObjectProperty<PaperScene> papersceneProperty() {
		return papersceneProperty;
	}

	public Map<String, PaperScene> getPaperScenes() {
		return paperScenes;
	}

}

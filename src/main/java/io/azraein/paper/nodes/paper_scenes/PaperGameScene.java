package io.azraein.paper.nodes.paper_scenes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.tinylog.Logger;

import io.azraein.paper.PaperApp;
import io.azraein.paper.core.Paper;
import io.azraein.paper.core.impl.IGameEvent;
import io.azraein.paper.nodes.location_nodes.LocationChangeNode;
import io.azraein.paper.nodes.location_nodes.LocationDisplayNode;
import io.azraein.paper.nodes.location_nodes.LocationEntityNode;
import io.azraein.paper.nodes.location_nodes.LocationMenuNode;
import io.azraein.paper.nodes.player_nodes.PlayerEquipmentNode;
import io.azraein.paper.nodes.player_nodes.PlayerInventoryNode;
import io.azraein.paper.nodes.player_nodes.PlayerStatsNode;
import io.azraein.paper.nodes.system_nodes.SystemControlNode;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Separator;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PaperGameScene extends PaperScene {

	private VBox rootContainer;

	private HBox locationControlHbox;
	private HBox playerControlHbox;
	private HBox systemControlHbox;

	private AnimationTimer gameLoopAnimTimer;
	private List<IGameEvent> repeatableEvents;
	private ConcurrentLinkedQueue<IGameEvent> paperEventQueue;

	// Game Loop
	private float timeSinceLastClockUpdate = 0;
	private float timeSinceLastEventUpdate = 0;
	private long lastUpdateTime = 0;
	private float deltaTime = 0;

	// Game Properties

	public PaperGameScene(PaperApp paperApp) {
		super(paperApp);

		paperEventQueue = new ConcurrentLinkedQueue<>();

		rootContainer = new VBox();
		locationControlHbox = new HBox();
		playerControlHbox = new HBox();
		systemControlHbox = new HBox();

		repeatableEvents = new ArrayList<>();
		gameLoopAnimTimer = new AnimationTimer() {

			List<IGameEvent> dummy = new ArrayList<>();

			@Override
			public void handle(long now) {
				// PaperGameScene "GameLoop"
				if (lastUpdateTime == 0)
					lastUpdateTime = now;

				// Setup a DeltaTime Variable
				deltaTime = (float) ((now - lastUpdateTime) / 1000000000.0);

				// Process Calendar/Clock Updates
				timeSinceLastClockUpdate += deltaTime;
				if (timeSinceLastClockUpdate >= 2) {
					Paper.paperCalendar.update(deltaTime);
					timeSinceLastClockUpdate = 0.0f;
				}

				// Run any GameEvents in the Queue
				if (getPaperEventQueue().size() > 0) {
					Logger.debug("We have events in the queue! Start running!");

					var gameEvent = getPaperEventQueue().poll();
					// Since we're checking to see if the gameEventQueues size is bigger than 0
					// it "should" never return null.... hopefully

					// If it's a repeating event, add it to the repeatableEvents list.
					if (gameEvent.doesRepeat())
						repeatableEvents.add(gameEvent);

					Platform.runLater(() -> gameEvent.onGameEventAction());
				}

				// Run Any repeatableEvents, should wait about 5 seconds

				// Do something similar to clock event, every 5 seconds it runs a repeatable
				// event.
				if (timeSinceLastEventUpdate >= 5) {
					for (IGameEvent gameEvent : repeatableEvents) {

						if (gameEvent.shouldStop()) {
							// Add the event to the dummy list, then continue past this entry, we don't want
							// it to run if it's supposed to stop
							dummy.add(gameEvent);
							continue;
						}

						Platform.runLater(() -> gameEvent.onGameEventAction());
					}

					timeSinceLastEventUpdate = 0.0f;
				}				
				
				timeSinceLastEventUpdate += deltaTime;
				
				// Hopefully avoid any co-modification errors
				if (!dummy.isEmpty()) {
					Logger.debug("Removing cancelled events");
					repeatableEvents.removeAll(dummy);
					dummy.clear(); // Clear the dummy list for next time
				}

				lastUpdateTime = now;
			}
		};

		paperApp.locationProperty().addListener((obs, oldV, newV) -> {
			if (newV != null) {
				Logger.debug("Clearing Events, Switching Location");
				repeatableEvents.clear();
				paperEventQueue.clear();
			}
		});

		// Create the PaperNodes
		Insets commonInsets = new Insets(5);

		// Location Nodes
		LocationChangeNode locationChangeNode = new LocationChangeNode(paperApp, this);
		LocationDisplayNode locationDisplayNode = new LocationDisplayNode(paperApp);
		LocationMenuNode locationMenuNode = new LocationMenuNode(paperApp, this);
		LocationEntityNode locationEntityNode = new LocationEntityNode(paperApp);

		// Player Nodes
		PlayerStatsNode playerStatsNode = new PlayerStatsNode(paperApp, this);
		PlayerEquipmentNode playerEquipmentNode = new PlayerEquipmentNode(paperApp);
		PlayerInventoryNode playerInventoryNode = new PlayerInventoryNode(paperApp);

		// System Nodes
		SystemControlNode systemControlNode = new SystemControlNode(paperApp);

		// Create A VBox for the LocationMenuNode and LocationEntityNode and
		// Corresponding ScrollPanes. Toss them in these fancy TitledPane thingies!
		// They're so cool!!!!
		ScrollPane menuScroll = new ScrollPane(locationMenuNode);
		TitledPane menuPane = new TitledPane("Location Menu", menuScroll);
		menuPane.setExpanded(false);
		menuPane.setPadding(commonInsets);
		menuScroll.setPadding(commonInsets);
		menuScroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		menuScroll.setMaxHeight(120);

		ScrollPane entityScroll = new ScrollPane(locationEntityNode);
		TitledPane entityPane = new TitledPane("Location Npcs", entityScroll);
		entityPane.setExpanded(false);
		entityPane.setPadding(commonInsets);
		entityScroll.setPadding(commonInsets);
		entityScroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		entityScroll.setMaxHeight(120);

		// Listeners
		paperApp.locationProperty().addListener((obs, oldValue, newValue) -> {

			if (newValue != null) {
				menuPane.setText("Location Menu | " + newValue.getLocationEvents().size());
				entityPane.setText("Location Npcs | " + newValue.getLocationEntities().size());
			}
		});

		VBox menuEntitySeparatingVBox = new VBox();
		menuEntitySeparatingVBox.setPadding(commonInsets);
		menuEntitySeparatingVBox.getChildren().addAll(menuPane, new Separator(Orientation.HORIZONTAL), entityPane,
				new Separator(Orientation.HORIZONTAL));

		ScrollPane menuEntitySeparatingScroll = new ScrollPane(menuEntitySeparatingVBox);
		menuEntitySeparatingScroll.setPadding(new Insets(5));
		menuEntitySeparatingScroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		menuEntitySeparatingScroll.setMaxHeight(360);

		VBox displayStatsSeparatingVBox = new VBox();
		displayStatsSeparatingVBox.setPadding(commonInsets);
		displayStatsSeparatingVBox.getChildren().addAll(locationDisplayNode, new Separator(Orientation.HORIZONTAL),
				playerStatsNode);

		locationControlHbox.setPadding(commonInsets);
		locationControlHbox.getChildren().addAll(menuEntitySeparatingScroll, new Separator(Orientation.VERTICAL),
				displayStatsSeparatingVBox);

		playerControlHbox.setPadding(commonInsets);
		playerControlHbox.getChildren().addAll(playerEquipmentNode, new Separator(Orientation.VERTICAL),
				playerInventoryNode);

		systemControlHbox.setPadding(commonInsets);
		systemControlHbox.getChildren().addAll(locationChangeNode, new Separator(Orientation.VERTICAL),
				systemControlNode);

		rootContainer.getChildren().addAll(locationControlHbox, playerControlHbox, systemControlHbox);
		setRootContent(rootContainer);
	}

	public void addGameEventToQueue(IGameEvent gameEvent) {
		if (!paperEventQueue.contains(gameEvent))
			paperEventQueue.add(gameEvent);
	}

	public void startLoop() {
		gameLoopAnimTimer.start();
	}

	public void stopLoop() {
		gameLoopAnimTimer.stop();
	}

	public List<IGameEvent> getRepeatableEvents() {
		return repeatableEvents;
	}

	public ConcurrentLinkedQueue<IGameEvent> getPaperEventQueue() {
		return paperEventQueue;
	}

}

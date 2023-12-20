package io.azraein.paper.nodes.paper_scenes;

import org.tinylog.Logger;

import io.azraein.paper.PaperApp;
import io.azraein.paper.core.entities.Entity;
import io.azraein.paper.core.entities.EntityState;
import io.azraein.paper.core.entities.Player;
import io.azraein.paper.core.entities.stats.Characteristics;
import io.azraein.paper.core.system.DiceUtils;
import javafx.animation.AnimationTimer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class PaperBattleScene extends PaperScene {

	public enum BattleState {
		PLAYER_TURN, ENEMY_TURN, VICTORY, DEFEAT
	}

	private final ObjectProperty<Entity> battleEntityEnemyProperty = new SimpleObjectProperty<>();
	private final ObjectProperty<Player> battleEntityPlayerProperty = new SimpleObjectProperty<>();

	private final ObjectProperty<BattleState> battleStateProperty = new SimpleObjectProperty<>();

	private AnimationTimer battleTimer;

	private float timeSinceLastTurnUpdate = 0;
	private long lastUpdateTime = 0;
	private float deltaTime = 0;

	public PaperBattleScene(PaperApp paperApp) {
		super(paperApp);
		battleStateProperty.set(BattleState.ENEMY_TURN);

		// Get Player
		battleEntityPlayerProperty.set(paperApp.playerProperty().get());

		// DEBUG UI NODE FOR TESTING PURPOSES
		HBox rootContainer = new HBox();
		GridPane gp = new GridPane();
		GridPane ge = new GridPane();

		gp.setPadding(new Insets(15));
		gp.setHgap(5);
		gp.setVgap(5);

		ge.setPadding(new Insets(15));
		ge.setHgap(5);
		ge.setVgap(5);

		Label enemyName = new Label("Name: ");
		Label enemyHp = new Label("00/00");
		Label playerName = new Label("Name: ");
		Label playerHp = new Label("00/00");

		Button attackButton = new Button("Attack");
		attackButton.setOnAction(e -> handleAttack());

		gp.add(playerName, 0, 0);
		gp.add(playerHp, 1, 0);
		gp.add(attackButton, 0, 1);

		ge.add(enemyName, 0, 0);
		ge.add(enemyHp, 1, 0);

		battleEntityEnemyProperty.addListener((obs, oldValue, newValue) -> {
			if (newValue != null) {
				enemyName.setText("Name: " + newValue.getEntityName());
				enemyHp.setText(newValue.getEntityCurrentHealthPoints() + "/" + newValue.getEntityMaxHealthPoints());
				newValue.entityCurrentHealthPointsProperty().addListener((obds, oldHp, newHp) -> {
					enemyHp.setText(newHp + "/" + newValue.getEntityMaxHealthPoints());
				});
			}
		});

		battleEntityPlayerProperty.addListener((obs, oldValue, newValue) -> {
			if (newValue != null) {
				playerName.setText("Name: " + newValue.getEntityName());
				playerHp.setText(newValue.getEntityCurrentHealthPoints() + "/" + newValue.getEntityMaxHealthPoints());
				newValue.entityCurrentHealthPointsProperty().addListener((obds, oldHp, newHp) -> {
					playerHp.setText(newHp + "/" + newValue.getEntityMaxHealthPoints());
				});
			}
		});

		battleStateProperty.addListener((obs, oldValue, newValue) -> {
			if (newValue.equals(BattleState.ENEMY_TURN))
				attackButton.setDisable(true);
			else if (newValue.equals(BattleState.PLAYER_TURN))
				attackButton.setDisable(false);
		});

		battleTimer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				if (lastUpdateTime == 0)
					lastUpdateTime = now;

				// Setup a DeltaTime Variable
				deltaTime = (float) ((now - lastUpdateTime) / 1000000000.0);

				timeSinceLastTurnUpdate += deltaTime;
				if (timeSinceLastTurnUpdate >= 1) {
					handleBattleTurn();
					timeSinceLastTurnUpdate = 0;
				}

				lastUpdateTime = now;
			}
		};

		rootContainer.getChildren().addAll(gp, ge);
		getChildren().add(rootContainer);
	}

	private void handleBattleTurn() {
		var currentState = battleStateProperty.get();
		switch (currentState) {
		case PLAYER_TURN:
			onPlayerTurn();
			break;
		case ENEMY_TURN:
			onEnemyTurn();
			break;
		case VICTORY:
			onVictory();
			break;
		case DEFEAT:
			onDefeat();
			break;
		}
	}

	public void startBattle(Entity enemyEntity) {
		battleEntityEnemyProperty.set(enemyEntity);
		battleEntityPlayerProperty.set(paperApp.playerProperty().get());

		var player = battleEntityPlayerProperty.get();

		// Set who goes first based off of the DEX characteristic
		if (enemyEntity.getEntityCharacteristic(Characteristics.DEX) > player
				.getEntityCharacteristic(Characteristics.DEX))
			battleStateProperty.set(BattleState.ENEMY_TURN);
		else
			battleStateProperty.set(BattleState.PLAYER_TURN);

		// TODO: Whatever other changes or things that need to be done when starting a
		// battle.

		battleTimer.start();
	}

	public void stopLoop() {
		battleTimer.stop();
	}

	private void onDefeat() {
		Logger.debug("DEFEAT");
		var player = battleEntityPlayerProperty.get();
		var enemy = battleEntityEnemyProperty.get();
		// TODO: Player Death and the works
	}

	private void onVictory() {
		Logger.debug("VICTORY");
		var player = battleEntityPlayerProperty.get();
		var enemy = battleEntityEnemyProperty.get();
		// Give the spoils if any, and put back into the game scene
		// TODO: Enemy lootlist inventory stuff, etc.,
		paperApp.changePaperScene("gameScene");

	}

	private void onEnemyTurn() {
		Logger.debug("ENEMY_TURN");
		var player = battleEntityPlayerProperty.get();
		var enemy = battleEntityEnemyProperty.get();
		// TODO: Handle enemy debuffs, status conditions etc.

		// The enemy will always attack on its turn and always try dodge on the opposing
		// turn, give the player the same choice

		if (!DiceUtils.rollCharacteristicCheck(player, Characteristics.DEX))
			enemy.attackEntity(player);

		if (player.getEntityState().equals(EntityState.DEAD))
			battleStateProperty.set(BattleState.DEFEAT);
		else
			battleStateProperty.set(BattleState.PLAYER_TURN);
	}

	private void onPlayerTurn() {
		Logger.debug("PLAYER_TURN");
		var player = battleEntityPlayerProperty.get();
		// TODO: Handle Debuffs, and Status Conditions

	}

	private void handleAttack() {
		Logger.debug("Handling attack on the player turn");
		var enemy = battleEntityEnemyProperty.get();
		var player = battleEntityPlayerProperty.get();

		// Check to see if the opponent dodge's first
		if (!DiceUtils.rollCharacteristicCheck(enemy, Characteristics.DEX)) {
			Logger.debug("We're attacking the enemy!");

			player.attackEntity(enemy);

			if (enemy.getEntityState().equals(EntityState.DEAD))
				battleStateProperty.set(BattleState.VICTORY);
			else
				battleStateProperty.set(BattleState.ENEMY_TURN);
		} else {
			// TODO: Check a myriad of things, but for now just set the state to the enemy's
			// turn
			Logger.debug("The enemy dodged");
			battleStateProperty.set(BattleState.ENEMY_TURN);
		}

	}

}

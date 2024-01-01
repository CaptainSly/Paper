package io.azraein.paper.scenes;

import org.tinylog.Logger;

import io.azraein.paper.PaperApp;
import io.azraein.paper.core.entities.Entity;
import io.azraein.paper.core.entities.EntityState;
import io.azraein.paper.core.entities.Player;
import io.azraein.paper.core.entities.stats.Characteristics;
import io.azraein.paper.core.entities.stats.Skills;
import io.azraein.paper.core.entities.status_buffs.Debuff;
import io.azraein.paper.core.entities.status_buffs.StatusConditions;
import io.azraein.paper.core.system.DiceUtils;
import io.azraein.paper.nodes.battle_nodes.BattleViewNode;
import javafx.animation.AnimationTimer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class PaperBattleScene extends PaperScene {

	public enum BattleState {
		PLAYER_TURN, ENEMY_TURN, VICTORY, DEFEAT
	}

	private final ObjectProperty<Entity> battleEntityEnemyProperty = new SimpleObjectProperty<>();
	private final ObjectProperty<Player> battleEntityPlayerProperty = new SimpleObjectProperty<>();

	private BattleState battleState = BattleState.PLAYER_TURN;
	private AnimationTimer battleTimer;

	private float timeSinceLastTurnUpdate = 0;
	private long lastUpdateTime = 0;
	private float deltaTime = 0;

	private BattleViewNode battleViewNode;

	private TextArea battleDescArea;
	private Button attackBtn;

	public PaperBattleScene(PaperApp paperApp) {
		super(paperApp);
		battleState = BattleState.ENEMY_TURN;
		battleViewNode = new BattleViewNode(paperApp);
		battleDescArea = new TextArea();

		// Get Player
		battleEntityPlayerProperty.set(paperApp.playerProperty().get());

		battleDescArea.setWrapText(true);
		battleDescArea.setEditable(false);

		VBox rootContainer = new VBox();

		attackBtn = new Button("Attack");
		attackBtn.setOnAction(e -> handleAttack());

		battleTimer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				if (lastUpdateTime == 0)
					lastUpdateTime = now;

				// Setup a DeltaTime Variable
				deltaTime = (float) ((now - lastUpdateTime) / 1000000000.0);

				timeSinceLastTurnUpdate += deltaTime;
				if (timeSinceLastTurnUpdate >= 1.583f) {
					handleBattleTurn();
					timeSinceLastTurnUpdate = 0;
				}

				lastUpdateTime = now;
			}
		};

		rootContainer.getChildren().addAll(battleViewNode, battleDescArea, attackBtn);
		getChildren().add(rootContainer);
	}

	private void handleBattleTurn() {
		var currentState = battleState;
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
		battleViewNode.startBattle(enemyEntity);

		var player = battleEntityPlayerProperty.get();

		// Set who goes first based off of the DEX characteristic
		if (enemyEntity.getEntityCharacteristic(Characteristics.DEX) > player
				.getEntityCharacteristic(Characteristics.DEX))
			battleState = BattleState.ENEMY_TURN;
		else {
			battleState = BattleState.PLAYER_TURN;
			battleDescArea.appendText("It's " + player.getEntityName() + "'s turn\n");
		}

		// TODO: Whatever other changes or things that need to be done when starting a
		// battle.
		battleTimer.start();
	}

	public void stopLoop() {
		battleTimer.stop();
	}

	private void onDefeat() {
		Logger.debug("DEFEAT");
		// TODO: Player Death and the works
	}

	private void onVictory() {
		Logger.debug("VICTORY");
		var player = battleEntityPlayerProperty.get();
		var enemy = battleEntityEnemyProperty.get();
		// Give the spoils if any, and put back into the game scene
		// TODO: Enemy lootlist inventory stuff, etc.,

		player.addGold(enemy.getEntityGold());
		paperApp.changePaperScene("gameScene");

	}

	private void onEnemyTurn() {
		attackBtn.setDisable(true);
		var player = battleEntityPlayerProperty.get();
		var enemy = battleEntityEnemyProperty.get();
		// TODO: Handle enemy debuffs, status conditions etc.
		battleDescArea.appendText("It's " + enemy.getEntityName() + "'s turn\n");

		var enemyStsConds = enemy.getEntityStatusConditions();
		for (StatusConditions status : enemyStsConds)
			switch (status) {
			case BLINDED:
				break;
			case PARALYZED:
				break;
			case POISONED:
				break;
			case SLEEPING:
				break;
			case FROZEN:
				break;
			case SCORCHED:
				break;
			}

		// Debuffs
		Logger.debug("Handling Enemy Debuffs");
		var enemyDebuffs = enemy.getEntityDebuffs();
		for (Debuff debuff : enemyDebuffs)
			debuff.onAction(); // TODO: Debuffs need more data

		// The enemy will always attack on its turn and always try dodge on the opposing
		// turn, give the player the same choice
		
		// TODO: Come up with a better system, this one isn't fun for a video game. 
		

		if (!DiceUtils.rollSkillCheck(player, Skills.DODGE)) {
			int damage = enemy.attackEntity(player);
			battleDescArea.appendText("BAM! " + enemy.getEntityName() + " struck " + player.getEntityName() + " for "
					+ damage + " damage!\n");
		} else
			battleDescArea.appendText("WOOSH! " + player.getEntityName() + " dodged the attack!\n");

		if (player.getEntityState().equals(EntityState.DEAD))
			battleState = BattleState.DEFEAT;
		else {
			battleState = BattleState.PLAYER_TURN;
			battleDescArea.appendText("It's " + player.getEntityName() + "'s turn!\n");
		}
	}

	private void onPlayerTurn() {
		if (attackBtn.isDisabled())
			attackBtn.setDisable(false);
	}

	private void handleAttack() {
		attackBtn.setDisable(true);
		Logger.debug("Handling Player Debuffs");
		var enemy = battleEntityEnemyProperty.get();
		var player = battleEntityPlayerProperty.get();

		Logger.debug("Handling Player Status Conditions");
		var playerStsConds = player.getEntityStatusConditions();
		for (StatusConditions status : playerStsConds)
			switch (status) {
			case BLINDED:
				break;
			case PARALYZED:
				break;
			case POISONED:
				break;
			case SLEEPING:
				break;
			case FROZEN:
				break;
			case SCORCHED:
				break;
			}

		Logger.debug("Handling Player Debuffs");
		for (Debuff debuff : player.getEntityDebuffs())
			debuff.onAction();

		// Check to see if the opponent dodge's first
		if (!DiceUtils.rollSkillCheck(enemy, Skills.DODGE)) {
			Logger.debug("We're attacking the enemy!");

			int damage = player.attackEntity(enemy);
			battleDescArea.appendText("BAM!!! " + player.getEntityName() + " struck " + enemy.getEntityName() + " for "
					+ damage + " damage!\n");

			if (enemy.getEntityState().equals(EntityState.DEAD))
				battleState = BattleState.VICTORY;
			else
				battleState = BattleState.ENEMY_TURN;
		} else {
			// TODO: Check a myriad of things, but for now just set the state to the enemy's
			// turn
			Logger.debug("The enemy dodged");
			battleDescArea.appendText("WOOSH! " + enemy.getEntityName() + " dodged the attack!\n");
			battleState = BattleState.ENEMY_TURN;
		}

	}

}

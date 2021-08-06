package captainsly.paper.mechanics.nodes.alerts;

import captainsly.paper.entities.Enemy;
import captainsly.paper.entities.Player;
import captainsly.paper.entities.stats.Stat;
import captainsly.paper.mechanics.nodes.regions.WorldRegion;
import captainsly.paper.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class BattleAlert extends Alert {

	public enum BattleState {
		PLAYER_TURN, ENEMY_TURN, WIN_STATE, LOSE_STATE
	}

	private Player player;
	private Enemy enemy;
	private BattleState currentState;
	private SimpleStringProperty currentStateProperty;

	private BorderPane battleAlertPane;
	private GridPane playerAreaGrid, enemyAreaGrid;
	private TextArea battleOutputArea;

	public BattleAlert(Player player, Enemy enemy, WorldRegion worldRegion) {
		super(AlertType.INFORMATION);
		this.player = player;
		this.enemy = enemy;
		// TODO: Prototype the battle alert, decide whether or not an alert is the best
		// way to show it. Will need to be able to autoclose the alert when the battle
		// is over

		// Initalize the controls
		battleAlertPane = new BorderPane();

		// The Player and Enemy have different controls, the enemy area should show the
		// enemy health, their level and name, current weapon etc.
		// The player area should have the option for combat, using an item from the
		// inventory, running away, or magic
		playerAreaGrid = new GridPane();
		enemyAreaGrid = new GridPane();

		battleOutputArea = new TextArea();
		battleOutputArea.setEditable(false);

		// Get the player and enemy speed stat and see which is higher
		if (player.getActorStat(Stat.SPD) > enemy.getActorStat(Stat.SPD))
			currentState = BattleState.PLAYER_TURN; // Player is faster we go first
		else if (player.getActorStat(Stat.SPD) < enemy.getActorStat(Stat.SPD))
			currentState = BattleState.ENEMY_TURN; // enemy is faster they go first
		else {
			int rando = worldRegion.getRNJesus().nextInt(2);
			currentState = rando == 1 ? BattleState.PLAYER_TURN : BattleState.ENEMY_TURN;
			// They have to be equal so basically toss a coin to see who goes first
		}

		// Set the CurrentStateProperty to be the normal case version of currentState's
		// name
		currentStateProperty = new SimpleStringProperty(Utils.toNormalCase(currentState.name()));

		// Bind the property to the label
		Label currentStateLabel = new Label();
		currentStateLabel.textProperty().bind(currentStateProperty);

		setupBattleAlert();

		// Set the Padding and what not
		battleAlertPane.setPadding(new Insets(5, 5, 5, 5));
		enemyAreaGrid.setHgap(5);
		enemyAreaGrid.setVgap(5);
		playerAreaGrid.setHgap(5);
		playerAreaGrid.setVgap(5);

		// Add the Player Area Grid to the left side of the battleAlertPane and the
		// battleOutputPane to the right side
		battleAlertPane.setLeft(playerAreaGrid);
		battleAlertPane.setRight(enemyAreaGrid);
		battleAlertPane.setCenter(currentStateLabel);
		battleAlertPane.setBottom(battleOutputArea);

		this.getDialogPane().setContent(battleAlertPane);
	}

	private void setupBattleAlert() {
		// First thing to do is declare the controls we'll need at the top, then split
		// it off into sections
		Label playerNameLabel = new Label();
		Label enemyNameLabel = new Label();

		// Setup the Player Side
		playerNameLabel.setText(player.getActorName());

		// Player Controls
		Button attackBtn = new Button("Attack");
		Button magicBtn = new Button("Magic");
		Button itemsBtn = new Button("Items");
		Button runBtn = new Button("Run");

		attackBtn.setOnAction(e -> {
			// TODO: ATTACK
			// Did the player hit? If so the player does their attack stat worth of damage -
			// the enemys defence.

		});

		magicBtn.setOnAction(e -> {
			// TODO: Use a PopOver from ControlsFX and allow the user to select a spell and
			// cast it if they have enough mp
		});

		itemsBtn.setOnAction(e -> {
			// TODO: Use a PopOver from ControlsFX and allow the user to select an item to
			// use from an inventory list
		});

		runBtn.setOnAction(e -> {
			// TODO: If the player is faster than the enemy, run away. Else if the player is
			// slower, coin toss

		});

		// Setup the Enemy Side
		enemyNameLabel.setText(enemy.getActorName());

		// Add the controls to the list
		playerAreaGrid.add(playerNameLabel, 0, 0);
		playerAreaGrid.add(attackBtn, 0, 1);
		playerAreaGrid.add(magicBtn, 1, 1);
		playerAreaGrid.add(itemsBtn, 0, 2);
		playerAreaGrid.add(runBtn, 1, 2);

		enemyAreaGrid.add(enemyNameLabel, 0, 0);

	}

	private void write(String message) {
		battleOutputArea.appendText(message);
	}

}

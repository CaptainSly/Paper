package captainsly.paper.mechanics.locations.actions;

import captainsly.paper.entities.Enemy;
import captainsly.paper.entities.Player;
import captainsly.paper.mechanics.Registry;
import captainsly.paper.mechanics.nodes.alerts.BattleAlert;
import captainsly.paper.mechanics.nodes.regions.WorldRegion;

public class BattleAction extends Action {

	private Player player;
	private Enemy enemy;

	public BattleAction() {
		super("actionBattle", "Battle Action");
		enemy = Registry.enemyRegistry.get("enemySlimeGreen");
	}

	@Override
	public void onAction(WorldRegion worldRegion) {
		inAction = true;

		player = worldRegion.getPlayer();

		worldRegion.refresh();
		worldRegion.getMovementRegion().disableMovement();
		worldRegion.resetOutput();

		// Need to take in account who is faster, then have them go first
		worldRegion.write("Time to fight: " + enemy.getActorName());
		BattleAlert activeBattle = new BattleAlert(player, enemy, worldRegion);
		activeBattle.showAndWait();
		inAction = false;
		worldRegion.refresh();
		worldRegion.getMovementRegion().checkLocationPositions(worldRegion.getCurrentLocation());
		

	}

}

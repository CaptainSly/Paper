package captainsly.paper.mechanics.locations.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.bernardomg.tabletop.dice.history.RollHistory;
import com.bernardomg.tabletop.dice.interpreter.DiceRoller;
import com.bernardomg.tabletop.dice.parser.DefaultDiceParser;

import captainsly.paper.entities.stats.Stat;
import captainsly.paper.mechanics.Lootlist;
import captainsly.paper.mechanics.Registry;
import captainsly.paper.mechanics.items.Item;
import captainsly.paper.nodes.regions.WorldRegion;
import javafx.application.Platform;

public class MineAction extends Action {

	private List<Item> oreTypes;
	private Lootlist lootList;

	public MineAction() {
		super("actionMine", "Mine");
		oreTypes = new ArrayList<Item>();
		lootList = Registry.lootListRegistry.get("mineList");
		for (String id : lootList.getLootList())
			oreTypes.add(Registry.itemRegistry.get(id));

		actionCounter = 4;
	}

	@Override
	public void onAction(WorldRegion worldNode) {
		inAction = true;
		worldNode.refresh();
		worldNode.getMovementRegion().disableMovement();
		worldNode.resetOutput();

		Timer oreTimer = new Timer();
		TimerTask oreMineTask = new TimerTask() {
			@Override
			public void run() {

				Platform.runLater(() -> {

					worldNode.write("Pick...");
					if (actionCounter <= 1) {
						oreTimer.cancel();

						int index = worldNode.getRNJesus().nextInt(2);
						int amount = worldNode.getRNJesus()
								.nextInt(2 * worldNode.getPlayer().getActorStat(Stat.LEVEL) + 3);

						if (amount == 0)
							amount++;

						worldNode.getPlayer().getActorInventory().add(oreTypes.get(index), amount);
						worldNode.write("\nGot " + amount + " " + oreTypes.get(index).getItemName());

						RollHistory roll = new DiceRoller().transform(new DefaultDiceParser().parse("1d6*" + amount));
						worldNode.getPlayer().modifyXp(roll.getTotalRoll());

						worldNode.refresh();
						actionCounter = 4;
						inAction = false;

						worldNode.getMovementRegion().checkLocationPositions(worldNode.getCurrentLocation());
						return;
					}

					actionCounter--;
					worldNode.refresh();
				});
			}

		};

		oreTimer.scheduleAtFixedRate(oreMineTask, 1000, 1000);
	}

}

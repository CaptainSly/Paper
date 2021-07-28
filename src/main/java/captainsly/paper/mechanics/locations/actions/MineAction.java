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

	private final String[] mineSayings = new String[] { "Pick...", "Thwack...", "Boink...", "Strike..." };

	public MineAction() {
		super("actionMine", "Mine");
		oreTypes = new ArrayList<Item>();
		lootList = Registry.lootListRegistry.get("mineList");
		for (String id : lootList.getLootList())
			oreTypes.add(Registry.itemRegistry.get(id));

		actionCounter = 4;
	}

	@Override
	public void onAction(WorldRegion worldRegion) {
		inAction = true;
		worldRegion.refresh();

		worldRegion.getMovementRegion().disableMovement();
		worldRegion.resetOutput();

		Timer oreTimer = new Timer();
		TimerTask oreMineTask = new TimerTask() {
			@Override
			public void run() {

				Platform.runLater(() -> {

					worldRegion.write(mineSayings[worldRegion.getRNJesus().nextInt(mineSayings.length)]);
					if (actionCounter <= 1) {
						oreTimer.cancel();

						RollHistory roll = new DiceRoller()
								.transform(new DefaultDiceParser().parse("1d" + oreTypes.size()));
						int index = roll.getTotalRoll() - 1;
						int amount = worldRegion.getRNJesus()
								.nextInt(2 * worldRegion.getPlayer().getActorStat(Stat.LEVEL) + 3) + 1;

						if (amount == 0)
							amount++;

						worldRegion.getPlayer().getActorInventory().add(oreTypes.get(index), amount);
						worldRegion.write("\nGot " + amount + " " + oreTypes.get(index).getItemName());

						worldRegion.getPlayer().modifyXp(amount * 50);

						worldRegion.refresh();
						actionCounter = 4;
						inAction = false;

						worldRegion.getMovementRegion().checkLocationPositions(worldRegion.getCurrentLocation());
						return;
					}

					actionCounter--;
					worldRegion.refresh();
				});
			}

		};

		oreTimer.scheduleAtFixedRate(oreMineTask, 1000, 1000);
	}

}

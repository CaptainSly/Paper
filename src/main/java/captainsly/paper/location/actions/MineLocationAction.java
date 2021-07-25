package captainsly.paper.location.actions;

import java.util.Timer;
import java.util.TimerTask;

import com.bernardomg.tabletop.dice.history.RollHistory;
import com.bernardomg.tabletop.dice.interpreter.DiceRoller;
import com.bernardomg.tabletop.dice.parser.DefaultDiceParser;

import captainsly.paper.entities.stats.Stat;
import captainsly.paper.location.Location;
import captainsly.paper.mechanics.Registry;
import captainsly.paper.mechanics.items.Item;
import captainsly.paper.nodes.regions.WorldRegion;
import javafx.application.Platform;

public class MineLocationAction extends LocationAction {

	private Item[] oreTypes;

	private int oreTimerCounter = 4;

	public MineLocationAction(Location parentLocation) {
		super(parentLocation, "Mine", "Default Test Mine ");
		oreTypes = new Item[2];
		oreTypes[0] = Registry.itemRegistry.get("oreCopper");
		oreTypes[1] = Registry.itemRegistry.get("oreIron");

		Action mineAction = new Action("mineAction") {

			@Override
			public void onAction(WorldRegion worldNode) {
				inAction = true;
				setLocationActionStatus(LocationStatus.MINING);
				locationTimer = oreTimerCounter;
				worldNode.refresh();

				Timer oreTimer = new Timer();
				TimerTask oreMineTask = new TimerTask() {
					@Override
					public void run() {

						Platform.runLater(() -> {
							if (oreTimerCounter <= 1) {
								oreTimer.cancel();
								setLocationActionStatus(LocationStatus.NONE);
								int index = worldNode.getRNJesus().nextInt(2);
								int amount = worldNode.getRNJesus()
										.nextInt(2 * worldNode.getPlayer().getActorStat(Stat.LEVEL) + 3);
								if (amount == 0)
									amount++;

								worldNode.getPlayer().getActorInventory().add(oreTypes[index], amount);
								RollHistory roll = new DiceRoller()
										.transform(new DefaultDiceParser().parse("1d6*" + amount));
								System.out.println("ROLL TOTAL: " + roll.getTotalRoll());
								worldNode.getPlayer().modifyXp(roll.getTotalRoll());

								locationTimer = 0;
								worldNode.refresh();
								oreTimerCounter = 4;
								inAction = false;
								return;
							}

							oreTimerCounter--;
							locationTimer = oreTimerCounter;
							worldNode.refresh();
						});
					}

				};
				oreTimer.scheduleAtFixedRate(oreMineTask, 1000, 1000);
			}

		};

		this.setAction(mineAction);
	}

}

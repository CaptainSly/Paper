package captainsly.paper.entities;

import com.bernardomg.tabletop.dice.history.RollHistory;
import com.bernardomg.tabletop.dice.interpreter.DiceRoller;
import com.bernardomg.tabletop.dice.parser.DefaultDiceParser;

import captainsly.paper.mechanics.Lootlist;
import captainsly.paper.mechanics.Registry;

public class Merchant extends Actor {

	public Merchant(String actorId, String actorName) {
		super(actorId, actorName);		
		modifyActorGold(5000);
		
		Lootlist merchantLootList = Registry.lootListRegistry.get("merchantListTim");
		for (String id : merchantLootList.getLootList()) {
			RollHistory roll = new DiceRoller().transform(new DefaultDiceParser().parse("1d6*" + 5));
			getActorInventory().add(Registry.itemRegistry.get(id), roll.getTotalRoll());
		}
	}

}

package io.azraein.paper.core.system;

import com.bernardomg.tabletop.dice.history.RollHistory;
import com.bernardomg.tabletop.dice.interpreter.DiceInterpreter;
import com.bernardomg.tabletop.dice.interpreter.DiceRoller;
import com.bernardomg.tabletop.dice.parser.DefaultDiceParser;
import com.bernardomg.tabletop.dice.parser.DiceParser;

import io.azraein.paper.core.entities.Entity;
import io.azraein.paper.core.entities.stats.Characteristics;
import io.azraein.paper.core.entities.stats.Skills;

public class DiceUtils {

	public static int rollDice(String notation) {
		final DiceParser parser = new DefaultDiceParser();
		final DiceInterpreter<RollHistory> roller = new DiceRoller();
		RollHistory rolls = parser.parse(notation, roller);

		return rolls.getTotalRoll();
	}

	public static boolean rollCharacteristicCheck(Entity entity, Characteristics characteristic) {
		int roll = rollDice("1d100");
		if (roll <= entity.getEntityCharacteristic(characteristic))
			return true;
		else
			return false;
	}

	public static boolean rollSkillCheck(Entity entity, Skills skill) {
		int roll = rollDice("1d100");
		if (roll <= entity.getEntitySkill(skill))
			return true;
		else
			return false;
	}

}

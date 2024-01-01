package io.azraein.paper.core.entities;

import org.tinylog.Logger;

import io.azraein.paper.core.Paper;
import io.azraein.paper.core.background.CharacterBackground;
import io.azraein.paper.core.entities.stats.Characteristics;
import io.azraein.paper.core.entities.stats.Skills;
import io.azraein.paper.core.system.DiceUtils;

public class Npc extends Entity {

	public Npc(String entityId, String entityName, CharacterBackground characterBackground) {
		super(entityId, entityName, characterBackground);
		generateNpc();
	}

	private void generateNpc() {
		// Chacteristics
		this.getEntityCharacteristics()[Characteristics.STR.ordinal()] = (DiceUtils.rollDice("3d6*5"));
		this.getEntityCharacteristics()[Characteristics.CON.ordinal()] = (DiceUtils.rollDice("3d6*5"));
		this.getEntityCharacteristics()[Characteristics.SIZ.ordinal()] = (DiceUtils.rollDice("(2d6+6)*5"));
		this.getEntityCharacteristics()[Characteristics.DEX.ordinal()] = (DiceUtils.rollDice("3d6*5"));
		this.getEntityCharacteristics()[Characteristics.APP.ordinal()] = (DiceUtils.rollDice("3d6*5"));
		this.getEntityCharacteristics()[Characteristics.INT.ordinal()] = (DiceUtils.rollDice("(2d6+6)*5"));
		this.getEntityCharacteristics()[Characteristics.POW.ordinal()] = (DiceUtils.rollDice("3d6*5"));
		this.getEntityCharacteristics()[Characteristics.EDU.ordinal()] = (DiceUtils.rollDice("(2d6+6)*5"));

		this.getEntityCharacterBackground().generateSkillPoints();
		int[] charBackPoints = this.getEntityCharacterBackground().getBackgroundSkillsPoints();

		for (Skills skill : Skills.FIGHTING_SKILLS()) {
			int mod = Paper.rnJesus.nextInt(2) + 2;
			this.getEntitySkills()[skill.ordinal()] += this.getEntityCharacteristic(Characteristics.STR) / mod;
		}

		for (Skills skill : Skills.SOCIAL_SKILLS()) {
			int mod = Paper.rnJesus.nextInt(3) + 2;
			this.getEntitySkills()[skill.ordinal()] += this.getEntityCharacteristic(Characteristics.APP) / mod;
		}

		for (Skills skill : Skills.INVESTIGATION_SKILLS()) {
			int mod = Paper.rnJesus.nextInt(3) + 2;
			this.getEntitySkills()[skill.ordinal()] += this.getEntityCharacteristic(Characteristics.INT) / mod;
		}

		for (Skills skill : Skills.FIRST_AID_SKILLS()) {
			int mod = Paper.rnJesus.nextInt(4) + 2;
			this.getEntitySkills()[skill.ordinal()] += this.getEntityCharacteristic(Characteristics.CON) / mod;
		}

		for (Skills skill : Skills.STEALTH_SKILLS()) {
			int mod = Paper.rnJesus.nextInt(3) + 2;
			this.getEntitySkills()[skill.ordinal()] += this.getEntityCharacteristic(Characteristics.DEX) / mod;
		}

		for (Skills skill : Skills.GATHERING_SKILLS()) {
			int mod = Paper.rnJesus.nextInt(3) + 2;
			this.getEntitySkills()[skill.ordinal()] += this.getEntityCharacteristic(Characteristics.SIZ) / mod;
		}

		for (Skills skill : Skills.PROCESSING_SKILLS()) {
			int mod = Paper.rnJesus.nextInt(4) + 2;
			this.getEntitySkills()[skill.ordinal()] += this.getEntityCharacteristic(Characteristics.EDU) / mod;
		}

		for (Skills skill : Skills.MAGIC_SKILLS()) {
			int mod = Paper.rnJesus.nextInt(3) + 2;
			this.getEntitySkills()[skill.ordinal()] += this.getEntityCharacteristic(Characteristics.POW) / mod;
		}

		for (Skills skill : Skills.values()) {
			this.getEntitySkills()[skill.ordinal()] += charBackPoints[skill.ordinal()];
			Logger.debug("[" + this.getEntityName() + "] - " + skill.name() + ": " + this.getEntitySkill(skill));
		}
	}

	@Override
	public void onEntityDeath() {
		Logger.debug(getEntityName() + " has suffered a terrible fate");
	}

}

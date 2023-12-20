package io.azraein.paper.core.background;

import io.azraein.paper.core.entities.stats.Skills;

public class CharacterBackgroundFactory {

	private String backgroundName;
	private String backgroundDescription;

	private int backgroundSkillPoints;

	public CharacterBackgroundFactory setBackgroundName(String backgroundName) {
		this.backgroundName = backgroundName;
		return this;
	}

	public CharacterBackgroundFactory setBackgroundDescription(String backgroundDescription) {
		this.backgroundDescription = backgroundDescription;
		return this;
	}

	public CharacterBackgroundFactory setBackgroundSkillPoints(int backgroundSkillPoints) {
		this.backgroundSkillPoints = backgroundSkillPoints;
		return this;
	}

	public CharacterBackground createBackground() {
		return new CharacterBackground(this.backgroundName, this.backgroundDescription, this.backgroundSkillPoints);
	}

	public CharacterBackground createThiefBackground() {
		CharacterBackground thief = new CharacterBackground("Thief", "TODO", 75);
		thief.setBackgroundSkills(Skills.STEALTH_SKILLS());
		return thief;
	}

	public CharacterBackground createMageBackground() {
		CharacterBackground mage = new CharacterBackground("Mage", "TODO", 65);
		mage.setBackgroundSkills(Skills.MAGIC_SKILLS());
		return mage;
	}

}

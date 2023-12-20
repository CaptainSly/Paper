package io.azraein.paper.core.background;

import java.util.ArrayList;
import java.util.List;

import org.tinylog.Logger;

import io.azraein.paper.core.Paper;
import io.azraein.paper.core.entities.stats.Skills;

public class CharacterBackground {

	private String backgroundName;
	private String backgroundDescription;

	private int backgroundSkillPoints;

	private int[] backgroundSkillsPoints;
	private List<Skills> backgroundSkills;

	public CharacterBackground(String backgroundName, String backgroundDescription, int backgroundSkillPoints) {
		this.backgroundName = backgroundName;
		this.backgroundDescription = backgroundDescription;
		this.backgroundSkillPoints = backgroundSkillPoints;

		backgroundSkillsPoints = new int[Skills.values().length];
		backgroundSkills = new ArrayList<>();
	}

	/**
	 * Generates skill points for a character based on their background. The method
	 * randomly selects skills from the character's backgroundSkills and distributes
	 * points to them within specified limits, considering the current skill points
	 * and a total available pool.
	 *
	 * @implNote This method utilizes a random distribution of skill points and
	 *           checks constraints to ensure the generated points are within
	 *           acceptable limits.
	 * @see Skills Enum representing different skills that can be chosen.
	 *
	 * @author Azraein
	 * @version 0.1.0
	 */
	public void generateSkillPoints() {
		int totalPoints = this.backgroundSkillPoints;

		while (totalPoints > 0) {
			// Choose a random skill from the backgroundSkills
			Skills skill = backgroundSkills.get(Paper.rnJesus.nextInt(backgroundSkills.size()));
			Logger.debug(skill.name() + " has been choosen");

			// Generate a random number between 1 and 15 for the amount of points to
			// distribute.
			int distributingPoints = Paper.rnJesus.nextInt(1, 15);
			Logger.debug("distributingPoints = " + distributingPoints);

			// Check to see if distributingPoints is greater than totalPoints, if so, skip
			// this loop
			if (distributingPoints > totalPoints) {
				Logger.debug(
						"distrubtingPoints is greater than totalPoints " + distributingPoints + " > " + totalPoints);
				continue;
			}

			// Check to see if adding the distributingPoints to the chosen skill would make
			// the backgroundSkillsPoints less than or equal to 35
			int isHigher = backgroundSkillsPoints[skill.ordinal()] + distributingPoints;
			if (isHigher <= 35) {
				// The skillpoints can be added and since the skill is under threshold
				backgroundSkillsPoints[skill.ordinal()] += distributingPoints;
				totalPoints -= distributingPoints;
			} else {
				Logger.debug(skill.name() + " is at, " + backgroundSkillsPoints[skill.ordinal()] + " skipping.");
				continue;
			}

		}
	}

	public void setBackgroundSkills(Skills... skills) {
		for (Skills skill : skills)
			backgroundSkills.add(skill);
	}

	public String getBackgroundName() {
		return backgroundName;
	}

	public String getBackgroundDescription() {
		return backgroundDescription;
	}

	public int getBackgroundSkillPoints() {
		return backgroundSkillPoints;
	}

	public int[] getBackgroundSkillsPoints() {
		return backgroundSkillsPoints;
	}

	public List<Skills> getBackgroundSkills() {
		return backgroundSkills;
	}

}

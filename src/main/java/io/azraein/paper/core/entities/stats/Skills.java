package io.azraein.paper.core.entities.stats;

public enum Skills {

	// TODO: Figure out the best skills to use in the game.

	// Gathering Skills
	MINING, FISHING, WOODCUTTING, THIEVING,

	// Processing Skills
	SMITHING, COOKING, FARMING, CONSTRUCTING,

	// Social Skills
	BARTERING, SPEECH, INTIMIDATION, DECEPTION, CHARM, PERSUADE,

	// Stealth Skills
	DISGUISE_SELF, STEALTH, LOCKSMITH,

	// Investigation Skills
	PERCEPTION, INVESTIGATION, INSIGHT,

	// First Aid Skills
	FIRST_AID, MEDICINE,

	// Magic Skills
	ARCANA, ALCHEMY, ENCHANTING,

	// Fighting Skills
	DODGE, RANGED, MELEE,;

	public static Skills[] GATHERING_SKILLS() {
		return new Skills[] { MINING, FISHING, WOODCUTTING, THIEVING };
	}

	public static Skills[] PROCESSING_SKILLS() {
		return new Skills[] { SMITHING, COOKING, FARMING, CONSTRUCTING };
	}

	public static Skills[] SOCIAL_SKILLS() {
		return new Skills[] { BARTERING, SPEECH, INTIMIDATION, DECEPTION, CHARM, PERSUADE };
	}

	public static Skills[] STEALTH_SKILLS() {
		return new Skills[] { DISGUISE_SELF, STEALTH, LOCKSMITH };
	}

	public static Skills[] INVESTIGATION_SKILLS() {
		return new Skills[] { PERCEPTION, INVESTIGATION, INSIGHT };
	}

	public static Skills[] FIRST_AID_SKILLS() {
		return new Skills[] { FIRST_AID, MEDICINE };
	}

	public static Skills[] MAGIC_SKILLS() {
		return new Skills[] { ARCANA, ALCHEMY, ENCHANTING };
	}

	public static Skills[] FIGHTING_SKILLS() {
		return new Skills[] { DODGE, RANGED, MELEE };
	}

}

package captainsly.paper.mechanics.magic;

public enum Magic {

	LIGHT, DARK, FIRE, ICE, EARTH;

	Magic getTypeOpposite() {
		switch (this) {
			case LIGHT:
				return DARK;
			case DARK:
				return LIGHT;
			case FIRE:
				return EARTH;
			case ICE:
				return FIRE;
			case EARTH:
				return ICE;
			default:
				return LIGHT;
		}
	}

}

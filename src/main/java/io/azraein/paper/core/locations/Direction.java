package io.azraein.paper.core.locations;

public enum Direction {

	NORTH, SOUTH, EAST, WEST;

	public Direction getOpposite() {
		switch (this) {
		case EAST:
			return WEST;
		default:
		case NORTH:
			return SOUTH;
		case SOUTH:
			return NORTH;
		case WEST:
			return EAST;
		}
	}

}

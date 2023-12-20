package io.azraein.paper.core.entities;

import io.azraein.paper.core.background.CharacterBackground;

public class Player extends Entity {

	public Player(String entityName, CharacterBackground characterBackground) {
		super("playerEntity", entityName, characterBackground);
	}

}

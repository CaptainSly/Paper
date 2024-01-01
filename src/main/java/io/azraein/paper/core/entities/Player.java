package io.azraein.paper.core.entities;

import org.tinylog.Logger;

import io.azraein.paper.core.background.CharacterBackground;

public class Player extends Entity {

	public Player(String entityName, CharacterBackground characterBackground) {
		super("playerEntity", entityName, characterBackground);
	}

	@Override
	public void onEntityDeath() {
		Logger.debug(getEntityName() + " has suffered a terrible fate");
	}

}

package io.azraein.paper.core.entities.status_buffs;

import org.tinylog.Logger;

public class Debuff {

	private final String debuffName;
	private final int debuffDuration;

	public Debuff(String debuffName, int debuffDuration) {
		this.debuffName = debuffName;
		this.debuffDuration = debuffDuration;
	}

	public void onAction() {
		Logger.debug(debuffName + ": Testing Debuff");
	}

	public String getDebuffName() {
		return debuffName;
	}

	public int getDebuffDuration() {
		return debuffDuration;
	}

}

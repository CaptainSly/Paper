package io.azraein.paper.core.entities.status_buffs;

public class Buff {

	private final String buffName;
	private final int buffDuration;

	public Buff(String buffName, int buffDuration) {
		this.buffName = buffName;
		this.buffDuration = buffDuration;
	}

	public String getBuffName() {
		return buffName;
	}

	public int getBuffDuration() {
		return buffDuration;
	}
	
	

}

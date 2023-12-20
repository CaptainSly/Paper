package io.azraein.paper.core.events;

import org.tinylog.Logger;

import io.azraein.paper.core.impl.IGameEvent;

public class TestEvent implements IGameEvent {

	private boolean stop = false;

	@Override
	public void onGameEventAction() {
		Logger.debug("TEST");
	}

	@Override
	public void reset() {
		stop = false;
	}

	@Override
	public void stop() {
		stop = true;
	}

	@Override
	public boolean doesRepeat() {
		return true;
	}

	@Override
	public boolean shouldStop() {
		return stop;
	}

	@Override
	public String eventName() {
		return "Test Event";
	}

}

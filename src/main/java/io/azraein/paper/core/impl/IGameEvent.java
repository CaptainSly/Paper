package io.azraein.paper.core.impl;

public interface IGameEvent {

	void onGameEventAction();
	
	void stop();
	
	void reset();
	
	String eventName();
	
	boolean doesRepeat();
	
	boolean shouldStop();

}

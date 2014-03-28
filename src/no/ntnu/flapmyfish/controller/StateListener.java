package no.ntnu.flapmyfish.controller;

public interface StateListener {
	
	public enum GameState {START, FINISH, REMATCH, MESSAGE_UPDATED, FLAP};
	
	public void buttonClicked(int buttonId);
	
	public void gameStateChanged(GameState state);

}

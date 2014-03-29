package no.ntnu.flapmyfish.controller;

public interface StateListener {
	
	public enum GameState {START_MP, START_SP, END_MATCH, REMATCH, MESSAGE_UPDATED};
	
	public void buttonClicked(int buttonId);
	
	public void gameStateChanged(GameState state);

}

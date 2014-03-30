package no.ntnu.flapmyfish.controller;

public interface StateListener {
	
	public enum GameState {START_MP, START_SP, FINISH_MP, FINISH_SP, REMATCH, WAITING_FOR_OPPONENT_TO_FINISH, MESSAGE_UPDATED};
	
	public void buttonClicked(int buttonId);
	
	public void gameStateChanged(GameState state);

}

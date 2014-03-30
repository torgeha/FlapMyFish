package no.ntnu.flapmyfish.controller;


public interface GameListener extends StateListener {
	
	public enum GameScreenType {SINGLEPLAYER, MULTIPLAYER};
	
	public GameScreenType getCurrentGameScreenType();

}

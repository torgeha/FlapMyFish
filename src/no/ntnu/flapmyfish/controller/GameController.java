package no.ntnu.flapmyfish.controller;


public interface GameController extends StateListener {
	
	public enum GameScreenType {SINGLEPLAYER, MULTIPLAYER};
	
	public GameScreenType getCurrentGameScreenType();

}

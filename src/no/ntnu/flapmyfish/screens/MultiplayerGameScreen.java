package no.ntnu.flapmyfish.screens;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.ExtendedLayer;
import no.ntnu.flapmyfish.MainActivity;
import no.ntnu.flapmyfish.R;
import no.ntnu.flapmyfish.controller.StateListener.GameState;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class MultiplayerGameScreen extends GameScreen {
	
	//private StateListener listener;
	private ExtendedLayer remoteLayer;
	private Sprite opponent;
	public String message;
	private final float UPDATE_INTERVAL = 0.1f; //10 times pr. sec
	private float timeSinceLastMessage;
	private boolean opponentIsKilled;
	
	//TODO: Call listener.gameStateChanged(GameState.FINISH) to submit highscore and launch 'Hall of fame'

	
	/*public MultiplayerGameScreen(StateListener listener){
		super();
		this.listener = listener;
	}
	
	public void setListener(StateListener listener){
		this.listener = listener;
	}*/
	
	public void update(float dt){
		super.update(dt);
		timeSinceLastMessage += dt;
		if (timeSinceLastMessage > UPDATE_INTERVAL){
			message = getPlayer().getPosition().getY()/Constants.WINDOW_HEIGHT + " " + getPlayer().getPoints();
			//if (listener != null) listener.gameStateChanged(GameState.MESSAGE_UPDATED);
			MainActivity.getControllerInstance().gameStateChanged(GameState.MESSAGE_UPDATED);
			timeSinceLastMessage = 0;
		}
	}
	
	public String getMessage(){
		return message;
	}
	
	protected void init(){
		super.init();
		remoteLayer = new ExtendedLayer();
		opponent = new Sprite(new Image(R.drawable.hero_fish));
		remoteLayer.addSprite(opponent);
		
		scoreBoard.setMultiplayer(true);
		scoreBoard.setOpponentScore(0);
		
		opponent.setPosition(Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/2);
		
		world.addLayer(remoteLayer);
	}
	
	public int getOpponentScore(){
		return scoreBoard.getOpponentScore();
	}
	
	public boolean opponentIsKilled(){
		return opponentIsKilled;
	}
	
	public void setOpponentKilled(boolean b){
		opponentIsKilled = b;
	}
	
	public void updateOpponent(float y, int points){
		scoreBoard.setOpponentScore(points);
		opponent.setPosition(Constants.WINDOW_WIDTH/2, y*Constants.WINDOW_HEIGHT);
	}
	
}
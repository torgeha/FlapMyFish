package no.ntnu.flapmyfish.screens;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.ExtendedLayer;
import no.ntnu.flapmyfish.R;
import no.ntnu.flapmyfish.controller.FlapListener;
import no.ntnu.flapmyfish.controller.StateListener;
import no.ntnu.flapmyfish.controller.StateListener.GameState;
import no.ntnu.flapmyfish.tokens.Player;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class MultiplayerGameScreen extends GameScreen implements FlapListener{
	
	//private static MultiplayerGameScreen INSTANCE;
	private StateListener listener;
	private ExtendedLayer remoteLayer;
	private Player opponent;
	public String message;
	
	/*public static MultiplayerGameScreen getInstance(){
		if (INSTANCE == null) INSTANCE = new MultiplayerGameScreen();
		return INSTANCE;
	}*/
	
	public MultiplayerGameScreen(StateListener listener){
		this.listener = listener;
	}
	
	public void setListener(StateListener listener){
		this.listener = listener;
	}
	
	public void update(float dt){
		super.update(dt);
//		message = getYPosition()/Constants.WINDOW_HEIGHT + " " + player.getPoints();
//		if (listener != null) listener.gameStateChanged(GameState.MESSAGE_UPDATED);
	}
	
	public String getMessage(){
		return message;
	}
	
	protected void init(){
		super.init();
		remoteLayer = new ExtendedLayer();
		opponent = new Player(R.drawable.hero_fish);
		remoteLayer.addSprite(opponent);
		
		score.setMultiplayer(true);
		score.setOpponentPoints(0);
		
		opponent.setPosition(Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/2);
		
		world.addLayer(remoteLayer);
	}
	
	public void updateOpponent(float y, int points){
		score.setOpponentPoints(points);
		opponent.setPosition(Constants.WINDOW_WIDTH/2, y*Constants.WINDOW_HEIGHT);
	}
	
	public void flapOpponent() {
		this.opponent.flap();
	}

	@Override
	public void onFlap() {
		// TODO Auto-generated method stub
		listener.gameStateChanged(GameState.FLAP);
	}
	
}
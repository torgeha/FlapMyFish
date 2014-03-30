package no.ntnu.flapmyfish.screens;

import java.util.ArrayList;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.MainActivity;
import no.ntnu.flapmyfish.R;
import no.ntnu.flapmyfish.controller.GameController.GameScreenType;
import no.ntnu.flapmyfish.controller.StateListener.GameState;
import no.ntnu.flapmyfish.gui.ImageButton;
import no.ntnu.flapmyfish.tokens.ExtendedSprite;
import no.ntnu.flapmyfish.util.GraphicsUtils;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.gui.Widget;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GameOverScreen extends State implements WidgetListener{

	private ExtendedSprite gameOverBackground;
	private ArrayList<ImageButton> btns;
	private String gameOverMessage;
	private Paint textPaint;
	
	public GameOverScreen() {
		init();
		update(0);
	}
	
	private void init() {
		gameOverBackground = new ExtendedSprite(new Image(R.drawable.game_over));
		gameOverBackground.setSizeByHeight(1.2f);
//		gameOverBackground.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
//		gameOverBackground.setOffset(0, 0);
		gameOverBackground.setPosition(Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/2);
//		gameOverBackground.setPosition(gameOverBackground.getWidth(), 0);
		generateButtons(new String[]{"Main menu", "Play again"});
		
		textPaint = GraphicsUtils.createPaint(32*Constants.SCALE, Color.WHITE, true);
		
		if (MainActivity.getControllerInstance().getCurrentGameScreenType() == GameScreenType.SINGLEPLAYER){
			//gameOverMessage = "Score: "+MainActivity.getCurrentGameScreen().getPlayerScore();
			if (MainActivity.getCurrentGameScreen().hasNewHighscore()) gameOverMessage = "New highscore!";
			else gameOverMessage = "";
		} else if (MainActivity.getControllerInstance().getCurrentGameScreenType() == GameScreenType.MULTIPLAYER){
			//gameOverMessage = "Your score: "+MainActivity.getCurrentGameScreen().getPlayerScore()+"\nOpponent score: "+((MultiplayerGameScreen) MainActivity.getCurrentGameScreen()).getOpponentScore();
			MultiplayerGameScreen currentMpGameScreen = (MultiplayerGameScreen) MainActivity.getCurrentGameScreen();
			if (currentMpGameScreen.opponentIsKilled()){
				setFinishedMpGameOverMessage(currentMpGameScreen);
			} else {
				gameOverMessage = "Waiting for opponent to finish.";
			}
		}
	}
	
	public void setFinishedMpGameOverMessage(MultiplayerGameScreen currentMpGameScreen){
		int yourScore = currentMpGameScreen.getPlayerScore();
		int opponentScore = currentMpGameScreen.getOpponentScore();
		if (yourScore > opponentScore){
			gameOverMessage = "You won!";
		} else if (yourScore < opponentScore){
			gameOverMessage = "You lost!";
		} else {
			gameOverMessage = "It's a draw!";
		}
	}
	
	public void draw(Canvas canvas) {
		getGame().getPreviousState().draw(canvas);
		gameOverBackground.draw(canvas);
		for (ImageButton button : btns) {
			button.draw(canvas);
		}
		canvas.drawText(gameOverMessage, Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT / 2f, textPaint);
	}
	
	public void update(float dt) {
		gameOverBackground.update(dt);
	}
	
	private void generateButtons(String[] labels){		
		btns = new ArrayList<ImageButton>();
		for (int i=0; i<labels.length; i++){
			ExtendedSprite idle = new ExtendedSprite(new Image(R.drawable.btn_go_menu_idle));
			ExtendedSprite down = new ExtendedSprite(new Image(R.drawable.btn_go_menu_down));
			idle.setSizeByHeight(0.17f);
			down.setSizeByHeight(0.17f);
			ExtendedSprite[] btn_sprites = new ExtendedSprite[]{idle, down};
			float y = (i == 0) ? Constants.WINDOW_HEIGHT / 1.5f : btns.get(i-1).getPosition().getY()+(1.2f * btn_sprites[0].getHeight());			
			ImageButton btn = new ImageButton(Constants.WINDOW_WIDTH/2, y, labels[i], btn_sprites);
			addTouchListener(btn);
			btn.addWidgetListener(this);
			btns.add(btn);
		}
	}
	
	/*private void removeAllTouchListeners(){
		for (ImageButton btn :btns){
			btn.removeWidgetListener(this);
			removeTouchListener(btn);
		}
	}*/

	@Override
	public void actionPerformed(WidgetAction action) {
		Widget source = action.getSource();
		removeAllTouchListeners();
		if (source == btns.get(0)) {
			getGame().popState();
			getGame().popState();
			getGame().pushState(new MainMenuScreen());
		}
		else if (source == btns.get(1)) {
			if (MainActivity.getControllerInstance().getCurrentGameScreenType() == GameScreenType.SINGLEPLAYER){
				System.out.println("Starting new singleplayer game");
				getGame().popState();
				//getGame().popState();
				MainActivity.getControllerInstance().gameStateChanged(GameState.START_SP);
			} else if (MainActivity.getControllerInstance().getCurrentGameScreenType() == GameScreenType.MULTIPLAYER){
				System.out.println("Starting new multiplayer game");
				getGame().popState();
				//getGame().popState();
				MainActivity.getControllerInstance().gameStateChanged(GameState.START_MP);
			}
		}
	}
}

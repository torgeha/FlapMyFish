package no.ntnu.flapmyfish.screens;

import java.util.ArrayList;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.MainActivity;
import no.ntnu.flapmyfish.R;
import no.ntnu.flapmyfish.controller.GameListener.GameScreenType;
import no.ntnu.flapmyfish.controller.StateListener.GameState;
import no.ntnu.flapmyfish.gui.ImageButton;
import no.ntnu.flapmyfish.tokens.ExtendedSprite;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.gui.Widget;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;
import android.graphics.Canvas;

public class GameOverScreen extends State implements WidgetListener{

	private ExtendedSprite gameOverBackground;
	private ArrayList<ImageButton> btns;
	
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
	}
	
	public void draw(Canvas canvas) {
		getGame().getPreviousState().draw(canvas);
		gameOverBackground.draw(canvas);
		for (ImageButton button : btns) {
			button.draw(canvas);
		}
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

	@Override
	public void actionPerformed(WidgetAction action) {
		Widget source = action.getSource();
		if (source == btns.get(0)) {
			getGame().popState();
			getGame().popState();
		}
		else if (source == btns.get(1)) {
			if (MainActivity.getListenerInstance().getCurrentGameScreenType() == GameScreenType.SINGLEPLAYER){
				System.out.println("Starting new singleplayer game");
				GameScreen gameScreen = new GameScreen();
				getGame().popState();
				getGame().popState();
				getGame().pushState(gameScreen);				
			} else if (MainActivity.getListenerInstance().getCurrentGameScreenType() == GameScreenType.MULTIPLAYER){
				System.out.println("Starting new multiplayer game");
				getGame().popState();
				getGame().popState();
				MainActivity.getListenerInstance().gameStateChanged(GameState.START_MP);
			}
		}
	}
}

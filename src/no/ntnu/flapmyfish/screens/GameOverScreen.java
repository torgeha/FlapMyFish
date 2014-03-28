package no.ntnu.flapmyfish.screens;

import java.util.ArrayList;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.R;
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
	private ArrayList<ImageButton> buttons;
	
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
		buttons = new ArrayList<ImageButton>();
		generateButtons(new String[]{"Main Menu", "Try Again"});
	}
	
	public void draw(Canvas canvas) {
		getGame().getPreviousState().draw(canvas);
		gameOverBackground.draw(canvas);
		for (ImageButton button : buttons) {
			button.draw(canvas);
		}
	}
	
	public void update(float dt) {
		gameOverBackground.update(dt);
	}
	
	private void generateButtons(String[] labels) {
		Image[] buttonImages = new Image[]{new Image(R.drawable.btn_go_menu_idle), new Image(R.drawable.btn_go_menu_down)};
		ImageButton button1 = new ImageButton(Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/1.5f, labels[0], buttonImages);
		ImageButton button2 = new ImageButton(Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/1.15f, labels[1], buttonImages);
		
		for (ImageButton button : new ImageButton[]{button1, button2}) {
			addTouchListener(button);
			button.addWidgetListener(this);
			buttons.add(button);
		}
		
	}

	@Override
	public void actionPerformed(WidgetAction action) {
		Widget source = action.getSource();
		if (source == buttons.get(0)) {
			getGame().popState(2);
		}
		else if (source == buttons.get(1)) {
			getGame().popState(2);
			getGame().pushState(new GameScreen());
		}
	}
}

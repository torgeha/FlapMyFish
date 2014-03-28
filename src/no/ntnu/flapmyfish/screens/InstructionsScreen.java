package no.ntnu.flapmyfish.screens;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.R;
import no.ntnu.flapmyfish.tokens.ExtendedSprite;
import sheep.game.State;
import sheep.graphics.Image;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class InstructionsScreen extends State {

	ExtendedSprite background;
	
	public InstructionsScreen() {
		init();
	}
	
	private void init() {
		background = new ExtendedSprite(new Image(R.drawable.instructions_screen));
		background.setSizeByHeight(1.0f);
		background.setPosition(Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/2);
		update(0);
	}
	
	public void draw(Canvas canvas) {
		background.draw(canvas);
		
	}
	
	public void update(float dt) {
		background.update(dt);
	}
	
	public boolean onTouchDown(MotionEvent motionEvent) {
		getGame().popState();
		return true;
	}
}

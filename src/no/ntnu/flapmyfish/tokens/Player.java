package no.ntnu.flapmyfish.tokens;

import no.ntnu.flapmyfish.Constants;
import android.graphics.Canvas;
import android.view.MotionEvent;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class Player extends Fish {
	
	public Player(Image img) {
		super(img);		
		setPosition(Constants.PLAYER_STARTING_POSITION_X, Constants.PLAYER_STARTING_POSITION_Y);
		
		//player should sink at first
		stopFlap();
		
	}
	
	public void draw(Canvas canvas) {
		draw(canvas);
	}
	
	public void update(float dt) {
		//apply physics
		//check collision
		//add points
		
		
		update(dt);
	}
	
	//Is it necessary to check both ways?
	public void collided(Sprite a, Sprite b) {
		//Check if the collision involves the player
		if (!(a == this || b == this)) {
			return;
		}
		else if (a == this && b instanceof Enemy) {
			//Die
		}
		else if (a == this && b instanceof Food) {
			//Extra point
		}
		else if (a instanceof Enemy && b == this) {
			//DIE
		}
		else if (a instanceof Food && b == this) {
			//extra point
		}
		
	}
	
	public boolean onTouchDown(MotionEvent event) {
		flap();
		return false;
	}
	
	public boolean onTouchUp(MotionEvent event) {
		stopFlap();
		return false;
	}
	
	private void stopFlap() {
		setSpeed(0, Constants.PLAYER_SINK_SPEED);
	}
	
	private void flap() {
		setSpeed(0, Constants.PLAYER_FLAP_SPEED);
	}
}

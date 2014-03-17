package no.ntnu.flapmyfish.tokens;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.controller.CollisionController;
import sheep.input.TouchListener;
import android.view.MotionEvent;

public class Player extends Fish implements TouchListener {
	
	private float lastDelta;
	
	public Player(int resId) {
		super(resId);
		setPosition((Constants.WINDOW_WIDTH)/2, (Constants.WINDOW_HEIGHT)/2);
		addCollisionListener(new CollisionController());
		sink();
	}
	
	@Override
	public void update(float dt){
		this.lastDelta = dt;
		super.update(dt);
		if (getSpeed().getY() >= Constants.PLAYER_SINK_SPEED) setAcceleration(0, 0); //Stops accelerating after maximum speed is reached.
	}
	
	public float getLastDelta(){
		return lastDelta;
	}
	
	private void sink() {
		setSpeed(0, Constants.PLAYER_SINK_SPEED);
	}
	
	private void flap() {
		setSpeed(0, -Constants.PLAYER_FLAP_SPEED);
	}
	
	@Override
	public boolean onTouchDown(MotionEvent event) {
		flap();
		return false;
	}
	
	@Override
	public boolean onTouchUp(MotionEvent event) {
		setAcceleration(0, Constants.PLAYER_SINK_ACCELERATION);
		return false;
	}

	@Override
	public boolean onTouchMove(MotionEvent event) {
		return false;
	}

}
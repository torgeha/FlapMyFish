package no.ntnu.flapmyfish.tokens;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.controller.PlayerCollisionController;
import sheep.input.TouchListener;
import android.view.MotionEvent;

public class Player extends Fish implements TouchListener {
	
	private float lastDelta;
	private boolean touchDown;
	private int points;
	private float counter;
	
	public Player(int resId) {
		super(resId);
		setPosition((Constants.WINDOW_WIDTH)/2, (Constants.WINDOW_HEIGHT)/2);
		addCollisionListener(new PlayerCollisionController());
		sink();
	}
	
	@Override
	public void update(float dt){
		this.lastDelta = dt;
		counter += dt;
		if ((counter / 1.0f) > Constants.SCORE_FREQUENCY) {
			addPoints(Constants.TRAVEL_POINTS);
			counter = 0f;
		}
		super.update(dt);
		if (getSpeed().getY() >= Constants.PLAYER_SINK_SPEED) setAcceleration(0, 0); //Stops accelerating after maximum speed is reached.
		fixPosition();
		super.update(0);
	}
	
	
	
	public int getPoints() {
		return points;
	}
	
	public void addPoints(int points){
		this.points += points;
	}

	public void fixPosition(){
		if((getPosition().getY()-getHeight()/2.0f)<=0){
			//TOP
			setPosition(getPosition().getX(), getHeight()/2.0f);
			if (!touchDown) setSpeed(0, 0);
		} else if ((getPosition().getY()+getHeight()/2.0f)>=Constants.WINDOW_HEIGHT){
			//BOTTOM
			setPosition(getPosition().getX(), Constants.WINDOW_HEIGHT-getHeight()/2.0f);
			if (!touchDown) setSpeed(0, 0);
		}
	}
	
	public float getLastDelta(){
		return lastDelta;
	}
	
	private void sink() {
		setSpeed(0, Constants.PLAYER_SINK_SPEED);
	}
	
	private void flap() {
		setAcceleration(0, 0);
		setSpeed(0, -Constants.PLAYER_FLAP_SPEED);
	}
	
	@Override
	public boolean onTouchDown(MotionEvent event) {
		flap();
		touchDown = true;
		return false;
	}
	
	@Override
	public boolean onTouchUp(MotionEvent event) {
		touchDown = false;
		setAcceleration(0, Constants.PLAYER_SINK_ACCELERATION);
		return false;
	}

	@Override
	public boolean onTouchMove(MotionEvent event) {
		return false;
	}

}
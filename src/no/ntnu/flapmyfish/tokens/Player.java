package no.ntnu.flapmyfish.tokens;

import no.ntnu.flapmyfish.Constants;
import sheep.input.TouchListener;
import android.view.MotionEvent;

public class Player extends Fish implements TouchListener {
	
	private float lastDelta;
	private boolean touchDown;
	private int points;
	private float counter;
	private float initialFrameDuration;
	
//	public Player(int resId) {
//		super(resId);
//		initPlayer();
//	}
	
	public Player(int[] keyFramesResIds, float frameDuration, int currentFrame) {
		super(keyFramesResIds, frameDuration, currentFrame);
		initPlayer();
		this.initialFrameDuration = frameDuration;
	}
	
	@Override
	public void update(float dt){
		this.lastDelta = dt;

		updateAnimation(dt);

		counter += dt;
		if ((counter / 1.0f) > Constants.SCORE_FREQUENCY) {
			addPoints(Constants.TRAVEL_POINTS);
			counter = 0f;
		}

		super.update(dt);
		if (getSpeed().getY() >= Constants.PLAYER_SINK_SPEED || getSpeed().getY() <= -Constants.PLAYER_MAX_FLAP_SPEED) setAcceleration(0, 0); //Stops accelerating after maximum speed is reached.
		fixPosition();
		super.update(0);
		
		if(touchDown) setAcceleration(0, -Constants.PLAYER_FLAP_ACCELERATION);
		else setAcceleration(0, Constants.PLAYER_SINK_ACCELERATION);
		
		setOrientation(20*getSpeed().getY()/Constants.PLAYER_MAX_FLAP_SPEED);
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
			setSpeed(0, Constants.PLAYER_FLAP_SPEED);
//			if (!touchDown) setSpeed(0, 0);
		} else if ((getPosition().getY()+getHeight()/2.0f)>=Constants.WINDOW_HEIGHT){
			//BOTTOM
			setPosition(getPosition().getX(), Constants.WINDOW_HEIGHT-getHeight()/2.0f);
//			setSpeed(0, -getSpeed().getY());
			setSpeed(0, -Constants.PLAYER_FLAP_SPEED);
//			if (!touchDown) setSpeed(0, 0);
		}
	}
	
	private void updateAnimation(float dt)
	{
//		if(touchDown || currentFrame != 0) updateAnimationFrame(dt);
		updateAnimationFrame(dt);
//		if(!(getAcceleration().getY() <= 0)) updateAnimationFrame(dt);
	}
	
	public float getLastDelta(){
		return lastDelta;
	}
	
	private void initPlayer()
	{
		setPosition((Constants.WINDOW_WIDTH)/2, (Constants.WINDOW_HEIGHT)/2);
		sink();
		setSizeByHeight(0.1f);
		setShape(getWidth()/getScale().getX()/4.0f, getHeight()/getScale().getY()/1.1f);
		setShapeOffset(getWidth()/getScale().getX()/4*3, 0);
	}
	
	private void sink() {
//		setSpeed(0, Constants.PLAYER_SINK_SPEED);
		setAcceleration(0, Constants.PLAYER_SINK_ACCELERATION);
	}
	
	private void flap() {
		setSpeed(0, -.5f*Constants.PLAYER_FLAP_SPEED);
		setAcceleration(0, -Constants.PLAYER_FLAP_ACCELERATION);
		Constants.FLAP_SOUND.play();
	}
	
	@Override
	public boolean onTouchDown(MotionEvent event) {
		flap();
		touchDown = true;
		frameDuration = initialFrameDuration/2.0f;
//		frameTimeLeft += frameDuration;
		return false;
	}
	
	@Override
	public boolean onTouchUp(MotionEvent event) {
		touchDown = false;
		frameDuration = initialFrameDuration;
		setAcceleration(0, Constants.PLAYER_SINK_ACCELERATION);
		return false;
	}

	@Override
	public boolean onTouchMove(MotionEvent event) {
		return false;
	}

}
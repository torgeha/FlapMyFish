package no.ntnu.flapmyfish.tokens;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.controller.PlayerCollisionController;
import sheep.input.TouchListener;
import android.view.MotionEvent;

public class Player extends Fish implements TouchListener {
	
	private float lastDelta;
	private boolean touchDown;
	
//	public Player(int resId) {
//		super(resId);
//		initPlayer();
//	}
	
	public Player(int[] keyFramesResIds, float frameDuration, int currentFrame) {
		super(keyFramesResIds, frameDuration, currentFrame);
		initPlayer();
	}
	
	@Override
	public void update(float dt){
		this.lastDelta = dt;
		updateAnimation(dt);
		super.update(dt);
		if (getSpeed().getY() >= Constants.PLAYER_SINK_SPEED) setAcceleration(0, 0); //Stops accelerating after maximum speed is reached.
		fixPosition();
		super.update(0);
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
	
	private void updateAnimation(float dt)
	{
		if(touchDown || currentFrame != 0) updateAnimationFrame(dt);
//		if(!(getAcceleration().getY() <= 0)) updateAnimationFrame(dt);
	}
	
	public float getLastDelta(){
		return lastDelta;
	}
	
	private void initPlayer()
	{
		setPosition((Constants.WINDOW_WIDTH)/2, (Constants.WINDOW_HEIGHT)/2);
		addCollisionListener(new PlayerCollisionController());
		sink();
		setSizeByHeight(0.1f);
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
		frameTimeLeft += frameDuration;
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
package no.ntnu.flapmyfish.tokens;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.MainActivity;
import no.ntnu.flapmyfish.screens.GameScreen;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import sheep.audio.Audio;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class Player extends Fish {
	
	
	private static int sound = -1;
	private float ySpeed;


	
	public Player(Image img) {
		super(img);		
		setPosition(Constants.PLAYER_STARTING_POSITION_X, Constants.PLAYER_STARTING_POSITION_Y);
		
		//player should sink at first
		stopFlap();
		

		if(sound == -1){
			//sound = GameScreen.soundPool.load(this, R.raw.fish, 1); -- Her må det riktige inn objectet(this) muligens endres ettersom vi bestemmer hvor vi ønsker lyd
		}
		
	}
	
	private void playSound(){
		GameScreen.soundPool.play(sound, MainActivity.volume, MainActivity.volume, 0, 0, 1);
	}
	
	public void draw(Canvas canvas) {
		draw(canvas);
	}
	
	public void update(float dt) {
		//apply physics
		//check collision??
		//add points based on distance traveled
		
		
		update(dt);
	}
	

	public boolean onTouchDown(MotionEvent event) {
		flap();
		playSound();
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
	
	public void move(){
//		this.x = this.getView().getWidth() / 6;

		if(ySpeed < 0){
			ySpeed = ySpeed * 2 / 3 + getSpeedTimeDecrease() / 2;
		}else{
			this.ySpeed += getSpeedTimeDecrease();
		}

		
		
		super.move(); 
		
	}

	private int getSpeedTimeDecrease() {
		
		return Constants.WINDOW_HEIGHT / 320;
	}
}

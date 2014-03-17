package no.ntnu.flapmyfish;

import android.graphics.Color;

public class Constants {

	//Points for scoring
	//Highscore is set from sharedPreferences on startup and stored on pause
	public static int HIGHSCORE;
	public static final int FOOD_POINTS = 5;
	public static final int TRAVEL_POINTS = 1;
	public static final int SCORE_COLOR = Color.WHITE;
	
	//should be between 0.1f and 1.5f
	public static final float SCORE_FREQUENCY = 0.5f;
	
	public static final float PLAYER_FLAP_SPEED = 200;
	public static final float PLAYER_SINK_SPEED = 150;
	public static final float PLAYER_FLAP_ACCELERATION = 200;
	public static final float PLAYER_SINK_ACCELERATION = 200;
	public static final float BACKGROUND_SPEED = 50;
	
	//Change relative to screen size
	public static final float PLAYER_STARTING_POSITION_X = 30;
	public static final float PLAYER_STARTING_POSITION_Y = 50;

	public static int WINDOW_WIDTH;
	public static int WINDOW_HEIGHT;
	
	//Default volume for sound
	public static final float DEFAULT_VOLUME = 0.4f;

}
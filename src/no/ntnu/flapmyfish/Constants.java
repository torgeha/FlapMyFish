package no.ntnu.flapmyfish;

import sheep.audio.Sound;
import sheep.graphics.Font;
import android.graphics.Color;
import android.graphics.Typeface;

public class Constants {

	//Points for scoring
	//Highscore is set from sharedPreferences on startup and stored on pause
	public static int HIGHSCORE;
	public static final int FOOD_POINTS = 5;
	public static final int TRAVEL_POINTS = 1;
	public static final int SCORE_COLOR = Color.WHITE;
	
	//should be between 0.1f and 1.5f
	public static final float SCORE_FREQUENCY = 0.5f;
	
	public static float PLAYER_FLAP_SPEED/* = 200*/;
	public static float PLAYER_SINK_SPEED/* = 150*/;
	public static float PLAYER_MAX_FLAP_SPEED;
	public static float PLAYER_FLAP_ACCELERATION/* = 200*/;
	public static float PLAYER_SINK_ACCELERATION/* = 200*/;
	public static float BACKGROUND_SPEED/* = 50*/;
	
	//Change relative to screen size
	public static final float PLAYER_STARTING_POSITION_X = 30;
	public static final float PLAYER_STARTING_POSITION_Y = 50;

	public static int WINDOW_WIDTH;
	public static int WINDOW_HEIGHT;
	
	//Default volume for sound
	public static final float DEFAULT_VOLUME = 0.4f;
	
	//Level numbers
	public static final int LEVEL_SNIPPET_POOL_SIZE = 8;
	public static final int LEVEL_LENGTH = 100;
	public static int MAX_ENEMY_SPEED;
	public static final int NUMBER_OF_BLOCKS_X_DIR = 6;
	public static final int NUMBER_OF_BLOCKS_Y_DIR = 6;
	public static int SNIPPET_WIDTH;
	public static int TIME_BETWEEN_SNIPPETS = 1;
	
	//Fonts
	//public static final Font FONT_WHITE_SANS_BOLD_50 = new Font(255, 255, 255, 50f, Typeface.SANS_SERIF, Typeface.BOLD);
	
	//Factors to support different screen sizes/densities
	public static float SCALE;
	
	//Animation constants
	public static final float EATING_FRAME_DURATION = 0.03f;
	
	//Sounds
	public static final Sound FLAP_SOUND = new Sound(R.raw.fish_sound_v2);
	public static final Sound EAT_SOUND = new Sound(R.raw.food_eaten_v2);
	public static final Sound SPLAT = new Sound(R.raw.splat);
}
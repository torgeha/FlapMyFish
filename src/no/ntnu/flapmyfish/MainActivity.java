package no.ntnu.flapmyfish;

import no.ntnu.flapmyfish.controller.GameController;
import no.ntnu.flapmyfish.level.LevelSnippet;
import no.ntnu.flapmyfish.screens.GameScreen;
import no.ntnu.flapmyfish.screens.MainMenuScreen;
import no.ntnu.flapmyfish.screens.MultiplayerGameScreen;
import sheep.game.Game;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends MultiplayerActivity {

	private Game game;
	
	private static GameController MAIN_CONTROLLER;
	
	//Volume for sound
	public static float volume = Constants.DEFAULT_VOLUME;
	
	public static GameController getControllerInstance(){
		return MAIN_CONTROLLER;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MAIN_CONTROLLER = this;
		game = new Game(this, null);
		
//		  DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
        
		Display display = getWindowManager().getDefaultDisplay(); 
		@SuppressWarnings("deprecation")
		int width = display.getWidth();  // deprecated
		int height = display.getHeight();  // deprecated
        
        Constants.WINDOW_WIDTH  = width;
        Constants.WINDOW_HEIGHT = height;
        Constants.SNIPPET_WIDTH = Constants.WINDOW_WIDTH/3;
                
        Constants.PLAYER_FLAP_SPEED = (int)(0.45f*Constants.WINDOW_HEIGHT);
        Constants.PLAYER_FLAP_ACCELERATION = Constants.WINDOW_HEIGHT;
        Constants.PLAYER_MAX_FLAP_SPEED = 0.5f*Constants.WINDOW_HEIGHT;
        Constants.PLAYER_SINK_SPEED = (int)(0.45f*Constants.WINDOW_HEIGHT);
        Constants.PLAYER_SINK_ACCELERATION = (int)(/*0.80f**/Constants.WINDOW_HEIGHT);
        Constants.BACKGROUND_SPEED = (int)(0.08f*Constants.WINDOW_WIDTH);
        Constants.MAX_ENEMY_SPEED = (int)(0.4f*Constants.WINDOW_WIDTH);
        
        //Get the screen's density scale
        Constants.SCALE = getResources().getDisplayMetrics().density;
        
        //get highscore from file, if it exists
        SharedPreferences prefs = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        int score = prefs.getInt("myHighscore", 0);
        Constants.HIGHSCORE = score;
		
		game.pushState(new MainMenuScreen());
        
        //necessary for reading of levelsnippet file
        LevelSnippet.am = getAssets();

//		game.pushState(new GameScreen());
		setContentView(game);	
	}
	
	public GameScreenType getCurrentGameScreenType(){
		if (currentGameScreen instanceof MultiplayerGameScreen) return GameScreenType.MULTIPLAYER;
		else if (currentGameScreen instanceof GameScreen) return GameScreenType.SINGLEPLAYER;
		else return null;
	}
	
	protected void onPause() {
		super.onPause();
		//Save highscore if game is interrupted
		SharedPreferences prefs = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("myHighscore", Constants.HIGHSCORE);
		editor.commit();
	}

}
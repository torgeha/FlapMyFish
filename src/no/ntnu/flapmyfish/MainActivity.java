package no.ntnu.flapmyfish;

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
	
	//Volume for sound
	public static float volume = Constants.DEFAULT_VOLUME;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
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
        
        //Get the screen's density scale
        Constants.SCALE = getResources().getDisplayMetrics().density;
        
        //get highscore from file, if it exists
        SharedPreferences prefs = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        int score = prefs.getInt("myHighscore", 0);
        Constants.HIGHSCORE = score;
		
		game.pushState(new MainMenuScreen(this));
//		game.pushState(new GameScreen());
		setContentView(game);
		
	}
	
	protected void onPause() {
		super.onPause();
		//Save highscore if game is interrupted
		SharedPreferences prefs = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("myHighscore", Constants.HIGHSCORE);
		editor.commit();
	}
	
	public void gameStateChanged(GameState state){
		if (state == GameState.START){
			//MultiplayerGameScreen mpGameScreen = new MultiplayerGameScreen(this);
			mpGameScreen = new MultiplayerGameScreen(this);
			game.pushState(mpGameScreen);
			return;
		}
		super.gameStateChanged(state);
	}

}
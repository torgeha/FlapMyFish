package no.ntnu.flapmyfish;

import no.ntnu.flapmyfish.screens.MainMenuScreen;
import sheep.game.Game;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Display;

import com.google.example.games.basegameutils.BaseGameActivity;

public class MainActivity extends BaseGameActivity {

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
		
		game.pushState(new MainMenuScreen());
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

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}

}
package no.ntnu.flapmyfish;

import no.ntnu.flapmyfish.screens.GameScreen;
import no.ntnu.flapmyfish.screens.MainMenuScreen;
import sheep.game.Game;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;

public class MainActivity extends Activity {

	private Game game;
	private Point size;
	
	
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
		
//		game.pushState(new MainMenuScreen());
		game.pushState(new GameScreen());
		setContentView(game);
		
	}

	

}

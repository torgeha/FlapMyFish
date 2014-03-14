package no.ntnu.flapmyfish;

import no.ntnu.flapmyfish.screens.GameScreen;
import no.ntnu.flapmyfish.screens.MainMenuScreen;
import sheep.game.Game;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class MainActivity extends Activity {

	private Game game;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		game = new Game(this, null);
		
		DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        
        Constants.WINDOW_WIDTH  = dm.widthPixels;
        Constants.WINDOW_HEIGHT = dm.heightPixels;
		
//		game.pushState(new MainMenuScreen());
		game.pushState(new GameScreen());
		setContentView(game);
		
	}

	

}

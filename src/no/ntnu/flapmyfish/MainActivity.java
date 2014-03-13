package no.ntnu.flapmyfish;

import no.ntnu.flapmyfish.screens.MainMenuScreen;
import sheep.game.Game;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	private Game game;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		game = new Game(this, null);
		
		game.pushState(new MainMenuScreen());
		setContentView(game);
		
	}

	

}

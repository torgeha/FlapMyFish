package no.ntnu.flapmyfish;

import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;

import no.ntnu.flapmyfish.screens.GameScreen;
import sheep.game.Game;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends Activity implements GameHelperListener {

	private Game game;
	private Point size;
	private GameHelper gameHelper;
	private String mRoomId = null;

	// Volume for sound
	public static float volume = Constants.DEFAULT_VOLUME;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// just guessed the prameters, check?
		gameHelper = new GameHelper(this, GameHelper.CLIENT_ALL);

		game = new Game(this, null);

		// DisplayMetrics dm = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(dm);

		Display display = getWindowManager().getDefaultDisplay();
		@SuppressWarnings("deprecation")
		int width = display.getWidth(); // deprecated
		int height = display.getHeight(); // deprecated

		Constants.WINDOW_WIDTH = width;
		Constants.WINDOW_HEIGHT = height;
		Constants.SNIPPET_WIDTH = Constants.WINDOW_WIDTH / 3;

		// get highscore from file, if it exists
		SharedPreferences prefs = this.getSharedPreferences("myPrefs",
				Context.MODE_PRIVATE);
		int score = prefs.getInt("myHighscore", 0);
		Constants.HIGHSCORE = score;

		// game.pushState(new MainMenuScreen());
		game.pushState(new GameScreen());
		setContentView(game);

	}

	protected void onStart() {
		gameHelper.onStart(this);
	}

	protected void onStop() {
		leaveRoom();
		// stopKeepingScreenOn
		// wating screen
		gameHelper.onStop();
	}

	protected void leaveRoom() {
		if (mRoomId != null) {
			Games.RealTimeMultiplayer.leave(gameHelper.getApiClient(),
					(RoomUpdateListener) this, mRoomId);
			mRoomId = null;
			// waiting screen
		} else {
			// goto mainScreen
		}
	}

	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		gameHelper.onActivityResult(requestCode, responseCode, intent);

	}

	// protected void acceptInviteToRoom(String invId) {
	// RoomConfig.Builder roomConfigBuilder =
	// RoomConfig.builder((RoomUpdateListener) this);
	//
	// roomConfigBuilder.setInvitationIdToAccept(invId)
	// .setMessageReceivedListener((RealTimeMessageReceivedListener) this)
	// .setRoomStatusUpdateListener((RoomStatusUpdateListener) this);
	//
	// //waitingScreen??
	//
	// //keepScreenOn
	//
	// //reset game variables
	//
	// Games.RealTimeMultiplayer.join(gameHelper.getApiClient(),
	// roomConfigBuilder.build());
	// }
	/**
	 * Automatch with one other player
	 */
	protected void startQuickGame() {
		final int MIN_OPPONENTS = 1, MAX_OPPONENTS = 1;

		Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(
				MIN_OPPONENTS, MAX_OPPONENTS, 0);

		RoomConfig.Builder rtmConfigBuilder = RoomConfig
				.builder((RoomUpdateListener) this);
		rtmConfigBuilder
				.setMessageReceivedListener((RealTimeMessageReceivedListener) this);
		rtmConfigBuilder
				.setRoomStatusUpdateListener((RoomStatusUpdateListener) this);
		rtmConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);

		// waiting screen
		// keep screen on
		// reset game variables

		Games.RealTimeMultiplayer.create(gameHelper.getApiClient(),
				rtmConfigBuilder.build());
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Save highscore if game is interrupted
		SharedPreferences prefs = this.getSharedPreferences("myPrefs",
				Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("myHighscore", Constants.HIGHSCORE);
		editor.commit();
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		// Goto mainMenu

	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		Games.Invitations.registerInvitationListener(gameHelper.getApiClient(),
				(OnInvitationReceivedListener) this);
		// necessary?
		// if (gameHelper.getInvitationId() != null) {
		// acceptInviteToRoom(gameHelper.getInvitationId());
		// return;
		// }
		// goto mainscreen
	}

}

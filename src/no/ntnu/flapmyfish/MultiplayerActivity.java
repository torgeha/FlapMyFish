package no.ntnu.flapmyfish;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import no.ntnu.flapmyfish.controller.GameController;
import no.ntnu.flapmyfish.screens.GameOverScreen;
import no.ntnu.flapmyfish.screens.GameScreen;
import no.ntnu.flapmyfish.screens.MainMenuScreen;
import no.ntnu.flapmyfish.screens.MultiplayerGameScreen;
import sheep.game.Game;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMultiplayer.ReliableMessageSentCallback;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.example.games.basegameutils.BaseGameActivity;

/**
 * Developing a Real-time Multiplayer Game in Android:
 * https://developers.google.com/games/services/android/realtimeMultiplayer
 */
public abstract class MultiplayerActivity extends BaseGameActivity implements RoomUpdateListener, RealTimeMessageReceivedListener, RoomStatusUpdateListener, GameController, ReliableMessageSentCallback {

	// request code for the "select players" UI
	// can be any number as long as it's unique
	final static int RC_SELECT_PLAYERS = 10000;

	// arbitrary request code for the waiting room UI.
	// This can be any integer that's unique in your Activity.
	final static int RC_WAITING_ROOM = 10002;

	final static int RC_LEADERBOARD = 10003;

	private String myId, otherId, roomId;
	protected MultiplayerGameScreen mpGameScreen;
	protected GameScreen gameScreen;
	protected static GameScreen currentGameScreen;

	public static GameScreen getCurrentGameScreen(){
		return currentGameScreen;
	}

	@Override
	public void onJoinedRoom(int statusCode, Room room) {
		if (statusCode != GamesStatusCodes.STATUS_OK) {
			// display error
			return;  
		}

		// get waiting room intent
		Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(getApiClient(), room, Integer.MAX_VALUE);
		startActivityForResult(i, RC_WAITING_ROOM);
	}

	@Override
	public void onRoomCreated(int statusCode, Room room) {
		if (statusCode != GamesStatusCodes.STATUS_OK) {
			// display error
			return;
		}

		// get waiting room intent
		Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(getApiClient(), room, Integer.MAX_VALUE);
		startActivityForResult(i, RC_WAITING_ROOM);
	}


	private void startQuickGame() {
		// auto-match criteria to invite one random automatch opponent.  
		// You can also specify more opponents (up to 3). 
		Bundle am = RoomConfig.createAutoMatchCriteria(1, 1, 0);

		// build the room config:
		RoomConfig.Builder roomConfigBuilder = makeBasicRoomConfigBuilder();
		roomConfigBuilder.setAutoMatchCriteria(am);
		RoomConfig roomConfig = roomConfigBuilder.build();

		// create room:
		Games.RealTimeMultiplayer.create(getApiClient(), roomConfig);

		// prevent screen from sleeping during handshake
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		// go to game screen
	}

	// create a RoomConfigBuilder that's appropriate for your implementation
	private RoomConfig.Builder makeBasicRoomConfigBuilder() {
		return RoomConfig.builder(this)
				.setMessageReceivedListener(this)
				.setRoomStatusUpdateListener(this);
	}

	@Override
	public void onSignInFailed() {
	}

	@Override
	public void onSignInSucceeded() {
	}

	@Override
	public void onLeftRoom(int statusCode, String roomId) {
	}

	@Override
	public void onRoomConnected(int statusCode, Room room) {
		otherId = getOtherParticipantId(room);
		gameStateChanged(GameState.START_MP);
	}


	private int levelSentToken, waitingForOpponentToken, finishedMpToken = -1;

	protected void setUpAndStartLevel(){
		if (myId.compareTo(otherId) > 0){
			System.out.println("This device is sending level");
			levelSentToken = sendReliableMessageToOther(mpGameScreen.getLevelString());
		} else {
			System.out.println("This device is NOT sending level");
		}
	}

	private String getOtherParticipantId(Room room){
		for (Participant p : room.getParticipants()){
			if (!p.getParticipantId().equals(myId)) return p.getParticipantId();
		}
		return null;
	}

	public static final String FIRST_PLAYER_IS_KILLED = "FIRST_IS_KILLED";
	public static final String MP_GAME_IS_FINISHED = "MP_GAME_IS_FINISHED";

	@Override
	public void onRealTimeMessageReceived(RealTimeMessage message) {
		try {
			String decoded = new String(message.getMessageData(), "UTF-8");
			if (decoded.equals(FIRST_PLAYER_IS_KILLED)){
				mpGameScreen.setOpponentKilled(true);
			} else if (decoded.equals(MP_GAME_IS_FINISHED)){
				currentGameOverScreen.setFinishedMpGameOverMessage(mpGameScreen);
			} else {				
				String[] data = decoded.split(" ");
				if (data.length == 2){
					float y = Float.parseFloat(data[0]);
					int score = Integer.parseInt(data[1]);
					System.out.println("Y: "+y+", Score: "+score);
					if (mpGameScreen == null) gameStateChanged(GameState.START_MP);
					mpGameScreen.updateOpponent(y, score);
				} else {
					System.out.println("STATUS Received level");
					mpGameScreen.setLevel(decoded);
					Game.getInstance().getCurrentState().removeAllTouchListeners();
					Game.getInstance().popState();
					Game.getInstance().pushState(mpGameScreen);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onConnectedToRoom(Room room) {
		// TODO Auto-generated method stub
		myId = room.getParticipantId(Games.Players.getCurrentPlayerId(getApiClient()));

		roomId = room.getRoomId();
	}

	@Override
	public void onDisconnectedFromRoom(Room room) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onP2PConnected(String participantId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onP2PDisconnected(String participantId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPeerDeclined(Room arg0, List<String> arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPeerInvitedToRoom(Room arg0, List<String> arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPeerJoined(Room arg0, List<String> arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPeerLeft(Room arg0, List<String> arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPeersConnected(Room arg0, List<String> arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPeersDisconnected(Room arg0, List<String> arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRoomAutoMatching(Room room) {
		// TODO Auto-generated method stub
		roomId = room.getRoomId();

	}

	@Override
	public void onRoomConnecting(Room room) {
		// TODO Auto-generated method stub
		roomId = room.getRoomId();
	}

	@Override
	public void buttonClicked(int buttonId) {
		switch (buttonId){
		case MainMenuScreen.BTN_ID_MULTI_PLAYER:
			if (isSignedIn()) startQuickGame();
			else Toast.makeText(this, "You are not signed in.", Toast.LENGTH_SHORT).show();
		}

	}

	private GameOverScreen currentGameOverScreen;

	@Override
	public void gameStateChanged(GameState state) {
		System.out.println("GameState changed: "+state.toString());
		if (state == GameState.MESSAGE_UPDATED){
			Games.RealTimeMultiplayer.sendUnreliableMessageToOthers(getApiClient(), mpGameScreen.getMessage().getBytes(Charset.forName("UTF-8")), roomId);
		}
		else if (state == GameState.START_MP){
			mpGameScreen = new MultiplayerGameScreen();
			currentGameScreen = mpGameScreen;
			setUpAndStartLevel();
		}
		else if (state == GameState.START_SP){
			currentGameScreen = new GameScreen();
			Game.getInstance().popState();
			Game.getInstance().pushState(currentGameScreen);
		}
		else if (state == GameState.FINISH_SP){
			Game.getInstance().pushState(new GameOverScreen());
		}
		else if (state == GameState.FINISH_MP){
			finishedMpToken = sendReliableMessageToOther(MP_GAME_IS_FINISHED); //Notify opponent.
			currentGameOverScreen = new GameOverScreen();
			Game.getInstance().pushState(currentGameOverScreen);
		}
		else if (state == GameState.WAITING_FOR_OPPONENT_TO_FINISH){
			waitingForOpponentToken = sendReliableMessageToOther(FIRST_PLAYER_IS_KILLED); //Send message to opponent that you're killed
			currentGameOverScreen = new GameOverScreen(); //Update this screen when opponent finishes
			Game.getInstance().pushState(currentGameOverScreen);
		}
	}

	/*private void sendUnreliableMessageToOthers(String message){
		Games.RealTimeMultiplayer.sendUnreliableMessageToOthers(getApiClient(), message.getBytes(Charset.forName("UTF-8")), roomId);
	}

	private void sendUnreliableMessageToOther(String message){
		Games.RealTimeMultiplayer.sendUnreliableMessage(getApiClient(), message.getBytes(Charset.forName("UTF-8")), roomId, otherId);
	}*/

	private int sendReliableMessageToOther(String message){
		return Games.RealTimeMultiplayer.sendReliableMessage(getApiClient(), this, message.getBytes(Charset.forName("UTF-8")), roomId, otherId);
	}

	public void submitScore(int score){
		Games.Leaderboards.submitScore(getApiClient(), getResources().getString(R.string.leaderboard_id), score);
	}

	public void launchLeaderboard(){
		startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), getResources().getString(R.string.leaderboard_id)), RC_LEADERBOARD);
	}

	@Override
	public void onActivityResult(int request, int response, Intent intent) {
		if (request == RC_WAITING_ROOM) {
			if (response == Activity.RESULT_OK) {
				// (start game)
				// This is handled in onRoomConnected(int, Room)
			}
			else if (response == Activity.RESULT_CANCELED) {
				// Waiting room was dismissed with the back button. The meaning of this
				// action is up to the game. You may choose to leave the room and cancel the
				// match, or do something else like minimize the waiting room and
				// continue to connect in the background.

				// in this example, we take the simple approach and just leave the room:
				Games.RealTimeMultiplayer.leave(getApiClient(), this, roomId);
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			}
			else if (response == GamesActivityResultCodes.RESULT_LEFT_ROOM) {
				// player wants to leave the room.
				Games.RealTimeMultiplayer.leave(getApiClient(), this, roomId);
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			}
		}
	}

	@Override
	public void onRealTimeMessageSent(int statusCode, int tokenId, String recipientParticipantId) {
		System.out.println("Real time message sent: "+statusCode);
		if (tokenId == levelSentToken){
			switch (statusCode){
			case GamesStatusCodes.STATUS_OK:
				System.out.println("Token Id: "+tokenId);
				Game.getInstance().getCurrentState().removeAllTouchListeners();
				Game.getInstance().popState();
				Game.getInstance().pushState(mpGameScreen);
				System.out.println("Level success!! STATUS_OK");
				break;
			/*else if (tokenId == waitingForOpponentToken){

			} else if (tokenId == finishedMpToken){

			}*/
		case GamesStatusCodes.STATUS_REAL_TIME_MESSAGE_SEND_FAILED:
			//If failed to send message --> Try again
			setUpAndStartLevel();
			System.out.println("Level success!! STATUS_REAL_TIME_MESSAGE_SEND_FAILED");
			//Game.getInstance().pushState(mpGameScreen);
			break;
		default:
			System.out.println("Level failed!!! STATUS");
			break;
		}
	}
}

}
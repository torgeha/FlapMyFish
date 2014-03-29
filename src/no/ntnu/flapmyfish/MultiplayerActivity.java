package no.ntnu.flapmyfish;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import no.ntnu.flapmyfish.controller.GameListener;
import no.ntnu.flapmyfish.screens.GameScreen;
import no.ntnu.flapmyfish.screens.MainMenuScreen;
import no.ntnu.flapmyfish.screens.MultiplayerGameScreen;
import sheep.game.Game;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

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
public abstract class MultiplayerActivity extends BaseGameActivity implements RoomUpdateListener, RealTimeMessageReceivedListener, RoomStatusUpdateListener, GameListener, ReliableMessageSentCallback {
	
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
	
	
	
	protected void setUpAndStartLevel(){
		if (myId.compareTo(otherId) > 0){
			sendReliableMessageToOther(mpGameScreen.getLevelString());
		}
	}
	
	private String getOtherParticipantId(Room room){
		for (Participant p : room.getParticipants()){
			if (!p.equals(myId)) return p.getParticipantId();
		}
		return null;
	}
	

	@Override
	public void onRealTimeMessageReceived(RealTimeMessage message) {
		try {
			String decoded = new String(message.getMessageData(), "UTF-8");
			String[] data = decoded.split(" ");
			if (data.length == 2){
				float y = Float.parseFloat(data[0]);
				int score = Integer.parseInt(data[1]);
				System.out.println("Y: "+y+", Score: "+score);
				if (mpGameScreen == null) gameStateChanged(GameState.START_MP);
				mpGameScreen.updateOpponent(y, score);				
			} else {
				mpGameScreen.setLevel(decoded);
				Game.getInstance().pushState(mpGameScreen);
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
				startQuickGame();
		}
		
	}

	@Override
	public void gameStateChanged(GameState state) {
		System.out.println("GameState changed: "+state.toString());
		if (state == GameState.MESSAGE_UPDATED){
			Games.RealTimeMultiplayer.sendUnreliableMessageToOthers(getApiClient(), mpGameScreen.getMessage().getBytes(Charset.forName("UTF-8")), roomId);
		}
		if (state == GameState.START_MP){
			mpGameScreen = new MultiplayerGameScreen(this);
			currentGameScreen = mpGameScreen;
			setUpAndStartLevel();
			return;
		} else if (state == GameState.END_MATCH){

		} else if (state == GameState.START_SP){
			currentGameScreen = new GameScreen();
			Game.getInstance().pushState(currentGameScreen);
		}
	}
	
	private void sendUnreliableMessageToOthers(String message){
		Games.RealTimeMultiplayer.sendUnreliableMessageToOthers(getApiClient(), message.getBytes(Charset.forName("UTF-8")), roomId);
	}
	
	private void sendReliableMessageToOther(String message){
		Games.RealTimeMultiplayer.sendReliableMessage(getApiClient(), this, message.getBytes(Charset.forName("UTF-8")), roomId, otherId);
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
		switch (statusCode){
		case GamesStatusCodes.STATUS_OK:
			Game.getInstance().pushState(mpGameScreen);
			break;
		case GamesStatusCodes.STATUS_REAL_TIME_MESSAGE_SEND_FAILED:
			setUpAndStartLevel(); //Try again
			break;
		}
	}

}
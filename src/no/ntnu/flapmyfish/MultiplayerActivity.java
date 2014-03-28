package no.ntnu.flapmyfish;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import no.ntnu.flapmyfish.controller.StateListener;
import no.ntnu.flapmyfish.screens.MainMenuScreen;
import no.ntnu.flapmyfish.screens.MultiplayerGameScreen;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.example.games.basegameutils.BaseGameActivity;

/**
 * Developing a Real-time Multiplayer Game in Android:
 * https://developers.google.com/games/services/android/realtimeMultiplayer
 */
public class MultiplayerActivity extends BaseGameActivity implements RoomUpdateListener, RealTimeMessageReceivedListener, RoomStatusUpdateListener, StateListener {
	
	// request code for the "select players" UI
	// can be any number as long as it's unique
	final static int RC_SELECT_PLAYERS = 10000;
	
	// arbitrary request code for the waiting room UI.
	// This can be any integer that's unique in your Activity.
	final static int RC_WAITING_ROOM = 10002;

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
	
	protected MultiplayerGameScreen mpGameScreen;
	
	private void launchOpponentSelectionScreen(){
		// launch the player selection screen
		// minimum: 1 other player; maximum: 3 other players
		Intent intent = Games.RealTimeMultiplayer.getSelectOpponentsIntent(getApiClient(), 1, 3);
		startActivityForResult(intent, RC_SELECT_PLAYERS);
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
		Toast.makeText(this, "Sign in failed!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onSignInSucceeded() {
		//startQuickGame();
		//launchOpponentSelectionScreen();
		Toast.makeText(this, "Sign in succeeded!", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onLeftRoom(int statusCode, String roomId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRoomConnected(int statusCode, Room room) {
		System.out.println("ABC Creator ID: "+room.getCreatorId());
		System.out.println("ABC Participants ID: "+room.getParticipantIds());
		//Toast.makeText(this, "Participants: "+room.getParticipantIds(), Toast.LENGTH_SHORT);
		//participantId = room.getParticipantIds().get(1);
		//roomId = room.getRoomId();
		gameStateChanged(GameState.START);
	}
	
	String roomId; //participantId;

	@Override
	public void onRealTimeMessageReceived(RealTimeMessage message) {
		try {
			String decoded = new String(message.getMessageData(), "UTF-8");
			String[] data = decoded.split(" ");
			float y = Float.parseFloat(data[0]);
			int score = Integer.parseInt(data[1]);
			System.out.println("Y: "+y+", Score: "+score);
			if (mpGameScreen == null) gameStateChanged(GameState.START);
			mpGameScreen.updateOpponent(y, score);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void onConnectedToRoom(Room room) {
		// TODO Auto-generated method stub
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
				if (isSignedIn()){
					//launchOpponentSelectionScreen();
					startQuickGame();
					System.out.println("Option 1 chosen");
					return;
				}
				else {
					Toast.makeText(this, "You are not signed in", Toast.LENGTH_SHORT).show();
					System.out.println("Option 2 chosen");
					return;
				}
				//break;
		}
		
	}

	@Override
	public void gameStateChanged(GameState state) {
		if (state == GameState.MESSAGE_UPDATED){
			Games.RealTimeMultiplayer.sendUnreliableMessageToOthers(getApiClient(), mpGameScreen.getMessage().getBytes(Charset.forName("UTF-8")), roomId);
		}
	}
	
	@Override
	public void onActivityResult(int request, int response, Intent intent) {
	    if (request == RC_WAITING_ROOM) {
	        if (response == Activity.RESULT_OK) {
	            // (start game)
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


}
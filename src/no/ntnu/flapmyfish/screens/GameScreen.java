package no.ntnu.flapmyfish.screens;



import no.ntnu.flapmyfish.BackgroundLayer;
import no.ntnu.flapmyfish.MainActivity;
import no.ntnu.flapmyfish.LoopingBackgroundLayer;
import no.ntnu.flapmyfish.R;
import sheep.collision.CollisionLayer;
import sheep.game.State;
import sheep.game.World;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import sheep.audio.Audio;;

public class GameScreen extends State {

	private World world;
	private LoopingBackgroundLayer loopingBgLayer;
	private CollisionLayer colLayer;
	
	//Handles the audio and audio control 
	public static SoundPool soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
	public static MediaPlayer musicPlayer = null;
	public boolean musicShouldPlay = false;

	
	
	public GameScreen() {
		setUpBackGround();
		setUpCollisionLayer();
		initMusicPlayer();

	}
	
	
	public void initMusicPlayer(){
		
		//To avoid unnecessary re-instantiation 
		if(musicPlayer == null){
			//musicPlayer = MediaPlayer.create(this, R.raw.NAVN_PA_LYDFIL_HER);  .raw m� opprettes
			musicPlayer.setLooping(true);
			musicPlayer.setVolume(MainActivity.volume, MainActivity.volume);
		}
		
		// Reset song to position 0
		musicPlayer.seekTo(0);	

	public GameScreen() {
		init();
	}

	public void update(float dt) {
		world.update(dt);
	}


	public void draw(Canvas canvas) {
		world.draw(canvas);
	}

	private void init() {
		world = new World();

		loopingBgLayer = new LoopingBackgroundLayer(R.drawable.background1);
		world.addLayer(loopingBgLayer);

		colLayer = new CollisionLayer();
		world.addLayer(colLayer);
	}


	public World getWorld() {
		return world;
	}
	
	
}

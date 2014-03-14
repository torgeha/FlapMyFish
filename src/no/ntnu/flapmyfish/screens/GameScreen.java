package no.ntnu.flapmyfish.screens;




import no.ntnu.flapmyfish.BackgroundLayer;
import no.ntnu.flapmyfish.MainActivity;
import no.ntnu.flapmyfish.R;
import sheep.collision.CollisionLayer;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.game.World;
import sheep.graphics.Image;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import sheep.audio.Audio;;

public class GameScreen extends State {

	private World world;
	private BackgroundLayer bgLayer;
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
			//musicPlayer = MediaPlayer.create(this, R.raw.NAVN_PA_LYDFIL_HER);  .raw må opprettes
			musicPlayer.setLooping(true);
			musicPlayer.setVolume(MainActivity.volume, MainActivity.volume);
		}
		
		// Reset song to position 0
		musicPlayer.seekTo(0);	
	}
	
	
	public void update(float dt) {
		world.update(dt);
	}
	
	public void draw(Canvas canvas) {
		world.draw(canvas);
	}
	
	
	private void setUpBackGround() {
		world = new World();
		bgLayer = new BackgroundLayer();
		world.addLayer(bgLayer);
		Sprite bgSprite = new Sprite(new Image(R.drawable.background));
		bgLayer.addSprite(bgSprite);
	}
	
	private void setUpCollisionLayer() {
		colLayer = new CollisionLayer();
		world.addLayer(colLayer);
	}


	public World getWorld() {
		return world;
	}
	
	
}

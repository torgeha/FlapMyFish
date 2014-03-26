package no.ntnu.flapmyfish.screens;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.ExtendedLayer;
import no.ntnu.flapmyfish.LoopingBackgroundLayer;
import no.ntnu.flapmyfish.R;
import no.ntnu.flapmyfish.level.Level;
import no.ntnu.flapmyfish.level.LevelFactory;
import no.ntnu.flapmyfish.tokens.HorizontalBorder;
import no.ntnu.flapmyfish.tokens.Player;
import no.ntnu.flapmyfish.tokens.Score;
import sheep.collision.CollisionLayer;
import sheep.game.State;
import sheep.game.World;
import android.graphics.Canvas;

public class GameScreen extends State {

	private World world;
	private LoopingBackgroundLayer loopingBgLayer;
	private CollisionLayer colLayer;
	private ExtendedLayer foregroundLayer;
	private Level level;
	
	
	//Handles the audio and audio control 
	/*public static SoundPool soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
	public static MediaPlayer musicPlayer = null;
	public boolean musicShouldPlay = false;*/

	public GameScreen() {
		init();
	}

	@Override
	public void update(float dt) {
		world.update(dt);
		level.update(dt);
	}

	@Override
	public void draw(Canvas canvas) {
		world.draw(canvas);
	}

	private void init() {
		world = new World();

		loopingBgLayer = new LoopingBackgroundLayer(R.drawable.background_looper);
		world.addLayer(loopingBgLayer);

		colLayer = new CollisionLayer();
		world.addLayer(colLayer);
		
		foregroundLayer = new ExtendedLayer();
		world.addLayer(foregroundLayer);
		
		Player player = new Player(R.drawable.hero_fish_v2);
		addTouchListener(player);
		colLayer.addSprite(player);
		
		Score score = Score.getInstance();
		foregroundLayer.addSprite(score);
		HorizontalBorder bottomBorder = new HorizontalBorder(Constants.WINDOW_HEIGHT, 100);
		HorizontalBorder topBorder = new HorizontalBorder(0, -100);
		colLayer.addSprite(bottomBorder);
		colLayer.addSprite(topBorder);
		
		level = new Level(LevelFactory.generateLevel(), colLayer);
	}

	public World getWorld() {
		return world;
	}
}

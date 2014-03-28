package no.ntnu.flapmyfish.screens;

import no.ntnu.flapmyfish.ExtendedLayer;
import no.ntnu.flapmyfish.LoopingBackgroundLayer;
import no.ntnu.flapmyfish.R;
import no.ntnu.flapmyfish.level.Level;
import no.ntnu.flapmyfish.level.LevelFactory;
import no.ntnu.flapmyfish.tokens.CountDownTimer;
import no.ntnu.flapmyfish.tokens.Player;
import no.ntnu.flapmyfish.tokens.ScoreBoard;
import sheep.collision.CollisionLayer;
import sheep.game.State;
import sheep.game.World;
import android.graphics.Canvas;

public class GameScreen extends State {

	protected World world;
	protected ScoreBoard score;
	
	private LoopingBackgroundLayer loopingBgLayer;
	private CollisionLayer colLayer;
	private ExtendedLayer foregroundLayer;
	private Level level;
	private CountDownTimer countDownTimer;
	private Player player;

	public GameScreen() {
		init();
		world.update(0);
		level.update(0);
	}

	public void update(float dt) {
		if (countDownTimer.isFinished()){
			world.update(dt);
			level.update(dt);
			if (countDownTimer.hasAciveMessage()) countDownTimer.update(dt);
		} else {
			countDownTimer.update(dt);
		}
	}

	public void draw(Canvas canvas) {
		world.draw(canvas);
		if (!countDownTimer.isFinished() || countDownTimer.hasAciveMessage()) countDownTimer.draw(canvas);
	}
	
	public Player getPlayer(){
		return player;
	}
	
	protected void init() {
		world = new World();

		loopingBgLayer = new LoopingBackgroundLayer(R.drawable.background1);
		world.addLayer(loopingBgLayer);

		colLayer = new CollisionLayer();
		world.addLayer(colLayer);
		
		foregroundLayer = new ExtendedLayer();
		world.addLayer(foregroundLayer);
		
		int[] playerImgs = {R.drawable.hero_fish_frame1, R.drawable.hero_fish_frame2,
				R.drawable.hero_fish_frame3, R.drawable.hero_fish_frame2};
		player = new Player(playerImgs, 0.1f, 0);
		addTouchListener(player);
		colLayer.addSprite(player);
		
		score = new ScoreBoard(player);
		foregroundLayer.addSprite(score);
		
		countDownTimer = new CountDownTimer(3, "GO!");
		
		level = new Level(LevelFactory.generateLevel(), colLayer);
	}

	public World getWorld() {
		return world;
	}
}

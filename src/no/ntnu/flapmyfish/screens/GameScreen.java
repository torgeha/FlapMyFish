package no.ntnu.flapmyfish.screens;

import no.ntnu.flapmyfish.ExtendedLayer;
import no.ntnu.flapmyfish.LoopingBackgroundLayer;
import no.ntnu.flapmyfish.R;
import no.ntnu.flapmyfish.level.Level;
import no.ntnu.flapmyfish.level.LevelFactory;
import no.ntnu.flapmyfish.tokens.CountDownTimer;
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
	private CountDownTimer countDownTimer;

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
	
	private void init() {
		world = new World();

		loopingBgLayer = new LoopingBackgroundLayer(R.drawable.background1);
		world.addLayer(loopingBgLayer);

		colLayer = new CollisionLayer();
		world.addLayer(colLayer);
		
		foregroundLayer = new ExtendedLayer();
		world.addLayer(foregroundLayer);
		
		int[] playerImgs = {R.drawable.hero_fish_frame1, R.drawable.hero_fish_frame2,
				R.drawable.hero_fish_frame3, R.drawable.hero_fish_frame2};
		Player player = new Player(playerImgs, 0.1f, 0);
		addTouchListener(player);
		colLayer.addSprite(player);
		
		Score score = Score.getInstance();
		foregroundLayer.addSprite(score);
		
		countDownTimer = new CountDownTimer(3, "GO!");
		
		level = new Level(LevelFactory.generateLevel(), colLayer);
	}

	public World getWorld() {
		return world;
	}
}

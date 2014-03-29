package no.ntnu.flapmyfish.screens;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.ExtendedLayer;
import no.ntnu.flapmyfish.LoopingBackgroundLayer;
import no.ntnu.flapmyfish.MainActivity;
import no.ntnu.flapmyfish.R;
import no.ntnu.flapmyfish.controller.StateListener.GameState;
import no.ntnu.flapmyfish.level.Level;
import no.ntnu.flapmyfish.level.LevelFactory;
import no.ntnu.flapmyfish.tokens.CountDownTimer;
import no.ntnu.flapmyfish.tokens.Enemy;
import no.ntnu.flapmyfish.tokens.Food;
import no.ntnu.flapmyfish.tokens.Player;
import no.ntnu.flapmyfish.tokens.ScoreBoard;
import no.ntnu.flapmyfish.util.KillListener;
import sheep.collision.CollisionLayer;
import sheep.collision.CollisionListener;
import sheep.game.Sprite;
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
	private String levelString;

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

		loopingBgLayer = new LoopingBackgroundLayer(R.drawable.background);
		world.addLayer(loopingBgLayer);

		colLayer = new CollisionLayer();
		world.addLayer(colLayer);
		
		foregroundLayer = new ExtendedLayer();
		world.addLayer(foregroundLayer);
		
		int[] playerImgs = {R.drawable.hero_fish_frame1, R.drawable.hero_fish_frame2,
				R.drawable.hero_fish_frame3, R.drawable.hero_fish_frame2};
		player = new Player(playerImgs, 0.1f, 0);
		player.addCollisionListener(new PlayerCollisionListener());

		addTouchListener(player);
		colLayer.addSprite(player);
		
		score = new ScoreBoard(player);
		foregroundLayer.addSprite(score);
		
		countDownTimer = new CountDownTimer(3, "GO!");
		
		levelString = LevelFactory.generateLevel();
		level = new Level(levelString, colLayer, player);
	}
	
	public String getLevelString(){
		return levelString;
	}
	
	public void setLevel(String levelString){
		this.levelString = levelString;
		level = new Level(levelString, colLayer, player);
	}

	public World getWorld() {
		return world;
	}
	
	public int getPlayerScore(){
		return getPlayer().getPoints();
	}
	
	private class PlayerCollisionListener implements CollisionListener, KillListener {
		
		private Player player;
		/**
		 * Sprite a = player, Sprite b = other
		 */
		@Override
		public void collided(Sprite a, Sprite b) {
			player = (Player) a;
			if (b instanceof Food) {
				b.die();
				player.addPoints(Constants.FOOD_POINTS);
			}
			else if (b instanceof Enemy) {
				//TODO: kill player, save score if new highscore, game over.
				
				Enemy enemy = (Enemy)b;
				enemy.closeJaws();
				enemy.addKillListener(this);
				player.setSpeed(enemy.getSpeed());
				//TODO: run blood splatter animation
				//save score if new highscore
			}
			
		}
		
		public void fishKilled() {
			player.die();
			if (Constants.HIGHSCORE < player.getPoints()) {
				Constants.HIGHSCORE = player.getPoints();
			}
			MainActivity.getListenerInstance().gameStateChanged(GameState.END_MATCH);
			getGame().pushState(new GameOverScreen());
			
		}
	}
}

package no.ntnu.flapmyfish.screens;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.ExtendedLayer;
import no.ntnu.flapmyfish.LoopingBackgroundLayer;
import no.ntnu.flapmyfish.R;
import no.ntnu.flapmyfish.level.Level;
import no.ntnu.flapmyfish.level.LevelFactory;
import no.ntnu.flapmyfish.tokens.CountDownTimer;
import no.ntnu.flapmyfish.tokens.Enemy;
import no.ntnu.flapmyfish.tokens.Food;
import no.ntnu.flapmyfish.tokens.HorizontalBorder;
import no.ntnu.flapmyfish.tokens.Player;
import no.ntnu.flapmyfish.tokens.Score;
import no.ntnu.flapmyfish.util.KillListener;
import sheep.collision.CollisionLayer;
import sheep.collision.CollisionListener;
import sheep.game.Sprite;
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

		loopingBgLayer = new LoopingBackgroundLayer(R.drawable.background_looper);
		world.addLayer(loopingBgLayer);

		colLayer = new CollisionLayer();
		world.addLayer(colLayer);
		
		foregroundLayer = new ExtendedLayer();
		world.addLayer(foregroundLayer);
		
		int[] playerImgs = {R.drawable.hero_fish_frame1, R.drawable.hero_fish_frame2,
				R.drawable.hero_fish_frame3, R.drawable.hero_fish_frame2};

		player = new Player(playerImgs, 0.1f, 0);
		player.addCollisionListener(new PlayerCollisionLister());

		addTouchListener(player);
		colLayer.addSprite(player);
		
		Score score = Score.getInstance();
		foregroundLayer.addSprite(score);
		
		countDownTimer = new CountDownTimer(3, "GO!");
		
		level = new Level(LevelFactory.generateLevel(), colLayer, player);
	}

	public World getWorld() {
		return world;
	}
	
	private class PlayerCollisionLister implements CollisionListener, KillListener {
		
		private Player player;
		/**
		 * Sprite a = player, Sprite b = other
		 */
		@Override
		public void collided(Sprite a, Sprite b) {
			player = (Player) a;
//			if (b instanceof HorizontalBorder) {
//				HorizontalBorder hb = (HorizontalBorder) b;
//				if (hb.isTopBorder()) {
////					a.setPosition(a.getPosition().getX(), a.getPosition().getY()+player.getLastDelta()*Constants.PLAYER_FLAP_SPEED);
////					player.setAcceleration(player.getAcceleration().getX(), -2*Constants.PLAYER_FLAP_ACCELERATION);
//					player.setSpeed(0, -2000);
//					System.out.println("KRASJA I TOPPEN!");
//				}
//				else {
////					a.setPosition(a.getPosition().getX(), a.getPosition().getY()-player.getLastDelta()*Constants.PLAYER_FLAP_SPEED);
//					player.setAcceleration(player.getAcceleration().getX(), 2*Constants.PLAYER_FLAP_ACCELERATION);
//				}
//				a.setSpeed(0, 0);
//			}
			if (b instanceof Food) {
				b.die();
				Score.getInstance().addFoodPoints();
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
			if (Constants.HIGHSCORE < Score.getInstance().getPoints()) {
				Constants.HIGHSCORE = Score.getInstance().getPoints();
			}
			getGame().pushState(new GameOverScreen());
			
		}
	}
}

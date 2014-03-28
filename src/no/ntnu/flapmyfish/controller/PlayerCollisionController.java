package no.ntnu.flapmyfish.controller;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.tokens.Enemy;
import no.ntnu.flapmyfish.tokens.Food;
import no.ntnu.flapmyfish.tokens.HorizontalBorder;
import no.ntnu.flapmyfish.tokens.Player;
import no.ntnu.flapmyfish.tokens.ScoreBoard;
import sheep.collision.CollisionListener;
import sheep.game.Sprite;

public class PlayerCollisionController implements CollisionListener {

	
	/**
	 * Sprite a = player, Sprite b = other
	 */
	@Override
	public void collided(Sprite a, Sprite b) {
		/*if (b instanceof HorizontalBorder) {
			HorizontalBorder hb = (HorizontalBorder) b;
			if (hb.isTopBorder()) a.setPosition(a.getPosition().getX(), a.getPosition().getY()+player.getLastDelta()*Constants.PLAYER_FLAP_SPEED);
			else a.setPosition(a.getPosition().getX(), a.getPosition().getY()-player.getLastDelta()*Constants.PLAYER_FLAP_SPEED);
			a.setSpeed(0, 0);
		}*/
		if (b instanceof Food) {
			b.die();
			Player player = (Player) a;
			//Score.getInstance().addFoodPoints();
			player.addPoints(Constants.FOOD_POINTS);
		}
		else if (b instanceof Enemy) {
			//TODO: kill player, save score if new highscore, game over.
			
			//save score if new highscore
			/*if (Constants.HIGHSCORE < Score.getInstance().getPoints()) {
				Constants.HIGHSCORE = Score.getInstance().getPoints();
			}*/
		}
		
	}

}

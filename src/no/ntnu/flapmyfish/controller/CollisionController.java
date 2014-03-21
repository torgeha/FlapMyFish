package no.ntnu.flapmyfish.controller;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.tokens.Enemy;
import no.ntnu.flapmyfish.tokens.Food;
import no.ntnu.flapmyfish.tokens.HorizontalBorder;
import no.ntnu.flapmyfish.tokens.Player;
import no.ntnu.flapmyfish.tokens.Score;
import sheep.collision.CollisionListener;
import sheep.game.Sprite;

public class CollisionController implements CollisionListener {

	
	/**
	 * Sprite a = player, Sprite b = other
	 */
	@Override
	public void collided(Sprite a, Sprite b) {
		Player player = (Player) a;
		if (b instanceof HorizontalBorder) {
			HorizontalBorder hb = (HorizontalBorder) b;
			if (hb.isTopBorder()) a.setPosition(a.getPosition().getX(), a.getPosition().getY()+player.getLastDelta()*Constants.PLAYER_FLAP_SPEED);
			a.setSpeed(0, 0);
		}
		else if (b instanceof Food) {
			//TODO: remove food, add points.
			Score.getInstance().addFoodPoints();
		}
		else if (b instanceof Enemy) {
			//TODO: kill player, save score if new highscore, game over.
			
			//save score if new highscore
			if (Constants.HIGHSCORE < Score.getInstance().getPoints()) {
				Constants.HIGHSCORE = Score.getInstance().getPoints();
			}
		}
		
	}

}

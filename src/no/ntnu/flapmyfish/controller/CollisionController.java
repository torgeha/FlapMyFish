package no.ntnu.flapmyfish.controller;

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
			a.setSpeed(0, 0);
		}
		else if (b instanceof Food) {
			//TODO: remove food, add points.
			Score.getInstance().addFoodPoints();
		}
		else if (b instanceof Enemy) {
			//TODO: kill player, game over.
		}
		
	}

}

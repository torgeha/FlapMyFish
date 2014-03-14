package no.ntnu.flapmyfish.controller;

import no.ntnu.flapmyfish.tokens.Enemy;
import no.ntnu.flapmyfish.tokens.Food;
import no.ntnu.flapmyfish.tokens.InvisibleWall;
import sheep.collision.CollisionListener;
import sheep.game.Sprite;

public class CollisionController implements CollisionListener {

	
	/**
	 * Sprite a = player, Sprite b = other
	 */
	@Override
	public void collided(Sprite a, Sprite b) {
		if (b instanceof InvisibleWall) {
			//TODO: stop movement in y direction.
		}
		else if (b instanceof Food) {
			//TODO: remove food, add points.
		}
		else if (b instanceof Enemy) {
			//TODO: kill player, game over.
		}
		
	}

}

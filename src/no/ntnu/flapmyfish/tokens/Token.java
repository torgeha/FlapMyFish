package no.ntnu.flapmyfish.tokens;

import sheep.collision.CollisionListener;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class Token extends Sprite implements CollisionListener {
	
	public Token(Image img) {
		super(img);
	}
	
	@Override
	public void collided(Sprite a, Sprite b) {
		// TODO Auto-generated method stub
		
	}

}

package no.ntnu.flapmyfish.tokens;

import sheep.game.Sprite;
import sheep.graphics.Image;

public class Enemy extends Fish {
		
	public Enemy(Image img, float xPosition, float yPosition, float xSpeed) {
		super(img, xSpeed, xPosition, yPosition);
	}
	
	public void collided(Sprite a, Sprite b) {
		
	}
}
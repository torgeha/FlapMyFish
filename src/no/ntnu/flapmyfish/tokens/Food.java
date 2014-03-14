package no.ntnu.flapmyfish.tokens;

import sheep.game.Sprite;
import sheep.graphics.Image;

public class Food extends Fish {
	
	public Food(Image img, float xSpeed, float xPosition, float yPosition) {
		super(img, xSpeed, xPosition, yPosition);
	}
	
	public void collided(Sprite a, Sprite b) {
		
	}

}

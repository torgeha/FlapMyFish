package no.ntnu.flapmyfish.tokens;

import sheep.game.Sprite;
import sheep.graphics.Image;

public class Enemy extends Fish {
	
	private float xPosition;
	private float yPosition;
	private float xSpeed;
	
	public Enemy(Image img, float xPosition, float yPosition, float xSpeed) {
		super(img);
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.xSpeed = xSpeed;
	}
	
	public void collided(Sprite a, Sprite b) {
		
	}

	public float getxPosition() {
		return xPosition;
	}

	public void setxPosition(float xPosition) {
		this.xPosition = xPosition;
	}

	public float getyPosition() {
		return yPosition;
	}

	public void setyPosition(float yPosition) {
		this.yPosition = yPosition;
	}

	public float getxSpeed() {
		return xSpeed;
	}

	public void setxSpeed(float xSpeed) {
		this.xSpeed = xSpeed;
	}
}

package no.ntnu.flapmyfish.tokens;

import sheep.graphics.Image;

public class Fish extends Token {
	
	private float xPosition;
	private float yPosition;
	private float xSpeed;
	private float ySpeed;
	
	public Fish(Image img) {
		this(img, 0,0,0);
	}
	
	public Fish(Image img, float xSpeed, float xPosition, float yPosition) {
		super(img);
		this.xSpeed = xSpeed;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}
	
	public void move(){
		
		xPosition += xSpeed;
		yPosition += ySpeed;
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

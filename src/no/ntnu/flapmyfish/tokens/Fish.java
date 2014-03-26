package no.ntnu.flapmyfish.tokens;

import sheep.graphics.Image;
import sheep.graphics.SpriteView;

public class Fish extends Token {
	
	protected Image[] keyFrames;
	protected int currentFrame;
	protected float frameDuration;
	protected float frameTimeLeft;
	
	public Fish(int resId){
		super(new Image(resId));
	}
	
	public Fish(int[] keyFramesResIds, float frameDuration, int currentFrame) {
		super(new Image(keyFramesResIds[currentFrame]));
		
		this.keyFrames = new Image[keyFramesResIds.length];
		for(int i = 0; i < keyFramesResIds.length; i++) {
			keyFrames[i] = new Image(keyFramesResIds[i]);
		}
		this.currentFrame = currentFrame;
		this.frameDuration = frameDuration;
		frameTimeLeft = 0;
	}
	
	protected void updateAnimationFrame(float dt)
	{
		frameTimeLeft += dt;
		
		if(frameTimeLeft >= frameDuration)
		{
			currentFrame = (currentFrame + 1) % keyFrames.length;
			setView((SpriteView) keyFrames[currentFrame]);
			frameTimeLeft -= frameDuration;
		}
	}
}

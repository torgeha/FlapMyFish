package no.ntnu.flapmyfish.tokens;

import android.graphics.Canvas;
import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.R;
import no.ntnu.flapmyfish.util.KillListener;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.graphics.SpriteView;

public class Enemy extends Fish {
	
	private Image[][] keyFramesMatrix;
	private int currentBite;
	private boolean isEating;
	private boolean isAlignedWithTarget;
	private Fish target;
	private int numBloodSplashImgs;
	private float eatingFrameTimeLeft;
	private boolean hasReachedMaxBloodFrame;
	private int incValue;
	private KillListener killListener;
	
	public Enemy(int resId) {
		super(resId);
	}
	
	public Enemy(int[][] keyFramesResIds, float frameDuration, int currentFrame,
			int numBloodSplashImgs, Fish target) {
		super(keyFramesResIds[0][currentFrame]);
		
		this.keyFramesMatrix = new Image[keyFramesResIds.length][keyFramesResIds[0].length];
		for(int i = 0; i < keyFramesResIds.length; i++) {
			for(int j = 0; j < keyFramesResIds[i].length; j++) {
				keyFramesMatrix[i][j] = new Image(keyFramesResIds[i][j]);
			}
		}
		this.currentFrame = currentFrame;
		this.currentBite = keyFramesMatrix.length-1-numBloodSplashImgs;
		this.frameDuration = frameDuration;
		frameTimeLeft = 0;
		this.isEating = false;
		this.target = target;
		this.eatingFrameTimeLeft = 0;
		this.hasReachedMaxBloodFrame = false;
		this.numBloodSplashImgs = numBloodSplashImgs;
		this.incValue = 1;
	}
	
	public void addKillListener(KillListener listener) {
		killListener = listener;
	}

	public void initShape()
	{
		setShape(getWidth()/getScale().getX()/3.0f, getHeight()/getScale().getY()/3f);
		setShapeOffset(getWidth()/getScale().getX()/16, getHeight()/getScale().getY()/8f*3);
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		checkIfAlignedWithTarget();
		updateAnimationFrame(dt);
	}
	
	protected void updateAnimationFrame(float dt)
	{
		frameTimeLeft += dt;
		if(isEating)
		{
			eatingFrameTimeLeft += dt;
			if(eatingFrameTimeLeft >= Constants.EATING_FRAME_DURATION)
			{
				if(currentBite == keyFramesMatrix.length-1)
				{
					hasReachedMaxBloodFrame = true;
					killListener.fishKilled();
				}
				if(hasReachedMaxBloodFrame)
				{
					if(currentBite > keyFramesMatrix.length-1-numBloodSplashImgs)
					{
						currentBite--;
					}
				}
				else if(currentBite < keyFramesMatrix.length-1)
				{
					currentBite++;
				}
				
				eatingFrameTimeLeft -= Constants.EATING_FRAME_DURATION;
			}
		}
		if(frameTimeLeft >= frameDuration)
		{
			if(!isEating)
			{
				if(!isAlignedWithTarget && currentBite < keyFramesMatrix.length-1-numBloodSplashImgs)
				{
					currentBite++;
				}
				else if(isAlignedWithTarget && currentBite != 0)
				{
					currentBite--;
				}
			}
			
//			currentFrame = (currentFrame + 1) % keyFramesMatrix[currentBite].length;
			if(currentFrame == keyFramesMatrix[currentBite].length-1 ||
					currentFrame == 0)
			{
				incValue *= -1;
			}
			currentFrame += incValue;
			setView((SpriteView) keyFramesMatrix[currentBite][currentFrame]);
			frameTimeLeft -= frameDuration;
		}
	}
	
	private void checkIfAlignedWithTarget()
	{
		float enemyTop = getPosition().getY()-(getHeight()/2);
		float enemyBottom = getPosition().getY()+(getHeight()/2);
		float enemyRight = getPosition().getX()+(getWidth()/2);
		float targetTop = target.getPosition().getY()-(target.getHeight()/2);
		float targetBottom = target.getPosition().getY()+(target.getHeight()/2);
		float targetLeft = target.getPosition().getX()-(target.getWidth()/2);
		if((targetTop <= enemyBottom && targetTop >= enemyTop ||
				targetBottom <= enemyBottom && targetBottom >= enemyTop) &&
				targetLeft <= enemyRight)
		{
			isAlignedWithTarget = true;
		}
		else
		{
			isAlignedWithTarget = false;
		}
	}
	
	public void closeJaws()
	{
		isEating = true;
	}
}
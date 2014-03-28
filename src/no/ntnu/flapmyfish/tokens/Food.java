package no.ntnu.flapmyfish.tokens;

import no.ntnu.flapmyfish.Constants;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class Food extends Fish {
	
	public Food(int resId) {
		super(resId);
	}
	
	public Food(int[] keyFramesResIds, float frameDuration, int currentFrame) {
		super(keyFramesResIds, frameDuration, currentFrame);
	}
	
	public void initShape()
	{
//		setShape(getWidth(), getHeight());
//		setShape(getWidth()/2, getHeight()/1.2f);
//		setShapeOffset(-getWidth(), 0);
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		updateAnimationFrame(dt);
	}
}

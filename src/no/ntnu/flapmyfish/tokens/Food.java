package no.ntnu.flapmyfish.tokens;

public class Food extends Fish {
	
	public Food(int resId) {
		super(resId);
	}
	
	public Food(int[] keyFramesResIds, float frameDuration, int currentFrame) {
		super(keyFramesResIds, frameDuration, currentFrame);
	}
	
	public void initShape()
	{
		setShape(getWidth()/getScale().getX(), getHeight()/getScale().getY()/1.1f);
//		setShapeOffset(-getWidth(), 0);
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		updateAnimationFrame(dt);
	}
}

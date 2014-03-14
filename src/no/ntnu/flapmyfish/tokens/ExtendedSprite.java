package no.ntnu.flapmyfish.tokens;

import no.ntnu.flapmyfish.Constants;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class ExtendedSprite extends Sprite
{
//	private float imageWidth;
//	private float imageHeight;
	float xRatio;
	float yRatio;
	private float imageRatio;
	
	public ExtendedSprite(Image image)
	{
		super(image);
		float imageWidth = image.getWidth();
		float imageHeight = image.getHeight();
		xRatio = imageWidth/Constants.WINDOW_WIDTH;
		yRatio = imageHeight/Constants.WINDOW_HEIGHT;
		imageRatio = imageWidth/imageHeight;
	}
	
	public ExtendedSprite() {
		super();
	}
	
	/**
	 * Sets the size of the sprite relative to the screen size.
	 * 
	 * @param width
	 * @param height
	 */
	public void setSize(float width, float height)
	{
		setScale(width*xRatio, height*yRatio);
	}
	
	/**
	 * Sets the height of the sprite relative to the height of the screen.
	 * The width is set to preserve the original aspect ratio.
	 * @param height
	 */
	public void setSizeByHeight(float height)
	{
		float width = height*imageRatio*yRatio;
		setScale(width, height*yRatio);
	}
	
	/**
	 * Returns the height of the sprite relative to the screen height.
	 * @return
	 */
	public float getHeight()
	{
		return getScale().getY()/yRatio;
	}
}
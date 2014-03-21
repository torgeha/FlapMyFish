package no.ntnu.flapmyfish.tokens;

import no.ntnu.flapmyfish.Constants;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class ExtendedSprite extends Sprite
{
	private float imageWidth;
	private float imageHeight;
	float xRatio;
	float yRatio;
	private float imageRatio;
	
	public ExtendedSprite(Image image)
	{
		super(image);
		imageWidth = image.getWidth();
		imageHeight = image.getHeight();
		xRatio = Constants.WINDOW_WIDTH/imageWidth;
		yRatio = Constants.WINDOW_HEIGHT/imageHeight;
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
	public void setRelativeSize(float width, float height)
	{
		setScale(width*xRatio, height*yRatio);
		//TODO:
//		setShape(width, height);
//		setOffset(x, y);
	}
	
	/**
	 * Set the size of the sprite in pixels.
	 * 
	 * @param width
	 * @param height
	 */
	public void setSize(float width, float height) {
		setScale(width/imageWidth, height/imageHeight);
	}
	
	/**
	 * Sets the height of the sprite relative to the height of the screen.
	 * The width is set to preserve the original aspect ratio.
	 * @param height
	 */
	public void setSizeByHeight(float height)
	{
		float width = height*yRatio;
		setScale(width, height*yRatio);
		//TODO:
		//setShape(width, height);
		//setOffset(x, y);
	}
	
	/**
	 * Returns the width of the sprite relative to the screen width.
	 * @return
	 */
	public float getRelativeWidth()
	{
		return getScale().getX()/xRatio;
	}
	
	/**
	 * Returns the width of the sprite in pixels.
	 * @return
	 */
	public float getWidth()
	{
		return imageWidth*getScale().getX();
	}
	
	/**
	 * Returns the height of the sprite relative to the screen height.
	 * @return
	 */
	public float getRelativeHeight()
	{
		return getScale().getY()/yRatio;
	}
	
	/**
	 * Returns the height of the sprite in pixels.
	 * @return
	 */
	public float getHeight()
	{
		return imageHeight*getScale().getY();
	}
}
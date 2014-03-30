package no.ntnu.flapmyfish.gui;

import no.ntnu.flapmyfish.tokens.ExtendedSprite;
import no.ntnu.flapmyfish.util.GraphicsUtils;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.gui.TextButton;
import sheep.math.BoundingBox;
import android.graphics.Canvas;
import android.graphics.Color;

/**
 * ImageButton extends {@link TextButton} and is a button which displays an {@link Image} behind the text.
 */
public class ImageButton extends TextButton {
	
	private ExtendedSprite[] btn_backgrounds;
	
	/**
	 * Creates a new {@code ImageButton} at the current position with the specified label and images for the idle and down states.
	 * @param x The position along the x-axis.
	 * @param y The position along the y-axis.
	 * @param label The label of the button.
	 * @param sprites An array of {@link ExtendedSprite}-objects used for the idle and down states.
	 */
	public ImageButton(float x, float y, String label, ExtendedSprite[] sprites) {
		super(x, y, label, GraphicsUtils.createPaint(sprites[0].getHeight()/2f, Color.WHITE, true));
		btn_backgrounds = sprites;
		updateSpritePosition();
		setBoundingBox(new BoundingBox(getPosition().getX()-getWidth()/2, getPosition().getX()+getWidth()/2, getPosition().getY()-getHeight()/2, getPosition().getY()+getHeight()/2));
	}
	
	public float getWidth(){
		return btn_backgrounds[0].getWidth();
	}
	
	public float getHeight(){
		return btn_backgrounds[0].getHeight();
	}
	
	private void updateSpritePosition(){
		for (ExtendedSprite sprite : btn_backgrounds){
			sprite.setPosition(getPosition().getX(), getPosition().getY());
			sprite.update(0);
		}
	}
	
	@Override
	public void draw(Canvas canvas) {
		ExtendedSprite sprite = btn_backgrounds[getState()];
		sprite.draw(canvas);
		super.draw(canvas, getPosition().getX(), getPosition().getY()+sprite.getHeight()/8);
	}

}
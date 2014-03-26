package no.ntnu.flapmyfish.gui;

import no.ntnu.flapmyfish.util.GraphicsUtils;
import sheep.graphics.Image;
import sheep.gui.TextButton;
import sheep.math.BoundingBox;
import android.graphics.Canvas;
import android.graphics.Color;

/**
 * ImageButton extends {@link TextButton} and is a button which displays an {@link Image} behind the text.
 */
public class ImageButton extends TextButton {
	
	private Image[] btn_backgrounds;

	/**
	 * Creates a new {@code ImageButton} at the current position with the specified label and a single image for both the idle and down state.
	 * @param x The position along the x-axis.
	 * @param y The position along the y-axis.
	 * @param label The label of the button.
	 * @param img An {@link Image} which will be used for both the idle and down state.
	 */
	public ImageButton(float x, float y, String label, Image img) {
		this(x, y, label, new Image[]{img, img});
	}
	
	/**
	 * Creates a new {@code ImageButton} at the current position with the specified label and images for the idle and down states.
	 * @param x The position along the x-axis.
	 * @param y The position along the y-axis.
	 * @param label The label of the button.
	 * @param imgs An array of {@link Image}-objects used for the idle and down states.
	 */
	public ImageButton(float x, float y, String label, Image[] imgs) {
		super(x, y, label, GraphicsUtils.createPaint(imgs[0].getHeight()/2f, Color.WHITE, true));
		btn_backgrounds = imgs;
		setBoundingBox(new BoundingBox(getPosition().getX()-getWidth()/2, getPosition().getX()+getWidth()/2, getPosition().getY()-getHeight()/2, getPosition().getY()+getHeight()/2));
	}
	
	public float getWidth(){
		return btn_backgrounds[0].getWidth();
	}
	
	public float getHeight(){
		return btn_backgrounds[0].getHeight();
	}
	
	@Override
	public void draw(Canvas canvas) {
		Image img = btn_backgrounds[getState()]; 
		img.draw(canvas, getPosition().getX()-img.getWidth()/2, getPosition().getY()-img.getHeight()/2);
		super.draw(canvas, getPosition().getX(), getPosition().getY()+img.getHeight()/8);
	}	

}
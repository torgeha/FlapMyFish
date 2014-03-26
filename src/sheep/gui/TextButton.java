/**
 * Copyright (c) 2009 Anders Ruud
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


package sheep.gui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import sheep.graphics.Font;
import sheep.math.BoundingBox;
import sheep.math.Vector2;

/**
 * A TextButton is a simple borderless button which trigger
 * WidgetEvents when clicked.
 */
public class TextButton extends Widget {

	Vector2 position;
	String label;

	// The Paint to use for the IDLE and DOWN states.
	Paint paint[];
	
	// The BoundingBox of the textbutton.
	BoundingBox box;
	
	/**
	 * Button state idle.
	 */
	public static final int STATE_IDLE = 0;
	
	/**
	 * Button state down.
	 */
	public static final int STATE_DOWN = 1;
	
	/**
	 * Number of button states.
	 */
	public static final int STATE_SIZE = 2;
	
	// The current state.
	int state;
	
	/**
	 * Creates a new TextButton at the current position with the 
	 * specified label.
	 * @param x The position along the x-axis.
	 * @param y The position along the y-axis.
	 * @param label The label of the button.
	 */
	public TextButton(float x, float y, String label) {
		this(x, y, label, new Paint[]{Font.WHITE_SANS_BOLD_20, Font.BLUE_SANS_BOLD_20});
	}
	
	/**
	 * Creates a new TextButton at the current position with the 
	 * specified label.
	 * @param x The position along the x-axis.
	 * @param y The position along the y-axis.
	 * @param label The label of the button.
	 * @param paint An array of Paint used for the idle and down states.
	 */
	public TextButton(float x, float y, String label, Paint paint[]) {
		this(new Vector2(x, y), label, paint);
	}
	
	public TextButton(float x, float y, String label, Paint paint) {
		this(new Vector2(x, y), label, new Paint[] {paint, paint});
	}
	
	/**
	 * Creates a new TextButton at the current position with the 
	 * specified label.
	 * @param position The position of the button.
	 * @param label The label of the button.
	 * @param paint An array of Paint used for the idle and down states.
	 */
	public TextButton(Vector2 position, String label, Paint paint[]) {
		this.position = position;
		this.label = label;
		this.paint = paint;
		this.state = STATE_IDLE;
		
		// Fails silently.
		if(paint.length != STATE_SIZE)
			return;
		
		updateBox(label, paint);
	}

	/**
	 * Updates the BoundingBox of the button.
	 * @param label The label of the button.
	 * @param paint The paint objects for the button.
	 */
	private void updateBox(String label, Paint paint[]) {
		Rect bounds = new Rect();
		paint[0].getTextBounds(label, 0, label.length(), bounds);
		
		bounds.offset((int)position.getX(), (int)position.getY());
		
		this.box = new BoundingBox(bounds);
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawText(label, 0, label.length(), position.getX(), position.getY(), paint[state]);
	}
	
	public void draw(Canvas canvas, float x, float y) {
		canvas.drawText(label, 0, label.length(), x, y, paint[state]);
	}
	
	/**
	 * Gets the BoundingBox for this TextButton.
	 * @return
	 */
	public BoundingBox getBoundingBox() {
		return box;	
	}
	
	public void setBoundingBox(BoundingBox box) {
		this.box = box;
	}
	
	/**
	 * Gets the label of this TextButton.
	 * @return The label of this TextButton.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label of this TextButton.
	 * @param label The new label.
	 */
	public void setLabel(String label) {
		this.label = label;
	}


	@Override
	public boolean onTouchDown(MotionEvent event) {
		
		if(box.contains(event.getX(), event.getY())) {
			this.state = STATE_DOWN;
			return true;
		}
		
		return false;
	}

	@Override
	public boolean onTouchMove(MotionEvent event) {

		if(box.contains(event.getX(), event.getY())) {
			this.state = STATE_DOWN;
			return true;
		}
		
		this.state = STATE_IDLE;
		
		return false;
	}

	@Override
	public boolean onTouchUp(MotionEvent event) {
		
		
		if(box.contains(event.getX(), event.getY())) {
			this.state = STATE_IDLE;
			
			for(WidgetListener l : listeners)
				l.actionPerformed(new WidgetAction(this));
			
			return true;
		}
		
		return false;
	}
	
	public Vector2 getPosition(){
		return position;
	}
	
	/**
	 * Returns an {@code int} representing the state of this button.
	 * @return {@value #STATE_IDLE} if idle and {@value #STATE_DOWN} if button is pressed down. 
	 * @see {@link #STATE_IDLE}, {@link #STATE_DOWN}
	 */
	public int getState(){
		return state;
	}
	
}

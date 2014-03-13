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

package sheep.graphics;

import sheep.game.Game;
import sheep.math.BoundingBox;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;

/**
 * A Sprite representation which draws a transformed image onto
 * the Canvas.
 */
public class Image extends SpriteView {

	private Drawable drawable;
	
	/**
	 * Creates a new Image from the resource identifier.
	 * @param i The resource identifier.
	 */
	public Image(int i) {
		this(Game.getInstance().getResources().getDrawable(i));
	}
	
	/**
	 * Creates a new Image from the specified Drawable.
	 * @param drawable The Drawable to represent the Sprite.
	 */
	public Image(Drawable drawable) {
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		this.drawable = drawable;
	}
	
	/**
	 * Draws the Image at the specified position.
	 * @param canvas The Canvas to draw the Image on.
	 * @param x The position along the x-axis.
	 * @param y The position along the y-axis.
	 */
	public void draw(Canvas canvas, float x, float y) {
		canvas.save();
		canvas.translate(x, y);
		drawable.draw(canvas);
		canvas.restore();
	}
	
	@Override
	public void draw(Canvas canvas, Matrix transformation) {
		canvas.save();
		canvas.concat(transformation);
		drawable.draw(canvas);
		canvas.restore();
	}

	@Override
	public void update(float dt) {
		// Nothing happens.
	}
	
	/**
	 * Gets the width of this Image.
	 * @return The width of this Image.
	 */
	public float getWidth() {
		return drawable.getMinimumWidth();
	}
	
	/**
	 * Gets the height of this Image.
	 * @return The height of this Image.
	 */
	public float getHeight() {
		return drawable.getMinimumHeight();
	}

	/**
	 * Gets the BoundingBox of this Image.
	 * @return The BoundingBox of this Image.
	 */
	public BoundingBox getBoundingBox() {
		return new BoundingBox(getWidth(), getHeight());
	}
	
}

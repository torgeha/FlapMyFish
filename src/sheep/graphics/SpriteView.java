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

import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * This class is an abstraction over various visual representations for
 * Sprites. The loose coupling between the Sprite (model) and the SpriteView 
 * (view) makes it possible to present the same Sprite in different ways easily. 
 * For instance, one might want some Sprites drawn as static images, and others
 * as animations.
 */
public abstract class SpriteView {

	/**
	 * Updates the SpriteView based on the current timestep. This is needed 
	 * because some views may be dynamic, i.e. change according to time, e.g.
	 * animations. 
	 * 
	 * @param dt The time since last frame.
	 */
	public abstract void update(float dt);
	
	/**
	 * Draws the SpriteView with the given transformation.
	 * 
	 * @param canvas The Android Canvas to draw the Sprite onto.
	 * @param transformation The transformation to apply to the SpriteView.
	 */
	public abstract void draw(Canvas canvas, Matrix transformation);
}

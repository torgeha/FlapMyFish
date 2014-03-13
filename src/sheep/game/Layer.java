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

package sheep.game;

import sheep.math.BoundingBox;
import android.graphics.Canvas;

/**
 * Abstract class for all Layers. A Layer may be used in a World object to 
 * organize the game scene.
 */
public abstract class Layer {

	/**
	 * Called by World when this Layer should update its state, that is move
	 * objects, perform animations, and so forth.
	 * @param dt The timestep since last frame (seconds).
	 */
	public abstract void update(float dt);
	
	/**
	 * Called by World when this Layer should draw its state.
	 * @param canvas The Canvas object on which to draw the state.
	 * @param box The bounding box of the area of interest, or in other
	 * words: the camera.
	 */
	public abstract void draw(Canvas canvas, BoundingBox box);
}

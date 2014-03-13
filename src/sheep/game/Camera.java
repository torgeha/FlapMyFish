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
import sheep.math.Vector2;

/**
 * Can be attached to a World object and translate the entire scene according
 * its position.
 */
public class Camera {

	Vector2 position;
	Vector2 size;
	
	/**
	 * A camera at (-MAX, -MAX) with dimensions (MAX, MAX).
	 */
	public static final Camera INFINITY = new Camera(new Vector2(-Float.MAX_VALUE, -Float.MAX_VALUE), 
			new Vector2(Float.MAX_VALUE, Float.MAX_VALUE));
	
	/**
	 * A camera at (0, 0) with dimensions (MAX, MAX).
	 */
	public static final Camera ORIGO = new Camera(new Vector2(0, 0), 
			new Vector2(Float.MAX_VALUE, Float.MAX_VALUE));
	
	/**
	 * Creates a new Camera at (0, 0) with the specified size. The size of the
	 * camera is used to exclude sprites which are outside the camera bounding box
	 * from being drawn on screen.
	 * @param width The width of the Camera.
	 * @param height The height of the Camera.
	 */
	public Camera(float width, float height) {
		this(new Vector2(0, 0), new Vector2(width, height));
	}
	
	/**
	 * Creates a new Camera at the specified position with the specified size.
	 * @param position 
	 * @param size 
	 */
	public Camera(Vector2 position, Vector2 size) {
		this.position = position;
		this.size = size;
	}

	/**
	 * Gets the BoundingBox of this Camera.
	 * @return A BoundingBox for this Camera.
	 */
	public BoundingBox getBoundingBox() {
		return new BoundingBox(position.getX(), position.getX()+size.getX(), position.getY(), position.getY()+size.getY());
	}
	
	/**
	 * Gets the position of this Camera.
	 * @return The position.
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * Sets the position for this Camera.
	 * @param position The new position for the Camera.
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	/**
	 * Moves the Camera by (dx, dy).
	 * @param dx Distance to move along x-axis.
	 * @param dy Disatnce to move along y-axis.
	 */
	public void move(float dx, float dy) {
		this.position.move(dx, dy);
	}

	/**
	 * Gets the size of this Camera.
	 * @return The width and height.
	 */
	public Vector2 getSize() {
		return size;
	}

	/**
	 * Sets the size of this Camera.
	 * @param size The new size of the Camera.
	 */
	public void setSize(Vector2 size) {
		this.size = size;
	}
	
	/**
	 * Gets the width of this Camera.
	 * @return The width of this Camera.
	 */
	public float getWidth() {
		return size.getX();
	}
	
	/**
	 * Gets the height of this Camera.
	 * @return The height of this Camera.
	 */
	public float getHeight() {
		return size.getY();
	}
	
}

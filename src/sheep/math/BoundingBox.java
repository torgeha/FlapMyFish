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

package sheep.math;

/**
 * A bounding box defined by to pairs min/max limits along the
 * x and y axes.
 */
public class BoundingBox {

	// Limits of the bounding box in word coordinates.
	float xmin, xmax;
	float ymin, ymax;
	
	/**
	 * A BoundingBox with unlimited limits.
	 */
	public static final BoundingBox INFINITY = 
		new BoundingBox(-Float.MAX_VALUE, Float.MAX_VALUE, -Float.MAX_VALUE, Float.MAX_VALUE);
	
	/**
	 * Creates a new BoundingBox from the Android Rect.
	 * @param r The Rect to use as source.
	 */
	public BoundingBox(android.graphics.Rect r) {
		this(Math.min(r.left, r.right), Math.max(r.left, r.right),
				Math.min(r.top, r.bottom), Math.max(r.top, r.bottom));
	}
	
	/**
	 * Creates a new BoundingBox at the specified position with size 0
	 * along both axes.
	 * @param position
	 */
	public BoundingBox(Vector2 position) {
		this(position.getX(), position.getX(), position.getY(), position.getY());
	}

	/**
	 * Creates a new BoundingBox with the four limits defined.
	 * @param xmin The lower x-limit.
	 * @param xmax The upper x-limit.
	 * @param ymin The lower y-limit.
	 * @param ymax The upper y-limit.
	 */
	public BoundingBox(float xmin, float xmax, float ymin, float ymax) {
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
	}
	
	/**
	 * Creates a new BoundingBox at (0, 0) with the specified size.
	 * @param width The width of the BoundingBox.
	 * @param height The height of the BoundingBox.
	 */
	public BoundingBox(float width, float height) {
		this(0, width, 0, height);
	}
	
	/**
	 * Gets the BoundingBox represented as points in a float array. Can
	 * be used directly with the Polygon class.
	 * @return An array of 8 floats.
	 */
	public float[] getPoints() {
		return new float[]{ 
				xmin, ymin,
				xmin, ymax,
				xmax, ymax,
				xmax, ymin
		};
	}
	
	/**
	 * Checks whether a BoundingBox intersects another BoundingBox. The boxes also
	 * intersect if one box is completely inside the other.
	 * @param box The other BoundingBox to test.
	 * @return True if intersection, false otherwise.
	 */
	public boolean intersects(BoundingBox box) {
		return !((xmin > box.xmax || xmax < box.xmin) && (ymin > box.ymax || ymax < box.ymin));
	}
	
	/**
	 * Checks whether this BoundingBox completely contains another BoundingBox.
	 * @param box The other BoundingBox.
	 * @return True if this box contains the other, false otherwise.
	 */
	public boolean contains(BoundingBox box) {
		return (box.xmin >= xmin &&  box.xmax <= xmax) && (box.ymin >= ymin && box.ymax <= ymax);
	}
	
	/**
	 * Checks whether a point is contined within the BoundingBox.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean contains(float x, float y) {
		return (x >= xmin && x <= xmax) && (y >= ymin && y <= ymax);
	}
	
	@Override
	public String toString() {
		return "("+(int)xmin+","+(int)xmax+") ("+(int)ymin+","+(int)ymax+")";
	}
}

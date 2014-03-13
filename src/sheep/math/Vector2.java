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
 * A 2D Vector with basic operations like add, sub, length, 
 * normalize, and so forth. 
 */
public class Vector2 {

	// Position of the vector.
	private float x;
	private float y;
	
	// Commonly used fixed vectors.
	public static final Vector2 ZERO = new Vector2(0, 0);
	public static final Vector2 ONE = new Vector2(1, 1);
	
	/**
	 * Creates a new Vector2 (x, y).
	 * @param x The position along the x-axis.
	 * @param y The position along the y-axis.
	 */
	public Vector2(float x, float y) {
		this.setX(x);
		this.setY(y);
	}
	
	/**
	 * Sets the x-position of the vector.
	 * @param x The position along the x-axis.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Gets the x-position of the vector.
	 * @return The position along the x-axis.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets the y-position of the vector.
	 * @param y The position along the y-axis.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Gets the y-position of the vector.
	 * @return The position along the y-axis.
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Sets both components of the vector.
	 * @param x The position along the y-axis.
	 * @param y The position along the y-axis.
	 */
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Moves the vector by (dx, dy).
	 * @param dx The displacement along the x-axis.
	 * @param dy The displacement along the y-axis.
	 */
	public void move(float dx, float dy) {
		this.x += dx;
		this.y += dy;
	}
	
	/**
	 * Gets the opposite vector.
	 * @return A vector with both components negated.
	 */
	public Vector2 getNegated() {
		return new Vector2(-x, -y);
	}	
	
	/**
	 * Adds to vectors.
	 * @param v The other vector to add with this one.
	 * @return The sum of the vectors.
	 */
	public Vector2 getAdded(Vector2 v) {
		return new Vector2(x+v.getX(), y+v.getY());
	}
	
	/**
	 * Subtracts one vector from another.
	 * @param v The vector to subtract from this one.
	 * @return The resulting vector of the subtraction.
	 */
	public Vector2 getSubtracted(Vector2 v) {
		return new Vector2(x-v.getX(), y-v.getY());
	}
	
	/**
	 * Projects this vector onto another vector.
	 * @param v The vector to project this one onto.
	 * @return The result of the projection.
	 */
	public Vector2 getProjected(Vector2 v) {
		
		// Dot product.
		float dp = dot(v);
		
		// Length of other vector.
		float lsq = getLengthSquared();
		
		return new Vector2((dp/lsq)*v.getX(), (dp/lsq)*v.getY());
	}
	
	/**
	 * Gets a normal vector from this vector.
	 * @return The normal vector.
	 */
	public Vector2 getNormal() {
		// Right hand normal.
		return new Vector2(-y, x);
	}
	
	/**
	 * Gets a normalized version of this vector.
	 * @return The normalized vector.
	 */
	public Vector2 getNormalized() {
		float length = getLength();
		return new Vector2(x/length, y/length);
	}
	
	/**
	 * Negates this vector so that it points in the opposite
	 * direction.
	 */
	public void negate() {
		this.x *= -1;
		this.y *= -1;
	}
	
	/**
	 * Adds one vector into this vector.
	 * @param v The vector to add to this one.
	 */
	public void add(Vector2 v) {
		this.x += v.getX();
		this.y += v.getY();
	}
	
	/**
	 * Adds a scaled vector into this vector.
	 * @param v The vector to add to this one.
	 * @param f The scaling factor.
	 */
	public void add(Vector2 v, float f) {
		this.x += v.getX()*f;
		this.y += v.getY()*f;		
	}
	
	/**
	 * Subtracts a vector from this one.
	 * @param v The vector to subtract.
	 */
	public void subtract(Vector2 v) {
		this.x -= v.getX();
		this.y -= v.getY();
	}
	
	/**
	 * Multiples each components with a scalar.
	 * @param f The scalar to multiply the vector with.
	 */
	public void multiply(float f) {
		this.x *= f;
		this.y *= f;
	}

	/**
	 * Scale the x-component of the vector.
	 * @param f The scaling factor.
	 */
	public void multiplyX(float f) {
		this.x *= f;
	}
	
	/**
	 * Scale the y-component of the vector.
	 * @param f The scaling factor.
	 */
	public void multiplyY(float f) {
		this.y *= f;
	}
	
	/**
	 * Gets the dot product of this vector and another one.
	 * @param v The other vector.
	 * @return The dot product.
	 */
	public float dot(Vector2 v) {
		return x*v.getX()+y*getY();
	}
	
	/**
	 * Gets the dot product of this vector and an xy-pair.
	 * @param x X-component of the other vector.
	 * @param y Y-component of the other vector.
	 * @return The dot product.
	 */
	public float dot(float x, float y) {
		return this.x*x+this.y*y;
	}
	
	/**
	 * Gets the length of this vector.
	 * @return The length of this vector.
	 */
	public float getLength() {
		return (float)Math.sqrt((double)getLengthSquared());
	}
	
	/**
	 * Gets the square length of this vector. This is often all that
	 * is needed and saves one sqrt().
	 * @return The squared length of the vector.
	 */
	public float getLengthSquared() {
		return x*x+y*y;
	}
	
	/**
	 * Normalizes this vector.
	 * @return The length of old the vector.
	 */
	public float normalize() {
		float length = getLength();
		x /= length;
		y /= length;
		return length;
	}
	
	/**
	 * Gets a scaled version of this vector.
	 * @param f The scaling factor.
	 * @return The scaled vector.
	 */
	public Vector2 getMultiplied(float f) {
		return new Vector2(x*f, y*f);
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
	
}

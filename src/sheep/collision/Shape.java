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

package sheep.collision;

import sheep.math.BoundingBox;
import sheep.math.Vector2;
import android.graphics.Matrix;

/**
 * An abstraction over all shapes in the collision detection
 * system. Users can subclass this and create custom shapes 
 * if necessary.
 * 
 * In principle, Shapes do not have a position. They represent location
 * independent geometry, and their position at any given time is determined
 * by the current parent Sprite. 
 * 
 * The collision detection method follows the principle of the
 * template pattern. Some steps are fixed and some are abstract.
 */
public abstract class Shape {
	
	/**
	 * The high level collision detection function. It gets the axes
	 * from each of the shapes and check for separation on each one.
	 * @param shape The other Shape which may or may not collide with this Shape.
	 * @return True on collision, false otherwise.
	 */
	public boolean collides(Shape shape) {
		
		// Get the axes for both polygons.
		Vector2 a[] = getAxes();
		Vector2 b[] = shape.getAxes();
		
		for(int i = 0; i<a.length; i++)
		{
			if(separates(shape, a[i]))
				return false;
		}
		
		for(int i = 0; i<b.length; i++)
		{
			if(separates(shape, b[i]))
				return false;
		}
		
		return true;
	}
	
	/*
	 * @todo
	public Vector2 findResolutionVector(Shape shape) {

		// Get the axes for both polygons.
		Vector2 a[] = getAxes();
		Vector2 b[] = shape.getAxes();
		
		float minLengthSquared = -Float.MAX_VALUE;
		Vector2 minVector = null;
		
		for(int i = 0; i<a.length; i++)
		{
			float l_sq = a[i].getLengthSquared();
			
			if(l_sq < minLengthSquared){
				minLengthSquared = l_sq;
				minVector = a[i].getNormalized();
			}
		}
		
		for(int i = 0; i<b.length; i++)
		{
			float l_sq = b[i].getLengthSquared();
			
			if(l_sq < minLengthSquared){
				minLengthSquared = l_sq;
				minVector = b[i].getNormalized();
			}
		}
		
		if(minVector == null)
			return null;
		
		
		return minVector.getMultiplied(0);		
	} */
	
	/**
	 * Checks for separation between two shapes along a certain axis.
	 * @param shape The other Shape to check with this Shape.
	 * @param axis The axis which is to be tested.
	 * @return True if separate along the axis, false otherwise.
	 */
	public boolean separates(Shape shape, Vector2 axis) {
		
		Interval a_proj = project(axis);
		Interval b_proj = shape.project(axis);
		
		return a_proj.isSeparate(b_proj);
	}

	/**
	 * Gets the axes for this Shape. 
	 * @return A vector of all the axes for the Shape.
	 */
	public abstract Vector2[] getAxes();
	
	/**
	 * Projects this Shape onto the axis.
	 * @param axis The axis to project this Shape onto.
	 * @return The resulting Interval along the axis. 
	 */
	public abstract Interval project(Vector2 axis);
	
	/**
	 * Gets the BoundingBox of the Shape. This is used in particular during
	 * spatial partitioning to determine in which quadrant the Shape should
	 * be inserted.
	 * @return The BoundingBox of the Shape. 
	 */
	public abstract BoundingBox getBoundingBox();

	/**
	 * Updates the Shape according to the parent's transformation matrix. This can 
	 * also be used to animate the Shape.
	 * @param dt The timestep since last frame (in seconds).
	 * @param matrix The transformation matrix of the parent Sprite.
	 */
	public abstract void update(float dt, Matrix matrix);
		
}

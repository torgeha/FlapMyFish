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
 * An implementation of the Shape class for general convex polygons. This class
 * can (obviously) be used to represent or imitate common shapes, like rectangles
 * or circles. 
 */
public class Polygon extends Shape {

	// The (initial) points of the Polygon.
	private float points[];
	
	// The cached (current) points of the Polygon. (Updated by 
	// each call to the update method.
	private float cache[];
	
	// Storage for the separation axis of the polygon.
	private Vector2 axes[];
	
	// The number of vertices in the polygon.
	int size;
	
	/**
	 * Creates a new Polygon from the array. The array is packed with alternating
	 * x and y values, that is: {x0, y0, x1, y1, x2, y2, etc}.
	 * @param points The points from which to create the polygon.
	 */
	public Polygon(float points[]) {
		this.points = points;
		this.size = points.length/2;
		cache = new float[points.length];
		axes = new Vector2[points.length/2];
		
		for(int i = 0; i<axes.length; i++)
			axes[i] = new Vector2(0, 0);
	}
	
	@Override
	public void update(float dt, Matrix matrix) {
	
		matrix.mapPoints(cache, points);
		
		// Update axes.
		for(int i = 0;i<size;i++)
		{
			// Edge = (x1 - x0, y1 - y0)
			// Axis = (-Y, X) = (-(y1 - y0), (x1 - x0))
			float x0 = cache[(i)*2];
			float y0 = cache[(i)*2+1];
			float x1 = cache[((i+1)%size)*2];
			float y1 = cache[((i+1)%size)*2+1];
			axes[i].set(-(y1-y0), x1-x0);
		}
	}
		
	@Override
	public Vector2[] getAxes() {
		return axes;
	}

	@Override
	public Interval project(Vector2 axis) {
		
		float min = Float.MAX_VALUE; // +infinity
		float max = -Float.MAX_VALUE; // -infinity
		
		for(int i = 0; i<size; i++)
		{
			float d = axis.dot(cache[i*2], cache[i*2+1]);
			min = Math.min(min, d);
			max = Math.max(max, d);
		}
		
		return new Interval(min, max);
	}

	@Override
	public BoundingBox getBoundingBox() {
		
		float xmin = -Float.MIN_VALUE, ymin = -Float.MIN_VALUE;
		float xmax = Float.MAX_VALUE, ymax = Float.MAX_VALUE;				
		
		for(int i = 0; i<size; i++)
		{
			xmin = Math.min(xmin, cache[i*2]);
			xmax = Math.max(xmax, cache[i*2]);
			ymin = Math.min(ymin, cache[i*2+1]);
			ymax = Math.max(ymax, cache[i*2+1]);
		}		
		
		return new BoundingBox(xmin, xmax, ymin, ymax);
	}

}

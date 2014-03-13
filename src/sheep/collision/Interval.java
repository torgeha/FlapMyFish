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

/**
 * Contains an interval used in the collision detection algortihm. This class
 * exists only to make the high level algorithms more readable.
 */
public class Interval {

	// The bounds of the interval.
	float lower;
	float upper;

	/**
	 * Creates a new Interval with the specified bounds. It is NOT verified
	 * that lower < upper!
	 * @param lower The lower bound.
	 * @param upper The upper bound.
	 */
	public Interval(float lower, float upper) {
		this.lower = lower;
		this.upper = upper;
	}
	
	/**
	 * Gets overlap between the two Intervals. 
	 * @param i The other Interval which may or may not overlap.
	 * @return The overlap. May be negative.
	 */
	public float getOverlap(Interval i) {
		return Math.max(upper - i.lower, lower - i.upper);
	}
	
	/**
	 * Checks whether two Intervals are separate.
	 * @param i The other Interval which may or may not overlap.
	 * @return True on overlap, false otherwise.
	 */
	public boolean isSeparate(Interval i) {
		return lower > i.upper || upper < i.lower;
	}
	
	/**
	 * Converts the Interval to a String.
	 * @return A string on the form (lower, upper).
	 */
	@Override
	public String toString() {
		return "("+lower+","+upper+")";
	}

}

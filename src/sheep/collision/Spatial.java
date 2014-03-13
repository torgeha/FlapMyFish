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

import sheep.game.Sprite;
import sheep.math.BoundingBox;

/**
 * Interface for all spatial partitioning structures. 
 */
public interface Spatial {

	/**
	 * Inserts a Sprite into the structure. How a Sprite is inserted is up the
	 * subclass, and may be based on information found in the Sprite, like its
	 * BoundingBox.
	 * @param sprite The Sprite to insert.
	 */
	public void insert(Sprite sprite);
	
	/**
	 * Visits all Sprites which intersect a certain area.
	 * @param visitor The visitor callback.
	 * @param box The BoundingBox for the area of interest.
	 */
	public void visit(SpatialVisitor visitor, BoundingBox box);
	
	/**
	 * Clears all sprites from the structure. When rebuilding the structure, 
	 * it is usually more efficient to clear the elements than to reallocate 
	 * the entire structure.
	 */
	public void clear();
	
}

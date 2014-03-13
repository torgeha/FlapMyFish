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

import java.util.LinkedList;
import sheep.game.Sprite;
import sheep.math.BoundingBox;

/**
 * Implements a "flat" partitioning as a reference. If there aren't that many 
 * objects in the scene, this is probably faster than a QuadTree.
 */
public class Flat implements Spatial {

	// The list of sprites.
	LinkedList<Sprite> sprites;
	
	/**
	 * Constructor.
	 */
	public Flat() {
		sprites = new LinkedList<Sprite>();
	}
	
	@Override
	public void clear() {
		sprites.clear();
	}

	@Override
	public void insert(Sprite sprite) {
		sprites.add(sprite);
	}

	@Override
	public void visit(SpatialVisitor visitor, BoundingBox box) {
		for(Sprite s : sprites){
			visitor.encounter(s);
		}
	}

}

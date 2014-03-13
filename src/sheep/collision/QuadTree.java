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
 * A QuadTree is a way of dividing the game scene into quadrants recursively. 
 * By putting sprites into the lower levels nodes of the tree, one can exclude 
 * iteration of far away sprites during collision detection.
 */
public class QuadTree implements Spatial {

	// The root quad. 
	private Quad root;
	
	// The boundaries of the world.
	private float xmin, xmax, ymin, ymax;
	
	// The recursion depth. 
	private int depth;
	
	// A list of pointers to all lists in the tree. This makes it faster
	// to clear the entire quadtree.
	LinkedList<LinkedList<Sprite>> lists;
	
	/**
	 * Creates a new QuadTree using the boundaries and depth of the old 
	 * quad tree.
	 * @param tree The old quad tree.
	 */
	public QuadTree(QuadTree tree) {
		this(tree.xmin, tree.xmax, tree.ymin, tree.ymax, tree.depth);
	}
	
	/**
	 * Creates a new QuadTree with boundaries x = [-width, width], and
	 * y = [-height, height].
	 * @param width The half-width of the world.
	 * @param height The half-height of the world.
	 * @param depth The number of recursion steps.
	 */
	public QuadTree(float width, float height, int depth) {
		this(-width, width, -height, height, depth);
	}
	
	/**
	 * Creates a new QuadTree with the given boundaries and recursion depth.
	 * @param xmin The lower boundary on the x-axis.
	 * @param xmax The upper boundary on the x-axis.
	 * @param ymin The lower boundary on the y-axis.
	 * @param ymax The upper boundary on the y-axis.
	 * @param depth The number of recursion steps.
	 */
	public QuadTree(float xmin, float xmax, float ymin, float ymax, int depth) {
		this.lists = new LinkedList<LinkedList<Sprite>>();
		root = new Quad(this, xmin, xmax, ymin, ymax, depth);
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
		this.depth = depth;
	}
	
	/**
	 * Adds a sprite list to the lists of sprite lists. The list
	 * of sprite lists is used to clear the quadtree quickly.
	 * @param list The list to add to the "list of lists".
	 */
	public void add(LinkedList<Sprite> list) {
		lists.add(list);
	}
	
	@Override
	public void visit(SpatialVisitor visitor, BoundingBox box) {
		if(root.intersects(box)) {
			root.visit(visitor, box);
		}
	}	
	
	@Override
	public void insert(Sprite sprite) {
		root.insert(sprite);
	}	
	
	@Override
	public void clear() {
		for(LinkedList<Sprite> list : lists) {
			list.clear();
		}
	}
}

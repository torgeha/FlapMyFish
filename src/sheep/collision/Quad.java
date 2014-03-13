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
import sheep.game.SpriteContainer;
import sheep.math.BoundingBox;

/**
 * The node in the QuadTree. Each Quad contains four other Quads
 * (unless it is a root node).
 */
public class Quad implements SpriteContainer {

	// The four child quads. This may be null if the Quad is a 
	// leaf node.
	private Quad quads[];
	
	// The bounding box of this node.
	private BoundingBox box;
	
	/**
	 * The number of children each non-leaf quad has (4).
	 */
	public static final int NUM_CHILDREN = 4;
	
	// List of sprites in this quad.
	private LinkedList<Sprite> sprites;
	
	/**
	 * Creates a new Quad, and possibly four child Quads (and so on). 
	 * @param tree The parent tree.
	 * @param xmin The lower boundary on the x-axis.
	 * @param xmax The upper boundary on the x-axis.
	 * @param ymin The lower boundary on the y-axis.
	 * @param ymax The upper boundary on the y-axis.
	 * @param depth The number of recursion steps. When this hits 0 we're at a child node.
	 */
	public Quad(QuadTree tree, float xmin, float xmax, float ymin, float ymax, int depth) {
		
		sprites = new LinkedList<Sprite>();
		tree.add(sprites);
		
		// Create the bounding box. 
		box = new BoundingBox(xmin, xmax, ymin, ymax);
		
		// Create children if not last  level.
		if(depth - 1 > 0)
		{
			// Allocate memory.
			quads = new Quad[NUM_CHILDREN];
					
			// The interval centers.
			float xc = xmin + 0.5f*(xmax - xmin);
			float yc = ymin + 0.5f*(ymax - ymin);
			
			// Initialize children.
			quads[0] = new Quad(tree, xmin, xc, ymin, yc, depth-1); // Top left.
			quads[1] = new Quad(tree, xc, xmax, ymin, yc, depth-1); // Top right.
			quads[2] = new Quad(tree, xmin, xc, yc, ymax, depth-1); // Bottom left.
			quads[3] = new Quad(tree, xc, xmax, yc, ymax, depth-1); // Bottom right.
			
		}
	}
	
	/**
	 * Checks whether this Quad fully contains the BoundingBox.
	 * @param box The BoundingBox to check for "collisions" with this Quad.
	 * @return True if it fully contains the box, false if it's outside or intersects the boundaries.
	 */
	public boolean contains(BoundingBox box) {
		return this.box.contains(box);
	}
	
	/**
	 * Checks whether this Quad intersects with the BoundingBox (including the case of
	 * the box being completely inside the Quad).
	 * @param box
	 * @return
	 */
	public boolean intersects(BoundingBox box) {
		return this.box.intersects(box);
	}
	
	/**
	 * Visits all Sprites in this Quad and continue into child nodes
	 * which intersect the BoundingBox.
	 * @param visitor The visitor callback.
	 * @param box The BoundingBox for the area of interest.
	 */
	public void visit(SpatialVisitor visitor, BoundingBox box) {
		
		// Visit sprites in this node.
		for(Sprite sprite : sprites) {
			visitor.encounter(sprite);
		}
		
		// Visit child quads.
		if(quads != null) {
			for(int i = 0; i<NUM_CHILDREN; i++) {
				if(quads[i].intersects(box)) {
					quads[i].visit(visitor, box);
				}
			}
		}
		
	}

	/**
	 * Tried to insert the Sprite into a child Quad which completely
	 * contains the Sprite, or, if that fails, inserts it into this quad.
	 * @param sprite The Sprite to insert into the Quad (or its children).
	 */
	public void insert(Sprite sprite) {
		
		// Try to insert into one of the children.
		if(quads != null) {
			for(int i = 0; i<NUM_CHILDREN; i++) {
				if(quads[i].contains(sprite.getBoundingBox())) {
					quads[i].insert(sprite);
					return;
				}
			}
		}
		
		// Can't insert into children; insert here.
		addSprite(sprite);		
	}
	
	@Override
	public void addSprite(Sprite sprite) {
		sprites.add(sprite);
	}
	
	@Override
	public void removeSprite(Sprite sprite) {
		sprites.remove(sprite);
	}
	
	public String toString() {
		return box.toString();
	}
	
}

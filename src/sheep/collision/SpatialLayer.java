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
import sheep.game.Layer;
import sheep.game.Sprite;
import sheep.game.SpriteContainer;
import sheep.math.BoundingBox;
import android.graphics.Canvas;

/**
 * A layer which can partition sprites according to a Spatial implementor. For
 * instance, one could use a QuadTree to split up the scene into quads recursively.
 */
public class SpatialLayer extends Layer implements SpriteContainer {

	// Contains all sprites in a flat list. Spatial partitioners may rebuild the
	// structure each frame, and this is the source used for those rebuilds.
	LinkedList<Sprite> sprites;
	
	// In order to avoid iteration errors, a separate list is needed to temporarily
	// contain all sprites that "died" during the last frame. After all sprites have
	// been updated, the dead sprites (the ones present in this list) will be removed
	// from the master list.
	LinkedList<Sprite> kill;
	
	// This is the spatial partitioner. By default, a "flat" partitioner will be used.
	Spatial spatial;
	
	// Visitor for drawing sprites. 
	DrawSpriteVisitor drawSpriteVisitor;
	
	// Visitor for executing collision detection between sprites.
	CollideSpriteVisitor collideSpriteVisitor;
	
	/**
	 * Creates a new SpatialLayer with flat partitioning.
	 */
	public SpatialLayer() {
			
		// Creates a "flat" partitioner by default.
		spatial = new Flat();
		
		drawSpriteVisitor = new DrawSpriteVisitor();
		collideSpriteVisitor = new CollideSpriteVisitor();
		
		sprites = new LinkedList<Sprite>();
		kill = new LinkedList<Sprite>();
	}
	
	@Override
	public void draw(Canvas canvas, BoundingBox box) {
		// The drawSpriteVisitor needs to know which canvas to use.
		drawSpriteVisitor.canvas = canvas;
		
		// Draw all sprites intersecting the specified box.
		spatial.visit(drawSpriteVisitor, box);
	}

	@Override
	public void update(float dt) {
				
		// Clear the partitioner.
		spatial.clear();
		
		for(Sprite s : sprites) {
			
			// Insert sprites into the partitioner.
			spatial.insert(s);
			
			// This moves the sprite's position, orientation etc.
			s.update(dt);
		}

		for(Sprite s : sprites) {
			collideSpriteVisitor.self = s;
			spatial.visit(collideSpriteVisitor, s.getBoundingBox());
		}
		
		// Remove killed sprites.
		for(Sprite s : kill) {
			sprites.remove(s);
		}
		
		kill.clear();

	}
	
	@Override
	public void addSprite(Sprite sprite) {
		sprites.add(sprite);
		sprite.setParent(this);
	}

	@Override
	public void removeSprite(Sprite sprite) {
		sprites.remove(sprite);
		sprite.setParent(null);
	}
	

	/**
	 * A visitor which draws sprites.
	 */
	private class DrawSpriteVisitor implements SpatialVisitor {

		Canvas canvas;
				
		public void encounter(Sprite sprite) {
			sprite.draw(canvas);
		}
		
	}
	
	/**
	 * A visitor which executes collisions between sprites.
	 */
	private class CollideSpriteVisitor implements SpatialVisitor {
		
		Sprite self;
		
		public void encounter(Sprite sprite) {
			if(sprite != self) {
				self.collides(sprite);
			}
		}
	}
	
}

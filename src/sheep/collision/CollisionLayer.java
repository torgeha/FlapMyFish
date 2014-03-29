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
import java.util.Vector;
import sheep.game.Layer;
import sheep.game.Sprite;
import sheep.game.SpriteContainer;
import sheep.math.BoundingBox;
import android.graphics.Canvas;

/**
 * A layer which contains Sprites and generates collisions between them, 
 * but without the overhead a spatial partitioning. 
 */
public class CollisionLayer extends Layer implements SpriteContainer {

	// The vector of all sprites.
	Vector<Sprite> sprites;
	
	// In order to avoid iteration errors, a separate list is needed to temporarily
	// contain all sprites that "died" during the last frame. After all sprites have
	// been updated, the dead sprites (the ones present in this list) will be removed
	// from the master list.
	LinkedList<Sprite> kill;
 	
	/**
	 * Constructor. Allocates the Sprite lists.
	 */
	public CollisionLayer() {
		sprites = new Vector<Sprite>();
		kill = new LinkedList<Sprite>();
	}
	
	@Override
	public void draw(Canvas canvas, BoundingBox box) {
//		for(int i = 0; i < sprites.size(); i++) {
		for(int i = sprites.size()-1; i >= 0; i--) {
			sprites.elementAt(i).draw(canvas);
		}
	}

	@Override
	public void update(float dt) {
		
		int size = sprites.size();
		
		for(int i = 0; i < size; i++) {
			sprites.elementAt(i).update(dt);
		}
		
		for(int i = 0; i < size; i++) {
			for(int j = i+1; j < size; j++) {
				sprites.elementAt(i).collides(sprites.elementAt(j));
			}
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
		kill.add(sprite);
		sprite.setParent(null);
	}

}

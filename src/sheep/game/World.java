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

package sheep.game;

import java.util.*;
import android.graphics.Canvas;

/**
 * A World is a simply a collection of Layers and a Camera.
 */
public class World {

	// List of all layers.
	LinkedList<Layer> layers;

	// By default, the camera will be at (0, 0) with unlimited size.
	Camera camera;
	
	/**
	 * Creates a new World with a stationary camera.
	 */
	public World() {
		this(Camera.ORIGO);
	}
	
	/**
	 * Creates a new World with a specified camera.
	 * @param camera The Camera to use for the world.
	 */
	public World(Camera camera) {
		this.camera = camera;
		layers = new LinkedList<Layer>();
	}
	
	/**
	 * Adds a Layer to the World.
	 * @param layer The Layer to add.
	 */
	public void addLayer(Layer layer) {
		layers.add(layer);
	}
	
	/**
	 * Removes a Layer from the World.
	 * @param layer The Layer to remove.
	 */
	public void removeLayer(Layer layer) {
		layers.remove(layer);
	}
	
	/**
	 * Updates all the Layers in this World.
	 * @param dt The timestep since last frame (seconds).
	 */
	public void update(float dt) {
		for(Layer l : layers) {
			l.update(dt);
		}
	}
	
	/**
	 * Applies Camera transformations and draws all the Layers in this World.
	 * @param canvas The Canvas onto which the World will be drawn.
	 */
	public void draw(Canvas canvas) {
		
		canvas.save();
		canvas.translate(-camera.getPosition().getX(), -camera.getPosition().getY());		
		
		for(Layer l : layers) {
			l.draw(canvas, camera.getBoundingBox());
		}
		
		canvas.restore();
	}

	/**
	 * Gets the Camera in use by this World.
	 * @return The Camera of this World.
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * Sets a new Camera for this World.
	 * @param camera The new Camera for this World.
	 */
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
}

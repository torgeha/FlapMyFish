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

import java.util.LinkedList;
import sheep.collision.CollisionListener;
import sheep.collision.Polygon;
import sheep.collision.Rectangle;
import sheep.collision.Shape;
import sheep.graphics.Image;
import sheep.graphics.SpriteView;
import sheep.math.BoundingBox;
import sheep.math.Vector2;
import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * The Sprite represents superclass of the "models" of the game objects. It contains
 * position, speed, scale and other common properties. It can optionally contain a Shape
 * and be used in the collision detection system.
 * 
 * The Sprite has no graphical representation, it contains only a reference to a SpriteView
 * objects, which knows how to draw the Sprite based on its current transformation. Having a
 * SpriteView is also optional; it is possible to have invisible Sprites which act as "sensors"
 * in the collision detection system.
 * 
 * Sprite objects may also generate collision events, and classes implementing CollisionListener 
 * may listen to these events. 
 */
public class Sprite {

	Vector2 position = new Vector2(0.0f, 0.0f);
	Vector2 speed = new Vector2(0.0f, 0.0f);
	Vector2 acceleration = new Vector2(0.0f, 0.0f);
	
	Vector2 offset = new Vector2(0.0f, 0.0f);
	float orientation = 0.0f;
	Vector2 scale = new Vector2(1.0f, 1.0f);
	
	// The graphical representation of the Sprite (optional).
	SpriteView view;
	
	// The "physical" representation of the Sprite (optional).
	Shape shape;
	
	// The object which contains this Sprite.
	SpriteContainer parent;
	
	// All objects listening to collision events from this Sprite.
	LinkedList<CollisionListener> collisionListeners;
	
	// The transformation matrix for this Sprite. This is updated each
	// frame.
	Matrix matrix;
	
	// The groups and group mask.
	long group = 0x0;
	long mask = 0x0;
	
	/**
	 * Creates a new Sprite with no Shape and no SpriteView.
	 */
	public Sprite() {
		this((SpriteView)null);
	}
	
	/**
	 * Creates a Sprite from the given Image. The Shape is automatically
	 * generated from the Image dimensions, and the offset is automatically
	 * set to the center of the image.
	 * @param image The Image to represent the Sprite visually.
	 */
	public Sprite(Image image) {
		this((SpriteView)image);
		
		this.offset.setX(image.getWidth()/2.0f);
		this.offset.setY(image.getHeight()/2.0f);
		
		shape = new Polygon(image.getBoundingBox().getPoints());
	}
	
	/**
	 * Creates a new Sprite with the specified SpriteView. No Shape is
	 * created for the Sprite, the and the offset remains (0, 0).
	 * @param view The SpriteView for this Sprite.
	 */
	public Sprite(SpriteView view) {
		this.collisionListeners = new LinkedList<CollisionListener>();
		this.view = view;
		matrix = new Matrix();
		updateMatrix();
	}
	
	/**
	 * Moves the Sprite, updates the transformation matrix and updates
	 * view and shape.
	 * @param dt The time step since last frame (seconds).
	 */
	public void update(float dt){
		// Accelerate
		speed.add(acceleration, dt);
		position.add(speed, dt);
				
		updateMatrix();
		
		if(view != null)
			view.update(dt);
		
		if(shape != null)
			shape.update(dt, matrix);
	}
	
	/**
	 * Draws this Sprite on the Canvas.
	 * @param canvas The Canvas to draw the Sprite onto.
	 */
	public void draw(Canvas canvas) {
		if(view != null)
			view.draw(canvas, matrix);
	}
	
//	/**
//	 * Updates the current transformation matrix.
//	 */
//	private void updateMatrix() {
//		matrix.reset();
//		matrix.preTranslate(position.getX() - offset.getX(), position.getY() - offset.getY());
//		matrix.preRotate(orientation);
//		matrix.preScale(scale.getX(), scale.getY());
//	}
	
	
	//XXX: Possible bug fixed.
	//Changed the code here, to get the flipping of sprites right.
	//Now the sprite is flipped around the offset point.
	//(Can also fix the flipping-bug by inverting the offset in the relevant sprite.
	//That is, e.g. to write "setOffset(-getOffset().getX(), getOffset().getY());" when
	//"setScale(-getScale().getX(), getScale().getY());" is used.)
	/**
	 * Updates the current transformation matrix.
	 */
	private void updateMatrix() {
		matrix.reset();
		matrix.preTranslate(position.getX(), position.getY());
		matrix.preRotate(orientation);
		matrix.preScale(scale.getX(), scale.getY());
		matrix.preTranslate(-offset.getX(),-offset.getY());
	}
	
	/**
	 * Sets the parent SpriteContainer. If the Sprite knows its parent container, 
	 * it can remove itself from it.
	 * @param parent The SpriteContainer holding the Sprite.
	 */
	public void setParent(SpriteContainer parent) {
		this.parent = parent;
	}
	
	/**
	 * Sets a new Shape used for collision detection.
	 * @param shape The new Shape for this Sprite.
	 */
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	
	/**
	 * Sets a new rectangular Shape for this Sprite.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 */
	public void setShape(float width, float height) {
		this.shape = new Rectangle(width, height);
	}
	
	/**
	 * Sets the group bits for this Sprite. Each set bit (1) indicates
	 * membership of the corresponding group. For instance, to make a Sprite
	 * a member of group 1 and 3 (but no others) set the group bits to 0x10, which
	 * in binary is 0101.
	 * @param group The group bits.
	 */
	public void setGroup(long group) {
		this.group = group;
	}
	
	/**
	 * Gets the group bits for this Sprite. See setGroup.
	 * @return
	 */
	public long getGroup() {
		return this.group;
	}
	
	/**
	 * Adds a group to the Sprite. In order to add a Sprite to group 5, just
	 * call addGroup(5).
	 * @param n The group number (0-63).
	 */
	public void addGroup(int n) {
		this.group = this.group | (1 << n);
	}
	
	/**
	 * Sets the group mask for this Sprite. Each set bit in this mask indicates that
	 * the Sprite should NOT collide with other members of that group. For instance,
	 * to keep this Sprite from colliding with members of group 3 and 4, call
	 * setMask(0x24), which in binary is 00011.
	 * @param mask The collision mask bits for this Sprite.
	 */
	public void setMask(long mask) {
		this.mask = mask;
	}
	
	/**
	 * Sets a collision mask bit. For instance, to keep this Sprite from colliding with 
	 * groups 3 and 4, just call addMask(3) and addMask(4).
	 * @param n The group number to keep this Sprite from colliding with.
	 */
	public void addMask(int n) {
		this.mask = this.mask | (1 << n);
	}
	
	/**
	 * Gets the mask bits for this Sprite.
	 * @return The mask bits for this Sprite.
	 */
	public long getMask() {
		return this.mask;
	}
	
	/**
	 * Removes this Sprite from its parent continer. If no parent is set, calling this
	 * function has no effect.
	 */
	public void die() {
		
		// Remove the sprite from parent.
		if(parent != null)
			this.parent.removeSprite(this);
		
	}
	
	/**
	 * Sets a new SpriteView for this Sprite.
	 * @param view The new SpriteView for this Sprite.
	 */
	public void setView(SpriteView view) {
		this.view = view;
	}
	
	/**
	 * Gets the current SpriteView for this Sprite.
	 * @return The current SpriteView for this Sprite.
	 */
	public SpriteView getView() {
		return this.view;
	}
	
	/**
	 * Gets the BoundingBox of this Sprite. If the Sprite has no shape, the BoundingBox 
	 * will have dimensions (0, 0). 
	 * @return The BoundingBox of this Sprite.
	 */
	public BoundingBox getBoundingBox() {
		if(shape == null)
			return new BoundingBox(position);
		return shape.getBoundingBox();
	}

	/**
	 * Gets the current position of the Sprite.
	 * @return The current position of the Sprite.
	 */
	public Vector2 getPosition() {
		return position;
	}
	
	/**
	 * Gets the position of this Sprite along the x-axis.
	 * @return The x-position.
	 */
	public float getX() {
		return position.getX();
	}
	
	/**
	 * Gets the position of this SPrite along the y-axis.
	 * @return The y-position.
	 */
	public float getY() {
		return position.getY();
	}

	/**
	 * Sets a new position for this Sprite.
	 * @param position The new position.
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	/**
	 * Sets a new position for the Sprite.
	 * @param x The position along the x-axis.
	 * @param y The posiiton alnog the y-axis.
	 */
	public void setPosition(float x, float y) {
		this.position.set(x, y);
	}

	/**
	 * Gets the speed of this Sprite.
	 * @return The speed of this Sprite.
	 */
	public Vector2 getSpeed() {
		return speed;
	}

	/**
	 * Sets a new speed for this Sprite using a vector. 
	 * @param speed The new speed for this Sprite.
	 */
	public void setSpeed(Vector2 speed) {
		this.speed = speed;
	}
	
	/**
	 * Sets a new speed for this Sprite.
	 * @param x The speed along the x-axis.
	 * @param y The speed along the y-axis.
	 */
	public void setSpeed(float x, float y) {
		speed.set(x, y);
	}

	/**
	 * Sets the speed along the x-axis for this Sprite.
	 * @param x The position along the x-axis.
	 */
	public void setXSpeed(float x) {
		speed.setX(x);
	}
	
	/**
	 * Sets the speed along the y-axis for this Sprite.
	 * @param y The speed along the y-axis.
	 */
	public void setYSpeed(float y) {
		speed.setY(y);
	}
	
	/**
	 * Gets the acceleration for this Sprite.
	 * @return The acceleration for this Sprite.
	 */
	public Vector2 getAcceleration() {
		return acceleration;
	}

	/**
	 * Sets a new acceleration for this Sprite using a vector.
	 * @param acceleration The new acceleration for this Sprite.
	 */
	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}
	
	/**
	 * Sets a new acceleration for this Sprite.
	 * @param x The acceleration along the x-axis.
	 * @param y The acceleration along the y-axis.
	 */
	public void setAcceleration(float x, float y) {
		this.acceleration.set(x, y);
	}

	/**
	 * Gets the offset of this Sprite.
	 * @return The offset of this Sprite.
	 */
	public Vector2 getOffset() {
		return offset;
	}

	/**
	 * Sets a new offset for this Sprite using a vector.
	 * @param offset The new offset of the Sprite.
	 */
	public void setOffset(Vector2 offset) {
		this.offset = offset;
	}

	/**
	 * Sets a new offset for the Sprite.
	 * @param x The offset along the x-axis. 
	 * @param y The offset along the y-axis.
	 */
	public void setOffset(float x, float y) {
		this.offset.set(x, y);
	}
	
	/**
	 * Gets the orientation of this Sprite.
	 * @return The orientation of this Sprite.
	 */
	public float getOrientation() {
		return orientation;
	}

	/**
	 * Sets a new orientation for this Sprite.
	 * @param orientation The new orientation.
	 */
	public void setOrientation(float orientation) {
		this.orientation = orientation;
	}

	/**
	 * Rotates this Sprite a certain amount.
	 * @param deg The number of degrees to rotate the Sprite.
	 */
	public void rotate(float deg) {
		this.orientation += deg;
	}
	
	/**
	 * Gets the scale of this Ssrite.
	 * @return The scale of this SPrite.
	 */
	public Vector2 getScale() {
		return scale;
	}

	/**
	 * Sets the scale of this Sprite.
	 * @param scale The new scale for this Sprite.
	 */
	public void setScale(Vector2 scale) {
		this.scale = scale;
	}
	
	/**
	 * Sets the scale for this Sprite.
	 * @param sx The scale along the x-axis.
	 * @param sy The scale along the y-axis.
	 */
	public void setScale(float sx, float sy) {
		this.scale.set(sx, sy);
	}
	
	/**
	 * Checks for collision between this and another Sprite. If at least one of the Sprites have
	 * no shape, calling this has no effect.
	 * @param sprite The other Sprite.
	 * @return True if the two Sprites collide, false otherwise.
	 */
	public boolean collides(Sprite sprite) {
		
		// Check mask.
		if(((group & sprite.mask) != 0) || ((sprite.group & mask) != 0))
			return false;
		
		// If one of the sprites have no shape, then they can't collide.
		if(shape == null || sprite.shape == null)
			return false;
		
		boolean collided = shape.collides(sprite.shape);
		
		if(collided) {
			notifyCollisionListeners(sprite);
			sprite.notifyCollisionListeners(this);
		}
		
		return collided;
	}
	
	/**
	 * Notifies listeners that a collision happened.
	 * @param sprite The other Sprite involved in the collision.
	 */
	private void notifyCollisionListeners(Sprite sprite) {
		for(CollisionListener l : collisionListeners) {
			l.collided(this, sprite);
		}	
	}
	
	/**
	 * Adds an object which listens for collision events on this Sprite.
	 * @param listener The new collision listener.
	 */
	public void addCollisionListener(CollisionListener listener) {
		this.collisionListeners.add(listener);
	}
	
	/**
	 * Removes a CollisionListener from this Sprite.
	 * @param listener The CollisionListener to remove.
	 */
	public void removeCollisionListener(CollisionListener listener) {
		this.collisionListeners.remove(listener);
	}
}

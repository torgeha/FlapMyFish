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

package sheep.input;

import java.util.LinkedList;

import sheep.math.Vector2;

import android.view.MotionEvent;
import android.view.View;

/**
 * This singleton class tracks the state of the touchscreen. It also
 * issues MotionEvents which may implementors of the TouchListener
 * interface may subscribe to.
 */
public class Touch implements View.OnTouchListener {

	// Objects listening for keyboard events.
	LinkedList<TouchListener> listeners;
	
	// Last known touched position.
	Vector2 position;
	
	/**
	 * Inner class which holds the Touch instance. This is 
	 * necessary for lazy initialization. 
	 * @author Anders Ruud
	 */
	private static class TouchHolder {
		private final static Touch INSTANCE = new Touch();
	}
	
	protected Touch() {
		position = new Vector2(0, 0);
		
		// Create the linked list.
		listeners = new LinkedList<TouchListener>();
	}
	
	/**
	 * Gets the instance of the Keyboard class.
	 * @return The one and only instance of the Keyboard class.
	 */
	public static Touch getInstance() {
		return TouchHolder.INSTANCE;
	}
	
	/**
	 * Adds a TouchListener to listen for events.
	 * @param listener The TouchListener to add.
	 */
	public void addTouchListener(TouchListener listener) {
		this.listeners.add(listener);
	}
	
	/**
	 * Removes a TouchListener to listen for events.
	 * @param listener The TouchListener to add.
	 */
	public void removeTouchListener(TouchListener listener) {
		this.listeners.remove(listener);
	}

	/**
	 * Gets the last known position of a touch.
	 * @return The last known touched position on the screen.
	 */
	public Vector2 getPosition() {
		return position;
	}
	
	/**
	 * Gets the last known touched position along the x-axis.
	 * @return Last known touched x-position.
	 */
	public float getX() {
		return position.getX();
	}
	
	/**
	 * Gets the last known touched position along the y-axis.
	 * @return Last known touched x-position.
	 */
	public float getY() {
		return position.getY();
	}
	
	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		
		this.position.set(event.getX(), event.getY());
		
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			for(TouchListener l : listeners)
				l.onTouchDown(event);
			break;
		case MotionEvent.ACTION_UP:
			for(TouchListener l : listeners)
				l.onTouchUp(event);			
			break;
		case MotionEvent.ACTION_MOVE:
			for(TouchListener l : listeners)
				l.onTouchMove(event);			
			break;
		}
		
		
		
		return true;
	}
	
}

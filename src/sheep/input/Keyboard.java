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

import android.view.KeyEvent;
import android.view.View;

/**
 * This singleton class tracks the state of the keyboard. The key 
 * input callbacks in Android are good, but games are primarily 
 * concerned with the current state of certain keys.
 * @author Anders Ruud
 */
public class Keyboard implements View.OnKeyListener {

	// Objects listening for keyboard events.
	LinkedList<KeyboardListener> listeners;
	
	// Contains the state of each button.
	private boolean map[];
	
	/**
	 * Protected constructor to prevent instantiation. 
	 */
	protected Keyboard() {
		// Make room for all the keys.
		map = new boolean[KeyEvent.MAX_KEYCODE];
		
		// Create the linked list.
		listeners = new LinkedList<KeyboardListener>();
	}
	
	/**
	 * Inner class which holds the keyboard instance. This is 
	 * necessary for lazy initialization. 
	 * @author Anders Ruud
	 */
	private static class KeyboardHolder {
		private final static Keyboard INSTANCE = new Keyboard();
	}
	
	/**
	 * Gets the instance of the Keyboard class.
	 * @return The one and only instance of the Keyboard class.
	 */
	public static Keyboard getInstance() {
		return KeyboardHolder.INSTANCE;
	}
	
	/**
	 * Adds a KeyboardListener to listen for events.
	 * @param listener The KeyboardListener to add.
	 */
	public void addKeyboardListener(KeyboardListener listener) {
		this.listeners.add(listener);
	}
	
	/**
	 * Removes a KeyboardListener.
	 * @param listener The KeyboardListener to remove.
	 */
	public void removeKeyboardListener(KeyboardListener listener) {
		this.listeners.remove(listener);
	}
	
	/**
	 * Checks whether a key is down.
	 * @param key The virtual key code from KeyEvent.
	 * @return True if down, false if up and false if unknown key.
	 */
	public boolean isDown(int key) {
		
		// Bounds check.
		if(key < 0 || key >= map.length)
			return false;
		
		return map[key];
	}

	@Override
	public boolean onKey(View arg0, int arg1, KeyEvent event) {
		
		int key = event.getKeyCode();
		
		// Bounds check.
		if(key < 0 || key >= map.length)
			return false;
		
		switch(event.getAction()) {
			case KeyEvent.ACTION_DOWN:
				
				map[key] = true; // Set the key state to DOWN (true).
				
				// Notify listeners 
				for(KeyboardListener l : listeners) {
					if(l.onKeyDown(event)) {
						return true;
					}
				}	
				
				break;
				
			case KeyEvent.ACTION_UP:
				
				map[key] = false; // Set the key state to UP (true).
				
				// Notify listeners 
				for(KeyboardListener l : listeners) {
					if(l.onKeyUp(event)) {
						return true;
					}
				}	
				
				break;
		}
		
	
		
		return false;
	}
	
}

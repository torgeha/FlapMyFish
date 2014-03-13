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

import sheep.input.KeyboardListener;
import sheep.input.TouchListener;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * You should inherit from this class to create the various states for your game.
 * Each subclass should override the update and draw method to implement the game logic
 * and rendering logic, respectively.
 * 
 * At any time, the top State on the state stack receives events from the keyboard ad
 * touch screen, and objects may listen to these events by subscribing to its parent state.
 */
public class State implements KeyboardListener, TouchListener {
			
	LinkedList<KeyboardListener> keyboardListeners;
	LinkedList<TouchListener> touchListeners;
	
	// A reference to the parent Game. This is set by game
	// when pushed onto the stack.
	private Game game;
	
	/**
	 * Creates a new State.
	 */
	public State() {
		keyboardListeners = new LinkedList<KeyboardListener>();
		touchListeners = new LinkedList<TouchListener>();
	}
	
	/**
	 * Adds a KeyboardListener to this State.
	 * @param listener A KeyboardListener.
	 */
	public void addKeyboardListener(KeyboardListener listener) {
		this.keyboardListeners.add(listener);
	}
	
	/**
	 * Removes a KeyboardListener to this State. If not present, nothing happens.
	 * @param listener The KeyboardListener to remove.
	 */
	public void removeKeyboardListener(KeyboardListener listener) {
		this.keyboardListeners.remove(listener);
	}
	
	/**
	 * Adds a TouchListener to the State.
	 * @param listener A TouchListener.
	 */
	public void addTouchListener(TouchListener listener) {
		this.touchListeners.add(listener);
	}
	
	/**
	 * Removes a TouchListener to this State. If not present, nothing happens.
	 * @param listener The TouchListener to remove.
	 */
	public void removeTouchListener(TouchListener listener) {
		this.touchListeners.remove(listener);
	}
	
	/**
	 * This function is called when the game state should be updated.
	 * @param dt The time since last frame.
	 */
	public void update(float dt) {
		
	}
	
	/**
	 * This function is called when the game state should be drawn
	 * on screen.
	 * @param canvas The Canvas object onto which the State should be drawn.
	 */
	public void draw(Canvas canvas) {
		
	}	
	
	/**
	 * Gets the Game associated with this State.
	 * @return
	 */
	public Game getGame() {
		if(game == null) {
			throw new NullPointerException("The game reference is not set for " +
					"this State.");
		} else {
			return game;
		}
	}

	/**
	 * Sets the Game associated with this State. Only the Game class should
	 * call this method.
	 * @param game The Game associated with this State.
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public boolean onKeyDown(KeyEvent event) {
		for(KeyboardListener l : keyboardListeners) {
			if(l.onKeyDown(event)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onKeyUp(KeyEvent event) {
		for(KeyboardListener l : keyboardListeners) {
			if(l.onKeyUp(event)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onTouchDown(MotionEvent event) {
		for(TouchListener l : touchListeners) {
			if(l.onTouchDown(event)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onTouchMove(MotionEvent event) {
		for(TouchListener l : touchListeners) {
			if(l.onTouchMove(event)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onTouchUp(MotionEvent event) {
		for(TouchListener l : touchListeners) {
			if(l.onTouchUp(event)) {
				return true;
			}
		}
		return false;
	}
	
	
}

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

import java.util.EmptyStackException;
import java.util.Stack;
import sheep.input.KeyboardListener;
import sheep.input.TouchListener;
import sheep.util.Timer;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * The GameThread contains all the States and is the main loop the game. It listens
 * for keyboard and touch events and forwards these events to the current stack top.
 * 
 * It also keeps track of time, and calls update and draw on the top stack element
 * each loop iteration.
 */
public class GameThread extends Thread implements KeyboardListener, TouchListener {

	// Whether the thread is running or not. 
	private boolean running;
	
	// Pointer to the parent Game.
	protected Game game;
	
	// Stack of States.
	protected Stack<sheep.game.State> states;
	
	// A timer, so we can properly generate the timesteps between the 
	// frames.
	Timer timer;
	
	/**
	 * Creates a new GameThread. 
	 * @param game The Game containing the thread.
	 */
	public GameThread(Game game) {
		running = false;
		this.game = game;
		
		timer = new Timer();
		
		// Create empty stack.
		states = new Stack<sheep.game.State>();
		
		// Add an empty state. Adding via game so that
		// the parent game reference gets set.
		{
			sheep.game.State s = new sheep.game.State();
			s.setGame(this.game);
			states.push(s);
		}
			
	}
	
	@Override
	public void run() {
		while (running) {
			Canvas canvas = null;
			try {
				canvas = game.getHolder().lockCanvas(null);
				
				synchronized (game.getHolder()) {

					// Update the game.
					states.peek().update(timer.getDelta());
					
					
					// Draw the game.
					if (canvas != null) states.peek().draw(canvas);						
				}
				
			} finally {
				// do this in a finally so that if an exception is thrown
				// during the above, we don't leave the Surface in an
				// inconsistent state
				if (canvas != null) {
					game.getHolder().unlockCanvasAndPost(canvas);
				}
			}
		}		
	}

	/**
	 * The main loop checks this value each iteration. Set it to false to stop 
	 * the thread.
	 * @param running True to keep the thread running, false otherwise.
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * Checks whether the thread is running.
	 * @return True if thread is running, false otherwise.
	 */
	public boolean isRunning() {
		return running;
	}
	
	/**
	 * Gets the previous State in the stack. (The one beneath the top).
	 * @return The State right beneath the top.
	 */
	public synchronized sheep.game.State getPreviousState() {
		
		int n = states.size();
		
		if(n < 2) {
			return null;
		}
		
		return states.elementAt(n-2);
	}
	
	/**
	 * Pushes the state onto the state stack of in game thread.
	 * @param state The state to push onto the stack.
	 */
	public synchronized void pushState(sheep.game.State state) {
		states.push(state);
	}
	
	/**
	 * Pops a certain number of states from the stack.
	 * @param n The number of states to pop.
	 */
	public synchronized void popState(int n) {
		
		// Treat a size of less than N as empty. This way 
		// we can always assume at least one item.
		if(states.size() <= n) {
			throw new EmptyStackException();
		} else {
			for(int i = 0; i<n; i++) {
				states.pop();
			}
		}
	}

	@Override
	public synchronized boolean onKeyDown(KeyEvent event) {
		states.peek().onKeyDown(event);
		return true;
	}

	@Override
	public synchronized boolean onKeyUp(KeyEvent event) {
		states.peek().onKeyUp(event);
		return true;
	}

	@Override
	public synchronized boolean onTouchDown(MotionEvent event) {
		states.peek().onTouchDown(event);
		return true;
	}

	@Override
	public synchronized boolean onTouchMove(MotionEvent event) {
		states.peek().onTouchMove(event);
		return true;
	}

	@Override
	public synchronized boolean onTouchUp(MotionEvent event) {
		states.peek().onTouchUp(event);
		return true;
	}
	
}

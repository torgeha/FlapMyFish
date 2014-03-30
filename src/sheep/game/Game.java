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

import sheep.input.Keyboard;
import sheep.input.Touch;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * This class is subclass of SurfaceView, which makes it "viewable" in
 * an Android application. It contains a the main game thread, to which
 * it also redirects all calls from both Android and users.
 */
public class Game extends SurfaceView implements SurfaceHolder.Callback {
	
	// The game thread.
	GameThread thread;
	
	/**
	 * Creates a new Game. You should only create one Game for each
	 * project.
	 * @param context The Context for this applications.
	 * @param attrs The AttributeSet (may be null).
	 */
	public Game(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.setLongClickable(true);
		
		getHolder().addCallback(this);
		
		thread = new GameThread(this);
		
		Log.i("Sheep", "Game started");
				
		setOnKeyListener(Keyboard.getInstance());
		setOnTouchListener(Touch.getInstance());
		
		Keyboard.getInstance().addKeyboardListener(thread);
		Touch.getInstance().addTouchListener(thread);
		
		// To get key events.
		setFocusable(true);
		
		INSTANCE = this;
	}
	
	private static Game INSTANCE;
	
	/**
	 * Gets the Game instance. One Game must have been created previously, 
	 * otherwise this will be null.
	 * @return The single Game instance.
	 */
	public static Game getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Pushes the state onto the state stack of in game thread.
	 * @param state The state to push onto the stack.
	 */
	public void pushState(State state) {
		state.setGame(this);
		thread.pushState(state);
	}
	
	/**
	 * Pops a state from the stack.
	 */
	public void popState() {
		thread.popState(1);
	}
	
	/**
	 * Pops a certain number of states from the stack.
	 * @param n The number of states to pop.
	 */
	public void popState(int n) {
		thread.popState(n);
	}
	
	/**
	 * Gets the previous State in the stack. (The one beneath the top).
	 * @return The State right beneath the top.
	 */
	public State getPreviousState() {
		return thread.getPreviousState();
	}
	
	/**
	 * Gets the current State in the stack. (The one on the top).
	 * @return The State on the top.
	 */
	public State getCurrentState() {
		return thread.getCurrentState();
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		thread.setRunning(false);
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
	
}

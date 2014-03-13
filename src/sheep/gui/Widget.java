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

package sheep.gui;

import java.util.LinkedList;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import sheep.input.KeyboardListener;
import sheep.input.TouchListener;

/**
 * The superclass of all components in the GUI system. A Widget can be
 * objects like a button, a list or textfield.
 * 
 * Each Widget can also trigger events, which classes conforming to the 
 * WidgetListener iterface can listen to.
 */
public abstract class Widget implements KeyboardListener, TouchListener {

	LinkedList<WidgetListener> listeners;
	
	/**
	 * Constructor.
	 */
	public Widget() {
		listeners = new LinkedList<WidgetListener>();
	}
	
	/**
	 * Called when the Widget should be drawn. 
	 * @param canvas The Canvas to draw the Widget onto.
	 */
	public abstract void draw(Canvas canvas);
	
	/**
	 * Adds a WidgetListener to this Widget.
	 * @param listener The WidgetListener to add.
	 */
	public void addWidgetListener(WidgetListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Remove a WidgetListener to this Widget.
	 * @param listener The WidgetListener to remove.
	 */
	public void removeWidgetListener(WidgetListener listener) {
		listeners.remove(listener);
	}
	
	@Override
	public boolean onKeyDown(KeyEvent event) {
		return false;
	}

	@Override
	public boolean onKeyUp(KeyEvent event) {
		return false;
	}

	@Override
	public boolean onTouchDown(MotionEvent event) {
		return false;
	}

	@Override
	public boolean onTouchMove(MotionEvent event) {
		return false;
	}

	@Override
	public boolean onTouchUp(MotionEvent event) {
		return false;
	}
	
}

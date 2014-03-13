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

package sheep.graphics;

import android.graphics.Paint;

/**
 * This class exists to simplify the creation of predefined 
 * pure-color Paint.
 */
public class Color extends Paint {
	
	/**
	 * Solid white.
	 */
	public static final Color WHITE = new Color(255,255,255);
	
	/**
	 * Solid black.
	 */
	public static final Color BLACK = new Color(0, 0, 0);
	
	/**
	 * Solid red.
	 */
	public static final Color RED = new Color(255, 0, 0);
	
	/**
	 * Solid green.
	 */
	public static final Color GREEN = new Color(0, 255, 0);
	
	/**
	 * Solid blue.
	 */
	public static final Color BLUE = new Color(0, 0, 255);
	
	/**
	 * Creates a new color with the specified components. 
	 * @param r The red color component (0-255).
	 * @param g The green color component (0-255).
	 * @param b The blue color component (0-255).
	 */	
	public Color(int r, int g, int b) {
		this(255, r, g, b);
	}
	
	/**
	 * Creates a new color with the specified components. Note that
	 * alpha is the first value.
	 * @param a The alpha component (0-255).
	 * @param r The red color component (0-255).
	 * @param g The green color component (0-255).
	 * @param b The blue color component (0-255).
	 */
	public Color(int a, int r, int g, int b) {
		this.setARGB(a, r, g, b);
		
		// Anti-aliasing on by default.
		this.setAntiAlias(true);
	}
}

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
import android.graphics.Typeface;

/**
 * This class exists primarily as a convenience when drawing text. It predefines
 * a few static Paint objects with suitable attributes.
 */
public class Font extends Paint {

	/**
	 * A white sans-serif font, 12px. 
	 */
	public static final Font WHITE_SANS_NORMAL_12 = new Font(255, 255, 255, 12.0f, 
			Typeface.SANS_SERIF, Typeface.NORMAL);
	
	/**
	 * Blue sans-serif font, 12px.
	 */
	public static final Font BLUE_SANS_NORMAL_12 = new Font(57, 152, 249, 12.0f, 
			Typeface.SANS_SERIF, Typeface.NORMAL);

	/**
	 * A white sans-serif bold font, 16px. 
	 */
	public static final Font WHITE_SANS_BOLD_16 = new Font(255, 255, 255, 16.0f, 
			Typeface.SANS_SERIF, Typeface.BOLD);
	
	/**
	 * Blue sans-serif font bold, 16px.
	 */
	public static final Font BLUE_SANS_BOLD_16 = new Font(57, 152, 249, 16.0f, 
			Typeface.SANS_SERIF, Typeface.BOLD);
	
	/**
	 * A white sans-serif bold font, 20px. 
	 */
	public static final Font WHITE_SANS_BOLD_20 = new Font(255, 255, 255, 20.0f, 
			Typeface.SANS_SERIF, Typeface.BOLD);
	
	/**
	 * Blue sans-serif font bold, 20px.
	 */
	public static final Font BLUE_SANS_BOLD_20 = new Font(57, 152, 249, 20.0f, 
			Typeface.SANS_SERIF, Typeface.BOLD);
	
	/**
	 * Creates a new Font with the specified color, size and style.
	 * @param r Red component (0-255).
	 * @param g Green component (0-255).
	 * @param b Blue component (0-255).
	 * @param size The size of the font face.
	 * @param family For instance Typeface.SANS_SERIF.
	 * @param style For instance Typeface.BOLD.
	 */
	public Font(int r, int g, int b, float size, Typeface family, int style) {
		this.setARGB(255, r, g, b);
		this.setAntiAlias(true);
		this.setTextSize(size);
		this.setTypeface(Typeface.create(family, style));
	}

	
}

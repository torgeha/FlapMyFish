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

/**
 * An interface for classes which contains Sprites. When a Sprite is added
 * a reference to the parent SpriteContainer is set, which allows the Sprite
 * to remove itself from this container later. This is useful in collision
 * response, where the caller can easily destroy the Sprite and have it
 * removed from its container without having a reference to the correct
 * container available.
 */
public interface SpriteContainer {

	/**
	 * Adds a Sprite to this SpriteContainer.
	 * @param sprite The Sprite to add to this SpriteContainer.
	 */
	public void addSprite(Sprite sprite);
	
	/**
	 * Removes a Sprite from this SpriteContainer.
	 * @param sprite The Sprite to remove from the SpriteContainer.
	 */
	public void removeSprite(Sprite sprite);
	
}

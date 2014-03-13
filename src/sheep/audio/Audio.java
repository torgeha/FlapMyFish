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

package sheep.audio;

import sheep.game.Game;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Represents the audio device on the system. Contains an instance of the SoundPool 
 * class from Android SDK which enables low latency playback of sounds.
 */
public class Audio {
	
	// This object manages sounds in a "pool". See Android documentation
	// for more information.
	SoundPool pool;
	
	/**
	 * Private constructor (singleton).
	 */
	private Audio() {
		// 16 has here been chosen as an upper limit of simultaneous sounds.
		pool = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);
	}
	
	/**
	 * Holds the sole instance of the Audio singleton. This class exists only
	 * to allow lazy allocation of the singleton.
	 */
	private static class AudioHolder {
		private final static Audio INSTANCE = new Audio();
	}
	
	/**
	 * Gets the sole instance of this class (creating it on first call).
	 * @return The instalce of this class.
	 */
	public static Audio getInstance() {
		return AudioHolder.INSTANCE;
	}
	
	/**
	 * Gets the SoundPool object this class holds.
	 * @return A SoundPool object.
	 */
	public SoundPool getSoundPool() {
		return pool;
	}
	
	/**
	 * Loads a sound from the bundle.
	 * @param res The resource identifier, for instance R.raw.sound.
	 * @return The sound identifier, as provided by the SoundPool.
	 */
	public int load(int res) {
		return pool.load(Game.getInstance().getContext(), res, 1);
	}
}
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

/**
 * Represents a single playable sound. Uses the Audio singleton to load the
 * sound internally.
 */
public class Sound {
	
	// The sound number provided by SoundPool.
	int id;
	
	/**
	 * Creates a new Sound from the resource identifier. If you have a file "sound.wav"
	 * the "raw" directory, then you could load that sound with the resource identifier
	 * R.raw.sound. 
	 * @param res The resource identifier, for instance R.raw.sound.
	 */
	public Sound(int res) {
		id = Audio.getInstance().load(res);
	}
	
	@Override
	protected void finalize() throws Throwable {
	    try {
	        Audio.getInstance().getSoundPool().unload(id);
	    } finally {
	        super.finalize();
	    }
	}
	
	/**
	 * Pauses this Sound. If the Sound isn't playing, nothing happens.
	 */
	public void pause() {
		Audio.getInstance().getSoundPool().pause(id);
	}
	
	/**
	 * Plays this Sound (once) with normal volume and pitch.
	 */
	public void play() {
		play(1.0f, 1.0f, 0, 1.0f);
	}
	
	/**
	 * Plays this Sound a certain number of loops with normal volume 
	 * and pitch.
	 * @param loop The number of times to loop the Sound (0 = no loop, -1 = forever).
	 */
	public void play(int loop) {
		play(1.0f, 1.0f, loop, 1.0f);
	}
	
	/**
	 * Plays this Sound (once) with a certain volume.
	 * @param volume Volume of both channels (range: 0.0 to 1.0).
	 */
	public void play(float volume) {
		play(volume, volume, 0, 1.0f);
	}
	
	/**
	 * Plays this Sound <code>loop</code> times with a certain volume. 
	 * @param volume Volume of both channels (range: 0.0 to 1.0).
	 * @param loop The number of times to loop the Sound (0 = no loop, -1 = forever).
	 */
	public void play(float volume, int loop) {
		play(volume, volume, loop, 1.0f);
	}
	
	/**
	 * Plays this Sound <code>loop</code> times with a certain volume and rate. 
	 * @param volume Volume of both channels (range: 0.0 to 1.0).
	 * @param loop The number of times to loop the Sound (0 = no loop, -1 = forever).
	 * @param rate Playback rate (1.0 = normal, 2.0 = twice as fast).
	 */
	public void play(float volume, int loop, float rate) {
		play(volume, volume, loop, rate);
	}
	
	/**
	 * Plays this Sound <code>loop</code> times with a certain volume for each channel and rate. 
	 * @param leftVolume Volume of left channel (range: 0.0 to 1.0).
	 * @param rightVolume Volume of right channel (range: 0.0 to 1.0).
	 * @param loop The number of times to loop this Sound (0 = no loop, -1 = forever).
	 * @param rate Playback rate (1.0 = normal, 2.0 = twice as fast).
	 */
	public void play(float leftVolume, float rightVolume, int loop, float rate) {
		Audio.getInstance().getSoundPool().play(id, leftVolume, rightVolume, 1, loop, rate);
	}
	
	/**
	 * Stops this Sound. If Sound isn't playing, nothing happens.
	 */
	public void stop() {
		Audio.getInstance().getSoundPool().stop(id);
	}
	
	/**
	 * Sets the number of loops for this Sound.
	 * @param loop The number of times to loop this Sound (0 = no loop, -1 = forever).
	 */
	public void setLoop(int loop) {
		Audio.getInstance().getSoundPool().setLoop(id, loop);
	}
	
	/**
	 * Sets the playback rate for this Sound.
	 * @param rate Playback rate (1.0 = normal, 2.0 = twice as fast).
	 */
	public void setRate(float rate) {
		Audio.getInstance().getSoundPool().setRate(id, rate);
	}
	
	/**
	 * Sets the volume for both channels.
	 * @param volume Volume of both channels (range: 0.0 to 1.0).
	 */
	public void setVolume(float volume) {
		setVolume(volume, volume);
	}
	
	/**
	 * Sets the volume for each channel individually.
	 * @param left Volume of left channel (range: 0.0 to 1.0).
	 * @param right Volume of right channel (range: 0.0 to 1.0).
	 */
	public void setVolume(float left, float right) {
		Audio.getInstance().getSoundPool().setVolume(id, left, right);
	}	
}
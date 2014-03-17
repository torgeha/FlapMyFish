package no.ntnu.flapmyfish.level;

import no.ntnu.flapmyfish.Constants;

public class SpriteInfo {

	private float xBlock;
	private float yBlock;
	private float speed;
	private TokenType type;
	
	/**
	 * 
	 * @param xBlock X location of Sprite in snippet, value between 0 and Constants.NUMBER_OF_BLOCKS_X_DIR.
	 * @param yBlock Y location of Sprite in snippet, value between 0 and Constants.NUMBER_OF_BLOCKS_Y_DIR.
	 * @param speed
	 * @param type
	 */
	public SpriteInfo(float xBlock, float yBlock, float speed, TokenType type) {
		if (xBlock<0 || yBlock <0 || xBlock >= Constants.NUMBER_OF_BLOCKS_X_DIR 
				|| yBlock >= Constants.NUMBER_OF_BLOCKS_Y_DIR) {
			throw new IllegalArgumentException("Illegal value for xBlock or yBlock");
		}
		this.xBlock = xBlock;
		this.yBlock = yBlock;
		this.speed = speed;
		this.type = type;
	}

	public float getXBlock() {
		return xBlock;
	}

	public float getYBlock() {
		return yBlock;
	}

	public float getSpeed() {
		return speed;
	}

	public TokenType getType() {
		return type;
	}
	
	
	
}

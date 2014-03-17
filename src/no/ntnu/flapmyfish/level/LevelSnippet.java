package no.ntnu.flapmyfish.level;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.R;
import no.ntnu.flapmyfish.tokens.Enemy;
import no.ntnu.flapmyfish.tokens.ExtendedSprite;
import no.ntnu.flapmyfish.tokens.Food;

public class LevelSnippet {

	private SpriteInfo[] spriteInfos;
	
	public LevelSnippet(int snippetId) {
		spriteInfos = getSnippetSprites(snippetId);
	}
	
	public ExtendedSprite[] getSprites() {
		ExtendedSprite[] sprites = new ExtendedSprite[spriteInfos.length];
		for (int i = 0; i<spriteInfos.length; i++) {
			sprites[i] = generateSpriteFromInfo(spriteInfos[i]);
		}
		return sprites;
	}
	
	private SpriteInfo[] getSnippetSprites(int id) {
		SpriteInfo[] sprites;
		if (true/*id == 1*/) {
			sprites = new SpriteInfo[2];
			sprites[0] = new SpriteInfo(5, 5, -Constants.MAX_ENEMY_SPEED, TokenType.FOOD);
			sprites[1] = new SpriteInfo(3, 4, -Constants.MAX_ENEMY_SPEED, TokenType.SHARK);
			//sprites[2] = new SpriteInfo(3, 4, -Constants.MAX_ENEMY_SPEED, TokenType.SHARK);
		}
		return sprites;
	}
	
	private ExtendedSprite generateSpriteFromInfo(SpriteInfo spriteInfo) {
		ExtendedSprite s;
		if (spriteInfo.getType() == TokenType.SHARK) {
			s = new Enemy(R.drawable.shark);
//			s.setSizeByHeight(.12f);
		}
		else {//if (spriteInfo.getType() == TokenType.FOOD) {
			s = new Food(R.drawable.food_fish);
//			s.setSizeByHeight(.1f);
		}
		s.setXSpeed(spriteInfo.getSpeed());
		float xPos = Constants.WINDOW_WIDTH*1.1f + (Constants.SNIPPET_WIDTH/Constants.NUMBER_OF_BLOCKS_X_DIR)*(spriteInfo.getXBlock());
		float yPos = (s.getHeight()*.6f)+((Constants.WINDOW_HEIGHT-s.getHeight()*1.2f)/Constants.NUMBER_OF_BLOCKS_Y_DIR)*(spriteInfo.getYBlock());
		s.setPosition(xPos, yPos);
		s.update(0);
		return s;
	}
	
}

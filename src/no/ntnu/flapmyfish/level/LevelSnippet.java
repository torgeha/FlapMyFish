package no.ntnu.flapmyfish.level;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.R;
import no.ntnu.flapmyfish.tokens.Enemy;
import no.ntnu.flapmyfish.tokens.Food;
import sheep.game.Sprite;

public class LevelSnippet {

	private SpriteInfo[] spriteInfos;
	
	public LevelSnippet(int snippetId) {
		spriteInfos = getSnippetSprites(snippetId);
	}
	
	public Sprite[] getSprites() {
		Sprite[] sprites = new Sprite[spriteInfos.length];
		for (int i = 0; i<spriteInfos.length; i++) {
			sprites[i] = generateSpriteFromInfo(spriteInfos[i]);
		}
		return sprites;
	}
	
	private SpriteInfo[] getSnippetSprites(int id) {
		SpriteInfo[] sprites;
		if (true/*id == 1*/) {
			sprites = new SpriteInfo[3];
			sprites[0] = new SpriteInfo(0, 0, Constants.MAX_ENEMY_SPEED, TokenType.SHARK);
			sprites[0] = new SpriteInfo(5, 5, Constants.MAX_ENEMY_SPEED, TokenType.FOOD);
			sprites[0] = new SpriteInfo(7, 9, Constants.MAX_ENEMY_SPEED, TokenType.SHARK);
		}
		return sprites;
	}
	
	private Sprite generateSpriteFromInfo(SpriteInfo spriteInfo) {
		Sprite s;
		if (spriteInfo.getType() == TokenType.SHARK) {
			s = new Enemy(R.drawable.shark);
		}
		else {//if (spriteInfo.getType() == TokenType.FOOD) {
			s = new Food(R.drawable.food_fish);
		}
		s.setXSpeed(spriteInfo.getSpeed());
		float xPos = Constants.WINDOW_WIDTH*1.1f + (Constants.SNIPPET_WIDTH/Constants.NUMBER_OF_BLOCKS_X_DIR)*(spriteInfo.getXBlock());
		float yPos = (Constants.WINDOW_HEIGHT*.05f)+(Constants.WINDOW_HEIGHT/Constants.NUMBER_OF_BLOCKS_Y_DIR)*(spriteInfo.getYBlock());
		s.setPosition(xPos, yPos);
		return s;
	}
	
}

package no.ntnu.flapmyfish.level;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.R;
import no.ntnu.flapmyfish.tokens.Enemy;
import no.ntnu.flapmyfish.tokens.ExtendedSprite;
import no.ntnu.flapmyfish.tokens.Food;
import no.ntnu.flapmyfish.tokens.Player;

public class LevelSnippet {

	private SpriteInfo[] spriteInfos;
	private static int[] foodImgs = {R.drawable.victim_fish_frame1, R.drawable.victim_fish_frame2,
			R.drawable.victim_fish_frame3, R.drawable.victim_fish_frame2};
	private static int[][] sharkImgs =
		{
		{R.drawable.s_h_t_0_0,R.drawable.s_h_t_0_1,R.drawable.s_h_t_0_2,R.drawable.s_h_t_0_3,R.drawable.s_h_t_0_4,R.drawable.s_h_t_0_5,R.drawable.s_h_t_0_6,R.drawable.s_h_t_0_7,R.drawable.s_h_t_0_8,R.drawable.s_h_t_0_9,R.drawable.s_h_t_0_10},
		{R.drawable.s_h_t_1_0,R.drawable.s_h_t_1_1,R.drawable.s_h_t_1_2,R.drawable.s_h_t_1_3,R.drawable.s_h_t_1_4,R.drawable.s_h_t_1_5,R.drawable.s_h_t_1_6,R.drawable.s_h_t_1_7,R.drawable.s_h_t_1_8,R.drawable.s_h_t_1_9,R.drawable.s_h_t_1_10},
		{R.drawable.s_h_t_2_0,R.drawable.s_h_t_2_1,R.drawable.s_h_t_2_2,R.drawable.s_h_t_2_3,R.drawable.s_h_t_2_4,R.drawable.s_h_t_2_5,R.drawable.s_h_t_2_6,R.drawable.s_h_t_2_7,R.drawable.s_h_t_2_8,R.drawable.s_h_t_2_9,R.drawable.s_h_t_2_10},
		{R.drawable.s_h_t_3_0,R.drawable.s_h_t_3_1,R.drawable.s_h_t_3_2,R.drawable.s_h_t_3_3,R.drawable.s_h_t_3_4,R.drawable.s_h_t_3_5,R.drawable.s_h_t_3_6,R.drawable.s_h_t_3_7,R.drawable.s_h_t_3_8,R.drawable.s_h_t_3_9,R.drawable.s_h_t_3_10},
		{R.drawable.s_h_t_4_0,R.drawable.s_h_t_4_1,R.drawable.s_h_t_4_2,R.drawable.s_h_t_4_3,R.drawable.s_h_t_4_4,R.drawable.s_h_t_4_5,R.drawable.s_h_t_4_6,R.drawable.s_h_t_4_7,R.drawable.s_h_t_4_8,R.drawable.s_h_t_4_9,R.drawable.s_h_t_4_10},
		{R.drawable.s_h_t_5_0,R.drawable.s_h_t_5_1,R.drawable.s_h_t_5_2,R.drawable.s_h_t_5_3,R.drawable.s_h_t_5_4,R.drawable.s_h_t_5_5,R.drawable.s_h_t_5_6,R.drawable.s_h_t_5_7,R.drawable.s_h_t_5_8,R.drawable.s_h_t_5_9,R.drawable.s_h_t_5_10},
		{R.drawable.s_h_t_6_0,R.drawable.s_h_t_6_1,R.drawable.s_h_t_6_2,R.drawable.s_h_t_6_3,R.drawable.s_h_t_6_4,R.drawable.s_h_t_6_5,R.drawable.s_h_t_6_6,R.drawable.s_h_t_6_7,R.drawable.s_h_t_6_8,R.drawable.s_h_t_6_9,R.drawable.s_h_t_6_10},
		{R.drawable.s_h_t_7_0,R.drawable.s_h_t_7_1,R.drawable.s_h_t_7_2,R.drawable.s_h_t_7_3,R.drawable.s_h_t_7_4,R.drawable.s_h_t_7_5,R.drawable.s_h_t_7_6,R.drawable.s_h_t_7_7,R.drawable.s_h_t_7_8,R.drawable.s_h_t_7_9,R.drawable.s_h_t_7_10},
		{R.drawable.s_h_t_8_0,R.drawable.s_h_t_8_1,R.drawable.s_h_t_8_2,R.drawable.s_h_t_8_3,R.drawable.s_h_t_8_4,R.drawable.s_h_t_8_5,R.drawable.s_h_t_8_6,R.drawable.s_h_t_8_7,R.drawable.s_h_t_8_8,R.drawable.s_h_t_8_9,R.drawable.s_h_t_8_10}
		};

	
	private Player player;
	
	public LevelSnippet(int snippetId, Player player) {
		spriteInfos = getSnippetSprites(snippetId);
		this.player = player;
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
		if (true /*id == 1*/) {
			sprites = new SpriteInfo[3];
			sprites[0] = new SpriteInfo(5, 5, -Constants.MAX_ENEMY_SPEED, TokenType.FOOD);
			sprites[1] = new SpriteInfo(3, 4, -Constants.MAX_ENEMY_SPEED, TokenType.SHARK);
			sprites[2] = new SpriteInfo(0, 0, -Constants.MAX_ENEMY_SPEED, TokenType.SHARK);
		}
//		else /*if (id > 5) */{
//			sprites = new SpriteInfo[2];
//			sprites[0] = new SpriteInfo(5, 5, -Constants.MAX_ENEMY_SPEED, TokenType.FOOD);
//			sprites[1] = new SpriteInfo(3, 4, -Constants.MAX_ENEMY_SPEED, TokenType.SHARK);
//			sprites[2] = new SpriteInfo(1, 1, -Constants.MAX_ENEMY_SPEED, TokenType.SHARK);
//		}
		
		return sprites;
	}
	
	private ExtendedSprite generateSpriteFromInfo(SpriteInfo spriteInfo) {
		ExtendedSprite s;
		if (spriteInfo.getType() == TokenType.SHARK) {
			s = new Enemy(sharkImgs, 0.1f, 1, 3, player);
			s.setSizeByHeight(.24f);
		}
		else {//if (spriteInfo.getType() == TokenType.FOOD) {
			
			s = new Food(foodImgs, 0.1f, 0);
			s.setSizeByHeight(.08f);
		}
		s.setXSpeed(spriteInfo.getSpeed());
		float xPos = Constants.WINDOW_WIDTH*1.1f + (Constants.SNIPPET_WIDTH/Constants.NUMBER_OF_BLOCKS_X_DIR)*(spriteInfo.getXBlock());
		float yPos = (s.getHeight()*.6f)+((Constants.WINDOW_HEIGHT-s.getHeight()*1.2f)/(Constants.NUMBER_OF_BLOCKS_Y_DIR-1))*(spriteInfo.getYBlock());
		s.setPosition(xPos, yPos);
		s.update(0);
		return s;
	}
	
}

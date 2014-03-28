package no.ntnu.flapmyfish.level;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.R;
import no.ntnu.flapmyfish.tokens.Enemy;
import no.ntnu.flapmyfish.tokens.ExtendedSprite;
import no.ntnu.flapmyfish.tokens.Food;
import no.ntnu.flapmyfish.tokens.Player;
import android.content.res.AssetManager;

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

	private static String[][] snippets /*= {
		{"5 5 -1.0 F","3 4 -1.0 E","0 0 -1.0 E"},
		{"0 5 -1.0 E","2 1 -1.0 E","4 0 -1.0 F"},
		{"0 2 -1.0 E","3 2 -1.0 F","3 3 -1.0 E"},
		{"0 0 -1.0 E","2 3 -1.0 E","5 5 -1.0 E"},
		{"3 1 -1.0 E","3 4 -1.0 E"},
		{"0 2 -1.0 F","2 2 -1.0 E","4 4 -1.0 E"},
		{"0 4 -1.0 E","2 2 -1.0 E","4 0 -1.0 E"},
		{"0 2 -1.0 E","2 2 -1.0 F","4 2 -1.0 E"}
}*/;
	public static AssetManager am;
	
	private Player player;
	
	public LevelSnippet(int snippetId, Player player) {
		if (snippets == null) {
			readSnippets();
		}
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
		String[] snippet = LevelSnippet.snippets[id];
		sprites = new SpriteInfo[snippet.length];
		for (int i = 0; i < snippet.length; i++) {
			String[] sprite = snippet[i].split(" ");
			float xBlock = Float.valueOf(sprite[0]);
			float yBlock = Float.valueOf(sprite[1]);
			float speed = Float.valueOf(sprite[2])*Constants.MAX_ENEMY_SPEED;
			TokenType type = sprite[3].equals("E") ? TokenType.SHARK : TokenType.FOOD;
			sprites[i] = new SpriteInfo(xBlock, yBlock, speed, type);	
		} 
		
//		if (true /*id == 1*/) {
//			sprites = new SpriteInfo[3];
//			sprites[0] = new SpriteInfo(5, 5, -Constants.MAX_ENEMY_SPEED, TokenType.FOOD);
//			sprites[1] = new SpriteInfo(3, 4, -Constants.MAX_ENEMY_SPEED, TokenType.SHARK);
//			sprites[2] = new SpriteInfo(0, 0, -Constants.MAX_ENEMY_SPEED, TokenType.SHARK);
//		}
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
			s = new Enemy(sharkImgs, 0.07f+(0.03f*-Constants.MAX_ENEMY_SPEED/spriteInfo.getSpeed()), new Random().nextInt(sharkImgs[0].length-1)+1, 3, player);
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
	
	private void readSnippets() {
		String path = "snippets.txt";
		ArrayList<String[]> snippets = new ArrayList<String[]>();
		InputStream is = null;
		InputStreamReader ir = null;
		BufferedReader br = null;
		try {
			is = LevelSnippet.am.open(path);
			ir = new InputStreamReader(is);
			br = new BufferedReader(ir);
			String line; 
			while ((line = br.readLine()) !=null) {
				String[] sprites = line.split(";");
				snippets.add(sprites);
			}
			LevelSnippet.snippets = snippets.toArray(new String[snippets.size()][0]);
		} catch (FileNotFoundException e) {
			System.out.println("Could not read file "+path);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not read line");
			e.printStackTrace();
		}
		finally {
			try {
				if (is!=null) {
					is.close();					
				}
				if (ir!=null) {
					ir.close();					
				}
				if (br!=null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

package no.ntnu.flapmyfish.level;

import java.util.Random;

import no.ntnu.flapmyfish.Constants;

public class LevelFactory {

	public static String generateLevel() {
		String level ="";
		Random r = new Random();
		for (int i = 0; i < Constants.LEVEL_LENGTH; i++) {
			level += r.nextInt(Constants.LEVEL_SNIPPET_POOL_SIZE);
		}
		return level;
	}	
}

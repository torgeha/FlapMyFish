package no.ntnu.flapmyfish.level;

import java.util.Random;

import no.ntnu.flapmyfish.Constants;

public class LevelFactory {

	public static String generateLevel() {
		String level ="";
		Random r = new Random();
		int snippetNr;
		level += r.nextInt(Constants.LEVEL_SNIPPET_POOL_SIZE);
		for (int i = 1; i < Constants.LEVEL_LENGTH; i++) {
			while ((snippetNr = r.nextInt(Constants.LEVEL_SNIPPET_POOL_SIZE))==level.charAt(i-1)-'0');
			level += snippetNr;
		}
		System.out.println(level);
		return level;
	}	
}

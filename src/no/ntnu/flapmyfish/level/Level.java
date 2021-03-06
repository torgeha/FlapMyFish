package no.ntnu.flapmyfish.level;

import no.ntnu.flapmyfish.Constants;
import sheep.collision.CollisionLayer;
import no.ntnu.flapmyfish.tokens.ExtendedSprite;
import no.ntnu.flapmyfish.tokens.Player;

public class Level {

	private LevelSnippet[] snippets;
	private float timePassed;
	private CollisionLayer collisionLayer;
	private int nextSnippet;

	
	public Level(String levelString, CollisionLayer colLayer, Player player) {
		nextSnippet = 0;
		timePassed = 0;
		collisionLayer = colLayer;
		snippets = new LevelSnippet[Constants.LEVEL_LENGTH];
		for (int i = 0; i<levelString.length(); i++) {
			int snippetId = levelString.charAt(i) - '0';
			snippets[i] = new LevelSnippet(snippetId, player);
		}
	}
	
	public void update(float dt) {
		timePassed+=dt;
//		if (timePassed >=3) {
		if (timePassed >=Constants.TIME_BETWEEN_SNIPPETS) {
			for (ExtendedSprite sprite : snippets[nextSnippet].getSprites()) {
				collisionLayer.addSprite(sprite);
			}
			nextSnippet = (nextSnippet+1)%Constants.LEVEL_LENGTH;
//			timePassed -= 3;
			timePassed -= Constants.TIME_BETWEEN_SNIPPETS;
		}
	}
}

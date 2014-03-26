package no.ntnu.flapmyfish;

import java.util.ArrayList;
import java.util.LinkedList;

import sheep.game.Layer;
import sheep.game.Sprite;
import sheep.game.SpriteContainer;
import sheep.math.BoundingBox;
import android.graphics.Canvas;

public class ExtendedLayer extends Layer implements SpriteContainer{

	private ArrayList<Sprite> sprites;
	private LinkedList<Sprite> kill;
	
	public ExtendedLayer() {
		sprites = new ArrayList<Sprite>();
		kill = new LinkedList<Sprite>();
	}
	
	@Override
	public void update(float dt) {
		for (Sprite s : sprites) {
			s.update(dt);
		}
		
		for (Sprite s : kill) {
			sprites.remove(s);
		}
		kill.clear();
	}

	@Override
	public void draw(Canvas canvas, BoundingBox box) {
		for (Sprite s : sprites) {
			s.draw(canvas);
		}
	}
	
	@Override
	public void addSprite(Sprite sprite) {
		sprites.add(sprite);
		sprite.setParent(this);
	}

	@Override
	public void removeSprite(Sprite sprite) {
		kill.add(sprite);
		sprite.setParent(null);
	}
	
}

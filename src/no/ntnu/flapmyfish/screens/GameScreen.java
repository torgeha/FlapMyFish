package no.ntnu.flapmyfish.screens;

import no.ntnu.flapmyfish.LoopingBackgroundLayer;
import no.ntnu.flapmyfish.R;
import sheep.collision.CollisionLayer;
import sheep.game.State;
import sheep.game.World;
import android.graphics.Canvas;

public class GameScreen extends State {

	private World world;
	private LoopingBackgroundLayer loopingBgLayer;
	private CollisionLayer colLayer;

	public GameScreen() {
		init();
	}

	public void update(float dt) {
		world.update(dt);
	}


	public void draw(Canvas canvas) {
		world.draw(canvas);
	}

	private void init() {
		world = new World();

		loopingBgLayer = new LoopingBackgroundLayer(R.drawable.background1);
		world.addLayer(loopingBgLayer);

		colLayer = new CollisionLayer();
		world.addLayer(colLayer);
	}
}

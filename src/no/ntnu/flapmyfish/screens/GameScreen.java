package no.ntnu.flapmyfish.screens;




import no.ntnu.flapmyfish.BackgroundLayer;
import no.ntnu.flapmyfish.R;
import sheep.collision.CollisionLayer;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.game.World;
import sheep.graphics.Image;
import android.graphics.Canvas;

public class GameScreen extends State {

	private World world;
	private BackgroundLayer bgLayer;
	private CollisionLayer colLayer;
	
	
	public GameScreen() {
		setUpBackGround();
		setUpCollisionLayer();
	}
	
	
	public void update(float dt) {
		world.update(dt);
	}
	
	public void draw(Canvas canvas) {
		world.draw(canvas);
	}
	
	
	private void setUpBackGround() {
		world = new World();
		bgLayer = new BackgroundLayer();
		world.addLayer(bgLayer);
		Sprite bgSprite = new Sprite(new Image(R.drawable.background));
		bgLayer.addSprite(bgSprite);
	}
	
	private void setUpCollisionLayer() {
		colLayer = new CollisionLayer();
		world.addLayer(colLayer);
	}
}

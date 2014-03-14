package no.ntnu.flapmyfish.screens;




import no.ntnu.flapmyfish.BackgroundLayer;
import no.ntnu.flapmyfish.R;
import no.ntnu.flapmyfish.tokens.ExtendedSprite;
import sheep.game.Layer;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.game.World;
import sheep.graphics.Image;
import android.graphics.Canvas;

public class GameScreen extends State {

	private World world;
	private BackgroundLayer bgLayer;
	
	
	public GameScreen() {
		setUpBackGround();
		
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
		ExtendedSprite bgSprite = new ExtendedSprite(new Image(R.drawable.background));
		bgLayer.addSprite(bgSprite);
		bgSprite.setOffset(0, 0);
		bgSprite.setSize(1, 1);
	}
}

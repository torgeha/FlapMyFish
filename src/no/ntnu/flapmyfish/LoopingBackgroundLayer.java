package no.ntnu.flapmyfish;

import java.util.LinkedList;

import sheep.game.Sprite;
import sheep.graphics.Image;

/**
 * This class extends {@link ExtendedLayer} and simulates an infinite background loop which moves leftwards. 
 */
public class LoopingBackgroundLayer extends ExtendedLayer {
	
	private static final int NO_OF_SPRITES = 2;
	private LinkedList<Sprite> backgroundChain;
	private float imgWidth;
	
	/**
	 * @param resId The resource ID of the drawable to be looped.
	 */
	public LoopingBackgroundLayer(int resId) {
		createBackgroundChain(resId);
	}
	
	@Override
	public void update(float dt) {
		if (firstSpriteHasLeftScreen()) rearrangeChain();
		super.update(dt);
	}
	
	/**
	 * @return <code>true</code> if the first (leftmost) sprite of the chain has left the screen completely.
	 */
	private boolean firstSpriteHasLeftScreen(){
		return backgroundChain.peek().getPosition().getX() <= -imgWidth;
	}
	
	/**
	 * Moves the first (leftmost) sprite of the chain to the end of the chain
	 */
	private void rearrangeChain(){
		Sprite s = backgroundChain.poll();
		s.setPosition(s.getPosition().getX()+imgWidth*NO_OF_SPRITES, 0);
		backgroundChain.add(s);
	}
	
	private void createBackgroundChain(int resId){
		backgroundChain = new LinkedList<Sprite>();
		Image img = new Image(resId);
		imgWidth = img.getWidth();
		for (int i=0; i<NO_OF_SPRITES; i++){
			Sprite s = new Sprite(img);
			s.setOffset(0, 0);
			s.setPosition(i*imgWidth, 0);
			s.setSpeed(-Constants.BACKGROUND_SPEED, 0);
			addSprite(s);
			backgroundChain.add(s);
		}
	}

}
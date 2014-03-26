package no.ntnu.flapmyfish.tokens;

import no.ntnu.flapmyfish.Constants;
import sheep.graphics.Image;

public class Token extends ExtendedSprite {
	
	public Token(Image img) {
		super(img);
	}
	
	public Token() {
		super();
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		if (getPosition().getX()+Constants.WINDOW_WIDTH/3.0f/*+(getWidth()/2.0f)*/<0) {
			die();
		}
	}

}

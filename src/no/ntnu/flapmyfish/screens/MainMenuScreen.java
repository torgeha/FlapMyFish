package no.ntnu.flapmyfish.screens;

import java.util.ArrayList;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.LoopingBackgroundLayer;
import no.ntnu.flapmyfish.R;
import no.ntnu.flapmyfish.gui.ImageButton;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.gui.Widget;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;
import sheep.math.BoundingBox;
import android.graphics.Canvas;

public class MainMenuScreen extends State implements WidgetListener {

	private LoopingBackgroundLayer loopingBgLayer;
	private ArrayList<ImageButton> btns;

	public MainMenuScreen(){
		init();
	}

	public void draw(Canvas canvas){
		loopingBgLayer.draw(canvas, new BoundingBox(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
		for (ImageButton btn : btns){
			btn.draw(canvas);
		}
	}

	public void update(float dt){
		loopingBgLayer.update(dt);
	}

	public void init(){
		loopingBgLayer = new LoopingBackgroundLayer(R.drawable.background1);

		generateButtons(new String[] {"Play", "Instructions", "Settings"});
	}

	private void generateButtons(String[] labels){
		Image[] btn_imgs = new Image[]{new Image(R.drawable.btn_bg_menu_idle), new Image(R.drawable.btn_bg_menu_down)};
		btns = new ArrayList<ImageButton>();
		for (int i=0; i<labels.length; i++){
			float y = (i == 0) ? 66.67f * Constants.SCALE : btns.get(i-1).getPosition().getY()+btn_imgs[0].getHeight() + 13.33f*Constants.SCALE;			
			ImageButton btn = new ImageButton(Constants.WINDOW_WIDTH/2, y, labels[i], btn_imgs);
			addTouchListener(btn);
			btn.addWidgetListener(this);
			btns.add(btn);
		}
	}

	@Override
	public void actionPerformed(WidgetAction action) {
		Widget source = action.getSource();
		if (source == btns.get(0)){
			//Play
//			getGame().popState();
			getGame().pushState(new GameScreen());			
		} else if (source == btns.get(1)){
			//Instructions
		} else if (source == btns.get(2)){
			//Settings			
		}
	}

}
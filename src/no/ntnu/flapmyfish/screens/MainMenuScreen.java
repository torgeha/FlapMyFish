package no.ntnu.flapmyfish.screens;

import java.util.ArrayList;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.LoopingBackgroundLayer;
import no.ntnu.flapmyfish.R;
import no.ntnu.flapmyfish.controller.StateListener;
import no.ntnu.flapmyfish.controller.StateListener.GameState;
import no.ntnu.flapmyfish.gui.ImageButton;
import no.ntnu.flapmyfish.tokens.ExtendedSprite;
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
	private ExtendedSprite headerLogo;
	
	public static final int BTN_ID_SINGLE_PLAYER = 500;
	public static final int BTN_ID_MULTI_PLAYER = 501;
	public static final int BTN_ID_INSTRUCTIONS = 502;
	
	private StateListener listener;
	
	public MainMenuScreen(StateListener listener){
		this();
		setStateListener(listener);
	}

	public MainMenuScreen(){
		init();
	}

	public void draw(Canvas canvas){
		loopingBgLayer.draw(canvas, new BoundingBox(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
		headerLogo.draw(canvas);
		for (ImageButton btn : btns){
			btn.draw(canvas);
		}
	}

	public void update(float dt){
		loopingBgLayer.update(dt);
	}
	
	public void setStateListener(StateListener listener){
		this.listener = listener;
	}

	public void init(){
		loopingBgLayer = new LoopingBackgroundLayer(R.drawable.background);
		headerLogo = new ExtendedSprite(new Image(R.drawable.header));
		headerLogo.setPosition(Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/5.5f );
		headerLogo.update(0);
		generateButtons(new String[] {"Single player", "Multiplayer", "Instructions"});
	}

	private void generateButtons(String[] labels){		
		btns = new ArrayList<ImageButton>();
		for (int i=0; i<labels.length; i++){
			ExtendedSprite idle = new ExtendedSprite(new Image(R.drawable.btn_bg_menu_idle));
			ExtendedSprite down = new ExtendedSprite(new Image(R.drawable.btn_bg_menu_down));
			idle.setSizeByHeight(0.17f);
			down.setSizeByHeight(0.17f);
			ExtendedSprite[] btn_sprites = new ExtendedSprite[]{idle, down};
			float y = (i == 0) ? Constants.WINDOW_HEIGHT / 2.25f : btns.get(i-1).getPosition().getY()+(1.2f * btn_sprites[0].getHeight());			
			ImageButton btn = new ImageButton(Constants.WINDOW_WIDTH/2, y, labels[i], btn_sprites);
			addTouchListener(btn);
			btn.addWidgetListener(this);
			btns.add(btn);
		}
	}

	@Override
	public void actionPerformed(WidgetAction action) {
		Widget source = action.getSource();
		if (source == btns.get(0)){
			//Single player
			//getGame().popState();
			listener.buttonClicked(BTN_ID_SINGLE_PLAYER);
			listener.gameStateChanged(GameState.START_SP);
			//getGame().pushState(new GameScreen());
		} else if (source == btns.get(1)){
			//Multiplayer
			listener.buttonClicked(BTN_ID_MULTI_PLAYER);
		} else if (source == btns.get(2)){
			//Instructions
			listener.buttonClicked(BTN_ID_INSTRUCTIONS);
			getGame().pushState(new InstructionsScreen());
		}
	}

}
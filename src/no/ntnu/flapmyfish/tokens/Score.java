package no.ntnu.flapmyfish.tokens;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.util.GraphicsUtils;
import android.graphics.Canvas;
import android.graphics.Color;

public class Score extends Token {

	private int opponentScore;
	private boolean isMultiplayer;
	private Player player;
	
	public Score(Player player){
		super();
		init(player);
	}
	
	public void setOpponentPoints(int score){
		opponentScore = score;
	}

	public void setMultiplayer(boolean isMultiplayer) {
		this.isMultiplayer = isMultiplayer;
	}
	
	private void init(Player player) {
		this.player = player;
		//Set the score in the upper left corner of the screen
		setPosition(10f * Constants.SCALE, 46.67f * Constants.SCALE);
	}
	
	public void draw(Canvas canvas) { 
		canvas.drawText(Integer.toString(player.getPoints()), getPosition().getX(), getPosition().getY(), GraphicsUtils.createPaint(48*Constants.SCALE, Color.WHITE, false));		
		if (isMultiplayer) canvas.drawText("Opponent: "+ opponentScore, getPosition().getX(), getPosition().getY()+(20*Constants.SCALE), GraphicsUtils.createPaint(16*Constants.SCALE, Color.WHITE, false));
		else canvas.drawText("Highscore: "+Constants.HIGHSCORE, getPosition().getX(), getPosition().getY()+(20*Constants.SCALE), GraphicsUtils.createPaint(16*Constants.SCALE, Color.WHITE, false));
	}
	
}
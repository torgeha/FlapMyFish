package no.ntnu.flapmyfish.tokens;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.util.GraphicsUtils;
import android.graphics.Canvas;
import android.graphics.Color;

//TODO: Implement callback or observer pattern
public class CountDownTimer extends Token {
	
	private float remainingTime;
	private String finishedMessage;
	private String countMessage = "";
	
	public CountDownTimer(float time, String finishedMessage){
		this.finishedMessage = finishedMessage;
		this.remainingTime = time;
	}
	
	public void update(float dt){
		remainingTime -= dt;
		if (isFinished()) countMessage = finishedMessage; 
		else countMessage = String.valueOf((int) Math.ceil(remainingTime));
	}
	
	public void draw(Canvas canvas){
		canvas.drawText(countMessage, Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/4+20, GraphicsUtils.createPaint(150, Color.WHITE, true));
	}
	
	public boolean isFinished(){
		return remainingTime <= 0;
	}
	
	public boolean hasAciveMessage(){
		return remainingTime > -1;
	}

}
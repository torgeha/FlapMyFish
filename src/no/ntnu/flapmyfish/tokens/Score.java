package no.ntnu.flapmyfish.tokens;

import no.ntnu.flapmyfish.Constants;
import no.ntnu.flapmyfish.util.GraphicsUtils;
import android.graphics.Canvas;
import android.graphics.Color;

public class Score extends Token {

	private int points;
	private static Score score;
	private float counter;
	
	public Score() {
		super();
		init();
	}
	
	public static Score getInstance() {
		if (score == null) {
			score = new Score();
		}
		return score;
	}
	
	private void init() {
		this.points = 0;
		this.counter = 0f;
		
		//Set the score in the upper left corner of the screen
		setPosition(10f * Constants.SCALE, 46.67f * Constants.SCALE);
	}
	
	public void draw(Canvas canvas) { 
		canvas.drawText(Integer.toString(points), getPosition().getX(), getPosition().getY(), GraphicsUtils.createPaint(48*Constants.SCALE, Color.WHITE, false));
		canvas.drawText("Highscore: "+Constants.HIGHSCORE, getPosition().getX(), getPosition().getY()+(20*Constants.SCALE), GraphicsUtils.createPaint(16*Constants.SCALE, Color.WHITE, false));
	}
	
	public void update(float dt) {
		counter += dt;
		if ((counter / 1.0f) > Constants.SCORE_FREQUENCY) {
			addTravelPoints();
			counter = 0f;
		}
	}
	
	private void addPoints(int points) {
		this.points += points;
	}
	
	public void addFoodPoints() {
		addPoints(Constants.FOOD_POINTS);
	}
	
	public void addTravelPoints() {
		addPoints(Constants.TRAVEL_POINTS);
	}
	
	public int getPoints() {
		return points;
	}
	
}
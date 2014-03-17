package no.ntnu.flapmyfish.tokens;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import no.ntnu.flapmyfish.Constants;

public class Score extends Token {

	private int points;
	private static Score score = null;
	private Paint scorePaint;
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
		
		//Set the score in the upper left corner
		setPosition(Constants.WINDOW_WIDTH / 15, Constants.WINDOW_HEIGHT / 15);
		
		Typeface tf = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD);
		this.scorePaint = new Paint();
		scorePaint.setTypeface(tf);
		scorePaint.setColor(Constants.SCORE_COLOR);
		scorePaint.setTextSize(Constants.WINDOW_HEIGHT / 22);
	}
	
	public void draw(Canvas canvas) {
		canvas.drawText(points + "", getPosition().getX(), getPosition().getY(), scorePaint);
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

package no.ntnu.flapmyfish.tokens;

import no.ntnu.flapmyfish.Constants;

public class Score extends Token {

	private int points;
	private static Score score = null;
	
	public Score() {
		super();
	}
	
	public static Score getInstance() {
		if (score == null) {
			score = new Score();
		}
		return score;
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

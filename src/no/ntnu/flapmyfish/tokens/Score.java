package no.ntnu.flapmyfish.tokens;

public class Score extends Token {

	private int points;
	private static Score score = null;
	
	public Score() {
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
		addPoints(Cosntants.TRAVEL_POINTS);
	}
	
	public int getPoints() {
		return points;
	}
	
}

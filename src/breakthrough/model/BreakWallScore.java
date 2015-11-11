package breakthewall.model;

public class BreakWallScore {
	
	private static int breakWallScore;
	
	public BreakWallScore(int score) {
		breakWallScore = score;
	}
	
	public int getCurrentScore() {
		return breakWallScore;
	}
	
	public void addPoints(int points) {
		breakWallScore += points;
	}
	
	public void subtractPoints(int points) {
		breakWallScore -= points;
	}

}

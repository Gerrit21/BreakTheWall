package breakthewall.model;

/**
 * Class to set and retrieve the game score.
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, October 2015.
 */
public class BreakWallScore {
	
	private int breakWallScore;
	
	/**
	 * Constructor takes score as a parameter
	 * and sets it to current game score
	 * 
	 * @param score the given score
	 */
	public BreakWallScore(int score) {
		breakWallScore = score;
	}
	
	/** 
	 * @return breakWallScore the current game score
	 */
	public int getCurrentScore() {
		return this.breakWallScore;
	}

	/** 
	 * Public method sets the game score
	 * 
	 * @param score the new game score
	 */
	public void setCurrentScore(int score) {
		breakWallScore = score;
	}
	
	/**
	 * Public method to add points to the score
	 * 
	 * @param points the points to be added to the score
	 */	
	public void addPoints(int points) {
		breakWallScore += points;
	}

	/**
	 * Public method to subtract points from the score
	 * 
	 * @param points the points to be subtracted from the score
	 */	
	public void subtractPoints(int points) {
		breakWallScore -= points;
	}

}

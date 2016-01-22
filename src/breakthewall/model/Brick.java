package breakthewall.model;

/**
 * Interface contains common methods of all brick types
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, October 2015.
 */
public interface Brick {
	
	/**
	 * @return the bonus object of a bonus brick
	 */
	public Bonus getBonusObject();
	
	/** 
	 * @return whether a brick has a bonus object (true) or not (false)
	 */
	public boolean hasBonusObject();
	
	/**
	 * @return the stability of the brick object
	 */
	public int getStability();
	
	/**
	 * Public method sets the stabibility of a brick
	 * 
	 * @param stability the stability of a brick object
	 */
	public void setStability(int stability);
	
	/**
	 * @return the points which are released when the brick object is destroyed
	 */
	public int getPoints();
	
	/**
	 * Public method sets the points which are released when the brick object is destroyed
	 * @param points
	 */
	public void setPoints(int points);

}

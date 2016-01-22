package breakthewall.model;

/**
 * Interface for common methods of all bonus brick types
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, October 2015.
 */
public interface Bonus {

	/**
	 * Returns the specific type of Bonus
	 * 
	 * @return the bonus type as a String
	 */
	public String getBonusType();
	/**
	 * Returns an image Reference of the Bonus 
	 * which is handed to the brick the bonus is associated with 
	 * 
	 * @return the image reference of the bonus as a String
	 */
	public String getImage();
}

package breakthewall.model;

/**
 * Interface for common methods of all game elements
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, October 2015.
 */
public interface GameElement {
	
	/**
	 * @return the x-coordinate of the element
	 */
	public int getXCoord();
	
	/**
	 * Public method to set the x-coordinate of the element
	 * 
	 * @param xCoord the new x-coordinate of the element
	 */
	public void setXCoord(int xCoord);

	/**
	 * @return the y-coordinate of the element
	 */
	public int getYCoord();
	
	/**
	 * Public method to set the y-coordinate of the element
	 * 
	 * @param yCoord the new y-coordinate of the element
	 */
	public void setYCoord(int yCoord);
	
	/**
	 * @return the height of the element
	 */
	public int getHeight();
	
	/**
	 * Public method to set height of the element
	 * 
	 * @param height the new height of the element
	 */
	public void setHeight(int height);
	
	/**
	 * @return the width of the element
	 */
	public int getWidth();
	
	/**
	 * Public method to set width of the element
	 * 
	 * @param width the new width of the element
	 */
	public void setWidth(int width);	
	
	/**
	 * Public method to set image reference of the element
	 * 
	 * @param imagePath the new image reference of the element
	 */
	public void setImage(String imagePath);
	
	/**
	 * @return the image reference of the element
	 */
	public String getImage();
	
	/**
	 * @return the id of the element
	 */
	public String getId();
	
	/**
	 * Public method to set the id of the element
	 * 
	 * @param id the id of the element
	 */
	public void setId(String id);
	
	/**
	 * @return the state of the element, whether it has been destroyed (true) or not (false)
	 */
	public boolean getDestroyedState();
	
	/**
	 * Public method to set the state of the element, 
	 * whether it has been destroyed (true) or not (false)
	 * 
	 * @param id the state of the element
	 */
	public void setDestroyedState(boolean isDestroyed);

}

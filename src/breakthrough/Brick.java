package breakthrough;

/**
 * Interface mit gemeinsamen Methoden aller Brick-Typen.
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public interface Brick {
	
	public Bonus getBonusObject();
	
	public boolean hasBonusObject();
	
	public int getXCoord();
	
	public void setXCoord(int xCoord);
	
	public int getYCoord();
	
	public void setYCoord(int yCoord);
	
	public int getHeight();
	
	public void setHeight(int height);
	
	public int getWidth();
	
	public void setWidth(int width);
	
	public int getStability();
	
	public void setStability(int stability);	
	
	public void setImage(String imagePath);
	
	public String getImage();
	
	public String getId();
	
	public void setId(String id);

}

package breakthewall.model;

/**
 * Interface mit gemeinsamen Methoden aller Brick-Typen.
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public interface Brick {
	
	public Bonus getBonusObject();
	
	public boolean hasBonusObject();
	
	public int getStability();
	
	public void setStability(int stability);	

}

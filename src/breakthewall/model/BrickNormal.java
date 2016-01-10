package breakthewall.model;

import javax.swing.ImageIcon;

import breakthewall.BreakWallConfig;

/**
 * Klasse zur Erstellung eines normalen Bricks.
 * Über die Variable stability kann die Härte eines normalen Bricks
 * variiert werden.
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BrickNormal implements GameElement, Brick {

	private int xCoord, yCoord, height, width, stability, points;
	private String imagePath;
	private String id = "";
	private boolean isDestroyed = false;

	/**
	 *  Konstruktor für variierend harte Bricks.
	 *  @param stability Für den Brick zu setzende Stabilität
	 */
	public BrickNormal(int stability) {
		imagePath = BreakWallConfig.brickImgHard;
		this.stability = stability;
		this.points = BreakWallConfig.pointsNormal; 
		this.width = new ImageIcon(imagePath).getImage().getWidth(null);
		this.height = new ImageIcon(imagePath).getImage().getHeight(null);
	}
	
	/**
	 *  Konstruktor für normal harte Bricks mit einer stability von 1 (siehe BreakWallData).
	 */
	public BrickNormal() {
		imagePath = BreakWallConfig.brickImgNormal;
		this.stability = BreakWallConfig.stabilityNormal;
		this.points = BreakWallConfig.pointsNormal; 
		this.width = new ImageIcon(imagePath).getImage().getWidth(null);
		this.height = new ImageIcon(imagePath).getImage().getHeight(null);
	}
	
	public boolean hasBonusObject() {
		return false;
	}

	@Override
	public int getXCoord() {
		return this.xCoord;
	}

	@Override
	public void setXCoord(int xCoord) {
		this.xCoord = xCoord;
		
	}

	@Override
	public int getYCoord() {
		return this.yCoord;
	}

	@Override
	public void setYCoord(int yCoord) {
		this.yCoord = yCoord;
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;		
	}
	
	@Override
	public int getStability() {
		return this.stability;
	}

	@Override
	public void setStability(int stability) {
		this.stability = stability;
	}	

	@Override
	public void setImage(String imagePath) {
		this.imagePath = imagePath;
		
	}

	@Override
	public String getImage() {
		return this.imagePath;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Bonus getBonusObject() {
		return null;
	}

	@Override
	public boolean getDestroyedState() {
		return this.isDestroyed;
	}

	@Override
	public void setDestroyedState(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
		
	}

	@Override
	public int getPoints() {
		return this.points;
	}

	@Override
	public void setPoints(int points) {
		this.points = points;		
	}
}

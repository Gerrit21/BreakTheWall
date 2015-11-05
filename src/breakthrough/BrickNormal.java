package breakthrough;

import javax.swing.ImageIcon;

/**
 * Klasse zur Erstellung eines normalen Bricks.
 * Über die Variable stability kann die Härte eines normalen Bricks
 * variiert werden.
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BrickNormal implements Brick {

	private int xCoord;
	private int yCoord;
	private int height;
	private int width;
	private String imagePath;
	private String id = "";
	private int stability = BreakWallData.stabilityNormal;

	/**
	 *  Konstruktor für variierend harte Bricks.
	 *  @param stability Für den Brick zu setzende Stabilität
	 */
	public BrickNormal(int stability) {
		imagePath = BreakWallData.brickImgHard;
		this.stability = stability;
		System.out.println("Härte: " + stability);
		this.width = new ImageIcon(imagePath).getImage().getWidth(null);
		this.height = new ImageIcon(imagePath).getImage().getHeight(null);
	}
	
	/**
	 *  Konstruktor für normal harte Bricks mit einer stability von 1 (siehe BreakWallData).
	 */
	public BrickNormal() {
		imagePath = BreakWallData.brickImgNormal;
		this.width = new ImageIcon(imagePath).getImage().getWidth(null);
		this.height = new ImageIcon(imagePath).getImage().getHeight(null);
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
	public void activateBonusObject() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

}

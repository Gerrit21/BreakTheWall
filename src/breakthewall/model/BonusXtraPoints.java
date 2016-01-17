package breakthewall.model;

import breakthewall.BreakWallConfig;

/** 
 * Class of bonus object which lets bonus points
 * fall from a hit brick
 * 
 * @author Mareike R�ncke, Gerrit Schulte
 * @version 1.0, October 2015.
 */
public class BonusXtraPoints implements Bonus, GameElement {
	
	private String imagePath = BreakWallConfig.brickXtraPointsImg;
	private String id;
	private int bonusPoints;
	private int xCoord, yCoord, height, width;
	private boolean isDestroyed = false;
	
	public int setBonusPoints() {
		bonusPoints = BreakWallModel.randomFromRange(1, 20);
		return bonusPoints;
	}
	
	public int getBonusPoints() {
		setBonusPoints();
		return bonusPoints;
	}
	
	public void moveBonus(int speed) {
		yCoord = yCoord + speed;
	}
	
	@Override
	public String getBonusType() {
		return this.getClass().getSimpleName();
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
	public void setImage(String imagePath) {
		this.imagePath = imagePath;
	}
	
	/**
	 * @ return imagePath image path of image associated with this bonus
	 */
	@Override
	public String getImage() {
		return this.imagePath;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
		
	}

	@Override
	public boolean getDestroyedState() {
		return this.isDestroyed;
	}

	@Override
	public void setDestroyedState(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
		
	}

}

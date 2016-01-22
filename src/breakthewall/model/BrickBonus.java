package breakthewall.model;

import breakthewall.BreakWallConfig;
import breakthewall.view.BreakWallView;

/**
 * Class to create bricks with bonus objects.
 * One of several types of bonus objects is randomly created
 * when bonus brick is created.
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, October 2015.
 */
public class BrickBonus implements GameElement, Brick {
	
	private int yCoord, xCoord, height, width, points;
	private String imagePath;
	private String id = "";
	private int stability = BreakWallConfig.stabilityNormal;
	private boolean isDestroyed = false;	
	private Bonus randomBonus;
	
	/**
	 * Constructor call creates a random bonus for the brick
	 */
	public BrickBonus() {
		setRandomBonus();
		this.points = BreakWallConfig.pointsBonus;
		imagePath = getBonusObject().getImage();
		this.width = BreakWallView.getImageByURL(this, imagePath).getWidth(null);
		this.height = BreakWallView.getImageByURL(this, imagePath).getHeight(null);
	}
	
	/**
	 * Constructor call creates a bonus brick with the specified bonus
	 * 
	 * @param bonusRef String reference of the specified bonus object
	 */
	public BrickBonus(String bonusRef) {
		setBonusObject(bonusRef);
		this.points = BreakWallConfig.pointsBonus;
		imagePath = getBonusObject().getImage();
		this.width = BreakWallView.getImageByURL(this, imagePath).getWidth(null);
		this.height = BreakWallView.getImageByURL(this, imagePath).getHeight(null);		
	}
	
	/**
	 * Bonus type of bonus brick instance is randomly created by this private method
	 */
	private void setRandomBonus() {
		int randomInt = BreakWallModel.randomFromRange(1, 4);
		if(randomInt == 1) {
			randomBonus = new BonusXtraLife();
		} else if(randomInt == 2) {
			randomBonus = new BonusBallSpeed();
		} else if(randomInt == 3) {
			randomBonus = new BonusPaddleWidth();
		} else {
			randomBonus = new BonusXtraPoints();
		}
	}
	
	/**
	 * Private methods sets the bonus of the brick object to a given type
	 * 
	 * @param bonusRef the given type of bonus object
	 */
	private void setBonusObject(String bonusRef) {
		if(bonusRef.equals("BonusXtraLife")) {
			randomBonus = new BonusXtraLife();
		} else if(bonusRef.equals("BonusBallSpeed")) {
			randomBonus = new BonusBallSpeed();
		} else if(bonusRef.equals("BonusPaddleWidth")) {
			randomBonus = new BonusPaddleWidth();
		} else if(bonusRef.equals("BonusXtraPoints")) {
			randomBonus = new BonusXtraLife();
		}		
	}
	
	
	@Override
	public Bonus getBonusObject() {
		return randomBonus;
	}
	
	@Override
	public boolean hasBonusObject() {
		return true;
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

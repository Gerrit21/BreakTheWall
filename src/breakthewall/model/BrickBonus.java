package breakthewall.model;

import breakthewall.BreakWallConfig;
import breakthewall.view.BreakWallView;

/**
 * Klasse zur Erzeugung eines Bricks mit Bonus-Objekt
 * Es wird einer von verschiedenen Typen von Bonus-Objekten zufällig erzeugt
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BrickBonus implements GameElement, Brick {
	
	private int yCoord, xCoord, height, width, points;
	private String imagePath;
	private String id = "";
	private int stability = BreakWallConfig.stabilityNormal;
	private boolean isDestroyed = false;
	
	private Bonus randomBonus;
	
	/*
	 * Bei Konstruktoraufruf wird dem Bonus-Brick ein zufälliger Bonus hinzugefügt
	 */
	public BrickBonus() {
		setRandomBonus();
		this.points = BreakWallConfig.pointsBonus;
		imagePath = getBonusObject().getImage();
		this.width = BreakWallView.getImageByURL(this, imagePath).getWidth(null);
		this.height = BreakWallView.getImageByURL(this, imagePath).getHeight(null);
	}
	
	public BrickBonus(String bonusRef) {
		setBonusObject(bonusRef);
		this.points = BreakWallConfig.pointsBonus;
		imagePath = getBonusObject().getImage();
		this.width = BreakWallView.getImageByURL(this, imagePath).getWidth(null);
		this.height = BreakWallView.getImageByURL(this, imagePath).getHeight(null);		
	}
	
	/**
	 * Der Bonus-Typ für eine Bonus-Brick-Instanz wird zufällig erstellt
	 */
	private void setRandomBonus() {
		int randomInt = BreakWallModel.randomFromRange(1, 3);
		if(randomInt == 1) {
			randomBonus = new BonusXtraLife();
		} else if(randomInt == 2) {
			randomBonus = new BonusBallSpeed();
		} else {
			randomBonus = new BonusPaddleWidth();
		}
	}

	public Bonus getBonusObject() {
		return randomBonus;
	}
	
	public void setBonusObject(String bonusRef) {
		if(bonusRef.equals("BonusXtraLife")) {
			randomBonus = new BonusXtraLife();
		} else if(bonusRef.equals("BonusBallSpeed")) {
			randomBonus = new BonusBallSpeed();
		} else if(bonusRef.equals("BonusPaddleWidth")) {
			randomBonus = new BonusPaddleWidth();
		}		
	}
	
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

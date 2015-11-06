package breakthrough;

import javax.swing.ImageIcon;
import java.util.Random;

/**
 * Klasse zur Erzeugung eines Bricks mit Bonus-Objekt
 * Es wird einer von verschiedenen Typen von Bonus-Objekten zufällig erzeugt
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BrickBonus implements Brick {
	
	private int xCoord;
	private int yCoord;
	private int height;
	private int width;
	private String imagePath;
	private String id = "";
	private int stability = BreakWallData.stabilityNormal;
	
	private Bonus randomBonus;
	
	/*
	 * Bei Konstruktoraufruf wird dem Bonus-Brick ein zufälliger Bonus hinzugefügt
	 */
	public BrickBonus() {
		setRandomBonus();
		imagePath = getBonusObject().getImage();
		this.width = new ImageIcon(imagePath).getImage().getWidth(null);
		this.height = new ImageIcon(imagePath).getImage().getHeight(null);
	}
	
	/**
	 * Der Bonus-Typ für eine Bonus-Brick-Instanz wird zufällig erstellt
	 */
	private void setRandomBonus() {
		int randomInt = BreakWall.randomFromRange(1, 3);
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

}

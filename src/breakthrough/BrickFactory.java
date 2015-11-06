package breakthrough;

import java.util.ArrayList;

/**
 * Klasse zur Erstellung einer Brick-Wand, bestehend aus verschiedenen Brick-Typen.
 * 
 * @author Mareike R�ncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BrickFactory {
	
	private BreakWallView gameField;
	private Brick randomBrick;
	private ArrayList<Brick> brickList;
	private int wallWidth = BreakWallData.wallWidth;
	private int wallHeight = BreakWallData.wallHeight;
	private int brickX = 30;
	private int brickY = 30 + BreakWallData.barHeight;
	
	/*
	 * Konstruktor ruft die Methode zur Erzeugung der Brick-Wand auf.
	 * Diese wird in das �bergebene Spielfeld gezeichnet.
	 */
	
	public BrickFactory(BreakWallView gameField) {
		this.gameField = gameField;		
		brickList = new ArrayList<Brick>();
		buildWall();
	}
	
	/**
	 * Ruft die Methode getRandomBrick() zur Erzeugung eines Bricks auf und 
	 * zeichnet diesen Brick in das Spielfeld.
	 * Die Begrenzungen f�r die Brick-Wand sind in der Klasse BreakWallData definiert.
	 */
	
	public void buildWall() {
		// setzt erzeugte Bricks mit einem Abstand von 10 Pixeln neben- und untereinander
		while(getBrickY() < wallHeight) {			
			while(getBrickX() < wallWidth) {
				randomBrick = getRandomBrick();
				randomBrick.setXCoord(getBrickX());
				randomBrick.setYCoord(getBrickY());				
				randomBrick.setId(Integer.toString(getBrickX()) + Integer.toString(getBrickY()));
				brickList.add(randomBrick);
				gameField.addElementToGameField(randomBrick.getImage(), randomBrick.getId(), getBrickX(), getBrickY(), randomBrick.getWidth(), randomBrick.getHeight());
				setBrickX(getBrickX() + randomBrick.getWidth() + 10);
			}
			setBrickY(getBrickY() + randomBrick.getHeight() + 10);
			// versetzt die Brick-Zeilen in zuf�lligem Abstand vom Rand
			setBrickX(BreakWall.randomFromRange(1, 3) * 15);
		}
	}
	
	/**
	 * �ber eine Random-Funktion wird zuf�llig einer von 3 Brick-Typen erzeugt.
	 * Die Wahrscheinlichkeit f�r einen Typen ist in der Klasse BreakWallData definiert.
	 */
	
	private void setRandomBrick() {
		int randomInt = BreakWall.randomFromRange(1, 100);
		if(randomInt <= BreakWallData.normalPossible) {
			randomBrick = new BrickNormal();
		} else if(randomInt > BreakWallData.normalPossible) {
			if(randomInt < (BreakWallData.bonusPossible + BreakWallData.normalPossible)) {
				randomBrick = new BrickBonus();				
			} else if(randomInt >= (BreakWallData.bonusPossible + BreakWallData.normalPossible)) {
				randomBrick = new BrickNormal(BreakWallData.stabilityHard);
			}
		}
	}
	
	private Brick getRandomBrick() {
		setRandomBrick();
		return this.randomBrick;
	}
	
	public ArrayList<Brick> getBrickList() {
		return this.brickList;
	}
	
	public void removeFromBrickList(Brick removeBrick) {
		this.brickList.remove(removeBrick);
	}
	
	public void setBrickX(int x) {
		this.brickX = x;
	}
	
	public int getBrickX() {
		return this.brickX;
	}
	
	public void setBrickY(int y) {
		this.brickY = y;
	}
	
	public int getBrickY() {
		return this.brickY;
	}
}

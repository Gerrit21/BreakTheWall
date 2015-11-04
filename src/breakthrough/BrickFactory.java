package breakthrough;

/**
 * Klasse zur Erstellung einer Brick-Wand, bestehend aus verschiedenen Brick-Typen.
 * TEST
 * 
 * @author Mareike R�ncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BrickFactory {
	
	private BreakWallView gameField;
	Brick randomBrick;
	private int wallWidth = BreakWallData.wallWidth;
	private int wallHeight = BreakWallData.wallHeight;
	private int brickX = 30;
	private int brickY = 30;
	
	/*
	 * Konstruktor ruft die Methode zur Erzeugung der Brick-Wand auf.
	 * Diese wird in das �bergebene Spielfeld gezeichnet.
	 */
	
	public BrickFactory(BreakWallView gameField) {
		this.gameField = gameField;
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
				gameField.addElementToGameField(randomBrick.getImage(), getBrickX(), getBrickY(), randomBrick.getWidth(), randomBrick.getHeight());
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
				((BrickBonus) randomBrick).getBonusObject().activate();
			} else if(randomInt >= (BreakWallData.bonusPossible + BreakWallData.normalPossible)) {
				randomBrick = new BrickNormal(BreakWallData.stabilityHard);
			}
		}
	}
	
	private Brick getRandomBrick() {
		setRandomBrick();
		return this.randomBrick;
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

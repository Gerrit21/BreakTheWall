package breakthrough;

/**
 * Klasse zur Kapselung global ben�tigter Spielvariablen.
 * 
 * @author Mareike R�ncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BreakWallData {
	/*
	 *  Variablen für das gesamte Spielfeld
	 */	
	public static final String title = "Break The Wall";
	public static final int gameFieldWidth = 500;
	public static final int gameFieldHeight = 478;
	public static final int offsetWidth = gameFieldWidth;
	public static final int offsetHeight = 455;
	public static final int wallWidth = gameFieldWidth - 60;
	public static final int wallHeight = 150; 
	
	/*
	 *  Variablen für Spiel-Elemente
	 */
	
	// Paddle-Variablen
	public static final String paddleImg = "img/paddle.png";
	public static final int initialPaddleX = 300;
	public static final int initialPaddleY = 430;
	public static final int paddleOffsetTop = 4;
	public static int paddleSpeed = 5;
	
	// Ball-Variablen
	public static final String ballImg = "img/ball.png";
	// für eventuelle weiße Ränder um das Bild
	// falls keine Ränder vorhanden, auf 0 setzen
	public static final int ballOffset = 4;
	public static final int initialBallX = 320;
	public static final int initialBallY = initialPaddleY - 16;
	public static final int initialBallXDir = 1;
	public static final int initialBallYDir = -2;	
	public static int ballSpeed = 1;
	
	// Brick-Variablen
	public static final String brickImgNormal = "img/brick_n.png";
	public static final String brickImgHard = "img/brick_h.png";
	public static final String brickXtraLifeImg = "img/Ice_cream.png";
	public static final int stabilityNormal = 1;
	public static final int stabilityHard = 3;
	
	/*
	 * Level-Schwierigkeiten
	 * Level 1
	 * 
	 */
	
	// Leben
	public static int lifeCount = 3;
	// Bilder	
	public static final String bgImagePath = "img/bg_1.png";
	
	// Wahrscheinlichkeiten für Brick-Varianten in Prozent	
	public static int bonusPossible = 20;
	public static int hardPossible = 5;
	public static int normalPossible = 100 - (hardPossible + bonusPossible);
}

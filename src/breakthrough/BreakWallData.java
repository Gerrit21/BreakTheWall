package breakthrough;

/**
 * Klasse zur Kapselung global ben�tigter Spielvariablen.
 * 
 * @author Mareike R�ncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BreakWallData {
	/*
	 *  Variablen f�r das gesamte Spielfeld
	 */
	public static final String bgImagePath = "img/bg_1.png";
	public static final String title = "Break The Wall";
	public static final int gameFieldWidth = 600;
	public static final int gameFieldHeight = 520;
	public static final int offsetWidth = gameFieldWidth - 69;
	public static final int offsetHeight = 500;
	public static final int wallWidth = 540;
	public static final int wallHeight = 150; 
	
	/*
	 *  Variablen f�r Spiel-Elemente
	 */
	
	// Paddle-Variablen
	public static final String paddleImg = "img/paddle.png";
	public static final int initialPaddleX = 300;
	public static final int initialPaddleY = 350;
	public static final int initialPaddleSpeed = 5;
	
	// Ball-Variablen
	public static final String ballImg = "img/ball.png";
	public static final int initialBallX = 320;
	public static final int initialBallY = 335;
	public static final int initialBallSpeed = 5;
	
	// Brick-Variablen
	public static final String brickImg = "img/brick_n.png";
	public static final int stabilityNormal = 1;
	public static final int stabilityHard = 3;
	
	/*
	 * Level-Schwierigkeiten
	 * Level 1
	 * 
	 */
	
	// Wahrscheinlichkeiten f�r Brick-Varianten in Prozent	
	public static final int bonusPossible = 5;
	public static final int hardPossible = 5;
	public static final int normalPossible = 100 - (hardPossible + bonusPossible);
}

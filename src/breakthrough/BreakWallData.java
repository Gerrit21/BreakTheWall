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
	public static final int gameFieldWidth = 600;
	public static final int gameFieldHeight = 498;
	public static final int offsetWidth = gameFieldWidth;
	public static final int offsetHeight = 495;
	public static final int wallWidth = gameFieldWidth - 60;
	public static final int wallHeight = 150;
	public static final int barWidth = 590;
	public static final int barHeight = 40;
	
	/*
	 *  Variablen fÃ¼r Spiel-Elemente
	 */
	
	// Paddle-Variablen
	public static final String paddleImg = "img/paddle.png";
	public static final int initialPaddleX = 300;
	public static final int initialPaddleY = 430;
	public static final int paddleOffsetTop = 4;
	public static int paddleSpeed = 10;
	
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
	 * 
	 */
	
	// Leben
	public static int lifeCount;
	// Bilder	
	public static String bgImagePath;
	
	// Wahrscheinlichkeiten fÃ¼r Brick-Varianten in Prozent	
	public static int bonusPossible;
	public static int hardPossible;
	public static int normalPossible;
	
	// hier können Variablen für levelabhängige Einstellungen gesetzt werden
	// to do: die Einstellungen sollen möglichst aus einer XML-Datei eingelesen werden
	public static void setLevelDifficulty(int level) {
		if(level == 1) {
			// Leben
			lifeCount = 3;
			// Bilder	
			bgImagePath = "img/bg_1.png";
			
			// Wahrscheinlichkeiten fÃ¼r Brick-Varianten in Prozent	
			bonusPossible = 10;
			hardPossible = 20;
			normalPossible = 100 - (hardPossible + bonusPossible);			
		}
	}

}

package breakthewall;

/**
 * Klasse zur Kapselung global ben�tigter Spielvariablen.
 * 
 * @author Mareike R�ncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BreakWallConfig {
	/*
	 *  Variablen für das gesamte Spielfeld
	 */	
	public static final String title = "Break The Wall";
	public static final int gameFieldWidth = 600;
	public static final int gameFieldHeight = 498;
	public static final int offsetWidth = gameFieldWidth - 15;
	public static final int offsetHeight = gameFieldHeight - 5;
	public static final int wallWidth = gameFieldWidth - 60;
	public static final int barWidth = 590;
	public static final int barHeight = 40;
	
	// Die Anzahl der Spiellevel, die implementiert sind
	// to do: diese Zahl kann aus einer Level-XML extrahiert werden
	public static final int levelCount = 3;
	
	/*
	 *  Variablen für Spiel-Elemente
	 */
	
	// Paddle-Variablen
	public static final String paddleImg = "img/paddle.png";
	public static final int initialPaddleX = 300;
	public static final int initialPaddleY = 430;
	public static final int paddleOffsetTop = 4;
	public static int paddleSpeed = 10;
	
	// Ball-Variablen
	public static final String ballImg = "img/ball.png";
	// f�r eventuelle wei�e R�nder um das Bild
	// falls keine R�nder vorhanden, auf 0 setzen
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
	
	// Musik & Sounds
	public static final String musicIconPlaying = "/breakthewall/media/music_playing.png";
	public static final String musicIconPausing = "/breakthewall/media/music_pausing.png";
	public static String[] backgroundMusic = {
			"/breakthewall/media/Bensound-Cute.wav", 
			"/breakthewall/media/Bellevue-Gates.wav"
			};
	
	// XML-Dokumente und Pfade
	// Pfad für XML-Dateien
	public static final String highscorePath = "src/breakthewall/xml/";
	public static final String highscoreXML = "highscore.xml";
	
	// Punktevergabe für verschiedene Arten von Bricks
	public static final int pointsNormal = 10;
	public static final int pointsBonus = 20;
	// Faktor, um den sich die Punktzahl eines Steins vervielfacht,
	// wenn die Ballgeschwindigkeit erhöht wird
	public static final int bonusFactor = 3;
	// Strafpunkte, wenn der Ball ins Aus geht (werden positiv angegeben)
	public static final int penalityPoints = 50;
	
	/*
	 * Level-Schwierigkeiten
	 * 
	 */
	
	public static int wallHeight;
	// Leben
	public static int lifeCount;
	// Bilder	
	public static String bgImagePath;
	
	// Wahrscheinlichkeiten für Brick-Varianten in Prozent	
	public static int bonusPossible;
	public static int hardPossible;
	public static int normalPossible;
	
	// hier können Variablen für levelabhängige Einstellungen gesetzt werden
	// to do: die Einstellungen für die Level sollen möglichst aus einer XML-Datei eingelesen werden
	public static void setLevelDifficulty(int level) {
		if(level == 1) {
			// Leben
			lifeCount = 3;
			// Bilder	
			bgImagePath = "img/bg_1.png";
			wallHeight = 100;
			
			// Wahrscheinlichkeiten für Brick-Varianten in Prozent	
			bonusPossible = 20;
			hardPossible = 10;
			normalPossible = 100 - (hardPossible + bonusPossible);			
		}
		if(level == 2) {
			// Bilder	
			bgImagePath = "img/bg_2.png";
			wallHeight = 120;
			// Wahrscheinlichkeiten für Brick-Varianten in Prozent	
			bonusPossible = 30;
			hardPossible = 20;
			normalPossible = 100 - (hardPossible + bonusPossible);			
		}
		
		if(level == 3) {
			// Bilder	
			bgImagePath = "img/bg_3.png";
			wallHeight = 150;
			// Wahrscheinlichkeiten für Brick-Varianten in Prozent	
			bonusPossible = 40;
			hardPossible = 30;
			normalPossible = 100 - (hardPossible + bonusPossible);			
		}		
		// to do: Variablen für das jeweilige Level aus einer XML-Datei auslesen
		// Variablen hängen vom jeweiligen Level ab
		// Leben
		// lifeCount = Integer.parseInt(BreakWallConfigXML.getConfigData("lifeCount", level));
		// Bilder	
		// bgImagePath = BreakWallConfigXML.getConfigData("bgImagePath", level);
		
		// Wahrscheinlichkeiten für Brick-Varianten in Prozent
		// bonusPossible = Integer.parseInt(BreakWallConfigXML.getConfigData("bonusPossible", level));
		// hardPossible = Integer.parseInt(BreakWallConfigXML.getConfigData("hardPossible", level));
		// normalPossible = 100 - (hardPossible + bonusPossible);		
	}

}

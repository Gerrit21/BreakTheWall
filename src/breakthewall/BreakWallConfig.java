package breakthewall;

import java.util.ArrayList;

/**
 * Klasse zur Kapselung global ben?tigter Spielvariablen.
 * 
 * @author Mareike R?ncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BreakWallConfig {
	
	// ****************************************************
	// Wichtige Variablen
	// Stehen hart in der Config, da sie möglichst nicht geändert werden sollten
	
	// XML-Dokumente und Pfade
	// Pfad für XML-Dateien
	public static final String highscorePath = "src/breakthewall/xml/";
	public static final String configXML = "config.xml";
	public static final String highscoreXML = "highscore.xml";
	
	// Breite & Höhe des Spielfelds
	public static final int gameFieldWidth = 600;
	public static final int gameFieldHeight = 498;
	public static final int offsetWidth = gameFieldWidth - 15;
	public static final int offsetHeight = gameFieldHeight - 5;
	public static final int wallWidth = gameFieldWidth - 60;
	public static final int barWidth = 590;
	public static final int barHeight = 40;
	
	// Startposition des Paddles
	public static final int initialPaddleX = 300;
	public static final int initialPaddleY = 430;
	public static final int paddleOffsetTop = 4;
	
	// Startposition des Balls
	public static final int initialBallX = 320;
	public static final int initialBallY = initialPaddleY - 16;
	
	// für eventuelle weiße Ränder um das Bild
	// falls keine Ränder vorhanden, auf 0 setzen
	public static final int ballOffset = 4;
	
	// Anzahl der Level
	// wird automatisch aus den vorhandenen Leveln in der config.xml errechnet
	public static int levelCount = 0;
	
	// ****************************************************
	// ****************************************************
	// Flexible Variablen
	// Können in der config.xml geändert werden
	
	// Spieltitel
	public static String title;
	
	// Bildpfade
	public static String paddleImg, ballImg, brickImgNormal,brickImgHard,
			brickXtraLifeImg, brickPaddleWidthImg, brickBallSpeedImg,
			musicIconPlaying, musicIconPausing;
	
	
	// Die Anzahl der Spiellevel, die implementiert sind
	// to do: diese Zahl kann aus einer Level-XML extrahiert werden
	
	// Initiale Geschwindigkeit des Paddle
	public static int paddleSpeed;

	// Abprallwinkel und Geschwindigkeit des Balls
	public static int initialBallXDir, initialBallYDir, ballSpeed;
	
	// Variablen für Brick-Stabilität
	public static int stabilityNormal, stabilityHard;
	
	// Punktevergabe für verschiedene Arten von Bricks
	public static int pointsNormal, pointsBonus; 
	
	// Faktor, um den sich die Punktzahl eines Steins vervielfacht,
	// wenn die Ballgeschwindigkeit erhöht wird
	public static int bonusFactor;
	// Strafpunkte, wenn der Ball ins Aus geht (werden positiv angegeben)
	public static int penalityPoints;
	
	// Musik & Sounds
	public static ArrayList<String> backgroundMusic = new ArrayList<String>();
	
	/*
	 * Level-Schwierigkeiten
	 * 
	 */
	
	// Höhe der Wand im Level, Leben, Wahrscheinlichkeiten für Brick-Varianten in Prozent 
	public static int wallHeight, lifeCount, bonusPossible, hardPossible, normalPossible;
	// Hintergrund-Bild	
	public static String bgImagePath;
	
	// hier können Variablen für levelabhängige Einstellungen gesetzt werden
	// to do: die Einstellungen für die Level sollen möglichst aus einer XML-Datei eingelesen werden
	public static void setLevelDifficulty(int level) {
		// Configuration ggf. aus XML-Datei laden
		// Es wird angegeben, welche Parameter ausgelesen werden sollen
		BreakWallConfigXML.setLevelConfigurations(level);
		if(hardPossible + bonusPossible <= 100) {
			normalPossible = 100 - (hardPossible + bonusPossible);
		} else {
			System.out.println("Added values of variables 'hardPossible' and 'bonusPossible' cannot be more than 100.");
			System.out.println("Please adjust config.xml accordingly.");
			System.exit(0);
		}
						
	}

}

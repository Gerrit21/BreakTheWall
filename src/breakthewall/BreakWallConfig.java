package breakthewall;

import java.util.ArrayList;

/**
 * Class to encapsulate globally used variables.
 * Variables are partly retrieved from xml data.
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, October 2015.
 */
public class BreakWallConfig {
	
	// ****************************************************
	// Important Variables:
	// Not retrieved from xml because they should not be changed
	
	// XML documents and paths
	// Path for xml data
	public static final String xmlPath = "src\\breakthewall\\xml\\";
	public static final String highscoreNetworkPath = "src\\breakthewall\\remote\\xml\\";
	public static final String configXML = "config.xml";
	public static final String highscoreXML = "highscore.xml";
	
	// width and height of game field
	public static final int gameFieldWidth = 600;
	public static final int gameFieldHeight = 498;
	public static final int offsetWidth = gameFieldWidth - 5;
	public static final int offsetHeight = gameFieldHeight - 5;
	public static final int wallWidth = gameFieldWidth - 60;
	public static final int barWidth = 600;
	public static final int barHeight = 40;
	
	// start position of paddle
	public static final int initialPaddleX = 300;
	public static final int initialPaddleY = 430;
	public static final int paddleOffsetTop = 4;
	
	// start position of ball
	public static final int initialBallX = 320;
	public static final int initialBallY = initialPaddleY - 16;
	
	// offset for white borders around images
	public static final int ballOffset = 4;
	
	// level count
	// is automatically retrieved from existing levels in config.xml
	public static int levelCount = 0;
	
	// boolean to decide whether to save highscore.xml on a remote server or locally
	public static boolean useRemoteHighscoreList;
	
	// ****************************************************
	// ****************************************************
	// Flexible variables
	// Values are to be stored in config.xml
	
	// title
	public static String title;
	
	// image paths
	public static String paddleImg, ballImg, brickImgNormal,brickImgHard,
			brickXtraLifeImg, brickPaddleWidthImg, brickBallSpeedImg, brickXtraPointsImg,
			musicIconPlaying, musicIconPausing, backArrow;

	// initial speed of paddle
	public static int paddleSpeed;

	// direction angle and speed of ball
	public static int initialBallXDir, initialBallYDir, ballSpeed;
	
	// variables for brick stability
	public static int stabilityNormal, stabilityHard;
	
	// points for different kinds of bricks
	public static int pointsNormal, pointsBonus, maxExtraPoints; 
	
	// multiply factor for brick points
	// when ball speed is multiplied
	public static int bonusFactor;
	// penalty if ball hits the bottom
	public static int penalityPoints;
	
	// music and sounds
	public static ArrayList<String> backgroundMusic = new ArrayList<String>();
	
	/*
	 * level difficulty
	 * 
	 */
	
	// brick wall height by level, lives, possibility of brick variants in percent 
	public static int wallHeight, lifeCount, bonusPossible, hardPossible, normalPossible;
	// background image	
	public static String bgImagePath;
	
	// level specific variables
	public static void setLevelDifficulty(int level) {
		// level configurations are retrieved from config.xml, too
		BreakWallConfigXML.setLevelConfigurations(level);
		// added possibilities of bricks in percent may not exceed 100%
		if(hardPossible + bonusPossible <= 100) {
			normalPossible = 100 - (hardPossible + bonusPossible);
		} else {
			System.out.println("Added values of variables 'hardPossible' and 'bonusPossible' cannot be more than 100.");
			System.out.println("Please adjust config.xml accordingly.");
			System.exit(0);
		}
						
	}

}

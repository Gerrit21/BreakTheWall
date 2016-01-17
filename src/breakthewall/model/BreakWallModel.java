package breakthewall.model;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import breakthewall.BreakWallConfig;
import breakthewall.BreakWallConfigXML;
import breakthewall.remote.RemoteHighscoreClient;
import breakthewall.view.BreakWallView;

/**
 * Main model class to coordinate all game element instances.
 * Is an Observable which informs its Observers of model data changes.
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, October 2015.
 */
public class BreakWallModel extends Observable {
		
	private PlayerPaddle gamePaddle;
	private PlayerBall gameBall;
	private BreakWallMusic musicObj;
	private Thread musicThread;
	private Point ballTop, ballBottom, ballLeft, ballRight;
	private BrickWall gameWall;
	private ArrayList<GameElement> brickList;
	private Brick activeBrick;
	private BreakWallScore gameScore;
	private BreakWallXML gameXMLInstance;
	private Document dom;
	private ArrayList<GameElement> breakWallElements, currentBrickList, movableBonusObjects;
	private String struckBrick = "";
	private String tempBrick = "";
	private Timer timer;
	private int constantPaddleWidth, currentLevel, currentLives;
	private int scoreFactor = 1;
	private boolean moveBall, changeDir, musicIsPlaying, buttonClick;
	private boolean updateLevel = false;
	private String gameInfoText = "";
	
	/**
	 * Constructor initiates instantiation of game elements
	 * starts game loop
	 */
	public BreakWallModel() {		
		startNewGame();
	}
	
	// ***************************************************************//
	// Methods to organize the course of game
	// ***************************************************************//

	/**
	 * Private method to create relevant game elements:
	 * music, paddle, ball, brick wall e.g.
	 */
	private void initGameElements() {
		// start music thread
		musicObj = new BreakWallMusic();
		musicThread = new Thread(musicObj);
		if(!musicIsPlaying) {
			musicObj.playMusic(true);
			musicThread.start();
			musicIsPlaying = true;					
		}	
		breakWallElements = new ArrayList<GameElement>();
		movableBonusObjects = new ArrayList<GameElement>(); 
		gamePaddle = new PlayerPaddle();	
		gameBall = new PlayerBall();	
		moveBall = false;
		changeDir = true;
		constantPaddleWidth = gamePaddle.getWidth();
		breakWallElements.add(gameBall);
		breakWallElements.add(gamePaddle);
		// create brick wall, add it to a list of game elements
		for(int i = 0; i < gameWall.getBrickList().size(); i++) {
			breakWallElements.add(gameWall.getBrickList().get(i));
		}							
	}
	
	/**
	 * Public method to initiate a new game
	 */
	public void startNewGame() {
		currentLevel = 1;
		BreakWallConfig.setLevelDifficulty(currentLevel);
		currentLives = BreakWallConfig.lifeCount;
		musicIsPlaying = false;		
		gameScore = new BreakWallScore(0);
		gameXMLInstance = new BreakWallXML(this);
		gameWall = new BrickWall();
		initGameElements();
		setInfoText("Press Arrow-Keys to navigate the paddle left and right. Press Space-Key to start the game.");
		playGame();		
	}
	
	/**
	 * Public method to initiate every new level after the first level
	 */
	public void startNewLevel() {
		System.out.println("Start new Level");
		updateLevel = false;
		currentLevel++;
		scoreFactor = 1;
		// set new level difficulty
		BreakWallConfig.setLevelDifficulty(currentLevel);
		gameWall = new BrickWall();
		initGameElements();
		setChanged();
		notifyObservers("updateLevel");
		setInfoText("You've entered Level " + currentLevel + "!");
		playGame();
	}

	/**
	 * Public method to initiate an existing level from a loaded game
	 */
	public void startExistingLevel() {
		System.out.println("Load existing Game");
		BreakWallConfig.setLevelDifficulty(currentLevel);
		currentLives = BreakWallConfig.lifeCount;
		updateLevel = false;
		scoreFactor = 1;
		gameWall = new BrickWall();
		gameWall.setBrickList(brickList);
		initGameElements();
		setInfoText("You may start at Level " + currentLevel + "!");
		playGame();
	}
	
	/**
	 * Public method to load existing game of a user.
	 * Variables (current level, score) will be taken from the highscore list
	 */
	public void loadGame(String userName) {
		Document currentDocument = getHighscoreDocument();		
		Element root = currentDocument.getDocumentElement();
		NodeList list = root.getElementsByTagName("user");

		for (int i = 0; i < list.getLength(); ++i) {
			Element e = (Element) list.item(i);
			if(gameXMLInstance.getTagInfo("name", e).equals(userName)) {
				currentLevel = Integer.parseInt(gameXMLInstance.getTagInfo("level", e).toString());
				BreakWallConfig.lifeCount = Integer.parseInt(gameXMLInstance.getTagInfo("life", e).toString());
				gameScore.setCurrentScore(Integer.parseInt(gameXMLInstance.getTagInfo("highscore", e).toString()));
				loadBrickWall(e.getElementsByTagName("brick"));			
			}
		}
		BreakWallConfigXML.setLevelConfigurations(currentLevel);
		setChanged();
		notifyObservers("loadLevel");
	}
	
	/**
	 * Public method to load existing brick wall which is stored in the user highscore list.
	 * Brick infos are restored from the node list.
	 * 
	 * @param xmlBrickList NodeList of bricks
	 */
	public void loadBrickWall(NodeList xmlBrickList) {
		brickList = new ArrayList<GameElement>();
		for (int i = 0; i < xmlBrickList.getLength(); ++i) {
			Element e = (Element) xmlBrickList.item(i);
			// stored brick's type is "normal brick"
			// stability has to be set
			if(gameXMLInstance.getTagInfo("type", e).equals("BrickNormal")) {
				int stability = Integer.valueOf((String) gameXMLInstance.getTagInfo("stability", e));
				int xPos = Integer.valueOf((String) gameXMLInstance.getTagInfo("xPos", e));
				int yPos = Integer.valueOf((String) gameXMLInstance.getTagInfo("yPos", e));
				BrickNormal newBrick = new BrickNormal(stability);
				newBrick.setXCoord(xPos);
				newBrick.setYCoord(yPos);
				newBrick.setId(Integer.toString(newBrick.getXCoord()) + Integer.toString(newBrick.getYCoord()));
				brickList.add(newBrick);
				// stored brick's type is "bonus brick"
				// bonus type has to be set
			} else if(gameXMLInstance.getTagInfo("type", e).equals("BrickBonus")) {
				int xPos = Integer.valueOf((String) gameXMLInstance.getTagInfo("xPos", e));
				int yPos = Integer.valueOf((String) gameXMLInstance.getTagInfo("yPos", e));
				String bonusRef = (String) gameXMLInstance.getTagInfo("bonus", e);
				BrickBonus newBrick = new BrickBonus(bonusRef);
				newBrick.setXCoord(xPos);
				newBrick.setYCoord(yPos);
				newBrick.setId(Integer.toString(newBrick.getXCoord()) + Integer.toString(newBrick.getYCoord()));
				brickList.add(newBrick);
				
			}
		}
	}
	
	/**
	 * Public method to start game loop
	 */
	public void playGame() {	
		setInfoText("Play ...");
		setChanged();
		notifyObservers("focusGameElements");			
        timer = new Timer();
        timer.scheduleAtFixedRate(new GameLoopScheduler(), 500, 10);
        System.out.println("play");
	}
	
	/**
	 * Public method to stop game loop
	 */
	public void pauseGame() {
		setInfoText("Pause ...");
		stopGame();
		System.out.println("pause");
	}

	/**
	 * public method to save a game instance with reference to a user's name.
	 * Game is saved in a highscore-xml-document. 
	 */
	public void saveGame(String userName) {
		setInfoText("Save Highscore...");
		stopGame();
		RemoteHighscoreClient newHighscore = new RemoteHighscoreClient();
		newHighscore.addEntry(gameScore.getCurrentScore());		
		try {
			gameXMLInstance.createUserXML(userName, gameWall.getBrickList());
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void enterName() {
		// Eingabefeld fuer Namen
		
		setInfoText("Enter your Name...");
		setChanged();
		notifyObservers("showEnterName");
		
	}
	
	/**
	 * Public method to end game
	 */
	public void exitGame() {
		setInfoText("Exit the Game...");
		System.exit(0);		
	}
	
	/**
	 * Public method to invoke visibility of the game menu
	 */
	public void menuGame() {
		setInfoText("Go to menu...");
		setChanged();
		notifyObservers("showMenu");
		stopGame();
	}

	/**
	 * Public method to invoke closing of the game menu
	 */
	public void backGame() {
		setInfoText("Go back to Game...");
		setChanged();
		notifyObservers("quitMenu");
		playGame();
		
	}
	
	/**
	 * Public method to invoke closing of the highscore list
	 */
	public void backMenu() {
		setInfoText("Go back to Menu...");
		setChanged();
		notifyObservers("quitHighscore");
		
		
	}
	
	public void backMenuAfterSave() {
		setInfoText("Go back to Menu...");
		setChanged();
		notifyObservers("quitEnterName");
		
	}
	
	public void setPlayPause(boolean buttonClick) {
		this.buttonClick = buttonClick;
			
	}
	
	public boolean getPlayPause() {
		return this.buttonClick;
	}
	
	public void setMusicPlaying(boolean boolval) {
		setChanged();
		if(boolval == true) {
			notifyObservers("showPlayButton");
		} else {
			notifyObservers("showMuteButton");
		}			
		musicObj.playMusic(boolval);
	}
	
	
	public void scoreGame() {
		//loadGame("Helmut");
		setInfoText("Go to Highscore...");
		setChanged();
		notifyObservers("showHighscore");
		stopGame();
	}
	
	public void restartNewGame() {
		
		System.out.println("Start New Game");
		setInfoText("New Game...");
		setChanged();
		notifyObservers("startNewGame");
		startNewGame();
		
	}
	
	public void selectUser() {
		setInfoText("Go to User List...");
		setChanged();
		notifyObservers("showUserSelect");
		stopGame();
	}
	
	public void loadUser(String name) {
		setChanged();
		notifyObservers("loadUserGame");		
		loadGame(name);
	}
	
	public Document getHighscoreDocument() {
		dom = null;		
		File xml = new File(System.getProperty("user.dir") + File.separator + BreakWallConfig.highscorePath + BreakWallConfig.highscoreXML);
	    if (xml.exists() && xml.length() != 0) {
	    	dom = gameXMLInstance.parseFile(xml);
	    }
	    return dom;
	}
	

	// ***************************************************************//
	// Methods to manipulate game elements
	// ***************************************************************//
	
	/**
	 * Method changes x-position of game paddle to move it left
	 */
	public void movePaddleLeft() {
		if(gamePaddle.getXCoord() > 0) {
			gamePaddle.movePaddle("left");
			if(moveBall == false) {
				gameBall.restBall("left", gamePaddle.getSpeed());
			}
		}
	}

	/**
	 * Method changes x-position of game paddle to move it right
	 */
	public void movePaddleRight() {
		if(gamePaddle.getXCoord() < (BreakWallConfig.offsetWidth - gamePaddle.getWidth())) {
			gamePaddle.movePaddle("right");
			if(moveBall == false) {
				gameBall.restBall("right", gamePaddle.getSpeed());
			}
		}
	}

	/**
	 * Method sets ball direction for user's first shot.
	 * Sets whether the ball should rest on the paddle or move.
	 * 
	 * @param moveBall set if the ball should move or not 
	 */
	public void setBallAction(boolean moveBall) {
		// 
		int randomDir = randomFromRange(1, 2);
		System.out.println(randomDir);
		if(randomDir == 1) {
			gameBall.setDirX(gameBall.getDirX() * 1);
		} else {
			gameBall.setDirX(gameBall.getDirX() * (-1));
		}		
		this.moveBall = moveBall;
	}
	
	/**
	 * Method returns whether the ball should rest on the paddle or move
	 * 
	 * @return moveBall
	 */
	public boolean getBallAction() {
		return this.moveBall;
	}
	
	/**
	 * Method for cheating: randomly removes bricks to get through levels faster.
	 * Removed bricks will not add to users current score.
	 * Press 'e' key to remove brick.
	 */
	public void cheat() {
		if(gameWall.getBrickList().size() > 0) {
			int randomBrickIndex = randomFromRange(0, gameWall.getBrickList().size() - 1);
			gameWall.getBrickList().get(randomBrickIndex).setDestroyedState(true);
			gameWall.removeFromBrickList(gameWall.getBrickList().get(randomBrickIndex));
		}
	}

	/**
	 * Method returns an ArrayList of game elements:
	 * bricks, ball, paddle
	 * 
	 * @return breakWallElements ArrayList of game elements 
	 */
	public ArrayList<GameElement> getElementList() {
		return this.breakWallElements;
	}

	/**
	 * Method returns an ArrayList of movable bonus elements:
	 * bonus elements
	 * 
	 * @return movableBonusObjects ArrayList of game elements  
	 */
	public ArrayList<GameElement> getMovableElementsList() {
		return this.movableBonusObjects;
	}
	
	/**
	 * Method removes an element from its element list
	 * when it has been destroyed
	 */
	public void removeDestroyedElements() {
		for(int i = 0; i < breakWallElements.size(); i++) {
			if(breakWallElements.get(i).getDestroyedState() == true) {
				breakWallElements.remove(i);
			}
		}		
		for(int j = 0; j < movableBonusObjects.size(); j++) {			
			if(movableBonusObjects.get(j).getDestroyedState() == true) {
				movableBonusObjects.remove(j);
			}
			
		}
	}
	
	/**
	 * Method sets info text which will be displayed on the GUI
	 * @param text text to be displayed
	 */
	private void setInfoText(String text) {
		this.gameInfoText = text;
	}
	
	/**
	 * Method to get text to be displayed on the GUI
	 * 
	 * @return gameInfoText text to be displayed 
	 */
	public String getInfoText() {
		return this.gameInfoText;
	}

	/**
	 * Public method to get text to be displayed on the GUI
	 * 
	 * @return gameInfoText text to be displayed 
	 */
	public int getScore() {
		return  gameScore.getCurrentScore();
	}
	
	public int getLevel() {
		return  this.currentLevel;
	}
	
	public int getLives() {
		return  this.currentLives;
	}
	
	public Brick getActiveBrick() {
		return activeBrick;
	}
	
	// ***************************************************************//
	// Methods to detect ball collisions with other game elements
	// ***************************************************************//
	
	/**
	 * Private method detects if ball touches a wall, changes ball direction
	 * or subtracts life
	 */
	private void collisionDetection() {    	
		// points are defined as middle-top, middle-right, middle-bottom, middle-left of ball
		// needed for collision detection by several methods
		ballTop = new Point((gameBall.getXCoord() + (gameBall.getWidth() / 2)), gameBall.getYCoord() + BreakWallConfig.ballOffset);
		ballBottom = new Point((gameBall.getXCoord() + (gameBall.getWidth() / 2)), (gameBall.getYCoord() - BreakWallConfig.ballOffset + gameBall.getHeight()));
		ballLeft = new Point(gameBall.getXCoord() +  BreakWallConfig.ballOffset, (gameBall.getYCoord() + (gameBall.getHeight() / 2)));
		ballRight = new Point((gameBall.getXCoord() - BreakWallConfig.ballOffset + gameBall.getWidth()), (gameBall.getYCoord() + (gameBall.getHeight() / 2)));		
		// ball is located within the left and right boundary of the game field
		if((ballLeft.getX() > 0) && (ballRight.getX() < BreakWallConfig.offsetWidth)) {
	    // ball touches or passes the left or right boundary of the game field
		// the x-direction of the ball is being reverted	
		} else if((ballLeft.getX() <= 0) || (ballRight.getX() >= BreakWallConfig.offsetWidth)) {
			gameBall.setDirX(-1 * gameBall.getDirX());
			changeDir = true;
		}
		// ball is located within the top and bottom boundary of the game field
		if((ballTop.getY() > BreakWallConfig.barHeight) && (ballBottom.getY() < BreakWallConfig.offsetHeight)) {
		// ball touches or passes the top boundary of the game field	
	    // the y-direction of the ball is being reverted
		} else if((ballTop.getY() <= BreakWallConfig.barHeight)) {
			gameBall.setDirY(-1 * gameBall.getDirY());
			changeDir = true;
		// ball touches or passes the bottom boundary of the game field	
		// player loses a life or game is over	
		} else if(ballBottom.getY() > BreakWallConfig.offsetHeight) {
			setInfoText("You've lost a life!");
			gameScore.subtractPoints(BreakWallConfig.penalityPoints);
			scoreFactor = 1;
			if(currentLives > 1) {
				currentLives -= 1;			
				gameBall.setXCoord(gamePaddle.getXCoord() + (gamePaddle.getWidth() / 2));
				gameBall.setYCoord(gamePaddle.getYCoord() - gameBall.getHeight());
				gameBall.setDirY(BreakWallConfig.initialBallYDir);			
				gameBall.setDirX(BreakWallConfig.initialBallXDir);
				gamePaddle.setWidth(BreakWallView.getImageByURL(this, gamePaddle.getId()).getWidth(null));
			} else {
				gameBall.setDestroyedState(true);
				setInfoText("Game Over!");
				stopGame();
			}
		}
		detectPaddleCollision();
		detectBrickCollision();
	}
	
	/**
	 * Private method detects if ball touches the paddle, changes ball direction
	 */
	private void detectPaddleCollision() {
		Rectangle paddleRect = new Rectangle(gamePaddle.getXCoord(), gamePaddle.getYCoord(), gamePaddle.getWidth(), gamePaddle.getHeight() - BreakWallConfig.paddleOffsetTop);
		if(movableBonusObjects.size() > 0) {
			for(int i = 0; i < movableBonusObjects.size(); i++) {
				if(paddleRect.contains(new Point (movableBonusObjects.get(i).getXCoord(), movableBonusObjects.get(i).getYCoord()))) {					
					gameScore.addPoints(((BonusXtraPoints) movableBonusObjects.get(i)).getBonusPoints());
					((BonusXtraPoints) movableBonusObjects.get(i)).setDestroyedState(true);
				}				
			}			
		}
		// ball touches top boundary of paddle
		if((paddleRect.contains(ballBottom)) && (changeDir == true)) {					
			gameBall.setDirY(-1 * gameBall.getDirY());
			changeDir = false;
			return;
		// ball touches left or right boundary of paddle
		// lässt den Ball wieder nach oben fliegen	
		} else if((changeDir == true) && (paddleRect.contains(ballLeft)) || (paddleRect.contains(ballRight))) {				    
			gameBall.setDirY(-1 * gameBall.getDirY());
			gameBall.setDirX(-1 * gameBall.getDirX());
			changeDir = false;
			return;	
		}		
	}
	
	/**
	 * Private method detects if ball touches a brick,
	 * sets brick destroyed and adds points to user scores,
	 * changes direction of ball
	 */
	private void detectBrickCollision() {
		currentBrickList = gameWall.getBrickList();
		GameElement currentBrick;
		Rectangle brickRect;
		for(int j = 0; j < currentBrickList.size(); j++) {
			currentBrick = currentBrickList.get(j);
			brickRect = new Rectangle(currentBrick.getXCoord(), currentBrick.getYCoord(), currentBrick.getWidth(), currentBrick.getHeight());

			// ball touches top or bottom boundary of brick
			if((brickRect.contains(ballTop)) || (brickRect.contains(ballBottom))) {
				tempBrick = currentBrick.getId();
				if(!(struckBrick.equals(tempBrick))) {
					Brick tempCurrentBrick = (Brick) currentBrick;
					tempCurrentBrick.setStability(tempCurrentBrick.getStability() - 1);
					gameScore.addPoints(tempCurrentBrick.getPoints() * scoreFactor);
					if(tempCurrentBrick.getStability() <= 0) {						
						currentBrick.setDestroyedState(true);
						gameWall.removeFromBrickList(currentBrick);
						if(tempCurrentBrick.hasBonusObject() == true) {
							activateBonus(tempCurrentBrick);
						}
					}
				}
				gameBall.setDirY(-1 * gameBall.getDirY());
				changeDir = true;
				struckBrick = tempBrick;
				return;
			// ball touches left or right boundary of brick		
			} else if((brickRect.contains(ballLeft)) || (brickRect.contains(ballRight))) {
				tempBrick = currentBrick.getId();
				if(!(struckBrick.equals(tempBrick))) {
					Brick tempCurrentBrick = (Brick) currentBrick;					
					tempCurrentBrick.setStability(tempCurrentBrick.getStability() - 1);
					gameScore.addPoints(tempCurrentBrick.getPoints() * scoreFactor);
					if(tempCurrentBrick.getStability() <= 0) {						
						currentBrick.setDestroyedState(true);
						gameWall.removeFromBrickList(currentBrick);
						if(tempCurrentBrick.hasBonusObject() == true) {
							activateBonus(tempCurrentBrick);
						}
					}
				}
				gameBall.setDirX(-1 * gameBall.getDirX());
				changeDir = true;
				struckBrick = tempBrick;
				return;
			}
		}		
	}
	
	/**
	 * Private method detects the bonus object for a hit brick,
	 * invokes methods to activate given bonus
	 * 
	 * @param activeBrick brick associated with a bonus
	 */
	private void activateBonus(Brick activeBrick) {
		this.activeBrick = activeBrick;
		if(activeBrick.getBonusObject().getBonusType().equals("BonusBallSpeed")) {
			BonusBallSpeed tempBonus = (BonusBallSpeed) activeBrick.getBonusObject();
			int newAccel = tempBonus.setBallSpeed(gameBall.getDirY());
			scoreFactor = scoreFactor * BreakWallConfig.bonusFactor;
			gameBall.setDirY(newAccel);
			setInfoText("Bonus: Change speed of ball!");
		} else if(activeBrick.getBonusObject().getBonusType().equals("BonusPaddleWidth")) {
			BonusPaddleWidth tempBonus = (BonusPaddleWidth) activeBrick.getBonusObject();
			int newWidth = tempBonus.changePaddleSize(constantPaddleWidth);
			gamePaddle.setWidth(newWidth);
			setInfoText("Bonus: Change paddle width!");
		} else if(activeBrick.getBonusObject().getBonusType().equals("BonusXtraLife")) {
			setInfoText("Bonus: Add Xtra Life!");
			currentLives++;
		} else if(activeBrick.getBonusObject().getBonusType().equals("BonusXtraPoints")) {			
			GameElement movableBonus = (GameElement) activeBrick.getBonusObject();
			movableBonus.setXCoord(((BrickBonus) activeBrick).getXCoord());
			movableBonus.setYCoord(((BrickBonus) activeBrick).getYCoord());
			movableBonus.setWidth(30);
			movableBonus.setHeight(10);
			movableBonus.setId("Bonus" + movableBonus.getXCoord() + movableBonus.getYCoord());
			movableBonusObjects.add(movableBonus);			
			setInfoText("Bonus: Add Xtra Points!");
			System.out.println("Bonus: Add Xtra Points!");
		}
	}
	
	
	/**
	 * Static mathod to create a random number within a min. and max. value.
	 * 
	 * @param min mimimum value of random number
	 * @param max maximum value of random number
	 * @return randomNum a random number
	 */
	public static int randomFromRange(int min, int max) {
	    Random random = new Random();
	    int randomNum = random.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	/**
	 * Private class to schedule game loop
	 * Updates positions of of moving game elements 
	 * 
	 * @author Mareike Röncke, Gerrit Schulte
	 *
	 */
    private class GameLoopScheduler extends TimerTask {
    	
    	// moves ball and calls collision check method,
    	// informs view to update game element positions on game field,
    	// calls method to remove destroyed elements
        public void run() {
			if(gameWall.getBrickList().size() == 0) {
				updateLevel = true;
			}
        	if(movableBonusObjects.size() > 0) {
        		for(int i = 0; i < movableBonusObjects.size(); i++) {
        			if(movableBonusObjects.get(i).getClass().getSimpleName().equals("BonusXtraPoints")) {
        				 ((BonusXtraPoints) movableBonusObjects.get(i)).moveBonus(1);
        				if(((BonusXtraPoints) movableBonusObjects.get(i)).getYCoord() > BreakWallConfig.gameFieldHeight) {
        				 	((BonusXtraPoints) movableBonusObjects.get(i)).setDestroyedState(true);
        				}
        			}
        		}
        	}
        	if(moveBall == true) {
            	gameBall.moveBall();
            	collisionDetection();      		
        	}
    		setChanged();
    		if(updateLevel == false) {
        		notifyObservers("updateGameElements");
        		removeDestroyedElements();    			
    		} else if(updateLevel == true) {
    			if(currentLevel < BreakWallConfig.levelCount) {
        			notifyObservers("initLevel");
    			} else {
    				setInfoText("You have archieved " + gameScore.getCurrentScore() + " Points! Congrats!");
    			}    			
    			stopGame();
    		}
        	
        }
    }

    /**
     * Public method stops game loop
     */
    public void stopGame() {
    	setChanged();
    	notifyObservers("stopGame");
        timer.cancel();
    }	
}

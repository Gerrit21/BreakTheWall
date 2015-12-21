package breakthewall.model;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import breakthewall.BreakWallConfig;
import breakthewall.remote.RemoteHighscoreClient;
import breakthewall.view.BreakWallView;
import breakthewall.view.MenuView;
import breakthewall.view.NavigationBarView;

/**
 * Klasse zur Koordinierung der verschiedenen Break-The-Wall-Spielelemente.
 * 
 * @author Mareike R�ncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BreakWallModel extends Observable {
		
	private PlayerPaddle gamePaddle;
	private PlayerBall gameBall;
	//private BreakWallMusic gameMusic;
	private Point ballTop, ballBottom, ballLeft, ballRight;
	private BrickWall gameWall;
	private BreakWallScore gameScore;
	private ArrayList<GameElement> breakWallElements;
	private ArrayList<GameElement> currentBrickList;
	private String struckBrick = "";
	private String tempBrick = "";
	private Timer timer;
	private int constantPaddleWidth;
	private int currentLevel;
	private boolean moveBall, changeDir;
	private boolean updateLevel = false;
	private String gameInfoText;
	private boolean buttonClick;
	
	

	
	
	/**
	 * Konstruktoraufruf initiiert die Erzeugung der Spielelemente
	 * und startet den Game-Loop
	 */
	public BreakWallModel(int initialLevel) {		
		// das Level festlegen, mit dem das Spiel starten soll
		currentLevel = initialLevel;
		BreakWallConfig.setLevelDifficulty(currentLevel);
		gameScore = new BreakWallScore(0);
		initGameElements();
		setInfoText("Press Arrow-Keys to navigate the paddle left and right. Press Space-Key to start the game.");
		playGame();
	}

	/**
	 * Klasse erzeugt Objekte aller initial spielrelevanten Elemente,
	 * also Spielfeld, Paddle, Ball und Brick-Wand
	 * und zeichnet diese auf die Spielfl�che.
	 */
	private void initGameElements() {
	//	gameMusic = new BreakWallMusic();
	//	gameMusic.start();	
		breakWallElements = new ArrayList<GameElement>();
		gamePaddle = new PlayerPaddle();	
		gameBall = new PlayerBall();
		moveBall = false;
		changeDir = true;
		constantPaddleWidth = gamePaddle.getWidth();
		breakWallElements.add(gameBall);
		breakWallElements.add(gamePaddle);
		
		// Brick-Wand erstellen und Bricks zur Liste der Spielelemente hinzuf�gen
		gameWall = new BrickWall();
		for(int i = 0; i < gameWall.getBrickList().size(); i++) {
			breakWallElements.add(gameWall.getBrickList().get(i));
		}				
	}
	
	public void startNewLevel() {
		updateLevel = false;
		currentLevel++;
		BreakWallConfig.setLevelDifficulty(currentLevel);
		setChanged();
		notifyObservers("updateLevel");
		initGameElements();
		setInfoText("You've entered Level " + currentLevel + "!");
		playGame();
	}
	
	/*
	 * �ffentliche Methoden, um Model-Daten zu �ndern
	 */
	
	public void movePaddleLeft() {
		if(gamePaddle.getXCoord() > 0) {
			gamePaddle.movePaddle("left");
			if(moveBall == false) {
				gameBall.restBall("left", gamePaddle.getSpeed());
			}
		}
	}
	
	public void movePaddleRight() {
		if(gamePaddle.getXCoord() < (BreakWallConfig.offsetWidth - gamePaddle.getWidth())) {
			gamePaddle.movePaddle("right");
			if(moveBall == false) {
				gameBall.restBall("right", gamePaddle.getSpeed());
			}
		}
	}
	
	public void playGame() {
		
		setInfoText("Play ...");
		setChanged();
		notifyObservers("focusGameElements");			
        timer = new Timer();
        timer.scheduleAtFixedRate(new GameLoopScheduler(), 500, 10);		
	}
	
	public void pauseGame() {
		setInfoText("Pause ...");
		stopGame();
	}
	
	public void saveGame() {
		setInfoText("Want to save your highscore? Implement it.");
		stopGame();
		// to do: ggf. ins Hauptmen� integrieren
		RemoteHighscoreClient newHighscore = new RemoteHighscoreClient();
		newHighscore.addEntry(gameScore.getCurrentScore());		
	}
	
	
	public void exitGame() {
		setInfoText("Exit the Game...");
		System.exit(0);		
	}
	
	public void menuGame() {
		setInfoText("Go to menu...");
		setChanged();
		notifyObservers("showMenu");
		stopGame();
	}
	
	public void backGame() {
		setInfoText("Go back to Game...");
		setChanged();
		notifyObservers("quitMenu");
		playGame();
		System.out.println("Menu remove");
		
	}
	
	public void setPlayPause(boolean buttonClick) {
		this.buttonClick = buttonClick;
			
	}
	
	public boolean getPlayPause() {
		return this.buttonClick;
	}
	
	
	public void setBallAction(boolean moveBall) {
		// Zuf�llige Ballrichtung beim ersten Wurf festlegen
		int randomDir = randomFromRange(1, 2);
		System.out.println(randomDir);
		if(randomDir == 1) {
			gameBall.setDirX(gameBall.getDirX() * 1);
		} else {
			gameBall.setDirX(gameBall.getDirX() * (-1));
		}		
		this.moveBall = moveBall;
	}
	
	public boolean getBallAction() {
		return this.moveBall;
	}
	
	public void remove(int index) {
		breakWallElements.remove(index);
	}
	
	// cheat mode: randomly remove bricks to get through levels faster
	// press 'e' key
	public void cheat() {
		if(gameWall.getBrickList().size() > 0) {
			int randomBrickIndex = randomFromRange(0, gameWall.getBrickList().size() - 1);
			gameWall.getBrickList().get(randomBrickIndex).setDestroyedState(true);
			gameWall.removeFromBrickList(gameWall.getBrickList().get(randomBrickIndex));
		}
	}
	
	public ArrayList<GameElement> getElementList() {
		return this.breakWallElements;
	}
	
	public void setInfoText(String text) {
		this.gameInfoText = text;
	}
	
	public String getInfoText() {
		return this.gameInfoText;
	}
	
	private void collisionDetection() {    	
		// die Punkte sind definiert als Mitte Oben, Mitte Rechts, Mitte Unten, Mitte Links des Balls
		// werden f�r die Kollisionsdetektion an verschiedenen Stellen ben�tigt
		ballTop = new Point((gameBall.getXCoord() + (gameBall.getWidth() / 2)), gameBall.getYCoord() + BreakWallConfig.ballOffset);
		ballBottom = new Point((gameBall.getXCoord() + (gameBall.getWidth() / 2)), (gameBall.getYCoord() - BreakWallConfig.ballOffset + gameBall.getHeight()));
		ballLeft = new Point(gameBall.getXCoord() +  BreakWallConfig.ballOffset, (gameBall.getYCoord() + (gameBall.getHeight() / 2)));
		ballRight = new Point((gameBall.getXCoord() - BreakWallConfig.ballOffset + gameBall.getWidth()), (gameBall.getYCoord() + (gameBall.getHeight() / 2)));
				
		// der Ball befindet sich innerhalb der linken und rechten Begrenzung des Spielfeldes
		if((ballLeft.getX() > 0) && (ballRight.getX() < BreakWallConfig.offsetWidth)) {
	    // der Ball ber�hrt oder �berschreitet die linke und rechte Begrenzung des Spielfeldes
		// die X-Richtung wird umgekehrt	
		} else if((ballLeft.getX() <= 0) || (ballRight.getX() >= BreakWallConfig.offsetWidth)) {
			gameBall.setDirX(-1 * gameBall.getDirX());
			changeDir = true;
		}		
		// der Ball befindet sich innerhalb der oberen und unteren Begrenzung des Spielfeldes
		if((ballTop.getY() > BreakWallConfig.barHeight) && (ballBottom.getY() < BreakWallConfig.offsetHeight)) { 
		// der Ball ber?hrt oder ?berschreitet die obere Begrenzung des Spielfeldes
	    // die Y-Richtung wird umgekehrt
		} else if((ballTop.getY() <= BreakWallConfig.barHeight)) {
			gameBall.setDirY(-1 * gameBall.getDirY());
			changeDir = true;
		// der Ball ber�hrt oder unterschreitet die untere Begrenzung des Spielfeldes
		// Spieler verliert ein Leben oder verliert das Spiel	
		} else if(ballBottom.getY() > BreakWallConfig.offsetHeight) {
			setInfoText("You've lost a life!");
			gameScore.subtractPoints(20);
			if(BreakWallConfig.lifeCount > 1) {
				BreakWallConfig.lifeCount -= 1;			
				gameBall.setXCoord(gamePaddle.getXCoord() + (gamePaddle.getWidth() / 2));
				gameBall.setYCoord(gamePaddle.getYCoord() - gameBall.getHeight());
				gameBall.setDirY(BreakWallConfig.initialBallYDir);			
				gameBall.setDirX(BreakWallConfig.initialBallXDir);
			} else {
				gameBall.setDestroyedState(true);
				setInfoText("Game Over!");
				stopGame();
			}
		}
		detectPaddleCollision();
		detectBrickCollision();
	}
	
	private void detectPaddleCollision() {
		Rectangle paddleRect = new Rectangle(gamePaddle.getXCoord(), gamePaddle.getYCoord(), gamePaddle.getWidth(), gamePaddle.getHeight() - BreakWallConfig.paddleOffsetTop);
		// Ball st��t Paddle von oben an
		if((paddleRect.contains(ballBottom)) && (changeDir == true)) {					
			gameBall.setDirY(-1 * gameBall.getDirY());
			changeDir = false;
			return;
		// Ball st��t Paddle von links oder rechts
		// l�sst den Ball wieder nach oben fliegen	
		} else if((changeDir == true) && (paddleRect.contains(ballLeft)) || (paddleRect.contains(ballRight))) {				    
			gameBall.setDirY(-1 * gameBall.getDirY());
			gameBall.setDirX(-1 * gameBall.getDirX());
			changeDir = false;
			return;	
		}		
	}
	
	private void detectBrickCollision() {
		currentBrickList = gameWall.getBrickList();
		GameElement currentBrick;
		Rectangle brickRect;
		for(int j = 0; j < currentBrickList.size(); j++) {
			currentBrick = currentBrickList.get(j);
			brickRect = new Rectangle(currentBrick.getXCoord(), currentBrick.getYCoord(), currentBrick.getWidth(), currentBrick.getHeight());

			// Ball st��t Brick von unten oder oben an
			if((brickRect.contains(ballTop)) || (brickRect.contains(ballBottom))) {
				tempBrick = currentBrick.getId();
				if(!(struckBrick.equals(tempBrick))) {
					// Cast ist notwendig, um stability setter/ getter zu benutzen
					Brick tempCurrentBrick = (Brick) currentBrick;
					tempCurrentBrick.setStability(tempCurrentBrick.getStability() - 1);
					if(tempCurrentBrick.getStability() == 0) {
						gameScore.addPoints(10);
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
			// Ball st��t Brick von links oder rechts an		
			} else if((brickRect.contains(ballLeft)) || (brickRect.contains(ballRight))) {
				tempBrick = currentBrick.getId();
				if(!(struckBrick.equals(tempBrick))) {
					// Cast ist notwendig, um stability setter/ getter zu benutzen
					Brick tempCurrentBrick = (Brick) currentBrick;					
					tempCurrentBrick.setStability(tempCurrentBrick.getStability() - 1);
					if(tempCurrentBrick.getStability() == 0) {
						gameScore.addPoints(10);
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
	
	private void activateBonus(Brick activeBrick) {
		if(activeBrick.getBonusObject().getBonusType().equals("BonusBallSpeed")) {
			BonusBallSpeed tempBonus = (BonusBallSpeed) activeBrick.getBonusObject();
			int newAccel = tempBonus.setBallSpeed(gameBall.getDirY());
			gameBall.setDirY(newAccel);
			setInfoText("Bonus: Change speed of ball!");
		} else if(activeBrick.getBonusObject().getBonusType().equals("BonusPaddleWidth")) {
			BonusPaddleWidth tempBonus = (BonusPaddleWidth) activeBrick.getBonusObject();
			int newWidth = tempBonus.changePaddleSize(constantPaddleWidth/2);
			gamePaddle.setWidth(newWidth);
			setInfoText("Bonus: Change paddle width!");
		} else if(activeBrick.getBonusObject().getBonusType().equals("BonusXtraLife")) {
			setInfoText("Bonus: Add Xtra Life!");
			BreakWallConfig.lifeCount++;
		}
	}
	
	private void removeDestroyedElements() {
		for(int i = 0; i < breakWallElements.size(); i++) {
			if(breakWallElements.get(i).getDestroyedState() == true) {
				remove(i);
			}
		}
	}
	
	
	/**
	 * Statische Klasse zur Erzeugung einer Zufallszahl
	 * innerhalb einer Ober- und Untergrenze.
	 * Ober- und Untergrenze dienen haupts�chlich dazu,
	 * die 0 als m�gliches Ergebnis auszuschlie�en.
	 * 
	 * @param min Untergrenze der m�glichen Zufallszahl
	 * @param max Obergrenze der m�glichen Zufallszahl
	 * @return randomNum die Zufallszahl innerhalb der Grenzen
	 */
	public static int randomFromRange(int min, int max) {
	    Random random = new Random();
	    int randomNum = random.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	/**
	 * Private Klasse zum Scheduling des Game-Loops
	 * Aktualisiert die Position von Spielelementen in einem festgelegten Zeitrahmen 
	 * 
	 * @author Mareike R�ncke, Gerrit Schulte
	 *
	 */
    private class GameLoopScheduler extends TimerTask {
    	
    	// bewegt den Ball und pr�ft auf Kollision
    	// informiert die Beobachter von �nderungen der Daten
    	// entfernt ggf. Elemente aus der Element-Liste des Spiels
        public void run() {
			if(gameWall.getBrickList().size() == 0) {
				updateLevel = true;
			}
        	// System.out.println(gameScore.getCurrentScore());
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

    public void stopGame() {
        timer.cancel();
    }	
}

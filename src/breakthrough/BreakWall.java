package breakthrough;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Klasse zur Koordinierung der verschiedenen Break-The-Wall-Spielelemente.
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BreakWall {
		
	private BreakWallView gameField;
	private PlayerPaddle gamePaddle;
	private PlayerBall gameBall;
	private Point ballTop, ballBottom, ballLeft, ballRight;
	private BrickFactory gameWall;
	private ArrayList<Brick> currentBrickList;
	private ArrayList<Object> gameElements;
	private String struckBrick = "";
	private String tempBrick = "";
	private Timer timer;
	private int constantPaddleWidth;
	private NavigationBar panelbar;
	
	/**
	 * Konstruktoraufruf initiiert die Erzeugung der Spielelemente
	 * und startet den Game-Loop
	 */
	public BreakWall(int initialLevel) {
		// das Level festlegen, mit dem das Spiel starten soll
		BreakWallData.setLevelDifficulty(initialLevel);
		initGameElements();
        timer = new Timer();
        timer.scheduleAtFixedRate(new GameLoopScheduler(), 500, 10);
        
        MVCBreakWallModel model = new MVCBreakWallModel();
		panelbar = new NavigationBar(model);
		gameField.addPanelToGameField(panelbar, 0, 0, BreakWallData.barWidth, BreakWallData.barHeight);
		model.zeitmessung();
	}

	/**
	 * Klasse erzeugt Objekte aller initial spielrelevanten Elemente,
	 * also Spielfeld, Paddle, Ball und Brick-Wand
	 * und zeichnet diese auf die Spielfläche.
	 */
	private void initGameElements() {
		gameElements = new ArrayList<Object>();
		// Spielelemente erzeugen
		gameField = new BreakWallView();
		gameField.addKeyListener(new KeyboardEvents());
		gameElements.add(gameField);
		gamePaddle = new PlayerPaddle();
		gameElements.add(gamePaddle);
		gameBall = new PlayerBall();
		constantPaddleWidth = gamePaddle.getWidth();
		gameElements.add(gameBall);
		// Elemente zum Spielfeld hinzufügen
		gameField.addElementToGameField(gamePaddle.getImage(), "", gamePaddle.getXCoord(), gamePaddle.getYCoord(), gamePaddle.getWidth(), gamePaddle.getHeight());		
		gameField.addElementToGameField(gameBall.getImage(), "", gameBall.getXCoord(), gameBall.getYCoord(), gameBall.getWidth(), gameBall.getHeight());
		// Brick-Wand zum Spielfeld hinzufügen
		gameWall = new BrickFactory(gameField);
		
		
		
		
	}
	
	private void collisionDetection() {
    	
		// die Punkte sind definiert als Mitte Oben, Mitte Rechts, Mitte Unten, Mitte Links des Balls
		// werden für die Kollisionsdetektion an verschiedenen Stellen benötigt
		ballTop = new Point((gameBall.getXCoord() + (gameBall.getWidth() / 2)), gameBall.getYCoord() + BreakWallData.ballOffset);
		ballBottom = new Point((gameBall.getXCoord() + (gameBall.getWidth() / 2)), (gameBall.getYCoord() - BreakWallData.ballOffset + gameBall.getHeight()));
		ballLeft = new Point(gameBall.getXCoord() +  BreakWallData.ballOffset, (gameBall.getYCoord() + (gameBall.getHeight() / 2)));
		ballRight = new Point((gameBall.getXCoord() - BreakWallData.ballOffset + gameBall.getWidth()), (gameBall.getYCoord() + (gameBall.getHeight() / 2)));
				
		// der Ball befindet sich innerhalb der linken und rechten Begrenzung des Spielfeldes
		if((ballLeft.getX() > 0) && (ballRight.getX() < BreakWallData.offsetWidth)) {
	    // der Ball berührt oder überschreitet die linke und rechte Begrenzung des Spielfeldes
		// die X-Richtung wird umgekehrt	
		} else if((ballLeft.getX() <= 0) || (ballRight.getX() >= BreakWallData.offsetWidth)) {
			gameBall.setDirX(-1 * gameBall.getDirX());
		}		
		// der Ball befindet sich innerhalb der oberen und unteren Begrenzung des Spielfeldes
		if((ballTop.getY() > BreakWallData.barHeight) && (ballBottom.getY() < BreakWallData.offsetHeight)) { 
		// der Ball ber?hrt oder ?berschreitet die obere Begrenzung des Spielfeldes
	    // die Y-Richtung wird umgekehrt
		} else if((ballTop.getY() <= BreakWallData.barHeight)) {
			gameBall.setDirY(-1 * gameBall.getDirY());
		// der Ball berührt oder unterschreitet die untere Begrenzung des Spielfeldes
		// Spieler verliert ein Leben oder verliert das Spiel	
		} else if(ballBottom.getY() > BreakWallData.offsetHeight) {
			System.out.println("You failed!");
			if(BreakWallData.lifeCount > 1) {
				BreakWallData.lifeCount -= 1;			
				gameBall.setXCoord(gamePaddle.getXCoord() + (gamePaddle.getWidth() / 2));
				gameBall.setYCoord(gamePaddle.getYCoord() - gameBall.getHeight());
				gameBall.setDirY(BreakWallData.initialBallYDir);			
				gameBall.setDirX(BreakWallData.initialBallXDir);
			} else {
				gameField.removeElementFromGameField(gameBall.getImage());
				System.out.println("Game Over!");
				stopGame();
			}
		}
		detectPaddleCollision();
		detectBrickCollision();
	}
	
	private void detectPaddleCollision() {
		Rectangle paddleRect = new Rectangle(gamePaddle.getXCoord(), gamePaddle.getYCoord(), gamePaddle.getWidth(), gamePaddle.getHeight() - BreakWallData.paddleOffsetTop);
		// Ball stößt Paddle von oben an
		if(paddleRect.contains(ballBottom)) {					
			gameBall.setDirY(-1 * gameBall.getDirY());
			return;
		// Ball stößt Paddle von links oder rechts
		// lässt den Ball wieder nach oben fliegen	
		} else if((paddleRect.contains(ballLeft)) || (paddleRect.contains(ballRight))) {				    
			gameBall.setDirY(-1 * gameBall.getDirY());
			gameBall.setDirX(-1 * gameBall.getDirX());
			return;	
		}		
	}
	
	private void detectBrickCollision() {
		currentBrickList = gameWall.getBrickList();
		for(int j = 0; j < currentBrickList.size(); j++) {
			Brick currentBrick = currentBrickList.get(j);
			Rectangle brickRect = new Rectangle(currentBrick.getXCoord(), currentBrick.getYCoord(), currentBrick.getWidth(), currentBrick.getHeight());

			// Ball stößt Brick von unten oder oben an
			if((brickRect.contains(ballTop)) || (brickRect.contains(ballBottom))) {
				tempBrick = currentBrick.getId();
				if(!(struckBrick.equals(tempBrick))) {
					currentBrick.setStability(currentBrick.getStability() - 1);
					if(currentBrick.getStability() == 0) {
						gameField.removeElementFromGameField(currentBrick.getId());
						gameWall.removeFromBrickList(currentBrick);
						if(currentBrick.hasBonusObject() == true) {
							activateBonus(currentBrick);
						}
					}
				}
				gameBall.setDirY(-1 * gameBall.getDirY());
				struckBrick = tempBrick;
				return;
			// Ball stößt Brick von links oder rechts an		
			} else if((brickRect.contains(ballLeft)) || (brickRect.contains(ballRight))) {
				tempBrick = currentBrick.getId();
				if(!(struckBrick.equals(tempBrick))) {
					currentBrick.setStability(currentBrick.getStability() - 1);
					if(currentBrick.getStability() == 0) {
						gameField.removeElementFromGameField(currentBrick.getId());
						gameWall.removeFromBrickList(currentBrick);						
					}
				}
				gameBall.setDirX(-1 * gameBall.getDirX());
				struckBrick = tempBrick;
				return;
			}
		}		
	}
	
	private void activateBonus(Brick activeBrick) {
		if(activeBrick.getBonusObject().getBonusType().equals("BonusBallSpeed")) {
			BonusBallSpeed tempBonus = (BonusBallSpeed) activeBrick.getBonusObject();
			int newAccel = tempBonus.setBallSpeed(gameBall.getDirY());
			gameBall.setDirY(newAccel);;
		} else if(activeBrick.getBonusObject().getBonusType().equals("BonusPaddleWidth")) {
			BonusPaddleWidth tempBonus = (BonusPaddleWidth) activeBrick.getBonusObject();
			int newWidth = tempBonus.changePaddleSize(constantPaddleWidth/2);
			gamePaddle.setWidth(newWidth);
			gameField.redrawElement(gamePaddle.getImage(), gamePaddle.getXCoord(), gamePaddle.getYCoord(), gamePaddle.getWidth(), gamePaddle.getHeight());
		} else if(activeBrick.getBonusObject().getBonusType().equals("BonusXtraLife")) {
			System.out.println("Add Xtra Life!");
			BreakWallData.lifeCount++;
		}
	}
	
	
	/**
	 * Statische Klasse zur Erzeugung einer Zufallszahl
	 * innerhalb einer Ober- und Untergrenze.
	 * Ober- und Untergrenze dienen hauptsächlich dazu,
	 * die 0 als mögliches Ergebnis auszuschließen.
	 * 
	 * @param min Untergrenze der möglichen Zufallszahl
	 * @param max Obergrenze der möglichen Zufallszahl
	 * @return randomNum die Zufallszahl innerhalb der Grenzen
	 */
	public static int randomFromRange(int min, int max) {
	    Random random = new Random();
	    int randomNum = random.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	/**
	 * Private Klasse zur Reaktion auf Tastendruck
	 * 
	 * @author Mareike Röncke, Gerrit Schulte
	 *
	 */	
	private class KeyboardEvents implements KeyListener {
		
		public void keyPressed(KeyEvent e) {
			
			// entspricht der linken Pfeiltaste
			if(e.getKeyCode() == 37) {
				// bewege das Paddle nach links
				gamePaddle.movePaddle("left");
			}
			// entspricht der rechten Pfeiltaste
			if(e.getKeyCode() == 39) {
				// bewege das Paddle nach rechts
				gamePaddle.movePaddle("right");								
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}		
	}
	
	/**
	 * Private Klasse zum Scheduling des Game-Loops
	 * Aktualisiert die Position von Spielelementen in einem festgelegten Zeitrahmen 
	 * 
	 * @author Mareike Röncke, Gerrit Schulte
	 *
	 */
    class GameLoopScheduler extends TimerTask {
    	
    	// bewegt den Ball und prüft auf Kollision
        public void run() {      	
        	gameBall.moveBall();
        	gameField.relocateElement(gameBall.getImage(), gameBall.getXCoord(), gameBall.getYCoord());
        	gameField.relocateElement(gamePaddle.getImage(), gamePaddle.getXCoord(), gamePaddle.getYCoord());
        	collisionDetection();
        }
    }
    
    
    
    public void stopGame() {
        timer.cancel();
    }	
}

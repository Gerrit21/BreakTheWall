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
	private Timer timer;
	
	/**
	 * Konstruktoraufruf initiiert die Erzeugung der Spielelemente
	 * und startet den Game-Loop
	 */
	public BreakWall() {
		initGameElements();
        timer = new Timer();
        timer.scheduleAtFixedRate(new GameLoopScheduler(), 500, 10);		
	}

	/**
	 * Klasse erzeugt Objekte aller initial spielrelevanten Elemente,
	 * also Spielfeld, Paddle, Ball und Brick-Wand
	 * und zeichnet diese auf die Spielfläche.
	 */
	private void initGameElements() {
		// Spielelemente erzeugen
		gameField = new BreakWallView();
		gameField.addKeyListener(new KeyboardEvents());
		gamePaddle = new PlayerPaddle();
		
		gameBall = new PlayerBall();
		
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
		if((ballTop.getY() > 0) && (ballBottom.getY() < BreakWallData.offsetHeight)) { 
		// der Ball berührt oder überschreitet die obere Begrenzung des Spielfeldes
	    // die Y-Richtung wird umgekehrt
		} else if((ballTop.getY() <= 0)) {
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
					gameField.removeElementFromGameField(currentBrick.getId());
					gameWall.removeFromBrickList(currentBrick);
					gameBall.setDirY(-1 * gameBall.getDirY());
					return;
			// Ball stößt Brick von links oder rechts		
			} else if((brickRect.contains(ballLeft)) || (brickRect.contains(ballRight))) {				    
					gameField.removeElementFromGameField(currentBrick.getId());
					gameWall.removeFromBrickList(currentBrick);
					gameBall.setDirX(-1 * gameBall.getDirX());
					return;	
			}
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
        	gameField.redrawElement(gameBall.getImage(), gameBall.getXCoord(), gameBall.getYCoord());
        	gameField.redrawElement(gamePaddle.getImage(), gamePaddle.getXCoord(), gamePaddle.getYCoord());
        	collisionDetection();
        }
    }

    public void stopGame() {
        timer.cancel();
    }	
}

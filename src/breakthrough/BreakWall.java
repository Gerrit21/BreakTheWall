package breakthrough;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
	// private BrickFactory gameWall;
	// private ArrayList<Brick> currentBrickList;
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
		gameField.addElementToGameField(gamePaddle.getImage(), gamePaddle.getXCoord(), gamePaddle.getYCoord(), gamePaddle.getWidth(), gamePaddle.getHeight());		
		gameField.addElementToGameField(gameBall.getImage(), gameBall.getXCoord(), gameBall.getYCoord(), gameBall.getWidth(), gameBall.getHeight());
		// Brick-Wand zum Spielfeld hinzufügen
		new BrickFactory(gameField);
				
	}
	
	private void collisionDetection() {		
		// der Ball befindet sich innerhalb der linken und rechten Begrenzung des Spielfeldes
		if((gameBall.getXCoord() > 0) && (gameBall.getXCoord() < (BreakWallData.offsetWidth - gameBall.getWidth()))) {
	    // der Ball berührt oder überschreitet die linke und rechte Begrenzung des Spielfeldes
		// die X-Richtung wird umgekehrt	
		} else if((gameBall.getXCoord() <= 0) || (gameBall.getXCoord() >= (BreakWallData.offsetWidth - gameBall.getWidth()))) {
			gameBall.setDirX(-1 * gameBall.getDirX());
		}		
		// der Ball befindet sich innerhalb der oberen und unteren Begrenzung des Spielfeldes
		if((gameBall.getYCoord() > 0) && (gameBall.getYCoord() < BreakWallData.offsetHeight)) {
			detectPaddleCollision();
			// detectBrickCollision();
		// der Ball berührt oder überschreitet die obere und untere Begrenzung des Spielfeldes
	    // die Y-Richtung wird umgekehrt
		} else if((gameBall.getYCoord() <= 0)) {
			gameBall.setDirY(-1 * gameBall.getDirY());
		} else if(gameBall.getYCoord() > BreakWallData.offsetHeight) {
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
	}
	
	private void detectPaddleCollision() {
		// if(changeDirection == true) {
			int paddleLeft = gamePaddle.getXCoord();
			int paddleRight = gamePaddle.getXCoord() + gamePaddle.getWidth();
			int ballLeft = gameBall.getXCoord() + gameBall.getWidth();
			int ballRight = gameBall.getXCoord();
			// die rechte Ecke und linke Ecke des Balls befinden sich vertikal auf einer Linie
			// mit der linken und rechten Ecke des Paddles
			if((paddleLeft < ballLeft) && (paddleRight > ballRight)) {
				int paddleTop = gamePaddle.getYCoord() + BreakWallData.paddleOffsetTop;
				int paddleBottom = gamePaddle.getYCoord() + gamePaddle.getHeight();
				int ballBottom = gameBall.getYCoord() + gameBall.getHeight();
				// Ball berührt außerdem das Paddle, befindet sich aber nicht darunter
				if((paddleTop <= ballBottom) && (paddleBottom > ballBottom)) {
					gameBall.setDirY(-1 * gameBall.getDirY());
					return;
				}
			}
		// }
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

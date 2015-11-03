package breakthrough;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * Klasse zur Koordinierung der verschiedenen Break-The-Wall-Spielelemente.
 * 
 * @author Mareike R�ncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BreakWall {
		
	private BreakWallView gameField;
	private PlayerPaddle gamePaddle;
	private PlayerBall gameBall;
	
	/**
	 * Konstruktoraufruf erzeugt Objekte aller initial spielrelevanten Elemente,
	 * also Spielfeld, Paddle, Ball und Brick-Wand
	 * und zeichnet diese auf die Spielfl�che.
	 */
	public BreakWall() {
		gameField = new BreakWallView();
		gameField.addKeyListener(new KeyboardEvents());
		gamePaddle = new PlayerPaddle();
		gameField.addElementToGameField(gamePaddle.getImage(), gamePaddle.getXCoord(), gamePaddle.getYCoord(), gamePaddle.getWidth(), gamePaddle.getHeight());
		gameBall = new PlayerBall();
		gameField.addElementToGameField(gameBall.getImage(), gameBall.getXCoord(), gameBall.getYCoord(), gameBall.getWidth(), gameBall.getHeight());
		new BrickFactory(gameField);
	}
	
	/**
	 * Private Klasse zur Reaktion auf Tastendruck
	 * 
	 * @author Mareike R�ncke, Gerrit Schulte
	 *
	 */	
	private class KeyboardEvents implements KeyListener {
		
		public void keyPressed(KeyEvent e) {
			
			// entspricht der linken Pfeiltaste
			if(e.getKeyCode() == 37) {
				// bewege das Paddle nach links
				gamePaddle.movePaddle("left");
				if(gameBall.getState() == false) {
					gameBall.setSpeed(gamePaddle.getSpeed());
					gameBall.restBall("left");					
				}
			}
			// entspricht der rechten Pfeiltaste
			if(e.getKeyCode() == 39) {
				// bewege das Paddle nach rechts
				gamePaddle.movePaddle("right");
				if(gameBall.getState() == false) {
					gameBall.setSpeed(gamePaddle.getSpeed());
					gameBall.restBall("right");
				}								
			}
			// aktualisiere die Position von Ball und Paddle auf dem Spielfeld
			gameField.redrawElement(gameBall.getImage(), gameBall.getXCoord(), gameBall.getYCoord());
			gameField.redrawElement(gamePaddle.getImage(), gamePaddle.getXCoord(), gamePaddle.getYCoord());
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
}

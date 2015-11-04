package breakthrough;

import javax.swing.ImageIcon;

/**
 * Runnable-Klasse zur Erstellung des Paddles.
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class PlayerPaddle implements Runnable {
		
	private String imagePath = BreakWallData.paddleImg;
	private int xCoord = BreakWallData.initialPaddleX;
	private int yCoord = BreakWallData.initialPaddleY;
	private int speed = BreakWallData.paddleSpeed;
	private int width;
	private int height;

	public PlayerPaddle() {
		this.width = new ImageIcon(imagePath).getImage().getWidth(null);
		this.height = new ImageIcon(imagePath).getImage().getHeight(null);		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Bewegt das Paddle auf dem Spielfeld in Geschwindigkeit speed
	 * nach links und rechts, bis die Grenzen des Spielfelds erreicht sind.
	 * @param direction Bewegungsrichtung, durch Tastendruck übergeben
	 */
	public void movePaddle(String direction) {
		int currentX = getXCoord(); 
		if((direction == "left") && (currentX > 0)) {			
			setXCoord(currentX - getSpeed());
		} else if((direction == "right") && (currentX < (BreakWallData.offsetWidth - getWidth()))) {
			setXCoord(currentX + getSpeed());
		}
	}
	
	public int getXCoord() {
		return this.xCoord;
	}
	
	public void setXCoord(int xCoord) {
		this.xCoord = xCoord;
	}
	
	public int getYCoord() {
		return this.yCoord;
	}
	
	public void setYCoord(int yCoord) {
		this.yCoord = yCoord;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setImage(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public String getImage() {
		return this.imagePath;
	}
	
	public void setSpeed(int speed) {
		this.speed =speed;
	}
	
	public int getSpeed() {
		return this.speed;
	}

}

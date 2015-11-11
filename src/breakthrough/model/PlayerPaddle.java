package breakthewall.model;

import javax.swing.ImageIcon;

import breakthewall.BreakWallConfig;

/**
 * Runnable-Klasse zur Erstellung des Paddles.
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class PlayerPaddle implements GameElement {
		
	private String imagePath = BreakWallConfig.paddleImg;
	private int xCoord = BreakWallConfig.initialPaddleX;
	private int yCoord = BreakWallConfig.initialPaddleY;
	private int speed = BreakWallConfig.paddleSpeed;
	private int width;
	private int height;
	private String id;
	private boolean isDestroyed = false;

	public PlayerPaddle() {
		this.id = imagePath;
		this.width = new ImageIcon(imagePath).getImage().getWidth(null);
		this.height = new ImageIcon(imagePath).getImage().getHeight(null);		
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
		} else if((direction == "right") && (currentX < (BreakWallConfig.offsetWidth - getWidth()))) {
			setXCoord(currentX + getSpeed());
		}
	}
	
	public void setSpeed(int speed) {
		this.speed =speed;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	@Override
	public int getXCoord() {
		return this.xCoord;
	}
	
	@Override
	public void setXCoord(int xCoord) {
		this.xCoord = xCoord;
	}
	
	@Override
	public int getYCoord() {
		return this.yCoord;
	}
	
	@Override
	public void setYCoord(int yCoord) {
		this.yCoord = yCoord;
	}
	
	@Override
	public int getHeight() {
		return this.height;
	}
	
	@Override
	public void setHeight(int height) {
		this.height = height;
	}
	
	@Override
	public int getWidth() {
		return this.width;
	}
	
	@Override
	public void setWidth(int width) {
		this.width = width;
	}
	
	@Override
	public void setImage(String imagePath) {
		this.imagePath = imagePath;
	}
	
	@Override
	public String getImage() {
		return this.imagePath;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
		
	}
	
	@Override
	public boolean getDestroyedState() {
		return this.isDestroyed;
	}

	@Override
	public void setDestroyedState(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
		
	}

}

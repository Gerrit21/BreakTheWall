package breakthewall.model;

import javax.swing.ImageIcon;

import breakthewall.BreakWallData;

/**
 * Runnable-Klasse zur Erstellung eines Spielballs.
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class PlayerBall implements GameElement {
	
	private String imagePath = BreakWallData.ballImg;
	private int xCoord = BreakWallData.initialBallX;
	private int yCoord = BreakWallData.initialBallY;
	private int speed = BreakWallData.ballSpeed;
	private int width;
	private int height;
	private boolean isMoving = false;	
	private int directionX = BreakWallData.initialBallXDir * speed;
	private int directionY = BreakWallData.initialBallYDir * speed;
	private String id;
	private boolean isDestroyed = false;
	
	public PlayerBall() {
		this.id = imagePath;
		this.width = new ImageIcon(imagePath).getImage().getWidth(null);
		this.height = new ImageIcon(imagePath).getImage().getHeight(null);
	}
	
	/**
	 * Methode, um den Ball auf dem Spielfeld zu bewegen.
	 */
	
	public void moveBall() {
		setXCoord(xCoord + directionX);
		setYCoord(yCoord + directionY);		
	}
	
	/**
	 * Methode, um den Ball synchron zum Paddle zu bewegen
	 * 
	 * Ermöglicht es, denn Ball erst auf Befehl (z.B. Tastendruck) "abzufeuern"
	 * @param direction Aktuelle Bewegungsrichtung des Paddles
	 */
	public void restBall(String direction) {		
		int currentX = getXCoord(); 
		if((direction == "left") && (currentX > 0)) {			
			setXCoord(currentX - getSpeed());
		} else if((direction == "right") && (currentX < BreakWallData.offsetWidth)) {
			setXCoord(currentX + getSpeed());
		}
	}

	public void setSpeed(int speed) {
		this.speed =speed;
		setDirX(directionX * speed);
		setDirY(directionY * speed);
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public void setDirX(int directionX) {
		this.directionX = directionX;
	}
	
	public int getDirX() {
		return this.directionX;
	}
	
	public void setDirY(int directionY) {
		this.directionY = directionY;
	}
	
	public int getDirY() {
		return this.directionY;
	}	
	
	public boolean getState() {
		return this.isMoving;
	}
	
	public void setState(boolean isMoving) {
		this.isMoving = isMoving;
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

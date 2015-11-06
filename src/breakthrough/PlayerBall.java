package breakthrough;

import javax.swing.ImageIcon;

/**
 * Runnable-Klasse zur Erstellung eines Spielballs.
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class PlayerBall {
	
	private String imagePath = BreakWallData.ballImg;
	private int xCoord = BreakWallData.initialBallX;
	private int yCoord = BreakWallData.initialBallY;
	private int speed = BreakWallData.ballSpeed;
	private int width;
	private int height;
	private boolean isMoving = false;	
	private int directionX = BreakWallData.initialBallXDir * speed;
	private int directionY = BreakWallData.initialBallYDir * speed;
	
	public PlayerBall() {
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

}

package breakthewall.model;

import breakthewall.BreakWallConfig;
import breakthewall.view.BreakWallView;

/**
 * Class to create the ball object
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, October 2015.
 */
public class PlayerBall implements GameElement {
	
	private String imagePath = BreakWallConfig.ballImg;
	private int xCoord = BreakWallConfig.initialBallX;
	private int yCoord = BreakWallConfig.initialBallY;
	private int speed = BreakWallConfig.ballSpeed;
	private int width;
	private int height;
	private boolean isMoving = false;	
	private int directionX = BreakWallConfig.initialBallXDir * speed;
	private int directionY = BreakWallConfig.initialBallYDir * speed;
	private String id;
	private boolean isDestroyed = false;
	
	public PlayerBall() {
		// the id of the ball image is the ball image reference
		this.id = imagePath;
		this.width = BreakWallView.getImageByURL(this, imagePath).getWidth(null);
		this.height = BreakWallView.getImageByURL(this, imagePath).getHeight(null);
	}
	
	/**
	 * Public method to move ball along the x- and y-axis
	 */
	public void moveBall() {
		setXCoord(xCoord + directionX);
		setYCoord(yCoord + directionY);		
	}
	
	/**
	 * Public method to move the ball when it rests on the paddle.
	 * 
	 * @param direction current direction of paddle movement
	 * @param speed current speed of paddle movement
	 */
	public void restBall(String direction, int speed) {		 
		if(direction == "left") {			
			setXCoord(xCoord - speed);
		} else if(direction == "right") {
			setXCoord(xCoord + speed);
		}
	}

	/**
	 * Public method to set the current speed of the ball
	 * 
	 * @param speed new speed of the ball
	 */
	public void setSpeed(int speed) {
		this.speed =speed;
		setDirX(directionX * speed);
		setDirY(directionY * speed);
	}
	
	/**
	 * @return the current speed of the ball
	 */
	public int getSpeed() {
		return this.speed;
	}
	
	/**
	 * Public method sets the x-axis-direction of the ball.
	 * 
	 * @param directionX the new x-axis-direction of the ball
	 */
	public void setDirX(int directionX) {
		this.directionX = directionX;
	}
	
	/**
	 * @return directionX the x-axis-direction of the ball
	 */
	public int getDirX() {
		return this.directionX;
	}

	/**
	 * Public method set the y-axis-direction of the ball.
	 * 
	 * @param directionY the new y-axis-direction of the ball
	 */
	public void setDirY(int directionY) {
		this.directionY = directionY;
	}
	
	/**
	 * @return directionY the y-axis-direction of the ball
	 */
	public int getDirY() {
		return this.directionY;
	}	
	
	/**
	 * @return isMoving whether the ball is moving on the game field (true) or resting on the paddle (false)
	 */
	public boolean getState() {
		return this.isMoving;
	}
	
	/**
	 * Public methods sets whether the ball is moving on the game field (true) or resting on the paddle (false)
	 * 
	 * @param isMoving moving on the game field (true) or resting on the paddle (false)
	 */
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

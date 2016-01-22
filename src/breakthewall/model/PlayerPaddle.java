package breakthewall.model;

import breakthewall.BreakWallConfig;
import breakthewall.view.BreakWallView;

/**
 * Class to create the paddle object
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, October 2015.
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
		// the id of the paddle image is the paddle image reference
		this.id = imagePath;
		this.width = BreakWallView.getImageByURL(this, imagePath).getWidth(null);
		this.height = BreakWallView.getImageByURL(this, imagePath).getHeight(null);		
	}
	
	/**
	 * Public methods moves the paddle on the x-axis of the game field.
	 * "left" means that the x-value decreases down to zero, 
	 * "right" means that the x-value increases up the the game field width. 
	 * 
	 * @param direction direction of the paddle movement, controlled by arrow keys
	 */
	public void movePaddle(String direction) {
		int currentX = getXCoord(); 
		if((direction == "left") && (currentX > 0)) {			
			setXCoord(currentX - getSpeed());
		} else if((direction == "right") && (currentX < (BreakWallConfig.offsetWidth - getWidth()))) {
			setXCoord(currentX + getSpeed());
		}
	}
	
	/**
	 * Public method to set the current speed of the paddle
	 * 
	 * @param speed new speed of the paddle
	 */
	public void setSpeed(int speed) {
		this.speed =speed;
	}
	
	/**
	 * @return the current speed of the paddle
	 */
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

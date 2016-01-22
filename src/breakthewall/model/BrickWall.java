package breakthewall.model;

import java.util.ArrayList;

import breakthewall.BreakWallConfig;

/**
 * Class to build up a brick wall from objects of type "BrickNormal" and "BrickBonus"
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, October 2015.
 */
public class BrickWall {
	
	private GameElement randomBrick;
	private ArrayList<GameElement> brickList;
	private int wallWidth = BreakWallConfig.wallWidth;
	private int wallHeight = BreakWallConfig.wallHeight;
	private int brickX = 30;
	private int brickY = 30 + BreakWallConfig.barHeight;
	
	/**
	 * Constructor call method to build up the wall and initializes the list of bricks
	 */
	public BrickWall() {		
		brickList = new ArrayList<GameElement>();
		buildWall();
	}
	
	/**
	 * Constructor call takes a given brick list as a parameter and sets it as this wall instance's list
	 * 
	 * @param brickList the list of bricks for this wall
	 */
	public BrickWall(ArrayList<GameElement> brickList) {		
		this.brickList = brickList;
	}
	
	/**
	 * Private methods calls a method to create a random brick.
	 * Sets different parameters to the brick to define its position
	 * and appearance on the game field.
	 */
	
	private void buildWall() {
		// sets bricks to the wall within a given height and width
		// the height depends on the current game level
		// the width depends on the actual width of the game field
		while(getBrickY() < wallHeight) {			
			while(getBrickX() < wallWidth) {
				// get a new brick and set parameters to the brick
				randomBrick = getRandomBrick();
				randomBrick.setXCoord(getBrickX());
				randomBrick.setYCoord(getBrickY());				
				randomBrick.setId(Integer.toString(getBrickX()) + Integer.toString(getBrickY()));
				brickList.add(randomBrick);
				setBrickX(getBrickX() + randomBrick.getWidth() + 10);
			}
			setBrickY(getBrickY() + randomBrick.getHeight() + 10);
			setBrickX(BreakWallModel.randomFromRange(1, 3) * 15);
		}
	}
	
	/**
	 * Private method randomly creates a new brick
	 * Possibilities are taken from config values.
	 */
	private void setRandomBrick() {
		int randomInt = BreakWallModel.randomFromRange(1, 100);
		if(randomInt <= BreakWallConfig.normalPossible) {
			randomBrick = new BrickNormal(BreakWallConfig.stabilityNormal);
		} else if(randomInt > BreakWallConfig.normalPossible) {
			if(randomInt < (BreakWallConfig.bonusPossible + BreakWallConfig.normalPossible)) {
				randomBrick = new BrickBonus();				
			} else if(randomInt >= (BreakWallConfig.bonusPossible + BreakWallConfig.normalPossible)) {
				randomBrick = new BrickNormal(BreakWallConfig.stabilityHard);
			}
		}
	}
	
	/** 
	 * @return a random new brick object
	 */
	private GameElement getRandomBrick() {
		setRandomBrick();
		return this.randomBrick;
	}
	
	/**
	 * @return the brick list of the brick wall object
	 */
	public ArrayList<GameElement> getBrickList() {
		return this.brickList;
	}
	
	/**
	 * @param brickList set the brick list for a new brick wall object
	 */
	public void setBrickList(ArrayList<GameElement> brickList) {
		this.brickList = brickList;
	}
	
	/**
	 * @param removeBrick a brick that should be removed from the brick list
	 */
	public void removeFromBrickList(GameElement removeBrick) {
		this.brickList.remove(removeBrick);
	}
	
	/** 
	 * @param x the x-position of a brick in regard of the brick wall
	 */
	public void setBrickX(int x) {
		this.brickX = x;
	}

	/**
	 * @return x-position of the current brick
	 */
	public int getBrickX() {
		return this.brickX;
	}
	
	/** 
	 * @param y the x-position of a brick in regard of the brick wall 
	 */
	public void setBrickY(int y) {
		this.brickY = y;
	}
	
	/**
	 * @return y-position of the current brick
	 */
	public int getBrickY() {
		return this.brickY;
	}

}

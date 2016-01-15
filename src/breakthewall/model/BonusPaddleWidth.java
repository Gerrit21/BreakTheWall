package breakthewall.model;

import breakthewall.BreakWallConfig;

/** 
 * Class of bonus object which changes paddle width 
 * when brick inheriting this bonus type is hit
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, October 2015.
 */
public class BonusPaddleWidth implements Bonus {

	private String imagePath = BreakWallConfig.brickPaddleWidthImg;
	private int tempSize;
	
	/**
	 * Changes the paddle size by a random number
	 * between 20 Pixels and the width of the original paddle image
	 * 
	 * @param paddleWidth original width of paddle image
	 * @return tempSize new paddle size
	 */
	public int changePaddleSize(int paddleWidth) {
		tempSize = BreakWallModel.randomFromRange(20, (paddleWidth*2));
		return tempSize;
	}

	/**
	 * @ return imagePath image path of image associated with this bonus
	 */
	@Override
	public String getImage() {
		return this.imagePath;
	}

	/**
	 * @ return bonusType simple name of this class
	 */
	@Override
	public String getBonusType() {
		return this.getClass().getSimpleName();
	}

}

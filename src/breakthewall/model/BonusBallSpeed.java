package breakthewall.model;

import breakthewall.BreakWallConfig;

/** 
 * Class of bonus object which changes ball speed 
 * when brick inheriting this bonus type is hit
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, October 2015.
 */
public class BonusBallSpeed implements Bonus {
	
	private String imagePath = BreakWallConfig.brickBallSpeedImg;
	private String bonusType = this.getClass().getSimpleName();
	private int newBallSpeed;
	
	/** 
	 * Public method returns double speed of ball 
	 * 
	 * @param acceleration current ball speed
	 * @return	newBallSpeed
	 */
	public int setBallSpeed(int ballSpeed) {
		newBallSpeed = ballSpeed * 2;
		return newBallSpeed;
	}
	
	@Override
	public String getBonusType() {
		return bonusType;
	}

	@Override
	public String getImage() {
		return this.imagePath;
	}

}

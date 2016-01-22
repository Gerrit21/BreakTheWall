package breakthewall.model;

import breakthewall.BreakWallConfig;

/** 
 * Class of bonus object which returns an extra life 
 * when brick inheriting this bonus type is hit
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, October 2015.
 */
public class BonusXtraLife implements Bonus {
	
	private String imagePath = BreakWallConfig.brickXtraLifeImg;

	/**
	 * @return an extra life
	 */
	public int addXtraLife() {
		return 1;
	}

	@Override
	public String getImage() {
		return this.imagePath;
	}

	@Override
	public String getBonusType() {
		return this.getClass().getSimpleName();
	}

}

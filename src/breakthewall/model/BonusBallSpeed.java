package breakthewall.model;

import breakthewall.BreakWallConfig;

/** 
 * Klasse zur Erzeugung eines Bonus-Objekts, das die Geschwindigkeit des Balls ver�ndert, 
 * wenn ein Brick mit diesem Bonus getroffen wurde.
 * 
 * @author Mareike R�ncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BonusBallSpeed implements Bonus {
	
	private String imagePath = BreakWallConfig.brickImgNormal;
	
	public String getBonusType() {
		return this.getClass().getSimpleName();
	}
	
	public int setBallSpeed(int acceleration) {
		return acceleration * 2;
	}

	@Override
	public String getImage() {
		return this.imagePath;
	}

}

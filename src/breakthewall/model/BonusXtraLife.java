package breakthewall.model;

import breakthewall.BreakWallConfig;

/**
 * Klasse zur Erzeugung eines Bonus-Objekts, dass dem Spieler
 * ein Leben hinzuf�gt, wenn ein Brick mit diesem Bonus getroffen wurde.
 * 
 * @author Mareike R�ncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BonusXtraLife implements Bonus {
	
	private String imagePath = BreakWallConfig.brickXtraLifeImg;
	
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

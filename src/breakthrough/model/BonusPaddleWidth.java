package breakthewall.model;

import breakthewall.BreakWallData;

/**
 * Klasse zur Erzeugung eines Bonus-Objekts, das die Breite des Paddles
 * verändert, wenn ein Brick mit diesem Bonus getroffen wurde.
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BonusPaddleWidth implements Bonus {

	private String imagePath = BreakWallData.brickImgNormal;
	private int tempSize;
	
	public int changePaddleSize(int paddleWidth) {
		tempSize = BreakWallModel.randomFromRange((paddleWidth/2), (paddleWidth*2));
		return tempSize;
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

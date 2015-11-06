package breakthrough;

/** 
 * Klasse zur Erzeugung eines Bonus-Objekts, das die Geschwindigkeit des Balls verändert, 
 * wenn ein Brick mit diesem Bonus getroffen wurde.
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BonusBallSpeed implements Bonus {
	
	private String imagePath = BreakWallData.brickImgNormal;
	
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

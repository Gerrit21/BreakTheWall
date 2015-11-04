package breakthrough;

/** 
 * Klasse zur Erzeugung eines Bonus-Objekts, das die Geschwindigkeit des Balls ver�ndert, 
 * wenn ein Brick mit diesem Bonus getroffen wurde.
 * 
 * @author Mareike R�ncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BonusBallSpeed implements Bonus {
	
	private String imagePath = BreakWallData.brickImgNormal;
	
	@Override
	public void activate() {
		System.out.println("Change ball speed");
		
	}

	@Override
	public String getImage() {
		return this.imagePath;
	}

}

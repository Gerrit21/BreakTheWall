package breakthrough;

/**
 * Klasse zur Erzeugung eines Bonus-Objekts, das die Breite des Paddles
 * ver�ndert, wenn ein Brick mit diesem Bonus getroffen wurde.
 * 
 * @author Mareike R�ncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BonusPaddleWidth implements Bonus {

	private String imagePath = BreakWallData.brickImgNormal;
	
	@Override
	public void activate() {
		System.out.println("Shorten or widen paddle");
		
	}

	@Override
	public String getImage() {
		return this.imagePath;
	}

}

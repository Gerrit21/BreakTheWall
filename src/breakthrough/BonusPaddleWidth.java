package breakthrough;

/**
 * Klasse zur Erzeugung eines Bonus-Objekts, das die Breite des Paddles
 * verändert, wenn ein Brick mit diesem Bonus getroffen wurde.
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BonusPaddleWidth implements Bonus {

	@Override
	public void activate() {
		System.out.println("Shorten or widen paddle");
		
	}

}

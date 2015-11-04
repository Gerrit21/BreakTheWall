package breakthrough;

/**
 * Klasse zur Erzeugung eines Bonus-Objekts, dass dem Spieler
 * ein Leben hinzufügt, wenn ein Brick mit diesem Bonus getroffen wurde.
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BonusXtraLife implements Bonus {
	
	private String imagePath = BreakWallData.brickXtraLifeImg;
	
	@Override
	public void activate() {
		// System.out.println("Add extra life");		
	}

	@Override
	public String getImage() {
		return this.imagePath;
	}

}

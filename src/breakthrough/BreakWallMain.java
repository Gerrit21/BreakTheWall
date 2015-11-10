package breakthewall;

import breakthewall.model.BreakWallModel;
import breakthewall.view.BreakWallView;

/**
 * Diese Main-Methode startet das Spiel.
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BreakWallMain {

	public static void main(String[] args) {
		BreakWallModel newModel = new BreakWallModel(1);
		new BreakWallView(newModel);

	}

}

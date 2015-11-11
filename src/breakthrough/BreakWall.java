package breakthewall;

import breakthewall.controller.BreakWallController;
import breakthewall.model.BreakWallModel;
import breakthewall.view.BreakWallView;

public class BreakWall {
	
	private static volatile BreakWall gameInstance;
	
	/**
	 * Öffentlicher Konstruktoraufruf stellt sicher,
	 * dass nur eine Instanz des Spiels erzeugt wird
	 * Singleton-Entwurfsmuster wird verwendet
	 */
	public static BreakWall getInstance(int initialLevel) {
		if (gameInstance == null) {
			gameInstance = new BreakWall(initialLevel);
		}
		return gameInstance;
	}

	/**
	 * Privater Konstruktoraufruf initiiert das Spiel
	 * MVC-Entwurfsmuster wird verwendet
	 */
	private BreakWall(int initialLevel) {
		BreakWallModel newModel = new BreakWallModel(initialLevel);
		BreakWallController newController = new BreakWallController(newModel);
		new BreakWallView(newModel, newController);
	}

}
